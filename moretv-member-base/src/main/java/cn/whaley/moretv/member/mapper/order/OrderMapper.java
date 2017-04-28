package cn.whaley.moretv.member.mapper.order;

import java.util.List;
import java.util.Map;

import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.model.order.Order;
import org.apache.ibatis.annotations.Select;

/**
* Mapper: OrderMapper
* Model : Order
* Table : business_order
*
* This Mapper generated by MyBatis Generator Extend at 2017-03-31 15:00:02
*/
public interface OrderMapper extends GenericMapper<Order, Integer> {

    List<Order> listByAccountId(String accoundId);

    Order getByOrderCode(String orderCode);

    @Select("select count(1) from business_order where account_id = #{accountId} " +
            "and order_type = 1 and valid_status = 1 and trade_status = 3")
    Integer hasPurchaseOrder(Integer accountId);
    
    Order getByOrderIdForUpdate(int orderId);
    
    int updateOrderPayStatus(Map<String, Object> map);
}