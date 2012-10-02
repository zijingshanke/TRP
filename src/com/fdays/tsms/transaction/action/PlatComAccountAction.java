package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.right.UserRightInfo;
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

public class PlatComAccountAction extends BaseAction {
	private PlatComAccountBiz platComAccountBiz;
	private CompanyBiz companyBiz;
	private PlatformBiz platformBiz;
	private AccountBiz accountBiz;
	private SysInitBiz sysInitBiz;

	// 添加
	public ActionForward savePlatComAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		PlatComAccount platComAccount = (PlatComAccount) form;
		Inform inf = new Inform();
		try {
			if (platComAccount.getAccountId() > 0
					&& platComAccount.getPlatformId() > 0
					&& platComAccount.getCompanyId() > 0) {
				PlatComAccount pComAccount = new PlatComAccount();
				Account account = accountBiz.getAccountByid(platComAccount
						.getAccountId());
				Company company = companyBiz.getCompanyById(platComAccount
						.getCompanyId());
				Platform platform = platformBiz.getPlatformById(platComAccount
						.getPlatformId());

				pComAccount.setStatus(platComAccount.getStatus());
				pComAccount.setType(platComAccount.getType());
				pComAccount.setAccount(account);// 面象对象形式添加
				pComAccount.setCompany(company);
				pComAccount.setPlatform(platform);
				pComAccount.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				pComAccount.setUserName(uri.getUser().getUserName());
				long num = platComAccountBiz.save(pComAccount);
				// --更新静态库
				PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
						sysInitBiz, 6);
				MainTask.put(listener);
				// -------------------------
				if (num > 0) {
					return new ActionRedirect("/transaction/platComAccountList.do?thisAction=savePage");
				} else {
					inf.setMessage("您添加平台账号据失败！");
					inf.setBack(true);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 修改
	public ActionForward updatePlatComAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		PlatComAccount platComAccount = (PlatComAccount) form;
		Inform inf = new Inform();
		try {
			if (platComAccount.getId() > 0) {
				if (platComAccount.getAccountId() > 0
						&& platComAccount.getPlatformId() > 0
						&& platComAccount.getCompanyId() > 0) {
					PlatComAccount pComAccount = platComAccountBiz
							.getPlatComAccountById(platComAccount.getId());
					Account account = accountBiz.getAccountByid(platComAccount
							.getAccountId());
					Company company = companyBiz.getCompanyById(platComAccount
							.getCompanyId());
					Platform platform = platformBiz
							.getPlatformById(platComAccount.getPlatformId());
					pComAccount.setStatus(platComAccount.getStatus());
					pComAccount.setType(platComAccount.getType());

					pComAccount.setAccount(account);
					pComAccount.setCompany(company);
					pComAccount.setPlatform(platform);
					pComAccount.setUpdateDate(new Timestamp(System.currentTimeMillis()));
					pComAccount.setUserName(uri.getUser().getUserName());
					long flag = platComAccountBiz.update(pComAccount);
					// --更新静态库
					PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
							sysInitBiz, 6);
					MainTask.put(listener);
					//
					if (flag > 0) {
						return new ActionRedirect("/transaction/platComAccountList.do?thisAction=list");
					} else {
						inf.setMessage("您修改平台账号失败！");
						inf.setBack(true);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		String forwardPage = "inform";
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