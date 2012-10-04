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

/**
 * GDS接口工具类
 * 
 * @author sc
 */
public class GDSUtil {

	public static TempPNR getTempPNRByGDSInterface(String pnr, TempPNR tempPNR)
			throws AppException {

		LogUtil myLog = new AirticketLogUtil(true, true, GDSUtil.class, "");
		String pnrXMLInfo = "";

		try {
			String url = "http://pid.fdays.net:352/ib_tranx_req.asp?uid=Websd&sessionid=0&verify=0&termid=sztest&cmd=rt_parse&pnr="
					+ pnr + "&get_price=1&hk=0&get_ticket=1";
			
			myLog.info("url----" + url);
			
			pnrXMLInfo = HttpInvoker.readContentFromGet(url);
			myLog.info(pnrXMLInfo);
			if (pnrXMLInfo
					.contains("<?xml version=\"1.0\" encoding=\"GB2312\"?>")) {
				// ByteArrayInputStream sbInput=new
				// ByteArrayInputStream(pnrXMLInfo.toString().getBytes("GB2312"));
				// SAXReader reader = new SAXReader();
				// Document document = reader.read(sbInput);
				Document document = DocumentHelper.parseText(pnrXMLInfo);
				List<Element> elementList = document.selectNodes("/rt_parse");
				for (Element e : elementList) {
					String ret_value = e.attributeValue("ret_value");// 父节点的名称
					myLog.info(ret_value + "-------------------------"
							+ e.elementText("pnr") + e.elementText("b_pnr")
							+ e.elementText("is_team")
							+ e.elementText("HasINF") + e.elementText("IsCHD")
							+ e.elementText("remark"));

					if (ret_value != null && !ret_value.equals("0")) {

						tempPNR.setRt_parse_ret_value(Long.valueOf(ret_value));
						tempPNR.setPnr(e.elementText("pnr"));
						tempPNR.setB_pnr(e.elementText("b_pnr"));
						tempPNR.setIs_team(e.elementText("is_team"));
						tempPNR.setIsCHD(e.elementText("IsCHD"));
						tempPNR.setRemark(e.elementText("remark"));

						// 循环子节点获取乘客数据
						List<Element> elementListPassengers = e
								.selectNodes("/rt_parse/passengers");
						for (Element ePassengers : elementListPassengers) {
							String passengers_count = ePassengers
									.attributeValue("count");
							System.out.println("---" + passengers_count);
							if (passengers_count != null
									&& !passengers_count.equals("0")) {
								List<TempPassenger> tempPassengerList = new ArrayList<TempPassenger>();
								List<Element> elementListPassenger = ePassengers
										.elements("passenger");
								for (Element ePassenger : elementListPassenger) {
									TempPassenger tempPassenger = new TempPassenger();
									myLog.info(ePassenger.elementText("Name"));
									myLog.info(ePassenger.elementText("NI"));
									tempPassenger.setName(ePassenger
											.elementText("Name"));
									tempPassenger.setNi(ePassenger
											.elementText("NI"));
									tempPassengerList.add(tempPassenger);
								}
								tempPNR.setTempPassengerList(tempPassengerList);
							}
						}

						// 循环子节点获取航班数据
						List<Element> elementListFlight = e
								.selectNodes("/rt_parse/lines");
						for (Element eFlight : elementListFlight) {
							String lines_count = eFlight
									.attributeValue("count");
							myLog.info(lines_count);
							if (lines_count != null && !lines_count.equals("0")) {
								tempPNR.setLines_count(Long
										.valueOf(lines_count));
								List<Element> elineList = eFlight
										.elements("Line");
								List<TempFlight> tempFlightList = new ArrayList<TempFlight>();
								for (Element eLine : elineList) {
									TempFlight TempFlight = new TempFlight();
									System.out.println(eLine
											.elementText("AirLine"));
									TempFlight.setFlightNo(eLine
											.elementText("AirLine"));
									TempFlight.setCabin(eLine
											.elementText("Cabin"));
									TempFlight.setDiscount(eLine
											.elementText("Discount"));
									TempFlight.setDepartureCity(eLine
											.elementText("DepartureCity"));
									TempFlight.setDestineationCity(eLine
											.elementText("DestinationCity"));
									TempFlight.setDepartureAirPort(eLine
											.elementText("DepartureAirPort"));
									TempFlight.setDestinationAirPort(eLine
											.elementText("DestinationAirPort"));
									if (eLine.elementText("Date") != null
											&& eLine.elementText("StartTime") != null) {
										TempFlight.setTempDate(eLine
												.elementText("Date"), eLine
												.elementText("StartTime"));
									}
									TempFlight.setTempArrivaltime(eLine
											.elementText("ArriveTime"));
									TempFlight.setState(eLine
											.elementText("State"));
									tempFlightList.add(TempFlight);
								}
								tempPNR.setTempFlightList(tempFlightList);
							}
						}
						// 票号
						List<Element> elementListTickets = e
								.selectNodes("/rt_parse/tickets");
						for (Element eTickets : elementListTickets) {
							String tickets_count = eTickets
									.attributeValue("count");
							if (tickets_count != null
									&& !tickets_count.equals("0")) {
								tempPNR.setTickets_count(Long
										.valueOf(tickets_count));
								List<Element> eticketList = eTickets
										.elements("ticket");
								List tempTicketsList = new ArrayList();
								// tempTicketsList.add(eTickets.elementText("ticket"));
								for (Element eTicket : eticketList) {
									tempTicketsList.add(eTicket.getText());
									System.out.println("ticket====="
											+ eTicket.getText());
								}
								tempPNR.setTempTicketsList(tempTicketsList);
							}
						}
						// 获取航班价格
						List<Element> elementListPrices = document
								.selectNodes("/rt_parse/prices");
						for (Element ePrices : elementListPrices) {
							String price_Count = ePrices
									.attributeValue("count");// 父节点的名称
							if (price_Count != null && !price_Count.equals("0")) {
								tempPNR.setPrice_Count(Long
										.valueOf(price_Count));

								List<Element> epriceList = ePrices
										.elements("price");
								java.math.BigDecimal fare1 = new BigDecimal(0);
								java.math.BigDecimal tax1 = new BigDecimal(0);
								java.math.BigDecimal yq1 = new BigDecimal(0);
								for (Element ePrice : epriceList) {
									String fare = ePrice.elementText("Fare");
									String tax = ePrice.elementText("Tax");
									String yq = ePrice.elementText("YQ");
									if (fare != null) {

										fare1 = (new BigDecimal(fare));
									}
									if (tax != null) {
										tax1 = (new BigDecimal(tax));
									}
									if (yq != null) {
										yq1 = (new BigDecimal(yq));
									}
									myLog.info("fare---"
											+ ePrice.elementText("Fare")
											+ ePrice.elementText("Tax")
											+ ePrice.elementText("YQ"));

								}
								if (tempPNR.getPassengers_count() > 0) {

									/*
									 * tempPNR.setFare(fare1.multiply(new
									 * BigDecimal(tempPNR.getPassengers_count())));//票面价
									 * tempPNR.setTax(tax1.multiply(new
									 * BigDecimal(tempPNR.getPassengers_count())));///机建费
									 * tempPNR.setYq(yq1.multiply(new
									 * BigDecimal(tempPNR.getPassengers_count())));
									 * //燃油税
									 */

									tempPNR.setFare(fare1);// 票面价
									tempPNR.setTax(tax1);// /机建费
									tempPNR.setYq(yq1); // 燃油税

								}
								myLog.info("fare1===" + tempPNR.getFare()
										+ "tax1===" + tempPNR.getTax()
										+ "yq1====" + tempPNR.getYq());
							}
						}
					} else {
						tempPNR.setRt_parse_ret_value(Long.valueOf(0));
						myLog.info("PNR无效！！！");
					}
				}
			} else {
				tempPNR.setRt_parse_ret_value(Long.valueOf(0));
				myLog.info("PNR无效！！！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempPNR;
	}

}
