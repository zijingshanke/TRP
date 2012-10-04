package com.fdays.tsms.system.dao;

import java.util.List;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.TicketLogListForm;
import com.neza.base.BaseDAO;
import com.neza.exception.AppException;

public interface TicketLogDAO extends BaseDAO {

	public List list(TicketLogListForm tslf) throws AppException;

	public long save(TicketLog TicketLog) throws AppException;

	public long merge(TicketLog TicketLog) throws AppException;

	public long update(TicketLog TicketLog) throws AppException;

	public TicketLog getTicketLogById(long id) throws AppException;

	public void deleteById(long id) throws AppException;

	public boolean getTicketLogByUserId(long id) throws AppException;
	
	public List getTicketLogByOrderNo(String orderNo) throws AppException;
	public List getTicketLogByOrderIds(String orderIds) throws AppException;
}
