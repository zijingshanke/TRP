package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.exception.AppException;

public interface StatementDAO {

	public List list(StatementListForm rlf) throws AppException;
	public List list() throws AppException;
	public void delete(long id)  throws AppException;
	public long save(Statement statement) throws AppException;
	public long update(Statement statement) throws AppException;
	public long merge(Statement statement) throws AppException;
	//根据id查询 (lrc)
	public Statement getStatementById(long id) throws AppException;
	//返回一个List集合
	public List<Statement> getStatementList() throws AppException;
	//根据收款账号
	public List<Statement> getStatementListByToAccountId(long toAccountId) throws AppException;

}