package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order_api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     * 
     */
    @RequestMapping(value = "/get_order", method = RequestMethod.POST)
    public ResultResponse getOrder(BaseRequest baseRequest) {
        return orderService.listByAccountId(baseRequest);
    }
    
    /**
     * 获取订单明细
     * 
     */
    @RequestMapping(value = "/get_order_detail", method = RequestMethod.POST)
    public ResultResponse getOrderDetail(BaseRequest baseRequest, String orderCode) {
        if(StringUtils.isEmpty(orderCode)){
            return ResultResponse.define(ApiCodeEnum.API_PARAM_ERR);
        }
        return orderService.getByOrderCode(baseRequest, orderCode);
    }
}
