package com.fdays.tsms.airticket;

import com.neza.base.ListActionForm;

public class StatisticsOrderListForm extends ListActionForm
{
	 private long id;
	 private String orderNo;
	 private String flightCode;
	 private String startEnd;
	 private String boardingTime;
	 private String passengerName;
	 private String ticketNumber;
	 private String sort;			//排序字段
	 private java.math.BigDecimal totalAmount;
	 private java.math.BigDecimal profit;
	 private java.math.BigDecimal rate;
	 private java.math.BigDecimal profitAfter;
	 private Long tranType;
	 private Long groupId;
	 private long saleStatisticsId;
	 private com.fdays.tsms.policy.SaleStatistics saleStatistics;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getFlightCode() {
		return flightCode;
	}
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	public String getStartEnd() {
		return startEnd;
	}
	public void setStartEnd(String startEnd) {
		this.startEnd = startEnd;
	}
	public String getBoardingTime() {
		return boardingTime;
	}
	public void setBoardingTime(String boardingTime) {
		this.boardingTime = boardingTime;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public java.math.BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(java.math.BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public java.math.BigDecimal getProfit() {
		return profit;
	}
	public void setProfit(java.math.BigDecimal profit) {
		this.profit = profit;
	}
	public java.math.BigDecimal getRate() {
		return rate;
	}
	public void setRate(java.math.BigDecimal rate) {
		this.rate = rate;
	}
	public java.math.BigDecimal getProfitAfter() {
		return profitAfter;
	}
	public void setProfitAfter(java.math.BigDecimal profitAfter) {
		this.profitAfter = profitAfter;
	}
	public Long getTranType() {
		return tranType;
	}
	public void setTranType(Long tranType) {
		this.tranType = tranType;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public com.fdays.tsms.policy.SaleStatistics getSaleStatistics() {
		return saleStatistics;
	}
	public void setSaleStatistics(
			com.fdays.tsms.policy.SaleStatistics saleStatistics) {
		this.saleStatistics = saleStatistics;
	}
	public long getSaleStatisticsId() {
		return saleStatisticsId;
	}
	public void setSaleStatisticsId(long saleStatisticsId) {
		this.saleStatisticsId = saleStatisticsId;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}