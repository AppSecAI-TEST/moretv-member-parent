package cn.whaley.moretv.member.service.order.impl;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.order.OrderMapper;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.service.order.BaseOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
public abstract class BaseOrderServiceImpl extends GenericServiceImpl<Order, Integer, OrderMapper> implements BaseOrderService {

	@Autowired
	OrderMapper orderMapper;
	
    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public Boolean hasPurchaseOrder(Integer accountId) {
        Integer count = getGenericMapper().hasPurchaseOrder(accountId);
        return count > 0 ? true : false;
    }
    
	@Override
	public OrderMapper getGenericMapper() {
		return orderMapper;
	}
}
