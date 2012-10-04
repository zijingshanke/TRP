package com.fdays.tsms.transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fdays.tsms.transaction.util.AirticketOrderReportComparator;

public class AirticketReportGroup {
	private long saleOrderFlag = new Long(0);
	private String groupMarkNo = "";

	// --------------
	private List<AirticketOrderReport> orderReportList = new ArrayList<AirticketOrderReport>();

	public AirticketReportGroup() {

	}

	public AirticketReportGroup(List<AirticketOrderReport> orderReportList,
			String groupNo) {
		orderReportList = sortReportListByEntryTime(orderReportList);
		this.orderReportList = orderReportList;
		this.groupMarkNo = groupNo;
	}

	// -------报表结果集分组，无OrderGroup对象
	public static List<AirticketReportGroup> getSubGroupListAsReport(
			List<AirticketOrderReport> orderReportList) {
		System.out
				.println(" getSubGroupListAsReport AirticketOrderReport List size:"
						+ orderReportList.size());
		String temp = "";
		List<AirticketReportGroup> groupList = new ArrayList<AirticketReportGroup>();
		for (int i = 0; i < orderReportList.size(); i++) {
			AirticketOrderReport ao = orderReportList.get(i);
			if (ao != null) {
				if (i == 0) {
					temp = ao.getSubGroupMark();
					groupList.add(new AirticketReportGroup(getSameSubGroupAsReport(
							orderReportList, temp), temp));
					continue;
				}

				if (!ao.getSubGroupMark().trim().equals(temp)) {
					temp = ao.getSubGroupMark().trim();
					groupList.add(new AirticketReportGroup(getSameSubGroupAsReport(
							orderReportList, temp), temp));
					// System.out.println("===add groupList==="+temp);
				}
			}
		}
		System.out.println("exchange AiriticketGroup List Success.."
				+ groupList.size());
		return groupList;
	}

	public static List<AirticketOrderReport> getSameSubGroupAsReport(
			List<AirticketOrderReport> orderReportList, String groupNo) {
		List<AirticketOrderReport> tempOrderList = new ArrayList<AirticketOrderReport>();

		for (int i = 0; i < orderReportList.size(); i++) {
			AirticketOrderReport ao = orderReportList.get(i);
			String tempGroupNo = ao.getGroupId() + "-" + ao.getSubGroupMarkNo();
			if (tempGroupNo.equals(groupNo)) {
				tempOrderList.add(ao);
			}
		}
		return tempOrderList;
	}

	public List<AirticketOrderReport> sortReportListByEntryTime(
			List<AirticketOrderReport> orderReportList) {

		AirticketOrderReportComparator comp = new AirticketOrderReportComparator();
		// 执行排序方法
		Collections.sort(orderReportList, comp);

		return orderReportList;
	}

	public long getSaleOrderFlag() {
		return saleOrderFlag;
	}

	public void setSaleOrderFlag(long saleOrderFlag) {
		this.saleOrderFlag = saleOrderFlag;
	}

	public String getGroupMarkNo() {
		return groupMarkNo;
	}

	public void setGroupMarkNo(String groupMarkNo) {
		this.groupMarkNo = groupMarkNo;
	}

	public List<AirticketOrderReport> getOrderReportList() {
		return orderReportList;
	}

	public void setOrderReportList(List<AirticketOrderReport> orderReportList) {
		this.orderReportList = orderReportList;
	}

}
