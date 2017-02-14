package cn.whaley.member.sync.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * rabbitmq sender
 */
@Component
public class MessageProducer {

    @Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void send(String exchange, String routingKey, Object object) {
		this.rabbitTemplate.convertAndSend(exchange, routingKey, object);
	}
}
