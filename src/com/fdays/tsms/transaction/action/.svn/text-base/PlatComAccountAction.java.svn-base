package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.CompanyBiz;

import com.fdays.tsms.transaction.biz.PlatComAccountBiz;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatComAccountAction extends BaseAction{

	private PlatComAccountBiz platComAccountBiz;
	private CompanyBiz companyBiz;
	private PlatformBiz platformBiz;
	private AccountBiz accountBiz;
	private SysInitBiz sysInitBiz;

	//添加
	public ActionForward savePlatComAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PlatComAccount platComAccount = (PlatComAccount) form;
		Inform inf = new Inform();
		try {
			if(platComAccount.getAccountId()>0 && platComAccount.getPlatformId()>0 && platComAccount.getCompanyId()>0)
			{
				PlatComAccount pComAccount = new PlatComAccount();
				Account account = accountBiz.getAccountByid(platComAccount.getAccountId());
				Company company = companyBiz.getCompanyByid(platComAccount.getCompanyId());
				Platform platform = platformBiz.getPlatformByid(platComAccount.getPlatformId());
				
				pComAccount.setStatus(platComAccount.getStatus());
				pComAccount.setType(platComAccount.getType());
				pComAccount.setAccount(account);//面象对象形式添加
				pComAccount.setCompany(company);
				pComAccount.setPlatform(platform);
				long num =platComAccountBiz.save(pComAccount);
	           
				 if (num > 0) {
					inf.setMessage("您已经成功添加平台账号！");
					inf.setForwardPage("/transaction/platComAccountList.do");
					inf.setParamId("thisAction");
					inf.setParamValue("list");
				}else{
					inf.setMessage("您添加平台账号据失败！");
					inf.setBack(true);
				}	
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,6);
			MainTask.put(listener);
			//-------------------------
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改
	public ActionForward updatePlatComAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatComAccount platComAccount = (PlatComAccount) form;
		Inform inf = new Inform();
		try {
			if(platComAccount.getId()>0)
			{				
				if(platComAccount.getAccountId()>0 && platComAccount.getPlatformId()>0 && platComAccount.getCompanyId()>0)
				{
					PlatComAccount pComAccount = platComAccountBiz.getPlatComAccountById(platComAccount.getId());					
					Account account = accountBiz.getAccountByid(platComAccount.getAccountId());
					Company company = companyBiz.getCompanyByid(platComAccount.getCompanyId());
					Platform platform = platformBiz.getPlatformByid(platComAccount.getPlatformId());
					pComAccount.setStatus(platComAccount.getStatus());
					pComAccount.setType(platComAccount.getType());
					
					pComAccount.setAccount(account);
					pComAccount.setCompany(company);
					pComAccount.setPlatform(platform);
					long flag = platComAccountBiz.update(pComAccount);
					
					if (flag > 0) {
						inf.setMessage("您已经成功修改平台账号！");
						inf.setForwardPage("/transaction/platComAccountList.do");
						inf.setParamId("thisAction");
						inf.setParamValue("list");
					}else{
						inf.setMessage("您修改平台账号失败！");
						inf.setBack(true);
					}
				}
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,6);
			MainTask.put(listener);
			//
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	
	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPlatComAccountBiz(PlatComAccountBiz platComAccountBiz) {
		this.platComAccountBiz = platComAccountBiz;
	}

	public void setCompanyBiz(CompanyBiz companyBiz) {
		this.companyBiz = companyBiz;
	}


	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}
}