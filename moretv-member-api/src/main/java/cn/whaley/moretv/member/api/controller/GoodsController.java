package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.service.goods.GoodsService;
import cn.whaley.moretv.member.api.service.goods.GoodsSpuService;
import cn.whaley.moretv.member.base.annotation.ValidateIgnore;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 商品接口
 *
 * Created by Bob Jiang on 2017/3/21.
 */
@RestController
@RequestMapping("/goods_api")
public class GoodsController {

    @Autowired
    public GoodsService goodsService;

    @Autowired
    public GoodsSpuService goodsSpuService;

    /**
     * <p>会员商品介绍列表接口</p>
     * 商品模型列表
     *
     * @param baseRequest
     * @return
     */
    @RequestMapping(value = "/get_goods_spu", method = RequestMethod.POST)
    public ResultResponse getGoodsSpuList(@ValidateIgnore BaseRequest baseRequest) {
         return goodsSpuService.getGoodsSpuList();
    }

    /**
     * <p>会员商品价格明细列表接口</p>
     *
     * @param baseRequest
     * @param goodsTag 会员编码
     * @return
     */
    @RequestMapping(value = "/get_goods_by_tag", method = RequestMethod.POST)
    public ResultResponse getGoodsByTag(BaseRequest baseRequest, String goodsTag) {
        if (StringUtils.isEmpty(goodsTag)) {
            return ResultResponse.define(ApiCodeEnum.API_PARAM_GOODS_TAG_ID_NULL);
        }
        return goodsService.getGoodsByTag(baseRequest.getAccountId(), goodsTag);
    }

    /**
     * <p>查询会员对应的商品模型</p>
     *
     * @param baseRequest
     * @param memberCode 会员编码
     * @return
     */
    @RequestMapping(value = "/get_goods_spu_by_tag", method = RequestMethod.POST)
    public ResultResponse getGoodsSpuList(BaseRequest baseRequest, String memberCode) {
        if (StringUtils.isEmpty(memberCode)) {
            return ResultResponse.define(ApiCodeEnum.API_PARAM_MEMBER_CODE_ID_NULL);
        }
        return goodsSpuService.getGoodsSpuListByTag(memberCode);
    }
}
