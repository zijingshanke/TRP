package com.fdays.tsms.transaction.biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportCompareListForm;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.neza.exception.AppException;

public interface ReportCompareBiz {

	public String saveCompareResult(ReportCompare reportCompare,
			HttpServletRequest request) throws AppException;

	public HttpServletRequest insertPlatformReport(
			ReportCompare platformCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException;

	public String comparePlatformReport(HttpServletRequest request)
			throws AppException;

	public List<ReportCompare> getPlatformOrderCompareList(
			ReportCompare platformCompare) throws AppException;

	public HttpServletRequest insertBSPReport(ReportCompare platformCompare,
			PlatformReportIndex reportIndex, HttpServletRequest request)
			throws AppException;

	public String compareBSPReport(HttpServletRequest request)
			throws AppException;

	public List<ReportCompare> getBSPOrderCompareList(
			ReportCompare platformCompare) throws AppException;

	public HttpServletRequest insertNetworkReport(
			ReportCompare platformCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException;

	public String compareNetworkReport(HttpServletRequest request)
			throws AppException;

	public List<ReportCompare> getNetworkOrderCompareList(
			ReportCompare platformCompare) throws AppException;

	public HttpServletRequest insertBankReport(ReportCompare platformCompare,
			PlatformReportIndex reportIndex, HttpServletRequest request)
			throws AppException;

	public String compareBankReport(HttpServletRequest request)
			throws AppException;

	public List<ReportCompare> getBankOrderCompareList(
			ReportCompare platformCompare) throws AppException;

	public long save(ReportCompare compare) throws AppException;

	public long merge(ReportCompare compare) throws AppException;

	public long update(ReportCompare compare) throws AppException;

	public void deleteReportCompare(int id) throws AppException;

	public List<ReportCompare> getCompareListByResultId(long resultId)
			throws AppException;

	public List<ReportCompare> getCompareListByResultIdType(long resultId,
			String type) throws AppException;

	public void clearReportCompareResultProblemList(
			ReportCompareResult reportCompareResult) throws AppException;

	public void clearCompareSession(HttpServletRequest request)
			throws AppException;

	public List<ReportCompare> sortReportCompareList(List<ReportCompare> compareList);

	public List getValidReportCompareList() throws AppException;

	public List list(ReportCompareListForm ulf) throws AppException;

	public ReportCompare getReportCompareById(long id);

	public ReportCompare queryById(long id) throws AppException;

}
