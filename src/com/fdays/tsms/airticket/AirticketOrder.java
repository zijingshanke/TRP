package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.Date;

import com.fdays.tsms.airticket._entity._AirticketOrder;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class AirticketOrder extends _AirticketOrder {
	private boolean isSetStatementUser = false;
	private static final long serialVersionUID = 1L;
	private String pnr; // 预订记录编码
	private String forwardPage;
	private java.math.BigDecimal ticketPrice = new BigDecimal(0);// 票面价
	private java.math.BigDecimal documentPrice = new BigDecimal(0);
	private java.math.BigDecimal insurancePrice = new BigDecimal(0);
	private java.math.BigDecimal rebate = new BigDecimal(0);
	private long statement_type;
	private java.math.BigDecimal totalAmount = new BigDecimal(0);// 订单金额
	private Long platformId = Long.valueOf(0);
	private Long companyId = Long.valueOf(0);
	private Long accountId = Long.valueOf(0);
	private Long agentId = Long.valueOf(0);
	private TicketLog ticketLog = new TicketLog(); // 操作日志
	private Statement statement = new Statement();
	private String addType;// 添加类型(外部 or 内部 pnr)
	private int passengersCount = 0; // 乘客人数
	private long agentNo;// 客户ID
	private java.math.BigDecimal teamAddPrice = new BigDecimal(0);// 团队加价
	private java.math.BigDecimal agentAddPrice = new BigDecimal(0);// 客户加价
	private long flightId;// 航班Id
	private String drawer;// 出票人
	private String flightCode;// 航班号
	private String startPoint;// 出发地
	private String endPoint;// 目的地
	private String flightClass;// 舱位
	private String discount;// 折扣
	private long totlePernson;// 团队总人数
	private long teamAdultCount;// 成人数
	private long teamChildCount;// 儿童数
	private long teamBabyCount;// 婴儿数
	private long allPeople = 0;// 总人数
	private java.math.BigDecimal totlePrice = new BigDecimal(0);// 票面价
	private java.math.BigDecimal airportPrice = new BigDecimal(0);// 机建税
	private java.math.BigDecimal fuelPrice = new BigDecimal(0);// 燃油税

	private java.math.BigDecimal allTotlePrice = new BigDecimal(0);// 总票面价
	private java.math.BigDecimal allAirportPrice = new BigDecimal(0);// 总机建税
	private java.math.BigDecimal allFuelPrice = new BigDecimal(0);// 总燃油税

	private java.math.BigDecimal adultAirportPrice = new BigDecimal(0);// 机建税(成人)
	private java.math.BigDecimal adultFuelPrice = new BigDecimal(0);// 燃油税(成人)
	private java.math.BigDecimal childAirportPrice = new BigDecimal(0);// 机建税(儿童)
	private java.math.BigDecimal childfuelPrice = new BigDecimal(0);// 燃油税(儿童)
	private java.math.BigDecimal babyAirportPrice = new BigDecimal(0);// 机建税(婴儿)
	private java.math.BigDecimal babyfuelPrice = new BigDecimal(0);// 燃油税(婴儿)
	private java.math.BigDecimal handlingCharge = new BigDecimal(0);// 手续费

	// 修改图队利润
	private long statementId;// 结算ID
	private java.math.BigDecimal txt_ActualAmount = new BigDecimal(0);// 实收款
	private java.math.BigDecimal txt_UnsettledAccount = new BigDecimal(0);// 未结款
	private java.math.BigDecimal txt_Commission = new BigDecimal(0);// 现返佣金
	private java.math.BigDecimal txt_RakeOff = new BigDecimal(0);// 后返佣金
	private java.math.BigDecimal txt_TotalAmount = new BigDecimal(0);// 总金额

	// 团队利润(收入)--客户
	private long airticketOrderId;// 机票订单ID
	private java.math.BigDecimal txtAgentFeeTeams = new BigDecimal(0);// 应付出团代理费（现返）
	private java.math.BigDecimal txtUnAgentFeeTeams = new BigDecimal(0);// 应付出团代理费（未返）
	private String txtRemark;// 备注
	private java.math.BigDecimal txtSAmount = new BigDecimal(0);// 应收票款
	private java.math.BigDecimal txtTotalAmount = new BigDecimal(0);// 实收票款
	private java.math.BigDecimal txtAmountMore = new BigDecimal(0);// 多收票价
	private java.math.BigDecimal txtTaxMore = new BigDecimal(0);// 多收税
	private java.math.BigDecimal txtSourceTGQFee = new BigDecimal(0);// 收退票手续费
	private java.math.BigDecimal txtAgents = new BigDecimal(0);// 返点

	// 团队利润(支入)--航空公司
	private java.math.BigDecimal txtProfits = new BigDecimal(0);// 团毛利润
	private java.math.BigDecimal txtAgentFeeCarrier = new BigDecimal(0);// 月底返代理费
	private java.math.BigDecimal txtTUnAmount = new BigDecimal(0);// 应付票款
	private java.math.BigDecimal txtTAmount = new BigDecimal(0);// 实付票款
	private java.math.BigDecimal txtCharge = new BigDecimal(0);// 手续费
	private java.math.BigDecimal txtTargetTGQFee = new BigDecimal(0);// 付退票手续费
	private java.math.BigDecimal txtAgentT = new BigDecimal(0);// 返点
	private java.math.BigDecimal txtAgent = new BigDecimal(0);// 月底返点

	// 团队确认支付
	private long rptOrderItem_ctl01_selAccount;// 支付账号
	private java.sql.Timestamp txtConfirmTime;// 时间
	private String rptOrderItem_ctl01_txtOrderRemark;// 备注
	private String cyrs;// 用于显示承运人

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

	private String boardingTime;// 起飞时间
	// 订单状态
	public static final long STATUS_1 = 1;// 1：新订单
	public static final long STATUS_2 = 2;// 2：申请成功，等待支付
	public static final long STATUS_3 = 3;// 3：支付成功，等待出票
	public static final long STATUS_4 = 4;// 4：取消出票，等待退款，未支付
	public static final long STATUS_9 = 9;// 9：取消出票，等待退款，已支付
	public static final long STATUS_10 = 10;// 10：取消出票，等待退款
	
	public static final long STATUS_5 = 5;// 5：出票成功，交易结束
	public static final long STATUS_6 = 6;// 5：已退款，交易结束
	public static final long STATUS_7 = 7;// get lock 锁定
	public static final long STATUS_8 = 8;// release lock 解锁

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

	public static final long STATUS_80 = 80;// 交易结束
	public static final long STATUS_88 = 88;// 已废弃

	// ---------------------------------------------团队票定案状态从100开始
	public static final long STATUS_101 = 101;// 1：新订单,待统计利润
	public static final long STATUS_111 = 111;// 1：新订单,等待申请

	public static final long STATUS_102 = 102;// 2：申请成功，等待支付
	public static final long STATUS_103 = 103;// 3：支付成功，等待出票
	public static final long STATUS_104 = 104;// 4：取消出票，等待退款
	public static final long STATUS_105 = 105;// 5：出票成功，交易结束
	public static final long STATUS_106 = 106;// 6：已退款，交易结束

	public static final long STATUS_107 = 107;// 退票订单，等待审核
	public static final long STATUS_108 = 108;// 退票审核通过，等待退款
	public static final long STATUS_109 = 109;// 已经退款，交易结束
	public static final long STATUS_110 = 110;// 退票未通过，交易结束

	// ------------------------团队专用------------------------------------

	private String statusText;
	// 机票类型
	public static final long TICKETTYPE_1 = 1;// 1：普通
	public static final long TICKETTYPE_2 = 2;// 2：团队
	public static final long TICKETTYPE_3 = 3;// 3：b2c
	private String ticketTypeText;

	// 交易类型
//	public static final long TRANTYPE__2 = 1;// 1：买入(采购)
//	public static final long TRANTYPE__1 = 2;// 2：卖出(销售)
	public static final long TRANTYPE_3 = 3;// 3：退票
	public static final long TRANTYPE_4 = 4;// 4：废票
	public static final long TRANTYPE_5 = 5;// 5：改签
	
	public static final long TRANTYPE__2 = 2;// 1：买入(采购)
	public static final long TRANTYPE__1 = 1;// 2：卖出(销售)

	

	// 业务类型
//	public static final long BUSINESSTYPE__2 = 1;// 1：买入
//	public static final long BUSINESSTYPE__1 = 2;// 2：卖出

	public static final long BUSINESSTYPE__2 = 2;// 1：买入
	public static final long BUSINESSTYPE__1 = 1;// 2：卖出

	private String tranTypeText;
	private String businessTypeText;

	private LogUtil myLog;

	public AirticketOrder() {
	}

	public AirticketOrder(String group_mark_no) {
		this.groupMarkNo = group_mark_no;
	}

	public String getRebateText() {
		return "";
	}

	public String getBusinessTypeText() {
		if (this.getBusinessType() != null) {
			if (this.getBusinessType() == TRANTYPE__2) {
				businessTypeText = "买入";
			} else if (this.getBusinessType() == TRANTYPE__1) {
				businessTypeText = "卖出";
			} else {
				businessTypeText = "";
			}
		} else {
			businessTypeText = "";
		}
		return businessTypeText;
	}

	public String getTranTypeText() {
		if (this.getTranType() != null) {
			if (this.getTranType() == TRANTYPE__2) {
				tranTypeText = "买入";
			} else if (this.getTranType() == TRANTYPE__1) {
				tranTypeText = "卖出";
			} else if (this.getTranType() == TRANTYPE_3) {
				tranTypeText = "退票";
			} else if (this.getTranType() == TRANTYPE_4) {
				tranTypeText = "废票";
			} else if (this.getTranType() == TRANTYPE_5) {
				tranTypeText = "改签";
			} else {
				tranTypeText = "";
			}
		} else {
			tranTypeText = "";
		}
		return tranTypeText;
	}

	public String getTicketTypeText() {
		if (this.getTicketType() != null) {
			if (this.getTicketType() == TICKETTYPE_1) {
				ticketTypeText = "普通";
			} else if (this.getTicketType() == TICKETTYPE_2) {
				ticketTypeText = "团队";
			} else if (this.getTicketType() == TICKETTYPE_3) {
				ticketTypeText = "B2C";
			} else {
				ticketTypeText = "";
			}

		} else {
			ticketTypeText = "";
		}
		return ticketTypeText;
	}

	// 总人数
	public long getTotlePerson() {
		if (this.getAdultCount() != null && this.getChildCount() != null
				&& this.getBabyCount() != null) {
			totlePernson = this.getAdultCount() + this.getChildCount()
					+ this.getBabyCount();
		} else if (this.getAdultCount() != null && this.getChildCount() != null
				&& this.getBabyCount() == null) {
			totlePernson = this.getAdultCount() + this.getChildCount();
		} else if (this.getAdultCount() != null && this.getChildCount() == null
				&& this.getBabyCount() == null) {
			totlePernson = this.getAdultCount();
		} else if (this.getAdultCount() != null && this.getChildCount() == null
				&& this.getBabyCount() != null) {
			totlePernson = this.getAdultCount() + this.getBabyCount();
		} else if (this.getAdultCount() == null && this.getChildCount() != null
				&& this.getBabyCount() != null) {
			totlePernson = this.getChildCount() + this.getBabyCount();
		} else {
			totlePernson = 0;
		}
		return totlePernson;
	}

	public String getStatusText() {

		if (this.getStatus() != null) {
			if (this.getStatus() == STATUS_1) {
				statusText = "新订单";
			} else if (this.getStatus() == STATUS_2) {
				statusText = "申请成功，等待支付";
			} else if (this.getStatus() == STATUS_3) {
				statusText = "支付成功，等待出票";
			} else if (this.getStatus() == STATUS_4) {
				statusText = "取消出票，等待退款(未支付)";
			} else if (this.getStatus() == STATUS_9) {
				statusText = "取消出票，等待退款(已支付)";
			}else if (this.getStatus() == STATUS_10) {
				statusText = "取消出票，等待退款";
			}   else if (this.getStatus() == STATUS_5) {
				statusText = "出票成功，交易结束";
			} else if (this.getStatus() == STATUS_6) {
				statusText = "已退款，交易结束";
			} else if (this.getStatus() == STATUS_7) {
				statusText = "申请成功，等待支付 已锁定";
			} else if (this.getStatus() == STATUS_8) {
				statusText = "申请成功，等待支付 已解锁";
			} else if (this.getStatus() == STATUS_19
					|| this.getStatus() == STATUS_24) {
				statusText = "退票订单，等待审核";
			} else if (this.getStatus() == STATUS_20
					|| this.getStatus() == STATUS_25) {
				statusText = "退票订单，等待审核";
			} else if (this.getStatus() == STATUS_21) {
				statusText = "退票审核通过，等待退款";
			} else if (this.getStatus() == STATUS_22) {
				statusText = "已经退款，交易结束";
			} else if (this.getStatus() == STATUS_23) {
				statusText = "退票未通过，交易结束";
			} else if (this.getStatus() == STATUS_29
					|| this.getStatus() == STATUS_34) {
				statusText = "废票订单，等待审核";
			} else if (this.getStatus() == STATUS_30
					|| this.getStatus() == STATUS_35) {
				statusText = "废票订单，等待审核";
			} else if (this.getStatus() == STATUS_31) {
				statusText = "废票审核通过，等待退款";
			} else if (this.getStatus() == STATUS_32) {
				statusText = "废票已经退款，交易结束";
			} else if (this.getStatus() == STATUS_33) {
				statusText = "废票未通过，交易结束";
			} else if (this.getStatus() == STATUS_39
					|| this.getStatus() == STATUS_46) {
				statusText = "改签订单，等待审核";
			} else if (this.getStatus() == STATUS_40
					|| this.getStatus() == STATUS_47) {
				statusText = "改签订单，等待审核";
			} else if (this.getStatus() == STATUS_41) {
				statusText = "改签审核通过，等待支付";
			} else if (this.getStatus() == STATUS_42) {
				statusText = "改签审核通过，等待支付";
			} else if (this.getStatus() == STATUS_43) {
				statusText = "改签已支付，等待确认";
			} else if (this.getStatus() == STATUS_44) {
				statusText = "改签未通过，交易结束";
			} else if (this.getStatus() == STATUS_45) {
				statusText = "改签完成，交易结束";
			} else if (this.getStatus() == STATUS_80) {
				statusText = "交易结束";
			} else if (this.getStatus() == STATUS_88) {
				statusText = "已废弃";
			} else if (this.getStatus() == STATUS_101) {
				statusText = "新订单,待统计利润";
			} else if (this.getStatus() == STATUS_102) {
				statusText = "申请成功，等待支付";
			} else if (this.getStatus() == STATUS_103) {
				statusText = "支付成功，等待出票";
			} else if (this.getStatus() == STATUS_104) {
				statusText = "取消出票，等待退款";
			} else if (this.getStatus() == STATUS_105) {
				statusText = "出票成功，交易结束";
			} else if (this.getStatus() == STATUS_106) {
				statusText = "已退款，交易结束";
			} else if (this.getStatus() == STATUS_107) {
				statusText = "退票订单，等待审核";
			} else if (this.getStatus() == STATUS_108) {
				statusText = "退票审核通过，等待退款";
			} else if (this.getStatus() == STATUS_109) {
				statusText = "已经退款，交易结束";
			} else if (this.getStatus() == STATUS_110) {
				statusText = "退票未通过，交易结束";
			} else if (this.getStatus() == STATUS_111) {
				statusText = "新订单,等待申请";
			} else {
				statusText = "";
			}

		} else {
			statusText = "";
		}
		return statusText;
	}

	// 页面显示，操作人姓名
	public String getCurrentOperatorName() {
		if (this.currentOperator != null
				&& "".equals(this.currentOperator) == false) {
			return UserStore.getUserNameByNo(this.currentOperator);
		}
		return "";
	}

	// 页面显示，支付人
	public String getOrderPayerNo() {
		if (this.businessType != null) {
			if (this.businessType == TRANTYPE__2) {// 买入
				return getStatementUserNo();
			}
		}

		return "";
	}

	// 页面显示，支付人姓名
	public String getOrderPayerName() {
		if (this.businessType != null) {
			if (this.businessType == TRANTYPE__2) {// 买入
				return getStatementUserName();
			}
		}
		return "";
	}

	public String getStatementUserNo() {
		if (this.statement != null) {
			if (this.statement.getSysUser() != null) {
				return this.statement.getSysUser().getUserNo();
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getStatementUserName() {
		if (this.statement != null) {
			if (this.statement.getSysUser() != null) {
				return this.statement.getSysUser().getUserName();
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getEntryOperatorName() {
		if (this.entryOperator != null
				&& "".equals(this.entryOperator) == false) {
			return UserStore.getUserNameByNo(this.entryOperator);
		}
		return "";
	}

	public String getPayOperatorName() {
		if (this.payOperator != null && "".equals(this.payOperator) == false) {
			return UserStore.getUserNameByNo(this.payOperator);
		}
		return "";
	}

	public String getRetireTypeInfo() {
		if (this.tranType != null) {
			if (this.tranType == 3) {
				if (this.returnReason != null) {
					if (this.returnReason == "客规") {
						return "客规" + this.transRule;
					} else {
						return this.returnReason;
					}
				}
			} else if (this.tranType == 4) {
				return "废票";
			}
		}
		return "";
	}

	public String getOldOrderNo() {
		if (this.oldOrderNo != null) {
			return this.oldOrderNo;
		} else {
			return "";
		}
	}

	public String getEntryOrderDate() {
		String mydate = "";
		if (this.entryTime != null && "".equals(entryTime) == false) {
			Date tempDate = new Date(entryTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public void setDrawPnr(String drawPnr) {
		if (drawPnr != null) {
			drawPnr = drawPnr.trim();
		}
		this.drawPnr = drawPnr;
	}

	public void setSubPnr(String subPnr) {
		if (subPnr != null) {
			subPnr = subPnr.trim();
		}
		this.subPnr = subPnr;
	}

	public void setBigPnr(String bigPnr) {
		if (bigPnr != null) {
			bigPnr = bigPnr.trim();
		}
		this.bigPnr = bigPnr;
	}

	public void setTicketPrice(java.math.BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public void setFuelPrice(java.math.BigDecimal fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public void setAirportPrice(java.math.BigDecimal airportPrice) {
		this.airportPrice = airportPrice;
	}

	public java.math.BigDecimal getTotlePrice() {
		return totlePrice;
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

	public java.math.BigDecimal getDomentPrice() {
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

	public void setTotlePrice(java.math.BigDecimal totlePrice) {
		this.totlePrice = totlePrice;
	}

	public java.math.BigDecimal getAirportPrice() {
		return airportPrice;
	}

	public java.math.BigDecimal getFuelPrice() {
		return fuelPrice;
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

	public java.math.BigDecimal getTxtUnAgentFeeTeams() {
		return txtUnAgentFeeTeams;
	}

	public void setTxtUnAgentFeeTeams(java.math.BigDecimal txtUnAgentFeeTeams) {
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

	public void setTxt_UnsettledAccount(
			java.math.BigDecimal txt_UnsettledAccount) {
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
		if (this.totlePrice != null && this.getAllPeople() > 0) {
			allTotlePrice = this.ticketPrice.multiply(new java.math.BigDecimal(
					this.allPeople));
		}
		return allTotlePrice;
	}

	public void setAllTotlePrice(java.math.BigDecimal allTotlePrice) {
		this.allTotlePrice = allTotlePrice;
	}

	public java.math.BigDecimal getAllAirportPrice() {

		if (this.allAirportPrice != null && this.getAllPeople() > 0) {
			allAirportPrice = this.airportPrice
					.multiply(new java.math.BigDecimal(this.allPeople));
		}
		return allAirportPrice;

	}

	public void setAllAirportPrice(java.math.BigDecimal allAirportPrice) {
		this.allAirportPrice = allAirportPrice;
	}

	public java.math.BigDecimal getAllFuelPrice() {

		if (this.allFuelPrice != null && this.getAllPeople() > 0) {
			allFuelPrice = this.fuelPrice.multiply(new java.math.BigDecimal(
					this.getAllPeople()));
		}
		return allFuelPrice;
	}

	public void setAllFuelPrice(java.math.BigDecimal allFuelPrice) {
		this.allFuelPrice = allFuelPrice;
	}

	public long getAllPeople() {
		allPeople = 0;
		if (this.getAdultCount() != null) {
			allPeople += this.getAdultCount();
		}
		if (this.getChildCount() != null) {
			allPeople += this.getChildCount();
		}
		if (this.getBabyCount() != null) {
			allPeople += this.getBabyCount();
		}
		return allPeople;
	}

	public void setAllPeople(long allPeople) {
		this.allPeople = allPeople;
	}

	public String getAddType() {
		return addType;
	}

	public void setAddType(String addType) {
		this.addType = addType;
	}

	public long getTeamAdultCount() {
		return teamAdultCount;
	}

	public void setTeamAdultCount(long teamAdultCount) {
		this.teamAdultCount = teamAdultCount;
	}

	public long getTeamChildCount() {
		return teamChildCount;
	}

	public void setTeamChildCount(long teamChildCount) {
		this.teamChildCount = teamChildCount;
	}

	public long getTeamBabyCount() {
		return teamBabyCount;
	}

	public void setTeamBabyCount(long teamBabyCount) {
		this.teamBabyCount = teamBabyCount;
	}

	public java.math.BigDecimal getTxtAmountMore() {
		return txtAmountMore;
	}

	public void setTxtAmountMore(java.math.BigDecimal txtAmountMore) {
		this.txtAmountMore = txtAmountMore;
	}

	public java.math.BigDecimal getTxtTaxMore() {
		return txtTaxMore;
	}

	public void setTxtTaxMore(java.math.BigDecimal txtTaxMore) {
		this.txtTaxMore = txtTaxMore;
	}

	public java.math.BigDecimal getTxtSourceTGQFee() {
		return txtSourceTGQFee;
	}

	public void setTxtSourceTGQFee(java.math.BigDecimal txtSourceTGQFee) {
		this.txtSourceTGQFee = txtSourceTGQFee;
	}

	public java.math.BigDecimal getTxtTargetTGQFee() {
		return txtTargetTGQFee;
	}

	public void setTxtTargetTGQFee(java.math.BigDecimal txtTargetTGQFee) {
		this.txtTargetTGQFee = txtTargetTGQFee;
	}

	public java.math.BigDecimal getTxtAgents() {
		return txtAgents;
	}

	public void setTxtAgents(java.math.BigDecimal txtAgents) {
		this.txtAgents = txtAgents;
	}

	public java.math.BigDecimal getTxtAgentT() {
		return txtAgentT;
	}

	public void setTxtAgentT(java.math.BigDecimal txtAgentT) {
		this.txtAgentT = txtAgentT;
	}

	public java.math.BigDecimal getTxtAgent() {
		return txtAgent;
	}

	public void setTxtAgent(java.math.BigDecimal txtAgent) {
		this.txtAgent = txtAgent;
	}

	public java.math.BigDecimal getTxtCharge() {
		return txtCharge;
	}

	public void setTxtCharge(java.math.BigDecimal txtCharge) {
		this.txtCharge = txtCharge;
	}

	public String getTxtRemark() {
		return txtRemark;
	}

	public void setTxtRemark(String txtRemark) {
		this.txtRemark = txtRemark;
	}

	public int getPassengersCount() {
		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
			passengersCount = this.getPassengers().size();
		}
		return passengersCount;
	}

	public void setPassengersCount(int passengersCount) {
		this.passengersCount = passengersCount;
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

	public java.sql.Timestamp getTxtConfirmTime() {
		return txtConfirmTime;
	}

	public void setTxtConfirmTime(java.sql.Timestamp txtConfirmTime) {
		this.txtConfirmTime = txtConfirmTime;
	}

	public String getRptOrderItem_ctl01_txtOrderRemark() {
		return rptOrderItem_ctl01_txtOrderRemark;
	}

	public void setRptOrderItem_ctl01_txtOrderRemark(
			String rptOrderItem_ctl01_txtOrderRemark) {
		this.rptOrderItem_ctl01_txtOrderRemark = rptOrderItem_ctl01_txtOrderRemark;
	}

	public String getCyrs() {
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0) {
			for (Object obj : this.getFlights()) {
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getCyr()
						+ "," : flight.getCyr());
				num++;
			}
		}
		return cyrs = sb.toString();
	}

	public void setCyrs(String cyrs) {
		this.cyrs = cyrs;
	}

	public long getRptOrderItem_ctl01_selAccount() {
		return rptOrderItem_ctl01_selAccount;
	}

	public void setRptOrderItem_ctl01_selAccount(
			long rptOrderItem_ctl01_selAccount) {
		this.rptOrderItem_ctl01_selAccount = rptOrderItem_ctl01_selAccount;
	}

	public boolean isSetStatementUser() {
		return isSetStatementUser;
	}

	public void setSetStatementUser(boolean isSetStatementUser) {
		this.isSetStatementUser = isSetStatementUser;
	}

}
