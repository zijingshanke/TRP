package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportOptDAOImp extends ReportDAOImp implements ReportOptDAO {
	public List<OptTransaction> getOptTransactionList(Report report)throws AppException {		
		Hql hql = new Hql();
		Long operatorDepart=Constant.toLong(report.getOperatorDepart());
		if (operatorDepart>0) {		
			if (operatorDepart == 1) {// 出票组			
				hql.addHql(getOptTransactionListForDrawDepart(report));
			} else if (operatorDepart == 2) {// 倒票组
				hql.addHql(getOptTransactionListForResaleDepart(report));
			} else if (operatorDepart == 3) {// 退票组
				hql.addHql(getOptTransactionListForRetireDepart(report));				
			} else if (operatorDepart == 11) {//B2C组
				hql.addHql(getOptTransactionListForDrawDepart(report));
			} else if (operatorDepart == 12) {//团队部
				hql.addHql(getOptTransactionListForTeamDepart(report));
			} else if (operatorDepart == 21) {// 支付组				
				hql.addHql(getOptTransactionListForPayDepart(report));
			} else if (operatorDepart == 22) {// 财务部
				hql.addHql(getOptTransactionListForFinanceDepart(report));
			}else{
				System.out.println(" operatorDepart is not defined.....");
			}
		}		
		
		List<OptTransaction> list = new ArrayList();
		Query query = this.getQuery(hql);
		if (query != null ){
			list= query.list();
			if(list != null){
				if(list.size() > 0){
					return list;
				}
			} 
		}
		return list;
	}
	
	/**
	 * 操作员统计(团队部)
	 */
	public Hql getOptTransactionListForTeamDepart(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		System.out.println("startDate:" + startDate + "--endDate:" + endDate);

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
		
		Hql baseHql21 = getOptOrder(startDate, endDate, "u.userNo", "21");
		Hql hqlNormalOrder = getOrderCount(baseHql21);
		hql.addHql(hqlNormalOrder);
		hql.add(",");
		Hql baseHql22 = getOptOrder(startDate, endDate, "u.userNo", "22");
		Hql hqlRetireOrder = getOrderCount(baseHql22);
		hql.addHql(hqlRetireOrder);
		hql.add(",");		
	
		Hql hqlTicketCount = getTicketCount(baseHql21);
		hql.addHql(hqlTicketCount);
		hql.add(",");
		
		Hql hqlInAmount = getOrderAmount(baseHql21,Statement.type_1,Statement.SUBTYPE_10);
		hql.addHql(hqlInAmount);
		hql.add(",");
		
		Hql hqlOutAmount = getOrderAmount(baseHql21,Statement.type_2,Statement.SUBTYPE_20);
		hql.addHql(hqlOutAmount);
		
		hql.add(",");		
		Hql hqlInRetireAmount = getOrderAmount(baseHql21,Statement.type_1,Statement.SUBTYPE_10);
		hql.addHql(hqlInRetireAmount);
		hql.add(",");
		
		Hql hqlOutRetireAmount = getOrderAmount(baseHql21,Statement.type_2,Statement.SUBTYPE_20);
		hql.addHql(hqlOutRetireAmount);		
		
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
		
		System.out.println(hql.getSql());
		return hql;
	}
	
	
	/**
	 * 操作员统计(财务部)
	 */
	public Hql getOptTransactionListForFinanceDepart(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		System.out.println("startDate:" + startDate + "--endDate:" + endDate);

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
		
		Hql baseHql1 = getOptStatementOrder(startDate, endDate, "u.userId","1",Statement.type_1,Statement.SUBTYPE_10);
		Hql hqlNormalOrder = getOrderCount(baseHql1);
		hql.addHql(hqlNormalOrder);
		hql.add(",");
		
		Hql baseHql3 = getOptStatementOrder(startDate, endDate, "u.userId","3",Statement.SUBTYPE_11+","+Statement.SUBTYPE_21);
		Hql hqlRetireOrder = getOrderCount(baseHql3);
		hql.addHql(hqlRetireOrder);
		hql.add(",");
		
		Hql baseHql4 = getOptStatementOrder(startDate, endDate, "u.userId","4",Statement.SUBTYPE_11+","+Statement.SUBTYPE_21);
		Hql hqlInValidOrder = getOrderCount(baseHql4);
		hql.addHql(hqlInValidOrder);
		hql.add(",");		
		
//		//===========出票和退废订单都有的情况下，如何统计机票数量
		Hql hqlTicketCount = getTicketCount(baseHql1);
		hql.addHql(hqlTicketCount);
		hql.add(",");
		
		Hql hqlInAmount = getStatementAmount(startDate,endDate,"u.userId",Statement.type_1,Statement.SUBTYPE_10);
		hql.addHql(hqlInAmount);		
		hql.add(",");
		
		Hql hqlOutAmount = getStatementAmount(startDate,endDate,"u.userId",Statement.type_2,Statement.SUBTYPE_20);
		hql.addHql(hqlOutAmount);		
		hql.add(",");
		
		Hql hqlInRetireAmount = getStatementAmount(startDate,endDate,"u.userId",Statement.type_1,Statement.SUBTYPE_11);
		hql.addHql(hqlInRetireAmount);		
		hql.add(",");
		
		Hql hqlOutRetireAmount = getStatementAmount(startDate,endDate,"u.userId",Statement.type_2,Statement.SUBTYPE_21);
		hql.addHql(hqlOutRetireAmount);	
		
		hql.add(" ) from SysUser u ");

		hql.add(" where u.id in(");
		hql.add(" select u2.userId from SysUser u2 where 1=1 ");
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
		
		System.out.println(hql.getSql());
		return hql;
	}
	
	/**
	 * 操作员统计(支付组)
	 */
	public Hql getOptTransactionListForPayDepart(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		System.out.println("startDate:" + startDate + "--endDate:" + endDate);

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
		
		Hql baseHql = getOptPayOrder(startDate, endDate, "u.userNo",Statement.type_2,Statement.SUBTYPE_20);
		Hql hqlNormalOrder = getOrderCount(baseHql);
		hql.addHql(hqlNormalOrder);
		hql.add(",");
		
		Hql hqlTicketCount = getTicketCount(baseHql);
		hql.addHql(hqlTicketCount);
		hql.add(",");
		
		Hql hqlOutAmount = getOrderAmount(baseHql,Statement.type_2,Statement.SUBTYPE_20);
		hql.addHql(hqlOutAmount);
		
		hql.add(",");
		hql.add("('pay')");
		
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
		
		System.out.println(hql.getSql());
		return hql;
	}

	
	/**
	 * 操作员统计(退票组)
	 */
	public Hql getOptTransactionListForRetireDepart(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		System.out.println("startDate:" + startDate + "--endDate:" + endDate);

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
		
		Hql baseHql3 = getOptOrder(startDate, endDate, "u.userNo", "3");
		Hql hqlRetireOrder = getOrderCount(baseHql3);
		hql.addHql(hqlRetireOrder);
		hql.add(",");
		
		Hql baseHql4 = getOptOrder(startDate, endDate, "u.userNo", "4");
		Hql hqlInvalidOrder = getOrderCount(baseHql4);
		hql.addHql(hqlInvalidOrder);
		hql.add(",");
		
		
		Hql hqlInRetireAmount = getOrderAmount(baseHql3,Statement.type_1,Statement.SUBTYPE_11);
		hql.addHql(hqlInRetireAmount);
		hql.add(",");
		
		Hql hqlOutRetireAmount = getOrderAmount(baseHql4,Statement.type_2,Statement.SUBTYPE_21);
		hql.addHql(hqlOutRetireAmount);
		hql.add(",");
		
		hql.add("('retire')");
		
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
		
		System.out.println(hql.getSql());
		return hql;
	}
	
	/**
	 * 操作员统计(倒票组)
	 */
	public Hql getOptTransactionListForResaleDepart(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		System.out.println("startDate:" + startDate + "--endDate:" + endDate);

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
		
		Hql baseHql1 = getOptOrder(startDate, endDate, "u.userNo", "1");
		Hql hqlNormalOrder = getOrderCount(baseHql1);
		hql.addHql(hqlNormalOrder);
		hql.add(",");
		
		Hql baseHql6 = getOptOrder(startDate, endDate, "u.userNo", "6");
		Hql hqlTicketCount = getTicketCount(baseHql6);
		hql.addHql(hqlTicketCount);
		hql.add(",");
		
		Hql hqlInAmount = getOrderAmount(baseHql1,Statement.type_1,Statement.SUBTYPE_10);
		hql.addHql(hqlInAmount);
		hql.add(",");
		
		Hql hqlOutAmount = getOrderAmount(baseHql6,Statement.type_2,Statement.SUBTYPE_20);
		hql.addHql(hqlOutAmount);
		
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
		
		System.out.println(hql.getSql());
		return hql;
	}
	
	
	/**
	 * 操作员统计(出票组)
	 */
	public Hql getOptTransactionListForDrawDepart(Report report)
			throws AppException {
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		System.out.println("startDate:" + startDate + "--endDate:" + endDate);

		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
		
		Hql baseHql1 = getOptOrder(startDate, endDate, "u.userNo", "1");
		Hql hqlNormalOrder = getOrderCount(baseHql1);
		hql.addHql(hqlNormalOrder);
		hql.add(",");
		
		Hql baseHql6 = getOptOrder(startDate, endDate, "u.userNo", "6");
		Hql hqlTicketCount = getTicketCount(baseHql6);
		hql.addHql(hqlTicketCount);
		hql.add(",");
		
		Hql hqlOutAmount = getOrderAmount(baseHql1,Statement.type_2,Statement.SUBTYPE_20);
		hql.addHql(hqlOutAmount);
		
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
		
		System.out.println(hql.getSql());
		return hql;
	}

	/**
	 * @param Hql baseHql
	 * @param statementType
	 *            1:收款 2：付款
	 */
	public Hql getOrderAmount(Hql baseHql, long statementType,long orderSubtype) throws AppException {
		Hql hql = new Hql();
		if (baseHql != null) {
			hql.add(" (select sum(s.totalAmount) from Statement s ");
			hql.add(" where 1=1");
			hql.add(" and s.type=" + statementType);
			hql.add(" and s.orderSubtype=" + orderSubtype);			
			hql.add(" and s.orderId in (select distinct a.id  ");

			hql.addHql(baseHql);

			hql.add(" )  )  ");
			System.out.println(hql.getSql());
		}
		return hql;
	}
	
	/**
	 * @param statementType
	 *            1:收款 2：付款
	 */
	public Hql getStatementAmount(String startDate, String endDate, String userIdExp,long statementType,long orderSubtype) throws AppException {
		Hql hql = new Hql();
		hql.add(" ( ");
		hql.add(" select sum(s.totalAmount) from Statement s ");
		hql.add(" where 1=1");
		hql.add(" and s.type=" + statementType);
		hql.add(" and s.orderSubtype=" + orderSubtype);			
		hql.add(" and s.sysUser.userId="+userIdExp);			

		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql.add(" and  s.statementDate  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		
		hql.add(")  ");
		System.out.println(hql.getSql());
		return hql;
	}

	// 订单数
	public Hql getOrderCount(Hql baseHql) throws AppException {
		Hql hql = new Hql();		
		if (baseHql != null) {
			hql.add(" (select count(distinct a.orderGroup.id) ");
			hql.addHql(baseHql);
			hql.add(" ) ");
			System.out.println(hql.getSql());
		}
		return hql;
	}

	// 机票数（乘机人）
	public Hql getTicketCount(Hql baseHql) throws AppException {
		Hql hql = new Hql();
		
		if (baseHql != null) {
			hql.add(" (select count(distinct p.ticketNumber) from Passenger p ");
			hql.add(" where p.airticketOrder.id in(select distinct a.id  ");
			hql.addHql(baseHql);
			hql.add(" ) ) ");
			System.out.println(hql.getSql());
		}
		return hql;
	}
	
	
	/**
	 *  获取需要统计的订单
	 *  Statement ==>Order
	 *  
	 * @param orderType
	 * 1:正常 2：改签 3：退票 4：废票 5：取消 6:卖出 7：退废票
	*/
	public Hql getOptStatementOrder(String startDate,String endDate,String userIdExp,String orderType,String orderSubtypes) throws AppException {
			Hql hql = new Hql();
			hql.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
			hql.add(" from AirticketOrder o,Statement s");
			hql.add(" where o.id=s.orderId ");
			hql.add(" and s.sysUser.userId="+userIdExp);
			hql.add(" and s.orderSubtype in("+orderSubtypes+")");
			
			hql.addHql(getOrderStatusHql(orderType));
			
			if ("".equals(startDate) == false && "".equals(endDate) == false) {
				hql.add(" and  o.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
				hql.addParamter(startDate);
				hql.addParamter(endDate);
			}
			hql.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");
			return hql;
	}

	
	/**
	 *  获取需要统计的订单
	 *  Statement ==>Order
	 *  
	 * @param orderType
	 * 1:正常 2：改签 3：退票 4：废票 5：取消 6:卖出 7：退废票
	*/
	public Hql getOptStatementOrder(String startDate,String endDate,String userIdExp,String orderType,long statementType,long orderSubtype) throws AppException {
			Hql hql = new Hql();
			hql.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
			hql.add(" from AirticketOrder o,Statement s");
			hql.add(" where o.id=s.orderId ");
			hql.add(" and s.sysUser.userId="+userIdExp);
			hql.add(" and s.type="+statementType);
			hql.add(" and s.orderSubtype="+orderSubtype);
			
			hql.addHql(getOrderStatusHql(orderType));
			
			if ("".equals(startDate) == false && "".equals(endDate) == false) {
				hql.add(" and  s.statementDate  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
				hql.addParamter(startDate);
				hql.addParamter(endDate);
			}
			hql.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");
			return hql;
	}	
	
	/**
	 * 获取需要统计的订单
	 *	@param orderType
	 * 1:正常 2：改签 3：退票 4：废票 5：取消 6:卖出 7：退废票
	*/
	public Hql getOptOrder(String startDate, String endDate, String userNoExp,
			String orderType) throws AppException {
		if (orderType != null && "".equals(orderType) == false) {
			Hql hql = new Hql();
			hql.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
			hql.add(" from AirticketOrder o ");
			hql.add(" where 1=1 ");
			hql.addHql(getOrderStatusHql(orderType));
			
//			hql.add(" and  o.entryOperator like '% "+ userNoExp + "%' ");
			hql.add(" and  o.entryOperator="+ userNoExp+ " ");
			
			if ("".equals(startDate) == false && "".equals(endDate) == false) {
				hql.add(" and  o.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
				hql.addParamter(startDate);
				hql.addParamter(endDate);
			}
			hql.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");
			return hql;
		} else {
			return null;
		}
	}	
	
	/**
	 * @param orderType
	 * @param 1:正常 2：改签 3：退票 4：废票 5：取消 6:卖出 7：退废票
	 * @param 21： 团队正常订单
	 * @param 22: 团队退票订单
	 * */
	public Hql getOrderStatusHql(String orderType){
		Hql hql=new Hql();
		if (orderType == "1") {// 正常订单
			hql.add(" and  o.status in(" + AirticketOrder.GROUP_11 + ")");// 出票成功
		}
		if (orderType == "2") {// 改签订单
			hql.add(" and  o.status in(" + AirticketOrder.GROUP_12
					+ " ) ");// --改签完成
		}
		if (orderType == "3") {// 退票订单
			hql.add(" and  o.status  in(" + AirticketOrder.GROUP_13
					+ ") ");// --退票完成
		}
		if (orderType == "4") {// 废票订单
			hql.add(" and  o.status  in(" + AirticketOrder.GROUP_14
					+ ") ");// --废票完成
		}
		if (orderType == "5") {// 取消订单
			hql.add(" and  o.status  in(" + AirticketOrder.GROUP_15
					+ ") ");
		}
		if (orderType == "6") {// 卖出订单
			hql.add(" and  o.status  in(" + AirticketOrder.GROUP_11 + ","
					+ AirticketOrder.GROUP_13 + ","
					+ AirticketOrder.GROUP_14 + ") ");// ---出票或退废票完成
		}
		if (orderType == "7") {// 退废订单
			hql.add(" and  o.status  in("+ AirticketOrder.GROUP_13 + ","
					+ AirticketOrder.GROUP_14 + ") ");// ---退废票完成
		}
		if (orderType == "21") {// 团队正常订单
			hql.add(" and  o.status  in(" + AirticketOrder.GROUP_21
					+ ") ");
		}
		if (orderType == "22") {// 团队退票订单
			hql.add(" and  o.status  in(" + AirticketOrder.GROUP_22
					+ ") ");
		}
		return hql;
	}
	

	/**
	 *  获取需要统计的订单
	 *  支付组
	*/
	public Hql getOptPayOrder(String startDate,String endDate,String userNoExp,long statementType,long orderSubtype) throws AppException {
			Hql hql = new Hql();
			hql.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
			hql.add(" from AirticketOrder o,Statement s");
			hql.add(" where o.id=s.orderId ");
			hql.add(" and s.type="+statementType);
			hql.add(" and s.orderSubtype="+orderSubtype);
			
			hql.add(" and o.payOperator="+userNoExp);
			if ("".equals(startDate) == false && "".equals(endDate) == false) {
				hql.add(" and  o.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
				hql.addParamter(startDate);
				hql.addParamter(endDate);
			}
			hql.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");
			return hql;
	}	
	
	
//	public Hql getOptOrderByIdExp(String orderIdExp) throws AppException {
//			Hql hql = new Hql();
//			hql.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
//			hql.add(" from AirticketOrder o");
//			hql.add(" where o.id="+orderIdExp);
//			hql.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");
//			return hql;
//	}	
	
	
	// 获取需要统计的订单(经手，备份)
	public Hql _getOptOrder(String startDate, String endDate, String userId,
			String type) throws AppException {
		if (type != null && "".equals(type) == false) {
			Hql hql = new Hql();
			hql.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
			hql.add(" from AirticketOrder o");

			if (type == "1") {// 正常订单
				hql.add(" where  o.status in(" + AirticketOrder.GROUP_11 + ")");// 出票成功
				hql.add(" and o.status  not in(" + AirticketOrder.GROUP_12
						+ "," + AirticketOrder.GROUP_13 + ","
						+ AirticketOrder.GROUP_14 + ") ");// --退、废、改签完成
			}

			if (type == "2") {// 改签订单
				hql.add(" where  o.status in(" + AirticketOrder.GROUP_12
						+ " ) ");// --改签完成
			}

			if (type == "3") {// 退票订单
				hql.add(" where  o.status  in(" + AirticketOrder.GROUP_13
						+ ") ");// --退票完成
			}
			if (type == "4") {// 废票订单
				hql.add(" where  o.status  in(" + AirticketOrder.GROUP_14
						+ ") ");// --废票完成
			}

			if (type == "5") {// 取消订单
				hql.add(" where  o.status  in(" + AirticketOrder.GROUP_15
						+ ") ");
			}

			if (type == "6") {// 卖出订单
				hql.add(" where  o.status  in(" + AirticketOrder.GROUP_11 + ","
						+ AirticketOrder.GROUP_13 + ","
						+ AirticketOrder.GROUP_14 + ") ");// ---出票或退废票完成
			}
			if (type == "7") {// 退废订单
				hql.add(" where  o.status  in("+ AirticketOrder.GROUP_13 + ","
						+ AirticketOrder.GROUP_14 + ") ");// ---退废票完成
			}

			hql.add(" and  exists(select t.orderId from TicketLog t where o.id=t.orderId  and t.sysUser.id="
							+ userId + ")");

			if ("".equals(startDate) == false && "".equals(endDate) == false) {
				hql.add(" and  o.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
				hql.addParamter(startDate);
				hql.addParamter(endDate);
			}
			hql.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");

			return hql;
		} else {
			return null;
		}
	}	
	
//	/**
//	 * 操作员统计
//	 */
//	public List<OptTransaction> getOptTransactionList(Report report)
//			throws AppException {
//		String startDate = report.getStartDate();
//		String endDate = report.getEndDate();
//
//		Hql hql = new Hql();
//		hql.add(" select new com.fdays.tsms.airticket.OptTransaction(u,");
//
//		Hql hqlNormalOrder = getOrderCount(startDate, endDate, "u.userId", "1");
//		hql.addHql(hqlNormalOrder);
//		hql.add(",");
//
//		Hql hqlUmbuchenOrder = getOrderCount(startDate, endDate, "u.userId",
//				"2");
//		hql.addHql(hqlUmbuchenOrder);
//		hql.add(",");
//
//		Hql hqlRetireOrder = getOrderCount(startDate, endDate, "u.userId", "3");
//		hql.addHql(hqlRetireOrder);
//		hql.add(",");
//
//		Hql hqlInvalidOrder = getOrderCount(startDate, endDate, "u.userId", "4");
//		hql.addHql(hqlInvalidOrder);
//		hql.add(",");
//
//		Hql hqlCancelOrder = getOrderCount(startDate, endDate, "u.userId", "5");
//		hql.addHql(hqlCancelOrder);
//		hql.add(",");
//
//		Hql hqlTicketCount = getTicketCount(startDate, endDate, "u.userId", "6");
//		hql.addHql(hqlTicketCount);
//		hql.add(",");
//
//		Hql hqlInAmount = getOrderAmount(startDate, endDate, "u.userId", "1",
//				Statement.type_1);
//		hql.addHql(hqlInAmount);
//		hql.add(",");
//
//		Hql hqlOutAmount = getOrderAmount(startDate, endDate, "u.userId", "1",
//				Statement.type_2);
//		hql.addHql(hqlOutAmount);
//
//		hql.add(",");
//
//		Hql hqlRetireInAmount = getOrderAmount(startDate, endDate, "u.userId",
//				"7", Statement.type_1);
//		hql.addHql(hqlRetireInAmount);
//		hql.add(",");
//
//		Hql hqlRetireOutAmount = getOrderAmount(startDate, endDate, "u.userId",
//				"7", Statement.type_2);
//		hql.addHql(hqlRetireOutAmount);
//
//		hql.add(" ) from SysUser u ");
//
//		hql.add(" where u.id in(");
//		hql.add(" select u2.id from SysUser u2 where 1=1 ");
//		if (report.getOperatorDepart() != null
//				&& report.getOperatorDepart() > 0) {
//			hql.add("   and u2.userDepart = " + report.getOperatorDepart());// 部门
//		}
//
//		if (report.getOperator() != null
//				&& !"".equals(report.getOperator().trim())) {
//			hql.add("  and (u2.userName like '%" + report.getOperator().trim()
//					+ "%' ");// 操作人
//			hql.add(" or u2.userNo like '%" + report.getOperator().trim()
//					+ "%' ))");
//		}
//		hql.add(" ) ");
//
//		System.out.println("startDate:" + startDate + "--endDate:" + endDate);
//		System.out.println(hql.getSql());
//
//		List<OptTransaction> list = new ArrayList();
//		Query query = this.getQuery(hql);
//		if (query != null && query.list() != null && query.list().size() > 0) {
//			list = query.list();
//		}
//		return list;
//	}
}
