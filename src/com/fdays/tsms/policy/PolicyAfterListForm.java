package com.fdays.tsms.policy;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

public class PolicyAfterListForm extends ListActionForm {

	 protected long id;
     protected String flightCode;
     protected String flightCodeExcept;
     protected String startEnd;
     protected String flightClass;
     protected String flightClassExcept;
     protected Long discount;
     protected java.math.BigDecimal rate;
     protected Long travelType;
     protected Long ticketType;
     protected java.math.BigDecimal quota;
     protected String memo;
     protected String userName;
     protected java.sql.Timestamp updateDate;
     protected Long status;
     protected java.sql.Timestamp beginDate;
     protected java.sql.Timestamp endDate;
     protected Long ticketNum;
     protected Long airlinePolicyAfterId;
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


	public Long getTicketNum() {
		return ticketNum;
	}


	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}


	public void setId(long id) {
		this.id = id;
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


	public String getStartEnd() {
		return startEnd;
	}


	public void setStartEnd(String startEnd) {
		this.startEnd = startEnd;
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


	public Long getDiscount() {
		return discount;
	}


	public void setDiscount(Long discount) {
		this.discount = discount;
	}


	public java.math.BigDecimal getRate() {
		return rate;
	}


	public void setRate(java.math.BigDecimal rate) {
		this.rate = rate;
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


	public java.math.BigDecimal getQuota() {
		return quota;
	}


	public void setQuota(java.math.BigDecimal quota) {
		this.quota = quota;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public java.sql.Timestamp getUpdateDate() {
		return updateDate;
	}


	public void setUpdateDate(java.sql.Timestamp updateDate) {
		this.updateDate = updateDate;
	}


	public Long getStatus() {
		return status;
	}


	public void setStatus(Long status) {
		this.status = status;
	}


	public Long getAirlinePolicyAfterId() {
		return airlinePolicyAfterId;
	}


	public void setAirlinePolicyAfterId(Long airlinePolicyAfterId) {
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public AirlinePolicyAfter getAirlinePolicyAfter() {
		return airlinePolicyAfter;
	}


	public void setAirlinePolicyAfter(AirlinePolicyAfter airlinePolicyAfter) {
		this.airlinePolicyAfter = airlinePolicyAfter;
	}
	
}
