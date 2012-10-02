package com.fdays.tsms.transaction.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.ReportCompareListForm;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.ReportCompareBiz;
import com.fdays.tsms.transaction.biz.ReportCompareResultBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ReportCompareListAction extends BaseAction {
	private AccountBiz accountBiz;	
	private PlatformBiz platformBiz;
	private ReportCompareBiz reportCompareBiz;
	private ReportCompareResultBiz reportCompareResultBiz;
	

	
	/**
	 * 进入平台报表对比
	*/
	public ActionForward redirectPlatformCompareManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {		
		ReportCompare platformCompare=(ReportCompare)request.getSession().getAttribute("tempCompare");
		
		if(platformCompare==null){
			platformCompare = new ReportCompare();			
		}		
		platformCompare.setThisAction("insertPlatformReport");
		
		request.getSession().setAttribute("tempCompare",platformCompare);
		
//		List<Platform> platformList = platformBiz.getValidPlatformList();
		List<Platform> platformList = PlatComAccountStore.getSalePlatform();		
		request.getSession().setAttribute("platformList", platformList);
		
		String forwardPage = "platformCompareManage";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 进入BSP报表对比
	*/
	public ActionForward redirectBSPCompareManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {		
		ReportCompare reportCompare=(ReportCompare)request.getSession().getAttribute("tempCompare");
		
		if(reportCompare==null){
			reportCompare = new ReportCompare();			
		}		
		reportCompare.setThisAction("insertBSPReport");
		
		request.getSession().setAttribute("tempCompare",reportCompare);
		
		String forwardPage = "bspCompareManage";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 进入网电报表对比
	*/
	public ActionForward redirectNetworkCompareManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {		
		ReportCompare reportCompare=(ReportCompare)request.getSession().getAttribute("tempCompare");
		
		if(reportCompare==null){
			reportCompare = new ReportCompare();			
		}		
		reportCompare.setThisAction("insertNetworkReport");
		
		request.getSession().setAttribute("tempCompare",reportCompare);
		
		String forwardPage = "networkCompareManage";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 进入银行（支付平台）报表对比
	*/
	public ActionForward redirectBankCompareManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {		
		ReportCompare reportCompare=(ReportCompare)request.getSession().getAttribute("tempCompare");
		
		if(reportCompare==null){
			reportCompare = new ReportCompare();			
		}		
		reportCompare.setThisAction("insertPlatformReport");
		
		request.getSession().setAttribute("tempCompare",reportCompare);
		
		List<Account> accountList =accountBiz.getValidAccountList();
		request.getSession().setAttribute("accountList", accountList);		
		
		String forwardPage = "bankCompareManage";
		return (mapping.findForward(forwardPage));
	}
	
	public ActionForward clearPlatformCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			reportCompareBiz.clearCompareSession(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirectPlatformCompareManage(mapping, form, request, response);
	}

	public ActionForward clearBSPCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			reportCompareBiz.clearCompareSession(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirectBSPCompareManage(mapping, form, request, response);
	}
	
	public ActionForward clearNetworkCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			reportCompareBiz.clearCompareSession(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirectNetworkCompareManage(mapping, form, request, response);
	}

	public ActionForward clearBankCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			reportCompareBiz.clearCompareSession(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return redirectBankCompareManage(mapping, form, request, response);
	}
	
	
	
	/**
	 * 删除对比结果
	*/
	public ActionForward deleteReportCompare(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {			
		ReportCompareListForm compareListForm=(ReportCompareListForm)form;
		if(compareListForm==null){
			compareListForm = new ReportCompareListForm();			
		}	
		String forwardPage = "";
		Inform inf = new Inform();		
		long compareResultId=Constant.toLong(compareListForm.getResultId());
		if(compareResultId>0){	
				int[] compareIds=compareListForm.getSelectedItems();
				if(compareIds==null||compareIds.length<1){
					compareIds=compareListForm.getSelectedItems2();
				}
				if(compareIds!=null){
					for (int i = 0; i < compareIds.length; i++) {
						int id = compareIds[i];		
						if(id>0){
							reportCompareBiz.deleteReportCompare(id);
							
							ActionRedirect redirect = new ActionRedirect("../transaction/reportCompareResultList.do");
							redirect.addParameter("thisAction","view");
							redirect.addParameter("id",compareResultId);
							return redirect;
						}else{
							inf.setMessage("请选择报表对比结果");
							inf.setBack(true);	
						}
					}			
				}else{
					inf.setMessage("selectedItems is null 请选择报表对比结果");
					inf.setBack(true);	
				}						
		}else{
			inf.setMessage("resultID为空");
			inf.setBack(true);			
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	public void setReportCompareBiz(ReportCompareBiz reportCompareBiz) {
		this.reportCompareBiz = reportCompareBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}
	public void setReportCompareResultBiz(
			ReportCompareResultBiz reportCompareResultBiz) {
		this.reportCompareResultBiz = reportCompareResultBiz;
	}
	
}
