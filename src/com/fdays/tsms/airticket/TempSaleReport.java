package com.fdays.tsms.airticket;

import java.math.BigDecimal;

public class TempSaleReport {

	private java.sql.Timestamp orderTime;//订单时间
	private String toCompany;  //卖出商
	private String fromCompany;//买入商
	private java.math.BigDecimal toCompany_fanDian;//卖出商 返点
	private java.math.BigDecimal fromCompany_fanDian;//买入商 返点
	private java.math.BigDecimal kueiDian=new BigDecimal(0);//亏点
	private String subPnr; //预定pnr
	private String drawPnr;//出票pnr
	private String bigPnr; //大pnr
	private String passengerName;  //乘客姓名
	private int passengerNumber;//乘机人数
	private String startPoint;//出发地
	private String endPoint;  //目的地
	private String cyr;//承运人
	private String flightCode;//航班号
	private String flightClass;//仓位
	private String discount;//折扣
	private java.math.BigDecimal ticketPrice;//单张票面价
	private java.math.BigDecimal AllTicketPrice;//票面总价
	private java.math.BigDecimal airportPrice;//单张机建税
	private java.math.BigDecimal AllAirportPrice;//总机建税
	private java.math.BigDecimal fuelPrice;//单张燃油税
	private java.math.BigDecimal allFuelPrice;//总燃油税
	private java.sql.Timestamp boardingTime;//起飞时间
	private String ticketNumber;//票号
	private String toAirOrderNo;//卖出商订单号
	private java.math.BigDecimal realIncome;//实际收入
	private java.math.BigDecimal reportIncome;//报表收入
	private String  toPCAccount;//收款帐号
	private String fromAirOrderNo;//买入商订单号
	private java.math.BigDecimal realPayout;//实际支出
	private java.math.BigDecimal reportPayout;//报表支出
	private String  fromPCAccount;//付款帐号
	private java.math.BigDecimal profit;//利润
	private String sysUser;//操作人
	private String toState;//供应状态
	private String fromState;//采购状态
	private String toRemark;//供应备注
	private String fromRemark;//采购备注
    protected java.util.Set flights = new java.util.HashSet(0);
    protected java.util.Set passengers = new java.util.HashSet(0);
	
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
	public String getToCompany() {
		return toCompany;
	}
	public void setToCompany(String toCompany) {
		this.toCompany = toCompany;
	}
	public String getFromCompany() {
		return fromCompany;
	}
	public void setFromCompany(String fromCompany) {
		this.fromCompany = fromCompany;
	}
	public java.math.BigDecimal getToCompany_fanDian() {
		return toCompany_fanDian;
	}
	public void setToCompany_fanDian(java.math.BigDecimal toCompany_fanDian) {
		this.toCompany_fanDian = toCompany_fanDian;
	}
	public java.math.BigDecimal getFromCompany_fanDian() {
		return fromCompany_fanDian;
	}
	public void setFromCompany_fanDian(java.math.BigDecimal fromCompany_fanDian) {
		this.fromCompany_fanDian = fromCompany_fanDian;
	}
	public java.math.BigDecimal getKueiDian() {
		if(this.getToCompany_fanDian()!=null&&this.getFromCompany_fanDian()!=null){
			kueiDian=this.getFromCompany_fanDian().subtract(this.getToCompany_fanDian());
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
	public java.math.BigDecimal getRealIncome() {
		return realIncome;
	}
	public void setRealIncome(java.math.BigDecimal realIncome) {
		this.realIncome = realIncome;
	}
	public java.math.BigDecimal getReportIncome() {
		return reportIncome;
	}
	public void setReportIncome(java.math.BigDecimal reportIncome) {
		this.reportIncome = reportIncome;
	}
	public String getToPCAccount() {
		return toPCAccount;
	}
	public void setToPCAccount(String toPCAccount) {
		this.toPCAccount = toPCAccount;
	}
	public String getFromAirOrderNo() {
		return fromAirOrderNo;
	}
	public void setFromAirOrderNo(String fromAirOrderNo) {
		this.fromAirOrderNo = fromAirOrderNo;
	}
	public java.math.BigDecimal getRealPayout() {
		return realPayout;
	}
	public void setRealPayout(java.math.BigDecimal realPayout) {
		this.realPayout = realPayout;
	}
	public java.math.BigDecimal getReportPayout() {
		return reportPayout;
	}
	public void setReportPayout(java.math.BigDecimal reportPayout) {
		this.reportPayout = reportPayout;
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
	public void setOrderTime(java.sql.Timestamp orderTime) {
		this.orderTime = orderTime;
	}
	
	
	
}
