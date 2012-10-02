package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.user.UserStore;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportDAOImp extends BaseDAOSupport implements ReportDAO {
	public List getOrderList(Report report) throws AppException {
		Hql hql = new Hql();
		hql
				.add(" from AirticketOrder b where exists(select distinct orderGroup.id  ");
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
			hql
					.add(" and( a.entryOperator like ? or a.entryOperator like ? ) ");// 操作人
			hql.addParamter("%" + report.getOperator().trim() + "%");
			hql.addParamter("%"
					+ UserStore.getUserNoByName(report.getOperator().trim())
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

		hql
				.add(" and b.orderGroup.id=a.orderGroup.id and b.subGroupMarkNo=a.subGroupMarkNo)");

		hql.add("  order by b.orderGroup.id,b.subGroupMarkNo desc ");

		System.out.println("---report----sql---" + hql.toString());

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

	// 获取需要统计的订单
	public Hql getOptOrder(String startDate, String endDate, String userId,
			String type) throws AppException {
		if (type != null && "".equals(type) == false) {
			Hql hql = new Hql();
			hql
					.add(" from AirticketOrder a where exists(select distinct o.orderGroup.id  ");
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

			hql
					.add(" and  exists(select t.orderId from TicketLog t where o.id=t.orderId  and t.sysUser.id="
							+ userId + ")");

			if ("".equals(startDate) == false && "".equals(endDate) == false) {
				hql
						.add(" and  o.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
				hql.addParamter(startDate);
				hql.addParamter(endDate);
			}
			hql
					.add(" and o.orderGroup.id=a.orderGroup.id and o.subGroupMarkNo=a.subGroupMarkNo)");

			return hql;
		} else {
			return null;
		}
	}
}
