package cn.whaley.moretv.member.notify.service.member;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;

public interface MemberOpsService {
	
	ResultResponse deliveryMemberByOrderId(Integer orderId);
}
