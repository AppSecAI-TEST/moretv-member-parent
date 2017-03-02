package cn.whaley.moretv.member.mapper.goods;

import org.apache.ibatis.annotations.Select;

import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.model.goods.Member;

/**
* Mapper: MemberMapper
* Model : Member
* Table : member
*
* This Mapper generated by MyBatis Generator Extend at 2017-03-02 17:00:35
*/
public interface MemberMapper extends GenericMapper<Member, Integer> {

    @Select("select * from member where code = #{memberCode}")
    Member selectByCode(String memberCode);
}