package cn.whaley.moretv.member.sync.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;
import cn.whaley.moretv.member.sync.service.goods.GoodsSpuService;
import cn.whaley.moretv.member.sync.service.member.MemberPackageRelationService;
import cn.whaley.moretv.member.sync.service.member.MemberProgramRelationService;
import cn.whaley.moretv.member.sync.service.member.MemberService;

@RestController
@RequestMapping("/resetRedis")
public class ResetRedisController {
    private static final Logger logger = LoggerFactory.getLogger(ResetRedisController.class); 

    @Autowired
    private GoodsService goodsService;
    
    @Autowired
    private GoodsSpuService goodsSpuService;
    
    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberProgramRelationService memberProgramRelationService;
    
    @Autowired
    private MemberPackageRelationService memberPackageRelationService;
    
    @RequestMapping(value = "/doReset")
    public ResultResponse reset(String type, String programCode) {
        if(StringUtils.isEmpty(type)){
            return ResultResponse.failed();
        }
        
        Map<String, Object> result = null;
        
        switch(type){
            case "goods":
                result = goodsService.resetRedis();
                break;
            case "goodsSpu":
                result = goodsSpuService.resetRedis();
                break;
            case "member":
                result = memberService.resetRedis();
                break;
            case "product":
                if(StringUtils.isEmpty(programCode)){
                    result = getFailedMap();
                    break;
                }else
                    result = memberProgramRelationService.resetRedis(programCode);
                break;
            case "productPackage":
                result = memberPackageRelationService.resetRedis();
                break;
            default:
                result = getFailedMap();
                break;
        }
        
        return ResultResponse.success(result);
    }
    
    private Map<String, Object> getFailedMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("note", "wrong params!!!");
        return result;
    }
}
