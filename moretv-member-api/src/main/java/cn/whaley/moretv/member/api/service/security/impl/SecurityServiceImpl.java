package cn.whaley.moretv.member.api.service.security.impl;

import cn.whaley.moretv.member.api.dto.security.SecurityInternalResponse;
import cn.whaley.moretv.member.api.dto.security.SecurityOutSideResponse;
import cn.whaley.moretv.member.api.service.member.MemberService;
import cn.whaley.moretv.member.api.service.security.SecurityService;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.exception.SystemException;
import cn.whaley.moretv.member.base.manager.ExternalManage;
import cn.whaley.moretv.member.base.manager.MsdManage;
import cn.whaley.moretv.member.model.member.MemberProgramRelation;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


/**
 * Created by Bob Jiang on 2017/4/20.
 */
@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MemberService memberService;

    @Override
    public ResultResponse authentication(Integer accountId, String cp, String videoInfo, String memberCode) {
        validate(cp, videoInfo);

        ResultResponse<MemberProgramRelation> vipResponse = isProgramVIP(videoInfo);
        if (vipResponse.isSuccess()) {
            // 如果节目是VIP，则需要进行账号判断
            // 查询出账号是否有节目所绑定的会员权益
            // 如果有，根据CP来鉴权
            // 如果没有，error
            MemberProgramRelation relation = vipResponse.getData();
            Boolean isMember = memberService.accountIsMember(accountId, relation.getMemberCode());
            if (isMember) {
                return getAccessToken(accountId, cp, videoInfo);
            } else {
                return ResultResponse.define(ApiCodeEnum.API_SECURITY_ERR_MEMBER_NO_AUTH);
            }
        } else {
            // 如果节目是免费的，再判断CP，根据CP来鉴权
            // 如果CP为腾讯，则需要判断账号是否有腾讯会员
            // 如果有，根据CP来鉴权
            // 如果没有，返回空
            if (GlobalConstant.CP_TENCENT.equals(cp)) {
                Boolean isMember = isTencentMember(accountId);
                if (!isMember) {
                    accountId = null;
                }
                return getAccessToken(accountId, cp, videoInfo);
            } else {
                return getAccessToken(null, cp, videoInfo);
            }
        }
    }

    @Override
    public ResultResponse authenticationNoLogin(String cp, String videoInfo) {
        validate(cp, videoInfo);

        if (isProgramVIP(videoInfo).isSuccess()) {
            // 如果节目是VIP，error
            return ResultResponse.define(ApiCodeEnum.API_SECURITY_ERR_PROGRAM_VIP);
        } else {
            // 如果节目是免费的，则根据CP来鉴权，其他CP返回空
            if (GlobalConstant.CP_TENCENT.equals(cp)) {
                return ResultResponse.success(new SecurityInternalResponse(cp, ""));
            }
            return getAccessToken(null, cp, videoInfo);
        }
    }

    /**
     * 查询节目是否为VIP
     * @param videoInfo
     * @return
     */
    private ResultResponse<MemberProgramRelation> isProgramVIP(String videoInfo) {
        ResultResponse<MemberProgramRelation> response = ResultResponse.failed();
        ValueOperations<String, String> opsValue = redisTemplate.opsForValue();

        String key = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PROGRAM_RELATION, videoInfo);
        String value = opsValue.get(key);

        if (!StringUtils.isEmpty(value)) {
            response = ResultResponse.success(JSON.parseObject(value, MemberProgramRelation.class));
        }
        return response;
    }

    private Boolean isTencentMember(Integer accountId) {
        return memberService.accountIsTencentMember(accountId);
    }

    /**
     * 鉴权 节目AccessToken
     *
     * @param cp
     * @param videoInfo
     * @return
     */
    private ResultResponse getAccessToken(Integer accountId, String cp, String videoInfo) {
        String accessToken = "";

        if (GlobalConstant.CP_TENCENT.equals(cp)) {
            if (accountId != null) {
                accessToken = ExternalManage.getAccessToken();
            }
            return ResultResponse.success(new SecurityOutSideResponse(cp, 2, "", accessToken));
        } else if (GlobalConstant.CP_MOGUV.equals(cp) || GlobalConstant.CP_XIANGGU.equals(cp)) {
            accessToken = MsdManage.getInternalAccessToken(cp, videoInfo);
            if (accountId != null) {
                return ResultResponse.success(new SecurityOutSideResponse(cp, 1, accessToken, ""));
            }
        }

        return ResultResponse.success(new SecurityInternalResponse(cp, accessToken));
    }

    /**
     * 参数校验
     *
     * @param cp
     * @param videoInfo
     */
    private void validate(String cp, String videoInfo) {
        if (StringUtils.isEmpty(cp)) {
            throw new SystemException(ApiCodeEnum.API_PARAM_CP_NULL);
        }
        if (StringUtils.isEmpty(videoInfo)) {
            throw new SystemException(ApiCodeEnum.API_PARAM_VIDEOINFO_NULL);
        }
    }
}
