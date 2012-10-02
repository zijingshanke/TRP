package com.fdays.tsms.airticket;


import com.fdays.tsms.airticket._entity._Passenger;

public class Passenger extends _Passenger{
  	private static final long serialVersionUID = 1L;
  	
  	 public String getTicketNumber() {
  		 if (this.ticketNumber==null) {
			return "";
		}
         return this.ticketNumber;
     }
  	
}
