package cn.whaley.member.base.manager;

import cn.whaley.member.base.util.HttpHelper;
import cn.whaley.member.base.util.LogHelper;
import cn.whaley.member.base.util.PropertiyHelp;

import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * Created by admin on 15/9/28.
 * manager external token
 */

@SuppressWarnings("ALL")
public class ExternalManage {

    protected static String accessToken = null;
    protected static long timestamp = 0;

    public static String getAccessToken() throws Exception {
        if ((System.currentTimeMillis() - timestamp) > (90 * 60 * 1000) || accessToken == null) {
            if (!ExternalManage.generateAccessToken())
                return "";
        }
        return ExternalManage.accessToken;
    }

    protected static void setAccessToken(String accessToken) {
        ExternalManage.accessToken = accessToken;
        ExternalManage.timestamp = System.currentTimeMillis();
    }

    protected static boolean generateAccessToken() throws Exception {
        HttpHelper httpHandle = new HttpHelper();
        httpHandle.setConnectTimeout(5000);
        httpHandle.setReadTimeout(8000);
        String uri = PropertiyHelp.getContextProperty("TENCENT_SERVER") + "/get_token?version=1&format=json&appid=" + PropertiyHelp.getContextProperty("APP_ID") + "&appkey=" + PropertiyHelp.getContextProperty("APP_KEY");
        LogHelper.getLogger().info("external_uri_request,uri:" + uri);
        String res = httpHandle.doGet(uri);
        LogHelper.getLogger().info("external_uri_response,response:" + res + ",uri:" + uri);
        Map<String, Object> resMap = JSON.parseObject(res);
        Map<String, Object> resultMap = (Map<String, Object>) resMap.get("result");
        if (!resultMap.get("code").toString().equals("0") || !resultMap.get("ret").toString().equals("0"))
            return false;
        Map<String, Object> dataMap = (Map<String, Object>) resMap.get("data");
        String accessToken = dataMap.get("access_token").toString();
        ExternalManage.setAccessToken(accessToken);
        return true;
    }
}
