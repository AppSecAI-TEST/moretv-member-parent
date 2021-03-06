package cn.whaley.moretv.member.sync.service.goods.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.util.BeanHandler;
import cn.whaley.moretv.member.mapper.goods.GoodsSkuMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.service.goods.impl.BaseGoodsServiceImpl;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;
import cn.whaley.moretv.member.sync.util.RedisResetResponseUtil;

/**
* ServiceImpl: GoodsServiceImpl
* Mapper : GoodsMapper
* Model  : Goods
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-02-20 20:07:22
*/
@Service
@Transactional
public class GoodsServiceImpl extends BaseGoodsServiceImpl implements GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private GoodsSkuMapper goodsSkuMapper;

    @Override
    public ResultResponse syncGoods(GoodsDto goodsDto) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();

        String status = goodsDto.getGoodsStatus();

        if (GlobalEnum.StatusText.OFFSHEIF.getCode().equals(status)) {
            offsheifGoods(goodsDto, opsHash);
        } else if (GlobalEnum.StatusText.PUBLISHED.getCode().equals(status)){
            publishGoods(goodsDto, opsHash);
        } else {
            throw new SystemException(ApiCodeEnum.API_DATA_GOODS_STATUS_ERR);
        }

        return ResultResponse.success();
    }

    private void offsheifGoods(GoodsDto goodsDto, HashOperations<String, String, String> opsHash) {

        opsHash.delete(CacheKeyConstant.REDIS_KEY_GOODS, goodsDto.getGoodsCode());
        logger.info("syncGoods: 删除商品缓存, goodsCode:{}", goodsDto.getGoodsCode());

        Goods goods = new Goods();
        goods.setId(goodsDto.getId());
        goods.setGoodsStatus(GlobalEnum.StatusText.OFFSHEIF.getCode());
        goods.setUpdateTime(goodsDto.getUpdateTime());
        int line = goodsMapper.updateByPrimaryKeySelective(goods);
        if (line > 0) {
            logger.info("syncGoods: 修改商品状态为下架, goodsCode:{}", goodsDto.getGoodsCode());
        }
    }

    private void publishGoods(GoodsDto goodsDto, HashOperations<String, String, String> opsHash) {

        List<GoodsSku> skuList = goodsDto.getGoodsSkuList();

        if (skuList != null && skuList.size() == 1) {
            String value = JSON.toJSONString(goodsDto);
            opsHash.put(CacheKeyConstant.REDIS_KEY_GOODS, goodsDto.getGoodsCode(), value);
            logger.info("syncGoods: 更新商品缓存, goodsCode:{}", goodsDto.getGoodsCode());
        } else {
            logger.info("syncGoods: 不缓存组合商品, goodsCode:{}", goodsDto.getGoodsCode());
        }

        Goods goods = BeanHandler.copyProperties(goodsDto, Goods.class);

        int count = goodsMapper.existGoodsById(goodsDto.getId());
        if (count > 0) {
            goodsMapper.updateByPrimaryKeySelective(goods);
            logger.info("syncGoods: 更新商品数据, goodsCode:{}", goodsDto.getGoodsCode());
        } else {
            goodsMapper.insertWithId(goods);
            logger.info("syncGoods: 同步商品数据, goodsCode:{}", goodsDto.getGoodsCode());
        }

        for (GoodsSku sku : skuList) {
            count = goodsSkuMapper.existGoodsSkuById(sku.getId());
            if (count == 0) {
                goodsSkuMapper.insertWithId(sku);
                logger.info("syncGoods: 同步商品SKU数据, goodsCode:{}, skuId:{}", goodsDto.getGoodsCode(), sku.getId());
            }
        }
    }

    @Override
    public Map<String, Object> resetRedis() {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        List<String> addList = new ArrayList<>();
        List<String> deleteList = new ArrayList<>();
        
        //1、获取数据库全部商品
        List<Goods> goodsList = goodsMapper.listByStatus(GlobalEnum.StatusText.PUBLISHED.getCode());
        
        //2、获取商品hash的所有key
        Set<String> goodsKeys = opsHash.keys(CacheKeyConstant.REDIS_KEY_GOODS);
        
        //3、全部删除并且记录用于返回
        for(String key : goodsKeys){
            opsHash.delete(CacheKeyConstant.REDIS_KEY_GOODS, key);
            deleteList.add(key);
            logger.info("resetGoods: 遍历清除商品, goodsCode:{}", key);
        }
        
        //2、遍历商品，查询商品的sku，如果商品状态为[发布]&&sku数量为1，那么存入redis
        for(Goods goods : goodsList){
            List<GoodsSku> goodsSkuList = goodsSkuMapper.selectByGoodsCode(goods.getGoodsCode());
            
            GoodsDto goodsDto = new GoodsDto();
            BeanUtils.copyProperties(goods, goodsDto);
            goodsDto.setGoodsSkuList(goodsSkuList);
            
            if(goodsSkuList.size() == 1){
                opsHash.put(CacheKeyConstant.REDIS_KEY_GOODS, goods.getGoodsCode(), JSON.toJSONString(goodsDto));
                addList.add(goods.getGoodsCode());
                logger.info("resetGoods: 重新插入商品缓存, goodsCode:{}", goodsDto.getGoodsCode());
            }
        }
        
        return RedisResetResponseUtil.getResetRedisMap(addList, deleteList);
    }

}