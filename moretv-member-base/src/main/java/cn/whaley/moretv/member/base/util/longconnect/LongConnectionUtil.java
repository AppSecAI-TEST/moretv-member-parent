package cn.whaley.moretv.member.base.util.longconnect;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.whaley.moretv.member.base.config.CustomProperty;

/**
 * 长连接工具类
 */
public class LongConnectionUtil {
    private static Logger logger = LoggerFactory.getLogger(LongConnectionUtil.class);

    private static CustomProperty.LongConnection longConnection;

    public static void setLongConnection(CustomProperty.LongConnection longConnection) {
        LongConnectionUtil.longConnection = longConnection;
    }

    /**
     * 给单个会员发送
     */
    public static boolean pushForSpecificUsers(LongConnectionMsg msg) {
        try {
            String result = HttpClientUtil.post(longConnection.getUrl(), setParam(msg));
            if(result == null)
                return false;
            
            JSONObject j = JSON.parseObject(result);
            if(j.getInteger("status").intValue() != 500)
                return true;
            else
                return false;
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException", e);
        }
        
        return false;
    }

    private static String setParam(LongConnectionMsg msg) throws UnsupportedEncodingException {
        String time = String.valueOf(System.currentTimeMillis() / 1000);
        String sign = DigestUtils.sha1Hex(longConnection.getAppSecret() + time + time);
        String jsonMsg = JSON.toJSONString(msg);
        logger.info("longConnect params->{}", jsonMsg);
        String data = URLEncoder.encode(Base64.encodeBase64String(jsonMsg.getBytes()), "UTF-8");
        
/*        StringBuffer sb = new StringBuffer();
        for(Integer userId : msg.getAccoundId()){
            sb.append(userId.toString()).append(",");
        } 
        sb.delete(sb.length()-1, sb.length());*/
        
        Map<String, Object> map = new HashMap<>();
        map.put("app_key", longConnection.getAppKey());
        map.put("round", time);
        map.put("timestamp", time);
        map.put("sign", sign);
        map.put("business_type", longConnection.getBusinessType());
        map.put("data", data);
        map.put("targets", msg.getData().getAccount());
        
        return HttpClientUtil.appendParams(map);
    }

}
