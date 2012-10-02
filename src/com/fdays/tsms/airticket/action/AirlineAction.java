package com.fdays.tsms.airticket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.biz.AirlineBiz;
import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.airticket.Airline;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirlineAction extends BaseAction {
	private AirlineBiz airlineBiz;
	private SysInitBiz sysInitBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Airline airline = (Airline) form;
		Inform inf = new Inform();
		try {
			Airline tempAirline = (Airline) airlineBiz.getAirlineById(airline.getId());
			tempAirline.setBegin(airline.getBegin().toUpperCase());
			tempAirline.setEnd(airline.getEnd().toUpperCase());
			tempAirline.setPrice(airline.getPrice());
			tempAirline.setDistance(airline.getDistance());
			tempAirline.setStatus(airline.getStatus());
			airlineBiz.update(tempAirline);
			request.setAttribute("airline", tempAirline);

			inf.setMessage("更新成功");
			inf.setForwardPage("/airticket/airlinelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

			// --更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz, 11);
			MainTask.put(listener);
			// ---------
		} catch (Exception ex) {
			inf.setMessage("更新航线出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Airline airline = (Airline) form;
		Inform inf = new Inform();
		try {
			Airline tempAirline = new Airline();
			tempAirline.setBegin(airline.getBegin().toUpperCase());
			tempAirline.setEnd(airline.getEnd().toUpperCase());
			tempAirline.setPrice(airline.getPrice());
			tempAirline.setDistance(airline.getDistance());
			tempAirline.setStatus(Airline.STATUS_1);
			airlineBiz.save(tempAirline);

			request.setAttribute("airline", tempAirline);
			inf.setMessage("增加成功");
			inf.setForwardPage("/airticket/airlinelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

			// --更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz, 11);
			MainTask.put(listener);
			// ---------
		} catch (Exception ex) {
			inf.setMessage("增加用户出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}


	public void setAirlineBiz(AirlineBiz airlineBiz) {
		this.airlineBiz = airlineBiz;
	}

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}
}