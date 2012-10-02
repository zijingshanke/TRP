package com.fdays.tsms.policy;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

/**
 * data 2011-01-24
 */
public class AirlinePolicyAfterListForm extends ListActionForm {

	protected long id;
    protected String carrier;
    protected String flightCode;
    protected String flightCodeExcept;
    protected String startPoint;
    protected String endPoint;
    protected String flightClass;
    protected String flightClassExcept;
    protected java.sql.Timestamp beginDate;
    protected java.sql.Timestamp endDate;
    protected String weekDayExcept;
    protected String discount;
    protected java.math.BigDecimal commission;
    protected java.math.BigDecimal commissionOther;
    protected java.math.BigDecimal rakeOff;
    protected String memo;
    protected Long travelType;
    protected Long ticketType;
    protected Long type;
    protected Long status;
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

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
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

	public String getWeekDayExcept() {
		return weekDayExcept;
	}

	public void setWeekDayExcept(String weekDayExcept) {
		this.weekDayExcept = weekDayExcept;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public java.math.BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(java.math.BigDecimal commission) {
		this.commission = commission;
	}

	public java.math.BigDecimal getCommissionOther() {
		return commissionOther;
	}

	public void setCommissionOther(java.math.BigDecimal commissionOther) {
		this.commissionOther = commissionOther;
	}

	public java.math.BigDecimal getRakeOff() {
		return rakeOff;
	}

	public void setRakeOff(java.math.BigDecimal rakeOff) {
		this.rakeOff = rakeOff;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
