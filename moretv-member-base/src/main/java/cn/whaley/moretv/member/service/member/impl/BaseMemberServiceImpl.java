package cn.whaley.moretv.member.service.member.impl;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.model.member.Member;
import cn.whaley.moretv.member.service.member.BaseMemberService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Bob Jiang on 2017/3/30.
 */
public abstract class BaseMemberServiceImpl extends GenericServiceImpl<Member, Integer> implements BaseMemberService {

    @Override
    public List<Member> getMemberList() {
        List<Member> members = Lists.newArrayList();

        HashOperations<String, String, String> opsHash = getRedisTemplate().opsForHash();
        Map<String, String> map = opsHash.entries(CacheKeyConstant.REDIS_KEY_MEMBER);

        if (CollectionUtils.isEmpty(map)) {
            return members;
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            Member member = JSON.parseObject(entry.getValue(), Member.class);
            members.add(member);
        }
        return members;
    }

}
