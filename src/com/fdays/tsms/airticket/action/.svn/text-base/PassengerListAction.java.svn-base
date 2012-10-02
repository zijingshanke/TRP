package com.fdays.tsms.airticket.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.neza.base.BaseAction;
import com.neza.exception.AppException;
import com.fdays.tsms.airticket.PassengerListForm;
import com.fdays.tsms.airticket.biz.PassengerBiz;

public class PassengerListAction extends BaseAction {
	
	public PassengerBiz passengerBiz;
	/***************************************************************************
	 * 分页 sc
	 **************************************************************************/
    public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PassengerListForm ulf = (PassengerListForm) form;
		if (ulf == null)
			ulf = new PassengerListForm();

		try {
			ulf.setList(passengerBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listresume";

		return (mapping.findForward(forwardPage));
	}
	
	

	public PassengerBiz getPassengerBiz() {
		return passengerBiz;
	}

	public void setPassengerBiz(PassengerBiz passengerBiz) {
		this.passengerBiz = passengerBiz;
	}
}
