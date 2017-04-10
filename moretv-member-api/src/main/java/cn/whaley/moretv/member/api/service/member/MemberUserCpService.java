package cn.whaley.moretv.member.api.service.member;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.member.MemberUserCp;



public interface MemberUserCpService extends GenericService<MemberUserCp, Integer> {

    ResultResponse getCpInfo(BaseRequest baseRequest);

}