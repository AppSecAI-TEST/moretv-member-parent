package cn.whaley.moretv.member.service.member;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.member.Member;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * Created by Bob Jiang on 2017/3/30.
 */
public interface BaseMemberService extends GenericService<Member, Integer> {

    /**
     * 查询所有会员模型 base service
     *
     * @return
     */
    List<Member> getMemberList();

}
