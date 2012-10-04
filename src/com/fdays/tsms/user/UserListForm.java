package com.fdays.tsms.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.neza.base.ListActionForm;

public class UserListForm extends ListActionForm {

	private int userId;
	private long userType = new Long(0);
	private long userDepart = new Long(0);
	private long userStatus = new Long(0);
	private String userName = "";
	private String userNo = "";
	private String serialNumber = "";
	private String userEmail = "";
	private String key = "";

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {

		thisAction = "";
		userName = "";
		this.setIntPage(1);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getUserType() {
		return userType;
	}

	public void setUserType(long userType) {
		this.userType = userType;
	}

	public long getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(long userStatus) {
		this.userStatus = userStatus;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public long getUserDepart() {
		return userDepart;
	}

	public void setUserDepart(long userDepart) {
		this.userDepart = userDepart;
	}

	
}
