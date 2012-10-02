package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountListForm;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class AccountListAction extends BaseAction {
	private AccountBiz accountBiz;
	private PaymentToolBiz paymentToolBiz;

	// 分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountListForm accountListForm = (AccountListForm) form;
		List<PaymentTool> paymentToolList = paymentToolBiz.getPaymentToolList();// PlatComAccountStore.paymentToolList
		request.setAttribute("paymentToolList", paymentToolList);
		
		if (accountListForm == null) {
			accountListForm = new AccountListForm();
		}
		try {
			accountListForm.setList(accountBiz.list(accountListForm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("accountListForm", accountListForm);
		return mapping.findForward("listAccount");
	}

	// 账号余额
	public ActionForward listAccountBanlance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AccountListForm accountListForm = (AccountListForm) form;
		if (accountListForm == null) {
			accountListForm = new AccountListForm();
		}
		try {
			accountListForm.setList(accountBiz.list(accountListForm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		accountListForm.addSumField(1, "totalAmount");

		forwardPage = "listAccountBalance";
		request.setAttribute("accountListForm", accountListForm);
		return mapping.findForward(forwardPage);
	}

	// 导出
	public ActionForward downloadAccountBalance(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AccountListForm alf = (AccountListForm) form;
		if (alf != null) {

			ArrayList<ArrayList<Object>> lists = accountBiz
					.getAccountBalanceList(alf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("UTF-8"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("listAccountBalance");
		}
	}

	// 显示详细信息
	public ActionForward viewAccountPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		try {
			String accountId = request.getParameter("accountId");
			if (accountId != null && (!accountId.equals(""))) {
				Account account = accountBiz.getAccountById(Long
						.parseLong(accountId));
				request.setAttribute("account", account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "viewAccount";
		return mapping.findForward(forwardPage);

	}

	// 跳转添加页面
	public ActionForward savePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Account account = new Account();
		List<PaymentTool> paymentToolList = paymentToolBiz.getPaymentToolList();// PlatComAccountStore.paymentToolList
		request.setAttribute("paymentToolList", paymentToolList);
		
		account.setThisAction("saveAccount");
		request.setAttribute("account", account);
		String forwardPage = "editAccount";
		return mapping.findForward(forwardPage);
	}

	// 跳转修改页面
	public ActionForward updatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountListForm accountListForm = (AccountListForm) form;
		
		List<PaymentTool> paymentToolList = paymentToolBiz.getPaymentToolList();// PlatComAccountStore.paymentToolList
		request.setAttribute("paymentToolList", paymentToolList);
		
		long accountId = accountListForm.getSelectedItems()[0];
		if (accountId > 0) {
			Account account = accountBiz.getAccountById(accountId);
			account.setThisAction("updateAccount");
			account.setPaymentToolId(account.getPaymentTool().getId());
			request.setAttribute("account", account);
		} else {
			request.setAttribute("account", new Account());
		}
		return mapping.findForward("editAccount");
	}

	// 删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountListForm accountListForm = (AccountListForm) form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < accountListForm.getSelectedItems().length; i++) {
				id = accountListForm.getSelectedItems()[i];
				Account account = null;
				int b = 0;
				if (id > 0)
					account = accountBiz.getAccountById(id);// 查询子表中是否有数据
				if (account != null) {
					b = account.getPlatComAccounts().size();
				}
				if (b <= 0)// 没有数据
				{
					message += accountBiz.delete(id);// 根据id删除
					if (message > 0) {
						inf.setMessage("您已经成功删除该支付账号!");
					} else {
						inf.setMessage("删除失败!");
					}
				} else {
					inf.setMessage("不能删除,删除失败!");
				}
			}
			return new ActionRedirect(
					"/transaction/accountList.do?thisAction=list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

}