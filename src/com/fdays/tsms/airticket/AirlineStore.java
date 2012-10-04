package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.UnitConverter;
import com.neza.exception.AppException;

/**
 * 航线基准价、航空公司舱位折扣信息
 */
public class AirlineStore {
	public static List<Airline> airlineList = new ArrayList<Airline>();
	public static List<AirlinePlace> airlinePlaceList = new ArrayList<AirlinePlace>();
	
	// 设置票面折后价、燃油、机建
	public static TempPNR setTempPNRPrice(TempPNR tempPNR) throws AppException {
		try {
			String carrier = tempPNR.getCarrier();
			String cabin = tempPNR.getCabin();
			String begin = tempPNR.getBegin();
			String end = tempPNR.getEnd();

			if (carrier != null && "".equals(carrier) == false && cabin != null
					&& "".equals(cabin) == false && begin != null
					&& "".equals(begin) == false && end != null
					&& "".equals(end) == false) {
				tempPNR = getStandPriceByAirline(tempPNR, begin, end);

				BigDecimal rate = new BigDecimal(100);
				rate = getSpecialPriceByAirlineCompany(tempPNR);

				if (rate == null || rate == BigDecimal.ZERO) {
					cabin = cabin.trim().toUpperCase();
					rate = getRateByCarrierCode(carrier, cabin);
				}
				
				tempPNR=setRateForEachTempFlight(tempPNR,rate);//更新航段折扣

				rate = rate.divide(new BigDecimal(100));
				if (tempPNR.getFare() != null) {
					BigDecimal tempFare = tempPNR.getFare().multiply(rate);
					tempFare = UnitConverter.round_half_upBigDecimal(tempFare,
							-1);
					tempPNR.setFare(tempFare);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempPNR;
	}

	// 匹配特殊折扣
	private static BigDecimal getSpecialPriceByAirlineCompany(TempPNR tempPNR) {
		try {
			if (tempPNR != null) {
				String company = tempPNR.getCarrier();
				String code = tempPNR.getCabin();
				String begin = tempPNR.getBegin();
				String end = tempPNR.getEnd();
				if (airlineList != null) {
					for (int i = 0; i < airlineList.size(); i++) {
						Airline tempAirline = airlineList.get(i);
						String tempBegin = tempAirline.getBegin().toUpperCase();
						String tempEnd = tempAirline.getEnd().toUpperCase();

						boolean flag = begin.toUpperCase().equals(tempBegin)
								&& end.toUpperCase().equals(tempEnd);
						boolean flag2 = begin.toUpperCase().equals(tempEnd)
								&& end.toUpperCase().equals(tempBegin);

						if (flag || flag2) {
							AirlinePlace tempAirlinePlace = getAirlinePlaceByAirlineCompany(
									tempAirline, company, code);
							if (tempAirlinePlace != null) {
								System.out.println(company + "--" + code + "舱,"
										+ tempBegin + "--" + tempEnd
										+ "--符合特殊折扣规则："
										+ tempAirlinePlace.getRate());
								return new BigDecimal(tempAirlinePlace
										.getRate());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}

	// 设置基准价、燃油、机建
	private static TempPNR getStandPriceByAirline(TempPNR tempPNR,
			String begin, String end) {
		try {
			System.out.println("begin:" + begin + "--end:" + end);
			if (tempPNR != null) {
				if (airlineList != null) {
					for (int i = 0; i < airlineList.size(); i++) {
						Airline tempAirline = airlineList.get(i);
						String tempBegin = tempAirline.getBegin().toUpperCase();
						String tempEnd = tempAirline.getEnd().toUpperCase();

						boolean flag = begin.toUpperCase().equals(tempBegin)
								&& end.toUpperCase().equals(tempEnd);
						boolean flag2 = begin.toUpperCase().equals(tempEnd)
								&& end.toUpperCase().equals(tempBegin);

						if (flag || flag2) {
							System.out.println(tempBegin + "-" + tempEnd
									+ "--flag:" + flag + "--flag2:" + flag2);
							Long tempPrice = tempAirline.getPrice();
							if (tempPrice != null && tempPrice > 0) {
								tempPNR.setFare(new BigDecimal(tempPrice));
							}

							Long tempDistance = tempAirline.getDistance();
							if (tempDistance != null && tempDistance > 0) {
								tempPNR = Airline.setTempPNRAirportFuel(
										tempPNR, tempDistance);
							}

							System.out.println("基准价：" + tempPrice + "--航程："
									+ tempDistance);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempPNR;
	}

	public static BigDecimal getRateByCarrierCode(String carrier, String code) {
		System.out.println("getRateByCarrierCode()====>>" + carrier + "--code:"
				+ code);
		BigDecimal rate = new BigDecimal(100);
		try {
			if (airlinePlaceList != null) {
				for (int i = 0; i < airlinePlaceList.size(); i++) {
					AirlinePlace tempAirlinePlace = airlinePlaceList.get(i);
					String tempCarrier = tempAirlinePlace.getCompany()
							.toUpperCase();
					String tempCode = tempAirlinePlace.getCode().toUpperCase();
					if (tempCarrier != null && tempCode != null) {
						if (carrier.toUpperCase().equals(tempCarrier)
								&& code.toUpperCase().equals(tempCode)) {
							Long tempRate = tempAirlinePlace.getRate();
							if (tempRate != null && tempRate > 0) {
								System.out.println("found Rate:" + tempRate);
								return new BigDecimal(tempRate);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("not found Rate return 100 ");
		return rate;
	}

	public static AirlinePlace getAirlinePlaceByAirlineCompany(Airline airline,
			String company, String code) {
		AirlinePlace airlinePlace = null;
		try {
			if (airline != null && airlinePlaceList != null) {
				for (int i = 0; i < airlinePlaceList.size(); i++) {
					AirlinePlace tempAirlinePlace = airlinePlaceList.get(i);
					if (tempAirlinePlace.getAirline() == airline
							&& company.equals(tempAirlinePlace.getCompany())
							&& code.equals(tempAirlinePlace.getCode())) {
						return tempAirlinePlace;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return airlinePlace;
	}
	
	public static long getDiscountRateByCompany(String company, String code) {
		Long discountRate = new Long(0);
		try {
			if (airlinePlaceList != null) {
				for (int i = 0; i < airlinePlaceList.size(); i++) {
					AirlinePlace tempAirlinePlace = airlinePlaceList.get(i);
					Airline airline=tempAirlinePlace.getAirline();//特殊航线
					if (company.equals(tempAirlinePlace.getCompany())
							&& code.equals(tempAirlinePlace.getCode())) {
						return tempAirlinePlace.getRate();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discountRate;
	}
	
	public static long getDiscountRateByCompanyAirline(String company, String code,String beginPoint,String endPoint) {
		Long discountRate = new Long(0);
		try {
			if ( airlinePlaceList != null) {
				for (int i = 0; i < airlinePlaceList.size(); i++) {
					AirlinePlace tempAirlinePlace = airlinePlaceList.get(i);
					Airline airline=tempAirlinePlace.getAirline();//特殊航线
					
					if(isSameAirline(beginPoint,endPoint,airline)){
						if (company.equals(tempAirlinePlace.getCompany())
								&& code.equals(tempAirlinePlace.getCode())) {
							return tempAirlinePlace.getRate();
						}
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discountRate;
	}

	/**
	 * 更新折扣
	 * */
	public static TempPNR setRateForEachTempFlight(TempPNR tempPNR,
			BigDecimal discount) {
		if (tempPNR != null) {
			if (discount != null && discount.compareTo(BigDecimal.ZERO) > 0) {
				List<TempFlight> tempFlightList = tempPNR.getTempFlightList();
				if (tempFlightList != null) {
					List<TempFlight> newFlightList=new ArrayList<TempFlight>();
					for (int i = 0; i < tempFlightList.size(); i++) {
						TempFlight tempFlight=tempFlightList.get(i);
						
						if(tempFlight!=null){
							tempFlight.setDiscount(discount.toString());
							newFlightList.add(tempFlight);
						}
					}
					tempPNR.setTempFlightList(newFlightList);
					System.out.println("update TempFlightList success...");
				}
			}
		}
		return tempPNR;
	}
	
	/**
	 *是否为同一航线 
	 **/
	public static boolean isSameAirline(String beginPoint,String endPoint,Airline airline){
		boolean result=false;
		if(airline!=null){
			String tempBegin = Constant.toUpperCase(airline.getBegin());
			String tempEnd = Constant.toUpperCase(airline.getEnd());
			boolean flag1 = Constant.toUpperCase(beginPoint).equals(tempBegin)&&Constant.toUpperCase(endPoint).equals(tempEnd);
			boolean flag2 = Constant.toUpperCase(beginPoint).equals(tempEnd)&&Constant.toUpperCase(endPoint).equals(tempBegin);

			result=flag1 || flag2;
		}
		return result;
	}
}
