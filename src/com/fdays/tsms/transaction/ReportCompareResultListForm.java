package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class ReportCompareResultListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id = 0;
	public String name;
	public Long paymenttoolId=Long.valueOf(0);
	public Long accountId=Long.valueOf(0);
	public Long platformId=Long.valueOf(0);
	
	public java.sql.Timestamp beginDate;
	public java.sql.Timestamp endDate;
	public String beginDateStr;
	public String endDateStr;
	public String memo;
	public String userNo;
	public java.sql.Timestamp lastDate;
	public Long compareType=Long.valueOf(0);
	public Long tranType=Long.valueOf(0);	
	public Long type=Long.valueOf(0);
	public Long status=Long.valueOf(0);
	
	public String fileName = "";
	public String listAttachName = "";

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

	public Long getPaymenttoolId() {
		return paymenttoolId;
	}

	public Long getTranType() {
		return tranType;
	}

	public void setTranType(Long tranType) {
		this.tranType = tranType;
	}

	public void setPaymenttoolId(Long paymenttoolId) {
		this.paymenttoolId = paymenttoolId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
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
	
	

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
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

	public Long getCompareType() {
		return compareType;
	}

	public void setCompareType(Long compareType) {
		this.compareType = compareType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getListAttachName() {
		return listAttachName;
	}

	public void setListAttachName(String listAttachName) {
		this.listAttachName = listAttachName;
	}

}
