package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.TeamAirticketOrderReport;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.exception.AppException;

public interface ReportsBiz {
	public List marketReportsList(AirticketOrderListForm rlf) throws AppException ;
	public ArrayList<ArrayList<Object>> getMarketReportsList(AirticketOrderListForm alf)throws AppException;
	//银行卡付款统计
	public List getStatementList(StatementListForm statementForm) throws AppException;

	//团队销售报表
	public List getTeamAirTicketOrderList(AirticketOrderListForm rlf) throws AppException;
	//导出团队机票销售报表
	public ArrayList<ArrayList<Object>> downloadTeamAirTicketOrder(AirticketOrderListForm rlf,List<TeamAirticketOrderReport> rePortlist)throws AppException;
	
	//导出团队未返代理费报表
	public ArrayList<ArrayList<Object>> downloadTeamNotReturnProxy(AirticketOrderListForm rlf,List<TeamAirticketOrderReport> rePortlist)throws AppException;
	
	//赋值给TeamAirticketOrderReport类
	public List<TeamAirticketOrderReport>  getTeamAirticketOrderReport(AirticketOrderListForm rlf) throws AppException;
	//团队未返代理费报表（将数据赋给TeamAirticketOrderReport类）
	public List<TeamAirticketOrderReport> getTeamNotReturnProxy(AirticketOrderListForm rlf) throws AppException;

	public List saleReportsByGroupMarkNoList(AirticketOrderListForm rlf) throws AppException;
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo,String tranType) throws AppException;
	
	public List saleReportsList(AirticketOrderListForm rlf) throws AppException;

	public ArrayList<ArrayList<Object>> downLoadsaleReports(AirticketOrderListForm alf)throws AppException;
	public List getPayment_toolList(long type) throws AppException;
	public ArrayList<ArrayList<Object>> downLoadRetireReports(AirticketOrderListForm alf)throws AppException;

}
