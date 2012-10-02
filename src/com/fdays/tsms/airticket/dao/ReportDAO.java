package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ReportDAO extends BaseDAO {
	public List marketReportsList(AirticketOrderListForm rlf)
			throws AppException;

	public List getGroupIdForSaleReport(AirticketOrderListForm rlf)
			throws AppException;

	public List getGroupIdForRetireReport(AirticketOrderListForm rlf)
			throws AppException;

	public List getGroupIdForTeamSaleReport(AirticketOrderListForm rlf)
			throws AppException;

}
