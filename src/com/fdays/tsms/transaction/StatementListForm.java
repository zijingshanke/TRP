package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class StatementListForm extends ListActionForm{

	private String statementNo;//结算单号
	private long status=Long.valueOf(-1);//状态
	private String status1; //查询已结/部分结数据

	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public long getStatus() {
		return status;
	}
	public void setStatus(long status) {
		this.status = status;
	}
	public String getStatementNo() {
		return statementNo;
	}
	public void setStatementNo(String statementNo) {
		this.statementNo = statementNo;
	}
	

}
