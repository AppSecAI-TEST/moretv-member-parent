package cn.whaley.moretv.member.api.util;

import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.api.dto.goods.GoodsResponse;
import cn.whaley.moretv.member.api.dto.goods.GoodsSpuResponse;
import cn.whaley.moretv.member.api.dto.order.OrderDetailDto;
import cn.whaley.moretv.member.api.dto.order.OrderListDto;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.util.BeanHandler;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.goods.GoodsSpu;
import cn.whaley.moretv.member.model.order.Order;

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

    public static OrderListDto convert2OrderListDto(Order order, OrderListDto orderDto) {
        orderDto.setPurchaseTime(order.getCreateTime().getTime());
        orderDto.setOrderCode(order.getOrderCode());
        orderDto.setGoodsName(order.getGoodsCode());
        orderDto.setGoodsCode(order.getGoodsCode());
        orderDto.setPayMethodCode(order.getPayChannel());
        orderDto.setPayMethodName(OrderEnum.PayChannel.getNameByCode(order.getPayChannel()));
        orderDto.setPrice(order.getRealPrice());
        orderDto.setPayStatus(OrderEnum.PayStatus.getNameByCode(order.getPayStatus()));
        return orderDto;
    }

    public static OrderDetailDto convert2OrderDetailDto(Order order, OrderDetailDto orderDto) {
        orderDto.setPurchaseTime(order.getCreateTime().getTime());
        orderDto.setOrderCode(order.getOrderCode());
        orderDto.setGoodsName(order.getGoodsCode());
        orderDto.setGoodsCode(order.getGoodsCode());
        orderDto.setPayMethodCode(order.getPayChannel());
        orderDto.setPayMethodName(OrderEnum.PayChannel.getNameByCode(order.getPayChannel()));
        orderDto.setPrice(order.getRealPrice());
        orderDto.setPayStatus(OrderEnum.PayStatus.getNameByCode(order.getPayStatus()));
        return orderDto;
    }
}
