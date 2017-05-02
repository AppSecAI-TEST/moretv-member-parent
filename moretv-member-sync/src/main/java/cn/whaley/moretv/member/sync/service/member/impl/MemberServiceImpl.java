package cn.whaley.moretv.member.sync.service.member.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.mapper.member.MemberPackageRelationMapper;
import cn.whaley.moretv.member.model.member.Member;
import cn.whaley.moretv.member.model.member.MemberPackageRelation;
import cn.whaley.moretv.member.service.member.impl.BaseMemberServiceImpl;
import cn.whaley.moretv.member.sync.dto.goods.MemberCppr;
import cn.whaley.moretv.member.sync.dto.goods.MemberDto;
import cn.whaley.moretv.member.sync.dto.goods.MemberMpr;
import cn.whaley.moretv.member.sync.service.member.MemberService;

/**
* ServiceImpl: MemberServiceImpl
* Mapper : MemberMapper
* Model  : Member
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-03-02 17:00:35
*/
@Service
@Transactional
public class MemberServiceImpl extends BaseMemberServiceImpl implements MemberService {

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);


    @Override
    public void sync(MemberDto memberDto) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        
        //1、处理会员模型
        Member member = memberMapper.selectByCode(memberDto.getMemberCode());
        member = dealWithMember(member, memberDto, opsHash);
        
    }

    

    /**
     * 处理会员模型
     * @param member
     * @param memberDto
     */
    private Member dealWithMember(Member member, MemberDto memberDto, HashOperations<String, String, String> opsHash) {
        StringBuffer sb = new StringBuffer();
        List<MemberMpr> mprList = memberDto.getMprList();
        for(int i = 0; i < mprList.size(); i++){
            MemberMpr mm = mprList.get(i);
            sb.append(mm.getProductCode());
            if(i < (mprList.size() - 1))
                sb.append(",");
        }
        
        Date d = new Date();
        if(member == null){
            member = new Member();
            member.setCode(memberDto.getMemberCode());
            member.setCreateTime(d);
            member.setName(memberDto.getMemberName());
            member.setRemark(memberDto.getRemark());
            member.setStatus(memberDto.getMemberStatus());
            member.setUpdateTime(d);
            member.setProductCode(sb.toString());
            memberMapper.insertSelective(member);
            logger.info("mq.listen.member->insert member->{}",member.toString());
        }else{
            member.setName(memberDto.getMemberName());
            member.setRemark(memberDto.getRemark());
            member.setStatus(memberDto.getMemberStatus());
            member.setUpdateTime(d);
            member.setProductCode(sb.toString());
            memberMapper.updateByPrimaryKeySelective(member);
            logger.info("mq.listen.member->update member->{}",member.toString());
        }   
        
        if(member.getStatus().equals(GlobalEnum.StatusText.PUBLISHED.getCode()))
            opsHash.put(CacheKeyConstant.REDIS_KEY_MEMBER, member.getCode(), JSON.toJSONString(member));
        else
            opsHash.delete(CacheKeyConstant.REDIS_KEY_MEMBER, member.getCode());
        
        return member;
    }

}