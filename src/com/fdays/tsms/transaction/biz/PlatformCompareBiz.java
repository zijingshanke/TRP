package com.fdays.tsms.transaction.biz;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.neza.exception.AppException;

public interface PlatformCompareBiz {

	public HttpServletRequest insertPlatformReport(
			PlatformCompare platformCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException;

	public String comparePlatformReport(HttpServletRequest request)
			throws AppException;

	public List<PlatformCompare> getOrderCompareList(
			PlatformCompare platformCompare) throws AppException;

}
