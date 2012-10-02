package com.fdays.tsms.airticket;


import java.math.BigDecimal;

import com.fdays.tsms.airticket._entity._AirticketOrder;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.transaction.Statement;

public class AirticketOrder extends _AirticketOrder{
  	private static final long serialVersionUID = 1L;
  	private String pnr; //预订记录编码
  	private String forwardPage; 
  	private java.math.BigDecimal ticketPrice=new BigDecimal(0);//票面价
  	private java.math.BigDecimal documentPrice=new BigDecimal(0);
  	private java.math.BigDecimal insurancePrice=new BigDecimal(0);
  	private java.math.BigDecimal rebate=new BigDecimal(0);
  	private long statement_type;
  	private java.math.BigDecimal totalAmount=new BigDecimal(0);//订单金额
  	private Long platformId=Long.valueOf(0);
  	private Long companyId=Long.valueOf(0);
  	private Long accountId=Long.valueOf(0);
	private Long agentId=Long.valueOf(0);
	private TicketLog ticketLog=new TicketLog();  //操作日志
	private Statement statement=new Statement();
	
	 private long agentNo;//客户ID	
	 private long flightId;//航班Id
	 private String drawer;//出票人
	 private String flightCode;//航班号
	 private String startPoint;//出发地
	 private String endPoint;//目的地
	 private String flightClass;//舱位
	 private String discount;//折扣
	 private long totlePernson;//团队总人数
	 private long allPeople=0;//总人数
	 private java.math.BigDecimal totlePrice=new BigDecimal(0);//票面价
	 private java.math.BigDecimal airportPrice=new BigDecimal(0);//机建税
	 private java.math.BigDecimal fuelPrice=new BigDecimal(0);//燃油税
	 
	 private java.math.BigDecimal allTotlePrice=new BigDecimal(0);//总票面价
	 private java.math.BigDecimal allAirportPrice=new BigDecimal(0);//总机建税
	 private java.math.BigDecimal allFuelPrice=new BigDecimal(0);//总燃油税
	 
	 private java.math.BigDecimal adultAirportPrice=new BigDecimal(0);//机建税(成人)
	 private java.math.BigDecimal adultFuelPrice=new BigDecimal(0);//燃油税(成人)
	 private java.math.BigDecimal childAirportPrice=new BigDecimal(0);//机建税(儿童)
	 private java.math.BigDecimal childfuelPrice=new BigDecimal(0);//燃油税(儿童)
	 private java.math.BigDecimal babyAirportPrice=new BigDecimal(0);//机建税(婴儿)
	 private java.math.BigDecimal babyfuelPrice=new BigDecimal(0);//燃油税(婴儿)
	 private java.math.BigDecimal handlingCharge=new BigDecimal(0);//手续费
	 
	 //修改图队利润
	 private long statementId;//结算ID
	 private java.math.BigDecimal txt_ActualAmount=new BigDecimal(0);//实收款
	 private java.math.BigDecimal txt_UnsettledAccount=new BigDecimal(0);//未结款
	 private java.math.BigDecimal txt_Commission=new BigDecimal(0);//现返佣金
	 private java.math.BigDecimal txt_RakeOff=new BigDecimal(0);//后返佣金
	 private java.math.BigDecimal txt_TotalAmount=new BigDecimal(0);//总金额
	 
	 //团队利润(收入)
	 private long airticketOrderId;//机票订单ID
	 private java.math.BigDecimal txtAgentFeeTeams = new BigDecimal(0);//应付出团代理费（现返）
	 private long txtUnAgentFeeTeams;//应付出团代理费（未返）
	 private java.math.BigDecimal txtSAmount = new BigDecimal(0);//应收票款
	 private java.math.BigDecimal txtTotalAmount = new BigDecimal(0);//实收票款
	 
	//团队利润(支入)
	 private java.math.BigDecimal txtProfits = new BigDecimal(0);//团毛利润
	 private java.math.BigDecimal txtAgentFeeCarrier = new BigDecimal(0);//月底返代理费
	 private java.math.BigDecimal txtTUnAmount = new BigDecimal(0);//应付票款
	 private java.math.BigDecimal txtTAmount = new BigDecimal(0);//实付票款
	 
	 
	 private String[] flightIds;
	 private String[] flightCodes;
	 private String[] boardingTimes;
	 private String[] flightClasss;
	 private String[] discounts;
	 private String[] startPoints;
	 private String[] endPoints;
	 private String[] passengerIds;
	 private String[] passengerNames;
	 private String[] passengerCardno;
	 private String[] passengerTicketNumber;
	 
