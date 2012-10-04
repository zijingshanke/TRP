package com.fdays.tsms.user;

import com.fdays.tsms.user._entity._SysUser;

public class SysUser extends _SysUser {
	private static final long serialVersionUID = 1L;
	private String userPassword1 = "";
	private String userPassword2 = "";
	private String rand = "";

	public String getUserDepartInfo() {
		if (this.userDepart == null || "".equals(this.userDepart)) {
			return "无";
		} else {
			if (this.userDepart.intValue() == 1) {
				return "出票组";
			} else if (this.userDepart.intValue() == 2) {
				return "倒票组";
			} else if (this.userDepart.intValue() == 3) {
				return "退票组";
			} else if (this.userDepart.intValue() == 11) {
				return "B2C组";
			} else if (this.userDepart.intValue() == 12) {
				return "团队部";
			} else if (this.userDepart.intValue() == 21) {
				return "支付组";
			} else if (this.userDepart.intValue() == 22) {
				return "财务部";
			} else if (this.userDepart.intValue() == 31) {
				return "管理组";
			} else if (this.userDepart.intValue() == 41) {
				return "政策组";
			} else {
				return "未定义";
			}
		}
	}

	public String getUserPassword1() {
		return userPassword1;
	}

	public void setUserPassword1(String userPassword1) {
		this.userPassword1 = userPassword1;
	}

	public String getUserPassword2() {
		return userPassword2;
	}

	public void setUserPassword2(String userPassword2) {
		this.userPassword2 = userPassword2;
	}

	public String getRand() {
		return rand;
	}

	public void setRand(String rand) {
		this.rand = rand;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getUserStatusCaption() {
		if (this.userStatus.intValue() == 1)
			return "启用";
		else
			return "停用";
	}

}
