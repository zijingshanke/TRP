package com.fdays.tsms.airticket;

import com.neza.base.ListActionForm;

public class AlidzForm extends  ListActionForm{
	
	private String pnr;
	private int bigPnr;
	private String air;
	private String b2bUser;
	private String b2bPwd;
	private int autoPayFlag;
	
	
	
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public int getBigPnr() {
		return bigPnr;
	}
	public void setBigPnr(int bigPnr) {
		this.bigPnr = bigPnr;
	}
	public String getAir() {
		return air;
	}
	public void setAir(String air) {
		this.air = air;
	}
	public String getB2bUser() {
		return b2bUser;
	}
	public void setB2bUser(String user) {
		b2bUser = user;
	}
	public String getB2bPwd() {
		return b2bPwd;
	}
	public void setB2bPwd(String pwd) {
		b2bPwd = pwd;
	}
	public int getAutoPayFlag() {
		return autoPayFlag;
	}
	public void setAutoPayFlag(int autoPayFlag) {
		this.autoPayFlag = autoPayFlag;
	}
}
