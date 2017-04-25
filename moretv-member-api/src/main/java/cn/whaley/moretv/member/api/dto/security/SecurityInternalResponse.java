package cn.whaley.moretv.member.api.dto.security;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2017/4/20.
 */
public class SecurityInternalResponse implements Serializable {

    private static final long serialVersionUID = 3207555924983993110L;

    private String cp;                  //源名称
    private String internalAccessToken; //内部鉴权token（香菇和蘑菇源才有值）

    public SecurityInternalResponse() { }

    public SecurityInternalResponse(String cp, String internalAccessToken) {
        this.cp = cp;
        this.internalAccessToken = internalAccessToken;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getInternalAccessToken() {
        return internalAccessToken;
    }

    public void setInternalAccessToken(String internalAccessToken) {
        this.internalAccessToken = internalAccessToken;
    }

    @Override
    public String toString() {
        return "{cp='" + cp + '\'' +
                ", internalAccessToken='" + internalAccessToken + '\'' +
                '}';
    }
}
