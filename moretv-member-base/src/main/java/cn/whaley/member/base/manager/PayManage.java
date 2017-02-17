package cn.whaley.member.base.manager;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.whaley.member.base.constant.GlobleConstant;
import cn.whaley.member.base.util.HttpHelper;
import cn.whaley.member.base.util.JsonHelper;
import cn.whaley.member.base.util.LogHelper;
import cn.whaley.member.base.util.MD5Util;
import cn.whaley.member.base.util.PropertiyHelp;

/**
 * 支付接口
 * 
 * @author linfeng 2016年4月1日
 * 
 */
public class PayManage {

	public static Map<String, Object> preCreateOrder(String payMethod, String orderNo, String amount, String goodsNo)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		LogHelper.getLogger().info("向支付服务器下单  preCreateOrder");
        HttpHelper httpHandle = new HttpHelper();
        httpHandle.setConnectTimeout(5000);
        httpHandle.setReadTimeout(5000);
		String uri = PropertiyHelp.getContextProperty("PAY_GATEWAY_SERVER") + "/pay/pre_create_order";
        LogHelper.getLogger().info("external_uri_request,uri:" + uri);
        
		String notifyUrl = getLocalNotifyUrl();

        Map<String,String> parameterMap = new HashMap<String, String>();
        parameterMap.put("pay_method", payMethod);
        parameterMap.put("order_no", orderNo);
        parameterMap.put("amount", amount);
        parameterMap.put("goods_no", goodsNo);
        parameterMap.put("notify_url", notifyUrl);
        
        StringBuilder sb = new StringBuilder();
        sb.append(payMethod);
        sb.append(orderNo);
        sb.append(amount);
        sb.append(goodsNo);
        sb.append(notifyUrl);
        sb.append(GlobleConstant.WHALEY_SERVICE_ID);
        
        String sign = MD5Util.string2MD5(sb.toString());
        parameterMap.put("sign", sign);

        String res = httpHandle.doPost(uri, parameterMap);
        LogHelper.getLogger().info("external_uri_response,response" + res + ",uri:" + uri);

        Map<String, Object> payMap = JsonHelper.jsonToMap(res);

        String status = payMap.get("status").toString();
		if (!status.equals("200")) {
			resultMap.putAll(payMap);
			return resultMap;
		}
        
		// 签名校验
        String rSign = payMap.get("sign").toString();
        
        String rContent = payMap.get("content").toString();
        String rData = payMap.get("data").toString();
        
        sb = new StringBuilder();
        sb.append(rContent);
        sb.append(rData);
        sb.append(GlobleConstant.WHALEY_SERVICE_ID);
		if (!MD5Util.string2MD5(sb.toString()).equals(rSign)) {
			/*resultMap.put("status", ApiCodeInfo.API_CREATE_ORDER_SIGN_ERR);
			resultMap.put("msg", ApiCodeInfo.getErrMsg(ApiCodeInfo.API_CREATE_ORDER_SIGN_ERR));*/
			return resultMap;
        }

		resultMap.putAll(payMap);
		return resultMap;
    }

	public static Map<String, Object> refreshOrderQRCode(String orderNo) throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		LogHelper.getLogger().info("向支付服务器刷新支付编码  refreshOrderQRCode");
		HttpHelper httpHandle = new HttpHelper();
		httpHandle.setConnectTimeout(5000);
		httpHandle.setReadTimeout(5000);

		String uri = PropertiyHelp.getContextProperty("PAY_GATEWAY_SERVER") + "/pay/agin_to_pay";
		LogHelper.getLogger().info("external_uri_request,uri:" + uri);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("order_no", orderNo);

		StringBuilder sb = new StringBuilder();
		sb.append(orderNo);
		sb.append(GlobleConstant.WHALEY_SERVICE_ID);

		String sign = MD5Util.string2MD5(sb.toString());
		parameterMap.put("sign", sign);

		String res = httpHandle.doPost(uri, parameterMap);
		LogHelper.getLogger().info("external_uri_response,response" + res + ",uri:" + uri);

		Map<String, Object> payMap = JsonHelper.jsonToMap(res);

		String status = payMap.get("status").toString();
		if (!status.equals("200")) {
			resultMap.putAll(payMap);
			return resultMap;
		}

		// 签名校验
		String rSign = payMap.get("sign").toString();

		String rContent = payMap.get("content").toString();
		String rData = payMap.get("data").toString();

		sb = new StringBuilder();
		sb.append(rContent);
		sb.append(rData);
		sb.append(GlobleConstant.WHALEY_SERVICE_ID);
		if (!MD5Util.string2MD5(sb.toString()).equals(rSign)) {
		/*	resultMap.put("status", ApiCodeInfo.API_GET_QRCODE_SIGN_ERR);
			resultMap.put("msg", ApiCodeInfo.getErrMsg(ApiCodeInfo.API_GET_QRCODE_SIGN_ERR));*/
			return resultMap;
		}

		resultMap.putAll(payMap);
		return resultMap;
	}

	private static String localNotifyUrl = null;

	private static String getLocalNotifyUrl() {
		if (localNotifyUrl == null) {
			synchronized (PayManage.class) {
				if (localNotifyUrl == null) {
					localNotifyUrl = PropertiyHelp.getContextProperty("LOCAL_HOST_SERVER")
							+ "/order/notification_private";
				}
			}
		}
		return localNotifyUrl;
	}
	/**
	 * 通知临时服务器修改状态
	 * @param payMethod
	 * @param orderNo
	 * @param amount
	 * @param goodsNo
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> notifyTempServer(String orderNo, String info){
		
		LogHelper.getLogger().info("通知临时服务器修改状态 notifyTempServer:"+info);
        HttpHelper httpHandle = new HttpHelper();
        httpHandle.setConnectTimeout(5000);
        httpHandle.setReadTimeout(5000);
		String uri = PropertiyHelp.getContextProperty("TEMP_PAY_DISPATCH_URL") + "/update_order_status";
        LogHelper.getLogger().info("notifyTempServer_request,uri:" + uri);

        Map<String,String> parameterMap = new HashMap<String, String>();
        
        String res=null;
        Map<String, Object> payMap=null;
		try {
			parameterMap.put("order_no", orderNo);
	        parameterMap.put("info", URLEncoder.encode(info, "utf-8"));
			res = httpHandle.doPost(uri, parameterMap);
			LogHelper.getLogger().info("notifyTempServer_response,response" + res + ",uri:" + uri);

	        payMap = JsonHelper.jsonToMap(res);

	        String status = payMap.get("status").toString();
			if (!status.equals("200")) {
				res=httpHandle.doPost(uri, parameterMap);
				payMap = JsonHelper.jsonToMap(res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogHelper.getLogger().info("conver error :" + res);
		}
       
		return payMap;
    }
}
