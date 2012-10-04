package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountCheck;
import com.fdays.tsms.transaction.AccountCheckListForm;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.AccountCheckBiz;
import com.fdays.tsms.user.biz.UserBiz;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class AccountCheckListAction extends BaseAction {
	private AccountBiz accountBiz;
	private AccountCheckBiz accountCheckBiz;
	private UserBiz userBiz;

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		AccountCheckListForm accountCheckListForm = (AccountCheckListForm) form;
		List<Account> accountList = accountBiz.getValidAccountList();
		request.setAttribute("accountList", accountList);

		if (accountCheckListForm == null) {
			accountCheckListForm = new AccountCheckListForm();
		}
		try {
			accountCheckListForm.setList(accountCheckBiz
					.list(accountCheckListForm,uri));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("aclf", accountCheckListForm);
		return mapping.findForward("list");
	}

	// 导出
	public ActionForward downloadAccountCheck(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		AccountCheckListForm alf = (AccountCheckListForm) form;
		if (alf != null) {

			ArrayList<ArrayList<Object>> lists = accountCheckBiz
					.getAccountCheckList(alf,uri);
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
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AccountCheck accountCheckForm = (AccountCheck) form;
		try {
			long id = accountCheckForm.getId();
			if (id > 0) {
				AccountCheck accountCheck = accountCheckBiz
						.getAccountCheckById(id);
				request.setAttribute("accountCheck", accountCheck);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "view";
		return mapping.findForward(forwardPage);

	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountCheck accountCheck = new AccountCheck();

		accountCheck.setThisAction("checkOn");
		request.setAttribute("accountCheck", accountCheck);
		
		List<Account> accountList=accountBiz.getValidAccountListByTranType(Account.tran_type_1+","+Account.tran_type_3);
		request.setAttribute("accountList", accountList);
				
		String forwardPage = "edit";
		return mapping.findForward(forwardPage);
	}

	//编辑
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountCheckListForm accountListForm = (AccountCheckListForm) form;

		long id = accountListForm.getSelectedItems()[0];
		if (id > 0) {
			AccountCheck accountCheck = accountCheckBiz.getAccountCheckById(id);
			accountCheck.setThisAction("update");
			request.setAttribute("accountCheck", accountCheck);
			
			List<Account> accountList=accountBiz.getValidAccountListByTranType(Account.tran_type_1+","+Account.tran_type_3);
			request.setAttribute("accountList", accountList);
		} else {
			request.setAttribute("accountCheck", new AccountCheck());
		}
		return mapping.findForward("edit");
	}
	
	// 下班
	public ActionForward checkOff(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountCheckListForm accountCheckListForm = (AccountCheckListForm) form;

		long id = accountCheckListForm.getId();
		if (id > 0) {
			AccountCheck accountCheck = accountCheckBiz.getAccountCheckById(id);
			accountCheck.setThisAction("checkOff");
			request.setAttribute("accountCheck", accountCheck);
		} else {
			request.setAttribute("accountCheck", new AccountCheck());
		}
		return mapping.findForward("edit");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AccountCheckListForm accountCheckListForm = (AccountCheckListForm) form;
		Inform inf = new Inform();
	
		long accountCheckId = accountCheckListForm.getSelectedItems()[0];

		if (accountCheckId > 0) {
			try {
				AccountCheck accountCheck = accountCheckBiz.getAccountCheckById(accountCheckId);				
				accountCheck.setStatus(AccountCheck.STATES_1);//无效
				accountCheckBiz.update(accountCheck);

				return new ActionRedirect("/transaction/accountCheckList.do?thisAction=list");				
			} catch (Exception e) {
				e.printStackTrace();
				inf.setMessage("删除异常");
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