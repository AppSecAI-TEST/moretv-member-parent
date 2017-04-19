package cn.whaley.moretv.member.api.service.goods;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.service.goods.BaseGoodsSpuService;

/**
* Service: GoodsSpuService
* Mapper : GoodsSpuMapper
* Model  : GoodsSpu
*
* This Service generated by MyBatis Generator Extend at 2017-03-21 14:37:17
*/
public interface GoodsSpuService extends BaseGoodsSpuService {

    /**
     * <p>会员商品介绍列表</p>
     *
     * 获取所有独立商品模型
     * @return
     */
    ResultResponse getGoodsSpuList();

    /**
     * <p>查询会员对应的商品模型</p>
     *
     * @param memberCode 会员编码
     * @return
     */
    ResultResponse getGoodsSpuListByTag(String memberCode);

}