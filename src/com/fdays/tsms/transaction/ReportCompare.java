package com.fdays.tsms.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction._entity._ReportCompare;
import com.neza.tool.DateUtil;

public class ReportCompare extends _ReportCompare {
	private static final long serialVersionUID = 1L;
	// 优先匹配 PNR、金额、银行账号、订单号、人数、航程
	public Long platformId;
	public Long accountId;
	
	public Account inAccount;
	public Account outAccount;
	public Account inRetireAccount;
	public Account outRetireAccount;

	public BigDecimal perOutAmount = BigDecimal.ZERO;

	public BigDecimal airportPrice = BigDecimal.ZERO;
	public BigDecimal fuelPrice = BigDecimal.ZERO;
	public BigDecimal drawProfits=BigDecimal.ZERO;

	public String filePath = "";
	public String fileName = "";
	public String listAttachName = "";
	public String beginDateStr = "";
	public String endDateStr = "";
	
	//--------合计
	public int totalRowNum = -1;
	public BigDecimal totalInAmount = BigDecimal.ZERO;
	public BigDecimal totalOutAmount = BigDecimal.ZERO;
	public Account totalInRetireAccount;
	public Account totalOutRetireAccount;
	public BigDecimal totalInRetireAmount = BigDecimal.ZERO;
	public BigDecimal totalOutRetireAmount = BigDecimal.ZERO;
	public Long totalPassengerCount = Long.valueOf(0);

	public AirticketOrder order;// 查询待匹配数据中转

	public static final long TYPE_1 = 1;// 销售
	public static final long TYPE_2 = 2;// 采购
	public static final long TYPE_13 = 13;// 供应退废
	public static final long TYPE_14 = 14;// 采购退废
	public static final long TYPE_15 = 15;// 供应退票
	public static final long TYPE_16 = 16;// 采购退票
	public static final long TYPE_17 = 17;// 供应废票
	public static final long TYPE_18 = 18;// 采购废票


	public static final long RESULT_TYPE_1 = 1;// 对账只存在于本系统
	public static final long RESULT_TYPE_2 = 2;// 对账只存在于上传文件
	
	public static final long STATES_1 = 1;// 有效
	public static final long STATES_0 = 0;// 无效

	public ReportCompare() {
	}

	public ReportCompare(AirticketOrder order, String inStatement,
			String outStatement, String inRefundStatement,
			String outRefundStatement) {
		if (order != null) {
			this.platformId = order.getPlatform().getId();
			this.order = order;
			setOrderAsStatement(inStatement, Statement.SUBTYPE_10);
			setOrderAsStatement(outStatement, Statement.SUBTYPE_20);
			setOrderAsStatement(inRefundStatement, Statement.SUBTYPE_11);
			setOrderAsStatement(outRefundStatement, Statement.SUBTYPE_21);
			setPlatformCompare(this.order);
		}
	}
	
	public ReportCompare(AirticketOrder order) {
		if (order != null) {
			this.platformId = order.getPlatform().getId();
			this.order = order;
	
			setPlatformCompare(this.order);
		}
	}

	public ReportCompare(AirticketOrder order, Flight flight) {
		if (order != null&&flight!=null) {
		this.platformId = order.getPlatform().getId();
		this.order = order;

		setPlatformCompare(this.order);

		this.flightCode = flight.getFlightCode();
		this.flightClass = flight.getFlightClass();
		this.startPoint = flight.getStartPoint();
		this.endPoint = flight.getEndPoint();
		}
	}

