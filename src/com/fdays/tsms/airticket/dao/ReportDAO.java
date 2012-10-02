package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Report;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ReportDAO extends BaseDAO {
	public List<AirticketOrder> getOrderList(Report report) throws AppException;
}
