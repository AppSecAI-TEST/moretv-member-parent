package cn.whaley.moretv.member.base.res;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;

import java.util.HashMap;

/**
 * Created by Bob Jiang on 2017/3/21.
 */
public class ResultResponse<V> extends HashMap<String, Object> {

    private static final long serialVersionUID = -2746096075868445005L;

    protected static final String CODE = "code";
    protected static final String MSG = "msg";
    protected static final String DATA = "data";
    protected String dataKey;

    public ResultResponse(ApiCodeEnum apiCodeEnum) {
        put(CODE, apiCodeEnum.getCode());
        put(MSG, apiCodeEnum.getMsg());
    }

    public ResultResponse() {
        this(ApiCodeEnum.API_UNKNOWN);
    }

    public ResultResponse(ApiCodeEnum apiCodeEnum, String dataKey, V data) {
        this(apiCodeEnum);
        put(dataKey, data);
        this.dataKey = dataKey;
    }

    public static ResultResponse success() {
        return new ResultResponse(ApiCodeEnum.API_OK);
    }

    public static <V> ResultResponse<V> success(V data) {
        return success(DATA, data);
    }

    public static <V> ResultResponse<V> success(String key, V data) {
        return new ResultResponse(ApiCodeEnum.API_OK, key, data);
    }

    public static ResultResponse failed() {
        return new ResultResponse(ApiCodeEnum.API_SYS_ERR);
    }

    public static ResultResponse define(ApiCodeEnum apiCodeEnum) {
        return new ResultResponse(apiCodeEnum);
    }

    public static <V> ResultResponse<V> define(ApiCodeEnum apiCodeEnum, String key, V data) {
        return new ResultResponse(apiCodeEnum, key, data);
    }

    public static <V> ResultResponse<V> define(ApiCodeEnum apiCodeEnum, V data) {
        return define(apiCodeEnum, DATA, data);
    }

    public int getCode() {
        return Integer.valueOf(get(CODE).toString()).intValue();
    }

    public String getMsg() {
        return get(MSG).toString();
    }

    public V getData() {
        return (V) get(dataKey);
    }

    public boolean isSuccess() {
        return ApiCodeEnum.API_OK.getCode() == getCode();
    }

    @Override
    public String toString() {
        StringBuilder value = new StringBuilder();
        value.append("{")
                .append("code=").append(getCode())
                .append(", msg='").append(getMsg()).append('\'');

        if (dataKey != null) {
            value.append(", ").append(dataKey).append("='").append(getData()).append('\'');
        }
        value.append('}');
        return value.toString();
    }

}
