package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class PaymentToolListForm extends ListActionForm{

	private String name;//支付名称
	private String type;//支付类型



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
