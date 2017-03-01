package cn.whaley.moretv.member.base.constant;

/**
 * Created by Bob Jiang on 2017/3/1.
 */
public enum ApiCodeEnum {

    API_UNKNOWN(ApiCodeInfo.API_UNKNOWN, "unknown"),
    API_OK(ApiCodeInfo.API_OK, "success"),
    API_SYS_ERR(ApiCodeInfo.API_SYS_ERR, "system error"),
    API_PARAM_ERR(ApiCodeInfo.API_PARAM_ERR, "params error"),
    API_PARAM_NULL(ApiCodeInfo.API_PARAM_NULL, "params is null"),
    API_DATA_ERR(ApiCodeInfo.API_DATA_ERR, "system data error"),
    API_SIGN_ERR(ApiCodeInfo.API_SIGN_ERR, "sign error"),
    API_DATA_NOT_EXIST(ApiCodeInfo.API_DATA_NOT_EXIST, "data not exist"),

    API_DATA_GOODS_STATUS_ERR(ApiCodeInfo.API_DATA_GOODS_STATUS_ERR, "goodsStatus error");
    ;

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

    ApiCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
