package com.fdays.tsms.transaction.util;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportRecode;
import com.neza.exception.AppException;

/**
 * 报表导入工具类
 * 
 */

public class ReportRecodeImportUtil {

	// 导入平台报表
	public static List<ReportRecode> getReportRecodeAsPlatform(File reportFile,
			PlatformReportIndex reportIndex) throws AppException {
		List<ReportRecode> reportRecodeList = new ArrayList<ReportRecode>();
		ReportRecode totalReportRecode = new ReportRecode();
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							
							BigDecimal totalInAmount = BigDecimal.ZERO;
							BigDecimal totalOutAmount = BigDecimal.ZERO;
							Long totalPassengerCount = Long.valueOf(0);
							int totalRowNum = 0;

							for (int i = 1; i < rownum; i++) {
								ReportRecode reportRecode = new ReportRecode();
								reportRecode.setReportRownum(Long
										.valueOf(i + 1));
								totalRowNum += (i + 1);

								int tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i)
											.getContents();
									subPnr = Constant.toUpperCase(subPnr,
											new Long(6));
									reportRecode.setSubPnr(subPnr);
								}
								
								if ("".equals(Constant.toString(reportRecode
										.getSubPnr()))) {
									continue;
								}

								tempIndex = reportIndex
										.getIndexValueByName("passengerCount");
								if (tempIndex >= 0) {
									String passengerCountStr = Constant
											.toUpperCase(
													sheet.getCell(tempIndex, i)
															.getContents(),
													new Long(2));
									Long passengerCount = Constant
											.toLong(passengerCountStr);
									reportRecode
											.setPassengerCount(passengerCount);
									totalPassengerCount += passengerCount;
								}

								tempIndex = reportIndex
										.getIndexValueByName("inAccount");
								if (tempIndex >= 0) {
									String inAccountName = sheet.getCell(
											tempIndex, i).getContents();
									inAccountName = Constant.toUpperCase(
											inAccountName, new Long(30));
									// reportRecode.setInAccountName(inAccountName);
								}

								tempIndex = reportIndex
										.getIndexValueByName("outAccount");
								if (tempIndex >= 0) {
									String outAccountName = sheet.getCell(
											tempIndex, i).getContents();
									outAccountName = Constant.toUpperCase(
											outAccountName, new Long(30));
									// reportRecode.setOutAccountName(outAccountName);
								}

								tempIndex = reportIndex
										.getIndexValueByName("inAmount");
								if (tempIndex >= 0) {
									String inAmountStr = sheet.getCell(
											tempIndex, i).getContents();
									inAmountStr = Constant.toUpperCase(
											inAmountStr, new Long(10));
									BigDecimal inAmount = Constant
											.toBigDecimal(inAmountStr);
									reportRecode.setAmount(inAmount);
									totalInAmount = totalInAmount.add(inAmount);
								}

