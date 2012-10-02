package com.fdays.tsms.policy;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

/**
 * OrderPolicyAfterList的actionForm
 * @author chenqx
 * data		2010-12-24
 */
public class SaleStatisticsListForm extends ListActionForm {
    protected long id;
    protected String carrier;
    protected java.sql.Timestamp beginDate;
    protected java.sql.Timestamp endDate;
    protected Long ticketNum;
    protected java.math.BigDecimal saleAmount;
    protected java.math.BigDecimal afterAmount;
    protected Long status;
    protected com.fdays.tsms.policy.AirlinePolicyAfter airlinePolicyAfter;
    protected Long airlinePolicyAfterId;
   
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(Long ticketNum) {
		this.ticketNum = ticketNum;
	}

	public java.math.BigDecimal getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(java.math.BigDecimal saleAmount) {
		this.saleAmount = saleAmount;
	}

	public java.math.BigDecimal getAfterAmount() {
		return afterAmount;
	}

	public void setAfterAmount(java.math.BigDecimal afterAmount) {
		this.afterAmount = afterAmount;
	}

	public com.fdays.tsms.policy.AirlinePolicyAfter getAirlinePolicyAfter() {
		return airlinePolicyAfter;
	}

	public void setAirlinePolicyAfter(
			com.fdays.tsms.policy.AirlinePolicyAfter airlinePolicyAfter) {
		this.airlinePolicyAfter = airlinePolicyAfter;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getAirlinePolicyAfterId() {
		return airlinePolicyAfterId;
	}

	public void setAirlinePolicyAfterId(Long airlinePolicyAfterId) {
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}

}
