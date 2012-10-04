package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import com.fdays.tsms.airticket._entity._Flight;
import com.neza.tool.DateUtil;

public class Flight extends _Flight implements Comparator<Object> {
	private static final long serialVersionUID = 1L;
	
	public static final long STATES_1 = 1;// 有效
	public static final long STATES_0 = 0;// 无效
	public static final long STATES_3 = 3;// 退
	public static final long STATES_4 = 4;// 废
	public static final long STATES_5 = 5;// 改

	private String cyr = "";
	private String cyrText;
	private String startPointText;
	private String endPointText;
	private String hcText = "";// 航程

	public String getHcText() {
		StringBuffer hcTemp = new StringBuffer();
		if (this.startPoint != null && !"".equals(this.startPoint.trim())) {
			hcTemp.append(this.startPoint);
		}
		if (this.endPoint != null && !"".equals(this.endPoint.trim())) {
			hcTemp.append("-");
			hcTemp.append(this.endPoint);
		}
		return hcText = hcTemp.toString();
	}

	public void setHcText(String hcText) {
		this.hcText = hcText;
	}

	public String getCyr() {
		if (this.flightCode != null && "".equals(this.flightCode) == false) {
			if (this.flightCode.length() > 3) {
				return this.flightCode.substring(0, 2).toUpperCase();
			} else {
				return "NO";
			}
		} else {
			return "cyr";
		}
	}

	public String getStartPointText() {

		if (this.getStartPoint() != null
				&& !"".equals(this.getStartPoint().trim())) {
			startPointText = Airport.getNameByCode(this.getStartPoint());
		}
		return startPointText;
	}

	public String getBoardingDate() {
		String mydate = "";
		if (boardingTime != null && "".equals(boardingTime) == false) {
			Date tempDate = new Date(boardingTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd");
		}
		return mydate;
	}

	public void setStartPointText(String startPointText) {
		this.startPointText = startPointText;
	}

	public String getEndPointText() {

		if (this.getEndPoint() != null && !"".equals(this.getEndPoint().trim())) {
			endPointText = Airport.getNameByCode(this.getEndPoint());
		}
		return endPointText;
	}

	public void setEndPointText(String endPointText) {
		this.endPointText = endPointText;
	}

	public String getCyrText() {
		if (this.cyr != null) {
			cyrText = Carrier.getNameByCode(this.cyr);
		}
		return cyrText;
	}

	public void setCyrText(String cyrText) {
		this.cyrText = cyrText;
	}

	public void setCyr(String cyr) {
		this.cyr = cyr;
	}

	public String getFormatBoardingTime() {
		String mydate = "";
		if (this.boardingTime != null && "".equals(boardingTime) == false) {
			Date tempDate = new Date(boardingTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatBoardingDate() {
		String mydate = "";
		if (this.boardingTime != null && "".equals(boardingTime) == false) {
			Date tempDate = new Date(boardingTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd");
		}
		return mydate;
	}

	public java.math.BigDecimal getTicketPrice() {
		if (this.ticketPrice == null) {
			return BigDecimal.ZERO;
		}
		return this.ticketPrice.abs();
	}

	public java.math.BigDecimal getAirportPriceAdult() {
		if (this.airportPriceAdult == null) {
			return BigDecimal.ZERO;
		}
		return this.airportPriceAdult.abs();
	}

	public java.math.BigDecimal getFuelPriceAdult() {
		if (this.fuelPriceAdult == null) {
			return BigDecimal.ZERO;
		}
		return this.fuelPriceAdult.abs();
	}

	public java.math.BigDecimal getAirportPriceBaby() {
		if (this.airportPriceBaby == null) {
			return BigDecimal.ZERO;
		}
		return this.airportPriceBaby.abs();
	}

	public java.math.BigDecimal getFuelPriceBaby() {
		if (this.fuelPriceChild == null) {
			return BigDecimal.ZERO;
		}
		return this.fuelPriceBaby.abs();
	}

	public java.math.BigDecimal getAirportPriceChild() {
		if (this.airportPriceChild == null) {
			return BigDecimal.ZERO;
		}
		return this.airportPriceChild.abs();
	}

	public int compare(Object o1, Object o2) {
		Flight flight1 = (Flight) o1;
		Flight flight2 = (Flight) o2;

		Long id1 = flight1.getId();
		Long id2 = flight2.getId();

		// System.out.println("time1:"+time1);
		// System.out.println("time2:"+time2);
		int flag = id1.compareTo(id2);

		// System.out.println("flag:"+flag);

		if (flag > 0) {
			return 1;// 第一个大于第二个
		}
		if (flag < 0) {
			return -1;// 第一个小于第二个
		} else {
			return 0;// 等于
		}
	}
}
