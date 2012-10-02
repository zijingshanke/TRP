package com.fdays.tsms.transaction.dao;

import java.math.BigDecimal;
import java.util.List;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.exception.AppException;

public interface StatementDAO {
	public void synStatementAmount(long orderId);

	public void synOldStatementAmount(long orderId);

	public BigDecimal getStatementAmount(long orderId, long orderSubtype,
			long ticketType) throws AppException;

	public Account getStatementAccountByOrderSubType(long orderid,
			long orderSubtype, long orderType) throws AppException;

	public Account getStatementAccountByOrderGroupType(long groupId,
			long tranType, long orderSubtype, long orderType)
			throws AppException;

	public Statement getStatementByOrderSubType(long orderid,
			long orderSubtype, long orderType) throws AppException;

	public List getStatementListByOrderSubType(long orderid, long orderSubtype,
			long orderType) throws AppException;

	public List list(StatementListForm rlf) throws AppException;

	public List list() throws AppException;

	public List getStatementListByOrder(long orderid, long ordertype)
			throws AppException;

	public Statement getStatementByOrder(long orderid, long ordertype,
			long statementType) throws AppException;

	public List getStatementListByOrders(String orderid, long ordertype)
			throws AppException;

	public void delete(long id) throws AppException;

	public long save(Statement statement) throws AppException;

	public long update(Statement statement) throws AppException;

	public long merge(Statement statement) throws AppException;

	public Statement getStatementById(long id) throws AppException;
}
