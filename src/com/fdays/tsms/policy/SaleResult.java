package com.fdays.tsms.policy;

import java.math.BigDecimal;

public class SaleResult {	
	private BigDecimal saleAmounts = BigDecimal.valueOf(0);		
	private BigDecimal ticketNums = BigDecimal.valueOf(0);
	private BigDecimal afterAmounts= BigDecimal.valueOf(0);		
	private BigDecimal rateAfter= BigDecimal.valueOf(0);
	private BigDecimal amounts = BigDecimal.valueOf(0);					//计量净额
	private BigDecimal awardAmounts = BigDecimal.valueOf(0);			//计奖净额
	private BigDecimal highClassTicketNums = BigDecimal.valueOf(0);		//高舱票数
	
	public void addAmounts(BigDecimal amount){
		amounts = amounts.add(amount);
	}
	public void addAwardAmounts(BigDecimal awardAmount){
		awardAmounts = awardAmounts.add(awardAmount);
	}
	public void addHighClassTicketNums(BigDecimal highClassTicketNum){
		highClassTicketNums = highClassTicketNums.add(highClassTicketNum);
	}
	
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
	
	
	public BigDecimal getAmounts() {
		return amounts;
	}

	public BigDecimal getAwardAmounts() {
		return awardAmounts;
	}

	public BigDecimal getHighClassTicketNum() {
		return highClassTicketNums;
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