								tempIndex = reportIndex
										.getIndexValueByName("outAmount");
								if (tempIndex >= 0) {
									String outAmountStr = sheet.getCell(
											tempIndex, i).getContents();
									outAmountStr = Constant.toUpperCase(
											outAmountStr, new Long(10));
									BigDecimal outAmount = Constant
											.toBigDecimal(outAmountStr);
									reportRecode.setAmount(outAmount);
									totalOutAmount = totalOutAmount
											.add(outAmount);
								}
								reportRecode.setReportName(reportIndex.getName());
								reportRecode.setPlatformReportIndex(reportIndex);
								// reportRecode.setType(tranType);
								// reportRecode.setStatus(status);
								reportRecode.setPlatformId(reportIndex.getPlatformId());
								reportRecodeList.add(reportRecode);
							}

							totalReportRecode.setTotalInAmount(totalInAmount);
							totalReportRecode.setTotalOutAmount(totalOutAmount);
							totalReportRecode.setTotalPassengerCount(totalPassengerCount);
							totalReportRecode.setTotalRowNum(totalRowNum);
							// request.getSession().setAttribute(
							// "totalReportCompare", totalCompare);
						} else {
							totalReportRecode.setMessage("工作表没有数据!");  
							System.out.println("=======>>rownum not > 0");
							reportRecodeList.add(totalReportRecode);
						}
					} else {
						totalReportRecode.setMessage("此工作本没有工作表!");
						System.out.println("=======>>sheet is not exists");
						reportRecodeList.add(totalReportRecode);
					}
					book.close();
				} else {
					totalReportRecode.setMessage("此文件没有工作本!");
					System.out.println("=======>>workbook is not exists");
					reportRecodeList.add(totalReportRecode);
				}
			} else {
				totalReportRecode.setMessage("文件不存在!");
				System.out.println("=======>>reportFile is not exists");
				reportRecodeList.add(totalReportRecode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析平台报表异常。。" + e.getMessage());
			totalReportRecode.setMessage("解析平台报表异常......"+e.getMessage());
			reportRecodeList.add(totalReportRecode);
		}
		System.out
				.println("=======>>import platform report to reportRecodeList size:"
						+ reportRecodeList.size());
		reportRecodeList = sortReportRecodeList(reportRecodeList);
		return reportRecodeList;
	}

	// 导入支付工具报表
	public static List<ReportRecode> getReportRecodeAsPaytool(File reportFile,
			PlatformReportIndex reportIndex) throws AppException {
		List<ReportRecode> reportRecodeList = new ArrayList<ReportRecode>();
		ReportRecode totalReportRecode = new ReportRecode();
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							BigDecimal totalInAmount = BigDecimal.ZERO;
							BigDecimal totalOutAmount = BigDecimal.ZERO;
							Long totalPassengerCount = Long.valueOf(0);
							int totalRowNum = 0;

							for (int i = 1; i < rownum; i++) {
								ReportRecode reportRecode = new ReportRecode();
								reportRecode.setReportRownum(Long
										.valueOf(i + 1));
								totalRowNum += (i + 1);

								int tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									Cell cell = sheet.getCell(tempIndex, i);
									if (cell != null) {
										String subPnr = cell.getContents();
										
										subPnr=getPNRByReportInfo(subPnr);
										
										subPnr = Constant.toUpperCase(subPnr,
												new Long(6));
										reportRecode.setSubPnr(subPnr);
									}
								}
								if ("".equals(Constant.toString(reportRecode
										.getSubPnr()))) {
									continue;
								}

								tempIndex = reportIndex
										.getIndexValueByName("airOrderNo");
								if (tempIndex >= 0) {
									Cell cell = sheet.getCell(tempIndex, i);
									if (cell != null) {
										String airOrderNo = cell.getContents();
										airOrderNo = StringUtil
												.removeChiness(airOrderNo);
										airOrderNo = Constant.toUpperCase(
												airOrderNo, new Long(30));
										reportRecode.setAirOrderNo(airOrderNo);
									}
								}

								tempIndex = reportIndex
										.getIndexValueByName("tranPlatformName");
								if (tempIndex >= 0) {
									Cell cell = sheet.getCell(tempIndex, i);
									if (cell != null) {
										String tranPlatformName = cell
												.getContents();
										tranPlatformName = Constant
												.toUpperCase(tranPlatformName,
														new Long(30));
										reportRecode
												.setTranPlatformName(tranPlatformName);
										// ---------------
										reportRecode
												.setPlatformId(ReportRecode
														.getPlatfromIdByNameValue(tranPlatformName));
									}
								}

								tempIndex = reportIndex
										.getIndexValueByName("passengerCount");
								if (tempIndex >= 0) {
									Cell cell = sheet.getCell(tempIndex, i);
									if (cell != null) {
										String passengerCountStr = cell
												.getContents();
										passengerCountStr = Constant
												.toUpperCase(passengerCountStr,
														new Long(2));
										Long passengerCount = Constant
												.toLong(passengerCountStr);
										reportRecode
												.setPassengerCount(passengerCount);
										totalPassengerCount += passengerCount;
									}
								}

								// tempIndex = reportIndex
								// .getIndexValueByName("inAccount");
								// if (tempIndex >= 0) {
								// String inAccountName = sheet.getCell(
								// tempIndex, i).getContents();
								// inAccountName = Constant.toUpperCase(
								// inAccountName, new Long(30));
								// //
								// reportRecode.setInAccountName(inAccountName);
								// }

								tempIndex = reportIndex
										.getIndexValueByName("outAccount");
								if (tempIndex >= 0) {
									Cell cell = sheet.getCell(tempIndex, i);
									if (cell != null) {
										String outAccountName = cell
												.getContents();
										outAccountName = Constant.toUpperCase(
												outAccountName, new Long(30));
										// reportRecode.setOutAccountName(outAccountName);
									}
								}

								tempIndex = reportIndex
										.getIndexValueByName("inAmount");
								if (tempIndex >= 0) {
									Cell cell = sheet.getCell(tempIndex, i);
									if (cell != null) {
										String inAmountStr = cell.getContents();
										inAmountStr = Constant.toUpperCase(
												inAmountStr, new Long(10));
										BigDecimal inAmount = Constant
												.toBigDecimal(inAmountStr);
										reportRecode.setAmount(inAmount);
										totalInAmount = totalInAmount
												.add(inAmount);
									}
								}

								if (Constant.toBigDecimal(reportRecode
										.getAmount()) == BigDecimal.ZERO) {
									tempIndex = reportIndex
											.getIndexValueByName("outAmount");
									if (tempIndex >= 0) {
										Cell cell = sheet.getCell(tempIndex, i);
										if (cell != null) {
											String outAmountStr = cell
													.getContents();
											outAmountStr = Constant
													.toUpperCase(outAmountStr,
															new Long(10));
											BigDecimal outAmount = Constant
													.toBigDecimal(outAmountStr);
											reportRecode.setAmount(outAmount);
											totalOutAmount = totalOutAmount
													.add(outAmount);
										}
									}
								}

								reportRecode.setReportName(reportIndex.getName());
								reportRecode.setType(ReportRecode.RECODE_TYPE_31);
								// reportRecode.setStatus(status);
								long payToolId = reportIndex.getPaymenttoolId();
								reportRecode.setPayToolId(payToolId);
								reportRecode.setPlatformReportIndex(reportIndex);
								reportRecodeList.add(reportRecode);
							}
							totalReportRecode.setTotalInAmount(totalInAmount);
							totalReportRecode.setTotalOutAmount(totalOutAmount);
							totalReportRecode
									.setTotalPassengerCount(totalPassengerCount);
							totalReportRecode.setTotalRowNum(totalRowNum);
							// request.getSession().setAttribute(
							// "totalReportCompare", totalCompare);
						} else {
							System.out.println("=======>>rownum not > 0");	
							totalReportRecode.setMessage("工作表没有数据!");		
							reportRecodeList.add(totalReportRecode);
						}
					} else {
						System.out.println("=======>>sheet is not exists");
						totalReportRecode.setMessage("此工作本没有工作表!");
						reportRecodeList.add(totalReportRecode);
					}
					book.close();
				} else {
					System.out.println("=======>>workbook is not exists");
					totalReportRecode.setMessage("此文件没有工作本!");
					reportRecodeList.add(totalReportRecode);
				}
			} else {
				System.out.println("=======>>reportFile is not exists");
				totalReportRecode.setMessage("文件不存在!");
				reportRecodeList.add(totalReportRecode);
			}
		} catch (Exception e) {
			e.printStackTrace();
			totalReportRecode.setMessage("解析平台报表异常......"+e.getMessage());
			System.out.println("解析平台报表异常。。" + e.getMessage());
			reportRecodeList.add(totalReportRecode);
		}
		System.out.println("=======>>import paytool report to reportRecodeList size:"
						+ reportRecodeList.size());
		reportRecodeList = sortReportRecodeList(reportRecodeList);
		return reportRecodeList;
	}

	public List<ReportRecode> getReportRecodeAsAlipay(ReportRecode reportRecode)
			throws AppException {
		List<ReportRecode> recodeList = new ArrayList<ReportRecode>();

		// protected Long payToolId;
		// protected Long platformId;
		// protected String subPnr;
		// protected String airOrderNo;
		// protected java.math.BigDecimal amount;
		// protected Long statementType;
		// protected Long accountId;
		// protected String accountName;
		// protected Long passengerCount;
		// protected java.sql.Timestamp reportDate;
		// protected Long reportRownum;
		// protected Long status;

		return recodeList;
	}

	public List<ReportRecode> getReportRecodeAsTenpay(ReportRecode reportRecode)
			throws AppException {
		List<ReportRecode> recodeList = new ArrayList<ReportRecode>();
		return recodeList;
	}

	// 排序
	public static List<ReportRecode> sortReportRecodeList(
			List<ReportRecode> reportRecodeList) {
		ReportRecodeComparator comp = new ReportRecodeComparator();
		try {
			Collections.sort(reportRecodeList, comp);// 执行排序方法
		} catch (Exception e) {
			System.out.println("====排序异常=========");
			e.printStackTrace();
		}
		return reportRecodeList;
	}

	public static void main(String[] args) {
		String sampleInfo = "`1204058901201101152568053333^201101155718^[羿天下]^ZH^199500^0^JQTNJ1";

//		sampleInfo = "`1205251201201101150000697582^110115184611740127^JQTVVL^^127100^0^广州-南昌";

//		sampleInfo = "订单号:XS00682386|JM18HX";
		
		sampleInfo="JM18HX";
		
		sampleInfo="`1202448001201101010922174761^634294701523859059^HDS559^杨斌^117900^1^YNJ-PEK";
		
		System.out.println(getPNRByReportInfo(sampleInfo));
	}

	public static String getPNRByReportInfo(String source) {
		Pattern pattern = Pattern.compile("[A-Z]\\w{4,5}");
		Matcher m = null;
		if (source.contains("^")) {
			String[] str = source.split("\\^");
			for (int i = 0; i < str.length; i++) {
				if (str[i].length() == 5 || str[i].length() == 6) {
					m = pattern.matcher(str[i]);
					if (m.matches()) {
						return str[i];
					}
				}
			}
		} else if (source.contains("|")) {
			String sourceCode = source.substring(source.lastIndexOf("|") + 1);
			m = pattern.matcher(sourceCode);
			if (m.matches()) {
				return sourceCode;
			}
		}else if(source.length()==5 || source.length() ==6){
			m = pattern.matcher(source);
			if (m.matches()) {
				return source;
			}
		}
		return "";
	}
}