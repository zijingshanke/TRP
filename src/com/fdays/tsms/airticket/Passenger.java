package com.fdays.tsms.airticket;


import com.fdays.tsms.airticket._entity._Passenger;

public class Passenger extends _Passenger{
  	private static final long serialVersionUID = 1L;
  	
	public static final long STATES_1 = 1;// 有效
	public static final long STATES_0 = 0;// 无效
	public static final long STATES_3 = 3;// 退
	public static final long STATES_4 = 4;// 废
	public static final long STATES_5 = 5;// 改
	
  	
  	 public String getTicketNumber() {
  		 if (this.ticketNumber==null) {
			return "";
		}
         return this.ticketNumber;
     }  	
}
