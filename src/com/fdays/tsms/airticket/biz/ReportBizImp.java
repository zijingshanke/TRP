package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import com.fdays.tsms.airticket.AirticketGroup;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.airticket.TeamProfit;
import com.fdays.tsms.airticket.TeamReport;
import com.fdays.tsms.airticket.GeneralReport;
import com.fdays.tsms.airticket.GeneralReportComparator;
import com.fdays.tsms.airticket.dao.ReportDAO;
import com.fdays.tsms.airticket.dao.ReportOptDAO;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.user.SysUser;
import com.fdays.tsms.user.UserStore;
import com.fdays.tsms.user.dao.UserDAO;
import com.neza.exception.AppException;

public class ReportBizImp implements ReportBiz {
	private ReportDAO reportDAO;
	private ReportOptDAO reportOptDAO;
	private UserDAO userDAO;
	
	/**
	 * 操作员收付款统计报表下载
	 */
	public ArrayList<ArrayList<Object>> downloadOptTransactionReport(
			Report report) throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("操作人");
		list_title.add("操作人姓名");
		list_title.add("订单总数 ");
		list_title.add("正常订单");
		list_title.add("改签订单");
		list_title.add("退票订单");
		list_title.add("废票订单");
		list_title.add("取消订单");
		list_title.add("卖出机票数量 ");
		list_title.add("收款金额");
		list_title.add("付款金额");		
		list_title.add("出票利润");		
		list_title.add("收退款金额");
		list_title.add("付退款金额");
		list_title.add("退票利润");
		list_title.add("总利润");		
		list_title.add("取消出票收款");
		list_title.add("取消出票退款");

		Long totalOrderNum = new Long(0);
		Long normalOrderNum = new Long(0);
		Long retireOrderNum = new Long(0);
		Long invalidOrderNum = new Long(0);
		Long umbuchenOrderNum = new Long(0);
		Long cancelOrderNum = new Long(0);
		Long saleTicketNum = new Long(0);
		java.math.BigDecimal inAmount = BigDecimal.ZERO;
		java.math.BigDecimal outAmount = BigDecimal.ZERO;
		java.math.BigDecimal drawProfits = BigDecimal.ZERO;
		java.math.BigDecimal inRetireAmount = BigDecimal.ZERO;
		java.math.BigDecimal outRetireAmount = BigDecimal.ZERO;
		java.math.BigDecimal retireProfits = BigDecimal.ZERO;
		java.math.BigDecimal inCancelAmount = BigDecimal.ZERO;
		java.math.BigDecimal outCancelAmount = BigDecimal.ZERO;
		java.math.BigDecimal totalProfits = BigDecimal.ZERO;

		list_context.add(list_title);

		List<OptTransaction> list = listOptTransaction(report);

		ArrayList<Object> list_context_item = new ArrayList<Object>();
		for (int i = 0; i < list.size(); i++) {
			OptTransaction optTransaction = list.get(i);
			list_context_item = new ArrayList<Object>();
			list_context_item.add(optTransaction.getOpterateNo());// 操作人
			list_context_item.add(optTransaction.getOpterateName());
			list_context_item.add(optTransaction.getTotalOrderNum());
			list_context_item.add(optTransaction.getNormalOrderNum());
			list_context_item.add(optTransaction.getUmbuchenOrderNum());
			list_context_item.add(optTransaction.getRetireOrderNum());
			list_context_item.add(optTransaction.getInvalidOrderNum());
			list_context_item.add(optTransaction.getCancelOrderNum());
			list_context_item.add(optTransaction.getSaleTicketNum());
			list_context_item.add(optTransaction.getInAmount());
			list_context_item.add(optTransaction.getOutAmount());
			list_context_item.add(optTransaction.getDrawProfits());
			list_context_item.add(optTransaction.getInRetireAmount());
			list_context_item.add(optTransaction.getOutRetireAmount());
			list_context_item.add(optTransaction.getRetireProfits());		
			list_context_item.add(optTransaction.getTotalProfits());			
			list_context_item.add(optTransaction.getInCancelAmount());
			list_context_item.add(optTransaction.getOutCancelAmount());
			list_context.add(list_context_item);

			// 合计累加
			totalOrderNum += optTransaction.getTotalOrderNum();
			normalOrderNum += optTransaction.getNormalOrderNum();
			retireOrderNum += optTransaction.getRetireOrderNum();
			invalidOrderNum += optTransaction.getInvalidOrderNum();
			umbuchenOrderNum += optTransaction.getUmbuchenOrderNum();
			cancelOrderNum += optTransaction.getCancelOrderNum();
			saleTicketNum += optTransaction.getSaleTicketNum();
			inAmount = inAmount.add(optTransaction.getInAmount());
			outAmount = outAmount.add(optTransaction.getOutAmount());
			drawProfits=drawProfits.add(optTransaction.getDrawProfits());
			inRetireAmount = inRetireAmount.add(optTransaction
					.getInRetireAmount());
			outRetireAmount = outRetireAmount.add(optTransaction
					.getOutRetireAmount());
			retireProfits=retireProfits.add(optTransaction.getRetireProfits());
			totalProfits=totalProfits.add(optTransaction.getTotalProfits());
			
			inCancelAmount = inCancelAmount.add(optTransaction
					.getInCancelAmount());
			outCancelAmount = outCancelAmount.add(optTransaction
					.getOutCancelAmount());
			
		}
		// 合计
		list_context_item = new ArrayList<Object>();
		list_context_item.add("");
		list_context_item.add("合 计");
		list_context_item.add(totalOrderNum);//订单总数
		list_context_item.add(normalOrderNum);
		list_context_item.add(umbuchenOrderNum);
		list_context_item.add(retireOrderNum);
		list_context_item.add(invalidOrderNum);
		list_context_item.add(cancelOrderNum);
		list_context_item.add(saleTicketNum);// 卖出机票数量
		list_context_item.add(inAmount);// 收款金额
		list_context_item.add(outAmount);
		list_context_item.add(drawProfits);//出票利润
		list_context_item.add(inRetireAmount);
		list_context_item.add(outRetireAmount);
		list_context_item.add(retireProfits);// 退票利润
		list_context_item.add(totalProfits);// 总利润
		list_context_item.add(inCancelAmount);
		list_context_item.add(outCancelAmount);

		list_context.add(list_context_item);

