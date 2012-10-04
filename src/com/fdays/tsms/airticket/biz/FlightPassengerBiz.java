package com.fdays.tsms.airticket.biz;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.TempPNR;
import com.neza.exception.AppException;

public interface FlightPassengerBiz {
	/**
	 * 保存新订单的航班、乘机人，指定航班、乘机人
	 * 
	 */
	public void saveFlightPassengerBySetForOrder(AirticketOrder newOrder,
			Set passengers, Set flights) throws AppException;
	
	public void saveFlightPassengerBySetForOrder(AirticketOrder newOrder,
			Set passengers, Set flights,long retireStatus) throws AppException;

	public void saveFlightPassengerByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException;

	public void saveFlightByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException;

	public void savePassengerBySetForOrder(AirticketOrder newOrder,
			Set passengerSet,long retireStatus) throws AppException;

	public void saveFlightBySetForOrder(AirticketOrder newOrder, Set flightSet,long retireStatus)
			throws AppException;

	public void saveFlightByIdsForOrder(AirticketOrder newOrder,
			String[] flightIds) throws AppException;

	public void savePassengerByIdsForOrder(AirticketOrder newOrder,
			String[] oldPassengerIds) throws AppException;

	public void savePassengerByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException;

	public void saveFlightPassengerByOrderForm(
			AirticketOrder airticketOrderForm, AirticketOrder newOrder,long retireStatus)
			throws AppException;

	public void saveFlightPassengerByTempPNR(TempPNR tempPNR,
			AirticketOrder newOrder) throws AppException;

	public AirticketOrder saveFlightPassengerInOrderByTempPNR(TempPNR tempPNR,
			AirticketOrder newOrder) throws AppException;

	public void saveFlightPassengerByRequest(HttpServletRequest request,
			AirticketOrder newOrder) throws AppException;

	public void updateFlightPassengerByRequest(HttpServletRequest request,
			AirticketOrder newOrder) throws AppException;

	public void updateSynFlightPassengerByRequest(HttpServletRequest request,
			List<AirticketOrder> orderList) throws AppException;

}
