package cn.whaley.moretv.member.base.constant;

/**
 * Redis key
 *
 * Created by Bob Jiang on 2017/3/1.
 */
public interface CacheKeyConstant {

    //商品 key
    String REDIS_KEY_GOODS = "moretv:member:goods";

    //商品模型 key
    String REDIS_KEY_GOODS_SPU = "moretv:member:goodsSpu";
    
    //会员模型
    String REDIS_KEY_MEMBER = "moretv:member:member";
    
    //会员模型和节目包
    String REDIS_KEY_MEMBER_PACKAGE_RELATION = "moretv:member:package:relation";
    
    String REDIS_KEY_MEMBER_PROGRAM_RELATION = "moretv:member:program:relation";
}
