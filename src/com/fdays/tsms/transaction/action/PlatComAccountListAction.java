package com.fdays.tsms.transaction.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountListForm;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.CompanyBiz;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.fdays.tsms.transaction.biz.PlatComAccountBiz;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatComAccountListAction extends BaseAction{

	PlatComAccountBiz platComAccountBiz;
	CompanyBiz companyBiz;
	PlatformBiz platformBiz;
	AccountBiz accountBiz;
	PaymentToolBiz paymentToolBiz;
	
	public CompanyBiz getCompanyBiz() {
		return companyBiz;
	}

	public void setCompanyBiz(CompanyBiz companyBiz) {
		this.companyBiz = companyBiz;
	}

	public PlatformBiz getPlatformBiz() {
		return platformBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public AccountBiz getAccountBiz() {
		return accountBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}
	
	
	//测试dwr
	public ActionForward testDWR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		request.setAttribute("platformList", PlatComAccountStore.platFormList);
		return mapping.findForward("testDWR");
		
	}

	//分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatComAccountListForm platComAccountListForm = (PlatComAccountListForm)form;
		request.setAttribute("companyList", PlatComAccountStore.companyList);
		request.setAttribute("platformList", PlatComAccountStore.platFormList);
		request.setAttribute("accountList", PlatComAccountStore.accountList);
		if(platComAccountListForm==null)
		{
			platComAccountListForm=new PlatComAccountListForm();
		}
		try {
			platComAccountListForm.setList(platComAccountBiz.list(platComAccountListForm));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.setAttribute("platComAccountListForm", platComAccountListForm);
		return mapping.findForward("listPlatComAccount");	
	}
	
	//跳转添加页面
	public ActionForward savePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatComAccount platComAccount = new PlatComAccount();
		try {
			//List<PaymentTool> paymentToolList=paymentToolBiz.getPaymentToolList();//支付工具表
			//List<Platform> platformList =platformBiz.getPlatformList();//交易平台表
			//List<Company> companyList =companyBiz.getCompanyList();//公司表
			request.setAttribute("companyList", PlatComAccountStore.companyList);
			request.setAttribute("platformList", PlatComAccountStore.platFormList);
			request.setAttribute("accountList", PlatComAccountStore.accountList);
			//request.setAttribute("paymentToolList", paymentToolList);
			platComAccount.setThisAction("savePlatComAccount");
			request.setAttribute("platComAccount", platComAccount);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}		
		String forwardPage = "editPlatComAccount";
		return mapping.findForward(forwardPage);
	}
	
	//跳转修改页面
	public ActionForward updatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatComAccountListForm platComAccountListForm = (PlatComAccountListForm)form;	
		
		long platComAccountId=platComAccountListForm.getSelectedItems()[0];
		if(platComAccountId>0)
		{
			PlatComAccount platComAccount = platComAccountBiz.getPlatComAccountById(platComAccountId);
			platComAccount.setThisAction("updatePlatComAccount");
			platComAccount.setCompanyId(platComAccount.getCompany().getId());//修改时返回下拉框原数据
			platComAccount.setPlatformId(platComAccount.getPlatform().getId());
			platComAccount.setAccountId(platComAccount.getAccount().getId());
			request.setAttribute("companyList", PlatComAccountStore.companyList);
			request.setAttribute("platformList", PlatComAccountStore.platFormList);
			request.setAttribute("accountList", PlatComAccountStore.accountList);
			request.setAttribute("platComAccount", platComAccount);
		}else
		{
			request.setAttribute("platComAccount", new PlatComAccount());
		}
		return mapping.findForward("editPlatComAccount");
	}
	//删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatComAccountListForm platComAccountListForm = (PlatComAccountListForm)form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();		
		int message = 0;
		try {
			for (int i = 0; i < platComAccountListForm.getSelectedItems().length; i++) {
				id = platComAccountListForm.getSelectedItems()[i];				
				PlatComAccount platComAccount=null;
				int b=0;
				if (id > 0)
					platComAccount = platComAccountBiz.getPlatComAccountById(id);//查询子表中是否有数据
					message += platComAccountBiz.delete(id);//根据id删除
					if (message > 0) {
						inf.setMessage("您已经成功删除该平台账号表!");
					} else {
						inf.setMessage("删除失败!");
					}
				}	
			inf.setForwardPage("/transaction/platComAccountList.do");
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


	public PlatComAccountBiz getPlatComAccountBiz() {
		return platComAccountBiz;
	}

	public void setPlatComAccountBiz(PlatComAccountBiz platComAccountBiz) {
		this.platComAccountBiz = platComAccountBiz;
	}

	public PaymentToolBiz getPaymentToolBiz() {
		return paymentToolBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}
}