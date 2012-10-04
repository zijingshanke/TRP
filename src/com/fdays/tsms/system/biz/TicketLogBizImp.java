package com.fdays.tsms.system.biz;

import java.util.List;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.TicketLogListForm;
import com.fdays.tsms.system.dao.TicketLogDAO;
import com.neza.exception.AppException;

public class TicketLogBizImp implements TicketLogBiz {
	private TicketLogDAO ticketLogDAO;
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			HibernateTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

	public List getTicketLogs(TicketLogListForm tslf) throws AppException {
		return ticketLogDAO.list(tslf);
	}

	public void saveTicketLog(TicketLog syslog) throws AppException {
		ticketLogDAO.save(syslog);
	}

	public void deleteTicketLog(int id) throws AppException {
		ticketLogDAO.deleteById(id);
	}

	public int updateTicketLog(TicketLog TicketLog) throws AppException {
		ticketLogDAO.update(TicketLog);
		return 0;
	}

	public TicketLog getTicketLogById(int id) throws AppException {
		return ticketLogDAO.getTicketLogById(id);
	}
	
	public List getTicketLogByOrderNo(String orderNo) throws AppException	{
		return ticketLogDAO.getTicketLogByOrderNo(orderNo);
	}

	public TicketLogDAO getTicketLogDAO() {
		return ticketLogDAO;
	}
}
