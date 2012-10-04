package com.fdays.tsms.airticket.biz;

import java.util.List;

import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlinePlace;
import com.fdays.tsms.airticket.AirlinePlaceListForm;
import com.fdays.tsms.airticket.dao.AirlinePlaceDAO;
import com.neza.exception.AppException;

public class AirlinePlaceBizImp implements AirlinePlaceBiz {
	private AirlinePlaceDAO airlinePlaceDAO;

	public List list(AirlinePlaceListForm rlf) throws AppException {
		return airlinePlaceDAO.list(rlf);
	}

	public List list() throws AppException {
		return airlinePlaceDAO.list();
	}

	public void delete(long id) throws AppException {
		airlinePlaceDAO.delete(id);
	}
	
	public long deleteAirlinePlace(long id) throws AppException {
		AirlinePlace tempAirlinePlace = airlinePlaceDAO.getAirlinePlaceById(id);
		if (tempAirlinePlace == null) {
			return 0;
		} else {
			tempAirlinePlace.setStatus(Airline.STATUS_2);
			airlinePlaceDAO.update(tempAirlinePlace);
			return 1;
		}
	}

	public long save(AirlinePlace airline) throws AppException {
		return airlinePlaceDAO.save(airline);
	}

	public long update(AirlinePlace airline) throws AppException {
		return airlinePlaceDAO.update(airline);
	}

	public AirlinePlace getAirlinePlaceById(long id) throws AppException {
		return airlinePlaceDAO.getAirlinePlaceById(id);
	}

	public AirlinePlace getAirlinePlaceByCarrier(String carrier, String code)
			throws AppException {
		return airlinePlaceDAO.getAirlinePlaceByCarrier(carrier, code);
	}

	public void setAirlinePlaceDAO(AirlinePlaceDAO airlinePlaceDAO) {
		this.airlinePlaceDAO = airlinePlaceDAO;
	}

}
