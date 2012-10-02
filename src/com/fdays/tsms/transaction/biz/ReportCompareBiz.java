package com.fdays.tsms.transaction.biz;

import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.transaction.ReportRecode;
import com.neza.exception.AppException;

public interface ReportCompareBiz {
	public String comparePlatformSystem(ReportRecode reportRecode,
			HttpServletRequest request) throws AppException;

	public String comparePlatformPaytool(ReportRecode reportRecode,
			HttpServletRequest request) throws AppException;

	public String saveCompareResult(ReportRecode reportRecode,
			HttpServletRequest request) throws AppException;
}
