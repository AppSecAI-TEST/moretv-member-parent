package cn.whaley.moretv.member.order.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
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

	@Autowired
	private BaseGoodsService baseGoodsService;
	
	@Override
	public ResultResponse createOrder(String goodsCode, String payType,
			int payAutoRenew, int accountId) {
		Goods goods = null;
		GoodsSku goodsSku = null;
		Date now = new Date();
		//检查商品
	    ResultResponse<GoodsDto> goodCheck= baseGoodsService.checkCanBuyGoods(goodsCode,accountId);
	    if (!goodCheck.isSuccess()) {
	    	return goodCheck;
	    } else {
	    	goods = goodCheck.getData();
	    }
	    
	    List<GoodsSku> goodsSkuList = goodCheck.getData().getGoodsSkuList();
	    if (goodsSkuList == null || goodsSkuList.size() > 1) {
	    	ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
	    } else {
	    	goodsSku = goodsSkuList.get(0);
	    }
	    
	    //创建订单
	    Order order =new Order();
	    order.setAccountId(accountId);
	    order.setBusinessType(OrderEnum.BusinessType.PAY.getCode());
	    order.setOrderChannel(null);
	    order.setOrderChannelKey(null);
	    order.setIsAutoRenewal(payAutoRenew);
	    order.setOrderType(OrderEnum.OrderType.BUY.getCode());
	    order.setValidStatus(GlobalEnum.Status.VALID.getCode());
	    order.setTradeStatus(OrderEnum.TradeStatus.TRADE_INIT.getCode());
	    order.setPayStatus(OrderEnum.PayStatus.WAITING_PAY.getCode());
	    order.setCreateTime(now);
	    order.setOverTime(DateFormatUtil.addHours(now, 2));
	    order = createOrderByGoods(goods,order);
	    
	    //创建订单明细
	    OrderItem orderItem = new OrderItem();
	    orderItem.setOrderCode(orderItem.getOrderCode());
	    orderItem.setCreateTime(now);
	    orderItem = createOrderItemByGoodsSku(goodsSku, orderItem);
		return null;
	}

    @Override
    public ResultResponse pay(PayGatewayRequest payGatewayRequest) {
        //TODO 1、MD5
        
        //2、验证
        //2.1、验证商品
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        String goodsStr = opsHash.get(CacheKeyConstant.REDIS_KEY_GOODS, payGatewayRequest.getGoodsCode());
        if(StringUtils.isEmpty(goodsStr)){
            logger.error("申请支付, 商品不存在, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
        }
        
        //2.2、验证订单
        Order order = orderMapper.getByOrderCode(payGatewayRequest.getOrderCode());
        if(order == null){
            logger.error("申请支付, 订单不存在, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
        }
        
        if(order.getTradeStatus().intValue() != OrderEnum.TradeStatus.TRADE_INIT.getCode()
                || order.getPayStatus().intValue() != OrderEnum.PayStatus.WAITING_PAY.getCode()){
            logger.error("申请支付,订单状态错误, 订单信息->{}", order.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_ORDER_STATUS_ERR);
        }
        
        //3、向支付网关支付
        PayGatewayResponse payGatewayResponse = PayGatewayUtil.pay(payGatewayRequest);
        if(payGatewayResponse == null ){
            logger.error("申请支付, 支付网关http返回非200, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_PAY_GATEWAY_ERR);
        }
        
        if(payGatewayResponse.getStatus().intValue() != 1){
            logger.error("申请支付, 支付网关错误, 支付网关返回->{}", payGatewayResponse.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_PAY_GATEWAY_ERR);
        }
        
        //4、更新订单状态, 只会从[待支付]更新到[支付中]
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", new Date());
        map.put("newPayStatus", OrderEnum.PayStatus.PAYING.getCode());
        map.put("oldPayStatus", order.getPayStatus());
        map.put("orderCode", payGatewayRequest.getOrderCode());
        orderMapper.updateOrderPayStatus(map);

        return ResultResponse.success(new OrderPayResponse(payGatewayResponse.getContent()));
    }
}
