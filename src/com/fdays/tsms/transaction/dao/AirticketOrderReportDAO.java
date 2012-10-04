package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.transaction.AirticketOrderReport;
import com.neza.exception.AppException;

public interface AirticketOrderReportDAO {
	public List<AirticketOrderReport> getAirticketOrderReportList(Report report) throws AppException;
}
