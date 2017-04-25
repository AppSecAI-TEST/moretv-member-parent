package cn.whaley.moretv.member.service.tencent.impl;

import cn.whaley.moretv.member.base.config.CustomProperty;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.manager.ExternalManage;
import cn.whaley.moretv.member.base.util.HttpHelper;
import cn.whaley.moretv.member.service.tencent.BaseTencentService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * Created by Bob Jiang on 2017/4/13.
 */
@Service
public class BaseTencentServiceImpl implements BaseTencentService {

    protected static final Logger logger = LoggerFactory.getLogger(BaseTencentService.class);

    @Autowired
    protected CustomProperty.Tencent tencent;

    public String getAccessToken() {
        return ExternalManage.getAccessToken();
    }

    public JSONObject getRequest(String url, String logMsg) {
        HttpHelper httpHandle = new HttpHelper();
        try {
            logger.info("{} Request: {}", logMsg, url);
            String response = httpHandle.doGet(url);
            logger.info("{} Response: {}", logMsg, response);
            return result(response, logMsg);
        } catch (Exception e) {
            logger.error(logMsg + " Exception ", e);
            throw new SystemException(ApiCodeEnum.API_TENCENT_REQUEST_ERR);
        }
    }

    public JSONObject result(String resultResponse, String logMsg) {
        JSONObject response = JSON.parseObject(resultResponse);
        JSONObject result = response.getJSONObject("result");

        if (!GlobalConstant.TENCENT_RESULT_STUTAS_YES.equals(result.getString("ret"))
                || !GlobalConstant.TENCENT_RESULT_STUTAS_YES.equals(result.getString("code"))) {
            logger.error("{} Error : {}", logMsg, result.getString("msg"));
            throw new SystemException(String.valueOf(ApiCodeEnum.API_TENCENT_RESULT_ERR.getCode()), result.getString("msg"));
        }
        return response;
    }

}
