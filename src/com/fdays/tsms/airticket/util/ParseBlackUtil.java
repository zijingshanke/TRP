package com.fdays.tsms.airticket.util;

import java.io.BufferedReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.base.util.StringUtil;
import com.neza.tool.DateUtil;

/**
 * 解析黑屏PNR信息
 * 
 * @author YanRui
 */
public class ParseBlackUtil {
	public static String regEx_passenger = "(\\d\\.[\\s+\\D])+ \\s*?[A-Z0-9]{5}";
	private static String regEx_flight1 = "((\\d\\.[\\s+\\D]){1}+ \\s*+[A-Z0-9]{2}[0-9]{3,4}+ \\s*[A-Z]{1}+ \\s+[A-Z0-9]{6,7}+\\s+[A-Z]{6}+\\s*+[A-Z0-9]{3}+\\s*+[0-9]{4}+\\s*+[0-9]{4})";
	private static String regEx_flight2 = "((\\d\\.[\\s+\\D]){1}+ \\s*+[A-Z0-9]{2}[0-9]{3,4}+ \\s*[A-Z]{1}+ \\s+[A-Z0-9]{14,15}+\\s+[A-Z0-9]{3}+\\s+[0-9]{4})";

	private static String regEx_passengerCancel = "(\\d\\.[\\W+(\\)\\d]+?)+ \\s*?[A-Z0-9]{5}";
	private static String regEx_passengerCancel2 = "(\\d\\.+[\\s+\\D])+ \\s*?[A-Z0-9]{5}";

	public static String regEx_ticketno = "([0-9]{3}-[0-9]{10}-[0-9]{2})|([0-9]{3}-[0-9]{10})";// ([0-9]{13})|
	private static String regEx_BPNR1 = "((RMK)+\\s*+[A-Z]{2}+/+\\s*+[A-Z0-9]{5,6})";// RMK
	// CA/ABCDE1
	private static String regEx_BPNR2 = "([\\-]{1}+\\s*+[A-Z]{2}[\\-]{1}[A-Z]{5,6})";// -CA-ABCDE2

	private static String CancelString = "*THIS PNR WAS ENTIRELY CANCELLED*";

	public static int Type_Content = 1;
	public static int Type_Path = 2;

	public static void main(String[] args) {
		// replaceBlank();

		String sampleTxt = "E:\\tsms\\doc\\PNRSample\\BlackSample2.txt";

		TempPNR tempPnr = getTempPNRByBlack(sampleTxt, Type_Path);

		printTempPNRInfo(tempPnr);
	}

	public static TempPNR getTempPNRByBlack(String sampleTxt, int type) {
		TempPNR tempPnr = new TempPNR();

		String sampleInfo = getSampleInfo(type, sampleTxt);
		if (sampleInfo.indexOf(CancelString) >= 0) {
			sampleInfo = sampleInfo.replaceAll(
					"THIS PNR WAS ENTIRELY CANCELLED", " ");
			String passengerLine = getPassengerLine_regEx_cancel(sampleInfo);

			tempPnr = getPassenger_cancel(tempPnr, passengerLine);
		} else {
			String passengerLine = getPassengerLine_regEx(sampleInfo);
			tempPnr = getPassenger(tempPnr, passengerLine);
		}
		tempPnr = getTicketNo_regEx(tempPnr, sampleInfo);

		tempPnr = getBPNR1(tempPnr, sampleInfo);

		if (tempPnr.getB_pnr() == null
				&& "".equals(tempPnr.getB_pnr()) == false) {
			tempPnr = getBPNR2(tempPnr, sampleInfo);
		}

		// ----航程类型一
		String[] flightLine = getFlightLine_regEx_type1(sampleInfo);
		tempPnr = getFlight_regEx_type1(tempPnr, flightLine);

		if (tempPnr.getTempFlightList() == null
				|| tempPnr.getTempFlightList().size() <= 0) {
			// ----航程类型二
			String[] flightLine2 = getFlightLine_regEx_type2(sampleInfo);
			tempPnr = getFlight_regEx_type2(tempPnr, flightLine2);
		}
		return tempPnr;
	}

