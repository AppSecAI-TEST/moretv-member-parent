package cn.whaley.moretv.member.base.util.paygateway;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.config.CustomProperty;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayRequest;
import cn.whaley.moretv.member.base.dto.pay.gateway.PayGatewayResponse;

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

    /**
     * 向支付网关申请支付
     */
    public static PayGatewayResponse pay(PayGatewayRequest payGatewayRequest) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
            HttpPost httpPost = new HttpPost(customProperty.getPayGatewayServer() + "/tvmore/createPayOrder");
            httpPost.setConfig(requestConfig);
            String params = setParam(payGatewayRequest);
            StringEntity stringEntity = new StringEntity(params,
                    ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
            httpPost.setEntity(stringEntity);
            logger.info("payGateway post params {}", params);
            response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("payGateway response error, http status->{}",
                        response.getStatusLine().getStatusCode());
                return null;
            }

            HttpEntity entity = response.getEntity();
            String r = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            logger.info("payGateway post response {}", r);

            PayGatewayResponse j = JSON.parseObject(r, PayGatewayResponse.class);
            return j;
        } catch (Exception e) {
            logger.error("payGateway post error", e);
        } finally {
            try {
                if (response != null)
                    response.close();
                if (httpclient != null)
                    httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String setParam(PayGatewayRequest payGatewayRequest) {
        StringBuffer sb = new StringBuffer();
        sb.append("businessType").append(payGatewayRequest.getPayType()).append("&orderNo=").append(payGatewayRequest.getOrderCode())
                .append("&sign=").append(payGatewayRequest.getSign()).append("&accountId=").append(payGatewayRequest.getAccountId());

        return sb.toString();
    }
}