	 private String boardingTime;//起飞时间
	//订单状态
	public static final long STATUS_1=1;//1：新订单
	public static final long STATUS_2=2;//2：申请成功，等待支付
	public static final long STATUS_3=3;//3：支付成功，等待出票
	public static final long STATUS_4=4;//4：取消出票，等待退款
	public static final long STATUS_5=5;//5：出票成功，交易结束
	public static final long STATUS_6=6;//5：已退款，交易结束
	public static final long STATUS_7=7;//get lock 锁定
	public static final long STATUS_8=8;//release lock 解锁 
	
	//public static final long STATUS_10=10;//B2C订单，等待收款
	public static final long STATUS_19=19;//退票订单，等待审核
	public static final long STATUS_20=20;//退票订单，等待审核
	public static final long STATUS_21=21;//退票审核通过，等待退款
	public static final long STATUS_22=22;//已经退款，交易结束
	public static final long STATUS_23=23;//退票未通过，交易结束
	
	public static final long STATUS_29=29;//废票订单，等待审核
	public static final long STATUS_30=30;//废票订单，等待审核
	public static final long STATUS_31=31;//废票审核通过，等待退款
	public static final long STATUS_32=32;//废票已经退款，交易结束
	public static final long STATUS_33=33;//废票未通过，交易结束
	
	public static final long STATUS_39=39;//改签订单，等待审核
	public static final long STATUS_40=40;//改签订单，等待审核
	public static final long STATUS_41=41;//改签审核通过，等待支付
	public static final long STATUS_42=42;//改签审核通过，等待支付
	public static final long STATUS_43=43;//改签已支付，等待确认
	public static final long STATUS_44=44;//改签未通过，交易结束
	public static final long STATUS_45=45;//改签完成，交易结束
	public static final long STATUS_80=80;//交易结束
	public static final long STATUS_88=88;//已废弃
	
	
	private String statusText;
	//机票类型
	public static final long TICKETTYPE_1=1;//1：普通
	public static final long TICKETTYPE_2=2;//2：团队
	public static final long TICKETTYPE_3=3;//3：b2c
	private String ticketTypeText;
	
	//交易类型
	public static final long TRANTYPE_1=1;//1：买入
	public static final long TRANTYPE_2=2;//2：卖出
	public static final long TRANTYPE_3=3;//3：退票
	public static final long TRANTYPE_4=4;//4：废票
	public static final long TRANTYPE_5=5;//5：改签

	private String tranTypeText;
	
	
	
	public String getTranTypeText() {
		if(this.getTranType()!=null){
			if(this.getTranType()==TRANTYPE_1){
				tranTypeText="买入";
			}else if(this.getTranType()==TRANTYPE_2){
				tranTypeText="卖出";
			}else if(this.getTranType()==TRANTYPE_3){
				tranTypeText="退票";
			}else if(this.getTranType()==TRANTYPE_4){
			    tranTypeText="废票";	
			}else if(this.getTranType()==TRANTYPE_5){
			    tranTypeText="改签";
			}else{
			    tranTypeText="";
			}
		}else{
			tranTypeText="";
		}
		return tranTypeText;
	}

	public String getTicketTypeText (){
		if(this.getTicketType()!=null){
			if(this.getTicketType()==TICKETTYPE_1){
				ticketTypeText="普通"; 
			}else if(this.getTicketType()==TICKETTYPE_2){
				ticketTypeText="团队"; 
			}else if(this.getTicketType()==TICKETTYPE_3){
				ticketTypeText="B2C"; 
			}else{
				ticketTypeText=""; 
			}
			
		}else{
			ticketTypeText=""; 
		}
		return ticketTypeText;
	}
   
