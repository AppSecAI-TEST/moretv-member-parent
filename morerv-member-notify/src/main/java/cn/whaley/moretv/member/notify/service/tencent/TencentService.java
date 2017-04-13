package cn.whaley.moretv.member.notify.service.tencent;

import cn.whaley.moretv.member.base.dto.ThirdPartyInfo;
import cn.whaley.moretv.member.service.tencent.BaseTencentService;

import java.util.List;

/**
 * Created by Bob Jiang on 2017/4/13.
 */
public interface TencentService extends BaseTencentService {

    void createTencentOrder(String cpAccount, String vipPackage, List<ThirdPartyInfo> thirdPartyInfoList, String orderCode);

}
