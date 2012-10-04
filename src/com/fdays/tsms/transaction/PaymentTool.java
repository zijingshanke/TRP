package com.fdays.tsms.transaction;

import com.fdays.tsms.transaction._entity._PaymentTool;
import com.neza.utility.PingYin;

public class PaymentTool extends _PaymentTool {
	private static final long serialVersionUID = 1L;

	// 类型
	public static final long type_1 = 1;// 银行
	public static final long type_2 = 2;// 支付平台
	public static final long type_3 = 3;// 现金

	// 状态
	public static final long STATES_0 = 0;// 有效
	public static final long STATES_1 = 1;// 无效
	private String charSet="GBK";
	
	private String idTranType ;			//记录ID和交易类型
	private long platformIReportIndexId;
	
	public String getIdTranType() {
		return idTranType;
	}

	public void setIdTranType(String idTranType) {
		this.idTranType = idTranType;
	}
	
	

	public long getPlatformIReportIndexId() {
		return platformIReportIndexId;
	}

	public void setPlatformIReportIndexId(long platformIReportIndexId) {
		this.platformIReportIndexId = platformIReportIndexId;
	}

	public String getShowName() {
		if (this.name != null && "".equals(this.name) == false) {
			String myFirstLetter = PingYin.getFirstLetter(this.name,charSet);
			if (myFirstLetter != null && myFirstLetter.length() > 1) {
				myFirstLetter = myFirstLetter.substring(0, 1);
				return myFirstLetter + "-" + this.name;
			}
		}
		return this.name;
	}

	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType().intValue() == type_1) {
				return "银行";
			} else if (this.getType().intValue() == type_2) {
				return "支付平台";
			} else if (this.getType().intValue() == type_3) {
				return "现金";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	// 状态
	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATES_0) {
				return "有效";
			} else if (this.getStatus().intValue() == STATES_1) {
				return "无效";
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
}
