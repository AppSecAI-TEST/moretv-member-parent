package cn.whaley.moretv.member.service.goods.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsSpuMapper;
import cn.whaley.moretv.member.model.goods.GoodsSpu;
import cn.whaley.moretv.member.service.goods.BaseGoodsSpuService;


import com.alibaba.fastjson.JSON;

	
/**
 * GoodsSpu Base ServiceImpl
 *
 * 在此定义多个系统都会用到的Service方法，mapper通过getGenericMapper调用
 *
 * Created by tangzc on 2017/3/16.
 */
@Service
public class BaseGoodsSpuServiceImpl extends GenericServiceImpl<GoodsSpu, Integer, GoodsSpuMapper> implements BaseGoodsSpuService {

	@Autowired
	protected GoodsSpuMapper goodsSpuMapper;

	@Autowired
	protected RedisTemplate redisTemplate;
	
    @Override
    public List<GoodsSpu> getGoodsSpuByGoodsNo(String goodsNo){
    	List<GoodsSpu> goodsSpulist = null;
    	String goodsSpuKey = "goodsKey";
    	HashOperations<String,String,String> ops = redisTemplate.opsForHash();
    	String goodsStr = ops.get(goodsSpuKey, goodsNo);
    	if(goodsStr != null){
    		GoodsSpu goodsSpu = JSON.parseObject(goodsStr, GoodsSpu.class);
    		goodsSpulist.add(goodsSpu);
    	}
		return goodsSpulist;
    }

	@Override
	public GoodsSpuMapper getGenericMapper() {
		return goodsSpuMapper;
	}
}
