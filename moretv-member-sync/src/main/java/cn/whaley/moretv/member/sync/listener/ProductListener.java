package cn.whaley.moretv.member.sync.listener;


import cn.whaley.moretv.member.base.constant.GlobalConstant;

import cn.whaley.moretv.member.sync.dto.goods.ProductDto;
import cn.whaley.moretv.member.sync.service.member.MemberProgramRelationService;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 后台会增量将某一片库下节目发布过来
 */
@Component
@RabbitListener(queues = GlobalConstant.MORETV_PUBLISH_CONTENT_PRODUCT_QUEUE)
public class ProductListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);

    @Autowired
    private MemberProgramRelationService memberProgramRelationService;
    
    

    @RabbitHandler
    public void listen(String str) {
        logger.info("mq.listen.product->" + str);
        if(StringUtils.isEmpty(str)){
            logger.error("mq.listen.product->can not be empty");
            return;
        }
        
        ProductDto productDto = null;
        try {
            productDto = JSON.parseObject(str, ProductDto.class);
            memberProgramRelationService.sync(productDto);
        } catch (Exception e) {
            logger.error("mq.listen.product->", e);
        }
       
    }

}
