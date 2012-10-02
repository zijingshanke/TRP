package com.fdays.tsms.airticket.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.airticket.biz.PassengerBiz;
import com.fdays.tsms.airticket.biz.TempPNRBiz;
import com.fdays.tsms.airticket.util.CabinUtil;
import com.fdays.tsms.airticket.util.IBEUtil;
import com.fdays.tsms.airticket.util.ParseBlackUtil;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirticketOrderAction extends BaseAction {
	public TempPNRBiz tempPNRBiz;
	public AirticketOrderBiz airticketOrderBiz;
	public PassengerBiz passengerBiz;
	public TicketLogBiz ticketLogBiz;

	/***************************************************************************
	 * 通过黑屏PNR接口获取数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByBlackPNR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			String pnrInfo = request.getParameter("pnrInfo");
			if (pnrInfo != null && !"".equals(pnrInfo.trim())) {
				TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(pnrInfo);
				tempPNR=IBEUtil.setTicketPriceByIBEInterface(tempPNR);
				tempPNR=CabinUtil.setTicketPriceByIBEInterface(tempPNR);
				if (tempPNR != null) {					
					AirticketOrder checkAO=new AirticketOrder();
					checkAO.setTranType(AirticketOrder.TRANTYPE__1);
					checkAO.setSubPnr(tempPNR.getPnr());
					boolean checkPnr=airticketOrderBiz.checkPnrisMonth(checkAO); //验证pnr是否重复添加
					if(checkPnr==false){
						request.setAttribute("msg", "已存在相同PNR!");
					}
					if(true){						
					request.setAttribute("tempPNR", tempPNR);
					airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
					// 设置临时会话
					UserRightInfo uri = (UserRightInfo) request.getSession()
							.getAttribute("URI");
					uri.setTempPNR(tempPNR);
					forwardPage = airticketOrderFrom.getForwardPage();					
					}else{
						inf.setMessage("PNR已存在，请勿重复添加！");
						inf.setBack(true);
						forwardPage = "inform";
						request.setAttribute("inf", inf);
					}
				} else {
					inf.setMessage("PNR 信息错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 通过PNR获取内部数据 sc
	 **************************************************************************/
	public ActionForward getAirticketOrderForRetireUmbuchen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom.getPnr() != null	&& !"".equals(airticketOrderFrom.getPnr().trim())) {				
				airticketOrderFrom.setSubPnr(airticketOrderFrom.getPnr());
				System.out.println("TranType====>"+airticketOrderFrom.getTranType());
			//if(airticketOrderBiz.checkPnrisMonth(airticketOrderFrom)){
				
				//根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderForRetireUmbuchen(airticketOrderFrom.getPnr(),airticketOrderFrom.getBusinessType(),airticketOrderFrom.getTranType());
				
				
				if (airticketOrder != null && airticketOrder.getId() != 0L) {
					request.setAttribute("airticketOrder", airticketOrder);

					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("PNR 不存在！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}		
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 通过PNR获取内部数据 sc(多项选择)
	 **************************************************************************/
	public ActionForward getAirticketOrderForRetireUmbuchenBySelect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
		   String aoId=	request.getParameter("aoId");
			if (aoId!= null	&& !"".equals(aoId.trim())) {				
			
				//根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(Long.valueOf(aoId));
				
				if (airticketOrder != null && airticketOrder.getId() != 0L) {
					request.setAttribute("airticketOrder", airticketOrder);

					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("id 不存在！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}		
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}
	

	/***************************************************************************
	 * 通过	信息PNR获取外部数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByBlackOutPNR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {

			String pnrInfo = request.getParameter("pnrInfo");
			if (pnrInfo != null && !"".equals(pnrInfo.trim())) {

				TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(pnrInfo);
				tempPNR=IBEUtil.setTicketPriceByIBEInterface(tempPNR);
				tempPNR=CabinUtil.setTicketPriceByIBEInterface(tempPNR);
				if (tempPNR != null) {

					airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());

					AirticketOrder ao = new AirticketOrder();
					ao.setDrawPnr(tempPNR.getPnr());// 出票pnr
					ao.setSubPnr(tempPNR.getPnr());// 预订pnr
					ao.setBigPnr(tempPNR.getB_pnr());// 大pnr
					ao.setTicketPrice(tempPNR.getFare());// 票面价格
					ao.setAirportPrice(tempPNR.getTax());// 机建费
					ao.setFuelPrice(tempPNR.getYq());// 燃油税

					// 乘机人
					List<TempPassenger> tempPassengerList = tempPNR
							.getTempPassengerList();
					Set tmpPassengerSet = new HashSet();
					
					List tempTicketsList = tempPNR.getTempTicketsList();
					for (int i = 0; i < tempPassengerList.size(); i++) {
						TempPassenger tempPassenger = tempPassengerList.get(i);
						Passenger passenger = new Passenger();
						passenger.setAirticketOrder(ao);
						passenger.setName(tempPassenger.getName()); // 乘机人姓名
						passenger.setCardno(tempPassenger.getNi());// 证件号
						passenger.setType(1L); // 类型
						passenger.setStatus(1L);// 状态
						if (tempTicketsList != null
								&& tempTicketsList.size() == tempPassengerList
										.size()) {
							System.out.println("tempTicketsList===="
									+ tempPNR.getTempTicketsList().get(i));
							passenger.setTicketNumber(tempTicketsList.get(i)
									.toString()); // 票号
						}
						tmpPassengerSet.add(passenger);
					}
					ao.setPassengers(tmpPassengerSet);
					// 航班
					List<TempFlight> tempFlightList = tempPNR
							.getTempFlightList();
					Set tmpFlightSet = new HashSet();
					for (TempFlight tempFlight : tempFlightList) {
						Flight flight = new Flight();
						flight.setAirticketOrder(ao);
						flight.setFlightCode(tempFlight.getAirline());// 航班号
						flight.setStartPoint(tempFlight.getDepartureCity()); // 出发地
						flight.setEndPoint(tempFlight.getDestineationCity());// 目的地
						flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
						flight.setDiscount(tempFlight.getDiscount());// 折扣
						flight.setFlightClass(tempFlight.getCabin());// 舱位
						flight.setStatus(1L); // 状态
						tmpFlightSet.add(flight);

					}
					ao.setFlights(tmpFlightSet);
					ao.setAddType("OutPNR");// 设置添加类型
					// 设置临时会话
					UserRightInfo uri = (UserRightInfo) request.getSession()
							.getAttribute("URI");
					uri.setTempPNR(tempPNR);
					request.setAttribute("airticketOrder", ao);
					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("PNR 错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
					System.out.println("PNR 错误");
				}
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 清空 tempPNR sc
	 **************************************************************************/
	public ActionForward clearTempPNR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		uri.setTempPNR(null);
		return (mapping.findForward("addTradingOrder"));
	}

	/***************************************************************************
	 * 倒票-订单录入 sc
	 **************************************************************************/
	public ActionForward createTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		TempPNR tempPNR = uri.getTempPNR();
		Inform inf = new Inform();
		try {
			if (tempPNR != null) {// && tempPNR.getRt_parse_ret_value() != 0L
				airticketOrderFrom.getStatement().setTotalAmount(
						airticketOrderFrom.getTotalAmount());// 总金额
				airticketOrderFrom.getStatement().setActualAmount(
						airticketOrderFrom.getTotalAmount());// 实收款
				airticketOrderFrom.getStatement().setUnsettledAccount(
						new BigDecimal(0));// 未结款
				airticketOrderFrom.getStatement().setCommission(
						new BigDecimal(0));// 现返佣金
				airticketOrderFrom.getStatement().setRakeOff(new BigDecimal(0));// 后返佣金

				airticketOrderFrom.setStatus(AirticketOrder.STATUS_1); // 订单状态
				airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1); // 设置机票类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_0);// 操作日志
				// 类型
				// 创建订单
				airticketOrderBiz.createPNR(airticketOrderFrom, tempPNR, uri);
				uri.setTempPNR(null);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrderFrom
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
			System.out.println("PNR 错误");
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * B2C-订单录入 sc
	 **************************************************************************/
	public ActionForward createB2CTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		TempPNR tempPNR = uri.getTempPNR();
		Inform inf = new Inform();
		try {
			if (tempPNR != null) {// && tempPNR.getRt_parse_ret_value() != 0L

				airticketOrderFrom.getStatement().setTotalAmount(
						airticketOrderFrom.getTotalAmount());// 总金额
				airticketOrderFrom.getStatement().setActualAmount(
						new BigDecimal(0));// 实收款
				airticketOrderFrom.getStatement().setUnsettledAccount(
						airticketOrderFrom.getTotalAmount());// 未结款
				airticketOrderFrom.getStatement().setCommission(
						new BigDecimal(0));// 现返佣金
				airticketOrderFrom.getStatement().setRakeOff(new BigDecimal(0));// 后返佣金

				airticketOrderFrom.setStatus(AirticketOrder.STATUS_1); // 订单状态
				airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_3); // 设置机票类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_32);// 操作日志
				// 类型
				// 创建订单
				airticketOrderBiz.createPNR(airticketOrderFrom, tempPNR, uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrderFrom
						.getGroupMarkNo());
				return redirect;
			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
			System.out.println("PNR 错误");
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 申请支付 sc
	 **************************************************************************/
	public ActionForward applyTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder.setStatus(AirticketOrder.STATUS_3);
			//	airticketOrder.getStatement().setTotalAmount(
			//			airticketOrderFrom.getTotalAmount());// 设置交易金额
				String platformId = request.getParameter("platformId9");
				String companyId = request.getParameter("companyId9");
				String accountId = request.getParameter("accountId9");

				if (platformId != null && !"".equals(platformId.trim())) {
					airticketOrderFrom.setPlatformId(Long.valueOf(platformId));
				}
				if (companyId != null && !"".equals(companyId.trim())) {
					airticketOrderFrom.setCompanyId(Long.valueOf(companyId));
				}
				if (accountId != null && !"".equals(accountId.trim())) {
					airticketOrderFrom.setAccountId(Long.valueOf(accountId));
				}
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_2); // 订单状态
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_3);// 操作日志
				// 类型
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setStatement_type(Statement.type__2); // 买入
				airticketOrderBiz.createApplyTickettOrder(airticketOrderFrom,
						airticketOrder,uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";

		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}
	
	/***************************************************************************
	 * 重新 申请支付 sc
	 **************************************************************************/
	public ActionForward anewApplyTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getGroupMarkNo()!=null&&!"".equals(airticketOrderFrom.getGroupMarkNo().trim())) {
                if(airticketOrderFrom.getId()>0){
                	AirticketOrder ao = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
                	ao.setStatus(AirticketOrder.STATUS_10);
                	airticketOrderBiz.update(ao);
                }
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderByMarkNo(airticketOrderFrom.getGroupMarkNo(),"1");
				airticketOrder.setStatus(AirticketOrder.STATUS_3);
				
				String platformId = request.getParameter("platformId9");
				String companyId = request.getParameter("companyId9");
				String accountId = request.getParameter("accountId9");

				if (platformId != null && !"".equals(platformId.trim())) {
					airticketOrderFrom.setPlatformId(Long.valueOf(platformId));
				}
				if (companyId != null && !"".equals(companyId.trim())) {
					airticketOrderFrom.setCompanyId(Long.valueOf(companyId));
				}
				if (accountId != null && !"".equals(accountId.trim())) {
					airticketOrderFrom.setAccountId(Long.valueOf(accountId));
				}
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_2); // 订单状态
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_3);// 操作日志
				// 类型
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setStatement_type(Statement.type__2); // 买入
				airticketOrderBiz.createApplyTickettOrder(airticketOrderFrom,
						airticketOrder,uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";

		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 确认支付 sc
	 **************************************************************************/
	public ActionForward confirmTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());

				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (airticketOrderFrom.getAccountId() != null
						&& airticketOrderFrom.getCompanyId() != null
						&& airticketOrderFrom.getPlatformId() != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="
							+ airticketOrderFrom.getAccountId());
					platComAccount = platComAccountStore
							.getPlatComAccountByAllId(airticketOrderFrom
									.getPlatformId(), airticketOrderFrom
									.getCompanyId(), airticketOrderFrom
									.getAccountId());
				}

				/*
				 * List<AirticketOrder> listao = airticketOrderBiz
				 * .listBygroupMarkNo(airticketOrder.getGroupMarkNo(), "1,2");
				 * for (AirticketOrder ao : listao) {
				 * 
				 * if (ao.getStatement().getType() == 1 && platComAccount !=
				 * null) {// 卖出
				 * 
				 * ao.getStatement().setToPCAccount(platComAccount);// 收款帐号 }
				 */

				airticketOrder.getStatement().setFromPCAccount(platComAccount);// 付款帐号
				airticketOrder.setStatus(AirticketOrder.STATUS_3); // 修改订单状态
				// airticketOrder.setDrawPnr(airticketOrderFrom.getDrawPnr());
				airticketOrder.setSubPnr(airticketOrderFrom.getPnr());
				airticketOrder
						.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 订单号
				airticketOrder.setRebate(airticketOrderFrom.getRebate());// 政策
				airticketOrder.getStatement().setTotalAmount(
						airticketOrderFrom.getTotalAmount());// 设置交易金额totalAmount
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);
				airticketOrderBiz.resetStatementUserByAirticketOrder(airticketOrder, uri.getUser());

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setType(TicketLog.TYPE_7);// 操作日志 类型
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";

		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 出票 sc
	 **************************************************************************/
	public ActionForward ticket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder.setStatus(AirticketOrder.STATUS_5);
				airticketOrder.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_5);// 操作日志
				// 类型
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());
				// 获取乘客信息
				String[] ticketNumber = request
						.getParameterValues("ticketNumber");
				String[] pId = request.getParameterValues("pId");
				String groupMarkNo = request.getParameter("groupMarkNo2");// 订单组号
               if(groupMarkNo!=null&&!"".equals(groupMarkNo.trim())){
            	   
            	   List<AirticketOrder> listao =new ArrayList<AirticketOrder>();
            	   listao.add(airticketOrder);
            	   
            	   AirticketOrder ao1 = airticketOrderBiz.getAirticketOrderByMarkNo(groupMarkNo,"1");
            	   
            	   if (ao1!=null&&ao1.getId()>0) {
						ao1.setStatus(AirticketOrder.STATUS_5);
						ao1.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
						ao1.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
						airticketOrderBiz.update(ao1);
						listao.add(ao1);
					}
            	   
            	   
          			
       				for (AirticketOrder ao : listao) {
       					List pa = passengerBiz.listByairticketOrderId(ao.getId());
       					if (pa != null && groupMarkNo != null
       							&& pa.size() == ticketNumber.length) {
       						for (int i = 0; i < pa.size(); i++) {
       							if (ao.getId() > 0) {
       								Passenger passenger = (Passenger) pa.get(i);
       								System.out.println(i + "==="+ ticketNumber[i].trim());
       								passenger.setTicketNumber(ticketNumber[i].trim());
       								passengerBiz.update(passenger);
       							}
       						}
       					}
       				}
                	   	   
			/*	List<AirticketOrder> listao = airticketOrderBiz.listBygroupMarkNo(groupMarkNo, "1,2");
				for (AirticketOrder ao : listao) {
					List pa = passengerBiz.listByairticketOrderId(ao.getId());
					if (pa != null && groupMarkNo != null
							&& pa.size() == ticketNumber.length) {
						for (int i = 0; i < pa.size(); i++) {
							if (ao.getId() > 0) {
								Passenger passenger = (Passenger) pa.get(i);
								System.out.println(i + "==="+ ticketNumber[i].trim());
								passenger.setTicketNumber(ticketNumber[i].trim());
								passengerBiz.update(passenger);
							}
						}
					}
					// 修改订单状态
					if (ao.getId() != airticketOrder.getId()) {
						ao.setStatus(AirticketOrder.STATUS_5);
						ao.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
						ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
						airticketOrderBiz.update(ao);
					}
				}*/
				
               }
               airticketOrder.setDrawer(uri.getUser().getUserNo());
               airticketOrder.setCurrentOperator(uri.getUser().getUserNo());
               airticketOrderBiz.update(airticketOrder);
				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setType(TicketLog.TYPE_5);// 操作日志 类型
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;
			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";

		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 取消出票 sc
	 **************************************************************************/
	public ActionForward quitTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder.setMemo(airticketOrderFrom.getMemo());
				//airticketOrder.setStatus(AirticketOrder.STATUS_4);
				if(airticketOrderFrom.getStatus()!=null){
					airticketOrder.setStatus(airticketOrderFrom.getStatus());
				}
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setType(TicketLog.TYPE_4);// 操作日志 类型
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}
	
	/***************************************************************************
	 * 取消出票,确认退款
	 **************************************************************************/
	public ActionForward agreeCancelRefund(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder.setStatus(airticketOrderFrom.getStatus()); // 订单状态
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrder.getStatement().setSysUser(uri.getUser());

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_6) {
					ticketLog.setType(TicketLog.TYPE_6);// 操作日志 类型
					airticketOrder.getStatement().setStatus(Statement.STATUS_1);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_7) {// 锁定
					
					airticketOrder.setCurrentOperator(uri.getUser().getUserNo());//当前操作人
					ticketLog.setType(TicketLog.TYPE_29);
                   
					
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_8) {// 解锁
                  
				if(airticketOrder.getCurrentOperator()!=null &&!"".equals(airticketOrder.getCurrentOperator())&&airticketOrder.getCurrentOperator().equals(uri.getUser().getUserNo())){
	                 airticketOrder.setCurrentOperator(null);//当前操作人  
					ticketLog.setType(TicketLog.TYPE_30);
					 }else{
						    inf.setMessage("订单已被:"+airticketOrder.getCurrentOperator()+"锁定！");
							inf.setBack(true);
							forwardPage = "inform";
							request.setAttribute("inf", inf);
							return (mapping.findForward(forwardPage));
					 }
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_44) {// 改签未通过

					ticketLog.setType(TicketLog.TYPE_27);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41) {// 改签审核通过

					ticketLog.setType(TicketLog.TYPE_23);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_42) {// 改签审核通过

					ticketLog.setType(TicketLog.TYPE_24);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_45) {// 改签完成

					ticketLog.setType(TicketLog.TYPE_26);
				}else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_88) {// 废弃订单
					
					airticketOrder.getStatement().setStatus(Statement.STATUS_88);
					airticketOrder.getStatement().setOptTime(new Timestamp(System.currentTimeMillis()));
					ticketLog.setType(TicketLog.TYPE_88);
					airticketOrderBiz.update(airticketOrder);
					ticketLogBiz.saveTicketLog(ticketLog);
					ActionRedirect redirect = new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=list");
					return redirect;
				}
				
				airticketOrderBiz.update(airticketOrder);
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder.getGroupMarkNo());
				return redirect;
			} else {
				inf.setMessage("操作错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 编辑备注 sc
	 **************************************************************************/
	public ActionForward editRemark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder.setMemo(airticketOrderFrom.getMemo());
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setType(TicketLog.TYPE_50);// 操作日志 类型
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 创建内部退费 订单 sc
	 **************************************************************************/
	public ActionForward addRetireTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");

		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				
				airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());
			//	if(airticketOrderBiz.checkPnrisMonth(airticketOrderFrom)){//验证是否一个月内重复退费pnr 
				if(true){	
				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_19); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__1);// 结算类型
					airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_9);// 操作日志
					// 类型

				} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_29); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__1);// 结算类型
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_14);// 操作日志 类型
				}
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.getStatement().setSysUser(uri.getUser());
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);//业务类型
				airticketOrderBiz.createRetireTradingOrder(airticketOrderFrom,airticketOrder,uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder.getGroupMarkNo());
				return redirect;
			}else{			
				inf.setMessage("PNR已存在，请勿重复添加！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="+ airticketOrder.getGroupMarkNo());
				forwardPage = "inform";				
			}
			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
			System.out.println("PNR 错误");
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 通过外部（prn信息或黑屏信息）创建退费 订单 sc
	 **************************************************************************/
	public ActionForward addOutRetireTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		airticketOrderFrom.getStatement().setSysUser(uri.getUser());
		try {

			TempPNR tempPNR = uri.getTempPNR();
			if (tempPNR != null) {
				
				airticketOrderFrom.setDrawPnr(tempPNR.getPnr());// 出票pnr
				airticketOrderFrom.setSubPnr(tempPNR.getPnr());// 预订pnr
				airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());// 大pnr
				airticketOrderFrom.setTicketPrice(tempPNR.getFare());// 票面价格
				airticketOrderFrom.setAirportPrice(tempPNR.getTax());// 机建费
				airticketOrderFrom.setFuelPrice(tempPNR.getYq());// 燃油税
				
				airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1);
				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_24); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__1);// 结算类型
					airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_9);// 操作日志
					// 类型

				} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_34); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__1);// 结算类型
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_14);// 操作日志 类型
				}
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);//业务类型
				
				
				String platformId = request.getParameter("platformId");
				String companyId = request.getParameter("companyId");
				String accountId = request.getParameter("accountId");

				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (platformId != null && companyId != null
						&& accountId != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="+ airticketOrderFrom.getAccountId());
					
					platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId),
									Long.valueOf(companyId), Long.valueOf(accountId));
					if (platComAccount!=null) {//设置
						airticketOrderFrom.getStatement().setFromPCAccount(platComAccount);
					} 
				}

				airticketOrderBiz.createOutRetireTradingOrder(airticketOrderFrom, tempPNR,new AirticketOrder(),uri);
				uri.setTempPNR(null);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrderFrom
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
			System.out.println("PNR 错误");
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 审核退废 并且创建（ 收款订单） 订单 sc
	 **************************************************************************/
	public ActionForward auditRetireTrading(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
               //System.out.println("groupMarkNo=====>"+airticketOrderFrom.getGroupMarkNo());
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				//System.out.println("getPassengersCount v ====="+airticketOrder.getPassengersCount());
               AirticketOrder ao=airticketOrderBiz.getAirticketOrderByGroupMarkNor(airticketOrderFrom.getGroupMarkNo(),AirticketOrder.TRANTYPE__2);
               airticketOrderFrom.setDrawPnr(ao.getDrawPnr());
				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_21); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__2);// 结算类型
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_10);// 操作日志 类型
					airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_3);
					airticketOrder.setStatus(AirticketOrder.STATUS_20);
				} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_31); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__2);// 结算类型
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_15);// 操作日志 类型
					airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_4);
					airticketOrder.setStatus(AirticketOrder.STATUS_30);
				}
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.getStatement().setSysUser(uri.getUser());
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);//业务类型
				airticketOrderBiz.createRetireTradingOrder(airticketOrderFrom,ao,uri);
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
 						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", ao
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	
	/***************************************************************************
	 * 审核退废 并且创建（ 收款订单） 订单 sc   外部
	 **************************************************************************/
	public ActionForward auditOutRetireTrading(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		TempPNR tempPNR = uri.getTempPNR();
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
               System.out.println("groupMarkNo=====>"+airticketOrderFrom.getGroupMarkNo());
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
            
				if(airticketOrder!=null){
					airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1);
					airticketOrderFrom.setDrawPnr(airticketOrder.getDrawPnr());// 出票pnr
					airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
					airticketOrderFrom.setBigPnr(airticketOrder.getBigPnr());// 大pnr
					airticketOrderFrom.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
					airticketOrderFrom.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
					airticketOrderFrom.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
				}
				airticketOrderFrom.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
				airticketOrderFrom.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());// 设置交易金额totalAmount
				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_21); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__2);// 结算类型
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_10);// 操作日志 类型
					airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_3);
					airticketOrder.setStatus(AirticketOrder.STATUS_25);
				} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_31); // 订单状态
					airticketOrderFrom.getStatement().setType(Statement.type__2);// 结算类型
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_15);// 操作日志 类型
					airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_4);
					airticketOrder.setStatus(AirticketOrder.STATUS_35);
				}
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.getStatement().setSysUser(uri.getUser());
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);//业务类型
				
				
				String platformId = request.getParameter("platformId12");
				String companyId = request.getParameter("companyId12");
				String accountId = request.getParameter("accountId12");

				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (platformId != null && companyId != null
						&& accountId != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="+ airticketOrderFrom.getAccountId());
					
					platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId),
									Long.valueOf(companyId), Long.valueOf(accountId));
					if (platComAccount!=null) {//设置
						airticketOrderFrom.getStatement().setToPCAccount(platComAccount);
					} 
				}
				
				
				airticketOrderBiz.createOutRetireTradingOrder(airticketOrderFrom,new TempPNR(),airticketOrder,uri);
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}
	/***************************************************************************
	 * 审核退废 订单 sc 外部
	 **************************************************************************/
	public ActionForward auditOutRetireTrading2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder	.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 票号
				airticketOrder.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
				airticketOrder.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());// 设置交易金额totalAmount
				TicketLog ticketLog = new TicketLog();
				if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
					airticketOrder.setStatus(AirticketOrder.STATUS_21);
					ticketLog.setType(TicketLog.TYPE_11);// 操作日志 类型
				} else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
					airticketOrder.setStatus(AirticketOrder.STATUS_31);
					ticketLog.setType(TicketLog.TYPE_16);// 操作日志 类型
				}
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}
	/***************************************************************************
	 * 审核退废 订单 sc
	 **************************************************************************/
	public ActionForward auditRetireTrading2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder	.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 票号
				airticketOrder.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
				
				airticketOrder.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());
				TicketLog ticketLog = new TicketLog();
				if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
					airticketOrder.setStatus(AirticketOrder.STATUS_21);
					ticketLog.setType(TicketLog.TYPE_11);// 操作日志 类型
				} else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
					airticketOrder.setStatus(AirticketOrder.STATUS_31);
					ticketLog.setType(TicketLog.TYPE_16);// 操作日志 类型
				}
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 确认退票/废票/改签 收、退款 sc
	 **************************************************************************/
	public ActionForward collectionRetireTrading(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		TicketLog ticketLog = new TicketLog();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());

				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票

					ticketLog.setType(TicketLog.TYPE_12);// 操作日志 类型
					airticketOrder.setStatus(AirticketOrder.STATUS_22); // 订单状态

				} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
					ticketLog.setType(TicketLog.TYPE_17);// 操作日志 类型
					airticketOrder.setStatus(AirticketOrder.STATUS_32); // 订单状态
				}
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				if(airticketOrderFrom.getStatement().getActualAmount()!=null){
				airticketOrder.getStatement().setTotalAmount(airticketOrderFrom.getStatement().getActualAmount());
				airticketOrder.getStatement().setActualAmount(airticketOrderFrom.getStatement().getActualAmount());
			    }
				if(airticketOrderFrom.getOptTime()!=null){
					airticketOrder.setOptTime(airticketOrderFrom.getOptTime());// 操作时间
				}else{
					airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				}
				airticketOrderBiz.update(airticketOrder);
				airticketOrderBiz.resetStatementUserByAirticketOrder(airticketOrder, uri.getUser());

				// 操作日志

				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;
			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 通过外部pnr创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addOutWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		airticketOrderFrom.getStatement().setSysUser(uri.getUser());
		try {

			TempPNR tempPNR = uri.getTempPNR();
			if (tempPNR != null) {
                 
				airticketOrderFrom.setDrawPnr(tempPNR.getPnr());// 出票pnr
				airticketOrderFrom.setSubPnr(tempPNR.getPnr());// 预订pnr
				airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());// 大pnr
				airticketOrderFrom.setTicketPrice(tempPNR.getFare());// 票面价格
				airticketOrderFrom.setAirportPrice(tempPNR.getTax());// 机建费
				airticketOrderFrom.setFuelPrice(tempPNR.getYq());// 燃油税
	
				airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1);
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_46); // 订单状态
				airticketOrderFrom.getStatement().setType(Statement.type__1);// 结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_21);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);//

				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);//业务类型
				
				String platformId = request.getParameter("platformId");
				String companyId = request.getParameter("companyId");
				String accountId = request.getParameter("accountId");

				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (platformId != null && companyId != null
						&& accountId != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="+ airticketOrderFrom.getAccountId());
					
					platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId),
									Long.valueOf(companyId), Long.valueOf(accountId));
					if (platComAccount!=null) {//设置
						//airticketOrderFrom.getStatement().setFromPCAccount(platComAccount);
						airticketOrderFrom.getStatement().setToPCAccount(platComAccount);
					} 
				}
				
				airticketOrderBiz.createOutWaitAgreeUmbuchenOrder(airticketOrderFrom, tempPNR,new AirticketOrder(),uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrderFrom
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("创建失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());


				airticketOrderFrom.setStatus(AirticketOrder.STATUS_39); // 订单状态
				airticketOrderFrom.getStatement().setType(Statement.type__1);// 结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_21);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);//

				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.getStatement().setSysUser(uri.getUser());
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);//业务类型
				airticketOrderBiz.createWaitAgreeUmbuchenOrder(airticketOrderFrom, airticketOrder,uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("创建失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 审核改签 订单 sc
	 **************************************************************************/
	public ActionForward auditWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				AirticketOrder ao=airticketOrderBiz.getAirticketOrderByGroupMarkNor(airticketOrderFrom.getGroupMarkNo(),AirticketOrder.TRANTYPE__2);
				airticketOrderFrom.setDrawPnr(ao.getDrawPnr());
				airticketOrderFrom.setBigPnr(ao.getBigPnr());
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_41); // 订单状态
				airticketOrderFrom.getStatement().setType(Statement.type__2);// 结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_23);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);

				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.getStatement().setSysUser(uri.getUser());
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);//业务类型
				
				String platformId = request.getParameter("platformId5");
				String companyId = request.getParameter("companyId5");
				String accountId = request.getParameter("accountId5");
				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (platformId != null && companyId != null
						&& accountId != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="+ airticketOrderFrom.getAccountId());
					
					platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId),
									Long.valueOf(companyId), Long.valueOf(accountId));
					if (platComAccount!=null) {//设置
						//airticketOrderFrom.getStatement().setToPCAccount(platComAccount);
						airticketOrderFrom.getStatement().setFromPCAccount(platComAccount);
					} 
				}
				
				airticketOrderBiz.createWaitAgreeUmbuchenOrder(airticketOrderFrom, ao,uri);
				
				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}
	
	/***************************************************************************
	 * 审核改签 订单(外部) sc
	 **************************************************************************/
	public ActionForward auditOutWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				
				if(airticketOrder!=null){
				airticketOrderFrom.setDrawPnr(airticketOrder.getDrawPnr());// 出票pnr
				airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
				airticketOrderFrom.setBigPnr(airticketOrder.getBigPnr());// 大pnr
				airticketOrderFrom.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
				airticketOrderFrom.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
				airticketOrderFrom.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
				}
				
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_41); // 订单状态
				airticketOrderFrom.getStatement().setType(Statement.type__2);// 结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_23);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.getStatement().setSysUser(uri.getUser());
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);//业务类型
				String platformId = request.getParameter("platformId14");
				String companyId = request.getParameter("companyId14");
				String accountId = request.getParameter("accountId14");

				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (platformId != null && companyId != null
						&& accountId != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="+ airticketOrderFrom.getAccountId());
					
					platComAccount = platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId),
									Long.valueOf(companyId), Long.valueOf(accountId));
					if (platComAccount!=null) {//设置
						//airticketOrderFrom.getStatement().setToPCAccount(platComAccount);
						airticketOrderFrom.getStatement().setFromPCAccount(platComAccount);
					} 
				}
				
				airticketOrderBiz.createOutWaitAgreeUmbuchenOrder(airticketOrderFrom,new TempPNR() ,airticketOrder,uri);
				
				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;
			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 收付 改签 订单 sc
	 **************************************************************************/
	public ActionForward receiptWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());

			/*	String platformId = request.getParameter("platformId6");
				String companyId = request.getParameter("companyId6");
				String accountId = request.getParameter("accountId6");

				// 设置平台号
				PlatComAccountStore platComAccountStore = new PlatComAccountStore();
				PlatComAccount platComAccount = null;
				if (platformId != null && companyId != null
						&& accountId != null) {
					System.out.println("airticketOrderFrom.getAccountId()=="
							+ airticketOrderFrom.getAccountId());
					platComAccount = platComAccountStore
							.getPlatComAccountByAllId(Long.valueOf(platformId),
									Long.valueOf(companyId), Long
											.valueOf(accountId));
				}

				if (airticketOrder.getStatement().getType() == Statement.type_1) {
					airticketOrder.getStatement().setFromPCAccount(
							platComAccount);
				} else if (airticketOrder.getStatement().getType() == Statement.type_2) {
					airticketOrder.getStatement()
							.setToPCAccount(platComAccount);
				}*/

				if (airticketOrder.getStatement().getType() == Statement.type__1) {
					airticketOrderFrom.getStatement().setType(Statement.type__2);
					airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
					airticketOrderFrom.setGroupMarkNo(airticketOrder.getGroupMarkNo());
					List listao = airticketOrderBiz.getListByOrder(airticketOrderFrom);
					if (listao != null && listao.size() > 0) {
						AirticketOrder ao = (AirticketOrder) listao.get(0);
						ao.setStatus(AirticketOrder.STATUS_42);
						airticketOrderBiz.update(ao);
					}

				}
				if(airticketOrderFrom.getTotalAmount()!=null){
				airticketOrder.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());
				}
				airticketOrder.setStatus(AirticketOrder.STATUS_43);
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setType(TicketLog.TYPE_24);// 操作日志 类型
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 修改 订单状态 sc
	 **************************************************************************/
	public ActionForward updateOrderStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder.setStatus(airticketOrderFrom.getStatus()); // 订单状态
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrder.getStatement().setSysUser(uri.getUser());

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_6) {

					ticketLog.setType(TicketLog.TYPE_6);// 操作日志 类型
					airticketOrder.getStatement().setStatus(Statement.STATUS_1);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_7) {// 锁定
					
					airticketOrder.setCurrentOperator(uri.getUser().getUserNo());//当前操作人
					ticketLog.setType(TicketLog.TYPE_29);
                   
					
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_8) {// 解锁
                  
				if(airticketOrder.getCurrentOperator()!=null &&!"".equals(airticketOrder.getCurrentOperator())&&airticketOrder.getCurrentOperator().equals(uri.getUser().getUserNo())){
	                 airticketOrder.setCurrentOperator(null);//当前操作人  
					ticketLog.setType(TicketLog.TYPE_30);
					 }else{
						    inf.setMessage("订单已被:"+airticketOrder.getCurrentOperator()+"锁定！");
							inf.setBack(true);
							forwardPage = "inform";
							request.setAttribute("inf", inf);
							return (mapping.findForward(forwardPage));
					 }
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_44) {// 改签未通过

					ticketLog.setType(TicketLog.TYPE_27);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41) {// 改签审核通过

					ticketLog.setType(TicketLog.TYPE_23);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_42) {// 改签审核通过

					ticketLog.setType(TicketLog.TYPE_24);
				} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_45) {// 改签完成

					ticketLog.setType(TicketLog.TYPE_26);
				}else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_88) {// 废弃订单
					
					airticketOrder.getStatement().setStatus(Statement.STATUS_88);
					airticketOrder.getStatement().setOptTime(new Timestamp(System.currentTimeMillis()));
					ticketLog.setType(TicketLog.TYPE_88);
					airticketOrderBiz.update(airticketOrder);
					ticketLogBiz.saveTicketLog(ticketLog);
					ActionRedirect redirect = new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=list");
					return redirect;
				}
				
				airticketOrderBiz.update(airticketOrder);
				ticketLogBiz.saveTicketLog(ticketLog);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrder.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 手动 添加订单 sc
	 **************************************************************************/
	public ActionForward handworkAddTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom != null) {

				airticketOrderBiz.handworkAddTradingOrder(airticketOrderFrom,
						request, uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrderFrom
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 跳转 编辑订单页 sc
	 **************************************************************************/
	public ActionForward forwardEditTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				request.setAttribute("airticketOrder", airticketOrder);
				forwardPage = "editTradingOrder";

			} else {
				inf.setMessage("操作错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 修改订单 sc
	 **************************************************************************/
	public ActionForward editTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom != null) {
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_51);// 操作日志
																				// 类型
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderBiz.editTradingOrder(airticketOrderFrom, request,
						uri);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", airticketOrderFrom
						.getGroupMarkNo());
				return redirect;

			} else {
				inf.setMessage("操作错误！");
				inf.setBack(true);
				forwardPage = "inform";
			}

		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	// 团队订单录入(销售订单)
	public ActionForward saveAirticketOrderTemp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom.getAgentNo() > 0)// 客户ID
			{
				airticketOrderBiz.saveAirticketOrderTemp(airticketOrderFrom,
						uri, request, response);				
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
					+ airticketOrderFrom.getAirticketOrderId());
			} else {
				inf.setMessage("购票客户不能为空！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	// 团队订单录入（退票订单）
	public ActionForward saveRufundAirticketOrderTemp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom.getAgentNo() > 0)// 客户ID
			{
				airticketOrderBiz.saveAirticketOrderTemp(airticketOrderFrom,
						uri, request, response);

				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
						+ airticketOrderFrom.getAirticketOrderId());
			} else {
				inf.setMessage("购票客户不能为空！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 跳转原来机票添加页面
	public ActionForward saveAirticketOrderPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		try {
			AirticketOrder airticketOrder = new AirticketOrder();
			request.setAttribute("airticketOrder", airticketOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "addTeamTradingOrder";
		return mapping.findForward(forwardPage);
	}

	// 修改机票订单信息
	public ActionForward updateAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		Inform inf = new Inform();
		try {
			if (airticketOrderForm.getId() > 0) {
				long agentNo = airticketOrderForm.getAgentNo();
				airticketOrderBiz.updateAirticketOrderTemp(airticketOrderForm,
						uri, agentNo, request, response);
				
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
								+ airticketOrderForm.getId());
				
			} else {
				inf.setMessage("您改机票订单数据失败！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 添加或修改团队利润统计(编辑里的添加统计)
	public ActionForward insertTeamTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderForm = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderForm.getAirticketOrderId()>0)
			{
				airticketOrderBiz.updateAirticketOrderAgentAvia(airticketOrderForm, airticketOrderForm.getAirticketOrderId(), uri, request, response);
				
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
					+ airticketOrderForm.getAirticketOrderId());
			} else {
				inf.setMessage("您添加团队利润数据失败！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 新团队订单录入利润统计(统计利润)
	public ActionForward saveTeamTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderForm = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderForm.getAirticketOrderId()>0)
			{
				airticketOrderBiz.updateAirticketOrderAgentAvia(airticketOrderForm, airticketOrderForm.getAirticketOrderId(), uri, request, response);
				
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamForpayAirticketOrder");
			} else {
				inf.setMessage("您添加团队利润数据失败！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	// 修改团队利润(修改结算表)
	public ActionForward updateTeamStatement(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		
		if(airticketOrderForm.getAirticketOrderId()>0)//调用AirticketOrderBizImp里的修改利润显示方法
		{
			airticketOrderBiz.updaTempAirticketOrderPrice(airticketOrderForm, airticketOrderForm.getAirticketOrderId(), request, response);
		}
		Inform inf = new Inform();
		try {
			if (airticketOrderForm.getStatementId() > 0) {
				airticketOrderBiz.updateTeamStatement(airticketOrderForm, uri,
						request, response);

				inf.setMessage("您已经成功修改团队利润数据！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
								+ airticketOrderForm.getAirticketOrderId());
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("您改机票团队利润失败数据！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}
	
	
	//团队专用
	
	//确认支付
    public ActionForward teamAirticketOrderupdateAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    	AirticketOrder airticketOrderForm = (AirticketOrder)form;
    	UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
		"URI");
    		String forwardPage ="";
    		Inform inf = new Inform();
    		long airticketOrderId = airticketOrderForm.getAirticketOrderId();
    		try {
				if(airticketOrderId>0)
				{
					airticketOrderBiz.editTeamAirticketOrderOK(airticketOrderForm, airticketOrderId,uri, request, response);
					inf.setMessage("确认支付成功！");
					inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=listTeamWaitOutAirticketOrder");
				}else {
					inf.setMessage("确认支付失败！");
					inf.setBack(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}    		
			request.setAttribute("inf", inf);
			 forwardPage = "inform";
			return (mapping.findForward(forwardPage));
    	
    }
    
	/***************************************************************************
	 * 通过PNR接口获取数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByPNR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom.getPnr() != null&& !"".equals(airticketOrderFrom.getPnr().trim())) {
				
				AirticketOrder checkAO=new AirticketOrder();
				checkAO.setTranType(AirticketOrder.TRANTYPE__1);
				checkAO.setSubPnr(airticketOrderFrom.getPnr());
				boolean checkPnr=airticketOrderBiz.checkPnrisToday(checkAO);
				 //验证pnr是否重复添加
				if(true){
				System.out.println("airticketOrderFrom.getPnr() ---"
						+ airticketOrderFrom.getPnr());
				TempPNR tempPNR = tempPNRBiz.getTempPNRByPnr(airticketOrderFrom
						.getPnr());
				if (tempPNR != null && tempPNR.getRt_parse_ret_value() != 0L) {
					request.setAttribute("tempPNR", tempPNR);
					airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
					// 设置临时会话
					UserRightInfo uri = (UserRightInfo) request.getSession()
							.getAttribute("URI");
					uri.setTempPNR(tempPNR);
					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("PNR 错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
					System.out.println("PNR 错误");
				}
				}else{
					inf.setMessage("PNR已存在，请勿重复添加！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				
				}
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}
    
    
    /***************************************************************************
	 * 通过PNR获取外部数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByOutPNR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom.getPnr() != null
					&& !"".equals(airticketOrderFrom.getPnr().trim())) {
				TempPNR tempPNR = tempPNRBiz.getTempPNRByPnr(airticketOrderFrom
						.getPnr());
				if (tempPNR != null && tempPNR.getRt_parse_ret_value() != 0L) {

					airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());

					AirticketOrder ao = new AirticketOrder();
					ao.setDrawPnr(tempPNR.getPnr());// 出票pnr
					ao.setSubPnr(tempPNR.getPnr());// 预订pnr
					ao.setBigPnr(tempPNR.getB_pnr());// 大pnr
					ao.setTicketPrice(tempPNR.getFare());// 票面价格
					ao.setAirportPrice(tempPNR.getTax());// 机建费
					ao.setFuelPrice(tempPNR.getYq());// 燃油税

					// 乘机人
					List<TempPassenger> tempPassengerList = tempPNR
							.getTempPassengerList();
					Set tmpPassengerSet = new HashSet();
					List tempTicketsList = tempPNR.getTempTicketsList();
					for (int i = 0; i < tempPassengerList.size(); i++) {
						TempPassenger tempPassenger = tempPassengerList.get(i);
						Passenger passenger = new Passenger();
						passenger.setAirticketOrder(ao);
						passenger.setName(tempPassenger.getName()); // 乘机人姓名
						passenger.setCardno(tempPassenger.getNi());// 证件号
						passenger.setType(1L); // 类型
						passenger.setStatus(1L);// 状态
						if (tempTicketsList != null
								&& tempTicketsList.size() == tempPassengerList
										.size()) {
							System.out.println("tempTicketsList===="
									+ tempPNR.getTempTicketsList().get(i));
							passenger.setTicketNumber(tempTicketsList.get(i)
									.toString()); // 票号
						}
						tmpPassengerSet.add(passenger);
					}
					ao.setPassengers(tmpPassengerSet);
					// 航班
					List<TempFlight> tempFlightList = tempPNR
							.getTempFlightList();
					Set tmpFlightSet = new HashSet();
					for (TempFlight tempFlight : tempFlightList) {
						Flight flight = new Flight();
						flight.setAirticketOrder(ao);
						flight.setFlightCode(tempFlight.getAirline());// 航班号
						flight.setStartPoint(tempFlight.getDepartureCity()); // 出发地
						flight.setEndPoint(tempFlight.getDestineationCity());// 目的地
						flight.setBoardingTime(tempFlight.getStarttime());// 起飞时间
						flight.setDiscount(tempFlight.getDiscount());// 折扣
						flight.setFlightClass(tempFlight.getCabin());// 舱位
						flight.setStatus(1L); // 状态
						tmpFlightSet.add(flight);

					}
					ao.setFlights(tmpFlightSet);
					ao.setAddType("OutPNR");// 设置添加类型
					// 设置临时会话
					UserRightInfo uri = (UserRightInfo) request.getSession()
							.getAttribute("URI");
					uri.setTempPNR(tempPNR);
					request.setAttribute("airticketOrder", ao);
					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("PNR 错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
					System.out.println("PNR 错误");
				}
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}
	public void setTempPNRBiz(TempPNRBiz tempPNRBiz) {
		this.tempPNRBiz = tempPNRBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}

	public void setPassengerBiz(PassengerBiz passengerBiz) {
		this.passengerBiz = passengerBiz;
	}

	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}

}
