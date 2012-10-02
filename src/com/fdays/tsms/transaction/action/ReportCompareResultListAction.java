package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportCompareResultListForm;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.ReportCompareBiz;
import com.fdays.tsms.transaction.biz.ReportCompareResultBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ReportCompareResultListAction extends BaseAction {
	private PlatformBiz platformBiz;
	private ReportCompareBiz reportCompareBiz;
	private ReportCompareResultBiz reportCompareResultBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		ReportCompareResultListForm ulf = (ReportCompareResultListForm) form;
		int id = 0;
		if (ulf.getSelectedItems().length > 0) {
			id = ulf.getSelectedItems()[0];
		} else
			id = ulf.getId();
		if (id > 0) {
			ReportCompareResult reportCompareResult = (ReportCompareResult) reportCompareResultBiz
					.getReportCompareResultById(id);
			if (reportCompareResult == null) {
				System.out.println("ReportCompareResult==null");
			}
			reportCompareResult.setThisAction("update");
			request.setAttribute("reportCompareResult", reportCompareResult);

			// List<Platform> platformList = platformBiz.getValidPlatformList();
			List<Platform> platformList = PlatComAccountStore.getSalePlatform();
			request.setAttribute("platformList", platformList);

			List<Account> accountList = PlatComAccountStore.accountList;
			request.setAttribute("accountList", accountList);

			List<PaymentTool> paymentToolList = PlatComAccountStore.paymentToolList;
			request.setAttribute("paymentToolList", paymentToolList);
		} else{
			request.setAttribute("reportCompareResult",new ReportCompareResult());
		}
			
		forwardPage = "editReportCompareResult";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		ReportCompareResultListForm ulf = (ReportCompareResultListForm) form;
		int id = ulf.getId();
		if (id > 0) {
			ReportCompareResult reportCompareResult = (ReportCompareResult) reportCompareResultBiz
					.getReportCompareResultById(id);
			reportCompareResult.setThisAction("view");
			request.setAttribute("reportCompareResult", reportCompareResult);
			
			List<ReportCompare> problemCompareList1=reportCompareBiz.getCompareListByResultIdType(id,ReportCompare.RESULT_TYPE_1);
			request.setAttribute("problemCompareList1", problemCompareList1);		
			request.setAttribute("problemCompareList1Size", problemCompareList1.size());	
			List<ReportCompare> problemCompareList2=reportCompareBiz.getCompareListByResultIdType(id,ReportCompare.RESULT_TYPE_2);
			request.setAttribute("problemCompareList2", problemCompareList2);	
			request.setAttribute("problemCompareList2Size", problemCompareList2.size());	
			
			
			forwardPage = "viewReportCompareResult";
		} else {
			inf.setMessage("ID不能为空");
			inf.setBack(true);
			forwardPage = "inform";
		}
		
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ReportCompareResultListForm ulf = (ReportCompareResultListForm) form;
		if (ulf == null)
			ulf = new ReportCompareResultListForm();

		try {
			ulf.setStatus(ReportCompareResult.STATES_1);
			ulf.setList(reportCompareResultBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);

		// List<Platform> platformList = platformBiz.getValidPlatformList();
		List<Platform> platformList = PlatComAccountStore.getSalePlatform();
		request.setAttribute("platformList", platformList);

		List<Account> accountList = PlatComAccountStore.accountList;
		request.setAttribute("accountList", accountList);

		List<PaymentTool> paymentToolList = PlatComAccountStore.paymentToolList;
		request.setAttribute("paymentToolList", paymentToolList);

		forwardPage = "listReportCompareResult";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		ReportCompareResultListForm ulf = (ReportCompareResultListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < ulf.getSelectedItems().length; i++) {
				id = ulf.getSelectedItems()[i];
				if (id > 0)
					message += reportCompareResultBiz
							.deleteReportCompareResult(id);
				}
			if (message > 0) {
				inf.setMessage("您已经成功删除平台报表索引!");
			} else {
				inf.setMessage("删除失败!");
			}

			inf.setForwardPage("/transaction/reportCompareResultList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("listReportCompareResult");

		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setReportCompareResultBiz(
			ReportCompareResultBiz reportCompareResultBiz) {
		this.reportCompareResultBiz = reportCompareResultBiz;
	}

	public void setReportCompareBiz(ReportCompareBiz reportCompareBiz) {
		this.reportCompareBiz = reportCompareBiz;
	}


}
