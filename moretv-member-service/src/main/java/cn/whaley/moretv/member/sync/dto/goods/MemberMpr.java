package cn.whaley.moretv.member.sync.dto.goods;

import java.io.Serializable;
import java.util.Date;

public class MemberMpr implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 2184704285917989398L;
    private Integer id;
    private Integer operatorId;
    private String memberCode;
    private String memberName;
    private String productCode;
    private String productName;
    private String relationStatus;
    private Date createTime;
    private Date updateTime;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOperatorId() {
        return operatorId;
    }
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
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
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getRelationStatus() {
        return relationStatus;
    }
    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

