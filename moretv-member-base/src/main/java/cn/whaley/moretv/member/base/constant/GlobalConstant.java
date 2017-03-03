package cn.whaley.moretv.member.base.constant;

/**
 * Created by tangzc on 2015/9/18.
 */
public interface GlobalConstant {

    int CP_ORDERSTUTAS_COMPLETE = 2;
    
    String UNIT_YUAN = "元";
    
    String CP_TENCENT = "tencent"; 
    
    String WHALEY_SERVICE_ID = "111";

    //商品队列
    String MORETV_PUBLISH_GOODS_ROUTER_KEY = "moretv.publish_goods_router";

    //商品模型队列
    String MORETV_PUBLISH_GOODS_SPU_ROUTER_KEY = "moretv.publish_goods_spu_router";
    
    //会员模型队列
    String MORETV_PUBLISH_MEMBER_QUEUE = "moretv.publish.member.queue";
    
    //片库队列
    String MORETV_PUBLISH_CONTENT_PRODUCT_QUEUE = "moretv.publish.member.content.product.queue";

}
