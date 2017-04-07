package cn.whaley.moretv.member.service.goods.impl;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.GoodsMapper;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.service.goods.BaseGoodsService;


/**
 * Goods Base ServiceImpl
 *
 * 在此定义多个系统都会用到的Service方法，mapper通过getGenericMapper调用
 *
 * Created by Bob Jiang on 2017/3/16.
 */
public abstract class BaseGoodsServiceImpl extends GenericServiceImpl<Goods, Integer, GoodsMapper> implements BaseGoodsService {

}
