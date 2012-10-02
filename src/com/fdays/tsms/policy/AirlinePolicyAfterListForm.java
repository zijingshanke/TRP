package com.fdays.tsms.policy;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

/**
 * AirlinePolicyAfterList的actionForm
 * @author chenqx
 * data		2010-12-7
 */
public class AirlinePolicyAfterListForm extends ListActionForm {

	protected long id;
    protected String name;
    protected String carrier;
    protected java.sql.Timestamp beginDate;
    protected java.sql.Timestamp endDate;
    protected String memo;
    protected java.math.BigDecimal quota;
    protected Long status;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public java.math.BigDecimal getQuota() {
		return quota;
	}

	public void setQuota(java.math.BigDecimal quota) {
		this.quota = quota;
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
	
}
