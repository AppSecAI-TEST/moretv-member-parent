package cn.whaley.moretv.member.api.dto.member;

public class MemberCpResponse {
    private String vuid;
    private String vtoken;
    private String cp;
    
    public String getVuid() {
        return vuid;
    }
    public void setVuid(String vuid) {
        this.vuid = vuid;
    }
    public String getVtoken() {
        return vtoken;
    }
    public void setVtoken(String vtoken) {
        this.vtoken = vtoken;
    }
    public String getCp() {
        return cp;
    }
    public void setCp(String cp) {
        this.cp = cp;
    }
    
    @Override
    public String toString() {
        return "MemberCpResponse [vuid=" + vuid + ", vtoken=" + vtoken + ", cp=" + cp + "]";
    }
    
    public MemberCpResponse(String vuid, String vtoken, String cp) {
        this.vuid = vuid;
        this.vtoken = vtoken;
        this.cp = cp;
    }
    
    public MemberCpResponse() {
    }
}
