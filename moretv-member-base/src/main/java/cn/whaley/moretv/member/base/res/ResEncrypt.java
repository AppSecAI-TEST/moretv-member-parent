package cn.whaley.moretv.member.base.res;


import cn.whaley.moretv.member.base.constant.ApiCodeEnum;

public class ResEncrypt<T> extends ResBase<T> {
    
    protected T data;
	
	public ResEncrypt() { }

	public ResEncrypt(ApiCodeEnum codeEnum, T data) {
		super(codeEnum);
		this.data = data;
	}

	public ResEncrypt(int code, String msg, T data) {
		super(code, msg);
		this.data = data;
	}

	public static <T> ResBase<T> success(T data) {
		return new ResEncrypt(ApiCodeEnum.API_OK, data);
	}

	public static <T> ResBase<T> define(ApiCodeEnum codeEnum, T data) {
		return new ResEncrypt(codeEnum, data);
	}

	public static <T> ResBase<T> define(int code, String msg, T data) {
		return new ResEncrypt(code, msg, data);
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
