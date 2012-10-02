package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Date;

public class TeamAirticketOrderReport {

	private Date oPtime;//出票日期
	private long agentType;//客票类型
	private String carrier;//承运人
	private String startPoint;//出发地
	private String endPoing;//目的地
	private long totalTicket;//张数
	private String airticketNo;//订单号
	private String flightTime;//航班日期
	private String flightCode;//航班号
	private String flightClass;//舱位
	private java.math.BigDecimal ticketPrice =BigDecimal.valueOf(0);//票面价
	public java.math.BigDecimal teamAddPrice;  //团队加价
	public java.math.BigDecimal agentAddPrice;  //客户加价
	private java.math.BigDecimal guestTickPrice;//收客人票款
	
	private String agentName;//购票客户
	private java.math.BigDecimal incomeretreat_charge;//收退票手续费
	private java.math.BigDecimal incomeTicketPrice;//收票款
	private java.math.BigDecimal airportTax;//机场税
	private java.math.BigDecimal copeTicketprice;//应付票款
	private java.math.BigDecimal actual_incomeretreat_charge;//实付退票手续费
	private java.math.BigDecimal paidPrice;//实付款
	private java.math.BigDecimal confirm_payment_Price;//确认支付金额
	private String accountNo;//支付账号
	private String paymentMemo;//支付备注
	private String paymentTime;//支付时间
	private String paymentName;//支付人
	private java.math.BigDecimal agentFeeCarrier;// 月底返代理费
	private java.math.BigDecimal profits;// 团毛利润
	private java.math.BigDecimal refundProfit;//退票利润 
	private java.math.BigDecimal amountMore;// 多收票款
	private java.math.BigDecimal taxMore;// 多收税
	private java.math.BigDecimal commission;//现返
	private java.math.BigDecimal unsettledAccount;//未返
	private String unsettledMome;//未返备注
	private java.math.BigDecimal pureProfits;//净利合计
	private java.math.BigDecimal totalProce;//总金额
	private String sysName;//操作人
	
	
	//计算用
	
	private java.math.BigDecimal total_airport_price;//总机建税
	private java.math.BigDecimal total_fuel_price;//总燃油税
	
	private java.math.BigDecimal commisson_count;//现返点
	private java.math.BigDecimal rakeoff_count;//后返点
	private java.math.BigDecimal handling_charge;//手续费
	private java.math.BigDecimal proxy_price;////应付出团代理费
	private long adult_count;//成人数
	private long child_count;//儿童数
	private long baby_count;//婴儿数
	
	protected java.util.Set flights = new java.util.HashSet(0);
	protected java.util.Set passengers = new java.util.HashSet(0);
	
	//计算后
	private java.math.BigDecimal profitsInfo;// 团毛利润
	private java.math.BigDecimal agentFeeCarrierInfo;//// 月底返代理费
	private java.math.BigDecimal copeTicketpriceInfo;//应付票款
	private java.math.BigDecimal paidPriceInfo;//实付款
	private java.math.BigDecimal airportTaxInfo;//机场税
	private java.math.BigDecimal pureProfitsInfo;//净利合计
	private long totalPersonInfo;//总人数(张数)
	
	private String agentTypeInfo;//客户类型
	
	public String getAgentTypeInfo()
	{
		if(this.agentType >0)
		{
			if(this.getAgentType() ==1)
			{
				agentTypeInfo="B2C散客";
			}else if(this.getAgentType() ==2)
			{
				agentTypeInfo="团队";
			}else if(this.getAgentType() ==3)
			{
				agentTypeInfo="B2B";
			}
		}else 
		{
			agentTypeInfo="";
		}
		return agentTypeInfo;
	}
	//机场税
	public java.math.BigDecimal getAirportTaxInfo()
	{
		if(this.total_airport_price.compareTo(BigDecimal.valueOf(0))==1)//总机建税>0
		{
			if(this.total_fuel_price.compareTo(BigDecimal.valueOf(0))==1)//总燃油税>0
			{
				airportTaxInfo =this.total_airport_price.add(this.total_fuel_price);
			}else
			{
				airportTaxInfo =this.total_airport_price;
			}
		}else if(this.total_airport_price.compareTo(BigDecimal.valueOf(0))==0 && 
				this.total_fuel_price.compareTo(BigDecimal.valueOf(0))==1)//总机建税<0 总燃油税>0
		{
			airportTaxInfo =this.total_fuel_price;
		}else
		{
			airportTaxInfo=BigDecimal.valueOf(0);
		}
		return airportTaxInfo;
	}
	
