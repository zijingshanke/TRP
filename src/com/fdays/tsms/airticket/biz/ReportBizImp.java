package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fdays.tsms.airticket.AirticketGroup;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TeamAirticketOrderReport;
import com.fdays.tsms.airticket.TeamProfit;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.TempSaleReport;
import com.fdays.tsms.airticket.dao.ReportDAO;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.user.UserStore;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class ReportBizImp implements ReportBiz {
	private AirticketOrderDAO airticketOrderDAO;
	private ReportDAO reportDAO;

	/***************************************************************************
	 * 散票销售报表 sc
	 **************************************************************************/
	public List<TempSaleReport> getSaleReportContent(AirticketOrderListForm rlf)
			throws AppException {
		List<TempSaleReport> tempSaleReportsList = new ArrayList<TempSaleReport>();
		List groupIdList = reportDAO.getGroupIdForSaleReport(rlf);
		for (int i = 0; i < groupIdList.size(); i++) {
			Long groupId = (Long) groupIdList.get(i);
			System.out.println("group id:" + groupId);

			List<AirticketOrder> airticketOrderList = airticketOrderDAO.listByGroupIdAndTranType(groupId, "1,2");
			tempSaleReportsList = getTempSaleReportListForSale(airticketOrderList, tempSaleReportsList);
		}
		System.out.println("--销售报表 size:" + tempSaleReportsList.size());
		return tempSaleReportsList;
	}

	/***************************************************************************
	 * 退废报表 sc
	 **************************************************************************/
	public List<TempSaleReport> getRetireReportContent(
			AirticketOrderListForm rlf) throws AppException {
		List<TempSaleReport> tempRetireReportsList = new ArrayList<TempSaleReport>();
		List groupIdList = reportDAO.getGroupIdForRetireReport(rlf);
		for (int i = 0; i < groupIdList.size(); i++) {
			Long groupId = (Long) groupIdList.get(i);
			System.out.println("group id:" + groupId);
			List<AirticketOrder> airticketOrderList = airticketOrderDAO
					.listByGroupId(groupId);

			List<AirticketGroup> subGroupList = AirticketGroup
					.getSubGroupList(airticketOrderList);

			for (int j = 0; j < subGroupList.size(); j++) {
				AirticketGroup tempGroup = (AirticketGroup) subGroupList.get(j);
				List<AirticketOrder> subOrderList = tempGroup.getOrderList();

				tempRetireReportsList = getTempRetireReportList(subOrderList,
						tempRetireReportsList);
			}

		}
		System.out.println("--退废报表 size:" + tempRetireReportsList.size());
		return tempRetireReportsList;
	}

	/**
	 * 团队销售报表
	 */
	public List<TeamAirticketOrderReport> getTeamSaleReportContent(
			AirticketOrderListForm rlf) throws AppException {
		List<TeamAirticketOrderReport> tempSaleReportsList = new ArrayList<TeamAirticketOrderReport>();

		List groupIdList = reportDAO.getGroupIdForTeamSaleReport(rlf);
		for (int i = 0; i < groupIdList.size(); i++) {
			Long groupId = (Long) groupIdList.get(i);
			System.out.println("group id:" + groupId);

			List<AirticketOrder> airticketOrderList = airticketOrderDAO
					.listByGroupIdAndTranType(groupId, "1,2,3");
			tempSaleReportsList = getTeamReportForSale(airticketOrderList,
					tempSaleReportsList);
		}
		return tempSaleReportsList;
	}

	/**
	 * 组装销售报表内容
	 */
	private List<TempSaleReport> getTempSaleReportListForSale(
			List<AirticketOrder> airticketOrderList,
			List<TempSaleReport> tempSaleReportsList) throws AppException {
		System.out.println("orderList size--->" + airticketOrderList.size());

		if (airticketOrderList != null && airticketOrderList.size() > 0) {
			AirticketOrder saleOrder = new AirticketOrder();// 卖出(同组中只有一条)
			AirticketOrder buyOrder = new AirticketOrder();// 买入(可能有多笔)
			
			saleOrder = getSaleAirticketOrderByList(airticketOrderList);

			for (int j = 0; j < airticketOrderList.size(); j++) {

				AirticketOrder tempOrder = (AirticketOrder) airticketOrderList
						.get(j);
				if (tempOrder != null
						&& tempOrder.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
					buyOrder = tempOrder;

					TempSaleReport tsr = getTempSaleReportByOrderArray(buyOrder,saleOrder);
					tempSaleReportsList.add(tsr);
				}
			}
		}
		return tempSaleReportsList;
	}

	/**
	 * 组装退废报表内容
	 */
	private List<TempSaleReport> getTempRetireReportList(
			List<AirticketOrder> airticketOrderList,
			List<TempSaleReport> tempSaleReportsList) throws AppException {
		System.out.println("getTempRetireReportList orderList size--->"
				+ airticketOrderList.size());

		if (airticketOrderList != null) {
			AirticketOrder saleOrder = new AirticketOrder();// 卖出(同组中只有一条)
			AirticketOrder buyOrder = new AirticketOrder();// 买入(可能有多笔)

			saleOrder = getSaleAirticketOrderByListForRetire(airticketOrderList);

			if (airticketOrderList.size() > 1) {
				for (int j = 0; j < airticketOrderList.size(); j++) {
					AirticketOrder tempOrder = (AirticketOrder) airticketOrderList
							.get(j);
					if (tempOrder != null) {
						if (tempOrder.getStatus() == null) {
							continue;
						} else {
								if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2) {// 买入
									if (filterOrderForRetireReport(saleOrder, tempOrder)) {
										buyOrder = tempOrder;
										TempSaleReport tsr = getTempRetireReportByOrderArray(
												buyOrder, saleOrder);
										tempSaleReportsList.add(tsr);
									} else {
										continue;
									}
								}							
						}
					}
				}
			} else {
				buyOrder = null;
				if(filterNormalOrderForRetireReport(saleOrder)){
					TempSaleReport tsr = getTempRetireReportByOrderArray(buyOrder,
							saleOrder);
					tempSaleReportsList.add(tsr);
				}				
			}
		}
		return tempSaleReportsList;
	}
	
	private boolean filterOrderForRetireReport(AirticketOrder saleOrder,AirticketOrder buyOrder)throws AppException{		
		if (saleOrder.getId() == buyOrder.getId()) {// 两条订单ID相同
			System.out.println("ERROR the same order id");
			return false;
		} 
		
		return filterNormalOrderForRetireReport(buyOrder);
	}
	
	private boolean filterNormalOrderForRetireReport(AirticketOrder tempOrder){
		String stringStroe=AirticketOrder.GROUP_FILTERSUCCESS;
		String buyStatus=tempOrder.getStatus()+"";
		
		if(buyStatus!=null&&"".equals(buyStatus)==false){
			if (StringUtil.containsExistString(buyStatus,stringStroe)) {
				System.out.println("tempOrder id:"+ tempOrder.getId()
						+ "--status:"+ tempOrder.getStatus()+ "--正常订单不用导出");
				return false;
			}else{
				System.out.println("add tempOrder id:"+ tempOrder.getId()+ "--status:"+ tempOrder.getStatus()+ "--非正常订单，退废报表导出");
				return true;
			}		
		}else{
			System.err.println("order id:"+tempOrder.getId()+"--status is null");
			return false;
		}		
	}

	/***************************************************************************
	 * 销售报表获取卖出订单
	 **************************************************************************/
	private AirticketOrder getSaleAirticketOrderByList(
			List<AirticketOrder> airticketOrderList) throws AppException {
		AirticketOrder saleOrder = new AirticketOrder();
		for (int j = 0; j < airticketOrderList.size(); j++) {
			AirticketOrder tempSaleOrder = (AirticketOrder) airticketOrderList
					.get(j);
			if (tempSaleOrder != null
					&& tempSaleOrder.getTranType() != null
					&& tempSaleOrder.getTranType() == AirticketOrder.TRANTYPE__1) {// 卖出
				saleOrder = tempSaleOrder;
			}
		}
		return saleOrder;
	}

	/***************************************************************************
	 * 退废报表获取卖出订单
	 * 
	 * @param 同一小组Order
	 **************************************************************************/
	private AirticketOrder getSaleAirticketOrderByListForRetire(
			List<AirticketOrder> airticketOrderList) throws AppException {
		AirticketOrder saleOrder = new AirticketOrder();
		for (int j = 0; j < airticketOrderList.size(); j++) {
			AirticketOrder tempSaleOrder = (AirticketOrder) airticketOrderList
					.get(j);
			if (tempSaleOrder != null
					&& tempSaleOrder.getBusinessType() != null) {
				if (tempSaleOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1) {
					saleOrder = tempSaleOrder;// 卖出
				}
			}
		}
		return saleOrder;
	}

	// 散票---组装报表对象
	private TempSaleReport getTempRetireReportByOrderArray(
			AirticketOrder buyOrder, AirticketOrder saleOrder) {
		TempSaleReport tsr = new TempSaleReport();

		tsr.setToTime(saleOrder.getEntryTime());
		tsr.setSysUser(saleOrder.getEntryOperatorName());
		
		tsr.setSubPnr(saleOrder.getSubPnr());// 预定pnr
		tsr.setDrawPnr(saleOrder.getDrawPnr());// 出票pnr
		tsr.setBigPnr(saleOrder.getBigPnr());// 大pnr
		
		if (saleOrder.getPlatform() != null) {
			tsr.setToPlatform(saleOrder.getPlatform().getName());// 卖出商
		}
		if (saleOrder.getRebate() != null) {
			tsr.setToCompany_fanDian(saleOrder.getRebate());// 卖出商 返点
		}
		tsr.setToHandlingCharge(saleOrder.getHandlingCharge());// 手续费
		tsr.setToAirOrderNo(saleOrder.getAirOrderNo());// 卖出商订单号
		if (saleOrder.getTotalAmount() != null) {
			tsr.setRealIncome(saleOrder.getTotalAmount());// 实际收入
			tsr.setReportIncome(saleOrder.getTotalAmount());// 报表收入
		} else {
			tsr.setRealIncome(new BigDecimal(0));
			tsr.setReportIncome(new BigDecimal(0));// 报表收入
		}

		if (saleOrder.getAccount() != null) {
			tsr.setToAccount(saleOrder.getAccount().getName());// 收款帐号
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
		tsr.setToOldOrderNo(saleOrder.getOldOrderNo());
		tsr.setToState(saleOrder.getStatusText());// 供应状态
		tsr.setToRemark(saleOrder.getMemo());// 供应备注
		tsr.setRetireType(saleOrder.getRetireTypeInfo());

		if (buyOrder == null) {
			tsr.setFromOldOrderNo("‘");
			tsr.setFromPlatform("");// 买入商
			tsr.setFromCompany_fanDian(BigDecimal.ZERO);		
			tsr.setFromHandlingCharge(BigDecimal.ZERO);// 手续费（卖出）
			tsr.setFromAirOrderNo("");// 买入商订单号
			tsr.setRealPayout(BigDecimal.ZERO);// 实际支出（退款金额）
			tsr.setReportPayout(BigDecimal.ZERO);// 报表支出
			tsr.setPayOperator("");
			tsr.setFromAccount("");// 付款帐号
			tsr.setProfit(new BigDecimal(0));// 利润
			tsr.setOrderTime(null);// 订单时间
			tsr.setFormTime(null);
			tsr.setFromState("");// 采购状态
			tsr.setFromRemark("");// 采购备注
		} else {
			tsr.setFromOldOrderNo(buyOrder.getOldOrderNo());
			if (buyOrder.getPlatform() != null) {
				tsr.setFromPlatform(buyOrder.getPlatform().getName());// 买入商
			}
			if (buyOrder.getRebate() != null) {
				tsr.setFromCompany_fanDian(buyOrder.getRebate());// 买入商 返点
			}
			tsr.setFromHandlingCharge(buyOrder.getHandlingCharge());// 手续费（卖出）
			tsr.setFromAirOrderNo(buyOrder.getAirOrderNo());// 买入商订单号
			if (buyOrder.getTotalAmount() != null) {
				tsr.setRealPayout(buyOrder.getTotalAmount());// 实际支出（退款金额）
				tsr.setReportPayout(buyOrder.getTotalAmount());// 报表支出
				String tempRefundOpt = getRefundOptNameByOrder(buyOrder);// 退款操作人

				if (tempRefundOpt == null || "".equals(tempRefundOpt)) {
					tempRefundOpt = getRefundOptNameByOrder(saleOrder);
				}
				tsr.setPayOperator(tempRefundOpt);
			} else {
				tsr.setRealPayout(new BigDecimal(0));
				tsr.setReportPayout(new BigDecimal(0));// 报表支出
			}
			if (buyOrder.getAccount() != null) {
				tsr.setFromAccount(buyOrder.getAccount().getName());// 付款帐号
			}
			if (saleOrder.getTotalAmount() != null
					&& buyOrder.getTotalAmount() != null) {
				BigDecimal profit = saleOrder.getTotalAmount().subtract(
						buyOrder.getTotalAmount());
				tsr.setProfit(profit);// 利润
			} else {
				tsr.setProfit(new BigDecimal(0));// 利润
			}
			tsr.setOrderTime(buyOrder.getEntryTime());// 订单时间
			tsr.setFormTime(buyOrder.getEntryTime());
			tsr.setFromState(buyOrder.getStatusText());// 采购状态
			tsr.setFromRemark(buyOrder.getMemo());// 采购备注
		}
		return tsr;
	}

	// 散票---组装报表对象
	private TempSaleReport getTempSaleReportByOrderArray(AirticketOrder fromAo,
			AirticketOrder toAo) {
		TempSaleReport tsr = new TempSaleReport();
		tsr.setOrderGroupNo(fromAo.getOrderGroup().getNo());// 临时字段
		tsr.setOrderTime(fromAo.getEntryTime());// 订单时间

		if (toAo.getPlatform() != null) {
			tsr.setToPlatform(toAo.getPlatform().getName());// 卖出商
		}
		if (fromAo.getPlatform() != null) {
			tsr.setFromPlatform(fromAo.getPlatform().getName());// 买入商
		}
		if (toAo.getRebate() != null) {
			tsr.setToCompany_fanDian(toAo.getRebate());// 卖出商 返点
		}
		if (fromAo.getRebate() != null) {
			tsr.setFromCompany_fanDian(fromAo.getRebate());// 买入商 返点
		}
		tsr.setKueiDian(tsr.getKueiDian());// 亏点
		tsr.setSubPnr(fromAo.getSubPnr());// 预定pnr
		tsr.setDrawPnr(fromAo.getDrawPnr());// 出票pnr
		tsr.setBigPnr(fromAo.getBigPnr());// 大pnr

		StringBuffer passengerName = new StringBuffer();
		StringBuffer ticketNumber = new StringBuffer();
		Set pasSet = fromAo.getPassengers();
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

		Set fliSet = fromAo.getFlights();
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
			fromAo.setAdultCount(Long.valueOf(pasSet.size()));
		} else {
			tsr.setPassengerNumber(0);
		}

		tsr.setTicketPrice(fromAo.getTicketPrice());// 单张票面价
		tsr.setAirportPrice(fromAo.getAirportPrice());// 单张机建税
		tsr.setFuelPrice(fromAo.getFuelPrice());// 单张燃油税
		tsr.setAllTicketPrice(fromAo.getTotalTicketPrice());// 票面总价
		tsr.setAllAirportPrice(fromAo.getTotalAirportPrice());// 总机建税
		tsr.setAllFuelPrice(fromAo.getTotalFuelPrice());// 总燃油税

		tsr.setToAirOrderNo(toAo.getAirOrderNo());// 卖出商订单号
		if (toAo.getTotalAmount() != null) {
			tsr.setRealIncome(toAo.getTotalAmount());// 实际收入
			tsr.setReportIncome(toAo.getTotalAmount());// 报表收入
		} else {
			tsr.setRealIncome(new BigDecimal(0));
			tsr.setReportIncome(new BigDecimal(0));// 报表收入
		}

		if (toAo.getAccount() != null) {
			tsr.setToAccount(toAo.getAccount().getName());// 收款帐号
		}
		tsr.setFromAirOrderNo(fromAo.getAirOrderNo());// 买入商订单号
		if (fromAo.getTotalAmount() != null) {
			tsr.setRealPayout(fromAo.getTotalAmount());// 实际支出
			tsr.setReportPayout(fromAo.getTotalAmount());// 报表支出
		} else {
			tsr.setRealPayout(new BigDecimal(0));
			tsr.setReportPayout(new BigDecimal(0));// 报表支出
		}
		if (fromAo.getAccount() != null) {
			tsr.setFromAccount(fromAo.getAccount().getName());// 付款帐号
		}
		if (toAo.getTotalAmount() != null && fromAo.getTotalAmount() != null) {
			BigDecimal profit = toAo.getTotalAmount().subtract(
					fromAo.getTotalAmount());
			tsr.setProfit(profit);// 利润
		} else {
			tsr.setProfit(new BigDecimal(0));// 利润
		}

		tsr.setSysUser(toAo.getEntryOperatorName());// 操作人

		tsr.setToState(toAo.getStatusText());// 供应状态
		tsr.setFromState(fromAo.getStatusText());// 采购状态
		tsr.setToRemark(toAo.getMemo());// 供应备注
		tsr.setFromRemark(fromAo.getMemo());// 采购备注
		tsr.setRetireType(fromAo.getRetireTypeInfo());//

		return tsr;
	}

	private String getRefundOptNameByOrder(AirticketOrder order) {
		/**
		 * 暂时填写付退款操作人
		 */
		String tempPayOperator = "";
		if (order.getTranType() == AirticketOrder.TRANTYPE__1) {
			// 卖出取消出票--付退款
			tempPayOperator = order.getOperate21();
		}
		if (order.getTranType() == AirticketOrder.TRANTYPE__2) {
			// 买入取消出票--收退款
			tempPayOperator = order.getOperate20();
		}
		if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__1
				&& order.getTranType() == AirticketOrder.TRANTYPE_3) {
			// 卖出退票--付退款
			tempPayOperator = order.getOperate43();
		}
		if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__1
				&& order.getTranType() == AirticketOrder.TRANTYPE_3) {
			// 买入退票--收退款
			tempPayOperator = order.getOperate42();
		}
		if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__1
				&& order.getTranType() == AirticketOrder.TRANTYPE_4) {
			// 卖出废票--收退款
			tempPayOperator = order.getOperate54();
		}
		if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__2
				&& order.getTranType() == AirticketOrder.TRANTYPE_4) {
			// 买入废票--付退款
			tempPayOperator = order.getOperate55();
		}

		if (tempPayOperator != null && "".equals(tempPayOperator) == false) {
			return UserStore.getUserNameByNo(tempPayOperator);
		} else {
			return "";
		}
	}

	// 下载销售报表
	public ArrayList<ArrayList<Object>> downloadSaleReport(
			AirticketOrderListForm alf) throws AppException {
		String downloadDate = "";
		if (alf.getDownloadDate() == null || alf.getDownloadDate().equals(""))
			alf.setDownloadDate(com.neza.tool.DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"));

		downloadDate = alf.getDownloadDate();
		List data = getSaleReportContent(alf);

		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("#销售报表");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#查询时间：");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(downloadDate);
		list_context.add(list_title);
		list_title = new ArrayList<Object>();

		list_title.add("订单操作时间");
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
		list_title.add("供应状态");
		list_title.add("采购状态");
		list_title.add("供应备注");
		list_title.add("采购备注");

		list_context.add(list_title);
		for (int i = 0; i < data.size(); i++) {
			TempSaleReport tsr = (TempSaleReport) data.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(tsr.getEntryOrderDate());// 订单时间
			list_context_item.add(tsr.getToPlatform());// 卖出商
			list_context_item.add(tsr.getToCompany_fanDian() + "%");// 卖出商 返点
			list_context_item.add(tsr.getFromPlatform());// 买入商
			list_context_item.add(tsr.getFromCompany_fanDian() + "%");// 买入商
			// 返点
			list_context_item.add(tsr.getKueiDian() + "%");// 亏点
			list_context_item.add(tsr.getSubPnr());// 预定PNR
			list_context_item.add(tsr.getDrawPnr());// 出票PNR
			list_context_item.add(tsr.getBigPnr());// 大PNR
			list_context_item.add(tsr.getPassengerName());// 乘客姓名
			list_context_item.add(tsr.getPassengerNumber());// 乘机人数
			list_context_item.add(tsr.getStartPoint());// 出发地
			list_context_item.add(tsr.getEndPoint());// 目的地
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
			list_context_item.add("’" + tsr.getToAirOrderNo());// 卖出商订单号
			list_context_item.add(tsr.getRealIncome());// 实际收入
			list_context_item.add(tsr.getReportIncome());// 报表收入
			list_context_item.add(tsr.getToAccount());// 收款帐号
			list_context_item.add("’" + tsr.getFromAirOrderNo());// 买入商订单号
			list_context_item.add(tsr.getRealPayout());// 实际支出
			list_context_item.add(tsr.getReportPayout());// 报表支出
			list_context_item.add(tsr.getFromAccount());// 付款帐号
			list_context_item.add(tsr.getProfit());// 利润
			list_context_item.add(tsr.getSysUser());// 操作人
			list_context_item.add(tsr.getToState());// 供应状态
			list_context_item.add(tsr.getFromState());// 采购状态
			list_context_item.add(tsr.getToRemark());// 供应备注
			list_context_item.add(tsr.getFromRemark());// 采购备注
			list_context.add(list_context_item);
		}

		return list_context;
	}

	/***************************************************************************
	 * 下载退废报表 sc
	 **************************************************************************/
	public ArrayList<ArrayList<Object>> downloadRetireReport(
			AirticketOrderListForm alf) throws AppException {
		String downloadDate = "";
		if (alf.getDownloadDate() == null || alf.getDownloadDate().equals(""))
			alf.setDownloadDate(com.neza.tool.DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"));

		downloadDate = alf.getDownloadDate();
		List data = getRetireReportContent(alf);

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
		list_title.add("仓位");
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
			TempSaleReport tsr = (TempSaleReport) data.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(tsr.getEntryOrderDate());// 订单时间
			list_context_item.add(tsr.getSysUser());// 操作人
			list_context_item.add(tsr.getSubPnr());// 预定PNR
			list_context_item.add(tsr.getDrawPnr());// 出票PNR
			list_context_item.add(tsr.getBigPnr());// 大PNR
			list_context_item.add(tsr.getPassengerName());// 乘客姓名
			list_context_item.add(tsr.getPassengerNumber());// 乘机人数
			list_context_item.add(tsr.getToPlatform());// 卖出商
			list_context_item.add("’" + tsr.getToAirOrderNo());// 卖出商订单号
			list_context_item.add("’" + tsr.getToOldOrderNo());// 原始订单号
			list_context_item.add(tsr.getFromPlatform());// 买入商
			list_context_item.add("’" + tsr.getFromAirOrderNo());// 买入商订单号
			list_context_item.add("’" + tsr.getFromOldOrderNo());// 原始订单号
			list_context_item.add(tsr.getTicketNumber());// 票号
			list_context_item.add(tsr.getStartPoint());// 出发地
			list_context_item.add(tsr.getEndPoint());// 目的地
			list_context_item.add(tsr.getFlightCode());// 航班号
			list_context_item.add(tsr.getBoardingDate());// 起飞时间
			list_context_item.add(tsr.getRetireType());// 退废类别
			list_context_item.add(tsr.getToHandlingCharge());// 手续费（供应--卖出）
			list_context_item.add(tsr.getFromHandlingCharge());// 手续费（采购--买入）
			list_context_item.add(tsr.getToDate());// 收款时间
			list_context_item.add(tsr.getRealIncome());// 实际收入
			list_context_item.add(tsr.getRealIncome());// 应收金额
			list_context_item.add(tsr.getRealPayout());// 出票付款金额
			list_context_item.add(tsr.getPassengerNumber());// 出票人数
			list_context_item.add(tsr.getTicketPrice());// 单张票面价
			list_context_item.add(tsr.getAllTicketPrice());// 票面总价
			list_context_item.add(tsr.getAirportPrice());// //单张机建税
			list_context_item.add(tsr.getAllAirportPrice());// 总机建税
			list_context_item.add(tsr.getFuelPrice());// 单张燃油税
			list_context_item.add(tsr.getAllFuelPrice());// 总燃油税
			list_context_item.add(tsr.getCyr());// 承运人
			list_context_item.add(tsr.getFlightClass());// 仓位
			list_context_item.add(tsr.getToAccount());// 收款帐号
			list_context_item.add(tsr.getFormDate());// 退款时间
			list_context_item.add(tsr.getRealPayout());// 退款金额
			list_context_item.add(tsr.getFromAccount());// 付款帐号
			list_context_item.add(tsr.getPayOperator());// 退款操作人
			list_context_item.add(tsr.getToState());// 供应状态
			list_context_item.add(tsr.getFromState());// 采购状态
			list_context_item.add(tsr.getToRemark());// 供应备注
			list_context_item.add(tsr.getFromRemark());// 采购备注

			list_context.add(list_context_item);
		}
		return list_context;
	}

	/**
	 * 组装团队销售报表内容
	 */
	private List<TeamAirticketOrderReport> getTeamReportForSale(
			List<AirticketOrder> airticketOrderList,
			List<TeamAirticketOrderReport> teamAirticketOrderReport)
			throws AppException {
		System.out.println("orderList size--->" + airticketOrderList.size());

		if (airticketOrderList != null && airticketOrderList.size() > 0) {
			AirticketOrder saleOrder = new AirticketOrder();// 卖出(同组中只有一条)
			AirticketOrder buyOrder = new AirticketOrder();// 买入(可能有多笔)

			saleOrder = getSaleAirticketOrderByList(airticketOrderList);

			Long coloumFlag = new Long(0);
			for (int j = 0; j < airticketOrderList.size(); j++) {

				AirticketOrder tempOrder = (AirticketOrder) airticketOrderList
						.get(j);
				if (tempOrder != null
						&& tempOrder.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
					buyOrder = tempOrder;

					System.out.println("coloumFlag:" + coloumFlag);
					// if (coloumFlag>0) {
					// saleOrder=new AirticketOrder();
					// }
					TeamAirticketOrderReport tsr = getTempTeamSaleReportByOrderArray(
							saleOrder, buyOrder);
					teamAirticketOrderReport.add(tsr);
					coloumFlag = coloumFlag + 1;
				}
			}
		}
		return teamAirticketOrderReport;
	}

	/**
	 * 组装团队销售报表对象
	 */
	private TeamAirticketOrderReport getTempTeamSaleReportByOrderArray(
			AirticketOrder saleOrder, AirticketOrder buyOrder) {
		TeamAirticketOrderReport tsr = new TeamAirticketOrderReport();
		if (saleOrder.getEntryTime() != null) {
			tsr.setEntry_time(DateUtil.getDateString(saleOrder.getEntryTime(),
					"yyyy-MM-dd"));// 录单时间
		}

		tsr.setAgentType(saleOrder.getDrawer());// 出票人
		if (saleOrder.getAgent() != null) {
			tsr.setAgentName(saleOrder.getAgent().getName());// 购票客户
		}
		tsr.setFlightHC(saleOrder.getFlightsTxt());
		tsr.setTotalTicketNumber(saleOrder.getTotalPerson());
		tsr.setFlightTime(saleOrder.getBoardingDatesTxt());// 航班日期
		tsr.setFlightCode(saleOrder.getFlightsCodeTxt());// 航班号
		tsr.setFlightClass(saleOrder.getFlightClassTxt());// 舱位
		tsr.setDiscount(saleOrder.getDiscountTxt());// 折扣
		tsr.setCarrier(saleOrder.getCyrsTxt());// 承运人
		tsr.setTeamAddPrice(saleOrder.getTeamaddPrice());
		tsr.setAgentAddPrice(saleOrder.getAgentaddPrice());

		tsr.setAirticketNo(buyOrder.getAirOrderNo());// 订单号
		if (buyOrder.getAccount() != null) {
			tsr.setAccountNo(buyOrder.getAccount().getName());// 支付账号(显示付款账号)
		}
		tsr.setConfirm_payment_Price(buyOrder.getTotalAmount());// 确认付款金额
		if (buyOrder.getPayTime() == null) {
			tsr.setPay_Time("");//
			System.err.println("order id:" + buyOrder.getId()
					+ " payTime is null");
		} else {
			tsr.setPay_Time(buyOrder.getFormatPayTime());// 收付款时间(确认支付时间)
		}

		tsr.setPaymentName(buyOrder.getPayOperatorName());// 支付人
		tsr.setPaymentMemo(buyOrder.getMemo());// 支付备注

		TeamProfit teamProfit = new TeamProfit(saleOrder, buyOrder);

		tsr.setCommission(teamProfit.getCommission());// 现返
		tsr.setUnsettledAccount(saleOrder.getRakeOff());// 未返
		tsr.setProxy_price(saleOrder.getProxyPrice());// 应付出团代理费（未返）
		tsr.setUnsettledMome(saleOrder.getMemo());// 未返备注
		tsr.setTotalProce(teamProfit.getSaleTotalAmount());// 总金额（实收票款）
		tsr.setSysName(saleOrder.getEntryOperatorName());// 操作人
		tsr.setIncomeretreat_charge(saleOrder.getIncomeretreatCharge());// 收退票手续费
		tsr.setIncomeTicketPrice(teamProfit.getSaleTicketPrice());// 收票款(应收票款)
		tsr.setAmountMore(teamProfit.getSaleOverPrice());// 多收票款(团队加价+客户加价)
		tsr.setTaxMore(saleOrder.getOverAirportfulePrice());// 多收税
		tsr.setTicketPrice(saleOrder.getTotalTicketPrice());// 票面价
		tsr.setTotal_airport_price(saleOrder.getTotalAirportPrice());// 机建
		tsr.setTotal_fuel_price(saleOrder.getTotalFuelPrice());// 燃油

		// 航空公司
		tsr.setCommisson_count(buyOrder.getCommissonCount());// 返点
		tsr.setHandling_charge(buyOrder.getHandlingCharge());// 手续费
		tsr.setRakeoff_count(buyOrder.getRakeoffCount());// 后返点
		tsr.setActual_incomeretreat_charge(buyOrder.getIncomeretreatCharge());// 实付退票手续费
		tsr.setAgentFeeCarrier(buyOrder.getRakeOff());// 月底返代理费
		tsr.setProfits(teamProfit.getGrossProfit());// 团毛利润
		tsr.setCopeTicketprice(teamProfit.getBuyTicketPrice());// 应付票款
		tsr.setPaidPrice(teamProfit.getBuyTotalAmount());// 实付款
		tsr.setRefundProfit(teamProfit.getRefundProfit());// 退票利润
		tsr.setPureProfits(teamProfit.getTotalProfit());// 净利合计
		return tsr;
	}

	// 导出团队机票销售报表
	public ArrayList<ArrayList<Object>> downloadTeamSaleReport(
			AirticketOrderListForm rlf) throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		rlf.setPerPageNum(10000);// 设制分页显示数据

		List list = getTeamSaleReportContent(rlf);

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
		long ticketNum = new Long(0);// 票面价
		long incomeretreat_charge = new Long(0);// //收退票手续费
		long incomeTicketPrice = new Long(0);// 收票款
		long airportTaxInfo = new Long(0);// 机场税
		long copeTicketprice = new Long(0);// 应付票款
		long actual_incomeretreat_charge = new Long(0);// 实付退票手续费
		long paidPrice = new Long(0);// 实付款
		long confirm_payment_Price = new Long(0);// 确认支付金额
		long agentFeeCarrierInfo = new Long(0);// 月底返代理费
		long profitsInfo = new Long(0);// 团毛利润
		long refundProfit = new Long(0);// 退票利润
		long amountMore = new Long(0);// 多收票款
		long taxMore = new Long(0);// 多收税
		long commission = new Long(0);// 现返
		long unsettledAccount = new Long(0);// 未返
		long pureProfits = new Long(0);// 净利合计
		long totalProce = new Long(0);

		for (int i = 0; i < list.size(); i++) {
			TeamAirticketOrderReport t = (TeamAirticketOrderReport) list.get(i);
			list_context_item = new ArrayList<Object>();
			list_context_item.add(t.getEntry_time());// 出票日期
			list_context_item.add(t.getAgentType());// 客票类型
			list_context_item.add(t.getCarrier());// 承运人
			list_context_item.add(t.getFlightHC());// 航程
			list_context_item.add(t.getTotalTicketNumber());// 张数
			list_context_item.add(t.getAirticketNo() + "");// 订单号
			list_context_item.add(t.getFlightTime());// 航班日期
			list_context_item.add(t.getFlightCode());// 航班号
			list_context_item.add(t.getFlightClass());// 舱位
			list_context_item.add(t.getDiscount());// 折扣
			list_context_item.add(t.getTicketPrice());// 票面价
			list_context_item.add(t.getAgentName());// 购票客户
			list_context_item.add(t.getIncomeretreat_charge());// 收退票手续费
			list_context_item.add(t.getIncomeTicketPrice());// 收票款
			list_context_item.add(t.getAirportTaxInfo());// 机场税
			list_context_item.add(t.getCopeTicketprice());// 应付票款
			list_context_item.add(t.getActual_incomeretreat_charge());// 实付退票手续费
			list_context_item.add(t.getPaidPrice());// 实付款
			list_context_item.add(t.getConfirm_payment_Price());// 确认支付金额
			list_context_item.add(t.getAccountNo());// 支付账号
			list_context_item.add(t.getPaymentMemo());// 支付备注
			if (t.getPay_Time() != null) {
				list_context_item.add(t.getPay_Time());// 收付款时间
			} else {
				list_context_item.add("");
			}
			list_context_item.add(t.getPaymentName());// 支付人
			list_context_item.add(t.getAgentFeeCarrierInfo());// 月底返代理费
			list_context_item.add(t.getProfits());// 团毛利润
			list_context_item.add(t.getRefundProfit());// 退票利润
			list_context_item.add(t.getAmountMore());// 多收票款
			list_context_item.add(t.getTaxMore());// 多收税
			list_context_item.add(t.getCommission());// 现返
			list_context_item.add(t.getUnsettledAccount());// 未返
			list_context_item.add(t.getUnsettledMome());// 未返备注
			list_context_item.add(t.getPureProfits());// 净利合计
			list_context_item.add(t.getAgentName());// 购票客户
			list_context_item.add(t.getTotalProce());// 总金额
			list_context_item.add(t.getSysName());// 操作人
			list_context.add(list_context_item);

			// 累加
			personNum += t.getTotalTicketNumber();// 张数
			ticketNum += t.getTicketPrice().doubleValue();// 票面价
			incomeretreat_charge += t.getIncomeretreat_charge().doubleValue();// //收退票手续费
			incomeTicketPrice += t.getIncomeTicketPrice().doubleValue();// 收票款
			airportTaxInfo += t.getAirportTaxInfo().doubleValue();// 机场税
			copeTicketprice += t.getCopeTicketprice().doubleValue();// 应付票款
			actual_incomeretreat_charge += t.getActual_incomeretreat_charge()
					.doubleValue();// 实付退票手续费
			paidPrice += t.getPaidPrice().doubleValue();// 实付款
			confirm_payment_Price += t.getConfirm_payment_Price().doubleValue();// 确认支付金额
			agentFeeCarrierInfo += t.getAgentFeeCarrierInfo().doubleValue();// 月底返代理费
			profitsInfo += t.getProfitsInfo().doubleValue();// 团毛利润
			refundProfit += t.getRefundProfit().doubleValue();// 退票利润
			amountMore += t.getAmountMore().doubleValue();// 多收票款
			taxMore += t.getTaxMore().doubleValue();// 多收税
			commission += t.getCommission().doubleValue();// 现返
			unsettledAccount += t.getUnsettledAccount().doubleValue();// 未返
			pureProfits += t.getPureProfits().doubleValue();// 净利合计
			totalProce += t.getTotalProce().doubleValue();// 总金额
		}
		// 合计
		list_context_item = new ArrayList<Object>();
		list_context_item.add("合 计");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add(personNum);// 张数
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");// 折扣
		list_context_item.add(ticketNum);// 票面价
		list_context_item.add("");
		list_context_item.add(incomeretreat_charge);// //收退票手续费
		list_context_item.add(incomeTicketPrice);// 收票款
		list_context_item.add(airportTaxInfo);// 机场税
		list_context_item.add(copeTicketprice);// 应付票款
		list_context_item.add(actual_incomeretreat_charge);// 实付退票手续费
		list_context_item.add(paidPrice);// 实付款
		list_context_item.add(confirm_payment_Price);// 确认支付金额
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add(agentFeeCarrierInfo);// 月底返代理费
		list_context_item.add(profitsInfo);// 团毛利润
		list_context_item.add(refundProfit);// 退票利润
		list_context_item.add(amountMore);// 多收票款
		list_context_item.add(taxMore);// 多收税
		list_context_item.add(commission);// 现返
		list_context_item.add(unsettledAccount);// 未返
		list_context_item.add("");
		list_context_item.add(pureProfits);// 净利合计
		list_context_item.add("");
		list_context_item.add(totalProce);// 总金额
		list_context_item.add("");
		list_context.add(list_context_item);

		return list_context;
	}

	// 导出团队未返代理费报表
	public ArrayList<ArrayList<Object>> downloadTeamRakeOffReport(
			AirticketOrderListForm rlf) throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();

		List list = getTeamSaleReportContent(rlf);

		ArrayList<Object> list_title = new ArrayList<Object>();
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
			TeamAirticketOrderReport t = (TeamAirticketOrderReport) list.get(i);
			list_context_item = new ArrayList<Object>();
			list_context_item.add(t.getEntry_time());// 出票日期
			list_context_item.add(t.getAgentType());// 客票类型
			list_context_item.add(t.getCarrier());// 承运人
			list_context_item.add(t.getFlightHC());// 航程
			list_context_item.add(t.getTotalTicketNumber());// 张数
			list_context_item.add(t.getAirticketNo() + "");// 订单号
			list_context_item.add(t.getFlightTime());// 航班日期
			list_context_item.add(t.getFlightCode());// 航班号
			list_context_item.add(t.getFlightClass());// 舱位
			list_context_item.add(t.getDiscount());// 折扣
			list_context_item.add(t.getTicketPrice());// 票面价
			list_context_item.add(t.getTeamAddPrice());// 团队加价
			list_context_item.add(t.getAgentAddPrice());// 客户加价
			list_context_item.add(t.getAgentName());// 购票客户
			list_context_item.add(t.getUnsettledAccount());// 未返
			list_context_item.add(t.getUnsettledMome());// 未返备注
			list_context_item.add(t.getIncomeTicketPrice());// 收客户票款
			list_context_item.add(t.getSysName());// 操作人
			list_context.add(list_context_item);
		}
		return list_context;
	}

	// 散票原始销售报表
	public List marketReportsList(AirticketOrderListForm rlf)
			throws AppException {
		return reportDAO.marketReportsList(rlf);
	}

	// 下载用
	public ArrayList<ArrayList<Object>> getMarketReportsList(
			AirticketOrderListForm alf) throws AppException {
		String downloadDate = "";
		if (alf.getDownloadDate() == null || alf.getDownloadDate().equals(""))
			alf.setDownloadDate(com.neza.tool.DateUtil
					.getDateString("yyyy-MM-dd HH:mm:ss"));

		downloadDate = alf.getDownloadDate();
		List data = reportDAO.marketReportsList(alf);

		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		list_title.add("#原始销售报表");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("#指定查询时间：");
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add(downloadDate);
		list_context.add(list_title);
		list_title = new ArrayList<Object>();
		list_title.add("订单时间");
		list_title.add("平台");
		list_title.add("返点");
		list_title.add("预定PNR");
		list_title.add("出票PNR");
		list_title.add("大PNR");
		list_title.add("乘客");
		list_title.add("人数");
		list_title.add("航段");
		list_title.add("航段三字码");
		list_title.add("承运人");
		list_title.add("航班号");
		list_title.add("舱位");
		list_title.add("折扣");
		list_title.add("单票面价");
		list_title.add("单机建税");
		list_title.add("单燃油税");
		list_title.add("总票面价");
		list_title.add("总机建税");
		list_title.add("总燃油税");
		list_title.add("起飞日期");
		list_title.add("票号");
		list_title.add("订单编号");
		list_title.add("实收/付票款");
		list_title.add("支付方式/操作人");
		list_context.add(list_title);
		for (int i = 0; i < data.size(); i++) {
			AirticketOrder ao = (AirticketOrder) data.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();

			list_context_item.add(ao.getEntryOrderDate());// 订单时间
			if (ao.getPlatform() != null) {
				list_context_item.add(ao.getPlatform().getName());// 平台
			}

			list_context_item.add(ao.getCommission());// 返点
			list_context_item.add(ao.getSubPnr());// 预定PNR
			list_context_item.add(ao.getDrawPnr());// 出票PNR
			list_context_item.add(ao.getBigPnr());// 大PNR
			for (Object obj : ao.getPassengers()) {
				Passenger passenger = (Passenger) obj;
				list_context_item.add(passenger.getName());// 乘客
			}
			list_context_item.add(ao.getTotalPerson());// 人数
			StringBuffer hd = new StringBuffer();
			StringBuffer hdszm = new StringBuffer();
			StringBuffer cyr = new StringBuffer();
			StringBuffer flightCode = new StringBuffer();
			StringBuffer flightClass = new StringBuffer();
			StringBuffer discount = new StringBuffer();
			for (Object fobj : ao.getFlights()) {
				Flight flight = (Flight) fobj;
				hd.append(flight.getStartPointText());
				hd.append(flight.getEndPointText());
				hdszm.append(flight.getStartPoint() + "--"
						+ flight.getEndPoint());
				cyr.append(flight.getCyr());
				flightCode.append(flight.getFlightCode());
				flightClass.append(flight.getFlightClass());
				discount.append(flight.getDiscount());
			}
			list_context_item.add(hd);// 航段
			list_context_item.add(hdszm);// 航段三字码
			list_context_item.add(cyr);// 承运人
			list_context_item.add(flightCode);// 航班号
			list_context_item.add(flightClass);// 舱位
			list_context_item.add(discount);// 折扣
			list_context_item.add(ao.getTicketPrice());// 单票面价
			list_context_item.add(ao.getAirportPrice());// 单机建税
			list_context_item.add(ao.getFuelPrice());// 单燃油税
			list_context_item.add(ao.getTotalTicketPrice());// 总票面价
			list_context_item.add(ao.getTotalAirportPrice());// 总机建税
			list_context_item.add(ao.getTotalFuelPrice());// 总燃油税
			StringBuffer boardingTime = new StringBuffer();
			for (Object fobj2 : ao.getFlights()) {
				Flight flight = (Flight) fobj2;
				boardingTime.append(flight.getBoardingDate());
				list_context_item.add(boardingTime);// 起飞日期
			}
			StringBuffer ticketNumber = new StringBuffer();
			for (Object obj2 : ao.getPassengers()) {
				Passenger passenger = (Passenger) obj2;
				ticketNumber.append(passenger.getTicketNumber());
			}
			list_context_item.add(ticketNumber);// 票号
			list_context_item.add(ao.getAirOrderNo());// 订单编号
			list_context_item.add(ao.getTotalAmount());// 实收/付票款
			list_context_item.add(ao.getPayOperatorName());// 支付方式/操作人
			list_context.add(list_context_item);
		}

		return list_context;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}
}
