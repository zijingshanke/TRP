package com.fdays.tsms.transaction;

import java.util.Date;
import com.fdays.tsms.transaction._entity._ReportCompareResult;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class ReportCompareResult extends _ReportCompareResult{
  	private static final long serialVersionUID = 1L;  	

  	
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
			PaymentTool paymentTool = PlatComAccountStore.getPaymentToolById(paymenttoolId);
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
//		if (this.getCompareType() != null) {
//			if (this.getCompareType() == COMPARETYPE_1) {
//				return "交易平台";
//			} else if (this.getCompareType() == COMPARETYPE_2) {
//				return "BSP/网电";
//			} else if (this.getCompareType() == COMPARETYPE_3) {
//				return "银行/支付平台";
//			} else {
//				return "";
//			}
//		} else {
//			return "";
//		}
		return "";
	}

	public String getTypeInfo() {
		if (this.getType() != null) {
//			if (this.getType() == TYPE_1) {
//				return "供应";
//			} else if (this.getType() == TYPE_2) {
//				return "采购";
//			} else if (this.getType() == TYPE_13) {
//				return "供应退废";
//			} else if (this.getType() == TYPE_14) {
//				return "采购退废";
//			} else if (this.getType() == TYPE_15) {
//				return "供应退票";
//			} else if (this.getType() == TYPE_16) {
//				return "采购退票";
//			} else if (this.getType() == TYPE_2) {
//				return "供应废票";
//			} else if (this.getType() == TYPE_2) {
//				return "采购废票";
//			} else {
				return "";
//			}
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

	public String getFormatLastDate() {
		String mydate = "";
		if (this.lastDate != null && "".equals(lastDate) == false) {
			Date tempDate = new Date(lastDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}
  	
  	
}
