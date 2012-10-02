package com.fdays.tsms.transaction;

import java.math.BigDecimal;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction._entity._Statement;

public class Statement extends _Statement {
	private static final long serialVersionUID = 1L;
	// 状态
	public static final long STATUS_0 = 0;// 未结算
	public static final long STATUS_1 = 1;// 已结算
	public static final long STATUS_2 = 2;// 部分结算
	public static final long STATUS_88 = 88;// 已废弃
	
	
	public static final long type_1 = 1;// 支出
	public static final long type_2 = 2;// 收入

	public static final long ORDERTYPE_1 = 1;// 机票
	public static final long ORDERTYPE_2 = 2;// 酒店
	protected PlatComAccount fromPCAccount;// 付款帐号
	protected PlatComAccount toPCAccount;// 收款帐号
	private PlatComAccount platComAccount;// 帐号(用于页面显示)
	private long statementId;// 结算表ID
	private long airticketOrderId;// 机票订单表ID
	private String groupMarkNo;// 机票订单号

	private LogUtil myLog;

	public void setStatus(Long status) {
		myLog = new AirticketLogUtil(false, false, Statement.class, "");

		if (totalAmount == null || "".equals(totalAmount)) {
			totalAmount = new BigDecimal(0);// 总金额
		}
		if (actualAmount == null || "".equals(actualAmount)) {
			actualAmount = new BigDecimal(0);// 实收
		}
		if (unsettledAccount == null || "".equals(unsettledAccount)) {
			unsettledAccount = new BigDecimal(0);// 未结
		}
		if (commission == null || "".equals(commission)) {
			commission = new BigDecimal(0);// 现返
		}
		if (rakeOff == null || "".equals(rakeOff)) {
			rakeOff = new BigDecimal(0);// 后返
		}

		// ---根据业务规则自动更新结算单状态
		if (actualAmount.compareTo(totalAmount) == 1
				|| actualAmount.compareTo(totalAmount) == 0) {
			status = STATUS_1;//
		} else if (actualAmount.compareTo(totalAmount) == -1) {
			if (actualAmount.compareTo(new BigDecimal(0)) == 1) {
				status = STATUS_2;//
			} else {
				status = STATUS_0;//
			}
		}

		//myLog.info("结算单" + statementNo + "状态为： 【0：未结算 1：已结算 2：部分结算】" + status);

		this.status = status;
	}

	public static void main(String[] args) {
		BigDecimal a = new BigDecimal(1);
		BigDecimal b = new BigDecimal(2);
		BigDecimal c = new BigDecimal(2);

		System.out.println(a.compareTo(b));
		System.out.println(b.compareTo(c));
		System.out.println(b.compareTo(a));

	}

	// 状态
	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATUS_0) {
				return "未结算";
			} else if (this.getStatus().intValue() == STATUS_1) {
				return "已结算";
			} else if (this.getStatus().intValue() == STATUS_2) {
				return "部分结算";
			} else if (this.getStatus().intValue() == STATUS_88) {
				return "已废弃";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	// 结算类型
	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType() == type_1) {
				return "支出";
			} else if (this.getType() == type_2) {
				return "收入";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	// 订单类型
	public String getOrderTypeInfo() {
		if (this.getOrderType() != null) {
			if (this.getOrderType() == ORDERTYPE_1) {
				return "机票";
			} else if (this.getOrderType() == ORDERTYPE_2) {
				return "酒店";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getGroupMarkNo() {
		return groupMarkNo;
	}

	public void setGroupMarkNo(String groupMarkNo) {
		this.groupMarkNo = groupMarkNo;
	}

	public long getStatementId() {
		return statementId;
	}

	public void setStatementId(long statementId) {
		this.statementId = statementId;
	}

	public com.fdays.tsms.user.SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(com.fdays.tsms.user.SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public PlatComAccount getFromPCAccount() {
		return fromPCAccount;
	}

	public void setFromPCAccount(PlatComAccount fromPCAccount) {
		this.fromPCAccount = fromPCAccount;
	}

	public PlatComAccount getToPCAccount() {
		return toPCAccount;
	}

	public void setToPCAccount(PlatComAccount toPCAccount) {
		this.toPCAccount = toPCAccount;
	}

	public long getAirticketOrderId() {
		return airticketOrderId;
	}

	public void setAirticketOrderId(long airticketOrderId) {
		this.airticketOrderId = airticketOrderId;
	}

	public PlatComAccount getPlatComAccount() {
       if(this.type!=null&&!"".equals(this.type)){ 
		if (this.type == this.type_1) {
			platComAccount = this.fromPCAccount;
		} else if (this.type == this.type_2) {
			platComAccount = this.toPCAccount;
		} else {
			platComAccount = null;
		}
       }
		return platComAccount;
	}

	public void setPlatComAccount(PlatComAccount platComAccount) {
		this.platComAccount = platComAccount;
	}

}
