package cn.whaley.moretv.member.service.order.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.mapper.order.OrderMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.service.order.BaseOrderService;


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
    public  Order createOrderByGoods(Goods goods,Order order){
		UUID uuid = UUID.randomUUID();
		order.setOrderCode("MT" + uuid.toString().replace("-", ""));
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
    
    public void checkCanOrderCount(Integer accountId){
	
    	
    	BoundValueOperations<String,Long> opsValue = redisTemplate.boundValueOps("creteOrder"+accountId);
    	opsValue.get();
    	opsValue.expireAt(DateFormatUtil.addDay(1));
    	opsValue.increment(1);

    	
    }
    
	@Override
	public OrderMapper getGenericMapper() {
		return orderMapper;
	}
}
