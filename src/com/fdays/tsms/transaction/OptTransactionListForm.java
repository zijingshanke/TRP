package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class OptTransactionListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long userDepart = new Long(0);// 部门
	private String userDepartList = "";
	private String sysName;// 操作员
	private String banlanceDate;// 日期
	private String sessionId;

	private String startDate = "";// 开始时间
	private String endDate = "";// 结束时间

	public String getUserDepartList() {
		if (userDepart == 0) {
			return "1,2,3,11,12,21,22";
		}
		return userDepartList;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getBanlanceDate() {
		return banlanceDate;
	}

	public void setBanlanceDate(String banlanceDate) {
		this.banlanceDate = banlanceDate;
	}

	public long getUserDepart() {
		return userDepart;
	}

	public void setUserDepart(long userDepart) {
		this.userDepart = userDepart;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setUserDepartList(String userDepartList) {
		this.userDepartList = userDepartList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	

}
