package cn.whaley.moretv.member.service.order;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;


public interface BaseOrderService extends GenericService<Order, Integer> {

    /**
     * 用户是否有购买的订单
     *
     * @param accountId
     * @return
     */
    Boolean hasPurchaseOrder(Integer accountId);

    /**
     * 创建订单
     *
     * @param goods
     * @return
     */
    Order createOrderByGoods(Goods goods,Order order);

    /**
     * 创建订单明细
     *
     * @param goodsSku
     * @param orderItem
     * @return
     */
    OrderItem createOrderItemByGoodsSku(GoodsSku goodsSku, OrderItem orderItem);
    /**
     * 检查用户的下单次数
     * @param accountId
     * @return
     */
    ResultResponse checkCanOrderCount(String scene,Integer accountId);
    
}
