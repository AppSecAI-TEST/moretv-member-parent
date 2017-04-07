package cn.whaley.moretv.member.api.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Bob Jiang on 2017/3/30.
 */
public class MemberInfoResponse implements Serializable {

    private static final long serialVersionUID = 4603140529709246538L;

    private String memberCode;
    private String memberName;
    private Date startTime;
    private Date endTime;

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

    @Override
    public String toString() {
        return "{" +
                "memberCode='" + memberCode + '\'' +
                ", memberName='" + memberName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
