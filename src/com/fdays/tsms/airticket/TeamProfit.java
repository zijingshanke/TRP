package com.fdays.tsms.airticket;

import java.math.BigDecimal;

import com.fdays.tsms.airticket.util.ArithUtil;

/**
 * 团队利润统计规则
 * 
 * @author yanrui
 */
public class TeamProfit {
	private AirticketOrder saleOrder=new AirticketOrder();//卖出(对客户)
	private AirticketOrder buyOrder=new AirticketOrder();//买入(对航司)
	
	private java.math.BigDecimal saleOverPrice=BigDecimal.ZERO;//多收票价
	
	private java.math.BigDecimal commission=BigDecimal.ZERO;//现返
	private java.math.BigDecimal saleTicketPrice=BigDecimal.ZERO;//应收票款
	private java.math.BigDecimal saleTotalAmount=BigDecimal.ZERO;//实收票款	
	//-------------------------
	private java.math.BigDecimal grossProfit=BigDecimal.ZERO;//毛利润	
	private java.math.BigDecimal buyRakeOff=BigDecimal.ZERO;//月底返代理费	
	private java.math.BigDecimal buyTicketPrice=BigDecimal.ZERO;//应付票款	
	private java.math.BigDecimal buyTotalAmount=BigDecimal.ZERO;//实付票款	
	//--------------------------
	private java.math.BigDecimal refundProfit=BigDecimal.ZERO;//退票利润
	private java.math.BigDecimal totalProfit=BigDecimal.ZERO;//净利润	
	
	private java.math.BigDecimal totalAirportFuelPrice=BigDecimal.ZERO;//机建燃油税
	
	
	public TeamProfit(){
		
	} 
	
	public TeamProfit(AirticketOrder saleOrder,AirticketOrder buyOrder){
		this.saleOrder=saleOrder;
		this.buyOrder=buyOrder;
	}	
	
	//多收票价=团队加价+客户加价
	public java.math.BigDecimal getSaleOverPrice() {
		saleOverPrice=saleOrder.getTeamaddPrice().add(saleOrder.getAgentaddPrice());
		return saleOverPrice;
	}	

	//退票利润=收退票手续费-付退票手续费
	public java.math.BigDecimal getRefundProfit() {
		refundProfit=saleOrder.getIncomeretreatCharge().subtract(buyOrder.getIncomeretreatCharge());
		return refundProfit;
	}
	
	//净利润=团毛利润+退票利润+多收票款(团队和客户加价)+多收税款-应付出团代理费(现返)-应付出团代理费(未返)
	public java.math.BigDecimal getTotalProfit() {
		totalProfit=getGrossProfit().add(getRefundProfit());
		totalProfit=totalProfit.add(getSaleOverPrice());
		totalProfit=totalProfit.add(saleOrder.getOverAirportfulePrice());
		totalProfit=totalProfit.subtract(getCommission());
		totalProfit=totalProfit.subtract(saleOrder.getRakeOff());		
		return totalProfit;
	}

	//卖出--现返=(票面+多收票价)*返点
	public java.math.BigDecimal getCommission() {	
		commission=(saleOrder.getTotalTicketPrice().add(getSaleOverPrice())).multiply(saleOrder.getCommissonCount());
		commission=ArithUtil.round(commission,0);
		return commission;
	}
	
	//卖出--应收票款=（票面+多收票价）-现返+多收税-收退票手续费
	public java.math.BigDecimal getSaleTicketPrice() {
		saleTicketPrice=(saleOrder.getTotalTicketPrice().add(getSaleOverPrice()).subtract(getCommission()).add(saleOrder.getOverAirportfulePrice()).subtract(saleOrder.getIncomeretreatCharge()));
		return saleTicketPrice;
	}	
	
	//卖出--实收票款=应收票款+机建燃油税
	public java.math.BigDecimal getSaleTotalAmount() {
		saleTotalAmount=getSaleTicketPrice().add(getTotalAirportFuelPrice());
		return saleTotalAmount;
	}
	
	//毛利润=票面价*返点-手续费
	public java.math.BigDecimal getGrossProfit() {		
		grossProfit=buyOrder.getTotalTicketPrice().multiply(buyOrder.getCommissonCount());
		//grossProfit=grossProfit.subtract(buyOrder.getHandlingCharge());
		return grossProfit;
	}	
	
	//买入--月底返代理费=票面价*后返点数
	public java.math.BigDecimal getBuyRakeOff() {
		buyRakeOff=saleOrder.getTotalTicketPrice().multiply(buyOrder.getRakeoffCount());
		return buyRakeOff;
	}	
	
	//应付票款=票面价-毛利润-付退票手续费
	public java.math.BigDecimal getBuyTicketPrice() {
		buyTicketPrice=buyOrder.getTotalTicketPrice().subtract(getGrossProfit()).subtract(buyOrder.getIncomeretreatCharge());
		//System.out.println("buyTicketPrice:"+buyTicketPrice);
		return buyTicketPrice;
	}
	
	//实付票款=应收票款+机建燃油税
	public java.math.BigDecimal getBuyTotalAmount() {
		buyTotalAmount=getBuyTicketPrice().add(getTotalAirportFuelPrice());
		//System.out.println("buyTotalAmount:"+buyTotalAmount);
		return buyTotalAmount;
	}

	//机建燃油税
	public java.math.BigDecimal getTotalAirportFuelPrice() {
		totalAirportFuelPrice=saleOrder.getTotalAirportPrice().add(saleOrder.getTotalFuelPrice());
		return totalAirportFuelPrice;
	}		
	public void setSaleOverPrice(java.math.BigDecimal saleOverPrice) {
		this.saleOverPrice = saleOverPrice;
	}
	
	public AirticketOrder getSaleOrder() {
		return saleOrder;
	}
	public void setSaleOrder(AirticketOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
	public AirticketOrder getBuyOrder() {
		return buyOrder;
	}
	public void setBuyOrder(AirticketOrder buyOrder) {
		this.buyOrder = buyOrder;
	}

}
