package cn.whaley.moretv.member.service.order.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.order.OrderItemMapper;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.service.order.BaseOrderItemService;


@Service
public class BaseOrderItemServiceImpl extends GenericServiceImpl<OrderItem, Integer, OrderItemMapper> implements BaseOrderItemService {

	@Autowired
	protected OrderItemMapper OrderItemMapper;
	
    @Autowired
    protected RedisTemplate redisTemplate;

    @Override
    public  OrderItem createOrderItemByGoodsSku(GoodsSku goodsSku,OrderItem orderItem){

    	orderItem.setMemberCode(goodsSku.getMemberCode());
    	orderItem.setMemberName(goodsSku.getMemberName());
    	orderItem.setAmount(1);
    	orderItem.setUnitPrice(goodsSku.getOriginalPrice());
    	orderItem.setRealPrice(goodsSku.getSellingPrice());

    	OrderItemMapper.insert(orderItem);
		return orderItem;
    }
    
	@Override
	public OrderItemMapper getGenericMapper() {
		return OrderItemMapper;
	}
}
