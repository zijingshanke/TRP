package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fdays.tsms.airticket._entity._AirticketOrder;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.MyLabel;
import com.fdays.tsms.base.Operate;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.user.UserStore;
import com.fdays.tsms.right.UserRightInfo;
import com.neza.tool.DateUtil;

public class AirticketOrder extends _AirticketOrder
{
	private static final long serialVersionUID = 1L;
	private String pnr; // 预订记录编码
	private String forwardPage;
	private long groupId;
	
	private Long platComAccountId = Long.valueOf(0);
	private Long platformId = Long.valueOf(0);
	private Long companyId = Long.valueOf(0);
	private Long accountId = Long.valueOf(0);
	private Long agentId = Long.valueOf(0);
	private String addType;// 添加类型(外部 or 内部 pnr)
	private String importType;
	private String quitTicketType = "";
	private String quitTicketReason = "";

	private String[] airOrderNos = new String[0];
	private String[] flightIds;
	private String[] flightCodes;
	private String[] boardingTimes;
	private String[] flightClasss;
	private String[] discounts;
	private java.math.BigDecimal[] ticketPrices;
	private String[] startPoints;
	private String[] endPoints;
	private String[] passengerIds;
	private String[] passengerNames;
	private String[] passengerCardnos;
	private String[] passengerTicketNumbers;
	private java.math.BigDecimal[] transRules;

	private String boardingTime;// 起飞时间
	private Long originalPassCount = new Long(1);

	private Operate operate = new Operate();
	private Operate commonOperate = new Operate();
	private Operate teamCommonOperate = new Operate();

	private UserRightInfo uri = new UserRightInfo();
	private TicketLog ticketLog = new TicketLog(); // 操作日志
	private String path;

	private java.math.BigDecimal[] adultAirportPrices = { new BigDecimal(0) };// 机建税(成人)
	private java.math.BigDecimal[] adultFuelPrices = new BigDecimal[0];// 燃油税(成人)
	private java.math.BigDecimal[] childAirportPrices = new BigDecimal[0];// 机建税(儿童)
	private java.math.BigDecimal[] childFuelPrices = new BigDecimal[0];// 燃油税(儿童)
	private java.math.BigDecimal[] babyAirportPrices = new BigDecimal[0];// 机建税(婴儿)
	private java.math.BigDecimal[] babyFuelPrices = new BigDecimal[0];// 燃油税(婴儿)

	// -------团队利润统计-----
	private java.math.BigDecimal saleOverTicketPrice = BigDecimal.ZERO;
	private java.math.BigDecimal saleOverAirportfulePrice = BigDecimal.ZERO;
	private java.math.BigDecimal saleCommissonCount = BigDecimal.ZERO;
	private java.math.BigDecimal saleRakeOff = BigDecimal.ZERO;
	private java.math.BigDecimal saleIncomeretreatCharge = BigDecimal.ZERO;
	private java.math.BigDecimal saleTotalAmount = BigDecimal.ZERO;

	private String saleMemo = "";
	private java.math.BigDecimal buyHandlingCharge = BigDecimal.ZERO;
	private java.math.BigDecimal buyCommissonCount = BigDecimal.ZERO;
	private java.math.BigDecimal buyRakeoffCount = BigDecimal.ZERO;
	private java.math.BigDecimal buyIncomeretreatCharge = BigDecimal.ZERO;
	private java.math.BigDecimal buyTotalAmount = BigDecimal.ZERO;

	// 订单组
	public static final String GROUP_1 = "1,2,3,4,5,6,7,8,9,10,13,14,15,16";// 正常(状态组)
	public static final String GROUP_2 = "19,20,21,22,23,24,25,29,30,31,32,33,34,35";// 退废
	public static final String GROUP_3 = "39,40,41,42,43,44,45,46,47";// 改签
	public static final String GROUP_5 = "4,13,6,14,15,9,10,6,16";// 取消出票（退废报表使用）
	public static final String GROUP_FILTERSUCCESS = "1,2,3,5,7,8";// 需过滤的正常订单（退废报表使用）

	public static final String GROUP_7 = "101,102,103,105";// 团队正常票
	public static final String GROUP_8 = "107,116,117,108,109,110";// 团队退票
	public static final String GROUP_9 = "102,103,105,116,117,108,109,110";// 团队销售/未返报表(已统计利润)

	// 操作员统计
	public static final String GROUP_11 = "5";// 正常有效：出票完成
	public static final String GROUP_12 = "45";// 改签订单：改签完成
	public static final String GROUP_13 = "22";// 退票订单：退票完成
	public static final String GROUP_14 = "32";// 废票订单：废票完成
	public static final String GROUP_15 = "4,6,9,10,13,14,15,16";// 取消订单
	public static final String GROUP_21 = "105";// 团队正常订单
	public static final String GROUP_22 = "109,110";// 团队退票订单

	// 机票类型
	public static final long TICKETTYPE_1 = 1;// 1：散票
	public static final long TICKETTYPE_2 = 2;// 2：团队
	public static final long TICKETTYPE_3 = 3;// 3：b2c

	// 业务类型
	public static final long BUSINESSTYPE__2 = 2;// 买入
	public static final long BUSINESSTYPE__1 = 1;// 卖出

	// 交易类型
	public static final long TRANTYPE__1 = 1;// 卖出(销售)
	public static final long TRANTYPE__2 = 2;// 买入(采购)
	public static final long TRANTYPE_3 = 3;// 3：退票
	public static final long TRANTYPE_4 = 4;// 4：废票
	public static final long TRANTYPE_5 = 5;// 5：改签

	// 订单分组（权限）
	public static final long ORDER_GROUP_TYPE1 = 91;// 散票买卖
	public static final long ORDER_GROUP_TYPE2 = 92;// 散票改签
	public static final long ORDER_GROUP_TYPE3 = 93;// 散票退废
	public static final long ORDER_GROUP_TYPE7 = 97;// 团队买卖
	public static final long ORDER_GROUP_TYPE8 = 98;// 团队退废

	public static final long LOCK0 = 0;// 正常
	public static final long LOCK1 = 1;// 已锁定

	// 订单状态
	public static final long STATUS_1 = 1;// 新订单
	public static final long STATUS_2 = 2;// 申请成功，等待支付
	public static final long STATUS_3 = 3;// 支付成功，等待出票
	public static final long STATUS_5 = 5;// 出票成功，交易结束

	public static final long STATUS_4 = 4;// 已经取消出票（买入未支付)(未终止)
	public static final long STATUS_13 = 13;//等待取消出票（卖出已经取消出票，强制已经支付的买入只能进行取消出票操作）

	public static final long STATUS_10 = 10;//取消出票,等待退款(卖出订单之前已收款,等待退款)
	public static final long STATUS_9 = 9;// 取消出票,等待退款(买入订单已支付)

	public static final long STATUS_6 = 6;// 已经退款，交易结束(未终止)
	public static final long STATUS_16 = 16;// 已经退款，交易结束（现废除，与6合并）（未终止）

	public static final long STATUS_14 = 14;// 取消出票,交易结束(终止)
	public static final long STATUS_15 = 15;// 取消出票已经退款(终止)

	public static final long STATUS_7 = 7;// get lock 锁定
	public static final long STATUS_8 = 8;// release lock 已解锁

	// public static final long STATUS_10=10;//B2C订单，等待收款
	public static final long STATUS_19 = 19;// 退票订单，等待审核
	public static final long STATUS_20 = 20;// 退票订单，等待审核
	public static final long STATUS_21 = 21;// 退票审核通过，等待退款
	public static final long STATUS_22 = 22;// 已经退款，交易结束
	public static final long STATUS_23 = 23;// 退票未通过，交易结束

	public static final long STATUS_24 = 24;// 退票订单，等待审核(外部导入)
	public static final long STATUS_25 = 25;// 退票订单，等待审核(外部导入)

	public static final long STATUS_29 = 29;// 废票订单，等待审核
	public static final long STATUS_30 = 30;// 废票订单，等待审核
	public static final long STATUS_31 = 31;// 废票审核通过，等待退款
	public static final long STATUS_32 = 32;// 废票已经退款，交易结束
	public static final long STATUS_33 = 33;// 废票未通过，交易结束

	public static final long STATUS_34 = 34;// 废票订单，等待审核(外部导入)
	public static final long STATUS_35 = 35;// 废票订单，等待审核(外部导入)

	public static final long STATUS_39 = 39;// 改签订单，等待审核
	public static final long STATUS_40 = 40;// 改签订单，等待审核
	public static final long STATUS_41 = 41;// 改签审核通过，等待支付
	public static final long STATUS_42 = 42;// 改签审核通过，等待支付
	public static final long STATUS_43 = 43;// 改签已支付，等待确认
	public static final long STATUS_44 = 44;// 改签未通过，交易结束
	public static final long STATUS_45 = 45;// 改签完成，交易结束

	public static final long STATUS_46 = 46;// 改签订单，等待审核(外部导入)
	public static final long STATUS_47 = 47;// 改签订单，等待审核(外部导入)

	// public static final long STATUS_80 = 80;//交易结束
	public static final long STATUS_88 = 88;// 已废弃

