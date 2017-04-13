package cn.whaley.moretv.member.notify.service.tencent.impl;

import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.ThirdPartyInfo;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.model.cp.CpOrderItem;
import cn.whaley.moretv.member.notify.dto.cp.CpOrderDto;
import cn.whaley.moretv.member.notify.service.cp.CpOrderService;
import cn.whaley.moretv.member.notify.service.tencent.TencentService;
import cn.whaley.moretv.member.service.tencent.impl.BaseTencentServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Created by Bob Jiang on 2017/4/13.
 */
@Service
@Transactional
public class TencentServiceImpl extends BaseTencentServiceImpl implements TencentService {

    @Autowired
    private CpOrderService cpOrderService;

    private ThreadLocal<Date> now = new ThreadLocal<>();

    @Override
    public void createTencentOrder(String cpAccount, String vipPackage, List<ThirdPartyInfo> thirdPartyInfoList, String orderCode) {
        now.set(new Date());
        // 1.订购腾讯会员
        // 2.CpOrder入库
        ResultResponse<CpOrderDto> response = createOrder(cpAccount, vipPackage, orderCode, thirdPartyInfoList);
        // 3.腾讯发货
        if (response.isSuccess()) {
            CpOrderDto cpOrderDto = response.getData();
            tencentConfirmOrder(cpAccount, cpOrderDto.getCpOrderCode());
        }
        // 4.同步CP订单
    }

    /**
     * 创建腾讯订购订单并落库
     * @param cpAccount
     * @param vipPackage
     * @param orderCode
     * @param thirdPartyInfoList
     * @return
     */
    private ResultResponse<CpOrderDto> createOrder(String cpAccount, String vipPackage, String orderCode, List<ThirdPartyInfo> thirdPartyInfoList) {
        JSONObject jsonObject = tencentCreateOrder(cpAccount, vipPackage);
        String cpOrderCode = jsonObject.getJSONObject("data").getString("order_id");
        CpOrderDto orderDto = parseOrder(cpOrderCode, cpAccount, orderCode, thirdPartyInfoList);
        return cpOrderService.createCpOrder(orderDto);
    }

    /**
     * 订购腾讯会员
     *
     * @param cpAccount
     * @param vipPackage
     * @return
     */
    private JSONObject tencentCreateOrder(String cpAccount, String vipPackage) {
        String url = String.format("%s&access_token=%s&vippkg=%s&vuid=%s",
                tencent.getCreateOrderServer(), getAccessToken(), URLEncoder.encode(vipPackage), cpAccount);
        return getRequest(url, "订购腾讯会员");
    }

    /**
     * 腾讯发货
     *
     * @param cpAccount
     * @param orderId
     * @return
     */
    private JSONObject tencentConfirmOrder(String cpAccount, String orderId) {
        String url = String.format("%s&access_token=%s&order_id=%s&vuid=%s",
                tencent.getDeliveryNoticeServer(), getAccessToken(), orderId, cpAccount);
        return getRequest(url, "腾讯发货");
    }

    /**
     * 构建CpOrder DTO
     *
     * @param cpOrderCode
     * @param cpAccount
     * @param orderCode
     * @param thirdPartyInfoList
     * @return
     */
    private CpOrderDto parseOrder(String cpOrderCode, String cpAccount, String orderCode, List<ThirdPartyInfo> thirdPartyInfoList) {
        CpOrderDto orderDto = new CpOrderDto();
        orderDto.setCpOrderCode(cpOrderCode);
        orderDto.setCpAccount(cpAccount);
        orderDto.setBusinessOrderCode(orderCode);
        orderDto.setCpOrderStatus(OrderEnum.CpOrderStatus.COMPLETE.getCode());
        orderDto.setCreateTime(now.get());

        List<CpOrderItem> items = parseCpOrderItem(cpOrderCode, thirdPartyInfoList);
        orderDto.setCpOrderItems(items);
        return orderDto;
    }

    /**
     * 构建CpOrderItem
     *
     * @param cpOrderCode
     * @param thirdPartyInfoList
     * @return
     */
    private List<CpOrderItem> parseCpOrderItem(String cpOrderCode, List<ThirdPartyInfo> thirdPartyInfoList) {
        List<CpOrderItem> items = Lists.newArrayList();
        for (ThirdPartyInfo thirdPartyInfo : thirdPartyInfoList) {
            CpOrderItem item = new CpOrderItem();
            item.setCpOrderCode(cpOrderCode);
            item.setCpProduct(thirdPartyInfo.getCpProduct());
            item.setDurationMonth(thirdPartyInfo.getDuration());
            item.setDurationDay(thirdPartyInfo.getDurationDay());
            item.setPrice(0);
            item.setTotalPrice(0);
            item.setCreateTime(now.get());
            items.add(item);
        }
        return items;
    }


}
