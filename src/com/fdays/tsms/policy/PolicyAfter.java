package com.fdays.tsms.policy;


import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fdays.tsms.airticket.AirlineStore;
import com.fdays.tsms.policy._entity._PolicyAfter;

public class PolicyAfter extends _PolicyAfter{
  	private static final long serialVersionUID = 1L;
  	private String endDateStr;
  	private long airlinePolicyAfterId;
  	
	public long getAirlinePolicyAfterId() {
		return airlinePolicyAfterId;
	}
	public void setAirlinePolicyAfterId(long airlinePolicyAfterId) {
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}
	
    public java.math.BigDecimal getQuota() {
    	if(this.quota == null){
    		quota = BigDecimal.ZERO;
    	}
        return this.quota;
    }
    
    public void setQuota(java.math.BigDecimal quota) {
    	if(quota == null){
    		quota = BigDecimal.ZERO;
    	}
        this.quota = quota;
    }
    
    public java.math.BigDecimal getRate() {
    	if(this.rate == null){
    		return BigDecimal.ZERO;
    	}
        return this.rate;
    }
    
    public void setRate(java.math.BigDecimal rate) {
    	if(rate == null){
    		rate = BigDecimal.ZERO;
    	}
        this.rate = rate;
    }
    
  
    public Long getTravelType() {
    	if(this.travelType == null){
    		travelType = 0l;
    	}
        return this.travelType;
    }
    
    public void setTravelType(Long travelType) {
    	if(travelType == null){
    		travelType = 0l;
    	}
        this.travelType = travelType;
    }

    public Long getTicketType() {
    	if(this.ticketType == null){
    		ticketType = 0l;
    	}
        return this.ticketType;
    }
    
    public void setTicketType(Long ticketType) {
    	if(ticketType == null){
    		ticketType = 0l;
    	}
        this.ticketType = ticketType;
    }

    public Long getStatus() {
    	if(this.status == null){
    		status = 0l;
    	}
        return this.status;
    }
    
    public void setStatus(Long status) {
    	if(status == null){
    		status = 0l;
    	}
        this.status = status;
    }
    public Long getTicketNum() {
    	if(this.ticketNum == null){
    		ticketNum = 0l;
    	}
        return this.ticketNum;
    }
    
    public void setTicketNum(Long ticketNum) {
    	if(ticketNum == null){
    		ticketNum = 0l;
    	}
        this.ticketNum = ticketNum;
    }


    public String getStatusInfo() {
    	if(this.status == null || this.status==0 ){
    		return "其它";
    	}
    	if(this.status==1){
    		return "启用";
    	}
		 return "停用";
    }
	public String getTravelTypeInfo(){
		if(this.travelType == null || this.travelType == 0){
			return "其它";
		}
		if(this.travelType == 1){
			return "单程";
		}
		return "往返";
	}
	
	public String getTicketTypeInfo(){
		if(this.ticketType == null || this.ticketType == 0){
			return "其它";
		}
		if(this.ticketType == 1){
			return "客票类型1";
		}
		return "客票类型2";
	}
	
	public boolean agreeStartEnd(String str) 												//航段
	{	
		if(this.startEnd == null){		//如果政策不作判断，则黙认符合
			return true;
		}
		//如果机票没有注明航段或都注明的航段格式错误(先处理此判断，可避免下面str.substring(3)报超出索引的异常)	
		if((str+"").length()<7 ){											
			return  false;
		}
		String startEndUpCase = (this.startEnd+"").toUpperCase();
		if(startEndUpCase.contains("*-*") ||  startEndUpCase.contains(str.toUpperCase()) 
				||startEndUpCase.contains("*"+str.substring(3).toUpperCase()) 
				||startEndUpCase.contains(str.substring(0,3).toUpperCase()+"-*")){
			return true;
		}
		return false;
	}
	
	public boolean agreeStartEndExcept(String str) 											//不适航段
	{	
		if(this.startEndExcept == null){	//如果政策不作判断,则黙认符合
			return true;
		}
		//如果机票没有注明航段或都注明的航段格式错误(先处理此判断，可避免下面str.substring(3)报超出索引的异常)
		if((str+"").length()<7 ){ 												
			return  true;
		}
		String startEndExceptUpCase = (this.startEndExcept+"").toUpperCase();
		if(startEndExceptUpCase.contains("*-*") || startEndExceptUpCase.contains(str.toUpperCase()) 
				||startEndExceptUpCase.contains("*"+str.substring(3).toUpperCase()) 
				||startEndExceptUpCase.contains(str.substring(0,3).toUpperCase()+"-*")){
			return false;
		}
		return true;
	}
	
	public boolean agreeFlightCode(String str) 												//航班
	{
		if(this.flightCode == null){
			return true;
		}
		if((this.flightCode+"").toUpperCase().contains(str.toUpperCase())){
			return true;
		}
//		System.out.println("agreeFlightCode:false  "+str);
		return false;
	}
	
  
	public boolean agreeFlightCodeExcept(String str) 										//不适航班
	{
		if(this.flightCodeExcept == null){
			return true;
		}
		if((this.flightCodeExcept+"").toUpperCase().contains(str.toUpperCase())){
//			System.out.println("agreeFlightCodeExcept:false  "+str);
			return false;
		}
		return true;
	}
	
	public boolean agreeFlightClass(String str)												//舱位
	{
		if(this.flightClass == null){
			return true;
		}
		if((this.flightClass+"").toUpperCase().contains(str.toUpperCase())){
			return true;
		}
//		System.out.println("agreeFlightClass:false  "+str);
		return false;
	}
	
	public boolean agreeFlightClassExcept(String str)										//不适舱位
	{
		if(this.flightClassExcept == null){
			return true;
		}
		if((this.flightClassExcept+"").toUpperCase().contains(str.toUpperCase())){
//			System.out.println("agreeFlightClassExcept:false  "+str);
			return false;
		}
		return true;
	}
	
	public boolean agreeDiscount(String code)												//折扣
	{		
		long tempDiscount=AirlineStore.getDiscountRateByCompany(this.getAirlinePolicyAfter().getCarrier(),code);
		if(tempDiscount > this.discount){
		  return true;
		}
		else{
			return false;
		}
	}
	public boolean agreeDate(Timestamp flightDate){											//时间
		if(this.beginDate == null || this.endDate == null){
			return true;
		}
		if(this.beginDate.compareTo(flightDate) <= 0 && this.endDate.compareTo(flightDate) >=0){
			return true;
		}
		return false;
	}
	public boolean agreeTickNum(Long tickNum){												//票数
		if(this.ticketNum == null || this.ticketNum > tickNum){
			return true;
		}
//		System.out.println("agreeTickNum:false  "+tickNum);
		return false;
	}
	public String getEndDateStr() {
		return endDateStr;
	}
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}
	
	
}
