package com.fdays.tsms.transaction.biz;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	// 根据id查询 (lrc)
	public Statement getStatementById(long id) throws AppException;

	// 显示该订单下的机票订单
	public void viewAirticketOrder(String statementId,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException;

	// 返回一个List集合
	public List<Statement> getStatementList() throws AppException;

	// 根据收款账号
	public List<Statement> getStatementListByToAccountId(long toAccountId)
			throws AppException;

}
