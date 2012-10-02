package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.biz.ReportCompareResultBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ReportCompareResultAction extends BaseAction {
	private ReportCompareResultBiz ReportCompareResultBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		ReportCompareResult reportIndex = (ReportCompareResult) form;
		Inform inf = new Inform();
		try {
			ReportCompareResult tempReportIndex = (ReportCompareResult) ReportCompareResultBiz
					.getReportCompareResultById(reportIndex.getId());
			tempReportIndex.setPlatformId(reportIndex.getPlatformId());
			tempReportIndex.setAccountId(reportIndex.getAccountId());
			tempReportIndex.setPaymenttoolId(reportIndex.getPaymenttoolId());			
			tempReportIndex.setMemo(reportIndex.getMemo());
			tempReportIndex.setType(reportIndex.getType());
//			tempReportIndex.setCompareType(reportIndex.getCompareType());			
			tempReportIndex.setStatus(reportIndex.getStatus());
			tempReportIndex.setLastDate(new Timestamp(System
					.currentTimeMillis()));
			tempReportIndex.setUserNo(uri.getUser().getUserNo());
			ReportCompareResultBiz.update(tempReportIndex);

			inf.setMessage("您已经成功更新了对比结果！");
			inf.setForwardPage("/transaction/ReportCompareResultList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

		} catch (Exception ex) {
			inf.setMessage("更新对比结果出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		ReportCompareResult reportIndex = (ReportCompareResult) form;
		Inform inf = new Inform();
		try {
			ReportCompareResult tempReportIndex = new ReportCompareResult();
			tempReportIndex.setPlatformId(reportIndex.getPlatformId());
			tempReportIndex.setAccountId(reportIndex.getAccountId());
			tempReportIndex.setPaymenttoolId(reportIndex.getPaymenttoolId());
			tempReportIndex.setMemo(reportIndex.getMemo());
			tempReportIndex.setType(reportIndex.getType());
//			tempReportIndex.setCompareType(reportIndex.getCompareType());			
//			tempReportIndex.setStatus(ReportCompareResult.STATES_1);
			tempReportIndex.setLastDate(new Timestamp(System
					.currentTimeMillis()));
			tempReportIndex.setUserNo(uri.getUser().getUserNo());
			ReportCompareResultBiz.save(tempReportIndex);
			
			inf.setMessage("您已经成功增加了对比结果！");
			inf.setForwardPage("/transaction/ReportCompareResultList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加对比结果出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setReportCompareResultBiz(
			ReportCompareResultBiz ReportCompareResultBiz) {
		this.ReportCompareResultBiz = ReportCompareResultBiz;
	}
}