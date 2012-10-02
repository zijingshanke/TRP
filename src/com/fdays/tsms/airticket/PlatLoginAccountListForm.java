package com.fdays.tsms.airticket;

import com.neza.base.ListActionForm;

public class PlatLoginAccountListForm extends ListActionForm{

	
	private String platformName;//交易平台名称
	private String loginName;//登录账号
	
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
}
