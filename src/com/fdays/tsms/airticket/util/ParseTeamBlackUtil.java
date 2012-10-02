package com.fdays.tsms.airticket.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.base.util.StringUtil;

/**
 * 解析团队黑屏PNR信息
 * 
 * @author YanRui
 */
public class ParseTeamBlackUtil extends ParseBlackUtil {
	//
	public static String regEx_pnr_Team = "(\\d\\.[\\s*+\\D])";
	public static String regEx_passenger_Team = "([A-Z]{5}+ \\s*+(\\d\\.[\\W]))";
	private static String regEx_flight_Team1 = "(([\\D]){1}+[A-Z0-9]{2}[0-9]{3,4}+ \\s*[A-Z]{1}+ \\s*+[A-Z0-9]{6,7}+\\s+[A-Z]{6}+\\s*+[A-Z0-9]{3,4}+\\s*+[0-9]{4}+\\s*+[0-9]{4})";

	public static void main(String[] args) {
		String sampleTxt = "E:\\tsms\\doc\\PNRSample\\Team1.txt";
		getTempPNRByTeamBlack(sampleTxt, Type_Path);
	}

	public static TempPNR getTempPNRByTeamBlack(String sampleTxt, int type) {
		TempPNR tempPnr = new TempPNR();

		String sampleInfo = getSampleInfo(type, sampleTxt);

		// ----编码
		tempPnr=getSubPnr(tempPnr,sampleInfo);

		// ----乘机人
//		String passengerLine = getPassengerLine_regEx_Team(sampleInfo);
		// tempPnr = getPassenger(tempPnr, passengerLine);

		// ----航程类型一
		// String[] flightLine = getFlightLine_regEx_team_type1(sampleInfo);
		// tempPnr = getFlight_regEx_team_type1(tempPnr, flightLine);

		// ----票号
		// String lineInfo[] = getLineInfo(type, sampleTxt);
		// tempPnr = getTeamTicketNo(tempPnr, lineInfo);

		printTempPNRInfo(tempPnr);
		return tempPnr;
	}
	
	/**
	 * 匹配预定编码
	 * */
	public static TempPNR getSubPnr(TempPNR tempPnr,String sampleInfo){
		String pnrLine="";
		
		Pattern p = Pattern.compile(regEx_pnr_Team);

		Matcher m = p.matcher(sampleInfo);
		if (m.find()) {
			pnrLine = m.group();
		}

		System.out.println("pnrLine:" + pnrLine);
		
		String pnr="";
		
		tempPnr.setPnr(pnr);
		return tempPnr;
	}
	
	/**
	 * 匹配乘机人(团队编码)
	 */
	public static String getPassengerLine_regEx_Team(String sampleInfo) {
		String passengerLine = "";
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");

//		Pattern p = Pattern.compile(regEx_passenger_Team);
//
//		String[] rsArray = p.split(sampleInfo);
//
//		int rsArrayLen = rsArray.length;
//
//		if (rsArrayLen > 0) {
//			passengerLine = rsArray[0];
//		}
//		myLog.info("passengerLine1:" + passengerLine);

		Pattern p2 = Pattern.compile(regEx_passenger_Team);
		Matcher m2 = p2.matcher(passengerLine);
		if (m2.find()) {
			passengerLine = m2.group();
		}
		myLog.info("passengerLine2:" + passengerLine);
		return passengerLine;
	}

	/**
	 * 匹配航程 行 团队类型一
	 */
	public static String[] getFlightLine_regEx_team_type1(String sampleInfo) {
		String[] flightLine = new String[5];
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");
		Pattern p = Pattern.compile(regEx_flight_Team1);
		Matcher m = p.matcher(sampleInfo);

		myLog.info("---------团队原始航程信息-----");
		int j = 0;
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.find()) {
				flightLine[j] = m.group();
				myLog.info(m.group());
				j++;
			}
		}
		return flightLine;
	}

	/**
	 * 获取航班信息类型一
	 */
	public static TempPNR getFlight_regEx_team_type1(TempPNR tempPnr,
			String[] lineInfo) {
		List<TempFlight> flights = new ArrayList<TempFlight>();

		for (int j = 0; j < lineInfo.length; j++) {
			String flightString = lineInfo[j];
			if (flightString != null && "".equals(flightString) == false) {
				// System.out.println("flightString:" + flightString);
				TempFlight flight = getTempFlightByLineInfo_regEx_team_type1(flightString);
				flights.add(flight);
			}
		}
		tempPnr.setTempFlightList(flights);
		return tempPnr;
	}

	/**
	 * 获取单条航班信息（正则取String）类型一
	 */
	public static TempFlight getTempFlightByLineInfo_regEx_team_type1(
			String content) {
		TempFlight flight = new TempFlight();

		String result[] = content.split(" ");
		result = StringUtil.removeSpilthSpace(result, 10);

		String tempBoradingDate = "";
		String tempBoradingTime = "";

		for (int i = 0; i < result.length; i++) {
			String info = result[i];

			if (info != null && info.length() > 0) {
				// System.out.println(i + "--" + info);
				if (i == 0) {
					flight.setFlightNo(info);// 航班号
				}
				if (i == 1) {
					flight.setCabin(info);
				}
				if (i == 2) {
					// System.out.println("getTempFlightByLineInfo_regEx:" +
					// info);
					tempBoradingDate = info.substring(2, info.length());
				}
				if (i == 5) {
					tempBoradingTime = info.substring(0, 2) + ":"
							+ info.substring(2, info.length());
				}

				if (i == 3) {
					flight.setDepartureCity(info.substring(0, 3));
					flight.setDestineationCity(info.substring(3, 6));
				}
			}
		}
		flight.setTempDate(tempBoradingDate, tempBoradingTime);
		return flight;
	}

	/**
	 * 获取票号(团队)
	 */
	public static TempPNR getTeamTicketNo(TempPNR tempPnr, String[] lineInfo) {
		List<TempPassenger> passengers = tempPnr.getTempPassengerList();
		List tempTicketList = tempPnr.getTempTicketsList();
		if (tempTicketList == null) {
			tempTicketList = new ArrayList();
		}

		int ticketIndex = 0;
		for (int i = 0; i < lineInfo.length; i++) {
			String info = lineInfo[i];
			if (info != null && "".equals(info) == false) {
				int flag = info.indexOf(".TN/");
				String ticketNo = "";
				if (flag > 0) {
					info = info.trim();
					ticketNo = info.substring(flag + 4, flag + 4 + 14);
					if (ticketNo.trim().length() < 13) {
						continue;
					} else {
						tempTicketList.add(ticketNo);
						ticketIndex += 1;
					}

				}
			}
		}
		tempPnr.setTempTicketsList(tempTicketList);

		return tempPnr;
	}

}
