package cn.whaley.moretv.member.model.order;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: DeliveredOrder
* Table: delivered_order
* Alias: dlvo
*
* This Model generated by MyBatis Generator Extend.
*/
public class DeliveredOrder extends BaseModel<Integer> implements Serializable {
    /**
     * item的唯一标识
     * order_item_code
     */
    private String orderItemCode;

    /**
     * 账号ID
     * account_id
     */
    private String accountId;

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
     * 时长_月
     * duration_month
     */
    private Integer durationMonth;

    /**
     * 时长_日
     * duration_day
     */
    private Integer durationDay;

    /**
     * 发货订单创建时间
     * create_time
     */
    private Date createTime;

    /**
     * 订单开始时间
     * start_time
     */
    private Date startTime;

    /**
     * 订单结束时间
     * end_time
     */
    private Date endTime;

    /**
     * 失效时间
     * invalid_time
     */
    private Date invalidTime;

    /**
     * 有效时间
     * valid_time
     */
    private Date validTime;

    /**
     * 1->有效；2->失效
     * status
     */
    private Integer status;

    /**
     * 最后操作人员ID
     * operator_id
     */
    private Integer operatorId;

    /**
     * 发货订单更新时间
     * update_time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /**
     * item的唯一标识
     * order_item_code
     */
    public String getOrderItemCode() {
        return orderItemCode;
    }

    /**
     * item的唯一标识
     * order_item_code
     *
     * @param orderItemCode item的唯一标识
     */
    public void setOrderItemCode(String orderItemCode) {
        this.orderItemCode = orderItemCode;
    }

    /**
     * 账号ID
     * account_id
     */
    public String getAccountId() {
        return accountId;
    }

    /**
     * 账号ID
     * account_id
     *
     * @param accountId 账号ID
     */
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

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
     * 时长_月
     * duration_month
     */
    public Integer getDurationMonth() {
        return durationMonth;
    }

    /**
     * 时长_月
     * duration_month
     *
     * @param durationMonth 时长_月
     */
    public void setDurationMonth(Integer durationMonth) {
        this.durationMonth = durationMonth;
    }

    /**
     * 时长_日
     * duration_day
     */
    public Integer getDurationDay() {
        return durationDay;
    }

    /**
     * 时长_日
     * duration_day
     *
     * @param durationDay 时长_日
     */
    public void setDurationDay(Integer durationDay) {
        this.durationDay = durationDay;
    }

    /**
     * 发货订单创建时间
     * create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 发货订单创建时间
     * create_time
     *
     * @param createTime 发货订单创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 订单开始时间
     * start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 订单开始时间
     * start_time
     *
     * @param startTime 订单开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 订单结束时间
     * end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 订单结束时间
     * end_time
     *
     * @param endTime 订单结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 失效时间
     * invalid_time
     */
    public Date getInvalidTime() {
        return invalidTime;
    }

    /**
     * 失效时间
     * invalid_time
     *
     * @param invalidTime 失效时间
     */
    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }

    /**
     * 有效时间
     * valid_time
     */
    public Date getValidTime() {
        return validTime;
    }

    /**
     * 有效时间
     * valid_time
     *
     * @param validTime 有效时间
     */
    public void setValidTime(Date validTime) {
        this.validTime = validTime;
    }

    /**
     * 1->有效；2->失效
     * status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 1->有效；2->失效
     * status
     *
     * @param status 1->有效；2->失效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 最后操作人员ID
     * operator_id
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * 最后操作人员ID
     * operator_id
     *
     * @param operatorId 最后操作人员ID
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * 发货订单更新时间
     * update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 发货订单更新时间
     * update_time
     *
     * @param updateTime 发货订单更新时间
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
        sb.append(", orderItemCode=").append(orderItemCode);
        sb.append(", accountId=").append(accountId);
        sb.append(", orderCode=").append(orderCode);
        sb.append(", memberCode=").append(memberCode);
        sb.append(", memberName=").append(memberName);
        sb.append(", durationMonth=").append(durationMonth);
        sb.append(", durationDay=").append(durationDay);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", invalidTime=").append(invalidTime);
        sb.append(", validTime=").append(validTime);
        sb.append(", status=").append(status);
        sb.append(", operatorId=").append(operatorId);
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
        DeliveredOrder other = (DeliveredOrder) that;
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