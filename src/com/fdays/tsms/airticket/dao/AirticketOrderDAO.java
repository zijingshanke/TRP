package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.OrderGroup;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AirticketOrderDAO extends BaseDAO {
	public AirticketOrder airticketOrderByPNR(String pnr) throws AppException;

	// B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf)
			throws AppException;

	// 根据结算ID查询
	public AirticketOrder getAirticketOrderByStatementId(long statementId)
			throws AppException;

	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
			throws AppException;

	// 验证pnr是否重复添加
	public boolean checkPnrisToday(AirticketOrder airticketOrder)
			throws AppException;

	// 验证pnr是一月内否重复 退费 改签
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
			throws AppException;

	public AirticketOrder getDrawedAirticketOrderByGroupId(
			long groupId, long tranType) throws AppException;

	// 根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
			long businessType, long tranType) throws AppException;

	// 团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf)
			throws AppException;
	
	// 根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
			throws AppException;
	
	public List list(AirticketOrderListForm rlf) throws AppException;
	public List listTeam(AirticketOrderListForm rlf) throws AppException;
	
	
	public List list() throws AppException;
	
	// 根据订单组编号
	public List<AirticketOrder> listByGroupId(long groupId)
			throws AppException;
	// 根据订单组、小组号 查询
	public List<AirticketOrder> listBySubGroupByAndGroupId(long groupId,long subMarkNo) throws AppException;
	
	public List<AirticketOrder> getAirticketOrderListByPNR(String  subPnr,String tranType)throws AppException;
	
	public List<AirticketOrder> listByGroupIdAndTranType(
			long groupId, String tranType) throws AppException;
	
	public List<AirticketOrder> listByGroupIdAndTranTypeStatus(
			long groupId, String tranType,String status) throws AppException;
	
	public List<AirticketOrder> listByGroupIdAndBusinessTranType(
			long groupId, String tranType,String businessType) throws AppException;

	public void delete(long id) throws AppException;

	public long save(AirticketOrder airticketOrder) throws AppException;

	public long update(AirticketOrder airticketOrder) throws AppException;
	
	public long getNewSubGroupMarkNo(long orgerGroupId) throws AppException;
	
	public long saveOrderGroup(OrderGroup og) throws AppException;
	public OrderGroup getOrderGroupById(long id) throws AppException;

}
