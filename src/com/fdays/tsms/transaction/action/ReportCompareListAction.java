package com.fdays.tsms.transaction.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class ReportCompareListAction extends BaseAction {
	private AccountBiz accountBiz;	
	private PlatformBiz platformBiz;
	
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

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}
}
