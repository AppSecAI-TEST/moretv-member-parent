package cn.whaley.moretv.member.base.res;

import cn.whaley.moretv.member.base.constant.ApiCodeInfo;

/**
 * Created by admin on 15/9/23.
 * 数据返回对象基类
 */
public class ResBase {

    public ResBase() {
        this.code = ApiCodeInfo.CodeEnum.API_UNKNOWN.getCode();
        this.msg = ApiCodeInfo.CodeEnum.API_UNKNOWN.getMsg();
    }

    protected int code;

    protected String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
