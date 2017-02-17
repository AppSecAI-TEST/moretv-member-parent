package cn.whaley.member.base.exception;

import cn.whaley.member.base.res.ResBase;

/**
 * 创建订单失败异常，用来回滚事务
 * 
 * @author tangzc 2016年4月1日
 * 
 */
public class UpdateServerException extends SystemException {

	private static final long serialVersionUID = -5475178649532352502L;

	private ResBase resBase;

	public ResBase getResBase() {
		return resBase;
	}

	public void setResBase(ResBase resBase) {
		this.resBase = resBase;
	}

}
