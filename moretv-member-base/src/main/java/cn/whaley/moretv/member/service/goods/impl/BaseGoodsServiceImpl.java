package cn.whaley.moretv.member.service.goods.impl;



import java.util.Date;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;
import cn.whaley.moretv.member.service.order.BaseOrderService;

import com.alibaba.fastjson.JSON;

	
/**
 * Goods Base ServiceImpl
 *
 * 在此定义多个系统都会用到的Service方法，mapper通过getGenericMapper调用
 *
 * Created by tangzc on 2017/3/16.
 */
@Service
public class BaseGoodsServiceImpl extends GenericServiceImpl<Goods, Integer, GoodsMapper> implements BaseGoodsService {

    @Autowired
    protected GoodsMapper goodsMapper;

	@Autowired
    @Qualifier("baseOrderServiceImpl")
    protected BaseOrderService baseOrderService;
	
	@Autowired
	protected RedisTemplate redisTemplate;
	
    @Override
	public GoodsDto getGoodsByGoodsNo(String goodsNo) {
        GoodsDto goods = null;
    	String goodsKey = CacheKeyConstant.REDIS_KEY_GOODS;
    	HashOperations<String,String,String> ops = redisTemplate.opsForHash();
    	String goodsStr = ops.get(goodsKey, goodsNo);
    	if(goodsStr != null){
    		goods = JSON.parseObject(goodsStr, GoodsDto.class);
    	}
		return goods;
    }
    
    @Override
	public ResultResponse<GoodsDto> checkCanBuyGoods(String goodsNo, String accountId){
    	ResultResponse<GoodsDto> response = null;
    	Date now = new Date();
    	//获取商品
        GoodsDto goods = getGoodsByGoodsNo(goodsNo);
    	if(goods != null && goods.getIsDisplayed()){
    		//上架时间
            if(goods.getStartTime()!=null && now.before(goods.getStartTime())){
            	return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
            }
            if(goods.getEndTime()!=null && now.after(goods.getEndTime())){
            	return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_NOT_ONLINE);
            }
            
//    		首次购买
    		if(GlobalEnum.GoodsClass.FIRST_GOODS.getCode() ==goods.getGoodsClass() && baseOrderService.hasPurchaseOrder(accountId)){
    			return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_CAN_NOT_BUY);
    		}
    		
    	}else{
    		return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_STATUS_ERR);
    	}
    	response = ResultResponse.success(goods);
		return response;
    }

    @Override
    public GoodsMapper getGenericMapper() {
        return goodsMapper;
    }
}
