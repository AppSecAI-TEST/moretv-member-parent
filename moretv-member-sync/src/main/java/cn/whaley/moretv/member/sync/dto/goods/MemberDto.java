package cn.whaley.moretv.member.sync.dto.goods;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MemberDto implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -8677398722744340822L;
    private Integer id;
    private Date createTime;
    private String createTimeStr;
    private String memberCode;
    private String memberName;
    private String memberStatus;
    private Integer operatorId;
    private Date publishTime;
    private String publishTimeStr;
    private String remark;
    private Date updateTime;
    private String updateTimeStr;
    private List<MemberMpr> mprList;
    private List<MemberCppr> cpprList;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public String getPublishTimeStr() {
        return publishTimeStr;
    }

    public void setPublishTimeStr(String publishTimeStr) {
        this.publishTimeStr = publishTimeStr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public List<MemberMpr> getMprList() {
        return mprList;
    }

    public void setMprList(List<MemberMpr> mprList) {
        this.mprList = mprList;
    }

    public List<MemberCppr> getCpprList() {
        return cpprList;
    }

    public void setCpprList(List<MemberCppr> cpprList) {
        this.cpprList = cpprList;
    }
    
}
