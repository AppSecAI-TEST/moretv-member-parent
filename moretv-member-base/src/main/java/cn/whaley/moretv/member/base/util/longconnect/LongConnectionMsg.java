package cn.whaley.moretv.member.base.util.longconnect;

import cn.whaley.moretv.member.base.constant.GlobalEnum.StatusText;

public class LongConnectionMsg {
    private String title;
    
    private Integer[] accoundId;
    
    private String content;
    
    private String type;
    
    private Long time;
    
    public enum StatusType {
        EXPIRED("expired", "会员过期"),
        BUY("buy", "购买");

        private String code;
        private String name;

        StatusType (String code, String name) {
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
            return "";
        }
    }

    
    public LongConnectionMsg(String title, Integer[] accoundId, String content, String type, Long time) {
        this.title = title;
        this.accoundId = accoundId;
        this.content = content;
        this.type = type;
        this.time = time;
    }

    public LongConnectionMsg() {
    }

    @Override
    public String toString() {
        return "LongConnectionMsg [title=" + title + ", accoundId=" + accoundId + ", content=" + content + ", type="
                + type + ", time=" + time + "]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer[] getAccoundId() {
        return accoundId;
    }

    public void setAccoundId(Integer[] accoundId) {
        this.accoundId = accoundId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
