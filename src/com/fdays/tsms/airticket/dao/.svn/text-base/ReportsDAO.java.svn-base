package com.fdays.tsms.airticket.dao;

import java.util.List;

import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ReportsDAO extends BaseDAO{
	public List marketReportsList(AirticketOrderListForm rlf) throws AppException;
	
	//银行卡付款统计
	public List getStatementList(StatementListForm statementForm) throws AppException;
}
