package cn.whaley.moretv.member.notify.service.tencent;

import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.service.tencent.BaseTencentService;

import java.util.List;

/**
 * Created by Bob Jiang on 2017/4/13.
 */
public interface TencentService extends BaseTencentService {

    /**
     * 创建CP订单 订购腾讯会员
     *
     * 1.获取CP账号
     * 2.判断是否需要申请CP会员
     * 3.订购腾讯会员
     * 4.CpOrder入库
     * 5.腾讯发货
     * 6.发送CP订单Queue
     *
     * @param order 业务订单
     * @param orderItems 业务订单明细
     */
    void createTencentOrder(Order order, List<OrderItem> orderItems);

    /**
     * 获取CP账号，如果系统不存在此CP账号，则请求腾讯创建
     *
     * @param accountId 账号ID
     * @param createAccount 若会员库中不存在此CP账号，是否需要请求腾讯创建
     * @return
     */
    String getCpAccount(String accountId, Boolean createAccount);

}
