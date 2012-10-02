package com.fdays.tsms.policy;

import java.math.BigDecimal;

import com.fdays.tsms.policy._entity._SaleStatistics;

 

public class SaleStatistics extends _SaleStatistics
{
	private static final long serialVersionUID = 1L;
	private Long airlinePolicyAfterId;
 	private int quotaByStatistics;
 
	
	
 

	public java.math.BigDecimal getSaleAmount() {
    	if(this.saleAmount == null){
    		saleAmount = BigDecimal.ZERO;
    	}
        return this.saleAmount;
    }
    
 
    

    public java.math.BigDecimal getAfterAmount() {
    	if(this.afterAmount == null){
    		this.afterAmount = BigDecimal.ZERO;
    	}
    	return afterAmount;
    }

 
    
    public java.math.BigDecimal getHighClassAward() {
    	if(this.highClassAward == null){
    		this.highClassAward = BigDecimal.ZERO;
    	}
        return this.highClassAward;
    }
    
 

    public Long getHighClassTicketNum() {
    	if(this.highClassTicketNum == null){
    		this.highClassTicketNum = 0l;
    	}
        return this.highClassTicketNum;
    }
    
 

    public java.math.BigDecimal getProfitAfter() {
    	if(this.profitAfter == null){
    		this.profitAfter = BigDecimal.ZERO;
    	}
        return this.profitAfter;
    }
    
 
       


    public java.math.BigDecimal getProfit() {
    	if(this.profit == null){
    		this.profit = BigDecimal.ZERO;
    	}
        return this.profit;
    }
 
	public Long getAirlinePolicyAfterId()
	{
		return airlinePolicyAfterId;
	}

	public void setAirlinePolicyAfterId(Long airlinePolicyAfterId)
	{
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}

	public BigDecimal getSaleAmountPercent()
	{
		
		BigDecimal percent = BigDecimal.valueOf(0);
		if(this.getAirlinePolicyAfter() != null 
				&& getAirlinePolicyAfter().getQuota().longValue() == 0){
			return BigDecimal.valueOf(100);
		}
		
		if (this.getAirlinePolicyAfter() != null 
				&& getAirlinePolicyAfter().getQuota().longValue() > 0)
		{
			percent = saleAmount.divide(this.getAirlinePolicyAfter().getQuota(),2, BigDecimal.ROUND_CEILING)
			    .multiply(BigDecimal.valueOf(100));
		}
		return percent;
	}
	

  

	public int getQuotaByStatistics()
  {
  	return quotaByStatistics;
  }

	public void setQuotaByStatistics(int quotaByStatistics)
  {
  	this.quotaByStatistics = quotaByStatistics;
  }

	public String getStatusInfo()
	{
		if (this.status == null || this.status == 0) { return "无"; }
		if (this.status == 1) { return "启用"; }
		return "停用";
	}
}
