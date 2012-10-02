package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.OrderStatement;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.user.UserStore;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportDAOImp extends BaseDAOSupport implements ReportDAO {	
	public List getOrderStatementList(Report report)throws AppException{
		Hql hql=new Hql();
		hql.addHql(getOrderStatementListHql(report));
		
		List<AirticketOrder> list = new ArrayList();
		Query query = this.getQuery(hql);
		if (query != null) {
			List<OrderStatement> tempList = query.list();
			if (tempList != null && tempList.size() > 0) {
				for (int i = 0; i < tempList.size(); i++) {
					OrderStatement orderStatement=tempList.get(i);
					AirticketOrder order=orderStatement.getOrder();
					list.add(order);
				}				
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
		
		hql.addHql(getOrderListHql2(report));
		System.out.println("getOrderStatementListHql:"+hql);
		return hql;
	}

	
	
	public List getOrderList(Report report)throws AppException{
		Hql hql=new Hql();
		hql.addHql(getOrderListHql(report));
		
		List list = new ArrayList();
		Query query = this.getQuery(hql);
		if (query != null) {
			list = query.list();
			if (list != null && list.size() > 0) {
				return list;
			}
		}

		System.out.println("---report group list size  ---->" + list.size());
		return list;
	}
	
	public Hql getOrderListHql2(Report report) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirticketOrder b  where exists(select distinct orderGroup.id  ");
		hql.add("from AirticketOrder a where 1=1");
		//hql.add(" and a.status not in(88) ");

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
					.getSalePlatformIds());
			hql.add("  and  a.platform.id  in (" + salePlatformIds
					+ ") and a.businessType=1 ");
		}

		if (report.getBuyPlatformIds() != null) {
			String buyPlatformIds = StringUtil.getStringByArray(report
					.getBuyPlatformIds());
			hql.add("  and  a.platform.id  in (" + buyPlatformIds
					+ ") and a.businessType=2 ");
		}

		if (report.getReceiveAccountIds() != null) {
			String receiveAccountIds = StringUtil.getStringByArray(report
					.getReceiveAccountIds());
			hql.add("  and  a.account.id  in (" + receiveAccountIds
					+ ") and a.businessType=1 ");
		}

		if (report.getPayAccountIds() != null) {
			String payAccountIds = StringUtil.getStringByArray(report
					.getPayAccountIds());
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
	
	
	public Hql getOrderListHql(Report report) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirticketOrder b where exists(select distinct orderGroup.id  ");
		hql.add("from AirticketOrder a where 1=1 and a.status not in(88) ");

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
					.getSalePlatformIds());
			hql.add("  and  a.platform.id  in (" + salePlatformIds
					+ ") and a.businessType=1 ");
		}

		if (report.getBuyPlatformIds() != null) {
			String buyPlatformIds = StringUtil.getStringByArray(report
					.getBuyPlatformIds());
			hql.add("  and  a.platform.id  in (" + buyPlatformIds
					+ ") and a.businessType=2 ");
		}

		if (report.getReceiveAccountIds() != null) {
			String receiveAccountIds = StringUtil.getStringByArray(report
					.getReceiveAccountIds());
			hql.add("  and  a.account.id  in (" + receiveAccountIds
					+ ") and a.businessType=1 ");
		}

		if (report.getPayAccountIds() != null) {
			String payAccountIds = StringUtil.getStringByArray(report
					.getPayAccountIds());
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
					+ StringUtil.getStringByArray(salePlatformIds));
			System.out.println("receiveAccountIds=======>"
					+ StringUtil.getStringByArray(receiveAccountIds));
		}
	}

	
}
