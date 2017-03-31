package cn.whaley.moretv.member.api.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.mapper.order.OrderMapper;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;

@Service
@Transactional
public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    
    
    @Override
    public GenericMapper<Order, Integer> getGenericMapper() {
        return orderMapper;
    }
}