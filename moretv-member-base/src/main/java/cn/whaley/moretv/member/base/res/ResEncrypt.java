package cn.whaley.moretv.member.base.res;


import cn.whaley.moretv.member.base.constant.ApiCodeInfo;

public class ResEncrypt<T> extends ResBase {
    
    protected T data;
	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResEncrypt() { }

	public ResEncrypt(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public ResEncrypt(ApiCodeInfo.CodeEnum codeEnum) {
		this.code = codeEnum.getCode();
		this.msg = codeEnum.getMsg();
	}

	public ResEncrypt(ApiCodeInfo.CodeEnum codeEnum, T data) {
		this.code = codeEnum.getCode();
		this.msg = codeEnum.getMsg();
		this.data = data;
	}

	public ResEncrypt(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public static ResEncrypt success() {
		return new ResEncrypt(ApiCodeInfo.CodeEnum.API_OK);
	}

	public static <T> ResEncrypt<T> success(T data) {
		return new ResEncrypt(ApiCodeInfo.CodeEnum.API_OK, data);
	}

	public static ResEncrypt failed() {
		return new ResEncrypt(ApiCodeInfo.CodeEnum.API_SYS_ERR);
	}

	public static ResEncrypt failed(String msg) {
		return new ResEncrypt(ApiCodeInfo.CodeEnum.API_SYS_ERR.getCode(), msg);
	}

	public static ResEncrypt failed(int code, String msg) {
		return new ResEncrypt(code, msg);
	}

	public static ResEncrypt failed(ApiCodeInfo.CodeEnum codeEnum) {
		return new ResEncrypt(codeEnum);
	}

	public static ResEncrypt define(ApiCodeInfo.CodeEnum codeEnum) {
		return new ResEncrypt(codeEnum);
	}

	public static ResEncrypt define(int code, String msg) {
		return new ResEncrypt(code, msg);
	}

	public static <T> ResEncrypt<T> define(ApiCodeInfo.CodeEnum codeEnum, T data) {
		return new ResEncrypt(codeEnum, data);
	}

	public static <T> ResEncrypt<T> define(int code, String msg, T data) {
		return new ResEncrypt(code, msg, data);
	}

}
