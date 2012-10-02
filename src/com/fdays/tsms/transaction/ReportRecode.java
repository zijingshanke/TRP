package com.fdays.tsms.transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction._entity._ReportRecode;

public class ReportRecode extends _ReportRecode {
	private static final long serialVersionUID = 1L;

	public Long platformRecodeResultId;
	public Long paytoolRecodeResultId;

	public String platformRecodeResultName;
	public String paytoolRecodeResultName;

	public String filePath = "";
	public String fileName = "";

	public String tranPlatformName = "";

	public long compareType = 0;
	public long reportCompareResultId = 0;

	public Timestamp tempReportDate;

	public long reportRecodeResultId;

	public String[] compareCondition;// 对比条件
	public String compareConditionStr;// 对比条件(页面赋值)
	public long compareStandard = 0;// 以谁为标准

	private BigDecimal inAmount = BigDecimal.ZERO;
	private BigDecimal outAmount = BigDecimal.ZERO;
	private BigDecimal inRefundAmount = BigDecimal.ZERO;
	private BigDecimal outRefundAmount = BigDecimal.ZERO;

	/**
	 * 对账只存在于平台（平台-系统）
	 */
	public static final long RECODE_TYPE_1 = 1;
	/**
	 * 对账只存在于系统（平台-系统）
	 */
	public static final long RECODE_TYPE_2 = 2;
	/**
	 * 对账只存在于平台（平台-支付工具）
	 */
	public static final long RECODE_TYPE_11 = 11;
	/**
	 * 对账只存在于支付工具（平台-支付工具）
	 */
	public static final long RECODE_TYPE_12 = 12;
	/**
	 * 导入记录（平台）
	 */
	public static final long RECODE_TYPE_21 = 21;
	/**
	 * 导入记录（支付工具）
	 */
	public static final long RECODE_TYPE_31 = 31;
	/**
	 * 系统记录
	 */
	public static final long RECODE_TYPE_41 = 41;

	/** 平台-系统 */
	public static final long COMPARE_TYPE_1 = 1;
	/** 平台-支付工具 */
	public static final long COMPARE_TYPE_2 = 2;

	public static final long COMPARE_STANDARD_1 = 1;// 以交易平台为标准对比
	public static final long COMPARE_STANDARD_2 = 2;// 以支付工具为标准对比
	public static final long COMPARE_STANDARD_3 = 3;// 以系统记录为标准对比

	public static final long STATUS_1 = 1;// 有效
	public static final long STATUS_0 = 0;// 无效

	// --------合计
	public int totalRowNum = -1;
	public BigDecimal totalInAmount = BigDecimal.ZERO;
	public BigDecimal totalOutAmount = BigDecimal.ZERO;
	public BigDecimal totalInRetireAmount = BigDecimal.ZERO;
	public BigDecimal totalOutRetireAmount = BigDecimal.ZERO;
	public Long totalPassengerCount = Long.valueOf(0);

	public ReportRecode() {

	}

	public static boolean comparePlatformReport(ReportRecode reportRecodeForm,
			ReportRecode compare, ReportRecode order) {
		boolean flag1 = false;
		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		boolean flag5 = false;

		String[] compareCondition = reportRecodeForm.getCompareCondition();

		if (StringUtil.containsExistString(compareCondition, "subPnr")) {
			if (Constant.toUpperCase(compare.getSubPnr()).equals(
					Constant.toUpperCase(order.getSubPnr()))) {
				flag1 = true;
			}
		}

		if (StringUtil.containsExistString(compareCondition, "airOrderNo")) {
			if (Constant.toUpperCase(compare.getAirOrderNo()).equals(
					Constant.toUpperCase(order.getAirOrderNo()))) {
				flag2 = true;
			}
		}

		// BigDecimal compareInAmount = Constant.toBigDecimal(compare
		// .getInAmount());
		// BigDecimal orderInAmount =
		// Constant.toBigDecimal(order.getInAmount());
		// int result = compareInAmount.compareTo(orderInAmount);
		// if (result == 0) {
		// flag3 = true;
		// }

		if (StringUtil.containsExistString(compareCondition, "passengerCount")) {
			if (Constant.toLong(compare.getPassengerCount()).compareTo(
					Constant.toLong(order.getPassengerCount())) == Long
					.valueOf(0)) {
				flag5 = true;
			}
		}

		if (flag1 && flag2 /* && flag3 */&& flag5) {
			return true;
		} else {
			return false;
		}
	}

