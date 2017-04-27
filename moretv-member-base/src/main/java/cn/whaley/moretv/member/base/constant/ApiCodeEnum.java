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

    API_SECURITY_ERR_PROGRAM_VIP(ApiCodeInfo.API_SECURITY_ERR_PROGRAM_VIP, "authentication error, program is vip"),
    API_SECURITY_ERR_MEMBER_NO_AUTH(ApiCodeInfo.API_SECURITY_ERR_MEMBER_NO_AUTH, "authentication error, account is not a member"),

    API_TENCENT_ACCESS_TOKEN_ERR(ApiCodeInfo.API_TENCENT_ACCESS_TOKEN_ERR, "tencent access token error"),
    API_TENCENT_REQUEST_ERR(ApiCodeInfo.API_TENCENT_REQUEST_ERR, "tencent request error"),
    API_TENCENT_RESULT_ERR(ApiCodeInfo.API_TENCENT_RESULT_ERR, "tencent result error"),
    API_MSD_ACCESS_TOKEN_ERR(ApiCodeInfo.API_MSD_ACCESS_TOKEN_ERR, "msd access token error"),

    API_DATA_GOODS_STATUS_ERR(ApiCodeInfo.API_DATA_GOODS_STATUS_ERR, "goodsStatus error"),
    API_PARAM_ACCOUNT_ID_NULL(ApiCodeInfo.API_PARAM_ACCOUNT_ID_NULL, "accountId is null"),
    API_PARAM_GOODS_TAG_ID_NULL(ApiCodeInfo.API_PARAM_GOODS_TAG_ID_NULL, "goodsTag is null"),
    API_PARAM_MEMBER_CODE_ID_NULL(ApiCodeInfo.API_PARAM_MEMBER_CODE_ID_NULL, "memberCode is null"),
    API_DATA_GOODS_NOT_ONLINE(ApiCodeInfo.API_DATA_GOODS_NOT_ONLINE, "goods not online"),
    API_DATA_GOODS_CAN_NOT_BUY(ApiCodeInfo.API_DATA_GOODS_CAN_NOT_BUY, "goods can not buy"),
    API_PARAM_CP_NULL(ApiCodeInfo.API_PARAM_CP_NULL, "param cp is null"),
    API_PARAM_VIDEOINFO_NULL(ApiCodeInfo.API_PARAM_VIDEOINFO_NULL, "param videoinfo is null"),
    
    API_DATA_ORDER_STATUS_ERR(ApiCodeInfo.API_DATA_ORDER_STATUS_ERR, "orderStatus error"),
    
    API_DATA_PAY_GATEWAY_ERR(ApiCodeInfo.API_DATA_PAY_GATEWAY_ERR, "pay_gateway error"),
    API_PARAM_PAY_NOTIFY_STATUS_ERR(ApiCodeInfo.API_PARAM_PAY_NOTIFY_STATUS_ERR, "pay notify status error"),
    API_PARAM_PAY_FEE_ERR(ApiCodeInfo.API_PARAM_PAY_FEE_ERR, "pay fee error"),
    API_DATA_MEMBER_AUTH_NOT_EXIST(ApiCodeInfo.API_DATA_MEMBER_AUTH_NOT_EXIST, "member not exist"),
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
