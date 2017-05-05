package cn.whaley.moretv.member.order.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.whaley.moretv.member.base.util.RedisLock;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.order.service.order.OrderService;
import cn.whaley.moretv.member.order.service.order.impl.OrderServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.manager.PayManage;
import cn.whaley.moretv.member.base.util.IpUtil;
import cn.whaley.moretv.member.base.util.MD5Util;
import cn.whaley.moretv.member.base.util.StringHelper;

@RestController
@RequestMapping("/order_api")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 创建订单
     * 
     */
    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    public ResultResponse create_order(BaseRequest baseRequest, HttpServletRequest request,
    		@RequestParam(value = "sessionToken") String sessionToken,
    		@RequestParam(value = "goodsCode") String goodsCode,
    		@RequestParam(value = "payAutoRenew") Integer payAutoRenew, 
    		@RequestParam(value = "payType")String payType,
    		@RequestParam(value = "openId", required = false)String openId) {
    	
    	if (StringUtils.isEmpty(payType)||StringUtils.isEmpty(goodsCode) || payAutoRenew ==null) {
			return ResultResponse.define(ApiCodeEnum.API_PARAM_NULL);
    	}
    	
    	if(OrderEnum.PayChannel.WECHAT.getCode().equals(payType) && StringUtils.isEmpty(openId)){
    	    logger.error("微信支付缺少openId->{}", baseRequest.toString());
    	    return ResultResponse.define(ApiCodeEnum.API_PARAM_NULL);
    	}
    	
    	ResultResponse checkOrderCount = orderService.checkCanOrderCount(baseRequest.getAccountId());
    	if (!checkOrderCount.isSuccess()) {
    		return checkOrderCount.define();
    	}

    	ResultResponse<Order> result = orderService.createOrder(goodsCode, payType, payAutoRenew, baseRequest.getAccountId());

    	//设定返回值
    	if (result.isSuccess()) {
    	    Map<String,String> data = new HashMap<String,String>();
            Order order = result.getData();

    		String payUrl = PayManage.getPayUrl(sessionToken, IpUtil.getIp2(request), System.currentTimeMillis(),
                    goodsCode, order.getOrderTitle(), payAutoRenew, payType, order.getOrderCode(),
                    order.getPaymentAmount(), order.getAccountId(), openId);

    		data.put("orderCode",order.getOrderCode());
    		data.put("redirectUrl", payUrl);
    		return ResultResponse.success(data);
    	}
        return result;
    }
    
    /**
     * 请求支付 
     * @throws Exception 
     */
    @RequestMapping(value = "/pay")
    public ResultResponse pay(PayGatewayRequest payRequest) {
        return orderService.pay(payRequest);
    }
}
