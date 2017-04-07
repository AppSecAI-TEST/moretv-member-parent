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

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    public static Object validate(Object[] args, LogAspect.LogInfo logInfo) {
        if (logInfo.getClazzName().startsWith("HttpErrorHandler")) {
            return null;
        }

        if (args == null || args.length == 0) {
            return define(ApiCodeEnum.API_PARAM_NULL, logInfo);
        }

        if (args[0].getClass() != BaseRequest.class && args[0].getClass().getSuperclass() != BaseRequest.class) {
            return define(ApiCodeEnum.API_PARAM_ERR, logInfo);
        } else {
            BaseRequest baseRequest = (BaseRequest) args[0];

            if (StringUtils.isEmpty(baseRequest.getAccessToken()) && baseRequest.getAccountId() == null
                    && StringUtils.isEmpty(baseRequest.getAppVersion()) && StringUtils.isEmpty(baseRequest.getClientType())
                    && StringUtils.isEmpty(baseRequest.getDeviceSerial()) && baseRequest.getTimestamp() == null) {
                return define(ApiCodeEnum.API_PARAM_NULL, logInfo);
            }

            ValidateIgnore accountIgnore = getAccountIgnore(logInfo.getClazz(), logInfo.getMethodName());
            if (accountIgnore == null && baseRequest.getAccountId() == null) {
                return define(ApiCodeEnum.API_PARAM_ACCOUNT_ID_NULL, logInfo);
            }
        }
        return null;
    }

    private static Object define(ApiCodeEnum apiCodeEnum, LogAspect.LogInfo logInfo) {
        ResultResponse result = ResultResponse.define(apiCodeEnum);
        logger.info(logInfo.afterLog(result));
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