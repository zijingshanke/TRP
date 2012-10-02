package com.fdays.tsms.airticket.util;

import java.io.BufferedReader;
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
 * 解析黑屏PNR信息
 * 
 * @author YanRui
 */
public class ParseBlackUtil {
	private static String regEx_passenger = "(\\d\\.[\\s+\\D])+ \\s*?[A-Z0-9]{5}";
	private static String regEx_flight = "((\\d\\.[\\s+\\D]){1}+ \\s*+[A-Z0-9]{2}[0-9]{3,4}+ \\s*[A-Z]{1}+ \\s+[A-Z0-9]{6,7}+\\s+[A-Z]{6}+\\s*+[A-Z0-9]{3}+\\s*+[0-9]{4}+\\s*+[0-9]{4})";

	private static String regEx_ticketno = "";
	private static String regEx_BPNR = "";

	public static void main(String[] args) {
		// TempPNR tempPnr = getTempPNRByBlack("");
		// printTempPNRInfo(tempPnr);

		// replaceBlank();
		getTempPNRByBlack("");

	}

	public static TempPNR getTempPNRByBlack(String sampleTxt) {
//		sampleTxt = "E:\\tsms\\doc\\PNRSample\\BlackSample9.txt";

		String lineInfo[] = getLineInfo(1, sampleTxt);
		String sampleInfo = getSampleInfo(1, sampleTxt);

		TempPNR tempPnr = new TempPNR();

		// tempPnr = getPassenger(tempPnr, lineInfo[0]);
		String passengerLine = getPassengerLine_regEx(sampleInfo);
		tempPnr = getPassenger(tempPnr, passengerLine);

		// tempPnr = getFlight_lineInfo(tempPnr, lineInfo);
		String[] flightLine = getFlightLine_regEx(sampleInfo);
		tempPnr = getFlight_regEx(tempPnr, flightLine);

		tempPnr = getTicketNo(tempPnr, lineInfo);

		tempPnr = getBPNR(tempPnr, lineInfo);

		printTempPNRInfo(tempPnr);

		return tempPnr;
	}

	public static void printTempPNRInfo(TempPNR tempPnr) {
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");
		List<TempFlight> flightList = tempPnr.getTempFlightList();

		for (int j = 0; j < flightList.size(); j++) {
			TempFlight flight = flightList.get(j);
			myLog.info((j + 1) + "-航班：" + flight.getAirline() + "-舱位："
					+ flight.getCabin() + "出发：" + flight.getDestineationCity()
					+ "-到达：" + flight.getDepartureCity() + "-出发日期："
					+ flight.getDate());
		}

		List ticketList = tempPnr.getTempTicketsList();
		for (int k = 0; k < ticketList.size(); k++) {
			String ticketNo = (String) ticketList.get(k);
			myLog.info((k + 1) + "-票号：" + ticketNo);
		}

		myLog.info("大编码：" + tempPnr.getB_pnr());
	}

