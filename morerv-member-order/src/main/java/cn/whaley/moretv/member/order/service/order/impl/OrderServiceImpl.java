package cn.whaley.moretv.member.order.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.message.MessagePolicy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayResponse;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.manager.PayManage;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.base.util.MessageProducer;
import cn.whaley.moretv.member.base.util.paygateway.PayGatewayUtil;
import cn.whaley.moretv.member.model.cp.CpAccount;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.order.dto.pay.OrderPayResponse;
import cn.whaley.moretv.member.order.service.order.OrderService;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;
import cn.whaley.moretv.member.service.queue.publish.PublishMemberToAdmin;

@Service
public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private BaseGoodsService baseGoodsService;
	
	@Autowired
	private PublishMemberToAdmin publishMemberToAdmin;
	
	@Transactional
	@Override
	public ResultResponse<Order> createOrder(String goodsCode, String payType,
			int payAutoRenew, int accountId) {
		Goods goods = null;
		GoodsSku goodsSku = null;
		Date now = new Date();
		//检查商品
	    ResultResponse<GoodsDto> goodCheck= baseGoodsService.checkCanBuyGoods(goodsCode,accountId);
	    if (!goodCheck.isSuccess()) {
	    	logger.info("goodsError:"+goodCheck.toString());
	    	return ResultResponse.define(goodCheck.getCode(),goodCheck.getMsg());
	    } else {
	    	goods = goodCheck.getData();
	    }
	    
	    List<GoodsSku> goodsSkuList = goodCheck.getData().getGoodsSkuList();
	    if (goodsSkuList == null || goodsSkuList.size() > 1) {
	    	logger.info("goodsSkuError");
	    	return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
	    } else {
	    	goodsSku = goodsSkuList.get(0);
	    }
	    
	    //创建订单
	    Order order =new Order();
	    order.setAccountId(accountId);
	    order.setBusinessType(OrderEnum.BusinessType.PAY.getCode());
	    order.setOrderChannel(OrderEnum.OrderChannel.WAP.getCode());
	    order.setOrderChannelKey(null);
	    order.setIsAutoRenewal(payAutoRenew);
	    order.setPayChannel(payType);
	    order.setOrderType(OrderEnum.OrderType.BUY.getCode());
	    order.setValidStatus(GlobalEnum.Status.VALID.getCode());
	    order.setTradeStatus(OrderEnum.TradeStatus.TRADE_INIT.getCode());
	    order.setPayStatus(OrderEnum.PayStatus.WAITING_PAY.getCode());
	    order.setCreateTime(now);
	    order.setOverTime(DateFormatUtil.addHours(now, 2));
	    order = createOrderByGoods(goods,order);

	    //创建订单明细
	    OrderItem orderItem = new OrderItem();
	    orderItem.setOrderCode(order.getOrderCode());
	    orderItem.setCreateTime(now);
	    orderItem = createOrderItemByGoodsSku(goodsSku, orderItem);

	    publishMemberToAdmin.publishOrder(order);
	    publishMemberToAdmin.publishOrderItem(orderItem);
	    
	    return ResultResponse.success(order);
	}
	
    @Override
    public ResultResponse pay(PayGatewayRequest payGatewayRequest) {
        Date now = new Date();
        //1、验证MD5
        if(!checkSign(payGatewayRequest)){
            logger.error("申请支付, md5验证失败, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_SIGN_ERR);
        }
        
        //2、验证订单超时时间
        long overtime = payGatewayRequest.getCreateTime().longValue() + (payGatewayRequest.getExpireTime().longValue() * 60 * 1000);
        if(overtime > now.getTime()){
            logger.error("申请支付, 订单已超时, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_ORDER_OVER_TIME_ERR);
        }
        
        //3、验证商品是否存在redis中
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        String goodsStr = opsHash.get(CacheKeyConstant.REDIS_KEY_GOODS, payGatewayRequest.getGoodsCode());
        if(StringUtils.isEmpty(goodsStr)){
            logger.error("申请支付, 商品不存在, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
        }
        
        //4、更新订单状态为【支付中】，防止并发请求
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", now);
        map.put("newPayStatus", OrderEnum.PayStatus.PAYING.getCode());
        map.put("oldPayStatus", OrderEnum.PayStatus.WAITING_PAY.getCode());
        map.put("orderCode", payGatewayRequest.getOrderCode());
        map.put("tradeStatus", OrderEnum.TradeStatus.TRADE_INIT.getCode());
        int result = orderMapper.updateOrderPayStatus(map);//在当前连接事务提交前，其他连接都在这里等待;而当前事务提交后，支付状态已经是2了，其他的连接执行的话影响行数必为0
        
        if(result == 0){
            logger.error("申请支付,订单不存在或订单状态错误！->{}",payGatewayRequest.toString());
            throw new RuntimeException("申请支付, 订单不存在或订单状态错误！");
        }
        
        //5、向支付网关支付
        PayGatewayResponse payGatewayResponse = PayGatewayUtil.pay(payGatewayRequest);
        
        if(payGatewayResponse.getStatus().intValue() != 1){
            logger.error("申请支付, 支付网关错误, 支付网关返回->{}", payGatewayResponse.toString());
            throw new RuntimeException("向支付网关申请支付失败");
        }
        
        return ResultResponse.success(new OrderPayResponse(payGatewayResponse.getContent()));
    }

    private boolean checkSign(PayGatewayRequest payGatewayRequest) {
        //拼接MD5的参数
        String param = PayManage.getParams4Sign(payGatewayRequest.getCip(), payGatewayRequest.getTimestamp(), 
                payGatewayRequest.getGoodsCode(), payGatewayRequest.getSubject(), payGatewayRequest.getPayAutoRenew(), 
                payGatewayRequest.getPayType(), payGatewayRequest.getOrderCode(), payGatewayRequest.getFee(), payGatewayRequest.getAccountId()).toString();
        
        if(PayManage.getPayUrlSign(param).equals(payGatewayRequest.getSign()))
            return true;
        
        return false;
    }
}
