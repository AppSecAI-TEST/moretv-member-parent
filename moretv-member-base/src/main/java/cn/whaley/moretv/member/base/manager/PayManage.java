package cn.whaley.moretv.member.base.manager;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.util.HttpHelper;
import cn.whaley.moretv.member.base.util.JsonHelper;
import cn.whaley.moretv.member.base.util.LogHelper;
import cn.whaley.moretv.member.base.util.MD5Util;

/**
 * 支付接口
 * 
 * @author linfeng 2016年4月1日
 * 
 */
public class PayManage {

	private static String PAY_GATEWAY_SERVER;
	private static String LOCAL_HOST_SERVER;
	private static String TEMP_PAY_DISPATCH_URL;
    
    private static String payParamSignKey; //支付签名常量
    
	private static String localNotifyUrl = null;

	public static void setPayGatewayServer(String payGatewayServer) {
		PAY_GATEWAY_SERVER = payGatewayServer;
	}

	public static void setLocalHostServer(String localHostServer) {
		LOCAL_HOST_SERVER = localHostServer;
	}

	public static void setTempPayDispatchUrl(String tempPayDispatchUrl) {
		TEMP_PAY_DISPATCH_URL = tempPayDispatchUrl;
	}

	public static Map<String, Object> preCreateOrder(String payMethod, String orderNo, String amount, String goodsNo)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		LogHelper.getLogger().info("向支付服务器下单  preCreateOrder");
        HttpHelper httpHandle = new HttpHelper();
        httpHandle.setConnectTimeout(5000);
        httpHandle.setReadTimeout(5000);
		String uri = PAY_GATEWAY_SERVER + "/pay/pre_create_order";
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
        sb.append(GlobalConstant.WHALEY_SERVICE_ID);
        
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
        sb.append(GlobalConstant.WHALEY_SERVICE_ID);
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

		String uri = PAY_GATEWAY_SERVER + "/pay/agin_to_pay";
		LogHelper.getLogger().info("external_uri_request,uri:" + uri);

		Map<String, String> parameterMap = new HashMap<String, String>();
		parameterMap.put("order_no", orderNo);

		StringBuilder sb = new StringBuilder();
		sb.append(orderNo);
		sb.append(GlobalConstant.WHALEY_SERVICE_ID);

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
		sb.append(GlobalConstant.WHALEY_SERVICE_ID);
		if (!MD5Util.string2MD5(sb.toString()).equals(rSign)) {
		/*	resultMap.put("status", ApiCodeInfo.API_GET_QRCODE_SIGN_ERR);
			resultMap.put("msg", ApiCodeInfo.getErrMsg(ApiCodeInfo.API_GET_QRCODE_SIGN_ERR));*/
			return resultMap;
		}

		resultMap.putAll(payMap);
		return resultMap;
	}

	private static String getLocalNotifyUrl() {
		if (localNotifyUrl == null) {
			synchronized (PayManage.class) {
				if (localNotifyUrl == null) {
					localNotifyUrl = LOCAL_HOST_SERVER
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
		String uri = TEMP_PAY_DISPATCH_URL + "/update_order_status";
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
	
	/**
	 * 支付加密前端加密参数
	 * @param sessionToken
	 * @param cip
	 * @param goodsCode
	 * @param subject
	 * @param payAutoRenew
	 * @param payType
	 * @param orderCode
	 * @param fee
	 * @param accountId
	 * @return
	 */
	public static String getPayUrl(String sessionToken, String cip,Long timestamp,String goodsCode,String subject
			,int payAutoRenew,String payType,String orderCode,int fee,int accountId){
		StringBuffer parm = getParams4Sign(cip, timestamp, goodsCode, subject, payAutoRenew, payType, orderCode, fee, accountId);
		String sign = getPayUrlSign(parm.toString());
		//需要作为验签的参数
		parm.append("&version=1.0");
		parm.append("&sign="+sign);
		//域名路径
		String hostStr = LOCAL_HOST_SERVER+"/order_api/pay?";
		
		return hostStr+parm.toString();
    }
	
	public static String getPayUrlSign(String url){
		StringBuffer parm = new StringBuffer(url);
		parm.append(payParamSignKey);
		return MD5Util.string2MD5(parm.toString());
    }
	
	public static StringBuffer getParams4Sign(String cip,Long timestamp,String goodsCode,String subject
            ,int payAutoRenew,String payType,String orderCode,int fee,int accountId){
	    StringBuffer parm = new StringBuffer();
        //需要作为验签的参数
        parm.append("orderCode="+orderCode);
        parm.append("&timestamp="+timestamp);
        parm.append("&cip="+cip);
        parm.append("&goodsCode="+goodsCode);
        parm.append("&subject="+subject);
        parm.append("&payAutoRenew="+payAutoRenew);
        parm.append("&payType="+payType);
        parm.append("&fee="+fee);
        parm.append("&expire_time=120");
        parm.append("&accountId="+accountId);
        return parm;
	}
}
