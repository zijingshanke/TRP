package com.fdays.tsms.transaction.dao;

import java.util.List;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.ReportCompareListForm;
import com.neza.exception.AppException;

public interface ReportCompareDAO {
	public List<ReportCompare> listCompareOrder(String platformId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType) throws AppException;

	public List<ReportCompare> listCompareOrderByAccount(String accountId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType, String statementSubType)
			throws AppException;

	public long save(ReportCompare compare) throws AppException;

	public long merge(ReportCompare compare) throws AppException;

	public long update(ReportCompare compare) throws AppException;

	public List<ReportCompare> getCompareListByResultId(long resultId)
			throws AppException;

	public List<ReportCompare> getCompareListByResultIdType(long resultId,
			String type) throws AppException;

	public List getValidReportCompareList() throws AppException;

	public List list(ReportCompareListForm ulf) throws AppException;

	public void deleteById(long id) throws AppException;

	public ReportCompare getReportCompareById(long id);

	public ReportCompare queryById(long id) throws AppException;
}