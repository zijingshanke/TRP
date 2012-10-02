package com.fdays.tsms.policy._entity;



/**
 * PolicyAfter generated by hbm2java
 */


public class _PolicyAfter 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected String carrier;
     protected String flightCode;
     protected String flightCodeExcept;
     protected String startPoint;
     protected String endPoint;
     protected String flightClass;
     protected String flightClassExcept;
     protected java.sql.Timestamp beginDate;
     protected java.sql.Timestamp endDate;
     protected Long discount;
     protected java.math.BigDecimal rate;
     protected Long travelType;
     protected Long ticketType;
     protected Long type;
     protected String memo;
     protected Long status;

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public String getCarrier() {
        return this.carrier;
    }
    
    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
    


    public String getFlightCode() {
        return this.flightCode;
    }
    
    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }
    


    public String getFlightCodeExcept() {
        return this.flightCodeExcept;
    }
    
    public void setFlightCodeExcept(String flightCodeExcept) {
        this.flightCodeExcept = flightCodeExcept;
    }
    


    public String getStartPoint() {
        return this.startPoint;
    }
    
    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }
    


    public String getEndPoint() {
        return this.endPoint;
    }
    
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
    


    public String getFlightClass() {
        return this.flightClass;
    }
    
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    


    public String getFlightClassExcept() {
        return this.flightClassExcept;
    }
    
    public void setFlightClassExcept(String flightClassExcept) {
        this.flightClassExcept = flightClassExcept;
    }
    


    public java.sql.Timestamp getBeginDate() {
        return this.beginDate;
    }
    
    public void setBeginDate(java.sql.Timestamp beginDate) {
        this.beginDate = beginDate;
    }
    


    public java.sql.Timestamp getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(java.sql.Timestamp endDate) {
        this.endDate = endDate;
    }
    


    public Long getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(Long discount) {
        this.discount = discount;
    }
    


    public java.math.BigDecimal getRate() {
        return this.rate;
    }
    
    public void setRate(java.math.BigDecimal rate) {
        this.rate = rate;
    }
    


    public Long getTravelType() {
        return this.travelType;
    }
    
    public void setTravelType(Long travelType) {
        this.travelType = travelType;
    }
    


    public Long getTicketType() {
        return this.ticketType;
    }
    
    public void setTicketType(Long ticketType) {
        this.ticketType = ticketType;
    }
    


    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
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

