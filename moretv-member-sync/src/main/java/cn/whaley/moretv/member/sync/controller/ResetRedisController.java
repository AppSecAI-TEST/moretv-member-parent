package cn.whaley.moretv.member.sync.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.sync.listener.GoodsListener;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;

@RestController
@RequestMapping("/resetRedis")
public class ResetRedisController {
    private static final Logger logger = LoggerFactory.getLogger(ResetRedisController.class); 

    @Autowired
    private GoodsService goodsService;
    
    @RequestMapping(value = "/doReset")
    public ResultResponse reset(String type) {
        if(StringUtils.isEmpty(type)){
            return ResultResponse.failed();
        }
        
        switch(type){
            case "goods":
                goodsService.resetRedis();
                break;
            default:
                break;
        }
        
        return ResultResponse.success();
    }
}
