package com.fdays.tsms.airticket;

import java.sql.Timestamp;
import java.util.Comparator;


public class TempSaleReportComparator implements Comparator<Object>{
	
	public TempSaleReportComparator() {
		
	}
	
	public TempSaleReportComparator(String aa){
		
	}

	public int compare(Object o1, Object o2) {
		TempSaleReport report1=(TempSaleReport)o1;
		TempSaleReport report2=(TempSaleReport)o2;
		
		Timestamp time1=report1.getOrderTime();
		Timestamp time2=report2.getOrderTime();
		
//		System.out.println("time1:"+time1);
//		System.out.println("time2:"+time2);
		int flag=time1.compareTo(time2);	
	
//		System.out.println("flag:"+flag);		
		
		if (flag>0) {
			return 1;//第一个大于第二个
		}if (flag<0) {
			return -1;//第一个小于第二个
		}else{
			return 0;//等于
		}
	}
	
}
		