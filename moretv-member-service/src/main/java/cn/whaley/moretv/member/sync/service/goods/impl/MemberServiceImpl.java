package cn.whaley.moretv.member.sync.service.goods.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.goods.MemberMapper;
import cn.whaley.moretv.member.mapper.goods.MemberPackageRelationMapper;
import cn.whaley.moretv.member.model.goods.Member;
import cn.whaley.moretv.member.model.goods.MemberPackageRelation;
import cn.whaley.moretv.member.sync.dto.goods.MemberCppr;
import cn.whaley.moretv.member.sync.dto.goods.MemberDto;
import cn.whaley.moretv.member.sync.dto.goods.MemberMpr;
import cn.whaley.moretv.member.sync.service.goods.MemberService;

/**
* ServiceImpl: MemberServiceImpl
* Mapper : MemberMapper
* Model  : Member
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-03-02 17:00:35
*/
@Service
@Transactional
public class MemberServiceImpl extends GenericServiceImpl<Member, Integer> implements MemberService {
    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private MemberPackageRelationMapper memberPackageRelationMapper;

    @Override
    public GenericMapper<Member, Integer> getGenericMapper() {
        return memberMapper;
    }

    @Override
    public void sync(MemberDto memberDto) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        
        //1、处理会员模型
        Member member = memberMapper.selectByCode(memberDto.getMemberCode());
        member = dealWithMember(member, memberDto, opsHash);
        
        //2、处理 会员模型-节目包
        dealWithMemberPackage(member,memberDto, opsHash);
        
    }

    /**
     * 处理会员模型-节目包
     * @param member
     * @param memberDto
     */
    private void dealWithMemberPackage(Member member, MemberDto memberDto, HashOperations<String, String, String> opsHash) {
        List<MemberCppr> cpprListNew = memberDto.getCpprList();
        List<MemberPackageRelation> mpprListOld = memberPackageRelationMapper.listByMemberCode(member.getCode());
        boolean isExist = false;
        
        for(MemberCppr cpprNew : cpprListNew){
            //遍历新的
            isExist = false;
            for(MemberPackageRelation mpprOld : mpprListOld){
                if(cpprNew.getPackageCode().equals(mpprOld.getPackageCode())){
                    //新的在数据库里已经存在了,更新数据库中的
                    copyCommonProperties(cpprNew, mpprOld, member);
                    memberPackageRelationMapper.updateByPrimaryKeySelective(mpprOld);
                    isExist = true;
                    logger.info("mq.listen.member->update memberPackageRelation->{}",mpprOld.toString());
                    
                    opsHash.put(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, mpprOld.getMemberCode() + ":" +  
                            mpprOld.getPackageCode(), JSON.toJSONString(mpprOld));
                }
            }
            
            if(!isExist){
                //接收的不存在数据库中,则新增
                MemberPackageRelation memberPackageRelation = new MemberPackageRelation();
                copyCommonProperties(cpprNew, memberPackageRelation, member);
                memberPackageRelation.setMemberCode(member.getCode());
                memberPackageRelation.setPackageCode(cpprNew.getPackageCode());
                memberPackageRelation.setCreateTime(new Date());
               
                memberPackageRelationMapper.insertSelective(memberPackageRelation);
                logger.info("mq.listen.member->insert memberPackageRelation->{}",memberPackageRelation.toString());
                
                opsHash.put(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, memberPackageRelation.getMemberCode() + ":" +  
                        memberPackageRelation.getPackageCode(), JSON.toJSONString(memberPackageRelation));
            }
        }
        
        for(MemberPackageRelation mpprOld : mpprListOld){
            isExist = false;
            for(MemberCppr cpprNew : cpprListNew){
                if(cpprNew.getPackageCode().equals(mpprOld.getPackageCode())){
                    //遍历老的，如果老的存在新的中，那么前面肯定已经处理过了
                    isExist = true;
                }
            }
            
            if(!isExist){
                //如果老的不在新的里面，则删除
                mpprOld.setStatus(GlobalEnum.Bound.UNBOUND.getCode());
                mpprOld.setUpdateTime(new Date());
                memberPackageRelationMapper.updateByPrimaryKeySelective(mpprOld);
                logger.info("mq.listen.member->delete memberPackageRelation->{}",mpprOld.toString());
                
                opsHash.put(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, mpprOld.getMemberCode() + ":" +  
                        mpprOld.getPackageCode(), JSON.toJSONString(mpprOld));
            }
        }        
    }
    

    private void copyCommonProperties(MemberCppr source, MemberPackageRelation target, Member member) {
        target.setMemberName(member.getName());    
        target.setPackageName(source.getPackageName());
        target.setProgramSourceCode(source.getProgramSourceCode());
        target.setProgramSourceExternalCode(source.getProgramSourceExternalCode());
        target.setProgramSourceLabel(source.getProgramSourceLabel());
        target.setProgramSourceOriginalType(source.getProgramSourceOriginalType());
        target.setStatus(source.getRelationStatus());
        target.setUpdateTime(new Date());
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
        
        opsHash.put(CacheKeyConstant.REDIS_KEY_MEMBER, member.getCode(), JSON.toJSONString(member));
        return member;
    }
}