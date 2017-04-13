package cn.whaley.moretv.member.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by leiwenbin on 15/9/29.
 * Log Object
 */

public class LogHelper {

    protected Logger logger;

    private static volatile LogHelper instance = null;

    private LogHelper(Class clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    public static Logger getLogger(Class clazz) {
        if(instance == null) {
            synchronized(clazz) {
                if(instance == null) {
                    instance = new LogHelper(clazz);
                }
            }
        }
        return instance.logger;
    }

    public static Logger getLogger() {
        return getLogger(LogHelper.class);
    }

}
