package cn.whaley.moretv.member.model.goods;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.util.Date;

/**
* Model: GoodsSpu
* Table: goods_spu
* Alias: spu
*
* This Model generated by MyBatis Generator Extend.
*/
public class GoodsSpu extends BaseModel<Integer> implements Serializable {
    /**
     * 商品模型编号
     * goods_base_code
     */
    private String goodsBaseCode;

    /**
     * 商品模型名称
     * goods_base_name
     */
    private String goodsBaseName;

    /**
     * 首页显示banner
     * is_banner_display
     */
    private Boolean isBannerDisplay;

    /**
     * 1:独立，2组合
     * composited_type
     */
    private Integer compositedType;

    /**
     * *banner序位
     * banner_order
     */
    private Integer bannerOrder;

    /**
     * banner图
     * banner_picture
     */
    private String bannerPicture;

    /**
     * banner提示语
     * banner_tip
     */
    private String bannerTip;

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
     * 会员价格
     * member_price
     */
    private String memberPrice;

    /**
     * 年费
     * year_price
     */
    private Integer yearPrice;

    /**
     * 权益介绍图
     * member_rights_picture
     */
    private String memberRightsPicture;

    /**
     * 权益背景图
     * member_back_picture
     */
    private String memberBackPicture;

    /**
     * 发布时间
     * publish_time
     */
    private Date publishTime;

    /**
     * 操作人id
     * operator_id
     */
    private Integer operatorId;

    /**
     * delete：删除，valid：正常
     * goods_status
     */
    private String goodsStatus;

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
     * 商品模型编号
     * goods_base_code
     */
    public String getGoodsBaseCode() {
        return goodsBaseCode;
    }

    /**
     * 商品模型编号
     * goods_base_code
     *
     * @param goodsBaseCode 商品模型编号
     */
    public void setGoodsBaseCode(String goodsBaseCode) {
        this.goodsBaseCode = goodsBaseCode;
    }

    /**
     * 商品模型名称
     * goods_base_name
     */
    public String getGoodsBaseName() {
        return goodsBaseName;
    }

    /**
     * 商品模型名称
     * goods_base_name
     *
     * @param goodsBaseName 商品模型名称
     */
    public void setGoodsBaseName(String goodsBaseName) {
        this.goodsBaseName = goodsBaseName;
    }

    /**
     * 首页显示banner
     * is_banner_display
     */
    public Boolean getIsBannerDisplay() {
        return isBannerDisplay;
    }

    /**
     * 首页显示banner
     * is_banner_display
     *
     * @param isBannerDisplay 首页显示banner
     */
    public void setIsBannerDisplay(Boolean isBannerDisplay) {
        this.isBannerDisplay = isBannerDisplay;
    }

    /**
     * 1:独立，2组合
     * composited_type
     */
    public Integer getCompositedType() {
        return compositedType;
    }

    /**
     * 1:独立，2组合
     * composited_type
     *
     * @param compositedType 1:独立，2组合
     */
    public void setCompositedType(Integer compositedType) {
        this.compositedType = compositedType;
    }

    /**
     * *banner序位
     * banner_order
     */
    public Integer getBannerOrder() {
        return bannerOrder;
    }

    /**
     * *banner序位
     * banner_order
     *
     * @param bannerOrder *banner序位
     */
    public void setBannerOrder(Integer bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    /**
     * banner图
     * banner_picture
     */
    public String getBannerPicture() {
        return bannerPicture;
    }

    /**
     * banner图
     * banner_picture
     *
     * @param bannerPicture banner图
     */
    public void setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    /**
     * banner提示语
     * banner_tip
     */
    public String getBannerTip() {
        return bannerTip;
    }

    /**
     * banner提示语
     * banner_tip
     *
     * @param bannerTip banner提示语
     */
    public void setBannerTip(String bannerTip) {
        this.bannerTip = bannerTip;
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
     * 会员价格
     * member_price
     */
    public String getMemberPrice() {
        return memberPrice;
    }

    /**
     * 会员价格
     * member_price
     *
     * @param memberPrice 会员价格
     */
    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    /**
     * 年费
     * year_price
     */
    public Integer getYearPrice() {
        return yearPrice;
    }

    /**
     * 年费
     * year_price
     *
     * @param yearPrice 年费
     */
    public void setYearPrice(Integer yearPrice) {
        this.yearPrice = yearPrice;
    }

    /**
     * 权益介绍图
     * member_rights_picture
     */
    public String getMemberRightsPicture() {
        return memberRightsPicture;
    }

    /**
     * 权益介绍图
     * member_rights_picture
     *
     * @param memberRightsPicture 权益介绍图
     */
    public void setMemberRightsPicture(String memberRightsPicture) {
        this.memberRightsPicture = memberRightsPicture;
    }

    /**
     * 权益背景图
     * member_back_picture
     */
    public String getMemberBackPicture() {
        return memberBackPicture;
    }

    /**
     * 权益背景图
     * member_back_picture
     *
     * @param memberBackPicture 权益背景图
     */
    public void setMemberBackPicture(String memberBackPicture) {
        this.memberBackPicture = memberBackPicture;
    }

    /**
     * 发布时间
     * publish_time
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 发布时间
     * publish_time
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 操作人id
     * operator_id
     */
    public Integer getOperatorId() {
        return operatorId;
    }

    /**
     * 操作人id
     * operator_id
     *
     * @param operatorId 操作人id
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * delete：删除，valid：正常
     * goods_status
     */
    public String getGoodsStatus() {
        return goodsStatus;
    }

    /**
     * delete：删除，valid：正常
     * goods_status
     *
     * @param goodsStatus delete：删除，valid：正常
     */
    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
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
        sb.append(", goodsBaseCode=").append(goodsBaseCode);
        sb.append(", goodsBaseName=").append(goodsBaseName);
        sb.append(", isBannerDisplay=").append(isBannerDisplay);
        sb.append(", compositedType=").append(compositedType);
        sb.append(", bannerOrder=").append(bannerOrder);
        sb.append(", bannerPicture=").append(bannerPicture);
        sb.append(", bannerTip=").append(bannerTip);
        sb.append(", memberCode=").append(memberCode);
        sb.append(", memberName=").append(memberName);
        sb.append(", memberPrice=").append(memberPrice);
        sb.append(", yearPrice=").append(yearPrice);
        sb.append(", memberRightsPicture=").append(memberRightsPicture);
        sb.append(", memberBackPicture=").append(memberBackPicture);
        sb.append(", publishTime=").append(publishTime);
        sb.append(", operatorId=").append(operatorId);
        sb.append(", goodsStatus=").append(goodsStatus);
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
        GoodsSpu other = (GoodsSpu) that;
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