	/**
	 * 匹配航程 行
	 */
	public static String[] getFlightLine_regEx(String sampleInfo) {
		String[] flightLine = new String[5];
		LogUtil myLog = new AirticketLogUtil(false, false,
				ParseBlackUtil.class, "");
		Pattern p = Pattern.compile(regEx_flight);
		Matcher m = p.matcher(sampleInfo);

		myLog.info("----------原始航程信息-----");
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
	 * 匹配乘机人、编码行
	 */
	public static String getPassengerLine_regEx(String sampleInfo) {
		String passengerLine = "";
		LogUtil myLog = new AirticketLogUtil(false, false,
				ParseBlackUtil.class, "");

		Pattern p = Pattern.compile(regEx_passenger);

		String[] rsArray = p.split(sampleInfo);
		int rsArrayLen = rsArray.length;

		if (rsArrayLen > 0) {
			passengerLine = rsArray[0];
		}
		myLog.info("passengerLine:" + passengerLine);
		return passengerLine;
	}

	/**
	 * 获取航班信息
	 */
	public static TempPNR getFlight_regEx(TempPNR tempPnr, String[] lineInfo) {
		List<TempFlight> flights = new ArrayList<TempFlight>();

		for (int j = 0; j < lineInfo.length; j++) {
			String flightString = lineInfo[j];
			if (flightString != null && "".equals(flightString) == false) {
				// System.out.println("flightString:" + flightString);
				TempFlight flight = getTempFlightByLineInfo_regEx(flightString);
				flights.add(flight);
			}
		}
		tempPnr.setTempFlightList(flights);
		return tempPnr;
	}

	/**
	 * 获取票号
	 */
	public static TempPNR getTicketNo(TempPNR tempPnr, String[] lineInfo) {
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
					int flag = info.indexOf(".TN/");
					int flag2 = info.indexOf(".T/");
					String ticketNo = "";
					if (flag > 0) {		
						String flagMore = info.substring(20, 21);
						if ("-".equals(flagMore)) {// 一行多张票号相连，如20100821-25
							String tickeBase = info.substring(6, 18);
							String beginNoStr = info.substring(18, 20);
							String endNoStr = info.substring(21, 23);
							int[] tempNo = StringUtil.getUnitNoByBeginString(
									beginNoStr, endNoStr, 1);
							
							for (int j = 0; j < tempNo.length; j++) {
								ticketNo=tickeBase+tempNo[j];
								if (ticketIndex <= (passengerSize - 1)) {
									tempTicketList.add(ticketNo);
									ticketIndex += 1;
								}								
							}
						} else {//分别
							ticketNo = info.substring(6, 20);

							if (ticketIndex <= (passengerSize - 1)) {
								tempTicketList.add(ticketNo);
								ticketIndex += 1;
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
								ticketNo=tickeBase+tempNo[j];
								if (ticketIndex <= (passengerSize - 1)) {
									tempTicketList.add(ticketNo);
									ticketIndex += 1;
								}								
							}
						} else {//分别
							ticketNo = info.substring(5, 19);
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

	/**
	 * 获取单条航班信息（正则取String）
	 */
	public static TempFlight getTempFlightByLineInfo_regEx(String content) {
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
					flight.setAirline(info);// 航班号
				}
				if (i == 2) {
					flight.setCabin(info);
				}
				if (i == 3) {
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
	 * 获取乘机人姓名
	 */
	public static TempPNR getPassenger(TempPNR tempPnr, String content) {
		List<TempPassenger> passengers = new ArrayList<TempPassenger>();
		TempPassenger passenger;

		// System.out.println("content:" + content);

		String result[] = content.split(" ");
		int count = result.length;

		for (int i = 0; i < count; i++) {
			String info = result[i];

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
		tempPnr.setTempPassengerList(passengers);

		return tempPnr;
	}

	/**
	 * 获取大编码
	 */
	public static TempPNR getBPNR(TempPNR tempPnr, String[] lineInfo) {
		for (int i = 0; i < lineInfo.length; i++) {
			String info = lineInfo[i];
			if (info != null && "".equals(info) == false && info.length() > 4) {
				if (info.indexOf("RMK") > 0) {
					info = info.trim();
					if (info.length() == 14 || info.length() == 15) {
						int index = info.indexOf("/");
						info = info.substring(index + 1, index + 6);
						tempPnr.setB_pnr(info);
					}
				}
			}
		}
		return tempPnr;
	}

	/**
	 * 转成单行的原始数据
	 * 
	 * @param int
	 *            type:1,sampleTxt字符串 2,sampleTxt路径
	 */
	public static String getSampleInfo(int type, String sampleTxt) {
		String sampleInfo = "";
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");
		try {
			BufferedReader br = StringUtil.getBufferReaderByInput(type,
					sampleTxt);

			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				sampleInfo = sampleInfo + line;
			}
			br.close();

			myLog.info("--黑屏原始信息------" + sampleInfo);
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
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");

		int i = 0;
		String lineInfo[] = new String[50];

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

	/**
	 * 获取航班信息(旧版)
	 */
	public static TempPNR getFlight_lineInfo(TempPNR tempPnr, String[] lineInfo) {
		List<TempFlight> flights = new ArrayList<TempFlight>();

		for (int j = 0; j < lineInfo.length; j++) {
			String flightString = lineInfo[j];
			if (flightString != null && "".equals(flightString) == false) {
				System.out.println("flightString:" + flightString);
				TempFlight flight = getTempFlightByLineInfo(flightString);
				flights.add(flight);
			}
		}
		tempPnr.setTempFlightList(flights);
		return tempPnr;
	}

	/**
	 * 获取单条航班信息(旧版)
	 */
	public static TempFlight getTempFlightByLineInfo(String content) {
		TempFlight flight = new TempFlight();

		// System.out.println("content:" + content);

		String result[] = content.split(" ");
		int count = result.length;

		String tempBoradingDate = "";
		String tempBoradingTime = "";

		for (int i = 0; i < count; i++) {
			String info = result[i];

			if (info.length() > 0) {
				// System.out.println(i + "--" + info);

				if (i == 3) {
					flight.setAirline(info);// 航班号
				}
				if (i == 4) {
					flight.setCabin(info);
				}
				if (i == 7) {
					tempBoradingDate = info.substring(2, info.length());
				}
				if (i == 13) {
					tempBoradingTime = info.substring(0, 2) + ":"
							+ info.substring(2, info.length());
				}
				if (i == 9) {
					flight.setDepartureCity(info.substring(0, 3));
					flight.setDestineationCity(info.substring(3, 6));
				}
			}
		}
		flight.setTempDate(tempBoradingDate, tempBoradingTime);
		return flight;
	}

	public static void replaceBlank() {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		String str = "I am a, I am Hello ok, \n new line ffdsa!";
		System.out.println("before:" + str);
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		System.out.println("after:" + after);
	}
}
