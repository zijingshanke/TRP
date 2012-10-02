package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fdays.tsms.airticket._entity._AirticketOrder;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.user.UserStore;
import com.fdays.tsms.right.UserRightInfo;
import com.neza.tool.DateUtil;

public class AirticketOrder extends _AirticketOrder {
	private boolean isSetStatementUser = false;
	private static final long serialVersionUID = 1L;
	private String pnr; // 预订记录编码
	private String forwardPage;
	private long statement_type;
	private Long platformId = Long.valueOf(0);
	private Long companyId = Long.valueOf(0);
	private Long accountId = Long.valueOf(0);
	private Long agentId = Long.valueOf(0);
	private TicketLog ticketLog = new TicketLog(); // 操作日志
	private String addType;// 添加类型(外部 or 内部 pnr)
	private int passengersCount = 0; // 乘客人数
	private long agentNo;// 客户ID
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
	public static final long TRANTYPE_3 = 3;// 3：退票
	public static final long TRANTYPE_4 = 4;// 4：废票
	public static final long TRANTYPE_5 = 5;// 5：改签

	public static final long TRANTYPE__2 = 2;// 买入(采购)
	public static final long TRANTYPE__1 = 1;// 卖出(销售)

	
	// 业务类型
	public static final long BUSINESSTYPE__2 = 2;// 买入
	public static final long BUSINESSTYPE__1 = 1;// 卖出
	
	//订单分组
	public static final long ORDER_GROUP_TYPE1=91;//买卖
	public static final long ORDER_GROUP_TYPE2=92;//改签
	public static final long ORDER_GROUP_TYPE3=93;//退废
	

	private String tranTypeText;
	private String businessTypeText;

	private LogUtil myLog;

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
	
	private Operate  operate=new Operate();
	private UserRightInfo uri=new UserRightInfo();
    private String path;
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

	public java.math.BigDecimal getTotalAmount() {
		if (this.totalAmount != null) {
			return this.totalAmount;
		} else {
			return new BigDecimal(0);
		}
	}

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
	private java.math.BigDecimal txtTax2 = new BigDecimal(0);// 机建燃油税

	// 团队利润(支入)--航空公司
	private java.math.BigDecimal txtProfits = new BigDecimal(0);// 团毛利润
	private java.math.BigDecimal txtAgentFeeCarrier = new BigDecimal(0);// 月底返代理费
	private java.math.BigDecimal txtTUnAmount = new BigDecimal(0);// 应付票款
	private java.math.BigDecimal txtTAmount = new BigDecimal(0);// 实付票款
	private java.math.BigDecimal txtCharge = new BigDecimal(0);// 手续费
	private java.math.BigDecimal txtTargetTGQFee = new BigDecimal(0);// 付退票手续费
	private java.math.BigDecimal txtAgentT = new BigDecimal(0);// 返点
	private java.math.BigDecimal txtAgent = new BigDecimal(0);// 月底返点
	private java.math.BigDecimal txtTax = new BigDecimal(0);// 机建燃油税

	// 团队确认支付
	private long selAccount;// 支付账号
	private java.math.BigDecimal txtOrderAmount = new BigDecimal(0);;// 订单金额
	private java.sql.Timestamp txtConfirmTime;// 时间
	private String txtOrderRemark;// 备注
	private String cyrs;// 用于显示承运人

	// //////////////团队确认退（票）款//////////////////
	// -------退票-----------
	private long airticketOrderFoId;
	private java.math.BigDecimal txtRefundIncomeretreatChargeFo = new BigDecimal(
			0);// 付退票手续费
	private java.sql.Timestamp txtConfirmTimeFo;// 确认退款时间
	private String txtCurrentOperatorFo;// 操作人
	private String txtOrderRemarkFo;// 备注

	// ----------卖出----------
	private long airticketOrderToId;
	private java.math.BigDecimal txtRefundIncomeretreatChargeTo = new BigDecimal(
			0);// 付退票手续费
	private java.sql.Timestamp txtConfirmTimeTo;// 确认退款时间
	private String txtCurrentOperatorTo;// 操作人
	private String txtOrderRemarkTo;// 备注
	// ////////////////////////////////////////////

	// 团队利润计算
	private java.math.BigDecimal txtAgentFeeTeamsInfo;// 应付出团代理费（现返）
	private java.math.BigDecimal txtSAmountInfo;// 应收票款（收票款）
	private java.math.BigDecimal txtTotalAmountInfo;// 实收票款（总金额）
	private java.math.BigDecimal txtProfitsInfo;// 团毛利润
	private java.math.BigDecimal txtAgentFeeCarrierInfo;// 月底返代理费
	private java.math.BigDecimal txtTUnAmountInfo;// 应付票款
	private java.math.BigDecimal txtTAmountInfo;// 实付票款
	private java.math.BigDecimal txtTProfitInfo;// 退票利润
	private java.math.BigDecimal txtTotalProfitInfo;// 净利合计

	public BigDecimal getTxtAgentFeeTeamsInfo() {// 应付出团代理费（现返）
		if (this.totalTicketPrice != null && this.txtAmountMore != null
				&& this.txtAgents != null)// 票面价
		{
			this.txtAgentFeeTeamsInfo = (this.totalTicketPrice
					.add(this.txtAmountMore)).multiply(this.txtAgents);// 应付出团代理费（现返）=(票面价+多收票价)*返点
		} else {
			this.txtAgentFeeTeamsInfo = BigDecimal.valueOf(0);
		}
		return txtAgentFeeTeamsInfo;
	}

