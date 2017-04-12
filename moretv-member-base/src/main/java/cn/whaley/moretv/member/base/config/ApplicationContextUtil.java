package cn.whaley.moretv.member.base.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    private ApplicationContextUtil(){}

    public static <T> T getBean(Class<T> clazz){
        return context.getBean(clazz);
    }

    public static Object getBean(String name){
        return context.getBean(name);
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        context = ctx;
    }
}
