package com.fdays.tsms.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.TicketLogListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class TicketLogDAOImp extends BaseDAOSupport implements TicketLogDAO {
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public TransactionTemplate getTransactionTemplate() {
		return transactionTemplate;
	}

	public boolean getTicketLogByUserId(long id) throws AppException {
		String hql = " from TicketLog where sysUser.userId=" + id;
		List list = this.list(hql);
		if (list.size() > 0) {
			return true;
		} else
			return false;
	}

	public List list(TicketLogListForm sllf) throws AppException {
		String hql = "from TicketLog t where 1=1 ";
		try {
			String logUser = sllf.getUserNo().toString().trim();
			String formDate = sllf.getFromDate().toString().trim();
			String toDate = sllf.getToDate().toString().trim();
			if (logUser != "" && logUser != null) {
				hql += " and sysUser.userNo like '%" + logUser + "%'";
			}
			if (!"".equals(formDate) && formDate != null && !"".equals(toDate)
					&& toDate != null) {
				hql += " and  to_char(optTime,'yyyy-MM-dd') between '"
						+ formDate + "' and '" + toDate + "'";

			}
			if (sllf.getOrderNo() != null && (!(sllf.getOrderNo().equals("")))) {
				hql += " and t.orderNo='" + sllf.getOrderNo() + "'";
			}
			hql += " order by optTime desc ";
		} catch (Exception ex) {
			ex.getMessage();
		}
		return this.list(hql, sllf);
	}

	public List list(TicketLog TicketLog) throws AppException {
		return null;
	}

	public List getTicketLogByOrderNo(String orderNo) throws AppException {
		Hql hql = new Hql();
		hql.add("from TicketLog t where t.orderNo like '%" + orderNo + "%'");
		hql.add(" order by id desc ");

		return this.list(hql);
	}
	
	public List getTicketLogByOrderIds(String orderIds) throws AppException {
		Hql hql = new Hql();
		hql.add("from TicketLog t where t.orderId in (" + orderIds + ")");
		hql.add(" order by id desc ");
		return this.list(hql);
	}

	public long save(TicketLog TicketLog) throws AppException {
		this.getHibernateTemplate().save(TicketLog);
		return TicketLog.getId();
	}

	public long merge(TicketLog TicketLog) throws AppException {
		this.getHibernateTemplate().merge(TicketLog);
		return TicketLog.getId();
	}

	public long update(TicketLog TicketLog) throws AppException {
		if (TicketLog.getId() > 0)
			return save(TicketLog);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			TicketLog TicketLog = (TicketLog) this.getHibernateTemplate().get(
					TicketLog.class, new Long(id));
			this.getHibernateTemplate().delete(TicketLog);
		}
	}

	public TicketLog getTicketLogById(long id) throws AppException {
		TicketLog TicketLog;
		if (id > 0) {
			TicketLog = (TicketLog) this.getHibernateTemplate().get(
					TicketLog.class, new Long(id));
			return TicketLog;
		} else
			return new TicketLog();
	}

}
