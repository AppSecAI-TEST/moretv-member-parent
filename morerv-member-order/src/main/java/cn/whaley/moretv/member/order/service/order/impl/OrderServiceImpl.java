package cn.whaley.moretv.member.order.service.order.impl;

import java.util.Date;
import java.util.List;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	    
	    
	    	/*sessionToken	会话级别标识，由安全中心获取【String】	   
	     	cip	客户端ip	   
	     	timestamp	当前时间戳【Long】	   
	     	version	当前接口版本，默认1.0【String】	   
	     	goodsCode	商品编码【String】	   
	     	subject	订单名称【String】	   
	     	payAutoRenew	是否自动续费【String】	   
	     	payType              	支付类型，alipay, wechat pay",【String】	   
	     	orderCode	订单号【String】	   
	     	fee	支付价格（分）【Long】	   
	     	expire_time	超时时间【Long】	   
	     	accountId	账号【int】	   
	     	sign	数据签名【Long】	*/ 

	    
	   
	    
		return null;
	}
}
