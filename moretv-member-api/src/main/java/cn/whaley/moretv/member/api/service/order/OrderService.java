package cn.whaley.moretv.member.api.service.order;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.res.ResultResponse;
import cn.whaley.moretv.member.service.order.BaseOrderService;

public interface OrderService extends BaseOrderService {

    ResultResponse listByAccountId(BaseRequest baseRequest);

    ResultResponse getByOrderCode(String orderCode);

}