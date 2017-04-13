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
        GoodsDto goodsDto = convertGoods(goodsStr);
        ResultResponse resBase = goodsService.syncGoods(goodsDto);

        if (!resBase.isSuccess()) {
            throw new SystemException(String.valueOf(resBase.getCode()), resBase.getMsg());
        }
        logger.info("goods_listen: sync Goods success!");
    }

    private GoodsDto convertGoods(String goodsStr) {
        if (StringUtils.isEmpty(goodsStr)) {
            logger.error("goods_listen: param is null");
            throw new SystemException(ApiCodeEnum.API_PARAM_NULL);
        }

        logger.info("goods_listen: param: {}", goodsStr);

        try {
            return JSON.parseObject(goodsStr, GoodsDto.class);
        } catch (Exception e) {
            logger.error("goods_listen: param parse to Goods error");
            throw new SystemException(ApiCodeEnum.API_PARAM_ERR);
        }
    }
}
