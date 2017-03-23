package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.service.goods.GoodsService;
import cn.whaley.moretv.member.api.service.goods.GoodsSpuService;
import cn.whaley.moretv.member.base.res.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
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
     * 会员商品介绍列表接口
     * 商品模型列表
     *
     * @param baseRequest
     * @return
     */
    @RequestMapping(value = "/get_goods_spu", method = RequestMethod.POST)
    public ResultResponse getGoodsSpuList(BaseRequest baseRequest) {
        try {
            return goodsSpuService.getGoodsSpuList();
        } catch (Exception e) {
            return ResultResponse.failed();
        }
    }

    /**
     * 会员商品价格明细列表接口
     *
     * @param baseRequest
     * @param goodsTag
     * @return
     */
    @RequestMapping(value = "/get_goods_by_tag", method = RequestMethod.POST)
    public ResultResponse getGoodsByTag(BaseRequest baseRequest, String goodsTag) {
        try {
            Integer accountId = Integer.valueOf(baseRequest.getAccountId());
            return goodsService.getGoodsByTag(accountId, goodsTag);
        } catch (Exception e) {
            return ResultResponse.failed();
        }
    }
}
