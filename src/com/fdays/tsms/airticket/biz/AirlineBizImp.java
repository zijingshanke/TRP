package com.fdays.tsms.airticket.biz;

import java.util.List;
import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlineListForm;
import com.fdays.tsms.airticket.dao.AirlineDAO;
import com.neza.exception.AppException;

public class AirlineBizImp implements AirlineBiz {
	private AirlineDAO airlineDAO;

	public List list(AirlineListForm rlf) throws AppException {
		return airlineDAO.list(rlf);
	}

	public List list() throws AppException {
		return airlineDAO.list();
	}
	
	public List getValidList() throws AppException{
		return airlineDAO.getValidList();
	}

	public void delete(long id) throws AppException {
		airlineDAO.delete(id);
	}

	public long deleteAirline(long id) throws AppException {
		Airline tempAirline = airlineDAO.getAirlineById(id);
		if (tempAirline == null) {
			return 0;
		} else {
			tempAirline.setStatus(Airline.STATUS_2);
			airlineDAO.update(tempAirline);
			return 1;
		}
	}

	public long save(Airline airline) throws AppException {
		return airlineDAO.save(airline);
	}

	public long update(Airline airline) throws AppException {
		return airlineDAO.update(airline);
	}

	public Airline getAirlineById(long id) throws AppException {
		return airlineDAO.getAirlineById(id);
	}

	public Airline getAirlineByCity(String start, String end)
			throws AppException {
		return airlineDAO.getAirlineByCity(start, end);
	}

	public void setAirlineDAO(AirlineDAO airlineDAO) {
		this.airlineDAO = airlineDAO;
	}

}
