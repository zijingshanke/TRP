package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.base.Constant;
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
		ReportCompareResult compareResult = (ReportCompareResult) form;
		Inform inf = new Inform();
		try {
			long resultId = Constant.toLong(compareResult.getId());
			if (resultId > 0) {
				ReportCompareResult tempCompareResult = ReportCompareResultBiz
						.getReportCompareResultById(resultId);
				tempCompareResult.setPlatformId(compareResult.getPlatformId());
				tempCompareResult.setAccountId(compareResult.getAccountId());
				tempCompareResult.setPaymenttoolId(compareResult
						.getPaymenttoolId());
				tempCompareResult.setMemo(compareResult.getMemo());
				tempCompareResult.setType(compareResult.getType());
				// tempCompareResult.setCompareType(compareResult.getCompareType());
				tempCompareResult.setStatus(compareResult.getStatus());
				tempCompareResult.setLastDate(new Timestamp(System
						.currentTimeMillis()));
				tempCompareResult.setUserNo(uri.getUser().getUserNo());
				ReportCompareResultBiz.update(tempCompareResult);

				inf.setMessage("您已经成功更新了对比结果!");
				inf.setForwardPage("/transaction/reportCompareResultList.do");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("compareResultId 不存在");
				inf.setBack(true);
			}

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
		ReportCompareResult compareResult = (ReportCompareResult) form;
		Inform inf = new Inform();
		try {
			ReportCompareResult tempCompareResult = new ReportCompareResult();
			tempCompareResult.setPlatformId(compareResult.getPlatformId());
			tempCompareResult.setAccountId(compareResult.getAccountId());
			tempCompareResult
					.setPaymenttoolId(compareResult.getPaymenttoolId());
			tempCompareResult.setMemo(compareResult.getMemo());
			tempCompareResult.setType(compareResult.getType());
			// tempCompareResult.setCompareType(compareResult.getCompareType());
			// tempCompareResult.setStatus(ReportCompareResult.STATES_1);
			tempCompareResult.setLastDate(new Timestamp(System
					.currentTimeMillis()));
			tempCompareResult.setUserNo(uri.getUser().getUserNo());
			ReportCompareResultBiz.save(tempCompareResult);

			inf.setMessage("您已经成功增加了对比结果!");
			inf.setForwardPage("/transaction/reportCompareResultList.do");
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