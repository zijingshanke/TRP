package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class StatementDAOImp extends BaseDAOSupport implements StatementDAO {
	public List getStatementListByOrder(long orderid, long ordertype)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1 and s.orderId=" + orderid
				+ " and s.orderType=" + ordertype);
		hql.add(" s.status not in(88) ");

		return this.list(hql);
	}

	public Statement getSaleStatementByOrder(long orderid, long ordertype)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1 and s.orderId=" + orderid
				+ " and s.orderType=" + ordertype+" and s.type="+Statement.type__1);
		hql.add(" s.status not in(88) ");

		List stateList=this.list(hql);
		if (stateList!=null) {
			return (Statement)stateList.get(0);
		}
		
		return null;
	}

	public Statement getStatementById(long id) throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where s.id=" + id);
		Query query = this.getQuery(hql);
		Statement statement = new Statement();
		if (query != null && query.list() != null && query.list().size() > 0) {
			statement = (Statement) query.list().get(0);
		}
		return statement;
	}

	// 返回一个List集合
	public List<Statement> getStatementList() throws AppException {
		List<Statement> list = new ArrayList<Statement>();
		Hql hql = new Hql();
		hql.add("from Statement");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			list = query.list();
		}
		return list;
	}

	// 根据收款账号
	public List<Statement> getStatementListByToAccountId(long toAccountId)
			throws AppException {
		List<Statement> list = new ArrayList<Statement>();
		Hql hql = new Hql();
		hql.add("from Statement s where s.toPCAccount.id=" + toAccountId);
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			list = query.list();
		}
		return list;
	}

	public List list(StatementListForm rlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1");
		if (rlf.getStatementNo() != null
				&& (!(rlf.getStatementNo().equals("")))) {
			hql.add(" and s.statementNo like '%" + rlf.getStatementNo().trim()
					+ "%'");
		}
		if (rlf.getStatus1() != null && (!(rlf.getStatus1().equals("")))) {
			hql.add(" and s.status in (" + rlf.getStatus1() + ")");// 用包拯关系查询
		}
		if (rlf.getStatus() >= 0) {
			hql.add(" and s.status=" + rlf.getStatus());
		}
		if (rlf.getDeleteStatus() == Statement.STATUS_88)// 已废弃
		{
			hql.add("and s.status not in(" + rlf.getDeleteStatus() + ")");
		}
		hql.add(" order by s.optTime desc");
		return this.list(hql, rlf);
	}

	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement where 1=1");
		return this.list(hql);
	}

	public void delete(long id) throws AppException {
		if (id > 0) {
			Statement statement = (Statement) this.getHibernateTemplate().get(
					Statement.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(statement);
		}
	}

	public long save(Statement statement) throws AppException {
		this.getHibernateTemplate().save(statement);
		return statement.getId();
	}

	public long update(Statement statement) throws AppException {
		if (statement.getId() > 0) {
			this.getHibernateTemplate().update(statement);
			return statement.getId();
		} else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public long merge(Statement statement) throws AppException {
		this.getHibernateTemplate().merge(statement);
		return statement.getId();
	}

}
