package cn.whaley.moretv.member.api.service.member;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;

import java.util.List;


/**
* Service: MemberUserAuthorityService
* Mapper : MemberUserAuthorityMapper
* Model  : MemberUserAuthority
*
* This Service generated by MyBatis Generator Extend at 2017-03-30 17:05:25
*/
public interface MemberUserAuthorityService extends GenericService<MemberUserAuthority, Integer> {

    /**
     * 查询用户的会员权益，包含所有状态
     *
     * @param accountId
     * @return
     */
    List<MemberUserAuthority> getMemberUserAuthority(Integer accountId);
}