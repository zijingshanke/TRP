package com.fdays.tsms.transaction;

import java.math.BigDecimal;

import com.fdays.tsms.transaction._entity._Account;
import com.neza.encrypt.MD5;
import com.neza.utility.PingYin;

public class Account extends _Account {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		System.out.println(MD5.encrypt("111111"));
		
	}

	private long paymentToolId;// 支付工具表ID
	protected java.math.BigDecimal totalAmount = new BigDecimal(0);

	// 交易类型
	public static final long tran_type_1 = 1;// 付款帐号
	public static final long tran_type_2 = 2;// 收款帐号
	public static final long tran_type_3 = 3;// 收付帐号

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
}