    public String getStatusText() {
    	
    	if(this.getStatus()!=null){
    		if(this.getStatus()==STATUS_1){
    			statusText="新订单";
    		}else if(this.getStatus()==STATUS_2){
    			statusText="申请成功，等待支付";
    		}else if(this.getStatus()==STATUS_3){
    			statusText="支付成功，等待出票";
    		}else if(this.getStatus()==STATUS_4){
    			statusText="取消出票，等待退款";
    		}else if(this.getStatus()==STATUS_5){
    			statusText="出票成功，交易结束";
    		}else if(this.getStatus()==STATUS_6){
    			statusText="已退款，交易结束";
    		}else if(this.getStatus()==STATUS_7){
    			statusText="申请成功，等待支付 已锁定";
    		}else if(this.getStatus()==STATUS_8){
    			statusText="申请成功，等待支付 已解锁";
    		}else if(this.getStatus()==STATUS_19){
    			statusText="退票订单，等待审核";
    		}else if(this.getStatus()==STATUS_20){
    			statusText="退票订单，等待审核";
    		}else if(this.getStatus()==STATUS_21){
    			statusText="退票审核通过，等待退款";
    		}else if(this.getStatus()==STATUS_22){
    			statusText="已经退款，交易结束";
    		}else if(this.getStatus()==STATUS_23){
    			statusText="退票未通过，交易结束";
    		}else if(this.getStatus()==STATUS_29){
    			statusText="废票订单，等待审核";
    		}else if(this.getStatus()==STATUS_30){
    			statusText="废票订单，等待审核";
    		}else if(this.getStatus()==STATUS_31){
    			statusText="废票审核通过，等待退款";
    		}else if(this.getStatus()==STATUS_32){
    			statusText="废票已经退款，交易结束";
    		}else if(this.getStatus()==STATUS_33){
    			statusText="废票未通过，交易结束";
    		}else if(this.getStatus()==STATUS_39){
    			statusText="改签订单，等待审核";
    		}else if(this.getStatus()==STATUS_40){
    			statusText="改签订单，等待审核";
    		}else if(this.getStatus()==STATUS_41){
    			statusText="改签审核通过，等待支付";
    		}else if(this.getStatus()==STATUS_42){
    			statusText="改签审核通过，等待支付";
    		}else if(this.getStatus()==STATUS_43){
    			statusText="改签已支付，等待确认";
    		}else if(this.getStatus()==STATUS_44){
    			statusText="改签未通过，交易结束";
    		}else if(this.getStatus()==STATUS_45){
    			statusText="改签完成，交易结束";
    		}else if(this.getStatus()==STATUS_80){
    			statusText="交易结束";
    		}else if(this.getStatus()==STATUS_88){
    			statusText="已废弃";
    		}else{
    			statusText="";
    		}
    		
    	}else{
    		statusText="";
    	}
		return statusText;
	}
   
