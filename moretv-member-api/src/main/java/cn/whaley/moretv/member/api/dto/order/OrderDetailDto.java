package cn.whaley.moretv.member.api.dto.order;

/**
 * 返回订单详情接口
 */
public class OrderDetailDto {
    /**
     * 购买时间戳
     */
    private Long purchaseTime;
    
    /**
     * 订单号
     */
    private String orderCode;
    
    /**
     * 商品名称
     */
    private String goodsName;
    
    /**
     * 商品编号
     */
    private String goodsCode;

    /**
     * 支付方式编号
     * alipay, wechat pay
     */
    private String payMethodCode;
    
    /**
     * 支付方式名称
     * 支付宝, 微信
     */
    private String payMethodName;
    
    /**
     * 价格（分）
     */
    private int price;
    
    /**
     * 支付状态
     */
    private String payStatus;

    public Long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getPayMethodCode() {
        return payMethodCode;
    }

    public void setPayMethodCode(String payMethodCode) {
        this.payMethodCode = payMethodCode;
    }

    public String getPayMethodName() {
        return payMethodName;
    }

    public void setPayMethodName(String payMethodName) {
        this.payMethodName = payMethodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public String toString() {
        return "OrderDetailDto [purchaseTime=" + purchaseTime + ", orderCode=" + orderCode + ", goodsName=" + goodsName
                + ", goodsCode=" + goodsCode + ", payMethodCode=" + payMethodCode + ", payMethodName=" + payMethodName
                + ", price=" + price + ", payStatus=" + payStatus + "]";
    }
    
}
