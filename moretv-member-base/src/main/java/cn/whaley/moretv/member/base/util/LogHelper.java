package cn.whaley.moretv.member.base.util;

import org.apache.log4j.Logger;

/**
 * Created by leiwenbin on 15/9/29.
 * Log Object
 */

public class LogHelper {

    protected Logger logger;

    private static volatile LogHelper instance = null;

    private LogHelper() {
        this.logger = Logger.getLogger("dolphinLogger");
        setLogger(logger);
    }

    public static Logger getLogger() {
        if(instance == null) {
            synchronized(LogHelper.class) {
                if(instance == null) {
                    instance = new LogHelper();
                }
            }
        }
        return instance.logger;
    }

    protected void setLogger(Logger logger) {
        this.logger = logger;
    }
}
