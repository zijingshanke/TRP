package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Report;
import com.neza.exception.AppException;

public interface ReportBiz {

	public List<OptTransaction> listOptTransaction(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadOptTransactionReport(
			Report report) throws AppException;

	public ArrayList<ArrayList<Object>> downloadTeamSaleReport(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadTeamRakeOffReport(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadSaleReport(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadPolicySaleReport(Report report)
			throws AppException;

	public ArrayList<ArrayList<Object>> downloadRetireReport(Report report)
			throws AppException;

}
