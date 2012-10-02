package com.fdays.tsms.transaction.util;

import java.util.Comparator;

import com.fdays.tsms.transaction.ReportCompare;

public class PlatformCompareComparator implements Comparator<Object> {
	public PlatformCompareComparator() {

	}

	public PlatformCompareComparator(String aa) {

	}

	public int compare(Object o1, Object o2) {
		ReportCompare report1 = (ReportCompare) o1;
		ReportCompare report2 = (ReportCompare) o2;

		// Long id1=report1.getSubPnr();
		// Long id2=report2.getOrderId();
		//		
		// int flag=id1.compareTo(id2);
		//		
		// if (flag>0) {
		// return 1;//第一个大于第二个
		// }if (flag<0) {
		// return -1;//第一个小于第二个
		// }else{
		// return 0;//等于
		// }

		String id1 = report1.getSubPnr();
		String id2 = report2.getSubPnr();

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
