package cn.whaley.moretv.member.base.constant;

/**
 * Created by tangzc on 2015/9/18.
 */
public interface GlobalConstant {
    
    String UNIT_YUAN = "元";

    //CP腾讯
    String CP_TENCENT = "tencent";
    //CP蘑菇
    String CP_MOGUV = "moguv";
    //CP香菇
    String CP_XIANGGU = "xiangguv";
    
    String WHALEY_SERVICE_ID = "111";

    int MAX_DURATION_MONTH = 12;
    int MAX_DURATION_DAY = 31;

    String CHARSET_UTF8 = "UTF-8";

    String TENCENT_RESULT_STUTAS_YES = "0";
    
    // 订单状态常量数组
 	public static final String[] ORDER_STATUS_STR_ARRAY = { "UNKNOWN", "WAIT_BUYER_PAY", "WAITING_SEND",
 			"TRADE_FINISHED", "TRADE_TIMEOUT" };

    //商品队列
    String MORETV_PUBLISH_GOODS_QUEUE = "moretv.publish.goods.queue";

    //商品模型队列
    String MORETV_PUBLISH_GOODS_SPU_QUEUE = "moretv.publish.goods.spu.queue";
    
    //会员模型队列
    String MORETV_PUBLISH_MEMBER_QUEUE = "moretv.publish.member.queue";
    
    //片库队列
    String MORETV_PUBLISH_CONTENT_PRODUCT_QUEUE = "moretv.publish.content.product.queue";

    // CP订单队列
    String MORETV_PUBLISH_CP_EXCHANGE = "moretv.publish.cp";
    String MORETV_PUBLISH_CP_ORDER_ROUTER_KEY = "moretv.publish.cp.order.router.key";
    String MORETV_PUBLISH_CP_ORDER_QUEUE = "moretv.publish.cp.order.queue";

    // CP订单明细
    String MORETV_PUBLISH_CP_ORDER_ITEM_ROUTER_KEY = "moretv.publish.cp.order.item.router.key";
    String MORETV_PUBLISH_CP_ORDER_ITEM_QUEUE = "moretv.publish.cp.order.item.queue";

    // 电视猫账号队列
    String MORETV_PUBLISH_CP_ACCOUNT_ROUTER_KEY = "moretv.publish.cp.account.router.key";
    String MORETV_PUBLISH_CP_ACCOUNT_QUEUE = "moretv.publish.cp.account.queue";
    
    // 电视猫订单队列
    String MORETV_PUBLISH_ORDER_EXCHANGE = "moretv.publish.order";
    String MORETV_PUBLISH_BUSINESS_ORDER_ROUTER_KEY = "moretv.publish.business.order.router.key";
    String MORETV_PUBLISH_BUSINESS_ORDER_QUEUE = "moretv.publish.business.order.queue";
    
    //电视猫订单明细
    String MORETV_PUBLISH_BUSINESS_ORDER_ITEM_ROUTER_KEY = "moretv.publish.business.order.item.router.key";
    String MORETV_PUBLISH_BUSINESS_ORDER_ITEM_QUEUE = "moretv.publish.business.order.item.queue";
    
    //电视猫会员权益队列
    String MORETV_PUBLISH_MEMBER_EXCHANGE = "moretv.publish.member";
    String MORETV_PUBLISH_MEMBER_USER_AUTHORITY_ROUTER_KEY = "moretv.publish.member.user.authority.router.key";
    String MORETV_PUBLISH_MEMBER_USER_AUTHORITY_QUEUE = "moretv.publish.member.user.authority.queue";
    
    //电视猫会员对于支付网关的产品编号
    String PAY_GATEWAY_PRODUCT_CODE = "tvmore";
}
