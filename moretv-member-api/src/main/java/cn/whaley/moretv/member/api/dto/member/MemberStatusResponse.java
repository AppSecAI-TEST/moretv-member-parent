package cn.whaley.moretv.member.api.dto.member;

import java.io.Serializable;

/**
 * Created by Bob Jiang on 2017/3/30.
 */
public class MemberStatusResponse extends MemberInfoResponse implements Serializable {

    private static final long serialVersionUID = 4118970544462840711L;

    private String memberStatus;
    private String memberStatusName;

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
                "memberCode='" + getMemberCode() + '\'' +
                ", memberName='" + getMemberName() + '\'' +
                ", startTime=" + getStartTime() +
                ", endTime=" + getEndTime() +
                ", memberStatus='" + memberStatus + '\'' +
                ", memberStatusName='" + memberStatusName + '\'' +
                '}';
    }
}
