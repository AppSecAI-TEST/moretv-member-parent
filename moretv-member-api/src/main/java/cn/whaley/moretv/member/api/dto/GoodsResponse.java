package cn.whaley.moretv.member.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Bob Jiang on 2017/3/21.
 */
public class GoodsResponse implements Serializable {

    private static final long serialVersionUID = -4927534851784218980L;

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
     * 会员编码unit
     * member_code
     */
    private String memberCode;

    /**
     * 月unit
     * duration_month
     */
    private Integer durationMonth;

    /**
     * 日unit
     * duration_day
     */
    private Integer durationDay;

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
     * 商品子类，1：普通商品，2：首次商品，3：非首次商品
     * goods_class
     */
    private Integer goodsClass;

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


    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSubName() {
        return goodsSubName;
    }

    public void setGoodsSubName(String goodsSubName) {
        this.goodsSubName = goodsSubName;
    }

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Integer getDurationMonth() {
        return durationMonth;
    }

    public void setDurationMonth(Integer durationMonth) {
        this.durationMonth = durationMonth;
    }

    public Integer getDurationDay() {
        return durationDay;
    }

    public void setDurationDay(Integer durationDay) {
        this.durationDay = durationDay;
    }

    public Integer getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(Integer goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    public Integer getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Integer originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Integer getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Integer sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(Integer goodsClass) {
        this.goodsClass = goodsClass;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    @Override
    public String toString() {
        return "{ goodsCode='" + goodsCode + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsSubName='" + goodsSubName + '\'' +
                ", memberCode='" + memberCode + '\'' +
                ", durationMonth=" + durationMonth +
                ", durationDay=" + durationDay +
                ", goodsOrder=" + goodsOrder +
                ", originalPrice=" + originalPrice +
                ", discount=" + discount +
                ", sellingPrice=" + sellingPrice +
                ", goodsType=" + goodsType +
                ", goodsClass=" + goodsClass +
                ", promotion='" + promotion + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                '}';
    }
}
