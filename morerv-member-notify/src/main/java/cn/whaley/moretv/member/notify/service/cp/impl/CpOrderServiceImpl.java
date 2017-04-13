package cn.whaley.moretv.member.notify.service.cp.impl;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.impl.GenericServiceImpl;
import cn.whaley.moretv.member.base.util.BeanHandler;
import cn.whaley.moretv.member.mapper.cp.CpOrderItemMapper;
import cn.whaley.moretv.member.mapper.cp.CpOrderMapper;
import cn.whaley.moretv.member.model.cp.CpOrder;
import cn.whaley.moretv.member.model.cp.CpOrderItem;
import cn.whaley.moretv.member.notify.dto.cp.CpOrderDto;
import cn.whaley.moretv.member.notify.service.cp.CpOrderService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ServiceImpl: CpOrderServiceImpl
* Mapper : CpOrderMapper
* Model  : CpOrder
*
* This ServiceImpl generated by MyBatis Generator Extend at 2017-04-13 15:43:39
*/
@Service
@Transactional
public class CpOrderServiceImpl extends GenericServiceImpl<CpOrder, Integer, CpOrderMapper> implements CpOrderService {

    @Autowired
    private CpOrderMapper cpOrderMapper;

    @Autowired
    private CpOrderItemMapper cpOrderItemMapper;

    @Override
    public CpOrderMapper getGenericMapper() {
        return cpOrderMapper;
    }

    @Override
    public ResultResponse<CpOrderDto> createCpOrder(CpOrderDto cpOrderDto) {

        CpOrder cpOrder = BeanHandler.copyProperties(cpOrderDto, CpOrder.class);
        cpOrderMapper.insertSelective(cpOrder);
        cpOrderDto.setId(cpOrder.getId());

        List<CpOrderItem> orderItemList = cpOrderDto.getCpOrderItems();
        createCpOrderItem(orderItemList);

        return ResultResponse.success(cpOrderDto);
    }

    private ResultResponse createCpOrderItem(List<CpOrderItem> orderItemList) {
        for (CpOrderItem item : orderItemList) {
            cpOrderItemMapper.insertSelective(item);
        }
        return ResultResponse.success();
    }
}