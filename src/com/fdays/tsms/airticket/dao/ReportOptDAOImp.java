package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportOptDAOImp extends ReportDAOImp implements ReportOptDAO {
	/**
	 * 操作员收付款统计
	 */
	public List<OptTransaction> getOptTransactionList(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");

		Hql hqlNormalOrder = getOrderCount(startDate, endDate, "u.userId", "1");
		hql.addHql(hqlNormalOrder);
		hql.add(",");

		Hql hqlUmbuchenOrder = getOrderCount(startDate, endDate, "u.userId",
				"2");
		hql.addHql(hqlUmbuchenOrder);
		hql.add(",");

		Hql hqlRetireOrder = getOrderCount(startDate, endDate, "u.userId", "3");
		hql.addHql(hqlRetireOrder);
		hql.add(",");

		Hql hqlInvalidOrder = getOrderCount(startDate, endDate, "u.userId", "4");
		hql.addHql(hqlInvalidOrder);
		hql.add(",");

		Hql hqlCancelOrder = getOrderCount(startDate, endDate, "u.userId", "5");
		hql.addHql(hqlCancelOrder);
		hql.add(",");

		Hql hqlTicketCount = getTicketCount(startDate, endDate, "u.userId", "6");
		hql.addHql(hqlTicketCount);
		hql.add(",");

		Hql hqlInAmount = getOrderAmount(startDate, endDate, "u.userId", "1",
				Statement.type_1);
		hql.addHql(hqlInAmount);
		hql.add(",");

		Hql hqlOutAmount = getOrderAmount(startDate, endDate, "u.userId", "1",
				Statement.type_2);
		hql.addHql(hqlOutAmount);

		hql.add(",");

		Hql hqlRetireInAmount = getOrderAmount(startDate, endDate, "u.userId",
				"7", Statement.type_1);
		hql.addHql(hqlRetireInAmount);
		hql.add(",");

		Hql hqlRetireOutAmount = getOrderAmount(startDate, endDate, "u.userId",
				"7", Statement.type_2);
		hql.addHql(hqlRetireOutAmount);

		hql.add(" ) from SysUser u ");

		hql.add(" where u.id in(");
		hql.add(" select u2.id from SysUser u2 where 1=1 ");
		if (report.getOperatorDepart() != null
				&& report.getOperatorDepart() > 0) {
			hql.add("   and u2.userDepart = " + report.getOperatorDepart());// 部门
		}

		if (report.getOperator() != null
				&& !"".equals(report.getOperator().trim())) {
			hql.add("  and (u2.userName like '%" + report.getOperator().trim()
					+ "%' ");// 操作人
			hql.add(" or u2.userNo like '%" + report.getOperator().trim()
					+ "%' ))");
		}
		hql.add(" ) ");

		System.out.println("startDate:" + startDate + "--endDate:" + endDate);
		System.out.println(hql.getSql());

		List<OptTransaction> list = new ArrayList();
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0) {
			list = query.list();
		}
		return list;
	}

	/**
	 * @param orderType
	 *            1:正常订单 7:退废订单
	 * @param statementType
	 *            1:收款 2：付款
	 */
	public Hql getOrderAmount(String startDate, String endDate, String userId,
			String orderType, long statementType) throws AppException {
		Hql hql = new Hql();
		Hql baseHql = getOptOrder(startDate, endDate, userId, orderType);
		if (baseHql != null) {
			hql.add(" (select sum(s.totalAmount) from Statement s ");
			hql.add(" where s.type=" + statementType);
			hql.add(" and s.orderId in (select distinct a.id  ");

			hql.addHql(baseHql);

			hql.add(" )  )  ");
			System.out.println(hql.getSql());
		}
		return hql;
	}

	// 订单数
	public Hql getOrderCount(String startDate, String endDate, String userId,
			String type) throws AppException {
		Hql hql = new Hql();
		Hql baseHql = getOptOrder(startDate, endDate, userId, type);
		if (baseHql != null) {
			hql.add(" (select count(distinct a.orderGroup.id) ");

			hql.addHql(baseHql);

			hql.add(" ) ");
			System.out.println(hql.getSql());
		}
		return hql;
	}

	// 机票数（乘机人）
	public Hql getTicketCount(String startDate, String endDate, String userId,
			String type) throws AppException {
		Hql hql = new Hql();
		Hql baseHql = getOptOrder(startDate, endDate, userId, type);
		if (baseHql != null) {
			hql
					.add(" (select count(distinct p.ticketNumber) from Passenger p ");
			hql.add(" where p.airticketOrder.id in(select distinct a.id  ");

			hql.addHql(baseHql);

			hql.add(" ) ) ");
			System.out.println(hql.getSql());
		}
		return hql;
	}
}
