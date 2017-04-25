package cn.whaley.moretv.member.notify.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.notify.service.order.OrderService;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.order.BaseOrderService;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;

@Service
@Transactional
public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
    protected BaseGoodsService baseGoodsService;
	
    /**
     * 通知发货
     * @param orderNo
     * @param orderStatus 支付网关状态
     * @return
     */
 	public ResultResponse delivery(String orderNo, String orderStatus) {
 		do {
    		Map<String,String> data = new HashMap<String,String>();
    		
    		if(!OrderEnum.PayStatus.DONE.getNameEng().equals(orderStatus) || !OrderEnum.PayStatus.TIMEOUT.getNameEng().equals(orderStatus)){
    			ResultResponse.define(ApiCodeEnum.API_PARAM_PAY_NOTIFY_STATUS_ERR);
    		}
    		
 			Order order = orderMapper.getByOrderCode(orderNo);
 			if (order==null) {
 				logger.error("订单{}不存在", orderNo);
 				return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
 			}
 			logger.error("订单{}:{}", orderNo,order.toString());
 			data.put("order_no", orderNo);
 			if(!OrderEnum.TradeStatus.TRADE_INIT.equals(order.getTradeStatus())){
 				data.put("order_status", OrderEnum.TradeStatus.getNameEngByCode(order.getTradeStatus()));
 				return ResultResponse.define(ApiCodeEnum.API_PARAM_PAY_NOTIFY_STATUS_ERR,data);
 			}
 			
 			//修改订单状态
 			if(OrderEnum.PayStatus.DONE.getNameEng().equals(orderStatus)){
 				order.setPayStatus(OrderEnum.PayStatus.DONE.getCode());
 				order.setUpdateTime(new Date());
 	 			orderMapper.updateByPrimaryKeySelective(order);
    		}else{
    			order.setPayStatus(OrderEnum.PayStatus.TIMEOUT.getCode());
    			order.setTradeStatus(OrderEnum.TradeStatus.TRADE_TIMEOUT.getCode());
    			order.setUpdateTime(new Date());
     			orderMapper.updateByPrimaryKeySelective(order);
 				data.put("order_status", OrderEnum.TradeStatus.getNameEngByCode(order.getTradeStatus()));
 				return ResultResponse.success(data);
    		}
 			
 			
 			//判定是否是首次购买商品
 			GoodsDto goods = baseGoodsService.getGoodsByGoodsNo(order.getGoodsCode());
 			if(GlobalEnum.GoodsClass.FIRST_GOODS.getCode() == goods.getGoodsClass()){
 				ResultResponse<GoodsDto> result =baseGoodsService.checkCanBuyGoods(order.getGoodsCode(), order.getAccountId());
 				if(ApiCodeInfo.API_OK !=result.getCode()){
 					//TODO 返回类型比较复杂
 					return result;
 				}
 			}
 			
 	
 			
 			//锁定用户，保持一个用户购买单个会员
 			
 			//订购会员
 			
 			/*TempInfo info=new TempInfo();
 			if (orderStatus.equals(GlobleConstant.ORDER_STATUS_STR_ARRAY[GlobleConstant.ORDER_STATUS_WAITING_PAYED])) {
 				
 				LogHelper.getLogger().info("判定是否是升级商品是否可以升级");
 				CheckUpgradeGoods result = checkUpgradePlan(whaleyOrder.getSn(),whaleyOrder.getWhaleyAccount(),whaleyOrder.getGoodsNo(),whaleyOrder);
 				if (ApiCodeInfo.API_OK!=result.getStatus()) {
 					res.setStatus(result.getStatus());
 					res.setMsg(result.getMsg());
 					break;
 				}
 				
 				LogHelper.getLogger().info("订单信息状态修改");
 				updateOrderStatus(whaleyOrder.getId(), orderStatus,res);
 				if (ApiCodeInfo.API_OK!=res.getStatus()) {
 					break;
 				}
 				
 				
 				LogHelper.getLogger().info("订购会员");
 				res = clubService.orderMember(whaleyOrder.getId(),result.getDesignatedTime());
 				if (res.getStatus()!=ApiCodeInfo.API_OK) {
 					info.setOrder_status(GlobleConstant.ORDER_STATUS_STR_ARRAY[GlobleConstant.ORDER_STATUS_TRANSACTION_FINISHED]);
 				}else {
 					info.setOrder_status(res.getOrder_status());
 					info.setProducts(res.getProducts());
 				}
 				
 			}else if (orderStatus.equals(GlobleConstant.ORDER_STATUS_STR_ARRAY[GlobleConstant.ORDER_STATUS_TIMEOUT])) {
 				LogHelper.getLogger().info("订单信息状态修改");
 				updateOrderStatus(whaleyOrder.getId(), orderStatus,res);
 				if (ApiCodeInfo.API_OK!=res.getStatus()) {
 					break;
 				}
 				info.setOrder_status(GlobleConstant.ORDER_STATUS_STR_ARRAY[GlobleConstant.ORDER_STATUS_TIMEOUT]);
 			}else {
 				res.setStatus(ApiCodeInfo.API_ORDER_STATUS_ERR);
 				res.setMsg(ApiCodeInfo.getErrMsg(ApiCodeInfo.API_ORDER_STATUS_ERR));
 				res.setOrder_status(GlobleConstant.ORDER_STATUS_STR_ARRAY[whaleyOrder.getOrderStatus()]);
 				break;
 			}
 			
 			LogHelper.getLogger().info("通知临时服务器更改状态");
 			PayManage.notifyTempServer(orderNo, JSONObject.fromObject(info).toString());*/
 			
 		} while (false);
 		
 		return null;
 	}
    
}
