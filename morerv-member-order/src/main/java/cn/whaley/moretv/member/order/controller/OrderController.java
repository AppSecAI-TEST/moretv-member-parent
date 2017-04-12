package cn.whaley.moretv.member.order.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.order.service.order.impl.OrderServiceImpl;

@RestController
@RequestMapping("/order_api")
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * 创建订单
     * 
     */
    @RequestMapping(value = "/create_order", method = RequestMethod.POST)
    public ResultResponse create_order(BaseRequest baseRequest) {
        try {
            
            //return orderService.listByAccountId(baseRequest);
        } catch (Exception e) {	
            logger.error("获取订单列表异常", e);
            //return ResultResponse.failed();
        }
        
        return null;
        
    }
}
