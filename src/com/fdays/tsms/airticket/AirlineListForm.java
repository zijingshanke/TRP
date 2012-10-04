package com.fdays.tsms.airticket;

import com.neza.base.ListActionForm;

public class AirlineListForm extends ListActionForm {
	private static final long serialVersionUID = 1L;

	private int id = 0;
	private String begin = "";
	private String end = "";
	private Long price = new Long(0);
	private Long distance = new Long(0);
	private Long status = new Long(0);

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBegin() {
		if(begin==null){
			return "";
		}
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}

	public String getEnd() {
		if(end==null){
			return "";
		}
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Long getDistance() {
		return distance;
	}

	public void setDistance(Long distance) {
		this.distance = distance;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
