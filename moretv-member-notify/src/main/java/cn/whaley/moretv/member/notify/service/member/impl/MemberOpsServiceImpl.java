package cn.whaley.moretv.member.notify.service.member.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import cn.whaley.moretv.member.base.constant.ApiCodeEnum;
import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.base.constant.GlobalEnum;
import cn.whaley.moretv.member.base.constant.OrderEnum;
import cn.whaley.moretv.member.base.dto.response.ResultResponse;
import cn.whaley.moretv.member.base.util.DateFormatUtil;
import cn.whaley.moretv.member.base.util.longconnect.LongConnectService;
import cn.whaley.moretv.member.mapper.member.MemberUserAuthorityMapper;
import cn.whaley.moretv.member.mapper.order.DeliveredOrderMapper;
import cn.whaley.moretv.member.mapper.order.OrderItemMapper;
import cn.whaley.moretv.member.mapper.order.OrderMapper;
import cn.whaley.moretv.member.model.member.MemberUserAuthority;
import cn.whaley.moretv.member.model.order.DeliveredOrder;
import cn.whaley.moretv.member.model.order.Order;
import cn.whaley.moretv.member.model.order.OrderItem;
import cn.whaley.moretv.member.notify.service.member.MemberOpsService;
import cn.whaley.moretv.member.notify.service.tencent.TencentService;
import cn.whaley.moretv.member.service.queue.publish.PublishMemberToAdmin;

@Service
public class MemberOpsServiceImpl implements MemberOpsService {
	
	protected static final Logger logger = LoggerFactory.getLogger(MemberOpsServiceImpl.class);
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private OrderItemMapper orderItemMapper;

	@Autowired
	private MemberUserAuthorityMapper memberUserAuthorityMapper;
	
	@Autowired
	private DeliveredOrderMapper deliveredOrderMapper;
	
	@Autowired
	private TencentService tencentService;
	
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private PublishMemberToAdmin publishMemberToAdmin;
    
    @Autowired
    private LongConnectService longConnectService;
    
    @Transactional
    @Override
	public ResultResponse deliveryMemberByOrderId(Integer orderId) {
		
		//通过订单id查询订单
		Order order = orderMapper.getByOrderIdForUpdate(orderId);
		if (order == null || order.getTradeStatus()==OrderEnum.TradeStatus.TRADE_FINISHED.getCode()) {
			return ResultResponse.define(ApiCodeEnum.API_DATA_ERR);
		}
		
		List<OrderItem> orderItemList = orderItemMapper.getByOrderNo(order.getOrderCode());
		OrderItem orderItem = orderItemList.get(0);
		
		Date now =new Date();
		Date memberStartTime = null;
		String accountId = order.getAccountId();
		String memberCode = orderItem.getMemberCode();
		String memberName = orderItem.getMemberName();
		Integer durationMonth = orderItem.getDurationMonth();
		Integer durationDay = orderItem.getDurationDay();

		MemberUserAuthority userAuthority = memberUserAuthorityMapper.selectByAccountIdAndMemberCode(accountId, memberCode);
		if(userAuthority==null){
			memberStartTime = now;
		}else{
			memberStartTime = userAuthority.getEffectiveTime();
		}
		Date memberEndTime = DateFormatUtil.addMonthAndDay(memberStartTime, durationMonth,durationDay);
		
		//创建发货单
		DeliveredOrder deliveredOrder = new DeliveredOrder();
		deliveredOrder.setOrderCode(order.getOrderCode());
		deliveredOrder.setOrderItemCode(orderItem.getOrderItemCode());
		deliveredOrder.setAccountId(accountId);
		deliveredOrder.setCreateTime(now);
		deliveredOrder.setDurationDay(durationDay);
		deliveredOrder.setDurationMonth(durationMonth);
		deliveredOrder.setStartTime(memberStartTime);
		deliveredOrder.setEndTime(memberEndTime);
		deliveredOrder.setMemberCode(memberCode);
		deliveredOrder.setMemberName(memberName);
		deliveredOrder.setStatus(GlobalEnum.Status.VALID.getCode());
		deliveredOrderMapper.insert(deliveredOrder);
		
		//修改会员权益
		if(userAuthority == null){
			userAuthority = new MemberUserAuthority();
			userAuthority.setAccountId(accountId);
			userAuthority.setMemberCode(memberCode);
			userAuthority.setMemberName(memberName);
			userAuthority.setStartTime(memberStartTime);
			userAuthority.setEffectiveTime(memberEndTime);
			userAuthority.setCreateTime(now);
			userAuthority.setStatus(GlobalEnum.Status.VALID.getCode());
			memberUserAuthorityMapper.insert(userAuthority);
		}else{
			userAuthority.setEffectiveTime(memberEndTime);
			userAuthority.setUpdateTime(now);
			userAuthority.setStatus(GlobalEnum.Status.VALID.getCode());
			memberUserAuthorityMapper.updateByPrimaryKey(userAuthority);
		}
		logger.info("userAuthority : {}", userAuthority);		
		
		//修改订单状态
		order.setTradeStatus(OrderEnum.TradeStatus.TRADE_FINISHED.getCode());
		order.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKeySelective(order);
			
		//订购腾讯
		tencentService.createTencentOrder(order, orderItemList);
		
		this.saveMemberAuthorityToRedis(userAuthority);
		
		publishMemberToAdmin.publishOrder(order);
		publishMemberToAdmin.publishOrderItem(orderItem);
		publishMemberToAdmin.publishMemberUserAuthority(userAuthority);
		publishMemberToAdmin.publishDeliveredOrder(deliveredOrder);
		
		//长连接提醒
		longConnectService.sendByAccountId(userAuthority, now);
		
		return ResultResponse.success();
	}
	
	/**
	 * 缓存会员信息
	 * @param memberAuthority
	 */
	private void saveMemberAuthorityToRedis(MemberUserAuthority memberAuthority) {
		try {
			HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
			String key = String.format(CacheKeyConstant.REDIS_KEY_MEMBER_AUTHORITY, memberAuthority.getAccountId());
			opsHash.put(key, memberAuthority.getMemberCode(), JSON.toJSONString(memberAuthority));
		} catch (Exception e) {
			logger.info("saveMemberAuthorityToRedis error:{}",memberAuthority.toString());
		}
    }
	
	
	
}
