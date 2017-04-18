package cn.whaley.moretv.member.order.service.order;

import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.service.order.BaseOrderService;

public interface OrderService extends BaseOrderService {

    /**
     * <p>创建订单接口</p>
     * 
     * @param goodsCode
     * @param payType
     * @param payAutoRenew
     * @param accountId
     * @return
     */
    
    ResultResponse createOrder(String goodsCode, String payType, int payAutoRenew, int accountId);

    ResultResponse pay(PayGatewayRequest payRequest);
    
}
