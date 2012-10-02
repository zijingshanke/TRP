package com.fdays.tsms.policy;

import java.sql.Timestamp;

import com.fdays.tsms.policy._entity._IndicatorStatistics;

public class IndicatorStatistics extends _IndicatorStatistics{
  	private static final long serialVersionUID = 1L;
	private long airlinePolicyAfterId;
	public long getAirlinePolicyAfterId() {
		return airlinePolicyAfterId;
	}
	public void setAirlinePolicyAfterId(long airlinePolicyAfterId) {
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}
  	
	public Long getIsAmount() {
		if(this.isAmount == null){
			this.isAmount = 0l;
		}
        return this.isAmount;
    }
	
	public String getIsAmountValue(){
		if(this.isAmount == 1l){
			return "是";
		}
		return "否";
	}
    
    public void setIsAmount(Long isAmount) {
    	if(isAmount == null){
    		isAmount = 0l;
    	}
        this.isAmount = isAmount;
    }

    public Long getIsAward() {
    	if(this.isAward == null){
    		this.isAward = 0l;
    	}
        return this.isAward;
    }
    
    public String getIsAwardValue(){
		if(this.isAward == 1l){
			return "是";
		}
		return "否";
	}
    
    public void setIsAward(Long isAward) {
    	if(isAward == null){
    		isAward = 0l;
    	}
        this.isAward = isAward;
    }

    public Long getIsHighClass() {
    	if(this.isHighClass == null){
    		this.isHighClass = 0l;
    	}
        return this.isHighClass;
    }
    
    public String getIsHighClassValue(){
		if(this.isHighClass == 1l){
			return "是";
		}
		return "否";
	}
    
    public void setIsHighClass(Long isHighClass) {
    	if(isHighClass == null){
    		isHighClass = 0l;
    	}
        this.isHighClass = isHighClass;
    }
    
    public Long getTravelType() {
    	if(this.travelType == null){
    		this.travelType = 1l;
    	}
        return this.travelType;
    }
    
    public String getTravelTypeValue(){
		if(this.travelType == 1l){
			return "单程";
		}
		return "其它";
	}
    
    public Long getTicketType() {
    	if(this.ticketType == null){
    		this.ticketType = 1l;
    	}
        return this.ticketType;
    }
    public String getTicketTypeValue(){
		if(this.ticketType == 1l){
			return "普通";
		}
		return "其它";
	}
    
	public boolean agreeFlightPoint(String str){	
		if(this.flightPoint == null){			//如果政策不作判断，则黙认符合
			return true;
		}
		//如果机票没有注明航段或都注明的航段格式错误(先处理此判断，可避免下面str.substring(3)报索引越界的异常)	
		if((str+"").length()<7 ){											
			return  false;
		}
		String flightPoint= (this.flightPoint+"").toUpperCase();
		if(flightPoint.contains("*-*") ||  flightPoint.contains(str.toUpperCase()) 
				||flightPoint.contains("*"+str.substring(3).toUpperCase()) 
				||flightPoint.contains(str.substring(0,3).toUpperCase()+"-*")){
			return true;
		}
		return false;
	}
	
	public boolean agreeFlightPointExcept(String str) 											//不适航段
	{	
		if(this.flightPointExcept == null){				//如果政策不作判断,则黙认符合
			return true;
		}
		//如果机票没有注明航段或都注明的航段格式错误(先处理此判断，可避免下面str.substring(3)报索引越界的异常)
		if((str+"").length()<7 ){ 												
			return  true;
		}
		String flightPointExceptUpCase = (this.flightPointExcept+"").toUpperCase();
		if(flightPointExceptUpCase.contains("*-*") || flightPointExceptUpCase.contains(str.toUpperCase()) 
				||flightPointExceptUpCase.contains("*"+str.substring(3).toUpperCase()) 
				||flightPointExceptUpCase.contains(str.substring(0,3).toUpperCase()+"-*")){
//			System.out.println("startEndExcept2:false  "+str);
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
	public boolean agreeDate(Timestamp flightDate){											//时间
		if(this.beginDate == null || this.endDate == null){
			return true;
		}
		if(this.beginDate.compareTo(flightDate) <= 0 && this.endDate.compareTo(flightDate) >=0){
			return true;
		}
		return false;
	}
	
	
}
