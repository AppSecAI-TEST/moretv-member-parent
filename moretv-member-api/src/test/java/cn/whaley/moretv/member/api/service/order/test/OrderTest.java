package cn.whaley.moretv.member.api.service.order.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.whaley.moretv.member.api.service.goods.GoodsService;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.model.order.Order;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;
    
    
    @Test
    public void testListOrder(){
        goodsService.getGoodsByGoodsNo("");
        List<Order> orderList = orderService.selectAll();
        assertEquals(orderList.size(), 0);
    }
    
}
