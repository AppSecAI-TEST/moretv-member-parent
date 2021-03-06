package cn.whaley.moretv.member.base.config;

import cn.whaley.moretv.member.base.manager.ExternalManage;
import cn.whaley.moretv.member.base.manager.MsdManage;
import cn.whaley.moretv.member.base.manager.PayManage;
import cn.whaley.moretv.member.base.util.longconnect.LongConnectionUtil;
import cn.whaley.moretv.member.base.util.paygateway.PayGatewayUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Bob Jiang on 2017/4/6.
 */
@Configuration
public class PropertyConfig {

    @Autowired
    private CustomProperty customProperty;

    @PostConstruct
    public void initProperty() {
        ExternalManage.setTencent(customProperty.getTencent());
        LongConnectionUtil.setLongConnection(customProperty.getLongConnection());
        MsdManage.setMsdServer(customProperty.getMsdServer());
        MsdManage.setXiangguServer(customProperty.getXiangguServer());
        PayManage.setLocalHostServer(customProperty.getLocalHostServer());
        PayManage.setPayGatewaySignKey(customProperty.getPayGatewaySignKey());
        PayGatewayUtil.setCustomProperty(customProperty);
    }
}
