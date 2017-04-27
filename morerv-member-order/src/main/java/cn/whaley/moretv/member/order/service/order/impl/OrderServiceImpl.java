package cn.whaley.moretv.member.order.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayResponse;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.manager.PayManage;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.base.util.paygateway.PayGatewayUtil;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.order.dto.pay.OrderPayResponse;
import cn.whaley.moretv.member.order.service.order.OrderService;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;

@Service
@Transactional
public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	private BaseGoodsService baseGoodsService;
	
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
	    //TODO 同步数据
	    //创建订单明细
	    OrderItem orderItem = new OrderItem();
	    orderItem.setOrderCode(orderItem.getOrderCode());
	    orderItem.setCreateTime(now);
	    orderItem = createOrderItemByGoodsSku(goodsSku, orderItem);
	    //TODO 同步数据

		return ResultResponse.success(order);
	}
	
    @Override
    public ResultResponse pay(PayGatewayRequest payGatewayRequest) {
        //1、验证
        //1.1、验证商品是否存在redis中
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        String goodsStr = opsHash.get(CacheKeyConstant.REDIS_KEY_GOODS, payGatewayRequest.getGoodsCode());
        if(StringUtils.isEmpty(goodsStr)){
            logger.error("申请支付, 商品不存在, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
        }
        
        //2、更新订单状态为【支付中】，防止并发请求
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", new Date());
        map.put("newPayStatus", OrderEnum.PayStatus.PAYING.getCode());
        map.put("oldPayStatus", OrderEnum.PayStatus.WAITING_PAY.getCode());
        map.put("orderCode", payGatewayRequest.getOrderCode());
        map.put("tradeStatus", OrderEnum.TradeStatus.TRADE_INIT.getCode());
        int result = orderMapper.updateOrderPayStatus(map);//在当前连接事务提交前，其他连接都在这里等待;而当前事务提交后，支付状态已经是2了，其他的连接执行的话影响行数必为0
        
        if(result == 0){
            logger.error("申请支付,订单不存在或订单状态错误！->{}",payGatewayRequest.toString());
            throw new RuntimeException("申请支付, 订单不存在或订单状态错误！");
        }
        
        //3、获取订单，上面影响行数为1说明订单肯定存在，状态也是正确的
        Order order = orderMapper.getByOrderCode(payGatewayRequest.getOrderCode());
        
        //4、根据订单的数据去验证请求的MD5
        if(!checkSign(payGatewayRequest, order)){
            logger.error("申请支付, md5验证失败, 请求参数->{}", payGatewayRequest.toString());
            throw new RuntimeException("申请支付, MD5验证失败");
        }
        
        //5、向支付网关支付
        PayGatewayResponse payGatewayResponse = PayGatewayUtil.pay(payGatewayRequest, order);
        
        if(payGatewayResponse.getStatus().intValue() != 1){
            logger.error("申请支付, 支付网关错误, 支付网关返回->{}", payGatewayResponse.toString());
            throw new RuntimeException("向支付网关申请支付失败");
        }
        
        return ResultResponse.success(new OrderPayResponse(payGatewayResponse.getContent()));
    }

    private boolean checkSign(PayGatewayRequest payGatewayRequest, Order order) {
        //拼接MD5的参数
        String param = PayManage.getParams4Sign(payGatewayRequest.getCip(), payGatewayRequest.getTimestamp(), 
                order.getGoodsCode(), order.getOrderTitle(), order.getIsAutoRenewal(), order.getPayChannel(), 
                order.getOrderCode(), order.getRealPrice(), order.getAccountId()).toString();
        
        if(PayManage.getPayUrlSign(param).equals(payGatewayRequest.getSign()))
            return true;
        
        return false;
    }
}
