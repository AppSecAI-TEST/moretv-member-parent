package cn.whaley.moretv.member.model.member;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: MemberUserAuthority
* Table: member_user_authority
* Alias: mua
*
* This Model generated by MyBatis Generator Extend.
*/
public class MemberUserAuthority extends BaseModel<Integer> implements Serializable {
    /**
     * 电视猫用户名
     * username
     */
    private String username;

    /**
     * 会员模型编码
     * member_code
     */
    private String memberCode;

    /**
     * 会员模型名称
     * member_name
     */
    private String memberName;

    /**
     * delete：删除，valid：正常
     * status
     */
    private String status;

    /**
     * 权益开始时间
     * start_time
     */
    private Date startTime;

    /**
     * 权益结束时间
     * end_time
     */
    private Date endTime;

    /**
     * 权益持续月
     * duration_month
     */
    private Short durationMonth;

    /**
     * 权益持续天,[0-30]
     * duration_day
     */
    private Byte durationDay;

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
     * 电视猫用户名
     * username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 电视猫用户名
     * username
     *
     * @param username 电视猫用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 会员模型编码
     * member_code
     */
    public String getMemberCode() {
        return memberCode;
    }

    /**
     * 会员模型编码
     * member_code
     *
     * @param memberCode 会员模型编码
     */
    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    /**
     * 会员模型名称
     * member_name
     */
    public String getMemberName() {
        return memberName;
    }

    /**
     * 会员模型名称
     * member_name
     *
     * @param memberName 会员模型名称
     */
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * delete：删除，valid：正常
     * status
     */
    public String getStatus() {
        return status;
    }

    /**
     * delete：删除，valid：正常
     * status
     *
     * @param status delete：删除，valid：正常
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 权益开始时间
     * start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 权益开始时间
     * start_time
     *
     * @param startTime 权益开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 权益结束时间
     * end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 权益结束时间
     * end_time
     *
     * @param endTime 权益结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 权益持续月
     * duration_month
     */
    public Short getDurationMonth() {
        return durationMonth;
    }

    /**
     * 权益持续月
     * duration_month
     *
     * @param durationMonth 权益持续月
     */
    public void setDurationMonth(Short durationMonth) {
        this.durationMonth = durationMonth;
    }

    /**
     * 权益持续天,[0-30]
     * duration_day
     */
    public Byte getDurationDay() {
        return durationDay;
    }

    /**
     * 权益持续天,[0-30]
     * duration_day
     *
     * @param durationDay 权益持续天,[0-30]
     */
    public void setDurationDay(Byte durationDay) {
        this.durationDay = durationDay;
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
        sb.append(", username=").append(username);
        sb.append(", memberCode=").append(memberCode);
        sb.append(", memberName=").append(memberName);
        sb.append(", status=").append(status);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", durationMonth=").append(durationMonth);
        sb.append(", durationDay=").append(durationDay);
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
        MemberUserAuthority other = (MemberUserAuthority) that;
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