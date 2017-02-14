package cn.whaley.member.sync.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.whaley.member.sync.constant.GlobalConstant;

/**
 * Created by tangzc  on 2017/2/13.
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Exchange exchangeGoods() {
        return new TopicExchange(GlobalConstant.MORETV_ADMIN_PUBLISH_GOODS_EXCHANGE);
    }

    @Bean
    public Queue queueGoods() {
        return new Queue(GlobalConstant.MORETV_ADMIN_PUBLISH_GOODS_ROUTER_KEY, true);
    }

    @Bean
    public Binding bindingExchangeGoods() {
        return BindingBuilder.bind(queueGoods()).to(exchangeGoods())
                .with(GlobalConstant.MORETV_ADMIN_PUBLISH_GOODS_ROUTER_KEY).noargs();
    }
}
