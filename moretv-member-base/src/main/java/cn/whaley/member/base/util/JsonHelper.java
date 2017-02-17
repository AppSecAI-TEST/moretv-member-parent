package cn.whaley.member.base.util;



import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * Created by tangzc on 17/2/14.
 * Json Tools
 */

public final class JsonHelper {

    public static String mapToString(Map<String, Object> source) {
        return JSON.toJSONString(source);
    }
    
	public static Map<String, Object> jsonToMap(String jsonStr) {
		Map<String, Object> map =(Map<String, Object>) JSONObject.parseObject(jsonStr);
		return map;
	}
	
	public static String objectToJson(Object object,String [] ignore) {
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
		Set<String> ignreSet = filter.getExcludes() ;
		for (int i = 0; i < ignore.length; i++) {
			ignreSet.add(ignore[i]);
		}
        return JSON.toJSONString(object,filter);
	}
}
