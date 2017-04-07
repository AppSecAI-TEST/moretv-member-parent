package cn.whaley.moretv.member.service.goods.impl;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.order.BaseOrderService;


/**
 * Goods Base ServiceImpl
 *
 * 在此定义多个系统都会用到的Service方法，mapper通过getGenericMapper调用
 *
 * Created by tangzc on 2017/3/16.
 */
@Service
public abstract class BaseGoodsServiceImpl extends GenericServiceImpl<Goods, Integer, GoodsMapper> implements BaseGoodsService {
	
	@Autowired
	BaseOrderService baseOrderService;
	
	@Autowired
	protected RedisTemplate redisTemplate;
	
    @Override
	public Goods getGoodsByGoodsNo(String goodsNo){
    	Goods goods = null;
    	String goodsKey = "goodsKey";
    	HashOperations<String,String,String> ops = redisTemplate.opsForHash();
    	String goodsStr = ops.get(goodsKey, goodsNo);
    	if(goodsStr != null){
    		goods = JSON.parseObject(goodsStr, Goods.class);
    	}
		return goods;
    }
    
    @Override
	public Goods checkCanBuyGoods(String goodsNo,int accountId){
    	//获取商品
    	Goods goods = getGoodsByGoodsNo(goodsNo);
    	if(goods != null){
    		//是否首次购买
    		if(baseOrderService.hasPurchaseOrder(accountId)){
    			
    		}
    	}
    	
		return goods;
    }
}
