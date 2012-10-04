package com.fdays.tsms.airticket.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.AirlinePlace;
import com.fdays.tsms.airticket.AirlinePlaceListForm;
import com.fdays.tsms.airticket.biz.AirlineBiz;
import com.fdays.tsms.airticket.biz.AirlinePlaceBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirlinePlaceListAction extends BaseAction {	
	private AirlineBiz airlineBiz;
	private AirlinePlaceBiz airlinePlaceBiz;

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		AirlinePlaceListForm ulf = (AirlinePlaceListForm) form;
		int id = 0;
		if (ulf.getSelectedItems().length > 0) {
			id = ulf.getSelectedItems()[0];
		} else
			id = ulf.getId();
		if (id > 0) {
			AirlinePlace airlinePlace = (AirlinePlace) airlinePlaceBiz.getAirlinePlaceById(id);
			if (airlinePlace == null) {
				System.out.println("AirlinePlace==null");
			}
			airlinePlace.setThisAction("update");
			request.setAttribute("airlinePlace", airlinePlace);
			request.setAttribute("airlineList", airlineBiz.getValidList());
		} else{
			request.setAttribute("airlinePlace", new AirlinePlace());
		}
		forwardPage = "editairlinePlace";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePlaceListForm ulf = (AirlinePlaceListForm) form;
		int id = ulf.getId();
		if (id > 0) {
			AirlinePlace airlinePlace = (AirlinePlace) airlinePlaceBiz.getAirlinePlaceById(id);
			airlinePlace.setThisAction("view");
			request.setAttribute("airlinePlace", airlinePlace);
		} else {
			forwardPage = "login";
		}

		forwardPage = "viewairlinePlace";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AirlinePlace airlinePlace = new AirlinePlace();
		airlinePlace.setThisAction("insert");
		request.setAttribute("airlinePlace", airlinePlace);
		airlinePlace.setStatus(new Long(1));
		
		request.setAttribute("airlineList", airlineBiz.getValidList());
		
		String forwardPage = "editairlinePlace";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePlaceListForm ulf = (AirlinePlaceListForm) form;
		if (ulf == null){
			ulf = new AirlinePlaceListForm();
		}

		try {
			List list=airlinePlaceBiz.list(ulf);
			ulf.setList(list);
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listairlinePlace";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AirlinePlaceListForm ulf = (AirlinePlaceListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < ulf.getSelectedItems().length; i++) {
				id = ulf.getSelectedItems()[i];
				if (id > 0)
					message += airlinePlaceBiz.deleteAirlinePlace(id);
			}
			if (message > 0) {
				inf.setMessage("您已经成功删除航线!");
			} else {
				inf.setMessage("删除失败!");
			}
			inf.setForwardPage("/airticket/airlinePlacelist.do");
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

	public void setAirlinePlaceBiz(AirlinePlaceBiz airlinePlaceBiz) {
		this.airlinePlaceBiz = airlinePlaceBiz;
	}
}
