package com.fdays.tsms.transaction.biz;

import java.util.List;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportCompareResultListForm;
import com.neza.exception.AppException;

public interface ReportCompareResultBiz {

	public List list(ReportCompareResultListForm ulf) throws AppException;

	public long save(ReportCompareResult ReportCompareResult)
			throws AppException;

	public ReportCompareResult queryById(long id) throws AppException;

	public long merge(ReportCompareResult ReportCompareResult)
			throws AppException;

	public long update(ReportCompareResult ReportCompareResult)
			throws AppException;

	public ReportCompareResult getReportCompareResultById(long id);

	public void deleteById(long id) throws AppException;

	public long deleteReportCompareResult(long id) throws AppException;

	public List list() throws AppException;

	public List getValidReportCompareResultList() throws AppException;

}
