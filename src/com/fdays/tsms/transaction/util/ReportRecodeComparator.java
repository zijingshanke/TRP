package com.fdays.tsms.transaction.util;

import java.util.Comparator;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.ReportRecode;

public class ReportRecodeComparator implements Comparator<Object> {
	public ReportRecodeComparator() {

	}

	public ReportRecodeComparator(String aa) {

	}

	public int compare(Object o1, Object o2) {
		ReportRecode report1 = (ReportRecode) o1;
		ReportRecode report2 = (ReportRecode) o2;
		String id1 = Constant.toString(report1.getSubPnr());
		String id2 = Constant.toString(report2.getSubPnr());

		if ("".equals(id1) || "".equals(id2)) {
			Long id3 = Constant.toLong(report1.getReportRownum());
			Long id4 = Constant.toLong(report1.getReportRownum());

			int flag = id3.compareTo(id4);
			if (flag > 0) {
				return 1;// 第一个大于第二个
			}
			if (flag < 0) {
				return -1;// 第一个小于第二个
			} else {
				return 0;// 等于
			}
		} else {
			int flag = id1.compareTo(id2);
			if (flag > 0) {
				return 1;// 第一个大于第二个
			}
			if (flag < 0) {
				return -1;// 第一个小于第二个
			} else {
				return 0;// 等于
			}
		}

	}
}
