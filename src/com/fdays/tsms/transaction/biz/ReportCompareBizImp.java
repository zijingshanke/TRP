package com.fdays.tsms.transaction.biz;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportCompareListForm;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.dao.ReportCompareDAO;
import com.fdays.tsms.transaction.dao.ReportCompareResultDAO;
import com.fdays.tsms.transaction.util.PlatformCompareComparator;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class ReportCompareBizImp implements ReportCompareBiz {
	private ReportCompareDAO reportCompareDAO;
	private ReportCompareResultDAO reportCompareResultDAO;

	/**
	 * 保存报表对比结果集
	 */
	public String saveCompareResult(ReportCompare reportCompare,
			HttpServletRequest request) throws AppException {
		String result = "";
		List<ReportCompare> problemList1 = (List<ReportCompare>) request
				.getSession().getAttribute("problemCompareList1");// -对账只存在于本系统
		// ;
		List<ReportCompare> problemList2 = (List<ReportCompare>) request
				.getSession().getAttribute("problemCompareList2");// -对账只存在于上传文件
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String userNo = uri.getUser().getUserNo();

		long resultId = saveCompareResultByProblem(reportCompare, new Long(1),
				userNo);
		ReportCompareResult reportCompareResult = reportCompareResultDAO
				.queryById(resultId);
		if (reportCompareResult != null) {
			saveReportCompareByProblem(problemList1, reportCompareResult,ReportCompare.RESULT_TYPE_1);
			saveReportCompareByProblem(problemList2, reportCompareResult,ReportCompare.RESULT_TYPE_2);
		}

		return result;
	}

	public void saveReportCompareByProblem(List<ReportCompare> problemList,
			ReportCompareResult reportCompareResult,Long resultType) throws AppException {
		for (int i = 0; i < problemList.size(); i++) {
			ReportCompare tempCompare = problemList.get(i);
			if (tempCompare != null&& reportCompareResult != null
					&& reportCompareResult.getId() > 0) {
				tempCompare.setReportCompareResult(reportCompareResult);
				tempCompare.setType(resultType);
				reportCompareDAO.save(tempCompare);
			}
		}
	}

	public long saveCompareResultByProblem(ReportCompare reportCompare,
			Long type, String userNo) throws AppException {
		ReportCompareResult compareResult = new ReportCompareResult();
		compareResult.setPlatformId(reportCompare.getPlatformId());
		// compareResult.setPaymenttoolId(reportCompare.getPayttoolId());
		compareResult.setAccountId(reportCompare.getAccountId());
		compareResult.setBeginDate(DateUtil.getTimestamp(reportCompare
				.getBeginDateStr(), "yyyy-MM-dd HH:mm:ss"));
		compareResult.setEndDate(DateUtil.getTimestamp(reportCompare
				.getEndDateStr(), "yyyy-MM-dd HH:mm:ss"));
		compareResult.setMemo("");
		compareResult.setUserNo(userNo);
		compareResult.setLastDate(new Timestamp(System.currentTimeMillis()));
		compareResult.setType(type);
		compareResult.setStatus(ReportCompareResult.STATES_1);
		compareResult.setName(reportCompare.getBeginDateStr() + "-"
				+ reportCompare.getEndDateStr() + "-"
				+ compareResult.getPlatformName());
		long flag = reportCompareResultDAO.save(compareResult);
		return flag;
	}

	/**
	 * 导入平台报表文件
	 */
	public HttpServletRequest insertPlatformReport(ReportCompare reportCompare,
			PlatformReportIndex reportIndex, HttpServletRequest request)
			throws AppException {
		long a = System.currentTimeMillis();

		List<ReportCompare> reportCompareList = getPlatformReportCompareList(
				reportCompare, reportIndex, request);

		request.getSession().setAttribute("reportCompareList",
				reportCompareList);
		request.getSession().setAttribute("reportCompareListSize",
				reportCompareList.size());

		long b = System.currentTimeMillis();
		System.out.println(" over insertPlatformReport  time:"
				+ ((b - a) / 1000) + "s");
		return request;
	}

	/**
	 * 对比平台报表
	 * 
	 * @param HttpServletRequest
	 * @param List
	 *            <PlatformCompare>
	 */
	@SuppressWarnings("unchecked")
	public String comparePlatformReport(HttpServletRequest request)
			throws AppException {
		List<ReportCompare> reportCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("reportCompareList");

		List<ReportCompare> orderCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("orderCompareList");

		List<ReportCompare> problemCompareList1 = getPlatformCompareResult(
				new Long(1), reportCompareList, orderCompareList);
		List<ReportCompare> problemCompareList2 = getPlatformCompareResult(
				new Long(2), reportCompareList, orderCompareList);

		request.getSession().setAttribute("problemCompareList1",
				problemCompareList1);// -对账只存在于本系统
		request.getSession().setAttribute("problemCompareList1Size",
				problemCompareList1.size());

		request.getSession().setAttribute("problemCompareList2",
				problemCompareList2);// -对账只存在于上传文件
		request.getSession().setAttribute("problemCompareList2Size",
				problemCompareList2.size());

		return "";
	}

	/**
	 * 获取平台报表对比结果
	 * 
	 * @param type
	 *            1:-对账只存在于本系统 2:-对账只存在于上传文件
	 * 
	 * @List<PlatformCompare> reportCompareList 解析报表后的记录
	 * @List<PlatformCompare> orderCompareList 系统内符合条件的记录
	 */
	private List<ReportCompare> getPlatformCompareResult(long type,
			List<ReportCompare> reportCompareList,
			List<ReportCompare> orderCompareList) throws AppException {
		List<ReportCompare> problemCompareList = new ArrayList<ReportCompare>();
		if (reportCompareList != null && orderCompareList != null) {
			if (Constant.toLong(type) == 1) {
				for (int i = 0; i < orderCompareList.size(); i++) {
					ReportCompare compare = orderCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < reportCompareList.size(); j++) {
						ReportCompare order = reportCompareList.get(j);
						flag = ReportCompare.comparePlatformReport(compare,
								order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于本系统-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			} else if (Constant.toLong(type) == 2) {
				for (int i = 0; i < reportCompareList.size(); i++) {
					ReportCompare compare = reportCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < orderCompareList.size(); j++) {
						ReportCompare order = orderCompareList.get(j);
						flag = ReportCompare.comparePlatformReport(compare,
								order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于上传文件-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			}
		}
		problemCompareList = sortListBySubPnr(problemCompareList);

		return problemCompareList;
	}

	/**
	 * 从系统内读取符合对比条件的记录
	 * 
	 * @PlatformCompare reportCompare 搜索条件
	 */
	public List<ReportCompare> getPlatformOrderCompareList(
			ReportCompare reportCompare) throws AppException {
		List<ReportCompare> compareList = new ArrayList<ReportCompare>();
		Long platformId = Constant.toLong(reportCompare.getPlatformId());
		Long type = Constant.toLong(reportCompare.getType());

		String beginDateStr = reportCompare.getBeginDateStr();
		String endDateStr = reportCompare.getEndDateStr();

		String businessType = "";
		String tranType = "";
		if (type == ReportCompare.TYPE_1) {// 销售（供应）
			businessType = "1";
			tranType = "1";
		}
		if (type == ReportCompare.TYPE_2) {// 采购
			businessType = "2";
			tranType = "2";
		}
		if (type == ReportCompare.TYPE_13) {// 供应退废
			businessType = "1";
			tranType = "3,4";
		}
		if (type == ReportCompare.TYPE_14) {// 采购退废
			businessType = "2";
			tranType = "3,4";
		}
		if (type == ReportCompare.TYPE_15) {// 供应退
			businessType = "1";
			tranType = "3";
		}
		if (type == ReportCompare.TYPE_15) {// 采购退
			businessType = "2";
			tranType = "3";
		}
		if (type == ReportCompare.TYPE_15) {// 供应废
			businessType = "1";
			tranType = "4";
		}
		if (type == ReportCompare.TYPE_15) {// 采购废
			businessType = "2";
			tranType = "4";
		} else {
			tranType = type + "";
		}

		try {
			List<ReportCompare> tempCompareList = reportCompareDAO
					.listCompareOrder(platformId + "", beginDateStr,
							endDateStr, businessType, tranType,
							AirticketOrder.TICKETTYPE_1 + "");

			List<AirticketOrder> orderList = new ArrayList<AirticketOrder>();
			for (int i = 0; i < tempCompareList.size(); i++) {
				ReportCompare tempCompare = tempCompareList.get(i);
				if (tempCompare != null) {
					AirticketOrder order = tempCompare.getOrder();
					orderList.add(order);
				}

			}
			System.out.println("====填充AirticketeOrder SUCCESS...");
			for (int i = 0; i < orderList.size(); i++) {
				AirticketOrder order = orderList.get(i);
				if (order != null) {
					Set flights = order.getFlights();
					Set passengers = order.getPassengers();

					for (Iterator iterator = flights.iterator(); iterator
							.hasNext();) {
						Flight flight = (Flight) iterator.next();
						// for (Iterator iterator2 = passengers.iterator();
						// iterator2.hasNext();) {
						// Passenger passenger = (Passenger) iterator2.next();
						// PlatformCompare compare=new
						// PlatformCompare(order,flight,passenger);
						// compareList.add(compare);
						// }
						ReportCompare compare = new ReportCompare(order, flight);
						compareList.add(compare);
					}
				} else {
					System.out.println("=========orderList order is null " + i);
				}
			}
			System.out.println("系统内符合条件的PlatformCompare：" + compareList.size());
		} catch (AppException e) {
			e.printStackTrace();
		}

		compareList = sortListBySubPnr(compareList);
		return compareList;
	}

	/**
	 * 根据索引设置，解析平台报表文件
	 */
	private List<ReportCompare> getPlatformReportCompareList(
			ReportCompare reportCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException {
		List<ReportCompare> reportCompareList = new ArrayList<ReportCompare>();

		Long type = Constant.toLong(reportCompare.getType());
		Long status = ReportCompare.STATES_1;

		String reportFilePath = Constant.PROJECT_PLATFORMREPORTS_PATH
				+ File.separator + reportCompare.fileName;

		String requestURL = Constant.toString(request.getRequestURL()
				.toString());
		if (requestURL.indexOf("tsms.fdays.com") < 0) {
			reportFilePath = "D:" + File.separator + reportFilePath;// 开发环境
		}

		File reportFile = new File(reportFilePath);
		// System.out.println("reportFile:" + reportFile);
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							ReportCompare preCompare = new ReportCompare();
							ReportCompare totalCompare = new ReportCompare();
							BigDecimal totalInAmount = BigDecimal.ZERO;
							BigDecimal totalOutAmount = BigDecimal.ZERO;
							Long totalPassengerCount = Long.valueOf(0);
							int totalRowNum = 0;

							for (int i = 1; i < rownum; i++) {
								ReportCompare compare = new ReportCompare();
								compare.setReportRownum(Long.valueOf(i + 1));
								totalRowNum += (i + 1);

								int tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i)
											.getContents();
									subPnr = Constant.toUpperCase(subPnr,
											new Long(6));
									if ("".equals(subPnr) == true
											&& preCompare.getSubPnr() != null) {
										subPnr = preCompare.getSubPnr();
									}
									compare.setSubPnr(subPnr);
								}

								tempIndex = reportIndex
										.getIndexValueByName("airOrderNo");
								if (tempIndex >= 0) {
									String airOrderNo = sheet.getCell(
											tempIndex, i).getContents();
									airOrderNo = StringUtil
											.removeChiness(airOrderNo);
									airOrderNo = Constant.toUpperCase(
											airOrderNo, new Long(30));
									if ("".equals(airOrderNo) == true
											&& preCompare.getAirOrderNo() != null) {
										airOrderNo = preCompare.getAirOrderNo();
									}
									compare.setAirOrderNo(airOrderNo);
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
									if (passengerCount == 0
											&& preCompare.getPassengerCount() > 0) {
										passengerCount = preCompare
												.getPassengerCount();
									}
									compare.setPassengerCount(passengerCount);
									totalPassengerCount += passengerCount;
								}

								tempIndex = reportIndex
										.getIndexValueByName("inAccount");
								if (tempIndex >= 0) {
									String inAccountName = sheet.getCell(
											tempIndex, i).getContents();
									inAccountName = Constant.toUpperCase(
											inAccountName, new Long(30));
									if ("".equals(inAccountName) == true
											&& preCompare.getInAccountName() != null) {
										inAccountName = preCompare
												.getInAccountName();
									}
									compare.setInAccountName(inAccountName);
								}

								tempIndex = reportIndex
										.getIndexValueByName("outAccount");
								if (tempIndex >= 0) {
									String outAccountName = sheet.getCell(
											tempIndex, i).getContents();
									outAccountName = Constant.toUpperCase(
											outAccountName, new Long(30));
									if ("".equals(outAccountName) == true
											&& preCompare.getOutAccountName() != null) {
										outAccountName = preCompare
												.getOutAccountName();
									}
									compare.setOutAccountName(outAccountName);
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
									if ("".equals(inAmountStr) == true
											&& preCompare.getInAmount() != null) {
										inAmount = preCompare.getInAmount();
									}
									compare.setInAmount(inAmount);
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
									if ("".equals(outAmountStr) == true
											&& preCompare.getOutAmount() != null) {
										outAmount = preCompare.getOutAmount();
									}
									compare.setOutAmount(outAmount);
									totalOutAmount = totalOutAmount
											.add(outAmount);
								}
								compare.setType(type);
								compare.setStatus(status);

								reportCompareList.add(compare);
								preCompare = compare;

								// if("".equals(Constant.toString(tempTicketNumber))==false){
								// reportCompareList=ReportCompareUtil.getCompareListByTempTicket(reportCompareList,compare,tempTicketNumber);
								// tempTicketNumber="";
								// }
							}
							totalCompare.setTotalInAmount(totalInAmount);
							totalCompare.setTotalOutAmount(totalOutAmount);
							totalCompare
									.setTotalPassengerCount(totalPassengerCount);
							totalCompare.setTotalRowNum(totalRowNum);
							request.getSession().setAttribute(
									"totalReportCompare", totalCompare);
						} else {
							System.out.println("=======>>rownum not > 0");
						}
					} else {
						System.out.println("=======>>sheet is not exists");
					}
					book.close();
				} else {
					System.out.println("=======>>workbook is not exists");
				}
			} else {
				System.out.println("=======>>reportFile is not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析平台报表异常。。" + e.getMessage());
		}
		System.out
				.println("=======>>import platform report to compareList size:"
						+ reportCompareList.size());
		reportCompareList = sortListBySubPnr(reportCompareList);
		return reportCompareList;
	}

	/**
	 * 导入BSP报表文件
	 */
	public HttpServletRequest insertBSPReport(ReportCompare reportCompare,
			PlatformReportIndex reportIndex, HttpServletRequest request)
			throws AppException {
		long a = System.currentTimeMillis();

		List<ReportCompare> reportCompareList = getBSPReportCompareList(
				reportCompare, reportIndex, request);

		request.getSession().setAttribute("reportCompareList",
				reportCompareList);
		request.getSession().setAttribute("reportCompareListSize",
				reportCompareList.size());

		long b = System.currentTimeMillis();
		System.out.println(" over insertBSPReport  time:" + ((b - a) / 1000)
				+ "s");
		return request;
	}

	/**
	 * 对比BSP报表
	 * 
	 * @param HttpServletRequest
	 * @param List
	 *            <PlatformCompare>
	 */
	@SuppressWarnings("unchecked")
	public String compareBSPReport(HttpServletRequest request)
			throws AppException {
		List<ReportCompare> reportCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("reportCompareList");

		List<ReportCompare> orderCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("orderCompareList");

		List<ReportCompare> problemCompareList1 = getPlatformCompareResult(
				new Long(1), reportCompareList, orderCompareList);
		List<ReportCompare> problemCompareList2 = getPlatformCompareResult(
				new Long(2), reportCompareList, orderCompareList);

		request.getSession().setAttribute("problemCompareList1",
				problemCompareList1);// -对账只存在于本系统
		request.getSession().setAttribute("problemCompareList1Size",
				problemCompareList1.size());

		request.getSession().setAttribute("problemCompareList2",
				problemCompareList2);// -对账只存在于上传文件
		request.getSession().setAttribute("problemCompareList2Size",
				problemCompareList2.size());

		return "";
	}

	/**
	 * 获取BSP报表对比结果
	 * 
	 * @param type
	 *            1:-对账只存在于本系统 2:-对账只存在于上传文件
	 * 
	 * @List<PlatformCompare> reportCompareList 解析报表后的记录
	 * @List<PlatformCompare> orderCompareList 系统内符合条件的记录
	 */
	private List<ReportCompare> getBSPCompareResult(long type,
			List<ReportCompare> reportCompareList,
			List<ReportCompare> orderCompareList) throws AppException {
		List<ReportCompare> problemCompareList = new ArrayList<ReportCompare>();
		if (reportCompareList != null && orderCompareList != null) {
			if (Constant.toLong(type) == 1) {
				for (int i = 0; i < orderCompareList.size(); i++) {
					ReportCompare compare = orderCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < reportCompareList.size(); j++) {
						ReportCompare order = reportCompareList.get(j);
						flag = ReportCompare.compareBSPReport(compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于本系统-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			} else if (Constant.toLong(type) == 2) {
				for (int i = 0; i < reportCompareList.size(); i++) {
					ReportCompare compare = reportCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < orderCompareList.size(); j++) {
						ReportCompare order = orderCompareList.get(j);
						flag = ReportCompare.compareBSPReport(compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于上传文件-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			}
		}
		return problemCompareList;
	}

	/**
	 * 从系统内读取符合对比条件的记录(BSP)
	 * 
	 * @PlatformCompare reportCompare 搜索条件
	 */
	public List<ReportCompare> getBSPOrderCompareList(
			ReportCompare reportCompare) throws AppException {
		List<ReportCompare> compareList = new ArrayList<ReportCompare>();

		String beginDateStr = reportCompare.getBeginDateStr();
		String endDateStr = reportCompare.getEndDateStr();

		String businessType = AirticketOrder.BUSINESSTYPE__2 + "";
		String tranType = AirticketOrder.TRANTYPE__2 + "";
		try {
			String platformId = "";
			List<Platform> platformList = PlatComAccountStore
					.getBSPBuyPlatform();
			for (int i = 0; i < platformList.size(); i++) {
				Platform platform = platformList.get(i);
				if (platform != null) {
					platformId += platform.getId() + ",";
				}
			}

			if (platformId.length() > 1) {
				platformId = platformId.substring(0, platformId.length() - 1);
			}

			List<ReportCompare> tempCompareList = reportCompareDAO
					.listCompareOrder(platformId, beginDateStr, endDateStr,
							businessType, tranType, AirticketOrder.TICKETTYPE_1
									+ "");

			List<AirticketOrder> orderList = new ArrayList<AirticketOrder>();
			for (int i = 0; i < tempCompareList.size(); i++) {
				ReportCompare tempCompare = tempCompareList.get(i);
				AirticketOrder order = tempCompare.getOrder();
				orderList.add(order);
			}
			System.out.println("====填充AirticketeOrder SUCCESS...");
			for (int i = 0; i < orderList.size(); i++) {
				AirticketOrder order = orderList.get(i);
				if (order != null) {
					Set flights = order.getFlights();
					Set passengers = order.getPassengers();
					for (Iterator iterator2 = passengers.iterator(); iterator2
							.hasNext();) {
						Passenger passenger = (Passenger) iterator2.next();
						ReportCompare compare = new ReportCompare(order,
								passenger);
						compareList.add(compare);
					}

				} else {
					System.out.println("=========orderList order is null " + i);
				}
			}
			System.out.println("系统内符合条件的BSPCompare：" + compareList.size());
		} catch (AppException e) {
			e.printStackTrace();
		}
		return compareList;
	}

	/**
	 * 根据索引设置，解析BSP报表文件
	 */
	private List<ReportCompare> getBSPReportCompareList(
			ReportCompare reportCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException {
		List<ReportCompare> reportCompareList = new ArrayList<ReportCompare>();

		String reportFilePath = Constant.PROJECT_PLATFORMREPORTS_PATH
				+ File.separator + reportCompare.fileName;

		String requestURL = Constant.toString(request.getRequestURL()
				.toString());
		if (requestURL.indexOf("tsms.fdays.com") < 0) {
			reportFilePath = "D:" + File.separator + reportFilePath;// 开发环境
		}

		File reportFile = new File(reportFilePath);
		System.out.println("reportFile:" + reportFile);
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							ReportCompare preCompare = new ReportCompare();

							for (int i = 1; i < rownum; i++) {
								ReportCompare compare = new ReportCompare();
								compare.setReportRownum(Long.valueOf(i + 1));

								int tempIndex = reportIndex
										.getIndexValueByName("ticketNumber");
								if (tempIndex >= 0) {
									String ticketNumber = sheet.getCell(
											tempIndex, i).getContents();
									ticketNumber = Constant.toUpperCase(
											ticketNumber, new Long(15));
									if ("".equals(ticketNumber) == true
											&& preCompare.getTicketNumber() != null) {
										ticketNumber = preCompare
												.getTicketNumber();
									}
									compare.setTicketNumber(ticketNumber);
								}

								tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i)
											.getContents();
									subPnr = Constant.toUpperCase(subPnr,
											new Long(6));
									if ("".equals(subPnr) == true
											&& preCompare.getSubPnr() != null) {
										subPnr = preCompare.getSubPnr();
									}
									compare.setSubPnr(subPnr);
								}

								tempIndex = reportIndex
										.getIndexValueByName("outAmount");
								if (tempIndex >= 0) {
									String outAmountStr = sheet.getCell(
											tempIndex, i).getContents();
									outAmountStr = outAmountStr.replaceAll(
											",|，", "");
									outAmountStr = Constant.toUpperCase(
											outAmountStr, new Long(10));
									BigDecimal outAmount = Constant
											.toBigDecimal(outAmountStr);
									if ("".equals(outAmountStr) == true
											&& preCompare.getOutAmount() != null) {
										outAmount = preCompare.getOutAmount();
									}
									compare.setOutAmount(outAmount);
								}
								compare.setStatus(ReportCompare.STATES_1);
								reportCompareList.add(compare);
								preCompare = compare;

								// if("".equals(Constant.toString(tempTicketNumber))==false){
								// reportCompareList=ReportCompareUtil.getCompareListByTempTicket(reportCompareList,compare,tempTicketNumber);
								// tempTicketNumber="";
								// }
							}
						} else {
							System.out.println("=======>>rownum not > 0");
						}
					} else {
						System.out.println("=======>>sheet is not exists");
					}
					book.close();
				} else {
					System.out.println("=======>>workbook is not exists");
				}
			} else {
				System.out.println("=======>>reportFile is not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析BSP报表异常。。" + e.getMessage());
		}
		System.out.println("=======>>import bsp report to compareList size:"
				+ reportCompareList.size());
		reportCompareList = sortListBySubPnr(reportCompareList);
		return reportCompareList;
	}

	/**
	 * 导入网电报表文件
	 */
	public HttpServletRequest insertNetworkReport(ReportCompare reportCompare,
			PlatformReportIndex reportIndex, HttpServletRequest request)
			throws AppException {
		long a = System.currentTimeMillis();

		List<ReportCompare> reportCompareList = getNetworkReportCompareList(
				reportCompare, reportIndex, request);

		request.getSession().setAttribute("reportCompareList",
				reportCompareList);
		request.getSession().setAttribute("reportCompareListSize",
				reportCompareList.size());

		long b = System.currentTimeMillis();
		System.out.println(" over insertNetworkReport  time:"
				+ ((b - a) / 1000) + "s");
		return request;
	}

	/**
	 * 对比网电报表
	 * 
	 * @param HttpServletRequest
	 * @param List
	 *            <PlatformCompare>
	 */
	@SuppressWarnings("unchecked")
	public String compareNetworkReport(HttpServletRequest request)
			throws AppException {
		List<ReportCompare> reportCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("reportCompareList");

		List<ReportCompare> orderCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("orderCompareList");

		List<ReportCompare> problemCompareList1 = getNetworkCompareResult(
				new Long(1), reportCompareList, orderCompareList);
		List<ReportCompare> problemCompareList2 = getNetworkCompareResult(
				new Long(2), reportCompareList, orderCompareList);

		request.getSession().setAttribute("problemCompareList1",
				problemCompareList1);// -对账只存在于本系统
		request.getSession().setAttribute("problemCompareList1Size",
				problemCompareList1.size());

		request.getSession().setAttribute("problemCompareList2",
				problemCompareList2);// -对账只存在于上传文件
		request.getSession().setAttribute("problemCompareList2Size",
				problemCompareList2.size());

		return "";
	}

	/**
	 * 获取网电报表对比结果
	 * 
	 * @param type
	 *            1:-对账只存在于本系统 2:-对账只存在于上传文件
	 * 
	 * @List<PlatformCompare> reportCompareList 解析报表后的记录
	 * @List<PlatformCompare> orderCompareList 系统内符合条件的记录
	 */
	private List<ReportCompare> getNetworkCompareResult(long type,
			List<ReportCompare> reportCompareList,
			List<ReportCompare> orderCompareList) throws AppException {
		List<ReportCompare> problemCompareList = new ArrayList<ReportCompare>();
		if (reportCompareList != null && orderCompareList != null) {
			if (Constant.toLong(type) == 1) {
				for (int i = 0; i < orderCompareList.size(); i++) {
					ReportCompare compare = orderCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < reportCompareList.size(); j++) {
						ReportCompare order = reportCompareList.get(j);
						flag = ReportCompare.compareBSPReport(compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于本系统-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			} else if (Constant.toLong(type) == 2) {
				for (int i = 0; i < reportCompareList.size(); i++) {
					ReportCompare compare = reportCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < orderCompareList.size(); j++) {
						ReportCompare order = orderCompareList.get(j);
						flag = ReportCompare.compareBSPReport(compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于上传文件-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			}
		}
		return problemCompareList;
	}

	/**
	 * 从系统内读取符合对比条件的记录(Network)
	 * 
	 * @PlatformCompare reportCompare 搜索条件
	 */
	public List<ReportCompare> getNetworkOrderCompareList(
			ReportCompare reportCompare) throws AppException {
		List<ReportCompare> compareList = new ArrayList<ReportCompare>();

		String beginDateStr = reportCompare.getBeginDateStr();
		String endDateStr = reportCompare.getEndDateStr();

		String businessType = AirticketOrder.BUSINESSTYPE__2 + "";
		String tranType = AirticketOrder.TRANTYPE__2 + "";
		try {
			String platformId = "";
			List<Platform> platformList = PlatComAccountStore
					.getNetworkBuyPlatform();
			for (int i = 0; i < platformList.size(); i++) {
				Platform platform = platformList.get(i);
				if (platform != null) {
					platformId += platform.getId() + ",";
				}
			}

			if (platformId.length() > 1) {
				platformId = platformId.substring(0, platformId.length() - 1);
			}

			List<ReportCompare> tempCompareList = reportCompareDAO
					.listCompareOrder(platformId, beginDateStr, endDateStr,
							businessType, tranType, AirticketOrder.TICKETTYPE_1
									+ "");

			List<AirticketOrder> orderList = new ArrayList<AirticketOrder>();
			for (int i = 0; i < tempCompareList.size(); i++) {
				ReportCompare tempCompare = tempCompareList.get(i);
				AirticketOrder order = tempCompare.getOrder();
				orderList.add(order);
			}
			System.out.println("====填充AirticketeOrder SUCCESS...");
			for (int i = 0; i < orderList.size(); i++) {
				AirticketOrder order = orderList.get(i);
				if (order != null) {
					Set flights = order.getFlights();
					Set passengers = order.getPassengers();
					for (Iterator iterator2 = passengers.iterator(); iterator2
							.hasNext();) {
						Passenger passenger = (Passenger) iterator2.next();
						ReportCompare compare = new ReportCompare(order,
								passenger);
						compareList.add(compare);
					}
				} else {
					System.out.println("=========orderList order is null " + i);
				}
			}
			System.out.println("系统内符合条件的NetworkCompare：" + compareList.size());
		} catch (AppException e) {
			e.printStackTrace();
		}
		return compareList;
	}

	/**
	 * 根据索引设置，解析网电报表文件
	 */
	private List<ReportCompare> getNetworkReportCompareList(
			ReportCompare reportCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException {
		List<ReportCompare> reportCompareList = new ArrayList<ReportCompare>();

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Long status = ReportCompare.STATES_1;

		String beginDateStr = reportCompare.getBeginDateStr();
		String endDateStr = reportCompare.getEndDateStr();

		Timestamp beginTime = null;
		if (beginDateStr != null && "".equals(beginDateStr) == false) {
			beginTime = DateUtil.getTimestamp(beginDateStr,
					"yyyy-MM-dd HH:mm:ss");
		}
		Timestamp endTime = null;
		if (endDateStr != null && "".equals(endDateStr) == false) {
			endTime = DateUtil.getTimestamp(endDateStr, "yyyy-MM-dd HH:mm:ss");
		}
		String userNo = uri.getUser().getUserNo();
		String SessionID = request.getSession().getId();

		String reportFilePath = Constant.PROJECT_PLATFORMREPORTS_PATH
				+ File.separator + reportCompare.fileName;

		String requestURL = Constant.toString(request.getRequestURL()
				.toString());
		if (requestURL.indexOf("tsms.fdays.com") < 0) {
			reportFilePath = "D:" + File.separator + reportFilePath;// 开发环境
		}

		File reportFile = new File(reportFilePath);
		System.out.println("reportFile:" + reportFile);
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							ReportCompare preCompare = new ReportCompare();

							for (int i = 1; i < rownum; i++) {
								ReportCompare compare = new ReportCompare();
								compare.setReportRownum(Long.valueOf(i + 1));

								int tempIndex = reportIndex
										.getIndexValueByName("ticketNumber");
								if (tempIndex >= 0) {
									String ticketNumber = sheet.getCell(
											tempIndex, i).getContents();
									ticketNumber = Constant.toUpperCase(
											ticketNumber, new Long(15));
									if ("".equals(ticketNumber) == true
											&& preCompare.getTicketNumber() != null) {
										ticketNumber = preCompare
												.getTicketNumber();
									}
									compare.setTicketNumber(ticketNumber);
								}

								tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i)
											.getContents();
									subPnr = Constant.toUpperCase(subPnr,
											new Long(6));
									if ("".equals(subPnr) == true
											&& preCompare.getSubPnr() != null) {
										subPnr = preCompare.getSubPnr();
									}
									compare.setSubPnr(subPnr);
								}

								tempIndex = reportIndex
										.getIndexValueByName("outAmount");
								if (tempIndex >= 0) {
									String outAmountStr = sheet.getCell(
											tempIndex, i).getContents();
									outAmountStr = outAmountStr.replaceAll(
											",|，", "");
									outAmountStr = Constant.toUpperCase(
											outAmountStr, new Long(10));
									BigDecimal outAmount = Constant
											.toBigDecimal(outAmountStr);
									if ("".equals(outAmountStr) == true
											&& preCompare.getOutAmount() != null) {
										outAmount = preCompare.getOutAmount();
									}
									compare.setOutAmount(outAmount);
								}

								compare.setStatus(status);
								reportCompareList.add(compare);
								preCompare = compare;

								// if("".equals(Constant.toString(tempTicketNumber))==false){
								// reportCompareList=ReportCompareUtil.getCompareListByTempTicket(reportCompareList,compare,tempTicketNumber);
								// tempTicketNumber="";
								// }
							}
						} else {
							System.out.println("=======>>rownum not > 0");
						}
					} else {
						System.out.println("=======>>sheet is not exists");
					}
					book.close();
				} else {
					System.out.println("=======>>workbook is not exists");
				}
			} else {
				System.out.println("=======>>reportFile is not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析网电报表异常。。" + e.getMessage());
		}
		System.out
				.println("=======>>import network report to compareList size:"
						+ reportCompareList.size());
		reportCompareList = sortListBySubPnr(reportCompareList);
		return reportCompareList;
	}

	/**
	 * 导入Bank(银行/支付平台)报表文件
	 */
	public HttpServletRequest insertBankReport(ReportCompare reportCompare,
			PlatformReportIndex reportIndex, HttpServletRequest request)
			throws AppException {
		long a = System.currentTimeMillis();

		List<ReportCompare> reportCompareList = getBankReportCompareList(
				reportCompare, reportIndex, request);

		request.getSession().setAttribute("reportCompareList",
				reportCompareList);
		request.getSession().setAttribute("reportCompareListSize",
				reportCompareList.size());

		long b = System.currentTimeMillis();
		System.out.println(" over insertBankReport  time:" + ((b - a) / 1000)
				+ "s");
		return request;
	}

	/**
	 * 对比Bank(银行/支付平台)报表
	 * 
	 * @param HttpServletRequest
	 * @param List
	 *            <PlatformCompare>
	 */
	@SuppressWarnings("unchecked")
	public String compareBankReport(HttpServletRequest request)
			throws AppException {
		List<ReportCompare> reportCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("reportCompareList");

		List<ReportCompare> orderCompareList = (List<ReportCompare>) request
				.getSession().getAttribute("orderCompareList");

		List<ReportCompare> problemCompareList1 = getPlatformCompareResult(
				new Long(1), reportCompareList, orderCompareList);
		List<ReportCompare> problemCompareList2 = getPlatformCompareResult(
				new Long(2), reportCompareList, orderCompareList);

		request.getSession().setAttribute("problemCompareList1",
				problemCompareList1);// -对账只存在于本系统
		request.getSession().setAttribute("problemCompareList1Size",
				problemCompareList1.size());

		request.getSession().setAttribute("problemCompareList2",
				problemCompareList2);// -对账只存在于上传文件
		request.getSession().setAttribute("problemCompareList2Size",
				problemCompareList2.size());

		return "";
	}

	/**
	 * 获取Bank(银行/支付平台)报表对比结果
	 * 
	 * @param type
	 *            1:-对账只存在于本系统 2:-对账只存在于上传文件
	 * 
	 * @List<PlatformCompare> reportCompareList 解析报表后的记录
	 * @List<PlatformCompare> orderCompareList 系统内符合条件的记录
	 */
	private List<ReportCompare> getBankCompareResult(long type,
			List<ReportCompare> reportCompareList,
			List<ReportCompare> orderCompareList) throws AppException {
		List<ReportCompare> problemCompareList = new ArrayList<ReportCompare>();
		if (reportCompareList != null && orderCompareList != null) {
			if (Constant.toLong(type) == 1) {
				for (int i = 0; i < orderCompareList.size(); i++) {
					ReportCompare compare = orderCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < reportCompareList.size(); j++) {
						ReportCompare order = reportCompareList.get(j);
						flag = ReportCompare.compareBSPReport(compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于本系统-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			} else if (Constant.toLong(type) == 2) {
				for (int i = 0; i < reportCompareList.size(); i++) {
					ReportCompare compare = reportCompareList.get(i);
					boolean flag = false;
					for (int j = 0; j < orderCompareList.size(); j++) {
						ReportCompare order = orderCompareList.get(j);
						flag = ReportCompare.compareBSPReport(compare, order);
						if (flag) {
							break;
						}
					}
					if (flag) {
						// System.out.println("核对OK");
					} else {
						System.out.println("--------对账只存在于上传文件-问题单--------");
						System.out.println("subPnr:" + compare.getSubPnr()
								+ "--orderNo:" + compare.getAirOrderNo());
						problemCompareList.add(compare);
					}
				}
			}
		}
		problemCompareList = sortListBySubPnr(problemCompareList);
		return problemCompareList;
	}

	/**
	 * 从系统内读取符合对比条件的记录Bank(银行/支付平台)
	 * 
	 * @PlatformCompare reportCompare 搜索条件
	 */
	public List<ReportCompare> getBankOrderCompareList(
			ReportCompare reportCompare) throws AppException {
		List<ReportCompare> compareList = new ArrayList<ReportCompare>();

		String beginDateStr = reportCompare.getBeginDateStr();
		String endDateStr = reportCompare.getEndDateStr();

		String accountId = Constant.toString(reportCompare.getAccountId() + "");

		String businessType = AirticketOrder.BUSINESSTYPE__2 + "";
		String tranType = AirticketOrder.TRANTYPE__2 + "";
		String statementType = Statement.SUBTYPE_10 + ","
				+ Statement.SUBTYPE_11 + "," + Statement.SUBTYPE_20 + ","
				+ Statement.SUBTYPE_21;
		try {

			List<ReportCompare> tempCompareList = reportCompareDAO
					.listCompareOrder(accountId, beginDateStr, endDateStr,
							businessType, tranType, AirticketOrder.TICKETTYPE_1
									+ "", statementType);

			List<AirticketOrder> orderList = new ArrayList<AirticketOrder>();
			for (int i = 0; i < tempCompareList.size(); i++) {
				ReportCompare tempCompare = tempCompareList.get(i);
				AirticketOrder order = tempCompare.getOrder();
				orderList.add(order);
			}
			System.out.println("====填充AirticketeOrder SUCCESS...");
			for (int i = 0; i < orderList.size(); i++) {
				AirticketOrder order = orderList.get(i);
				if (order != null) {
					// Set flights= order.getFlights();
					// Set passengers=order.getPassengers();
					// for (Iterator iterator2 = passengers.iterator();
					// iterator2.hasNext();) {
					// Passenger passenger = (Passenger) iterator2.next();
					// PlatformCompare compare=new
					// PlatformCompare(order,passenger);
					// compareList.add(compare);
					// }
					ReportCompare compare = new ReportCompare(order);
					compareList.add(compare);
				} else {
					System.out.println("=========orderList order is null " + i);
				}
			}
			System.out.println("系统内符合条件的BankCompare：" + compareList.size());
		} catch (AppException e) {
			e.printStackTrace();
		}

		compareList = sortListBySubPnr(compareList);
		return compareList;
	}

	/**
	 * 根据索引设置，解析Bank(银行/支付平台)报表文件
	 */
	private List<ReportCompare> getBankReportCompareList(
			ReportCompare reportCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) throws AppException {
		List<ReportCompare> reportCompareList = new ArrayList<ReportCompare>();

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Long status = ReportCompare.STATES_1;

		String beginDateStr = reportCompare.getBeginDateStr();
		String endDateStr = reportCompare.getEndDateStr();

		Timestamp beginTime = null;
		if (beginDateStr != null && "".equals(beginDateStr) == false) {
			beginTime = DateUtil.getTimestamp(beginDateStr,
					"yyyy-MM-dd HH:mm:ss");
		}
		Timestamp endTime = null;
		if (endDateStr != null && "".equals(endDateStr) == false) {
			endTime = DateUtil.getTimestamp(endDateStr, "yyyy-MM-dd HH:mm:ss");
		}
		String userNo = uri.getUser().getUserNo();
		String SessionID = request.getSession().getId();

		String reportFilePath = Constant.PROJECT_PLATFORMREPORTS_PATH
				+ File.separator + reportCompare.fileName;

		String requestURL = Constant.toString(request.getRequestURL()
				.toString());
		if (requestURL.indexOf("tsms.fdays.com") < 0) {
			reportFilePath = "D:" + File.separator + reportFilePath;// 开发环境
		}

		File reportFile = new File(reportFilePath);
		System.out.println("reportFile:" + reportFile);
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							ReportCompare preCompare = new ReportCompare();

							for (int i = 1; i < rownum; i++) {
								ReportCompare compare = new ReportCompare();
								compare.setReportRownum(Long.valueOf(i + 1));

								int tempIndex = reportIndex
										.getIndexValueByName("ticketNumber");
								if (tempIndex >= 0) {
									String ticketNumber = sheet.getCell(
											tempIndex, i).getContents();
									ticketNumber = Constant.toUpperCase(
											ticketNumber, new Long(15));
									if ("".equals(ticketNumber) == true
											&& preCompare.getTicketNumber() != null) {
										ticketNumber = preCompare
												.getTicketNumber();
									}
									compare.setTicketNumber(ticketNumber);
								}

								tempIndex = reportIndex
										.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i)
											.getContents();
									subPnr = Constant.toUpperCase(subPnr,
											new Long(6));
									if ("".equals(subPnr) == true
											&& preCompare.getSubPnr() != null) {
										subPnr = preCompare.getSubPnr();
									}
									compare.setSubPnr(subPnr);
								}

								tempIndex = reportIndex
										.getIndexValueByName("outAmount");
								if (tempIndex >= 0) {
									String outAmountStr = sheet.getCell(
											tempIndex, i).getContents();
									outAmountStr = outAmountStr.replaceAll(
											",|，", "");
									outAmountStr = Constant.toUpperCase(
											outAmountStr, new Long(10));
									BigDecimal outAmount = Constant
											.toBigDecimal(outAmountStr);
									if ("".equals(outAmountStr) == true
											&& preCompare.getOutAmount() != null) {
										outAmount = preCompare.getOutAmount();
									}
									compare.setOutAmount(outAmount);
								}

								compare.setStatus(status);

								reportCompareList.add(compare);
								preCompare = compare;

								// if("".equals(Constant.toString(tempTicketNumber))==false){
								// reportCompareList=ReportCompareUtil.getCompareListByTempTicket(reportCompareList,compare,tempTicketNumber);
								// tempTicketNumber="";
								// }
							}
						} else {
							System.out.println("=======>>rownum not > 0");
						}
					} else {
						System.out.println("=======>>sheet is not exists");
					}
					book.close();
				} else {
					System.out.println("=======>>workbook is not exists");
				}
			} else {
				System.out.println("=======>>reportFile is not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("解析Bank(银行/支付平台)报表异常。。" + e.getMessage());
		}
		System.out.println("=======>>import bank report to compareList size:"
				+ reportCompareList.size());
		reportCompareList = sortListBySubPnr(reportCompareList);
		return reportCompareList;
	}

	public List<ReportCompare> sortListBySubPnr(List<ReportCompare> compareList) {

		PlatformCompareComparator comp = new PlatformCompareComparator();
		// 执行排序方法
		Collections.sort(compareList, comp);

		return compareList;
	}

	public List<ReportCompare> getCompareListByResultIdType(long resultId,
			long type) throws AppException {
		return reportCompareDAO.getCompareListByResultIdType(resultId, type);
	}

	public List<ReportCompare> getCompareListByResultId(long resultId)
			throws AppException {
		return reportCompareDAO.getCompareListByResultId(resultId);
	}

	public List getValidReportCompareList() throws AppException {
		return reportCompareDAO.getValidReportCompareList();
	}

	public List list() throws AppException {
		return reportCompareDAO.list();
	}

	public List list(ReportCompareListForm ulf) throws AppException {
		return null;
	}

	public long merge(ReportCompare compare) throws AppException {
		return reportCompareDAO.merge(compare);
	}

	public long save(ReportCompare compare) throws AppException {
		return reportCompareDAO.save(compare);
	}

	public long update(ReportCompare compare) throws AppException {
		return reportCompareDAO.update(compare);
	}

	public void setReportCompareDAO(ReportCompareDAO reportCompareDAO) {
		this.reportCompareDAO = reportCompareDAO;
	}

	public void setReportCompareResultDAO(
			ReportCompareResultDAO reportCompareResultDAO) {
		this.reportCompareResultDAO = reportCompareResultDAO;
	}

	// String tempTicketNumber="";
	// tempIndex = reportIndex.getIndexValueByName("ticketNumber");
	// if (tempIndex >= 0) {
	// String ticketNumber = sheet.getCell(tempIndex, i).getContents();
	// ticketNumber = Constant.toUpperCase(ticketNumber, new Long(300));
	// ticketNumber = ticketNumber.replaceAll("[^A-Z0-9\\|]|[\\s]", "");
	// if("".equals(ticketNumber)==true&&preCompare.getTicketNumber()!=null){
	// ticketNumber=preCompare.getTicketNumber();
	// }
	// if(ticketNumber.length()>=13){
	// compare.setTicketNumber(ticketNumber.substring(0,13));
	// }
	//																	
	// if(ticketNumber.length()>20){
	// tempTicketNumber=ticketNumber;
	// }
	// }
	//	
	// tempIndex = reportIndex.getIndexValueByName("flightCode");
	// if (tempIndex >= 0) {
	// String flightCode = sheet.getCell(tempIndex, i).getContents();
	// flightCode = Constant.toUpperCase(flightCode, new Long(6));
	// if("".equals(flightCode)==true&&preCompare.getFlightCode()!=null){
	// flightCode=preCompare.getFlightCode();
	// }
	// compare.setFlightCode(flightCode);
	// }
	// tempIndex = reportIndex.getIndexValueByName("flightClass");
	// if (tempIndex >= 0) {
	// String flightClass =
	// sheet.getCell(reportIndex.getIndexValueByName("flightClass"),i).getContents();
	// flightClass = Constant.toUpperCase(flightClass, new Long(5));
	// if("".equals(flightClass)==true&&preCompare.getFlightClass()!=null){
	// flightClass=preCompare.getFlightClass();
	// }
	// compare.setFlightClass(flightClass);
	// }
	// tempIndex = reportIndex.getIndexValueByName("startPoint");
	// if (tempIndex >= 0) {
	// String startPoint = sheet.getCell(tempIndex, i).getContents();
	// startPoint = Constant.toUpperCase(startPoint, new Long(3));
	// if("".equals(startPoint)==true&&preCompare.getStartPoint()!=null){
	// startPoint=preCompare.getStartPoint();
	// }
	// compare.setStartPoint(startPoint);
	// }
	// tempIndex = reportIndex.getIndexValueByName("endPoint");
	// if (tempIndex >= 0) {
	// String endPoint = sheet.getCell(tempIndex,i).getContents();
	// endPoint = Constant.toUpperCase(endPoint,new Long(3));
	// if("".equals(endPoint)==true&&preCompare.getEndPoint()!=null){
	// endPoint=preCompare.getEndPoint();
	// }
	// compare.setEndPoint(endPoint);
	// }
	//
	// tempIndex = reportIndex.getIndexValueByName("discount");
	// if (tempIndex >= 0) {
	// String discount = sheet.getCell(tempIndex,i).getContents();
	// discount = Constant.toUpperCase(discount,new Long(3));
	// if("".equals(discount)==true&&preCompare.getDiscount()!=null){
	// discount=preCompare.getDiscount();
	// }
	// compare.setDiscount(discount);
	// }
}
