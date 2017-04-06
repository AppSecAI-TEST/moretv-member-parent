package cn.whaley.moretv.member.base.constant;


/**
 * Created by Bob Jiang on 2016/4/5.
 */
public interface OrderEnum {

    String UNKNOWN = "";

    /**
     * 订单业务类型
     */
    enum BusinessType {

        PREGIVE(1, "预设领取"),
        EXCHANGE(2, "兑换码兑换"),
        PAY(3, "支付开通"),
        GIVE(4, "赠送"),
        OTHER(0, "其他");

        private int code;
        private String name;

        BusinessType (int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(int code) {
            for (BusinessType aEnum : BusinessType.values()) {
                if (aEnum.getCode() == code) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    /**
     * 支付渠道
     */
    enum PayChannel {

        ALIPAY("alipay", "支付宝"),
        WECHAT("wechat", "微信");

        private String code;
        private String name;

        PayChannel (String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(String code) {
            for (PayChannel aEnum : PayChannel.values()) {
                if (aEnum.getCode().equals(code)) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    /**
     * 订单渠道
     */
    enum OrderChannel {

        WAP(1),
        TV(2),
        APP(3),
        OTHER(0);

        private int code;

        OrderChannel (int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

    }

    /**
     * 订单类型
     */
    enum OrderType {

        BUY(1, "购买"),
        REPLACEMENT(2, "换货"),
        ACTIVITY(3, "活动"),
        OTHER(0, "其他");

        private int code;
        private String name;

        OrderType (int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(int code) {
            for (OrderType aEnum : OrderType.values()) {
                if (aEnum.getCode() == code) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    /**
     * 交易状态
     */
    enum TradeStatus {

        WAITING_SEND(1, "待发货"),
        WAITING_PAY(2, "待支付"),
        TRADE_FINISHED(3, "交易完成"),
        TRADE_FAILED(4, "交易失败");

        private int code;
        private String name;

        TradeStatus (int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(int code) {
            for (TradeStatus aEnum : TradeStatus.values()) {
                if (aEnum.getCode() == code) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    /**
     * 支付状态
     */
    enum PayStatus {

        WAITING_PAY(1, "待支付"),
        PAYING(2, "支付中"),
        DONE(3, "支付完成"),
        TIMEOUT(4, "支付超时");

        private int code;
        private String name;

        PayStatus (int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public static String getNameByCode(int code) {
            for (PayStatus aEnum : PayStatus.values()) {
                if (aEnum.getCode() == code) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

}
