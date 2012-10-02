package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Date;

public class TeamAirticketOrderReport {
	private Date oPtime;//出票日期
	private String agentType;//客票类型
	private String carrier;//承运人
	private String startPoint;//出发地
	private String endPoing;//目的地
	
	private String flightHC="";//航程
	
	private long totalTicket;//张数
	private String airticketNo;//订单号
	private String flightTime;//航班日期
	private String flightCode;//航班号
	private String flightClass;//舱位
	private String discount;//折扣
	private java.math.BigDecimal ticketPrice =BigDecimal.valueOf(0);//票面价
	public java.math.BigDecimal teamAddPrice;  //团队加价
	public java.math.BigDecimal agentAddPrice;  //客户加价
	private java.math.BigDecimal guestTickPrice;//收客人票款
	
	private String agentName;//购票客户
	private java.math.BigDecimal incomeretreat_charge=BigDecimal.ZERO;//收退票手续费
	private java.math.BigDecimal incomeTicketPrice=BigDecimal.ZERO;//收票款
	private java.math.BigDecimal airportTax=BigDecimal.ZERO;//机场税
	private java.math.BigDecimal copeTicketprice=BigDecimal.ZERO;//应付票款
	private java.math.BigDecimal actual_incomeretreat_charge=BigDecimal.ZERO;//实付退票手续费
	private java.math.BigDecimal paidPrice=BigDecimal.ZERO;//实付款
	private java.math.BigDecimal confirm_payment_Price=BigDecimal.ZERO;//确认支付金额
	private String accountNo;//支付账号
	private String paymentMemo;//支付备注
	private String entry_time;//录单时间
	private String pay_Time;//收付款时间
	private String paymentName;//支付人
	private java.math.BigDecimal agentFeeCarrier=BigDecimal.ZERO;// 月底返代理费
	private java.math.BigDecimal profits=BigDecimal.ZERO;// 团毛利润
	private java.math.BigDecimal refundProfit=BigDecimal.ZERO;//退票利润 
	private java.math.BigDecimal amountMore=BigDecimal.ZERO;// 多收票款
	private java.math.BigDecimal taxMore=BigDecimal.ZERO;// 多收税
	private java.math.BigDecimal commission=BigDecimal.ZERO;//现返
	private java.math.BigDecimal unsettledAccount=BigDecimal.ZERO;//未返
	private String unsettledMome;//未返备注
	private java.math.BigDecimal pureProfits;//净利合计
	private java.math.BigDecimal totalProce;//总金额
	private String sysName;//操作人
	
	
	//计算用	
	private java.math.BigDecimal total_airport_price=BigDecimal.ZERO;//总机建税
	private java.math.BigDecimal total_fuel_price=BigDecimal.ZERO;//总燃油税	
	private java.math.BigDecimal commisson_count=BigDecimal.ZERO;//现返点
	private java.math.BigDecimal rakeoff_count=BigDecimal.ZERO;//后返点
	private java.math.BigDecimal handling_charge=BigDecimal.ZERO;//手续费
	private java.math.BigDecimal proxy_price=BigDecimal.ZERO;////应付出团代理费
	
	private long adult_count=new Long(0);//成人数
	private long child_count=new Long(0);//儿童数
	private long baby_count=new Long(0);//婴儿数
	private long totalTicketNumber=new Long(0);//总人数(张数)	
		
	//计算后
	private java.math.BigDecimal profitsInfo=BigDecimal.ZERO;// 团毛利润
	private java.math.BigDecimal agentFeeCarrierInfo=BigDecimal.ZERO;//// 月底返代理费
	private java.math.BigDecimal copeTicketpriceInfo=BigDecimal.ZERO;//应付票款
	private java.math.BigDecimal paidPriceInfo=BigDecimal.ZERO;//实付款
	private java.math.BigDecimal airportTaxInfo=BigDecimal.ZERO;//机场税
	private java.math.BigDecimal pureProfitsInfo=BigDecimal.ZERO;//净利合计

	private String agentTypeInfo;//客户类型
	
	
	
	
	
	public java.math.BigDecimal getProfitsInfo() {
		return profitsInfo;
	}

	public void setProfitsInfo(java.math.BigDecimal profitsInfo) {
		this.profitsInfo = profitsInfo;
	}

	public java.math.BigDecimal getAgentFeeCarrierInfo() {
		return agentFeeCarrierInfo;
	}

	public void setAgentFeeCarrierInfo(java.math.BigDecimal agentFeeCarrierInfo) {
		this.agentFeeCarrierInfo = agentFeeCarrierInfo;
	}

	public java.math.BigDecimal getCopeTicketpriceInfo() {
		return copeTicketpriceInfo;
	}

	public void setCopeTicketpriceInfo(java.math.BigDecimal copeTicketpriceInfo) {
		this.copeTicketpriceInfo = copeTicketpriceInfo;
	}

	public java.math.BigDecimal getPaidPriceInfo() {
		return paidPriceInfo;
	}

