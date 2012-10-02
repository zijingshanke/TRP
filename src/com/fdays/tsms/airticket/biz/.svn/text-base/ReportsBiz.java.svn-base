package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.exception.AppException;

public interface ReportsBiz {
	public List marketReportsList(AirticketOrderListForm rlf) throws AppException ;
	public ArrayList<ArrayList<Object>> getMarketReportsList(AirticketOrderListForm alf)throws AppException;
	//银行卡付款统计
	public List getStatementList(StatementListForm statementForm) throws AppException;
}
