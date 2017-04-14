package cn.whaley.moretv.member.order.dto.pay;

public class OrderPayResponse {
  //订单号
    private String orderNo;
    
    //支付信息内容
    private String content;
    
    //支付二维码信息
    private String payInfo;
    
    //轮询接口
    private String loopUrl;
    
    //附加参数
    private String extraParam;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getLoopUrl() {
        return loopUrl;
    }

    public void setLoopUrl(String loopUrl) {
        this.loopUrl = loopUrl;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }
}
