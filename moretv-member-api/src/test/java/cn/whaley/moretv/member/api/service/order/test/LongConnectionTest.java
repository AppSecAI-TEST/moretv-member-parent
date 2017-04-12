package cn.whaley.moretv.member.api.service.order.test;

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
        LongConnectionMsg msg = new LongConnectionMsg("会员到期提醒", new Integer[] { 74471045, 36096961},
                "你的会员马上就要到期了，请抓紧续费", LongConnectionMsg.StatusType.EXPIRED.getCode(), new Date().getTime());
        LongConnectionUtil.pushForSpecificUsers(msg);
    }
    
}
