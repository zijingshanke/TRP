package com.fdays.tsms.transaction;

import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction._entity._Statement;
import com.neza.tool.DateUtil;

public class Statement extends _Statement {
	private static final long serialVersionUID = 1L;
	// 状态
	public static final long STATUS_0 = 0;// 未结算
	public static final long STATUS_1 = 1;// 已结算
	public static final long STATUS_2 = 2;// 等待收款
	public static final long STATUS_88 = 88;// 已废弃
    
	public static final long type_1 = 1;// 收入
	public static final long type_2 = 2;// 支出
	
	
	public static final long ORDERTYPE_1 = 1;// 机票
	public static final long ORDERTYPE_2 = 2;// 酒店

	private long statementId;// 结算表ID
	private long airticketOrderId;// 机票订单表ID
	private String groupMarkNo;// 机票订单号
  protected Account fromAccount;
  protected Account toAccount;
	private LogUtil myLog;


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
			if (this.getType() == type_2) {
				return "支出";
			} else if (this.getType() == type_1) {
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
	
	public String getFormatOptTime(){
		if (this.optTime!=null) {
			return DateUtil.getDateString(this.optTime,"yyyy-MM-dd HH:mm:ss");
		}
		return "";
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

	public long getAirticketOrderId() {
		return airticketOrderId;
	}

	public void setAirticketOrderId(long airticketOrderId) {
		this.airticketOrderId = airticketOrderId;
	}

	public Account getFromAccount()
  {
  	return fromAccount;
  }

	public void setFromAccount(Account fromAccount)
  {
  	this.fromAccount = fromAccount;
  }

	public Account getToAccount()
  {
  	return toAccount;
  }

	public void setToAccount(Account toAccount)
  {
  	this.toAccount = toAccount;
  }
}
