package cn.whaley.moretv.member.base.manager;

import cn.whaley.moretv.member.base.util.HttpHelper;
import cn.whaley.moretv.member.base.util.LogHelper;
import cn.whaley.moretv.member.base.util.PropertiyHelp;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by admin on 15/9/28.
 * manager kgc
 */

public class KgcManage {
    public static boolean kgcAuth(String uuid, String text, String sign, String version) throws Exception {
        HttpHelper httpHandle = new HttpHelper();
        httpHandle.setConnectTimeout(5000);
        httpHandle.setReadTimeout(5000);
        String uri = PropertiyHelp.getContextProperty("KGC_SERVER") + "/verify" + "?userId=" + uuid + "&msg=" + text + "&sign=" + sign + "&version=" + version;
        LogHelper.getLogger().info("external_uri_request,uri:" + uri);
        String res = httpHandle.doGet(uri);
        LogHelper.getLogger().info("external_uri_response,response" + res + ",uri:" + uri);

        JSONObject kgcJson = JSONObject.parseObject(res);
        String status = kgcJson.getString("status");
        String result = kgcJson.getString("result");

        return !(!status.equals("200") || result == null || !result.equals("1"));
    }
}
