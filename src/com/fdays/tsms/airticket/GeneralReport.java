package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Date;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.AirticketOrderReport;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class GeneralReport {
	private String orderNos = "";
	private java.sql.Timestamp entryTime;// 订单时间
	private String salePlatform = ""; // 卖出商(平台)
	private String buyPlatform = "";// 买入商(平台)
	private java.math.BigDecimal saleRebate=BigDecimal.ZERO;// 卖出商 返点
	private java.math.BigDecimal buyRebate = BigDecimal.ZERO;// 买入商 返点
	private java.math.BigDecimal payOffPoint = BigDecimal.ZERO;// 亏点
	private String subPnr = ""; // 预定pnr
	private String drawPnr = "";// 出票pnr
	private String bigPnr = ""; // 大pnr
	private String passengerName = ""; // 乘客姓名
	private int passengerNumber = 0;// 乘机人数
	private String startPoint = "";// 出发地
	private String endPoint = ""; // 目的地
	private String flightsTxt="";//航程：AAA-BBB/BBB-AAA
	private String cyr = "";// 承运人
	private String flightCode = "";// 航班号
	private String flightClass = "";//舱位
	private String discount = "";// 折扣
	private java.math.BigDecimal ticketPrice = BigDecimal.ZERO;// 单张票面价
	private java.math.BigDecimal AllTicketPrice = BigDecimal.ZERO;// 票面总价
	private java.math.BigDecimal airportPrice = BigDecimal.ZERO;// 单张机建税
	private java.math.BigDecimal AllAirportPrice = BigDecimal.ZERO;// 总机建税
	private java.math.BigDecimal fuelPrice = BigDecimal.ZERO;// 单张燃油税
	private java.math.BigDecimal allFuelPrice = BigDecimal.ZERO;// 总燃油税
	private java.sql.Timestamp boardingTime;// 起飞时间
	private String ticketNumber = "";// 票号
	
	private String saleAirOrderNo = "";// 卖出商订单号
	private String buyAirOrderNo = "";// 买入商订单号		

	private java.math.BigDecimal profit = BigDecimal.ZERO;// 利润
	
	private String entryOperatorName = "";// 操作人
	
	private String saleStatus = "";// 供应状态
	private String buyStatus = "";// 采购状态
	private String saleMemo = "";// 供应备注
	private String buyMemo = "";// 采购备注
	private java.math.BigDecimal saleHandlingCharge = BigDecimal.ZERO; // 手续费（供应)
	private java.math.BigDecimal buyHandlingCharge = BigDecimal.ZERO; // 手续费(采购)
	
	private java.math.BigDecimal inAmount = BigDecimal.ZERO;// 实际收入
	private java.math.BigDecimal reportInAmount = BigDecimal.ZERO;// 报表收入
	
	private java.math.BigDecimal outAmount = BigDecimal.ZERO;// 实际支出
	private java.math.BigDecimal reportOutAmount = BigDecimal.ZERO;// 报表支出
	
	private String inAccount="";	
	private String outAccount="";	
	
	//--退款
	private java.math.BigDecimal inRefundAmount=BigDecimal.ZERO;	
	private java.math.BigDecimal outRefundAmount=BigDecimal.ZERO;
	
	private String inRefundAccount="";	
	private String outRefundAccount="";	
	
	private String outOperatorName = "";// 支付人
	private String inRefundOperator="";//收退款操作人
	private String outRefundOperator="";//付退款操作人
	
	private java.sql.Timestamp inRefundTime;// 收退款时间
	private java.sql.Timestamp outRefundTime;// 付退款时间
	
	private String saleOldOrderNo = "";
	private String buyOldOrderNo = "";

	private String retireType = "";

	protected java.util.Set flights = new java.util.HashSet(0);
	protected java.util.Set passengers = new java.util.HashSet(0);

	public String getBoardingDate() {
		if (boardingTime != null && "".equals(boardingTime) == false) {
			Date tempDate = new Date(boardingTime.getTime());
			return DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	


	public java.sql.Timestamp getInRefundTime() {
		return inRefundTime;
	}
	public void setInRefundTime(java.sql.Timestamp inRefundTime) {
		this.inRefundTime = inRefundTime;
	}

	public String getFormatInRefundTime() {
		if (inRefundTime != null && "".equals(inRefundTime) == false) {
			Date tempDate = new Date(inRefundTime.getTime());
			return DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}
	
	public String getFormatOutRefundTime() {
		if (outRefundTime != null && "".equals(outRefundTime) == false) {
			Date tempDate = new Date(outRefundTime.getTime());
			return DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return "";
	}	
	public java.sql.Timestamp getOutRefundTime() {
		return outRefundTime;
	}
	public void setOutRefundTime(java.sql.Timestamp outRefundTime) {
		this.outRefundTime = outRefundTime;
	}
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
	
	public void setOrderNos(AirticketOrderReport saleOrder, AirticketOrderReport buyOrder) {
		String orderNos = "";
		if (saleOrder != null) {
			orderNos += saleOrder.getOrderNo() + "/";
		}
		if (buyOrder != null) {
			orderNos += buyOrder.getOrderNo();
		}
		
		this.orderNos=orderNos;

	}

	public String getRetireType() {
		return retireType;
	}

	public void setRetireType(String retireType) {
		this.retireType = retireType;
	}
	
	

	public String getSaleStatus() {
		return saleStatus;
	}



	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}



	public String getBuyStatus() {
		return buyStatus;
	}



	public void setBuyStatus(String buyStatus) {
		this.buyStatus = buyStatus;
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

	public String getBuyPlatform() {
		return buyPlatform;
	}

	public void setBuyPlatform(String buyPlatform) {
		this.buyPlatform = buyPlatform;
	}

	public String getSalePlatform() {
		return salePlatform;
	}

	public void setSalePlatform(String salePlatform) {
		this.salePlatform = salePlatform;
	}


	
	public java.math.BigDecimal getSaleRebate() {
		return saleRebate;
	}



	public void setSaleRebate(java.math.BigDecimal saleRebate) {
		this.saleRebate = saleRebate;
	}



	public java.math.BigDecimal getBuyRebate() {
		return buyRebate;
	}



	public void setBuyRebate(java.math.BigDecimal buyRebate) {
		this.buyRebate = buyRebate;
	}



	public java.math.BigDecimal getPayOffPoint() {
		payOffPoint = Constant.toBigDecimal(getBuyRebate()).subtract(
					Constant.toBigDecimal(getSaleRebate()));
		return payOffPoint;
	}

	public void setPayOffPoint(java.math.BigDecimal payOffPoint) {
		this.payOffPoint = payOffPoint;
	}

	public String getSubPnr() {
		return subPnr;
	}

	public void setSubPnr(String subPnr) {
		this.subPnr = subPnr;
	}

	public String getDrawPnr() {
		return drawPnr;
	}

	public void setDrawPnr(String drawPnr) {
		this.drawPnr = drawPnr;
	}

	public String getBigPnr() {
		return bigPnr;
	}

	public void setBigPnr(String bigPnr) {
		this.bigPnr = bigPnr;
	}

	public String getPassengerName() {
		return passengerName;
	}

	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}

	public int getPassengerNumber() {
		return passengerNumber;
	}

	public void setPassengerNumber(int passengerNumber) {
		this.passengerNumber = passengerNumber;
	}

	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}



	public java.math.BigDecimal getSaleHandlingCharge() {
		return saleHandlingCharge;
	}



	public void setSaleHandlingCharge(java.math.BigDecimal saleHandlingCharge) {
		this.saleHandlingCharge = saleHandlingCharge;
	}



	public java.math.BigDecimal getBuyHandlingCharge() {
		return buyHandlingCharge;
	}



	public void setBuyHandlingCharge(java.math.BigDecimal buyHandlingCharge) {
		this.buyHandlingCharge = buyHandlingCharge;
	}



	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getCyr() {
		return cyr;
	}

	public void setCyr(String cyr) {
		this.cyr = cyr;
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

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public java.math.BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(java.math.BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public java.math.BigDecimal getAllTicketPrice() {
		return AllTicketPrice;
	}

	public void setAllTicketPrice(java.math.BigDecimal allTicketPrice) {
		AllTicketPrice = allTicketPrice;
	}

	public java.math.BigDecimal getAirportPrice() {
		return airportPrice;
	}

	public void setAirportPrice(java.math.BigDecimal airportPrice) {
		this.airportPrice = airportPrice;
	}

	public java.math.BigDecimal getAllAirportPrice() {
		return AllAirportPrice;
	}

	public void setAllAirportPrice(java.math.BigDecimal allAirportPrice) {
		AllAirportPrice = allAirportPrice;
	}

	public java.math.BigDecimal getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(java.math.BigDecimal fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public java.math.BigDecimal getAllFuelPrice() {
		return allFuelPrice;
	}

	public void setAllFuelPrice(java.math.BigDecimal allFuelPrice) {
		this.allFuelPrice = allFuelPrice;
	}

	public java.sql.Timestamp getBoardingTime() {
		return boardingTime;
	}

	public void setBoardingTime(java.sql.Timestamp boardingTime) {
		this.boardingTime = boardingTime;
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getSaleAirOrderNo() {
		return saleAirOrderNo;
	}

	public void setSaleAirOrderNo(String saleAirOrderNo) {
		this.saleAirOrderNo = saleAirOrderNo;
	}
	
	

	public String getSaleOldOrderNo() {
		return saleOldOrderNo;
	}



	public void setSaleOldOrderNo(String saleOldOrderNo) {
		this.saleOldOrderNo = saleOldOrderNo;
	}



	public String getBuyOldOrderNo() {
		return buyOldOrderNo;
	}



	public void setBuyOldOrderNo(String buyOldOrderNo) {
		this.buyOldOrderNo = buyOldOrderNo;
	}



	public String getInRefundOperator() {
		return inRefundOperator;
	}
	public void setInRefundOperator(String inRefundOperator) {
		this.inRefundOperator = inRefundOperator;
	}

	
	public String getOutRefundOperator() {
		return outRefundOperator;
	}
	public void setOutRefundOperator(String outRefundOperator) {
		this.outRefundOperator = outRefundOperator;
	}
	
	public void setOutOperatorName(String outOperatorName) {
		this.outOperatorName = outOperatorName;
	}



	public String getOutOperatorName() {
		return outOperatorName;
	}
	
	public String getInRefundOperatorName() {
		if (inRefundOperator != null && "".equals(inRefundOperator) == false) {
			return UserStore.getUserNameByNo(inRefundOperator);
		}
		return inRefundOperator;
	}
	public String getOutRefundOperatorName() {
		if (outRefundOperator != null && "".equals(outRefundOperator) == false) {
			return UserStore.getUserNameByNo(outRefundOperator);
		}
		return outRefundOperator;
	}
	

	public java.math.BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(java.math.BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public java.math.BigDecimal getReportInAmount() {
		return reportInAmount;
	}

	public void setReportInAmount(java.math.BigDecimal reportInAmount) {
		this.reportInAmount = reportInAmount;
	}

	public String getBuyAirOrderNo() {
		return buyAirOrderNo;
	}

	public void setBuyAirOrderNo(String buyAirOrderNo) {
		this.buyAirOrderNo = buyAirOrderNo;
	}	

	public java.math.BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(java.math.BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public java.math.BigDecimal getReportOutAmount() {
		return reportOutAmount;
	}

	public void setReportOutAmount(java.math.BigDecimal reportOutAmount) {
		this.reportOutAmount = reportOutAmount;
	}


	public java.math.BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(java.math.BigDecimal profit) {
		this.profit = profit;
	}
	public String getEntryOperatorName() {
		return entryOperatorName;
	}
	
	public void setEntryOperatorName(String entryOperatorName) {
		this.entryOperatorName = entryOperatorName;
	}



	public java.sql.Timestamp getEntryTime() {
		return entryTime;
	}

	public String getFormatEntryTime(String formatExp) {
		String mydate = "";
		if (entryTime != null && "".equals(entryTime) == false) {
			Date tempDate = new Date(entryTime.getTime());
			mydate = DateUtil.getDateString(tempDate,formatExp);
		}
		return mydate;
	}

	public void setEntryTime(java.sql.Timestamp entryTime) {
		this.entryTime = entryTime;
	}

	public String getInAccount() {
		return inAccount;
	}



	public void setInAccount(String inAccount) {
		this.inAccount = inAccount;
	}



	public String getOutAccount() {
		return outAccount;
	}



	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}



	public java.math.BigDecimal getInRefundAmount() {
		return inRefundAmount;
	}



	public void setInRefundAmount(java.math.BigDecimal inRefundAmount) {
		this.inRefundAmount = inRefundAmount;
	}



	public java.math.BigDecimal getOutRefundAmount() {
		return outRefundAmount;
	}



	public void setOutRefundAmount(java.math.BigDecimal outRefundAmount) {
		this.outRefundAmount = outRefundAmount;
	}



	public String getInRefundAccount() {
		return inRefundAccount;
	}



	public void setInRefundAccount(String inRefundAccount) {
		this.inRefundAccount = inRefundAccount;
	}



	public String getOutRefundAccount() {
		return outRefundAccount;
	}



	public void setOutRefundAccount(String outRefundAccount) {
		this.outRefundAccount = outRefundAccount;
	}



	public String getFlightsTxt() {
		return flightsTxt;
	}



	public void setFlightsTxt(String flightsTxt) {
		this.flightsTxt = flightsTxt;
	}

	
}
