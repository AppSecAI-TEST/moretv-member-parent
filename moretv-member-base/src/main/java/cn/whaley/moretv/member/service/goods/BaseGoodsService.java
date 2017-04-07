package cn.whaley.moretv.member.service.goods;

import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.member.Member;


/**
 * Goods Base Service
 *
 * 在此定义通用的Service方法，实现为BaseGoodsServiceImpl，
 * 每个前端系统中的GoodsService，都需继承此接口，实现类也需继承BaseGoodsServiceImpl
 *
 * Created by  tangzc on 2017/3/16.
 */
public interface BaseGoodsService extends GenericService<Goods, Integer> {
	/**
	 * 通过商品标号查询商品信息
	 * @return
	 */
    Goods getGoodsByGoodsNo(String goodsNo);
    
    RedisTemplate getRedisTemplate();
}
