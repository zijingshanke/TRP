package com.fdays.tsms.policy;


import java.math.BigDecimal;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.policy._entity._PolicyAfter;

public class PolicyAfter extends _PolicyAfter{
  	private static final long serialVersionUID = 1L;
  	private long id;
  	private long airlinePolicyAfterId;
	public long getAirlinePolicyAfterId() {
		return airlinePolicyAfterId;
	}
	public void setAirlinePolicyAfterId(long airlinePolicyAfterId) {
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	

    public String getStatusInfo() {
    	if(this.status==0 ||this.status == null ){
    		return "无";
    	}
    	if(this.status==1){
    		return "启用";
    	}
		 return "停用";
    }
	public String getTravelTypeInfo(){
		if(this.travelType == 0 || this.travelType == null){
			return "无类型";
		}
		if(this.travelType == 1){
			return "单程";
		}
		return "往返";
	}
	
	public String getTicketTypeInfo(){
		if(this.ticketType == 0 || this.ticketType == null){
			return "无类型";
		}
		if(this.ticketType == 1){
			return "客票类型1";
		}
		return "客票类型2";
	}
	
	public boolean isValidAirline(String str)
	{
		
		return true;
	}
	
	public BigDecimal rateAfterByOrder(AirticketOrder order) //返回后返的利润。
	{
		
		
		
		return null;
	}
}
