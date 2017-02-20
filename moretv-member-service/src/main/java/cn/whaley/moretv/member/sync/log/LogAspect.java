package cn.whaley.moretv.member.sync.log;


import org.aspectj.lang.JoinPoint;
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

    private ThreadLocal<LogInfo> localLog = new ThreadLocal<>();

    /**
     * 定义日志切入点
     */
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void logAspect() {}

    /**
     * 前置通知
     *
     * @param point
     */
    @Before("logAspect()")
    public void doBefore (JoinPoint point) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LogInfo logInfo = new LogInfo(point, request);
        localLog.set(logInfo);
        logger.info(logInfo.beforeLog());
    }

    /**
     * 后置通知
     *
     * @param result
     */
    @AfterReturning(pointcut = "logAspect()", returning = "result")
    public void doAfter (Object result) {
        LogInfo logInfo = localLog.get();
        if (logInfo != null) {
            logInfo.setResult(result);
            logInfo.setCostMilliseconds(System.currentTimeMillis() - logInfo.getBeginTime());
            logger.info(logInfo.afterLog());
            localLog.remove();
        }
    }

    /**
     * 异常通知
     *
     * @param e
     */
    @AfterThrowing(pointcut = "logAspect()", throwing = "e")
    public void doAfterThrowing (Throwable e) {
        LogInfo logInfo = localLog.get();
        if (logInfo != null) {
            logInfo.setCostMilliseconds(System.currentTimeMillis() - logInfo.getBeginTime());
            logger.error(logInfo.throwLog(e.getMessage()));
            localLog.remove();
        }
    }

    /**
     * 日志信息
     */
    class LogInfo {
        private String className;
        private String methodName;
        private String uri;
        private Object[] params;
        private String method;
        private Object result;
        private long beginTime;
        private long costMilliseconds;

        public LogInfo(JoinPoint point, HttpServletRequest request) {
            this.className  = point.getTarget().getClass().getSimpleName();
            this.methodName = point.getSignature().getName();
            this.uri        = request.getRequestURI();
            this.params     = point.getArgs();
            this.method     = request.getMethod();
            this.beginTime  = System.currentTimeMillis();
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
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
            buffer.append("LOG [").append(uri).append("][").append(method).append("] ");
            buffer.append(className).append(".").append(methodName);
            buffer.append("() 参数:").append(getParams());
            return buffer.toString();
        }

        public String afterLog() {
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [").append(uri).append("][").append(method).append("] ");
            buffer.append(className).append(".").append(methodName).append("()");
            buffer.append(" 响应结果：").append(result).append(" 耗时：").append(costMilliseconds).append("ms");
            return buffer.toString();
        }

        public String throwLog(String message) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOG [").append(uri).append("][").append(method).append("] ");
            buffer.append(className).append(".").append(methodName).append("()");
            buffer.append(" 参数:").append(getParams());
            buffer.append(" 耗时：").append(costMilliseconds).append("ms");
            buffer.append(" 异常信息：").append(message);
            return buffer.toString();
        }
    }

}
