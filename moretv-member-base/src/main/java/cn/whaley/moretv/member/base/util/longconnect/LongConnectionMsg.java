package cn.whaley.moretv.member.base.util.longconnect;


public class LongConnectionMsg {
    
    private MsgDate data;

    private String msgType;
    
    public class MsgDate {
        private String title;
        
        private String content;
        
        private String createTime;
        
        private String account;
        
        private String displayTime;
        
        private String bgPic;
        
        private String icon;

        public MsgDate(String title, String content, String createTime, String account, String displayTime,
                String bgPic, String icon) {
            this.title = title;
            this.content = content;
            this.createTime = createTime;
            this.account = account;
            this.displayTime = displayTime;
            this.bgPic = bgPic;
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getDisplayTime() {
            return displayTime;
        }

        public void setDisplayTime(String displayTime) {
            this.displayTime = displayTime;
        }

        public String getBgPic() {
            return bgPic;
        }

        public void setBgPic(String bgPic) {
            this.bgPic = bgPic;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @Override
        public String toString() {
            return "MsgDate [title=" + title + ", content=" + content + ", createTime=" + createTime + ", account="
                    + account + ", displayTime=" + displayTime + ", bgPic=" + bgPic + ", icon=" + icon + "]";
        }
        
    }
    
    public enum StatusType {
        EXPIRED("expired", "会员过期"),
        BUY("opened", "会员开通");

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
            for (StatusType aEnum : StatusType.values()) {
                if (aEnum.getCode().equals(code)) {
                    return aEnum.getName();
                }
            }
            return "";
        }
    }
    
    @Override
    public String toString() {
        return "LongConnectionMsg [data=" + data + ", msgType=" + msgType + "]";
    }

    public LongConnectionMsg(String msgType) {
        this.msgType = msgType;
    }

    public LongConnectionMsg() {
    }

    public MsgDate getData() {
        return data;
    }

    public void setData(MsgDate data) {
        this.data = data;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
