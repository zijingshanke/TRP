package com.fdays.tsms.transaction;

import com.fdays.tsms.transaction._entity._Agent;
import com.neza.utility.PingYin;

public class Agent extends _Agent {
	private static final long serialVersionUID = 1L;
	private long companyId;// 外键 公司ID

	// 类型
	public static final long type_1 = 1;// B2C散客
	public static final long type_2 = 2;// 团队
	public static final long type_3 = 3;// B2B

	// 状态
	public static final long STATES_0 = 0;// 有效
	public static final long STATES_1 = 1;// 无效
	private String charSet="GBK";
	public String getShowName() {
		if (this.name != null && "".equals(this.name) == false) {
			String myFirstLetter = PingYin.getFirstLetter(this.name,charSet);
			if (myFirstLetter != null && myFirstLetter.length() > 1) {
				myFirstLetter = myFirstLetter.substring(0, 1);
				return myFirstLetter + "-" + this.name;
			}
		}
		return this.name;
	}

	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType().intValue() == type_1) {
				return "B2C散客";
			} else if (this.getType().intValue() == type_2) {
				return "团队";
			} else if (this.getType().intValue() == type_3) {
				return "B2B";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// 状态
	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATES_0) {
				return "有效";
			} else if (this.getStatus().intValue() == STATES_1) {
				return "无效";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
}
