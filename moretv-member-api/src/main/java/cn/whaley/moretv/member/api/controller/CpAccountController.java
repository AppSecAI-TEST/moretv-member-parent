package cn.whaley.moretv.member.api.controller;

import cn.whaley.moretv.member.api.service.cp.CpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;

@RestController
@RequestMapping("/cp_api")
public class CpAccountController {

    @Autowired
    private CpAccountService cpAccountService;
    
    /**
     * 获取用户的第三方账号信息
     * @param baseRequest
     * @return
     */
    @RequestMapping(value = "/get_cp_info", method = RequestMethod.POST)
    public ResultResponse getCpInfo(BaseRequest baseRequest) {
        return cpAccountService.getCpAccountInfo(baseRequest);
    }
}
