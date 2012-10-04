package com.fdays.tsms.airticket.util;

import java.sql.Timestamp;
import java.util.Comparator;
import com.fdays.tsms.airticket.GeneralReport;

public class GeneralReportComparator implements Comparator<Object>{
	
	public GeneralReportComparator() {
		
	}	

	public int compare(Object o1, Object o2) {
		GeneralReport report1=(GeneralReport)o1;
		GeneralReport report2=(GeneralReport)o2;
		
		Timestamp time1=report1.getEntryTime();
		Timestamp time2=report2.getEntryTime();

		int flag=time1.compareTo(time2);	
		
		if (flag>0) {
			return 1;//第一个大于第二个
		}if (flag<0) {
			return -1;//第一个小于第二个
		}else{
			return 0;//等于
		}
	}	
}
		