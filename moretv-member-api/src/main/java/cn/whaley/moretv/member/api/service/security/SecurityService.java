package cn.whaley.moretv.member.api.service.security;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;

/**
 * Created by Bob Jiang on 2017/4/20.
 */
public interface SecurityService {

    ResultResponse authentication(String accountId, String cp, String videoInfo, String memberCode);

    ResultResponse authenticationNoLogin(String cp, String videoInfo);

}
