package cn.whaley.moretv.member.sync.service.member;

import cn.whaley.moretv.member.service.member.BaseMemberService;
import cn.whaley.moretv.member.sync.dto.goods.MemberDto;

/**
* Service: MemberService
* Mapper : MemberMapper
* Model  : Member
*
* This Service generated by MyBatis Generator Extend at 2017-03-02 17:00:35
*/
public interface MemberService extends BaseMemberService {

    void sync(MemberDto memberDto);
}