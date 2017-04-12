package cn.whaley.moretv.member.base.util.longconnect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cn.whaley.moretv.member.base.config.CustomProperty;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
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
import com.alibaba.fastjson.JSONObject;

public class LongConnectionUtil {
    private static Logger logger = LoggerFactory.getLogger(LongConnectionUtil.class);

    private static CustomProperty.LongConnection longConnection;

    public static void setLongConnection(CustomProperty.LongConnection longConnection) {
        LongConnectionUtil.longConnection = longConnection;
    }

    public static boolean pushForSpecificUsers(LongConnectionMsg msg) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
            HttpPost httpPost = new HttpPost(longConnection.getUrl());
            httpPost.setConfig(requestConfig);
            String params = setParam(msg);
            StringEntity stringEntity = new StringEntity(params, ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
            httpPost.setEntity(stringEntity);
            logger.info("pushForSpecificUsers post params {}", params);
            response = httpclient.execute(httpPost);
            
            if(response.getStatusLine().getStatusCode() != 200){ 
                logger.error("pushForSpecificUsers response error, http status->{}", response.getStatusLine().getStatusCode());
                return false;
            }
            
            HttpEntity entity = response.getEntity();
            String r = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            logger.info("pushForSpecificUsers post response {}", r);
            
            JSONObject j = JSON.parseObject(r);
            if(j.getInteger("status").intValue() != 500)
                return true;
            else
                return false;
        } catch (Exception e) {
            logger.error("pushForSpecificUsers post error", e);
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
        return false;
    }

    private static String setParam(LongConnectionMsg msg) throws UnsupportedEncodingException {
        StringBuffer sb = new StringBuffer();
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = DigestUtils.sha1Hex(longConnection.getAppSecret() + time + time);
        String jsonMsg = JSON.toJSONString(msg);
        logger.info("pushForSpecificUsers jsonMsg->{}", jsonMsg);
        String data = URLEncoder.encode(Base64.encodeBase64String(jsonMsg.getBytes()), "UTF-8");
        sb.append("app_key=").append(longConnection.getAppKey()).append("&round=").append(time)
                .append("&timestamp=").append(time).append("&sign=").append(sign).append("&business_type=")
                .append(longConnection.getBusinessType()).append("&data=").append(data).append("&targets=");
        
        for(Integer userId : msg.getAccoundId()){
            sb.append(userId.toString()).append(",");
        } 
        
        sb.delete(sb.length()-1, sb.length());
                
        return sb.toString();
    }

}
