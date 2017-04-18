package cn.whaley.moretv.member.base.util;

import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
import cn.whaley.moretv.member.base.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * rabbitmq sender
 */
@Component
public class MessageProducer {

	private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);

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
}
