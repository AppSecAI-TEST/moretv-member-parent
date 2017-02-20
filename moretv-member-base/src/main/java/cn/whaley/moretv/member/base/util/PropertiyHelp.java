package cn.whaley.moretv.member.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
 

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
 
/**
 * 自定义ProjectDBinfoConfigurer返回properties内容
 * @ClassName: ProjectDBinfoConfigurer
 * @Description: TODO (请用一句话描述该类做什么)
 * @author ZhangYQ iitshare@itblood.com
 * @date: 2012-11-20 下午11:48:32
 */
public class PropertiyHelp extends PropertyPlaceholderConfigurer {
    private static Map ctxPropertiesMap;
 
    @Override
    protected void processProperties(
            ConfigurableListableBeanFactory beanFactoryToProcess,
            Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        ctxPropertiesMap = new HashMap();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            ctxPropertiesMap.put(keyStr, value);
        }
    }
    
    public static String getContextProperty(String name) {
    	if (!ctxPropertiesMap.containsKey(name))
            return "";
        return String.valueOf(ctxPropertiesMap.get(name));
    }
}
