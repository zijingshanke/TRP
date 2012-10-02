package com.fdays.tsms.transaction.biz;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import jxl.Sheet;
import jxl.Workbook;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.dao.PlatformCompareDAO;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class PlatformCompareBizImp implements PlatformCompareBiz {
	private PlatformCompareDAO platformCompareDAO;

	public HttpServletRequest insertPlatformReport(
			PlatformCompare platformCompare,PlatformReportIndex reportIndex,HttpServletRequest request)
			throws AppException {
		long a = System.currentTimeMillis();
		
		List<PlatformCompare> reportCompareList = getReportCompareList(
				platformCompare, reportIndex, request);
		// request.setAttribute("reportCompareList", reportCompareList);
		request.getSession().setAttribute("reportCompareList", reportCompareList);
		
		long b = System.currentTimeMillis();
		System.out.println(" over get sql data  time:" + ((b - a) / 1000) + "s");
	
		return request;
	}

	/**
	 *@param HttpServletRequest
	 *@param List<PlatformCompare> 
	 * */
	public List<PlatformCompare> comparePlatformReport(HttpServletRequest request)
			throws AppException {
		List<PlatformCompare> problemCompareList=new ArrayList<PlatformCompare>();
		
		List<PlatformCompare> reportCompareList = (List<PlatformCompare>) request
				.getSession().getAttribute("reportCompareList");

		List<PlatformCompare> orderCompareList = (List<PlatformCompare>) request
				.getSession().getAttribute("orderCompareList");		
		

		if (reportCompareList != null && orderCompareList != null) {
			System.out.println("========reportCompareList:" + reportCompareList.size());
			System.out.println("========orderCompareList:"+ orderCompareList.size());

			for (int i = 0; i < reportCompareList.size(); i++) {
				PlatformCompare compare = reportCompareList.get(i);
				boolean flag=false;
				for (int j = 0; j < orderCompareList.size(); j++) {
					PlatformCompare order = orderCompareList.get(j);
					flag=PlatformCompare.compare(compare, order);
					if(flag){
						break;
					}
				}
				if(flag){
					System.out.println("核对OK");					
				}else{
					System.out.println("--------问题单--------");
					System.out.println("flightCode:"+compare.getFlightCode()+"--ticketNum:"+compare.getTicketNumber());
					problemCompareList.add(compare);
				}				
			}
		}
		
		request.getSession().setAttribute("problemCompareList", problemCompareList);
		return problemCompareList;
	}
	
	//从系统内读取符合对比条件的记录
	public List<PlatformCompare> getOrderCompareList(PlatformCompare platformCompare)throws AppException {
		List<PlatformCompare> compareList=new ArrayList<PlatformCompare>(); 
		Long platformId = Constant.toLong(platformCompare.getPlatformId());
		Long type = Constant.toLong(platformCompare.getType());

		String beginDateStr = platformCompare.getBeginDateStr();
		String endDateStr = platformCompare.getEndDateStr();


		String businessType = "";
		String tranType = "";
		if (type == PlatformCompare.TYPE_1) {//销售
			businessType="1";
			tranType = "1";
		}if (type == PlatformCompare.TYPE_2) {//采购
			businessType="2";
			tranType = "2";
		}if (type == PlatformCompare.TYPE_13) {// 供应退废
			businessType="1";
			tranType = "3,4";
		}if (type == PlatformCompare.TYPE_14) {// 采购退废
			businessType="2";
			tranType = "3,4";
		}if (type == PlatformCompare.TYPE_15) {// 供应退
			businessType="1";
			tranType = "3";
		}if (type == PlatformCompare.TYPE_15) {// 采购退
			businessType="2";
			tranType = "3";
		}if (type == PlatformCompare.TYPE_15) {// 供应废
			businessType="1";
			tranType = "4";
		}if (type == PlatformCompare.TYPE_15) {// 采购废
			businessType="2";
			tranType = "4";
		} else {
			tranType = type + "";
		}

		try {
			List<PlatformCompare> tempCompareList = platformCompareDAO.listCompareOrder(platformId, beginDateStr, endDateStr,businessType,
							tranType, AirticketOrder.TICKETTYPE_1 + "");
			
			List<AirticketOrder> orderList=new ArrayList<AirticketOrder>(); 
			for (int i = 0; i < tempCompareList.size(); i++) {
				PlatformCompare tempCompare=tempCompareList.get(i);
				AirticketOrder order=tempCompare.getOrder();
				orderList.add(order);				
			}			
			System.out.println("====填充AirticketeOrder SUCCESS...");
			for (int i = 0; i < orderList.size(); i++) {
				AirticketOrder order=orderList.get(i);
				if(order!=null){
					Set flights= order.getFlights();
					Set passengers=order.getPassengers();
				
					for (Iterator iterator = flights.iterator(); iterator.hasNext();) {
						Flight flight = (Flight) iterator.next();
//						for (Iterator iterator2 = passengers.iterator(); iterator2.hasNext();) {
//							Passenger passenger = (Passenger) iterator2.next();
//							PlatformCompare compare=new PlatformCompare(order,flight,passenger);
//							compareList.add(compare);
//						}		
						PlatformCompare compare=new PlatformCompare(order,flight);
						compareList.add(compare);
					}			
				}else{
					System.out.println("=========orderList order is null "+i);
				}				
			}					
			System.out.println("系统内符合条件的PlatformCompare：" + compareList.size());
		} catch (AppException e) {
			e.printStackTrace();
		}
		return compareList;
	}

	public List<PlatformCompare> getReportCompareList(
			PlatformCompare platformCompare, PlatformReportIndex reportIndex,
			HttpServletRequest request) {
		List<PlatformCompare> reportCompareList = new ArrayList<PlatformCompare>();

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Long platformId = Constant.toLong(platformCompare.getPlatformId());
		Long type = Constant.toLong(platformCompare.getType());
		Long status = PlatformCompare.STATES_1;

		String beginDateStr = platformCompare.getBeginDateStr();
		String endDateStr = platformCompare.getEndDateStr();

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

		File reportFile = new File("D:" + File.separator
				+ Constant.PROJECT_PLATFORMREPORTS_PATH + File.separator
				+ platformCompare.fileName);
		System.out.println("reportFile:" + reportFile);
		try {
			if (reportFile.exists()) {
				Workbook book = Workbook.getWorkbook(reportFile);
				if (book != null) {
					Sheet sheet = book.getSheet(0);
					if (sheet != null) {
						int rownum = sheet.getRows(); // 得到总行数
						if (rownum > 0) {
							PlatformCompare preCompare = new PlatformCompare();
						
							for (int i = 1; i < rownum; i++) {
								PlatformCompare compare = new PlatformCompare();
								int tempIndex = reportIndex.getIndexValueByName("subPnr");
								if (tempIndex >= 0) {
									String subPnr = sheet.getCell(tempIndex, i).getContents();
									subPnr = Constant.toUpperCase(subPnr,new Long(6));
									if("".equals(subPnr)==true&&preCompare.getSubPnr()!=null){
										subPnr=preCompare.getSubPnr();
									}
									compare.setSubPnr(subPnr);
								}
								
								tempIndex = reportIndex.getIndexValueByName("airOrderNo");
								if (tempIndex >= 0) {
									String airOrderNo = sheet.getCell(tempIndex, i).getContents();
									airOrderNo = Constant.toUpperCase(airOrderNo,new Long(30));		
									if("".equals(airOrderNo)==true&&preCompare.getAirOrderNo()!=null){
										airOrderNo=preCompare.getAirOrderNo();
									}
									compare.setAirOrderNo(airOrderNo);
								}
								
								tempIndex = reportIndex.getIndexValueByName("payOrderNo");
								if (tempIndex >= 0) {
									String payOrderNo = sheet.getCell(tempIndex, i).getContents();
									payOrderNo = Constant.toUpperCase(payOrderNo,new Long(30));		
									if("".equals(payOrderNo)==true&&preCompare.getPayOrderNo()!=null){
										payOrderNo=preCompare.getPayOrderNo();
									}
									compare.setPayOrderNo(payOrderNo);
								}
								
								tempIndex = reportIndex.getIndexValueByName("inAccount");
								if (tempIndex >= 0) {
									String inAccountName = sheet.getCell(tempIndex, i).getContents();
									inAccountName = Constant.toUpperCase(inAccountName,new Long(30));
									if("".equals(inAccountName)==true&&preCompare.getInAccountName()!=null){
										inAccountName=preCompare.getInAccountName();
									}
									compare.setInAccountName(inAccountName);
								}
								
								tempIndex = reportIndex.getIndexValueByName("outAccount");
								if (tempIndex >= 0) {
									String outAccountName = sheet.getCell(tempIndex, i).getContents();
									outAccountName = Constant.toUpperCase(outAccountName,new Long(30));	
									if("".equals(outAccountName)==true&&preCompare.getOutAccountName()!=null){
										outAccountName=preCompare.getOutAccountName();
									}	
									compare.setOutAccountName(outAccountName);
								}
								
								tempIndex = reportIndex.getIndexValueByName("inAmount");
								if (tempIndex >= 0) {		
									String inAmountStr = sheet.getCell(tempIndex, i).getContents();
									inAmountStr = Constant.toUpperCase(inAmountStr,new Long(10));
									BigDecimal inAmount=Constant.toBigDecimal(inAmountStr);
									if("".equals(inAmountStr)==true&&preCompare.getInAmount()!=null){
										inAmount=preCompare.getInAmount();
									}
									compare.setInAmount(inAmount);
								}
								
								tempIndex = reportIndex.getIndexValueByName("outAmount");
								if (tempIndex >= 0) {		
									String outAmountStr = sheet.getCell(tempIndex, i).getContents();
									outAmountStr = Constant.toUpperCase(outAmountStr,new Long(10));
									BigDecimal outAmount=Constant.toBigDecimal(outAmountStr);
									if("".equals(outAmountStr)==true&&preCompare.getOutAmount()!=null){
										outAmount=preCompare.getOutAmount();
									}
									compare.setOutAmount(outAmount);
								}
								
								String tempTicketNumber="";
								 tempIndex = reportIndex.getIndexValueByName("ticketNumber");
								if (tempIndex >= 0) {
									String ticketNumber = sheet.getCell(tempIndex, i).getContents();
									ticketNumber = Constant.toUpperCase(ticketNumber, new Long(300));
									ticketNumber = ticketNumber.replaceAll("[^A-Z0-9\\|]|[\\s]", "");
									if("".equals(ticketNumber)==true&&preCompare.getTicketNumber()!=null){
										ticketNumber=preCompare.getTicketNumber();								
									}
									if(ticketNumber.length()>=13){
										compare.setTicketNumber(ticketNumber.substring(0,13));		
									}
																								
									if(ticketNumber.length()>20){
										tempTicketNumber=ticketNumber;
									}
								}								
								
								tempIndex = reportIndex.getIndexValueByName("flightCode");
								if (tempIndex >= 0) {
									String flightCode = sheet.getCell(tempIndex, i).getContents();
									flightCode = Constant.toUpperCase(flightCode, new Long(6));
									if("".equals(flightCode)==true&&preCompare.getFlightCode()!=null){
										flightCode=preCompare.getFlightCode();
									}
									compare.setFlightCode(flightCode);
								}
								tempIndex = reportIndex.getIndexValueByName("flightClass");
								if (tempIndex >= 0) {
									String flightClass = sheet.getCell(reportIndex.getIndexValueByName("flightClass"),i).getContents();
									flightClass = Constant.toUpperCase(flightClass, new Long(5));
									if("".equals(flightClass)==true&&preCompare.getFlightClass()!=null){
										flightClass=preCompare.getFlightClass();								
									}
									compare.setFlightClass(flightClass);
								}								
								tempIndex = reportIndex.getIndexValueByName("startPoint");
								if (tempIndex >= 0) {
									String startPoint = sheet.getCell(tempIndex, i).getContents();
									startPoint = Constant.toUpperCase(startPoint, new Long(3));
									if("".equals(startPoint)==true&&preCompare.getStartPoint()!=null){
										startPoint=preCompare.getStartPoint();
									}
									compare.setStartPoint(startPoint);
								}
								tempIndex = reportIndex.getIndexValueByName("endPoint");
								if (tempIndex >= 0) {
									String endPoint = sheet.getCell(tempIndex,i).getContents();
									endPoint = Constant.toUpperCase(endPoint,new Long(3));	
									if("".equals(endPoint)==true&&preCompare.getEndPoint()!=null){
										endPoint=preCompare.getEndPoint();
									}
									compare.setEndPoint(endPoint);
								}
							
								tempIndex = reportIndex.getIndexValueByName("discount");
								if (tempIndex >= 0) {
									String discount = sheet.getCell(tempIndex,i).getContents();
									discount = Constant.toUpperCase(discount,new Long(3));
									if("".equals(discount)==true&&preCompare.getDiscount()!=null){
										discount=preCompare.getDiscount();
									}
									compare.setDiscount(discount);
								}
								compare.setPlatformId(platformId);
								compare.setType(type);
								compare.setStatus(status);
								compare.setBeginDate(beginTime);
								compare.setEndDate(endTime);
								compare.setUserNo(userNo);
								compare.setSessionId(SessionID);

								reportCompareList.add(compare);
								preCompare=compare;
								
								
//								if("".equals(Constant.toString(tempTicketNumber))==false){
//									reportCompareList=ReportCompareUtil.getCompareListByTempTicket(reportCompareList,compare,tempTicketNumber);
//									tempTicketNumber="";
//								}
							}
						}
					}
					book.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reportCompareList;
	}
	
	public void setPlatformCompareDAO(PlatformCompareDAO platformCompareDAO) {
		this.platformCompareDAO = platformCompareDAO;
	}
}
