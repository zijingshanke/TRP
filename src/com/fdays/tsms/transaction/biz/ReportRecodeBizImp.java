package com.fdays.tsms.transaction.biz;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeListForm;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.dao.PlatformReportIndexDAO;
import com.fdays.tsms.transaction.dao.ReportRecodeDAO;
import com.fdays.tsms.transaction.dao.ReportRecodeResultDAO;
import com.fdays.tsms.transaction.util.ReportRecodeImportUtil;
import com.neza.exception.AppException;

public class ReportRecodeBizImp implements ReportRecodeBiz {
	private ReportRecodeDAO reportRecodeDAO;
	private ReportRecodeResultDAO reportRecodeResultDAO;	
	private PlatformReportIndexDAO platformReportIndexDAO;

	// 导入报表
	public long saveRecodeByResult(ReportRecodeResult reportRecodeResult,
			HttpServletRequest request) throws AppException {
		List<ReportRecode> recodeList = new ArrayList<ReportRecode>();
		String reportFilePath = Constant.PROJECT_PLATFORMREPORTS_PATH
				+ File.separator + reportRecodeResult.getFileName();
		String requestURL = Constant.toString(request.getRequestURL()
				.toString());
		if (requestURL.indexOf("tsms.fdays.com") < 0) {
			reportFilePath = "F:" + File.separator + reportFilePath;// 开发环境
		}

		File reportFile = new File(reportFilePath);

		long reportType = Constant.toLong(reportRecodeResult.getReportType());

		String reportName="";
		if (reportType == ReportRecodeResult.REPORTTYPE_1) {
			long platformId = Constant.toLong(reportRecodeResult
					.getPlatformId());

			PlatformReportIndex reportIndex = platformReportIndexDAO
					.getReportIndexByPlatformIdType(platformId,
							reportRecodeResult.getTranType());

			if (reportIndex != null) {
				recodeList = ReportRecodeImportUtil.getReportRecodeAsPlatform(
						reportFile, reportIndex);
				reportName=reportIndex.getName();
			}
		} else if (reportType == ReportRecodeResult.REPORTTYPE_2) {
			long reportIndexId = Constant.toLong(reportRecodeResult
					.getReportIndexId());
			long paytoolId = Constant.toLong(reportRecodeResult.getPaytoolId());
			PlatformReportIndex reportIndex = null;
			// reportIndex = platformReportIndexDAO
			// .getReportIndexByPaymentTool(paytoolId);

			reportIndex = platformReportIndexDAO
					.getPlatformReportIndexById(reportIndexId);

			if (reportIndex != null) {
				recodeList = ReportRecodeImportUtil.getReportRecodeAsPaytool(
						reportFile, reportIndex);
				reportName=reportIndex.getName();
			}
		
		}
		System.out.println("保存条数："+recodeList.size()+"  reportRecodeResultID:"+reportRecodeResult.getId());
		saveReportRecodeList(recodeList, reportRecodeResult);
		
		String recodeSet=Constant.toString(reportRecodeResult.getRecodeSet());
		
		if(!StringUtil.containsExistString(reportName, recodeSet)){
			recodeSet=StringUtil.appendString(recodeSet, reportName, ",");
		}		
		reportRecodeResult.setRecodeSet(recodeSet);
		reportRecodeResultDAO.update(reportRecodeResult);
		return 0;
	}

	public void saveReportRecodeList(List<ReportRecode> recodeList,
			ReportRecodeResult reportRecodeResult) throws AppException {
		try {
			for (int i = 0; i < recodeList.size(); i++) {
				ReportRecode reportRecode = recodeList.get(i);
				reportRecode.setReportRecodeResult(reportRecodeResult);
				save(reportRecode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<ReportRecode> getReportRecodeListByResultId(long resultId)
			throws AppException {
		return reportRecodeDAO.getReportRecodeListByResultId(resultId);
	}

	public void deleteById(long id) throws AppException {
		reportRecodeDAO.deleteById(id);
	}

	public void deleteAllByResultId(long reportRecodeResultId)
			throws AppException {
		reportRecodeDAO.deleteAllByResultId(reportRecodeResultId);
	}

	public List list(ReportRecodeListForm rrlf) throws AppException {
		return null;
	}

	public long merge(ReportRecode reportRecode) throws AppException {
		return reportRecodeDAO.merge(reportRecode);
	}

	public long save(ReportRecode reportRecode) throws AppException {
		return reportRecodeDAO.save(reportRecode);
	}

	public long update(ReportRecode reportRecode) throws AppException {
		return reportRecodeDAO.update(reportRecode);
	}

	public ReportRecode getReportRecodeById(long id) {
		return reportRecodeDAO.getReportRecodeById(id);
	}

	public ReportRecode queryById(long id) throws AppException {
		return reportRecodeDAO.queryById(id);
	}

	public List<ReportRecode> getReportRecodeListByCompareResultIdType(long resultId,
			long recodeType) throws AppException {
		return reportRecodeDAO.getReportRecodeListByCompareResultIdType(resultId,
				recodeType);
	}

	public void setReportRecodeDAO(ReportRecodeDAO reportRecodeDAO) {
		this.reportRecodeDAO = reportRecodeDAO;
	}

	public void setPlatformReportIndexDAO(
			PlatformReportIndexDAO platformReportIndexDAO) {
		this.platformReportIndexDAO = platformReportIndexDAO;
	}

	public void setReportRecodeResultDAO(ReportRecodeResultDAO reportRecodeResultDAO) {
		this.reportRecodeResultDAO = reportRecodeResultDAO;
	}

	public List<Long> getDistinctIndexId(ReportRecodeResult reportRecodeResult)
			throws AppException {
		return this.reportRecodeDAO.getDistinctIndexId(reportRecodeResult);
	}

	public int getRowCountByIndexId(ReportRecodeResult reportRecodeResult,
			long indexId) throws AppException {
		
		return reportRecodeDAO.getRowCountByIndexId(reportRecodeResult, indexId);
	}

	public List<ReportRecode> getReportRecodeByResultIndex(
			ReportRecodeResult reportRecodeResult, long indexId)
			throws AppException {
		return this.reportRecodeDAO.getReportRecodeByResultIndex(reportRecodeResult, indexId);
	}
}
