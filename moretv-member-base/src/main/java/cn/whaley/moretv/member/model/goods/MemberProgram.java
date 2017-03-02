package cn.whaley.moretv.member.model.goods;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: MemberProgram
* Table: member_program
* Alias: pro
*
* This Model generated by MyBatis Generator Extend.
*/
public class MemberProgram extends BaseModel<Integer> implements Serializable {
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
     * 节目sid
     * program_code
     */
    private String programCode;

    /**
     * 节目标题
     * title
     */
    private String title;

    /**
     * deleted：删除，unbound：解绑，bound：绑定
     * status
     */
    private String status;

    /**
     * 当前节目所属片库
     * product_code
     */
    private String productCode;

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
     * 节目sid
     * program_code
     */
    public String getProgramCode() {
        return programCode;
    }

    /**
     * 节目sid
     * program_code
     *
     * @param programCode 节目sid
     */
    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    /**
     * 节目标题
     * title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 节目标题
     * title
     *
     * @param title 节目标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * deleted：删除，unbound：解绑，bound：绑定
     * status
     */
    public String getStatus() {
        return status;
    }

    /**
     * deleted：删除，unbound：解绑，bound：绑定
     * status
     *
     * @param status deleted：删除，unbound：解绑，bound：绑定
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 当前节目所属片库
     * product_code
     */
    public String getProductCode() {
        return productCode;
    }

    /**
     * 当前节目所属片库
     * product_code
     *
     * @param productCode 当前节目所属片库
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
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
        sb.append(", memberCode=").append(memberCode);
        sb.append(", memberName=").append(memberName);
        sb.append(", programCode=").append(programCode);
        sb.append(", title=").append(title);
        sb.append(", status=").append(status);
        sb.append(", productCode=").append(productCode);
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
        MemberProgram other = (MemberProgram) that;
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