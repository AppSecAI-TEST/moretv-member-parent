package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.api.util.RequestHandler;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.res.ResultResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order_api")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 获取订单列表
     * 
     */
    @RequestMapping(value = "/get_order", method = RequestMethod.POST)
    public ResultResponse getOrder(BaseRequest baseRequest) {
        try {
            logger.info("获取订单列表参数打印：" + baseRequest.toString());
            
            if(!RequestHandler.checkBaseRequest(baseRequest)){
                return ResultResponse.define(ApiCodeEnum.API_PARAM_ERR);
            }
            
            return orderService.listByAccountId(baseRequest);
        } catch (Exception e) {
            logger.error("获取订单列表异常", e);
            return ResultResponse.failed();
        }
        
    }
    
    /**
     * 获取订单明细
     * 
     */
    @RequestMapping(value = "/get_order_detail", method = RequestMethod.POST)
    public ResultResponse getOrderDetail(BaseRequest baseRequest, String orderCode) {
        try {
            logger.info("获取订单明细参数打印：" + baseRequest.toString());
            
            if(!RequestHandler.checkBaseRequest(baseRequest) || StringUtils.isEmpty(orderCode)){
                return ResultResponse.define(ApiCodeEnum.API_PARAM_ERR);
            }
            
            return orderService.getByOrderCode(orderCode);
        } catch (Exception e) {
            logger.error("获取订单明细异常", e);
            return ResultResponse.failed();
        }
        
    }
}
