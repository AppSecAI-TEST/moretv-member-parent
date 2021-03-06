package cn.whaley.moretv.member.api.service.member.impl;

import cn.whaley.moretv.member.api.dto.member.MemberInfoResponse;
import cn.whaley.moretv.member.api.dto.member.MemberStatusResponse;
import cn.whaley.moretv.member.api.service.member.MemberService;
import cn.whaley.moretv.member.api.service.member.MemberUserAuthorityService;
import cn.whaley.moretv.member.api.util.OrderingUtil;
import cn.whaley.moretv.member.api.util.ResponseHandler;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.model.member.Member;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;
import cn.whaley.moretv.member.service.member.impl.BaseMemberServiceImpl;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
* ServiceImpl: MemberServiceImpl
* Mapper : MemberMapper
* Model  : Member
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-03-30 17:05:25
*/
@Service
@Transactional
public class MemberServiceImpl extends BaseMemberServiceImpl implements MemberService {

    @Autowired
    private MemberUserAuthorityService memberUserAuthorityService;

    @Override
    public ResultResponse<List<MemberStatusResponse>> getAllMemberInfo(String accountId) {
        List<MemberStatusResponse> statusResponseList = Lists.newArrayList();
        Date now = new Date();

        //会员模型
        List<Member> members = getMemberList();
        if (members.isEmpty()) {
            return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
        }

        //会员权益
        List<MemberUserAuthority> authorityList = memberUserAuthorityService.getMemberUserAuthority(accountId);

        for (Member member : members) {
            MemberStatusResponse memberStatus = new MemberStatusResponse();
            memberStatus.setMemberCode(member.getCode());
            memberStatus.setMemberName(member.getName());

            GlobalEnum.MemberStatus status = GlobalEnum.MemberStatus.NOT_OPEN;

            for (MemberUserAuthority authority : authorityList) {
                if (member.getCode().equals(authority.getMemberCode())) {
                    memberStatus.setStartTime(authority.getStartTime());
                    memberStatus.setEndTime(authority.getEffectiveTime());

                    if (authority.getEffectiveTime().after(now)) {
                        status = GlobalEnum.MemberStatus.OPEN;
                    } else {
                        status = GlobalEnum.MemberStatus.EXPIRED;
                    }
                    break;
                }
            }
            memberStatus.setMemberStatus(status.getCode());
            memberStatus.setMemberStatusName(status.getName());
            statusResponseList.add(memberStatus);
        }

        return ResultResponse.success(OrderingUtil.orderingMember(statusResponseList));
    }

    @Override
    public ResultResponse<List<MemberInfoResponse>> getMemberInfo(String accountId) {
        List<MemberInfoResponse> infoResponseList = Lists.newArrayList();
        Date now = new Date();

        List<MemberUserAuthority> authorityList = memberUserAuthorityService.getMemberUserAuthority(accountId);
        if (authorityList.isEmpty()) {
            return ResultResponse.define(ApiCodeEnum.API_DATA_MEMBER_AUTH_NOT_EXIST);
        }

        for (MemberUserAuthority authority : authorityList) {
            //有效会员权益
            if (authority.getEffectiveTime().after(now)) {
                MemberInfoResponse response = ResponseHandler.copyProperties(authority, MemberInfoResponse.class);
                infoResponseList.add(response);
            }
        }
        if (infoResponseList.isEmpty()) {
            return ResultResponse.define(ApiCodeEnum.API_DATA_MEMBER_AUTH_NOT_EXIST);
        }
        return ResultResponse.success(OrderingUtil.orderingMember(infoResponseList));
    }

    @Override
    public Boolean accountIsMember(String accountId) {
        ResultResponse response = getMemberInfo(accountId);
        if (response.isSuccess()) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean accountIsMember(String accountId, String memberCode) {
        ResultResponse<List<MemberInfoResponse>> response = getMemberInfo(accountId);
        if (response.isSuccess()) {
            List<MemberInfoResponse> responseList = response.getData();
            for (MemberInfoResponse memberInfo : responseList) {
                if (memberInfo.getMemberCode().equals(memberCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Boolean accountIsTencentMember(String accountId) {
        ResultResponse<List<MemberInfoResponse>> response = getMemberInfo(accountId);
        if (response.isSuccess()) {
            HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();

            List<MemberInfoResponse> responseList = response.getData();
            for (MemberInfoResponse memberInfo : responseList) {
                String key = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, memberInfo.getMemberCode());
                Long count = opsHash.size(key);
                if (count > 0L) {
                    return true;
                }
            }
        }
        return false;
    }

}