package com.fdays.tsms.airticket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.biz.AirlineBiz;
import com.fdays.tsms.airticket.biz.AirlinePlaceBiz;
import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlinePlace;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirlinePlaceAction extends BaseAction {
	private AirlineBiz airlineBiz;
	private AirlinePlaceBiz airlinePlaceBiz;
	private SysInitBiz sysInitBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePlace airlinePlace = (AirlinePlace) form;
		Inform inf = new Inform();
		try {
			AirlinePlace tempAirlinePlace = (AirlinePlace) airlinePlaceBiz.getAirlinePlaceById(airlinePlace.getId());
			tempAirlinePlace.setCompany(airlinePlace.getCompany().toUpperCase());
			tempAirlinePlace.setCode(airlinePlace.getCode().toUpperCase());
			tempAirlinePlace.setRate(airlinePlace.getRate());
			tempAirlinePlace.setStatus(airlinePlace.getStatus());
			
			if(airlinePlace.getAirlineId()!=null){
				Airline tempAirline=airlineBiz.getAirlineById(airlinePlace.getAirlineId());
				if(tempAirline!=null&&tempAirline.getId()>0){
					tempAirlinePlace.setAirline(tempAirline);
				}else{
					tempAirlinePlace.setAirline(null);
				}
			}
		
			airlinePlaceBiz.update(tempAirlinePlace);
			request.setAttribute("airlinePlace", tempAirlinePlace);

			inf.setMessage("更新成功");
			inf.setForwardPage("/airticket/airlinePlacelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

			// --更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz, 12);
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
		AirlinePlace airlinePlace = (AirlinePlace) form;
		Inform inf = new Inform();
		try {
			AirlinePlace tempAirlinePlace = new AirlinePlace();
			tempAirlinePlace.setCompany(airlinePlace.getCompany().toUpperCase());
			tempAirlinePlace.setCode(airlinePlace.getCode().toUpperCase());
			tempAirlinePlace.setRate(airlinePlace.getRate());
			tempAirlinePlace.setStatus(AirlinePlace.STATUS_1);
			
			if(airlinePlace.getAirlineId()!=null){
				Airline tempAirline=airlineBiz.getAirlineById(airlinePlace.getAirlineId());
				if(tempAirline!=null&&tempAirline.getId()>0){
					tempAirlinePlace.setAirline(tempAirline);
				}else{
					tempAirlinePlace.setAirline(null);
				}
			}
			
			airlinePlaceBiz.save(tempAirlinePlace);

			request.setAttribute("airlinePlace", tempAirlinePlace);
			inf.setMessage("增加成功");
			inf.setForwardPage("/airticket/airlinePlacelist.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

			// --更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz, 12);
			MainTask.put(listener);
			// ---------
		} catch (Exception ex) {
			inf.setMessage("增加舱位折扣出错！错误信息是：" + ex.getMessage());
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

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}
}