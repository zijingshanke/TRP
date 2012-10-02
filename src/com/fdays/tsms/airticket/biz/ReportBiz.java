package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.airticket.GeneralReport;
import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.airticket.TeamReport;
import com.neza.exception.AppException;

public interface ReportBiz {
	public List<OptTransaction> listOptTransaction(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadOptTransactionReport(
			Report report, List data) throws AppException;

	public List<TeamReport> getTeamSaleReportContent(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadTeamSaleReport(Report report,
			List data) throws AppException;

	public List<TeamReport> getTeamRakeOffReportContent(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadTeamRakeOffReport(
			Report report, List data) throws AppException;

	public List<GeneralReport> getSaleReportContent(Report report)
			throws AppException;

	public List<GeneralReport> getSaleOrderReportContent(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadSaleReport(Report report,
			List data) throws AppException;

	public ArrayList<ArrayList<Object>> downloadPolicySaleReport(Report report,
			List data) throws AppException;

	public List<GeneralReport> getRetireReportContent(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadRetireReport(Report report,
			List data) throws AppException;

}
