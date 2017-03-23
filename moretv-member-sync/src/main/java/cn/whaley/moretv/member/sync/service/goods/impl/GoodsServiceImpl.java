package cn.whaley.moretv.member.sync.service.goods.impl;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.base.res.ResultResponse;
import cn.whaley.moretv.member.base.util.BeanHandler;
import cn.whaley.moretv.member.mapper.goods.GoodsMapper;
import cn.whaley.moretv.member.mapper.goods.GoodsSkuMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.service.goods.impl.BaseGoodsServiceImpl;
import cn.whaley.moretv.member.sync.dto.goods.GoodsDto;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsMapper goodsMapper;

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
            logger.error("syncGoods: error: {}", ApiCodeEnum.API_DATA_GOODS_STATUS_ERR.getMsg());
            return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_STATUS_ERR);
        }

        return ResultResponse.success();
    }

    private void offsheifGoods(GoodsDto goodsDto, HashOperations<String, String, String> opsHash) {

        String key = String.format(CacheKeyConstant.REDIS_KEY_GOODS, goodsDto.getGoodsType());
        opsHash.delete(key, goodsDto.getGoodsCode());
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
            String key = String.format(CacheKeyConstant.REDIS_KEY_GOODS, goodsDto.getGoodsType());
            String value = JSON.toJSONString(goodsDto);
            opsHash.put(key, goodsDto.getGoodsCode(), value);
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
    public GenericMapper<Goods, Integer> getGenericMapper() {
        return goodsMapper;
    }
}