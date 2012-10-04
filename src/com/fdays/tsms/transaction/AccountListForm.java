package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class AccountListForm extends ListActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long paymentToolId;// 支付工具名称
	private String name;// 支付账号名称
	private String accountNo;// 支付账号
	private long tranType=new Long(0);// 收付类型

	public long getPaymentToolId() {
		return paymentToolId;
	}

	public void setPaymentToolId(long paymentToolId) {
		this.paymentToolId = paymentToolId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public long getTranType() {
		return tranType;
	}

	public void setTranType(long tranType) {
		this.tranType = tranType;
	}

}
