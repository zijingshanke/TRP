package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class CompanyListForm extends ListActionForm{

	
	private String name;//公司名称
	private long type;//公司类型
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
}
