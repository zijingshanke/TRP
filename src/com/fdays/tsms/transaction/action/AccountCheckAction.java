package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountCheck;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.AccountCheckBiz;
import com.fdays.tsms.user.SysUser;
import com.fdays.tsms.user.biz.UserBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AccountCheckAction extends BaseAction {
	private AccountBiz accountBiz;
	private AccountCheckBiz accountCheckBiz;
	private UserBiz userBiz;

	public ActionForward checkOn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AccountCheck accountCheckform = (AccountCheck) form;
		Inform inf = new Inform();

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		long userId = uri.getUser().getUserId();
		try {
			long accountId = accountCheckform.getAccountId();

			if (accountId > 0 && userId > 0) {
				AccountCheck accountCheck = new AccountCheck();
				accountCheck.setCheckOnAmount(accountCheckform.getCheckOnAmount());
//				accountCheck.setTransInAmount(accountCheckform.getTransInAmount());
//				accountCheck.setTransOutAmount(accountCheckform.getTransOutAmount());
//				accountCheck.setTransOutAmount(accountCheckform.getTransOutAmount());
//				accountCheck.setPayAmount(accountCheckform.getPayAmount());
//				accountCheck.setRefundAmount(accountCheckform.getRefundAmount());
//				accountCheck.setCheckOffAmount(accountCheckform.getCheckOffAmount());

				accountCheck.setCheckOnDate(new Timestamp(System.currentTimeMillis()));
				accountCheck.setNote(accountCheckform.getNote());
				accountCheck.setType(AccountCheck.TYPE_0);
				accountCheck.setStatus(AccountCheck.STATES_0);

				SysUser user = userBiz.getUserById(userId);
				accountCheck.setSysUser(user);
				Account account = accountBiz.getAccountById(accountId);
				accountCheck.setAccount(account);

				long num = accountCheckBiz.save(accountCheck);

				if (num > 0) {
					return new ActionRedirect(
							"/transaction/accountCheckList.do?thisAction=list");
				} else {
					inf.setMessage("登记异常");
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
	
	public ActionForward checkOff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountCheck accountCheckform = (AccountCheck) form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		long userId = uri.getUser().getUserId();
		long accountCheckId = accountCheckform.getId();

		if (accountCheckId>0 && userId > 0) {
			try {
				AccountCheck accountCheck = accountCheckBiz.getAccountCheckById(accountCheckId);
				accountCheck.setCheckOnAmount(accountCheckform.getCheckOnAmount());
				accountCheck.setTransInAmount(accountCheckform.getTransInAmount());
				accountCheck.setTransOutAmount(accountCheckform.getTransOutAmount());
				accountCheck.setTransOutAmount(accountCheckform.getTransOutAmount());
				accountCheck.setPayAmount(accountCheckform.getPayAmount());
				accountCheck.setRefundAmount(accountCheckform.getRefundAmount());
				accountCheck.setCheckOffAmount(accountCheckform.getCheckOffAmount());

				accountCheck.setCheckOffDate(new Timestamp(System.currentTimeMillis()));
				
				accountCheck.setNote(accountCheckform.getNote());
				accountCheck.setType(AccountCheck.TYPE_1);
				accountCheck.setStatus(AccountCheck.STATES_0);

				SysUser user = userBiz.getUserById(userId);
				accountCheck.setSysUser(user);
				long num = accountCheckBiz.update(accountCheck);

				if (num > 0) {
					return new ActionRedirect(
							"/transaction/accountCheckList.do?thisAction=list");
				} else {
					inf.setMessage("登记异常");
					inf.setBack(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				inf.setMessage("登记异常");
				inf.setBack(true);
			}
		}

		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountCheck accountCheckform = (AccountCheck) form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		long userId = uri.getUser().getUserId();

		long accountId = accountCheckform.getAccountId();
		long accountCheckId = accountCheckform.getId();

		if (accountCheckId>0&&accountId > 0 && userId > 0) {
			try {
				AccountCheck accountCheck = accountCheckBiz.getAccountCheckById(accountCheckId);
				accountCheck.setCheckOnAmount(accountCheckform.getCheckOnAmount());
				accountCheck.setTransInAmount(accountCheckform.getTransInAmount());
				accountCheck.setTransOutAmount(accountCheckform.getTransOutAmount());
				accountCheck.setTransOutAmount(accountCheckform.getTransOutAmount());
				accountCheck.setPayAmount(accountCheckform.getPayAmount());
				accountCheck.setRefundAmount(accountCheckform.getRefundAmount());
				accountCheck.setCheckOffAmount(accountCheckform.getCheckOffAmount());
				accountCheck.setNote(accountCheckform.getNote());
				accountCheck.setType(accountCheckform.getType());
				accountCheck.setStatus(AccountCheck.STATES_0);

				SysUser user = userBiz.getUserById(userId);
				accountCheck.setSysUser(user);
				
				Account account = accountBiz.getAccountById(accountId);
				accountCheck.setAccount(account);

				long num = accountCheckBiz.update(accountCheck);

				if (num > 0) {
					return new ActionRedirect(
							"/transaction/accountCheckList.do?thisAction=list");
				} else {
					inf.setMessage("登记异常");
					inf.setBack(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
				inf.setBack(true);
			}
		}

		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	
	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setAccountCheckBiz(AccountCheckBiz accountCheckBiz) {
		this.accountCheckBiz = accountCheckBiz;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

}