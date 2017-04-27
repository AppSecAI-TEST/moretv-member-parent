package cn.whaley.moretv.member.base.util.longconnect;

import java.util.Map;

import javax.management.RuntimeErrorException;

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

public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String post(String url, String params) {
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse response = null;
        try {
            httpclient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(params,
                    ContentType.create("application/x-www-form-urlencoded", "UTF-8"));
            httpPost.setEntity(stringEntity);
            logger.info("http post params {}", params);
            response = httpclient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error("http response error, http status->{}", response.getStatusLine().getStatusCode());
                return null;
            }

            HttpEntity entity = response.getEntity();
            String r = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            logger.info("http post response {}", r);

            return r;
        } catch (Exception e) {
            logger.error("http post error", e);
            throw new RuntimeException("http post 异常，事务回滚");
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
    }

    public static String appendParams(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        
        return sb.delete(0, 1).toString();
    }
    
}
