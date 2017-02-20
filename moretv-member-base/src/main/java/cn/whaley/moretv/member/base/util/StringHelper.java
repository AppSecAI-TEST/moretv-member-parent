package cn.whaley.moretv.member.base.util;

import java.math.BigDecimal;

import cn.whaley.moretv.member.base.constant.GlobalConstant;


/**
 * Created by leiwenbin on 15/9/29. Log Object
 */

public class StringHelper {

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
}
