package cn.whaley.moretv.member.service.cp.impl;

import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.mapper.cp.CpAccountMapper;
import cn.whaley.moretv.member.model.cp.CpAccount;
import cn.whaley.moretv.member.service.cp.BaseCpAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Bob Jiang on 2017/4/19.
 */
@Service
public class BaseCpAccountServiceImpl extends GenericServiceImpl<CpAccount, Integer, CpAccountMapper>
        implements BaseCpAccountService {

    @Autowired
    protected CpAccountMapper cpAccountMapper;

    @Override
    public CpAccountMapper getGenericMapper() {
        return cpAccountMapper;
    }
}
