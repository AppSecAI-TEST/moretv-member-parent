package cn.whaley.moretv.member.service.queue.publish;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.util.MessageProducer;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;

import com.alibaba.fastjson.JSON;

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
			throw new SystemException(String.valueOf(ApiCodeInfo.API_ERROR), e.getMessage(), e);
		}
	}


    /**
     * 订单同步
     * @param 
     */
    public void publishOrder(Order order) {
  
        try {
        	String str=JSON.toJSONString(order);
        	logger.info("publishOrder:"+str);
        	 send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_BUSINESS_ORDER_ROUTER_KEY,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 订单同步
     * @param 
     */
    public void publishOrderItem(OrderItem orderItem) {
  
        try {
        	String str=JSON.toJSONString(orderItem);
        	logger.info("publishOrderItem:"+str);
        	 send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_BUSINESS_ORDER_ITEM_ROUTER_KEY,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 会员同步
     * @param 
     */
    public void publishMemberUserAuthority(MemberUserAuthority memberUserAuthority) {
  
        try {
        	String str=JSON.toJSONString(memberUserAuthority);
        	 logger.info("publishMemberUserAuthority:"+str);
        	 send(GlobalConstant.MORETV_PUBLISH_BUSINESS_EXCHANGE, GlobalConstant.MORETV_PUBLISH_BUSINESS_ORDER_ITEM_ROUTER_KEY,str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
