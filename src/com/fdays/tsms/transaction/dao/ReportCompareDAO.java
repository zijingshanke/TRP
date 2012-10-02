package com.fdays.tsms.transaction.dao;

import java.util.List;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.ReportCompareListForm;
import com.neza.exception.AppException;

public interface ReportCompareDAO {
	public List<ReportCompare> listCompareOrder(String platformId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType) throws AppException;

	public List<ReportCompare> listCompareOrder(String accountId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType, String statementSubType)
			throws AppException;

	public long save(ReportCompare compare) throws AppException;

	public long merge(ReportCompare compare) throws AppException;

	public long update(ReportCompare compare) throws AppException;

	public List<ReportCompare> getCompareListByResultId(long resultId)
			throws AppException;

	public List<ReportCompare> getCompareListByResultIdType(long resultId,
			long type) throws AppException;

	public List list() throws AppException;

	public List getValidReportCompareList() throws AppException;

	public List list(ReportCompareListForm ulf) throws AppException;
}