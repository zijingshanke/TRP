package com.fdays.tsms.transaction.biz;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeListForm;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.neza.exception.AppException;

public interface ReportRecodeBiz {

	public String saveRecodeByResult(ReportRecodeResult reportRecodeResult,
			HttpServletRequest request) throws AppException;

	public List<ReportRecode> getReportRecodeListByResultId(long resultId)
			throws AppException;

	public List<ReportRecode> getReportRecodeListByCompareResultIdType(long resultId,
			long recodeType) throws AppException;

	public long save(ReportRecode reportRecode) throws AppException;

	public long merge(ReportRecode reportRecode) throws AppException;

	public long update(ReportRecode reportRecode) throws AppException;

	public void deleteById(long id) throws AppException;

	public void deleteAllByResultId(long reportRecodeResultId)
			throws AppException;

	public List list(ReportRecodeListForm rrlf) throws AppException;

	public ReportRecode getReportRecodeById(long id);

	public ReportRecode queryById(long id) throws AppException;
	
	/**
	 * 根据ReportRecodeResult查询ReportRecode的不重复的indexId集合
	 * @param reportRecodeResult
	 * @return
	 * @throws AppException
	 */
	public List<Long> getDistinctIndexId(ReportRecodeResult reportRecodeResult) throws  AppException;
	
	public int getRowCountByIndexId(ReportRecodeResult reportRecodeResult,long indexId) throws AppException;
	
	public List<ReportRecode> getReportRecodeByResultIndex(ReportRecodeResult reportRecodeResult,long indexId) throws AppException;

	/**
	 * 获取指定ReportRecodeResult和索引id的ReportRecode总金额
	 * @param reportRecodeResult
	 * @param indexId
	 * @return
	 * @throws AppException
	 */
	public BigDecimal getMoneyByResultInex(ReportRecodeResult reportRecodeResult,long indexId) throws AppException;
	
	/**
	 * 获取指定ReportRecodeResult的ReportRecode总金额
	 * @param reportRecodeResult
	 * @param indexId
	 * @return
	 * @throws AppException
	 */
	public BigDecimal getMoneyByResult(ReportRecodeResult reportRecodeResult) throws AppException;
}
