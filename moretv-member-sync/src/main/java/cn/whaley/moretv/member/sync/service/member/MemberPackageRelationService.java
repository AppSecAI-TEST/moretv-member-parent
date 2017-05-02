package cn.whaley.moretv.member.sync.service.member;

import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.member.MemberPackageRelation;
import cn.whaley.moretv.member.sync.dto.goods.MemberCppr;

public interface MemberPackageRelationService extends GenericService<MemberPackageRelation, Integer> {

    void sync(MemberCppr memberCppr);
}