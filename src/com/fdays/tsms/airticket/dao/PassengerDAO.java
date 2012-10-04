package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.PassengerListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface PassengerDAO extends BaseDAO{

	public List list(PassengerListForm rlf) throws AppException;
	public List list() throws AppException;
	public void delete(long id)  throws AppException;
	public long save(Passenger passenger) throws AppException;
	public long update(Passenger passenger) throws AppException;
	public List<Passenger> listByairticketOrderId(long airticketOrderId) throws AppException;
	public Passenger getPassengerById(Long id) throws AppException;
}
