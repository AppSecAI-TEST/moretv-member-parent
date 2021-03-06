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
     * 账号ID
     * account_id
     */
    private String accountId;

    /**
     * 电视猫账号昵称
     * account_name
     */
    private String accountName;

    /**
     * 会员编码
     * member_code
     */
    private String memberCode;

    /**
     * 会员名称
     * member_name
     */
    private String memberName;

    /**
     * 开始时间
     * start_time
     */
    private Date startTime;

    /**
     * 有效时间
     * effective_time
     */
    private Date effectiveTime;

    /**
     * 有效状态，1：有效，0：无效
     * status
     */
    private Integer status;

    /**
     * 创建时间
     * create_time
     */
    private Date createTime;

    /**
     * 修改时间
     * update_time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

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
     * 电视猫账号昵称
     * account_name
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 电视猫账号昵称
     * account_name
     *
     * @param accountName 电视猫账号昵称
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 会员编码
     * member_code
     */
    public String getMemberCode() {
        return memberCode;
    }

    /**
     * 会员编码
     * member_code
     *
     * @param memberCode 会员编码
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
     * 开始时间
     * start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 开始时间
     * start_time
     *
     * @param startTime 开始时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 有效时间
     * effective_time
     */
    public Date getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * 有效时间
     * effective_time
     *
     * @param effectiveTime 有效时间
     */
    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    /**
     * 有效状态，1：有效，0：无效
     * status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 有效状态，1：有效，0：无效
     * status
     *
     * @param status 有效状态，1：有效，0：无效
     */
    public void setStatus(Integer status) {
        this.status = status;
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
     * 修改时间
     * update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 修改时间
     * update_time
     *
     * @param updateTime 修改时间
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
        sb.append(", accountId=").append(accountId);
        sb.append(", accountName=").append(accountName);
        sb.append(", memberCode=").append(memberCode);
        sb.append(", memberName=").append(memberName);
        sb.append(", startTime=").append(startTime);
        sb.append(", effectiveTime=").append(effectiveTime);
        sb.append(", status=").append(status);
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