	public ReportRecode(AirticketOrder order, Statement statement) {
		if (order != null) {
			Platform platform = order.getPlatform();
			if (platform != null) {
				this.platformId = platform.getId();
			}
			this.subPnr = order.getSubPnr();
			// bigPnr
			this.airOrderNo = order.getAirOrderNo();
			this.passengerCount = order.getTotalPerson();
		}

		if (statement != null) {
			long stateementType = statement.getOrderSubtype();
			if (Constant.toLong(stateementType) > 0) {
				this.statementType = stateementType;
				BigDecimal amount = Constant.toBigDecimal(statement
						.getTotalAmount());
				this.amount = amount;
				if (stateementType == Statement.SUBTYPE_10) // 正常收款
				{
					this.inAmount = amount;
					// accountID
					// accountName
					// payToolId
				}
				if (stateementType == Statement.SUBTYPE_11) // 收退款
				{
					this.inRefundAmount = amount;
					// accountID
					// accountName
					// payToolId
				}
				if (stateementType == Statement.SUBTYPE_20) // 正常付款
				{
					this.outAmount = amount;
					// accountID
					// accountName
					// payToolId
				}
				if (stateementType == Statement.SUBTYPE_21) // 付退款
				{
					this.outRefundAmount = amount;
					// accountID
					// accountName
					// payToolId
				}
			}
		}
		this.type = RECODE_TYPE_41;
	}

	public static long getPlatfromIdByNameValue(String platformName) {
		long platformId = 0;
		platformName = Constant.toString(platformName);
		// System.out.println("getPlatfromIdByNameValue():"+platformName);
		if ("".equals(platformName) == false) {
			Platform platform = PlatComAccountStore.getPlatformById(platformId);
			if (platform != null) {
				platformId = platform.getId();
			}
		}

		// --文字
		if (platformId < 1) {
			if ("深圳航空".equals(platformName) || "深航".equals(platformName)
					|| "深圳航空有限责任公司".equals(platformName)) {
				platformId = 55;
				// B2A-ZH 55
				// B2B-ZH 56
			}
			if ("南方航空".equals(platformName) || "南航".equals(platformName)) {
				platformId = 34;
			}
			if ("东方航空".equals(platformName) || "东航".equals(platformName)
					|| "中国东方航空股份有限公司".equals(platformName)) {
				platformId = 34;
			}

			if ("上海航空".equals(platformName) || "上航".equals(platformName)
					|| "上海航空股份有限公司".equals(platformName)) {
				platformId = 38;
			}

			if ("吉祥航空".equals(platformName) || "吉祥".equals(platformName)) {
				platformId = 40;
			}
			if ("XX航空".equals(platformName) || "X航".equals(platformName)) {
				platformId = 34;
			}
		}

		// ---财付通517（6511）
		if (platformId < 1) {
			String tempName = platformName.replaceAll("[^0-9]", "");
			if ("1204058901".equals(tempName)) {
				platformId = 10;// 羿天下
			}
			if ("1201187901".equals(tempName)) {
				platformId = 34;// 南方航空
			}
			if ("1205251201".equals(tempName)) {
				platformId = 21;// 517NA
			}
			if ("1202448001".equals(tempName)) {
				platformId = 71;// 北京商旅信通(51666/KKKK)
			}
		}

		return platformId;
	}

	public String getStatementTypeInfo() {
		return Statement.getSubTypeInfoByValue(this.statementType);
	}

	public String getCompareTypeInfo() {
		return getCompareTypeInfoByValue(this.compareType);
	}

	public static String getCompareTypeInfoByValue(Long compareType) {
		if (compareType != null) {
			if (compareType == COMPARE_TYPE_1) {
				return "平台-系统";
			} else if (compareType == COMPARE_TYPE_2) {
				return "平台-支付工具";
			} else {
				return "";
			}
		} else {
			return "";
		}
	}

	public String getPlatformName() {
		String platformName = "";
		if (platformId != null && platformId > 0) {
			Platform platform = PlatComAccountStore.getPlatformById(platformId);
			platformName = platform.getName();
		}
		return platformName;
	}

