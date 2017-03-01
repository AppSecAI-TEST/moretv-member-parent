package cn.whaley.moretv.member.base.constant;


/**
 * Created by Bob Jiang on 2016/12/14.
 */
public interface GlobalEnum {

    String UNKNOWN = "";

    enum Status {
        DELETE(0, "无效"),
        VALID(1, "有效");

        private int code;
        private String name;

        Status (int code, String name) {
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
            for (Status aEnum : Status.values()) {
                if (aEnum.getCode() == code) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    enum StatusText {
        DELETE("delete", "无效"),
        VALID("valid", "有效"),
        PUBLISHED("published", "已发布"),
        OFFSHEIF("offsheif", "下架");

        private String code;
        private String name;

        StatusText (String code, String name) {
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
            for (StatusText aEnum : StatusText.values()) {
                if (aEnum.getCode().equals(code)) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    enum Bound {
        BOUND("bound", "绑定"),
        DELETE("delete", "删除"),
        UNBOUND("unbound", "未绑");

        private String code;
        private String name;

        Bound (String code, String name) {
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
            for (Bound aEnum : Bound.values()) {
                if (aEnum.getCode().equals(code)) {
                    return aEnum.getName();
                }
            }
            return UNKNOWN;
        }
    }

    enum CompositedType {
        SINGLE(1, "独立"),
        MERGE(2, "组合");

        private int code;
        private String name;

        CompositedType (int code, String name) {
            this.code = code;
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    enum GoodsType {

        NORMAL_GOODS("购买", 1, "gm"),
        INTERNAL_FOR_FREE_GOODS("兑换码(免费)", 2, "dm"),
        INTERNAL_FOR_SALE_GOODS("兑换码(销售)", 3, "dx"),
        INTERNAL_GOODS("内置", 4, "nz"),
        UPGRADE_GOODS("升级", 5, "sj"),
        RENEWAL_GOODS("续费", 6, "xf"),
        ACTIVITY_GOODS("活动", 7, "hd");

        public static GoodsType getGoodsTypeEnum(int value) {
            for (GoodsType aEnum : GoodsType.values()) {
                if (aEnum.getValue() == value) {
                    return aEnum;
                }
            }
            return null;
        }

        private String name;
        private int value;
        private String code;

        GoodsType(String name, int value, String code) {
            this.name = name;
            this.value = value;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }

        public String getCode() {
            return code;
        }
    }

}
