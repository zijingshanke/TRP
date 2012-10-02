package com.fdays.tsms.airticket;

import java.util.HashMap;
import java.util.List;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.airticket.util.IBEUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.base.util.UnitConverter;
import com.neza.tool.DateUtil;

public class TempPNR {

	private Long rt_parse_ret_value;// 编码提取结果，如果为0，则表示该次请求失败
	private String pnr; // 预订记录编码
	private String b_pnr;
	private String is_team;
	private String hasNI;
	private String hasINF;
	private String isCHD;
	private String remark;
	private List<TempFlight> tempFlightList;// 航班信息
	private List<TempPassenger> tempPassengerList;// 乘客信息
	private List tempTicketsList;// 票号信息
	private Long tickets_count;// 票号数量
	private Long passengers_count; // 乘客信息count 无数据为0
	private Long lines_count; // 航班count 无数据为0
	private Long price_Count;// 价格数量
	private java.math.BigDecimal price;// 价格信息
	private java.math.BigDecimal fare;// 票面
	private java.math.BigDecimal tax;// 机建费
	private java.math.BigDecimal yq;// 燃油税

	private LogUtil myLog;

	public void setFare(java.math.BigDecimal fare) {
		if (fare == null || "".equals(fare)) {

		}

		this.fare = fare;
	}

	public void setTax(java.math.BigDecimal tax) {
//		myLog = new AirticketLogUtil(true, false, TempPNR.class, "");
//		myLog.info("11111111111");
//		if (tempFlightList != null) {
//			if (tempFlightList.size() > 0) {
//				TempFlight tempFlight = tempFlightList.get(0);
//				if (fare == null || "".equals(fare)) {
//					String fligthCode = tempFlight.getAirline();
//					String start = tempFlight.getDepartureCity();
//					String end = tempFlight.getDestineationCity();
//					String startDate = DateUtil.getDateString(tempFlight
//							.getDate().toString());
//
//					if (fligthCode != null && "".equals(fligthCode) == false
//							&& start != null && "".equals(start) == false
//							&& end != null && "".equals(end) == false
//							&& startDate != null
//							&& "".equals(startDate) == false) {
//
//						System.out.println("flightCode:" + fligthCode
//								+ "--length:" + fligthCode.length());
//						if (fligthCode.length() > 2) {
//							String carrier = fligthCode.substring(0, 2);
//
//							HashMap<String, String> ticketPrice = IBEUtil
//									.getTicketPriceByIBEInterface(1,
//											fligthCode, start, end, startDate,
//											carrier);
//
//							this.fare = UnitConverter
//									.getBigDecimalByString(ticketPrice
//											.get("fare"));
//							this.tax = UnitConverter
//									.getBigDecimalByString(ticketPrice
//											.get("tax"));
//							this.yq = UnitConverter
//									.getBigDecimalByString(ticketPrice
//											.get("yd"));
//						}
//					}
//				}
//			}
//		}
		this.tax = tax;
	}

	public void setYq(java.math.BigDecimal yq) {
		if (fare == null || "".equals(fare)) {

		}
		this.yq = yq;
	}

	public Long getRt_parse_ret_value() {
		return rt_parse_ret_value;
	}

	public void setRt_parse_ret_value(Long rt_parse_ret_value) {
		this.rt_parse_ret_value = rt_parse_ret_value;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public List<TempFlight> getTempFlightList() {
		return tempFlightList;
	}

	public void setTempFlightList(List<TempFlight> tempFlightList) {
		this.tempFlightList = tempFlightList;
	}

	public List<TempPassenger> getTempPassengerList() {
		return tempPassengerList;
	}

	public void setTempPassengerList(List<TempPassenger> tempPassengerList) {
		this.tempPassengerList = tempPassengerList;
	}

	public Long getPrice_Count() {
		return price_Count;
	}

	public void setPrice_Count(Long price_Count) {
		this.price_Count = price_Count;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.math.BigDecimal getFare() {
		return fare;
	}

	public java.math.BigDecimal getTax() {
		return tax;
	}

	public java.math.BigDecimal getYq() {
		return yq;
	}

	public String getB_pnr() {
		return b_pnr;
	}

	public void setB_pnr(String b_pnr) {
		this.b_pnr = b_pnr;
	}

	public String getIs_team() {
		return is_team;
	}

	public void setIs_team(String is_team) {
		this.is_team = is_team;
	}

	public String getHasNI() {
		return hasNI;
	}

	public void setHasNI(String hasNI) {
		this.hasNI = hasNI;
	}

	public String getHasINF() {
		return hasINF;
	}

	public void setHasINF(String hasINF) {
		this.hasINF = hasINF;
	}

	public String getIsCHD() {
		return isCHD;
	}

	public void setIsCHD(String isCHD) {
		this.isCHD = isCHD;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPassengers_count() {
		return passengers_count;
	}

	public void setPassengers_count(Long passengers_count) {
		this.passengers_count = passengers_count;
	}

	public Long getLines_count() {
		return lines_count;
	}

	public void setLines_count(Long lines_count) {
		this.lines_count = lines_count;
	}

	public List getTempTicketsList() {
		return tempTicketsList;
	}

	public void setTempTicketsList(List tempTicketsList) {
		this.tempTicketsList = tempTicketsList;
	}

	public Long getTickets_count() {
		return tickets_count;
	}

	public void setTickets_count(Long tickets_count) {
		this.tickets_count = tickets_count;
	}
}
