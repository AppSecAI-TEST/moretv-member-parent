package cn.whaley.moretv.member.api.dto.goods;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2017/3/21.
 */
public class GoodsSpuResponse implements Serializable {

    private static final long serialVersionUID = 5605509707592406275L;

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
     * 权益介绍图
     * member_rights_picture
     */
    private String memberRightsPicture;

    /**
     * 权益背景图
     * member_back_picture
     */
    private String memberBackPicture;

    public String getGoodsBaseCode() {
        return goodsBaseCode;
    }

    public void setGoodsBaseCode(String goodsBaseCode) {
        this.goodsBaseCode = goodsBaseCode;
    }

    public String getGoodsBaseName() {
        return goodsBaseName;
    }

    public void setGoodsBaseName(String goodsBaseName) {
        this.goodsBaseName = goodsBaseName;
    }

    public Boolean getBannerDisplay() {
        return isBannerDisplay;
    }

    public void setBannerDisplay(Boolean bannerDisplay) {
        isBannerDisplay = bannerDisplay;
    }

    public Integer getBannerOrder() {
        return bannerOrder;
    }

    public void setBannerOrder(Integer bannerOrder) {
        this.bannerOrder = bannerOrder;
    }

    public String getBannerPicture() {
        return bannerPicture;
    }

    public void setBannerPicture(String bannerPicture) {
        this.bannerPicture = bannerPicture;
    }

    public String getBannerTip() {
        return bannerTip;
    }

    public void setBannerTip(String bannerTip) {
        this.bannerTip = bannerTip;
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

    public String getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(String memberPrice) {
        this.memberPrice = memberPrice;
    }

    public String getMemberRightsPicture() {
        return memberRightsPicture;
    }

    public void setMemberRightsPicture(String memberRightsPicture) {
        this.memberRightsPicture = memberRightsPicture;
    }

    public String getMemberBackPicture() {
        return memberBackPicture;
    }

    public void setMemberBackPicture(String memberBackPicture) {
        this.memberBackPicture = memberBackPicture;
    }

    @Override
    public String toString() {
        return "{ goodsBaseCode='" + goodsBaseCode + '\'' +
                ", goodsBaseName='" + goodsBaseName + '\'' +
                ", isBannerDisplay=" + isBannerDisplay +
                ", bannerOrder=" + bannerOrder +
                ", bannerPicture='" + bannerPicture + '\'' +
                ", bannerTip='" + bannerTip + '\'' +
                ", memberCode='" + memberCode + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberPrice='" + memberPrice + '\'' +
                ", memberRightsPicture='" + memberRightsPicture + '\'' +
                ", memberBackPicture='" + memberBackPicture + '\'' +
                '}';
    }
}
