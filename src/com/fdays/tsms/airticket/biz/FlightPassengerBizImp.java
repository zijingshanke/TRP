package com.fdays.tsms.airticket.biz;

import java.util.HashSet;
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
	 * 
	 */
	public void saveFlightPassengerBySetForOrder(AirticketOrder newOrder,Set passengers,Set flights) throws AppException {
		saveFlightBySetForOrder(newOrder, flights);
		savePassengerBySetForOrder(newOrder, passengers);
	}

	public void saveFlightByOrder(AirticketOrder oldOrder,
			AirticketOrder newOrder) throws AppException {
		// 航班
		Set tempFlightList = oldOrder.getFlights();
		saveFlightBySetForOrder(newOrder, tempFlightList);
	}
	
	
	public void saveFlightBySetForOrder(AirticketOrder newOrder,Set flightSet)throws AppException{
		for (Object fobj : flightSet) {
			Flight tempflight = (Flight) fobj;
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(tempflight.getFlightCode());// 航班号
			flight.setStartPoint(tempflight.getStartPoint()); // 出发地
			flight.setEndPoint(tempflight.getEndPoint());// 目的地
			flight.setBoardingTime(tempflight.getBoardingTime());// 起飞时间
			flight.setDiscount(tempflight.getDiscount());// 折扣
			flight.setFlightClass(tempflight.getFlightClass());// 舱位			
			flight.setStatus(1L); // 状态
			
			flight.setTicketPrice(tempflight.getTicketPrice());
			flight.setAirportPriceAdult(tempflight.getAirportPriceAdult());
			flight.setFuelPriceAdult(tempflight.getFuelPriceAdult());
			flight.setAirportPriceChild(tempflight.getAirportPriceChild());
			flight.setFuelPriceChild(tempflight.getFuelPriceChild());			
			flightDAO.save(flight);
		}
	}
	
	
	public void saveFlightByIdsForOrder(AirticketOrder newOrder,String[] oldflightIds)throws AppException{
		for (int j = 0; j <oldflightIds.length; j++) {		
			if(oldflightIds[j]!=null&&"".equals(oldflightIds[j].trim())==false){
				long flightId=Long.parseLong(oldflightIds[j]);
				if(flightId>0){
					Flight tempflight=flightDAO.getFlightById(flightId);
					Flight flight = new Flight();
					flight.setAirticketOrder(newOrder);
					flight.setFlightCode(tempflight.getFlightCode());// 航班号
					flight.setStartPoint(tempflight.getStartPoint()); // 出发地
					flight.setEndPoint(tempflight.getEndPoint());// 目的地
					flight.setBoardingTime(tempflight.getBoardingTime());// 起飞时间
					flight.setDiscount(tempflight.getDiscount());// 折扣
					flight.setFlightClass(tempflight.getFlightClass());// 舱位			
					flight.setStatus(1L); // 状态
					
					flight.setTicketPrice(tempflight.getTicketPrice());
					flight.setAirportPriceAdult(tempflight.getAirportPriceAdult());
					flight.setFuelPriceAdult(tempflight.getFuelPriceAdult());
					flight.setAirportPriceChild(tempflight.getAirportPriceChild());
					flight.setFuelPriceChild(tempflight.getFuelPriceChild());	
					flight.setAirportPriceBaby(tempflight.getAirportPriceBaby());
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
		savePassengerBySetForOrder(newOrder, passengerSet);
	}
	
	public void savePassengerBySetForOrder(AirticketOrder newOrder,Set passengerSet)throws AppException{
		for (Object obj : passengerSet) {
			Passenger passengerTmp = (Passenger) obj;
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passengerTmp.getName()); // 乘机人姓名
			passenger.setCardno(passengerTmp.getCardno());// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passengerTmp.getTicketNumber()); // 票号
			passengerDAO.save(passenger);
		}		
	}
	

	public void saveFlightPassengerByOrderForm(
			AirticketOrder airticketOrderForm, AirticketOrder newOrder)
			throws AppException {
		// 乘机人
		String[] passengerIds = airticketOrderForm.getPassengerIds();
		String[] passengerNames = airticketOrderForm.getPassengerNames();
		String[] passengerCardno = airticketOrderForm.getPassengerCardno();
		String[] passengerTicketNumber = airticketOrderForm
				.getPassengerTicketNumber();
		for (int p = 0; p < passengerIds.length; p++) {
			int rowCount = Integer.valueOf(passengerIds[p]);
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passengerNames[rowCount]); // 乘机人姓名
			passenger.setCardno(passengerCardno[rowCount]);// 证件号
			passenger.setType(1L); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passengerTicketNumber[rowCount]); // 票号
			passengerDAO.save(passenger);
		}
		// 航班
		String[] flightIds = airticketOrderForm.getFlightIds();
		String[] flightCodes = airticketOrderForm.getFlightCodes();// 航班号
		String[] discounts = airticketOrderForm.getDiscounts();// 折扣
		String[] startPoints = airticketOrderForm.getStartPoints();// 出发地
		String[] endPoints = airticketOrderForm.getEndPoints();// 目的地
		String[] flightClasss = airticketOrderForm.getFlightClasss();// 舱位
		String[] boardingTimes = airticketOrderForm.getBoardingTimes();// 起飞时间

		for (int f = 0; f < flightIds.length; f++) {
			int fCount = Integer.valueOf(flightIds[f]);
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(flightCodes[fCount]);// 航班号
			flight.setStartPoint(startPoints[fCount]); // 出发地
			flight.setEndPoint(endPoints[fCount]);// 目的地
			flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[fCount]
					.toString(), "yyyy-MM-dd"));// 起飞时间
			flight.setDiscount(discounts[fCount]);// 折扣
			flight.setFlightClass(flightClasss[fCount]);// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);
		}
	}
	
	public void saveFlightPassengerByTempPNR(TempPNR tempPNR,AirticketOrder newOrder)throws AppException{
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
			if (tempTicketsList != null
					&& tempTicketsList.size() == tempPassengerList.size()) {
				System.out.println("tempTicketsList===="
						+ tempPNR.getTempTicketsList().get(i));
				passenger.setTicketNumber(tempTicketsList.get(i).toString()); // 票号
			}
			passengerDAO.save(passenger);
		}

		// 航班
		List<TempFlight> tempFlightList = tempPNR.getTempFlightList();
		for (TempFlight tempFlight : tempFlightList) {
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(tempFlight.getAirline());// 航班号
			flight.setStartPoint(tempFlight.getDepartureCity()); // 出发地
			flight.setEndPoint(tempFlight.getDestineationCity());// 目的地
			flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
			flight.setDiscount(tempFlight.getDiscount());// 折扣
			flight.setFlightClass(tempFlight.getCabin());// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);
		}
	}
	
	
	public AirticketOrder saveFlightPassengerInOrderByTempPNR(TempPNR tempPNR,AirticketOrder newOrder)throws AppException{
		// 乘机人
		List<TempPassenger> tempPassengerList = tempPNR
				.getTempPassengerList();
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
					&& tempTicketsList.size() == tempPassengerList
							.size()) {
				System.out.println("tempTicketsList===="
						+ tempPNR.getTempTicketsList().get(i));
				passenger.setTicketNumber(tempTicketsList.get(i)
						.toString()); // 票号
			}
			tmpPassengerSet.add(passenger);
		}
		newOrder.setPassengers(tmpPassengerSet);
		// 航班
		List<TempFlight> tempFlightList = tempPNR
				.getTempFlightList();
		Set tmpFlightSet = new HashSet();
		for (TempFlight tempFlight : tempFlightList) {
			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(tempFlight.getAirline());// 航班号
			flight.setStartPoint(tempFlight.getDepartureCity()); // 出发地
			flight.setEndPoint(tempFlight.getDestineationCity());// 目的地
			flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
			flight.setDiscount(tempFlight.getDiscount());// 折扣
			flight.setFlightClass(tempFlight.getCabin());// 舱位
			flight.setStatus(1L); // 状态
			tmpFlightSet.add(flight);

		}
		newOrder.setFlights(tmpFlightSet);
		return newOrder;
	}
	
	
	public void saveFlightPassengerByRequest(HttpServletRequest request,AirticketOrder newOrder)throws AppException{
		// 乘机人
		String[] passNames = request.getParameterValues("passNames");// 乘客姓名
		String[] passTypes = request.getParameterValues("passTypes");// 类型
//		String[] passCardNos = request
//				.getParameterValues("passCardNos");// 证件号
		String[] passTicketNumbers = request
				.getParameterValues("passTicketNumbers");// 票号
		for (int p = 0; p < passNames.length; p++) {
			Passenger passenger = new Passenger();
			passenger.setAirticketOrder(newOrder);
			passenger.setName(passNames[p]); // 乘机人姓名
//			passenger.setCardno(passCardNos[p]);// 证件号
			passenger.setType(Long.valueOf(passTypes[p])); // 类型
			passenger.setStatus(1L);// 状态
			passenger.setTicketNumber(passTicketNumbers[p]); // 票号
			passengerDAO.save(passenger);
		}

		// 航班
		String[] startPoints = request.getParameterValues("startPoints");// 出发地
		String[] endPoints = request.getParameterValues("endPoints");// 目的地
		String[] boardingTimes = request
				.getParameterValues("boardingTimes");// 出发时间
		String[] flightCodes = request.getParameterValues("flightCodes");// 航班号
		String[] flightClasss = request.getParameterValues("flightClasss");// 舱位
		String[] discounts = request.getParameterValues("discounts");// 折扣

		for (int j = 0; j < flightCodes.length; j++) {

			Flight flight = new Flight();
			flight.setAirticketOrder(newOrder);
			flight.setFlightCode(flightCodes[j].toString());// 航班号
			flight.setStartPoint(startPoints[j].toString()); // 出发地
			flight.setEndPoint(endPoints[j].toString());// 目的地
			flight.setBoardingTime(DateUtil.getTimestamp(boardingTimes[j]
					.toString(), "yyyy-MM-dd"));// 出发时间
			flight.setDiscount(discounts[j].toString());// 折扣
			flight.setFlightClass(flightClasss[j].toString());// 舱位
			flight.setStatus(1L); // 状态
			flightDAO.save(flight);
		}		
	}
	
	public void updateFlightPassengerByRequest(HttpServletRequest request,AirticketOrder newOrder)throws AppException{
		// 乘机人
		String[] passids = request.getParameterValues("passids");// id
		String[] passNames = request.getParameterValues("passNames");// 乘客姓名
		String[] passTypes = request.getParameterValues("passTypes");// 类型
//		String[] passCardNos = request.getParameterValues("passCardNos");// 证件号
		String[] passTicketNumbers = request.getParameterValues("passTicketNumbers");// 票号
		for (int p = 0; p < passNames.length; p++) {
			long pid = Long.valueOf(passids[p].toString());
			if (pid == 0) {
				Passenger passenger = new Passenger();
				passenger.setAirticketOrder(newOrder);
				passenger.setName(passNames[p]); // 乘机人姓名
//				passenger.setCardno(passTicketNumbers[p]);// 证件号
				passenger.setType(Long.valueOf(passTypes[p])); // 类型
				passenger.setStatus(1L);// 状态
				passenger.setTicketNumber(passTicketNumbers[p]); // 票号
				passengerDAO.save(passenger);
				System.out.println("passengerDAO  ok！");
			} else if (pid > 0) {
				Passenger passenger = passengerDAO.passengerById(pid);
				passenger.setName(passNames[p]); // 乘机人姓名
//				passenger.setCardno(passCardNos[p]);// 证件号
				passenger.setType(Long.valueOf(passTypes[p])); // 类型
				passenger.setStatus(1L);// 状态
				passenger.setTicketNumber(passTicketNumbers[p]); // 票号
				passengerDAO.update(passenger);
				System.out.println("update airticketOrder passenger ok！" + newOrder.getId());
			}
		}

		// 航班
		String[] flightIds = request.getParameterValues("flightIds");// id
		String[] startPoints = request
				.getParameterValues("startPoints");// 出发地
		String[] endPoints = request.getParameterValues("endPoints");// 目的地
		String[] boardingTimes = request
				.getParameterValues("boardingTimes");// 出发时间
		String[] flightCodes = request
				.getParameterValues("flightCodes");// 航班号
		String[] flightClasss = request
				.getParameterValues("flightClasss");// 舱位
		String[] discounts = request.getParameterValues("discounts");// 折扣

		for (int j = 0; j < flightCodes.length; j++) {
			long fid = Long.valueOf(flightIds[j].toString());
			if (fid == 0) {
				Flight flight = new Flight();
				flight.setAirticketOrder(newOrder);
				flight.setFlightCode(flightCodes[j].toString());// 航班号
				flight.setStartPoint(startPoints[j].toString()); // 出发地
				flight.setEndPoint(endPoints[j].toString());// 目的地
				flight.setBoardingTime(DateUtil.getTimestamp(
						boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
				flight.setDiscount(discounts[j].toString());// 折扣
				flight.setFlightClass(flightClasss[j].toString());// 舱位
				flight.setStatus(1L); // 状态
				flightDAO.save(flight);
			} else if (fid > 0) {
				Flight flight = flightDAO.getFlightById(fid);
				flight.setFlightCode(flightCodes[j].toString());// 航班号
				flight.setStartPoint(startPoints[j].toString()); // 出发地
				flight.setEndPoint(endPoints[j].toString());// 目的地
				flight.setBoardingTime(DateUtil.getTimestamp(
						boardingTimes[j].toString(), "yyyy-MM-dd"));// 出发时间
				flight.setDiscount(discounts[j].toString());// 折扣
				flight.setFlightClass(flightClasss[j].toString());// 舱位
				flight.setStatus(1L); // 状态
				flightDAO.update(flight);
			}
		}
	}
	
	

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}
	
	
}
