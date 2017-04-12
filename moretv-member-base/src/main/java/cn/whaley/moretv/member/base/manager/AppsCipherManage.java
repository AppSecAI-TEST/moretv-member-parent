package cn.whaley.moretv.member.base.manager;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.whaley.moretv.member.base.util.Base64Helper;
import cn.whaley.moretv.member.base.util.HttpHelper;
import cn.whaley.moretv.member.base.util.LogHelper;


/**
 * apps加解密工具类
 * 
 * @author linfeng 2016年4月8日
 * 
 */
public class AppsCipherManage {

	private static String APPS_ENCRYPT_URL;
	private static String APPS_DECRYPTO_URL;

	public static void setAppsEncryptUrl(String appsEncryptUrl) {
		APPS_ENCRYPT_URL = appsEncryptUrl;
	}

	public static void setAppsDecryptoUrl(String appsDecryptoUrl) {
		APPS_DECRYPTO_URL = appsDecryptoUrl;
	}

	/**
	 * apps加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String appsEncrypto(String data) {

		LogHelper.getLogger().info("Encrypto:" + data);
		HttpHelper httpHandle = new HttpHelper();
		httpHandle.setConnectTimeout(5000);
		httpHandle.setReadTimeout(5000);
		String url = APPS_ENCRYPT_URL;
		LogHelper.getLogger().info("appsEncrypto_request,uri:" + url);

		String res = null;
		String encryptoData = null;
		JSONObject JsonObject = null;
		Map<String, Object> parameterMap =new HashMap<String, Object>();
		try {
			parameterMap.put("text", URLEncoder.encode(data,"utf-8"));
			res = httpHandle.doPost(url, parameterMap);
			LogHelper.getLogger().info("appsEncrypto_response,response" + res);

			JsonObject = JSONObject.parseObject(res);

			String status = JsonObject.get("status").toString();
			if ("200".equals(status)) {
				encryptoData = JsonObject.get("cipher").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		LogHelper.getLogger().info("Encrypto_result:" + encryptoData);
		return encryptoData;
	}

	/**
	 * apps解密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String appsDecrypto(String data) {

		LogHelper.getLogger().info("Decrypto:" + data);
		HttpHelper httpHandle = new HttpHelper();
		httpHandle.setConnectTimeout(5000);
		httpHandle.setReadTimeout(5000);

		String res = null;
		String decryptoData = null;
		JSONObject JsonObject = null;
		try {
			String uri = APPS_DECRYPTO_URL + "?" + URLDecoder.decode(data, "utf-8");
			LogHelper.getLogger().info("appsDecrypto_request,uri:" + uri);
			res = httpHandle.doGet(uri);
			LogHelper.getLogger().info("appsDecrypto_response,response" + res);
			JsonObject = JSON.parseObject(res);

			String status = JsonObject.get("status").toString();
			if ("201".equals(status)) {
				decryptoData = Base64Helper
						.base64Decode(URLDecoder.decode(JsonObject.get("param").toString(), "utf-8").getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		LogHelper.getLogger().info("Decrypto_result:" + decryptoData);
		return decryptoData;
	}
}