	public Long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public String getPnr() {
		return pnr;
	}
	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
	public java.math.BigDecimal getRebate() {
		return rebate;
	}
	public void setRebate(java.math.BigDecimal rebate) {
		this.rebate = rebate;
	}
	public java.math.BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(java.math.BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public long getStatement_type() {
		return statement_type;
	}
	public void setStatement_type(long statement_type) {
		this.statement_type = statement_type;
	}
	public java.math.BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(java.math.BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public java.math.BigDecimal getDocumentPrice() {
		return documentPrice;
	}
	public void setDocumentPrice(java.math.BigDecimal documentPrice) {
		this.documentPrice = documentPrice;
	}
	public java.math.BigDecimal getInsurancePrice() {
		return insurancePrice;
	}
	public void setInsurancePrice(java.math.BigDecimal insurancePrice) {
		this.insurancePrice = insurancePrice;
	}
	public Long getAgentId() {
		return agentId;
	}
	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}
	public String getForwardPage() {
		return forwardPage;
	}
	public void setForwardPage(String forwardPage) {
		this.forwardPage = forwardPage;
	}

	public TicketLog getTicketLog() {
		return ticketLog;
	}

	public void setTicketLog(TicketLog ticketLog) {
		this.ticketLog = ticketLog;
	}

	public Statement getStatement() {
		return statement;
	}

	public void setStatement(Statement statement) {
		this.statement = statement;
	}


	public long getAgentNo() {
		return agentNo;
	}

	public void setAgentNo(long agentNo) {
		this.agentNo = agentNo;
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

	public java.math.BigDecimal getAdultAirportPrice() {
		return adultAirportPrice;
	}

	public void setAdultAirportPrice(java.math.BigDecimal adultAirportPrice) {
		this.adultAirportPrice = adultAirportPrice;
	}

	public java.math.BigDecimal getAdultFuelPrice() {
		return adultFuelPrice;
	}

	public void setAdultFuelPrice(java.math.BigDecimal adultFuelPrice) {
		this.adultFuelPrice = adultFuelPrice;
	}

	public java.math.BigDecimal getChildAirportPrice() {
		return childAirportPrice;
	}

	public void setChildAirportPrice(java.math.BigDecimal childAirportPrice) {
		this.childAirportPrice = childAirportPrice;
	}

	public java.math.BigDecimal getChildfuelPrice() {
		return childfuelPrice;
	}

	public void setChildfuelPrice(java.math.BigDecimal childfuelPrice) {
		this.childfuelPrice = childfuelPrice;
	}

	public java.math.BigDecimal getBabyAirportPrice() {
		return babyAirportPrice;
	}

	public void setBabyAirportPrice(java.math.BigDecimal babyAirportPrice) {
		this.babyAirportPrice = babyAirportPrice;
	}

	public java.math.BigDecimal getBabyfuelPrice() {
		return babyfuelPrice;
	}

	public void setBabyfuelPrice(java.math.BigDecimal babyfuelPrice) {
		this.babyfuelPrice = babyfuelPrice;
	}

	public String getBoardingTime() {
		return boardingTime;
	}

	public void setBoardingTime(String boardingTime) {
		this.boardingTime = boardingTime;
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

	public long getTotlePernson() {
		return totlePernson;
	}

	public void setTotlePernson(long totlePernson) {
		this.totlePernson = totlePernson;
	}

	public java.math.BigDecimal getTotlePrice() {
		return totlePrice;
	}

	public void setTotlePrice(java.math.BigDecimal totlePrice) {
		this.totlePrice = totlePrice;
	}

	public java.math.BigDecimal getAirportPrice() {
		return airportPrice;
	}

	public void setAirportPrice(java.math.BigDecimal airportPrice) {
		this.airportPrice = airportPrice;
	}

	public java.math.BigDecimal getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(java.math.BigDecimal fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public String getDrawer() {
		return drawer;
	}

	public void setDrawer(String drawer) {
		this.drawer = drawer;
	}

	public long getFlightId() {
		return flightId;
	}

	public void setFlightId(long flightId) {
		this.flightId = flightId;
	}

	public String[] getFlightIds() {
		return flightIds;
	}

	public void setFlightIds(String[] flightIds) {
		this.flightIds = flightIds;
	}

	public String[] getFlightCodes() {
		return flightCodes;
	}

	public void setFlightCodes(String[] flightCodes) {
		this.flightCodes = flightCodes;
	}

	public String[] getBoardingTimes() {
		return boardingTimes;
	}

	public void setBoardingTimes(String[] boardingTimes) {
		this.boardingTimes = boardingTimes;
	}

	public String[] getFlightClasss() {
		return flightClasss;
	}

	public void setFlightClasss(String[] flightClasss) {
		this.flightClasss = flightClasss;
	}

	public String[] getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String[] discounts) {
		this.discounts = discounts;
	}

	public String[] getEndPoints() {
		return endPoints;
	}

	public void setEndPoints(String[] endPoints) {
		this.endPoints = endPoints;
	}

	public String[] getStartPoints() {
		return startPoints;
	}

	public void setStartPoints(String[] startPoints) {
		this.startPoints = startPoints;
	}


	public java.math.BigDecimal getHandlingCharge() {
		return handlingCharge;
	}

	public void setHandlingCharge(java.math.BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}


	public java.math.BigDecimal getTxtAgentFeeTeams() {
		return txtAgentFeeTeams;
	}

	public void setTxtAgentFeeTeams(java.math.BigDecimal txtAgentFeeTeams) {
		this.txtAgentFeeTeams = txtAgentFeeTeams;
	}


	public java.math.BigDecimal getTxtSAmount() {
		return txtSAmount;
	}

	public void setTxtSAmount(java.math.BigDecimal txtSAmount) {
		this.txtSAmount = txtSAmount;
	}

	public java.math.BigDecimal getTxtTotalAmount() {
		return txtTotalAmount;
	}

	public void setTxtTotalAmount(java.math.BigDecimal txtTotalAmount) {
		this.txtTotalAmount = txtTotalAmount;
	}

	public java.math.BigDecimal getTxtProfits() {
		return txtProfits;
	}

	public void setTxtProfits(java.math.BigDecimal txtProfits) {
		this.txtProfits = txtProfits;
	}

	public java.math.BigDecimal getTxtAgentFeeCarrier() {
		return txtAgentFeeCarrier;
	}

	public void setTxtAgentFeeCarrier(java.math.BigDecimal txtAgentFeeCarrier) {
		this.txtAgentFeeCarrier = txtAgentFeeCarrier;
	}

	public java.math.BigDecimal getTxtTUnAmount() {
		return txtTUnAmount;
	}

	public void setTxtTUnAmount(java.math.BigDecimal txtTUnAmount) {
		this.txtTUnAmount = txtTUnAmount;
	}

	public java.math.BigDecimal getTxtTAmount() {
		return txtTAmount;
	}

	public void setTxtTAmount(java.math.BigDecimal txtTAmount) {
		this.txtTAmount = txtTAmount;
	}

	public long getTxtUnAgentFeeTeams() {
		return txtUnAgentFeeTeams;
	}

	public void setTxtUnAgentFeeTeams(long txtUnAgentFeeTeams) {
		this.txtUnAgentFeeTeams = txtUnAgentFeeTeams;
	}

	public long getAirticketOrderId() {
		return airticketOrderId;
	}

	public void setAirticketOrderId(long airticketOrderId) {
		this.airticketOrderId = airticketOrderId;
	}


	public String[] getPassengerIds() {
		return passengerIds;
	}

	public void setPassengerIds(String[] passengerIds) {
		this.passengerIds = passengerIds;
	}

	public String[] getPassengerNames() {
		return passengerNames;
	}

	public void setPassengerNames(String[] passengerNames) {
		this.passengerNames = passengerNames;
	}

	public String[] getPassengerCardno() {
		return passengerCardno;
	}

	public void setPassengerCardno(String[] passengerCardno) {
		this.passengerCardno = passengerCardno;
	}

	public String[] getPassengerTicketNumber() {
		return passengerTicketNumber;
	}

	public void setPassengerTicketNumber(String[] passengerTicketNumber) {
		this.passengerTicketNumber = passengerTicketNumber;
	}


	public long getStatementId() {
		return statementId;
	}

	public void setStatementId(long statementId) {
		this.statementId = statementId;
	}

	public java.math.BigDecimal getTxt_ActualAmount() {
		return txt_ActualAmount;
	}

	public void setTxt_ActualAmount(java.math.BigDecimal txt_ActualAmount) {
		this.txt_ActualAmount = txt_ActualAmount;
	}

	public java.math.BigDecimal getTxt_UnsettledAccount() {
		return txt_UnsettledAccount;
	}

	public void setTxt_UnsettledAccount(java.math.BigDecimal txt_UnsettledAccount) {
		this.txt_UnsettledAccount = txt_UnsettledAccount;
	}

	public java.math.BigDecimal getTxt_Commission() {
		return txt_Commission;
	}

	public void setTxt_Commission(java.math.BigDecimal txt_Commission) {
		this.txt_Commission = txt_Commission;
	}

	public java.math.BigDecimal getTxt_RakeOff() {
		return txt_RakeOff;
	}

	public void setTxt_RakeOff(java.math.BigDecimal txt_RakeOff) {
		this.txt_RakeOff = txt_RakeOff;
	}

	public java.math.BigDecimal getTxt_TotalAmount() {
		return txt_TotalAmount;
	}

	public void setTxt_TotalAmount(java.math.BigDecimal txt_TotalAmount) {
		this.txt_TotalAmount = txt_TotalAmount;
	}

	public java.math.BigDecimal getAllTotlePrice() {
		if(this.totlePrice!=null&&this.getAllPeople()>0){
			allTotlePrice=this.ticketPrice.multiply(new java.math.BigDecimal(this.allPeople));
		}
		return allTotlePrice;
	}

	public void setAllTotlePrice(java.math.BigDecimal allTotlePrice) {
		this.allTotlePrice = allTotlePrice;
	}

	public java.math.BigDecimal getAllAirportPrice() {
		
		if(this.allAirportPrice!=null&&this.getAllPeople()>0){
			allAirportPrice=this.airportPrice.multiply(new java.math.BigDecimal(this.allPeople));
		}
		return allAirportPrice;
		
	}

	public void setAllAirportPrice(java.math.BigDecimal allAirportPrice) {
		this.allAirportPrice = allAirportPrice;
	}

	public java.math.BigDecimal getAllFuelPrice() {
		
		if(this.allFuelPrice!=null&&this.getAllPeople()>0){
			allFuelPrice=this.fuelPrice.multiply(new java.math.BigDecimal(this.getAllPeople()));
		}
		return allFuelPrice;
	}

	public void setAllFuelPrice(java.math.BigDecimal allFuelPrice) {
		this.allFuelPrice = allFuelPrice;
	}

	public long getAllPeople() {
		allPeople=0;
		if(this.getAdultCount()!=null){
			allPeople+=this.getAdultCount();
		}
		if(this.getChildCount()!=null){
			allPeople+=this.getChildCount();
		}
		if(this.getBabyCount()!=null){
			allPeople+=this.getBabyCount();
		}
		return allPeople;
	}

	public void setAllPeople(long allPeople) {
		this.allPeople = allPeople;
	}


  	
}
