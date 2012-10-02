package com.fdays.tsms.transaction.dao;

import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.airticket.OrderStatement;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.AirticketOrderReport;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.user.UserStore;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AirticketOrderReportDAOImp extends BaseDAOSupport implements
		AirticketOrderReportDAO {

	public List<AirticketOrderReport> getAirticketOrderReportList(Report report) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirticketOrderReport b  where b.groupId in(select distinct groupId  ");
		hql.add(" from AirticketOrderReport a where 1=1");

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
//
//		if (report.getOperator() != null
//				&& !"".equals(report.getOperator().trim())) {
//			hql.add(" and( a.entryOperator like ? or a.entryOperator like ? ) ");// 操作人
//			hql.addParamter("%" + report.getOperator().trim() + "%");
//			hql.addParamter("%"+ UserStore.getUserNoByName(report.getOperator()) + "%");
//		}

		//按日期搜索
		String startDate = report.getStartDate();
		String endDate = report.getEndDate();
		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		if (report.getSalePlatformIds() != null) {
			String salePlatformIds = StringUtil.getStringByArray(report
					.getSalePlatformIds(),",");
			hql.add("  and  a.platformId  in (" + salePlatformIds
					+ ") and a.businessType=1 ");
		}

		if (report.getBuyPlatformIds() != null) {
			String buyPlatformIds = StringUtil.getStringByArray(report
					.getBuyPlatformIds(),",");
			hql.add("  and  a.platformId  in (" + buyPlatformIds
					+ ") and a.businessType=2 ");
		}

//		if (report.getReceiveAccountIds() != null) {
//			String receiveAccountIds = StringUtil.getStringByArray(report
//					.getReceiveAccountIds());
//			hql.add("  and  a.InAccountId  in (" + receiveAccountIds
//					+ ") and a.businessType=1 ");
//		}
//		if (report.getPayAccountIds() != null) {
//			String payAccountIds = StringUtil.getStringByArray(report
//					.getPayAccountIds());
//			hql.add("  and  a.account.id  in (" + payAccountIds
//					+ ") and a.businessType=2 ");
//		}
//		if (report.isRakeOff()) {
//			hql.add(" and a.rakeOff > 0");
//		}

		hql.add(" and b.groupId=a.groupId and b.subGroupMarkNo=a.subGroupMarkNo)");

		hql.add("  order by b.groupId,b.subGroupMarkNo desc ");

		System.out.println("---report----sql---" + hql.toString());
		
		Query query = this.getQuery(hql);
		List<AirticketOrderReport> tempList = null;
		if (query != null && query.list() != null) {
			tempList = query.list();
			return tempList;
		}
		return tempList;
	}
}



