package cn.whaley.moretv.member.base.dto;



/**
 * @author tangzhicheng 2016年4月23日
 * whaley 和  第三方产品包订购信息
 */
public class ThirdPartyInfo {
	
	 private String whaleyProduct;

	 private String cpProduct;
	 
	 private int duration;
	 
	 private int durationDay;
	 
	 private String cpProductName;//cpProduct+_+duration
	 
	 private String cpName;
	 
	 private String cpAccountId;
	 
	// private CpAccount cpAccount;

	public String getWhaleyProduct() {
		return whaleyProduct;
	}

	public void setWhaleyProduct(String whaleyProduct) {
		this.whaleyProduct = whaleyProduct;
	}

	public String getCpProduct() {
		return cpProduct;
	}

	public void setCpProduct(String cpProduct) {
		this.cpProduct = cpProduct;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getCpProductName() {
		return cpProductName;
	}

	public void setCpProductName(String cpProductName) {
		this.cpProductName = cpProductName;
	}

	public String getCpName() {
		return cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getCpAccountId() {
		return cpAccountId;
	}

	/*public void setCpAccountId(String cpAccountId) {
		this.cpAccountId = cpAccountId;
	}

	public void setCpAccount(CpAccount cpAccount) {
		this.cpAccount = cpAccount;
	}*/

	public int getDurationDay() {
		return durationDay;
	}

	public void setDurationDay(int durationDay) {
		this.durationDay = durationDay;
	}
}
