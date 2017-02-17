package cn.whaley.member.base.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 17/02/15.
 * ERROR CODE
 */

public class ApiCodeInfo {

    public static Map<Integer, String> errMap = new HashMap<Integer, String>();

    public final static int API_OK = 200;
	
    static
    {
        errMap.put(API_OK, "success");
    }

    public static String getErrMsg(int errCode) {
        if (!errMap.containsKey(errCode))
            return "";
        return errMap.get(errCode);
    }

}