	public void setPlatformCompare(AirticketOrder order) {
		if (order != null) {
			this.orderId = order.getId();
			this.platformId = order.getPlatform().getId();
			this.subPnr = order.getSubPnr();
			this.airOrderNo = order.getAirOrderNo();
			this.inAccountName = order.getInAccountName();
			this.inAccountNo = order.getInAccountNo();

			this.outAccountName = order.getOutAccountName();
			this.outAccountNo = order.getOutAccountNo();

			this.inAmount = order.getInAmount();
			this.outAmount = order.getOutAmount();

			this.ticketPrice=order.getTicketPrice();
			this.airportPrice=order.getAirportPrice();		
			this.fuelPrice=order.getFuelPrice();		
			
			this.passengerCount = new Long(order.getPassengerSize());
		}
	}

	public ReportCompare(AirticketOrder order, Flight flight,
			Passenger passenger) {
		this.order = order;

		setPlatformCompare(this.order);

		this.flightCode = flight.getFlightCode();
		this.flightClass = flight.getFlightClass();
		this.startPoint = flight.getStartPoint();
		this.endPoint = flight.getEndPoint();

		this.ticketNumber = passenger.getTicketNumber();
	}

	public ReportCompare(AirticketOrder order, Passenger passenger) {
		this.order = order;

		setPlatformCompare(this.order);

		this.ticketNumber = passenger.getTicketNumber();
	}

