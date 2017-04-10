package cn.whaley.moretv.member.base.constant;

/**
 * Created by Bob Jiang on 2017/3/1.
 */
public enum ApiCodeEnum {

    API_UNKNOWN(ApiCodeInfo.API_UNKNOWN, "unknown"),
    API_OK(ApiCodeInfo.API_OK, "success"),
    API_BAD_REQUEST(ApiCodeInfo.API_BAD_REQUEST, "bad Request"),
    API_NOT_FOUND(ApiCodeInfo.API_NOT_FOUND, "api not found"),
    API_ERROR(ApiCodeInfo.API_ERROR, "api error"),

    API_SYS_ERR(ApiCodeInfo.API_SYS_ERR, "system error"),
    API_PARAM_ERR(ApiCodeInfo.API_PARAM_ERR, "params error"),
    API_PARAM_NULL(ApiCodeInfo.API_PARAM_NULL, "params is null"),
    API_DATA_ERR(ApiCodeInfo.API_DATA_ERR, "system data error"),
    API_SIGN_ERR(ApiCodeInfo.API_SIGN_ERR, "sign error"),
    API_DATA_NOT_EXIST(ApiCodeInfo.API_DATA_NOT_EXIST, "data not exist"),

    API_DATA_GOODS_STATUS_ERR(ApiCodeInfo.API_DATA_GOODS_STATUS_ERR, "goodsStatus error"),
    API_PARAM_ACCOUNT_ID_NULL(ApiCodeInfo.API_PARAM_ACCOUNT_ID_NULL, "accountId is null"),
    API_PARAM_GOODS_TAG_ID_NULL(ApiCodeInfo.API_PARAM_GOODS_TAG_ID_NULL, "goodsTag is null"),
    API_DATA_GOODS_NOT_ONLINE(ApiCodeInfo.API_DATA_GOODS_NOT_ONLINE, "goods not online"),
    API_DATA_GOODS_CAN_NOT_BUY(ApiCodeInfo.API_DATA_GOODS_CAN_NOT_BUY, "goods can not buy"),
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
