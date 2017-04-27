package cn.whaley.moretv.member.sync.listener;


import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.GlobalConstant;

import cn.whaley.moretv.member.base.dto.goods.GoodsDto;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 同步商品
 *
 * Created by Bob Jiang on 2017/3/1.
 */
@Component
@RabbitListener(queues = GlobalConstant.MORETV_PUBLISH_GOODS_QUEUE)
public class GoodsListener {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsListener.class);

    @Autowired
    private GoodsService goodsService;

    @RabbitHandler
    public void listen(String goodsStr) {
        try {
            GoodsDto goodsDto = convertGoods(goodsStr);
            goodsService.syncGoods(goodsDto);
            logger.info("goods_listen: sync Goods success!");
        } catch (Exception e) {
            logger.error("goods_listen: error: ", e);
        }
    }

    private GoodsDto convertGoods(String goodsStr) {
        if (StringUtils.isEmpty(goodsStr)) {
            throw new SystemException(ApiCodeEnum.API_PARAM_NULL);
        }

        logger.info("goods_listen: param: {}", goodsStr);
        return JSON.parseObject(goodsStr, GoodsDto.class);
    }
}