	// ---------------------------------------------团队票定案状态从100开始
	public static final long STATUS_101 = 101;// 待统计利润
	public static final long STATUS_102 = 102;// 等待申请支付
	public static final long STATUS_103 = 103;// 等待确认支付
	public static final long STATUS_105 = 105;// 支付（出票）成功，交易结束
	public static final long STATUS_107 = 107;// 退票订单，待统计利润
	public static final long STATUS_116 = 116;// 已经统计利润，等待申请退票
	public static final long STATUS_117 = 117;// 已经申请退票，等待审核
	public static final long STATUS_108 = 108;// 退票已审,待收退款
	public static final long STATUS_109 = 109;// 已经退款，交易结束
	public static final long STATUS_110 = 110;// 退票未通过，交易结束

	// -------------------结算表统计结果
	private BigDecimal inAmount = BigDecimal.ZERO;
	private BigDecimal outAmount = BigDecimal.ZERO;
	private BigDecimal inRefundAmount = BigDecimal.ZERO;
	private BigDecimal outRefundAmount = BigDecimal.ZERO;

	private Timestamp inTime;
	private Timestamp outTime;
	private Timestamp inRefundTime;
	private Timestamp outRefundTime;

	//----------预删除
//	private Account inAccount;
//	private Account outAccount;
//	private Account inRefundAccount;
//	private Account outRefundAccount;	
	
	private String inAccountName="";
	private String outAccountName="";
	private String inRefundAccountName="";
	private String outRefundAccountName="";
	
	private String inAccountNo="";
	private String outAccountNo="";
	private String inRefundAccountNo="";
	private String outRefundAccountNo="";	
	
	private String inMemo="";
	private String outMemo="";
	private String inRefundMemo="";
	private String outRefundMemo="";
	

	// -------------------结算表统计结果

	public AirticketOrder()
	{

	}

	public AirticketOrder(long groupId)
	{
		this.orderGroup.setId(groupId);
	}

	
	
	public String getOrderNo()
	{
		if (this.orderNo == null || this.orderNo.equals("")) { return "O000000000000"; }
		return orderNo;
	}

	public BigDecimal getInAmount()
	{
		if (this.inAmount == null) { return BigDecimal.ZERO; }
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount)
	{
		this.inAmount = inAmount;
	}

	public BigDecimal getOutAmount()
	{
		if (this.outAmount == null) { return BigDecimal.ZERO; }
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount)
	{
		this.outAmount = outAmount;
	}

	public BigDecimal getInRefundAmount()
	{
		if (this.inRefundAmount == null) { return BigDecimal.ZERO; }
		return inRefundAmount;
	}

	public void setInRefundAmount(BigDecimal inRefundAmount)
	{
		this.inRefundAmount = inRefundAmount;
	}

	public BigDecimal getOutRefundAmount()
	{
		if (this.outRefundAmount == null) { return BigDecimal.ZERO; }
		return outRefundAmount;
	}

	public void setOutRefundAmount(BigDecimal outRefundAmount)
	{
		this.outRefundAmount = outRefundAmount;
	}

	public Timestamp getInTime()
	{
		return inTime;
	}

	public void setInTime(Timestamp inTime)
	{
		this.inTime = inTime;
	}

	public Timestamp getOutTime()
	{
		return outTime;
	}

	public void setOutTime(Timestamp outTime)
	{
		this.outTime = outTime;
	}

	public Timestamp getInRefundTime()
	{
		return inRefundTime;
	}

	public void setInRefundTime(Timestamp inRefundTime)
	{
		this.inRefundTime = inRefundTime;
	}

	public Timestamp getOutRefundTime()
	{
		return outRefundTime;
	}

