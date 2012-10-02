package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Date;

import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class TempSaleReport {
	private String orderNos = "";
	private java.sql.Timestamp orderTime;// 订单时间
	private String toPlatform = ""; // 卖出商(平台)
	private String fromPlatform = "";// 买入商(平台)
	private java.math.BigDecimal toCompany_fanDian=BigDecimal.ZERO;// 卖出商 返点
	private java.math.BigDecimal fromCompany_fanDian = BigDecimal.ZERO;// 买入商 返点
	private java.math.BigDecimal kueiDian = BigDecimal.ZERO;// 亏点
	private String subPnr = ""; // 预定pnr
	private String drawPnr = "";// 出票pnr
	private String bigPnr = ""; // 大pnr
	private String passengerName = ""; // 乘客姓名
	private int passengerNumber = 0;// 乘机人数
	private String startPoint = "";// 出发地
	private String endPoint = ""; // 目的地
	private String cyr = "";// 承运人
	private String flightCode = "";// 航班号
	private String flightClass = "";// 仓位
	private String discount = "";// 折扣
	private java.math.BigDecimal ticketPrice = BigDecimal.ZERO;// 单张票面价
	private java.math.BigDecimal AllTicketPrice = BigDecimal.ZERO;// 票面总价
	private java.math.BigDecimal airportPrice = BigDecimal.ZERO;// 单张机建税
	private java.math.BigDecimal AllAirportPrice = BigDecimal.ZERO;// 总机建税
	private java.math.BigDecimal fuelPrice = BigDecimal.ZERO;// 单张燃油税
	private java.math.BigDecimal allFuelPrice = BigDecimal.ZERO;// 总燃油税
	private java.sql.Timestamp boardingTime;// 起飞时间
	private String ticketNumber = "";// 票号
	private String toAirOrderNo = "";// 卖出商订单号

	private String fromAirOrderNo = "";// 买入商订单号		

	private java.math.BigDecimal profit = BigDecimal.ZERO;// 利润
	private String sysUser = "";// 操作人
	
	private String toState = "";// 供应状态
	private String fromState = "";// 采购状态
	private String toRemark = "";// 供应备注
	private String fromRemark = "";// 采购备注
	private java.math.BigDecimal toHandlingCharge = BigDecimal.ZERO; // 手续费（供应)
	private java.math.BigDecimal fromHandlingCharge = BigDecimal.ZERO; // 手续费(采购)
	
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
	
	private String fromPCAccount = "";// 付款帐号
	
	private String payOperator = "";// 支付人
	private String inRefundOperator="";//收退款操作人
	private String outRefundOperator="";//付退款操作人
	

	//---正常
	private java.sql.Timestamp formTime;// 时间
	private java.sql.Timestamp toTime;// 时间
	
	//--退款
	private java.sql.Timestamp inRefundTime;// 收退款时间
	private java.sql.Timestamp outRefundTime;// 付退款时间
	
	
	private java.math.BigDecimal formAmount = BigDecimal.ZERO;
	private java.math.BigDecimal toAmount = BigDecimal.ZERO;
	private String toOldOrderNo = "";
	private String fromOldOrderNo = "";

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

	public String getRetireType() {
		return retireType;
	}

	public void setRetireType(String retireType) {
		this.retireType = retireType;
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

	public String getToPlatform() {
		return toPlatform;
	}

	public void setToPlatform(String toPlatform) {
		this.toPlatform = toPlatform;
	}

	public String getFromPlatform() {
		return fromPlatform;
	}

	public void setFromPlatform(String fromPlatform) {
		this.fromPlatform = fromPlatform;
	}

	


	public java.math.BigDecimal getToCompany_fanDian() {
		if (this.toCompany_fanDian == null) {
			return BigDecimal.ZERO;
		}
		return toCompany_fanDian;
	}

	public void setToCompany_fanDian(java.math.BigDecimal toCompany_fanDian) {
		this.toCompany_fanDian = toCompany_fanDian;
	}

	public java.math.BigDecimal getFromCompany_fanDian() {
		if (this.fromCompany_fanDian == null) {
			return BigDecimal.ZERO;
		}
		return fromCompany_fanDian;
	}

	public void setFromCompany_fanDian(java.math.BigDecimal fromCompany_fanDian) {
		this.fromCompany_fanDian = fromCompany_fanDian;
	}

	public java.math.BigDecimal getKueiDian() {
		if (this.getToCompany_fanDian() != null
				&& this.getFromCompany_fanDian() != null) {
			kueiDian = this.getFromCompany_fanDian().subtract(
					this.getToCompany_fanDian());
		}
		return kueiDian;
	}

	public void setKueiDian(java.math.BigDecimal kueiDian) {
		this.kueiDian = kueiDian;
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

	public java.math.BigDecimal getToHandlingCharge() {
		return toHandlingCharge;
	}

	public void setToHandlingCharge(java.math.BigDecimal toHandlingCharge) {
		this.toHandlingCharge = toHandlingCharge;
	}

	public java.math.BigDecimal getFromHandlingCharge() {
		return fromHandlingCharge;
	}

	public void setFromHandlingCharge(java.math.BigDecimal fromHandlingCharge) {
		this.fromHandlingCharge = fromHandlingCharge;
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

	public String getToAirOrderNo() {
		return toAirOrderNo;
	}

	public void setToAirOrderNo(String toAirOrderNo) {
		this.toAirOrderNo = toAirOrderNo;
	}
	

	public String getInRefundOperator() {
		return inRefundOperator;
	}
	public void setInRefundOperator(String inRefundOperator) {
		this.inRefundOperator = inRefundOperator;
	}

	public String getPayOperator() {
		return payOperator;
	}
	public void setPayOperator(String payOperator) {
		this.payOperator = payOperator;
	}
	
	public String getOutRefundOperator() {
		return outRefundOperator;
	}
	public void setOutRefundOperator(String outRefundOperator) {
		this.outRefundOperator = outRefundOperator;
	}
	public String getPayOperatorName() {
		if (payOperator != null && "".equals(payOperator) == false) {
			return UserStore.getUserNameByNo(payOperator);
		}
		return payOperator;
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

	public String getFromAirOrderNo() {
		return fromAirOrderNo;
	}

	public void setFromAirOrderNo(String fromAirOrderNo) {
		this.fromAirOrderNo = fromAirOrderNo;
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

	public String getFromPCAccount() {
		return fromPCAccount;
	}

	public void setFromPCAccount(String fromPCAccount) {
		this.fromPCAccount = fromPCAccount;
	}

	public java.math.BigDecimal getProfit() {
		return profit;
	}

	public void setProfit(java.math.BigDecimal profit) {
		this.profit = profit;
	}

	public String getSysUser() {
		return sysUser;
	}

	public String getSysUserName() {
		if (sysUser != null && "".equals(sysUser) == false) {
			return UserStore.getUserNameByNo(sysUser);
		}

		return sysUser;
	}

	public void setSysUser(String sysUser) {
		this.sysUser = sysUser;
	}

	public String getToState() {
		return toState;
	}

	public void setToState(String toState) {
		this.toState = toState;
	}

	public String getFromState() {
		return fromState;
	}

	public void setFromState(String fromState) {
		this.fromState = fromState;
	}

	public String getToRemark() {
		return toRemark;
	}

	public void setToRemark(String toRemark) {
		this.toRemark = toRemark;
	}

	public String getFromRemark() {
		return fromRemark;
	}

	public void setFromRemark(String fromRemark) {
		this.fromRemark = fromRemark;
	}

	public java.sql.Timestamp getOrderTime() {
		return orderTime;
	}

	public String getOrderDate() {
		String mydate = "";
		if (orderTime != null && "".equals(orderTime) == false) {
			Date tempDate = new Date(orderTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public void setOrderTime(java.sql.Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public java.sql.Timestamp getFormTime() {
		return formTime;
	}

	public String getFormDate() {
		String mydate = "";
		if (formTime != null && "".equals(formTime) == false) {
			Date tempDate = new Date(formTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public void setFormTime(java.sql.Timestamp formTime) {
		this.formTime = formTime;
	}

	public java.sql.Timestamp getToTime() {
		return toTime;
	}

	public String getToDate() {
		String mydate = "";
		if (toTime != null && "".equals(toTime) == false) {
			Date tempDate = new Date(toTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public void setToTime(java.sql.Timestamp toTime) {
		this.toTime = toTime;
	}

	public java.math.BigDecimal getFormAmount() {
		return formAmount;
	}

	public void setFormAmount(java.math.BigDecimal formAmount) {
		this.formAmount = formAmount;
	}

	public java.math.BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(java.math.BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public String getToOldOrderNo() {
		return toOldOrderNo;
	}

	public void setToOldOrderNo(String toOldOrderNo) {
		this.toOldOrderNo = toOldOrderNo;
	}

	public String getFromOldOrderNo() {
		return fromOldOrderNo;
	}

	public void setFromOldOrderNo(String fromOldOrderNo) {
		this.fromOldOrderNo = fromOldOrderNo;
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

	
}