	//张数
	public long getTotalPersonInfo()
	{
		if(this.adult_count > 0)
		{
			if(this.child_count > 0)
			{
				if(this.baby_count > 0)
				{
					totalPersonInfo=this.adult_count + this.child_count + this.baby_count;
				}else
				{
					totalPersonInfo=this.adult_count + this.child_count;
				}
			}else if(this.adult_count > 0 && this.child_count == 0 && this.baby_count > 0)
			{
				totalPersonInfo=this.adult_count + this.baby_count;
			}else
			{
				totalPersonInfo=this.adult_count;
			}
		}else if( this.child_count > 0 && this.baby_count > 0)
		{
			totalPersonInfo= this.child_count + this.baby_count;
		}else
		{
			totalPersonInfo=0;
		}
		return totalPersonInfo;
	}
	
//	 应付出团代理费（现返）= (票面价 +多收票价)*返点
//	 2. 应付出团代理费（未返）= _______
//	 3. 应收票款 =(票面价 +多收票价) –现返+多收税 +收退票手续费
//	 4. 实收票款 =应收票款 + 机场税
//
//	 ----------------------------------------------对航空公司-----------------------------------------------------
//	 1.	团毛利润 =票面价 * 返点 – 手续费
	public BigDecimal getProfitsInfo()
	{
		if(this.ticketPrice.compareTo(BigDecimal.valueOf(0))==1)
		{
			if(this.commisson_count.compareTo(BigDecimal.valueOf(0))==1 &&this.handling_charge.compareTo(BigDecimal.valueOf(0))==1)//票面价>0
			{
				profitsInfo =this.ticketPrice.multiply(this.commisson_count).subtract(this.handling_charge);
			}else if(this.commisson_count.compareTo(BigDecimal.valueOf(0))==1 &&this.handling_charge.compareTo(BigDecimal.valueOf(0))==0)
			{
				profitsInfo =this.ticketPrice.multiply(this.commisson_count);
			}else
			{
				profitsInfo=BigDecimal.valueOf(0);
			}
		}
		return profitsInfo;
	}
	
//	 2.	月底返代理费 = 票面价 * 月底返点
	public BigDecimal getAgentFeeCarrierInfo()
	{
		if(this.ticketPrice.compareTo(BigDecimal.valueOf(0))==1 && this.rakeoff_count.compareTo(BigDecimal.valueOf(0))==1)
		{
			agentFeeCarrierInfo=this.ticketPrice.multiply(this.rakeoff_count);
		}else
		{
			agentFeeCarrierInfo=BigDecimal.valueOf(0);
		}
		return agentFeeCarrierInfo;
	}
	
//	 3.	应付票款 = 票面价 – 团毛利润 + 付退票手续费
//	public BigDecimal  getCopeTicketpriceInfo()
//	{
//		System.out.println(profitsInfo+"<<<<团毛利润");
//		if(this.ticketPrice.compareTo(BigDecimal.valueOf(0))==1)//票面价>0
//		{
//			if(this.profitsInfo.compareTo(BigDecimal.valueOf(0))==1)//团毛利润>0
//			{
//				if(this.incomeretreat_charge.compareTo(BigDecimal.valueOf(0))==1)//付退票手续费>0
//				{
//					copeTicketpriceInfo =this.ticketPrice.subtract(this.profitsInfo).add(this.incomeretreat_charge);
//				}else
//				{
//					copeTicketpriceInfo =this.ticketPrice.subtract(this.profitsInfo);
//				}
//			}else
//			{
//				copeTicketpriceInfo =this.ticketPrice;
//			}
//		}else
//		{
//			copeTicketpriceInfo=BigDecimal.valueOf(0);
//		}
//		
//		return copeTicketpriceInfo;
//	}
//	 4.	实付票款 = 应付票款 + 机场税
//	public BigDecimal getPaidPriceInfo()
//	{
//		if(this.copeTicketpriceInfo.compareTo(BigDecimal.valueOf(0))==1)//应付票款>0
//		{
//			if(this.airportTaxInfo.compareTo(BigDecimal.valueOf(0))==1)// 机场税>0
//			{
//				paidPriceInfo=this.copeTicketpriceInfo.add(this.airportTaxInfo);
//			}else
//			{
//				paidPriceInfo=this.copeTicketpriceInfo;
//			}
//		}else
//		{
//			paidPriceInfo=BigDecimal.valueOf(0);
//		}
//		return paidPriceInfo;
//	}
//	 5.	订单金额 = (不详)
//
//	 ---------------------------------------------利润-----------------------------------------------------
//	 1.	退票利润 =_______
	
//	 2.	净利合计= 团毛利润 + 退票利润 + 多收票款 + 多收税款 –应付出团代理费
//	public BigDecimal getPureProfitsInfo()
//	{
//		if(this.profitsInfo.compareTo(BigDecimal.valueOf(0))==1)//团毛利润>0
//		{
//			if(this.refundProfit.compareTo(BigDecimal.valueOf(0))==1)//退票利润>0
//			{
//				if(this.amountMore.compareTo(BigDecimal.valueOf(0))==1)//多收票款>0
//				{
//					if(this.taxMore.compareTo(BigDecimal.valueOf(0))==1)//多收税款>0
//					{
//						if(this.proxy_price.compareTo(BigDecimal.valueOf(0))==1)
//						{
//							pureProfitsInfo=this.profitsInfo.add(this.refundProfit).add(this.amountMore).add(this.taxMore).subtract(this.proxy_price);
//						}else
//						{
//							pureProfitsInfo=this.profitsInfo.add(this.refundProfit).add(this.amountMore).add(this.taxMore);
//						}
//					}else
//					{
//						pureProfitsInfo=this.profitsInfo.add(this.refundProfit).add(this.amountMore).subtract(this.proxy_price);
//					}
//				}else if(this.amountMore.compareTo(BigDecimal.valueOf(0))==0 && this.taxMore.compareTo(BigDecimal.valueOf(0))==1)
//				{
//					pureProfitsInfo=this.profitsInfo.add(this.refundProfit).add(this.taxMore).subtract(this.proxy_price);
//				}else
//				{
//					pureProfitsInfo=this.profitsInfo.add(this.refundProfit).add(this.taxMore);
//				}
//			}else{
//				pureProfitsInfo=this.profitsInfo;
//			}
//		}else
//		{
//			pureProfitsInfo=BigDecimal.valueOf(0);
//		}
//		return pureProfitsInfo;
//	}
//	

	
	
	
	
	
	
	
	

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

