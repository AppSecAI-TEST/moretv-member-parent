package cn.whaley.moretv.member.base.util.paygateway;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.config.CustomProperty;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayResponse;
import cn.whaley.moretv.member.base.util.MD5Util;
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
        String tempUrl = "/aliwappay";//默认支付宝
        if(OrderEnum.PayChannel.WECHAT.getCode().equals(payGatewayRequest.getPayType()))
            tempUrl = "/weixinwappay";
        
        String result = HttpClientUtil.post(customProperty.getPayGatewayServer() + tempUrl, setParam(payGatewayRequest, order));
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
        map.put("totalAmount", getYuanByFen(payGatewayRequest.getFee()));
        map.put("subject", order.getGoodsName());
        map.put("goodsNo", payGatewayRequest.getGoodsCode());
        map.put("notifyUrl", customProperty.getNotifyUrl());
        map.put("overTime", payGatewayRequest.getExpireTime());
        map.put("payMethod", payGatewayRequest.getPayType());
        
        map.put("sign", getSign(map));
        
        return HttpClientUtil.appendParams(map);
    }
    
    /**
     * 1、根据参数的key进行按字母顺序排序
     * 2、根据排序好的顺序将非空的值拼接求MD5
     */
    private static String getSign(Map<String, Object> map){
        StringBuffer sb = new StringBuffer();
        //排序
        Set<String> keySet = new TreeSet<>(map.keySet());
        for(String key : keySet){
            Object o = map.get(key);
            
            if(!StringUtils.isEmpty(o))
                sb.append(o);
        }
        
        sb.append(customProperty.getPayGatewaySignKey());
        
        return MD5Util.string2MD5(sb.toString());
    }
    
    /**
     * 将单位「分」转成单位 「元」
     */
    private static String getYuanByFen(Integer fen){
        return new BigDecimal(fen).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP).toString();
    }
    
/*    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("businessType", "businessType");
        map.put("orderNo", "orderNo");
        map.put("accountId", "accountId");
        map.put("totalAmount", "totalAmount");
        map.put("subject", "subject");
        map.put("goodsNo", "goodsNo");
        map.put("notifyUrl", "notifyUrl");
        map.put("overTime", "overTime");
        
        PayGatewayUtil.getSign(map);
    }*/
}
