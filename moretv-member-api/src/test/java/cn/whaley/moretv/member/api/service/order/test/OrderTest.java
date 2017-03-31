package cn.whaley.moretv.member.api.service.order.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.whaley.moretv.member.api.Application;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.model.order.Order;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
public class OrderTest {
    @Autowired
    private OrderService orderService;
    
    @Test
    public void testListOrder(){
        List<Order> orderList = orderService.selectAll();
        assertEquals(orderList.size(), 0);
    }
}
