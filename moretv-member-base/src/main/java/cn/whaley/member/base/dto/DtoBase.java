package cn.whaley.member.base.dto;

import java.util.Date;

/**
 * @author linfeng 2016年3月24日
 * dto基类
 */
public class DtoBase {

    public int id;

    public Date create_time;

    public Date update_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
    
}
