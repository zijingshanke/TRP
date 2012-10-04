package com.fdays.tsms.transaction;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fdays.tsms.transaction._entity._ReportRecodeResult;
import com.fdays.tsms.user.UserStore;
import com.neza.tool.DateUtil;

public class ReportRecodeResult extends _ReportRecodeResult {
	private static final long serialVersionUID = 1L;

	
	public long platformId = 0;
	public long paytoolId = 0;
	public long reportIndexId = 0;
	
	public String idTranType;		//platformId、交易类型
	
	public String fileName = "";
	public java.sql.Timestamp reportDate;
	
	public Map<Long,Map<String,Integer>> idNameCount = new HashMap<Long, Map<String,Integer>>();		//记录已导入报表中各索引下的条数
	
	
	public long tranType=0;

	public static final long REPORTTYPE_1 = 1;// 平台报表
	public static final long REPORTTYPE_2 = 2;// 支付工具报表

	public static final long STATUS_1 = 1;// 有效
	public static final long STATUS_0 = 0;// 无效

	public String getReportTypeInfo() {
		return getReportTypeInfoByValue(this.reportType);
	}

	public static String getReportTypeInfoByValue(Long reportType) {
		if (reportType != null) {
			if (reportType == REPORTTYPE_1) {
				return "交易平台";
			} else if (reportType == REPORTTYPE_2) {
				return "支付工具";
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

	public String getUserName() {
		String userName = "";
		if (userNo != null && "".equals(userNo) == false) {
			userName = UserStore.getUserNameByNo(userNo);
		}
		return userName;
	}

	public String getStatusInfo() {
		if (this.getStatus() != null) {
			if (this.getStatus() == STATUS_1) {
				return "有效";
			} else if (this.getStatus().intValue() == STATUS_0) {
				return "无效";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}

	public long getPaytoolId() {
		return paytoolId;
	}

	public void setPaytoolId(long paytoolId) {
		this.paytoolId = paytoolId;
	}

	
	
	public long getReportIndexId() {
		return reportIndexId;
	}

	public void setReportIndexId(long reportIndexId) {
		this.reportIndexId = reportIndexId;
	}

	public java.sql.Timestamp getReportDate() {
		return reportDate;
	}

	public void setReportDate(java.sql.Timestamp reportDate) {
		this.reportDate = reportDate;
	}

	
	
	public long getTranType() {
		return tranType;
	}

	public void setTranType(long tranType) {
		this.tranType = tranType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Map<Long, Map<String, Integer>> getIdNameCount() {
		return idNameCount;
	}

	public void setIdNameCount(Map<Long, Map<String, Integer>> idNameCount) {
		this.idNameCount = idNameCount;
	}

	public String getIdTranType() {
		return idTranType;
	}

	public void setIdTranType(String idTranType) {
		this.idTranType = idTranType;
	}
	
}
