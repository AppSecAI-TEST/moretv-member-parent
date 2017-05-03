package cn.whaley.moretv.member.service.order.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.whaley.moretv.member.mapper.order.OrderItemMapper;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.OrderItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.mapper.order.OrderMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.service.order.BaseOrderService;


@Service
public class BaseOrderServiceImpl extends GenericServiceImpl<Order, Integer, OrderMapper> implements BaseOrderService {

    protected static final Logger logger = LoggerFactory.getLogger(BaseOrderService.class);

	@Autowired
	protected OrderMapper orderMapper;

	@Autowired
	protected OrderItemMapper orderItemMapper;
	
    @Autowired
    protected RedisTemplate redisTemplate;
    
    @Override
    public Boolean hasPurchaseOrder(String accountId) {
        Integer count = getGenericMapper().hasPurchaseOrder(accountId);
        return count > 0 ? true : false;
    }

	@Override
    public  Order createOrderByGoods(Goods goods, Order order) {
		UUID uuid = UUID.randomUUID();
		String accountId = order.getAccountId();
		accountId = accountId.substring(accountId.length() - 4, accountId.length());
		order.setOrderCode("MT" + uuid.toString().replace("-", "") + accountId);
	    order.setOrderTitle(goods.getGoodsName());
	    order.setOrderDesc(goods.getGoodsDesc());
	    order.setTotalPrice(goods.getSellingPrice());
	    order.setRealPrice(goods.getOriginalPrice());
	    order.setPaymentAmount(goods.getSellingPrice());
	    order.setGoodsCode(goods.getGoodsCode());
	    order.setGoodsName(goods.getGoodsName());
	    order.setGoodsType(goods.getGoodsType());
    	orderMapper.insert(order);
    	
		return order;
    }

    @Override
	public OrderItem createOrderItemByGoodsSku(GoodsSku goodsSku, OrderItem orderItem) {
    	orderItem.setOrderItemCode("DT" + UUID.randomUUID().toString().replace("-", ""));
		orderItem.setMemberCode(goodsSku.getMemberCode());
		orderItem.setMemberName(goodsSku.getMemberName());
		orderItem.setAmount(1);
		orderItem.setValidStatus(GlobalEnum.Status.VALID.getCode());
		orderItem.setDurationDay(goodsSku.getDurationDay());
		orderItem.setDurationMonth(goodsSku.getDurationMonth());
		orderItem.setTotalPrice(goodsSku.getOriginalPrice());
		orderItem.setRealPrice(goodsSku.getSellingPrice());

		orderItemMapper.insert(orderItem);
		return orderItem;
	}
    
    /**
     * 检验下单次数
     * @param accountId
     * @return
     */
    @Override
    public ResultResponse checkCanOrderCount(String accountId){
    	BoundValueOperations<String,String> opsValue = redisTemplate.boundValueOps(CacheKeyConstant.REDIS_KEY_CREAT_ORDER + accountId);
    	String creteOrderCount = opsValue.get();
    	if (creteOrderCount == null) {
    		opsValue.expireAt(DateFormatUtil.addDay(1));
    	} else {
    		if (Long.parseLong(creteOrderCount) >= 50) {
    			return ResultResponse.define(ApiCodeEnum.API_DATA_ORDER_REQUEST_OVER_FIFTY);
    		}
    	}
    	opsValue.increment(1);
    	
    	return ResultResponse.success();
    }
    
	@Override
	public OrderMapper getGenericMapper() {
		return orderMapper;
	}
}
