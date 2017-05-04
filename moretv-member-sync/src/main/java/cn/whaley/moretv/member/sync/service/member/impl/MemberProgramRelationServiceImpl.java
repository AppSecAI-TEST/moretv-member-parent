package cn.whaley.moretv.member.sync.service.member.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.member.MemberProgramRelationMapper;
import cn.whaley.moretv.member.model.member.MemberProgramRelation;
import cn.whaley.moretv.member.sync.dto.goods.ProductDto;
import cn.whaley.moretv.member.sync.service.member.MemberProgramRelationService;
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
public class MemberProgramRelationServiceImpl extends GenericServiceImpl<MemberProgramRelation, Integer, MemberProgramRelationMapper> implements MemberProgramRelationService {

    private static final Logger logger = LoggerFactory.getLogger(MemberProgramRelationServiceImpl.class);
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Autowired
    private MemberProgramRelationMapper memberProgramRelationMapper;

    @Override
    public MemberProgramRelationMapper getGenericMapper() {
        return memberProgramRelationMapper;
    }

    
    @Override
    public void sync(ProductDto productDto) {
        //1、查询出当前节目跟会员模型的关系
        ValueOperations<String, String> opsValue = redisTemplate.opsForValue();
        Date date = new Date();
        List<MemberProgramRelation> mprList = memberProgramRelationMapper.listByProgramCode(productDto.getProgramCode());
        boolean isExist = false;
        for(MemberProgramRelation mpr : mprList){
            mpr.setUpdateTime(date);
            if(mpr.getMemberCode().equals(productDto.getMemberCode())){
                //数据库中存在跟当前接收到的会员-节目一样的记录，更新记录
                isExist = true;
                BeanUtils.copyProperties(productDto, mpr, "id","createTime","updateTime","status");
                mpr.setStatus(productDto.getRelationStatus());
                
                opsValue.set(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PROGRAM_RELATION, mpr.getProgramCode()), JSON.toJSONString(mpr));
            }else{
                //不是，要删除，因为一个节目只能被一个会员绑定
                mpr.setStatus(GlobalEnum.Bound.UNBOUND.getCode());
                redisTemplate.delete(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PROGRAM_RELATION, mpr.getProgramCode()));
            }
            
            memberProgramRelationMapper.updateByPrimaryKeySelective(mpr);
            logger.info("mq.listen.product->update memberProgramRelation->{}",mpr.toString());
        }
        
        if(!isExist){
            //数据库中不存在，要新增
            MemberProgramRelation mpr = new MemberProgramRelation();
            BeanUtils.copyProperties(productDto, mpr, "id","createTime","updateTime","status");
            mpr.setCreateTime(date);
            mpr.setUpdateTime(date);
            mpr.setStatus(productDto.getRelationStatus());
            memberProgramRelationMapper.insertSelective(mpr);
            opsValue.set(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PROGRAM_RELATION, mpr.getProgramCode()), JSON.toJSONString(mpr));
            logger.info("mq.listen.product->insert memberProgramRelation->{}",mpr.toString());
        }
    }


    @Override
    public Map<String, Object> resetRedis(String programCode) {
        ValueOperations<String, String> opsValue = redisTemplate.opsForValue();
        List<String> addList = new ArrayList<>();
        List<String> deleteList = new ArrayList<>();
        
        String[] programCodeArray = programCode.split(",");
        for(String programId : programCodeArray){
            boolean isBound = false;
            
            List<MemberProgramRelation> mprList = memberProgramRelationMapper.listByProgramCode(programId);
            for(MemberProgramRelation mpr : mprList){
                if(GlobalEnum.Bound.BOUND.getCode().equals(mpr.getStatus())){
                    //绑定的，插入redis
                    isBound = true;
                    opsValue.set(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PROGRAM_RELATION, programId), JSON.toJSONString(mpr));
                    addList.add(programId);
                    logger.info("reset program-member-relation, add prgoram->{}", programId);
                }
            }
            
            if(!isBound){
                //这个节目没有跟会员的绑定关系，删除redis
                redisTemplate.delete(String.format(CacheKeyConstant.REDIS_KEY_MEMBER_PROGRAM_RELATION, programId));
                deleteList.add(programId);
                logger.info("reset program-member-relation, delete prgoram->{}", programId);
            }
        }
        
        return RedisResetResponseUtil.getResetRedisMap(addList, deleteList);
    }
}