package cn.whaley.moretv.member.notify.config;

import cn.whaley.moretv.member.base.constant.GlobalConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Bob Jiang on 2017/2/6.
 */
@Configuration
public class RabbitConfig {

    // 会员模型
    @Bean
    public Exchange exchangeCP() {
        return new TopicExchange(GlobalConstant.MORETV_PUBLISH_CP_EXCHANGE);
    }
    
    @Bean
    public Queue queueCpOrder() {
        return new Queue(GlobalConstant.MORETV_PUBLISH_CP_ORDER_QUEUE, true);
    }
    
    @Bean
    public Binding bindingExchangeCP() {
        return BindingBuilder.bind(queueCpOrder()).to(exchangeCP())
                .with(GlobalConstant.MORETV_PUBLISH_CP_ORDER_ROUTER_KEY).noargs();
    }

}
