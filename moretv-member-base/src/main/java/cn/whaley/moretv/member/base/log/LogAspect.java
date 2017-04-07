package cn.whaley.moretv.member.base.log;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.util.ValidateHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


/**
 * <p>统一日志记录</p>
 *
 * Created by Bob Jiang on 2016/12/17.
 */
@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义日志切入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logAspect() {}

    @Around("logAspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LogInfo logInfo = new LogInfo(point, request);

        logger.info(logInfo.beforeLog());

        Object result = ValidateHandler.validate(args, logInfo);
        if (result != null) {
            return result;
        }

        try {
            result = point.proceed(args);
            logger.info(logInfo.afterLog(result));
            return result;
        } catch (Throwable e) {
            logger.error(logInfo.throwLog(e.getMessage()), e);
            return ResultResponse.failed(e.getMessage());
        }
    }

    /**
     * 日志信息
     */
    public class LogInfo {
        private Class clazz;
        private String methodName;
        private String uri;
        private Object[] params;
        private String method;
        private Object result;
        private long beginTime;
        private long costMilliseconds;

        public LogInfo(JoinPoint point, HttpServletRequest request) {
            this.clazz  = point.getTarget().getClass();
            this.methodName = point.getSignature().getName();
            this.uri        = request.getRequestURI();
            this.method     = request.getMethod();
            this.params     = point.getArgs();
            this.beginTime  = System.currentTimeMillis();
        }

        public String getClazzName() {
            return clazz.getSimpleName();
        }

        public Class getClazz() {
            return clazz;
        }

        public void setClazz(Class clazz) {
            this.clazz = clazz;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public String getParams() {
            return Arrays.toString(params);
        }

        public void setParams(Object[] params) {
            this.params = params;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getCostMilliseconds() {
            return costMilliseconds;
        }

        public void setCostMilliseconds(long costMilliseconds) {
            this.costMilliseconds = costMilliseconds;
        }

        public String beforeLog() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [Request  ][").append(uri).append("][").append(method).append("] ");
            buffer.append(getClazzName()).append(".").append(methodName);
            buffer.append("() 参数:").append(getParams());
            return buffer.toString();
        }

        public String afterLog(Object result) {
            setResult(result);
            setCostMilliseconds(System.currentTimeMillis() - getBeginTime());
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [Response ][").append(uri).append("][").append(method).append("] ");
            buffer.append(getClazzName()).append(".").append(methodName);
            buffer.append("() 响应结果：").append(result).append(" 耗时：").append(costMilliseconds).append("ms");
            return buffer.toString();
        }

        public String throwLog(String message) {
            setCostMilliseconds(System.currentTimeMillis() - getBeginTime());
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [Exception][").append(uri).append("][").append(method).append("] ");
            buffer.append(getClazzName()).append(".").append(methodName);
            buffer.append("() 参数:").append(getParams());
            buffer.append(" 耗时：").append(costMilliseconds).append("ms");
            buffer.append(" 异常信息：").append(message);
            return buffer.toString();
        }
    }

}
