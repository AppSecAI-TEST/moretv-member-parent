package cn.whaley.moretv.member.base.constant;

/**
 * Redis key
 *
 * Created by Bob Jiang on 2017/3/1.
 */
public interface CacheKeyConstant {

    //商品 hash key，field：goodsCode
    String REDIS_KEY_GOODS = "moretv:member:goods";

    //商品模型 hash key，field：goodsBaseCode
    String REDIS_KEY_GOODS_SPU = "moretv:member:goodsSpu";
    
    //会员模型
    String REDIS_KEY_MEMBER = "moretv:member:member";
    
    //会员模型和节目包
    String REDIS_KEY_MEMBER_PACKAGE_RELATION = "moretv:member:package:relation";
    
    //会员模型和节目关系：member:mprelation:{programCode}:{memberCode}
    String REDIS_KEY_MEMBER_PROGRAM_RELATION = "member:mprelation:";

    //会员权益，%s为accountId
    String REDIS_KEY_MEMBER_AUTHORITY = "moretv:member:authority:%s";
    
    //cp账号
    String REDIS_KEY_MEMBER_CP = "moretv:member:cp:%s";
}
