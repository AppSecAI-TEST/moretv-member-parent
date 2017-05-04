package cn.whaley.moretv.member.order.service.order.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.whaley.moretv.member.base.util.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
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
import cn.whaley.moretv.member.base.util.paygateway.PayGatewayUtil;
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
			int payAutoRenew, String accountId) {
		Goods goods = null;
		GoodsSku goodsSku = null;
		Date now = new Date();
		//检查商品
	    ResultResponse<GoodsDto> goodCheck = baseGoodsService.checkCanBuyGoods(goodsCode,accountId);
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

	    String key = String.format(CacheKeyConstant.REDIS_KEY_ORDER_CREATE_LOCK, accountId);
        RedisLock redisLock = new RedisLock(redisTemplate, key, 0, 5000);

        try {
            if (redisLock.lock()) {
                //创建订单
                Order order = new Order();
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
            } else {
                return ResultResponse.failed("order create failed");
            }
        } catch (Exception e) {
            logger.error("create order lock error", e);
            return ResultResponse.failed();
        } finally {
            redisLock.unlock();
        }
	}
	
    @Override
    @Transactional
    public ResultResponse pay(PayGatewayRequest payGatewayRequest) {
        Date now = new Date();
        //1、验证MD5
        if (!checkSign(payGatewayRequest)) {
            logger.error("申请支付, md5验证失败, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_SIGN_ERR);
        }

        //2、验证订单超时时间
        long overtime = payGatewayRequest.getCreateTime().longValue() + (payGatewayRequest.getExpireTime().longValue() * 60 * 1000);
        if (overtime < now.getTime()) {
            logger.error("申请支付, 订单已超时, 请求参数->{}", payGatewayRequest.toString());
            return ResultResponse.define(ApiCodeEnum.API_DATA_ORDER_OVER_TIME_ERR);
        }

		String orderCode = payGatewayRequest.getOrderCode();
		String key = String.format(CacheKeyConstant.REDIS_KEY_ORDER_PAY_LOCK, orderCode);
		RedisLock redisLock = new RedisLock(redisTemplate, key, 0, 5000);

		try {
			if (redisLock.lock()) {
                Order order = getGenericMapper().getByOrderCode(orderCode);
                if (order == null) {
                    return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
                }

                if (OrderEnum.PayStatus.WAITING_PAY.getCode() != order.getPayStatus()
                        || OrderEnum.TradeStatus.TRADE_INIT.getCode() != order.getTradeStatus()) {
                    return ResultResponse.define(ApiCodeEnum.API_DATA_ORDER_STATUS_ERR);
                }

                //4、更新订单状态为【支付中】
                Map<String, Object> map = new HashMap<>();
                map.put("updateTime", now);
                map.put("newPayStatus", OrderEnum.PayStatus.PAYING.getCode());
                map.put("oldPayStatus", OrderEnum.PayStatus.WAITING_PAY.getCode());
                map.put("orderCode", orderCode);
                map.put("tradeStatus", OrderEnum.TradeStatus.TRADE_INIT.getCode());
                orderMapper.updateOrderPayStatus(map);

				//5、向支付网关支付
                PayGatewayResponse payGatewayResponse = PayGatewayUtil.pay(payGatewayRequest);
				if (payGatewayResponse.getStatus().intValue() == ApiCodeInfo.API_OK) {

                    return ResultResponse.success(new OrderPayResponse(payGatewayResponse.getData()));
				} else {
                    logger.error("申请支付, 支付网关错误, 支付网关返回->{}", payGatewayResponse.toString());
                    return ResultResponse.define(ApiCodeEnum.API_DATA_PAY_GATEWAY_ERR.getCode(), payGatewayResponse.getMsg());
                }
			} else {
                logger.error("订单正在支付中！");
				return ResultResponse.define(ApiCodeEnum.API_DATA_PAY_GATEWAY_ERR.getCode(), "订单支付中!");
			}
		} catch (Exception e) {
            logger.error("order lock error", e);
            return ResultResponse.define(ApiCodeEnum.API_DATA_PAY_GATEWAY_ERR);
		} finally {
			redisLock.unlock();
		}
    }

    private boolean checkSign(PayGatewayRequest payGatewayRequest) {
        //拼接MD5的参数
        String param = PayManage.getParams4Sign(payGatewayRequest.getCip(), payGatewayRequest.getTimestamp(), 
                payGatewayRequest.getGoodsCode(), payGatewayRequest.getSubject(), payGatewayRequest.getPayAutoRenew(), 
                payGatewayRequest.getPayType(), payGatewayRequest.getOrderCode(), payGatewayRequest.getFee(), payGatewayRequest.getAccountId(), payGatewayRequest.getCreateTime()).toString();
        
        if(PayManage.getPayUrlSign(param).equals(payGatewayRequest.getSign()))
            return true;
        
        return false;
    }
}
