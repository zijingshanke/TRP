
package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class ReportRecodeListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 protected long id;
     protected Long payToolId;
     protected Long platformId;
     protected String subPnr;
     protected String airOrderNo;
     protected java.math.BigDecimal amount;
     protected Long statementType;
     protected Long accountId;
     protected String accountName;
     protected Long passengerCount;
     protected java.sql.Timestamp reportDate;
     protected Long reportRownum;
     protected Long status;
     protected Long type;
     protected String memo;
     protected long reportRecodeResultId;
     protected com.fdays.tsms.transaction.ReportRecodeResult reportRecodeResult;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getPayToolId() {
		return payToolId;
	}
	public void setPayToolId(Long payToolId) {
		this.payToolId = payToolId;
	}
	public Long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	public String getSubPnr() {
		return subPnr;
	}
	public void setSubPnr(String subPnr) {
		this.subPnr = subPnr;
	}
	public String getAirOrderNo() {
		return airOrderNo;
	}
	public void setAirOrderNo(String airOrderNo) {
		this.airOrderNo = airOrderNo;
	}
	public java.math.BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	public Long getStatementType() {
		return statementType;
	}
	public void setStatementType(Long statementType) {
		this.statementType = statementType;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Long getPassengerCount() {
		return passengerCount;
	}
	public void setPassengerCount(Long passengerCount) {
		this.passengerCount = passengerCount;
	}
	public java.sql.Timestamp getReportDate() {
		return reportDate;
	}
	public void setReportDate(java.sql.Timestamp reportDate) {
		this.reportDate = reportDate;
	}
	public Long getReportRownum() {
		return reportRownum;
	}
	public void setReportRownum(Long reportRownum) {
		this.reportRownum = reportRownum;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public com.fdays.tsms.transaction.ReportRecodeResult getReportRecodeResult() {
		return reportRecodeResult;
	}
	public void setReportRecodeResult(
			com.fdays.tsms.transaction.ReportRecodeResult reportRecodeResult) {
		this.reportRecodeResult = reportRecodeResult;
	}
	public long getReportRecodeResultId() {
		return reportRecodeResultId;
	}
	public void setReportRecodeResultId(long reportRecodeResultId) {
		this.reportRecodeResultId = reportRecodeResultId;
	}
	
}