	public long getAgentType() {
		return agentType;
	}
	public void setAgentType(long agentType) {
		this.agentType = agentType;
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
	public java.util.Set getFlights() {
		return flights;
	}
	public void setFlights(java.util.Set flights) {
		this.flights = flights;
	}
	public java.util.Set getPassengers() {
		return passengers;
	}
	public void setPassengers(java.util.Set passengers) {
		this.passengers = passengers;
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
		return ticketPrice;
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
		return unsettledAccount;
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
		return incomeTicketPrice;
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
		return incomeretreat_charge;
	}
	public void setIncomeretreat_charge(java.math.BigDecimal incomeretreat_charge) {
		this.incomeretreat_charge = incomeretreat_charge;
	}
	public java.math.BigDecimal getAirportTax() {
		return airportTax;
	}
	public void setAirportTax(java.math.BigDecimal airportTax) {
		this.airportTax = airportTax;
	}
	public java.math.BigDecimal getCopeTicketprice() {
		return copeTicketprice;
	}
	public void setCopeTicketprice(java.math.BigDecimal copeTicketprice) {
		this.copeTicketprice = copeTicketprice;
	}
	public java.math.BigDecimal getActual_incomeretreat_charge() {
		return actual_incomeretreat_charge;
	}
	public void setActual_incomeretreat_charge(
			java.math.BigDecimal actual_incomeretreat_charge) {
		this.actual_incomeretreat_charge = actual_incomeretreat_charge;
	}
	public java.math.BigDecimal getPaidPrice() {
		return paidPrice;
	}
	public void setPaidPrice(java.math.BigDecimal paidPrice) {
		this.paidPrice = paidPrice;
	}
	public java.math.BigDecimal getConfirm_payment_Price() {
		return confirm_payment_Price;
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
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPaymentName() {
		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public java.math.BigDecimal getAgentFeeCarrier() {
		return agentFeeCarrier;
	}
	public void setAgentFeeCarrier(java.math.BigDecimal agentFeeCarrier) {
		this.agentFeeCarrier = agentFeeCarrier;
	}
	public java.math.BigDecimal getProfits() {
		return profits;
	}
	public void setProfits(java.math.BigDecimal profits) {
		this.profits = profits;
	}
	public java.math.BigDecimal getRefundProfit() {
		return refundProfit;
	}
	public void setRefundProfit(java.math.BigDecimal refundProfit) {
		this.refundProfit = refundProfit;
	}
	public java.math.BigDecimal getAmountMore() {
		return amountMore;
	}
	public void setAmountMore(java.math.BigDecimal amountMore) {
		this.amountMore = amountMore;
	}
	public java.math.BigDecimal getTaxMore() {
		return taxMore;
	}
	public void setTaxMore(java.math.BigDecimal taxMore) {
		this.taxMore = taxMore;
	}
	public java.math.BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(java.math.BigDecimal commission) {
		this.commission = commission;
	}
	public java.math.BigDecimal getPureProfits() {
		return pureProfits;
	}
	public void setPureProfits(java.math.BigDecimal pureProfits) {
		this.pureProfits = pureProfits;
	}
	public java.math.BigDecimal getTotalProce() {
		return totalProce;
	}
	public void setTotalProce(java.math.BigDecimal totalProce) {
		this.totalProce = totalProce;
	}

	public java.math.BigDecimal getCommisson_count() {
		return commisson_count;
	}

	public void setCommisson_count(java.math.BigDecimal commisson_count) {
		this.commisson_count = commisson_count;
	}

	public java.math.BigDecimal getHandling_charge() {
		return handling_charge;
	}

	public void setHandling_charge(java.math.BigDecimal handling_charge) {
		this.handling_charge = handling_charge;
	}

	public java.math.BigDecimal getRakeoff_count() {
		return rakeoff_count;
	}

	public void setRakeoff_count(java.math.BigDecimal rakeoff_count) {
		this.rakeoff_count = rakeoff_count;
	}

	public java.math.BigDecimal getTotal_airport_price() {
		return total_airport_price;
	}

	public void setTotal_airport_price(java.math.BigDecimal total_airport_price) {
		this.total_airport_price = total_airport_price;
	}

	public java.math.BigDecimal getTotal_fuel_price() {
		return total_fuel_price;
	}

	public void setTotal_fuel_price(java.math.BigDecimal total_fuel_price) {
		this.total_fuel_price = total_fuel_price;
	}

	public java.math.BigDecimal getTeamAddPrice() {
		return teamAddPrice;
	}

	public void setTeamAddPrice(java.math.BigDecimal teamAddPrice) {
		this.teamAddPrice = teamAddPrice;
	}

	public java.math.BigDecimal getAgentAddPrice() {
		return agentAddPrice;
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
		return guestTickPrice;
	}
	public void setGuestTickPrice(java.math.BigDecimal guestTickPrice) {
		this.guestTickPrice = guestTickPrice;
	}
	

}
