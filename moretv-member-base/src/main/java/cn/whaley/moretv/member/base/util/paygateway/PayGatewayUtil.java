package cn.whaley.moretv.member.base.util.paygateway;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.config.CustomProperty;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayResponse;
import cn.whaley.moretv.member.base.util.longconnect.HttpClientUtil;
import cn.whaley.moretv.member.model.order.Order;

/**
 * 支付网关工具类
 */
public class PayGatewayUtil {

    /**
     * 配置信息
     */
    private static CustomProperty customProperty;

    public static void setCustomProperty(CustomProperty customProperty) {
        PayGatewayUtil.customProperty = customProperty;
    }

    /**
     * 向支付网关申请支付
     */
    public static PayGatewayResponse pay(PayGatewayRequest payGatewayRequest, Order order) {
        String result = HttpClientUtil.post(customProperty.getPayGatewayServer() + "/tvmore/createPayOrder", setParam(payGatewayRequest, order));
        if(result == null)
            return null;
        else
            return JSON.parseObject(result, PayGatewayResponse.class);
    }
    
    private static String setParam(PayGatewayRequest payGatewayRequest, Order order) {
        Map<String, Object> map = new HashMap<>();
        map.put("businessType", GlobalConstant.PAY_GATEWAY_PRODUCT_CODE);
        map.put("orderNo", payGatewayRequest.getOrderCode());
        map.put("accountId", payGatewayRequest.getAccountId());
        map.put("totalAmount", getYuanByFen(payGatewayRequest.getFree()));
        map.put("subject", order.getGoodsName());
        map.put("goodsNo", payGatewayRequest.getGoodsCode());
        map.put("notifyUrl", customProperty.getNotifyUrl());
        map.put("payMethod", payGatewayRequest.getPayType());
        map.put("overTime", payGatewayRequest.getExpireTime());
        
        return HttpClientUtil.appendParams(map);
    }
    
    private static String getYuanByFen(Long fen){
        return new BigDecimal(fen).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }
    
}
