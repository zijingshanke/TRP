package com.fdays.tsms.airticket;

import java.util.Comparator;


public class AirticketOrderComparator implements Comparator<Object>{
	
	public AirticketOrderComparator() {
		
	}
	
	public AirticketOrderComparator(String aa){
		
	}
	
	public int compare(Object o1, Object o2) {
		AirticketOrder order1=(AirticketOrder)o1;
		AirticketOrder order2=(AirticketOrder)o2;
		
		Long id1=order1.getId();
		Long id2=order2.getId();
		
//		System.out.println("time1:"+time1);
//		System.out.println("time2:"+time2);
		int flag=id1.compareTo(id2);
		
	
//		System.out.println("flag:"+flag);
		
		
		if (flag>0) {
			return 1;//第一个大于第二个
		}if (flag<0) {
			return -1;//第一个小于第二个
		}else{
			return 0;//等于
		}
	}
	

//	public int compare(Object o1, Object o2) {
//		AirticketOrder order1=(AirticketOrder)o1;
//		AirticketOrder order2=(AirticketOrder)o2;
//		
//		Timestamp time1=order1.getEntryTime();
//		Timestamp time2=order2.getEntryTime();
//		
////		System.out.println("time1:"+time1);
////		System.out.println("time2:"+time2);
//		int flag=time1.compareTo(time2);
//		
//	
////		System.out.println("flag:"+flag);
//		
//		
//		if (flag>0) {
//			return 1;//第一个大于第二个
//		}if (flag<0) {
//			return -1;//第一个小于第二个
//		}else{
//			return 0;//等于
//		}
//	}
	
}
		