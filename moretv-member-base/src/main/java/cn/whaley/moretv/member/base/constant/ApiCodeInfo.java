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

    //business
    int API_DATA_GOODS_STATUS_ERR = 510;
    int API_PARAM_ACCOUNT_ID_NULL = 511;
    int API_PARAM_GOODS_TAG_ID_NULL = 512;

}
