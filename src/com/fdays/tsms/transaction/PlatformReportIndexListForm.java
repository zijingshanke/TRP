package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class PlatformReportIndexListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private Long platformId = new Long(0);
	private Long accountId = new Long(0);
	private Long paymentToolId = new Long(0);
	
	private Long type = new Long(0);
	private Long compareType = new Long(0);
	private Long status = new Long(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	
	

	public Long getPaymentToolId() {
		return paymentToolId;
	}

	public void setPaymentToolId(Long paymentToolId) {
		this.paymentToolId = paymentToolId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getCompareType() {
		return compareType;
	}

	public void setCompareType(Long compareType) {
		this.compareType = compareType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
