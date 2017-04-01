package cn.whaley.moretv.member.api.util;

import org.springframework.util.StringUtils;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.util.BeanHandler;

public class RequestHandler extends BeanHandler {

    public static Boolean checkBaseRequest(BaseRequest baseRequest) {
        if (baseRequest == null)
            return false;

        if (StringUtils.isEmpty(baseRequest.getAccessToken()) || StringUtils.isEmpty(baseRequest.getAccountId())
                || StringUtils.isEmpty(baseRequest.getAppVersion()) || StringUtils.isEmpty(baseRequest.getClientType())
                || StringUtils.isEmpty(baseRequest.getDeviceSerial())
                || StringUtils.isEmpty(baseRequest.getTimestamp()))
            return false;
        
        return true;
    }
}
