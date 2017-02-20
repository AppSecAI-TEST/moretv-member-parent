package cn.whaley.moretv.member.model.goods;

import cn.whaley.moretv.member.base.model.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Model: Goods
* Table: goods
* Alias: goods
*
* This Model generated by MyBatis Generator Extend at 2017-02-20 21:46:34
*/
public class Goods extends BaseModel<Integer> implements Serializable {
    /**
     * 商品编码
     * goods_code
     */
    private String goodsCode;

    /**
     * 商品名称
     * goods_name
     */
    private String goodsName;

    /**
     * 商品名称
     * goods_sub_name
     */
    private String goodsSubName;

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
     * 商品序位
     * goods_order
     */
    private Integer goodsOrder;

    /**
     * 商品原价
     * original_price
     */
    private Integer originalPrice;

    /**
     * 折扣价
     * discount
     */
    private BigDecimal discount;

    /**
     * 销售价格
     * selling_price
     */
    private Integer sellingPrice;

    /**
     * 商品类别
     * goods_type
     */
    private Integer goodsType;

    /**
     * 商品状态，delete：删除，valid：正常，published:已发布
     * goods_status
     */
    private String goodsStatus;

    /**
     * 是否外部显示
     * is_displayed
     */
    private Boolean isDisplayed;

    /**
     * 打折信息
     * promotion
     */
    private String promotion;

    /**
     * 商品背景地址
     * img_url
     */
    private String imgUrl;

    /**
     * 描述
     * goods_desc
     */
    private String goodsDesc;

    /**
     * 预约上架时间
     * start_time
     */
    private Date startTime;

    /**
     * 预约下架时间
     * end_time
     */
    private Date endTime;

    /**
     * 备注
     * remark
     */
    private String remark;

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
     * 商品编码
     * goods_code
     */
    public String getGoodsCode() {
        return goodsCode;
    }

    /**
     * 商品编码
     * goods_code
     *
     * @param goodsCode 商品编码
     */
    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    /**
     * 商品名称
     * goods_name
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 商品名称
     * goods_name
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 商品名称
     * goods_sub_name
     */
    public String getGoodsSubName() {
        return goodsSubName;
    }

    /**
     * 商品名称
     * goods_sub_name
     *
     * @param goodsSubName 商品名称
     */
    public void setGoodsSubName(String goodsSubName) {
        this.goodsSubName = goodsSubName;
    }

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
     * 商品序位
     * goods_order
     */
    public Integer getGoodsOrder() {
        return goodsOrder;
    }

    /**
     * 商品序位
     * goods_order
     *
     * @param goodsOrder 商品序位
     */
    public void setGoodsOrder(Integer goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    /**
     * 商品原价
     * original_price
     */
    public Integer getOriginalPrice() {
        return originalPrice;
    }

    /**
     * 商品原价
     * original_price
     *
     * @param originalPrice 商品原价
     */
    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    /**
     * 折扣价
     * discount
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 折扣价
     * discount
     *
     * @param discount 折扣价
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 销售价格
     * selling_price
     */
    public Integer getSellingPrice() {
        return sellingPrice;
    }

    /**
     * 销售价格
     * selling_price
     *
     * @param sellingPrice 销售价格
     */
    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    /**
     * 商品类别
     * goods_type
     */
    public Integer getGoodsType() {
        return goodsType;
    }

    /**
     * 商品类别
     * goods_type
     *
     * @param goodsType 商品类别
     */
    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    /**
     * 商品状态，delete：删除，valid：正常，published:已发布
     * goods_status
     */
    public String getGoodsStatus() {
        return goodsStatus;
    }

    /**
     * 商品状态，delete：删除，valid：正常，published:已发布
     * goods_status
     *
     * @param goodsStatus 商品状态，delete：删除，valid：正常，published:已发布
     */
    public void setGoodsStatus(String goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    /**
     * 是否外部显示
     * is_displayed
     */
    public Boolean getIsDisplayed() {
        return isDisplayed;
    }

    /**
     * 是否外部显示
     * is_displayed
     *
     * @param isDisplayed 是否外部显示
     */
    public void setIsDisplayed(Boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    /**
     * 打折信息
     * promotion
     */
    public String getPromotion() {
        return promotion;
    }

    /**
     * 打折信息
     * promotion
     *
     * @param promotion 打折信息
     */
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    /**
     * 商品背景地址
     * img_url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 商品背景地址
     * img_url
     *
     * @param imgUrl 商品背景地址
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 描述
     * goods_desc
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }

    /**
     * 描述
     * goods_desc
     *
     * @param goodsDesc 描述
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    /**
     * 预约上架时间
     * start_time
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 预约上架时间
     * start_time
     *
     * @param startTime 预约上架时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 预约下架时间
     * end_time
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 预约下架时间
     * end_time
     *
     * @param endTime 预约下架时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
        sb.append(", goodsCode=").append(goodsCode);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsSubName=").append(goodsSubName);
        sb.append(", goodsBaseCode=").append(goodsBaseCode);
        sb.append(", goodsBaseName=").append(goodsBaseName);
        sb.append(", goodsOrder=").append(goodsOrder);
        sb.append(", originalPrice=").append(originalPrice);
        sb.append(", discount=").append(discount);
        sb.append(", sellingPrice=").append(sellingPrice);
        sb.append(", goodsType=").append(goodsType);
        sb.append(", goodsStatus=").append(goodsStatus);
        sb.append(", isDisplayed=").append(isDisplayed);
        sb.append(", promotion=").append(promotion);
        sb.append(", imgUrl=").append(imgUrl);
        sb.append(", goodsDesc=").append(goodsDesc);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", remark=").append(remark);
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
        Goods other = (Goods) that;
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