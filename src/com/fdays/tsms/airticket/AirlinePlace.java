package com.fdays.tsms.airticket;

import com.fdays.tsms.airticket._entity._AirlinePlace;

public class AirlinePlace extends _AirlinePlace {
	private static final long serialVersionUID = 1L;

	private Long airlineId = new Long(0);

	public static long STATUS_1 = new Long(1);// 启用
	public static long STATUS_2 = new Long(2);// 停用

	public String getStatusText() {
		if (this.status.intValue() == STATUS_1)
			return "启用";
		else {
			return "停用";
		}
	}

	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

}
