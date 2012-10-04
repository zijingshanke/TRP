package com.fdays.tsms.transaction.biz;

import java.sql.Timestamp;
import java.util.List;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.ReportRecodeResultListForm;
import com.neza.exception.AppException;

public interface ReportRecodeResultBiz {

	public List list(ReportRecodeResultListForm rrrlf) throws AppException;

	public long save(ReportRecodeResult reportRecodeResult)
			throws AppException;

	public ReportRecodeResult queryById(long id) throws AppException;

	public long merge(ReportRecodeResult reportRecodeResult)
			throws AppException;

	public long update(ReportRecodeResult reportRecodeResult)
			throws AppException;

	public ReportRecodeResult getReportRecodeResultById(long id);

	public void deleteById(long id) throws AppException;

	public List list() throws AppException;
	
	public ReportRecodeResult getReportRecodeResultByName(String name) throws AppException;
	
	/**
	 * 获取指定日期和报表类型的ReportRecodeResult
	 * @param date
	 * @param type
	 * @return
	 * @throws AppException
	 */
	public ReportRecodeResult getReportRecodeResultByDateType(Timestamp date,
			long type) throws AppException;
	
	public List<ReportRecodeResult> getReportRecodeResultListByType(long type)throws AppException;

}
