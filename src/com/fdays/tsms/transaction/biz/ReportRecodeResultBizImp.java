package com.fdays.tsms.transaction.biz;

import java.sql.Timestamp;
import java.util.List;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.ReportRecodeResultListForm;
import com.fdays.tsms.transaction.dao.ReportRecodeResultDAO;
import com.neza.exception.AppException;

public class ReportRecodeResultBizImp implements ReportRecodeResultBiz {
	private ReportRecodeResultDAO reportRecodeResultDAO;

	public long save(ReportRecodeResult reportRecodeResult)
			throws AppException {
		return reportRecodeResultDAO.save(reportRecodeResult);
	}

	public long merge(ReportRecodeResult reportRecodeResult)
			throws AppException {
		return reportRecodeResultDAO.merge(reportRecodeResult);
	}

	public long update(ReportRecodeResult reportRecodeResult)
			throws AppException {
		return reportRecodeResultDAO.update(reportRecodeResult);
	}

	public void deleteById(long id) throws AppException {
		reportRecodeResultDAO.deleteById(id);
	}

	public ReportRecodeResult getReportRecodeResultById(long id) {
		return reportRecodeResultDAO.getReportRecodeResultById(id);
	}

	public ReportRecodeResult queryById(long id) throws AppException {
		return reportRecodeResultDAO.queryById(id);
	}

	public List list() throws AppException {
		return reportRecodeResultDAO.list();
	}
	public List list(ReportRecodeResultListForm rrrlf) throws AppException {
		return reportRecodeResultDAO.list(rrrlf);
	}

	public void setReportRecodeResultDAO(ReportRecodeResultDAO reportRecodeResultDAO) {
		this.reportRecodeResultDAO = reportRecodeResultDAO;
	}

	public ReportRecodeResult getReportRecodeResultByName(String name)
			throws AppException {
		
		return this.reportRecodeResultDAO.getReportRecodeResultByName(name);
	}

	public List<ReportRecodeResult> getReportRecodeResultListByType(long type)
			throws AppException {
		return reportRecodeResultDAO.getReportRecodeResultListByType(type);
	}

	public ReportRecodeResult getReportRecodeResultByDateType(Timestamp date,
			long type) throws AppException {
		return reportRecodeResultDAO.getReportRecodeResultByDateType(date, type);
	}

}
