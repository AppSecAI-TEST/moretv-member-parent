package cn.whaley.moretv.member.service.order;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.order.Order;


public interface BaseOrderService extends GenericService<Order, Integer> {

    /**
     * 用户是否有购买的订单
     *
     * @param accountId
     * @return
     */
    Boolean hasPurchaseOrder(Integer accountId);
}
