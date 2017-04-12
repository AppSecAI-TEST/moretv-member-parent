package cn.whaley.moretv.member.service.order;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.OrderItem;



public interface BaseOrderItemService extends GenericService<OrderItem, Integer> {


    /**
     * 创建订单明细
     * @param goods
     * @return
     */
    OrderItem createOrderItemByGoodsSku(GoodsSku goodsSku,OrderItem orderItem);
}
