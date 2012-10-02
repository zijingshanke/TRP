package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface AirticketOrderDAO extends BaseDAO{
   
	public List list(AirticketOrderListForm rlf) throws AppException;
	public List list() throws AppException;
	public void delete(long id)  throws AppException;
	public long save(AirticketOrder airticketOrder) throws AppException;
	public long update(AirticketOrder airticketOrder) throws AppException;
	public AirticketOrder airticketOrderByPNR(String  pnr) throws AppException;
	//B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf) throws AppException;
	//根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId) throws AppException;
	//根据结算ID查询
	public AirticketOrder getAirticketOrderBystatementId(long statementId) throws AppException;
	//根据订单组编号返加List集合
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo) throws AppException;
	//返回一个List集合
	public List<AirticketOrder> getAirticketOrderList() throws AppException;
	public List<AirticketOrder> listBygroupMarkNo(String groupMarkNo,String tranType) throws AppException;
	public AirticketOrder getAirticketOrderBysubPnr(String  subPnr) throws AppException;
	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException;
	//验证pnr是否重复添加
	public boolean checkPnrisToday(AirticketOrder airticketOrder)throws AppException;
	//验证pnr是一月内否重复 退费 改签
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)throws AppException;
	public AirticketOrder getAirticketOrderByGroupMarkNor(String  groupMarkNo,long tranType)throws AppException;
	//根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String  subPnr,long businessType,long tranType) throws AppException;
	
	//团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf) throws AppException;
	public List<AirticketOrder> getAirticketOrderListByPNR(String  subPnr,String tranType)throws AppException;
	
}
