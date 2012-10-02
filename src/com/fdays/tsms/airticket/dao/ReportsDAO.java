package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ReportsDAO extends BaseDAO {
	public List marketReportsList(AirticketOrderListForm rlf)
			throws AppException;

	public List getGroupIdForSaleReport(AirticketOrderListForm rlf)
			throws AppException;

	public List getTeamAirTicketOrderList(AirticketOrderListForm rlf)
			throws AppException;

}
