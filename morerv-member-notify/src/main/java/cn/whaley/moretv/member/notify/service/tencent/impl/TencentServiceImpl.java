package cn.whaley.moretv.member.notify.service.tencent.impl;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.util.BeanHandler;
import cn.whaley.moretv.member.base.util.MD5Util;
import cn.whaley.moretv.member.base.util.MessageProducer;
import cn.whaley.moretv.member.model.cp.CpAccount;
import cn.whaley.moretv.member.model.cp.CpOrder;
import cn.whaley.moretv.member.model.cp.CpOrderItem;
import cn.whaley.moretv.member.model.member.MemberPackageRelation;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.notify.dto.cp.CpOrderDto;
import cn.whaley.moretv.member.notify.service.cp.CpAccountService;
import cn.whaley.moretv.member.notify.service.cp.CpOrderService;
import cn.whaley.moretv.member.notify.service.tencent.TencentService;
import cn.whaley.moretv.member.service.tencent.impl.BaseTencentServiceImpl;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Autowired
    private CpAccountService cpAccountService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MessageProducer messageProducer;

    private ThreadLocal<Date> now = new ThreadLocal<>();

    @Override
    public void createTencentOrder(Order order, List<OrderItem> orderItems) {
        now.set(new Date());
        String vipPackage = buildVipPackage(orderItems);
        logger.info("createTencentOrder : orderCode : {}, accountId : {}, vipPackage : {}",
                order.getOrderCode(), order.getAccountId(), vipPackage);

        if (vipPackage == null) {
            return;
        }
        String cpAccount = getCpAccount(order.getAccountId(), true);
        CpOrderDto cpOrderDto = createOrder(cpAccount, vipPackage, order.getOrderCode(), orderItems);

        logger.info("createTencentOrder : createCpOrder : {}", cpOrderDto);

        tencentConfirmOrder(cpAccount, cpOrderDto.getCpOrderCode());
        publishCpOrder(cpOrderDto);
    }

    @Override
    public String getCpAccount(Integer accountId, Boolean createAccount) {
        CpAccount cpAccount = cpAccountService.getCpAccount(accountId, GlobalConstant.CP_TENCENT);
        logger.info("getCpAccount : get cpAccount : {}", cpAccount);
        if (cpAccount != null) {
            return cpAccount.getCpAccount();
        } else if (createAccount) {
            JSONObject result = tencentGetVuid(accountId);
            String cpAccountId = result.getJSONObject("data").getString("vuid");
            String cpToken = result.getJSONObject("data").getString("vtoken");

            Date date = now != null && now.get() != null ? now.get() : new Date();
            cpAccount = cpAccountService.createCpAccount(cpAccountId, cpToken, accountId, date);
            logger.info("getCpAccount : create cpAccount : {}", cpAccount);
            publishCpAccount(cpAccount);
            return cpAccount.getCpAccount();
        } else {
            logger.info("getCpAccount : cpAccount is not exist");
            return null;
        }
    }

    /**
     * 创建腾讯订购订单并落库
     *
     * @param cpAccount
     * @param vipPackage
     * @param orderCode
     * @param orderItems
     * @return
     */
    private CpOrderDto createOrder(String cpAccount, String vipPackage,
            String orderCode, List<OrderItem> orderItems) {
        JSONObject jsonObject = tencentCreateOrder(cpAccount, vipPackage);
        String cpOrderCode = jsonObject.getJSONObject("data").getString("order_id");
        CpOrderDto orderDto = parseOrder(cpOrderCode, cpAccount, orderCode, orderItems);
        return cpOrderService.createCpOrder(orderDto).getData();
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
     * 申请腾讯账号
     *
     * @param accountId
     * @return
     */
    private JSONObject tencentGetVuid(Integer accountId) {
        String tpUserId = MD5Util.string2MD5(String.valueOf(accountId) + System.currentTimeMillis());
        String url = String.format("%s&access_token=%s&tp_userid=%s",
                tencent.getAccountApplicationServer(), getAccessToken(), tpUserId);
        return getRequest(url, "申请腾讯会员");
    }

    /**
     * 构建订购会员参数vippkg，会员间用"+"号分割
     *
     * @param orderItems
     * @return
     */
    private String buildVipPackage(List<OrderItem> orderItems) {
        StringBuilder packages = new StringBuilder();
        List<String> vipPackageList = buildVipPackageList(orderItems);
        for (String vip : vipPackageList) {
            packages.append(vip).append("+");
        }
        return packages.length() > 0 ? packages.substring(0, packages.length() - 1) : null;
    }

    /**
     * 根据订单明细中的会员，查询其对应的腾讯节目包的节目源外部编码。
     * 返回值List中每个item为一个腾讯会员，用"_"连接月时长，"_d"连接天时长。
     * 月数大于12，天数大于31，需拆分并时长。
     * @param orderItems
     * @return
     */
    private List<String> buildVipPackageList(List<OrderItem> orderItems) {
        List<String> vipPackageList = Lists.newArrayList();
        for (OrderItem item : orderItems) {
            HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
            String key = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, item.getMemberCode());

            List<String> list = opsHash.values(key);
            if (CollectionUtils.isEmpty(list)) {
                continue;
            }

            List<String> durationMonthList = durationMonth(item.getDurationMonth());
            List<String> durationDayList = durationDay(item.getDurationDay());

            for (String value : list) {
                MemberPackageRelation relation = JSON.parseObject(value, MemberPackageRelation.class);
                String sourceExternalCode = relation.getProgramSourceExternalCode();

                for (String month : durationMonthList) {
                    vipPackageList.add(sourceExternalCode + month);
                }
                for (String day : durationDayList) {
                    vipPackageList.add(sourceExternalCode + day);
                }
            }
        }
        return vipPackageList;
    }

    /**
     * 解析月时长
     *
     * @param durationMonth
     * @return
     */
    private List<String> durationMonth(Integer durationMonth) {
        int maxMonth = GlobalConstant.MAX_DURATION_MONTH;
        if (durationMonth <= 0) {
            return Lists.newArrayList();
        } else if (durationMonth <= maxMonth) {
            return Lists.newArrayList("_" + durationMonth);
        } else if (durationMonth > maxMonth) {
            List<String> list = Lists.newArrayList();
            int length = durationMonth % maxMonth == 0 ? durationMonth / maxMonth : durationMonth / maxMonth + 1;
            for (int i = 1; i <= length; i++) {
                if (i < length) {
                    list.add("_" + maxMonth);
                } else {
                    list.add("_" + (durationMonth - ((i - 1) * maxMonth)));
                }
            }
            return list;
        }
        return Lists.newArrayList();
    }

    /**
     * 解析天时长
     *
     * @param durationDay
     * @return
     */
    private List<String> durationDay(Integer durationDay) {
        int maxDay = GlobalConstant.MAX_DURATION_DAY;
        if (durationDay <= 0) {
            return Lists.newArrayList();
        } else if (durationDay <= maxDay) {
            return Lists.newArrayList("_d" + durationDay);
        } else if (durationDay > maxDay) {
            List<String> list = Lists.newArrayList();
            int length = durationDay % maxDay == 0 ? durationDay / maxDay : durationDay / maxDay + 1;
            for (int i = 1; i <= length; i++) {
                if (i < length) {
                    list.add("_d" + maxDay);
                } else {
                    list.add("_d" + (durationDay - ((i - 1) * maxDay)));
                }
            }
            return list;
        }
        return Lists.newArrayList();
    }

    /**
     * 构建CpOrder DTO
     *
     * @param cpOrderCode
     * @param cpAccount
     * @param orderCode
     * @param orderItems
     * @return
     */
    private CpOrderDto parseOrder(String cpOrderCode, String cpAccount, String orderCode, List<OrderItem> orderItems) {
        CpOrderDto orderDto = new CpOrderDto();
        orderDto.setCpOrderCode(cpOrderCode);
        orderDto.setCpAccount(cpAccount);
        orderDto.setBusinessOrderCode(orderCode);
        orderDto.setCpOrderStatus(OrderEnum.CpOrderStatus.COMPLETE.getCode());
        orderDto.setCreateTime(now.get());

        List<CpOrderItem> items = parseCpOrderItem(cpOrderCode, orderItems);
        orderDto.setCpOrderItems(items);
        return orderDto;
    }

    /**
     * 构建CpOrderItem
     *
     * @param cpOrderCode
     * @param orderItems
     * @return
     */
    private List<CpOrderItem> parseCpOrderItem(String cpOrderCode, List<OrderItem> orderItems) {
        List<CpOrderItem> items = Lists.newArrayList();
        for (OrderItem orderItem : orderItems) {
            CpOrderItem item = new CpOrderItem();
            item.setCpOrderCode(cpOrderCode);
            item.setCpMemberCode(orderItem.getMemberCode());
            item.setDurationMonth(orderItem.getDurationMonth());
            item.setDurationDay(orderItem.getDurationDay());
            item.setPrice(orderItem.getRealPrice());
            item.setTotalPrice(orderItem.getTotalPrice());
            item.setCreateTime(now.get());
            items.add(item);
        }
        return items;
    }

    private void publishCpOrder(CpOrderDto cpOrderDto) {
        CpOrder cpOrder = BeanHandler.copyProperties(cpOrderDto, CpOrder.class);
        messageProducer.send(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE,
                GlobalConstant.MORETV_PUBLISH_CP_ORDER_ROUTER_KEY, JSON.toJSONString(cpOrder));
        for (CpOrderItem item : cpOrderDto.getCpOrderItems()) {
            messageProducer.send(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE,
                    GlobalConstant.MORETV_PUBLISH_CP_ORDER_ITEM_ROUTER_KEY, JSON.toJSONString(item));
        }
    }

    private void publishCpAccount(CpAccount cpAccount) {
        messageProducer.send(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE,
                GlobalConstant.MORETV_PUBLISH_CP_ACCOUNT_ROUTER_KEY, JSON.toJSONString(cpAccount));
    }

}
