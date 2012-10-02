package com.fdays.tsms.transaction.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class StatementDAOImp extends BaseDAOSupport implements StatementDAO {

	public void synStatementAmount(long orderId) {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			try {
				Connection con = session.connection();
				con.setAutoCommit(true);
				CallableStatement call = con.prepareCall("{Call update_statementamount(?)}");
				call.setInt("orderId", (int)orderId);
				call.execute();
				call.close();
				con.commit();
				con.close();
			} catch (Exception ex) {
				System.out.println("--同步订单结算金额失败：" + ex.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void synOldStatementAmount(long orderId) {
		try {
			Session session = this.getSessionFactory().getCurrentSession();
			try {
				Connection con = session.connection();
				con.setAutoCommit(true);
				CallableStatement call = con.prepareCall("{Call update_oldstatementamount(?)}");
				call.setInt("orderId", (int)orderId);
				call.execute();
				call.close();
				con.commit();
				con.close();
			} catch (Exception ex) {
				System.out.println("--同步退改订单销售结算金额失败：" + ex.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public BigDecimal getStatementAmount(long orderId, long orderSubtype,
			long ticketType) throws AppException {
		Hql hql = new Hql();
		hql.add(" select sum(s.totalAmount) ");

		hql.add(" from Statement s where 1=1 ");
		hql.add(" and s.orderId=" + orderId);
		hql.add(" and s.orderSubtype=" + orderSubtype);
		hql.add(" and s.orderType=" + ticketType);

		Query query = this.getQuery(hql);
		BigDecimal amount = BigDecimal.ZERO;
		if (query != null) {
			List list = query.list();
			if (list != null) {
				if (list.size() > 0) {
					amount = (BigDecimal) list.get(0);
				}
			}
		}
		return amount;
	}

	public Account getStatementAccountByOrderSubType(long orderid,
			long orderSubtype, long orderType) throws AppException {
		Account account = null;
		try {
			Statement statement = getStatementByOrderSubType(orderid,
					orderSubtype, orderType);
			if (statement != null) {
				Long type = statement.getType();
				if (type != null) {
					if (type == Statement.type_1) {
						account = statement.getToAccount();
					} else if (type == Statement.type_2) {
						account = statement.getFromAccount();
					}
				} else {
					System.out.println("getStatementAccountByOrderSubType==>"
							+ statement.getId() + " type is null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	public Statement getStatementByOrderSubType(long orderid,
			long orderSubtype, long orderType) throws AppException {
		Statement statement = null;
		List list = getStatementListByOrderSubType(orderid, orderSubtype,
				orderType);
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				System.out.println("list size>1 ====>orderid:" + orderid
						+ "---orderSubtype" + orderSubtype + "--orderType:"
						+ orderType);
			}
			statement = (Statement) list.get(0);
		} else {
			System.out.println("list is null ====>orderid:" + orderid
					+ "---orderSubtype" + orderSubtype + "--orderType:"
					+ orderType);
		}
		return statement;
	}

	public List getStatementListByOrderSubType(long orderid, long orderSubtype,
			long orderType) throws AppException {
		Hql hql = new Hql();

		hql.add(" from Statement s where 1=1");
		hql.add(" and s.orderId=" + orderid);

		hql.add(" and s.orderSubtype=" + orderSubtype);

		hql.add(" and s.orderType=" + orderType);

		hql.add(" and s.status not in(88) ");

		List<Statement> list = new ArrayList<Statement>();
		Query query = this.getQuery(hql);

		if (query != null) {
			list = query.list();
			if (list != null) {
				if (list.size() > 0) {
					return list;
				}
			}
		}
		return list;
	}

	public Account getStatementAccountByOrderGroupType(long groupId,
			long tranType, long orderSubtype, long orderType)
			throws AppException {
		Account account = null;
		try {
			Statement statement = getStatementByOrderGroupType(groupId,
					tranType, orderSubtype, orderType);
			if (statement != null) {
				Long type = statement.getType();
				if (type != null) {
					if (type == Statement.type_1) {
						account = statement.getToAccount();
					} else if (type == Statement.type_2) {
						account = statement.getFromAccount();
					}
				} else {
					System.out.println("getStatementAccountByOrderSubType==>"
							+ statement.getId() + " type is null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return account;
	}

	public Statement getStatementByOrderGroupType(long groupId, long tranType,
			long orderSubtype, long orderType) throws AppException {
		Statement statement = null;
		List list = getStatementListByOrderGroupType(groupId, tranType,
				orderSubtype, orderType);
		if (list != null && list.size() > 0) {
			if (list.size() > 1) {
				System.out.println("list size>1 ====>groupid:" + groupId
						+ "---orderSubtype" + orderSubtype + "--orderType:"
						+ orderType);
			}
			statement = (Statement) list.get(0);
		} else {
			System.out.println("list is null ====>groupid:" + groupId
					+ "--orderSubtype" + orderSubtype + "--orderType:"
					+ orderType);
		}
		return statement;
	}

	public List getStatementListByOrderGroupType(long groupId, long tranType,
			long orderSubtype, long orderType) throws AppException {
		Hql hql = new Hql();

		hql.add(" from Statement s where 1=1");
		hql
				.add(" and s.orderId in(select o.id from AirticketOrder o where o.orderGroup.id="
						+ groupId);
		hql.add(" and o.tranType=" + tranType);
		hql.add(" and o.status=" + AirticketOrder.STATUS_5 + ")");

		hql.add(" and s.orderSubtype=" + orderSubtype);

		hql.add(" and s.orderType=" + orderType);

		hql.add(" and s.status not in(88) ");

		System.out.println(hql.getSql());
		List<Statement> list = new ArrayList<Statement>();
		Query query = this.getQuery(hql);

		if (query != null) {
			list = query.list();
			if (list != null) {
				if (list.size() > 0) {
					return list;
				}
			}
		}
		return list;
	}

	public List getStatementListByOrder(long orderid, long ordertype)
			throws AppException {
		Hql hql = new Hql();

		hql.add("from Statement s where 1=1 and s.orderId=" + orderid
				+ " and s.orderType=" + ordertype);
		hql.add("and s.status not in(88) ");
		return this.list(hql);
	}

	public List getStatementListByOrders(String orderid, long ordertype)
			throws AppException {
		Hql hql = new Hql();

		hql.add("from Statement s where 1=1 and s.orderId in(" + orderid
				+ ") and s.orderType=" + ordertype);
		hql.add("and s.status not in(88) ");
		return this.list(hql);
	}

	public Statement getStatementByOrder(long orderid, long ordertype,
			long statementType) throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1 and s.orderId=" + orderid
				+ " and s.orderType=" + ordertype + " and s.type="
				+ statementType);
		hql.add(" and s.status not in(88) ");
		Query query = this.getQuery(hql);
		Statement statement = new Statement();
		if (query != null) {
			List list = query.list();
			if (list != null) {
				if (list.size() > 0) {
					statement = (Statement) list.get(0);
				}
			}
		}
		return statement;
	}

	public Statement getStatementById(long id) throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where s.id=" + id);
		Query query = this.getQuery(hql);
		Statement statement = new Statement();
		if (query != null) {
			List list = query.list();
			if (list != null) {
				if (list.size() > 0) {
					statement = (Statement) list.get(0);
				}
			}
		}
		return statement;
	}

	public List getStatementListByAirticketGroupMarkNo(String groupMarkNo)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1 and s.status not in(88) ");
		hql.add("exists("
				+ " select o.id from airticketOrder o where o.groupMarkNo="
				+ groupMarkNo.trim());
		hql.add("and s.orderId=o.id  and s.orderType=" + 1);
		hql.add(" and s.status not in(88) )");

		return this.list(hql);
	}

	public List list(StatementListForm rlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from Statement s where 1=1");
		if (rlf.getStatementNo() != null
				&& (!(rlf.getStatementNo().equals("")))) {
			hql.add(" and s.statementNo like '%" + rlf.getStatementNo().trim()
					+ "%'");
		}

		hql.add(" and s.status not in(88) ");

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
