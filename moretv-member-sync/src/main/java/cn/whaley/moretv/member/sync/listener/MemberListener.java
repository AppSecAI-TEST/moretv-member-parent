package cn.whaley.moretv.member.sync.listener;


import cn.whaley.moretv.member.base.constant.GlobalConstant;

import cn.whaley.moretv.member.sync.dto.goods.MemberDto;
import cn.whaley.moretv.member.sync.service.member.MemberService;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 接收后台会员模型
 * 内容包括：会员模型  + 包含的片库 + 包含的节目包
 * 处理逻辑：更新会员模型表  + 更新会员模型-节目包关系表
 */
@Component
@RabbitListener(queues = GlobalConstant.MORETV_PUBLISH_MEMBER_QUEUE)
public class MemberListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberListener.class);

    @Autowired
    private MemberService memberService;
    
    

    @RabbitHandler
    public void listen(String str) {
        logger.info("mq.listen.member->" + str);
        if(StringUtils.isEmpty(str)){
            logger.error("mq.listen.member->can not be empty");
            return;
        }
        
        MemberDto memberDto = null;
        try {
            memberDto = JSON.parseObject(str, MemberDto.class);
            memberService.sync(memberDto);
        } catch (Exception e) {
            logger.error("mq.listen.member", e);
        }
       
    }

}
