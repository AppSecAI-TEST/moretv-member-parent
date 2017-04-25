package cn.whaley.moretv.member.base.constant;

/**
 * Created by tangzc on 2015/9/18.
 */
public interface GlobalConstant {
    
    String UNIT_YUAN = "元";
    
    String CP_TENCENT = "tencent"; 
    
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

    //电视猫会员对于支付网关的产品编号
    String PAY_GATEWAY_PRODUCT_CODE = "tvmore";
}
