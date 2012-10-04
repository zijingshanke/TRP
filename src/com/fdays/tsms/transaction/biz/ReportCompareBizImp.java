package com.fdays.tsms.transaction.biz;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.dao.ReportCompareResultDAO;
import com.fdays.tsms.transaction.dao.ReportRecodeDAO;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class ReportCompareBizImp implements ReportCompareBiz {
	private ReportCompareResultDAO reportCompareResultDAO;
	private ReportRecodeDAO reportRecodeDAO;

	// 平台-支付工具对比
	public String comparePlatformPaytool(ReportRecode reportRecodeForm,
			HttpServletRequest request) throws AppException {
		long platformRecodeResultId = Constant.toLong(reportRecodeForm
				.getPlatformRecodeResultId());
		long paytoolRecodeResultId = Constant.toLong(reportRecodeForm
				.getPaytoolRecodeResultId());

		List<ReportRecode> platformRecodeList = reportRecodeDAO
				.getReportRecodeListByResultId(platformRecodeResultId);

		List<ReportRecode> paytoolRecodeList = reportRecodeDAO
				.getReportRecodeListByResultId(paytoolRecodeResultId);

		ReportRecode tempReportRecode = (ReportRecode) request.getSession()
				.getAttribute("tempReportRecode");

		long compareStandard = Constant.toLong(reportRecodeForm
				.getCompareStandard());
		List<ReportRecode> problemCompareList1 = new ArrayList<ReportRecode>();
		List<ReportRecode> problemCompareList2 = new ArrayList<ReportRecode>();

		if (compareStandard > 0) {
			if (compareStandard == 1) {
				problemCompareList1 = getCompareResult(new Long(1),
						platformRecodeList, paytoolRecodeList, reportRecodeForm);
			}
			if (compareStandard == 2) {
				problemCompareList2 = getCompareResult(new Long(2),
						platformRecodeList, paytoolRecodeList, reportRecodeForm);
			}
		} else {
			problemCompareList1 = getCompareResult(new Long(1),
					platformRecodeList, paytoolRecodeList, reportRecodeForm);
			problemCompareList2 = getCompareResult(new Long(2),
					platformRecodeList, paytoolRecodeList, reportRecodeForm);
		}

		request.getSession().setAttribute("problemCompareList1",
				problemCompareList1);// -对账只存在于平台
		request.getSession().setAttribute("problemCompareList1Size",
				problemCompareList1.size());

		request.getSession().setAttribute("problemCompareList2",
				problemCompareList2);// -对账只存在于支付工具/系统
		request.getSession().setAttribute("problemCompareList2Size",
				problemCompareList2.size());

		tempReportRecode.setCompareStandard(compareStandard);
		tempReportRecode.setCompareCondition(reportRecodeForm
				.getCompareCondition());
		request.getSession().setAttribute("tempReportRecode", tempReportRecode);
		return "";
	}

	/**
	 * 获取对比结果
	 * 
	 * @param type
	 *            1:-对账只存在于本系统 2:-对账只存在于上传文件
	 * 
	 * @List<ReportRecode> platformRecodeList 平台
	 * @List<ReportRecode> paytoolRecodeList 支付工具
	 * @ReportRecode reportRecode 对比表单条件
	 * 
	 */
	private List<ReportRecode> getCompareResult(long type,
			List<ReportRecode> platformRecodeList,
			List<ReportRecode> paytoolRecodeList, ReportRecode reportRecodeForm)
			throws AppException {
		List<ReportRecode> problemCompareList = new ArrayList<ReportRecode>();
		if (platformRecodeList != null && paytoolRecodeList != null) {
			if (Constant.toLong(type) == 1) {
				for (int i = 0; i < paytoolRecodeList.size(); i++) {
					ReportRecode compare = paytoolRecodeList.get(i);
					boolean flag = false;
					for (int j = 0; j < platformRecodeList.size(); j++) {
						ReportRecode order = platformRecodeList.get(j);
						flag = ReportRecode.comparePlatformReport(
								reportRecodeForm, compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于平台-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr());
						problemCompareList.add(compare);
					}
				}
			} else if (Constant.toLong(type) == 2) {
				for (int i = 0; i < platformRecodeList.size(); i++) {
					ReportRecode compare = platformRecodeList.get(i);
					boolean flag = false;
					for (int j = 0; j < paytoolRecodeList.size(); j++) {
						ReportRecode order = paytoolRecodeList.get(j);
						flag = ReportRecode.comparePlatformReport(
								reportRecodeForm, compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于系统/支付工具-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr());
						problemCompareList.add(compare);
					}
				}
			}
		}
		// problemCompareList = sortReportCompareList(problemCompareList);

		return problemCompareList;
	}

	// 平台-系统对比
	public String comparePlatformSystem(ReportRecode reportRecode,
			HttpServletRequest request) throws AppException {

		return "";
	}

	/**
	 * 保存报表对比结果集
	 */
	public String saveCompareResult(ReportRecode reportRecode,
			HttpServletRequest request) throws AppException {
		String result = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String userNo = uri.getUser().getUserNo();

		long reportCompareResultId = Constant.toLong(reportRecode
				.getReportCompareResultId());
		ReportCompareResult reportCompareResult = null;
		if (reportCompareResultId > 0) {
			// ---
		} else {
			reportCompareResultId = saveCompareResultByReportRecode(
					reportRecode, userNo);
		}
		reportCompareResult = reportCompareResultDAO
				.queryById(reportCompareResultId);
		if (reportCompareResult != null) {
			long compareType = Constant.toLong(reportRecode.getCompareType());

			List<ReportRecode> problemList1 = (List<ReportRecode>) request
					.getSession().getAttribute("problemCompareList1");// 对账只存在于平台
			List<ReportRecode> problemList2 = (List<ReportRecode>) request
					.getSession().getAttribute("problemCompareList2");// 对账只存在于系统/支付工具

			if (compareType == ReportRecode.COMPARE_TYPE_1) {
				saveReportCompareList(problemList1, reportCompareResult,
						ReportRecode.RECODE_TYPE_1);
				saveReportCompareList(problemList2, reportCompareResult,
						ReportRecode.RECODE_TYPE_2);
			}

			if (compareType == ReportRecode.COMPARE_TYPE_2) {
				saveReportCompareList(problemList1, reportCompareResult,
						ReportRecode.RECODE_TYPE_11);
				saveReportCompareList(problemList2, reportCompareResult,
						ReportRecode.RECODE_TYPE_12);
			}
		}
		result = reportCompareResultId + "";
		return result;
	}

	public long saveCompareResultByReportRecode(ReportRecode reportRecode,
			String userNo) throws AppException {
		ReportCompareResult compareResult = new ReportCompareResult();
		Timestamp reportDate = reportRecode.getReportDate();
		String reportDateStr = DateUtil.getDateString(reportDate, "yyyy-MM-dd");
		
		String name = reportDateStr + reportRecode.getCompareTypeInfo()
				+ "对比结果";
		ReportCompareResult tempResult=reportCompareResultDAO.getLastSameReportCompareResult(reportDate,
				reportRecode.getCompareType());
		if(tempResult!=null){
			String tempName=tempResult.getName();
			int beginIndex="(".indexOf(tempName);
			int endIndex=")".indexOf(tempName);
			int resultIndex=0;
			if(beginIndex>0&&endIndex>0){				
				resultIndex=Constant.toInt(tempName.substring(beginIndex, endIndex));
				resultIndex+=1;
			}else{
				resultIndex=2;
			}
			name+="("+resultIndex+")";
		}else{
			name+="(1)";
		}
		
		compareResult.setPlatformId(reportRecode.getPlatformId());
		// compareResult.setPaymenttoolId(reportCompare.getPaymenttoolId());
		compareResult.setAccountId(reportRecode.getAccountId());
		compareResult.setBeginDate(DateUtil.getTimestamp(reportDateStr
				+ " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		compareResult.setEndDate(DateUtil.getTimestamp(reportDateStr
				+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
		String memo=reportRecode.getCompareStandardInfo()+",对比条件:"+reportRecode.getCompareConditionInfo();
		compareResult.setMemo(memo);
		compareResult.setUserNo(userNo);
		compareResult.setLastDate(new Timestamp(System.currentTimeMillis()));
		compareResult.setCompareType(reportRecode.getCompareType());
		// compareResult.setType(type);
		compareResult.setStatus(ReportCompareResult.STATES_1);
		compareResult.setName(name);
		long flag = reportCompareResultDAO.save(compareResult);
		
		return flag;
	}

	public void saveReportCompareList(List<ReportRecode> reportRecodeList,
			ReportCompareResult reportCompareResult, Long resultType)
			throws AppException {
		if (reportCompareResult != null && reportCompareResult.getId() > 0) {
			if (reportRecodeList != null) {
				for (int i = 0; i < reportRecodeList.size(); i++) {
					ReportRecode tempCompare = reportRecodeList.get(i);
					if (tempCompare != null) {
						ReportRecode newCompare = new ReportRecode();
						newCompare = (ReportRecode) tempCompare.clone();
						newCompare.setReportCompareResult(reportCompareResult);
						newCompare.setStatus(ReportRecode.STATUS_1);
						newCompare.setType(resultType);
						reportRecodeDAO.save(newCompare);
					} else {
						System.out
								.println("saveReportCompareList tempCompare is null.....");
					}
				}
			} else {
				System.out
						.println("saveReportCompareList reportCompareList is null.....");
			}
		} else {
			System.out
					.println("saveReportCompareList reportCompareResult is null.....");
		}
	}

	public void setReportCompareResultDAO(
			ReportCompareResultDAO reportCompareResultDAO) {
		this.reportCompareResultDAO = reportCompareResultDAO;
	}

	public void setReportRecodeDAO(ReportRecodeDAO reportRecodeDAO) {
		this.reportRecodeDAO = reportRecodeDAO;
	}

}