	public String getPaytoolName() {
		String paymenttoolName = "";
		if (payToolId != null && payToolId > 0) {
			PaymentTool paymentTool = PlatComAccountStore
					.getPaymentToolById(payToolId);
			paymenttoolName = paymentTool.getName();
		}
		return paymenttoolName;
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

	public String getTranPlatformName() {
		return tranPlatformName;
	}

	public void setTranPlatformName(String tranPlatformName) {
		this.tranPlatformName = tranPlatformName;
	}

	public int getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(int totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

	public BigDecimal getTotalInAmount() {
		return totalInAmount;
	}

	public void setTotalInAmount(BigDecimal totalInAmount) {
		this.totalInAmount = totalInAmount;
	}

	public BigDecimal getTotalOutAmount() {
		return totalOutAmount;
	}

	public void setTotalOutAmount(BigDecimal totalOutAmount) {
		this.totalOutAmount = totalOutAmount;
	}

	public BigDecimal getTotalInRetireAmount() {
		return totalInRetireAmount;
	}

	public void setTotalInRetireAmount(BigDecimal totalInRetireAmount) {
		this.totalInRetireAmount = totalInRetireAmount;
	}

	public BigDecimal getTotalOutRetireAmount() {
		return totalOutRetireAmount;
	}

	public void setTotalOutRetireAmount(BigDecimal totalOutRetireAmount) {
		this.totalOutRetireAmount = totalOutRetireAmount;
	}

	public Long getTotalPassengerCount() {
		return totalPassengerCount;
	}

	public void setTotalPassengerCount(Long totalPassengerCount) {
		this.totalPassengerCount = totalPassengerCount;
	}

	public BigDecimal getInAmount() {
		return inAmount;
	}

	public void setInAmount(BigDecimal inAmount) {
		this.inAmount = inAmount;
	}

	public BigDecimal getOutAmount() {
		return outAmount;
	}

	public void setOutAmount(BigDecimal outAmount) {
		this.outAmount = outAmount;
	}

	public BigDecimal getInRefundAmount() {
		return inRefundAmount;
	}

	public void setInRefundAmount(BigDecimal inRefundAmount) {
		this.inRefundAmount = inRefundAmount;
	}

	public BigDecimal getOutRefundAmount() {
		return outRefundAmount;
	}

	public void setOutRefundAmount(BigDecimal outRefundAmount) {
		this.outRefundAmount = outRefundAmount;
	}

	public Long getPlatformRecodeResultId() {
		return platformRecodeResultId;
	}

	public void setPlatformRecodeResultId(Long platformRecodeResultId) {
		this.platformRecodeResultId = platformRecodeResultId;
	}

	public Long getPaytoolRecodeResultId() {
		return paytoolRecodeResultId;
	}

	public void setPaytoolRecodeResultId(Long paytoolRecodeResultId) {
		this.paytoolRecodeResultId = paytoolRecodeResultId;
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

	public long getCompareType() {
		return compareType;
	}

	public void setCompareType(long compareType) {
		this.compareType = compareType;
	}

	public String getPlatformRecodeResultName() {
		return platformRecodeResultName;
	}

	public void setPlatformRecodeResultName(String platformRecodeResultName) {
		this.platformRecodeResultName = platformRecodeResultName;
	}

	public String getPaytoolRecodeResultName() {
		return paytoolRecodeResultName;
	}

	public void setPaytoolRecodeResultName(String paytoolRecodeResultName) {
		this.paytoolRecodeResultName = paytoolRecodeResultName;
	}

	public long getReportCompareResultId() {
		return reportCompareResultId;
	}

	public void setReportCompareResultId(long reportCompareResultId) {
		this.reportCompareResultId = reportCompareResultId;
	}

	public Timestamp getTempReportDate() {
		return tempReportDate;
	}

	public void setTempReportDate(Timestamp tempReportDate) {
		this.tempReportDate = tempReportDate;
	}

	public long getReportRecodeResultId() {
		return reportRecodeResultId;
	}

	public void setReportRecodeResultId(long reportRecodeResultId) {
		this.reportRecodeResultId = reportRecodeResultId;
	}

	public String[] getCompareCondition() {
		return compareCondition;
	}

	public void setCompareCondition(String[] compareCondition) {
		this.compareCondition = compareCondition;
	}

	public long getCompareStandard() {
		return compareStandard;
	}

	public void setCompareStandard(long compareStandard) {
		this.compareStandard = compareStandard;
	}

	public String getCompareConditionStr() {
		if (compareCondition != null) {
			compareConditionStr = StringUtil.getStringByArray(compareCondition,
					",");
		}
		return compareConditionStr;
	}

	public void setCompareConditionStr(String compareConditionStr) {
		this.compareConditionStr = compareConditionStr;
	}

	public String getCompareConditionInfo() {
		String info = "";
		if (this.compareCondition != null) {
			for (int i = 0; i < this.compareCondition.length; i++) {
				String name = Constant.toString(this.compareCondition[i]);
				if ("subPnr".equals(name)) {
					info += "预定编码" + ",";
				}
				if ("airOrderNo".equals(name)) {
					info += "商家订单号" + ",";
				}
				if ("amount".equals(name)) {
					info += "金额" + ",";
				}
				if ("account".equals(name)) {
					info += "账号" + ",";
				}
			}
		}
		return info;
	}

	public String getCompareStandardInfo() {
		String info = "";
		if (this.compareStandard == 1) {
			info = "以平台为标准对比";
		}

		if (this.compareStandard == 2) {
			if (this.compareType == COMPARE_TYPE_1) {
				info = "以系统为标准对比";
			}
			if (this.compareType == COMPARE_TYPE_2) {
				info = "以支付工具为标准对比";
			}			
		}
		return info;
	}

}
