package cn.whaley.moretv.member.api.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.whaley.moretv.member.api.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.dto.response.OrderDetailDto;
import cn.whaley.moretv.member.api.dto.response.OrderListDto;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.api.util.ResponseHandler;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.base.res.ResultResponse;
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


    @Override
    public ResultResponse listByAccountId(BaseRequest baseRequest) {
        List<Order> orderList = orderMapper.listByAccountId(baseRequest.getAccountId().toString());
        List<OrderListDto> orderDtoList = new ArrayList<>(orderList.size());
        for(Order order : orderList){
            //TODO 已查询出用户所有的订单，需要过滤
            
            OrderListDto orderDto = new OrderListDto();
            orderDtoList.add(ResponseHandler.convert2OrderListDto(order, orderDto));
        }
        
        return ResultResponse.success(orderDtoList);
    }


    @Override
    public ResultResponse getByOrderCode(String orderCode) {
        Order order = orderMapper.getByOrderCode(orderCode);
        if(order == null)
            return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
        
        OrderDetailDto orderDto = new OrderDetailDto();
        ResponseHandler.convert2OrderDetailDto(order, orderDto);
        return ResultResponse.success(orderDto);
    }
}