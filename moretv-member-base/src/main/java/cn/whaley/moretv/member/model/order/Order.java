package cn.whaley.moretv.member.model.order;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: Order
* Table: business_order
* Alias: bod
*
* This Model generated by MyBatis Generator Extend.
*/
public class Order extends BaseModel<Integer> implements Serializable {
    /**
     * 订单编号
     * order_code
     */
    private String orderCode;

    /**
     * 订单标题
     * order_title
     */
    private String orderTitle;

    /**
     * 订单描述
     * order_desc
     */
    private String orderDesc;

    /**
     * 账号ID
     * account_id
     */
    private Integer accountId;

    /**
     * 订单价格
     * total_price
     */
    private Integer totalPrice;

    /**
     * 实际价格
     * real_price
     */
    private Integer realPrice;

    /**
     * 支付金额
     * payment_amount
     */
    private Integer paymentAmount;

    /**
     * 支付类型，1：预设领取，2：兑换码兑换，3：支付开通，4：赠送，0：其他
     * business_type
     */
    private Integer businessType;

    /**
     * 支付渠道，weixin，alipay
     * pay_channel
     */
    private String payChannel;

    /**
     * 订单渠道，1：wap，2：tv，3：app，0：其他
     * order_channel
     */
    private Integer orderChannel;

    /**
     * 渠道Key
     * order_channel_key
     */
    private String orderChannelKey;

    /**
     * 是否自动续约，0：否，1：是
     * is_auto_renewal
     */
    private Integer isAutoRenewal;

    /**
     * 订单类型，1：购买，2：换货，3：活动，0：其他
     * order_type
     */
    private Integer orderType;

    /**
     * 订单有效状态，0：无效，1：有效
     * valid_status
     */
    private Integer validStatus;

    /**
     * 订单交易状态，1：待发货，2：待支付，3：交易完成，4：交易失败
     * trade_status
     */
    private Integer tradeStatus;

    /**
     * 订单支付状态，1：待支付，2：支付中，3：支付完成，4：支付超时
     * pay_status
     */
    private Integer payStatus;

    /**
     * 创建时间
     * create_time
     */
    private Date createTime;

    /**
     * 更新时间
     * update_time
     */
    private Date updateTime;

    /**
     * 超时时间
     * over_time
     */
    private Date overTime;

    /**
     * 订单外部编号
     * foreign_key
     */
    private String foreignKey;

    /**
     * 商品编号
     * goods_code
     */
    private String goodsCode;

    /**
     * 商品名称
     * goods_name
     */
    private String goodsName;

    /**
     * 商品类别
     * goods_type
     */
    private Integer goodsType;

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     * order_code
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 订单编号
     * order_code
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    /**
     * 订单标题
     * order_title
     */
    public String getOrderTitle() {
        return orderTitle;
    }

    /**
     * 订单标题
     * order_title
     *
     * @param orderTitle 订单标题
     */
    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    /**
     * 订单描述
     * order_desc
     */
    public String getOrderDesc() {
        return orderDesc;
    }

    /**
     * 订单描述
     * order_desc
     *
     * @param orderDesc 订单描述
     */
    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    /**
     * 账号ID
     * account_id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 账号ID
     * account_id
     *
     * @param accountId 账号ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 订单价格
     * total_price
     */
    public Integer getTotalPrice() {
        return totalPrice;
    }

    /**
     * 订单价格
     * total_price
     *
     * @param totalPrice 订单价格
     */
    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * 实际价格
     * real_price
     */
    public Integer getRealPrice() {
        return realPrice;
    }

    /**
     * 实际价格
     * real_price
     *
     * @param realPrice 实际价格
     */
    public void setRealPrice(Integer realPrice) {
        this.realPrice = realPrice;
    }

    /**
     * 支付金额
     * payment_amount
     */
    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 支付金额
     * payment_amount
     *
     * @param paymentAmount 支付金额
     */
    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    /**
     * 支付类型，1：预设领取，2：兑换码兑换，3：支付开通，4：赠送，0：其他
     * business_type
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * 支付类型，1：预设领取，2：兑换码兑换，3：支付开通，4：赠送，0：其他
     * business_type
     *
     * @param businessType 支付类型，1：预设领取，2：兑换码兑换，3：支付开通，4：赠送，0：其他
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * 支付渠道，weixin，alipay
     * pay_channel
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * 支付渠道，weixin，alipay
     * pay_channel
     *
     * @param payChannel 支付渠道，weixin，alipay
     */
    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    /**
     * 订单渠道，1：wap，2：tv，3：app，0：其他
     * order_channel
     */
    public Integer getOrderChannel() {
        return orderChannel;
    }

