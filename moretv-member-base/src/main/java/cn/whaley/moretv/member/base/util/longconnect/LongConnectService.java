package cn.whaley.moretv.member.base.util.longconnect;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.whaley.moretv.member.model.member.MemberUserAuthority;

@Component
public class LongConnectService {
    
    @Async("longConnectAsync")
    public void sendByAccountId(MemberUserAuthority userAuthority, Date now){
        SimpleDateFormat sdfCommon = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfChina = new SimpleDateFormat("yyyy年M月d日"); 
        
        if(now.equals(userAuthority.getCreateTime())){
            //首次开通会员调用长连接通知
            String title = "会员开通提醒";
            String content = "你的" + userAuthority.getMemberName() + "已经开通，有效期至" + sdfChina.format(userAuthority.getEffectiveTime());
            
            LongConnectionMsg msg = new LongConnectionMsg(LongConnectionMsg.StatusType.BUY.getCode());
            msg.setData(msg.new MsgDate(title, content, sdfCommon.format(now), userAuthority.getAccountId(), "5", "", ""));
            
            LongConnectionUtil.pushForSpecificUsers(msg);
        }
    }
}
