package cn.whaley.moretv.member.base.dto.request;

import java.io.Serializable;

/**
 * 通用接口参数
 *
 * Created by Bob Jiang on 2017/3/21.
 */
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = 8460607853038288959L;

    private String deviceSerial;
    private String clientType;
    private String accessToken;
    private Long timestamp;
    private String appVersion;
    private Integer accountId;

    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @Override
    public String toString() {
        return "{" +
                "deviceSerial='" + deviceSerial + '\'' +
                ", clientType='" + clientType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", timestamp=" + timestamp +
                ", appVersion='" + appVersion + '\'' +
                ", accountId='" + accountId + '\'' +
                '}';
    }
}
