package cn.whaley.moretv.member.sync.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.sync.dto.goods.MemberCppr;
import cn.whaley.moretv.member.sync.service.member.MemberPackageRelationService;


@Component
@RabbitListener(queues = GlobalConstant.MORETV_PUBLISH_CONTENT_PRODUCT_PACKAGE_QUEUE)
public class ProductPackageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductPackageListener.class);

    @Autowired
    private MemberPackageRelationService memberPackageRelationService;
    
    

    @RabbitHandler
    public void listen(String str) {
        logger.info("mq.listen.product.package->" + str);
        if(StringUtils.isEmpty(str)){
            logger.error("mq.listen.product.package->can not be empty");
            return;
        }
        
        MemberCppr memberCppr = null;
        try {
            memberCppr = JSON.parseObject(str, MemberCppr.class);
            memberPackageRelationService.sync(memberCppr);
        } catch (Exception e) {
            logger.error("mq.listen.product.package", e);
        }
       
    }

}
