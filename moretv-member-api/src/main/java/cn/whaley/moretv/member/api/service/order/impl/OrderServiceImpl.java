package cn.whaley.moretv.member.api.service.order.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.whaley.moretv.member.base.dto.request.BaseRequest;
import cn.whaley.moretv.member.api.dto.order.OrderDetailDto;
import cn.whaley.moretv.member.api.dto.order.OrderListDto;
import cn.whaley.moretv.member.api.service.order.OrderService;
import cn.whaley.moretv.member.api.util.ResponseHandler;
import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.service.order.impl.BaseOrderServiceImpl;

@Service
@Transactional
public class OrderServiceImpl extends BaseOrderServiceImpl implements OrderService {

    @Override
    public ResultResponse listByAccountId(BaseRequest baseRequest) {
        List<Order> orderList = orderMapper.listByAccountId(baseRequest.getAccountId().toString());
        List<OrderListDto> orderDtoList = new ArrayList<>(orderList.size());
        for(Order order : orderList){
            //只有交易状态为已完成的才返回
            if(order.getTradeStatus().intValue() != OrderEnum.TradeStatus.TRADE_FINISHED.getCode())
                continue;
            OrderListDto orderDto = new OrderListDto();
            orderDtoList.add(ResponseHandler.convert2OrderListDto(order, orderDto));
        }
        
        return ResultResponse.success(orderDtoList);
    }


    @Override
    public ResultResponse getByOrderCode(String orderCode) {
        Order order = orderMapper.getByOrderCode(orderCode);
        //只有交易状态为已完成的才返回
        if(order == null || (order.getTradeStatus().intValue() != OrderEnum.TradeStatus.TRADE_FINISHED.getCode()) )
            return ResultResponse.define(ApiCodeEnum.API_DATA_NOT_EXIST);
        
        OrderDetailDto orderDto = new OrderDetailDto();
        ResponseHandler.convert2OrderDetailDto(order, orderDto);
        return ResultResponse.success(orderDto);
    }
}
