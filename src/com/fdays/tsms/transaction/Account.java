package com.fdays.tsms.transaction;

import java.math.BigDecimal;

import com.fdays.tsms.transaction._entity._Account;

public class Account extends _Account {
	private static final long serialVersionUID = 1L;

	private long paymentToolId;// 支付工具表ID

	protected java.math.BigDecimal totalAmount = new BigDecimal(0);
	protected java.math.BigDecimal actualAmount = new BigDecimal(0);
	protected java.math.BigDecimal unsettledAccount = new BigDecimal(0);
	protected java.math.BigDecimal commission = new BigDecimal(0);
	protected java.math.BigDecimal rakeOff = new BigDecimal(0);

	// 交易类型
	public static final long tran_type_1 = 1;// 付款帐号
	public static final long tran_type_2 = 2;// 收款帐号
	public static final long tran_type_3 = 3;// 收付帐号

	// 状态
	public static final long STATES_0 = 0;// 有效
	public static final long STATES_1 = 1;// 无效

	public String getTypeInfo() {
		if (this.getType() != null) {
			return "";
		} else {
			return null;
		}
	}

	public String getTranTypeInfo() {
		if (this.getTranType() != null) {
			if (this.getTranType().intValue() == tran_type_1) {
				return "付款";
			} else if (this.getTranType().intValue() == tran_type_2) {
				return "收款";
			} else if (this.getTranType().intValue() == tran_type_3) {
				return "收付";
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

	public long getPaymentToolId() {
		return paymentToolId;
	}

	public void setPaymentToolId(long paymentToolId) {
		this.paymentToolId = paymentToolId;
	}

	public java.math.BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(java.math.BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public java.math.BigDecimal getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(java.math.BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}

	public java.math.BigDecimal getUnsettledAccount() {
		return unsettledAccount;
	}

	public void setUnsettledAccount(java.math.BigDecimal unsettledAccount) {
		this.unsettledAccount = unsettledAccount;
	}

	public java.math.BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(java.math.BigDecimal commission) {
		this.commission = commission;
	}

	public java.math.BigDecimal getRakeOff() {
		return rakeOff;
	}

	public void setRakeOff(java.math.BigDecimal rakeOff) {
		this.rakeOff = rakeOff;
	}

}
