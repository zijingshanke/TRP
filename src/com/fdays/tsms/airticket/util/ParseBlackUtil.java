package com.fdays.tsms.airticket.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.base.util.LogUtil;

import com.neza.tool.DateUtil;

/**
 * 解析黑屏PNR信息
 * 
 * @author YanRui
 */
public class ParseBlackUtil {

	public static void main(String[] args) {
		TempPNR tempPnr = getTempPNRByBlack("");
		printTempPNRInfo(tempPnr);
	}

	public static TempPNR getTempPNRByBlack(String sampleTxt) {
		// sampleTxt = "F:\\tsms\\doc\\PNRSample\\BlackSample6.txt";

		String lineInfo[] = getLineInfo(1, sampleTxt);

		TempPNR tempPnr = new TempPNR();

		tempPnr = getPassenger(tempPnr, lineInfo);

		tempPnr = getFlight(tempPnr, lineInfo);

		tempPnr = setTicketNo(tempPnr, lineInfo);

		return tempPnr;
	}

	public static void printTempPNRInfo(TempPNR tempPnr) {
		LogUtil myLog = new AirticketLogUtil(true, false, ParseBlackUtil.class,
				"");

		// -------getPassenger() result
		// List<TempPassenger> passList = tempPnr.getTempPassengerList();
		// for (int i = 0; i < passList.size(); i++) {
		// myLog.info(i + "-乘机人：" + passList.get(i).getName());
		// }
		// -------end----------------

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
	}

	/**
	 * 获取航班信息
	 */
	public static TempPNR getFlight(TempPNR tempPnr, String[] lineInfo) {
		List<TempFlight> flights = new ArrayList<TempFlight>();

		TempFlight flight = getTempFlightByLineInfo(lineInfo[1]);
		flights.add(flight);

		if (lineInfo[2].split(" ")[3].length() == 6) {
			flight = getTempFlightByLineInfo(lineInfo[2]);
			flights.add(flight);
		}
		tempPnr.setTempFlightList(flights);// ------------

		return tempPnr;
	}

	/**
	 * 获取票号
	 */
	public static TempPNR setTicketNo(TempPNR tempPnr, String[] lineInfo) {
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
						ticketNo = info.substring(6, 20);

						if (ticketIndex <= (passengerSize - 1)) {
							tempTicketList.add(ticketNo);
							ticketIndex += 1;
						}
					} else if (flag2 > 0) {
						ticketNo = info.substring(5, 19);
						if (ticketIndex <= (passengerSize - 1)) {
							tempTicketList.add(ticketNo);
							ticketIndex += 1;
						}
					}

				}
			}
		}
		tempPnr.setTempTicketsList(tempTicketList);

		return tempPnr;
	}

	/**
	 * 获取单条航班信息
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
					flight.setAirline(info);
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
					flight.setDestineationCity(info.substring(0, 3));
					flight.setDepartureCity(info.substring(3, 6));
				}
			}
		}
		flight.setTempDate(tempBoradingDate, tempBoradingTime);
		return flight;
	}

	/**
	 * 获取乘机人姓名
	 */
	public static TempPNR getPassenger(TempPNR tempPnr, String[] LineInfo) {
		String content = LineInfo[0];
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
					tempPnr.setPnr(info);// ------------
					// System.out.println("预定PNR:" + info);
				}
			}
		}
		tempPnr.setTempPassengerList(passengers);// ------------

		return tempPnr;
	}

	/**
	 * int type:1,sampleTxt字符串 2,sampleTxt路径
	 */
	public static String[] getLineInfo(int type, String sampleTxt) {
		int i = 0;
		String lineInfo[] = new String[30];
		BufferedReader br = null;

		try {
			if (type == 1) {				
				ByteArrayInputStream byteIn = new ByteArrayInputStream(
						sampleTxt.getBytes());
				br = new BufferedReader(new InputStreamReader(byteIn));
			} else if (type == 2) {
				br = new BufferedReader(new InputStreamReader(
						new FileInputStream(sampleTxt)));
			}

			for (String line = br.readLine(); line != null; line = br
					.readLine()) {
				// System.out.println(i + "--------" + line);
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
}
