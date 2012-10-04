package com.fdays.tsms.airticket.dao;

import com.fdays.tsms.transaction.Statement;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class OrderStatementHqlUtil {
	public static Hql getOrderStatementHql(String orderIdExp)
			throws AppException {
		Hql hql = new Hql();
		Hql inStatement = getStatement(orderIdExp, Statement.SUBTYPE_10);
		hql.addHql(inStatement);
		hql.add(",");

		Hql outStatement = getStatement(orderIdExp, Statement.SUBTYPE_20);
		hql.addHql(outStatement);
		hql.add(",");

		Hql inRefundStatement = getStatement(orderIdExp, Statement.SUBTYPE_11);
		hql.addHql(inRefundStatement);
		hql.add(",");

		Hql outRefundStatement = getStatement(orderIdExp, Statement.SUBTYPE_21);
		hql.addHql(outRefundStatement);

		System.out.println("==getOrderStatementHql2===========>>>>>>"
				+ hql.getSql());
		return hql;
	}

	public static Hql getStatement(String orderIdExp, Long orderSubtype)
			throws AppException {
		Hql hql = new Hql();
		hql.add(" ( select ");

		if (orderSubtype == Statement.SUBTYPE_10) {
			hql.add("'<accountName>'" + "||" + " s.toAccount.name " + "||"
					+ "'</accountName>'" + "||");
			hql.add("'<accountNo>'" + "||" + " s.toAccount.accountNo " + "||"
					+ "'</accountNo>'" + "||");
		}
		if (orderSubtype == Statement.SUBTYPE_11) {
			hql.add("'<accountName>'" + "||" + " s.toAccount.name " + "||"
					+ "'</accountName>'" + "||");
			hql.add("'<accountNo>'" + "||" + " s.toAccount.accountNo " + "||"
					+ "'</accountNo>'" + "||");
		}
		if (orderSubtype == Statement.SUBTYPE_20) {
			hql.add("'<accountName>'" + "||" + " s.fromAccount.name " + "||"
					+ "'</accountName>'" + "||");
			hql.add("'<accountNo>'" + "||" + " s.fromAccount.accountNo " + "||"
					+ "'</accountNo>'" + "||");
		}
		if (orderSubtype == Statement.SUBTYPE_21) {
			hql.add("'<accountName>'" + "||" + " s.fromAccount.name " + "||"
					+ "'</accountName>'" + "||");
			hql.add("'<accountNo>'" + "||" + " s.fromAccount.accountNo " + "||"
					+ "'</accountNo>'" + "||");
		}

		hql.add("'<totalAmount>'" + "||");
		hql.add(" s.totalAmount" + "||");
		hql.add("'</totalAmount>'" + "||");

		hql.add("'<statementDate>'" + "||");
		hql.add(" to_char(s.statementDate,'yyyy-MM-dd HH24:mm:ss') " + "||");
		hql.add("'</statementDate>'" + "||");

		hql.add("'<memo>'" + "||");
		hql.add(" s.memo " + "||");
		hql.add("'</memo>'");

		hql.add(" from Statement s where 1=1 ");
		hql.add(" and s.orderId=" + orderIdExp);
		hql.add(" and s.orderSubtype=" + orderSubtype);
		hql.add(" and rownum=1 ");
		hql.add(" ) ");
		return hql;
	}

	
}
