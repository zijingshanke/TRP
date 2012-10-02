package com.fdays.tsms.airticket.biz;

import java.util.List;

import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.PassengerListForm;
import com.fdays.tsms.airticket.dao.PassengerDAO;
import com.neza.exception.AppException;

public class PassengerBizImp implements PassengerBiz {

	public PassengerDAO passengerDAO;

	public PassengerDAO getPassengerDAO() {
		return passengerDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}

	public List list(PassengerListForm rlf) throws AppException {

		return passengerDAO.list(rlf);
	}

	public List list() throws AppException {
		return passengerDAO.list();
	}

	// 删除
	public void delete(long id) throws AppException {
		passengerDAO.delete(id);
	}

	// 添加保存
	public long save(Passenger passenger) throws AppException {
		return passengerDAO.save(passenger);
	}

	// 修改
	public long update(Passenger passenger) throws AppException {
		return passengerDAO.update(passenger);
	}
	
	public List<Passenger> listByairticketOrderId(long airticketOrderId) throws AppException{
		return passengerDAO.listByairticketOrderId(airticketOrderId);
	}
	public Passenger passengerById(Long id) throws AppException{
		return passengerDAO.getPassengerById(id);
	}
}
