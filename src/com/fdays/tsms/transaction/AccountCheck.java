package com.fdays.tsms.transaction;

import java.math.BigDecimal;
import java.util.Date;

import com.fdays.tsms.transaction._entity._AccountCheck;
import com.neza.tool.DateUtil;

public class AccountCheck extends _AccountCheck {
	private static final long serialVersionUID = 1L;

	private long userId = new Long(0);
	private long accountId = new Long(0);
	private String accountName="";
	
	
	// 类型
	public static final long TYPE_0 = 0;// 上班
	public static final long TYPE_1 = 1;// 下班

	// 状态
	public static final long STATES_0 = 0;// 有效
	public static final long STATES_1 = 1;// 无效
	
	
	// 状态
	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType() == TYPE_0) {
				return "上班";
			} else if (this.getType().intValue() == TYPE_1) {
				return "下班";
			} else {
				return "";
			}
		}
		return "";
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

	public java.math.BigDecimal getCheckOnAmount() {
		if (this.checkOnAmount == null) {
			return BigDecimal.ZERO;
		}
		return this.checkOnAmount;
	}

	public java.math.BigDecimal getTransInAmount() {
		if (this.transInAmount == null) {
			return BigDecimal.ZERO;
		}
		return this.transInAmount;
	}

	public java.math.BigDecimal getTransOutAmount() {
		if (this.transOutAmount == null) {
			return BigDecimal.ZERO;
		}
		return this.transOutAmount;
	}

	public java.math.BigDecimal getPayAmount() {
		if (this.payAmount == null) {
			return BigDecimal.ZERO;
		}
		return this.payAmount;
	}

	public java.math.BigDecimal getRefundAmount() {
		if (this.refundAmount == null) {
			return BigDecimal.ZERO;
		}
		return this.refundAmount;
	}

	public java.math.BigDecimal getCheckOffAmount() {
		if (this.checkOffAmount == null) {
			return BigDecimal.ZERO;
		}
		return this.checkOffAmount;
	}

	public String getFormatCheckOnDate() {
		String mydate = "";
		if (this.checkOnDate != null && "".equals(checkOnDate) == false) {
			Date tempDate = new Date(checkOnDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatCheckOffDate() {
		String mydate = "";
		if (this.checkOffDate != null && "".equals(checkOffDate) == false) {
			Date tempDate = new Date(checkOffDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

}
