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

	/***************************************************************************
	 * 分页 sc
	 **************************************************************************/
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatementListForm ulf = (StatementListForm) form;
		String temp = request.getParameter("status1");
		if (temp != null && temp.equals("")) {
			ulf.setStatus1(temp);
		}
		if (ulf == null)
			ulf = new StatementListForm();

		try {
			ulf.setList(statementBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}

		ulf.addSumField(1, "totalAmount");
		ulf.addSumField(2, "actualAmount");
		ulf.addSumField(3, "unsettledAccount");
		ulf.addSumField(4, "commission");
		ulf.addSumField(5, "rakeOff");

		request.setAttribute("ulf", ulf);
		forwardPage = "listStatementUp";

		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 查询已经结算,部分结算数据 分页 lrc
	 **************************************************************************/
	public ActionForward getStatementListByStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		StatementListForm ulf = (StatementListForm) form;
		String temp = request.getParameter("status1");

		if (ulf == null)
			ulf = new StatementListForm();

		try {
			ulf.setDeleteStatus(Statement.STATUS_88);// 过滤已废弃
			ulf.setList(statementBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		ulf.setStatus1(temp);
		ulf.addSumField(1, "totalAmount");
		ulf.addSumField(2, "actualAmount");
		ulf.addSumField(3, "unsettledAccount");
		ulf.addSumField(4, "commission");
		ulf.addSumField(5, "rakeOff");

		request.setAttribute("ulf", ulf);
		forwardPage = "listStatementOut";

		return (mapping.findForward(forwardPage));
	}

	// 显示结算订单详细信息
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

	// 显示该订单下的机票订单
	public ActionForward viewAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";

		try {
			String statementId = request.getParameter("statementId");
			if (statementId != null && (!(statementId.equals("")))) {
				statementBiz.viewAirticketOrder(statementId, request, response);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		forwardPage = "viewAirticketOrder";
		return mapping.findForward(forwardPage);

	}

	public StatementBiz getStatementBiz() {
		return statementBiz;
	}

	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}

}
