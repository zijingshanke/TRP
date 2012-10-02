package com.fdays.tsms.transaction.util;

import java.util.Comparator;

import com.fdays.tsms.transaction.AirticketOrderReport;


public class AirticketOrderReportComparator implements Comparator<Object>{
	
	public AirticketOrderReportComparator() {
		
	}
	
	public AirticketOrderReportComparator(String aa){
		
	}
	
	public int compare(Object o1, Object o2) {
		AirticketOrderReport report1=(AirticketOrderReport)o1;
		AirticketOrderReport report2=(AirticketOrderReport)o2;
		
		Long id1=report1.getOrderId();
		Long id2=report2.getOrderId();
		
		int flag=id1.compareTo(id2);
		
		if (flag>0) {
			return 1;//第一个大于第二个
		}if (flag<0) {
			return -1;//第一个小于第二个
		}else{
			return 0;//等于
		}
	}	
}
		