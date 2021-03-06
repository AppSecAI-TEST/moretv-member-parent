package cn.whaley.moretv.member.base.dto.pay.gateway;

import java.io.Serializable;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;

public class PayGatewayRequest extends BaseRequest implements Serializable {

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
    private Integer payAutoRenew;
    
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
    private Integer fee;
    
    /**
     * 超时时间(分钟)
     */
    private Integer expireTime;
    
    /**
     * MD5验证，由创建订单的时候生成
     */
    private String sign;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 微信的openId
     */
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

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

    public Integer getPayAutoRenew() {
        return payAutoRenew;
    }

    public void setPayAutoRenew(Integer payAutoRenew) {
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

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return super.toString() + "PayGatewayRequest [sessionToken=" + sessionToken + ", cip=" + cip + ", goodsCode=" + goodsCode
                + ", subject=" + subject + ", payAutoRenew=" + payAutoRenew + ", payType=" + payType + ", orderCode="
                + orderCode + ", fee=" + fee + ", expireTime=" + expireTime + ", sign=" + sign + ", createTime="
                + createTime + ", openId=" + openId + "]";
    }
    
}
