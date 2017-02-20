package cn.whaley.moretv.member.base.constant;


/**
 * Created by admin on 15/9/29.
 * ERROR CODE
 */

public interface ApiCodeInfo {

    int API_UNKNOWN = 0;
    int API_OK = 200;
    int API_SYS_ERR = 501;
    int API_PARAM_ERR = 502;
    int API_SYS_DATA_ERR = 505;
    int API_SIGN_ERR = 510;
    int API_DATA_NOT_EXIST = 404;

    int API_NOT_MODIFY = 311;
    int API_GOODS_NOT_EXIST = 314;
    int API_PAY_METHODE_NOT_EXIST = 315;
	int API_ORDER_NOT_EXIST = 316;
    int API_SN_ERR =503;
    int API_KGC_ERR = 504;
    int API_INTERNAL_AUTH_ERR = 506;
    int API_CP_ERR = 507;
    int API_CP_ACCOUNT_ERR = 508;
    int API_INTERNAL_TOKEN_ERR = 509;
    int API_ORDER_STATUS_ERR = 511;
	int API_ORDER_TIMEOUT = 512;
	int API_ORDER_FINISHED = 513;
	int API_TOKEN_INVALID = 514;
	int API_GET_QRCODE_FAIL = 515;
	int API_GET_QRCODE_SIGN_ERR = 516;
	int API_CREATE_ORDER_FAIL = 517;
	int API_CREATE_ORDER_SIGN_ERR = 518;
	int API_ENCRYPT_FAIL = 519;
	int API_DECRYPTO_FAIL = 520;
	int API_TEMP_SERVER_FAIL = 521;
	int API_ORDER_DELIVERYING = 522;
	int API_GET_PACKAGE_ERR= 523;
	int API_DURATION_ERR= 524;
	int API_TENCENT_CONFIRM_ORDER_ERR= 525;
	int API_UPGRADE_DATA_ERR= 526;
    int API_GOODSTYPE_ERR = 527;


    enum CodeEnum {

        API_UNKNOWN(ApiCodeInfo.API_UNKNOWN, "unknown"),
        API_OK(ApiCodeInfo.API_OK, "success"),
        API_SYS_ERR(ApiCodeInfo.API_SYS_ERR, "system error"),
        API_PARAM_ERR(ApiCodeInfo.API_PARAM_ERR, "params error"),
        API_SYS_DATA_ERR(ApiCodeInfo.API_SYS_DATA_ERR, "system data error"),
        API_SIGN_ERR(ApiCodeInfo.API_SIGN_ERR, "sign error"),
        API_DATA_NOT_EXIST(ApiCodeInfo.API_DATA_NOT_EXIST, "data not exist");

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }


        public String getMsg() {
            return msg;
        }

        CodeEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
