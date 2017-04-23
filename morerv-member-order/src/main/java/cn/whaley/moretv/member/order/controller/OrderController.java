package cn.whaley.moretv.member.order.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.order.service.order.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.manager.PayManage;
import cn.whaley.moretv.member.base.util.IpUtil;

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
    public ResultResponse create_order(HttpServletRequest request,BaseRequest baseRequest,String sessionToken,String goodsCode,Integer payAutoRenew,String payType) {
    	ResultResponse<Order> result = orderService.createOrder(goodsCode, payType, payAutoRenew, baseRequest.getAccountId());
    	Order order =result.getData();
    	
    	//设定返回值
    	Map<String,String> data = new HashMap<String,String>();
    	if(ApiCodeEnum.API_OK.getCode() == result.getCode()){
    		String payUrl = PayManage.getPayUrl(sessionToken, IpUtil.getIp2(request), System.currentTimeMillis(), goodsCode, order.getOrderTitle(), payAutoRenew, payType, order.getOrderCode(), order.getPaymentAmount(), order.getAccountId());
    		data.put("orderCode",order.getOrderCode());
    		data.put("redirectUrl", payUrl);
    		return ResultResponse.success(data);
    	}else{
    		return result;
    	}
    }
    
    /**
     * 请求支付 
     * @throws Exception 
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResultResponse pay(PayGatewayRequest payRequest) {
        return orderService.pay(payRequest);
    }
}
