package com.fdays.tsms.airticket.biz;

import java.util.List;
import com.fdays.tsms.airticket.AirlinePlace;
import com.fdays.tsms.airticket.AirlinePlaceListForm;
import com.neza.exception.AppException;

public interface AirlinePlaceBiz {
	public List list(AirlinePlaceListForm rlf) throws AppException;

	public List list() throws AppException;

	public void delete(long id) throws AppException;

	public long deleteAirlinePlace(long id) throws AppException;

	public long save(AirlinePlace airlinePlace) throws AppException;

	public long update(AirlinePlace airlinePlace) throws AppException;

	public AirlinePlace getAirlinePlaceById(long id) throws AppException;

	public AirlinePlace getAirlinePlaceByCarrier(String carrier, String code)
			throws AppException;

}
