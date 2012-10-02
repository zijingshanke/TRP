package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
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

	public AirticketOrder getDrawedAirticketOrderByGroupMarkNo(
			String groupMarkNo, long tranType) throws AppException;

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
	
	public List list() throws AppException;
	
	// 根据订单组编号返加List集合
	public List<AirticketOrder> listByGroupMarkNo(String groupMarkNo)
			throws AppException;
	
	public List<AirticketOrder> getAirticketOrderListByPNR(String  subPnr,String tranType)throws AppException;

	public List<AirticketOrder> listByGroupMarkNoAndTranType(
			String groupMarkNo, String tranType) throws AppException;
	
	public List<AirticketOrder> listByGroupMarkNoAndTranTypeStatus(
			String groupMarkNo, String tranType,String status) throws AppException;
	
	public List<AirticketOrder> listByGroupMarkNoAndBusinessTranType(
			String groupMarkNo, String tranType,String businessType) throws AppException;

	public void delete(long id) throws AppException;

	public long save(AirticketOrder airticketOrder) throws AppException;

	public long update(AirticketOrder airticketOrder) throws AppException;
	
	

}
