package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.service.security.SecurityService;
import cn.whaley.moretv.member.base.annotation.ValidateIgnore;
import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bob Jiang on 2017/4/20.
 */
@RestController
@RequestMapping("/security_api")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping("authentication")
    public ResultResponse authentication(BaseRequest baseRequest,
                                         String cp, String videoInfo, String memberCode) {
        return securityService.authentication(baseRequest.getAccountId(), cp, videoInfo, memberCode);
    }

    @RequestMapping("authentication_no_login")
    public ResultResponse authenticationNoLogin(@ValidateIgnore BaseRequest baseRequest,
            String cp, String videoInfo) {
        return securityService.authenticationNoLogin(cp, videoInfo);
    }
}
