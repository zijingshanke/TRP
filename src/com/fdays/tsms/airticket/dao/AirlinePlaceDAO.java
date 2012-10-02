package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.AirlinePlace;
import com.fdays.tsms.airticket.AirlinePlaceListForm;
import com.neza.exception.AppException;

public interface AirlinePlaceDAO {
	public List list(AirlinePlaceListForm rlf) throws AppException;

	public List list() throws AppException;
	public List getValidList() throws AppException;

	public void delete(long id) throws AppException;

	public long save(AirlinePlace airline) throws AppException;

	public long update(AirlinePlace airline) throws AppException;

	public AirlinePlace getAirlinePlaceById(long id) throws AppException;
	
	public AirlinePlace getAirlinePlaceByCarrier(String carrier,String code) throws AppException;	
}
