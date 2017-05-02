package cn.whaley.moretv.member.service.queue.publish;


import cn.whaley.moretv.member.model.cp.CpAccount;
import cn.whaley.moretv.member.model.cp.CpOrder;
import cn.whaley.moretv.member.model.cp.CpOrderItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;
import cn.whaley.moretv.member.model.order.DeliveredOrder;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;

import com.alibaba.fastjson.JSON;

import java.util.List;

@Service
public class PublishMemberToAdmin {
	
	private static final Logger logger = LoggerFactory.getLogger(PublishMemberToAdmin.class);
	
	 @Autowired
	private RabbitTemplate rabbitTemplate;

	public void send(String exchange, String routingKey, Object object) {
		try {
			this.rabbitTemplate.convertAndSend(exchange, routingKey, object);
			logger.info("[MessageProducer] exchange : {}, routingKey : {}, queue : {}",
					exchange, routingKey, object.toString());
		} catch (Throwable e) {
            logger.error("rabbit send queue error", e);
		}
	}


    /**
     * 订单同步
     * @param 
     */
    public void publishOrder(Order order) {
        String str = JSON.toJSONString(order);
        send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_BUSINESS_ORDER_ROUTER_KEY,str);
    }
    
    /**
     * 订单明细同步
     * @param 
     */
    public void publishOrderItem(OrderItem orderItem) {
        String str = JSON.toJSONString(orderItem);
        send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_BUSINESS_ORDER_ITEM_ROUTER_KEY,str);
    }
    
    /**
     * 订单发货
     * @param 
     */
    public void publishDeliveredOrder(DeliveredOrder deliveredOrder) {
        String str = JSON.toJSONString(deliveredOrder);
        send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_DELIVERED_ORDER_ROUTER_KEY,str);
    }
    
    /**
     * 会员同步
     * @param 
     */
    public void publishMemberUserAuthority(MemberUserAuthority memberUserAuthority) {
        String str = JSON.toJSONString(memberUserAuthority);
        send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_BUSINESS_ORDER_ITEM_ROUTER_KEY,str);
    }

    public void publishCpOrder(CpOrder cpOrder) {
        send(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE,
                GlobalConstant.MORETV_PUBLISH_CP_ORDER_ROUTER_KEY, JSON.toJSONString(cpOrder));
    }

    public void publishCpOrderItem(List<CpOrderItem> cpOrderItemList) {
        for (CpOrderItem item : cpOrderItemList) {
            send(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE,
                    GlobalConstant.MORETV_PUBLISH_CP_ORDER_ITEM_ROUTER_KEY, JSON.toJSONString(item));
        }
    }

    public void publishCpAccount(CpAccount cpAccount) {
        send(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE,
                GlobalConstant.MORETV_PUBLISH_CP_ACCOUNT_ROUTER_KEY, JSON.toJSONString(cpAccount));
    }
}
