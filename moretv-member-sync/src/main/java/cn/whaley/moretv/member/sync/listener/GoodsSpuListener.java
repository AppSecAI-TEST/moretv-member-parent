package cn.whaley.moretv.member.sync.listener;


import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.ApiCodeInfo;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.res.ResBase;
import cn.whaley.moretv.member.model.goods.GoodsSpu;
import cn.whaley.moretv.member.sync.dto.goods.GoodsDto;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;
import cn.whaley.moretv.member.sync.service.goods.GoodsSpuService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 同步商品模型
 *
 * Created by Bob Jiang on 2017/3/1.
 */
@Component
@RabbitListener(queues = GlobalConstant.MORETV_PUBLISH_GOODS_SPU_QUEUE)
public class GoodsSpuListener {
	
	private static final Logger logger = LoggerFactory.getLogger(GoodsSpuListener.class);

    @Autowired
    private GoodsSpuService goodsSpuService;

    @RabbitHandler
    public void listen(String goodsSpuStr) {
        GoodsSpu goodsSpu = convertGoodsSpu(goodsSpuStr);
        ResBase resBase = goodsSpuService.syncGoodsSpu(goodsSpu);

        if (resBase.getCode() != ApiCodeInfo.API_OK) {
            throw new SystemException(String.valueOf(resBase.getCode()), resBase.getMsg());
        }
        logger.info("goodsSpu_listen: sync GoodsSpu success!");
    }

    private GoodsSpu convertGoodsSpu(String goodsSpuStr) {
        if (StringUtils.isEmpty(goodsSpuStr)) {
            logger.error("goodsSpu_listen: param is null");
            throw new SystemException(ApiCodeEnum.API_PARAM_NULL);
        }

        logger.info("goodsSpu_listen: param: {}", goodsSpuStr);

        try {
            return JSON.parseObject(goodsSpuStr, GoodsSpu.class);
        } catch (Exception e) {
            logger.error("goodsSpu_listen: param parse to GoodsSpu error");
            throw new SystemException(ApiCodeEnum.API_PARAM_ERR);
        }
    }
}
