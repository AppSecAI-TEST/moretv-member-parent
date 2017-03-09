package cn.whaley.moretv.member.sync.dto.goods;

import java.io.Serializable;
import java.util.Date;

public class ProductDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5672475167141446676L;

    private Integer id;
    private Integer operatorId;
    private Date createTime;
    private Date publishTime;
    private Date updateTime;
    private String memberCode;
    private String memberName;
    private String packageCode;
    private String packageName;
    private String productCode;
    private String productName;
    private String programCode;
    private String programName;
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
    public String getProgramCode() {
        return programCode;
    }
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }
    public String getProgramName() {
        return programName;
    }
    public void setProgramName(String programName) {
        this.programName = programName;
    }
    public String getRelationStatus() {
        return relationStatus;
    }
    public void setRelationStatus(String relationStatus) {
        this.relationStatus = relationStatus;
    }
    
    
}
