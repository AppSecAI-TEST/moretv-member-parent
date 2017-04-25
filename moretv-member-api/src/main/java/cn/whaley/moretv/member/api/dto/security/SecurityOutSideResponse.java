package cn.whaley.moretv.member.api.dto.security;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2017/4/20.
 */
public class SecurityOutSideResponse implements Serializable {

    private static final long serialVersionUID = 4445181204942318846L;

    private String cp;                  //源名称
    private Integer programType = 0;    //0免费，1内部付费，2外部付费
    private String internalAccessToken; //内部鉴权token（香菇和蘑菇源才有值）
    private String outsideAccessToken;  //外部鉴权token（播腾讯源确有腾讯会员时才有值）

    public SecurityOutSideResponse() { }

    public SecurityOutSideResponse(String cp, Integer programType, String internalAccessToken, String outsideAccessToken) {
        this.cp = cp;
        this.programType = programType;
        this.internalAccessToken = internalAccessToken;
        this.outsideAccessToken = outsideAccessToken;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public Integer getProgramType() {
        return programType;
    }

    public void setProgramType(Integer programType) {
        this.programType = programType;
    }

    public String getInternalAccessToken() {
        return internalAccessToken;
    }

    public void setInternalAccessToken(String internalAccessToken) {
        this.internalAccessToken = internalAccessToken;
    }

    public String getOutsideAccessToken() {
        return outsideAccessToken;
    }

    public void setOutsideAccessToken(String outsideAccessToken) {
        this.outsideAccessToken = outsideAccessToken;
    }

    @Override
    public String toString() {
        return "{ cp='" + cp + '\'' +
                ", programType=" + programType +
                ", internalAccessToken='" + internalAccessToken + '\'' +
                ", outsideAccessToken='" + outsideAccessToken + '\'' +
                '}';
    }
}
