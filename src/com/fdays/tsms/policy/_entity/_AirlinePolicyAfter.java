package com.fdays.tsms.policy._entity;



/**
 * AirlinePolicyAfter generated by hbm2java
 */


public class _AirlinePolicyAfter 

  extends org.apache.struts.action.ActionForm implements Cloneable
 {
	private static final long serialVersionUID = 1L;

    // Fields    

     protected long id;
     protected String name;
     protected String carrier;
     protected java.sql.Timestamp beginDate;
     protected java.sql.Timestamp endDate;
     protected String memo;
     protected java.math.BigDecimal quota;
     protected Long status;
     protected Long highClassQuota;
     protected java.util.Set policyAfters = new java.util.HashSet(0);
     protected java.util.Set saleStatisticss = new java.util.HashSet(0);
     protected java.util.Set indicatorStatisticss = new java.util.HashSet(0);

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
    


    public String getCarrier() {
        return this.carrier;
    }
    
    public void setCarrier(String carrier) {
        this.carrier = carrier;
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
    


    public String getMemo() {
        return this.memo;
    }
    
    public void setMemo(String memo) {
        this.memo = memo;
    }
    


    public java.math.BigDecimal getQuota() {
        return this.quota;
    }
    
    public void setQuota(java.math.BigDecimal quota) {
        this.quota = quota;
    }
    


    public Long getStatus() {
        return this.status;
    }
    
    public void setStatus(Long status) {
        this.status = status;
    }
    


    public Long getHighClassQuota() {
        return this.highClassQuota;
    }
    
    public void setHighClassQuota(Long highClassQuota) {
        this.highClassQuota = highClassQuota;
    }
    


    public java.util.Set getPolicyAfters() {
        return this.policyAfters;
    }
    
    public void setPolicyAfters(java.util.Set policyAfters) {
        this.policyAfters = policyAfters;
    }
    


    public java.util.Set getSaleStatisticss() {
        return this.saleStatisticss;
    }
    
    public void setSaleStatisticss(java.util.Set saleStatisticss) {
        this.saleStatisticss = saleStatisticss;
    }
    


    public java.util.Set getIndicatorStatisticss() {
        return this.indicatorStatisticss;
    }
    
    public void setIndicatorStatisticss(java.util.Set indicatorStatisticss) {
        this.indicatorStatisticss = indicatorStatisticss;
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


