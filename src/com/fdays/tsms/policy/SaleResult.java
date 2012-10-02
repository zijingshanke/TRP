package com.fdays.tsms.policy;

import java.math.BigDecimal;

public class SaleResult {	
	private BigDecimal saleAmounts = BigDecimal.valueOf(0);		
	private BigDecimal ticketNums = BigDecimal.valueOf(0);		
	private BigDecimal afterAmounts= BigDecimal.valueOf(0);		
	private BigDecimal rateAfter= BigDecimal.valueOf(0);	
	
	public void addSaleAmount(BigDecimal saleAmount)
	{
		saleAmounts=saleAmounts.add(saleAmount);		
	}
	public void addTicketNums(BigDecimal ticketNum)
	{
		ticketNums=ticketNums.add(ticketNum);		
	}
	public void addTicketNums(long ticketNum)
	{
		ticketNums=ticketNums.add(BigDecimal.valueOf(ticketNum));		
	}
	public void addAfterAmounts(BigDecimal afterAmount)
	{
		afterAmounts=afterAmounts.add(afterAmount);		
	}
	public BigDecimal getRateAfter()
  {
  	return rateAfter;
  }
	public void setRateAfter(BigDecimal rateAfter)
  {
  	this.rateAfter = rateAfter;
  }
	public BigDecimal getSaleAmounts()
  {
  	return saleAmounts;
  }
	public BigDecimal getTicketNums()
  {
  	return ticketNums;
  }
	public BigDecimal getAfterAmounts()
  {
  	return afterAmounts;
  }
	
}