	public BigDecimal gettxtSAmountInfo() {// 应收款
		if (this.totalTicketPrice != null && this.txtAmountMore != null
				&& this.txtTaxMore != null && this.txtSourceTGQFee != null)// 票面价
		{
			this.txtSAmountInfo = this.totalTicketPrice.add(this.txtAmountMore)
					.subtract(getTxtAgentFeeTeamsInfo()).add(this.txtTaxMore)
					.add(this.txtSourceTGQFee);// 应收票款=(票面价+多收票价)-现返+多收税+收退票手续费
		} else {
			this.txtSAmountInfo = BigDecimal.valueOf(0);
		}
		return txtSAmountInfo;
	}

	public BigDecimal getTxtTotalAmountInfo()// 实收票款（总金额）
	{
		if (this.totalTicketPrice != null && this.txtTax2 != null) {
			this.txtTotalAmountInfo = this.totalTicketPrice.add(this.txtTax2);// 实收票款：=应收票款
			// +机建燃油税
		} else {
			this.txtTotalAmountInfo = BigDecimal.valueOf(0);
		}
		return txtTotalAmountInfo;
	}

	public BigDecimal getTxtProfitsInfo() {// 团毛利润
		if (this.totalTicketPrice != null && this.txtAgentT != null
				&& this.txtCharge != null) {
			this.txtProfitsInfo = this.totalTicketPrice
					.multiply(this.txtAgentT).subtract(this.txtCharge);// 团毛利润
			// =票面价
			// *返点-手续费
		} else {
			this.txtProfitsInfo = BigDecimal.valueOf(0);
		}
		return txtProfitsInfo;

	}

	public BigDecimal getTxtAgentFeeCarrierInfo() {// 月底返代理费
		if (this.totalTicketPrice != null && this.txtAgent != null) {
			this.txtAgentFeeCarrierInfo = this.totalTicketPrice
					.multiply(this.txtAgent);// 月底返代理费=票面价*月底返点
		} else {
			this.txtAgentFeeCarrierInfo = BigDecimal.valueOf(0);
		}
		return txtAgentFeeCarrierInfo;
	}

	public BigDecimal getTxtTUnAmountInfo() {// 应付票款

		if (this.totalTicketPrice != null && this.txtTargetTGQFee != null) {
			this.txtTUnAmountInfo = this.totalTicketPrice.subtract(
					getTxtProfitsInfo()).add(this.txtTargetTGQFee);// 应付票款 =票面价
			// -团毛利润
			// +付退票手续费：
		} else {
			this.txtTUnAmountInfo = BigDecimal.valueOf(0);
		}
		return txtTUnAmountInfo;
	}

	public BigDecimal getTxtTAmountInfo() {// 实付票款：=应付票款 +机建燃油税
		if (this.totalAirportPrice != null && this.totalFuelPrice != null) {
			this.txtTAmountInfo = getTxtTUnAmountInfo().add(
					totalAirportPrice.add(totalFuelPrice));
		} else {
			this.txtTAmountInfo = BigDecimal.valueOf(0);
		}
		return txtTAmountInfo;
	}

	public BigDecimal getTxtTProfitInfo() {// // 退票利润= 付退票手续费-收退票手续费
		if (this.txtTargetTGQFee != null && this.txtSourceTGQFee != null) {
			this.txtTProfitInfo = this.txtTargetTGQFee
					.subtract(this.txtSourceTGQFee);
		} else {
			this.txtTProfitInfo = BigDecimal.valueOf(0);
		}
		return txtTProfitInfo;
	}

	public BigDecimal getTxtTotalProfitInfo() {// 净利合计=团毛利润+退票利润+多收票款+多收税款-应付出团代理费（现返)-应付出团代理费
		if (getTxtProfitsInfo() != null && this.txtAmountMore != null
				&& this.txtTaxMore != null && this.txtUnAgentFeeTeams != null
				&& this.txtAgentFeeTeams != null) {
			this.txtTotalProfitInfo = getTxtProfitsInfo().add(
					getTxtTProfitInfo()).add(this.txtAmountMore).add(
					this.txtTaxMore).subtract(this.txtAgentFeeTeams).subtract(
					this.txtUnAgentFeeTeams);
		} else {
			this.txtTotalProfitInfo = BigDecimal.valueOf(0);
		}
		return txtTotalProfitInfo;
	}

	// 总人数
	public long getTotlePerson() {
		long totalPerson = new Long(0);
		if (this.ticketType != null) {
			if (this.ticketType == TICKETTYPE_2) {// 团队
				if (this.getAdultCount() != null) {
					totalPerson = totalPerson + this.getAdultCount();
				}
				if (this.getChildCount() != null) {
					totalPerson = totalPerson + this.getChildCount();
				}
				if (this.getBabyCount() != null) {
					totalPerson = totalPerson + this.getBabyCount();
				}
			} else {
				if (this.passengers != null) {
					totalPerson = this.passengers.size();
				}
			}
		}
		return totalPerson;
	}