	public void setOutRefundTime(Timestamp outRefundTime)
	{
		this.outRefundTime = outRefundTime;
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

	public String getInRefundAccountName() {
		return inRefundAccountName;
	}

	public void setInRefundAccountName(String inRefundAccountName) {
		this.inRefundAccountName = inRefundAccountName;
	}

	public String getOutRefundAccountName() {
		return outRefundAccountName;
	}

	public void setOutRefundAccountName(String outRefundAccountName) {
		this.outRefundAccountName = outRefundAccountName;
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

	public String getInRefundAccountNo() {
		return inRefundAccountNo;
	}

	public void setInRefundAccountNo(String inRefundAccountNo) {
		this.inRefundAccountNo = inRefundAccountNo;
	}

	public String getOutRefundAccountNo() {
		return outRefundAccountNo;
	}

	public void setOutRefundAccountNo(String outRefundAccountNo) {
		this.outRefundAccountNo = outRefundAccountNo;
	}


	public String getInMemo() {
		return inMemo;
	}

	public void setInMemo(String inMemo) {
		this.inMemo = inMemo;
	}

	public String getOutMemo() {
		return outMemo;
	}

	public void setOutMemo(String outMemo) {
		this.outMemo = outMemo;
	}

	public String getInRefundMemo() {
		return inRefundMemo;
	}

	public void setInRefundMemo(String inRefundMemo) {
		this.inRefundMemo = inRefundMemo;
	}

	public String getOutRefundMemo() {
		return outRefundMemo;
	}

	public void setOutRefundMemo(String outRefundMemo) {
		this.outRefundMemo = outRefundMemo;
	}

	public String getFormatInTime()
	{
		String mydate = "";
		if (this.inTime != null && "".equals(inTime) == false)
		{
			Date tempDate = new Date(inTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatOutTime()
	{
		String mydate = "";
		if (this.outTime != null && "".equals(outTime) == false)
		{
			Date tempDate = new Date(outTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatInRefundTime()
	{
		String mydate = "";
		if (this.inRefundTime != null && "".equals(inRefundTime) == false)
		{
			Date tempDate = new Date(inRefundTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatOutRefundTime()
	{
		String mydate = "";
		if (this.outRefundTime != null && "".equals(outRefundTime) == false)
		{
			Date tempDate = new Date(outRefundTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getRebateText()
	{
		return "";
	}

	public Long getLocked()
	{
		if (this.locked == null) { return new Long(88); }
		return this.locked;
	}

	public String getLockedText()
	{
		if (this.locked != null)
		{
			if (this.locked == 0)
			{
				return "正常";
			}
			else if (this.locked == 1) { return "已锁定"; }
		}
		return "";
	}

	public Long getAdultCount()
	{
		if (this.adultCount == null)
			this.adultCount = new Long(0);
		return this.adultCount;
	}

	public Long getChildCount()
	{
		if (this.childCount == null)
			this.childCount = new Long(0);
		return this.childCount;
	}

	public Long getBabyCount()
	{
		if (this.babyCount == null)
			this.babyCount = new Long(0);
		return this.babyCount;
	}

	public String getShowPNR()
	{
		String subPnr = getSubPnr();
		String drawPnr = getDrawPnr();
		String bigPnr = getBigPnr();

		if (subPnr == null || "".equals(subPnr))
		{
			subPnr = "...";
		}
		if (drawPnr == null || "".equals(drawPnr))
		{
			drawPnr = "...";
		}
		if (bigPnr == null || "".equals(bigPnr))
		{
			bigPnr = "...";
		}
		// String
		// showPnrHtml="<font color='#0033FF'>"+subPnr+"</font><font
		// color='red'>"+drawPnr+"</font><font
		// color='#0033FF'>"+bigPnr+"/</font>";
		return subPnr
		    + "<font style='color: blue'>/</font><font style='color: red'>"
		    + drawPnr + "</font><font style='color: blue'>/</font>" + bigPnr;
	}

	public String getBusinessTypeText()
	{
		String businessTypeText = "";
		if (this.getBusinessType() != null)
		{
			if (this.getBusinessType() == TRANTYPE__2)
			{
				businessTypeText = "买入";
			}
			else if (this.getBusinessType() == TRANTYPE__1)
			{
				businessTypeText = "卖出";
			}
			else
			{
				businessTypeText = "";
			}
		}
		else
		{
			businessTypeText = "";
		}
		return businessTypeText;
	}

	public String getTranTypeText()
	{
		String tranTypeText = "";
		if (this.getTranType() != null)
		{
			if (this.getTranType() == TRANTYPE__2)
			{
				tranTypeText = "买入";
			}
			else if (this.getTranType() == TRANTYPE__1)
			{
				tranTypeText = "卖出";
			}
			else if (this.getTranType() == TRANTYPE_3)
			{
				tranTypeText = "退票";
			}
			else if (this.getTranType() == TRANTYPE_4)
			{
				tranTypeText = "废票";
			}
			else if (this.getTranType() == TRANTYPE_5)
			{
				tranTypeText = "改签";
			}
			else
			{
				tranTypeText = "";
			}
		}
		else
		{
			tranTypeText = "";
		}
		return tranTypeText;
	}

	public String getTranTypeText2()
	{
		String tranTypeText = "";
		if (this.getTranType() != null)
		{
			if (this.getTranType() == TRANTYPE__1
			    || this.getTranType() == TRANTYPE__2)
			{
				tranTypeText = "销售";
			}
			else if (this.getTranType() == TRANTYPE_3)
			{
				tranTypeText = "退票";
			}
			else if (this.getTranType() == TRANTYPE_4)
			{
				tranTypeText = "废票";
			}
			else if (this.getTranType() == TRANTYPE_5)
			{
				tranTypeText = "改签";
			}
			else
			{
				tranTypeText = "";
			}
		}
		else
		{
			tranTypeText = "";
		}
		return tranTypeText;
	}

	public String getTicketTypeText()
	{
		String ticketTypeText = "";
		if (this.getTicketType() != null)
		{
			if (this.getTicketType() == TICKETTYPE_1)
			{
				ticketTypeText = "普通";
			}
			else if (this.getTicketType() == TICKETTYPE_2)
			{
				ticketTypeText = "团队";
			}
			else if (this.getTicketType() == TICKETTYPE_3)
			{
				ticketTypeText = "B2C";
			}
			else
			{
				ticketTypeText = "";
			}

		}
		else
		{
			ticketTypeText = "";
		}
		return ticketTypeText;
	}

	public String getAirOrderNosText()
	{
		String temp = "";
		if (airOrderNos != null && airOrderNos.length > 0)
		{
			temp = airOrderNos[0];
			for (int i = 1; i < airOrderNos.length; i++)
			{
				temp = temp + "," + airOrderNos[i];
			}
		}
		return temp;
	}

	public String getAirOrderNoHtmlText()
	{
		if (airOrderNo != null) { return airOrderNo.replaceAll("[,]", "<br/>"); }
		return "";
	}

	public String[] getAirOrderNos()
	{
		if (airOrderNo != null)
			return airOrderNo.split(",");
		return new String[0];

	}
	
	 public String getStatementAmount() {
	        return this.statementAmount;
	    }
	 
	 public String getShowTotalAmount() {
		 String statementAmount=Constant.toString(getStatementAmount());
		 if("".equals(statementAmount)){
			 return getTotalAmount()+"";
		 }else{
			 if("0".equals(statementAmount)){
				 return getTotalAmount()+"";
			 }else{
				 return this.statementAmount;
			 }
		 }
	 }
	    public java.math.BigDecimal getOldStatementAmount() {
	    	if(this.oldStatementAmount==null){
	    		return BigDecimal.ZERO;
	    	}
	        return this.oldStatementAmount;
	    }

	public void setAirOrderNos(String[] airOrderNos)
	{
		this.airOrderNos = airOrderNos;
	}

	public String[] getPassengerCardnos()
	{
		return passengerCardnos;
	}

	public void setPassengerCardnos(String[] passengerCardnos)
	{
		this.passengerCardnos = passengerCardnos;
	}

	public String[] getPassengerTicketNumbers()
	{
		return passengerTicketNumbers;
	}

	public void setPassengerTicketNumbers(String[] passengerTicketNumbers)
	{
		this.passengerTicketNumbers = passengerTicketNumbers;
	}

	public java.math.BigDecimal getTotalAmount()
	{
		if (this.totalAmount == null) { return new BigDecimal(0); }
		return this.totalAmount.abs();
	}
	


	public java.math.BigDecimal getBuyHandlingCharge()
	{
		if (buyHandlingCharge == null) { return BigDecimal.ZERO; }
		return buyHandlingCharge;
	}

	public void setBuyHandlingCharge(java.math.BigDecimal buyHandlingCharge)
	{
		this.buyHandlingCharge = buyHandlingCharge;
	}

	public java.math.BigDecimal getSaleTotalAmount()
	{
		if (this.saleTotalAmount == null) { return BigDecimal.ZERO; }
		return saleTotalAmount;
	}

	public void setSaleTotalAmount(java.math.BigDecimal saleTotalAmount)
	{
		this.saleTotalAmount = saleTotalAmount;
	}

	// 总人数
	public long getTotalPerson()
	{
		long totalPerson = new Long(0);
		if (this.ticketType != null)
		{
			if (this.ticketType == TICKETTYPE_2)
			{// 团队
				if (this.getAdultCount() != null)
				{
					totalPerson = totalPerson + this.getAdultCount();
				}
				if (this.getChildCount() != null)
				{
					totalPerson = totalPerson + this.getChildCount();
				}
				if (this.getBabyCount() != null)
				{
					totalPerson = totalPerson + this.getBabyCount();
				}
			}
			else
			{
				if (this.passengers != null)
				{
					totalPerson = this.passengers.size();
				}
			}
		}
		return totalPerson;
	}

	public String getStatusText()
	{
		String statusText = "";
		statusText=getStatusTextByValue(this.getStatus());
		return statusText;
	}
	
	public static String getStatusTextByValue(Long status)
	{
		String statusText = "";
		if (status != null)
		{
			if (status == STATUS_1)
			{
				statusText = "新订单";
			}
			else if (status == STATUS_2)
			{
				statusText = "等待支付";
			}
			else if (status == STATUS_3)
			{
				statusText = "等待出票";
			}
			else if (status == STATUS_4)
			{
				statusText = "取消出票";
			}
			else if (status == STATUS_13)
			{
				statusText = "等待取消出票";// 卖出已取消，买入只能取消
			}
			else if (status == STATUS_9)
			{
				statusText = "取消出票等待退款";// 买入
			}
			else if (status == STATUS_10)
			{
				statusText = "取消出票等待退款";// 卖出
			}
			else if (status == STATUS_5)
			{
				statusText = "出票成功";
			}
			else if (status == STATUS_6)
			{
				statusText = "取消出票已退款";
			}
			else if (status == STATUS_14)
			{
				statusText = "取消出票";// 已终止
			}
			else if (status == STATUS_15)
			{
				statusText = "取消出票已退款";// 已终止
			}
			else if (status == STATUS_16)
			{// 已废除
				statusText = "取消出票已退款";
			}
			else if (status == STATUS_7)
			{
				statusText = "等待支付已锁定";
			}
			else if (status == STATUS_8)
			{
				statusText = "等待支付已解锁";
			}
			else if (status == STATUS_19 || status == STATUS_24)
			{
				statusText = "退票等待审核";
			}
			else if (status == STATUS_20 || status == STATUS_25)
			{
				statusText = "退票等待审核";
			}
			else if (status == STATUS_21)
			{
				statusText = "退票已审待退款";
			}
			else if (status == STATUS_22)
			{
				statusText = "退票已退款";
			}
			else if (status == STATUS_23)
			{
				statusText = "退票未通过";
			}
			else if (status == STATUS_29 || status == STATUS_34)
			{
				statusText = "废票订单，等待审核";
			}
			else if (status == STATUS_30 || status == STATUS_35)
			{
				statusText = "废票订单，等待审核";
			}
			else if (status == STATUS_31)
			{
				statusText = "废票已审核，等待退款";
			}
			else if (status == STATUS_32)
			{
				statusText = "废票已退款";
			}
			else if (status == STATUS_33)
			{
				statusText = "废票未通过，交易结束";
			}
			else if (status == STATUS_39 || status == STATUS_46)
			{
				statusText = "改签订单，等待审核";
			}
			else if (status == STATUS_40 || status == STATUS_47)
			{
				statusText = "改签订单，等待审核";
			}
			else if (status == STATUS_41)
			{
				statusText = "改签已审核，等待支付";
			}
			else if (status == STATUS_42)
			{
				statusText = "改签已审核，等待支付";
			}
			else if (status == STATUS_43)
			{
				statusText = "改签已支付，等待确认";
			}
			else if (status == STATUS_44)
			{
				statusText = "改签未通过，交易结束";
			}
			else if (status == STATUS_45)
			{
				statusText = "改签完成，交易结束";
			}
			else if (status == STATUS_88)
			{
				statusText = "已废弃";
			}
			else if (status == STATUS_101)
			{
				statusText = "销售待统计利润";
			}
			else if (status == STATUS_102)
			{
				statusText = "等待申请支付";// 统计利润之后
			}
			else if (status == STATUS_103)
			{
				statusText = "等待确认支付";
			}
			else if (status == STATUS_105)
			{
				statusText = "完成出票";// 确认支付后
			}
			else if (status == STATUS_107)
			{
				statusText = "退票单创建，待统计利润";
			}
			else if (status == STATUS_116)
			{
				statusText = "已统计利润，待申请退票";
			}
			else if (status == STATUS_117)
			{
				statusText = "已申请退票，待审核";
			}
			else if (status == STATUS_108)
			{
				statusText = "已审退票,待收退款";
			}
			else if (status == STATUS_109)
			{
				statusText = "完成退款";
			}
			else if (status == STATUS_110)
			{
				statusText = "退票未通过，交易结束";
			}
			else
			{
				statusText = "";
			}
		}
		else
		{
			statusText = "";
		}
		return statusText;
	}

	// 页面显示，当前操作人姓名
	public String getCurrentOperatorName()
	{
		if (this.currentOperator != null
		    && "".equals(this.currentOperator) == false) { return UserStore
		    .getUserNameByNo(this.currentOperator); }
		return "";
	}

	// 操作人（录单人）（新版本）
	public String getShowEntryOperator()
	{
		if (this.businessType != null && this.tranType != null)
		{
			if (businessType == 1)
			{
				if (tranType == 1)
				{
					return operate1;
				}
				else if (tranType == 3)
				{
					// return operate35;
					return getEntryOperator();
				}
				else if (tranType == 4)
				{
					// return operate51;
					return getEntryOperator();
				}
				else if (tranType == 5)
				{
					// return operate71;
				}
			}
			else if (businessType == 2)
			{
				if (tranType == 2)
				{
					return operate13;
				}
				else if (tranType == 3)
				{
					return operate40;
				}
				else if (tranType == 4)
				{
					return operate52;
				}
				else if (tranType == 5)
				{
					// return operate72;
				}
			}
		}
		return "";
	}

	// 操作人（录单人）（新版本）
	public String getShowEntryOperatorName()
	{
		String entryOperator = getShowEntryOperator();
		if (entryOperator != null && "".equals(entryOperator) == false) { return UserStore
		    .getUserNameByNo(entryOperator); }
		return "";
	}

	// 支付人（录单人）（新版本）
	public String getShowPayOperator()
	{
		if (this.businessType != null && this.tranType != null
		    && this.status != null)
		{
			if (businessType == 1)
			{
				if (tranType == 1)
				{
					// 收款人
				}
				else if (tranType == 3)
				{
					// 收退款
				}
				else if (tranType == 4)
				{
					// 收退款
				}
				else if (tranType == 5)
				{
					// return operate71;//未定义
				}
			}
			else if (businessType == 2)
			{
				if (tranType == 2)
				{
					if (status == STATUS_7)
					{
						return operate14;
					}
					else
					{
						return operate15;
					}
				}
				else if (tranType == 3)
				{
					return operate43;
				}
				else if (tranType == 4)
				{
					return operate55;
				}
				else if (tranType == 5)
				{
					// return operate72;//未定义
				}
			}
		}
		return "";
	}

	// 支付人（录单人）（新版本）
	public String getShowPayOperatorName()
	{
		String payOperator = getShowPayOperator();
		if (payOperator != null && "".equals(payOperator) == false) { return UserStore
		    .getUserNameByNo(payOperator); }
		return "";
	}

	// 支付人（退废订单-收退款）（新版本）
	public String getShowRefundOperatorName()
	{
		String payOperator = getShowRefundOperator();
		if (payOperator != null && "".equals(payOperator) == false) { return UserStore
		    .getUserNameByNo(payOperator); }
		return "";
	}

	// 收款人（退废订单-收退款）（新版本）
	public String getShowRefundOperator()
	{
		if (this.businessType != null && this.tranType != null
		    && this.status != null)
		{
			if (businessType == 1)
			{
				if (tranType == 3)
				{
					// 付退票退款
					return operate43;
				}
				else if (tranType == 4)
				{
					// 付废票退款
					return operate55;
				}
				else if (tranType == 5)
				{
					//
				}
			}
			else if (businessType == 2)
			{
				if (tranType == 3)
				{
					// 收退票退款
					return operate42;
				}
				else if (tranType == 4)
				{
					// 收废票退款
					return operate54;
				}
				else if (tranType == 5)
				{
					// //未定义
				}
			}
		}
		return "";
	}

	// 操作人（录单人）(旧版)
	public String getEntryOperatorName()
	{
		if (this.entryOperator != null && "".equals(this.entryOperator) == false) { return UserStore
		    .getUserNameByNo(this.entryOperator); }
		return "";
	}

	// 操作人（支付人/收款人）(旧版)
	public String getPayOperatorName()
	{
		if (this.payOperator != null && "".equals(this.payOperator) == false)
		{
			return UserStore.getUserNameByNo(this.payOperator);
		}
		else
		{
			return getCurrentOperatorName();
		}
	}

	public String getEntryOrderDate()
	{
		String mydate = "";
		if (this.entryTime != null && "".equals(entryTime) == false)
		{
			Date tempDate = new Date(entryTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getDrawTimeText()
	{
		String mydate = "";
		if (this.drawTime != null && "".equals(drawTime) == false)
		{
			Date tempDate = new Date(drawTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getFormatPayTime()
	{
		String mydate = "";
		if (this.payTime != null && "".equals(payTime) == false)
		{
			Date tempDate = new Date(payTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getRetireTypeInfo()
	{
		if (this.tranType != null)
		{
			if (this.tranType == 3)
			{
				if (this.returnReason != null)
				{
					if (this.transRule != null && "客规".equals(this.returnReason.trim()))
					{
						return "客规" + this.transRule + "%";
					}
					else
					{
						return this.returnReason;
					}
				}
			}
			else if (this.tranType == 4) { return "废票"; }
		}
		return "";
	}

	public String getOldOrderNo()
	{
		if (this.oldOrderNo != null)
		{
			return this.oldOrderNo;
		}
		else
		{
			return "";
		}
	}

	public void setDrawPnr(String drawPnr)
	{
		if (drawPnr != null)
		{
			drawPnr = drawPnr.trim().toUpperCase();
		}
		this.drawPnr = drawPnr;
	}

	public void setSubPnr(String subPnr)
	{
		if (subPnr != null)
		{
			subPnr = subPnr.trim().toUpperCase();
		}
		this.subPnr = subPnr;
	}

	public String getImportType()
	{
		return importType;
	}

	public void setImportType(String importType)
	{
		this.importType = importType;
	}

	public void setBigPnr(String bigPnr)
	{
		if (bigPnr != null)
		{
			bigPnr = bigPnr.trim().toUpperCase();
		}
		this.bigPnr = bigPnr;
	}

	public void setUmbuchenPnr(String umbuchenPnr)
	{
		if (umbuchenPnr != null)
		{
			this.umbuchenPnr = umbuchenPnr.trim().toUpperCase();
		}
		this.umbuchenPnr = umbuchenPnr;
	}

	public java.math.BigDecimal getTotalTicketPrice()
	{
		if (this.totalTicketPrice == null) { return BigDecimal.ZERO; }
		return this.totalTicketPrice.abs();
	}

	public java.math.BigDecimal getTotalAirportPrice()
	{
		if (this.totalAirportPrice == null) { return BigDecimal.ZERO; }
		return this.totalAirportPrice.abs();
	}

	public java.math.BigDecimal getTotalFuelPrice()
	{
		if (this.totalFuelPrice == null) { return BigDecimal.ZERO; }
		return this.totalFuelPrice.abs();
	}

	public java.math.BigDecimal getHandlingCharge()
	{
		if (this.handlingCharge == null) { return BigDecimal.ZERO; }
		return this.handlingCharge;
	}

	public java.math.BigDecimal getOverTicketPrice()
	{
		if (this.overTicketPrice == null) { return BigDecimal.ZERO; }
		return this.overTicketPrice.abs();
	}

	public java.math.BigDecimal getCommissonCount()
	{
		if (this.commissonCount == null) { return BigDecimal.ZERO; }
		return this.commissonCount.abs();
	}

	public java.math.BigDecimal getOverAirportfulePrice()
	{
		if (this.overAirportfulePrice == null) { return BigDecimal.ZERO; }
		return this.overAirportfulePrice.abs();
	}

	public java.math.BigDecimal getRakeoffCount()
	{
		if (this.rakeoffCount == null) { return BigDecimal.ZERO; }
		return this.rakeoffCount.abs();
	}

	public java.math.BigDecimal getRakeOff()
	{
		if (this.rakeOff == null) { return BigDecimal.ZERO; }
		return this.rakeOff;
	}

	public void setTicketPrice(java.math.BigDecimal ticketPrice)
	{
		this.ticketPrice = ticketPrice;
	}

	public void setFuelPrice(java.math.BigDecimal fuelPrice)
	{
		this.fuelPrice = fuelPrice;
	}

	public void setAirportPrice(java.math.BigDecimal airportPrice)
	{
		this.airportPrice = airportPrice;
	}

	public java.math.BigDecimal getSaleRakeOff()
	{
		if (this.saleRakeOff == null) { return BigDecimal.ZERO; }
		return saleRakeOff.abs();
	}

	public void setSaleRakeOff(java.math.BigDecimal saleRakeOff)
	{
		this.saleRakeOff = saleRakeOff;
	}

	public Long getPlatComAccountId()
	{
		return platComAccountId;
	}

	public void setPlatComAccountId(Long platComAccountId)
	{
		this.platComAccountId = platComAccountId;
	}

	public Long getPlatformId()
	{
		return platformId;
	}

	public void setPlatformId(Long platformId)
	{
		this.platformId = platformId;
	}

	public Long getCompanyId()
	{
		return companyId;
	}

	public void setCompanyId(Long companyId)
	{
		this.companyId = companyId;
	}

	public Long getAccountId()
	{
		return accountId;
	}

	public void setAccountId(Long accountId)
	{
		this.accountId = accountId;
	}

	public String getPnr()
	{
		return pnr;
	}

	public void setPnr(String pnr)
	{
		this.pnr = pnr;
	}

	public java.math.BigDecimal getRebate()
	{
		if (this.rebate != null)
		{
			return rebate.abs();
		}
		else
		{
			return BigDecimal.ZERO;
		}
	}

	public java.math.BigDecimal getTicketPrice()
	{
		if (this.ticketPrice != null)
		{
			return ticketPrice.abs();
		}
		else
		{
			return BigDecimal.ZERO;
		}
	}

	public java.math.BigDecimal getDomentPrice()
	{
		if (this.documentPrice != null)
		{
			return documentPrice.abs();
		}
		else
		{
			return BigDecimal.ZERO;
		}
	}

	public java.math.BigDecimal getInsurancePrice()
	{
		if (this.insurancePrice != null)
		{
			return insurancePrice.abs();
		}
		else
		{
			return BigDecimal.ZERO;
		}
	}

	public String getQuitTicketType()
	{
		return quitTicketType;
	}

	public void setQuitTicketType(String quitTicketType)
	{
		this.quitTicketType = quitTicketType;
	}

	public String getQuitTicketReason()
	{
		return quitTicketReason;
	}

	public void setQuitTicketReason(String quitTicketReason)
	{
		this.quitTicketReason = quitTicketReason;
	}

	public Long getAgentId()
	{
		return agentId;
	}

	public void setAgentId(Long agentId)
	{
		this.agentId = agentId;
	}

	public String getForwardPage()
	{
		return forwardPage;
	}

	public void setForwardPage(String forwardPage)
	{
		this.forwardPage = forwardPage;
	}

	public TicketLog getTicketLog()
	{
		return ticketLog;
	}

	public void setTicketLog(TicketLog ticketLog)
	{
		this.ticketLog = ticketLog;
	}

	public String getBoardingTime()
	{

		return boardingTime;
	}

	public void setBoardingTime(String boardingTime)
	{
		this.boardingTime = boardingTime;
	}

	public long getTotlePernson()
	{
		return this.getAdultCount() + this.getChildCount() + this.getBabyCount();
	}

	public java.math.BigDecimal getAirportPrice()
	{
		if (airportPrice == null) { return BigDecimal.ZERO; }
		return airportPrice.abs();
	}

	public java.math.BigDecimal getFuelPrice()
	{
		if (fuelPrice == null) { return BigDecimal.ZERO; }
		return fuelPrice.abs();
	}

	public Operate getTeamCommonOperate()
	{
		return teamCommonOperate;
	}

	public void setTeamCommonOperate(Operate teamCommonOperate)
	{
		this.teamCommonOperate = teamCommonOperate;
	}

	public String[] getFlightIds()
	{
		return flightIds;
	}

	public void setFlightIds(String[] flightIds)
	{
		this.flightIds = flightIds;
	}

	public String[] getFlightCodes()
	{
		return flightCodes;
	}

	public void setFlightCodes(String[] flightCodes)
	{
		this.flightCodes = flightCodes;
	}

	public String[] getBoardingTimes()
	{
		return boardingTimes;
	}

	public Long getOriginalPassCount()
	{
		return originalPassCount;
	}

	public void setOriginalPassCount(Long originalPassCount)
	{
		this.originalPassCount = originalPassCount;
	}

	public void setBoardingTimes(String[] boardingTimes)
	{
		this.boardingTimes = boardingTimes;
	}

	public String[] getFlightClasss()
	{
		return flightClasss;
	}

	public void setFlightClasss(String[] flightClasss)
	{
		this.flightClasss = flightClasss;
	}

	public String[] getDiscounts()
	{
		return discounts;
	}

	public void setDiscounts(String[] discounts)
	{
		this.discounts = discounts;
	}

	public java.math.BigDecimal[] getTicketPrices()
	{
		return ticketPrices;
	}

	public void setTicketPrices(java.math.BigDecimal[] ticketPrices)
	{
		this.ticketPrices = ticketPrices;
	}

	public String[] getEndPoints()
	{
		return endPoints;
	}

	public void setEndPoints(String[] endPoints)
	{
		this.endPoints = endPoints;
	}

	public String[] getStartPoints()
	{
		return startPoints;
	}

	public void setStartPoints(String[] startPoints)
	{
		this.startPoints = startPoints;
	}

	public String[] getPassengerIds()
	{
		return passengerIds;
	}

	public void setPassengerIds(String[] passengerIds)
	{
		this.passengerIds = passengerIds;
	}

	public String[] getPassengerNames()
	{
		return passengerNames;
	}
	
	

	public java.math.BigDecimal[] getTransRules() {
		return transRules;
	}

	public void setTransRules(java.math.BigDecimal[] transRules) {
		this.transRules = transRules;
	}

	public void setPassengerNames(String[] passengerNames)
	{
		this.passengerNames = passengerNames;
	}

	public java.math.BigDecimal getIncomeretreatCharge()
	{
		if (incomeretreatCharge == null) { return BigDecimal.ZERO; }
		return this.incomeretreatCharge.abs();
	}

	public void setIncomeretreatCharge(BigDecimal incomeretreatCharge)
	{
		this.incomeretreatCharge = incomeretreatCharge;

	}

	public java.math.BigDecimal[] getAdultAirportPrices()
	{
		return adultAirportPrices;
	}

	public void setAdultAirportPrices(java.math.BigDecimal[] adultAirportPrices)
	{
		this.adultAirportPrices = adultAirportPrices;
	}

	public java.math.BigDecimal[] getAdultFuelPrices()
	{
		return adultFuelPrices;
	}

	public void setAdultFuelPrices(java.math.BigDecimal[] adultFuelPrices)
	{
		this.adultFuelPrices = adultFuelPrices;
	}

	public java.math.BigDecimal[] getChildAirportPrices()
	{
		return childAirportPrices;
	}

	public void setChildAirportPrices(java.math.BigDecimal[] childAirportPrices)
	{
		this.childAirportPrices = childAirportPrices;
	}

	public java.math.BigDecimal[] getChildFuelPrices()
	{
		return childFuelPrices;
	}

	public void setChildFuelPrices(java.math.BigDecimal[] childFuelPrices)
	{
		this.childFuelPrices = childFuelPrices;
	}

	public java.math.BigDecimal[] getBabyAirportPrices()
	{
		return babyAirportPrices;
	}

	public void setBabyAirportPrices(java.math.BigDecimal[] babyAirportPrices)
	{
		this.babyAirportPrices = babyAirportPrices;
	}

	public java.math.BigDecimal[] getBabyFuelPrices()
	{
		return babyFuelPrices;
	}

	public void setBabyFuelPrices(java.math.BigDecimal[] babyFuelPrices)
	{
		this.babyFuelPrices = babyFuelPrices;
	}

	public java.math.BigDecimal getTeamaddPrice()
	{
		if (this.teamaddPrice == null) { return BigDecimal.ZERO; }
//		return this.teamaddPrice.abs();
		return this.teamaddPrice;		
	}

	public java.math.BigDecimal getAgentaddPrice()
	{
		if (this.agentaddPrice == null) { return BigDecimal.ZERO; }
		return this.agentaddPrice.abs();
	}

	public String getCyrs()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getCyr() + ","
				    : flight.getCyr());
				num++;
			}
		}
		return sb.toString();
	}

	public String getFlightClassAll()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getFlightClass()
				    + "," : flight.getFlightClass());
				num++;
			}
		}
		return sb.toString();
	}

	// 计算退票手续费使用
	public String getCyr()
	{
		StringBuffer sb = new StringBuffer();
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(flight.getCyr());
				if (sb.length() > 1)
				{
					break;
				}
			}
		}
		return sb.toString();
	}

	public String getFlightClass()
	{
		StringBuffer sb = new StringBuffer();
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(flight.getFlightClass());
				if (sb.length() > 1)
				{
					break;
				}
			}
		}
		return sb.toString();
	}

	public String getCyrsHtml()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getCyr()
				    + "<br/>" : flight.getCyr());
				num++;
			}
		}
		return sb.toString();
	}

	public String getCyrsTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getCyr() + "/"
				    : flight.getCyr());
				num++;
			}
		}
		return sb.toString();
	}

	// 出发地
	public String getStartPointsTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getStartPoint()
				    + "/" : flight.getStartPoint());
				num++;
			}
		}
		return sb.toString();
	}

	// 目的地
	public String getEndPointsTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getEndPoint()
				    + "/" : flight.getEndPoint());
				num++;
			}
		}
		return sb.toString();
	}

	// 起飞时间
	public String getBoardingTimesTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight
				    .getFormatBoardingTime()
				    + "/" : flight.getFormatBoardingTime());
				num++;
			}
		}
		return sb.toString();
	}

	// 起飞时间
	public String getBoardingDatesTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight
				    .getFormatBoardingDate()
				    + "/" : flight.getFormatBoardingDate());
				num++;
			}
		}
		return sb.toString();
	}

	// 舱位
	public String getFlightClassTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getFlightClass()
				    + "/" : flight.getFlightClass());
				num++;
			}
		}
		return sb.toString();
	}

	// 折扣
	public String getDiscountTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getDiscount()
				    + "/" : flight.getDiscount());
				num++;
			}
		}
		return sb.toString();
	}

	// 行程
	public String getFlightsHtml()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getHcText()
				    + "<br/>" : flight.getHcText());
				num++;
			}
		}
		return sb.toString();
	}

	// 行程
	public String getFlightsTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getHcText() + "/"
				    : flight.getHcText());
				num++;
			}
		}
		return sb.toString();
	}

	public String getFlightsCodeHtml()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getFlightCode()
				    + "<br/>" : flight.getFlightCode());
				num++;
			}
		}
		return sb.toString();
	}

	public String getFlightsCodeTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getFlightCode()
				    + "/" : flight.getFlightCode());
				num++;
			}
		}
		return sb.toString();
	}

	public String getFlightsDiscountTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getDiscount()
				    + "/" : flight.getDiscount());
				num++;
			}
		}
		return sb.toString();
	}

	public String getFlightsDiscountHtml()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getDiscount()
				    + "<br/>" : flight.getDiscount());
				num++;
			}
		}
		return sb.toString();
	}

	public String getPassengersHtml()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getPassengers() != null && this.getPassengers().size() > 0)
		{
			for (Object obj : this.getPassengers())
			{
				Passenger passenger = (Passenger) obj;
				sb.append(num < this.getPassengers().size() - 1 ? passenger.getName()
				    + "<br/>" : passenger.getName());
				num++;
			}
		}
		return sb.toString();
	}

	public String getTicketsHtml()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getPassengers() != null && this.getPassengers().size() > 0)
		{
			for (Object obj : this.getPassengers())
			{
				Passenger passenger = (Passenger) obj;
				sb.append(num < this.getPassengers().size() - 1 ? passenger
				    .getTicketNumber()
				    + "<br/>" : passenger.getTicketNumber());
				num++;
			}
		}
		return sb.toString();
	}

	public String getTicketsTxt()
	{
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getPassengers() != null && this.getPassengers().size() > 0)
		{
			for (Object obj : this.getPassengers())
			{
				Passenger passenger = (Passenger) obj;
				sb.append(num < this.getPassengers().size() - 1 ? passenger
				    .getTicketNumber()
				    + "/" : passenger.getTicketNumber());
				num++;
			}
		}
		return sb.toString();
	}

	public String getAddType()
	{
		return addType;
	}

	public void setAddType(String addType)
	{
		this.addType = addType;
	}

	public Operate getOperate()
	{
		return operate;
	}

	public void setOperate(Operate operate)
	{
		this.operate = operate;
	}

	public UserRightInfo getUri()
	{
		return uri;
	}

	public void setUri(UserRightInfo uri)
	{
		this.uri = uri;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public java.sql.Timestamp getEntryTime()
	{

		return this.entryTime;
	}

	public java.sql.Timestamp getPayTime()
	{
		return this.payTime;
	}

	public java.math.BigDecimal getSaleOverTicketPrice()
	{
		if (saleOverTicketPrice == null) { return BigDecimal.ZERO; }
		return saleOverTicketPrice.abs();
	}

	public void setSaleOverTicketPrice(java.math.BigDecimal saleOverTicketPrice)
	{
		this.saleOverTicketPrice = saleOverTicketPrice;
	}

	public java.math.BigDecimal getSaleOverAirportfulePrice()
	{
		if (saleOverAirportfulePrice == null) { return BigDecimal.ZERO; }
		return saleOverAirportfulePrice.abs();
	}

	public void setSaleOverAirportfulePrice(
	    java.math.BigDecimal saleOverAirportfulePrice)
	{
		this.saleOverAirportfulePrice = saleOverAirportfulePrice;
	}

	public java.math.BigDecimal getSaleCommissonCount()
	{
		if (saleCommissonCount == null) { return BigDecimal.ZERO; }
		return saleCommissonCount.abs();
	}

	public void setSaleCommissonCount(java.math.BigDecimal saleCommissonCount)
	{
		this.saleCommissonCount = saleCommissonCount;
	}

	public java.math.BigDecimal getSaleIncomeretreatCharge()
	{
		if (saleIncomeretreatCharge == null) { return BigDecimal.ZERO; }
		return saleIncomeretreatCharge.abs();
	}

	public void setSaleIncomeretreatCharge(
	    java.math.BigDecimal saleIncomeretreatCharge)
	{
		this.saleIncomeretreatCharge = saleIncomeretreatCharge;
	}

	public String getSaleMemo()
	{
		return saleMemo;
	}

	public void setSaleMemo(String saleMemo)
	{
		this.saleMemo = saleMemo;
	}

	public java.math.BigDecimal getBuyCommissonCount()
	{
		if (buyCommissonCount == null) { return BigDecimal.ZERO; }
		return buyCommissonCount.abs();
	}

	public void setBuyCommissonCount(java.math.BigDecimal buyCommissonCount)
	{
		this.buyCommissonCount = buyCommissonCount;
	}

	public java.math.BigDecimal getBuyRakeoffCount()
	{
		if (buyRakeoffCount == null) { return BigDecimal.ZERO; }
		return buyRakeoffCount.abs();
	}

	public void setBuyRakeoffCount(java.math.BigDecimal buyRakeoffCount)
	{
		this.buyRakeoffCount = buyRakeoffCount;
	}

	public java.math.BigDecimal getBuyIncomeretreatCharge()
	{
		if (buyIncomeretreatCharge == null) { return BigDecimal.ZERO; }
		return buyIncomeretreatCharge.abs();
	}

	public void setBuyIncomeretreatCharge(
	    java.math.BigDecimal buyIncomeretreatCharge)
	{
		this.buyIncomeretreatCharge = buyIncomeretreatCharge;
	}

	public java.math.BigDecimal getBuyTotalAmount()
	{
		if (buyTotalAmount == null) { return BigDecimal.ZERO; }
		return buyTotalAmount.abs();
	}

	public void setBuyTotalAmount(java.math.BigDecimal buyTotalAmount)
	{
		this.buyTotalAmount = buyTotalAmount;
	}

	public String getDrawPnr()
	{
		if (this.drawPnr != null && !this.drawPnr.equals(""))
			return this.drawPnr.trim().toUpperCase();
		else
			return "";
	}

	public String getSubPnr()
	{
		if (this.subPnr != null && !this.subPnr.equals(""))
			return this.subPnr.trim().toUpperCase();
		else
			return "";
	}

	public String getBigPnr()
	{
		if (this.bigPnr != null && !this.bigPnr.equals(""))
			return this.bigPnr.trim().toUpperCase();
		else
			return "";
	}

	public Long getSubGroupMarkNo()
	{
		if (this.subGroupMarkNo == null)
			return new Long(0);

		return this.subGroupMarkNo;
	}

	public String getGroupMarkNo()
	{
		if (this.orderGroup == null) { return "GMN"; }
		return this.getOrderGroup().getNo();
	}

	public String getSubGroupMark()
	{
		if (this.orderGroup == null)
			return "FF";
		else
			return this.orderGroup.getId() + "-" + getSubGroupMarkNo();
	}

	public String getAirOrderNo()
	{
		if (this.airOrderNo == null) { return ""; }
		return this.airOrderNo;
	}

	public String getMemo()
	{
		if (this.memo == null) { return ""; }
		return this.memo;
	}

	public long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}

	public boolean isTodayFlight()
	{
		boolean flag = false;
		if (this.getFlights() != null && this.getFlights().size() > 0)
		{
			for (Object obj : this.getFlights())
			{
				Flight flight = (Flight) obj;
				String boardingDate = flight.getBoardingDate();
				if ("".equals(boardingDate) == false)
				{
					String systemDate = DateUtil.getDateString(new Date(System
					    .currentTimeMillis()), "yyyy-MM-dd");
					if (systemDate.equals(boardingDate))
					{
						flag = true;
					}
				}
				if (flag) { return flag; }
			}
		}
		// System.out.println("is today flight:"+flag);
		return flag;
	}

	public int getPassengerSize()
	{
		return this.getPassengers().size();
	}

	public Operate getCommonOperate()
	{
		return commonOperate;
	}

	public void setCommonOperate(Operate commonOperate)
	{
		this.commonOperate = commonOperate;
	}

	// =================== 散票======================
	public void getGeneralOperate(List<MyLabel> myLabels)
	{
		// /待处理新订单
		if (this.tranType == 1 && this.status == 1)
		{
			if (uri.hasRight("sb43"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv17(");
				sb.append("'" + this.id + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[取消出票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
			if (uri.hasRight("sb42"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv9(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.subPnr + "',");
				sb.append("'" + getAirOrderNo() + "',");
				sb.append("'" + this.totalAmount + "',");
				sb.append("'" + this.rebate + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[申请支付]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
		}

		// 卖出-取消出票
		if (this.tranType == 1 && this.status == 3)
		{
			if (uri.hasRight("sb43"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv17(");
				sb.append("'" + this.id + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[取消出票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
		}

		// 卖出已取消出票
		if (this.tranType == 1 && this.status == 4)
		{
			if (uri.hasRight("sb43"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv18(");
				sb.append("'" + this.id + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[取消出票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
		}

		// /锁定
		if (this.tranType == 2 && this.status == 2 || this.status == STATUS_8)
		{
			if (uri.hasRight("sb43"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv18(");
				sb.append("'" + this.id + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[取消出票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
			if (uri.hasRight("sb44"))
			{
				MyLabel ml2 = new MyLabel();
				ml2.setHref(this.path
				    + "/airticket/airticketOrder.do?thisAction=lockupOrder&id="
				    + this.id);
				ml2.setLabText(" [锁定]");
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
		}

		// 锁定/解锁
		if (this.tranType == 2 && this.status == STATUS_7)
		{
			if (uri.hasRight("sb43"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv18(");
				sb.append("'" + this.id + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[取消出票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}

			if (uri.getUser().getUserNo().equals(this.currentOperator))
			{
				MyLabel ml2 = new MyLabel();
				ml2.setHref(this.path
				    + "/airticket/airticketOrder.do?thisAction=unlockSelfOrder&id="
				    + this.id);
				ml2.setLabText(" [解锁]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
			else
			{
				if (uri.hasRight("sb45"))
				{
					MyLabel ml2 = new MyLabel();
					ml2.setHref(this.path
					    + "/airticket/airticketOrder.do?thisAction=unlockAllOrder&id="
					    + this.id);
					ml2.setLabText("[解锁他人订单]");
					ml2.setEndText("<br/>");
					myLabels.add(ml2);
					operate.setMyLabels(myLabels);
				}
			}

			if (uri.hasRight("sb46"))
			{
				if (uri.getUser().getUserNo().equals(this.currentOperator))
				{
					MyLabel ml3 = new MyLabel();
					StringBuffer sb = new StringBuffer();
					sb.append("onclick=\"");
					sb.append("showDiv(");
					sb.append("'" + this.id + "',");
					sb.append("'" + this.subPnr + "',");
					sb.append("'" + getAirOrderNo() + "',");
					sb.append("'" + this.totalAmount + "',");
					sb.append("'" + this.rebate + "',");
					sb.append("'" + this.getEntryOrderDate() + "'");
					sb.append(")\"");
					ml3.setEvents(sb.toString());
					ml3.setLabText("[确认支付]");
					ml3.setEndText("<br/>");
					myLabels.add(ml3);
					operate.setMyLabels(myLabels);
				}
			}
		}

		// 买入-已取消出票--可再次申请
		if (this.tranType == 2 && this.status == STATUS_4)
		{
			if (uri.hasRight("sb17"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv9(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.subPnr + "',");
				sb.append("'" + this.airOrderNo + "',");
				sb.append("'" + this.totalAmount + "',");
				sb.append("'" + this.rebate + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[再次申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
		}

		// 买入-取消出票
		if (this.tranType == 2 && this.status == STATUS_6)
		{
			if (uri.hasRight("sb17"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv9(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.subPnr + "',");
				sb.append("'" + this.airOrderNo + "',");
				sb.append("'" + this.totalAmount + "',");
				sb.append("'" + this.rebate + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[再次申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
		}

		// （卖出）取消出票 确认退款
		if (this.tranType == 1 && this.status == 10)
		{
			if (uri.hasRight("sb32"))
			{
				MyLabel ml = new MyLabel();
				// ml.setHref(this.path
				// + "/airticket/airticketOrder.do?thisAction=agreeCancelRefund&id="
				// + this.id);
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv19(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.totalAmount + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText(" [确认退款]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}
		// （买入）取消出票 确认收款
		if (this.tranType == 2 && this.status == 9)
		{
			if (uri.hasRight("sb32"))
			{
				MyLabel ml = new MyLabel();
				// ml.setHref(this.path
				// + "/airticket/airticketOrder.do?thisAction=agreeCancelRefund&id="
				// + this.id);
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv19(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.totalAmount + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText(" [确认收款]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}


		// 卖出取消出票，强制买入订单进入取消出票流程
		if (this.tranType == 2 && this.status == 13)
		{
			if (uri.hasRight("sb43"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv18(");
				sb.append("'" + this.id + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[取消出票]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
		}

		// 出票
		if (this.tranType == 2 && this.status == 3)
		{
			MyLabel ml = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv2(");
			sb.append("'" + this.id + "',");
			sb.append("'" + this.subPnr + "',");
			sb.append("'" + this.orderGroup.getId() + "'");
			sb.append(")\"");
			ml.setEvents(sb.toString());
			ml.setLabText("[确认出票]");
			ml.setEndText("<br/>");
			myLabels.add(ml);
			if (uri.hasRight("sb43"))
			{

				MyLabel ml2 = new MyLabel();
				StringBuffer sb1 = new StringBuffer();
				sb1.append("onclick=\"");
				sb1.append("showDiv18(");
				sb1.append("'" + this.id + "'");
				sb1.append(")\"");
				ml2.setEvents(sb1.toString());
				ml2.setLabText("[取消出票]");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// ========================== 退废=================
		// <!-- 退票 通过申请 创建买入退票-->
		if (this.tranType == 3 && this.status == 19)
		{
			if (uri.hasRight("sb51"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv3(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[通过申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// -- 退票 通过申请-- 更新卖出
		if (this.tranType == 3 && this.status == 20)
		{
			if (uri.hasRight("sb51"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv7(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[通过申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// 退票 确认退款
		if (this.businessType == 1 && this.tranType == 3 && this.status == 21)
		{
			if (uri.hasRight("sb52"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv4(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[确认退款]");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// <!-- 废票- 通过申请 3->
		if (this.tranType == 4 && this.status == 29)
		{
			if (uri.hasRight("sb51"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv3(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[通过申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// <!-- 废票- 通过申请 7->
		if (this.tranType == 4 && this.status == 30)
		{
			if (uri.hasRight("sb51"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv7(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[通过申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// 确认退款
		if (this.tranType == 4 && this.status == 31 && this.businessType == 1)
		{
			if (uri.hasRight("sb52"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv4(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[确认退款]");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		if (this.tranType == 4 && this.status == 35)
		{
			if (uri.hasRight("sb51"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv13(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "'");
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[通过申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// <!-- 确认收款 -->
		// 确认退款 退票
		if (this.tranType == 3 && this.status == 21 && this.businessType == 2)
		{
			if (uri.hasRight("sb52"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv4(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[确认收退款]");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// <!-- 确认收款 -->
		// 再次
		// 确认收退款，修改收款时间------------------------------------------------------------------
		/*
		 * if (this.tranType == 3 && this.status == 21 && this.businessType == 2) {
		 * if (uri.hasRight("sb52")) { MyLabel ml = new MyLabel(); StringBuffer sb =
		 * new StringBuffer(); sb.append("onclick=\""); sb.append("showDiv4(");
		 * sb.append("'" + this.id + "',"); sb.append("'" + this.tranType + "',");
		 * sb.append("'" + this.orderGroup.getId() + "',"); sb.append("'" +
		 * this.getEntryOrderDate() + "'"); sb.append(")\"");
		 * ml.setEvents(sb.toString()); ml.setLabText("[确认已收退款]"); myLabels.add(ml);
		 * } operate.setMyLabels(myLabels); }
		 */

		// 确认退款 废票
		if (this.tranType == 4 && this.status == 31 && this.businessType == 2)
		{
			if (uri.hasRight("sb52"))
			{
				MyLabel ml = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv4(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append("'" + this.orderGroup.getId() + "',");
				sb.append("'" + this.getEntryOrderDate() + "'");
				sb.append(")\"");
				ml.setEvents(sb.toString());
				ml.setLabText("[确认收款]");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// ======================= 改签 ====================
		// <!-- 申请改签 -->
		if (this.businessType == 1 && this.status == 39)
		{
			if (uri.hasRight("sb62"))
			{
				MyLabel ml = new MyLabel();
				ml
				    .setHref(this.path
				        + "/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id="
				        + this.id);
				ml.setLabText(" [拒绝申请]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			if (uri.hasRight("sb62"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("showDiv5(");
				sb.append("'" + this.id + "',");
				sb.append("'" + this.tranType + "',");
				sb.append(this.orderGroup.getId());
				sb.append(")\"");
				ml2.setEvents(sb.toString());
				ml2.setLabText("[通过申请]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
			}
			operate.setMyLabels(myLabels);
		}

		// 通过申请
		if (this.businessType == 1 && this.status == 40)
		{
			if (uri.hasRight("sb61"))
			{
				MyLabel ml = new MyLabel();
				ml
				    .setHref(this.path
				        + "/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=41&id="
				        + this.id);
				ml.setLabText(" [通过申请]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// 收款
		if (this.businessType == 1 && this.status == 41)
		{
			MyLabel ml2 = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv6(");
			sb.append("'" + this.id + "',");
			sb.append("'" + this.tranType + "'");
			sb.append(")\"");
			ml2.setEvents(sb.toString());
			ml2.setLabText("[收款]");
			ml2.setEndText("<br/>");
			myLabels.add(ml2);
			operate.setMyLabels(myLabels);
		}
		// 确认收款
		if (this.businessType == 1 && this.status == 43)
		{
			if (uri.hasRight("sb61"))
			{
				MyLabel ml = new MyLabel();
				ml.setHref(this.path
				    + "/airticket/airticketOrder.do?thisAction=finishUmbuchenOrder&id="
				    + this.id);
				ml.setLabText(" [确认收款]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}

		// <!-- 申请改签 -->
		// 收款
		if (this.businessType == 2 && this.status == 42)
		{
			MyLabel ml2 = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv6(");
			sb.append("'" + this.id + "',");
			sb.append("'" + this.tranType + "'");
			sb.append(")\"");
			ml2.setEvents(sb.toString());
			ml2.setLabText("[付款]");
			ml2.setEndText("<br/>");
			myLabels.add(ml2);
			operate.setMyLabels(myLabels);
		}

		// 改签 确认收款
		if (this.businessType == 2 && this.status == 43)
		{
			if (uri.hasRight("sb61"))
			{
				MyLabel ml = new MyLabel();
				ml.setHref(this.path
				    + "/airticket/airticketOrder.do?thisAction=finishUmbuchenOrder&id="
				    + this.id);
				ml.setLabText(" [确认收款]");
				ml.setEndText("<br/>");
				myLabels.add(ml);
			}
			operate.setMyLabels(myLabels);
		}
	}

	/**
	 * 散票管理操作
	 */
	public void getGenalManageLabel(List<MyLabel> myLabels)
	{
		if (uri.hasRight("sb81"))
		{
			MyLabel ml = new MyLabel();
			ml.setHref(this.path
			    + "/airticket/listAirTicketOrder.do?thisAction=editOrder&id="
			    + this.id);
			ml.setLabText("[编辑]");
			// ml.setEndText("<br/>");
			myLabels.add(ml);
			commonOperate.setMyLabels(myLabels);
		}

		if (uri.hasRight("sb82"))
		{
			MyLabel ml = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("return confirm('确定删除吗?');");
			sb.append("\"");
			ml.setEvents(sb.toString());

			ml
			    .setHref(this.path
			        + "/airticket/listAirTicketOrder.do?thisAction=deleteAirticketOrder&id="
			        + this.id + "&forwardPageFlag=General");
			ml.setLabText("[删除]");
			ml.setEndText("<br/>");
			myLabels.add(ml);
			commonOperate.setMyLabels(myLabels);
		}
	}

	// ======================团队订单管理======================================
	/**
	 * 团队票管理操作
	 */
	public void getTeamManageLabel(List<MyLabel> myLabels)
	{
		String editThisAction = "editTeamOrder";
		// System.out.println("-------------------------" + this.getId());

		if (this.tranType == 1 || this.tranType == 2)
		{
			editThisAction = "editTeamOrder";
		}
		else if (this.tranType.longValue() == 3)
		{
			editThisAction = "editTeamRefundOrder";
		}

		if (this.locked != null && this.locked.longValue() == LOCK1 && (this.tranType.longValue() == 1 || this.tranType.longValue() == 2))
		{
			if (uri.hasRight("sb95"))
			{
				MyLabel ml = new MyLabel();
				ml.setHref(this.path
				    + "/airticket/airticketOrderTeam.do?thisAction=unlock&id="
				    + this.id);
				ml.setLabText("[解锁]");
				// ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
			if (uri.hasRight("sb85")
			    && (this.status.longValue() <= AirticketOrder.STATUS_110 || this.status.longValue() == AirticketOrder.STATUS_117))
			{
				MyLabel ml = new MyLabel();

				ml.setHref(this.path + "/airticket/listAirTicketOrder.do?thisAction="
				    + editThisAction + "&id=" + this.id);
				ml.setLabText("[编辑]");

				// ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
		}
		else
		{
			if (uri.hasRight("sb95") && this.tranType.longValue() == 1 || this.tranType.longValue() == 2)
			{
				MyLabel ml = new MyLabel();
				ml.setHref(this.path
				    + "/airticket/airticketOrderTeam.do?thisAction=lock&id=" + this.id);
				ml.setLabText("[锁定");
				// ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}

			if (uri.hasRight("sb85")
			    && (this.status.longValue() <= AirticketOrder.STATUS_110 ||this.status.longValue() == AirticketOrder.STATUS_116||this.status.longValue() <= AirticketOrder.STATUS_117))
			{
				MyLabel ml = new MyLabel();
				ml.setHref(this.path + "/airticket/listAirTicketOrder.do?thisAction="
				    + editThisAction + "&id=" + this.id);
				ml.setLabText("[编辑]");
				// ml.setEndText("<br/>");
				myLabels.add(ml);
				operate.setMyLabels(myLabels);
			}
			else
			{
				if (this.tranType.longValue() == 1 || this.tranType.longValue() == 2)
				{
					/*
					 * boolean v = this.getOperate100() != null; boolean f =
					 * this.getOperate110() != null;
					 * System.out.println("----------------------" + uri);
					 * System.out.println("----------------------" + uri.getUser());
					 */

					if (((this.getOperate100() != null && uri != null
					    && uri.getUser() != null && this.getOperate100().equals(
					    uri.getUser().getUserNo())) || (this.getOperate110() != null
					    && uri != null && uri.getUser() != null && this.getOperate110()
					    .equals(uri.getUser().getUserNo())))
					    && this.status.longValue() <= AirticketOrder.STATUS_103)
					{
						MyLabel ml = new MyLabel();
						ml.setHref(this.path
						    + "/airticket/listAirTicketOrder.do?thisAction="
						    + editThisAction + "&id=" + this.id);
						ml.setLabText("[编辑]");
						// ml.setEndText("<br/>");
						myLabels.add(ml);
						operate.setMyLabels(myLabels);
					}
				}
				else if (this.tranType.longValue() == 3)
				{
					if (((this.getOperate121() != null && uri != null
					    && uri.getUser() != null && this.getOperate121().equals(
					    uri.getUser().getUserNo())) || (this.getOperate122() != null
					    && uri != null && uri.getUser() != null && this.getOperate122()
					    .equals(uri.getUser().getUserNo())))
					    && (this.status.longValue() == AirticketOrder.STATUS_107 || this.status
					        .longValue() == AirticketOrder.STATUS_116))

					{
						MyLabel ml = new MyLabel();
						ml.setHref(this.path
						    + "/airticket/listAirTicketOrder.do?thisAction="
						    + editThisAction + "&id=" + this.id);
						ml.setLabText("[编辑]");
						// ml.setEndText("<br/>");
						myLabels.add(ml);
						operate.setMyLabels(myLabels);
					}
				}
			}

			if (uri.hasRight("sb86"))
			{
				MyLabel ml2 = new MyLabel();
				StringBuffer sb = new StringBuffer();
				sb.append("onclick=\"");
				sb.append("return confirm('确定删除吗?');");
				sb.append("\"");
				ml2.setEvents(sb.toString());
				ml2
				    .setHref(this.path
				        + "/airticket/listAirTicketOrder.do?thisAction=deleteAirticketOrder&id="
				        + this.id + "&forwardPageFlag=Team");
				ml2.setLabText("[删除]");
				ml2.setEndText("<br/>");
				myLabels.add(ml2);
				operate.setMyLabels(myLabels);
			}
			else
			{
				if (((this.getOperate121() != null && uri != null
				    && uri.getUser() != null && this.getOperate121().equals(
				    uri.getUser().getUserNo())) || (this.getOperate122() != null
				    && uri != null && uri.getUser() != null && this.getOperate122()
				    .equals(uri.getUser().getUserNo())))
				    && (this.status.longValue() == AirticketOrder.STATUS_107 || this.status
				        .longValue() == AirticketOrder.STATUS_116))

				{
					MyLabel ml2 = new MyLabel();
					StringBuffer sb = new StringBuffer();
					sb.append("onclick=\"");
					sb.append("return confirm('确定删除吗?');");
					sb.append("\"");
					ml2.setEvents(sb.toString());
					ml2
					    .setHref(this.path
					        + "/airticket/listAirTicketOrder.do?thisAction=deleteAirticketOrder&id="
					        + this.id + "&forwardPageFlag=Team");
					ml2.setLabText("[删除]");
					ml2.setEndText("<br/>");
					myLabels.add(ml2);
					operate.setMyLabels(myLabels);
				}
			}
		}
	}

	/**
	 * 备注操作
	 */
	public void getRemarkLabel(List<MyLabel> myLabels)
	{
		if (uri.hasRight("sb30"))
		{
			MyLabel ml = new MyLabel();
			StringBuffer sb = new StringBuffer();
			sb.append("onclick=\"");
			sb.append("showDiv11(");
			sb.append("'" + this.id + "'");
			sb.append(")\"");
			ml.setEvents(sb.toString());
			if (this.memo != null)
			{
				ml.setLabText("<font color=\"red\">[备注]</font>");
			}
			else
			{
				ml.setLabText("[备注]");
			}
			// ml.setEndText("<br/>");
			myLabels.add(ml);

			commonOperate.setMyLabels(myLabels);
		}
	}

	/**
	 * 关联订单
	 */
	public void getRelateLabel(List<MyLabel> myLabels)
	{
		MyLabel ml = new MyLabel();
		ml.setHref(this.path
		    + "/airticket/listAirTicketOrder.do?thisAction=processing&id="
		    + this.id);
		ml.setLabText("[关联]");
		ml.setEndText("<br/>");
		myLabels.add(ml);
		commonOperate.setMyLabels(myLabels);
	}

	public String getCommonOperateText()
	{
		List<MyLabel> myLabels = new ArrayList<MyLabel>();
		if (this.tranType == null || this.ticketType == null || this.status == null)
		{
			return "";
		}
		else
		{
			getGenalManageLabel(myLabels);
			getRemarkLabel(myLabels);
			getRelateLabel(myLabels);
		}
		String commonOperateText = commonOperate.getOperateText();
		// System.out.println(commonOperateText);
		return commonOperateText;
	}

	// 没有关联操作链接
	public String getCommonOperateTextNoRelate()
	{
		List<MyLabel> myLabels = new ArrayList<MyLabel>();
		if (this.tranType == null || this.ticketType == null || this.status == null)
		{
			return "";
		}
		else
		{
			getGenalManageLabel(myLabels);
			getRemarkLabel(myLabels);
		}
		String commonOperateText = commonOperate.getOperateText();
		// System.out.println(commonOperateText);
		return commonOperateText;
	}

	public void getTeamCommonLabel(List<MyLabel> myLabels)
	{
		if (this.tranType != null || this.ticketType != null || this.status != null)
		{
			// MyLabel ml = new MyLabel();
			// ml.setHref(this.path
			// + "/airticket/listAirTicketOrder.do?thisAction=viewTeam&id="
			// + this.id);
			// ml.setLabText("[查看]");
			// ml.setEndText("<br/>");
			// myLabels.add(ml);
			// operate.setMyLabels(myLabels);
		}
	}

	public String getTradeOperate()
	{
		List<MyLabel> myLabels = new ArrayList<MyLabel>();
		if (this.tranType == null)
		{
			System.out.println("order id:" + this.id + "tranType is null");
			return "";
		}
		if (this.ticketType == null)
		{
			System.out.println("order id:" + this.id + "ticketType is null");
			return "";
		}
		if (this.status == null)
		{
			System.out.println("order id:" + this.id + "status is null");
			return "";
		}

		if (this.ticketType == 1)
		{
			// 散票订单管理
			getGeneralOperate(myLabels);
		}
		else if (this.ticketType == 2)
		{
			// 团队票订单管理
			getTeamManageLabel(myLabels);
			getTeamCommonLabel(myLabels);
		}
		String operateText = operate.getOperateText();
		// System.out.println(operateText);
		return operateText;
	}

	public String getGroupOrderNo()
	{
		if (this.orderGroup != null) { return this.orderGroup.getNo()
		    + this.getSubGroupMarkNo(); }
		return "";
	}

	public static String GeneralManagePath = "/airticket/listAirTicketOrder.do?thisAction=listAirTicketOrder";
	public static String TeamManagePath = "/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder";
	
	public AirticketOrder (long id,java.util.Set flights)
	{
		this.id=id;
		this.flights=flights;
	}

}
