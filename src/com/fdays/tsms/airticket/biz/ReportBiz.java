package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.exception.AppException;

public interface ReportBiz {
	public List marketReportsList(AirticketOrderListForm rlf)
			throws AppException;

	public ArrayList<ArrayList<Object>> getMarketReportsList(
			AirticketOrderListForm alf) throws AppException;

	public ArrayList<ArrayList<Object>> downloadTeamSaleReport(
			AirticketOrderListForm rlf) throws AppException;

	public ArrayList<ArrayList<Object>> downloadTeamRakeOffReport(
			AirticketOrderListForm rlf) throws AppException;

	public ArrayList<ArrayList<Object>> downloadSaleReport(
			AirticketOrderListForm alf) throws AppException;

	public ArrayList<ArrayList<Object>> downloadRetireReport(
			AirticketOrderListForm alf) throws AppException;

}
