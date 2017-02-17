package cn.whaley.member.base.res;

import cn.whaley.member.base.constant.GlobleConstant;

/**
 * Created by admin on 15/9/23.
 * 数据返回对象基类
 */
public class ResBase {

    public ResBase() {
        this.status = 0;
        this.msg = GlobleConstant.MSG_UNKNOWN;
    }

    protected int status;

    protected String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
