package com.fdays.tsms.airticket.biz;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.fdays.tsms.airticket.dao.PassengerDAO;
import com.fdays.tsms.base.Constant;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class FlightPassengerBizImp implements FlightPassengerBiz {
	private FlightDAO flightDAO;
	private PassengerDAO passengerDAO;

	/**
	 * 保存新订单的航班、乘机人
	 * 
	 */
	public void saveFlightPassengerByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException {
		saveFlightByOrder(oldOrder, newOrder);
		savePassengerByOrder(oldOrder, newOrder);
	}

	/**
	 * 保存新订单的航班、乘机人，指定航班、乘机人
	 */
	public void saveFlightPassengerBySetForOrder(AirticketOrder newOrder,
			Set passengers, Set flights) throws AppException {
		saveFlightBySetForOrder(newOrder, flights,Flight.STATES_1);
		savePassengerBySetForOrder(newOrder, passengers,Passenger.STATES_1);
	}
	
	/**
	 * 保存新订单的航班、乘机人，指定航班、乘机人
	 * 退废改类型
	 */
	public void saveFlightPassengerBySetForOrder(AirticketOrder newOrder,
			Set passengers, Set flights,long status) throws AppException {
		saveFlightBySetForOrder(newOrder, flights,status);
		savePassengerBySetForOrder(newOrder, passengers,status);
	}

	public void saveFlightByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException {
		Set tempFlightList = oldOrder.getFlights();
		saveFlightBySetForOrder(newOrder, tempFlightList,Flight.STATES_1);
	}

	public void saveFlightBySetForOrder(AirticketOrder newOrder, Set flightSet,long status)
			throws AppException {
		for (Object fobj : flightSet) {
			Flight tempflight = (Flight) fobj;
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(Constant.toUpperCase(tempflight
					.getFlightCode()));// 航班号
			flight.setStartPoint(Constant.toUpperCase(tempflight
					.getStartPoint())); // 出发地
			flight.setEndPoint(Constant.toUpperCase(tempflight.getEndPoint()));// 目的地
			flight.setBoardingTime(tempflight.getBoardingTime());// 起飞时间
			flight.setDiscount(tempflight.getDiscount());// 折扣
			flight.setFlightClass(Constant.toUpperCase(tempflight
					.getFlightClass()));// 舱位
			flight.setStatus(status); // 状态

			flight.setTicketPrice(tempflight.getTicketPrice());
			flight.setAirportPriceAdult(tempflight.getAirportPriceAdult());
			flight.setFuelPriceAdult(tempflight.getFuelPriceAdult());
			flight.setAirportPriceChild(tempflight.getAirportPriceChild());
			flight.setFuelPriceChild(tempflight.getFuelPriceChild());
			flightDAO.save(flight);
		}
	}

	public void saveFlightByIdsForOrder(AirticketOrder newOrder,
			String[] oldflightIds) throws AppException {
		for (int j = 0; j < oldflightIds.length; j++) {
			if (oldflightIds[j] != null
					&& "".equals(oldflightIds[j].trim()) == false) {
				long flightId = Long.parseLong(oldflightIds[j]);
				if (flightId > 0) {
					Flight tempflight = flightDAO.getFlightById(flightId);
					Flight flight = new Flight();
					flight.setAirticketOrder(newOrder);
					flight.setFlightCode(Constant.toUpperCase(tempflight
							.getFlightCode()));// 航班号
					flight.setStartPoint(Constant.toUpperCase(tempflight
							.getStartPoint())); // 出发地
					flight.setEndPoint(Constant.toUpperCase(tempflight
							.getEndPoint()));// 目的地
					flight.setBoardingTime(tempflight.getBoardingTime());// 起飞时间
					flight.setDiscount(tempflight.getDiscount());// 折扣
					flight.setFlightClass(Constant.toUpperCase(tempflight
							.getFlightClass()));// 舱位
					flight.setStatus(1L); // 状态

					flight.setTicketPrice(tempflight.getTicketPrice());
					flight.setAirportPriceAdult(tempflight
							.getAirportPriceAdult());
					flight.setFuelPriceAdult(tempflight.getFuelPriceAdult());
					flight.setAirportPriceChild(tempflight
							.getAirportPriceChild());
					flight.setFuelPriceChild(tempflight.getFuelPriceChild());
					flight
							.setAirportPriceBaby(tempflight
									.getAirportPriceBaby());
					flight.setFuelPriceBaby(tempflight.getFuelPriceBaby());

					flightDAO.save(flight);
				}
			}
		}
	}

	public void savePassengerByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException {
		// 乘机人
		Set passengerSet = oldOrder.getPassengers();
		savePassengerBySetForOrder(newOrder, passengerSet,Passenger.STATES_1);
	}

	public void savePassengerBySetForOrder(AirticketOrder newOrder,
			Set passengerSet,long status) throws AppException {
		for (Object obj : passengerSet) {
			Passenger passengerTmp = (Passenger) obj;
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passengerTmp.getName()); // 乘机人姓名
			passenger.setCardno(passengerTmp.getCardno());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(status);// 状态
			passenger.setTicketNumber(passengerTmp.getTicketNumber()); // 票号
			passengerDAO.save(passenger);
		}
	}

	public void savePassengerByIdsForOrder(AirticketOrder newOrder,
			String[] oldPassengerIds) throws AppException {
		if (newOrder != null && newOrder.getId() > 0 && oldPassengerIds != null) {
			for (int j = 0; j < oldPassengerIds.length; j++) {
				if (oldPassengerIds[j] != null
						&& "".equals(oldPassengerIds[j].trim()) == false) {
					long passengerId = Long.parseLong(oldPassengerIds[j]);
					if (passengerId > 0) {
						Passenger tempPassenger = passengerDAO
								.getPassengerById(passengerId);
						if (tempPassenger != null) {
							Passenger passenger = new Passenger();
							passenger.setAirticketOrder(newOrder);
							passenger.setName(tempPassenger.getName()); // 乘机人姓名
							passenger.setCardno(tempPassenger.getCardno());// 证件号
							passenger.setTicketNumber(tempPassenger
									.getTicketNumber()); // 票号
							passenger.setType(tempPassenger.getType()); // 类型
							passenger.setStatus(1L);// 状态
							passengerDAO.save(passenger);
						}
					}
				}
			}
		}
	}


	public void saveFlightPassengerByOrderForm(AirticketOrder form, AirticketOrder newOrder,long status)
			throws AppException {
		// 乘机人
		String[] passengerIds = form.getPassengerIds();
		String[] passengerNames = form.getPassengerNames();
		String[] passengerCardno = form.getPassengerCardnos();
		String[] passengerTicketNumber = form.getPassengerTicketNumbers();
		for (int p = 0; p < passengerIds.length; p++) {
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passengerNames[p]); // 乘机人姓名
			passenger.setCardno(passengerCardno[p]);// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(status);// 状态
			passenger.setTicketNumber(passengerTicketNumber[p]); // 票号
			passengerDAO.save(passenger);
		}
		// 航班
		String[] flightIds = form.getFlightIds();
		String[] flightCodes = form.getFlightCodes();// 航班号
		String[] discounts = form.getDiscounts();// 折扣
		String[] startPoints = form.getStartPoints();// 出发地
		String[] endPoints = form.getEndPoints();// 目的地
		String[] flightClasss = form.getFlightClasss();// 舱位
		String[] boardingTimes = form.getBoardingTimes();// 起飞时间

		for (int f = 0; f < flightIds.length; f++) {
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(Constant.toUpperCase(flightCodes[f]));// 航班号
			flight.setStartPoint(Constant.toUpperCase(startPoints[f])); // 出发地
			flight.setEndPoint(Constant.toUpperCase(endPoints[f]));// 目的地
			flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[f]
					.toString(), "yyyy-MM-dd"));// 起飞时间
			flight.setDiscount(discounts[f]);// 折扣
			flight.setFlightClass(Constant.toUpperCase(flightClasss[f]));// 舱位
			flight.setStatus(status); // 状态
			flightDAO.save(flight);
		}
	}

	public void saveFlightPassengerByTempPNR(TempPNR tempPNR,
			AirticketOrder newOrder) throws AppException {
		// 乘机人
		List<TempPassenger> tempPassengerList = tempPNR.getTempPassengerList();
		List tempTicketsList = tempPNR.getTempTicketsList();
		for (int i = 0; i < tempPassengerList.size(); i++) {
			TempPassenger tempPassenger = tempPassengerList.get(i);
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(tempPassenger.getName()); // 乘机人姓名
			passenger.setCardno(tempPassenger.getNi());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			if (tempTicketsList != null&& tempTicketsList.size() == tempPassengerList.size()) {
				System.out.println("tempTicketsList===="+ tempPNR.getTempTicketsList().get(i));
				passenger.setTicketNumber(tempTicketsList.get(i).toString()); // 票号
			}
			passengerDAO.save(passenger);
		}

		// 航班
		List<TempFlight> tempFlightList = tempPNR.getTempFlightList();
		for (TempFlight tempFlight : tempFlightList) {
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(Constant.toUpperCase(tempFlight
							.getFlightNo()));// 航班号
			flight.setStartPoint(Constant.toUpperCase(tempFlight
					.getDepartureCity())); // 出发地
			flight.setEndPoint(Constant.toUpperCase(tempFlight
					.getDestineationCity()));// 目的地
			flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
			flight.setDiscount(tempFlight.getDiscount());// 折扣
			flight.setFlightClass(Constant.toUpperCase(tempFlight.getCabin()));// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);
		}
	}

	public AirticketOrder saveFlightPassengerInOrderByTempPNR(TempPNR tempPNR,
			AirticketOrder newOrder) throws AppException {
		// 乘机人
		List<TempPassenger> tempPassengerList = tempPNR.getTempPassengerList();
		Set tmpPassengerSet = new HashSet();

		List tempTicketsList = tempPNR.getTempTicketsList();
		for (int i = 0; i < tempPassengerList.size(); i++) {
			TempPassenger tempPassenger = tempPassengerList.get(i);
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(tempPassenger.getName()); // 乘机人姓名
			passenger.setCardno(tempPassenger.getNi());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			if (tempTicketsList != null
					&& tempTicketsList.size() == tempPassengerList.size()) {
				System.out.println("tempTicketsList===="
						+ tempPNR.getTempTicketsList().get(i));
				passenger.setTicketNumber(tempTicketsList.get(i).toString()); // 票号
			}
			tmpPassengerSet.add(passenger);
		}
		newOrder.setPassengers(tmpPassengerSet);
		// 航班
		List<TempFlight> tempFlightList = tempPNR.getTempFlightList();
		Set tmpFlightSet = new HashSet();
		for (TempFlight tempFlight : tempFlightList) {
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight
					.setFlightCode(Constant.toUpperCase(tempFlight
							.getFlightNo()));// 航班号
			flight.setStartPoint(Constant.toUpperCase(tempFlight
					.getDepartureCity())); // 出发地
			flight.setEndPoint(Constant.toUpperCase(tempFlight
					.getDestineationCity()));// 目的地
			flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
			flight.setDiscount(tempFlight.getDiscount());// 折扣
			flight.setFlightClass(Constant.toUpperCase(tempFlight.getCabin()));// 舱位
			flight.setStatus(1L); // 状态
			tmpFlightSet.add(flight);

		}
		newOrder.setFlights(tmpFlightSet);
		return newOrder;
	}

	public void saveFlightPassengerByRequest(HttpServletRequest request,
			AirticketOrder newOrder) throws AppException {
		// 乘机人
		String[] passNames = request.getParameterValues("passNames");// 乘客姓名
		String[] passTypes = request.getParameterValues("passTypes");// 类型
		// String[] passCardNos = request
		// .getParameterValues("passCardNos");// 证件号
		String[] passTicketNumbers = request
				.getParameterValues("passTicketNumbers");// 票号
		for (int p = 0; p < passNames.length; p++) {
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passNames[p]); // 乘机人姓名
			// passenger.setCardno(passCardNos[p]);// 证件号
			passenger.setType(Long.valueOf(passTypes[p])); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passTicketNumbers[p]); // 票号
			passengerDAO.save(passenger);
		}

		// 航班
		String[] startPoints = request.getParameterValues("startPoints");// 出发地
		String[] endPoints = request.getParameterValues("endPoints");// 目的地
		String[] boardingTimes = request.getParameterValues("boardingTimes");// 出发时间
		String[] flightCodes = request.getParameterValues("flightCodes");// 航班号
		String[] flightClasss = request.getParameterValues("flightClasss");// 舱位
		String[] discounts = request.getParameterValues("discounts");// 折扣

		for (int j = 0; j < flightCodes.length; j++) {

			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(Constant.toUpperCase(flightCodes[j]));// 航班号
			flight.setStartPoint(Constant.toUpperCase(startPoints[j])); // 出发地
			flight.setEndPoint(Constant.toUpperCase(endPoints[j]));// 目的地
			flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j]
					.toString(), "yyyy-MM-dd"));// 出发时间
			flight.setDiscount(discounts[j].toString());// 折扣
			flight.setFlightClass(Constant.toUpperCase(flightClasss[j]));// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);
		}
	}

	public void updateFlightPassengerByRequest(HttpServletRequest request,
			AirticketOrder newOrder) throws AppException {
		deleteOrderFlights(newOrder);
		deleteOrderPassengers(newOrder);

		// 乘机人
		String[] passids = request.getParameterValues("passids");// id
		String[] passNames = request.getParameterValues("passNames");// 乘客姓名
		String[] passTypes = request.getParameterValues("passTypes");// 类型
		// String[] passCardNos = request.getParameterValues("passCardNos");//
		// 证件号
		String[] passTicketNumbers = request
				.getParameterValues("passTicketNumbers");// 票号
		for (int p = 0; p < passNames.length; p++) {
			long pid = Constant.toLong(passids[p]);
			if (pid == 0) {
				Passenger passenger = new Passenger();
				passenger.setAirticketOrder(newOrder);
				passenger.setName(passNames[p]); // 乘机人姓名
				// passenger.setCardno(passTicketNumbers[p]);// 证件号
				passenger.setType(Long.valueOf(passTypes[p])); // 类型
				passenger.setStatus(1L);// 状态
				passenger.setTicketNumber(passTicketNumbers[p]); // 票号
				passengerDAO.save(passenger);
				System.out.println("passengerDAO  ok!");
			} else if (pid > 0) {
//				System.out.println("=====>>>update order:" + newOrder.getId()
//						+ "---passenger:" + pid);
				Passenger passenger = passengerDAO.getPassengerById(pid);
				passenger.setAirticketOrder(newOrder);
				passenger.setName(passNames[p]); // 乘机人姓名
				// passenger.setCardno(passCardNos[p]);// 证件号
				passenger.setType(Long.valueOf(passTypes[p])); // 类型
				passenger.setStatus(1L);// 状态
				passenger.setTicketNumber(passTicketNumbers[p]); // 票号
				passengerDAO.update(passenger);
			}
		}

		// 航班
		String[] flightIds = request.getParameterValues("flightIds");// id
		String[] startPoints = request.getParameterValues("startPoints");// 出发地
		String[] endPoints = request.getParameterValues("endPoints");// 目的地
		String[] boardingTimes = request.getParameterValues("boardingTimes");// 出发时间
		String[] flightCodes = request.getParameterValues("flightCodes");// 航班号
		String[] flightClasss = request.getParameterValues("flightClasss");// 舱位
		String[] discounts = request.getParameterValues("discounts");// 折扣
		if (flightCodes != null) {
			for (int j = 0; j < flightCodes.length; j++) {
				Long fid = Constant.toLong(flightIds[j]);
				if (fid == 0) {
					Flight flight = new Flight();
					flight.setAirticketOrder(newOrder);
					flight.setFlightCode(Constant.toUpperCase(flightCodes[j]));// 航班号
					flight.setStartPoint(Constant.toUpperCase(startPoints[j])); // 出发地
					flight.setEndPoint(Constant.toUpperCase(endPoints[j]));// 目的地
					flight.setBoardingTime(DateUtil.getTimestamp(
							boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
					flight.setDiscount(discounts[j].toString());// 折扣
					flight
							.setFlightClass(Constant
									.toUpperCase(flightClasss[j]));// 舱位
					flight.setStatus(1L); // 状态
					flightDAO.save(flight);
				} else if (fid > 0) {
//					System.out.println("=====>>>update order:"
//							+ newOrder.getId() + "---flight:" + fid);
					Flight flight = flightDAO.getFlightById(fid);
					flight.setAirticketOrder(newOrder);
					flight.setFlightCode(Constant.toUpperCase(flightCodes[j]));// 航班号
					flight.setStartPoint(Constant.toUpperCase(startPoints[j]
							.toString())); // 出发地
					flight.setEndPoint(Constant.toUpperCase(endPoints[j]));// 目的地
					flight.setBoardingTime(DateUtil.getTimestamp(
							boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
					flight.setDiscount(discounts[j].toString());// 折扣
					flight
							.setFlightClass(Constant
									.toUpperCase(flightClasss[j]));// 舱位
					flight.setStatus(1L); // 状态
					flightDAO.update(flight);
				}
			}
		}
	}

	public void updateSynFlightPassengerByRequest(HttpServletRequest request,
			List<AirticketOrder> orderList) throws AppException {
		AirticketOrder newOrder = orderList.get(0);
		for (int i = 0; i < orderList.size(); i++) {
			AirticketOrder order = orderList.get(i);
			deleteOrderFlights(order);
			deleteOrderPassengers(order);
		}

		// 乘机人
		String[] passids = request.getParameterValues("passids");// id
		String[] passNames = request.getParameterValues("passNames");// 乘客姓名
		String[] passTypes = request.getParameterValues("passTypes");// 类型
		// String[] passCardNos = request.getParameterValues("passCardNos");//
		// 证件号
		String[] passTicketNumbers = request
				.getParameterValues("passTicketNumbers");// 票号
		for (int p = 0; p < passNames.length; p++) {
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passNames[p]); // 乘机人姓名
			// passenger.setCardno(passTicketNumbers[p]);// 证件号
			passenger.setType(Long.valueOf(passTypes[p])); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passTicketNumbers[p]); // 票号
			passengerDAO.save(passenger);

			for (int i = 0; i < orderList.size(); i++) {
				int k = i + 1;
				if (k < orderList.size()) {
					AirticketOrder tempOrder = orderList.get(k);
					Passenger tempPassenger = (Passenger) passenger.clone();
					tempPassenger.setAirticketOrder(tempOrder);
					passengerDAO.save(tempPassenger);
				}
			}
		}

		// 航班
		String[] flightIds = request.getParameterValues("flightIds");// id
		String[] startPoints = request.getParameterValues("startPoints");// 出发地
		String[] endPoints = request.getParameterValues("endPoints");// 目的地
		String[] boardingTimes = request.getParameterValues("boardingTimes");// 出发时间
		String[] flightCodes = request.getParameterValues("flightCodes");// 航班号
		String[] flightClasss = request.getParameterValues("flightClasss");// 舱位
		String[] discounts = request.getParameterValues("discounts");// 折扣
		if (flightCodes != null) {
			for (int j = 0; j < flightCodes.length; j++) {
				Flight flight = new Flight();
				flight.setAirticketOrder(newOrder);
				flight.setFlightCode(Constant.toUpperCase(flightCodes[j]));// 航班号
				flight.setStartPoint(Constant.toUpperCase(startPoints[j])); // 出发地
				flight.setEndPoint(Constant.toUpperCase(endPoints[j]));// 目的地
				flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j]
						.toString(), "yyyy-MM-dd"));// 出发时间
				flight.setDiscount(discounts[j].toString());// 折扣
				flight.setFlightClass(Constant.toUpperCase(flightClasss[j]));// 舱位
				flight.setStatus(1L); // 状态
				flightDAO.save(flight);

				for (int i = 0; i < orderList.size(); i++) {
					int k = i + 1;
					if (k < orderList.size()) {
						AirticketOrder tempOrder = orderList.get(k);
						Flight tempFlight = (Flight) flight.clone();
						tempFlight.setAirticketOrder(tempOrder);
						flightDAO.save(tempFlight);
					}
				}
			}
		}

	}

	public void deleteOrderPassengers(AirticketOrder order) throws AppException {
//		System.out.println("===deleteOrderPassengers===id:" + order.getId());
		Set passengers = order.getPassengers();
		Iterator itrPassenger = passengers.iterator();
		while (itrPassenger.hasNext()) {
			Passenger passenger = (Passenger) itrPassenger.next();
			passenger.setAirticketOrder(null);
			passengerDAO.update(passenger);
//			System.out.println("===deleteOrderFlights===passenger id:"
//					+ passenger.getId());
		}
	}

	public void deleteOrderFlights(AirticketOrder order) throws AppException {
//		System.out.println("===deleteOrderFlights===id:" + order.getId());

		Set flights = order.getFlights();
		Iterator itr = flights.iterator();
		while (itr.hasNext()) {
			Flight flight = (Flight) itr.next();
			flight.setAirticketOrder(null);
			flightDAO.update(flight);
//			System.out.println("===deleteOrderFlights===flight id:"
//					+ flight.getId());
		}
	}

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}
}
