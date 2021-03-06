package cn.whaley.moretv.member.model.member;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: Member
* Table: member
* Alias: mem
*
* This Model generated by MyBatis Generator Extend.
*/
public class Member extends BaseModel<Integer> implements Serializable {
    /**
     * 会员模型编码
     * code
     */
    private String code;

    /**
     * 会员模型名称
     * name
     */
    private String name;

    /**
     * 备注
     * remark
     */
    private String remark;

    /**
     * 会员模型绑定的片库(逗号隔开)
     * product_code
     */
    private String productCode;

    /**
     * delete：删除，valid：正常,published:已发布
     * status
     */
    private String status;

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
     * 会员模型编码
     * code
     */
    public String getCode() {
        return code;
    }

    /**
     * 会员模型编码
     * code
     *
     * @param code 会员模型编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 会员模型名称
     * name
     */
    public String getName() {
        return name;
    }

    /**
     * 会员模型名称
     * name
     *
     * @param name 会员模型名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 备注
     * remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
     * remark
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 会员模型绑定的片库(逗号隔开)
     * product_code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 会员模型绑定的片库(逗号隔开)
     * product_code
     *
     * @param productCode 会员模型绑定的片库(逗号隔开)
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * delete：删除，valid：正常,published:已发布
     * status
     */
    public String getStatus() {
        return status;
    }

    /**
     * delete：删除，valid：正常,published:已发布
     * status
     *
     * @param status delete：删除，valid：正常,published:已发布
     */
    public void setStatus(String status) {
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
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", remark=").append(remark);
        sb.append(", productCode=").append(productCode);
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
        Member other = (Member) that;
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