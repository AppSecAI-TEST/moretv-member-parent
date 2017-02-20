package cn.whaley.moretv.member.base.dto;

import java.util.Date;

/**
 * @author linfeng 2016年3月24日
 * dto基类
 */
public class DtoBase {

    public int id;

    public Date createTime;

    public Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
