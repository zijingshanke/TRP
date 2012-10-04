package com.fdays.tsms.airticket;


import com.fdays.tsms.airticket._entity._StatisticsOrder;

public class StatisticsOrder extends _StatisticsOrder{
  	private static final long serialVersionUID = 1L;
  	private long saleStatisticsId;
	public long getSaleStatisticsId() {
		return saleStatisticsId;
	}
	public void setSaleStatisticsId(long saleStatisticsId) {
		this.saleStatisticsId = saleStatisticsId;
	}
  	
}
