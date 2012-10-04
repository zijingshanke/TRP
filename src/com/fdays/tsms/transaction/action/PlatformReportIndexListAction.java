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
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.PlatformReportIndexListForm;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.PlatformReportIndexBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformReportIndexListAction extends BaseAction {
	private PlatformBiz platformBiz;
	private PlatformReportIndexBiz platformReportIndexBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		PlatformReportIndexListForm ulf = (PlatformReportIndexListForm) form;
		int id = 0;
		if (ulf.getSelectedItems().length > 0) {
			id = ulf.getSelectedItems()[0];
		} else
			id = ulf.getId();
		if (id > 0) {
			PlatformReportIndex platformReportIndex = (PlatformReportIndex) platformReportIndexBiz
					.getPlatformReportIndexById(id);
			if (platformReportIndex == null) {
				System.out.println("platformReportIndex==null");
			}
			platformReportIndex.setThisAction("update");
			request.setAttribute("platformReportIndex", platformReportIndex);

			List<Platform> platformList = platformBiz.getValidPlatformList();
//			List<Platform> platformList =PlatComAccountStore.getSalePlatform();
			request.setAttribute("platformList", platformList);
			
			List<Account> accountList =PlatComAccountStore.accountList;
			request.setAttribute("accountList", accountList);	
			
			List<PaymentTool> paymentToolList =PlatComAccountStore.paymentToolList;
			request.setAttribute("paymentToolList", paymentToolList);			
		} else
			request.setAttribute("platformReportIndex",
					new PlatformReportIndex());
		forwardPage = "edit";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PlatformReportIndexListForm ulf = (PlatformReportIndexListForm) form;
		int id = ulf.getId();
		if (id > 0) {
			PlatformReportIndex platformReportIndex = (PlatformReportIndex) platformReportIndexBiz
					.getPlatformReportIndexById(id);
			platformReportIndex.setThisAction("view");
			request.setAttribute("platformReportIndex", platformReportIndex);
		} else {
			forwardPage = "login";
		}

		forwardPage = "view";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformReportIndex platformReportIndex = new PlatformReportIndex();
		platformReportIndex.setThisAction("insert");
		platformReportIndex.setStatus(PlatformReportIndex.STATES_1);
		request.setAttribute("platformReportIndex", platformReportIndex);

		List<Platform> platformList = platformBiz.getValidPlatformList();
//		List<Platform> platformList =PlatComAccountStore.getSalePlatform();		
		request.setAttribute("platformList", platformList);
		
		List<Account> accountList =PlatComAccountStore.accountList;
		request.setAttribute("accountList", accountList);	
		
		List<PaymentTool> paymentToolList =PlatComAccountStore.paymentToolList;
		request.setAttribute("paymentToolList", paymentToolList);

		String forwardPage = "edit";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PlatformReportIndexListForm ulf = (PlatformReportIndexListForm) form;
		if (ulf == null)
			ulf = new PlatformReportIndexListForm();

		try {
			ulf.setStatus(PlatformReportIndex.STATES_1);
			ulf.setList(platformReportIndexBiz.list(ulf));			
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		
//		List<Platform> platformList = platformBiz.getValidPlatformList();
		List<Platform> platformList =PlatComAccountStore.getSalePlatform();		
		request.setAttribute("platformList", platformList);
		
		List<Account> accountList =PlatComAccountStore.accountList;
		request.setAttribute("accountList", accountList);	
		
		List<PaymentTool> paymentToolList =PlatComAccountStore.paymentToolList;
		request.setAttribute("paymentToolList", paymentToolList);
		
		forwardPage = "list";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformReportIndexListForm ulf = (PlatformReportIndexListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < ulf.getSelectedItems().length; i++) {
				id = ulf.getSelectedItems()[i];
				if (id > 0)
					message += platformReportIndexBiz
							.deletePlatformReportIndex(id);
			}

			if (message > 0) {
				inf.setMessage("您已经成功删除平台报表索引!");
			} else {
				inf.setMessage("删除失败!");
			}

			inf.setForwardPage("/transaction/platformReportIndexList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	public void setPlatformReportIndexBiz(
			PlatformReportIndexBiz platformReportIndexBiz) {
		this.platformReportIndexBiz = platformReportIndexBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}
}
