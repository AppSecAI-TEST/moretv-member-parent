package cn.whaley.moretv.member.service.goods.impl;

import cn.whaley.moretv.member.service.order.BaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


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
	protected BaseOrderService baseOrderService;

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
		return null;
    }
}
