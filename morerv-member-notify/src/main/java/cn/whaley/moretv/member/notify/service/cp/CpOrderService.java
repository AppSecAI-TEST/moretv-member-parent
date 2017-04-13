package cn.whaley.moretv.member.notify.service.cp;

import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.service.GenericService;
import cn.whaley.moretv.member.model.cp.CpOrder;
import cn.whaley.moretv.member.notify.dto.cp.CpOrderDto;

/**
* Service: CpOrderService
* Mapper : CpOrderMapper
* Model  : CpOrder
*
* This Service generated by MyBatis Generator Extend at 2017-04-13 15:43:39
*/
public interface CpOrderService extends GenericService<CpOrder, Integer> {

    ResultResponse<CpOrderDto> createCpOrder(CpOrderDto cpOrderDto);

}