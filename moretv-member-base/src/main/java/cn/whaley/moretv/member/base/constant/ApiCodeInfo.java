package cn.whaley.moretv.member.base.constant;


/**
 * Created by admin on 15/9/29.
 * ERROR CODE
 */

public interface ApiCodeInfo {

    //system
    int API_UNKNOWN = 0;
    int API_OK = 200;
    int API_BAD_REQUEST = 400;
    int API_NOT_FOUND = 404;
    int API_ERROR = 500;

    int API_DATA_NOT_EXIST = 204;

    int API_SYS_ERR = 501;
    int API_PARAM_ERR = 502;
    int API_PARAM_NULL = 503;
    int API_DATA_ERR = 504;
    int API_SIGN_ERR = 505;

    int API_SECURITY_ERR_PROGRAM_VIP = 506;
    int API_SECURITY_ERR_MEMBER_NO_AUTH = 507;

    //business
    int API_DATA_GOODS_STATUS_ERR = 510;
    int API_PARAM_ACCOUNT_ID_NULL = 511;
    int API_PARAM_GOODS_TAG_ID_NULL = 512;
    int API_DATA_GOODS_NOT_ONLINE = 513;
    int API_DATA_GOODS_CAN_NOT_BUY = 514;
    int API_PARAM_MEMBER_CODE_ID_NULL = 515;
    int API_PARAM_CP_NULL = 516;
    int API_PARAM_VIDEOINFO_NULL = 517;
    int API_DATA_ORDER_STATUS_ERR = 520;
    int API_DATA_MEMBER_AUTH_NOT_EXIST = 530;

    int API_TENCENT_ACCESS_TOKEN_ERR = 550;
    int API_TENCENT_REQUEST_ERR = 551;
    int API_TENCENT_RESULT_ERR = 552;
    int API_MSD_ACCESS_TOKEN_ERR = 553;


    int API_DATA_PAY_GATEWAY_ERR = 601;
    int API_PARAM_PAY_NOTIFY_STATUS_ERR = 602;


}
