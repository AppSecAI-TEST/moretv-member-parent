package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.service.security.SecurityService;
import cn.whaley.moretv.member.base.annotation.ValidateIgnore;
import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Bob Jiang on 2017/4/20.
 */
@RestController
@RequestMapping("/security_api")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    /**
     * <p>10. 用户登录播放鉴权接口</p>
     *
     * @param baseRequest
     * @param cp
     * @param videoInfo
     * @return
     */
    @RequestMapping("authentication")
    public ResultResponse authentication(BaseRequest baseRequest, @RequestParam String cp, @RequestParam String videoInfo) {
        return securityService.authentication(baseRequest.getAccountId(), cp, videoInfo, null);
    }

    /**
     * <p>9.非用户登录播放鉴权接口</p>
     *
     * @param baseRequest
     * @param cp
     * @param videoInfo
     * @return
     */
    @RequestMapping("authentication_no_login")
    public ResultResponse authenticationNoLogin(@ValidateIgnore BaseRequest baseRequest,
            @RequestParam String cp, @RequestParam String videoInfo) {
        return securityService.authenticationNoLogin(cp, videoInfo);
    }
}