		return list_context;
	}

	/**
	 * 操作员收付款统计
	 */
	public List<OptTransaction> listOptTransaction(Report report)
			throws AppException {
		String userNo=Constant.toString(report.getOperator());
		if("".equals(userNo)==false&&Constant.toLong(report.getOperatorDepart())==0){
			SysUser user=userDAO.getUserByNo(userNo);
			if(user!=null){
				Long operatorDepart=Constant.toLong(user.getUserDepart());
				if(operatorDepart>0){
					report.setOperatorDepart(operatorDepart);
				}
			}			
		}
		List<OptTransaction> optList = reportOptDAO.getOptTransactionList(report);
		return optList;
	}

	/**
	 * 散票销售报表
	 */
	public List<GeneralReport> getSaleReportContent(Report report)
			throws AppException {
		List<GeneralReport> GeneralReportsList = new ArrayList<GeneralReport>();

		report.setTicketTypeGroup(AirticketOrder.TICKETTYPE_1 + "");
		report.setTranType(AirticketOrder.TRANTYPE__1 + ","+ AirticketOrder.TRANTYPE__2);
		report.setStatusGroup(AirticketOrder.GROUP_1+","+AirticketOrder.STATUS_88);

		List<AirticketOrder> orderList = reportDAO.getOrderStatementList(report);	

		List<AirticketGroup> subGroupList = AirticketGroup.getSubGroupList(orderList);
		
		for (int j = 0; j < subGroupList.size(); j++) {
			long e = System.currentTimeMillis();
			AirticketGroup tempGroup = (AirticketGroup) subGroupList.get(j);
			List<AirticketOrder> subOrderList = tempGroup.getOrderList();
			GeneralReportsList = getGeneralReportListForSale(subOrderList,GeneralReportsList);
		}
		return GeneralReportsList;
	}

	/**
	 * 退废报表
	 */
	public List<GeneralReport> getRetireReportContent(Report report)
			throws AppException {
		List<GeneralReport> tempRetireReportsList = new ArrayList<GeneralReport>();
		report.setTicketTypeGroup(AirticketOrder.TICKETTYPE_1 + "");
		report.setTranType(AirticketOrder.TRANTYPE__1 + ","
				+ AirticketOrder.TRANTYPE__2 + "," + AirticketOrder.TRANTYPE_3
				+ "," + AirticketOrder.TRANTYPE_4);
		report.setStatusGroup(AirticketOrder.GROUP_1 + ","
				+ AirticketOrder.GROUP_2 + "," + AirticketOrder.GROUP_5+","+AirticketOrder.STATUS_88);

//		List<AirticketOrder> orderList = reportDAO.getOrderList(report);
		List<AirticketOrder> orderList = reportDAO.getOrderStatementList(report);		

		long a = System.currentTimeMillis();
		List<AirticketGroup> subGroupList = AirticketGroup.getSubGroupList(orderList);
		orderList=null;
		long b = System.currentTimeMillis();
		System.out.println("========Monitor Flag====AirticketGroup.getSubGroupList time:" + ((b - a) / 1000) + "s");	
		
		for (int j = 0; j < subGroupList.size(); j++) {
			AirticketGroup tempGroup = (AirticketGroup) subGroupList.get(j);
			List<AirticketOrder> subOrderList = tempGroup.getOrderList();
			tempRetireReportsList = getTempRetireReportList(subOrderList,
					tempRetireReportsList);
		}
		long c = System.currentTimeMillis();
		System.out.println("========Monitor Flag====getList<ReportXX> time:" + ((c - b) / 1000) + "s");
		
		tempRetireReportsList = sortListByOrderTime(tempRetireReportsList);
		long d = System.currentTimeMillis();
		System.out.println("========Monitor Flag====数据排序sortListByOrderTime time:" + ((d - c) / 1000) + "s");
		
		return tempRetireReportsList;
	}

	/**
	 * 团队销售报表
	 */
	public List<TeamReport> getTeamSaleReportContent(Report report)
			throws AppException {
		List<TeamReport> GeneralReportsList = new ArrayList<TeamReport>();
		report.setTicketTypeGroup(AirticketOrder.TICKETTYPE_2 + "");
		report.setTranType(AirticketOrder.TRANTYPE__1 + ","
				+ AirticketOrder.TRANTYPE__2 + "," + AirticketOrder.TRANTYPE_3);
		report.setStatusGroup(AirticketOrder.GROUP_9 + "");

//		List<AirticketOrder> orderList = reportDAO.getOrderList(report);
		List<AirticketOrder> orderList = reportDAO.getOrderStatementList(report);		


		List<AirticketGroup> subGroupList = AirticketGroup.getSubGroupList(orderList);

		for (int j = 0; j < subGroupList.size(); j++) {
			AirticketGroup tempGroup = (AirticketGroup) subGroupList.get(j);
			List<AirticketOrder> subOrderList = tempGroup.getOrderList();
			GeneralReportsList = getTeamReportForSale(subOrderList,
					GeneralReportsList);
		}
		return GeneralReportsList;
	}

	/**
	 * 团队未返报表
	 */
	public List<TeamReport> getTeamRakeOffReportContent(
			Report report) throws AppException {
		List<TeamReport> GeneralReportsList = new ArrayList<TeamReport>();
		report.setTicketTypeGroup(AirticketOrder.TICKETTYPE_2 + "");
		report.setTranType(AirticketOrder.TRANTYPE__1 + ","
				+ AirticketOrder.TRANTYPE__2 + "," + AirticketOrder.TRANTYPE_3);
		report.setStatusGroup(AirticketOrder.GROUP_9 + "");
		report.setRakeOff(true);
//		List<AirticketOrder> orderList = reportDAO.getOrderList(report);
		List<AirticketOrder> orderList = reportDAO.getOrderStatementList(report);		


		List<AirticketGroup> subGroupList = AirticketGroup.getSubGroupList(orderList);

		for (int j = 0; j < subGroupList.size(); j++) {
			AirticketGroup tempGroup = (AirticketGroup) subGroupList.get(j);
			List<AirticketOrder> subOrderList = tempGroup.getOrderList();
			GeneralReportsList = getTeamReportForSale(subOrderList,
					GeneralReportsList);
		}
		return GeneralReportsList;
	}

	/**
	 * 组装销售报表内容
	 */
	private List<GeneralReport> getGeneralReportListForSale(
			List<AirticketOrder> orderList,
			List<GeneralReport> GeneralReportsList) throws AppException {
		// System.out.println("orderList size--->" + airticketOrderList.size());

		if (orderList != null && orderList.size() > 0) {
			AirticketOrder saleOrder = new AirticketOrder();// 卖出(同组中只有一条)
			AirticketOrder buyOrder = new AirticketOrder();// 买入(可能有多笔)

			saleOrder = getSaleAirticketOrderByList(orderList);

			if(saleOrder!=null){
			if (orderList.size() > 1) {
				for (int j = 0; j < orderList.size(); j++) {
					AirticketOrder tempOrder = (AirticketOrder) orderList
							.get(j);
					if (tempOrder == null) {
						continue;
					}		
					
					if (tempOrder.getStatus() == null) {
						continue;
					}
					if(tempOrder.getTranType() == null){
						continue;
					}
					
					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
						if (tempOrder.getStatus() == AirticketOrder.STATUS_88) {
							buyOrder=null;
						}else{
							buyOrder = tempOrder;
						}
						
						GeneralReport tsr = getGeneralReportByOrderArray(
								buyOrder, saleOrder);
						GeneralReportsList.add(tsr);							
					}
				}
			} else {
				buyOrder = null;
				GeneralReport tsr = getTempRetireReportByOrderArray(buyOrder,
						saleOrder);
				GeneralReportsList.add(tsr);
			}
		}
		}
		return GeneralReportsList;
	}

	/**
	 * 组装退废报表内容
	 */
	private List<GeneralReport> getTempRetireReportList(
			List<AirticketOrder> orderList,
			List<GeneralReport> GeneralReportsList) throws AppException {
		// System.out.println("getTempRetireReportList orderList size--->"
		// + orderList.size());

		if (orderList != null) {
			AirticketOrder saleOrder = new AirticketOrder();// 卖出(同组中只有一条)
			AirticketOrder buyOrder = new AirticketOrder();// 买入(可能有多笔)

			saleOrder = getSaleAirticketOrderByListForRetire(orderList);

			if(saleOrder!=null){
			if (orderList.size() > 1) {
				for (int j = 0; j < orderList.size(); j++) {
					AirticketOrder tempOrder = (AirticketOrder) orderList
							.get(j);
					if (tempOrder != null) {
						if (tempOrder.getStatus() == null) {
							continue;
						}
						if(tempOrder.getBusinessType() == null){
							continue;
						}												
						if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2) {// 买入
							if (tempOrder.getStatus() != AirticketOrder.STATUS_88) {
								if (filterOrderForRetireReport(saleOrder,
										tempOrder)) {
									buyOrder = tempOrder;	
									GeneralReport tsr = getTempRetireReportByOrderArray(
											buyOrder, saleOrder);
									GeneralReportsList.add(tsr);
								}
							}else{
								buyOrder=null;
								
								if(filterNormalOrderForRetireReport(saleOrder)){
									GeneralReport tsr = getTempRetireReportByOrderArray(
											buyOrder, saleOrder);
									GeneralReportsList.add(tsr);
								}
								
							}
							
						}						
					}
				}
			} else {
				buyOrder = null;
				if (filterNormalOrderForRetireReport(saleOrder)) {
					GeneralReport tsr = getTempRetireReportByOrderArray(
							buyOrder, saleOrder);
					GeneralReportsList.add(tsr);
				}
			}
		}
		}
		return GeneralReportsList;
	}

	private boolean filterOrderForRetireReport(AirticketOrder saleOrder,
			AirticketOrder buyOrder) throws AppException {
		if (saleOrder.getId() == buyOrder.getId()) {// 两条订单ID相同
			System.out.println("ERROR the same order id");
			return false;
		}

		return filterNormalOrderForRetireReport(buyOrder);
	}

	private boolean filterNormalOrderForRetireReport(AirticketOrder tempOrder) {
		String stringStroe = AirticketOrder.GROUP_FILTERSUCCESS;
		String buyStatus = tempOrder.getStatus() + "";

		if (buyStatus != null && "".equals(buyStatus) == false) {
			if (StringUtil.containsExistString(buyStatus, stringStroe)) {
				// System.out.println("tempOrder id:" + tempOrder.getId()
				// + "--status:" + tempOrder.getStatus() + "--正常订单不用导出");
				return false;
			} else {
				// System.out.println("add tempOrder id:" + tempOrder.getId()
				// + "--status:" + tempOrder.getStatus()
				// + "--非正常订单，退废报表导出");
				return true;
			}
		} else {
			System.err.println("order id:" + tempOrder.getId()
					+ "--status is null");
			return false;
		}
	}

	/***************************************************************************
	 * 销售报表获取卖出订单
	 **************************************************************************/
	private AirticketOrder getSaleAirticketOrderByList(
			List<AirticketOrder> orderList) throws AppException {
		AirticketOrder saleOrder = null;
		for (int j = 0; j < orderList.size(); j++) {
			AirticketOrder tempSaleOrder = (AirticketOrder) orderList.get(j);
			if (tempSaleOrder != null
					&& tempSaleOrder.getBusinessType() != null){
				if(tempSaleOrder.getStatus()!=null){
					if(tempSaleOrder.getStatus()!=AirticketOrder.STATUS_88){
						if(tempSaleOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1) {// 卖出
							saleOrder = tempSaleOrder;
						}
					}
				}		
			}
		}
		return saleOrder;
	}

	/**
	 * 退废报表获取卖出订单
	 * 
	 * @param 同一小组Order
	 */
	private AirticketOrder getSaleAirticketOrderByListForRetire(
			List<AirticketOrder> orderList) throws AppException {
		AirticketOrder saleOrder = null;
		for (int j = 0; j < orderList.size(); j++) {
			AirticketOrder tempSaleOrder = (AirticketOrder) orderList.get(j);
			if (tempSaleOrder != null
					&& tempSaleOrder.getBusinessType() != null) {
				if(tempSaleOrder.getStatus()!=null){
					if(tempSaleOrder.getStatus()!=AirticketOrder.STATUS_88){
						if (tempSaleOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1) {
							saleOrder = tempSaleOrder;// 卖出
						}
					}
					
				}				
			}
		}
		return saleOrder;
	}

	/**
	 * 散票---组装退废报表对象
	 */
	private GeneralReport getTempRetireReportByOrderArray(
			AirticketOrder buyOrder, AirticketOrder saleOrder) {
		GeneralReport tsr = new GeneralReport();

		tsr.setOrderNos(saleOrder, buyOrder);

		tsr.setEntryTime(saleOrder.getEntryTime());
		tsr.setEntryOperatorName(saleOrder.getEntryOperatorName());

		tsr.setSubPnr(Constant.toUpperCase(saleOrder.getSubPnr()));// 预定PNR
		tsr.setDrawPnr(saleOrder.getDrawPnr());// 出票PNR
		tsr.setBigPnr(saleOrder.getBigPnr());// 大PNR

		if (saleOrder.getPlatform() != null) {
			tsr.setSalePlatform(saleOrder.getPlatform().getName());// 卖出商
		}
		if (saleOrder.getRebate() != null) {
			tsr.setSaleRebate(saleOrder.getRebate());// 卖出商 返点
		}
		tsr.setSaleHandlingCharge(saleOrder.getHandlingCharge());// 手续费
		tsr.setSaleAirOrderNo(saleOrder.getAirOrderNo());// 卖出商订单号
	
		tsr.setOutRefundAmount(Constant.toBigDecimal(saleOrder.getOutRefundAmount()));//付退款金额

		if(saleOrder.getOutRefundTime()!=null){
			tsr.setOutRefundTime(saleOrder.getOutRefundTime());//付退款时间
		}					
		tsr.setOutRefundAccount(saleOrder.getOutRefundAccountName());//付退款账号		
		
		String tempRefundOpt = getRefundOptNameByOrder(saleOrder);			
		tsr.setOutRefundOperator(tempRefundOpt);//付退款操作人			
				
		if(saleOrder!=null&&buyOrder!=null){
			if (saleOrder.getInAmount() != null&& buyOrder.getOutAmount() != null) {
				BigDecimal profit = saleOrder.getInAmount().subtract(buyOrder.getOutAmount());
				tsr.setProfit(profit);// 利润
			}else{
				tsr.setProfit(new BigDecimal(0));
			}
		}else {
			tsr.setProfit(new BigDecimal(0));
		}		

		StringBuffer passengerName = new StringBuffer();
		StringBuffer ticketNumber = new StringBuffer();
		Set pasSet = saleOrder.getPassengers();
		int pNum = 0;
		for (Object obj : pasSet) {
			Passenger pass = (Passenger) obj;
			passengerName.append(pNum < pasSet.size() - 1 ? pass.getName()
					+ "/" : pass.getName());
			ticketNumber.append(pNum < pasSet.size() - 1 ? pass
					.getTicketNumber()
					+ "/" : pass.getTicketNumber());
			pNum++;
		}
		tsr.setPassengerName(passengerName.toString());// 乘客姓名
		tsr.setTicketNumber(ticketNumber.toString());// 票号

		tsr.setFlightsTxt(saleOrder.getFlightsTxt());
		Set fliSet = saleOrder.getFlights();
		for (Object obj : fliSet) {
			Flight flight = (Flight) obj;
			tsr.setStartPoint(flight.getStartPoint());// 出发地
			tsr.setEndPoint(flight.getEndPoint()); // 目的地
			tsr.setCyr(flight.getCyr());// 承运人
			tsr.setFlightCode(flight.getFlightCode());// 航班号
			tsr.setFlightClass(flight.getFlightClass());// 仓位
			tsr.setDiscount(flight.getDiscount());// 折扣discount
			tsr.setBoardingTime(flight.getBoardingTime());// 起飞时间
		}

		
		if (pasSet != null && pasSet.size() > 0) {
			int passengerNum = pasSet.size();
			tsr.setPassengerNumber(passengerNum);// 乘客人数
			tsr.setTicketPrice(saleOrder.getTicketPrice());// 单张票面价
			tsr.setAirportPrice(saleOrder.getAirportPrice());// 单张机建税
			tsr.setFuelPrice(saleOrder.getFuelPrice());// 单张燃油税
			tsr.setAllTicketPrice(saleOrder.getTicketPrice().multiply(
					BigDecimal.valueOf(passengerNum)));// 票面总价
			tsr.setAllAirportPrice(saleOrder.getAirportPrice().multiply(
					BigDecimal.valueOf(passengerNum)));// 总机建税
			tsr.setAllFuelPrice(saleOrder.getFuelPrice().multiply(
					BigDecimal.valueOf(passengerNum)));// 总燃油税
		} else {
			tsr.setPassengerNumber(0);
		}
		tsr.setSaleOldOrderNo(saleOrder.getOldOrderNo());
		tsr.setSaleStatus(saleOrder.getStatusText());// 供应状态
		tsr.setSaleMemo(saleOrder.getMemo());// 供应备注
		tsr.setRetireType(saleOrder.getRetireTypeInfo());

		if (buyOrder == null) {
			tsr.setBuyOldOrderNo("‘");
			tsr.setBuyPlatform("");// 买入商
			tsr.setBuyRebate(BigDecimal.ZERO);
			tsr.setBuyHandlingCharge(BigDecimal.ZERO);// 手续费（卖出）
			tsr.setBuyAirOrderNo("");// 买入商订单号
			tsr.setOutAmount(BigDecimal.ZERO);// 出票付款金额					
			tsr.setReportOutAmount(BigDecimal.ZERO);// 报表支出
			tsr.setProfit(new BigDecimal(0));// 利润
			tsr.setInRefundTime(null);//收退款时间
			tsr.setInRefundAccount("");//收退款账号
			tsr.setInRefundAmount(BigDecimal.ZERO);//（退款金额）	
			tsr.setBuyStatus("");// 采购状态
			tsr.setBuyMemo("");// 采购备注
		} else {
			tsr.setBuyOldOrderNo(buyOrder.getOldOrderNo());
			if (buyOrder.getPlatform() != null) {
				tsr.setBuyPlatform(buyOrder.getPlatform().getName());// 买入商
			}				
			tsr.setBuyRebate(Constant.toBigDecimal(buyOrder.getRebate()));// 买入商 返点

			tsr.setBuyHandlingCharge(buyOrder.getHandlingCharge());// 手续费（卖出）
			tsr.setBuyAirOrderNo(buyOrder.getAirOrderNo());// 买入商订单号
			
			if(buyOrder.getTotalAmount()!=null){//出票付款金额
				tsr.setOutAmount(buyOrder.getTotalAmount());
			}			
			
			tsr.setInRefundAmount(Constant.toBigDecimal(buyOrder.getInRefundAmount()));// 收退款金额
			tsr.setReportInAmount(Constant.toBigDecimal(buyOrder.getInRefundAmount()));// 
									
			tsr.setInRefundAccount(buyOrder.getInRefundAccountName());//收退款账号
			if(buyOrder.getInRefundTime()!=null){
				tsr.setInRefundTime(buyOrder.getInRefundTime());//收退款时间
			}
			
			tsr.setBuyStatus(buyOrder.getStatusText());// 采购状态
			tsr.setBuyMemo(buyOrder.getMemo());// 采购备注
		}
		return tsr;
	}

	/**
	 * 散票---组装销售报表对象
	 */
	private GeneralReport getGeneralReportByOrderArray(
			AirticketOrder buyOrder, AirticketOrder saleOrder) {
		GeneralReport tsr = new GeneralReport();
		tsr.setOrderNos(saleOrder, buyOrder);

		if(buyOrder!=null){
			tsr.setEntryTime(buyOrder.getEntryTime());// 订单时间
			
			if (buyOrder.getPlatform() != null) {
				tsr.setBuyPlatform(buyOrder.getPlatform().getName());// 买入商
			}
			tsr.setBuyRebate(Constant.toBigDecimal(buyOrder.getRebate()));// 买入商 返点
			
			tsr.setDrawPnr(buyOrder.getDrawPnr());// 出票PNR
			tsr.setBigPnr(buyOrder.getBigPnr());// 大PNR
			
			tsr.setFlightsTxt(buyOrder.getFlightsTxt());
			StringBuffer passengerName = new StringBuffer();
			StringBuffer ticketNumber = new StringBuffer();
				
			Set pasSet = buyOrder.getPassengers();
			int pNum = 0;
			for (Object obj : pasSet) {
				Passenger pass = (Passenger) obj;
				passengerName.append(pNum < pasSet.size() - 1 ? pass.getName()
						+ "/" : pass.getName());
				ticketNumber.append(pNum < pasSet.size() - 1 ? pass
						.getTicketNumber()
						+ "/" : pass.getTicketNumber());
				pNum++;
			}
			tsr.setPassengerName(passengerName.toString());// 乘客姓名
			tsr.setTicketNumber(ticketNumber.toString());// 票号

			tsr.setFlightsTxt(saleOrder.getFlightsTxt());
			Set fliSet = buyOrder.getFlights();
			for (Object obj : fliSet) {
				Flight flight = (Flight) obj;
				tsr.setStartPoint(flight.getStartPoint());// 出发地
				tsr.setEndPoint(flight.getEndPoint()); // 目的地
				tsr.setCyr(flight.getCyr());// 承运人
				tsr.setFlightCode(flight.getFlightCode());// 航班号
				tsr.setFlightClass(flight.getFlightClass());// 仓位
				tsr.setDiscount(flight.getDiscount());// 折扣discount
				tsr.setBoardingTime(flight.getBoardingTime());// 起飞时间
			}
			if (pasSet != null && pasSet.size() > 0) {
				tsr.setPassengerNumber(pasSet.size());// 乘客人数
			} else {
				tsr.setPassengerNumber(0);
			}

			tsr.setTicketPrice(buyOrder.getTicketPrice());// 单张票面价
			tsr.setAirportPrice(buyOrder.getAirportPrice());// 单张机建税
			tsr.setFuelPrice(buyOrder.getFuelPrice());// 单张燃油税
			tsr.setAllTicketPrice(buyOrder.getTicketPrice().multiply(
					BigDecimal.valueOf(tsr.getPassengerNumber())));// 票面总价
			tsr.setAllAirportPrice(buyOrder.getAirportPrice().multiply(
					BigDecimal.valueOf(tsr.getPassengerNumber())));// 总机建税
			tsr.setAllFuelPrice(buyOrder.getFuelPrice().multiply(
					BigDecimal.valueOf(tsr.getPassengerNumber())));// 总燃油税
			
			tsr.setBuyAirOrderNo(buyOrder.getAirOrderNo());// 买入商订单号

			tsr.setOutAmount(Constant.toBigDecimal(buyOrder.getOutAmount()));// 实际支出
			tsr.setReportOutAmount(Constant.toBigDecimal(buyOrder.getOutAmount()));// 报表支出
			tsr.setOutAccount(buyOrder.getOutAccountName());// 付款帐号
			tsr.setOutOperatorName(buyOrder.getPayOperatorName());
			
			BigDecimal profit = Constant.toBigDecimal(saleOrder.getInAmount()).subtract(Constant.toBigDecimal(buyOrder.getOutAmount()));
			tsr.setProfit(profit);// 利润

			tsr.setBuyStatus(buyOrder.getStatusText());// 采购状态		
			tsr.setBuyMemo(buyOrder.getMemo());// 采购备注
			tsr.setRetireType(buyOrder.getRetireTypeInfo());//
		}		

		if (saleOrder.getPlatform() != null) {
			tsr.setSalePlatform(saleOrder.getPlatform().getName());// 卖出商
		}		
		tsr.setSaleRebate(Constant.toBigDecimal(saleOrder.getRebate()));// 卖出商 返点		

		tsr.setSubPnr(Constant.toUpperCase(saleOrder.getSubPnr()));// 预定PNR		

		tsr.setSaleAirOrderNo(saleOrder.getAirOrderNo());// 卖出商订单号		
		tsr.setInAmount(Constant.toBigDecimal(saleOrder.getInAmount()));// 实际收入
		tsr.setReportInAmount(Constant.toBigDecimal(saleOrder.getInAmount()));//报表收入	
		tsr.setInAccount(saleOrder.getInAccountName());// 收款帐号
		
		tsr.setEntryOperatorName(saleOrder.getEntryOperatorName());//操作人
		
		tsr.setSaleStatus(saleOrder.getStatusText());// 供应状态
		tsr.setSaleMemo(saleOrder.getMemo());// 供应备注
		return tsr;
	}

	private String getRefundOptNameByOrder(AirticketOrder order) {
		String tempPayOperator = "";
		if (order != null && order.getBusinessType() != null
				&& order.getTranType() != null) {

			if (order.getTranType() == AirticketOrder.TRANTYPE__1) {
				// 卖出取消出票--付退款
				tempPayOperator = order.getOperate20();
			}
			if (order.getTranType() == AirticketOrder.TRANTYPE__2) {
				// 买入取消出票--收退款
				tempPayOperator = order.getOperate21();
			}
			if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__1
					&& order.getTranType() == AirticketOrder.TRANTYPE_3) {
				// 卖出退票--付退款
				tempPayOperator = order.getOperate43();
			}
			if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__2
					&& order.getTranType() == AirticketOrder.TRANTYPE_3) {
				// 买入退票--收退款
				tempPayOperator = order.getOperate42();
			}
			if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__1
					&& order.getTranType() == AirticketOrder.TRANTYPE_4) {
				// 卖出废票--收退款
				tempPayOperator = order.getOperate55();
			}
			if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__2
					&& order.getTranType() == AirticketOrder.TRANTYPE_4) {
				// 买入废票--付退款
				tempPayOperator = order.getOperate54();
			}
		}

		if (tempPayOperator != null && "".equals(tempPayOperator) == false) {
			return UserStore.getUserNameByNo(tempPayOperator);
		} else {
			return "";
		}
	}

	/**
	 * 下载销售报表（财务）
	 */
	public ArrayList<ArrayList<Object>> downloadSaleReport(Report report)
			throws AppException {
		String downloadDate = com.neza.tool.DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss");

		List data = getSaleReportContent(report);

		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("#销售报表<财务版>");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#查询时间：");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(downloadDate);
		list_context.add(list_title);
		list_title = new ArrayList<Object>();

		list_title.add("订单操作时间");
		list_title.add("流水号");
		list_title.add("卖出商");
		list_title.add("返点");
		list_title.add("买入商");
		list_title.add("返点");
		list_title.add("亏点");
		list_title.add("预定PNR");
		list_title.add("出票PNR");
		list_title.add("大PNR");
		list_title.add("乘客姓名");
		list_title.add("乘机人数");
		list_title.add("起始地");
		list_title.add("目地的");
		list_title.add("航程");		
		list_title.add("承运人");
		list_title.add("航班号");
		list_title.add("舱位");
		list_title.add("折扣");
		list_title.add("单张票面价");
		list_title.add("票面总价");
		list_title.add("单张机建税");
		list_title.add("总机建税");
		list_title.add("单张燃油税");
		list_title.add("总燃油税");
		list_title.add("起飞时间");
		list_title.add("票号");
		list_title.add("卖出商订单号");
		list_title.add("实际收入");
		list_title.add("报表收入");
		list_title.add("收款帐号");
		list_title.add("买入商订单号");
		list_title.add("实际支出");
		list_title.add("报表支出");
		list_title.add("付款帐号");
		list_title.add("利润");
		list_title.add("操作人");
		list_title.add("付款人");
		list_title.add("供应状态");
		list_title.add("采购状态");
		list_title.add("供应备注");
		list_title.add("采购备注");

		list_context.add(list_title);
		for (int i = 0; i < data.size(); i++) {
			GeneralReport tsr = (GeneralReport) data.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(tsr.getFormatEntryTime("yyyy-MM-dd HH:mm:ss"));// 订单时间
			list_context_item.add(tsr.getOrderNos());// 流水号
			list_context_item.add(tsr.getSalePlatform());// 卖出商
			list_context_item.add(tsr.getSaleRebate() + "%");// 卖出返点
			list_context_item.add(tsr.getBuyPlatform());// 买入商
			list_context_item.add(tsr.getBuyRebate() + "%");// 买入返点
			list_context_item.add(tsr.getPayOffPoint() + "%");// 亏点
			list_context_item.add(tsr.getSubPnr());// 预定PNR
			list_context_item.add(tsr.getDrawPnr());// 出票PNR
			list_context_item.add(tsr.getBigPnr());// 大PNR
			list_context_item.add(tsr.getPassengerName());// 乘客姓名
			list_context_item.add(tsr.getPassengerNumber());// 乘机人数
			list_context_item.add(tsr.getStartPoint());// 出发地
			list_context_item.add(tsr.getEndPoint());// 目的地
			list_context_item.add(tsr.getFlightsTxt());//航程			
			list_context_item.add(tsr.getCyr());// 承运人
			list_context_item.add(tsr.getFlightCode());// 航班号
			list_context_item.add(tsr.getFlightClass());// 仓位
			list_context_item.add(tsr.getDiscount());// 折扣
			list_context_item.add(tsr.getTicketPrice());// 单张票面价
			list_context_item.add(tsr.getAllTicketPrice());// 票面总价
			list_context_item.add(tsr.getAirportPrice());// //单张机建税
			list_context_item.add(tsr.getAllAirportPrice());// 总机建税
			list_context_item.add(tsr.getFuelPrice());// 单张燃油税
			list_context_item.add(tsr.getAllFuelPrice());// 总燃油税
			list_context_item.add(tsr.getBoardingDate());// 起飞时间
			list_context_item.add(tsr.getTicketNumber());// 票号
			list_context_item.add("’" + tsr.getSaleAirOrderNo());// 卖出商订单号
			list_context_item.add(tsr.getInAmount());// 实际收入
			list_context_item.add(tsr.getReportInAmount());// 报表收入
			list_context_item.add(tsr.getInAccount());// 收款帐号
			list_context_item.add("’" + tsr.getBuyAirOrderNo());// 买入商订单号
			list_context_item.add(tsr.getOutAmount());// 实际支出
			list_context_item.add(tsr.getReportOutAmount());// 报表支出
			list_context_item.add(tsr.getOutAccount());// 付款帐号
			list_context_item.add(tsr.getProfit());// 利润
			list_context_item.add(tsr.getEntryOperatorName());// 操作人
			list_context_item.add(tsr.getOutOperatorName());// 付款人
			list_context_item.add(tsr.getSaleStatus());// 供应状态
			list_context_item.add(tsr.getBuyStatus());// 采购状态
			list_context_item.add(tsr.getSaleMemo());// 供应备注
			list_context_item.add(tsr.getBuyMemo());// 采购备注
			list_context.add(list_context_item);
		}

		return list_context;
	}

	/**
	 * 下载销售报表（政策）
	 */
	public ArrayList<ArrayList<Object>> downloadPolicySaleReport(Report report)
			throws AppException {
		String downloadDate = com.neza.tool.DateUtil
				.getDateString("yyyy-MM-dd HH:mm:ss");

		List data = getSaleReportContent(report);

		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("#销售报表<政策版>");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#查询时间：");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(downloadDate);
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("流水号");
		list_title.add("卖出商");
		list_title.add("返点");
		list_title.add("买入商");
		list_title.add("返点");
		list_title.add("亏点");
		list_title.add("预定PNR");
		list_title.add("出票PNR");
		list_title.add("乘客姓名");
		list_title.add("乘机人数");
		list_title.add("起始地");
		list_title.add("目地的");
		list_title.add("承运人");
		list_title.add("舱位");
		list_title.add("单张票面价");
		list_title.add("票面总价");
		list_title.add("实际收入");
		list_title.add("实际支出");
		list_title.add("操作人");
		list_title.add("付款人");
		list_title.add("供应状态");
		list_title.add("采购状态");
		list_title.add("供应备注");
		list_title.add("采购备注");

		list_context.add(list_title);
		for (int i = 0; i < data.size(); i++) {
			GeneralReport tsr = (GeneralReport) data.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(tsr.getOrderNos());// 流水号
			list_context_item.add(tsr.getSalePlatform());// 卖出商
			list_context_item.add(tsr.getSaleRebate() + "%");// 卖出返点
			list_context_item.add(tsr.getBuyPlatform());// 买入商
			list_context_item.add(tsr.getBuyRebate() + "%");// 买入返点
			list_context_item.add(tsr.getPayOffPoint() + "%");// 亏点
			list_context_item.add(tsr.getSubPnr());// 预定PNR
			list_context_item.add(tsr.getDrawPnr());// 出票PNR
			list_context_item.add(tsr.getPassengerName());// 乘客姓名
			list_context_item.add(tsr.getPassengerNumber());// 乘机人数
			list_context_item.add(tsr.getStartPoint());// 出发地
			list_context_item.add(tsr.getEndPoint());// 目的地
			list_context_item.add(tsr.getCyr());// 承运人
			list_context_item.add(tsr.getFlightClass());//舱位

			list_context_item.add(tsr.getTicketPrice());// 单张票面价
			list_context_item.add(tsr.getAllTicketPrice());// 票面总价

			list_context_item.add(tsr.getInAmount());// 实际收入
			list_context_item.add(tsr.getOutAmount());// 实际支出

			list_context_item.add(tsr.getEntryOperatorName());// 操作人
			list_context_item.add(tsr.getOutOperatorName());// 付款人
			list_context_item.add(tsr.getSaleStatus());// 供应状态
			list_context_item.add(tsr.getBuyStatus());// 采购状态
			list_context_item.add(tsr.getSaleMemo());// 供应备注
			list_context_item.add(tsr.getBuyMemo());// 采购备注
			list_context.add(list_context_item);
		}

		return list_context;
	}

	/**
	 * 下载退废报表
	 */
	public ArrayList<ArrayList<Object>> downloadRetireReport(Report report)
			throws AppException {
		String downloadDate = com.neza.tool.DateUtil.getDateString("yyyy-MM-dd HH:mm:ss");

		List data = getRetireReportContent(report);
		
		long a= System.currentTimeMillis();
		
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("#退废报表");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#查询时间：");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(downloadDate);
		list_context.add(list_title);
		list_title = new ArrayList<Object>();

		list_title.add("提交时间");
		list_title.add("流水号");
		list_title.add("操作人");
		list_title.add("PNR");
		list_title.add("出票PNR");
		list_title.add("大PNR");
		list_title.add("客户姓名");
		list_title.add("退票人数");
		list_title.add("卖出商");
		list_title.add("订单号");
		list_title.add("原始订单号");
		list_title.add("买入商");
		list_title.add("订单号");
		list_title.add("原始订单号");
		list_title.add("票号");
		list_title.add("起始地");
		list_title.add("目的地");
		list_title.add("航程");	
		list_title.add("航班号");		
		list_title.add("起飞时间");
		list_title.add("退废类别");
		list_title.add("供应手续费");
		list_title.add("采购手续费");
		list_title.add("收款时间");
		list_title.add("实收金额");
		list_title.add("应收金额");
		list_title.add("出票付款金额");
		list_title.add("出票人数");
		list_title.add("单张票面价");
		list_title.add("票面总价");
		list_title.add("单张机建税");
		list_title.add("总机建税");
		list_title.add("单张燃油税");
		list_title.add("总燃油税");
		list_title.add("承运人");
		list_title.add("舱位");
		list_title.add("收款帐号");
		list_title.add("退款时间");
		list_title.add("退款金额");
		list_title.add("退款帐号");
		list_title.add("退款操作人");
		list_title.add("卖出状态");
		list_title.add("买入状态");
		list_title.add("卖出备注");
		list_title.add("买入备注");

		list_context.add(list_title);
		for (int i = 0; i < data.size(); i++) {
			GeneralReport tsr = (GeneralReport) data.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(tsr.getFormatEntryTime("yyyy-MM-dd HH:mm:ss"));// 订单时间
			list_context_item.add(tsr.getOrderNos());// 流水号
			list_context_item.add(tsr.getEntryOperatorName());// 操作人
			list_context_item.add(tsr.getSubPnr());// 预定PNR
			list_context_item.add(tsr.getDrawPnr());// 出票PNR
			list_context_item.add(tsr.getBigPnr());// 大PNR
			list_context_item.add(tsr.getPassengerName());// 乘客姓名
			list_context_item.add(tsr.getPassengerNumber());// 乘机人数
			list_context_item.add(tsr.getSalePlatform());// 卖出商
			list_context_item.add("’" + tsr.getSaleAirOrderNo());// 卖出商订单号
			list_context_item.add("’" + tsr.getSaleOldOrderNo());// 原始订单号
			list_context_item.add(tsr.getBuyPlatform());// 买入商
			list_context_item.add("’" + tsr.getBuyAirOrderNo());// 买入商订单号
			list_context_item.add("’" + tsr.getBuyOldOrderNo());// 原始订单号
			list_context_item.add(tsr.getTicketNumber());// 票号
			list_context_item.add(tsr.getStartPoint());// 出发地
			list_context_item.add(tsr.getEndPoint());// 目的地
			list_context_item.add(tsr.getFlightsTxt());//航程		
			list_context_item.add(tsr.getFlightCode());// 航班号
			list_context_item.add(tsr.getBoardingDate());// 起飞时间
			list_context_item.add(tsr.getRetireType());// 退废类别
			list_context_item.add(tsr.getSaleHandlingCharge());// 手续费（供应--卖出）
			list_context_item.add(tsr.getBuyHandlingCharge());// 手续费（采购--买入）
			
			list_context_item.add(tsr.getFormatInRefundTime());// 收款时间（退款）
			list_context_item.add(tsr.getInRefundAmount());// 实际收入(收退款金额)
			list_context_item.add(tsr.getInRefundAmount());// 应收金额
			list_context_item.add(tsr.getOutAmount());// 出票付款金额
			
			list_context_item.add(tsr.getPassengerNumber());// 出票人数
			list_context_item.add(tsr.getTicketPrice());// 单张票面价
			list_context_item.add(tsr.getAllTicketPrice());// 票面总价
			list_context_item.add(tsr.getAirportPrice());// //单张机建税
			list_context_item.add(tsr.getAllAirportPrice());// 总机建税
			list_context_item.add(tsr.getFuelPrice());// 单张燃油税
			list_context_item.add(tsr.getAllFuelPrice());// 总燃油税
			list_context_item.add(tsr.getCyr());// 承运人
			list_context_item.add(tsr.getFlightClass());// 仓位
			list_context_item.add(tsr.getInRefundAccount());// 收退款账号
			list_context_item.add(tsr.getFormatOutRefundTime());//付退款时间
			list_context_item.add(tsr.getOutRefundAmount());// 付退款金额
			list_context_item.add(tsr.getOutRefundAccount());// 付退款帐号
			list_context_item.add(tsr.getOutRefundOperator());// 付退款操作人
			list_context_item.add(tsr.getSaleStatus());// 供应状态
			list_context_item.add(tsr.getBuyStatus());// 采购状态
			list_context_item.add(tsr.getSaleMemo());// 供应备注
			list_context_item.add(tsr.getBuyMemo());// 采购备注

			list_context.add(list_context_item);
		}
		long b = System.currentTimeMillis();
		System.out.println("========Monitor Flag====数据对象集合==>ConttextItem List  time:" + ((b - a) / 1000) + "s");
		 
		return list_context;
	}

	/**
	 * 组装团队销售报表内容
	 */
	private List<TeamReport> getTeamReportForSale(
			List<AirticketOrder> airticketOrderList,
			List<TeamReport> TeamReport)
			throws AppException {
		// System.out.println("orderList size--->" + airticketOrderList.size());

		if (airticketOrderList != null && airticketOrderList.size() > 0) {
			AirticketOrder saleOrder = new AirticketOrder();// 卖出(同组中只有一条)
			AirticketOrder buyOrder = new AirticketOrder();// 买入(可能有多笔)

			saleOrder = getSaleAirticketOrderByList(airticketOrderList);

			for (int j = 0; j < airticketOrderList.size(); j++) {

				AirticketOrder tempOrder = (AirticketOrder) airticketOrderList
						.get(j);
				if (tempOrder != null
						&& tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2) {// 买入
					buyOrder = tempOrder;

					TeamReport tsr = getTempTeamSaleReportByOrderArray(
							saleOrder, buyOrder);
					TeamReport.add(tsr);
				}
			}
		}
		return TeamReport;
	}

	/**
	 * 组装团队销售报表对象
	 */
	private TeamReport getTempTeamSaleReportByOrderArray(
			AirticketOrder saleOrder, AirticketOrder buyOrder) {
		TeamReport tsr = new TeamReport();

		tsr.setOrderNos(saleOrder, buyOrder);

		if (saleOrder.getEntryTime() != null) {
			tsr.setEntryTime(saleOrder.getEntryTime());// 录单时间
		}
		tsr.setDrawer(saleOrder.getDrawer());// 出票人
		if (saleOrder.getAgent() != null) {
			tsr.setAgentName(saleOrder.getAgent().getName());// 购票客户
		}
		tsr.setFlightsTxt(saleOrder.getFlightsTxt());
		tsr.setFlightTime(saleOrder.getBoardingDatesTxt());// 航班日期
		tsr.setFlightCode(saleOrder.getFlightsCodeTxt());// 航班号
		tsr.setFlightClass(saleOrder.getFlightClassTxt());// 舱位
		tsr.setDiscount(saleOrder.getDiscountTxt());// 折扣
		tsr.setCarrier(saleOrder.getCyrsTxt());// 承运人
		tsr.setTeamAddPrice(saleOrder.getTeamaddPrice());
		tsr.setAgentAddPrice(saleOrder.getAgentaddPrice());


		TeamProfit teamProfit = new TeamProfit(saleOrder, buyOrder);

		tsr.setCommission(teamProfit.getCommission());// 现返

		tsr.setSaleRakeOff(saleOrder.getRakeOff());// 未返
		tsr.setProxy_price(saleOrder.getProxyPrice());// 应付出团代理费（未返）
		tsr.setSaleMemo(saleOrder.getMemo());//备注
		

		tsr.setEntryOperatorName(saleOrder.getEntryOperatorName());// 操作人
		tsr.setTotalTicketNumber(saleOrder.getTotalPerson());// 张数
		tsr.setTicketPrice(saleOrder.getTotalTicketPrice());// 票面价
		tsr.setIncomeretreat_charge(saleOrder.getIncomeretreatCharge());// 收退票手续费
		tsr.setIncomeTicketPrice(teamProfit.getSaleTicketPrice());// 收票款(应收票款)
		tsr.setAmountMore(teamProfit.getSaleOverPrice());// 多收票款(团队加价+客户加价)
		tsr.setTaxMore(saleOrder.getOverAirportfulePrice());// 多收税
		tsr.setTotalAirportFuelPrice(teamProfit.getTotalAirportFuelPrice());//机建燃油税

		// 航空公司
		String tempAirOrderNo = buyOrder.getAirOrderNo();
		tempAirOrderNo = tempAirOrderNo.replaceAll(",|，", "-");
		tsr.setBuyAirticketNo(tempAirOrderNo);// 订单号
		tsr.setOutAccountName(buyOrder.getOutAccountName());//支付账号
		tsr.setOutAmount(buyOrder.getOutAmount());//付款金额
		tsr.setPayTime(buyOrder.getOutTime());//支付时间
		tsr.setOutOperatorName(buyOrder.getPayOperatorName());// 支付人
		tsr.setOutMemo(buyOrder.getOutMemo());// 支付备注	
		
		tsr.setOutRefundAccountName(saleOrder.getOutRefundAccountName());
		tsr.setOutRefundAmount(saleOrder.getOutRefundAmount());
		tsr.setOutRefundMemo(saleOrder.getOutRefundMemo());
		tsr.setInRefundAccountName(buyOrder.getInRefundAccountName());
		tsr.setInRefundAmount(buyOrder.getInRefundAmount());
		tsr.setInRefundMemo(buyOrder.getInRefundMemo());
		
		tsr.setCommisson_count(buyOrder.getCommissonCount());// 返点
		tsr.setHandling_charge(buyOrder.getHandlingCharge());// 手续费
		tsr.setRakeoff_count(buyOrder.getRakeoffCount());// 后返点
		tsr.setActual_incomeretreat_charge(buyOrder.getIncomeretreatCharge());// 实付退票手续费
		tsr.setBuyRakeOff(buyOrder.getRakeOff());// 月底返代理费
		tsr.setGrossProfit(teamProfit.getGrossProfit());// 团毛利润
		tsr.setBuyTicketPrice(teamProfit.getBuyTicketPrice());// 应付票款
		tsr.setBuyTotalAmount(teamProfit.getBuyTotalAmount());// 实付款
		tsr.setRefundProfit(teamProfit.getRefundProfit());// 退票利润
		tsr.setPureProfits(teamProfit.getTotalProfit());// 净利合计
		tsr.setSaleTotalAmount(teamProfit.getSaleTotalAmount());// 总金额（实收票款）

		if (saleOrder.getTranType() == AirticketOrder.TRANTYPE_3) {
			tsr.setCommission(tsr.getCommission().compareTo(
							BigDecimal.ZERO) > 0 ? tsr.getCommission()
							.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 现返
			tsr.setAmountMore(tsr.getAmountMore().compareTo(
							BigDecimal.ZERO) > 0 ? tsr.getAmountMore()
							.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 多收票款
			tsr.setTotalTicketNumber(tsr.getTotalTicketNumber() > 0 ? -tsr
					.getTotalTicketNumber() : 0);// 张数
			tsr.setTicketPrice(tsr.getTicketPrice().compareTo(
							BigDecimal.ZERO) > 0 ? tsr.getTicketPrice()
							.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 票面价
			tsr.setIncomeretreat_charge(saleOrder.getIncomeretreatCharge()
					.compareTo(BigDecimal.ZERO) > 0 ? saleOrder
					.getIncomeretreatCharge().multiply(new BigDecimal(-1))
					: BigDecimal.ZERO);// 收退票手续费
			tsr.setIncomeTicketPrice(teamProfit.getSaleTicketPrice().compareTo(
					BigDecimal.ZERO) > 0 ? teamProfit.getSaleTicketPrice()
					.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 收票款(应收票款)
			
			tsr.setTotalAirportFuelPrice(tsr.getTotalAirportFuelPrice().compareTo(BigDecimal.ZERO) > 0 ? tsr.getTotalAirportFuelPrice()
							.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 机场税(总燃油+总基建）
			
			tsr.setBuyTicketPrice(tsr.getBuyTicketPrice().compareTo(
					BigDecimal.ZERO) > 0 ? tsr.getBuyTicketPrice()
					.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 应付票款
			tsr.setActual_incomeretreat_charge(buyOrder
							.getIncomeretreatCharge()
							.compareTo(BigDecimal.ZERO) > 0 ? buyOrder
							.getIncomeretreatCharge().multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 实付退票手续费
			
			tsr.setBuyTotalAmount(tsr.getBuyTotalAmount().compareTo(
					BigDecimal.ZERO) > 0 ? tsr.getBuyTotalAmount()
					.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 实付款
			
			tsr.setOutAmount(buyOrder.getOutAmount().compareTo(
					BigDecimal.ZERO) > 0 ? buyOrder.getOutAmount().multiply(
					new BigDecimal(-1)) : BigDecimal.ZERO);// 确认付款金额
			
			tsr.setBuyRakeOff(tsr.getBuyRakeOff().compareTo(BigDecimal.ZERO) > 0 ? tsr.getBuyRakeOff().multiply(
					new BigDecimal(-1)) : BigDecimal.ZERO);// 月底返代理费
			tsr.setGrossProfit(tsr.getGrossProfit().compareTo(
					BigDecimal.ZERO) > 0 ? tsr.getGrossProfit().multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 团毛利润
			tsr.setSaleTotalAmount(tsr.getSaleTotalAmount().compareTo(BigDecimal.ZERO) > 0 ? tsr.getSaleTotalAmount()
					.multiply(new BigDecimal(-1)) : BigDecimal.ZERO);// 总金额（实收票款）
		}

		return tsr;
	}

	public ArrayList<ArrayList<Object>> downloadTeamSaleReport(Report report)
			throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();

		List list = getTeamSaleReportContent(report);
		list_title.add("流水号");
		list_title.add("出票日期");
		list_title.add("客票类型");
		list_title.add("承运人");
		list_title.add("航段");
		list_title.add("张数");
		list_title.add("订单号");
		list_title.add("航班日期");
		list_title.add("航班号");
		list_title.add("舱位");
		list_title.add("折扣");
		list_title.add("票面价");
		list_title.add("购票客户");
		list_title.add("收退票手续费");
		list_title.add("收票款");
		list_title.add("机场税");
		list_title.add("应付票款");
		list_title.add("实付退票手续费");
		list_title.add("实付款");
		list_title.add("确认支付金额");
		list_title.add("支付账号");
		list_title.add("支付备注");
		list_title.add("支付时间");
		list_title.add("支付人");
		list_title.add("付退款金额");
		list_title.add("付退款账号");
		list_title.add("付退款备注");	
		list_title.add("收退款金额");
		list_title.add("收退款账号");
		list_title.add("收退款备注");
		list_title.add("月底返代理费");
		list_title.add("团毛利润");
		list_title.add("退票利润");
		list_title.add("多收票款");
		list_title.add("多收税");
		list_title.add("现返");
		list_title.add("未返");
		list_title.add("未返备注");
		list_title.add("净利合计");
		list_title.add("购票客户");
		list_title.add("总金额");
		list_title.add("操作人");
		list_context.add(list_title);
		ArrayList<Object> list_context_item = new ArrayList<Object>();

		long personNum = new Long(0);// 张数
		BigDecimal ticketNum = BigDecimal.ZERO;// 票面价
		BigDecimal incomeretreat_charge = BigDecimal.ZERO;// //收退票手续费
		BigDecimal incomeTicketPrice = BigDecimal.ZERO;// 收票款
		BigDecimal totalAirportFuelPrice = BigDecimal.ZERO;// 机建燃油税
		BigDecimal buyTicketPrice = BigDecimal.ZERO;// 应付票款
		BigDecimal actual_incomeretreat_charge = BigDecimal.ZERO;// 实付退票手续费
		BigDecimal buyTotalAmount = BigDecimal.ZERO;// 实付款
		BigDecimal outAmount = BigDecimal.ZERO;// 确认支付金额
		BigDecimal outRefundAmount = BigDecimal.ZERO;//付退款金额
		BigDecimal inRefundAmount = BigDecimal.ZERO;//收退款金额
		
		BigDecimal buyRakeOff = BigDecimal.ZERO;// 月底返代理费
		BigDecimal profitsInfo = BigDecimal.ZERO;// 团毛利润
		BigDecimal refundProfit = BigDecimal.ZERO;// 退票利润
		BigDecimal amountMore = BigDecimal.ZERO;// 多收票款
		BigDecimal taxMore = BigDecimal.ZERO;// 多收税
		BigDecimal commission = BigDecimal.ZERO;// 现返
		BigDecimal saleRakeOff = BigDecimal.ZERO;// 未返
		BigDecimal totalProfits = BigDecimal.ZERO;// 净利合计
		BigDecimal totalAmount = BigDecimal.ZERO;

		for (int i = 0; i < list.size(); i++) {
			TeamReport t = (TeamReport) list.get(i);
			list_context_item = new ArrayList<Object>();
			list_context_item.add(t.getOrderNos());// 流水号
			list_context_item.add(t.getFormatEntryTime("yyyy-MM-dd"));// 出票日期
			list_context_item.add(t.getDrawer());// 出票人
			list_context_item.add(t.getCarrier());// 承运人
			list_context_item.add(t.getFlightsTxt());// 航程
			list_context_item.add(t.getTotalTicketNumber());// 张数
			list_context_item.add("'" + t.getBuyAirticketNo());// 订单号
			list_context_item.add(t.getFlightTime());// 航班日期
			list_context_item.add(t.getFlightCode());// 航班号
			list_context_item.add(t.getFlightClass());// 舱位
			list_context_item.add(t.getDiscount());// 折扣
			list_context_item.add(t.getTicketPrice());// 票面价
			list_context_item.add(t.getAgentName());// 购票客户
			list_context_item.add(t.getIncomeretreat_charge());// 收退票手续费
			list_context_item.add(t.getIncomeTicketPrice());// 收票款
			list_context_item.add(t.getTotalAirportFuelPrice());// 机建燃油税 
			list_context_item.add(t.getBuyTicketPrice());// 应付票款
			list_context_item.add(t.getActual_incomeretreat_charge());// 实付退票手续费
			list_context_item.add(t.getBuyTotalAmount());// 实付款
			list_context_item.add(t.getOutAmount());// 确认支付金额
			list_context_item.add(t.getOutAccountName());// 支付账号
			list_context_item.add("'"+t.getOutMemo());// 支付备注
			list_context_item.add(t.getFormatPayTime("yyyy-MM-dd"));//支付时间
			list_context_item.add(t.getOutOperatorName());// 支付人
			list_context_item.add(t.getOutRefundAmount());//
			list_context_item.add(t.getOutRefundAccountName());//
			list_context_item.add(t.getOutRefundMemo());//			
			list_context_item.add(t.getInRefundAmount());//
			list_context_item.add(t.getInRefundAccountName());//			
			list_context_item.add(t.getInRefundMemo());//
			
			list_context_item.add(t.getBuyRakeOff());// 月底返代理费
			list_context_item.add(t.getGrossProfit());// 团毛利润
			list_context_item.add(t.getRefundProfit());// 退票利润
			list_context_item.add(t.getAmountMore());// 多收票款
			list_context_item.add(t.getTaxMore());// 多收税
			list_context_item.add(t.getCommission());// 现返
			list_context_item.add(t.getSaleRakeOff());//未返
			list_context_item.add("'"+t.getSaleMemo());//备注
			list_context_item.add(t.getPureProfits());// 净利合计
			list_context_item.add(t.getAgentName());// 购票客户
			list_context_item.add(t.getSaleTotalAmount());// 总金额
			list_context_item.add(t.getEntryOperatorName());// 操作人
			list_context.add(list_context_item);

			// 累加
			personNum += t.getTotalTicketNumber();// 张数
			ticketNum = ticketNum.add(t.getTicketPrice());// 票面价
			incomeretreat_charge = incomeretreat_charge.add(t
					.getIncomeretreat_charge());// //收退票手续费
			incomeTicketPrice = incomeTicketPrice.add(t.getIncomeTicketPrice());// 收票款
			totalAirportFuelPrice = totalAirportFuelPrice.add(t.getTotalAirportFuelPrice());// 机建燃油税//																
			buyTicketPrice = buyTicketPrice.add(t.getBuyTicketPrice());// 应付票款
			actual_incomeretreat_charge = actual_incomeretreat_charge.add(t
					.getActual_incomeretreat_charge());// 实付退票手续费
			buyTotalAmount = buyTotalAmount.add(t.getBuyTotalAmount());// 实付款
			outAmount = outAmount.add(t.getOutAmount());// 确认支付金额
			outRefundAmount =outRefundAmount.add(t.getOutRefundAmount());//付退款金额
			inRefundAmount =inRefundAmount.add(t.getInRefundAmount());//收退款金额
			
			buyRakeOff = buyRakeOff.add(t.getBuyRakeOff());// 月底返代理费
			profitsInfo = profitsInfo.add(t.getGrossProfit());// 团毛利润
			refundProfit = refundProfit.add(t.getRefundProfit());// 退票利润
			amountMore = amountMore.add(t.getAmountMore());// 多收票款
			taxMore = taxMore.add(t.getTaxMore());// 多收税
			commission = commission.add(t.getCommission());// 现返
			saleRakeOff = saleRakeOff.add(t.getSaleRakeOff());// 未返
			totalProfits = totalProfits.add(t.getPureProfits());// 净利合计
			totalAmount = totalAmount.add(t.getSaleTotalAmount());// 总金额
		}
		// 合计
		list_context_item = new ArrayList<Object>();
		list_context_item.add("合 计");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(personNum);// 张数
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");// 折扣
		list_context_item.add(ticketNum);// 票面价
		list_context_item.add(" ");
		list_context_item.add(incomeretreat_charge);// //收退票手续费
		list_context_item.add(incomeTicketPrice);// 收票款
		list_context_item.add(totalAirportFuelPrice);// 机建燃油税
		list_context_item.add(buyTicketPrice);// 应付票款
		list_context_item.add(actual_incomeretreat_charge);// 实付退票手续费
		list_context_item.add(buyTotalAmount);// 实付款
		list_context_item.add(outAmount);// 确认支付金额
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(outRefundAmount);
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(inRefundAmount);
		list_context_item.add(" ");
		list_context_item.add(" ");
		list_context_item.add(buyRakeOff);// 月底返代理费
		list_context_item.add(profitsInfo);// 团毛利润
		list_context_item.add(refundProfit);// 退票利润
		list_context_item.add(amountMore);// 多收票款
		list_context_item.add(taxMore);// 多收税
		list_context_item.add(commission);// 现返
		list_context_item.add(saleRakeOff);// 未返
		list_context_item.add(" ");
		list_context_item.add(totalProfits);// 净利合计
		list_context_item.add(" ");
		list_context_item.add(totalAmount);// 总金额
		list_context_item.add(" ");
		list_context.add(list_context_item);

		return list_context;
	}

	public ArrayList<ArrayList<Object>> downloadTeamRakeOffReport(Report report)
			throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();

		List list = getTeamRakeOffReportContent(report);

		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("流水号");
		list_title.add("出票日期");
		list_title.add("客票类型");
		list_title.add("承运人");
		list_title.add("航段");
		list_title.add("张数");
		list_title.add("订单号");
		list_title.add("航班日期");
		list_title.add("航班号");
		list_title.add("舱位");
		list_title.add("折扣");
		list_title.add("票面价");
		list_title.add("团队加价");
		list_title.add("客户加价");
		list_title.add("购票客户");
		list_title.add("未返");
		list_title.add("未返备注");
		list_title.add("收客人票款");
		list_title.add("操作人");
		list_context.add(list_title);
		ArrayList<Object> list_context_item = new ArrayList<Object>();

		for (int i = 0; i < list.size(); i++) {
			TeamReport t = (TeamReport) list.get(i);
			list_context_item = new ArrayList<Object>();
			list_context_item.add(t.getOrderNos());// 流水号
			list_context_item.add(t.getFormatEntryTime("yyyy-MM-dd"));// 出票日期
			list_context_item.add(t.getDrawer());// 出票人
			list_context_item.add(t.getCarrier());// 承运人
			list_context_item.add(t.getFlightsTxt());// 航程
			list_context_item.add(t.getTotalTicketNumber());// 张数
			list_context_item.add("'" + t.getBuyAirticketNo());// 订单号
			list_context_item.add(t.getFlightTime());// 航班日期
			list_context_item.add(t.getFlightCode());// 航班号
			list_context_item.add(t.getFlightClass());// 舱位
			list_context_item.add(t.getDiscount());// 折扣
			list_context_item.add(t.getTicketPrice());// 票面价
			list_context_item.add(t.getTeamAddPrice());// 团队加价
			list_context_item.add(t.getAgentAddPrice());// 客户加价
			list_context_item.add(t.getAgentName());// 购票客户
			list_context_item.add(t.getSaleRakeOff());// 未返
			list_context_item.add(t.getSaleMemo());// 备注
			list_context_item.add(t.getIncomeTicketPrice());// 收客户票款
			list_context_item.add(t.getEntryOperatorName());// 操作人
			list_context.add(list_context_item);
		}
		return list_context;
	}
	
	private List<GeneralReport> sortListByOrderTime(
			List<GeneralReport> reportList) {

		GeneralReportComparator comp = new GeneralReportComparator();

		Collections.sort(reportList, comp);

		return reportList;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public void setReportOptDAO(ReportOptDAO reportOptDAO) {
		this.reportOptDAO = reportOptDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	

}