	public void setPaidPriceInfo(java.math.BigDecimal paidPriceInfo) {
		this.paidPriceInfo = paidPriceInfo;
	}

	public java.math.BigDecimal getAirportTaxInfo() {
		return airportTaxInfo;
	}

	public void setAirportTaxInfo(java.math.BigDecimal airportTaxInfo) {
		this.airportTaxInfo = airportTaxInfo;
	}

	public java.math.BigDecimal getPureProfitsInfo() {
		return pureProfitsInfo;
	}

	public void setPureProfitsInfo(java.math.BigDecimal pureProfitsInfo) {
		this.pureProfitsInfo = pureProfitsInfo;
	}

	public String getAgentTypeInfo() {
		return agentTypeInfo;
	}

	public void setAgentTypeInfo(String agentTypeInfo) {
		this.agentTypeInfo = agentTypeInfo;
	}

	public long getTotalTicketNumber() {
		return totalTicketNumber;
	}

	public void setTotalTicketNumber(long totalTicketNumber) {
		this.totalTicketNumber = totalTicketNumber;
	}

	public java.math.BigDecimal getProxy_price() {
		return proxy_price;
	}

	public void setProxy_price(java.math.BigDecimal proxy_price) {
		this.proxy_price = proxy_price;
	}
	
	public String getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}
	public String getEndPoing() {
		return endPoing;
	}
	public void setEndPoing(String endPoing) {
		this.endPoing = endPoing;
	}
	public Date getOPtime() {
		return oPtime;
	}
	public void setOPtime(Date ptime) {
		oPtime = ptime;
	}
	
	

	public String getFlightHC() {
		return flightHC;
	}

	public void setFlightHC(String flightHC) {
		this.flightHC = flightHC;
	}

	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public long getTotalTicket() {
		return totalTicket;
	}
	public void setTotalTicket(long totalTicket) {
		this.totalTicket = totalTicket;
	}
	public String getAirticketNo() {
		return airticketNo;
	}
	public void setAirticketNo(String airticketNo) {
		this.airticketNo = airticketNo;
	}

	public String getFlightTime() {
		return flightTime;
	}
	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getFlightCode() {
		return flightCode;
	}
	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}
	public String getFlightClass() {
		return flightClass;
	}
	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}
	public java.math.BigDecimal getTicketPrice() {
		if(this.ticketPrice !=null)
		{
			return ticketPrice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setTicketPrice(java.math.BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public java.math.BigDecimal getUnsettledAccount() {
		if(this.unsettledAccount !=null)
		{
			return unsettledAccount;
		}else
		{
			return BigDecimal.valueOf(0);
		}
		
	}
	public void setUnsettledAccount(java.math.BigDecimal unsettledAccount) {
		this.unsettledAccount = unsettledAccount;
	}
	public String getUnsettledMome() {
		return unsettledMome;
	}
	public void setUnsettledMome(String unsettledMome) {
		this.unsettledMome = unsettledMome;
	}
	public java.math.BigDecimal getIncomeTicketPrice() {
		if(this.incomeTicketPrice !=null)
		{
			return incomeTicketPrice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setIncomeTicketPrice(java.math.BigDecimal incomeTicketPrice) {
		this.incomeTicketPrice = incomeTicketPrice;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public java.math.BigDecimal getIncomeretreat_charge() {
		if(this.incomeretreat_charge !=null)
		{
			return incomeretreat_charge;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setIncomeretreat_charge(java.math.BigDecimal incomeretreat_charge) {
		this.incomeretreat_charge = incomeretreat_charge;
	}
	public java.math.BigDecimal getAirportTax() {
		if(this.airportTax !=null)
		{
			return airportTax;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setAirportTax(java.math.BigDecimal airportTax) {
		this.airportTax = airportTax;
	}
	public java.math.BigDecimal getCopeTicketprice() {
		if(this.copeTicketprice !=null)
		{
			return copeTicketprice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setCopeTicketprice(java.math.BigDecimal copeTicketprice) {
		this.copeTicketprice = copeTicketprice;
	}
	public java.math.BigDecimal getActual_incomeretreat_charge() {
		if(this.actual_incomeretreat_charge !=null)
		{
			return actual_incomeretreat_charge;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setActual_incomeretreat_charge(
			java.math.BigDecimal actual_incomeretreat_charge) {
		this.actual_incomeretreat_charge = actual_incomeretreat_charge;
	}
	public java.math.BigDecimal getPaidPrice() {
		if(this.paidPrice !=null)
		{
			return paidPrice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setPaidPrice(java.math.BigDecimal paidPrice) {
		this.paidPrice = paidPrice;
	}
	public java.math.BigDecimal getConfirm_payment_Price() {
		if(this.confirm_payment_Price !=null)
		{
			return confirm_payment_Price;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setConfirm_payment_Price(java.math.BigDecimal confirm_payment_Price) {
		this.confirm_payment_Price = confirm_payment_Price;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPaymentMemo() {
		return paymentMemo;
	}
	public void setPaymentMemo(String paymentMemo) {
		this.paymentMemo = paymentMemo;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public java.math.BigDecimal getAgentFeeCarrier() {
		if(this.agentFeeCarrier !=null)
		{
			return agentFeeCarrier;
		}else
		{
			return BigDecimal.valueOf(0);
		}
		
	}
	public void setAgentFeeCarrier(java.math.BigDecimal agentFeeCarrier) {
		this.agentFeeCarrier = agentFeeCarrier;
	}
	public java.math.BigDecimal getProfits() {
		if(this.profits !=null)
		{
			return profits;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setProfits(java.math.BigDecimal profits) {
		this.profits = profits;
	}
	public java.math.BigDecimal getRefundProfit() {
		if(this.refundProfit !=null)
		{
			return refundProfit;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setRefundProfit(java.math.BigDecimal refundProfit) {
		this.refundProfit = refundProfit;
	}
	public java.math.BigDecimal getAmountMore() {
		if(this.amountMore !=null)
		{
			return amountMore;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setAmountMore(java.math.BigDecimal amountMore) {
		this.amountMore = amountMore;
	}
	public java.math.BigDecimal getTaxMore() {
		if(this.taxMore !=null)
		{
			return taxMore;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setTaxMore(java.math.BigDecimal taxMore) {
		this.taxMore = taxMore;
	}
	public java.math.BigDecimal getCommission() {
		if(this.commission !=null)
		{
			return commission;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setCommission(java.math.BigDecimal commission) {
		this.commission = commission;
	}
	public java.math.BigDecimal getPureProfits() {
		if(this.pureProfits !=null)
		{
			return pureProfits;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setPureProfits(java.math.BigDecimal pureProfits) {
		this.pureProfits = pureProfits;
	}
	public java.math.BigDecimal getTotalProce() {
		if(this.totalProce !=null)
		{
			return totalProce;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setTotalProce(java.math.BigDecimal totalProce) {
		this.totalProce = totalProce;
	}

	public java.math.BigDecimal getCommisson_count() {
		if(this.commisson_count !=null)
		{
			return commisson_count;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setCommisson_count(java.math.BigDecimal commisson_count) {
		this.commisson_count = commisson_count;
	}

	public java.math.BigDecimal getHandling_charge() {
		if(this.handling_charge !=null)
		{
			return handling_charge;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setHandling_charge(java.math.BigDecimal handling_charge) {
		this.handling_charge = handling_charge;
	}

	public java.math.BigDecimal getRakeoff_count() {
		if(this.rakeoff_count !=null)
		{
			return rakeoff_count;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setRakeoff_count(java.math.BigDecimal rakeoff_count) {
		this.rakeoff_count = rakeoff_count;
	}

	public java.math.BigDecimal getTotal_airport_price() {
		if(this.total_airport_price !=null)
		{
			return total_airport_price;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setTotal_airport_price(java.math.BigDecimal total_airport_price) {
		this.total_airport_price = total_airport_price;
	}

	public java.math.BigDecimal getTotal_fuel_price() {
		if(this.total_fuel_price !=null)
		{
			return total_fuel_price;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setTotal_fuel_price(java.math.BigDecimal total_fuel_price) {
		this.total_fuel_price = total_fuel_price;
	}

	public java.math.BigDecimal getTeamAddPrice() {
		if(this.teamAddPrice !=null)
		{
			return teamAddPrice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setTeamAddPrice(java.math.BigDecimal teamAddPrice) {
		this.teamAddPrice = teamAddPrice;
	}

	public java.math.BigDecimal getAgentAddPrice() {
		if(this.agentAddPrice !=null)
		{
			return agentAddPrice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setAgentAddPrice(java.math.BigDecimal agentAddPrice) {
		this.agentAddPrice = agentAddPrice;
	}

	public long getAdult_count() {
		return adult_count;
	}

	public void setAdult_count(long adult_count) {
		this.adult_count = adult_count;
	}

	public long getChild_count() {
		return child_count;
	}

	public void setChild_count(long child_count) {
		this.child_count = child_count;
	}

	public long getBaby_count() {
		return baby_count;
	}

	public void setBaby_count(long baby_count) {
		this.baby_count = baby_count;
	}
	public java.math.BigDecimal getGuestTickPrice() {
		if(this.guestTickPrice !=null)
		{
			return guestTickPrice;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}
	public void setGuestTickPrice(java.math.BigDecimal guestTickPrice) {
		this.guestTickPrice = guestTickPrice;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getEntry_time() {
		return entry_time;
	}
	public void setEntry_time(String entry_time) {
		this.entry_time = entry_time;
	}
	public String getPay_Time() {
		return pay_Time;
	}
	public void setPay_Time(String pay_Time) {
		this.pay_Time = pay_Time;
	}

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	

}
