package com.fdays.tsms.policy;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

public class IndicatorStatisticsListForm extends ListActionForm {

	 protected long id;
	 protected long airlinePolicyAfterId;
	 protected String carrier;
     protected String flightCode;
     protected String flightCodeExcept;
     protected String flightPoint;
     protected String flightPointExcept;
     protected String flightClass;
     protected String flightClassExcept;
     protected Long travelType;
     protected Long ticketType;
     protected java.sql.Timestamp beginDate;
     protected java.sql.Timestamp endDate;
     protected Long isAmount;
     protected Long isAward;
     protected Long isHighClass;
     protected Long status;
     protected String remark;
     protected AirlinePolicyAfter airlinePolicyAfter;
     private String key = "";
     
  
	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {

		thisAction = "";
		this.setIntPage(1);
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public long getAirlinePolicyAfterId() {
		return airlinePolicyAfterId;
	}


	public void setAirlinePolicyAfterId(long airlinePolicyAfterId) {
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}


	public String getCarrier() {
		return carrier;
	}


	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	public String getFlightCode() {
		return flightCode;
	}


	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}


	public String getFlightCodeExcept() {
		return flightCodeExcept;
	}


	public void setFlightCodeExcept(String flightCodeExcept) {
		this.flightCodeExcept = flightCodeExcept;
	}


	public String getFlightPoint() {
		return flightPoint;
	}


	public void setFlightPoint(String flightPoint) {
		this.flightPoint = flightPoint;
	}


	public String getFlightPointExcept() {
		return flightPointExcept;
	}


	public void setFlightPointExcept(String flightPointExcept) {
		this.flightPointExcept = flightPointExcept;
	}


	public String getFlightClass() {
		return flightClass;
	}


	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}


	public String getFlightClassExcept() {
		return flightClassExcept;
	}


	public void setFlightClassExcept(String flightClassExcept) {
		this.flightClassExcept = flightClassExcept;
	}


	public Long getTravelType() {
		return travelType;
	}


	public void setTravelType(Long travelType) {
		this.travelType = travelType;
	}


	public Long getTicketType() {
		return ticketType;
	}


	public void setTicketType(Long ticketType) {
		this.ticketType = ticketType;
	}


	public java.sql.Timestamp getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(java.sql.Timestamp beginDate) {
		this.beginDate = beginDate;
	}


	public java.sql.Timestamp getEndDate() {
		return endDate;
	}


	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}


	public Long getIsAmount() {
		return isAmount;
	}


	public void setIsAmount(Long isAmount) {
		this.isAmount = isAmount;
	}


	public Long getIsAward() {
		return isAward;
	}


	public void setIsAward(Long isAward) {
		this.isAward = isAward;
	}


	public Long getIsHighClass() {
		return isHighClass;
	}


	public void setIsHighClass(Long isHighClass) {
		this.isHighClass = isHighClass;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public AirlinePolicyAfter getAirlinePolicyAfter() {
		return airlinePolicyAfter;
	}


	public void setAirlinePolicyAfter(AirlinePolicyAfter airlinePolicyAfter) {
		this.airlinePolicyAfter = airlinePolicyAfter;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}
	
}
