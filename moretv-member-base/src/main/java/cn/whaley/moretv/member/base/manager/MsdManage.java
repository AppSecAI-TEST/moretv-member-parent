package cn.whaley.moretv.member.base.manager;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.util.HttpHelper;
import cn.whaley.moretv.member.base.util.LogHelper;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by leiwenbin on 15/11/3.
 * manager internal accesstoken
 */

public class MsdManage {

    public static String MSD_SERVER;
    public static String XIANGGU_SERVER;

    public static void setMsdServer(String msdServer) {
        MSD_SERVER = msdServer;
    }

    public static void setXiangguServer(String xiangguServer) {
        XIANGGU_SERVER = xiangguServer;
    }

    public static String getInternalAccessToken(String cp, String videoinfo) {

        HttpHelper httpHandle = new HttpHelper();
        String uri = "%s?videoinfo=" + videoinfo + "&timestamp=" + System.currentTimeMillis();

        if (GlobalConstant.CP_MOGUV.equals(cp)) {
            uri = String.format(uri, MSD_SERVER);
        } else if(GlobalConstant.CP_XIANGGU.equals(cp)) {
            uri = String.format(uri, XIANGGU_SERVER);
        } else {
            throw new SystemException(ApiCodeEnum.API_PARAM_ERR);
        }

        LogHelper.getLogger().info("external_uri_request,uri:{}", uri);

        try {
            String res = httpHandle.doGet(uri);
            LogHelper.getLogger().info("external_uri_response,response:{}", res);

            JSONObject kgcJson = JSONObject.parseObject(res);
            String status = kgcJson.get("status").toString();

            if (!status.equals("200")) {
                throw new SystemException(ApiCodeEnum.API_MSD_ACCESS_TOKEN_ERR);
            }
            return kgcJson.get("internal_accesstoken").toString();
        } catch (Exception e) {
            LogHelper.getLogger().error("external_uri_response,error", e);
            throw new SystemException(ApiCodeEnum.API_MSD_ACCESS_TOKEN_ERR);
        }
    }
}
