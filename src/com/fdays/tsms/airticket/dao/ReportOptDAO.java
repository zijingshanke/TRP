package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Report;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface ReportOptDAO extends BaseDAO {
	public List<OptTransaction> getOptTransactionList(Report report)throws AppException;
}