	public static void printTempPNRInfo(TempPNR tempPnr) {
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");
		if (tempPnr != null) {
			myLog.info("预定编码：" + tempPnr.getPnr());
			myLog.info("大编码：" + tempPnr.getB_pnr());
			List<TempFlight> flightList = tempPnr.getTempFlightList();
			if (flightList != null && flightList.size() > 0) {
				for (int j = 0; j < flightList.size(); j++) {
					TempFlight flight = flightList.get(j);
					myLog.info((j + 1)
							+ "-航班："
							+ flight.getFlightNo()
							+ "-舱位："
							+ flight.getCabin()
							+ "出发："
							+ flight.getDepartureCity()
							+ "-到达："
							+ flight.getDestineationCity()
							+ "-出发日期："
							+ DateUtil.getDateString(new Date(flight.getDate()
									.getTime()), "yyyy-MM-dd")
							+ "--时间:"
							+ DateUtil.getDateString(new Date(flight
									.getStarttime().getTime()), "HH:mm:ss"));
				}
			}

			List ticketList = tempPnr.getTempTicketsList();
			if (ticketList != null && ticketList.size() > 0) {
				if (ticketList != null && ticketList.size() > 0) {
					for (int k = 0; k < ticketList.size(); k++) {
						String ticketNo = (String) ticketList.get(k);
						myLog.info((k + 1) + "-票号：" + ticketNo);
					}
				}
			}

			List passengerList = tempPnr.getTempPassengerList();
			if (passengerList != null && passengerList.size() > 0) {
				for (int m = 0; m < passengerList.size(); m++) {
					TempPassenger passenger = (TempPassenger) passengerList
							.get(m);
					if (passenger != null) {
						myLog.info((m + 1) + "-乘机人：" + passenger.getName());
					}

				}
			}
		}

		myLog.info("票价：" + tempPnr.getFare() + "--机建：" + tempPnr.getTax()
				+ "--燃油：" + tempPnr.getYq());

	}

