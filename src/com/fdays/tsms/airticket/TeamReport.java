package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.neza.tool.DateUtil;

public class TeamReport {	
	private String orderNos="";
	private String buyAirticketNo;//订单号
	private String carrier;//承运人	
	private String drawer;//出票人
	
	private String flightsTxt="";//航程
	private String flightTime;//航班日期
	private String flightCode;//航班号
	private String flightClass;//舱位
	private String discount;//折扣
	
	private java.math.BigDecimal ticketPrice =BigDecimal.valueOf(0);//票面价
	
	public java.math.BigDecimal teamAddPrice;  //团队加价
	public java.math.BigDecimal agentAddPrice;  //客户加价
	
	private String agentName;//购票客户
	private java.math.BigDecimal incomeretreat_charge=BigDecimal.ZERO;//收退票手续费
	private java.math.BigDecimal incomeTicketPrice=BigDecimal.ZERO;//收票款
	private java.math.BigDecimal airportTax=BigDecimal.ZERO;//机场税
	private java.math.BigDecimal actual_incomeretreat_charge=BigDecimal.ZERO;//实付退票手续费
	private java.math.BigDecimal buyTotalAmount=BigDecimal.ZERO;//实付款

	private java.math.BigDecimal refundProfit=BigDecimal.ZERO;//退票利润 
	
	private java.math.BigDecimal amountMore=BigDecimal.ZERO;// 多收票款
	private java.math.BigDecimal taxMore=BigDecimal.ZERO;// 多收税
	private java.math.BigDecimal commission=BigDecimal.ZERO;//现返
	
	private java.math.BigDecimal saleRakeOff=BigDecimal.ZERO;//后返
	
	private java.math.BigDecimal pureProfits;//净利合计
	private java.math.BigDecimal saleTotalAmount;//总金额(（实收票款）)
	private String entryOperatorName;//操作人	
	
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
	
	private String saleMemo;//
	private String buyMemo;//
	
		
	//计算后
	private java.math.BigDecimal grossProfit=BigDecimal.ZERO;// 团毛利润
	private java.math.BigDecimal buyRakeOff=BigDecimal.ZERO;//// 月底返代理费
	private java.math.BigDecimal buyTicketPrice=BigDecimal.ZERO;//应付票款
	private java.math.BigDecimal totalAirportFuelPrice=BigDecimal.ZERO;//机场税
	private java.math.BigDecimal pureProfitsInfo=BigDecimal.ZERO;//净利合计
	
	
	private String outAccountName;//支付账号
	private java.math.BigDecimal outAmount=BigDecimal.ZERO;//支付金额
	private Timestamp entryTime;//录单时间
	private Timestamp payTime;//付款时间
	private String outOperatorName;//支付人
	private String outMemo;//支付备注
	
	private String outRefundAccountName;//付退款账号
	private java.math.BigDecimal outRefundAmount=BigDecimal.ZERO;//付退款金额
	private Timestamp outRefundTime;//付退款时间
	private String outRefundMemo;//付退款备注
	
	private String inRefundAccountName;//收退款账号
	private java.math.BigDecimal inRefundAmount=BigDecimal.ZERO;//收退款金额
	private Timestamp inRefundTime;//收退款时间
	private String inRefundMemo;//收退款备注
	
	
	
	public String getOrderNos() {
		return orderNos;
	}

	public void setOrderNos(String orderNos) {
		this.orderNos = orderNos;
	}
	
	public void setOrderNos(AirticketOrder saleOrder, AirticketOrder buyOrder) {
		String orderNos = "";
		if (saleOrder != null) {
			orderNos += saleOrder.getOrderNo() + "/";
		}
		if (buyOrder != null) {
			orderNos += buyOrder.getOrderNo();
		}
		
		this.orderNos=orderNos;
	}
	

	public String getEntryOperatorName() {
		return entryOperatorName;
	}

	public void setEntryOperatorName(String entryOperatorName) {
		this.entryOperatorName = entryOperatorName;
	}

	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	public java.math.BigDecimal getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(java.math.BigDecimal grossProfit) {
		this.grossProfit = grossProfit;
	}

	public java.math.BigDecimal getPureProfitsInfo() {
		return pureProfitsInfo;
	}

	public void setPureProfitsInfo(java.math.BigDecimal pureProfitsInfo) {
		this.pureProfitsInfo = pureProfitsInfo;
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
	
	
	public String getFlightsTxt() {
		return flightsTxt;
	}

	public void setFlightsTxt(String flightsTxt) {
		this.flightsTxt = flightsTxt;
	}

	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}


