package cn.whaley.moretv.member.order.dto.pay;

public class OrderPayResponse {
    private String content;

    public OrderPayResponse(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
