package com.fdays.tsms.transaction.biz;

import java.util.List;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.util.AirticketOrderStore;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.neza.exception.AppException;

public class StatementBizImp implements StatementBiz {
	private StatementDAO statementDAO;
	private AirticketOrderDAO airticketOrderDAO;

	public void excuteSynOrderStatement() {
		try {
//			System.out.println("excuteSynOrderStatement---");
//			System.out.println("before execute:"+AirticketOrderStore.orderGroupIdString);

			String[] orderGroupIds = StringUtil.getSplitString(
					AirticketOrderStore.orderGroupIdString, ",");
			for (int i = 0; i < orderGroupIds.length; i++) {
				long orderGroupId = Constant.toLong(orderGroupIds[i]);
				if (orderGroupId > 0) {
					List orderIds=airticketOrderDAO.listIDByGroupId(orderGroupId);
					for (int j = 0; j < orderIds.size(); j++) {
						Long orderId=(Long) orderIds.get(j);
						if(orderId>0){
							synStatementAmount(orderId);
							synOldStatementAmount(orderId);
						}
					}
					AirticketOrderStore.removeOrderId(orderGroupId);
				}
			}			
//			System.out.println("after execute:"+AirticketOrderStore.orderGroupIdString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void synOldStatementAmount(long orderId) throws AppException {
		statementDAO.synOldStatementAmount(orderId);
	}

	public void synStatementAmount(long orderId) {
		statementDAO.synStatementAmount(orderId);
	}

	public Statement getStatementByOrderSubType(long orderid,
			long orderSubtype, long orderType) throws AppException {
		return statementDAO.getStatementByOrderSubType(orderid, orderSubtype,
				orderType);
	}

	public List getStatementListByOrderSubType(long orderid, long orderSubtype,
			long orderType) throws AppException {
		return statementDAO.getStatementListByOrderSubType(orderid,
				orderSubtype, orderType);
	}

	public List getStatementListByOrder(long orderid, long ordertype)
			throws AppException {
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

	
	
	
	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
}
