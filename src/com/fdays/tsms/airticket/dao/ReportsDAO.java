package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ReportsDAO extends BaseDAO {
	public List marketReportsList(AirticketOrderListForm rlf)
			throws AppException;

	public List saleReportsByGroupMarkNoList(AirticketOrderListForm rlf)
			throws AppException;


	// 团队销售报表
	public List getTeamAirTicketOrderList(AirticketOrderListForm rlf)
			throws AppException;

}
