package cn.whaley.moretv.member.base.dto.pay;

import java.io.Serializable;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;

public class PayRequest extends BaseRequest implements Serializable {

    private static final long serialVersionUID = 799228014905820287L;

    /**
     * 会话级别标识，由安全中心获取
     */
    private String sessionToken;
    
    /**
     * 客户端ip
     */
    private String cip;
    
    /**
     * 商品编码
     */
    private String goodsCode;
    
    /**
     * 订单名称
     */
    private String subject;
    
    /**
     * 是否自动续费
     */
    private String payAutoRenew;
    
    /**
     * 支付类型
     */
    private String payType;
    
    /**
     * 订单号
     */
    private String orderCode;
    
    /**
     * 支付价格（分）
     */
    private Long free;
    
    /**
     * 超时时间
     */
    private Long expireTime;
    
    /**
     * 数据签名
     */
    private String sign;

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getCip() {
        return cip;
    }

    public void setCip(String cip) {
        this.cip = cip;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getPayAutoRenew() {
        return payAutoRenew;
    }

    public void setPayAutoRenew(String payAutoRenew) {
        this.payAutoRenew = payAutoRenew;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getFree() {
        return free;
    }

    public void setFree(Long free) {
        this.free = free;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "PayRequest [sessionToken=" + sessionToken + ", cip=" + cip + ", goodsCode=" + goodsCode + ", subject="
                + subject + ", payAutoRenew=" + payAutoRenew + ", payType=" + payType + ", orderCode=" + orderCode
                + ", free=" + free + ", expireTime=" + expireTime + ", sign=" + sign + "]";
    }
}
