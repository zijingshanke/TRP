package com.fdays.tsms.airticket.biz;

import java.util.List;

import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.FlightListForm;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.neza.exception.AppException;

public class FlightBizImp implements FlightBiz {

	public FlightDAO flightDAO;

	public List list(FlightListForm rlf) throws AppException {

		return flightDAO.list(rlf);
	}

	public List list() throws AppException {

		return flightDAO.list();
	}

	//返回一个List集合
	public List<Flight> getFlightList() throws AppException
	{
		return flightDAO.getFlightList();
	}
	
	// 删除
	public void delete(long id) throws AppException {
		flightDAO.delete(id);
	}

	// 添加保存
	public long save(Flight flight) throws AppException {
		return flightDAO.save(flight);
	}

	// 修改
	public long update(Flight flight) throws AppException {
		return flightDAO.update(flight);
	}
	
	//根据订单机票ID查询 返回对象
	public Flight getFlightByAirticketOrderID(long airticketOrderId) throws AppException
	{
		return flightDAO.getFlightByAirticketOrderID(airticketOrderId);
	}
	
	//根据航班表ID查询
	public Flight getFlightById(long id) throws AppException
	{
		return flightDAO.getFlightById(id);
	}
	
	//根据订单机票ID查询 返回List集合
	public List<Flight> getFlightListByOrderId(long airticketOrderId) throws AppException
	{
		return flightDAO.getFlightListByOrderId(airticketOrderId);
	}

	public FlightDAO getFlightDAO() {
		return flightDAO;
	}

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

}
