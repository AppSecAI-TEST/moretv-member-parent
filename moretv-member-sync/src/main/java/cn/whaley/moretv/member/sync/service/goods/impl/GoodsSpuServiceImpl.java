package cn.whaley.moretv.member.sync.service.goods.impl;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsSpuMapper;
import cn.whaley.moretv.member.model.goods.GoodsSpu;
import cn.whaley.moretv.member.sync.service.goods.GoodsSpuService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* ServiceImpl: GoodsSpuServiceImpl
* Mapper : GoodsSpuMapper
* Model  : GoodsSpu
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-02-20 20:07:22
*/
@Service
@Transactional
public class GoodsSpuServiceImpl extends GenericServiceImpl<GoodsSpu, Integer, GoodsSpuMapper> implements GoodsSpuService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsSpuServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsSpuMapper goodsSpuMapper;

    @Override
    public ResultResponse syncGoodsSpu(GoodsSpu goodsSpu) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();

        String status = goodsSpu.getGoodsStatus();

        if (GlobalEnum.StatusText.OFFSHEIF.getCode().equals(status)) {
            offsheifGoodsSpu(goodsSpu, opsHash);
        } else if (GlobalEnum.StatusText.PUBLISHED.getCode().equals(status)){
            publishGoodsSpu(goodsSpu, opsHash);
        } else {
            logger.error("syncGoodsSpu: error: {}", ApiCodeEnum.API_DATA_GOODS_STATUS_ERR.getMsg());
            return ResultResponse.define(ApiCodeEnum.API_DATA_GOODS_STATUS_ERR);
        }

        return ResultResponse.success();
    }

    private void offsheifGoodsSpu(GoodsSpu goodsSpu, HashOperations<String, String, String> opsHash) {
        opsHash.delete(CacheKeyConstant.REDIS_KEY_GOODS_SPU, goodsSpu.getGoodsBaseCode());
        logger.info("syncGoodsSpu: 删除商品模型缓存, goodsBaseCode:{}", goodsSpu.getGoodsBaseCode());

        GoodsSpu spu = new GoodsSpu();
        spu.setId(goodsSpu.getId());
        spu.setGoodsStatus(GlobalEnum.StatusText.OFFSHEIF.getCode());
        spu.setUpdateTime(goodsSpu.getUpdateTime());
        int line = goodsSpuMapper.updateByPrimaryKeySelective(spu);
        if (line > 0) {
            logger.info("syncGoodsSpu: 修改商品模型状态为未发布, goodsBaseCode:{}", goodsSpu.getGoodsBaseCode());
        }
    }

    private void publishGoodsSpu(GoodsSpu goodsSpu, HashOperations<String, String, String> opsHash) {
        //独立商品模型才存入redis
        if (GlobalEnum.CompositedType.SINGLE.getCode() == goodsSpu.getCompositedType().intValue()) {
            String value = JSON.toJSONString(goodsSpu);
            opsHash.put(CacheKeyConstant.REDIS_KEY_GOODS_SPU, goodsSpu.getGoodsBaseCode(), value);
            logger.info("syncGoodsSpu: 更新商品模型缓存, goodsBaseCode:{}", goodsSpu.getGoodsBaseCode());
        }

        int count = goodsSpuMapper.existGoodsSpuById(goodsSpu.getId());
        if (count > 0) {
            goodsSpuMapper.updateByPrimaryKeySelective(goodsSpu);
            logger.info("syncGoodsSpu: 更新商品模型数据, goodsBaseCode:{}", goodsSpu.getGoodsBaseCode());
        } else {
            goodsSpuMapper.insertWithId(goodsSpu);
            logger.info("syncGoodsSpu: 同步商品模型数据, goodsBaseCode:{}", goodsSpu.getGoodsBaseCode());
        }
    }

    @Override
    public GoodsSpuMapper getGenericMapper() {
        return goodsSpuMapper;
    }
}