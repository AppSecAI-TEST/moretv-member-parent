package cn.whaley.moretv.member.sync.service.member.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.member.MemberPackageRelationMapper;
import cn.whaley.moretv.member.model.member.MemberPackageRelation;
import cn.whaley.moretv.member.sync.dto.goods.MemberCppr;
import cn.whaley.moretv.member.sync.service.member.MemberPackageRelationService;
import cn.whaley.moretv.member.sync.util.RedisResetResponseUtil;

/**
* ServiceImpl: MemberProgramRelationServiceImpl
* Mapper : MemberProgramRelationMapper
* Model  : MemberProgramRelation
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-03-03 11:41:48
*/
@Service
@Transactional
public class MemberPackageRelationServiceImpl extends GenericServiceImpl<MemberPackageRelation, Integer, MemberPackageRelationMapper> implements MemberPackageRelationService {

    private static final Logger logger = LoggerFactory.getLogger(MemberPackageRelationServiceImpl.class);
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private MemberPackageRelationMapper memberPackageRelationMapper;

    @Override
    public MemberPackageRelationMapper getGenericMapper() {
        return memberPackageRelationMapper;
    }

    
    @Override
    public void sync(MemberCppr memberCppr) {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        dealWithMemberPackage(memberCppr, opsHash);
    }
    
    /**
     * 处理会员模型-节目包
     * @param member
     * @param memberDto
     */
    private void dealWithMemberPackage(MemberCppr memberCppr, HashOperations<String, String, String> opsHash) {
        List<MemberPackageRelation> mpprListOld = memberPackageRelationMapper.listByMemberCode(memberCppr.getMemberCode());
        boolean isExist = false;
        
        for(MemberPackageRelation mpprOld : mpprListOld){
            if(memberCppr.getPackageCode().equals(mpprOld.getPackageCode())){
                //新的在数据库里已经存在了,更新数据库中的
                copyCommonProperties(memberCppr, mpprOld);
                memberPackageRelationMapper.updateByPrimaryKeySelective(mpprOld);
                isExist = true;
                logger.info("mq.listen.product.package->update memberPackageRelation->{}",mpprOld.toString());
                
                if(GlobalConstant.CP_TENCENT.equals(mpprOld.getProgramSourceCode()) &&  GlobalEnum.Bound.BOUND.getCode().equals(mpprOld.getStatus())){
                    opsHash.put(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, mpprOld.getMemberCode()),  
                            mpprOld.getPackageCode(), JSON.toJSONString(mpprOld));
                }else{
                    opsHash.delete(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, mpprOld.getMemberCode()), mpprOld.getPackageCode());
                }
            }
        }
        
        if(!isExist){
            //接收的不存在数据库中而且是腾讯的,则新增
            MemberPackageRelation memberPackageRelation = new MemberPackageRelation();
            copyCommonProperties(memberCppr, memberPackageRelation);
            memberPackageRelation.setMemberCode(memberCppr.getMemberCode());
            memberPackageRelation.setPackageCode(memberCppr.getPackageCode());
            memberPackageRelation.setCreateTime(new Date());
           
            memberPackageRelationMapper.insertSelective(memberPackageRelation);
            logger.info("mq.listen.product.package->insert memberPackageRelation->{}",memberPackageRelation.toString());
            
            if(GlobalConstant.CP_TENCENT.equals(memberCppr.getProgramSourceCode()) &&  GlobalEnum.Bound.BOUND.getCode().equals(memberPackageRelation.getStatus()) ){
                opsHash.put(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, memberPackageRelation.getMemberCode()),  
                        memberPackageRelation.getPackageCode(), JSON.toJSONString(memberPackageRelation));
            }
        }
    }
    
    private void copyCommonProperties(MemberCppr source, MemberPackageRelation target) {
        target.setMemberName(source.getMemberName());  
        target.setPackageName(source.getPackageName());
        target.setProgramSourceCode(source.getProgramSourceCode());
        target.setProgramSourceExternalCode(source.getProgramSourceExternalCode());
        target.setProgramSourceLabel(source.getProgramSourceLabel());
        target.setProgramSourceOriginalType(source.getProgramSourceOriginalType());
        target.setStatus(source.getRelationStatus());
        target.setUpdateTime(new Date());
    }


    @Override
    public Map<String, Object> resetRedis() {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        List<String> addList = new ArrayList<>();
        List<String> deleteList = new ArrayList<>();
        
        List<MemberPackageRelation> mppList = memberPackageRelationMapper.selectAll();
        for(MemberPackageRelation mpp : mppList){
            String temp = mpp.getMemberCode() + "->" + mpp.getPackageCode();
            
            if(GlobalConstant.CP_TENCENT.equals(mpp.getProgramSourceCode()) &&  GlobalEnum.Bound.BOUND.getCode().equals(mpp.getStatus())){
                opsHash.put(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, mpp.getMemberCode()),  
                        mpp.getPackageCode(), JSON.toJSONString(mpp));
                addList.add(temp);
                logger.info("reset memberPackageRelation, add memberPackageRelation->{}", temp);
            }else{
                opsHash.delete(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PACKAGE_RELATION, mpp.getMemberCode()), mpp.getPackageCode());
                deleteList.add(temp);
                logger.info("reset memberPackageRelation, delete memberPackageRelation->{}", temp);
            }
        }
        return RedisResetResponseUtil.getResetRedisMap(addList, deleteList);
    }
}