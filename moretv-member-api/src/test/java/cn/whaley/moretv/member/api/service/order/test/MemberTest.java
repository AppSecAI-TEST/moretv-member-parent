package cn.whaley.moretv.member.api.service.order.test;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.mapper.member.MemberUserAuthorityMapper;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberUserAuthorityMapper memberUserAuthorityMapper;

    @Autowired
    RedisTemplate redisTemplate;
    
    @Test
    public void testCreateMember() {
        String accountId = "100001";
        String memberCode = "member_tag";
        MemberUserAuthority userAuthority = new MemberUserAuthority();
        userAuthority.setAccountId(accountId);
        userAuthority.setMemberCode(memberCode);
        userAuthority.setMemberName("会员模型_白猫片库");
        userAuthority.setStartTime(DateFormatUtil.stringFormatDateTime("2017-05-02 16:10:00"));
        userAuthority.setEndTime(DateFormatUtil.stringFormatDateTime("2018-05-02 16:10:00"));
        userAuthority.setCreateTime(new Date());
        userAuthority.setStatus(GlobalEnum.Status.VALID.getCode());
        memberUserAuthorityMapper.insert(userAuthority);

        String key = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_AUTHORITY, accountId.toString());
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        opsHash.put(key, memberCode, JSON.toJSONString(userAuthority));
    }
    
}
