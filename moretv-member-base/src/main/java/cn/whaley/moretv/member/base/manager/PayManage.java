package cn.whaley.moretv.member.base.manager;

import java.util.Date;

import cn.whaley.moretv.member.base.util.MD5Util;

public class PayManage {

	private static String LOCAL_HOST_SERVER;
    
    private static String PAY_GATEWAY_SIGN_KEY; //支付签名常量
    
	public static void setLocalHostServer(String localHostServer) {
		LOCAL_HOST_SERVER = localHostServer;
	}

    public static void setPayGatewaySignKey(String payGatewaySignKey) {
        PAY_GATEWAY_SIGN_KEY = payGatewaySignKey;
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
			,int payAutoRenew, String payType, String orderCode, int fee, String accountId, String openId){
	    long createTime = new Date().getTime();
		StringBuffer parm = getParams4Sign(cip, timestamp, goodsCode, subject, payAutoRenew, payType, orderCode, fee, accountId, createTime);
		String sign = getPayUrlSign(parm.toString());
		//需要作为验签的参数
		parm.append("&version=1.0");
		parm.append("&sign=" + sign);
		parm.append("&openId=" + openId);
		//域名路径
		String hostStr = LOCAL_HOST_SERVER + "/order_api/pay?";
		
		return hostStr+parm.toString();
    }
	
	public static String getPayUrlSign(String url){
		StringBuffer parm = new StringBuffer(url);
		parm.append(PAY_GATEWAY_SIGN_KEY);
		return MD5Util.string2MD5(parm.toString());
    }
	
	public static StringBuffer getParams4Sign(String cip,Long timestamp,String goodsCode,String subject
            ,int payAutoRenew,String payType,String orderCode,int fee, String accountId, long createTime){
	    StringBuffer parm = new StringBuffer();
        //需要作为验签的参数
        parm.append("orderCode=" + orderCode);
        parm.append("&timestamp=" + timestamp);
        parm.append("&cip=" + cip);
        parm.append("&goodsCode=" + goodsCode);
        parm.append("&subject=" + subject);
        parm.append("&payAutoRenew=" + payAutoRenew);
        parm.append("&payType=" + payType);
        parm.append("&fee=" + fee);
        parm.append("&expireTime=120");
        parm.append("&accountId=" + accountId);
        parm.append("&createTime=" + createTime);
        return parm;
	}
}