	public static boolean comparePlatformReport(ReportCompare compare,
			ReportCompare order) {
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;

		if (Constant.toUpperCase(compare.getSubPnr()).equals(
				Constant.toUpperCase(order.getSubPnr()))) {
			flag1 = true;
		}

		if (Constant.toUpperCase(compare.getAirOrderNo()).equals(
				Constant.toUpperCase(order.getAirOrderNo()))) {
			flag2 = true;
		}

		BigDecimal compareInAmount = Constant.toBigDecimal(compare
				.getInAmount());
		BigDecimal orderInAmount = Constant.toBigDecimal(order.getInAmount());
		int result = compareInAmount.compareTo(orderInAmount);
		if (result == 0) {
			flag3 = true;
		}

		boolean flag4_1 = Constant.toUpperCase(compare.getInAccountName())
				.equals(Constant.toUpperCase(order.getInAccountName()));
		boolean flag4_2 = Constant.toUpperCase(compare.getInAccountNo())
				.equals(Constant.toUpperCase(order.getInAccountNo()));

		if (flag4_1 || flag4_2) {
			flag4 = true;
		}

		if (Constant.toLong(compare.getPassengerCount()).compareTo(
				Constant.toLong(order.getPassengerCount())) == Long.valueOf(0)) {
			flag5 = true;
		}

		// if(Constant.toUpperCase(compare.getFlightCode()).equals(Constant.toUpperCase(order.getFlightCode()))){
		// flag5=true;
		// }
		//		
		// if(flag){
		// String
		// compareTicketNum=StringUtil.removeAppointStr(compare.getTicketNumber(),"-");
		// String
		// orderTicketNum=StringUtil.removeAppointStr(order.getTicketNumber(),"-");
		// if(Constant.toUpperCase(compareTicketNum,new
		// Long(13)).equals(Constant.toUpperCase(orderTicketNum,new Long(13)))){
		// flag6=true;
		// }
		// }
		if (flag1 && flag2 && flag3 && flag4 && flag5) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean compareBSPReport(ReportCompare compare,
			ReportCompare order) {
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;

		if (Constant.toUpperCase(compare.getSubPnr()).equals(
				Constant.toUpperCase(order.getSubPnr()))) {
			flag1 = true;
		}

		BigDecimal compareInAmount = Constant.toBigDecimal(compare
				.getInAmount());
		BigDecimal orderInAmount = Constant.toBigDecimal(order.getInAmount());
		int result = compareInAmount.compareTo(orderInAmount);
		if (result == 0) {
			flag2 = true;
		}

		String compareTicketNum = StringUtil.removeAppointStr(compare
				.getTicketNumber(), "-");
		String orderTicketNum = StringUtil.removeAppointStr(order
				.getTicketNumber(), "-");
		if (Constant.toUpperCase(compareTicketNum, new Long(13)).equals(
				Constant.toUpperCase(orderTicketNum, new Long(13)))) {
			flag3 = true;
		}
		if (flag1 && flag2 && flag3 && flag4 && flag5) {
			return true;
		} else {
			return false;
		}
	}

	public void setOrderAsStatement(String statementStr, long subType) {
		statementStr = Constant.toString(statementStr);
		String accountName = StringUtil.getBetweenString(statementStr,
				"<accountName>", "</accountName>");
		String accountNo = StringUtil.getBetweenString(statementStr,
				"<accountNo>", "</accountNo>");
		String totalAmount = StringUtil.getBetweenString(statementStr,
				"<totalAmount>", "</totalAmount>");
		String statementDate = StringUtil.getBetweenString(statementStr,
				"<statementDate>", "</statementDate>");
		Timestamp statementTime = DateUtil.getTimestamp(statementDate,
				"yyyy-MM-dd HH:mm:ss");
		String memo = StringUtil.getBetweenString(statementStr, "<memo>",
				"</memo>");

		if (subType == Statement.SUBTYPE_10) {
			this.order.setInAccountName(accountName);
			this.order.setInAccountNo(accountNo);
			this.order.setInAmount(Constant.toBigDecimal(totalAmount));
			this.order.setInTime(statementTime);
			this.order.setInMemo(memo);
		}
		if (subType == Statement.SUBTYPE_11) {
			this.order.setInRefundAccountName(accountName);
			this.order.setInRefundAccountNo(accountNo);
			this.order.setInRefundAmount(Constant.toBigDecimal(totalAmount));
			this.order.setInRefundTime(statementTime);
			this.order.setInRefundMemo(memo);
		}
		if (subType == Statement.SUBTYPE_20) {
			this.order.setOutAccountName(accountName);
			this.order.setOutAccountNo(accountNo);
			this.order.setOutAmount(Constant.toBigDecimal(totalAmount));
			this.order.setOutTime(statementTime);
			this.order.setOutMemo(memo);
		}
		if (subType == Statement.SUBTYPE_21) {
			this.order.setOutRefundAccountName(accountName);
			this.order.setOutRefundAccountNo(accountNo);
			this.order.setOutRefundAmount(Constant.toBigDecimal(totalAmount));
			this.order.setOutRefundTime(statementTime);
			this.order.setOutRefundMemo(memo);
		}
	}

	public String getPlatformName() {
		String platformName = "";
		if (platformId != null && platformId > 0) {
			Platform platform = PlatComAccountStore.getPlatformById(platformId);
			platformName = platform.getName();
		}
		return platformName;
	}

	public String getAccountName() {
		String accountName = "";
		if (accountId != null && accountId > 0) {
			Account account = PlatComAccountStore.getAccountById(accountId);
			accountName = account.getName();
		}
		return accountName;
	}

	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType() == TYPE_1) {
				return "销售";
			} else if (this.getType() == TYPE_2) {
				return "采购";
			} else if (this.getType() == TYPE_13) {
				return "供应退废";
			} else if (this.getType() == TYPE_14) {
				return "采购退废";
			} else if (this.getType() == TYPE_15) {
				return "供应退票";
			} else if (this.getType() == TYPE_16) {
				return "采购退票";
			} else if (this.getType() == TYPE_17) {
				return "供应废票";
			} else if (this.getType() == TYPE_18) {
				return "采购废票";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATES_1) {
				return "有效";
			} else if (this.getStatus().intValue() == STATES_0) {
				return "无效";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getListAttachName() {
		return listAttachName;
	}

	public void setListAttachName(String listAttachName) {
		this.listAttachName = listAttachName;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public AirticketOrder getOrder() {
		return order;
	}

	public void setOrder(AirticketOrder order) {
		this.order = order;
	}

	public Long getPlatformId() {
		return this.platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public Account getInAccount() {
		return inAccount;
	}

	public void setInAccount(Account inAccount) {
		this.inAccount = inAccount;
	}

	public Account getOutAccount() {
		return outAccount;
	}

	public void setOutAccount(Account outAccount) {
		this.outAccount = outAccount;
	}

	public Account getInRetireAccount() {
		return inRetireAccount;
	}

	public void setInRetireAccount(Account inRetireAccount) {
		this.inRetireAccount = inRetireAccount;
	}

	public Account getOutRetireAccount() {
		return outRetireAccount;
	}

	public void setOutRetireAccount(Account outRetireAccount) {
		this.outRetireAccount = outRetireAccount;
	}


	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

//	public BigDecimal getTotalAirportFuelPrice() {
////		this.totalAirportFuelPrice = Constant.toBigDecimal(this.airportPrice).add(
////				Constant.toBigDecimal(this.fuelPrice));
////		return totalAirportFuelPrice;
//	}


//	public BigDecimal getDrawProfits() {		
//		drawProfits=Constant.toBigDecimal(getPerOutAmount()).subtract(this.ticketPrice);		
//		drawProfits=drawProfits.subtract(getTotalAirportFuelPrice());
//		drawProfits=drawProfits.multiply(BigDecimal.valueOf(-1));
//		return drawProfits;
//	}

	public void setDrawProfits(BigDecimal drawProfits) {
		this.drawProfits = drawProfits;
	}
	
	public BigDecimal getPerOutAmount() {
		String passengerCountStr=this.passengerCount.toString();
		BigDecimal passengerCount=Constant.toBigDecimal(passengerCountStr);
		if(passengerCount.compareTo(BigDecimal.ZERO)>0){
			perOutAmount=Constant.toBigDecimal(this.outAmount).divide(passengerCount,BigDecimal.ROUND_HALF_UP);
		}
		
		return perOutAmount;
	}

	public void setPerOutAmount(BigDecimal perOutAmount) {
		this.perOutAmount = perOutAmount;
	}	
	
	public BigDecimal getTotalInAmount() {
		return totalInAmount;
	}

	public void setTotalInAmount(BigDecimal totalInAmount) {
		this.totalInAmount = totalInAmount;
	}

	public BigDecimal getTotalOutAmount() {
		return totalOutAmount;
	}

	public void setTotalOutAmount(BigDecimal totalOutAmount) {
		this.totalOutAmount = totalOutAmount;
	}

	public Account getTotalInRetireAccount() {
		return totalInRetireAccount;
	}

	public void setTotalInRetireAccount(Account totalInRetireAccount) {
		this.totalInRetireAccount = totalInRetireAccount;
	}

	public Account getTotalOutRetireAccount() {
		return totalOutRetireAccount;
	}

	public void setTotalOutRetireAccount(Account totalOutRetireAccount) {
		this.totalOutRetireAccount = totalOutRetireAccount;
	}

	public BigDecimal getTotalInRetireAmount() {
		return totalInRetireAmount;
	}

	public void setTotalInRetireAmount(BigDecimal totalInRetireAmount) {
		this.totalInRetireAmount = totalInRetireAmount;
	}

	public BigDecimal getTotalOutRetireAmount() {
		return totalOutRetireAmount;
	}

	public void setTotalOutRetireAmount(BigDecimal totalOutRetireAmount) {
		this.totalOutRetireAmount = totalOutRetireAmount;
	}

	public Long getTotalPassengerCount() {
		return totalPassengerCount;
	}

	public void setTotalPassengerCount(Long totalPassengerCount) {
		this.totalPassengerCount = totalPassengerCount;
	}

	public int getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

	public BigDecimal getAirportPrice() {
		return airportPrice;
	}

	public void setAirportPrice(BigDecimal airportPrice) {
		this.airportPrice = airportPrice;
	}

	public BigDecimal getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(BigDecimal fuelPrice) {
		this.fuelPrice = fuelPrice;
	}
	
	
}
