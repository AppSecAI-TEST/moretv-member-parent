package cn.whaley.moretv.member.base.util;


import cn.whaley.moretv.member.base.annotation.ValidateIgnore;
import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.log.LogAspect;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Bob Jiang on 2017/4/6.
 */
public class ValidateHandler {

    public static Object validate(Object[] args, LogAspect.LogInfo logInfo) {
        if (logInfo.getClazzName().startsWith("HttpErrorHandler") || args == null || args.length == 0) {
            return null;
        }

        if (args[0] instanceof BaseRequest || BaseRequest.class.isAssignableFrom(args[0].getClass())) {
            BaseRequest baseRequest = (BaseRequest) args[0];

            if (StringUtils.isEmpty(baseRequest.getAccessToken()) && StringUtils.isEmpty(baseRequest.getAccountId())
                    && StringUtils.isEmpty(baseRequest.getAppVersion()) && StringUtils.isEmpty(baseRequest.getClientType())
                    && StringUtils.isEmpty(baseRequest.getDeviceId()) && baseRequest.getTimestamp() == null) {
                return define(ApiCodeEnum.API_PARAM_NULL, logInfo);
            }

            ValidateIgnore accountIgnore = getAccountIgnore(logInfo.getClazz(), logInfo.getMethodName());
            if (accountIgnore == null && StringUtils.isEmpty(baseRequest.getAccountId())) {
                return define(ApiCodeEnum.API_PARAM_ACCOUNT_ID_NULL, logInfo);
            }
        }
        return null;
    }

    private static Object define(ApiCodeEnum apiCodeEnum, LogAspect.LogInfo logInfo) {
        ResultResponse result = ResultResponse.define(apiCodeEnum);
        logInfo.afterLog(result);
        return result;
    }

    public static ValidateIgnore getAccountIgnore(Class clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                Annotation[][] annotations = method.getParameterAnnotations();
                if (annotations.length > 0){
                    for (int i = 0; i < annotations.length; i++) {
                        for (int j = 0; j < annotations[i].length; j++) {
                            Annotation annotation = annotations[i][j];
                            if (annotation instanceof ValidateIgnore) {
                                return (ValidateIgnore) annotation;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
