package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformCompareListForm;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.PlatformCompareBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformCompareListAction extends BaseAction {
	private PlatformBiz platformBiz;
	private PlatformCompareBiz platformCompareBiz;
	
	//导入交易平台报表
	public ActionForward addReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformCompare platformCompare = new PlatformCompare();
		platformCompare.setThisAction("insertReport");
		platformCompare.setStatus(PlatformCompare.STATES_1);
		request.setAttribute("platformCompare", platformCompare);		
		
		List<Platform> platformList = platformBiz.getValidPlatformList();
		request.setAttribute("platformList", platformList);
		
		String forwardPage = "importPlatformReport";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		PlatformCompareListForm ulf = (PlatformCompareListForm) form;
		int id = 0;
		if (ulf.getSelectedItems().length > 0) {
			id = ulf.getSelectedItems()[0];
		} else
			id = ulf.getId();
		if (id > 0) {
			System.out.println("Id:" + id);
			PlatformCompare platformCompare = (PlatformCompare) platformCompareBiz
					.getPlatformCompareById(id);
			if (platformCompare == null) {
				System.out.println("platformCompare==null");
			}
			platformCompare.setThisAction("update");
			request.setAttribute("platformCompare", platformCompare);
			
			List<Platform> platformList = platformBiz.getValidPlatformList();
			request.setAttribute("platformList", platformList);
		} else
			request.setAttribute("platformCompare",
					new PlatformCompare());
		forwardPage = "edit";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PlatformCompareListForm ulf = (PlatformCompareListForm) form;
		int id = ulf.getId();
		if (id > 0) {
			PlatformCompare platformCompare = (PlatformCompare) platformCompareBiz
					.getPlatformCompareById(id);
			platformCompare.setThisAction("view");
			request.setAttribute("platformCompare", platformCompare);
		} else {
			forwardPage = "login";
		}

		forwardPage = "view";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformCompare platformCompare = new PlatformCompare();
		platformCompare.setThisAction("insert");
		platformCompare.setStatus(PlatformCompare.STATES_1);
		request.setAttribute("platformCompare", platformCompare);		
		
		List<Platform> platformList = platformBiz.getValidPlatformList();
		request.setAttribute("platformList", platformList);
		
		String forwardPage = "edit";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PlatformCompareListForm ulf = (PlatformCompareListForm) form;
		if (ulf == null)
			ulf = new PlatformCompareListForm();

		try {
			ulf.setStatus(PlatformCompare.STATES_1);
			ulf.setList(platformCompareBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		
		List<Platform> platformList = platformBiz.getValidPlatformList();
		request.setAttribute("platformList", platformList);
		
		forwardPage = "list";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformCompareListForm ulf = (PlatformCompareListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < ulf.getSelectedItems().length; i++) {
				id = ulf.getSelectedItems()[i];
				if (id > 0)
					message += platformCompareBiz
							.deletePlatformCompare(id);
			}

			if (message > 0) {
				inf.setMessage("您已经成功删除平台报表索引!");
			} else {
				inf.setMessage("删除失败!");
			}

			inf.setForwardPage("/transaction/platformCompareList.do");
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
	
	

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPlatformCompareBiz(PlatformCompareBiz platformCompareBiz) {
		this.platformCompareBiz = platformCompareBiz;
	}


}