	public String getBuyAirticketNo() {
		return buyAirticketNo;
	}
	public void setBuyAirticketNo(String buyAirticketNo) {
		this.buyAirticketNo = buyAirticketNo;
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
	
	
	
	public java.math.BigDecimal getOutAmount() {
		if(this.outAmount !=null)
		{
			return outAmount;
		}else
		{
		return BigDecimal.ZERO;
		}
	}

	public void setOutAmount(java.math.BigDecimal outAmount) {
		this.outAmount = outAmount;
	}



	public String getOutAccountName() {
		return outAccountName;
	}

	public void setOutAccountName(String outAccountName) {
		this.outAccountName = outAccountName;
	}

	public String getOutOperatorName() {
		return outOperatorName;
	}

	public void setOutOperatorName(String outOperatorName) {
		this.outOperatorName = outOperatorName;
	}

	public String getOutMemo() {
		return outMemo;
	}

	public void setOutMemo(String outMemo) {
		this.outMemo = outMemo;
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

	
	public Timestamp getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Timestamp entryTime) {
		this.entryTime = entryTime;
	}
	
	public String getFormatEntryTime(String formatExp) {		
		if(entryTime!=null){
			return DateUtil.getDateString(entryTime,formatExp);
		}
		return "";
	}
	public Timestamp getPayTime() {
		return payTime;
	}

	public void setPayTime(Timestamp payTime) {
		this.payTime = payTime;
	}
	
	public String getFormatPayTime(String formatExp) {		
		if(payTime!=null){
			return DateUtil.getDateString(payTime,formatExp);
		}
		return "";
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
	
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public java.math.BigDecimal getSaleTotalAmount() {
		if(this.saleTotalAmount !=null)
		{
			return saleTotalAmount;
		}else
		{
			return BigDecimal.valueOf(0);
		}
	}

	public void setSaleTotalAmount(java.math.BigDecimal saleTotalAmount) {
		this.saleTotalAmount = saleTotalAmount;
	}

	public java.math.BigDecimal getBuyTotalAmount() {
		return buyTotalAmount;
	}

	public void setBuyTotalAmount(java.math.BigDecimal buyTotalAmount) {
		this.buyTotalAmount = buyTotalAmount;
	}

	public java.math.BigDecimal getBuyRakeOff() {
		return buyRakeOff;
	}

	public void setBuyRakeOff(java.math.BigDecimal buyRakeOff) {
		this.buyRakeOff = buyRakeOff;
	}

	public java.math.BigDecimal getBuyTicketPrice() {
		return buyTicketPrice;
	}

	public void setBuyTicketPrice(java.math.BigDecimal buyTicketPrice) {
		this.buyTicketPrice = buyTicketPrice;
	}
 
	public java.math.BigDecimal getTotalAirportFuelPrice() {
		return totalAirportFuelPrice;
	}

	public void setTotalAirportFuelPrice(java.math.BigDecimal totalAirportFuelPrice) {
		this.totalAirportFuelPrice = totalAirportFuelPrice;
	}

	public String getOutRefundAccountName() {
		return outRefundAccountName;
	}

	public void setOutRefundAccountName(String outRefundAccountName) {
		this.outRefundAccountName = outRefundAccountName;
	}

	public java.math.BigDecimal getOutRefundAmount() {
		return outRefundAmount;
	}

	public void setOutRefundAmount(java.math.BigDecimal outRefundAmount) {
		this.outRefundAmount = outRefundAmount;
	}

	public Timestamp getOutRefundTime() {
		return outRefundTime;
	}

	public void setOutRefundTime(Timestamp outRefundTime) {
		this.outRefundTime = outRefundTime;
	}

	public String getOutRefundMemo() {
		return outRefundMemo;
	}

	public void setOutRefundMemo(String outRefundMemo) {
		this.outRefundMemo = outRefundMemo;
	}

	public String getInRefundAccountName() {
		return inRefundAccountName;
	}

	public void setInRefundAccountName(String inRefundAccountName) {
		this.inRefundAccountName = inRefundAccountName;
	}

	public java.math.BigDecimal getInRefundAmount() {
		return inRefundAmount;
	}

	public void setInRefundAmount(java.math.BigDecimal inRefundAmount) {
		this.inRefundAmount = inRefundAmount;
	}

	public Timestamp getInRefundTime() {
		return inRefundTime;
	}

	public void setInRefundTime(Timestamp inRefundTime) {
		this.inRefundTime = inRefundTime;
	}

	public String getInRefundMemo() {
		return inRefundMemo;
	}

	public void setInRefundMemo(String inRefundMemo) {
		this.inRefundMemo = inRefundMemo;
	}

	public java.math.BigDecimal getSaleRakeOff() {
		return saleRakeOff;
	}

	public void setSaleRakeOff(java.math.BigDecimal saleRakeOff) {
		this.saleRakeOff = saleRakeOff;
	}

	public String getSaleMemo() {
		return saleMemo;
	}

	public void setSaleMemo(String saleMemo) {
		this.saleMemo = saleMemo;
	}

	public String getBuyMemo() {
		return buyMemo;
	}

	public void setBuyMemo(String buyMemo) {
		this.buyMemo = buyMemo;
	}
	
	
}
