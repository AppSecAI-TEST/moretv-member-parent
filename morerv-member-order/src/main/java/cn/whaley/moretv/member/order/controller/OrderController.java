package cn.whaley.moretv.member.order.controller;


import cn.whaley.moretv.member.order.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.dto.pay.PayRequest;
import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;

@RestController
@RequestMapping("/order_api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * 
     */
    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    public ResultResponse create_order(BaseRequest baseRequest) {
        return orderService.createOrder(null, null, 1, 1);
    }
    
    /**
     * 请求支付 
     * @throws Exception 
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResultResponse pay(PayRequest payRequest) {
        return orderService.pay(payRequest);
    }
}
