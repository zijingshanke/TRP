package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformCompareListForm;
import com.neza.exception.AppException;

public interface PlatformCompareDAO {
	public List<PlatformCompare> listCompareOrder(Long platformId,
			String startDate, String endDate, String businessType,
			String tranType, String ticketType) throws AppException;

	public List<AirticketOrder> listAsOrder(PlatformCompareListForm ulf)
			throws AppException;

}
