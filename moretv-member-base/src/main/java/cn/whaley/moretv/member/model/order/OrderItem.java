package cn.whaley.moretv.member.model.order;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: OrderItem
* Table: business_order_item
* Alias: boi
*
* This Model generated by MyBatis Generator Extend.
*/
public class OrderItem extends BaseModel<Integer> implements Serializable {
    /**
     * 订单编号
     * order_code
     */
    private String orderCode;

    /**
     * 会员编号
     * member_code
     */
    private String memberCode;

    /**
     * 会员名称
     * member_name
     */
    private String memberName;

    /**
     * 数量
     * amount
     */
    private Integer amount;

    /**
     * 单价
     * unit_price
     */
    private Integer unitPrice;

    /**
     * 实际价格
     * real_price
     */
    private Integer realPrice;

    /**
     * 订单有效状态，0：无效，1：有效
     * valid_status
     */
    private Integer validStatus;

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
     * 会员编号
     * member_code
     */
    public String getMemberCode() {
        return memberCode;
    }

    /**
     * 会员编号
     * member_code
     *
     * @param memberCode 会员编号
     */
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    /**
     * 会员名称
     * member_name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * 会员名称
     * member_name
     *
     * @param memberName 会员名称
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * 数量
     * amount
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * 数量
     * amount
     *
     * @param amount 数量
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * 单价
     * unit_price
     */
    public Integer getUnitPrice() {
        return unitPrice;
    }

    /**
     * 单价
     * unit_price
     *
     * @param unitPrice 单价
     */
    public void setUnitPrice(Integer unitPrice) {
        this.unitPrice = unitPrice;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", memberCode=").append(memberCode);
        sb.append(", memberName=").append(memberName);
        sb.append(", amount=").append(amount);
        sb.append(", unitPrice=").append(unitPrice);
        sb.append(", realPrice=").append(realPrice);
        sb.append(", validStatus=").append(validStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
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
        OrderItem other = (OrderItem) that;
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