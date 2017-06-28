package cn.whaley.moretv.member.sync.service.member.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.member.MemberUserAuthorityMapper;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;
import cn.whaley.moretv.member.sync.service.member.MemberUserAuthorityService;
import cn.whaley.moretv.member.sync.util.RedisResetResponseUtil;

@Service
@Transactional
public class MemberUserAuthorityServiceImpl extends GenericServiceImpl<MemberUserAuthority, Integer, MemberUserAuthorityMapper> implements MemberUserAuthorityService {

    private static final Logger logger = LoggerFactory.getLogger(MemberUserAuthorityServiceImpl.class);
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private MemberUserAuthorityMapper memberUserAuthorityMapper;

    @Override
    public MemberUserAuthorityMapper getGenericMapper() {
        return memberUserAuthorityMapper;
    }


    @Override
    public Map<String, Object> resetRedis(String accountId) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        List<String> addList = new ArrayList<>();
        List<String> deleteList = new ArrayList<>();
        List<String> accountIdList = Arrays.asList(accountId.split(","));

        //1、根据传入的用户id查询出所有有效记录
        Map<String, Object> params = new HashMap<>();
        params.put("accountIdList", accountIdList);
        params.put("status", GlobalEnum.Status.VALID.getCode());
        List<MemberUserAuthority> memberUserAuthorityList = memberUserAuthorityMapper.listByAccountIdAndStatus(params);
        
        //3、遍历通过用户ID，查询redis hash下所有的key，一个个清除
        for(String account : accountIdList){
            String bigKey = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_AUTHORITY, account);
            Set<String> smallKeys = opsHash.keys(bigKey);
            for(String smallKey : smallKeys){
                opsHash.delete(bigKey, smallKey);
                
                String temp = account + "->" + smallKey;
                deleteList.add(temp);
                logger.info("reset memberAuthority, clear memberAuthority->{}", temp);
            }
        }
        
        //4、遍历，重新将有效的写入redis
        for(MemberUserAuthority memberUserAuthority : memberUserAuthorityList){
            String key = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_AUTHORITY, memberUserAuthority.getAccountId());
            opsHash.put(key, memberUserAuthority.getMemberCode(), JSON.toJSONString(memberUserAuthority));
            
            String temp = memberUserAuthority.getAccountId() + "->" + memberUserAuthority.getMemberCode();
            addList.add(temp);
            logger.info("reset memberAuthority, add memberAuthority->{}", temp);
                
        }
        
        return RedisResetResponseUtil.getResetRedisMap(addList, deleteList);
    }

}