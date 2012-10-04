package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.OrderStatement;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.user.UserStore;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportDAOImp extends BaseDAOSupport implements ReportDAO {	
	
	public List getOrderStatementList(Report report)throws AppException{
		Hql hql=new Hql();
		hql.addHql(getOrderStatementListHql(report));		
		
		List<AirticketOrder> list = new ArrayList();
		
		long a1 = System.currentTimeMillis();
		Query query = this.getQuery(hql);
		long a2 = System.currentTimeMillis();
		System.out.println("========Monitor Flag=======this.getQuery(hql) time:" + ((a2 - a1) / 1000) + "s");	
		
		if (query != null) {
			long a = System.currentTimeMillis();
			List<OrderStatement> tempList = query.list();
			long b = System.currentTimeMillis();
			System.out.println("========Monitor Flag====Query List<OrderStatement> time:" + ((b - a) / 1000) + "s");	
			
			if (tempList != null && tempList.size() > 0) {
				long c = System.currentTimeMillis();
				for (int i = 0; i < tempList.size(); i++) {
					OrderStatement orderStatement=tempList.get(i);
					AirticketOrder order=orderStatement.getOrder();
					list.add(order);
				}			
				long d = System.currentTimeMillis();
				System.out.println("========Monitor Flag====Exchange Query Result==>List<Order> time:" + ((d - c) / 1000) + "s");	
			}
		}		
		System.out.println("---getOrderStatementList list size  ---->" + list.size());
		return list;
	}
	
	public Hql getOrderStatementListHql(Report report) throws AppException {
		Hql hql = new Hql();
		hql.add(" select new com.fdays.tsms.airticket.OrderStatement(b");
		
		hql.add(","+OrderStatementHqlUtil.getOrderStatementHql("b.id"));   
		
		hql.add(")");
		
		if(Constant.toLong(report.getReportType()).compareTo(Report.ReportType11)==0){
			String satementSubType=Statement.SUBTYPE_11+","+Statement.SUBTYPE_21;
			hql.addHql(getOrderListHqlByStatementDate(report,satementSubType));
		}else{
			hql.addHql(getOrderListHql(report));
		}
		System.out.println("getOrderStatementListHql:"+hql);
		return hql;
	}

	public Hql getOrderListHql(Report report) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirticketOrder b  where exists(select distinct orderGroup.id  ");
		hql.add(" from AirticketOrder a where 1=1");
		//hql.add(" and a.status not in(88) ");
//		hql.add(" and a.id=10621 ");

		if (report.getTicketTypeGroup() != null
				&& "".equals(report.getTicketTypeGroup()) == false) {
			hql.add(" and a.ticketType in(" + report.getTicketTypeGroup()
					+ ") ");
		}
		if (report.getTranTypeGroup() != null
				&& "".equals(report.getTranTypeGroup()) == false) {
			hql.add(" and a.tranType in(" + report.getTranTypeGroup() + ") ");
		}

		if (report.getStatusGroup() != null
				&& "".equals(report.getStatusGroup()) == false) {
			hql.add(" and a.status  in (" + report.getStatusGroup() + ") ");
		}

		if (report.getOperator() != null
				&& !"".equals(report.getOperator().trim())) {
			hql.add(" and( a.entryOperator like ? or a.entryOperator like ? ) ");// 操作人
			hql.addParamter("%" + report.getOperator().trim() + "%");
			hql.addParamter("%"+ UserStore.getUserNoByName(report.getOperator().trim())
							.trim() + "%");
		}

		// 按日期搜索
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		if ("".equals(startDate) == false && "".equals(endDate) == true) {
			hql.add(" and  a.entryTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate) == true && "".equals(endDate) == false) {
			hql.add(" and  a.entryTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		if (report.getSalePlatformIds() != null) {
			String salePlatformIds = StringUtil.getStringByArray(report
					.getSalePlatformIds(),",");
			hql.add("  and  a.platform.id  in (" + salePlatformIds
					+ ") and a.businessType=1 ");
		}

		if (report.getBuyPlatformIds() != null) {
			String buyPlatformIds = StringUtil.getStringByArray(report
					.getBuyPlatformIds(),",");
			hql.add("  and  a.platform.id  in (" + buyPlatformIds
					+ ") and a.businessType=2 ");
		}

		if (report.getReceiveAccountIds() != null) {
			String receiveAccountIds = StringUtil.getStringByArray(report
					.getReceiveAccountIds(),",");
			hql.add("  and  a.account.id  in (" + receiveAccountIds
					+ ") and a.businessType=1 ");
		}

		if (report.getPayAccountIds() != null) {
			String payAccountIds = StringUtil.getStringByArray(report
					.getPayAccountIds(),",");
			hql.add("  and  a.account.id  in (" + payAccountIds
					+ ") and a.businessType=2 ");
		}

		if (report.isRakeOff()) {
			hql.add(" and a.rakeOff > 0");
		}

		hql.add(" and b.orderGroup.id=a.orderGroup.id and b.subGroupMarkNo=a.subGroupMarkNo)");

		hql.add("  order by b.orderGroup.id,b.subGroupMarkNo desc ");

		System.out.println("---report----sql---" + hql.toString());
		
		return hql;
	}
	
	public Hql getOrderListHqlByStatementDate(Report report,String statementSubType) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirticketOrder b  where exists(select distinct orderGroup.id  ");
		hql.add(" from AirticketOrder a  where 1=1 ");
//		hql.add(" and a.id=9536 ");

		if (report.getTicketTypeGroup() != null
				&& "".equals(report.getTicketTypeGroup()) == false) {
			hql.add(" and a.ticketType in(" + report.getTicketTypeGroup()
					+ ") ");
		}
		if (report.getTranTypeGroup() != null
				&& "".equals(report.getTranTypeGroup()) == false) {
			hql.add(" and a.tranType in(" + report.getTranTypeGroup() + ") ");
		}

		if (report.getStatusGroup() != null
				&& "".equals(report.getStatusGroup()) == false) {
			hql.add(" and a.status  in (" + report.getStatusGroup() + ") ");
		}

		if (report.getOperator() != null
				&& !"".equals(report.getOperator().trim())) {
			hql.add(" and( a.entryOperator like ? or a.entryOperator like ? ) ");// 操作人
			hql.addParamter("%" + report.getOperator().trim() + "%");
			hql.addParamter("%"+ UserStore.getUserNoByName(report.getOperator().trim())
							.trim() + "%");
		}

		hql.add(" and a.id in( ");
		
		hql.add(" select s.orderId from Statement s where 1=1 ");
		
		
		// 按日期搜索
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		if ("".equals(startDate) == false && "".equals(endDate) == true) {
			hql.add(" and  s.statementDate > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate) == true && "".equals(endDate) == false) {
			hql.add(" and  s.statementDate < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql.add(" and  s.statementDate  between to_date(?,'yyyy-MM-dd HH24:mi:ss') and to_date(?,'yyyy-MM-dd HH24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		
		
		if(!"".equals(Constant.toString(statementSubType))){
			hql.add(" and s.orderSubtype in( "+statementSubType+" ) ");
		}
		
		
		hql.add(" ) ");

		if (report.getSalePlatformIds() != null) {
			String salePlatformIds = StringUtil.getStringByArray(report
					.getSalePlatformIds(),",");
			hql.add("  and  a.platform.id  in (" + salePlatformIds
					+ ") and a.businessType=1 ");
		}

		if (report.getBuyPlatformIds() != null) {
			String buyPlatformIds = StringUtil.getStringByArray(report
					.getBuyPlatformIds(),",");
			hql.add("  and  a.platform.id  in (" + buyPlatformIds
					+ ") and a.businessType=2 ");
		}

		if (report.getReceiveAccountIds() != null) {
			String receiveAccountIds = StringUtil.getStringByArray(report
					.getReceiveAccountIds(),",");
			hql.add("  and  a.account.id  in (" + receiveAccountIds
					+ ") and a.businessType=1 ");
		}

		if (report.getPayAccountIds() != null) {
			String payAccountIds = StringUtil.getStringByArray(report
					.getPayAccountIds(),",");
			hql.add("  and  a.account.id  in (" + payAccountIds
					+ ") and a.businessType=2 ");
		}

		if (report.isRakeOff()) {
			hql.add(" and a.rakeOff > 0");
		}

		hql.add(" and b.orderGroup.id=a.orderGroup.id and b.subGroupMarkNo=a.subGroupMarkNo)");

		hql.add("  order by b.orderGroup.id,b.subGroupMarkNo desc ");

		System.out.println("---report----sql---" + hql.toString());
		
		return hql;
	}

	public void printSelectedPlatformAcount(Report report) {
		if (report != null) {
			String[] salePlatformIds = report.getSalePlatformIds();
			String[] receiveAccountIds = report.getReceiveAccountIds();

			System.out.println("salePlatformIds=======>"
					+ StringUtil.getStringByArray(salePlatformIds,","));
			System.out.println("receiveAccountIds=======>"
					+ StringUtil.getStringByArray(receiveAccountIds,","));
		}
	}
}
