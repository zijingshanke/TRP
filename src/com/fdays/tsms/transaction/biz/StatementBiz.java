package com.fdays.tsms.transaction.biz;

import java.util.List;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.exception.AppException;

public interface StatementBiz {
	public List list(StatementListForm rlf) throws AppException;

	public List list() throws AppException;

	public List getStatementListByOrder(long orderid, long ordertype)
			throws AppException;

	public void delete(long id) throws AppException;

	public long save(Statement statement) throws AppException;

	public long update(Statement statement) throws AppException;

	public Statement getStatementById(long id) throws AppException;

	public List<Statement> getStatementList() throws AppException;

	public List<Statement> getStatementListByToAccountId(long toAccountId)
			throws AppException;

}
