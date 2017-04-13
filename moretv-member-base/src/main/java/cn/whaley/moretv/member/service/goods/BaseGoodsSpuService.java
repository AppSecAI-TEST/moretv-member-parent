package cn.whaley.moretv.member.service.goods;



import java.util.List;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.goods.GoodsSpu;


/**
 * GoodsSpu Base Service
 *
 * 在此定义通用的Service方法，实现为BaseGoodsSpuServiceImpl，
 * 每个前端系统中的GoodsSpuService，都需继承此接口，实现类也需继承BaseGoodsSpuServiceImpl
 *
 * Created by  tangzc on 2017/3/16.
 */
public interface BaseGoodsSpuService extends GenericService<GoodsSpu, Integer> {

}
