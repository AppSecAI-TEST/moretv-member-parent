package cn.whaley.moretv.member.api.service.order.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.whaley.moretv.member.base.util.longconnect.LongConnectionMsg;
import cn.whaley.moretv.member.base.util.longconnect.LongConnectionUtil;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LongConnectionTest {
    
    @Before
    public void setup() {

    }
    
    @Test
    public void testMsg() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-d hh:mm:ss");
        
        LongConnectionMsg msg = new LongConnectionMsg(LongConnectionMsg.StatusType.EXPIRED.getCode());
        msg.setData(msg.new MsgDate("会员到期提醒", "你的会员马上就要到期了，请抓紧续费", sdf.format(new Date()),
                        "123456789", "5", "http:...", ""));
        
        LongConnectionUtil.pushForSpecificUsers(msg);
    }
    
}
