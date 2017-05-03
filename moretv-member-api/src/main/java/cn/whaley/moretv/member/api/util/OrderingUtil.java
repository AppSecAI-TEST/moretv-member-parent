package cn.whaley.moretv.member.api.util;

import cn.whaley.moretv.member.api.dto.goods.GoodsResponse;
import cn.whaley.moretv.member.api.dto.goods.GoodsSpuResponse;
import cn.whaley.moretv.member.api.dto.member.MemberInfoResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by Bob Jiang on 2017/5/3.
 */
public class OrderingUtil {

    public static List<GoodsResponse> orderingGoods(List<GoodsResponse> responseList) {

        if (CollectionUtils.isEmpty(responseList)) {
            return Lists.newArrayList();
        }

        Collections.sort(responseList, new Ordering<GoodsResponse>() {
            @Override
            public int compare(GoodsResponse left, GoodsResponse right) {
                return left.getGoodsOrder().compareTo(right.getGoodsOrder());
            }
        }.compound(new Ordering<GoodsResponse>() {
            @Override
            public int compare(GoodsResponse left, GoodsResponse right) {
                return left.getGoodsCode().compareTo(right.getGoodsCode());
            }
        }));

        return responseList;
    }

    public static List<GoodsSpuResponse> orderingGoodsSpu(List<GoodsSpuResponse> responseList) {

        if (CollectionUtils.isEmpty(responseList)) {
            return Lists.newArrayList();
        }

        Collections.sort(responseList, new Ordering<GoodsSpuResponse>() {
            @Override
            public int compare(GoodsSpuResponse left, GoodsSpuResponse right) {
                return left.getBannerOrder().compareTo(right.getBannerOrder());
            }
        }.compound(new Ordering<GoodsSpuResponse>() {
            @Override
            public int compare(GoodsSpuResponse left, GoodsSpuResponse right) {
                return left.getGoodsBaseCode().compareTo(right.getGoodsBaseCode());
            }
        }));

        return responseList;
    }

    public static <T extends MemberInfoResponse> List<T> orderingMember(List<T> responseList) {
        if (CollectionUtils.isEmpty(responseList)) {
            return Lists.newArrayList();
        }

        Collections.sort(responseList, new Ordering<T>() {
            @Override
            public int compare(T left, T right) {
                return left.getMemberCode().compareTo(right.getMemberCode());
            }
        });

        return responseList;
    }
}
