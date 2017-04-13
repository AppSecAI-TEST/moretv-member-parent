package cn.whaley.moretv.member.service.goods.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsSkuMapper;
import cn.whaley.moretv.member.mapper.goods.GoodsSpuMapper;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.goods.GoodsSpu;
import cn.whaley.moretv.member.service.goods.BaseGoodsSkuService;
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
public class BaseGoodsSkuServiceImpl extends GenericServiceImpl<GoodsSku, Integer, GoodsSkuMapper> implements BaseGoodsSkuService {

	@Autowired
	protected GoodsSkuMapper goodsSkuMapper;

	@Autowired
	protected RedisTemplate redisTemplate;
	
    @Override
    public List<GoodsSku> getGoodsSkuByGoodsNo(String goodsNo){
    	List<GoodsSku> goodsSkulist = null;
    	String goodsSkuKey = "goodsKey";
    	HashOperations<String,String,String> ops = redisTemplate.opsForHash();
    	String goodsStr = ops.get(goodsSkuKey, goodsNo);
    	if(goodsStr != null){
    		GoodsSku goodsSku = JSON.parseObject(goodsStr, GoodsSku.class);
    		goodsSkulist.add(goodsSku);
    	}
		return goodsSkulist;
    }

	@Override
	public GoodsSkuMapper getGenericMapper() {
		return goodsSkuMapper;
	}
}
