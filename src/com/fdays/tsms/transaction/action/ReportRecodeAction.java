package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.biz.PlatformReportIndexBiz;
import com.fdays.tsms.transaction.biz.ReportCompareBiz;
import com.fdays.tsms.transaction.biz.ReportRecodeBiz;
import com.fdays.tsms.transaction.biz.ReportRecodeResultBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ReportRecodeAction extends BaseAction {
	private ReportRecodeBiz reportRecodeBiz;
	private ReportCompareBiz reportCompareBiz;
	private ReportRecodeResultBiz reportRecodeResultBiz;
	private PlatformReportIndexBiz platformReportIndexBiz;

	/**
	 * 平台-系统对比
	 */
	public ActionForward comparePlatformSystem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportRecode reportRecode = (ReportRecode) form;
		Inform inf = new Inform();
		try {
			reportCompareBiz.comparePlatformSystem(reportRecode, request);

			request.getSession().setAttribute("tempReportRecode", reportRecode);

			return (mapping.findForward("platformCompareManage"));
		} catch (Exception ex) {
			inf.setMessage("平台-系统对比出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 平台-支付工具对比
	 */
	public ActionForward comparePlatformPaytool(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportRecode reportRecode = (ReportRecode) form;
		Inform inf = new Inform();
		try {
			reportCompareBiz.comparePlatformPaytool(reportRecode, request);

			// request.getSession().setAttribute("tempReportRecode",
			// reportRecode);

			return (mapping.findForward("paytoolCompareManage"));
		} catch (Exception ex) {
			inf.setMessage("平台-系统对比出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 进入平台-系统对比
	 */
	public ActionForward redirectPlatformCompareManage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ReportRecode reportRecode = (ReportRecode) request.getSession()
				.getAttribute("tempReportRecode");

		if (reportRecode == null) {
			reportRecode = new ReportRecode();
		}
		reportRecode.setCompareType(ReportRecode.COMPARE_TYPE_1);
		request.getSession().setAttribute("tempReportRecode", reportRecode);
		
		
		String forwardPage = "platformCompareManage";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 进入平台支付工具报表对比
	 */
	public ActionForward redirectPaytoolCompareManage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ReportRecode reportRecode = (ReportRecode) request.getSession()
				.getAttribute("tempReportRecode");

		if (reportRecode == null) {
			reportRecode = new ReportRecode();
		}
		reportRecode.setCompareType(ReportRecode.COMPARE_TYPE_2);
		
		request.getSession().setAttribute("tempReportRecode", reportRecode);
		String forwardPage = "paytoolCompareManage";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 保存报表对比结果集
	 */
	public ActionForward saveCompareResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws AppException {
		ReportRecode reportRecode = (ReportRecode) form;
		String result=reportCompareBiz.saveCompareResult(reportRecode, request);		
		long resultId=Constant.toLong(result);
		return redirectReportCompareResultList(resultId);
	}

	/**
	 * 增加ReportRecode
	 * @throws AppException
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ReportRecode reportRecode = (ReportRecode) form;
		Inform inf = new Inform();
		try {
			ReportRecode tempReportRecode = new ReportRecode();
			tempReportRecode.setPayToolId(reportRecode.getPayToolId());
			tempReportRecode.setPlatformId(reportRecode.getPlatformId());
			tempReportRecode.setSubPnr(reportRecode.getSubPnr());
			tempReportRecode.setAirOrderNo(reportRecode.getAirOrderNo());
			tempReportRecode.setAmount(reportRecode.getAmount());
			tempReportRecode.setStatementType(reportRecode.getStatementType());
			tempReportRecode.setAccountId(reportRecode.getAccountId());
			tempReportRecode.setAccountName(reportRecode.getAccountName());
			tempReportRecode
					.setPassengerCount(reportRecode.getPassengerCount());
			tempReportRecode.setReportDate(reportRecode.getReportDate());
			tempReportRecode.setReportRownum(reportRecode.getReportRownum());
			tempReportRecode.setStatus(reportRecode.getStatus());
			tempReportRecode.setType(reportRecode.getType());
			tempReportRecode.setMemo(reportRecode.getMemo());

			reportRecodeBiz.save(tempReportRecode);
			inf.setMessage("您已经成功增加!");
			inf.setForwardPage("/transaction/reportRecodeList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ReportRecode reportRecode = (ReportRecode) form;
		Inform inf = new Inform();
		try {
			long resultId = reportRecode.getId();
			if (resultId > 0) {
				ReportRecode tempReportRecode = reportRecodeBiz
						.getReportRecodeById(resultId);
				tempReportRecode.setPayToolId(reportRecode.getPayToolId());
				tempReportRecode.setPlatformId(reportRecode.getPlatformId());
				tempReportRecode.setSubPnr(reportRecode.getSubPnr());
				tempReportRecode.setAirOrderNo(reportRecode.getAirOrderNo());
				tempReportRecode.setAmount(reportRecode.getAmount());
				tempReportRecode.setStatementType(reportRecode
						.getStatementType());
				tempReportRecode.setAccountId(reportRecode.getAccountId());
				tempReportRecode.setAccountName(reportRecode.getAccountName());
				tempReportRecode.setPassengerCount(reportRecode
						.getPassengerCount());
				tempReportRecode.setReportDate(reportRecode.getReportDate());
				tempReportRecode
						.setReportRownum(reportRecode.getReportRownum());
				tempReportRecode.setStatus(reportRecode.getStatus());
				tempReportRecode.setType(reportRecode.getType());
				tempReportRecode.setMemo(reportRecode.getMemo());
				reportRecodeBiz.update(tempReportRecode);

				inf.setMessage("您已经成功更新!");
				inf.setForwardPage("/transaction/reportRecodeList.do");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("reportRecodeId 不存在");
				inf.setBack(true);
			}

		} catch (Exception ex) {
			inf.setMessage("更新出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 根据日期查询平台报表和工具报表
	 * @throws AppException
	 */
	public ActionForward getReportRecodeByDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "paytoolCompareManage";
		ReportRecode reportRecode = (ReportRecode) form;
		Timestamp date = reportRecode.getTempReportDate();
		reportRecode.setReportDate(date);
		if (date == null) {
			return (mapping.findForward(forwardPage));
		} else {
			ReportRecodeResult platformResult = reportRecodeResultBiz
					.getReportRecodeResultByDateType(date,
							ReportRecodeResult.REPORTTYPE_1);
			ReportRecodeResult toolResult = reportRecodeResultBiz
					.getReportRecodeResultByDateType(date,
							ReportRecodeResult.REPORTTYPE_2);

			if (platformResult != null) {
				reportRecode.setPlatformRecodeResultId(platformResult.getId());
				reportRecode.setPlatformRecodeResultName(platformResult
						.getName());
			} else {
				reportRecode.setPlatformRecodeResultId(0l);
			}
			if (toolResult != null) {
				reportRecode.setPaytoolRecodeResultId(toolResult.getId());
				reportRecode.setPaytoolRecodeResultName(toolResult.getName());
			} else {
				reportRecode.setPaytoolRecodeResultId(0l);
			}
			request.getSession().setAttribute("tempReportRecode", reportRecode);

			if (platformResult == null && toolResult == null) {
				request.setAttribute("exception", "noInfo");
			} else if (platformResult == null) {
				request.setAttribute("exception", "noPlatformResult");
			} else if (toolResult == null) {
				request.setAttribute("exception", "noToolResult");
			}
		}
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 根据日期查询平台报表
	 * @throws AppException
	 */
	public ActionForward getPlatformReportRecodeByDate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "platformCompareManage";
		ReportRecode reportRecode = (ReportRecode) form;
		Timestamp date = reportRecode.getTempReportDate();
		reportRecode.setReportDate(date);
		if (date == null) {
			return (mapping.findForward(forwardPage));
		} else {
			ReportRecodeResult platformResult = reportRecodeResultBiz
					.getReportRecodeResultByDateType(date,
							ReportRecodeResult.REPORTTYPE_1);
			if(platformResult != null){
				reportRecode.setPlatformRecodeResultId(platformResult.getId());
				reportRecode.setPlatformRecodeResultName(platformResult
						.getName());
				List<Long> indexIdList = reportRecodeBiz.getDistinctIndexId(platformResult);
				List<Platform>  platformList = new ArrayList<Platform>();
				for(int i=0;i<indexIdList.size();i++){
					Long indexId = indexIdList.get(i);
					if(indexId != null){
						Platform pf = new Platform();
						PlatformReportIndex pri = platformReportIndexBiz.getPlatformReportIndexById(indexId);
						pf.setId(pri.getPlatformId());
						pf.setNameTranType(pri.getName()+"-"+pri.getTranTypeInfo());
						platformList.add(pf);
					}
				}
				request.setAttribute("platformList",platformList);
			}else{
				reportRecode.setPlatformRecodeResultId(0l);
				request.setAttribute("exception", "noPlatformResult");
			}
			request.getSession().setAttribute("tempReportRecode", reportRecode);
			
		}
		return (mapping.findForward(forwardPage));
	}
	
	public ActionRedirect redirectViewResult(long resultId){
		ActionRedirect redirect = new ActionRedirect("/transaction/reportRecodeResultList.do");
		redirect.addParameter("thisAction", "view");
		redirect.addParameter("id",resultId);
		return redirect;
	}
	
	public ActionRedirect redirectReportCompareResultList(long resultId){
		ActionRedirect redirect = new ActionRedirect("/transaction/reportCompareResultList.do");
		redirect.addParameter("thisAction", "view");
		redirect.addParameter("id",resultId);
		return redirect;
	}

	public void setReportRecodeBiz(ReportRecodeBiz reportRecodeBiz) {
		this.reportRecodeBiz = reportRecodeBiz;
	}

	public void setReportCompareBiz(ReportCompareBiz reportCompareBiz) {
		this.reportCompareBiz = reportCompareBiz;
	}

	public void setReportRecodeResultBiz(
			ReportRecodeResultBiz reportRecodeResultBiz) {
		this.reportRecodeResultBiz = reportRecodeResultBiz;
	}

	public void setPlatformReportIndexBiz(
			PlatformReportIndexBiz platformReportIndexBiz) {
		this.platformReportIndexBiz = platformReportIndexBiz;
	}
	

}