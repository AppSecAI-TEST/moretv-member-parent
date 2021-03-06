package cn.whaley.moretv.member.notify.service.order.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.util.RedisLock;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.notify.service.member.MemberOpsService;
import cn.whaley.moretv.member.notify.service.order.OrderService;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;
import cn.whaley.moretv.member.service.queue.publish.PublishMemberToAdmin;

@Service
public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	
	@Autowired
    protected BaseGoodsService baseGoodsService;
	
	@Autowired
    protected MemberOpsService memberOpsService;
	
    @Autowired
    protected RedisTemplate redisTemplate;
    
	@Autowired
	private PublishMemberToAdmin publishMemberToAdmin;
	
    /**
     * 通知发货
     * @param orderNo 订单号
     * @param orderStatus 支付网关状态
     *  @param fee 价格
     * @return
     */
 	public ResultResponse delivery(String orderNo, String orderStatus, int fee) {

		if (!OrderEnum.PayStatus.DONE.getNameEng().equals(orderStatus)
				&& !OrderEnum.PayStatus.TIMEOUT.getNameEng().equals(orderStatus)){
			return ResultResponse.define(ApiCodeEnum.API_PARAM_PAY_NOTIFY_STATUS_ERR);
		}

		Order order = orderMapper.getByOrderCode(orderNo);
		if (order == null) {
			logger.error("订单{}不存在", orderNo);
			return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
		}

		logger.error("订单{}:{}", orderNo, order.toString());
		if (OrderEnum.TradeStatus.TRADE_INIT.getCode() != order.getTradeStatus()
				&& OrderEnum.TradeStatus.WAITING_SEND.getCode() != order.getTradeStatus()) {
			return ResultResponse.define(ApiCodeEnum.API_PARAM_PAY_NOTIFY_STATUS_ERR);
		}

		//修改订单状态
		if (OrderEnum.PayStatus.DONE.getNameEng().equals(orderStatus)) {
			//支付价格判断
			if (order.getPaymentAmount() != fee) {
				return ResultResponse.define(ApiCodeEnum.API_PARAM_PAY_FEE_ERR);
			}

			//判定是否是首次购买商品
			GoodsDto goods = baseGoodsService.getGoodsByGoodsNo(order.getGoodsCode());
			if (GlobalEnum.GoodsClass.FIRST_GOODS.getCode() == goods.getGoodsClass()) {
				ResultResponse goodsResult = baseGoodsService.checkCanBuyGoods(order.getGoodsCode(), order.getAccountId());
				if(!goodsResult.isSuccess()){
					return goodsResult;
				}
			}

			order.setPayStatus(OrderEnum.PayStatus.DONE.getCode());
			order.setTradeStatus(OrderEnum.TradeStatus.WAITING_SEND.getCode());
			order.setUpdateTime(new Date());
			orderMapper.updateByPrimaryKeySelective(order);
		} else {
			order.setPayStatus(OrderEnum.PayStatus.TIMEOUT.getCode());
			order.setTradeStatus(OrderEnum.TradeStatus.TRADE_TIMEOUT.getCode());
			order.setUpdateTime(new Date());
			orderMapper.updateByPrimaryKeySelective(order);
			return ResultResponse.success();
		}
		publishMemberToAdmin.publishOrder(order);

		//锁定用户，保持一个用户只能同时订购一个会员
		ResultResponse memberResult = ResultResponse.failed();

		String key = String.format(CacheKeyConstant.REDIS_KEY_ORDER_MEMBER, order.getAccountId());
		RedisLock redisLock = new RedisLock(redisTemplate, key, 0, 5000);
		try {
			if (redisLock.lock()) {
				memberResult = memberOpsService.deliveryMemberByOrderId(order.getId());
			} else {
				return ResultResponse.define(ApiCodeEnum.API_DATA_MEMBER_IS_ORDERING);
			}
		} catch (Exception e) {
			logger.error("redisLock:error", e);
		} finally {
			redisLock.unlock();
		}

		return memberResult;
 	}
    
}
