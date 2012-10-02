package com.fdays.tsms.transaction;

import java.util.Date;

import com.fdays.tsms.transaction._entity._PlatformCompare;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class PlatformCompare extends _PlatformCompare {
	private static final long serialVersionUID = 1L;

	public String filePath = "";
	public String fileName = "";
	public String listAttachName = "";

	public String beginDateStr = "";
	public String endDateStr = "";

	public static final long TYPE_1 = 1;// 销售
	public static final long TYPE_2 = 2;// 退废

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getListAttachName() {
		return listAttachName;
	}

	public void setListAttachName(String listAttachName) {
		this.listAttachName = listAttachName;
	}

	public String getBeginDateStr() {
		return beginDateStr;
	}

	public void setBeginDateStr(String beginDateStr) {
		this.beginDateStr = beginDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

}
