package com.fdays.tsms.transaction.biz;

import java.sql.Timestamp;
import java.util.List;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportCompareResultListForm;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.dao.ReportCompareResultDAO;
import com.neza.exception.AppException;

public class ReportCompareResultBizImp implements ReportCompareResultBiz {
	private ReportCompareResultDAO reportCompareResultDAO;

	public long save(ReportCompareResult reportCompareResult)
			throws AppException {
		return reportCompareResultDAO.save(reportCompareResult);
	}

	public long merge(ReportCompareResult reportCompareResult)
			throws AppException {
		return reportCompareResultDAO.merge(reportCompareResult);
	}

	public long update(ReportCompareResult reportCompareResult)
			throws AppException {
		return reportCompareResultDAO.update(reportCompareResult);
	}

	public void deleteById(long id) throws AppException {
		reportCompareResultDAO.deleteById(id);
	}

	public long deleteReportCompareResult(long id) throws AppException {
		ReportCompareResult tempAirline = reportCompareResultDAO
				.getReportCompareResultById(id);
		if (tempAirline == null) {
			return 0;
		} else {
			tempAirline.setStatus(ReportCompareResult.STATES_0);
			reportCompareResultDAO.update(tempAirline);
			return 1;
		}
	}

	public ReportCompareResult getReportCompareResultById(long id) {
		return reportCompareResultDAO.getReportCompareResultById(id);
	}

	public ReportCompareResult queryById(long id) throws AppException {
		return reportCompareResultDAO.queryById(id);
	}

	public List list() throws AppException {
		return reportCompareResultDAO.list();
	}

	public List getValidReportCompareResultList() throws AppException {
		return reportCompareResultDAO.getValidReportCompareResultList();
	}

	public List list(ReportCompareResultListForm ulf) throws AppException {
		return reportCompareResultDAO.list(ulf);
	}

	public ReportCompareResult getLastSameReportCompareResult(Timestamp date,
			long compareType) throws AppException {
		return reportCompareResultDAO.getLastSameReportCompareResult(date,
				compareType);
	}

	public void setReportCompareResultDAO(
			ReportCompareResultDAO reportCompareResultDAO) {
		this.reportCompareResultDAO = reportCompareResultDAO;
	}

}
