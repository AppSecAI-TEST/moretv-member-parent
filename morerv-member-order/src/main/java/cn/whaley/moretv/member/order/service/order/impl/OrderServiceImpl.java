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
import cn.whaley.moretv.member.base.dto.pay.PayRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.manager.PayManage;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
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
    public ResultResponse pay(PayRequest payRequest) {
        //TODO 1、MD5
        
        //2、验证
        //2.1、验证商品
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        String goodsStr = opsHash.get(CacheKeyConstant.REDIS_KEY_GOODS, payRequest.getGoodsCode());
        if(StringUtils.isEmpty(goodsStr)){
            logger.error("申请支付, 商品不存在, 订单code->{}", payRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
        }
        
        //2.2、验证订单
        Order order = orderMapper.getByOrderCode(payRequest.getOrderCode());
        if(order == null){
            logger.error("申请支付, 订单不存在, 订单code->{}", payRequest.getOrderCode());
            return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
        }
        
        if(order.getTradeStatus().intValue() != OrderEnum.TradeStatus.TRADE_INIT.getCode()
                || order.getPayStatus().intValue() != OrderEnum.PayStatus.WAITING_PAY.getCode()){
            logger.error("申请支付,订单状态错误, 订单->{}", order.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_ORDER_STATUS_ERR);
        }
        
        //3、向支付网关支付
        Map<String, Object> result = null;
        try {
            result = PayManage.preCreateOrder(payRequest.getPayType(), payRequest.getOrderCode(),
                    String.valueOf(payRequest.getFree()), payRequest.getGoodsCode());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        OrderPayResponse resOrderPayInfo = new OrderPayResponse();
        
        if (Integer.parseInt(result.get("status").toString()) == ApiCodeInfo.API_OK) {
            resOrderPayInfo.setContent(result.get("content").toString());
            resOrderPayInfo.setOrderNo(payRequest.getOrderCode());
            resOrderPayInfo.setPayInfo(result.get("data").toString());
            resOrderPayInfo.setLoopUrl("");//TODO 
        } else {
            logger.error("申请支付, 支付网关错误, 订单->{}", order.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_PAY_GATEWAY_ERR);
        }
            
        //4、更新订单状态
        Map<String, Object> map = new HashMap<>();
        map.put("updateTime", new Date());
        map.put("newPayStatus", OrderEnum.PayStatus.PAYING.getCode());
        map.put("oldPayStatus", order.getPayStatus());
        map.put("orderCode", payRequest.getOrderCode());
        orderMapper.updateOrderPayStatus(map);
        
        return ResultResponse.success(resOrderPayInfo);
    }
}
