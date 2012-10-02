package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class AgentListForm extends ListActionForm{

	private long companyId;//外键 公司ID
	private String name;//客户名称
	private long type;//客户类型
	
	private String content;			//短信发送的内容
	private String receiver;		//短信接收人号码集合
	private String operatorObject;	//操作对象 (all:全部;b2c:团队;team:团队)
	
	public String getOperatorObject() {
		return operatorObject;
	}
	public void setOperatorObject(String operatorObject) {
		this.operatorObject = operatorObject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
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
