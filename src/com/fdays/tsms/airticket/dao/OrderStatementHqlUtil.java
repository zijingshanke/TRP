package com.fdays.tsms.airticket.dao;

import com.fdays.tsms.transaction.Statement;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class OrderStatementHqlUtil {
	public static Hql getOrderStatementHql(String orderIdExp)throws AppException{
//		public static final long SUBTYPE_10 = 10;// 正常收款
//		public static final long SUBTYPE_20 = 20;// 正常支付
//		public static final long SUBTYPE_11 = 11;// 收退款
//		public static final long SUBTYPE_21 = 21;// 付退款
		Hql hql = new Hql();		
		Hql inAccount = getStatementAccount(orderIdExp,Statement.SUBTYPE_10);
		hql.addHql(inAccount);
		hql.add(",");		
		Hql outAccount = getStatementAccount(orderIdExp,Statement.SUBTYPE_20);
		hql.addHql(outAccount);
		hql.add(",");			
		Hql inRefundAccount = getStatementAccount(orderIdExp,Statement.SUBTYPE_11);
		hql.addHql(inRefundAccount);		
		hql.add(",");		
		//-----------------------------------
		Hql outRefundAccount = getStatementAccount(orderIdExp,Statement.SUBTYPE_21);
		hql.addHql(outRefundAccount);
		//-----------------------------------
		hql.add(",");	
		
		Hql inAmount = getStatementAmount(orderIdExp,Statement.SUBTYPE_10);//正常收款
		hql.addHql(inAmount);
		hql.add(",");		
		Hql outAmount = getStatementAmount(orderIdExp,Statement.SUBTYPE_20);//正常付款
		hql.addHql(outAmount);
		hql.add(",");		
		//-----------------------------------
		Hql inRefundAmount = getStatementAmount(orderIdExp,Statement.SUBTYPE_11);//收退款
		hql.addHql(inRefundAmount);
		//-----------------------------------
		hql.add(",");		
		Hql outRefundAmount = getStatementAmount(orderIdExp,Statement.SUBTYPE_21);//付退款
		hql.addHql(outRefundAmount);
		
		hql.add(",");		
		Hql inTime = getStatementTime(orderIdExp,Statement.SUBTYPE_10);
		hql.addHql(inTime);
		hql.add(",");		
		Hql outTime = getStatementTime(orderIdExp,Statement.SUBTYPE_20);
		hql.addHql(outTime);
		hql.add(",");		
		Hql inRefundTime = getStatementTime(orderIdExp,Statement.SUBTYPE_11);
		hql.addHql(inRefundTime);
		hql.add(",");		
		Hql outRefundTime = getStatementTime(orderIdExp,Statement.SUBTYPE_21);
		hql.addHql(outRefundTime);		
		
		System.out.println("==getOrderStatementHql===========>>>>>>"+hql.getSql());		
		return hql;		
	}
	
	public static Hql getStatementAmount(String orderIdExp,Long orderSubtype)throws AppException{
		Hql hql=new Hql();
		hql.add(" (select sum(s.totalAmount)  from Statement s where 1=1 ");
		hql.add(" and s.orderId="+orderIdExp);
		hql.add(" and s.orderSubtype="+orderSubtype+")");    
		return hql;
	}
	
	public static Hql getStatementAccount(String orderIdExp,Long orderSubtype)throws AppException{
		Hql hql=new Hql();
		hql.add(" ( ");
		if(orderSubtype==Statement.SUBTYPE_10){			
			hql.add(" select max(s.toAccount) ");
		}
		if(orderSubtype==Statement.SUBTYPE_11){
			hql.add(" select max(s.toAccount) ");
		}				
		if(orderSubtype==Statement.SUBTYPE_20){
			hql.add(" select max(s.fromAccount) ");
		}		
		if(orderSubtype==Statement.SUBTYPE_21){
			hql.add(" select max(s.fromAccount) ");
		}
		hql.add(" from Statement s where 1=1 ");
		hql.add(" and s.orderId="+orderIdExp);
		hql.add(" and s.orderSubtype="+orderSubtype);
		
		hql.add(" ) ");
		return hql;
	}
	
	public static Hql getStatementTime(String orderIdExp,Long orderSubtype)throws AppException{
		Hql hql=new Hql();
		hql.add(" (select max(s.statementDate) from Statement s where 1=1 ");
		
		hql.add(" and s.orderId="+orderIdExp);
		hql.add(" and s.orderSubtype="+orderSubtype+")");    
		return hql;
	}
}
