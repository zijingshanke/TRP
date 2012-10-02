package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class AgentListForm extends ListActionForm{

	private long companyId;//外键 公司ID
	private String name;//客户名称
	private long type;//客户类型
	
	public long getType() {
		return type;
	}
	public void setType(long type) {
		this.type = type;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}