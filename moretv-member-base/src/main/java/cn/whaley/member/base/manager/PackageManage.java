package cn.whaley.member.base.manager;

import cn.whaley.member.base.constant.GlobleConstant;
import cn.whaley.member.base.dto.ThirdPartyInfo;

public class PackageManage {
	
/*	public static Map<String, String> tencentPackage=new HashMap<String, String>();
	
	static{
		for (int i = 1; i <13; i++) {
			String temp=PropertiyHelp.getContextProperty("tencent.wj_basic_"+i);
			if (!StringHelper.isEmpty(temp)) {
				tencentPackage.put("wj_basic_"+i, temp);
			}
		}
	}*/
	
/*    public static ThirdPartyInfo getTentcentPackage(String name,int duration) {
    	if (duration==0) {
    		return null;
		}
    	
    	String key =null;
    	do {
    		key =name+"_"+duration;
    		if (tencentPackage.containsKey(key)){
    			ThirdPartyInfo whaleyToCpProductInfo=new ThirdPartyInfo();
    			whaleyToCpProductInfo.setCpName(GlobleConstant.CP_TENCENT);
    			whaleyToCpProductInfo.setCpProduct(name);
    			whaleyToCpProductInfo.setDuration(duration);
    			whaleyToCpProductInfo.setCpProductName(tencentPackage.get(key));
    			return	whaleyToCpProductInfo;
    		}else {
        		duration=duration+1;
			}
		} while (duration < 13);
    	
        return null;
    }*/
    
    /*public static ThirdPartyInfo getTentcentPackage(String name,int duration) {
    	if (duration==0) {
    		return null;
		}
    	ThirdPartyInfo whaleyToCpProductInfo=new ThirdPartyInfo();
		whaleyToCpProductInfo.setCpName(GlobleConstant.CP_TENCENT);
		whaleyToCpProductInfo.setCpProduct(name);
		whaleyToCpProductInfo.setDuration(duration);
		
		//计算需要订购多少个包
		int productCount=duration/12;
		int remain =duration%12;
		StringBuffer cpProductName = new StringBuffer();
		for (int i = 0; i < productCount; i++) {
			cpProductName.append("+wj_basic_12");
		}
		if (remain!=0) {
			cpProductName.append("+wj_basic_");
			cpProductName.append(String.valueOf(remain));
		}
		whaleyToCpProductInfo.setCpProductName(cpProductName.substring(1, cpProductName.length()).toString());
    	
    	
        return whaleyToCpProductInfo;
    }*/
    /*
    public static ThirdPartyInfo getTentcentPackage(String name,int duration) {
    	if (duration==0) {
    		return null;
		}
    	duration = duration>12?12:duration;
    	ThirdPartyInfo whaleyToCpProductInfo=new ThirdPartyInfo();
		whaleyToCpProductInfo.setCpName(GlobleConstant.CP_TENCENT);
		whaleyToCpProductInfo.setCpProduct(name);
		whaleyToCpProductInfo.setDuration(duration);
		whaleyToCpProductInfo.setCpProductName(name+"_"+duration);
        return whaleyToCpProductInfo;
    }*/
    
    public static ThirdPartyInfo getTentcentPackage(String name,int duration,int durationDay) {
    	if ((duration == 0 && durationDay == 0) ) {
    		return null;
		}
    	
    	ThirdPartyInfo whaleyToCpProductInfo=new ThirdPartyInfo();
    	
    	if(duration >= 12){
    		//最多支持12个月
    		duration = 12;
    		durationDay = 0;
    	}
    	
    	whaleyToCpProductInfo.setDuration(duration);
    	whaleyToCpProductInfo.setDurationDay(durationDay);
		whaleyToCpProductInfo.setCpName(GlobleConstant.CP_TENCENT);
		whaleyToCpProductInfo.setCpProduct(name);
		
		String cpProductName = "";
		
		//腾讯月包
		if (duration!=0){
			cpProductName = name+"_" + duration;
		}
		
		//腾讯天包
		if (durationDay!=0){
			if (!"".equals(cpProductName)) {
				cpProductName =cpProductName +"+";
			}
			cpProductName = cpProductName + name+"_d" + durationDay;
		}
			
		whaleyToCpProductInfo.setCpProductName(cpProductName);
        return whaleyToCpProductInfo;
    }
}
