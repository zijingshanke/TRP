package com.fdays.tsms.transaction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class PlatformCompare extends org.apache.struts.action.ActionForm
		implements Cloneable {
	private static final long serialVersionUID = 1L;
	// 优先匹配 PNR、金额、银行账号、订单号、人数、航程
	protected Long platformId;
	protected String subPnr;
	public String airOrderNo;
	public String payOrderNo;
	public Account inAccount;
	public Account outAccount;
	public BigDecimal inAmount = BigDecimal.ZERO;
	public BigDecimal outAmount = BigDecimal.ZERO;
	public Account inRetireAccount;
	public Account outRetireAccount;
	public BigDecimal inRetireAmount=BigDecimal.ZERO;
	public BigDecimal outRetireAmount=BigDecimal.ZERO;
	
	public Long passengerCount;
	
	public String flightCode;
	public String flightClass;
	public String ticketNumber;
	public String startPoint;
	public String endPoint;

	public String discount;
	public java.sql.Timestamp beginDate;
	public java.sql.Timestamp endDate;
	public String userNo;
	public String sessionId;
	public String memo;
	public Long type;
	public Long status;

	public int rowNum = -1;
	public String filePath = "";
	public String fileName = "";
	public String listAttachName = "";
	public String beginDateStr = "";
	public String endDateStr = "";
	
	public AirticketOrder order;
	
	public String inAccountName;
	public String outAccountName;
	public String inRetireAccountName;
	public String ouRetiretAccountName;
	public String inAccountNo;
	public String outAccountNo;
	public String inRetireAccountNo;
	public String ouRetiretAccountNo;
	

	public static final long TYPE_1 = 1;// 销售
	public static final long TYPE_2 = 2;// 采购
	public static final long TYPE_13 = 13;// 供应退废
	public static final long TYPE_14 = 14;// 采购退废
	public static final long TYPE_15 = 15;// 供应退票
	public static final long TYPE_16 = 16;// 采购退票
	public static final long TYPE_17 = 17;// 供应废票
	public static final long TYPE_18 = 18;// 采购废票

	public static final long STATES_1 = 1;// 有效
	public static final long STATES_0 = 0;// 无效

	public PlatformCompare() {
	}

	public PlatformCompare(AirticketOrder order, Flight flight) {
		this.platformId = order.getPlatform().getId();
		this.order = order;
		
		setPlatformCompare(this.order);		
		
		this.flightCode = flight.getFlightCode();
		this.flightClass = flight.getFlightClass();
		this.startPoint = flight.getStartPoint();
		this.endPoint = flight.getEndPoint();
		this.discount = flight.getDiscount();
	}
	
	public void setPlatformCompare(AirticketOrder order){
		if(order!=null){
			this.platformId = order.getPlatform().getId();
			this.subPnr = order.getSubPnr();	
			this.airOrderNo=order.getAirOrderNo();		
			this.payOrderNo="";
			
			if(order.getInAccount()!=null){
				this.inAccountName=this.order.getInAccount().getName();
				this.inAccountNo=this.order.getInAccount().getAccountNo();
			}
			if(order.getOutAccount()!=null){
				this.outAccountName=this.order.getOutAccount().getName();
				this.outAccountNo=this.order.getOutAccount().getAccountNo();
			}
			
			this.inAmount=order.getInAmount();
			this.outAmount=order.getOutAmount();				
		}
	}

	public PlatformCompare(AirticketOrder order, Flight flight,
			Passenger passenger) {
		this.order = order;
		setPlatformCompare(this.order);
		

		this.flightCode = flight.getFlightCode();
		this.flightClass = flight.getFlightClass();
		this.startPoint = flight.getStartPoint();
		this.endPoint = flight.getEndPoint();
		this.discount = flight.getDiscount();

		this.ticketNumber = passenger.getTicketNumber();
	}

	public PlatformCompare(AirticketOrder order, Account inAccount,
			Account outAccount, BigDecimal inAmount, BigDecimal outAmount) {
		this.order = order;
		this.order.setInAccount(inAccount);
		this.order.setOutAccount(outAccount);
		this.order.setInAmount(Constant.toBigDecimal(inAmount));
		this.order.setOutAmount(Constant.toBigDecimal(outAmount));		
	}

	
	public static boolean compare(PlatformCompare compare, PlatformCompare order) {
		boolean flag = false;
		if (Constant.toUpperCase(compare.getSubPnr()).equals(
				Constant.toUpperCase(order.getSubPnr()))) {
			flag = true;
		}		
		if (Constant.toUpperCase(compare.getAirOrderNo()).equals(
				Constant.toUpperCase(order.getAirOrderNo()))) {
			flag = true;
		}		
		if (Constant.toBigDecimal(compare.getInAmount())==Constant.toBigDecimal(order.getInAmount())) {
			flag = true;
		}
		
		
		
		// if(Constant.toUpperCase(compare.getFlightCode()).equals(Constant.toUpperCase(order.getFlightCode()))){
		// flag=true;
		// }
		//		
		// if(flag){
		// String
		// compareTicketNum=StringUtil.removeAppointStr(compare.getTicketNumber(),"-");
		// String
		// orderTicketNum=StringUtil.removeAppointStr(order.getTicketNumber(),"-");
		// if(Constant.toUpperCase(compareTicketNum,new
		// Long(13)).equals(Constant.toUpperCase(orderTicketNum,new Long(13)))){
		// flag=true;
		// }
		// }

		return flag;
	}


	public String getPlatformName() {
		String platformName = "";
		if (platformId != null && platformId > 0) {
			Platform platform = PlatComAccountStore.getPlatformById(platformId);
			platformName = platform.getName();
		}
		return platformName;
	}

	public String getUserName() {
		String userName = "";
		if (userNo != null && "".equals(userNo) == false) {
			userName = UserStore.getUserNameByNo(userNo);
		}
		return userName;
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

	public String getFormatBeginDate() {
		String mydate = "";
		if (this.beginDate != null && "".equals(beginDate) == false) {
			Date tempDate = new Date(beginDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatEndDate() {
		String mydate = "";
		if (this.endDate != null && "".equals(endDate) == false) {
			Date tempDate = new Date(endDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
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

	public String getFlightCode() {
		return this.flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getFlightClass() {
		return this.flightClass;
	}

	public void setFlightClass(String flightClass) {
		this.flightClass = flightClass;
	}

	public String getTicketNumber() {
		return this.ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getStartPoint() {
		return this.startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return this.endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getSubPnr() {
		return this.subPnr;
	}

	public void setSubPnr(String subPnr) {
		this.subPnr = subPnr;
	}

	public String getDiscount() {
		return this.discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public java.sql.Timestamp getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(java.sql.Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public java.sql.Timestamp getEndDate() {
		return this.endDate;
	}

	public void setEndDate(java.sql.Timestamp endDate) {
		this.endDate = endDate;
	}

	public String getUserNo() {
		return this.userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getType() {
		return this.type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getStatus() {
		return this.status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}


	

	public String getAirOrderNo() {
		return airOrderNo;
	}

	public void setAirOrderNo(String airOrderNo) {
		this.airOrderNo = airOrderNo;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
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

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
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

	public BigDecimal getInRetireAmount() {
		return inRetireAmount;
	}

	public void setInRetireAmount(BigDecimal inRetireAmount) {
		this.inRetireAmount = inRetireAmount;
	}

	public BigDecimal getOutRetireAmount() {
		return outRetireAmount;
	}

	public void setOutRetireAmount(BigDecimal outRetireAmount) {
		this.outRetireAmount = outRetireAmount;
	}

	public Long getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(Long passengerCount) {
		this.passengerCount = passengerCount;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public String getInAccountNo() {
		return inAccountNo;
	}

	public void setInAccountNo(String inAccountNo) {
		this.inAccountNo = inAccountNo;
	}

	public String getOutAccountNo() {
		return outAccountNo;
	}

	public void setOutAccountNo(String outAccountNo) {
		this.outAccountNo = outAccountNo;
	}

	public String getInRetireAccountNo() {
		return inRetireAccountNo;
	}

	public void setInRetireAccountNo(String inRetireAccountNo) {
		this.inRetireAccountNo = inRetireAccountNo;
	}

	public String getOuRetiretAccountNo() {
		return ouRetiretAccountNo;
	}

	public void setOuRetiretAccountNo(String ouRetiretAccountNo) {
		this.ouRetiretAccountNo = ouRetiretAccountNo;
	}
	
	

	public String getInAccountName() {
		return inAccountName;
	}

	public void setInAccountName(String inAccountName) {
		this.inAccountName = inAccountName;
	}

	public String getOutAccountName() {
		return outAccountName;
	}

	public void setOutAccountName(String outAccountName) {
		this.outAccountName = outAccountName;
	}

	public String getInRetireAccountName() {
		return inRetireAccountName;
	}

	public void setInRetireAccountName(String inRetireAccountName) {
		this.inRetireAccountName = inRetireAccountName;
	}

	public String getOuRetiretAccountName() {
		return ouRetiretAccountName;
	}

	public void setOuRetiretAccountName(String ouRetiretAccountName) {
		this.ouRetiretAccountName = ouRetiretAccountName;
	}

	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	private String thisAction = "";

	public String getThisAction() {
		return thisAction;
	}

	public void setThisAction(String thisAction) {
		this.thisAction = thisAction;
	}

	private int index = 0;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
