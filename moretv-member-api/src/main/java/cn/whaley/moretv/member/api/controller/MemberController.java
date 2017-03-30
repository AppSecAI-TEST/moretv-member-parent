package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.service.member.MemberService;
import cn.whaley.moretv.member.base.res.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员接口
 *
 * Created by Bob Jiang on 2017/3/30.
 */
@RestController
@RequestMapping("/member_api")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * <p>获取用户会员订购续费列表</p>
     *
     * @param baseRequest
     * @return
     */
    @RequestMapping(value = "/get_all_member_info", method = RequestMethod.POST)
    public ResultResponse getAllMemberInfo(BaseRequest baseRequest) {
        try {
            return memberService.getAllMemberInfo(baseRequest.getAccountId());
        } catch (Exception e) {
            return ResultResponse.failed();
        }
    }
}
