package com.fdays.tsms.airticket.action;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlineListForm;
import com.fdays.tsms.airticket.biz.AirlineBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirlineListAction extends BaseAction {	
	private AirlineBiz airlineBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		AirlineListForm ulf = (AirlineListForm) form;
		int id = 0;
		if (ulf.getSelectedItems().length > 0) {
			id = ulf.getSelectedItems()[0];
		} else
			id = ulf.getId();
		if (id > 0) {
			Airline airline = (Airline) airlineBiz.getAirlineById(id);
			if (airline == null) {
				System.out.println("Airline==null");
			}
			airline.setThisAction("update");
			request.setAttribute("airline", airline);
		} else
			request.setAttribute("airline", new Airline());
		forwardPage = "editairline";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlineListForm ulf = (AirlineListForm) form;
		int id = ulf.getId();
		if (id > 0) {
			Airline airline = (Airline) airlineBiz.getAirlineById(id);
			airline.setThisAction("view");
			request.setAttribute("airline", airline);
		} else {
			forwardPage = "login";
		}

		forwardPage = "viewairline";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Airline airline = new Airline();
		airline.setThisAction("insert");
		request.setAttribute("airline", airline);
		airline.setStatus(new Long(1));
		String forwardPage = "editairline";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlineListForm ulf = (AirlineListForm) form;
		if (ulf == null)
			ulf = new AirlineListForm();

		try {
			ulf.setList(airlineBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listairline";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AirlineListForm ulf = (AirlineListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < ulf.getSelectedItems().length; i++) {
				id = ulf.getSelectedItems()[i];
				if (id > 0)
					message += airlineBiz.deleteAirline(id);
			}
			if (message > 0) {
				inf.setMessage("您已经成功删除航线!");
			} else {
				inf.setMessage("删除失败!");
			}
			inf.setForwardPage("/airticket/airlinelist.do");
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


	public void setAirlineBiz(AirlineBiz airlineBiz) {
		this.airlineBiz = airlineBiz;
	}
}
