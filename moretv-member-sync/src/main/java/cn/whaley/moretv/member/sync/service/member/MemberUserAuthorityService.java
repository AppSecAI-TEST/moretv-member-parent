package cn.whaley.moretv.member.sync.service.member;

import java.util.Map;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;

public interface MemberUserAuthorityService extends GenericService<MemberUserAuthority, Integer> {

    Map<String, Object> resetRedis(String accountId);
}