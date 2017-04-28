package cn.whaley.moretv.member.notify.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.util.MD5Util;
import cn.whaley.moretv.member.base.util.StringHelper;
import cn.whaley.moretv.member.notify.service.order.OrderService;

@RestController
@RequestMapping("/order_api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 通知回调
     * @param orderCode
     * @param payMethodCode
     * @param payMethodName
     * @param fee
     * @param orderStatus
     * @param sign
     * @return
     */
    @RequestMapping(value = "notification_private", method = RequestMethod.POST)
	public ResultResponse notification(@RequestParam(value = "orderCode") String orderCode,
			@RequestParam(value = "payMethodCode") String payMethodCode,
			@RequestParam(value = "payMethodName") String payMethodName,
			@RequestParam(value = "fee") Integer fee,
			@RequestParam(value = "orderStatus") String orderStatus,
			@RequestParam(value = "sign") String sign) {
    		//TODO 暂时注销check
    	
			/*//参数验证
			if (StringHelper.strsIsEmpty(orderCode,payMethodCode,payMethodName,orderStatus, sign) || fee == null) {
				return ResultResponse.define(ApiCodeEnum.API_PARAM_NULL);
			}
			
			if (!sign.equals(MD5Util.string2MD5(orderCode +payMethodCode+payMethodName+fee+orderStatus + ""))) {
				return ResultResponse.define(ApiCodeEnum.API_SIGN_ERR);
			}*/
        
		return orderService.delivery(orderCode, orderStatus, fee);
    }
}
