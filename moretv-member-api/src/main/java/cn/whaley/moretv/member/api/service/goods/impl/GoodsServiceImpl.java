package cn.whaley.moretv.member.api.service.goods.impl;

import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.api.dto.goods.GoodsResponse;
import cn.whaley.moretv.member.api.service.goods.GoodsService;
import cn.whaley.moretv.member.api.service.member.MemberService;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.api.util.ResponseHandler;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.service.goods.impl.BaseGoodsServiceImpl;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
* ServiceImpl: GoodsServiceImpl
* Mapper : GoodsMapper
* Model  : Goods
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-03-21 14:37:17
*/
@Service
@Transactional
public class GoodsServiceImpl extends BaseGoodsServiceImpl implements GoodsService {

    private static final Logger logger = LoggerFactory.getLogger(GoodsServiceImpl.class);

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderService orderService;

    @Override
    public ResultResponse getGoodsByTag(Integer accountId, String goodsTag) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        List<GoodsResponse> goodsList = Lists.newArrayList();
        Integer normalGoods = GlobalEnum.GoodsType.NORMAL_GOODS.getValue();
        long now = new Date().getTime();

        boolean isMember = memberService.accountIsMember(accountId);

        Integer goodsType = isMember ? GlobalEnum.GoodsType.RENEWAL_GOODS.getValue() : normalGoods;

        logger.info("get_goods_by_tag : accountId:{}, goodsTag:{}, isMember:{}, goodsType:{}",
                accountId, goodsTag, isMember, goodsType);

        List<String> list = opsHash.values(CacheKeyConstant.REDIS_KEY_GOODS);
        if (CollectionUtils.isEmpty(list)) {
            return ResultResponse.success(goodsList);
        }

        boolean hasPurchaseOrder = false;
        if (normalGoods.equals(goodsType)) {
            hasPurchaseOrder = orderService.hasPurchaseOrder(accountId);
        }

        for (String value : list) {
            GoodsDto goodsDto = JSON.parseObject(value, GoodsDto.class);
            Integer goodsClass = goodsDto.getGoodsClass();

            if (!goodsTag.equals(goodsDto.getGoodsBaseCode())) {
                continue;
            }

            if (!goodsDto.getIsDisplayed() || !goodsType.equals(goodsDto.getGoodsType())) {
                continue;
            }

            if (goodsDto.getStartTime() != null && goodsDto.getStartTime().getTime() > now) {
                continue;
            }

            if (goodsDto.getEndTime() != null && goodsDto.getEndTime().getTime() < now) {
                continue;
            }

            //有购买过商品，过滤掉首次商品
            if (hasPurchaseOrder && GlobalEnum.GoodsClass.FIRST_GOODS.getCode() == goodsClass.intValue()) {
                continue;
            //没有购买过商品，过滤掉非首次商品
            } else if (!hasPurchaseOrder && GlobalEnum.GoodsClass.NOT_FIRST_GOODS.getCode() == goodsClass.intValue()){
                continue;
            }
            GoodsResponse response = ResponseHandler.converGoods(goodsDto);
            goodsList.add(response);
        }

        logger.info("get_goods_by_tag :  accountId:{}, goodsSize:{}, hasPurchaseOrder:{}",
                accountId, goodsList.size(), hasPurchaseOrder);

        return ResultResponse.success(goodsList);
    }

}