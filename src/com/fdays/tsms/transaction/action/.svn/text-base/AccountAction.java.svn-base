package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AccountAction extends BaseAction{

	AccountBiz accountBiz;
	PaymentToolBiz paymentToolBiz;
	private SysInitBiz sysInitBiz;
	
	public SysInitBiz getSysInitBiz() {
		return sysInitBiz;
	}

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}

	public PaymentToolBiz getPaymentToolBiz() {
		return paymentToolBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}

	public AccountBiz getAccountBiz() {
		return accountBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}
	
	//添加
	public ActionForward saveAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Account account = (Account) form;
		Inform inf = new Inform();
		try {
			long paymentToolId=account.getPaymentToolId();
			if(paymentToolId>0)
			{
				Account acc = new Account();
				long type=1;
				PaymentTool paymentTool = paymentToolBiz.getPaymentToolByid(paymentToolId);
				acc.setName(account.getName());
				acc.setAccountNo(account.getAccountNo());
				acc.setType(type);
				acc.setStatus(account.getStatus());
				acc.setPaymentTool(paymentTool);				
				long num =accountBiz.save(acc);
           
			 if (num > 0) {
					inf.setMessage("您已经成功添加支付账号！");
					inf.setForwardPage("/transaction/accountList.do");
					inf.setParamId("thisAction");
					inf.setParamValue("list");
				}else{
					inf.setMessage("您添加支付账号失败！");
					inf.setBack(true);
				}	
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,4);
			MainTask.put(listener);
			//---------
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改
	public ActionForward updateAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Account account = (Account)form;
		Inform inf = new Inform();
		try {
			if(account.getId()>0)
			{
				long paymentToolId = account.getPaymentToolId();
				if(paymentToolId>0)
				{
					PaymentTool paymentTool =paymentToolBiz.getPaymentToolByid(paymentToolId);
					Account acc = accountBiz.getAccountByid(account.getId());
					acc.setName(account.getName());
					acc.setAccountNo(account.getAccountNo());
					acc.setType(account.getType());
					acc.setStatus(account.getStatus());
					acc.setPaymentTool(paymentTool);
					long flag =accountBiz.update(acc);
					
					if (flag > 0) {
						inf.setMessage("您已经成功修改支付账号！");
						inf.setForwardPage("/transaction/accountList.do");
						inf.setParamId("thisAction");
						inf.setParamValue("list");
					}else{
						inf.setMessage("您改支付账号失败！");
						inf.setBack(true);
					}
				}
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,4);
			MainTask.put(listener);
			//---------
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

}