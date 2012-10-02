package com.fdays.tsms.transaction.biz;

import java.util.List;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.neza.exception.AppException;

public class StatementBizImp implements StatementBiz {
	private StatementDAO statementDAO;
	
	public Statement getStatementByOrderSubType(long orderid,long orderSubtype,long orderType)throws AppException{
		return statementDAO.getStatementByOrderSubType(orderid, orderSubtype, orderType);
	}
	
	public List getStatementListByOrderSubType(long orderid,long orderSubtype,long orderType)throws AppException{
		return statementDAO.getStatementListByOrderSubType(orderid, orderSubtype, orderType);
	}
	
	public List getStatementListByOrder(long orderid, long ordertype)throws AppException {
		return statementDAO.getStatementListByOrder(orderid, ordertype);
}

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

	public Statement getStatementById(long id) throws AppException {
		return statementDAO.getStatementById(id);
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
	

}
