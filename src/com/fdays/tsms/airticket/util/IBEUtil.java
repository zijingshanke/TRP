package com.fdays.tsms.airticket.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.base.util.UnitConverter;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * IBE接口工具类
 * 
 * PNR导入订单
 * 
 * 查询航班基准票面价、机场、燃油税
 * 
 * @author yanrui
 */
public class IBEUtil {

	public static void main(String[] args) {
		int flightType = 1;
		String flightCode = "JD5182";//CZ8726
		String start = "LJG";
		String end = "PEK";
		String startDate = "2010-11-17";
		String carrier = "JD";

		getTicketPriceByIBEInterface(flightType, flightCode, start, end,
				startDate, carrier);
	}

	/**
	 * 更新TempPNR的票面价、燃油、机建
	 */
	public static TempPNR setTicketPriceByIBEInterface(TempPNR tempPnr)
			throws AppException {
		LogUtil myLog = new AirticketLogUtil(true, true, IBEUtil.class, "");

		try {

			List<TempFlight> tempFlightList = tempPnr.getTempFlightList();

			if (tempFlightList != null) {
				boolean flag = false;
				for (int i = 0; i < tempFlightList.size(); i++) {
					if (flag == false) {

						TempFlight tempFlight = tempFlightList.get(i);
						String fligthCode = tempFlight.getFlightNo();
						String start = tempFlight.getDepartureCity();
						String end = tempFlight.getDestineationCity();

						System.out.println("date:" + tempFlight.getDate());
						Date tempDate = new Date(tempFlight.getDate().getTime());
						String startDate = DateUtil.getDateString(tempDate,
								"yyyy-MM-dd");

						if (fligthCode != null
								&& "".equals(fligthCode) == false
								&& start != null && "".equals(start) == false
								&& end != null && "".equals(end) == false
								&& startDate != null
								&& "".equals(startDate) == false) {

							myLog.info("flightCode:" + fligthCode + "--length:"
									+ fligthCode.length());

							if (fligthCode.length() > 2) {
								String carrier = fligthCode.substring(0, 2);
								HashMap<String, String> ticketPrice = getTicketPriceByIBEInterface(
										1, fligthCode, start, end, startDate,
										carrier);

								tempPnr.setFare(UnitConverter
										.getBigDecimalByString(ticketPrice
												.get("fare")));
								tempPnr.setTax(UnitConverter
										.getBigDecimalByString(ticketPrice
												.get("tax")));
								tempPnr.setYq(UnitConverter
										.getBigDecimalByString(ticketPrice
												.get("yq")));
								flag = true;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			myLog.error(e.getMessage());
		}
		return tempPnr;
	}

	/**
	 * 查询航班燃油、机建、票面价
	 * 
	 * @param String
	 *            flightType 1:单程 2：联程、缺口程 3：往返程
	 * @param String
	 *            start
	 * @param String
	 *            end
	 * @param String
	 *            startDate
	 * @param String
	 *            carrier
	 * @param TempPNR
	 *            tempPNR
	 * @return HashMap<String, String>
	 * 
	 * @IBE接口错误码说明 1:起飞降落城市错误！ 2:航班查询时间错误！ 3:无此航段信息或无此航班！ 4:网络忙碌请重新查询！
	 */
	public static HashMap<String, String> getTicketPriceByIBEInterface(
			int flightType, String flightCode, String start, String end,
			String startDate, String carrier) {
		LogUtil myLog = new AirticketLogUtil(true, true, IBEUtil.class, "");

		HashMap<String, String> ticketPrice = new HashMap<String, String>();
		ticketPrice.put("tax", "0.00");
		ticketPrice.put("yq", "0.00");
		ticketPrice.put("fare", "0.00");

		String interfacePath = "http://ibe.fdays.com/SendIbe.asmx/";
		String url = interfacePath;
		String xmlInfo = "";

		if (flightType == 1) {
			url += "SendDC?";
		} else if (flightType == 2) {
			url += "SendLC?";
		} else if (flightType == 3) {
			url += "SendWF?";
		}

		url += "departure=" + start + "&destination=" + end + "&depart="
				+ startDate + "&carrier=" + carrier;

		myLog.info("ibe url:" + url);

		try {
			xmlInfo = HttpInvoker.readContentFromGet(url);
			myLog.info("ibe xmlInfo:" + "\n" + xmlInfo);

			Document document = DocumentHelper.parseText(xmlInfo);

			List<Element> elementList = document.selectNodes("/lists");
			for (Element e : elementList) {
				List<Element> flightList = e.selectNodes("FlightNO");
				for (Element flight : flightList) {
					Element tempflightCode = flight.element("flight");
					if (tempflightCode.getTextTrim().toUpperCase().equals(flightCode.toUpperCase())) {
						String fcn = flight.element("fcn").getTextTrim();// 机建
						String fyq = flight.element("fyq").getTextTrim();// 燃油
						String fcny = flight.element("fcny").getTextTrim();// Y舱基准价（不同舱位以此打折形成票面价）

						ticketPrice.put("tax", fcn);
						ticketPrice.put("yq", fyq);
						ticketPrice.put("fare", fcny);

						myLog.info("航班:" + flightCode + "--机建:" + fcn + "--燃油:"
								+ fyq + "--基准价：" + fcny);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticketPrice;
	}
}
