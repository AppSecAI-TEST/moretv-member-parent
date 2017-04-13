package cn.whaley.moretv.member.service.goods;



import java.util.List;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.model.goods.GoodsSku;


/**
 * GoodsSku Base Service
 *
 * 在此定义通用的Service方法，实现为BaseGoodsSkuServiceImpl，
 * 每个前端系统中的GoodsSkuService，都需继承此接口，实现类也需继承BaseGoodsSkuServiceImpl
 *
 * Created by  tangzc on 2017/3/16.
 */
public interface BaseGoodsSkuService extends GenericService<GoodsSku, Integer> {

	/**
	 * 通过商品标号查询对应会员明细
	 * @param goodsNo
	 * @return
	 */
    List<GoodsSku> getGoodsSkuByGoodsNo(String goodsNo);

}
