package com.fdays.tsms.transaction;

import java.util.Date;
import com.fdays.tsms.transaction._entity._ReportCompareResult;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class ReportCompareResult extends _ReportCompareResult {
	private static final long serialVersionUID = 1L;

	public Long compareType = Long.valueOf(0);
	public Long tranType = Long.valueOf(0);

	public static final long TRANTYPE_1 = 1;// 销售
	public static final long TRANTYPE_2 = 2;// 采购
	public static final long TRANTYPE_13 = 13;// 供应退废
	public static final long TRANTYPE_14 = 14;// 采购退废
	public static final long TRANTYPE_15 = 15;// 供应退票
	public static final long TRANTYPE_16 = 16;// 采购退票
	public static final long TRANTYPE_17 = 17;// 供应废票
	public static final long TRANTYPE_18 = 18;// 采购废票

	public static final long STATES_1 = 1;// 有效
	public static final long STATES_0 = 0;// 无效

	public String getPlatformName() {
		String platformName = "";
		if (platformId != null && platformId > 0) {
			Platform platform = PlatComAccountStore.getPlatformById(platformId);
			platformName = platform.getName();
		}
		return platformName;
	}

	public String getAccountName() {
		String platformName = "";
		if (accountId != null && accountId > 0) {
			Platform platform = PlatComAccountStore.getPlatformById(accountId);
			platformName = platform.getName();
		}
		return platformName;
	}

	public String getPaytoolName() {
		String paymenttoolName = "";
		if (paymenttoolId != null && paymenttoolId > 0) {
			PaymentTool paymentTool = PlatComAccountStore
					.getPaymentToolById(paymenttoolId);
			paymenttoolName = paymentTool.getName();
		}
		return paymenttoolName;
	}

	public String getUserName() {
		String userName = "";
		if (userNo != null && "".equals(userNo) == false) {
			userName = UserStore.getUserNameByNo(userNo);
		}
		return userName;
	}

	public String getCompareTypeInfo() {
		return ReportRecode.getCompareTypeInfoByValue(this.compareType);
	}	

	public String getTranTypeInfo() {
		return getTranTypeInfoByValue(this.getTranType());
	}

	public static String getTranTypeInfoByValue(Long tranType) {
		if (tranType != null) {
			if (tranType == TRANTYPE_1) {
				return "供应";
			} else if (tranType == TRANTYPE_2) {
				return "采购";
			} else if (tranType == TRANTYPE_13) {
				return "供应退废";
			} else if (tranType == TRANTYPE_14) {
				return "采购退废";
			} else if (tranType == TRANTYPE_15) {
				return "供应退票";
			} else if (tranType == TRANTYPE_16) {
				return "采购退票";
			} else if (tranType == TRANTYPE_2) {
				return "供应废票";
			} else if (tranType == TRANTYPE_2) {
				return "采购废票";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public static String getTranTypesBySelectValue(long type){
		String businessType = "";
		String tranType = "";
		if (type == ReportCompareResult.TRANTYPE_1) {// 销售（供应）
			businessType = "1";
			tranType = "1";
		}
		if (type == ReportCompareResult.TRANTYPE_2) {// 采购
			businessType = "2";
			tranType = "2";
		}
		if (type == ReportCompareResult.TRANTYPE_13) {// 供应退废
			businessType = "1";
			tranType = "3,4";
		}
		if (type == ReportCompareResult.TRANTYPE_14) {// 采购退废
			businessType = "2";
			tranType = "3,4";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 供应退
			businessType = "1";
			tranType = "3";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 采购退
			businessType = "2";
			tranType = "3";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 供应废
			businessType = "1";
			tranType = "4";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 采购废
			businessType = "2";
			tranType = "4";
		} else {
			tranType = type + "";
		}
		return tranType;
	}
	
	public static String getBusinessTypesBySelectValue(long type){
		String businessType = "";
		if (type == ReportCompareResult.TRANTYPE_1) {// 销售（供应）
			businessType = "1";
		}
		if (type == ReportCompareResult.TRANTYPE_2) {// 采购
			businessType = "2";
		}
		if (type == ReportCompareResult.TRANTYPE_13) {// 供应退废
			businessType = "1";
		}
		if (type == ReportCompareResult.TRANTYPE_14) {// 采购退废
			businessType = "2";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 供应退
			businessType = "1";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 采购退
			businessType = "2";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 供应废
			businessType = "1";
		}
		if (type == ReportCompareResult.TRANTYPE_15) {// 采购废
			businessType = "2";
		}
		return businessType;
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

	public String getFormatLastDate() {
		String mydate = "";
		if (this.lastDate != null && "".equals(lastDate) == false) {
			Date tempDate = new Date(lastDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

	public Long getPaymenttoolId() {
		if (this.paymenttoolId == null) {
			return Long.valueOf(0);
		}
		return this.paymenttoolId;
	}

	public Long getAccountId() {
		if (this.accountId == null) {
			return Long.valueOf(0);
		}
		return this.accountId;
	}

	public Long getPlatformId() {
		if (this.platformId == null) {
			return Long.valueOf(0);
		}
		return this.platformId;
	}

	public Long getCompareType() {
		return compareType;
	}

	public void setCompareType(Long compareType) {
		this.compareType = compareType;
	}

	public Long getTranType() {
		return tranType;
	}

	public void setTranType(Long tranType) {
		this.tranType = tranType;
	}

}
