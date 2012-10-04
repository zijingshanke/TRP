package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class PlatformbankCardPaymentListForm extends ListActionForm{

	private String startDate;
  	private String endDate;
  	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