    /**
     * 订单渠道，1：wap，2：tv，3：app，0：其他
     * order_channel
     *
     * @param orderChannel 订单渠道，1：wap，2：tv，3：app，0：其他
     */
    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    /**
     * 渠道Key
     * order_channel_key
     */
    public String getOrderChannelKey() {
        return orderChannelKey;
    }

    /**
     * 渠道Key
     * order_channel_key
     *
     * @param orderChannelKey 渠道Key
     */
    public void setOrderChannelKey(String orderChannelKey) {
        this.orderChannelKey = orderChannelKey;
    }

    /**
     * 是否自动续约，0：否，1：是
     * is_auto_renewal
     */
    public Integer getIsAutoRenewal() {
        return isAutoRenewal;
    }

    /**
     * 是否自动续约，0：否，1：是
     * is_auto_renewal
     *
     * @param isAutoRenewal 是否自动续约，0：否，1：是
     */
    public void setIsAutoRenewal(Integer isAutoRenewal) {
        this.isAutoRenewal = isAutoRenewal;
    }

    /**
     * 订单类型，1：购买，2：换货，3：活动，0：其他
     * order_type
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 订单类型，1：购买，2：换货，3：活动，0：其他
     * order_type
     *
     * @param orderType 订单类型，1：购买，2：换货，3：活动，0：其他
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 订单有效状态，0：无效，1：有效
     * valid_status
     */
    public Integer getValidStatus() {
        return validStatus;
    }

    /**
     * 订单有效状态，0：无效，1：有效
     * valid_status
     *
     * @param validStatus 订单有效状态，0：无效，1：有效
     */
    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    /**
     * 订单交易状态，1：待发货，2：待支付，3：交易完成，4：交易失败
     * trade_status
     */
    public Integer getTradeStatus() {
        return tradeStatus;
    }

    /**
     * 订单交易状态，1：待发货，2：待支付，3：交易完成，4：交易失败
     * trade_status
     *
     * @param tradeStatus 订单交易状态，1：待发货，2：待支付，3：交易完成，4：交易失败
     */
    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    /**
     * 订单支付状态，1：待支付，2：支付中，3：支付完成，4：支付超时
     * pay_status
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    /**
     * 订单支付状态，1：待支付，2：支付中，3：支付完成，4：支付超时
     * pay_status
     *
     * @param payStatus 订单支付状态，1：待支付，2：支付中，3：支付完成，4：支付超时
     */
    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 创建时间
     * create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * create_time
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * update_time
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 超时时间
     * over_time
     */
    public Date getOverTime() {
        return overTime;
    }

    /**
     * 超时时间
     * over_time
     *
     * @param overTime 超时时间
     */
    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    /**
     * 订单外部编号
     * foreign_key
     */
    public String getForeignKey() {
        return foreignKey;
    }

    /**
     * 订单外部编号
     * foreign_key
     *
     * @param foreignKey 订单外部编号
     */
    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    /**
     * 商品编号
     * goods_code
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * 商品编号
     * goods_code
     *
     * @param goodsCode 商品编号
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * 商品名称
     * goods_name
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 商品名称
     * goods_name
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 商品类别
     * goods_type
     */
    public Integer getGoodsType() {
        return goodsType;
    }

    /**
     * 商品类别
     * goods_type
     *
     * @param goodsType 商品类别
     */
    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", orderTitle=").append(orderTitle);
        sb.append(", orderDesc=").append(orderDesc);
        sb.append(", accountId=").append(accountId);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", realPrice=").append(realPrice);
        sb.append(", paymentAmount=").append(paymentAmount);
        sb.append(", businessType=").append(businessType);
        sb.append(", payChannel=").append(payChannel);
        sb.append(", orderChannel=").append(orderChannel);
        sb.append(", orderChannelKey=").append(orderChannelKey);
        sb.append(", isAutoRenewal=").append(isAutoRenewal);
        sb.append(", orderType=").append(orderType);
        sb.append(", validStatus=").append(validStatus);
        sb.append(", tradeStatus=").append(tradeStatus);
        sb.append(", payStatus=").append(payStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", overTime=").append(overTime);
        sb.append(", foreignKey=").append(foreignKey);
        sb.append(", goodsCode=").append(goodsCode);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsType=").append(goodsType);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Order other = (Order) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}