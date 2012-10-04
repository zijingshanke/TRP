package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.airticket.util.AirticketOrderStore;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.biz.StatementBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class StatementListAction extends BaseAction {
	private StatementBiz statementBiz;
	private AirticketOrderBiz airticketOrderBiz;	
	
	public ActionForward listStatementManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatementListForm ulf = (StatementListForm) form;

		if (ulf == null) {
			ulf = new StatementListForm();
		}
		
		try {
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}

		request.setAttribute("ulf", ulf);
		forwardPage = "listStatementManage";

		return (mapping.findForward(forwardPage));
	}
	
	public ActionForward updateOrderStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatementListForm ulf = (StatementListForm) form;

		if (ulf == null) {
			ulf = new StatementListForm();
		}
		
		try {
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}

		request.setAttribute("ulf", ulf);
		forwardPage = "listStatementManage";

		return (mapping.findForward(forwardPage));
	}
	
	public ActionForward updateOrderOldStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatementListForm ulf = (StatementListForm) form;

		if (ulf == null) {
			ulf = new StatementListForm();
		}
		
		try {
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}

		request.setAttribute("ulf", ulf);
		forwardPage = "listStatementManage";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatementListForm ulf = (StatementListForm) form;

		if (ulf == null) {
			ulf = new StatementListForm();
		}
		
		try {
			ulf.setList(statementBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}

		ulf.addSumField(1, "totalAmount");

		request.setAttribute("ulf", ulf);
		forwardPage = "listStatement";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward viewStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		String statementId = request.getParameter("statementId");
		if (statementId != null && (!statementId.equals(""))) {
			Statement statement = statementBiz.getStatementById(Long
					.parseLong(statementId));
			request.setAttribute("statement", statement);
		}
		forwardPage = "viewStatement";
		return mapping.findForward(forwardPage);
	}
	public ActionForward editStatement(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{

		StatementListForm slf = (StatementListForm) form;

		if (slf != null && slf.getId() > 0)
		{
			Statement statement = statementBiz.getStatementById(slf.getId());
			request.setAttribute("statement", statement);
			AirticketOrder ao = airticketOrderBiz.getAirticketOrderById(statement
			    .getOrderId());
			List<PlatComAccount> accountList=new ArrayList<PlatComAccount>();
			if (ao.getCompany() != null && ao.getPlatform() != null)
			{
				
				accountList = PlatComAccountStore
				    .getPlatComAccountListByCompanyIdType(ao.getCompany().getId(), ao
				        .getPlatform().getId(), statement.getAccountType());
			}
	
			request.setAttribute("accountList", accountList);
		}

		return mapping.findForward("editStatement");
	}
	public void setStatementBiz(StatementBiz statementBiz)
	{
		this.statementBiz = statementBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz)
	{
		this.airticketOrderBiz = airticketOrderBiz;
	}
}