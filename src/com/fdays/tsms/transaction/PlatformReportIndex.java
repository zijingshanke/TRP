package com.fdays.tsms.transaction;

import java.util.Date;

import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction._entity._PlatformReportIndex;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class PlatformReportIndex extends _PlatformReportIndex {
	private static final long serialVersionUID = 1L;

	public static final long TYPE_1 = 1;// 销售
	public static final long TYPE_2 = 2;// 退废

	public static final long STATES_1 = 1;// 有效
	public static final long STATES_0 = 0;// 无效

	public int getIndexValueByName(String name) {
		Long index = Constant.toLong(getIndexByName(name));
		return index.intValue();
	}

	public Long getIndexByName(String name) {
		Long index = new Long(-1);
		if (name != null && "".equals(name) == false) {
			if ("subPnr".equals(name)) {
				return subPnr;
			}
			if ("airOrderNo".equals(name)) {
				return airOrderNo;
			}
			if ("payOrderNo".equals(name)) {
				return payOrderNo;
			}			
			if ("passengerCount".equals(name)) {
				return passengerCount;
			}
			
			if ("inAccount".equals(name)) {
				return inAccount;
			}
			if ("outAccount".equals(name)) {
				return outAccount;
			}			
			if ("inAmount".equals(name)) {
				return inAmount;
			}
			if ("outAmount".equals(name)) {
				return outAmount;
			}
			if ("inRetireAccount".equals(name)) {
				return inRetireAccount;
			}
			if ("outRetireAccount".equals(name)) {
				return outRetireAccount;
			}
			if ("inRetireAmount".equals(name)) {
				return inRetireAmount;
			}
			if ("outRetireAmount".equals(name)) {
				return outRetireAmount;
			}
			if ("flightCode".equals(name)) {
				return flightCode;
			}
			if ("flightClass".equals(name)) {
				return flightClass;
			}
			if ("ticketNumber".equals(name)) {
				return ticketNumber;
			}
			if ("startPoint".equals(name)) {
				return startPoint;
			}
			if ("endPoint".equals(name)) {
				return endPoint;
			}
			
			if ("discount".equals(discount)) {
				return discount;
			}
		}
		return index;
	}

	public String getPlatformName() {
		String platformName = "";
		if (platformId != null && platformId > 0) {
			Platform platform = PlatComAccountStore.getPlatformById(platformId);
			platformName = platform.getName();
		}
		return platformName;
	}

	public String getUserName() {
		String userName = "";
		if (userNo != null && "".equals(userNo) == false) {
			userName = UserStore.getUserNameByNo(userNo);
		}
		return userName;
	}

	public String getTypeInfo() {
		if (this.getType() != null) {
			if (this.getType() == TYPE_1) {
				return "销售";
			} else if (this.getType() == TYPE_2) {
				return "退废";
			} else {
				return "";
			}
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

	public String getFormatLastDate() {
		String mydate = "";
		if (this.lastDate != null && "".equals(lastDate) == false) {
			Date tempDate = new Date(lastDate.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd HH:mm:ss");
		}
		return mydate;
	}

}
