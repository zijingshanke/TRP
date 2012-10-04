package com.fdays.tsms.airticket.action;

import com.fdays.tsms.airticket.biz.FlightBiz;
import com.neza.base.BaseAction;

public class FlightListAction extends BaseAction {	
	public FlightBiz flightBiz;

	public void setFlightBiz(FlightBiz flightBiz) {
		this.flightBiz = flightBiz;
	}	
}
