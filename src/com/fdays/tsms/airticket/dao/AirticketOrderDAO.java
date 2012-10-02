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
	//根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId) throws AppException;
	//根据订单组编号返加List集合
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo) throws AppException;
	//返回一个List集合
	public List<AirticketOrder> getAirticketOrderList() throws AppException;
	public List<AirticketOrder> listBygroupMarkNo(String groupMarkNo,String tranType) throws AppException;
	public AirticketOrder getAirticketOrderBysubPnr(String  subPnr) throws AppException;
	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException;
}
