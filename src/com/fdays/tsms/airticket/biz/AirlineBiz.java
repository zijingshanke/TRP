package com.fdays.tsms.airticket.biz;

import java.util.List;
import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlineListForm;
import com.neza.exception.AppException;

public interface AirlineBiz {
	public List list(AirlineListForm rlf) throws AppException;

	public List list() throws AppException;

	public void delete(long id) throws AppException;
	
	public long deleteAirline(long id) throws AppException;
	

	public long save(Airline airline) throws AppException;

	public long update(Airline airline) throws AppException;

	public Airline getAirlineById(long id) throws AppException;

	public Airline getAirlineByCity(String start, String end)
			throws AppException;
	
	public List getValidList() throws AppException;

}
