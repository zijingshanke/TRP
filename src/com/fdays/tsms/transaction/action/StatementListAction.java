package com.fdays.tsms.transaction.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.biz.StatementBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class StatementListAction extends BaseAction {
	private StatementBiz statementBiz;

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

	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}
}
