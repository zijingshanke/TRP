package com.fdays.tsms.system;

import com.fdays.tsms.user.SysUser;
import com.neza.base.ListActionForm;

public class TicketLogListForm extends ListActionForm{
	private long id;
	private String orderNo;
	private long orderType;
	private String ip;
	private java.sql.Timestamp optTime;
	private long type;
	private long status;
	private com.fdays.tsms.user.SysUser sysUser = new SysUser();

	private String fromDate = "";
	private String toDate = "";
	private String userNo = "";

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public long getOrderType() {
		return orderType;
	}

	public void setOrderType(long orderType) {
		this.orderType = orderType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public java.sql.Timestamp getOptTime() {
		return optTime;
	}

	public void setOptTime(java.sql.Timestamp optTime) {
		this.optTime = optTime;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public com.fdays.tsms.user.SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(com.fdays.tsms.user.SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	
}
