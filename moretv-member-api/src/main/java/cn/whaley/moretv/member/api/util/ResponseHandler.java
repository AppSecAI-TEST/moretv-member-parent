package cn.whaley.moretv.member.api.util;

import cn.whaley.moretv.member.api.dto.response.GoodsDto;
import cn.whaley.moretv.member.api.dto.response.GoodsResponse;
import cn.whaley.moretv.member.api.dto.response.GoodsSpuResponse;
import cn.whaley.moretv.member.base.util.BeanHandler;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.goods.GoodsSpu;

/**
 * Created by Bob Jiang on 2017/3/21.
 */
public class ResponseHandler extends BeanHandler {

    public static GoodsSpuResponse converGoodsSpu(GoodsSpu goodsSpu) {
        GoodsSpuResponse response = copyProperties(goodsSpu, GoodsSpuResponse.class);
        return response;
    }

    public static GoodsResponse converGoods(GoodsDto goods) {
        GoodsResponse response = copyProperties(goods, GoodsResponse.class);

        GoodsSku sku = goods.getGoodsSkuList().get(0);
        response.setMemberCode(sku.getMemberCode());
        response.setDurationMonth(sku.getDurationMonth());
        response.setDurationDay(sku.getDurationDay());
        return response;
    }
}
