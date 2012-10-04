package com.fdays.tsms.airticket.action;

import com.neza.base.BaseAction;
import com.fdays.tsms.airticket.biz.PassengerBiz;

public class PassengerListAction extends BaseAction {	
	public PassengerBiz passengerBiz;

	public void setPassengerBiz(PassengerBiz passengerBiz) {
		this.passengerBiz = passengerBiz;
	}
}
