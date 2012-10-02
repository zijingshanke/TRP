package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Date;
import com.fdays.tsms.airticket._entity._Flight;
import com.neza.tool.DateUtil;

public class Flight extends _Flight {
	private static final long serialVersionUID = 1L;

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
		if (this.boardingTime != null && "".equals(boardingTime) == false)
		{
			Date tempDate = new Date(boardingTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}
	
	public String getFormatBoardingDate() {
		String mydate = "";
		if (this.boardingTime != null && "".equals(boardingTime) == false)
		{
			Date tempDate = new Date(boardingTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd");
		}
		return mydate;
	}

	public java.math.BigDecimal getTicketPrice() {
		if(this.ticketPrice==null){
			return BigDecimal.ZERO;
		}
		return this.ticketPrice.abs();
	}

	public java.math.BigDecimal getAirportPriceAdult() {
		if(this.airportPriceAdult==null){
			return BigDecimal.ZERO;
		}
		return this.airportPriceAdult.abs();
	}

	public java.math.BigDecimal getFuelPriceAdult() {
		if(this.fuelPriceAdult==null){
			return BigDecimal.ZERO;
		}
		return this.fuelPriceAdult.abs();
	}

	public java.math.BigDecimal getAirportPriceBaby() {
		if(this.airportPriceBaby==null){
			return BigDecimal.ZERO;
		}
		return this.airportPriceBaby.abs();
	}

	public java.math.BigDecimal getFuelPriceBaby() {
		if(this.fuelPriceChild==null){
			return BigDecimal.ZERO;
		}
		return this.fuelPriceBaby.abs();
	}

	public java.math.BigDecimal getAirportPriceChild() {
		if(this.airportPriceChild==null){
			return BigDecimal.ZERO;
		}
		return this.airportPriceChild.abs();
	}

}
