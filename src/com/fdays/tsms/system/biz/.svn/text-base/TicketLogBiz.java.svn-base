package com.fdays.tsms.system.biz;

import java.util.List;

import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.TicketLogListForm;
import com.neza.exception.AppException;

public interface TicketLogBiz {

	public TicketLog getTicketLogById(int id) throws AppException;

	public List getTicketLogs(TicketLogListForm sllf) throws AppException;

	public void saveTicketLog(TicketLog TicketLog) throws AppException;

	public int updateTicketLog(TicketLog TicketLog) throws AppException;

	public void deleteTicketLog(int id) throws AppException;
	
	//根据订单号查询
	public TicketLog getTicketLogByOrderNo(String orderNo) throws AppException;

}