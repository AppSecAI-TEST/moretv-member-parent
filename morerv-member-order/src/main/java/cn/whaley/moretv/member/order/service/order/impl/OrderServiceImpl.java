package cn.whaley.moretv.member.order.service.order.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.order.service.order.OrderService;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.goods.BaseGoodsSkuService;
import cn.whaley.moretv.member.service.order.BaseOrderItemService;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;

public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
	BaseGoodsService baseGoodsService;
	
	@Autowired
	BaseGoodsSkuService baseGoodsSkuService;
	
	@Autowired
	BaseOrderItemService baseOrderItemService;
	
	@Override
	public ResultResponse creatOrder(String goodsCode, String payType,
			int payAutoRenew, int accountId) {
		Goods goods = null;
		GoodsSku goodsSku = null;
		Date now = new Date();
		//检查商品
	    ResultResponse<Goods> goodCheck= baseGoodsService.checkCanBuyGoods(goodsCode,accountId);
	    if(goodCheck.isSuccess()){
	    	return goodCheck;
	    }else{
	    	goods = goodCheck.getData();
	    }
	    
	    List<GoodsSku> goodsSkuList = baseGoodsSkuService.getGoodsSkuByGoodsNo(goodsCode);
	    if(goodsSkuList == null || goodsSkuList.size()>1){
	    	ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
	    }else{
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
	    orderItem = baseOrderItemService.createOrderItemByGoodsSku(goodsSku, orderItem);
		return null;
	}
}
