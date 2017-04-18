package cn.whaley.moretv.member.base.dto.pay.gateway;

/**
 * 向支付网关请求支付的返回值
 */
public class PayGatewayResponse {
    /**
     * 返回状态:1：成功 -1：参数校验失败 -2：订单不存在或订单金额不匹配 -3：支付方式错误 -99：未知异常
     */
    private Integer status;
    
    /**
     * 状态描述
     */
    private String msg;
    
    /**
     * 支付宝支付需要的数据
     */
    private String content;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PayGatewayResponse [status=" + status + ", msg=" + msg + ", content=" + content + "]";
    }
}
