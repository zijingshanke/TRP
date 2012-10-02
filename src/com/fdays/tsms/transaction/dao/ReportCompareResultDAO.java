package com.fdays.tsms.transaction.dao;

import java.sql.Timestamp;
import java.util.List;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportCompareResultListForm;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.neza.exception.AppException;

public interface ReportCompareResultDAO {
	public List list(ReportCompareResultListForm ulf) throws AppException;

	public long save(ReportCompareResult reportCompareResult)
			throws AppException;

	public ReportCompareResult queryById(long id) throws AppException;

	public long merge(ReportCompareResult reportCompareResult)
			throws AppException;

	public long update(ReportCompareResult reportCompareResult)
			throws AppException;

	public ReportCompareResult getReportCompareResultById(long id);

	public ReportCompareResult getLastSameReportCompareResult(Timestamp date,
			long compareType) throws AppException;

	public void deleteById(long id) throws AppException;

	public List list() throws AppException;

	public List getValidReportCompareResultList() throws AppException;

}
