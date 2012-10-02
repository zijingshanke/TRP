package com.fdays.tsms.airticket._entity;



/**
 * Flight generated by hbm2java
 */


public class _Flight 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected String flightCode;
     protected String startPoint;
     protected String endPoint;
     protected java.sql.Timestamp boardingTime;
     protected String flightClass;
     protected String discount;
     protected Long status;
     protected java.math.BigDecimal ticketPrice;
     protected java.math.BigDecimal airportPriceAdult;
     protected java.math.BigDecimal fuelPriceAdult;
     protected java.math.BigDecimal airportPriceBaby;
     protected java.math.BigDecimal fuelPriceBaby;
     protected java.math.BigDecimal airportPriceChild;
     protected java.math.BigDecimal fuelPriceChild;
     protected com.fdays.tsms.airticket.AirticketOrder airticketOrder;

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public String getFlightCode() {
        return this.flightCode;
    }
    
    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
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
    


    public java.sql.Timestamp getBoardingTime() {
        return this.boardingTime;
    }
    
    public void setBoardingTime(java.sql.Timestamp boardingTime) {
        this.boardingTime = boardingTime;
    }
    


    public String getFlightClass() {
        return this.flightClass;
    }
    
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    


    public String getDiscount() {
        return this.discount;
    }
    
    public void setDiscount(String discount) {
        this.discount = discount;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public java.math.BigDecimal getTicketPrice() {
        return this.ticketPrice;
    }
    
    public void setTicketPrice(java.math.BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    


    public java.math.BigDecimal getAirportPriceAdult() {
        return this.airportPriceAdult;
    }
    
    public void setAirportPriceAdult(java.math.BigDecimal airportPriceAdult) {
        this.airportPriceAdult = airportPriceAdult;
    }
    


    public java.math.BigDecimal getFuelPriceAdult() {
        return this.fuelPriceAdult;
    }
    
    public void setFuelPriceAdult(java.math.BigDecimal fuelPriceAdult) {
        this.fuelPriceAdult = fuelPriceAdult;
    }
    


    public java.math.BigDecimal getAirportPriceBaby() {
        return this.airportPriceBaby;
    }
    
    public void setAirportPriceBaby(java.math.BigDecimal airportPriceBaby) {
        this.airportPriceBaby = airportPriceBaby;
    }
    


    public java.math.BigDecimal getFuelPriceBaby() {
        return this.fuelPriceBaby;
    }
    
    public void setFuelPriceBaby(java.math.BigDecimal fuelPriceBaby) {
        this.fuelPriceBaby = fuelPriceBaby;
    }
    


    public java.math.BigDecimal getAirportPriceChild() {
        return this.airportPriceChild;
    }
    
    public void setAirportPriceChild(java.math.BigDecimal airportPriceChild) {
        this.airportPriceChild = airportPriceChild;
    }
    


    public java.math.BigDecimal getFuelPriceChild() {
        return this.fuelPriceChild;
    }
    
    public void setFuelPriceChild(java.math.BigDecimal fuelPriceChild) {
        this.fuelPriceChild = fuelPriceChild;
    }
    


    public com.fdays.tsms.airticket.AirticketOrder getAirticketOrder() {
        return this.airticketOrder;
    }
    
    public void setAirticketOrder(com.fdays.tsms.airticket.AirticketOrder airticketOrder) {
        this.airticketOrder = airticketOrder;
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


