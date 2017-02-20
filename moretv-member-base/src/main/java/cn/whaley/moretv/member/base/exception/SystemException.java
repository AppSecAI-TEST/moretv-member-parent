package cn.whaley.moretv.member.base.exception;

import cn.whaley.moretv.member.base.constant.ApiCodeInfo;

/**
 * 系统业务异常 throw new SystemException("XXXX")
 * @author lanyuan
 * @date 2014-12-10
 * @Email：mmm333zzz520@163.com
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -73567508601044861L;

	private String code;

	public SystemException() {}

	public SystemException(String code, String message) {
		super(message);
		this.code = code;
	}

	public SystemException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public SystemException(ApiCodeInfo.CodeEnum codeEnum) {
		super(codeEnum.getMsg());
		this.code = String.valueOf(codeEnum.getCode());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
