package com.fdays.tsms.airticket._entity;



/**
 * AirticketOrder generated by hbm2java
 */


public class _AirticketOrder 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected String airOrderNo;
     protected String groupMarkNo;
     protected String drawPnr;
     protected String subPnr;
     protected String bigPnr;
     protected java.math.BigDecimal ticketPrice;
     protected java.math.BigDecimal rebate;
     protected Long adultCount;
     protected Long childCount;
     protected Long babyCount;
     protected java.math.BigDecimal airportPrice;
     protected java.math.BigDecimal fuelPrice;
     protected java.math.BigDecimal documentPrice;
     protected java.math.BigDecimal insurancePrice;
     protected java.math.BigDecimal handlingCharge;
     protected String currentOperator;
     protected String drawer;
     protected Long ticketType;
     protected Long tranType;
     protected java.sql.Timestamp optTime;
     protected String memo;
     protected Long status;
     protected java.util.Set flights = new java.util.HashSet(0);
     protected java.util.Set passengers = new java.util.HashSet(0);
     protected com.fdays.tsms.transaction.Statement statement;
     protected com.fdays.tsms.transaction.Agent agent;

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public String getAirOrderNo() {
        return this.airOrderNo;
    }
    
    public void setAirOrderNo(String airOrderNo) {
        this.airOrderNo = airOrderNo;
    }
    


    public String getGroupMarkNo() {
        return this.groupMarkNo;
    }
    
    public void setGroupMarkNo(String groupMarkNo) {
        this.groupMarkNo = groupMarkNo;
    }
    


    public String getDrawPnr() {
        return this.drawPnr;
    }
    
    public void setDrawPnr(String drawPnr) {
        this.drawPnr = drawPnr;
    }
    


    public String getSubPnr() {
        return this.subPnr;
    }
    
    public void setSubPnr(String subPnr) {
        this.subPnr = subPnr;
    }
    


    public String getBigPnr() {
        return this.bigPnr;
    }
    
    public void setBigPnr(String bigPnr) {
        this.bigPnr = bigPnr;
    }
    


    public java.math.BigDecimal getTicketPrice() {
        return this.ticketPrice;
    }
    
    public void setTicketPrice(java.math.BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    


    public java.math.BigDecimal getRebate() {
        return this.rebate;
    }
    
    public void setRebate(java.math.BigDecimal rebate) {
        this.rebate = rebate;
    }
    


    public Long getAdultCount() {
        return this.adultCount;
    }
    
    public void setAdultCount(Long adultCount) {
        this.adultCount = adultCount;
    }
    


    public Long getChildCount() {
        return this.childCount;
    }
    
    public void setChildCount(Long childCount) {
        this.childCount = childCount;
    }
    


    public Long getBabyCount() {
        return this.babyCount;
    }
    
    public void setBabyCount(Long babyCount) {
        this.babyCount = babyCount;
    }
    


    public java.math.BigDecimal getAirportPrice() {
        return this.airportPrice;
    }
    
    public void setAirportPrice(java.math.BigDecimal airportPrice) {
        this.airportPrice = airportPrice;
    }
    


    public java.math.BigDecimal getFuelPrice() {
        return this.fuelPrice;
    }
    
    public void setFuelPrice(java.math.BigDecimal fuelPrice) {
        this.fuelPrice = fuelPrice;
    }
    


    public java.math.BigDecimal getDocumentPrice() {
        return this.documentPrice;
    }
    
    public void setDocumentPrice(java.math.BigDecimal documentPrice) {
        this.documentPrice = documentPrice;
    }
    


    public java.math.BigDecimal getInsurancePrice() {
        return this.insurancePrice;
    }
    
    public void setInsurancePrice(java.math.BigDecimal insurancePrice) {
        this.insurancePrice = insurancePrice;
    }
    


    public java.math.BigDecimal getHandlingCharge() {
        return this.handlingCharge;
    }
    
    public void setHandlingCharge(java.math.BigDecimal handlingCharge) {
        this.handlingCharge = handlingCharge;
    }
    


    public String getCurrentOperator() {
        return this.currentOperator;
    }
    
    public void setCurrentOperator(String currentOperator) {
        this.currentOperator = currentOperator;
    }
    


    public String getDrawer() {
        return this.drawer;
    }
    
    public void setDrawer(String drawer) {
        this.drawer = drawer;
    }
    


    public Long getTicketType() {
        return this.ticketType;
    }
    
    public void setTicketType(Long ticketType) {
        this.ticketType = ticketType;
    }
    


    public Long getTranType() {
        return this.tranType;
    }
    
    public void setTranType(Long tranType) {
        this.tranType = tranType;
    }
    


    public java.sql.Timestamp getOptTime() {
        return this.optTime;
    }
    
    public void setOptTime(java.sql.Timestamp optTime) {
        this.optTime = optTime;
    }
    


    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public java.util.Set getFlights() {
        return this.flights;
    }
    
    public void setFlights(java.util.Set flights) {
        this.flights = flights;
    }
    


    public java.util.Set getPassengers() {
        return this.passengers;
    }
    
    public void setPassengers(java.util.Set passengers) {
        this.passengers = passengers;
    }
    


    public com.fdays.tsms.transaction.Statement getStatement() {
        return this.statement;
    }
    
    public void setStatement(com.fdays.tsms.transaction.Statement statement) {
        this.statement = statement;
    }
    


    public com.fdays.tsms.transaction.Agent getAgent() {
        return this.agent;
    }
    
    public void setAgent(com.fdays.tsms.transaction.Agent agent) {
        this.agent = agent;
    }
    




  // The following is extra code specified in the hbm.xml files

  public Object clone()
  {
    Object o = null;
    try
    {
      o = super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      e.printStackTrace();
    }
    return o;
}

private String thisAction="";
 public String getThisAction()
 {
     return thisAction;
 }


public void setThisAction(String thisAction)
 {
     this.thisAction=thisAction;
 }

private int index=0;
 public int getIndex()
 {
     return index;
 }


public void setIndex(int index)
 {
     this.index=index;
 }
 





  // end of extra code specified in the hbm.xml files


}