	/**
	 * 匹配航程 行 类型一
	 */
	public static String[] getFlightLine_regEx_type1(String sampleInfo) {
		String[] flightLine = new String[5];
//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");
		sampleInfo = sampleInfo.replaceAll("[^A-Z0-9\\.\\*]|[\\s]", " ");

		Pattern p = Pattern.compile(regEx_flight1);
		Matcher m = p.matcher(sampleInfo);

//		myLog.info("----------原始航程信息-----");
		int j = 0;
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.find()) {
				flightLine[j] = m.group();
//				myLog.info(m.group());
				j++;
			}
		}
		return flightLine;
	}

	/**
	 * 匹配航程 行 类型二
	 */
	public static String[] getFlightLine_regEx_type2(String sampleInfo) {
		String[] flightLine = new String[5];
//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");
		Pattern p = Pattern.compile(regEx_flight2);
		Matcher m = p.matcher(sampleInfo);

//		myLog.info("------匹配特殊航程类型--原始信息-----");
		int j = 0;
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.find()) {
				flightLine[j] = m.group();
//				myLog.info("匹配到的：" + m.group());
				j++;
			}
		}
		return flightLine;
	}

	/**
	 * 匹配乘机人、编码行(正常编码)
	 */
	public static String getPassengerLine_regEx(String sampleInfo) {
		String passengerLine = "";
//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");

		Pattern p = Pattern.compile(regEx_passenger);
		

		String[] rsArray = p.split(sampleInfo);
		int rsArrayLen = rsArray.length;

		if (rsArrayLen > 0) {
			passengerLine = rsArray[0];
		}
		
//		myLog.info("-----原始乘机人信息---");
//		myLog.info(passengerLine);
		return passengerLine;
	}

	/**
	 * 匹配乘机人、编码行(编码已取消)
	 */
	public static String getPassengerLine_regEx_cancel(String sampleInfo) {
		String passengerLine = "";

//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");
		sampleInfo = sampleInfo.replaceAll("X", "");
		sampleInfo = sampleInfo.replaceAll("\\((001)\\)", "");

		Pattern p = Pattern.compile(regEx_passengerCancel2);

		String[] rsArray = p.split(sampleInfo);
		int rsArrayLen = rsArray.length;

		if (rsArrayLen > 0) {
			passengerLine = rsArray[0];
		}

//		myLog.info("passengerLine:" + passengerLine);

		Pattern p2 = Pattern.compile(regEx_passengerCancel);
		Matcher m = p2.matcher(passengerLine);
		if (m.find()) {
			passengerLine = m.group();
		}
//		myLog.info("passengerLine:" + passengerLine);
		return passengerLine;
	}

	/**
	 * 获取航班信息类型一
	 */
	public static TempPNR getFlight_regEx_type1(TempPNR tempPnr,
			String[] lineInfo) {
		List<TempFlight> flights = new ArrayList<TempFlight>();

		for (int j = 0; j < lineInfo.length; j++) {
			String flightString = lineInfo[j];
			if (flightString != null && "".equals(flightString) == false) {
				// System.out.println("flightString:" + flightString);
				TempFlight flight = getTempFlightByLineInfo_regEx_type1(flightString);
				flights.add(flight);
			}
		}
		tempPnr.setTempFlightList(flights);
		return tempPnr;
	}

	/**
	 * 获取航班信息类型二
	 */
	public static TempPNR getFlight_regEx_type2(TempPNR tempPnr,
			String[] lineInfo) {
		List<TempFlight> flights = new ArrayList<TempFlight>();

		for (int j = 0; j < lineInfo.length; j++) {
			String flightString = lineInfo[j];
			if (flightString != null && "".equals(flightString) == false) {
				// System.out.println("flightString:" + flightString);
				TempFlight flight = getTempFlightByLineInfo_regEx_type2(flightString);
				flights.add(flight);
			}
		}
		tempPnr.setTempFlightList(flights);
		return tempPnr;
	}

	/**
	 * 正则匹配票号
	 */
	public static TempPNR getTicketNo_regEx(TempPNR tempPnr, String sampleInfo) {
		try{

		List tempTicketList = tempPnr.getTempTicketsList();
		if (tempTicketList == null) {
			tempTicketList = new ArrayList();
		}

		int ticketIndex = 0;
		int passengerSize = 0;
		List<TempPassenger> passengers = tempPnr.getTempPassengerList();
		if (passengers != null) {
			passengerSize = passengers.size();
		}

//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");
		sampleInfo = sampleInfo.replaceAll("[^A-Z0-9\\.\\*\\-]|[\\s]", " ");

		Pattern p = Pattern.compile(regEx_ticketno);
		Matcher m = p.matcher(sampleInfo);

//		myLog.info("----------原始票号信息-----");
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.find()) {
				String tempTicketNo = m.group();
//				myLog.info(tempTicketNo);
				int tempTicketNoLength = tempTicketNo.length();
				String tempFlaeMore = tempTicketNo.substring(
						tempTicketNoLength - 3, tempTicketNoLength - 2);
				// System.out.println("tempFlaeMore:" + tempFlaeMore);
				if ("-".equals(tempFlaeMore)) {
					return getSerialTicketNo(tempPnr, tempTicketNo,
							tempTicketList, ticketIndex, passengerSize);
				} else {
					if (ticketIndex <= (passengerSize - 1)) {
						tempTicketList.add(tempTicketNo);
						ticketIndex += 1;
					}
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPnr;
	}

	public static TempPNR getSerialTicketNo(TempPNR tempPnr, String serialNo,
			List tempTicketList, int ticketIndex, int passengerSize) {
		try{
		if (serialNo != null && "".equals(serialNo) == false) {
			serialNo = serialNo.trim();
			if (serialNo.length() == 17) {
				String tickeBase = serialNo.substring(0, serialNo.length() - 5);
				// System.out.println(tickeBase);
				String beginNoStr = serialNo.substring(serialNo.length() - 5,
						serialNo.length() - 3);
				// System.out.println(beginNoStr);
				String endNoStr = serialNo.substring(serialNo.length() - 2,
						serialNo.length());
				// System.out.println(endNoStr);

				int[] tempNo = StringUtil.getUnitNoByBeginString(beginNoStr,
						endNoStr, 1);

				for (int j = 0; j < tempNo.length; j++) {
					String ticketNo = tickeBase + tempNo[j];
					if (ticketIndex <= (passengerSize - 1)) {
						tempTicketList.add(ticketNo);
						ticketIndex += 1;
						tempPnr.setTempTicketsList(tempTicketList);
					}
				}
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPnr;
	}

	/**
	 * 获取单条航班信息（正则取String）类型一
	 */
	public static TempFlight getTempFlightByLineInfo_regEx_type1(String content) {
		TempFlight flight = new TempFlight();

		String result[] = content.split(" ");
		result = StringUtil.removeSpilthSpace(result, 10);

		String tempBoradingDate = "";
		String tempBoradingTime = "";

		for (int i = 0; i < result.length; i++) {
			String info = result[i];

			if (info != null && info.length() > 0) {
				// System.out.println(i + "--" + info);
				if (i == 1) {
					flight.setFlightNo(info);// 航班号
				}
				if (i == 2) {
					flight.setCabin(info);
				}
				if (i == 3) {
					// System.out.println("getTempFlightByLineInfo_regEx:" +
					// info);
					tempBoradingDate = info.substring(2, info.length());
				}
				if (i == 6) {
					tempBoradingTime = info.substring(0, 2) + ":"
							+ info.substring(2, info.length());
				}

				if (i == 4) {
					flight.setDepartureCity(info.substring(0, 3));
					flight.setDestineationCity(info.substring(3, 6));
				}
			}
		}
		flight.setTempDate(tempBoradingDate, tempBoradingTime);
		return flight;
	}

	/**
	 * 获取单条航班信息（正则取String）类型二
	 */
	public static TempFlight getTempFlightByLineInfo_regEx_type2(String content) {
		TempFlight flight = new TempFlight();

		String result[] = content.split(" ");
		result = StringUtil.removeSpilthSpace(result, 10);

		String tempBoradingDate = "";
		String tempBoradingTime = "";

		for (int i = 0; i < result.length; i++) {
			String info = result[i];

			if (info != null && info.length() > 0) {
				// System.out.println(i + "--" + info);
				if (i == 1) {
					flight.setFlightNo(info);// 航班号
				}
				if (i == 2) {
					flight.setCabin(info);
				}
				if (i == 3) {
					int infoLength = info.length();
					// System.out.println(i + "--" + info + "--length:"+
					// infoLength);
					info = info.trim();
					String dateinfo = info.substring(0, infoLength - 8);
					// System.out.println(dateinfo);
					tempBoradingDate = dateinfo.substring(2, dateinfo.length());

					String departureCity = info.substring(infoLength - 6,
							infoLength - 3);
					String destineationCity = info.substring(infoLength - 3,
							infoLength);
					// System.out.println("departureCity:" + departureCity);
					// System.out.println("destineationCity:" +
					// destineationCity);
					flight.setDepartureCity(departureCity);
					flight.setDestineationCity(destineationCity);
				}
				if (i == 5) {
					tempBoradingTime = info.substring(0, 2) + ":"
							+ info.substring(2, info.length());
					// System.out.println(tempBoradingTime);
				}
			}
		}
		flight.setTempDate(tempBoradingDate, tempBoradingTime);
		return flight;
	}

	/**
	 * 获取乘机人姓名
	 */
	public static TempPNR getPassenger(TempPNR tempPnr, String content) {
		List<TempPassenger> passengers = new ArrayList<TempPassenger>();
		TempPassenger passenger;
		try{
		String result[] = content.split(" ");
		int count = result.length;

		for (int i = 0; i < count; i++) {
			String info = result[i];
			if(info!=null&&"".equals(info.trim())==false){
				// System.out.println("temp passenger:" + info);
				if (info.length() > 0) {

					int flag = info.indexOf(".", 1);

					if (flag > -1) {
						String name = info.substring(flag + 1, info.length());
						passenger = new TempPassenger();
						passenger.setName(name);
						passenger.setNi("");
						passengers.add(passenger);
					} else {
						tempPnr.setPnr(info);
					}
				}				
			}			
		}
		tempPnr.setTempPassengerList(passengers);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPnr;
	}

	/**
	 * 获取乘机人姓名(已取消编码)
	 */
	public static TempPNR getPassenger_cancel(TempPNR tempPnr, String content) {
		List<TempPassenger> passengers = new ArrayList<TempPassenger>();
		TempPassenger passenger;

		// System.out.println("content:" + content);
		try{
		String result[] = content.split(" ");
		int count = result.length;

		for (int i = 0; i < count; i++) {
			String info = result[i];

			if (info.length() > 0) {
				int flag = info.indexOf(".", 1);
				// System.out.println("passenger:"+info + "--" + flag);
				if (flag > -1) {
					String name = info.substring(flag + 1, info.length());
					passenger = new TempPassenger();
					passenger.setName(name);
					passenger.setNi("");
					passengers.add(passenger);
				} else {
					tempPnr.setPnr(info);
				}
			}
		}
		tempPnr.setTempPassengerList(passengers);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPnr;
	}

	/**
	 * 获取大编码（RMK）
	 */
	public static TempPNR getBPNR(TempPNR tempPnr, String[] lineInfo) {
		try{
		if(lineInfo!=null){
			for (int i = 0; i < lineInfo.length; i++) {
				String info = lineInfo[i];
				if (info != null && "".equals(info) == false && info.length() > 4) {
					if (info.indexOf("RMK") > 0) {
						info = info.trim();
						info = info.replaceAll("[^A-Z0-9\\.\\*\\-\\/]|[\\s]", " ");
						int index = info.indexOf("/");
						if (index > 0) {
							String tempBigPNR = info.substring(index + 1, info
									.length());
							if (tempBigPNR.length() == 5
									|| tempBigPNR.length() == 6) {
								// System.out.println(tempBigPNR);
								tempPnr.setB_pnr(tempBigPNR);
							}
						}
					}
				}
			}
		}}catch (Exception e) {
			e.printStackTrace();
		}
		return tempPnr;
	}

	/**
	 * 格式：RMK CA/B9WK6
	 */
	public static TempPNR getBPNR1(TempPNR tempPnr, String sampleInfo) {
		String bpnr = "";

		sampleInfo = sampleInfo.replaceAll("[^A-Z0-9\\/\\*]|[\\s]", " ");
		// System.out.println("去除特殊字符后："+sampleInfo);

		Pattern p = Pattern.compile(regEx_BPNR1);
		Matcher m = p.matcher(sampleInfo);

//		System.out.println("---------大编码-----");
		int j = 0;
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.find()) {
				bpnr = m.group();
				System.out.println(bpnr);
			}
		}

		if (bpnr != null && "".equals(bpnr) == false) {
			bpnr = bpnr.trim();
			int flag = bpnr.indexOf("/");
			if (flag > 0) {
				bpnr = bpnr.substring(flag + 1, bpnr.length());
				// System.out.println("BPNR:" + bpnr);
				tempPnr.setB_pnr(bpnr);
			}
		}

		return tempPnr;
	}

	/**
	 * 格式：-CA-ABCDE或-CA-ABCDEF
	 */
	public static TempPNR getBPNR2(TempPNR tempPnr, String sampleInfo) {
		String bpnr = "";

		sampleInfo = sampleInfo.replaceAll("[^A-Z0-9\\.\\*\\-]|[\\s]", " ");
		// System.out.println("去除特殊字符后："+sampleInfo);

		Pattern p = Pattern.compile(regEx_BPNR2);
		Matcher m = p.matcher(sampleInfo);

//		System.out.println("---------特殊大编码-----");
		int j = 0;
		for (int i = 0; i < m.groupCount(); i++) {
			if (m.find()) {
				bpnr = m.group();
//				System.out.println(bpnr);
			}
		}

		if (bpnr != null && "".equals(bpnr) == false) {
			bpnr = bpnr.trim();
			bpnr = bpnr.substring(4, bpnr.length());
//			System.out.println("BPNR:" + bpnr);
			tempPnr.setB_pnr(bpnr);
		}

		return tempPnr;
	}
	
	
	/**
	 * 
	 **/

	/**
	 * 转成单行的原始数据
	 * 
	 * @param int
	 *            type:1,sampleTxt字符串 2,sampleTxt路径
	 */
	public static String getSampleInfo(int type, String sampleTxt) {
		String sampleInfo = "";
//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");
		try {
			BufferedReader br = StringUtil.getBufferReaderByInput(type,
					sampleTxt);

			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				sampleInfo = sampleInfo + line;
			}
			br.close();

//			myLog.info("--黑屏原始信息------" + sampleInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sampleInfo;
	}

	/**
	 * @param int
	 *            type:1,sampleTxt字符串 2,sampleTxt路径
	 */
	public static String[] getLineInfo(int type, String sampleTxt) {
//		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
//				"");

		int i = 0;
		String lineInfo[] = new String[350];

		try {
			BufferedReader br = StringUtil.getBufferReaderByInput(type,
					sampleTxt);

			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				if (line.indexOf("rt") == 0) {
					continue;
				}
				// myLog.info(i + "--黑屏原始信息------" + line);
				lineInfo[i] = line;
				i++;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineInfo;
	}

	public static void replaceBlank() {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		String str = "I am a, I am Hello ok, \n new line ffdsa!";
		System.out.println("before:" + str);
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		System.out.println("after:" + after);
	}

	/**
	 * 获取票号(散票)（旧版）
	 */
	public static TempPNR _getTicketNo(TempPNR tempPnr, String[] lineInfo) {
		List<TempPassenger> passengers = tempPnr.getTempPassengerList();
		List tempTicketList = tempPnr.getTempTicketsList();
		if (tempTicketList == null) {
			tempTicketList = new ArrayList();
		}

		int passengerSize = passengers.size();
		int ticketIndex = 0;
		if (passengerSize > 0) {
			for (int i = 0; i < lineInfo.length; i++) {
				String info = lineInfo[i];
				if (info != null && "".equals(info) == false) {
					int flag = info.indexOf("TN/");
					int flag2 = info.indexOf(".T/");
					int flag3 = info.indexOf("TKNO/");
					String ticketNo = "";
					if (flag > 0) {
						// System.out.println(flag+"--"+info);
						String flagMore = info.substring(flag + 3 + 14,
								flag + 3 + 15);
						// System.out.println("flagMore:"+flagMore);
						if ("-".equals(flagMore)) {// 一行多张票号相连，如20100821-25
							String tickeBase = info.substring(flag + 3,
									flag + 3 + 13);
							String beginNoStr = info.substring(flag + 3 + 12,
									flag + 3 + 12 + 2);
							// System.out.println(beginNoStr);
							String endNoStr = info.substring(flag + 3 + 12 + 3,
									flag + 3 + 12 + 3 + 2);
							// System.out.println(endNoStr);
							int[] tempNo = StringUtil.getUnitNoByBeginString(
									beginNoStr, endNoStr, 1);

							for (int j = 0; j < tempNo.length; j++) {
								ticketNo = tickeBase + tempNo[j];
								if (ticketIndex <= (passengerSize - 1)) {
									tempTicketList.add(ticketNo);
									ticketIndex += 1;
								}
							}
						} else {// 分别
							info = info.trim();
							info = info.substring(flag + 3, info.length());
							ticketNo = info.substring(0, info.length());

							if (ticketNo.trim().length() < 13) {
								continue;
							} else {
								if (ticketIndex <= (passengerSize - 1)) {
									tempTicketList.add(ticketNo);
									ticketIndex += 1;
								}
							}
						}
					} else if (flag2 > 0) {
						String flagMore = info.substring(19, 20);
						if ("-".equals(flagMore)) {// 一行多张票号相连，如20100821-25
							String tickeBase = info.substring(5, 17);
							String beginNoStr = info.substring(17, 19);
							String endNoStr = info.substring(20, 22);
							int[] tempNo = StringUtil.getUnitNoByBeginString(
									beginNoStr, endNoStr, 1);

							for (int j = 0; j < tempNo.length; j++) {
								ticketNo = tickeBase + tempNo[j];

								if (ticketNo.trim().length() < 13) {
									continue;
								} else {
									if (ticketIndex <= (passengerSize - 1)) {
										tempTicketList.add(ticketNo);
										ticketIndex += 1;
									}
								}
							}
						} else {// 分别
							info = info.trim();
							info = info.substring(flag2 + 2, info.length());
							ticketNo = info.substring(0, info.length());

							if (ticketNo.length() < 13) {
								continue;
							} else {
								if (ticketIndex <= (passengerSize - 1)) {
									tempTicketList.add(ticketNo);
									ticketIndex += 1;
								}
							}
						}
					} else if (flag3 > 0) {
						info = info.trim();
						info = info.substring(flag3 + 4, info.length());
						ticketNo = info.substring(0, info.length());

						if (ticketNo.trim().length() < 13) {
							continue;
						} else {
							if (ticketIndex <= (passengerSize - 1)) {
								tempTicketList.add(ticketNo);
								ticketIndex += 1;
							}
						}
					}
				}
			}
		}
		tempPnr.setTempTicketsList(tempTicketList);

		return tempPnr;
	}
}
