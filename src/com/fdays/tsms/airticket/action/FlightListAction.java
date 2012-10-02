package com.fdays.tsms.airticket.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.fdays.tsms.airticket.FlightListForm;
import com.fdays.tsms.airticket.biz.FlightBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class FlightListAction extends BaseAction {

	
	public FlightBiz flightBiz;
	/***************************************************************************
	 * 分页 sc
	 **************************************************************************/
    public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		FlightListForm ulf = (FlightListForm) form;
		if (ulf == null)
			ulf = new FlightListForm();

		try {
			ulf.setList(flightBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listresume";

		return (mapping.findForward(forwardPage));
	}
    
    
	public FlightBiz getFlightBiz() {
		return flightBiz;
	}
	public void setFlightBiz(FlightBiz flightBiz) {
		this.flightBiz = flightBiz;
	}
	
}
