package cn.whaley.moretv.member.sync.dto.goods;

import java.io.Serializable;
import java.util.Date;

public class MemberCppr implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 3703121469491363381L;
    private Integer id;
    private Integer operatorId;
    private Date createTime;
    private Date publishTime;
    private Date updateTime;
    private String packageCode;
    private String packageName;
    private String productCode;
    private String productName;
    private String programSourceExternalCode;
    private String programSourceOriginalType;
    private String programSourceLabel;
    private String programSourceCode;
    
    
    public String getProgramSourceLabel() {
        return programSourceLabel;
    }
    public void setProgramSourceLabel(String programSourceLabel) {
        this.programSourceLabel = programSourceLabel;
    }
    public String getProgramSourceCode() {
        return programSourceCode;
    }
    public void setProgramSourceCode(String programSourceCode) {
        this.programSourceCode = programSourceCode;
    }
    private String relationStatus;
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
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getPublishTime() {
        return publishTime;
    }
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
    public String getProgramSourceExternalCode() {
        return programSourceExternalCode;
    }
    public void setProgramSourceExternalCode(String programSourceExternalCode) {
        this.programSourceExternalCode = programSourceExternalCode;
    }
    public String getProgramSourceOriginalType() {
        return programSourceOriginalType;
    }
    public void setProgramSourceOriginalType(String programSourceOriginalType) {
        this.programSourceOriginalType = programSourceOriginalType;
    }
    public String getRelationStatus() {
        return relationStatus;
    }
    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }
}
