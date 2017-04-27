package cn.whaley.moretv.member.notify.service.order;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.service.order.BaseOrderService;

public interface OrderService extends BaseOrderService {

 	ResultResponse delivery(String orderNo, String orderStatus,int fee);
}
