package com.fdays.tsms.airticket._entity;



/**
 * Passenger generated by hbm2java
 */


public class _Passenger 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected String name;
     protected String ticketNumber;
     protected String cardno;
     protected Long type;
     protected Long orderStatus;
     protected Long status;
     protected Long flightId;
     protected com.fdays.tsms.airticket.AirticketOrder airticketOrder;

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    


    public String getTicketNumber() {
        return this.ticketNumber;
    }
    
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
    


    public String getCardno() {
        return this.cardno;
    }
    
    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
    


    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
    }
    


    public Long getOrderStatus() {
        return this.orderStatus;
    }
    
    public void setOrderStatus(Long orderStatus) {
        this.orderStatus = orderStatus;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public Long getFlightId() {
        return this.flightId;
    }
    
    public void setFlightId(Long flightId) {
        this.flightId = flightId;
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


