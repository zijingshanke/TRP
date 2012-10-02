package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.neza.exception.AppException;

public class StatementBizImp implements StatementBiz {

	public StatementDAO statementDAO;

	public List list(StatementListForm rlf) throws AppException {

		return statementDAO.list(rlf);
	}

	public List list() throws AppException {
		return statementDAO.list();
	}

	public void delete(long id) throws AppException {
		statementDAO.delete(id);
	}

	public long save(Statement statement) throws AppException {
		return statementDAO.save(statement);
	}

	public long update(Statement statement) throws AppException {
		return statementDAO.update(statement);
	}

	//根据id查询 (lrc)
	public Statement getStatementById(long id) throws AppException
	{
		return statementDAO.getStatementById(id);
	}
	
	//返回一个List集合
	public List<Statement> getStatementList() throws AppException
	{
		return statementDAO.getStatementList();
	}
	
	//根据收款账号
	public List<Statement> getStatementListByToAccountId(long toAccountId) throws AppException
	{
		return statementDAO.getStatementListByToAccountId(toAccountId);
	}
	
	public StatementDAO getStatementDAO() {
		return statementDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
}