	public int getPassengersCount() {
		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
			passengersCount = this.getPassengers().size();
		}
		return passengersCount;
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
			} else if (this.getStatus() == STATUS_10) {
				statusText = "取消出票，等待退款";
			} else if (this.getStatus() == STATUS_5) {
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

	// 页面显示，当前操作人姓名
	public String getCurrentOperatorName() {
		if (this.currentOperator != null
				&& "".equals(this.currentOperator) == false) {
			return UserStore.getUserNameByNo(this.currentOperator);
		}
		return "";
	}

	// 操作人（录单人）
	public String getEntryOperatorName() {
		if (this.entryOperator != null
				&& "".equals(this.entryOperator) == false) {
			return UserStore.getUserNameByNo(this.entryOperator);
		}
		return "";
	}

	// 操作人（支付人/收款人）
	public String getPayOperatorName() {
		if (this.payOperator != null && "".equals(this.payOperator) == false) {
			return UserStore.getUserNameByNo(this.payOperator);
		} else {
			if (this.currentOperator != null
					&& "".equals(this.currentOperator) == false) {
				return UserStore.getUserNameByNo(this.currentOperator);
			}
		}
		return "";
	}

	public String getEntryOrderDate() {
		String mydate = "";
		if (this.entryTime != null && "".equals(entryTime) == false) {
			Date tempDate = new Date(entryTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getDrawTimeText() {
		String mydate = "";
		if (this.drawTime != null && "".equals(drawTime) == false) {
			Date tempDate = new Date(drawTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public String getRetireTypeInfo() {
		if (this.tranType != null) {
			if (this.tranType == 3) {
				if (this.returnReason != null) {
					if (this.transRule != null
							&& "客规".equals(this.returnReason.trim())) {
						return "客规" + this.transRule + "%";
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
		if (this.rebate != null) {
			return rebate;
		} else {
			return BigDecimal.ZERO;
		}
	}

	public java.math.BigDecimal getTicketPrice() {
		if (this.ticketPrice != null) {
			return ticketPrice;
		} else {
			return BigDecimal.ZERO;
		}
	}

	public long getStatement_type() {
		return statement_type;
	}

	public void setStatement_type(long statement_type) {
		this.statement_type = statement_type;
	}

	public java.math.BigDecimal getDomentPrice() {
		if (this.documentPrice != null) {
			return documentPrice;
		} else {
			return BigDecimal.ZERO;
		}
	}

	public java.math.BigDecimal getInsurancePrice() {
		if (this.insurancePrice != null) {
			return insurancePrice;
		} else {
			return BigDecimal.ZERO;
		}
	}

	public java.math.BigDecimal getAllTotlePrice() {
		if (this.totlePrice != null && getTotlePerson() > 0) {
			if (this.ticketPrice == null) {
				this.ticketPrice = BigDecimal.ZERO;
			}
			allTotlePrice = this.ticketPrice.multiply(new java.math.BigDecimal(
					getTotlePerson()));
		}
		return allTotlePrice;
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
		if (airportPrice == null) {
			return BigDecimal.ZERO;
		}
		return airportPrice;
	}

	public java.math.BigDecimal getFuelPrice() {
		if (fuelPrice == null) {
			return BigDecimal.ZERO;
		}
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
		if (handlingCharge == null) {
			return BigDecimal.ZERO;
		}
		return handlingCharge;
	}

	public void setHandlingCharge(java.math.BigDecimal handlingCharge) {
		this.handlingCharge = handlingCharge;
	}

	public java.math.BigDecimal getTxtAgentFeeTeams() {
		if (txtAgentFeeTeams == null) {
			return BigDecimal.ZERO;
		}
		return txtAgentFeeTeams;
	}

	public void setTxtAgentFeeTeams(java.math.BigDecimal txtAgentFeeTeams) {
		this.txtAgentFeeTeams = txtAgentFeeTeams;
	}

	public java.math.BigDecimal getTxtSAmount() {
		if (txtSAmount == null) {
			return BigDecimal.ZERO;
		}
		return txtSAmount;
	}

	public void setTxtSAmount(java.math.BigDecimal txtSAmount) {
		this.txtSAmount = txtSAmount;
	}

	public java.math.BigDecimal getTxtTotalAmount() {
		if (txtTotalAmount == null) {
			return BigDecimal.ZERO;
		}
		return txtTotalAmount;
	}

	public void setTxtTotalAmount(java.math.BigDecimal txtTotalAmount) {
		this.txtTotalAmount = txtTotalAmount;
	}

	public java.math.BigDecimal getTxtProfits() {
		if (txtProfits == null) {
			return BigDecimal.ZERO;
		}
		return txtProfits;
	}

	public void setTxtProfits(java.math.BigDecimal txtProfits) {
		this.txtProfits = txtProfits;
	}

	public java.math.BigDecimal getTxtAgentFeeCarrier() {
		if (txtAgentFeeCarrier == null) {
			return BigDecimal.ZERO;
		}
		return txtAgentFeeCarrier;
	}

	public void setTxtAgentFeeCarrier(java.math.BigDecimal txtAgentFeeCarrier) {
		this.txtAgentFeeCarrier = txtAgentFeeCarrier;
	}

	public java.math.BigDecimal getTxtTUnAmount() {
		if (txtTUnAmount == null) {
			return BigDecimal.ZERO;
		}
		return txtTUnAmount;
	}

	public void setTxtTUnAmount(java.math.BigDecimal txtTUnAmount) {
		this.txtTUnAmount = txtTUnAmount;
	}

	public java.math.BigDecimal getTxtTAmount() {
		if (txtTAmount == null) {
			return BigDecimal.ZERO;
		}
		return txtTAmount;
	}

	public void setTxtTAmount(java.math.BigDecimal txtTAmount) {
		this.txtTAmount = txtTAmount;
	}

	public java.math.BigDecimal getTxtUnAgentFeeTeams() {
		if (txtUnAgentFeeTeams == null) {
			return BigDecimal.ZERO;
		}
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
		if (txt_ActualAmount == null) {
			return BigDecimal.ZERO;
		}
		return txt_ActualAmount;
	}

	public void setTxt_ActualAmount(java.math.BigDecimal txt_ActualAmount) {
		this.txt_ActualAmount = txt_ActualAmount;
	}

	public java.math.BigDecimal getTxt_UnsettledAccount() {
		if (txt_UnsettledAccount == null) {
			return BigDecimal.ZERO;
		}
		return txt_UnsettledAccount;
	}

	public void setTxt_UnsettledAccount(
			java.math.BigDecimal txt_UnsettledAccount) {
		this.txt_UnsettledAccount = txt_UnsettledAccount;
	}

	public java.math.BigDecimal getTxt_Commission() {
		if (txt_Commission == null) {
			return BigDecimal.ZERO;
		}
		return txt_Commission;
	}

	public void setTxt_Commission(java.math.BigDecimal txt_Commission) {
		this.txt_Commission = txt_Commission;
	}

	public java.math.BigDecimal getTxt_RakeOff() {
		if (txt_RakeOff == null) {
			return BigDecimal.ZERO;
		}
		return txt_RakeOff;
	}

	public void setTxt_RakeOff(java.math.BigDecimal txt_RakeOff) {
		this.txt_RakeOff = txt_RakeOff;
	}

	public java.math.BigDecimal getTxt_TotalAmount() {
		if (txt_TotalAmount == null) {
			return BigDecimal.ZERO;
		}
		return txt_TotalAmount;
	}

	public void setTxt_TotalAmount(java.math.BigDecimal txt_TotalAmount) {
		this.txt_TotalAmount = txt_TotalAmount;
	}

	public void setAllTotlePrice(java.math.BigDecimal allTotlePrice) {
		this.allTotlePrice = allTotlePrice;
	}

	public void setAllAirportPrice(java.math.BigDecimal allAirportPrice) {
		this.allAirportPrice = allAirportPrice;
	}

	public java.math.BigDecimal getAllAirportPrice() {
		if (this.allAirportPrice != null && getTotlePerson() > 0) {
			allAirportPrice = getAirportPrice().multiply(
					new java.math.BigDecimal(getTotlePerson()));
		}
		return allAirportPrice;

	}

	public java.math.BigDecimal getAllFuelPrice() {
		if (this.allFuelPrice != null && getTotlePerson() > 0) {
			allFuelPrice = this.fuelPrice.multiply(new java.math.BigDecimal(
					getTotlePerson()));
		}
		return allFuelPrice;
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

	public String getCyrsInfo() {
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0) {
			for (Object obj : this.getFlights()) {
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight.getCyr()
						+ "<br/>" : flight.getCyr());
				num++;
			}
		}
		return cyrs = sb.toString();
	}

	public String getFlightsInfo() {
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getFlights() != null && this.getFlights().size() > 0) {
			for (Object obj : this.getFlights()) {
				Flight flight = (Flight) obj;
				sb.append(num < this.getFlights().size() - 1 ? flight
						.getHcText()
						+ "<br/>" : flight.getHcText());
				num++;
			}
		}
		return sb.toString();
	}

	public String getPassengersInfo() {
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
			for (Object obj : this.getPassengers()) {
				Passenger passenger = (Passenger) obj;
					sb.append(num < this.getPassengers().size() - 1 ? passenger
							.getName()
							+ "<br/>" : passenger.getName());
					num++;		
			}
		}
		return sb.toString();
	}

	public String getTicketsInfo() {
		StringBuffer sb = new StringBuffer();
		int num = 0;
		if (this.getPassengers() != null && this.getPassengers().size() > 0) {
			for (Object obj : this.getPassengers()) {
				Passenger passenger = (Passenger) obj;
					sb.append(num < this.getPassengers().size() - 1 ? passenger
							.getTicketNumber()
							+ "<br/>" : passenger.getTicketNumber());
					num++;		
			}
		}
		return sb.toString();
	}

	public void setAllFuelPrice(java.math.BigDecimal allFuelPrice) {
		this.allFuelPrice = allFuelPrice;
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
		if (txtTaxMore == null) {
			return BigDecimal.ZERO;
		}
		return txtTaxMore;
	}

	public void setTxtTaxMore(java.math.BigDecimal txtTaxMore) {
		this.txtTaxMore = txtTaxMore;
	}

	public java.math.BigDecimal getTxtSourceTGQFee() {
		if (txtSourceTGQFee == null) {
			return BigDecimal.ZERO;
		}
		return txtSourceTGQFee;
	}

	public void setTxtSourceTGQFee(java.math.BigDecimal txtSourceTGQFee) {
		this.txtSourceTGQFee = txtSourceTGQFee;
	}

	public java.math.BigDecimal getTxtTargetTGQFee() {
		if (txtTargetTGQFee == null) {
			return BigDecimal.ZERO;
		}
		return txtTargetTGQFee;
	}

	public void setTxtTargetTGQFee(java.math.BigDecimal txtTargetTGQFee) {
		this.txtTargetTGQFee = txtTargetTGQFee;
	}

	public java.math.BigDecimal getTxtAgents() {
		if (txtAgents == null) {
			return BigDecimal.ZERO;
		}
		return txtAgents;
	}

	public void setTxtAgents(java.math.BigDecimal txtAgents) {
		this.txtAgents = txtAgents;
	}

	public java.math.BigDecimal getTxtAgentT() {
		if (txtAgentT == null) {
			return BigDecimal.ZERO;
		}
		return txtAgentT;
	}

	public void setTxtAgentT(java.math.BigDecimal txtAgentT) {
		this.txtAgentT = txtAgentT;
	}

	public java.math.BigDecimal getTxtAgent() {
		if (txtAgent == null) {
			return BigDecimal.ZERO;
		}
		return txtAgent;
	}

	public void setTxtAgent(java.math.BigDecimal txtAgent) {
		this.txtAgent = txtAgent;
	}

	public java.math.BigDecimal getTxtCharge() {
		if (txtCharge == null) {
			return BigDecimal.ZERO;
		}
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

	public void setPassengersCount(int passengersCount) {
		this.passengersCount = passengersCount;
	}

	public java.math.BigDecimal getTeamAddPrice() {
		if (teamAddPrice == null) {
			return BigDecimal.ZERO;
		}
		return teamAddPrice;
	}

	public void setTeamAddPrice(java.math.BigDecimal teamAddPrice) {
		this.teamAddPrice = teamAddPrice;
	}

	public java.math.BigDecimal getAgentAddPrice() {
		if (agentAddPrice == null) {
			return BigDecimal.ZERO;
		}
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

	public void setCyrs(String cyrs) {
		this.cyrs = cyrs;
	}

	public boolean isSetStatementUser() {
		return isSetStatementUser;
	}

	public void setSetStatementUser(boolean isSetStatementUser) {
		this.isSetStatementUser = isSetStatementUser;
	}

	public java.math.BigDecimal getTxtTax2() {
		if (txtTax2 == null) {
			return BigDecimal.ZERO;
		}
		return txtTax2;
	}

	public void setTxtTax2(java.math.BigDecimal txtTax2) {
		this.txtTax2 = txtTax2;
	}

	public java.math.BigDecimal getTxtTax() {
		if (txtTax == null) {
			return BigDecimal.ZERO;
		}
		return txtTax;
	}

	public void setTxtTax(java.math.BigDecimal txtTax) {
		this.txtTax = txtTax;
	}

	public long getSelAccount() {
		return selAccount;
	}

	public void setSelAccount(long selAccount) {
		this.selAccount = selAccount;
	}

	public java.math.BigDecimal getTxtOrderAmount() {
		if (txtOrderAmount == null) {
			return BigDecimal.ZERO;
		}
		return txtOrderAmount;
	}

	public void setTxtOrderAmount(java.math.BigDecimal txtOrderAmount) {
		this.txtOrderAmount = txtOrderAmount;
	}

	public String getTxtOrderRemark() {
		return txtOrderRemark;
	}

	public void setTxtOrderRemark(String txtOrderRemark) {
		this.txtOrderRemark = txtOrderRemark;
	}

	public java.math.BigDecimal getTxtRefundIncomeretreatChargeFo() {
		if (txtRefundIncomeretreatChargeFo == null) {
			return BigDecimal.ZERO;
		}
		return txtRefundIncomeretreatChargeFo;
	}

	public void setTxtRefundIncomeretreatChargeFo(
			java.math.BigDecimal txtRefundIncomeretreatChargeFo) {
		this.txtRefundIncomeretreatChargeFo = txtRefundIncomeretreatChargeFo;
	}

	public java.sql.Timestamp getTxtConfirmTimeFo() {
		return txtConfirmTimeFo;
	}

	public void setTxtConfirmTimeFo(java.sql.Timestamp txtConfirmTimeFo) {
		this.txtConfirmTimeFo = txtConfirmTimeFo;
	}

	public String getTxtCurrentOperatorFo() {
		return txtCurrentOperatorFo;
	}

	public void setTxtCurrentOperatorFo(String txtCurrentOperatorFo) {
		this.txtCurrentOperatorFo = txtCurrentOperatorFo;
	}

	public String getTxtOrderRemarkFo() {
		return txtOrderRemarkFo;
	}

	public void setTxtOrderRemarkFo(String txtOrderRemarkFo) {
		this.txtOrderRemarkFo = txtOrderRemarkFo;
	}

	public java.math.BigDecimal getTxtRefundIncomeretreatChargeTo() {
		if (txtRefundIncomeretreatChargeTo == null) {
			return BigDecimal.ZERO;
		}
		return txtRefundIncomeretreatChargeTo;
	}

	public void setTxtRefundIncomeretreatChargeTo(
			java.math.BigDecimal txtRefundIncomeretreatChargeTo) {
		this.txtRefundIncomeretreatChargeTo = txtRefundIncomeretreatChargeTo;
	}

	public java.sql.Timestamp getTxtConfirmTimeTo() {
		return txtConfirmTimeTo;
	}

	public void setTxtConfirmTimeTo(java.sql.Timestamp txtConfirmTimeTo) {
		this.txtConfirmTimeTo = txtConfirmTimeTo;
	}

	public String getTxtCurrentOperatorTo() {
		return txtCurrentOperatorTo;
	}

	public void setTxtCurrentOperatorTo(String txtCurrentOperatorTo) {
		this.txtCurrentOperatorTo = txtCurrentOperatorTo;
	}

	public String getTxtOrderRemarkTo() {
		return txtOrderRemarkTo;
	}

	public void setTxtOrderRemarkTo(String txtOrderRemarkTo) {
		this.txtOrderRemarkTo = txtOrderRemarkTo;
	}

	public long getAirticketOrderFoId() {
		return airticketOrderFoId;
	}

	public void setAirticketOrderFoId(long airticketOrderFoId) {
		this.airticketOrderFoId = airticketOrderFoId;
	}

	public long getAirticketOrderToId() {
		return airticketOrderToId;
	}

	public void setAirticketOrderToId(long airticketOrderToId) {
		this.airticketOrderToId = airticketOrderToId;
	}

	public Operate getOperate() {
		return operate;
	}

	public void setOperate(Operate operate) {
		this.operate = operate;
	}
	public UserRightInfo getUri() {
		return uri;
	}

	public void setUri(UserRightInfo uri) {
		this.uri = uri;
	}

    
	public String getTradeOperate(){
		
		StringBuffer  OperateTemp=new StringBuffer();
		 List<MyLabel> myLabels=new ArrayList<MyLabel>();
	/////////////////////////////////////////////// 散票 ///////////////////////////////////////////////////////////
		 ///待处理新订单
		if(this.tranType==1 &&this.status==1){
		   if(uri.hasRight("sb43")){
			   MyLabel ml=new MyLabel();
			   StringBuffer sb=new StringBuffer();
			   sb.append("onclick=\"");
			   sb.append("showDiv8(");
			   sb.append("'"+this.id+"',");
			   sb.append("'"+this.subPnr+"',");
			   sb.append("'"+this.STATUS_4+"'");
			   sb.append(")\"");
			   ml.setEvents(sb.toString());
			   ml.setLabText("[取消出票]");
			   ml.setEndText("<br/> <td>");
			   myLabels.add(ml);
			   
			  
		   }
		   if(uri.hasRight("sb17")){
			   
			   MyLabel ml2=new MyLabel();
			   StringBuffer sb=new StringBuffer();
			   sb.append("onclick=\"");
			   sb.append("showDiv9(");
			   sb.append("'"+this.id+"',");
			   sb.append("'"+this.subPnr+"',");
			   sb.append("'"+this.airOrderNo+"',");
			   sb.append("'"+this.totalAmount+"',");
			   sb.append("'"+this.rebate+"'");
			   sb.append(")\"");
			   ml2.setEvents(sb.toString());
			   ml2.setLabText("[申请支付]");
			   myLabels.add(ml2);
		   }
		   
      
		operate.setMyLabels(myLabels); 
		}
		
		///取消出票
		
		if(this.tranType==1 &&this.status==3){
			 if(uri.hasRight("sb43")){
				 
				 MyLabel ml=new MyLabel();
				   StringBuffer sb=new StringBuffer();
				   sb.append("onclick=\"");
				   sb.append("showDiv8(");
				   sb.append("'"+this.id+"',");
				   sb.append("'"+this.subPnr+"',");
				   sb.append("'"+this.STATUS_9+"'");
				   sb.append(")\"");
				   ml.setEvents(sb.toString());
				   ml.setLabText("[取消出票]");
				   ml.setEndText("<br/> <td>");
				   myLabels.add(ml);
			 }
			 if(uri.hasRight("sb30")){
				
				 MyLabel ml2=new MyLabel();
				   StringBuffer sb=new StringBuffer();
				   sb.append("onclick=\"");
				   sb.append("showDiv11(");
				   sb.append("'"+this.id+"'");
				   sb.append(")\"");
				   ml2.setEvents(sb.toString());
				   if(this.memo!=null){
				   ml2.setLabText("<font color=\"red\">[备注]</font>");
				   }else{
					   ml2.setLabText("[备注]"); 
				   }
				   ml2.setEndText("<br/>");
				   myLabels.add(ml2);
			}
				operate.setMyLabels(myLabels); 
		}
        
		
		///锁定
		if(this.tranType==2 &&this.status==2||this.status==8){
			
			 if(uri.hasRight("sb43")){
						 
				   MyLabel ml=new MyLabel();
				   StringBuffer sb=new StringBuffer();
				   sb.append("onclick=\"");
				   sb.append("showDiv8(");
				   sb.append("'"+this.id+"',");
				   sb.append("'"+this.subPnr+"',");
				   sb.append("'"+this.STATUS_9+"'");
				   sb.append(")\"");
				   ml.setEvents(sb.toString());
				   ml.setLabText("[取消出票]");
				   ml.setEndText("<br/> <td>");
				   myLabels.add(ml);
					 }
			 if(uri.hasRight("sb44")){
				 MyLabel ml2=new MyLabel();
				   ml2.setHref(this.path+"/airticket/airticketOrder.do?thisAction=lockupOrder&id="+this.id);
				   ml2.setLabText(" [锁定]");
				   myLabels.add(ml2);
				 
			 }
			 operate.setMyLabels(myLabels);
		}
	
		//解锁	
		if(this.tranType==2 &&this.status==7){
			 if(uri.hasRight("sb43")){
				 
				   MyLabel ml=new MyLabel();
				   StringBuffer sb=new StringBuffer();
				   sb.append("onclick=\"");
				   sb.append("showDiv8(");
				   sb.append("'"+this.id+"',");
				   sb.append("'"+this.subPnr+"',");
				   sb.append("'"+this.STATUS_4+"'");
				   sb.append(")\"");
				   ml.setEvents(sb.toString());
				   ml.setLabText("[取消出票]");
				   ml.setEndText("<br/> <td>");
				   myLabels.add(ml);
			 }
			 if(uri.hasRight("sb45")){
				 if(uri.getUser().getUserNo().equals(this.currentOperator)){
					   MyLabel ml2=new MyLabel();
					   ml2.setHref(this.path+"/airticket/airticketOrder.do?thisAction=unlockSelfOrder&id="+this.id);
					   ml2.setLabText(" [解锁]");
					   ml2.setEndText("<br/><td>");
					   myLabels.add(ml2);
					 
				 }else{
					 
					   MyLabel ml2=new MyLabel();
					   ml2.setHref(this.path+"/airticket/airticketOrder.do?thisAction=unlockSelfOrder&id="+this.id);
					   ml2.setLabText(" [解锁他人订单]");
					   ml2.setEndText("<br/><td>");
					   myLabels.add(ml2);
				 }
				 
				 if(uri.hasRight("sb46")){
					 
					  MyLabel ml3=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.subPnr+"',");
					   sb.append("'"+this.airOrderNo+"',");
					   sb.append("'"+this.totalAmount+"',");
					   sb.append("'"+this.rebate+"'");
					   sb.append(")\"");
					   ml3.setEvents(sb.toString());
					   ml3.setLabText("[确认支付]");
					   ml3.setEndText("</td>");
					   myLabels.add(ml3);
				 }
				 
			 }
			 operate.setMyLabels(myLabels);
		}
		
		
		//取消出票 确认退款 status= 4
		if(this.tranType==2 &&this.status==4){
			if(uri.hasRight("sb52")){
				  MyLabel ml=new MyLabel();
				   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=6&id="+this.id);
				   ml.setLabText(" [确认退款]");
				   ml.setEndText("<br/>");
				   myLabels.add(ml);
			}
			if(uri.hasRight("sb17")){
				
				  MyLabel ml2=new MyLabel();
				   StringBuffer sb=new StringBuffer();
				   sb.append("onclick=\"");
				   sb.append("showDiv9(");
				   sb.append("'"+this.id+"',");
				   sb.append("'"+this.subPnr+"',");
				   sb.append("'"+this.airOrderNo+"',");
				   sb.append("'"+this.totalAmount+"',");
				   sb.append("'"+this.rebate+"'");
				   sb.append(")\"");
				   ml2.setEvents(sb.toString());
				   ml2.setLabText("[重新申请支付]");
				   ml2.setEndText("<br/> <td>");
				   myLabels.add(ml2);
			}
			 operate.setMyLabels(myLabels);
		}
		
		//取消出票 确认退款 status= 9 
		if(this.status==9||this.status==10){
			if(uri.hasRight("sb52")){
				  MyLabel ml=new MyLabel();
				   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=agreeCancelRefund&id="+this.id);
				   ml.setLabText(" [确认退款]");
				   ml.setEndText("<br/>");
				   myLabels.add(ml);
			}
			 operate.setMyLabels(myLabels);
		}
		
		
      //出票
		if(this.tranType==2 &&this.status==3){
			
			   MyLabel ml=new MyLabel();
			   StringBuffer sb=new StringBuffer();
			   sb.append("onclick=\"");
			   sb.append("showDiv2(");
			   sb.append("'"+this.id+"',");
			   sb.append("'"+this.subPnr+"',");
			   sb.append("'"+this.groupMarkNo+"'");
			   sb.append(")\"");
			   ml.setEvents(sb.toString());
			   ml.setLabText("[出票]");
			   ml.setEndText("<br/>");
			   myLabels.add(ml);
			   if(uri.hasRight("sb43")){
				   
				   MyLabel ml2=new MyLabel();
				   StringBuffer sb1=new StringBuffer();
				   sb1.append("onclick=\"");
				   sb1.append("showDiv8(");
				   sb1.append("'"+this.id+"',");
				   sb1.append("'"+this.subPnr+"',");
				   sb1.append("'"+this.STATUS_9+"'");
				   sb1.append(")\"");
				   ml2.setEvents(sb1.toString());
				   ml2.setLabText("[取消出票]");
				   myLabels.add(ml2);
		  }
			   operate.setMyLabels(myLabels);
	  }
				
	
			// 确认退款 status= 9 
			if(this.tranType==1 &&this.status==4){
				if(uri.hasRight("sb52")){
					  MyLabel ml=new MyLabel();
					   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=agreeCancelRefund&id="+this.id);
					   ml.setLabText(" [确认退款]");
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				}
				 operate.setMyLabels(myLabels);
			}		
	
			//完成出票 备注
			if(this.status==5){
				
				 if(uri.hasRight("sb30")){
						
					 MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml2.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml2.setLabText("[备注]"); 
					   }
					   ml2.setEndText("<br/>");
					   myLabels.add(ml2);
				}
				 operate.setMyLabels(myLabels);
			}
			
		
			
	//		取消出票 已退款，交易结束 2
			if(this.tranType==2 && this.status==6){
				
				 if(uri.hasRight("sb33")){
						
					 MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml2.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml2.setLabText("[备注]"); 
					   }
					   ml2.setEndText("<br/>");
					   myLabels.add(ml2);
				}
				 operate.setMyLabels(myLabels);
			}
			
			//		取消出票 已退款，交易结束 1
			if(this.tranType==1 && this.status==6){
				
				 if(uri.hasRight("sb17")){
						
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml2.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml2.setLabText("[备注]"); 
					   }
					   ml2.setEndText("<br/>");
					   myLabels.add(ml2);
				}
				 operate.setMyLabels(myLabels);
			}
			
		
		/////////////////////////////////////////////// 退费 ///////////////////////////////////////////////////////////
			
		//	<!-- 退票 通过申请-->
			if(this.tranType==3 && this.status==19){
				 if(uri.hasRight("sb56")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv3(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
			//	-- 退票 通过申请-- 7
			if(this.tranType==3 && this.status==20){
				 if(uri.hasRight("sb56")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv7(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
			
	      
			
			//<!-- 退票外部  通过申请-->
			if(this.tranType==3 && this.status==24){
				 if(uri.hasRight("sb55")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv12(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
           //通过申请 外部 
			if(this.tranType==3 && this.status==25){
		
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv13(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
      //确认退款
			if(this.tranType==3 && this.status==21 && this.businessType==1){
				 if(uri.hasRight("sb52")){
					
					   MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv4(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   ml.setLabText("[确认退款]");
					   myLabels.add(ml);
					
				}
				 operate.setMyLabels(myLabels);
			}
			
				
		//	<!-- 废票- 通过申请  3->
			
			if(this.tranType==4 && this.status==29){
				 if(uri.hasRight("sb56")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv3(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
	//	<!-- 废票- 通过申请  7->
			
			if(this.tranType==4 && this.status==30){
				 if(uri.hasRight("sb56")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv7(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
		      //确认退款
			if(this.tranType==4 && this.status==31 && this.businessType==1){
				 if(uri.hasRight("sb52")){
					
					   MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv4(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   ml.setLabText("[确认退款]");
					   myLabels.add(ml);
					
				}
				 operate.setMyLabels(myLabels);
			}
			

			
			
		///	<!-- 废票- 外部 -->
			
			if(this.tranType==4 && this.status==34){
				 if(uri.hasRight("sb55")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv12(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			
			if(this.tranType==4 && this.status==35){
				 if(uri.hasRight("sb55")){
					 MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml.setLabText("[备注]"); 
					   }
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
				 }
				 if(uri.hasRight("sb51")){
					 
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv13(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				 }
				 operate.setMyLabels(myLabels);
			}
			

		//	<!-- 确认收款 -->
			
			
			
		      //确认退款 退票
			if(this.tranType==3 && this.status==21 && this.businessType==2){
				 if(uri.hasRight("sb52")){
					
					   MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv4(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   ml.setLabText("[确认收款]");
					   myLabels.add(ml);
					
				}
				 operate.setMyLabels(myLabels);
			}
		    //确认退款 废票
			if(this.tranType==4 && this.status==31 && this.businessType==2){
				 if(uri.hasRight("sb52")){
					
					   MyLabel ml=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv4(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml.setEvents(sb.toString());
					   ml.setLabText("[确认收款]");
					   myLabels.add(ml);
					
				}
				 operate.setMyLabels(myLabels);
			}
			//		退废完成，交易结束
			if(this.status==22||this.status==32 ||this.status==23||this.status==33){
				
				 if(uri.hasRight("sb17")){
						
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv11(");
					   sb.append("'"+this.id+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   if(this.memo!=null){
					   ml2.setLabText("<font color=\"red\">[备注]</font>");
					   }else{
						   ml2.setLabText("[备注]"); 
					   }
					   ml2.setEndText("<br/>");
					   myLabels.add(ml2);
				}
				 operate.setMyLabels(myLabels);
			}
			
	/////////////////////////////////////////////// 改签 ///////////////////////////////////////////////////////////
			
			
			//  <!-- 申请改签 -->
			
			if(this.businessType==1 && this.status==39){
				
				if(uri.hasRight("sb62")){
					
					   MyLabel ml=new MyLabel();
					   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id="+this.id);
					   ml.setLabText(" [拒绝申请]");
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
					
				}
				if(uri.hasRight("sb62")){
					
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv5(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				}
				 operate.setMyLabels(myLabels);
			}
		
			
			
					
		//	 <!-- 申请改签 外部-->		
			
			if(this.businessType==1 && this.status==46){
				
				if(uri.hasRight("sb62")){
					
					   MyLabel ml=new MyLabel();
					   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=44&id="+this.id);
					   ml.setLabText(" [拒绝申请]");
					   ml.setEndText("<br/>");
					   myLabels.add(ml);
					
				}
				if(uri.hasRight("sb61")){
					
					   MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv14(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"',");
					   sb.append("'"+this.groupMarkNo+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[通过申请]");
					   ml2.setEndText("</td>");
					   myLabels.add(ml2);
				}
				 operate.setMyLabels(myLabels);
			}
			
          //通过申请
				if(this.businessType==1 && this.status==40){
							
							if(uri.hasRight("sb61")){
								
								   MyLabel ml=new MyLabel();
								   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=updateOrderStatus&status=41&id="+this.id);
								   ml.setLabText(" [通过申请]");
								   ml.setEndText("<br/>");
								   myLabels.add(ml);
								
							}
							 operate.setMyLabels(myLabels);	
				}		
				
				//收款
				if(this.businessType==1 && this.status==41){
					MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv6(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[收款]");
					   myLabels.add(ml2);
					   operate.setMyLabels(myLabels);
				}
			//确认收款
				if(this.businessType==1 && this.status==43){
					
					if(uri.hasRight("sb61")){
						
						   MyLabel ml=new MyLabel();
						   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=finishUmbuchenOrder&id="+this.id);
						   ml.setLabText(" [确认收款]");
						   ml.setEndText("<br/>");
						   myLabels.add(ml);
					}
					 operate.setMyLabels(myLabels);
		      }	
			
					
			//	<!-- 申请改签 -->
				
				
				//收款
				if(this.businessType==2 && this.status==42){
					MyLabel ml2=new MyLabel();
					   StringBuffer sb=new StringBuffer();
					   sb.append("onclick=\"");
					   sb.append("showDiv6(");
					   sb.append("'"+this.id+"',");
					   sb.append("'"+this.tranType+"'");
					   sb.append(")\"");
					   ml2.setEvents(sb.toString());
					   ml2.setLabText("[付款]");
					   myLabels.add(ml2);
					   operate.setMyLabels(myLabels);
				}
			//确认收款
				if(this.businessType==2 && this.status==43){
					
					if(uri.hasRight("sb61")){
						
						   MyLabel ml=new MyLabel();
						   ml.setHref(this.path+"/airticket/airticketOrder.do?thisAction=finishUmbuchenOrder&id="+this.id);
						   ml.setLabText(" [确认收款]");
						   ml.setEndText("<br/>");
						   myLabels.add(ml);
					}
					 operate.setMyLabels(myLabels);
		      }	
				
				
			//完成 改签 
				if(this.status==44||this.status==45){
					
					 if(uri.hasRight("sb30")){
							
						   MyLabel ml2=new MyLabel();
						   StringBuffer sb=new StringBuffer();
						   sb.append("onclick=\"");
						   sb.append("showDiv11(");
						   sb.append("'"+this.id+"'");
						   sb.append(")\"");
						   ml2.setEvents(sb.toString());
						   if(this.memo!=null){
						   ml2.setLabText("<font color=\"red\">[备注]</font>");
						   }else{
							   ml2.setLabText("[备注]"); 
						   }
						   ml2.setEndText("<br/>");
						   myLabels.add(ml2);
					}
					 operate.setMyLabels(myLabels);
				}
				
				if(this.businessType==1 && this.status==41){
					
					 if(uri.hasRight("sb30")){
							
						   MyLabel ml2=new MyLabel();
						   StringBuffer sb=new StringBuffer();
						   sb.append("onclick=\"");
						   sb.append("showDiv11(");
						   sb.append("'"+this.id+"'");
						   sb.append(")\"");
						   ml2.setEvents(sb.toString());
						   if(this.memo!=null){
						   ml2.setLabText("<font color=\"red\">[备注]</font>");
						   }else{
							   ml2.setLabText("[备注]"); 
						   }
						   ml2.setEndText("<br/>");
						   myLabels.add(ml2);
					}
					 operate.setMyLabels(myLabels);
				}
		
			
		System.out.println("operate.getOperateText()"+operate.getOperateText());
		return operate.getOperateText();
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
		
}
