package cn.whaley.moretv.member.api.dto.response;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bob Jiang on 2017/3/30.
 */
public class MemberStatusResponse implements Serializable {

    private static final long serialVersionUID = 4118970544462840711L;

    private String memberCode;
    private String memberName;
    private Date startTime;
    private Date endTime;
    private String memberStatus;
    private String memberStatusName;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getMemberStatusName() {
        return memberStatusName;
    }

    public void setMemberStatusName(String memberStatusName) {
        this.memberStatusName = memberStatusName;
    }

    @Override
    public String toString() {
        return "{" +
                "memberCode='" + memberCode + '\'' +
                ", memberName='" + memberName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", memberStatus='" + memberStatus + '\'' +
                ", memberStatusName='" + memberStatusName + '\'' +
                '}';
    }
}
