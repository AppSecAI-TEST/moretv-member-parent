package cn.whaley.moretv.member.service.goods.impl;

import org.springframework.data.redis.core.HashOperations;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;


/**
 * Goods Base ServiceImpl
 *
 * 在此定义多个系统都会用到的Service方法，mapper通过getGenericMapper调用
 *
 * Created by tangzc on 2017/3/16.
 */
public abstract class BaseGoodsServiceImpl extends GenericServiceImpl<Goods, Integer> implements BaseGoodsService {

    @Override
	public Goods getGoodsByGoodsNo(String goodsNo){
    	Goods goods = null;
    	String goodsKey = "goodsKey";
    	HashOperations<String,String,String> ops = getRedisTemplate().opsForHash();
    	String goodsStr = ops.get(goodsKey, goodsNo);
    	if(goodsStr != null){
    		goods = JSON.parseObject(goodsStr, Goods.class);
    	}
		return null;
    }
}
