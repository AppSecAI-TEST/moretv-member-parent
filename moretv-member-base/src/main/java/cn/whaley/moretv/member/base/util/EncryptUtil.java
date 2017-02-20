package cn.whaley.moretv.member.base.util;

import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
import cn.whaley.moretv.member.base.manager.AppsCipherManage;
import cn.whaley.moretv.member.base.res.ResBase;
import cn.whaley.moretv.member.base.res.ResEncrypt;

/**
 * Created by tangzc on 15/9/24.
 * Json Tools
 */

public final class EncryptUtil {

    public static ResEncrypt converResEncrypt(ResBase object) throws Exception {

		ResEncrypt resEncrypt = new ResEncrypt();
		resEncrypt.setCode(object.getCode());
		resEncrypt.setMsg(object.getMsg());
		if (object.getCode() == ApiCodeInfo.API_OK) {
			String data=JsonHelper.objectToJson(object, new String[]{"status","msg"});	
			String encryptData = AppsCipherManage.appsEncrypto(data);
			
			if (encryptData == null) {
				throw new Exception("encrypt data is null");
			}
			resEncrypt.setData(encryptData);
		}
		
		return resEncrypt;

    }
}
