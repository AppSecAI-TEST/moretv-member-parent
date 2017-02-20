package cn.whaley.moretv.member.sync.listener;


import cn.whaley.moretv.member.base.constant.GlobalConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
@RabbitListener(queues = GlobalConstant.MORETV_PUBLISH_GOODS_ROUTER_KEY)
public class GoodsListener {
	
	Logger logger = LoggerFactory.getLogger(GoodsListener.class);
	
    @RabbitHandler
    public void listen(String goodsStr) {
    	
        logger.info("goods_listen:{}",goodsStr);
		  // Goods goods = JSON.parseObject(goodsStr, Goods.class);
        System.out.println(goodsStr);
     
    }
}
