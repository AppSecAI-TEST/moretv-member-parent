package cn.whaley.moretv.member.api.dto.response;

import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;

import java.util.List;

/**
 * Created by Bob Jiang on 2017/3/21.
 */
public class GoodsDto extends Goods {

    private static final long serialVersionUID = -4927534851784218980L;

    private List<GoodsSku> goodsSkuList;

    public List<GoodsSku> getGoodsSkuList() {
        return goodsSkuList;
    }

    public void setGoodsSkuList(List<GoodsSku> goodsSkuList) {
        this.goodsSkuList = goodsSkuList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" { id=").append(id);
        sb.append(", goodsCode=").append(getGoodsCode());
        sb.append(", goodsName=").append(getGoodsName());
        sb.append(", goodsSubName=").append(getGoodsSubName());
        sb.append(", goodsBaseCode=").append(getGoodsBaseCode());
        sb.append(", goodsBaseName=").append(getGoodsBaseName());
        sb.append(", goodsOrder=").append(getGoodsOrder());
        sb.append(", originalPrice=").append(getOriginalPrice());
        sb.append(", discount=").append(getDiscount());
        sb.append(", sellingPrice=").append(getSellingPrice());
        sb.append(", goodsType=").append(getGoodsType());
        sb.append(", goodsStatus=").append(getGoodsStatus());
        sb.append(", isDisplayed=").append(getIsDisplayed());
        sb.append(", promotion=").append(getPromotion());
        sb.append(", imgUrl=").append(getImgUrl());
        sb.append(", goodsDesc=").append(getGoodsDesc());
        sb.append(", startTime=").append(getStartTime());
        sb.append(", endTime=").append(getEndTime());
        sb.append(", remark=").append(getRemark());
        sb.append(", createTime=").append(getCreateTime());
        sb.append(", updateTime=").append(getUpdateTime());
        sb.append(", goodsSkuList=").append(goodsSkuList);
        sb.append("}");
        return sb.toString();
    }
}
