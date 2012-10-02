package com.fdays.tsms.transaction._entity;



/**
 * AirticketOrderReport generated by hbm2java
 */


public class _AirticketOrderReport 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected Long groupId;
     protected Long subGroupMarkNo;
     protected String orderNo;
     protected Long orderId;
     protected Long businessType;
     protected Long tranType;
     protected Long ticketType;
     protected Long platformId;
     protected String platformName;
     protected java.math.BigDecimal rebate;
     protected String subPnr;
     protected String drawPnr;
     protected String passengerName;
     protected Long passengerCount;
     protected String startPoint;
     protected String endPoint;
     protected String carrier;
     protected String flightCode;
     protected String flightClass;
     protected java.math.BigDecimal ticketPrice;
     protected java.math.BigDecimal totalTicketPrice;
     protected String entryOperatorName;
     protected String payOperatorName;
     protected String inAccountName;
     protected String outAccountName;
     protected String inRetireAccountName;
     protected String outRetireAccountName;
     protected java.math.BigDecimal inAmount;
     protected java.math.BigDecimal outAmount;
     protected java.math.BigDecimal inRetireAmount;
     protected java.math.BigDecimal outRetireAmount;
     protected Long status;
     protected String memo;
     protected String userNo;
     protected java.sql.Timestamp lastDate;
     protected Long type;

     // Constructors
   
    // Property accessors


    public long getId() {
        return this.id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    


    public Long getGroupId() {
        return this.groupId;
    }
    
    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
    


    public Long getSubGroupMarkNo() {
        return this.subGroupMarkNo;
    }
    
    public void setSubGroupMarkNo(Long subGroupMarkNo) {
        this.subGroupMarkNo = subGroupMarkNo;
    }
    


    public String getOrderNo() {
        return this.orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    


    public Long getOrderId() {
        return this.orderId;
    }
    
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    


    public Long getBusinessType() {
        return this.businessType;
    }
    
    public void setBusinessType(Long businessType) {
        this.businessType = businessType;
    }
    


    public Long getTranType() {
        return this.tranType;
    }
    
    public void setTranType(Long tranType) {
        this.tranType = tranType;
    }
    


    public Long getTicketType() {
        return this.ticketType;
    }
    
    public void setTicketType(Long ticketType) {
        this.ticketType = ticketType;
    }
    


    public Long getPlatformId() {
        return this.platformId;
    }
    
    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }
    


    public String getPlatformName() {
        return this.platformName;
    }
    
    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }
    


    public java.math.BigDecimal getRebate() {
        return this.rebate;
    }
    
    public void setRebate(java.math.BigDecimal rebate) {
        this.rebate = rebate;
    }
    


    public String getSubPnr() {
        return this.subPnr;
    }
    
    public void setSubPnr(String subPnr) {
        this.subPnr = subPnr;
    }
    


    public String getDrawPnr() {
        return this.drawPnr;
    }
    
    public void setDrawPnr(String drawPnr) {
        this.drawPnr = drawPnr;
    }
    


    public String getPassengerName() {
        return this.passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    


    public Long getPassengerCount() {
        return this.passengerCount;
    }
    
    public void setPassengerCount(Long passengerCount) {
        this.passengerCount = passengerCount;
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
    


    public String getFlightClass() {
        return this.flightClass;
    }
    
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    


    public java.math.BigDecimal getTicketPrice() {
        return this.ticketPrice;
    }
    
    public void setTicketPrice(java.math.BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
    


    public java.math.BigDecimal getTotalTicketPrice() {
        return this.totalTicketPrice;
    }
    
    public void setTotalTicketPrice(java.math.BigDecimal totalTicketPrice) {
        this.totalTicketPrice = totalTicketPrice;
    }
    


    public String getEntryOperatorName() {
        return this.entryOperatorName;
    }
    
    public void setEntryOperatorName(String entryOperatorName) {
        this.entryOperatorName = entryOperatorName;
    }
    


    public String getPayOperatorName() {
        return this.payOperatorName;
    }
    
    public void setPayOperatorName(String payOperatorName) {
        this.payOperatorName = payOperatorName;
    }
    


    public String getInAccountName() {
        return this.inAccountName;
    }
    
    public void setInAccountName(String inAccountName) {
        this.inAccountName = inAccountName;
    }
    


    public String getOutAccountName() {
        return this.outAccountName;
    }
    
    public void setOutAccountName(String outAccountName) {
        this.outAccountName = outAccountName;
    }
    


    public String getInRetireAccountName() {
        return this.inRetireAccountName;
    }
    
    public void setInRetireAccountName(String inRetireAccountName) {
        this.inRetireAccountName = inRetireAccountName;
    }
    


    public String getOutRetireAccountName() {
        return this.outRetireAccountName;
    }
    
    public void setOutRetireAccountName(String outRetireAccountName) {
        this.outRetireAccountName = outRetireAccountName;
    }
    


    public java.math.BigDecimal getInAmount() {
        return this.inAmount;
    }
    
    public void setInAmount(java.math.BigDecimal inAmount) {
        this.inAmount = inAmount;
    }
    


    public java.math.BigDecimal getOutAmount() {
        return this.outAmount;
    }
    
    public void setOutAmount(java.math.BigDecimal outAmount) {
        this.outAmount = outAmount;
    }
    


    public java.math.BigDecimal getInRetireAmount() {
        return this.inRetireAmount;
    }
    
    public void setInRetireAmount(java.math.BigDecimal inRetireAmount) {
        this.inRetireAmount = inRetireAmount;
    }
    


    public java.math.BigDecimal getOutRetireAmount() {
        return this.outRetireAmount;
    }
    
    public void setOutRetireAmount(java.math.BigDecimal outRetireAmount) {
        this.outRetireAmount = outRetireAmount;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    


    public String getUserNo() {
        return this.userNo;
    }
    
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }
    


    public java.sql.Timestamp getLastDate() {
        return this.lastDate;
    }
    
    public void setLastDate(java.sql.Timestamp lastDate) {
        this.lastDate = lastDate;
    }
    


    public Long getType() {
        return this.type;
    }
    
    public void setType(Long type) {
        this.type = type;
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


