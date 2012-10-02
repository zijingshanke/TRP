package com.fdays.tsms.airticket.biz;

import javax.servlet.http.HttpServletRequest;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.TempPNR;
import com.neza.exception.AppException;

public interface FlightPassengerBiz {
	public void saveFlightPassengerByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException;

	public void saveFlightByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException;

	public void savePassengerByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException;

	public void saveFlightPassengerByOrderForm(
			AirticketOrder airticketOrderForm, AirticketOrder newOrder)
			throws AppException;

	public void saveFlightPassengerByTempPNR(TempPNR tempPNR,
			AirticketOrder newOrder) throws AppException;

	public AirticketOrder saveFlightPassengerInOrderByTempPNR(TempPNR tempPNR,
			AirticketOrder newOrder) throws AppException;

	public void saveFlightPassengerByRequest(HttpServletRequest request,
			AirticketOrder newOrder) throws AppException;

	public void updateFlightPassengerByRequest(HttpServletRequest request,
			AirticketOrder newOrder) throws AppException;

}
