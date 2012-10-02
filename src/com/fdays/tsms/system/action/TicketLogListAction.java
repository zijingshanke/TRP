package com.fdays.tsms.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.system.TicketLogListForm;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class TicketLogListAction extends BaseAction {
	private TicketLogBiz ticketLogBiz;

	// 显示所有
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		TicketLogListForm tllf = (TicketLogListForm) form;
		if (tllf == null)
			tllf = new TicketLogListForm();
		tllf.setList(ticketLogBiz.getTicketLogs(tllf));
		request.setAttribute("tllf", tllf);
		forwardPage = "listticketlog";
		return (mapping.findForward(forwardPage));
	}

	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}

}