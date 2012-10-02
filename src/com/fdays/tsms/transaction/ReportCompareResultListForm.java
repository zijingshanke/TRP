package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class ReportCompareResultListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int id = 0;
	public String name;
	public Long paymenttoolId;
	public Long accountId;
	public Long platformId;
	
	public java.sql.Timestamp beginDate;
	public java.sql.Timestamp endDate;
	public String memo;
	public String userNo;
	public java.sql.Timestamp lastDate;
	public Long compareType;
	public Long type;
	public Long status;

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

}
