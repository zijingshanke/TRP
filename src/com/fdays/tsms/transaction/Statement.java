package com.fdays.tsms.transaction;

import com.fdays.tsms.transaction._entity._Statement;

public class Statement extends _Statement {
	private static final long serialVersionUID = 1L;
	// 状态
	public static final long STATUS_0 = 0;// 未结算
	public static final long STATUS_1 = 1;// 已结算
	public static final long STATUS_2 = 2;// 部分结算
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

	public void setStatus(Long status) {
		//---根据金额、实收款、未结款，自动更新结算单状态
		
		
		
		this.status = status;
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

		if (this.type == this.type_1) {
			platComAccount = this.fromPCAccount;
		} else if (this.type == this.type_2) {
			platComAccount = this.toPCAccount;
		} else {
			platComAccount = null;
		}
		return platComAccount;
	}

	public void setPlatComAccount(PlatComAccount platComAccount) {
		this.platComAccount = platComAccount;
	}

}
