package com.fdays.tsms.transaction;

import com.fdays.tsms.transaction._entity._AirticketOrderReport;

public class AirticketOrderReport extends _AirticketOrderReport {
	private static final long serialVersionUID = 1L;

	public String getSubGroupMark() {
		return this.getGroupId() + "-" + getSubGroupMarkNo();
	}
}
