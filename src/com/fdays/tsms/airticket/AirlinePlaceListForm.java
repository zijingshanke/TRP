package com.fdays.tsms.airticket;

import com.neza.base.ListActionForm;

public class AirlinePlaceListForm extends ListActionForm {
	private static final long serialVersionUID = 1L;

	private int id = 0;
	private String company = "";
	private String code = "";
	private Long rate = new Long(0);
	private Long status = new Long(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}
	
	

}
