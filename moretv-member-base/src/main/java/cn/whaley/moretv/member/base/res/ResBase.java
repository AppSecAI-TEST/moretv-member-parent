package cn.whaley.moretv.member.base.res;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;

/**
 * Created by admin on 15/9/23.
 * 数据返回对象基类
 */
public class ResBase<T> {

    protected int code;
    protected String msg;

    public ResBase() {
        this(ApiCodeEnum.API_UNKNOWN);
    }

    public ResBase(ApiCodeEnum apiCodeEnum) {
        this.code = apiCodeEnum.getCode();
        this.msg = apiCodeEnum.getMsg();
    }

    public ResBase(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResBase success() {
        return new ResBase(ApiCodeEnum.API_OK);
    }

    public static ResBase failed() {
        return new ResBase(ApiCodeEnum.API_SYS_ERR);
    }

    public static ResBase failed(String msg) {
        return new ResBase(ApiCodeEnum.API_SYS_ERR.getCode(), msg);
    }

    public static ResBase failed(ApiCodeEnum codeEnum) {
        return new ResBase(codeEnum);
    }

    public static ResBase define(ApiCodeEnum codeEnum) {
        return new ResBase(codeEnum);
    }

    public static ResBase define(int code, String msg) {
        return new ResBase(code, msg);
    }

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

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
