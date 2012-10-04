package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.FlightListForm;
import com.neza.exception.AppException;

public interface FlightDAO {

	public List list(FlightListForm rlf) throws AppException;
	public List list() throws AppException;
	public void delete(long id)  throws AppException;
	public long save(Flight flight) throws AppException;
	public long update(Flight flight) throws AppException;
	//返回一个List集合
	public List<Flight> getFlightList() throws AppException;
	//根据航班表ID查询
	public Flight getFlightById(long id) throws AppException;
	//根据订单机票ID查询 返回对象
	public Flight getFlightByAirticketOrderID(long airticketOrderId) throws AppException;
	//根据订单机票ID查询 返回List集合
	public List<Flight> getFlightListByOrderId(long airticketOrderId) throws AppException;
}
