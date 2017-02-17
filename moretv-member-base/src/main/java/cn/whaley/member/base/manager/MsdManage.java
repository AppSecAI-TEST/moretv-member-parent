package cn.whaley.member.base.manager;

import cn.whaley.member.base.util.HttpHelper;
import cn.whaley.member.base.util.JsonHelper;
import cn.whaley.member.base.util.LogHelper;
import cn.whaley.member.base.util.PropertiyHelp;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by leiwenbin on 15/11/3.
 * manager internal accesstoken
 */

public class MsdManage {
    public static String getInternalAccessToken(String videoinfo) throws Exception {
        LogHelper.getLogger().info(videoinfo);
        HttpHelper httpHandle = new HttpHelper();
        httpHandle.setConnectTimeout(5000);
        httpHandle.setReadTimeout(5000);
        String uri = PropertiyHelp.getContextProperty("MSD_SERVER") + "/getToken?videoinfo=" + videoinfo + "&timestamp=" + System.currentTimeMillis();
        LogHelper.getLogger().info("external_uri_request,uri:" + uri);
        String res = httpHandle.doGet(uri);
        LogHelper.getLogger().info("external_uri_response,response" + res + ",uri:" + uri);

 
        JSONObject kgcJson = JSONObject.parseObject(res);
        String status = kgcJson.get("status").toString();

        if (!status.equals("200"))
            return "";
        String token = kgcJson.get("internal_accesstoken").toString();
        return token;
    }
}
