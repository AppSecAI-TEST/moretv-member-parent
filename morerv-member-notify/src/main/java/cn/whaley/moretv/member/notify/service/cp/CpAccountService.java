package cn.whaley.moretv.member.notify.service.cp;

import cn.whaley.moretv.member.model.cp.CpAccount;
import cn.whaley.moretv.member.service.cp.BaseCpAccountService;

import java.util.Date;

/**
* Service: CpAccountService
* Mapper : CpAccountMapper
* Model  : CpAccount
*
* This Service generated by MyBatis Generator Extend at 2017-04-13 15:43:39
*/
public interface CpAccountService extends BaseCpAccountService {

    CpAccount getCpAccount(Integer accountId, String cpSource);

    String createCpAccount(String cpAccountId, String cpToken, Integer accountId, Date date);
}