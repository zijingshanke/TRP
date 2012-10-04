package com.fdays.tsms.transaction.dao;

import java.math.BigDecimal;
import java.util.List;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeListForm;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.neza.exception.AppException;

public interface ReportRecodeDAO {
	public List<ReportRecode> getReportRecodeListByResultId(long resultId)
			throws AppException;

	public long save(ReportRecode reportRecode) throws AppException;

	public long merge(ReportRecode reportRecode) throws AppException;

	public long update(ReportRecode reportRecode) throws AppException;

	public List list(ReportRecodeListForm rrlf) throws AppException;

	public void deleteById(long id) throws AppException;

	public void deleteAllByResultId(long id) throws AppException;

	public ReportRecode getReportRecodeById(long id);

	public List<ReportRecode> getReportRecodeListByCompareResultIdType(long resultId,
			long recodeType) throws AppException;

	public ReportRecode queryById(long id) throws AppException;
	
	/**
	 * 根据ReportRecodeResult查询ReportRecode的不重复的indexId集合
	 * @param reportRecodeResult
	 * @return
	 * @throws AppException
	 */
	public List<Long> getDistinctIndexId(ReportRecodeResult reportRecodeResult) throws  AppException;
	
	public int getRowCountByIndexId(ReportRecodeResult reportRecodeResult,
			long indexId) throws AppException;
	
	public List<ReportRecode> getReportRecodeByResultIndex(ReportRecodeResult reportRecodeResult,
			long indexId) throws AppException;
	
	/**
	 * 获取指定ReportRecodeResult和索引id的ReportRecode总金额
	 * @param reportRecodeResult
	 * @param indexId
	 * @return
	 * @throws AppException
	 */
	public BigDecimal getMoneyByResultInex(ReportRecodeResult reportRecodeResult,long indexId) throws AppException;
	
	/**
	 * 获取指定ReportRecodeResult下ReportRecode的总金额
	 * @param reportRecodeResult
	 * @return
	 * @throws AppException
	 */
	public BigDecimal getMoneyByResult(ReportRecodeResult reportRecodeResult) throws AppException;

}