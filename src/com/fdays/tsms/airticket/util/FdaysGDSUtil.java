package com.fdays.tsms.airticket.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

/**
 * 旧财务系统订单提取接口
 * 
 * @author yanrui
*/
public class FdaysGDSUtil {

	private static String testPath = "http://192.168.1.10:9099/QueryFlight/QueryFlightInfo.aspx?pnr=";

	public static void main(String[] args) {
		String pnr = "Q0L5P";

		try {
			getTempPNRByFdaysGDSInterface(pnr);
		} catch (AppException e) {
			e.printStackTrace();
		}
	}

	public static TempPNR getTempPNRByFdaysGDSInterface(String pnr)
			throws AppException {
		TempPNR tempPNR = new TempPNR();
		LogUtil myLog = new AirticketLogUtil(true, true, GDSUtil.class, "");
		String pnrXMLInfo = "";
		String url = testPath + pnr;
		myLog.info("url----" + url);

		try {
			pnrXMLInfo = HttpInvoker.readContentFromGet(url);
			Document document = DocumentHelper.parseText(pnrXMLInfo);

			List<Element> elementList = document.selectNodes("/rt_pars");
			for (Element e : elementList) {
				String ret_value = e.attributeValue("ret_value");// 父节点的名称
				if (ret_value != null && !ret_value.equals("0")) {
					tempPNR.setRt_parse_ret_value(Long.valueOf(ret_value));
					tempPNR.setPnr(e.elementText("pnr"));
					tempPNR.setDraw_pnr(e.elementText("draw_pnr"));
					tempPNR.setB_pnr(e.elementText("big_pnr"));
					
					// 循环子节点获取乘客数据
					List<Element> elementListPassengers = e.selectNodes("/rt_pars/passengers");
					for (Element ePassengers : elementListPassengers) {
						String passengers_count = ePassengers.attributeValue("count");
						if (passengers_count != null&& !passengers_count.equals("0")) {
							tempPNR.setPassengers_count(Long.valueOf(passengers_count));
							tempPNR.setTickets_count(Long.valueOf(passengers_count));
							
							List<TempPassenger> tempPassengerList = new ArrayList<TempPassenger>();
							List tempTicketsList = new ArrayList();
							
							List<Element> elementListPassenger = ePassengers.elements("passenger");
							for (Element ePassenger : elementListPassenger) {
								TempPassenger tempPassenger = new TempPassenger();
								
								tempPassenger.setName(ePassenger.elementText("Name"));
								tempPassengerList.add(tempPassenger);
								tempTicketsList.add(ePassenger.elementText("ticket"));								
							}
							tempPNR.setTempPassengerList(tempPassengerList);
							tempPNR.setTempTicketsList(tempTicketsList);
						}
					}

					// 循环子节点获取航班数据
					List<Element> elementListFlight = e.selectNodes("/rt_pars/lines");
					for (Element eFlight : elementListFlight) {
						String lines_count = eFlight.attributeValue("count");
						myLog.info(lines_count);
						if (lines_count != null && !lines_count.equals("0")) {
							tempPNR.setLines_count(Long.valueOf(lines_count));
							List<Element> elineList = eFlight.elements("Line");
							
							List<TempFlight> tempFlightList = new ArrayList<TempFlight>();
							for (Element eLine : elineList) {
								TempFlight tempFlight = new TempFlight();
								tempFlight.setAirline(eLine.elementText("AirLine"));
								tempFlight.setCabin(eLine.elementText("Cabin"));
								tempFlight.setDiscount(eLine.elementText("Discount"));
								tempFlight.setDepartureCity(eLine.elementText("DepartureCity"));
								tempFlight.setDestineationCity(eLine.elementText("DestinationCity"));
								
								if (eLine.elementText("Date") != null) {
									String dateStr=eLine.elementText("Date");
									tempFlight.setStarttime(DateUtil.getTimestamp(dateStr,"yyyy-MM-dd HH:mm:ss"));									
								}
								tempFlightList.add(tempFlight);
							}
							tempPNR.setTempFlightList(tempFlightList);
						}
					}
					
					
					// 获取航班价格
					List<Element> elementListPrices = document.selectNodes("/rt_pars/prices");
					for (Element ePrices : elementListPrices) {
						String price_Count = ePrices.attributeValue("count");//父节点的名称
						if (price_Count != null && !price_Count.equals("0")) {
							tempPNR.setPrice_Count(Long.valueOf(price_Count));

							List<Element> epriceList = ePrices.elements("price");
							java.math.BigDecimal fare = new BigDecimal(0);
							java.math.BigDecimal tax = new BigDecimal(0);
							java.math.BigDecimal yq = new BigDecimal(0);
							for (Element ePrice : epriceList) {
								String fareStr = ePrice.elementText("Fare");
								String taxStr = ePrice.elementText("Tax");
								String yqStr = ePrice.elementText("YQ");
								if (fareStr != null) {
									fare =new BigDecimal(fareStr);
								}
								if (taxStr != null) {
									tax = (new BigDecimal(taxStr));
								}
								if (yqStr != null) {
									yq = (new BigDecimal(yqStr));
								}								
							}
							tempPNR.setFare(fare);// 票面价
							tempPNR.setTax(tax);// /机建费
							tempPNR.setYq(yq); // 燃油税
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempPNR;
	}
}