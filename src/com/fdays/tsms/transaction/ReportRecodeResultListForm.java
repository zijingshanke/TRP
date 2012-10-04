package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class ReportRecodeResultListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String name;
	protected java.sql.Timestamp beginDate;
	protected java.sql.Timestamp endDate;
	protected String recodeSet;
	protected String memo;
	protected String userNo;
	protected java.sql.Timestamp lastDate;
	protected Long reportType;
	protected Long status;
	protected Long reportRecodeId;
	public long indexId;
	protected java.util.Set<ReportRecode> reportRecodes = new java.util.HashSet<ReportRecode>(
			0);

	protected java.sql.Timestamp reportDate;
	
	

	public java.sql.Timestamp getReportDate() {
		return reportDate;
	}

	public void setReportDate(java.sql.Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRecodeSet() {
		return recodeSet;
	}

	public void setRecodeSet(String recodeSet) {
		this.recodeSet = recodeSet;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public java.sql.Timestamp getLastDate() {
		return lastDate;
	}

	public void setLastDate(java.sql.Timestamp lastDate) {
		this.lastDate = lastDate;
	}

	public Long getReportType() {
		if(this.reportType == null){
			this.reportType = 0l;
		}
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getReportRecodeId() {
		return reportRecodeId;
	}

	public void setReportRecodeId(Long reportRecodeId) {
		this.reportRecodeId = reportRecodeId;
	}

	public java.util.Set<ReportRecode> getReportRecodes() {
		return reportRecodes;
	}

	public void setReportRecodes(java.util.Set<ReportRecode> reportRecodes) {
		this.reportRecodes = reportRecodes;
	}

	public long getIndexId() {
		return indexId;
	}

	public void setIndexId(long indexId) {
		this.indexId = indexId;
	}
	
}
