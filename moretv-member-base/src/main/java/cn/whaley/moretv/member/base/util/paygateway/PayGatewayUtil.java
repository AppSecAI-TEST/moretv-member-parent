package cn.whaley.moretv.member.base.util.paygateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.whaley.moretv.member.base.config.CustomProperty;
import cn.whaley.moretv.member.base.dto.pay.PayRequest;

/**
 * 支付网关工具类
 */
public class PayGatewayUtil {
    private static Logger logger = LoggerFactory.getLogger(PayGatewayUtil.class);
    
    /**
     * 配置信息
     */
    private static CustomProperty customProperty;
    
    public static void setCustomProperty(CustomProperty customProperty) {
        PayGatewayUtil.customProperty = customProperty;
    }


    public static boolean pay(PayRequest payRequest){
        
        
        return false;
    }
}
