package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.service.member.MemberService;
import cn.whaley.moretv.member.base.res.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order_api")
public class OrderController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value = "/get_all_member_info", method = RequestMethod.POST)
    public ResultResponse getAllMemberInfo(BaseRequest baseRequest) {
        try {
            return memberService.getAllMemberInfo(baseRequest.getAccountId());
        } catch (Exception e) {
            return ResultResponse.failed();
        }
    }
}
