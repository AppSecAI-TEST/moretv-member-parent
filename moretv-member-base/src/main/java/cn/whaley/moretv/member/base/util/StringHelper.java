package cn.whaley.moretv.member.base.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.whaley.moretv.member.base.constant.GlobalConstant;

/**
 * Created by tangzc on 15/9/29. Log Object
 */

public class StringHelper {

	private static final Logger logger = LoggerFactory.getLogger(StringHelper.class);
	
	public static boolean isEmpty(String str) {
		Boolean flg = false;
		if (str == null || "".equals(str)) {
			flg = true;
		}
		return flg;
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean strsIsEmpty(String... str) {
		for (int i = 0; i < str.length; i++) {
			if (isEmpty(str[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if(str != null && (strLen = str.length()) != 0) {
			for(int i = 0; i < strLen; ++i) {
				if(!Character.isWhitespace(str.charAt(i))) {
					return false;
				}
			}
			return true;
		} else {
			return true;
		}
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	public static String converFenToYuan(int price) {
		return new BigDecimal(price).divide(new BigDecimal(100)).toString()+ GlobalConstant.UNIT_YUAN;
	}
	
	public static String converFenUpToFullYuan(int price) {
		int yuan = price / 100;
		if(price % 100 > 0){
			yuan++;
		}
		return yuan+ GlobalConstant.UNIT_YUAN;
	}
	
	public static boolean checkSignNew(Map<String, String> paramMap, String key, String sign) {
		if (paramMap == null || paramMap.size() == 0) {
			return false;
		}
		//按参数名称排序
		List<String> nameList = new ArrayList<String>(paramMap.keySet());
		Collections.sort(nameList, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}

		});
		String signStr = "";
		for (String nameKey : nameList) {
			String value = paramMap.get(nameKey);
			if (!isEmpty(value)) {
				signStr = signStr + value;
			}
		}
		//最后拼接上KEY
		signStr = signStr + key;
		//MD5校验
		logger.info("payparam:"+signStr);
		String calculateSign = MD5Util.string2MD5(signStr);
		if (calculateSign == null || !calculateSign.equals(sign)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 传入的对象所有属性必须为基本类型或其包装类，不能有复杂类型或集合
	 * @param param
	 * @param key
	 * @param sign
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean checkSignNewObject(Object param, String key, String sign) {
		Map<String, String> paramMap = new HashMap<String, String>();
		Class objClass = param.getClass();
		Field[] fields = objClass.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				Field f = fields[i];
				if ("sign".equals(f.getName())
						|| "serialVersionUID".equals(f.getName())) {
					continue;
				}
				f.setAccessible(true); // 设置些属性是可以访问的
				Object val = f.get(param);// 得到此属性的值
				if (val != null) {
					paramMap.put(f.getName(), val.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return checkSignNew(paramMap, key, sign);
	}
}
