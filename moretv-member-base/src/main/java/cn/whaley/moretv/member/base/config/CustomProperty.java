package cn.whaley.moretv.member.base.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Bob Jiang on 2017/4/12.
 */
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomProperty {

    // 腾讯相关配置
    private Tencent tencent;

    // 长连接配置
    private LongConnection longConnection;

    //apps加密
    private String appsEncryptUrl;

    //apps解密
    private String appsDecryptoUrl;

    // 支付网关
    private String payGatewayServer;

    // 本地服务地址
    private String localHostServer;

    // 临时服务地址
    private String tempPayDispatchUrl;

    //MSD地址
    private String msdServer;

    public static class Tencent {

        private String server;       //腾讯接口地址
        private String appId;        //腾讯AppId
        private String appKey;       //腾讯AppKey

        public String getServer() {
            return server;
        }

        public void setServer(String server) {
            this.server = server;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }
    }

    // 长连接配置
    public static class LongConnection {

        private String appKey;          //公钥
        private String appSecret;       //秘钥
        private String businessType;    //业务类型
        private String url;             //地址

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getAppSecret() {
            return appSecret;
        }

        public void setAppSecret(String appSecret) {
            this.appSecret = appSecret;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public Tencent getTencent() {
        return tencent;
    }

    public void setTencent(Tencent tencent) {
        this.tencent = tencent;
    }

    public LongConnection getLongConnection() {
        return longConnection;
    }

    public void setLongConnection(LongConnection longConnection) {
        this.longConnection = longConnection;
    }

    public String getAppsEncryptUrl() {
        return appsEncryptUrl;
    }

    public void setAppsEncryptUrl(String appsEncryptUrl) {
        this.appsEncryptUrl = appsEncryptUrl;
    }

    public String getAppsDecryptoUrl() {
        return appsDecryptoUrl;
    }

    public void setAppsDecryptoUrl(String appsDecryptoUrl) {
        this.appsDecryptoUrl = appsDecryptoUrl;
    }

    public String getMsdServer() {
        return msdServer;
    }

    public void setMsdServer(String msdServer) {
        this.msdServer = msdServer;
    }

    public String getPayGatewayServer() {
        return payGatewayServer;
    }

    public void setPayGatewayServer(String payGatewayServer) {
        this.payGatewayServer = payGatewayServer;
    }

    public String getLocalHostServer() {
        return localHostServer;
    }

    public void setLocalHostServer(String localHostServer) {
        this.localHostServer = localHostServer;
    }

    public String getTempPayDispatchUrl() {
        return tempPayDispatchUrl;
    }

    public void setTempPayDispatchUrl(String tempPayDispatchUrl) {
        this.tempPayDispatchUrl = tempPayDispatchUrl;
    }
}
