package cn.whaley.moretv.member.base.constant;

/**
 * Redis key
 *
 * Created by Bob Jiang on 2017/3/1.
 */
public interface CacheKeyConstant {

    /**
     * 商品 Hash key
     * field : goodsCode
     * table : goods, goods_sku
     *
     * 存储规则：存储已发布的商品数据。
     */
    String REDIS_KEY_GOODS = "moretv:member:goods";

    /**
     * 商品模型 Hash key
     * field : goodsBaseCode
     * table : good_spu
     *
     * 存储规则：存储已发布的商品模型数据。
     */
    String REDIS_KEY_GOODS_SPU = "moretv:member:goodsSpu";

    /**
     * 会员模型 Hash key
     * field : memberCode
     * table : member
     *
     * 存储规则：存储已发布的会员模型数据。
     */
    String REDIS_KEY_MEMBER = "moretv:member:member";

    /**
     * 会员模型和节目包 Hash key, %s -> memberCode
     * field : packageCode
     * table : member_package_relation
     *
     * 存储规则：存储会员模型与腾讯节目包已绑定的关系数据，可能会存在多个包。
     */
    String REDIS_KEY_MEMBER_PACKAGE_RELATION = "moretv:member:package:relation:%s";

    /**
     * 会员模型和节目关系 Value key, %s -> programCode
     * table : member_program_relation
     *
     * 存储规则：存储节目与会员模型的已绑定的关系数据，至多一条，即一个节目只能绑定到一个会员上。
     */
    String REDIS_KEY_MEMBER_PROGRAM_RELATION = "member:mprelation:%s";

    /**
     * 会员权益 Hash key, %s -> accountId
     * field : memberCode
     * table : member_user_authority
     *
     * 存储规则：存储账户有效的会员权益，会包含已过期的权益。
     */
    String REDIS_KEY_MEMBER_AUTHORITY = "moretv:member:authority:%s";

}
