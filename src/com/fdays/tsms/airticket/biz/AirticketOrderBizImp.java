package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;

import com.fdays.tsms.airticket.AirticketGroup;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.OrderGroup;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TeamProfit;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.fdays.tsms.airticket.dao.PassengerDAO;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.airticket.util.AirticketOrderStore;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.base.NoUtil;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.base.util.UnitConverter;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.dao.TicketLogDAO;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.dao.AccountDAO;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.CompanyDAO;
import com.fdays.tsms.transaction.dao.PlatComAccountDAO;
import com.fdays.tsms.transaction.dao.PlatformDAO;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.fdays.tsms.user.SysUser;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderBizImp implements AirticketOrderBiz
{
	private AirticketOrderDAO airticketOrderDAO;
	private FlightDAO flightDAO;
	private PassengerDAO passengerDAO;
	private StatementDAO statementDAO;
	private NoUtil noUtil;
	private AgentDAO agentDAO;
	private TicketLogDAO ticketLogDAO;
	private FlightPassengerBiz flightPassengerBiz;
	private PlatComAccountDAO platComAccountDAO;
	private PlatformDAO platformDAO;
	private CompanyDAO companyDAO;
	private AccountDAO accountDAO;

	// B2B订单录入
	public String createOrder(HttpServletRequest request, AirticketOrder form)
	    throws AppException
	{
		String forwardPage = "";
		Inform inf = new Inform();
	
		TempPNR tempPNR = (TempPNR) request.getSession().getAttribute("tempPNR");

		if (tempPNR != null)
		{
			form.setStatus(AirticketOrder.STATUS_1); // 订单状态
			form.setTicketType(AirticketOrder.TICKETTYPE_1); // 设置机票类型
			form.getTicketLog().setType(TicketLog.TYPE_1);// 操作日志

			// 创建正常销售订单
			forwardPage = createOrderByTempPNR(form, tempPNR,request);

			request.getSession().setAttribute("tempPNR", null);
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			inf.setMessage("请导入黑屏信息！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		return forwardPage;
	}

	/**
	 * 创建B2B销售订单调用
	 */
	public String createOrderByTempPNR(AirticketOrder form, TempPNR tempPNR,
	   HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		
		// 机票订单
		AirticketOrder newOrder = new AirticketOrder();
		newOrder.setSubPnr(form.getSubPnr());// 预订PNR
		if ("".equals(Constant.toString(form.getBigPnr())) == false)
		{
			newOrder.setBigPnr(form.getBigPnr());// 大PNR
		}
		newOrder.setTicketPrice(tempPNR.getFare());// 票面价格
		newOrder.setAirportPrice(tempPNR.getTax());// 机建费
		newOrder.setFuelPrice(tempPNR.getYq());// 燃油税

		if (Constant.toLong(form.getAgentId()) > 0)
		{
			Agent agent = agentDAO.getAgentByid(form.getAgentId());
			newOrder.setAgent(agent); // 购票客户
		}
		else
		{
			newOrder.setAgent(null);
		}

		newOrder.setDocumentPrice(form.getDocumentPrice());// 行程单费用
		newOrder.setInsurancePrice(form.getInsurancePrice());// 保险费
		newOrder.setRebate(form.getRebate());// 政策
		newOrder.setAirOrderNo(form.getAirOrderNo());// 机票订单号

		newOrder.setStatus(form.getStatus()); // 订单状态
		newOrder.setTicketType(form.getTicketType());// 机票类型
		newOrder.setTranType(AirticketOrder.TRANTYPE__1);// 交易类型

		newOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
		newOrder.setEntryOperator(uri.getUser().getUserNo());
		if (form.getOptTime() != null)
		{
			newOrder.setEntryTime(form.getOptTime());
		}
		else
		{
			newOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
		}

		newOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		if (newOrder.getTicketType() == AirticketOrder.TICKETTYPE_1)
		{
			newOrder.setOperate1(uri.getUser().getUserNo());
			newOrder.setOperate1Time(new Timestamp(System.currentTimeMillis()));
			newOrder.setOperate2(uri.getUser().getUserNo());
			newOrder.setOperate2Time(new Timestamp(System.currentTimeMillis()));
		}

		// 设置平台公司帐号
		Long platformId = form.getPlatformId();
		Long companyId = form.getCompanyId();
		Long accountId = form.getAccountId();
		if (platformId != null && companyId != null && accountId != null)
		{
			newOrder.setPlatform(platformDAO.getPlatformById(platformId));
			newOrder.setCompany(companyDAO.getCompanyById(companyId));
			newOrder.setAccount(accountDAO.getAccountById(accountId));
		}
		if (newOrder.getPlatform() == null || newOrder.getCompany() == null
		    || newOrder.getAccount() == null) { return "ACCOUNTERROR"; }
		newOrder.setTotalAmount(form.getTotalAmount());// 总金额

		// 创建一个新组
		OrderGroup og = saveOrderGroup(newOrder);

		newOrder.setOrderGroup(og);
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(og.getId());
		newOrder.setSubGroupMarkNo(newSubGroupNo);
		airticketOrderDAO.save(newOrder);
		flightPassengerBiz.saveFlightPassengerByTempPNR(tempPNR, newOrder);

		// 收款
		saveStatementByOrder(newOrder, uri.getUser(), Statement.type_1,
		    Statement.SUBTYPE_10, Statement.STATUS_1, new Timestamp(System
		        .currentTimeMillis()));

		// 销售订单录入日志
		saveAirticketTicketLog(newOrder, uri.getUser(),request, form.getTicketLog().getType(),"");

		// 销售-收款-日志
		saveAirticketTicketLog(newOrder, uri.getUser(),request, TicketLog.TYPE_2,"");
		
		String forwardPage = newOrder.getOrderGroup().getId() + "";

		return forwardPage;
	}

	/**
	 * 申请支付订单
	 * 
	 * @普通：进入待锁定状态
	 * @自动支付：完成确认支付,进入等待出票
	 */
	public String applyPayOrder(AirticketOrder form,AirticketOrder order, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		if (form != null && form.getId() > 0)
		{
//			AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form.getId());
	
			if(Constant.toLong(order.getTranType()).compareTo(AirticketOrder.TRANTYPE__1)==0){
				if(Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_1)!=0){
					System.out.println("----1、申请支付状态异常。。");
					return AirticketOrder.ORDER_GROUP_TYPE1 + "";
				}
			}else if(Constant.toLong(order.getTranType()).compareTo(AirticketOrder.TRANTYPE__2)==0){
				if(Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_4)!=0
						||Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_6)!=0){
					System.out.println("----2、申请支付状态异常。。");
					return AirticketOrder.ORDER_GROUP_TYPE1 + "";
				}
			}else{
				System.out.println("----2、申请支付TranType异常。。");
				return AirticketOrder.ORDER_GROUP_TYPE1 + "";
			}			
			
			// 机票订单
			AirticketOrder newOrder = new AirticketOrder();
			newOrder.setSubPnr(form.getPnr());// 预订pnr
			newOrder.setBigPnr(order.getBigPnr());// 大pnr
			newOrder.setTicketPrice(order.getTicketPrice());// 票面价格
			newOrder.setAirportPrice(order.getAirportPrice());// 机建费
			newOrder.setFuelPrice(order.getFuelPrice());// 燃油税
			newOrder.setAgent(order.getAgent()); // 购票客户
			newOrder.setDocumentPrice(order.getDocumentPrice());// 行程单费用
			newOrder.setInsurancePrice(form.getInsurancePrice());// 保险费
			newOrder.setRebate(form.getRebate());// 政策
			newOrder.setAirOrderNo(form.getAirOrderNo());// 机票订单号
			newOrder.setTicketType(order.getTicketType());// 机票类型

			newOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

			if (form.getOptTime() != null)
			{
				newOrder.setEntryTime(form.getOptTime());// 录入订单时间
			}
			else
			{
				newOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
			}

			newOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

			newOrder.setEntryOperator(uri.getUser().getUserNo());
			newOrder.setOperate13(uri.getUser().getUserNo());
			newOrder.setOperate13Time(new Timestamp(System.currentTimeMillis()));
			newOrder.setTranType(AirticketOrder.TRANTYPE__2);// 交易类型
			newOrder.setTotalAmount(form.getTotalAmount());// 总金额

			// 修改一个新组的时间
			updateOrderGroup(order);
			newOrder.setOrderGroup(order.getOrderGroup());
			newOrder.setSubGroupMarkNo(order.getSubGroupMarkNo());

			// 设置平台公司帐号
			long platformId = form.getPlatformId();
			long companyId = form.getCompanyId();
			long accountId = form.getAccountId();

			newOrder.setPlatform(platformDAO.getPlatformById(platformId));
			newOrder.setCompany(companyDAO.getCompanyById(companyId));
			newOrder.setAccount(accountDAO.getAccountById(accountId));

			if (newOrder.getPlatform() == null || newOrder.getCompany() == null
			    || newOrder.getAccount() == null) { return "ACCOUNTERROR"; }
			if (isAutoPay(newOrder))
			{
				autoPayOrder(order, newOrder, request, uri);
			}
			else
			{
				newOrder.setStatus(AirticketOrder.STATUS_2); // 待处理
				airticketOrderDAO.save(newOrder);
				flightPassengerBiz.saveFlightPassengerByOrder(order, newOrder);

				// 申请支付--日志
				saveAirticketTicketLog(newOrder, uri.getUser(),request, TicketLog.TYPE_13,"");
			}

			// 修改原销售订单信息
			order.setStatus(AirticketOrder.STATUS_3);
			airticketOrderDAO.update(order);				
			
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}			
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	/**
	 * @账号-BSP：申请即支付
	 * @账号-易宝信用5838 支付宝信用支付 快钱信用支付：申请即支付
	 * @账号-AUTOPAY :申请即支付
	 */
	private boolean isAutoPay(AirticketOrder newOrder) throws AppException
	{
		String platformName = Constant
		    .toUpperCase(newOrder.getPlatform().getName());
		String accountName = Constant.toUpperCase(newOrder.getAccount().getName());
		Long drawType=Constant.toLong(newOrder.getPlatform().getDrawType());
		if (platformName.indexOf("BSP") >= 0) { return true; }
		if (accountName.indexOf("AUTOPAY") >= 0) { return true; }
		if (accountName.indexOf("易宝信用5838") >= 0) { return true; }
		if (accountName.indexOf("支付宝信用支付") >= 0) { return true; }
		if (accountName.indexOf("快钱信用支付") >= 0) { return true; }
		if (accountName.indexOf("汇付信用支付") >= 0) {
			if(drawType==Platform.draw_type_1){//网电
				if (platformName.indexOf("B2A-ZH") >= 0) { 
					return false;
				}else if (platformName.indexOf("B2B-ZH") >= 0) { 
					return false;
				}
				else{
					return true;
				}
			}else{
				return false;
			}			
		}		
		return false;
	}

	// 自动确认支付
	private void autoPayOrder(AirticketOrder oldorder, AirticketOrder neworder,
	    HttpServletRequest request, UserRightInfo uri) throws AppException
	{
		neworder.setStatus(AirticketOrder.STATUS_3); // 跳过支付,进入等待出票
		neworder.setPayOperator(uri.getUser().getUserNo());
		neworder.setPayTime(new Timestamp(System.currentTimeMillis()));
		neworder.setOperate15(uri.getUser().getUserNo());
		neworder.setOperate15Time(new Timestamp(System.currentTimeMillis()));

		airticketOrderDAO.save(neworder);
		flightPassengerBiz.saveFlightPassengerByOrder(oldorder, neworder);

		// 付款
		saveStatementByOrder(neworder, uri.getUser(), Statement.type_2,
		    Statement.SUBTYPE_20, Statement.STATUS_1, neworder.getEntryTime());

		// 申请支付--日志
		saveAirticketTicketLog(neworder, uri.getUser(),request, TicketLog.TYPE_13,"");
		// 确认支付--日志
		saveAirticketTicketLog(neworder, uri.getUser(), request, TicketLog.TYPE_15,"");
	}

	// 重新申请支付
	public String anewApplyPayOrder(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		if (form != null && form.getGroupMarkNo() != null)
		{
			if (form.getId() > 0)
			{
				AirticketOrder ao = airticketOrderDAO.getAirticketOrderById(form
				    .getId());

				if (ao.getStatus() == AirticketOrder.STATUS_4)
				{
					ao.setStatus(AirticketOrder.STATUS_14);
					airticketOrderDAO.update(ao);
				}
				else if (ao.getStatus() == AirticketOrder.STATUS_6)
				{
					ao.setStatus(AirticketOrder.STATUS_15);
					airticketOrderDAO.update(ao);
				}
			}

			// 原销售订单订单
			AirticketOrder airticketOrder = getAirticketOrderByGroupIdAndTranType(
			    form.getGroupId(), "1");
			airticketOrder.setStatus(AirticketOrder.STATUS_3);
			airticketOrderDAO.update(airticketOrder);// 修改原订单信息

			// 新建买入订单
			AirticketOrder ao = new AirticketOrder();
			ao.setSubPnr(form.getPnr());// 预订pnr
			ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
			ao.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
			ao.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
			ao.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
			ao.setAgent(airticketOrder.getAgent()); // 购票客户
			ao.setDocumentPrice(airticketOrder.getDocumentPrice());// 行程单费用
			ao.setInsurancePrice(form.getInsurancePrice());// 保险费
			ao.setRebate(form.getRebate());// 政策
			ao.setAirOrderNo(form.getAirOrderNo());// 机票订单号

			ao.setOrderGroup(airticketOrder.getOrderGroup());
			updateOrderGroup(ao);// 订单组编号

			ao.setSubGroupMarkNo(airticketOrder.getSubGroupMarkNo());
			ao.setStatus(AirticketOrder.STATUS_2); // 订单状态
			ao.setTicketType(airticketOrder.getTicketType());// 机票类型
			ao.setTranType(Statement.type_2);// 交易类型

			ao.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
			ao.setEntryOperator(uri.getUser().getUserNo());
			ao.setPayOperator(uri.getUser().getUserNo());

			if (form.getOptTime() != null)
			{
				ao.setEntryTime(form.getOptTime());
			}
			else
			{
				ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
			}

			ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ao.setOperate13(uri.getUser().getUserNo());
			ao.setOperate13Time(new Timestamp(System.currentTimeMillis()));

			// 设置平台公司帐号
			long platformId = form.getPlatformId();
			long companyId = form.getCompanyId();
			long accountId = form.getAccountId();
			ao.setPlatform(platformDAO.getPlatformById(platformId));
			ao.setCompany(companyDAO.getCompanyById(companyId));
			ao.setAccount(accountDAO.getAccountById(accountId));

			if (ao.getPlatform() == null || ao.getCompany() == null
			    || ao.getAccount() == null) { return "ACCOUNTERROR"; }
			ao.setTotalAmount(form.getTotalAmount());// 总金额

			if (isAutoPay(ao))
			{
				autoPayOrder(airticketOrder, ao, request, uri);
			}
			else
			{
				ao.setStatus(AirticketOrder.STATUS_2); // 等待支付
				airticketOrderDAO.save(ao);
				flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);

				// 申请支付--日志
				saveAirticketTicketLog(ao, uri.getUser(), null,TicketLog.TYPE_13,"");
			}
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 锁定
	public String lockupOrder(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		if (form != null && form.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form.getId());

			if (Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_2)==0||Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_8)==0)
			{
				order.setStatus(AirticketOrder.STATUS_7); // 订单状态
				order.setCurrentOperator(uri.getUser().getUserNo());// 当前操作人

				order.setOperate14(uri.getUser().getUserNo());
				order.setOperate14Time(new Timestamp(System
				    .currentTimeMillis()));
				airticketOrderDAO.update(order);

				updateOrderGroup(order);

				saveAirticketTicketLog(order, uri.getUser(), request,TicketLog.TYPE_14,"");
				forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
			}
			else
			{
				forwardPage = "OVERLOOKED";
			}
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 解锁(自己的订单)
	public String unlockSelfOrder(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		if (form != null && form.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form.getId());	
			if(order!=null){
			if(Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_7)!=0){
				if (!"".equals(Constant.toString(order.getCurrentOperator())))
				{
					if (order.getCurrentOperator().equals(uri.getUser().getUserNo()))
					{
						order.setStatus(AirticketOrder.STATUS_8); // 订单状态
						order.setCurrentOperator(null);// 当前操作人
						order.setOperate16(uri.getUser().getUserNo());
						order.setOperate16Time(new Timestamp(System
						    .currentTimeMillis()));
						airticketOrderDAO.update(order);

						updateOrderGroup(order);

						saveAirticketTicketLog(order, uri.getUser(), request,
						    TicketLog.TYPE_16,"");
						forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
					}
					else
					{
						System.out.println("--orderId:" + order.getId() + "解锁人与锁定人不符");
						return "ERROR";
					}
				}
				else
				{
					System.out.println("--orderId:" + order.getId() + "没有当前操作人");
					return "ERROR";
				}
			}else{
				System.out.println("--orderId:" + order.getId() + "状态已解锁");
				forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
			}
			}else
			{
				forwardPage = "NOORDER";
			}
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 解锁（所有订单）
	public String unlockAllOrder(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		if (form != null && form.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form.getId());

			order.setStatus(AirticketOrder.STATUS_8); // 订单状态
			order.setCurrentOperator(null);// 当前操作人
			order.setOperate17(uri.getUser().getUserNo());
			order.setOperate17Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(order);

			updateOrderGroup(order);

			saveAirticketTicketLog(order, uri.getUser(), request,
			    TicketLog.TYPE_17,"");

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 确认支付
	public String confirmPayment(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		if (form != null && form.getId() > 0)
		{
			AirticketOrder order = getAirticketOrderById(form.getId());

			if(order!=null){
			
			if (Constant.toLong(order.getStatus()).compareTo(AirticketOrder.STATUS_7)==0)
			{
				// 设置平台公司帐号
				Long platformId = form.getPlatformId();
				Long companyId = form.getCompanyId();
				Long accountId = form.getAccountId();
				order.setPlatform(platformDAO.getPlatformById(platformId));
				order.setCompany(companyDAO.getCompanyById(companyId));
				order.setAccount(accountDAO.getAccountById(accountId));

				if (order.getPlatform() == null || order.getCompany() == null
				    || order.getAccount() == null) { return "ACCOUNTERROR"; }

				order.setStatus(AirticketOrder.STATUS_3); // 修改订单状态
				order.setSubPnr(form.getPnr());
				order.setAirOrderNo(form.getAirOrderNo());// 订单号
				order.setRebate(form.getRebate());// 政策
				order.setTotalAmount(form.getTotalAmount());// 设置金额
				order.setPayOperator(uri.getUser().getUserNo());
				if (form.getOptTime() != null)
				{
					order.setPayTime(form.getOptTime());
				}
				else
				{
					order.setPayTime(new Timestamp(System.currentTimeMillis()));
				}

				order.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

				order.setOperate15(uri.getUser().getUserNo());
				order.setOperate15Time(new Timestamp(System.currentTimeMillis()));
				order.setCurrentOperator(null);// 当前操作人
				update(order);

				// 付款
				saveStatementByOrder(order, uri.getUser(), Statement.type_2,
				    Statement.SUBTYPE_20, Statement.STATUS_1, order.getPayTime());

				updateOrderGroup(order);

				// 操作日志
				saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_15,"");
			}
			else
			{
				System.out.println("order_no:" + order.getOrderNo() + "--确认支付,状态异常:"
				    + order.getStatusText());
			}
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
			}else{
				forwardPage = "NOORDER";
			}
		}
			
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 确认出票
	public String confirmTicket(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (form != null && form.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(form.getId());
			Long groupId = airticketOrder.getOrderGroup().getId();

			airticketOrder.setStatus(AirticketOrder.STATUS_5);
			airticketOrder.setDrawPnr(form.getDrawPnr());// 出票pnr

			// 获取乘客信息
			String[] ticketNumber = request.getParameterValues("ticketNumber");

			List<AirticketOrder> listao = new ArrayList<AirticketOrder>();
			listao.add(airticketOrder);
			AirticketOrder ao1 = getAirticketOrderByGroupIdAndTranType(groupId, "1");

			if (ao1 != null && ao1.getId() > 0)
			{
				ao1.setStatus(AirticketOrder.STATUS_5);
				ao1.setDrawPnr(form.getDrawPnr());// 出票pnr
				ao1.setDrawTime(new Timestamp(System.currentTimeMillis()));// 出票时间
				ao1.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrder.setOperate5(uri.getUser().getUserNo());
				airticketOrder
				    .setOperate5Time(new Timestamp(System.currentTimeMillis()));
				airticketOrderDAO.update(ao1);
				listao.add(ao1);
			}
			for (AirticketOrder ao : listao)
			{
				List pa = passengerDAO.listByairticketOrderId(ao.getId());
				if (pa != null && pa.size() == ticketNumber.length)
				{
					for (int i = 0; i < pa.size(); i++)
					{
						if (ao.getId() > 0)
						{
							Passenger passenger = (Passenger) pa.get(i);
							passenger.setTicketNumber(ticketNumber[i].trim());
							passengerDAO.update(passenger);
						}
					}
				}
			}

			airticketOrder.setDrawTime(new Timestamp(System.currentTimeMillis()));// 出票时间
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrder.setOperate5(uri.getUser().getUserNo());
			airticketOrder.setOperate5Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			// -------------
			setNeverRequestAsDrawTicket(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
			    TicketLog.TYPE_5,"");
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 卖出订单 取消出票
	public String quitSaleTicket(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (form != null && form.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(form.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			airticketOrder.setStatus(AirticketOrder.STATUS_10);

			String memo = Constant.toString(form.getMemo());
			String quitTicketType = Constant.toString(form.getQuitTicketType());
			String quitTicketReason = Constant.toString(form.getQuitTicketReason());
			memo = quitTicketType + "/" + quitTicketReason + "/" + memo;
			airticketOrder.setMemo(memo);

			airticketOrder.setOperate4(uri.getUser().getUserNo());
			airticketOrder.setOperate4Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			setNeverRequestAsQuiutSaleTicket(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
			    TicketLog.TYPE_4,"");
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	/**
	 * @param AirticketOrder
	 *          当前操作的订单 卖出取消出票 买入订单进入相应的结束状态
	 */
	private void setNeverRequestAsQuiutSaleTicket(AirticketOrder airticketOrder)
	    throws AppException
	{
		LogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		Long groupId = airticketOrder.getOrderGroup().getId();
		Long subGroupMarkNo = airticketOrder.getSubGroupMarkNo();
		if (groupId != null && groupId > 0)
		{
			List<AirticketOrder> tempList = airticketOrderDAO
			    .listBySubGroupAndGroupId(groupId, subGroupMarkNo);
			if (tempList != null)
			{
				for (int i = 0; i < tempList.size(); i++)
				{
					AirticketOrder tempOrder = (AirticketOrder) tempList.get(i);
					Long tempStatus = tempOrder.getStatus();
					if (tempStatus != null && tempStatus > 0)
					{

						// 买入订单进入取消出票流程

						// 未支付
						if (tempStatus == AirticketOrder.STATUS_2
						    || tempStatus == AirticketOrder.STATUS_4
						    || tempStatus == AirticketOrder.STATUS_7
						    || tempStatus == AirticketOrder.STATUS_8)
						{

							tempOrder.setStatus(AirticketOrder.STATUS_14);
							myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
							    + tempOrder.getId() + "--卖出取消出票,设置买入单终止");
						}

						// 已经支付的，进入退款流程
						if (tempStatus == AirticketOrder.STATUS_3)
						{
							tempOrder.setStatus(AirticketOrder.STATUS_13);
							myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
							    + tempOrder.getId() + "--卖出取消出票,设置买入单进入待取消出票状态");
						}

						// 取消出票已退款，订单终止
						if (tempStatus == AirticketOrder.STATUS_6
						    || tempStatus == AirticketOrder.STATUS_16)
						{
							tempOrder.setStatus(AirticketOrder.STATUS_15);
							myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
							    + tempOrder.getId() + "--卖出取消出票,设置买入单终止");
						}

					}
					airticketOrderDAO.update(tempOrder);
				}
			}
		}
	}

	// 买入订单，取消出票
	public String quitBuyTicket(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (form != null && form.getId() > 0)
		{
			AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form
			    .getId());
			Long status = order.getStatus();

			if (status == AirticketOrder.STATUS_3
			    || status == AirticketOrder.STATUS_13)
			{// 支付成功
				order.setStatus(AirticketOrder.STATUS_9);// 等待退款（订单已支付过）
			}
			else
			{
				boolean result = isSaleQuiutTicket(order);
				if (result)
				{
					order.setStatus(AirticketOrder.STATUS_14);// 取消出票（终止）
				}
				else
				{
					order.setStatus(AirticketOrder.STATUS_4);// 取消出票（订单未终止）
				}
			}

			String memo = Constant.toString(form.getMemo());
			String quitTicketType = Constant.toString(form.getQuitTicketType());
			String quitTicketReason = Constant.toString(form.getQuitTicketReason());
			memo = quitTicketType + "/" + quitTicketReason + "/" + memo;
			order.setMemo(memo);

			order.setOperate4(uri.getUser().getUserNo());
			order.setOperate4Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(order);

			updateOrderGroup(order);

			// 操作日志
			saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_4,"");
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 判断同组卖出订单是否取消出票
	public boolean isSaleQuiutTicket(AirticketOrder order) throws AppException
	{
		boolean result = false;
		try
		{
			List<AirticketOrder> tempList = airticketOrderDAO
			    .listBySubGroupAndGroupId(order.getOrderGroup().getId(), order
			        .getSubGroupMarkNo());
			for (int i = 0; i < tempList.size(); i++)
			{
				AirticketOrder tempOrder = tempList.get(i);
				if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__1)
				{
					if (tempOrder.getStatus() == AirticketOrder.STATUS_10
					    || tempOrder.getStatus() == AirticketOrder.STATUS_15)
					{
						result = true;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	// 取消出票,确认退款
	public String agreeCancelRefund(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (form != null && form.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form
			    .getId());

			long businessType = order.getBusinessType();
			Account refundAccount = null;
			if (businessType == 1)
			{ // 卖出 取消出票，收款账号付退款

				refundAccount = statementDAO.getStatementAccountByOrderSubType(order
				    .getId(), Statement.SUBTYPE_10, Statement.ORDERTYPE_1);

				if (refundAccount != null)
				{
					order.setAccount(refundAccount);
					if (form.getTotalAmount() != null)
					{
						order.setTotalAmount(form.getTotalAmount());
						if (form.getOptTime() != null)
						{
							saveStatementByOrder(order, uri.getUser(), Statement.type_2,
							    Statement.SUBTYPE_21, Statement.STATUS_1, form.getOptTime());
						}
					}
				}
				else
				{
					return "STATEMENTACCOUNTERROR";
				}

				order.setOperate20(uri.getUser().getUserNo());
				order.setOperate20Time(new Timestamp(System.currentTimeMillis()));
				order.setStatus(AirticketOrder.STATUS_15); // 取消出票已经退款（终止）
				saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_20,"");
			}
			else if (businessType == 2)
			{// 买入 取消出票，付款账号收退款
				refundAccount = statementDAO.getStatementAccountByOrderSubType(order
				    .getId(), Statement.SUBTYPE_20, Statement.ORDERTYPE_1);
				if (refundAccount != null)
				{
					order.setAccount(refundAccount);
					if (form.getTotalAmount() != null)
					{
						order.setTotalAmount(form.getTotalAmount());
						if (form.getOptTime() != null)
						{
							saveStatementByOrder(order, uri.getUser(), Statement.type_1,
							    Statement.SUBTYPE_11, Statement.STATUS_1, form.getOptTime());
						}
					}
				}
				else
				{
					return "STATEMENTACCOUNTERROR";
				}

				saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_21,"");
				order.setOperate21(uri.getUser().getUserNo());
				order.setOperate21Time(new Timestamp(System.currentTimeMillis()));

				boolean result = isSaleQuiutTicket(order);
				if (result)
				{
					order.setStatus(AirticketOrder.STATUS_15); // 取消出票已经退款（终止）
				}
				else
				{
					order.setStatus(AirticketOrder.STATUS_6); // 取消出票已经退款（未终止）
				}
			}

			order.setOperate4(uri.getUser().getUserNo());
			order.setOperate4Time(new Timestamp(System.currentTimeMillis()));

			airticketOrderDAO.update(order);

			updateOrderGroup(order);

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	/**
	 * @param AirticketOrder
	 *          当前操作的订单 买入确认出票，另外的买入如果有取消出票（status=4）的单改为status=14
	 */
	private void setNeverRequestAsDrawTicket(AirticketOrder airticketOrder)
	    throws AppException
	{
		LogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");

		Long currentTranType = airticketOrder.getTranType();
		Long groupId = airticketOrder.getOrderGroup().getId();
		if (currentTranType != null && currentTranType > 0 && groupId != null
		    && groupId > 0)
		{
			if (currentTranType == AirticketOrder.TRANTYPE__2)
			{
				List tempList = airticketOrderDAO.listByGroupIdAndTranTypeStatus(
				    groupId, AirticketOrder.TRANTYPE__2 + "", AirticketOrder.STATUS_4
				        + "," + AirticketOrder.STATUS_6);
				if (tempList != null)
				{
					for (int i = 0; i < tempList.size(); i++)
					{
						AirticketOrder tempOrder = (AirticketOrder) tempList.get(i);
						Long currentStatus = airticketOrder.getTranType();
						if (currentStatus != null && currentStatus > 0)
						{
							if (tempOrder.getStatus() == AirticketOrder.STATUS_4)
							{
								tempOrder.setStatus(AirticketOrder.STATUS_14);
								myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
								    + tempOrder.getId() + "--买入确认出票,设置买入单(取消出票,未支付)不得再次申请");
							}
							if (tempOrder.getStatus() == AirticketOrder.STATUS_6)
							{
								tempOrder.setStatus(AirticketOrder.STATUS_15);
								myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
								    + tempOrder.getId() + "--买入确认出票,设置买入单(已经退款)不得再次申请");
							}
						}
						airticketOrderDAO.update(tempOrder);
					}
				}
			}
		}
	}

	// 编辑备注
	public String editRemark(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";

		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (form != null && form.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(form.getId());
			airticketOrder.setMemo(form.getMemo());

			airticketOrder.setOperate201(uri.getUser().getUserNo());
			airticketOrder.setOperate201Time((new Timestamp(System
			    .currentTimeMillis())));

			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
			    TicketLog.TYPE_201,"");

			forwardPage = getForwardPageByOrderType(airticketOrder);
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	/**
	 * 改签业务调用，需改造。
	 */
	public String updateAirticketOrderStatus(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (form != null && form.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(form.getId());

			Long ticketLogType = null;
			if (form.getStatus() == AirticketOrder.STATUS_44)
			{// 改签未通过
				ticketLogType = TicketLog.TYPE_80;
			}
			else if (form.getStatus() == AirticketOrder.STATUS_41)
			{// 改签审核通过
				ticketLogType = TicketLog.TYPE_73;
			}
			else if (form.getStatus() == AirticketOrder.STATUS_42)
			{// 改签审核通过
				ticketLogType = TicketLog.TYPE_73;
			}
			else if (form.getStatus() == AirticketOrder.STATUS_45)
			{// 改签完成
				ticketLogType = TicketLog.TYPE_76;
			}

			airticketOrder.setStatus(form.getStatus()); // 订单状态
			airticketOrderDAO.update(airticketOrder);
			updateOrderGroup(airticketOrder);

			saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
			    ticketLogType,"");

			forwardPage = getForwardPageByOrderType(airticketOrder);
		}
		else
		{
			forwardPage = "NOERROR";
		}
		return forwardPage;
	}

	// 创建退废票(创建卖出退废票)
	public String addRetireOrder(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		try
		{
			if (form != null && form.getId() > 0)
			{
				AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form.getId());
				form.setSubPnr(order.getSubPnr());

				String[] passengerId = form.getPassengerIds();
				
				if (form.getTranType() == AirticketOrder.TRANTYPE_3)
				{// 3：退票
					form.setStatus(AirticketOrder.STATUS_19); // 订单状态
					form.getTicketLog().setType(TicketLog.TYPE_35);// 操作日志
					updateDrawedOrderPassengerFlightRetire(passengerId, Passenger.STATES_3);
				}
				else if (form.getTranType() == AirticketOrder.TRANTYPE_4)
				{// 4：废票
					form.setStatus(AirticketOrder.STATUS_29); // 订单状态
					form.getTicketLog().setType(TicketLog.TYPE_51);// 操作日志
					updateDrawedOrderPassengerFlightRetire(passengerId, Passenger.STATES_4);
				}
				form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型

				long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(order.getOrderGroup().getId());

				form.setSubGroupMarkNo(newSubGroupNo);// 新的退废组

				forwardPage = saveRetireOrder(form, order, uri);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			forwardPage = "ERROR";
		}
		return forwardPage;
	}
	
	public void updateDrawedOrderPassengerFlightRetire(String[] passengerIds,long retireStatus){
		try{
		if(passengerIds!=null){
			for (int i = 0; i < passengerIds.length; i++) {
				long passengerId=Constant.toLong(passengerIds[i]);
				Passenger passenger=passengerDAO.getPassengerById(passengerId);
				if(passenger!=null&&passenger.getId()>0){
					passenger.setStatus(retireStatus);
					passengerDAO.update(passenger);
				}			
			}
		}	
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 审核退废票(卖出单，创建买入退废单)
	public String auditRetire(AirticketOrder form, UserRightInfo uri)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrder order = airticketOrderDAO.getAirticketOrderById(form.getId());

		AirticketOrder drawOrder = airticketOrderDAO.getDrawedAirticketOrderByGroupId(
		    order.getOrderGroup().getId(), AirticketOrder.TRANTYPE__2);

		form.setDrawPnr(drawOrder.getDrawPnr());
		if (form.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 3：退票
			form.setStatus(AirticketOrder.STATUS_21); // 订单状态
			form.getTicketLog().setType(TicketLog.TYPE_40);// 操作日志
			form.setTranType(AirticketOrder.TRANTYPE_3);
			form.setReturnReason(order.getReturnReason());
			order.setStatus(AirticketOrder.STATUS_20);
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE_4)
		{// 4：废票
			form.setStatus(AirticketOrder.STATUS_31); // 订单状态
			form.getTicketLog().setType(TicketLog.TYPE_52);// 操作日志
			form.setTranType(AirticketOrder.TRANTYPE_4);
			order.setStatus(AirticketOrder.STATUS_30);
		}
		if (form.getTransRule() != null)
		{
			order.setTransRule(form.getTransRule());// 客规百分比
		}

		order.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		airticketOrderDAO.update(order);
		updateOrderGroup(order);

		form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

		// ------------------------
		form.setEntryTime(order.getEntryTime());
		form.setSubGroupMarkNo(order.getSubGroupMarkNo());
		form.setPassengers(order.getPassengers());
		form.setFlights(order.getFlights());
		saveRetireOrder(form, drawOrder, uri);
		// ------------------------

		forwardPage = getForwardPageByOrderType(order);
		return forwardPage;
	}

	// 审核退废票（卖出单，第二次通过申请，更新卖出）
	public String auditRetire2(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		AirticketOrder order = airticketOrderDAO .getAirticketOrderById(form.getId());

		order.setAirOrderNo(form.getAirOrderNo());// 票号
		order.setHandlingCharge(form.getHandlingCharge());// 手续费
		order.setTotalAmount(form.getTotalAmount());

		Long currTicketType = null;
		if (order.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 3：退票
			order.setStatus(AirticketOrder.STATUS_21);
			currTicketType = TicketLog.TYPE_41;// 操作日志 类型
			order.setOperate41(uri.getUser().getUserNo());
			order
			    .setOperate41Time(new Timestamp(System.currentTimeMillis()));
		}
		else if (order.getTranType() == AirticketOrder.TRANTYPE_4)
		{// 4：废票
			order.setStatus(AirticketOrder.STATUS_31);
			currTicketType = TicketLog.TYPE_53;// 操作日志类型
			order.setOperate53(uri.getUser().getUserNo());
			order.setOperate53Time(new Timestamp(System.currentTimeMillis()));
		}
		order.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

		airticketOrderDAO.update(order);
		updateOrderGroup(order);

		saveAirticketTicketLog(order, uri.getUser(), request,currTicketType,"");
		return forwardPage;
	}
	
	// 创建退废票（调用方法）
	private String saveRetireOrder(AirticketOrder form,
	    AirticketOrder order, UserRightInfo uri) throws AppException
	{
		String forwardPage = "";
		// 机票订单
		AirticketOrder newOrder = new AirticketOrder();
		newOrder.setDrawPnr(form.getDrawPnr());// 出票pnr
		newOrder.setSubPnr(order.getSubPnr());// 预订pnr
		if (order.getBigPnr() != null)
		{
			newOrder.setBigPnr(order.getBigPnr());// 大pnr
		}
		if (form.getBigPnr() != null)
		{
			newOrder.setBigPnr(form.getBigPnr());// 大pnr
		}
		newOrder.setReferenceId(order.getId());
		newOrder.setOldOrderNo(order.getOldOrderNo());
		newOrder.setTicketPrice(order.getTicketPrice());// 票面价格
		newOrder.setAirportPrice(order.getAirportPrice());// 机建费
		newOrder.setFuelPrice(order.getFuelPrice());// 燃油税
		newOrder.setAgent(order.getAgent()); // 购票客户
		newOrder.setHandlingCharge(form.getHandlingCharge());// 手续费
		newOrder.setDocumentPrice(order.getDocumentPrice());// 行程单费用
		newOrder.setInsurancePrice(order.getInsurancePrice());// 保险费
		newOrder.setRebate(order.getRebate());// 政策
		newOrder.setAirOrderNo(form.getAirOrderNo());// 机票订单号

		updateOrderGroup(order);// 订单组编号
		newOrder.setOrderGroup(order.getOrderGroup());
		newOrder.setSubGroupMarkNo(form.getSubGroupMarkNo());
		newOrder.setStatus(form.getStatus()); // 订单状态
		newOrder.setTicketType(order.getTicketType());// 机票类型
		newOrder.setTranType(form.getTranType());// 交易类型
		newOrder.setMemo(form.getMemo());
		if (form.getReturnReason() != null)
		{
			newOrder.setReturnReason(form.getReturnReason());// 退票原因
		}

		if (form.getTransRule() != null)
		{
			newOrder.setTransRule(form.getTransRule());// 客规率
		}
		newOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		newOrder.setBusinessType(form.getBusinessType());// 业务类型
		newOrder.setReturnReason(form.getReturnReason());// 退废票原因
		newOrder.setEntryOperator(uri.getUser().getUserNo());

		if (form.getEntryTime() != null)
		{
			newOrder.setEntryTime(form.getEntryTime());
		}
		else
		{
			newOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
		}

		// 设置平台公司帐号
		newOrder.setPlatform(order.getPlatform());
		newOrder.setCompany(order.getCompany());
		newOrder.setAccount(order.getAccount());

		if (form.getTotalAmount() != null)
		{// 创建买入（第一次通过申请）
			newOrder.setTotalAmount(form.getTotalAmount());// 总金额
		}
		else
		{// 创建小组第一条卖出退废单
			newOrder.setTotalAmount(order.getTotalAmount());// 总金额
		}
		newOrder.setOldOrderNo(order.getAirOrderNo());// 原始订单号
		save(newOrder);
		

		if (newOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
		{
			newOrder.setOperate35(uri.getUser().getUserNo());
			newOrder.setOperate35Time(new Timestamp(System.currentTimeMillis()));
			saveStatementByOrder(newOrder, uri.getUser(), Statement.type_2,Statement.SUBTYPE_21, Statement.STATUS_0, newOrder.getEntryTime());
		}else if (newOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
		{
			newOrder.setOperate40(uri.getUser().getUserNo());
			newOrder.setOperate40Time(new Timestamp(System.currentTimeMillis()));
			saveStatementByOrder(newOrder, uri.getUser(), Statement.type_1,Statement.SUBTYPE_11, Statement.STATUS_0, newOrder.getEntryTime());
		}
		update(newOrder);

		if (form.getStatus() == AirticketOrder.STATUS_19
		    || form.getStatus() == AirticketOrder.STATUS_29)
		{// 创建退废
			flightPassengerBiz.saveFlightPassengerByOrderForm(form, newOrder,newOrder.getTranType());
		}
		else if (form.getStatus() == AirticketOrder.STATUS_21
		    || form.getStatus() == AirticketOrder.STATUS_31)
		{
			// 审核退废
			flightPassengerBiz.saveFlightPassengerBySetForOrder(newOrder, form.getPassengers(), form.getFlights());
		}

		// 操作日志
		saveAirticketTicketLog(newOrder, uri.getUser(),null, form.getTicketLog().getType(),"");

		forwardPage = getForwardPageByOrderType(newOrder);
		return forwardPage;
	}

	
	/***************************************************************************
	 * 确认退票/废票/ 收、退款
	 **************************************************************************/
	public String collectionRetire(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";
		if (form != null && form.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
			
			AirticketOrder order = getAirticketOrderById(form.getId());
		
			if(order!=null){
			long businessType = order.getBusinessType();
			Long ticketLogType = null;
			Long orderStatus = null;
		
			order.setTotalAmount(form.getTotalAmount());
			
			Account refundAccount = null;
			Statement statement=null;
			if (businessType == 2 && form.getTranType() == AirticketOrder.TRANTYPE_3)
			{// 买入退票,付款账号收退款
				refundAccount = statementDAO.getStatementAccountByOrderGroupType(order.getOrderGroup().getId(), AirticketOrder.TRANTYPE__2,Statement.SUBTYPE_20, Statement.ORDERTYPE_1);
				//refundAccount=accountDAO.getAccountById(Long.valueOf(107));		
				if (refundAccount != null)
				{
					order.setAccount(refundAccount);
					statement=statementDAO.getStatementByOrderIdAndStatus(order.getId(), Statement.SUBTYPE_11, Statement.STATUS_0);
					if(statement==null||statement.getId()<0){
						saveStatementByOrder(order, uri.getUser(), Statement.type_1,Statement.SUBTYPE_11, Statement.STATUS_1, form.getOptTime());
					}else{
						updateStatementByOrderForm(statement, uri.getUser(), Statement.type_1,Statement.SUBTYPE_11, Statement.STATUS_1, form.getOptTime(),order.getTotalAmount());				
						AirticketOrderStore.addOrderString(order.getOrderGroup().getId());
					}						
				}
				else{return "STATEMENTACCOUNTERROR";}
		
				orderStatus = AirticketOrder.STATUS_22;
				ticketLogType = TicketLog.TYPE_42;
				order.setOperate42(uri.getUser().getUserNo());
				order.setOperate42Time(new Timestamp(System.currentTimeMillis()));
				order.setStatus(orderStatus);
				airticketOrderDAO.update(order);
				updateOrderGroup(order);
			}else if (businessType == 1&& form.getTranType() == AirticketOrder.TRANTYPE_3)
			{// 卖出退票,收款账号付退款
				refundAccount = statementDAO.getStatementAccountByOrderGroupType(order.getOrderGroup().getId(), AirticketOrder.TRANTYPE__1,Statement.SUBTYPE_10, Statement.ORDERTYPE_1);
		
				if (refundAccount != null)
				{
					order.setAccount(refundAccount);
					statement=statementDAO.getStatementByOrderIdAndStatus(order.getId(), Statement.SUBTYPE_21, Statement.STATUS_0);
					if(statement==null||statement.getId()<0){
						saveStatementByOrder(order, uri.getUser(), Statement.type_2,Statement.SUBTYPE_21, Statement.STATUS_1, form.getOptTime());
					}else{
						updateStatementByOrderForm(statement, uri.getUser(), Statement.type_2,Statement.SUBTYPE_21, Statement.STATUS_1, form.getOptTime(),order.getTotalAmount());
						AirticketOrderStore.addOrderString(order.getOrderGroup().getId());
					}	
				}
				else{return "STATEMENTACCOUNTERROR";}
		
				orderStatus = AirticketOrder.STATUS_22;
				ticketLogType = TicketLog.TYPE_43;
				order.setOperate43(uri.getUser().getUserNo());
				order.setOperate43Time(new Timestamp(System.currentTimeMillis()));
				order.setStatus(orderStatus);
				airticketOrderDAO.update(order);
				updateOrderGroup(order);
			}
			else if (businessType == 2 && form.getTranType() == AirticketOrder.TRANTYPE_4)
			{
				// 买入废票,付款账号收退款
				refundAccount = statementDAO.getStatementAccountByOrderGroupType(order.getOrderGroup().getId(), AirticketOrder.TRANTYPE__2,Statement.SUBTYPE_20, Statement.ORDERTYPE_1);
				//refundAccount=accountDAO.getAccountById(Long.valueOf(45));				
				if (refundAccount != null)
				{
					order.setAccount(refundAccount);
					statement=statementDAO.getStatementByOrderIdAndStatus(order.getId(), Statement.SUBTYPE_11, Statement.STATUS_0);
					if(statement==null||statement.getId()<0){
						saveStatementByOrder(order, uri.getUser(), Statement.type_1,Statement.SUBTYPE_11, Statement.STATUS_1, form.getOptTime());
					}else{
						updateStatementByOrderForm(statement, uri.getUser(), Statement.type_1,Statement.SUBTYPE_11, Statement.STATUS_1, form.getOptTime(),order.getTotalAmount());
						AirticketOrderStore.addOrderString(order.getOrderGroup().getId());
					}
				}
				else{return "STATEMENTACCOUNTERROR";}		
				orderStatus = AirticketOrder.STATUS_32;
				ticketLogType = TicketLog.TYPE_54;
				order.setOperate54(uri.getUser().getUserNo());
				order.setOperate54Time(new Timestamp(System.currentTimeMillis()));
				order.setStatus(orderStatus);
		
				airticketOrderDAO.update(order);
				updateOrderGroup(order);
			}
			else if (businessType == 1&& form.getTranType() == AirticketOrder.TRANTYPE_4)
			{
				// 卖出废票,收款帐号付退款
				refundAccount = statementDAO.getStatementAccountByOrderGroupType(order.getOrderGroup().getId(), AirticketOrder.TRANTYPE__1,Statement.SUBTYPE_10, Statement.ORDERTYPE_1);
				//refundAccount=accountDAO.getAccountById(Long.valueOf(96));	
				if (refundAccount != null)
				{
					order.setAccount(refundAccount);
					statement=statementDAO.getStatementByOrderIdAndStatus(order.getId(), Statement.SUBTYPE_21, Statement.STATUS_0);
					if(statement==null||statement.getId()<0){
						saveStatementByOrder(order, uri.getUser(), Statement.type_2,Statement.SUBTYPE_21, Statement.STATUS_1, form.getOptTime());
					}else{
						updateStatementByOrderForm(statement, uri.getUser(),Statement.type_2,Statement.SUBTYPE_21, Statement.STATUS_1, form.getOptTime(),order.getTotalAmount());
						AirticketOrderStore.addOrderString(order.getOrderGroup().getId());
					}
				}
				else{return "STATEMENTACCOUNTERROR";}
				orderStatus = AirticketOrder.STATUS_32;
				ticketLogType = TicketLog.TYPE_55;
				order.setOperate55(uri.getUser().getUserNo());
				order.setOperate55Time(new Timestamp(System.currentTimeMillis()));
				order.setStatus(orderStatus);
				airticketOrderDAO.update(order);
				updateOrderGroup(order);
			}
			saveAirticketTicketLog(order, uri.getUser(), request, ticketLogType,"");
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE3 + "";
			}else{
				return "ERROR";
			}						
		}
		else
		{
			return "NOORDER";
		}
		return forwardPage;
	}
	
	private void updateStatementByOrderForm(Statement statement,SysUser sysUser,long statementType,long orderSubType,long status,Timestamp statementDate,BigDecimal totalAmount)throws AppException{
		statement.setTotalAmount(totalAmount);
		statement.setSysUser(sysUser);
		statement.setType(statementType);
		statement.setOrderSubtype(orderSubType);
		statement.setStatus(status);
		statement.setStatementDate(statementDate);
		statementDAO.update(statement);
	}
	
	// 创建改签票
	public String createUmbuchenOrder(AirticketOrder form,
	    AirticketOrder order, UserRightInfo uri) throws AppException
	{
		String forwardPage = "";
		// 机票订单
		AirticketOrder newOrder = new AirticketOrder();
		newOrder.setDrawPnr(form.getDrawPnr());// 出票pnr
		newOrder.setSubPnr(order.getSubPnr());// 预订pnr
		newOrder.setBigPnr(form.getBigPnr());// 大pnr
		newOrder.setUmbuchenPnr(form.getUmbuchenPnr());// 改签pnr
		newOrder.setTicketPrice(order.getTicketPrice());// 票面价格
		newOrder.setAirportPrice(order.getAirportPrice());// 机建费
		newOrder.setFuelPrice(order.getFuelPrice());// 燃油税
		newOrder.setAgent(order.getAgent()); // 购票客户
		newOrder.setDocumentPrice(order.getDocumentPrice());// 行程单费用
		newOrder.setInsurancePrice(order.getInsurancePrice());// 保险费
		newOrder.setRebate(order.getRebate());// 政策
		newOrder.setAirOrderNo(form.getAirOrderNo());// 机票订单号

		updateOrderGroup(order);// 订单组编号
		newOrder.setOrderGroup(order.getOrderGroup());
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(order
		    .getOrderGroup().getId());
		newOrder.setSubGroupMarkNo(newSubGroupNo);
		newOrder.setStatus(form.getStatus()); // 订单状态
		newOrder.setTicketType(order.getTicketType());// 机票类型
		newOrder.setTranType(AirticketOrder.TRANTYPE_5);// 交易类型
		newOrder.setMemo(form.getMemo());
		newOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		newOrder.setBusinessType(form.getBusinessType());// 业务类型
		newOrder.setEntryOperator(uri.getUser().getUserNo());
		newOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		Long platformId = form.getPlatformId();
		Long companyId = form.getCompanyId();
		Long accountId = form.getAccountId();
		newOrder.setPlatform(platformDAO.getPlatformById(platformId));
		newOrder.setCompany(companyDAO.getCompanyById(companyId));
		newOrder.setAccount(accountDAO.getAccountById(accountId));
		if (newOrder.getPlatform() == null || newOrder.getCompany() == null
		    || newOrder.getAccount() == null) { return "ACCOUNTERROR"; }

		newOrder.setTotalAmount(order.getTotalAmount());// 总金额
		airticketOrderDAO.save(newOrder);

		if (form.getStatus() == AirticketOrder.STATUS_39)
		{
			flightPassengerBiz.saveFlightPassengerByOrderForm(form, newOrder,newOrder.getTranType());
		}
		else if (form.getStatus() == AirticketOrder.STATUS_41)
		{
			flightPassengerBiz.saveFlightPassengerByOrder(order, newOrder);
		}
		// 操作日志
		saveAirticketTicketLog(newOrder, uri.getUser(),null,TicketLog.TYPE_71,"");
		forwardPage = getForwardPageByOrderType(order);
		return forwardPage;
	}

	
	// 确认收/付款，改签完成
	public String finishUmbuchenOrder(AirticketOrder airticketOrderForm,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (airticketOrderForm != null && airticketOrderForm.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderForm.getId());

			String groupMarkNo = airticketOrder.getOrderGroup().getNo();
			long businessType = airticketOrder.getBusinessType();

			airticketOrder.setStatus(AirticketOrder.STATUS_45);
			airticketOrderDAO.update(airticketOrder);
			updateOrderGroup(airticketOrder);

			if (businessType == 1)
			{// 卖出 付款
				saveStatementByOrder(airticketOrder, uri.getUser(), Statement.type_2,
				    Statement.SUBTYPE_10, Statement.STATUS_1, airticketOrder
				        .getOptTime());
			}
			else if (businessType == 2)
			{// 买入 收款
				saveStatementByOrder(airticketOrder, uri.getUser(), Statement.type_1,
				    Statement.SUBTYPE_20, Statement.STATUS_1, airticketOrder
				        .getOptTime());
			}
			else
			{
				return "ERROR";
			}
			saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
			    TicketLog.TYPE_76,"");

			forwardPage = getForwardPageByOrderType(airticketOrder);
			return forwardPage;
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 手动添加 订单
	public String addOrderByHand(HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		String totalAmount = request.getParameter("totalAmount");
		Long platformId = com.neza.base.Constant.toLong(request
		    .getParameter("platformId"));
		Long companyId = com.neza.base.Constant.toLong(request
		    .getParameter("companyId"));
		Long accountId = com.neza.base.Constant.toLong(request
		    .getParameter("accountId"));

		String drawPnr = request.getParameter("drawPnr");
		String subPnr = request.getParameter("subPnr");
		String bigPnr = request.getParameter("bigPnr");
		String airportPrice = request.getParameter("airportPrice");
		String fuelPrice = request.getParameter("fuelPrice");
		String ticketPrice = request.getParameter("ticketPrice");
		String handlingCharge = request.getParameter("handlingCharge");
		String rebate = request.getParameter("rebate");
		String tranType = request.getParameter("tranType");
		String airOrderNo = request.getParameter("airOrderNo");
		String ticketType = request.getParameter("ticketType");
		String returnReason = request.getParameter("returnReason");
		String transRule = request.getParameter("transRule");

		String entryOrderDate = request.getParameter("entryOrderDate");
		Timestamp entryTime = null;
		if (entryOrderDate != null && "".equals(entryOrderDate) == false)
		{
			entryTime = DateUtil.getTimestamp(entryOrderDate, "yyyy-MM-dd HH:mm:ss");
		}
		else
		{
			entryTime = new Timestamp(System.currentTimeMillis());
		}

		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setDrawPnr(drawPnr);// 出票pnr
		ao.setSubPnr(subPnr);// 预订pnr
		ao.setBigPnr(bigPnr);// 大pnr
		ao.setTicketPrice(new java.math.BigDecimal(ticketPrice));// 票面价格
		ao.setAirportPrice(new java.math.BigDecimal(airportPrice));// 机建费
		ao.setFuelPrice(new java.math.BigDecimal(fuelPrice));// 燃油税
		ao.setAgent(null); // 购票客户
		if (handlingCharge == null || "".equals(handlingCharge))
		{
			handlingCharge = "0";
		}
		ao.setHandlingCharge(new java.math.BigDecimal(handlingCharge));// 手续费
		ao.setDocumentPrice(new java.math.BigDecimal(0));// 行程单费用
		ao.setInsurancePrice(new java.math.BigDecimal(0));// 保险费
		ao.setRebate(new java.math.BigDecimal(rebate));// 政策
		ao.setAirOrderNo(airOrderNo);// 机票订单号
		ao.setTicketType(Long.valueOf(ticketType));// 机票类型
		ao.setOptTime(entryTime);// 操作时间
		ao.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(entryTime);// 录入订单时间

		ao.setTranType(Long.valueOf(tranType));// 交易类型
		long orderStatus = new Long(0);
		long ticketLogType = new Long(0);
		if (ao.getTranType() == AirticketOrder.TRANTYPE__1)
		{
			orderStatus = AirticketOrder.STATUS_1;
			ticketLogType = TicketLog.TYPE_1;
			ao.setOperate1(uri.getUser().getUserNo());
			ao.setOperate1Time(entryTime);
		}
		else if (ao.getTranType() == AirticketOrder.TRANTYPE_3)
		{
			orderStatus = AirticketOrder.STATUS_24;
			ticketLogType = TicketLog.TYPE_35;
			ao.setOperate35(uri.getUser().getUserNo());
			ao.setOperate35Time(entryTime);
		}
		else if (ao.getTranType() == AirticketOrder.TRANTYPE_4)
		{
			orderStatus = AirticketOrder.STATUS_34;
			ticketLogType = TicketLog.TYPE_51;
			ao.setOperate51(uri.getUser().getUserNo());
			ao.setOperate51Time(entryTime);
		}
		else if (ao.getTranType() == AirticketOrder.TRANTYPE_5)
		{
			orderStatus = AirticketOrder.STATUS_46;
			ticketLogType = TicketLog.TYPE_71;
			// ao.setOperate71(uri.getUser().getUserNo());
			// ao.setOperate71Time(entryTime);
		}
		ao.setStatus(Long.valueOf(orderStatus)); // 订单状态

		ao.setReturnReason(returnReason);
		if (transRule != null)
		{
			ao.setTransRule(UnitConverter.getBigDecimalByString(transRule));
		}

		// 设置平台帐号
		ao.setPlatform(platformDAO.getPlatformById(platformId));
		ao.setCompany(companyDAO.getCompanyById(companyId));
		ao.setAccount(accountDAO.getAccountById(accountId));
		System.out.println("accountId=" + accountId);
		if (ao.getPlatform() == null || ao.getCompany() == null
		    || ao.getAccount() == null) { return "ACCOUNTERROR"; }

		if (totalAmount == null || "".equals(totalAmount))
		{
			ao.setTotalAmount(BigDecimal.ZERO);
		}
		ao.setTotalAmount(new BigDecimal(totalAmount));
		// 创建一个新组
		OrderGroup og = this.saveOrderGroup(ao);
		ao.setOrderGroup(og);
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(og.getId());
		ao.setSubGroupMarkNo(newSubGroupNo);
		airticketOrderDAO.save(ao);

		flightPassengerBiz.saveFlightPassengerByRequest(request, ao);

		// 结算记录
		saveStatementByOrder(ao, uri.getUser(), Statement.type_1,
		    Statement.SUBTYPE_10, Statement.STATUS_1, entryTime);

		// 操作日志
		saveAirticketTicketLog(ao, uri.getUser(), request, ticketLogType,"");

		forwardPage = getForwardPageByOrderType(ao);
		return forwardPage;

	}

	// 跳转到编辑页面
	public void editOrder(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException
	{
		if (ulf == null)
		{
			ulf = new AirticketOrderListForm();
		}
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		Long id = ulf.getId();
		if (id != null && id > 0)
		{
			AirticketOrder order = getAirticketOrderById(id);
			request.setAttribute("airticketOrder", order);
			
			List<AirticketOrder> orderList = airticketOrderDAO.listBySubGroupAndGroupId(order
			    .getOrderGroup().getId(), order.getSubGroupMarkNo());
			
			ulf.setList(orderList);
			request.setAttribute("airticketOrderList", ulf);
			
			String ordersString = "";
			for (int i = 0; i < orderList.size(); i++)
			{
				AirticketOrder tempOrder = orderList.get(i);
				ordersString += tempOrder.getId() + ",";
			}
			if (ordersString.length() > 1)
			{
				ordersString = ordersString.substring(0, ordersString.length() - 1);
			}
			
			List<Statement> statementList = statementDAO.getStatementListByOrders(ordersString,
				    Statement.ORDERTYPE_1);
			request.setAttribute("statementList", statementList);
		}
		else
		{
			ulf.setList(new ArrayList());
		}

		
	}

	// 跳转到关联订单页面
	public void processing(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException
	{
		String path = request.getContextPath();
		ulf.setPerPageNum(100);
		if (ulf == null)
		{
			ulf = new AirticketOrderListForm();
		}
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		try
		{
			Long airticketOrderId = ulf.getId();
			if (airticketOrderId != null && airticketOrderId > 0)
			{
				AirticketOrder order = getAirticketOrderById(airticketOrderId);
				List<AirticketOrder> orderList = listByGroupId(order.getOrderGroup()
				    .getId());

				for (AirticketOrder ao : orderList)
				{
					ao.setUri(uri);
					ao.setPath(path);
				}

				List groupList = AirticketGroup.getGroupList(orderList);
				ulf.setList(groupList);
			}
			else
			{
				ulf.setList(new ArrayList());
			}
		}
		catch (Exception ex)
		{
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
	}

	// 编辑 订单
	public String editOrder(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		String forwardPage = "";

		if (form != null)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			String[] airticketOrderIds = request
			    .getParameterValues("airticketOrderIds");
//			String[] totalAmount = request.getParameterValues("totalAmount");
			String[] platformId = request.getParameterValues("platformId");
			String[] companyId = request.getParameterValues("companyId");
//			String[] accountId = request.getParameterValues("accountId");
			String[] entryOrderDate = request.getParameterValues("entryOrderDate");
			String[] drawPnr = request.getParameterValues("drawPnr");
			String[] subPnr = request.getParameterValues("subPnr");
			String[] bigPnr = request.getParameterValues("bigPnr");
			String[] airportPrice = request.getParameterValues("airportPrice");
			String[] fuelPrice = request.getParameterValues("fuelPrice");
			String[] ticketPrice = request.getParameterValues("ticketPrice");
			String[] handlingCharge = request.getParameterValues("handlingCharge");
			String[] rebate = request.getParameterValues("rebate");
			String[] airOrderNos = request.getParameterValues("airOrderNos");
			String[] returnReasons = request.getParameterValues("returnReason");
			String[] transRules=request.getParameterValues("transRule");
			
			if (airticketOrderIds != null)
			{
				List<AirticketOrder> tempOrderList = new ArrayList<AirticketOrder>();
				for (int i = 0; i < airticketOrderIds.length; i++)
				{
					Long id = Constant.toLong(airticketOrderIds[i]);
					if (id > 0)
					{
//						System.out.println("airticket order id:" + id);
						AirticketOrder order = airticketOrderDAO.getAirticketOrderById(id);
						tempOrderList.add(order);

						// 机票订单
						order.setDrawPnr(drawPnr[i]);// 出票pnr
						order.setSubPnr(subPnr[i]);// 预订pnr
						order.setBigPnr(bigPnr[i]);// 大pnr
						order.setTicketPrice(new BigDecimal(ticketPrice[i]));// 票面价格
						order.setAirportPrice(new BigDecimal(airportPrice[i]));// 机建费
						order.setFuelPrice(new BigDecimal(fuelPrice[i]));// 燃油税

						if (handlingCharge != null)
						{
							if (handlingCharge.length <= i && handlingCharge[i] != null
							    || "".equals(handlingCharge[i]) == false)
							{
								order.setHandlingCharge(new BigDecimal(handlingCharge[i]));// 手续费
							}
						}

						// order.setDocumentPrice(new BigDecimal(0));// 行程单费用
						// order.setInsurancePrice(new BigDecimal(0));// 保险费
						if (returnReasons != null && returnReasons[i] != null)
						{
							order.setReturnReason(returnReasons[i]);
						}
						if (transRules != null && transRules[i] != null)
						{
							order.setTransRule(Constant.toBigDecimal(transRules[i]));
						}

						order.setRebate(new BigDecimal(rebate[i]));// 政策
						order.setAirOrderNo(airOrderNos[i]);// 机票订单号
						order.setMemo(form.getMemo());
						if (entryOrderDate != null && entryOrderDate[i] == null
						    || "".equals(entryOrderDate))
						{
							order.setEntryTime(new Timestamp(System.currentTimeMillis()));
						}
						else
						{
							order.setEntryTime(DateUtil.getTimestamp(entryOrderDate[i],
							    "yyyy-MM-dd HH:mm:ss"));
						}

						// 设置平台帐号
						order.setPlatform(platformDAO.getPlatformById(com.neza.base.Constant
						    .toLong(platformId[i])));
						order.setCompany(companyDAO.getCompanyById(com.neza.base.Constant
						    .toLong(companyId[i])));
//						order.setAccount(accountDAO.getAccountById(com.neza.base.Constant
//						    .toLong(accountId[i])));

						if (order.getPlatform() == null || order.getCompany() == null
						    /*|| order.getAccount() == null*/) { return "ACCOUNTERROR"; }

//						order.setTotalAmount(new BigDecimal(totalAmount[i]));

						updateOrderGroup(order);

						update(order);

						// 操作日志
						saveAirticketTicketLog(order, uri.getUser(), request,
						    TicketLog.TYPE_202,"");
						forwardPage = order.getId() + "";
					}
					else
					{
						forwardPage = "NOORDER";
					}
				}

				flightPassengerBiz.updateSynFlightPassengerByRequest(request,
				    tempOrderList);

			}
			else
			{
				forwardPage = "NOORDER";
			}
		}
		else
		{
			forwardPage = "NOORDER";
		}

		return forwardPage;
	}

	// 申请支付
	public void applyTeamPayment(Long airticketOrderId, HttpServletRequest request)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		AirticketOrder order = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderId);

		List<AirticketOrder> orderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(order.getOrderGroup().getId(), order
		        .getSubGroupMarkNo());

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = orderList.get(i);
			if (tempOrder.getStatus() == AirticketOrder.STATUS_102)
			{
				tempOrder.setStatus(AirticketOrder.STATUS_103);// 等待确认支付
				tempOrder.setOperate102(uri.getUser().getUserNo());
				tempOrder.setOperate102Time(new Timestamp(System.currentTimeMillis()));
				tempOrder.setLocked(AirticketOrder.LOCK1);
				airticketOrderDAO.update(tempOrder);
			}
		}
		// 操作日志
		saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_102,"");
	}

	// 团队确认支付（支付即出票）
	public String confirmTeamPayment(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		long id = form.getId();
		AirticketOrder order = airticketOrderDAO.getAirticketOrderById(id);

		PlatComAccount pca = platComAccountDAO.getPlatComAccountById(Constant
		    .toLong(form.getPlatComAccountId()));
		Platform platform = null;
		Company company = null;
		Account account = null;
		if (pca != null)
		{
			platform = pca.getPlatform();
			company = pca.getCompany();
			account = pca.getAccount();
			if (platform == null || company == null || account == null) { return "ACCOUNTERROR"; }
		}
		else
		{
			return "ACCOUNTERROR";
		}

		List<AirticketOrder> orderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(order.getOrderGroup().getId(), order
		        .getSubGroupMarkNo());

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = orderList.get(i);
			tempOrder.setStatus(AirticketOrder.STATUS_105);// 支付成功，完成出票

			tempOrder.setPayOperator(uri.getUser().getUserNo());
			if (form.getOptTime() != null && "".equals(form.getOptTime()) == false)
			{
				tempOrder.setPayTime(form.getOptTime());// 付款时间
				tempOrder.setDrawTime(form.getOptTime());// 出票时间
			}
			else
			{
				tempOrder.setPayTime(new Timestamp(System.currentTimeMillis()));// 付款时间
				tempOrder.setDrawTime(new Timestamp(System.currentTimeMillis()));// 出票时间
			}

			if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
			{
				// 确认支付 备注平台、公司、帐号 只保存到买入订单
				tempOrder.setPlatform(platform);
				tempOrder.setCompany(company);
				tempOrder.setAccount(account);

				if (tempOrder.getMemo() != null)
				{
					form.setMemo(tempOrder.getMemo() + "--" + form.getMemo());
				}
				tempOrder.setMemo(form.getMemo());
				tempOrder.setOutMemo(form.getMemo());
			}

			tempOrder.setOperate103(uri.getUser().getUserNo());
			tempOrder.setOperate103Time(new Timestamp(System.currentTimeMillis()));
			tempOrder.setOperate104(uri.getUser().getUserNo());
			tempOrder.setOperate104Time(new Timestamp(System.currentTimeMillis()));

			tempOrder.setLocked(AirticketOrder.LOCK1);// 销售订单支付后即锁定

			airticketOrderDAO.update(tempOrder);
		}

		order.setAccount(account);
		order.setTotalAmount(form.getTotalAmount());
		Timestamp statementTime = null;
		if (form.getOptTime() != null && "".equals(form.getOptTime()) == false)
		{
			statementTime = form.getOptTime();
		}
		else
		{
			statementTime = new Timestamp(System.currentTimeMillis());
		}

		saveStatementByOrder(order, uri.getUser(), Statement.type_2,
		    Statement.SUBTYPE_20, Statement.STATUS_1, statementTime);

		saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_103,"");
		saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_104,"");

		return "SUCCESS";
	}

	// 团队解锁
	public void unlockTeam(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		long airticketOrderId = form.getId();
		AirticketOrder airticketOrder = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderId);

		List<AirticketOrder> orderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(airticketOrder.getOrderGroup().getId(),
		        airticketOrder.getSubGroupMarkNo());

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = orderList.get(i);

			tempOrder.setOperate117(uri.getUser().getUserNo());
			tempOrder.setOperate117Time(new Timestamp(System.currentTimeMillis()));

			tempOrder.setLocked(AirticketOrder.LOCK0);
			airticketOrderDAO.update(airticketOrder);
		}
		saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
		    TicketLog.TYPE_117,"");
	}

	// 团队解锁
	public void lockTeam(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		long airticketOrderId = form.getId();
		AirticketOrder airticketOrder = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderId);

		List<AirticketOrder> orderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(airticketOrder.getOrderGroup().getId(),
		        airticketOrder.getSubGroupMarkNo());

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = orderList.get(i);

			tempOrder.setOperate117(uri.getUser().getUserNo());
			tempOrder.setOperate117Time(new Timestamp(System.currentTimeMillis()));

			tempOrder.setLocked(AirticketOrder.LOCK1);
			airticketOrderDAO.update(airticketOrder);
		}
		saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
		    TicketLog.TYPE_117,"");
	}

	// 申请退票

	public void applyTeamRefund(Long airticketOrderId, HttpServletRequest request)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		AirticketOrder order = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderId);

		List<AirticketOrder> orderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(order.getOrderGroup().getId(), order
		        .getSubGroupMarkNo());

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = orderList.get(i);
			if (tempOrder.getStatus() == AirticketOrder.STATUS_116)
			{
				tempOrder.setStatus(AirticketOrder.STATUS_117);// 已经申请，等待审核
				tempOrder.setOperate124(uri.getUser().getUserNo());
				tempOrder.setOperate124Time(new Timestamp(System.currentTimeMillis()));
				airticketOrderDAO.update(tempOrder);
			}
		}
		// 操作日志
		saveAirticketTicketLog(order, uri.getUser(), request, TicketLog.TYPE_124,"");
	}

	// 退票审核
	public void checkTeamRefund(Long airticketOrderId, HttpServletRequest request)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		TicketLog ticketLog = new TicketLog();
		AirticketOrder order = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderId);

		List<AirticketOrder> orderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(order.getOrderGroup().getId(), order
		        .getSubGroupMarkNo());

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = orderList.get(i);
			if (tempOrder.getStatus() == AirticketOrder.STATUS_117)
			{
				tempOrder.setStatus(AirticketOrder.STATUS_108);// 退票审核通过，等待退款
				tempOrder.setOperate125(uri.getUser().getUserNo());
				tempOrder.setOperate125Time(new Timestamp(System.currentTimeMillis()));
				airticketOrderDAO.update(tempOrder);

				if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
				{
					Statement s = new Statement();
					s.setStatementNo(noUtil.getStatementNo());
					s.setToAccount(tempOrder.getAccount());
					s.setTotalAmount(tempOrder.getTotalAmount());
					s.setOrderId(tempOrder.getId());
					s.setOrderSubtype(Statement.SUBTYPE_11);
					s.setOrderType(Statement.ORDERTYPE_1);
					s.setSysUser(uri.getUser());
					s.setStatementDate(null);
					s.setStatus(Statement.STATUS_0);
					s.setType(Statement.type_1);
					statementDAO.save(s);
					ticketLog.setOrderId(s.getOrderId());
					ticketLog.setOrderNo(s.getStatementNo());
					ticketLog.setContent("同意退票，创建" + s.toLogString());
					ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
					ticketLog.setSysUser(uri.getUser());// 操作员
					ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
					ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
					ticketLog.setType(TicketLog.TYPE_125);
					ticketLog.setStatus(new Long(1));
					ticketLogDAO.save(ticketLog);
				}
			}
		}

	}

	// 团队退票，确认付退款
	public void confirmTeamRefundPayment(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		AirticketOrder airticketOrder = airticketOrderDAO
		    .getAirticketOrderById(form.getId());

		airticketOrder.setIncomeretreatCharge(form.getIncomeretreatCharge());// 收退票手续费
		airticketOrder.setStatus(AirticketOrder.STATUS_109);// 状态：已经退款，交易结束

		airticketOrder.setPayOperator(uri.getUser().getUserNo());

		if (form.getOptTime() != null && "".equals(form.getOptTime()) == false)
		{
			airticketOrder.setOptTime(form.getOptTime());// 收付款时间
		}
		else
		{
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));
		}
		airticketOrder.setMemo(form.getMemo());// 备注

		airticketOrder.setAccount(accountDAO.getAccountById(form.getAccountId()));
		airticketOrder.setTotalAmount(airticketOrder.getIncomeretreatCharge());
		airticketOrder.setOperate127(uri.getUser().getUserNo());
		airticketOrder.setOperate127Time(new Timestamp(System.currentTimeMillis()));
		airticketOrderDAO.update(airticketOrder);
		saveStatementByOrder(airticketOrder, uri.getUser(), Statement.type_2,
		    Statement.SUBTYPE_21, Statement.STATUS_1, new Timestamp(System
		        .currentTimeMillis()));

		saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
		    TicketLog.TYPE_127,"");
	}

	/**
	 * 团队订单--跳转到编辑页面
	 */
	public void editTeamOrder(long airticketOrderId, HttpServletRequest request)
	    throws AppException
	{
		if (airticketOrderId > 0)
		{
			AirticketOrder airticketOrder = getAirticketOrderById(airticketOrderId);

			request = loadFlightAgentByAirticketOrder(airticketOrder, request);

			String queryTranType = "";
			if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE__1
			    || airticketOrder.getTranType() == AirticketOrder.TRANTYPE__2)
			{
				queryTranType = AirticketOrder.TRANTYPE__1 + ","
				    + AirticketOrder.TRANTYPE__2;
			}
			else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3)
			{
				queryTranType = AirticketOrder.TRANTYPE_3 + "";
			}

			List<AirticketOrder> airticketOrderList = airticketOrderDAO
			    .listBySubGroupAndGroupId(airticketOrder.getOrderGroup().getId(),
			        airticketOrder.getSubGroupMarkNo());

			AirticketOrder buyerOrder = new AirticketOrder();
			String ordersString = "";
			if (airticketOrderList.size() > 1)
			{
				for (int i = 0; i < airticketOrderList.size(); i++)
				{
					AirticketOrder tempOrder = airticketOrderList.get(i);

					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__1)
					{// 卖出
						airticketOrder = tempOrder;
						ordersString += tempOrder.getId() + ",";
					}
					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__2)
					{// 买入
						buyerOrder = tempOrder;
						ordersString += tempOrder.getId() + ",";
					}
					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE_3)
					{// 退票
						if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
						{
							airticketOrder = tempOrder;
						}
						else if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
						{
							buyerOrder = tempOrder;
						}
						ordersString += tempOrder.getId() + ",";
					}
				}
			}

			if (ordersString.length() > 1)
			{
				ordersString = ordersString.substring(0, ordersString.length() - 1);
			}
			List<Statement> statementList = statementDAO.getStatementListByOrders(
			    ordersString, Statement.ORDERTYPE_1);

			List<TicketLog> ticketLogList = ticketLogDAO
			    .getTicketLogByOrderIds(ordersString);
			request.setAttribute("buyerOrder", buyerOrder);
			request.setAttribute("airticketOrder", airticketOrder);
			request.setAttribute("statementList", statementList);
			request.setAttribute("ticketLogList", ticketLogList);

			request.setAttribute("airticketOrderSize", airticketOrderList.size());
			request.setAttribute("teamProfit", new TeamProfit(airticketOrder,
			    buyerOrder));
		}
	}

	// 保存团队订单信息
	public long updateTeamAirticketOrder(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		AirticketOrder airticketOrder = new AirticketOrder();
		AirticketOrder tempOrder = new AirticketOrder();
		if (form.getId() > 0)
		{
			airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());

			if (airticketOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
			{
				// 更新卖出单
				if (form.getTranType() == AirticketOrder.TRANTYPE__1
				    || form.getTranType() == AirticketOrder.TRANTYPE__2)
				{
					form.setTranType(AirticketOrder.TRANTYPE__1);
				}
				else if (form.getTranType() == AirticketOrder.TRANTYPE_3)
				{
					form.setTranType(AirticketOrder.TRANTYPE_3);
				}
				form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
				airticketOrder = fillAirticketOrderByAirticketOrderForm(form,
				    airticketOrder);
				Agent agent = agentDAO.getAgentByid(form.getAgentId());
				airticketOrder.setAgent(agent);
				updateOrderGroup(airticketOrder);
				airticketOrderDAO.update(airticketOrder);
				airticketOrder.setOperate203(uri.getUser().getUserNo());
				airticketOrder.setOperate203Time(new Timestamp(System
				    .currentTimeMillis()));
				saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
				    TicketLog.TYPE_203,"");

				// 找到买入单
				List<AirticketOrder> airticketOrderList = airticketOrderDAO
				    .listBySubGroupByGroupIdAndType(airticketOrder.getOrderGroup()
				        .getId(), airticketOrder.getSubGroupMarkNo(),
				        AirticketOrder.BUSINESSTYPE__2);

				if (airticketOrderList.size() > 0)
				{
					tempOrder = airticketOrderList.get(0);

					if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
					{
						if (form.getTranType() == AirticketOrder.TRANTYPE__1
						    || form.getTranType() == AirticketOrder.TRANTYPE__2)
						{
							form.setTranType(AirticketOrder.TRANTYPE__2);
						}
						else if (form.getTranType() == AirticketOrder.TRANTYPE_3)
						{
							form.setTranType(AirticketOrder.TRANTYPE_3);
						}
						form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
						tempOrder = fillAirticketOrderByAirticketOrderForm(form, tempOrder);
						tempOrder.setTotalAmount(form.getTotalAmount());
						tempOrder.setTeamaddPrice(form.getTeamaddPrice());// 团队加价
						tempOrder.setAgentaddPrice(form.getAgentaddPrice());// 客户加价
						tempOrder.setAgent(null);
						updateOrderGroup(tempOrder);
						airticketOrderDAO.update(tempOrder);
						tempOrder.setOperate203(uri.getUser().getUserNo());
						tempOrder.setOperate203Time(new Timestamp(System
						    .currentTimeMillis()));
						saveAirticketTicketLog(tempOrder, uri.getUser(), request,
						    TicketLog.TYPE_203,"");
					}
				}
				else
				{
					// System.out.println("应该至少有两张单，除非是废票单");
				}
			}

			if (airticketOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
			{
				// 更新买入单
				if (form.getTranType() == AirticketOrder.TRANTYPE__1
				    || form.getTranType() == AirticketOrder.TRANTYPE__2)
				{
					form.setTranType(AirticketOrder.TRANTYPE__2);
				}
				else if (form.getTranType() == AirticketOrder.TRANTYPE_3)
				{
					form.setTranType(AirticketOrder.TRANTYPE_3);
				}
				form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
				airticketOrder = fillAirticketOrderByAirticketOrderForm(form,
				    airticketOrder);

				// 更新操作时间
				airticketOrder.setAgent(null);
				updateOrderGroup(airticketOrder);
				airticketOrderDAO.update(airticketOrder);
				airticketOrder.setOperate203(uri.getUser().getUserNo());
				airticketOrder.setOperate203Time(new Timestamp(System
				    .currentTimeMillis()));
				saveAirticketTicketLog(airticketOrder, uri.getUser(), request,
				    TicketLog.TYPE_203,"");

				// 找到卖出单
				List<AirticketOrder> airticketOrderList = airticketOrderDAO
				    .listBySubGroupByGroupIdAndType(airticketOrder.getOrderGroup()
				        .getId(), airticketOrder.getSubGroupMarkNo(),
				        AirticketOrder.BUSINESSTYPE__1);

				if (airticketOrderList.size() > 0)
				{
					tempOrder = airticketOrderList.get(0);

					if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
					{
						if (form.getTranType() == AirticketOrder.TRANTYPE__1
						    || form.getTranType() == AirticketOrder.TRANTYPE__2)
						{
							form.setTranType(AirticketOrder.TRANTYPE__1);
						}
						else if (form.getTranType() == AirticketOrder.TRANTYPE_3)
						{
							form.setTranType(AirticketOrder.TRANTYPE_3);
						}
						form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
						tempOrder = fillAirticketOrderByAirticketOrderForm(form, tempOrder);
						tempOrder.setAgent(agentDAO.getAgentByid(form.getAgentId()));
						updateOrderGroup(tempOrder);

						tempOrder.setOperate203(uri.getUser().getUserNo());
						tempOrder.setOperate203Time(new Timestamp(System
						    .currentTimeMillis()));
						airticketOrderDAO.update(tempOrder);
						saveAirticketTicketLog(tempOrder, uri.getUser(), request,
						    TicketLog.TYPE_203,"");
					}
				}
			}
			else
			{
				// System.out.println("应该至少有两张单，除非是废票单");
			}
		}
		else
		{// 新建买入卖出单
			Long ticketTypeFlag = new Long(0);
			airticketOrder = new AirticketOrder();
			if (form.getTranType() == AirticketOrder.TRANTYPE__1
			    || form.getTranType() == AirticketOrder.TRANTYPE__2)
			{
				form.setTranType(AirticketOrder.TRANTYPE__1);
				ticketTypeFlag = new Long(1);
			}
			else if (form.getTranType() == AirticketOrder.TRANTYPE_3)
			{
				form.setTranType(AirticketOrder.TRANTYPE_3);
				ticketTypeFlag = new Long(3);
			}

			form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
			airticketOrder = fillAirticketOrderByAirticketOrderForm(form,
			    airticketOrder);
			airticketOrder.setTicketType(AirticketOrder.TICKETTYPE_2);
			OrderGroup og = this.saveOrderGroup(airticketOrder);
			airticketOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
			airticketOrder.setEntryOperator(uri.getUser().getUserNo());

			if (form.getTranType() == AirticketOrder.TRANTYPE__1)
			{
				airticketOrder.setStatus(AirticketOrder.STATUS_101);
			}
			if (form.getTranType() == AirticketOrder.TRANTYPE_3)
			{
				airticketOrder.setStatus(AirticketOrder.STATUS_107);
			}

			airticketOrder.setTotalAmount(new BigDecimal("0"));
			airticketOrder.setAgent(agentDAO.getAgentByid(form.getAgentId()));
			airticketOrder.setOrderGroup(og);
			System.out.println("order--" + airticketOrder.getTranType() + "--status:"
			    + airticketOrder.getStatus());
			// 创建卖出单
			updateOrderGroup(airticketOrder);
			airticketOrder.setLocked(AirticketOrder.LOCK0);
			airticketOrderDAO.update(airticketOrder);

			if (ticketTypeFlag.longValue() == 1)
			{
				airticketOrder.setOperate100(uri.getUser().getUserNo());
				airticketOrder.setOperate100Time(new Timestamp(System
				    .currentTimeMillis()));
				saveAirticketTicketLog(tempOrder, uri.getUser(), request,
				    TicketLog.TYPE_100,"");
			}
			else if (ticketTypeFlag.longValue() == 3)
			{
				airticketOrder.setOperate121(uri.getUser().getUserNo());
				airticketOrder.setOperate121Time(new Timestamp(System
				    .currentTimeMillis()));
				saveAirticketTicketLog(tempOrder, uri.getUser(), request,
				    TicketLog.TYPE_121,"");
			}

			airticketOrderDAO.update(airticketOrder);

			// 创建买入单
			tempOrder = new AirticketOrder();

			if (form.getTranType() == AirticketOrder.TRANTYPE__1
			    || form.getTranType() == AirticketOrder.TRANTYPE__2)
			{
				form.setTranType(AirticketOrder.TRANTYPE__2);
			}
			else if (form.getTranType() == AirticketOrder.TRANTYPE_3)
			{
				form.setTranType(AirticketOrder.TRANTYPE_3);
			}
			form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
			tempOrder = fillAirticketOrderByAirticketOrderForm(form, tempOrder);
			tempOrder.setTicketType(AirticketOrder.TICKETTYPE_2);

			if (form.getTranType() == AirticketOrder.TRANTYPE__2)
			{
				tempOrder.setStatus(AirticketOrder.STATUS_101);
			}
			if (form.getTranType() == AirticketOrder.TRANTYPE_3)
			{
				tempOrder.setStatus(AirticketOrder.STATUS_107);
			}
			tempOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
			tempOrder.setEntryOperator(uri.getUser().getUserNo());
			tempOrder.setAgent(null);

			tempOrder.setOrderGroup(og);

			// System.out.println("order--"+tempOrder.getTranType()+"--status:"+tempOrder.getStatus());
			updateOrderGroup(tempOrder);
			airticketOrder.setLocked(AirticketOrder.LOCK0);
			airticketOrderDAO.update(tempOrder);

			if (ticketTypeFlag.longValue() == 1)
			{
				tempOrder.setOperate110(uri.getUser().getUserNo());
				tempOrder.setOperate110Time(new Timestamp(System.currentTimeMillis()));
				saveAirticketTicketLog(tempOrder, uri.getUser(), request,
				    TicketLog.TYPE_110,"");
			}
			else if (ticketTypeFlag.longValue() == 3)
			{
				tempOrder.setOperate122(uri.getUser().getUserNo());
				tempOrder.setOperate122Time(new Timestamp(System.currentTimeMillis()));
				saveAirticketTicketLog(tempOrder, uri.getUser(), request,
				    TicketLog.TYPE_122,"");
			}
			airticketOrderDAO.update(tempOrder);
		}

		// 航班信息
		Set flights = airticketOrder.getFlights();
		Iterator itr = flights.iterator();
		while (itr.hasNext())
		{
			Flight flight = (Flight) itr.next();
			flight.setAirticketOrder(null);
			flightDAO.update(flight);
		}

		flights = tempOrder.getFlights();
		itr = flights.iterator();
		while (itr.hasNext())
		{
			Flight flight = (Flight) itr.next();
			flight.setAirticketOrder(null);
			flightDAO.update(flight);
		}

		for (int i = 0; i < form.getFlightCodes().length; i++)
		{
			Flight flight = new Flight();
			flight.setFlightCode(form.getFlightCodes()[i].toUpperCase());// 航班号
			flight.setStartPoint(form.getStartPoints()[i].toUpperCase());// 出发地
			flight.setEndPoint(form.getEndPoints()[i].toUpperCase());
			flight.setBoardingTime(DateUtil.getTimestamp(form.getBoardingTimes()[i]
			    .toString(), "yyyy-MM-dd"));
			flight.setFlightClass(form.getFlightClasss()[i].toString().toUpperCase());
			flight.setDiscount(form.getDiscounts()[i].toString());
			flight.setTicketPrice(form.getTicketPrices()[i]);// 票面价
			flight.setAirportPriceAdult(form.getAdultAirportPrices()[i]);
			flight.setFuelPriceAdult(form.getAdultFuelPrices()[i]);
			flight.setAirportPriceChild(form.getChildAirportPrices()[i]);
			flight.setFuelPriceChild(form.getChildFuelPrices()[i]);
			flight.setAirportPriceBaby(form.getBabyAirportPrices()[i]);
			flight.setFuelPriceBaby(form.getBabyFuelPrices()[i]);
			flight.setStatus(flight.getStatus());// 状态
			flight.setAirticketOrder(airticketOrder);
			flightDAO.save(flight);

			Flight tempFlight = (Flight) flight.clone();
			tempFlight.setAirticketOrder(tempOrder);
			flightDAO.save(tempFlight);
		}

		// 乘机人信息
		Set passengers = airticketOrder.getPassengers();
		Iterator itrPassenger = passengers.iterator();
		while (itrPassenger.hasNext())
		{
			Passenger passenger = (Passenger) itrPassenger.next();
			passenger.setAirticketOrder(null);
			passengerDAO.update(passenger);
		}

		passengers = tempOrder.getPassengers();
		itrPassenger = passengers.iterator();
		while (itr.hasNext())
		{
			Passenger passenger = (Passenger) itrPassenger.next();
			passenger.setAirticketOrder(null);
			passengerDAO.update(passenger);
		}

		for (int i = 0; i < form.getPassengerNames().length; i++)
		{
			if (form.getPassengerNames()[i].trim().equals(""))
				continue;
			Passenger passenger = new Passenger();
			passenger.setName(form.getPassengerNames()[i].toUpperCase());
			passenger.setCardno(form.getPassengerCardnos()[i].toUpperCase());
			passenger.setTicketNumber(form.getPassengerTicketNumbers()[i]);
			passenger.setStatus(new Long(1));// 状态
			passenger.setAirticketOrder(airticketOrder);
			passengerDAO.save(passenger);

			Passenger tempPassenger = (Passenger) passenger.clone();
			tempPassenger.setAirticketOrder(tempOrder);
			passengerDAO.save(tempPassenger);
		}

		airticketOrderDAO.update(airticketOrder);
		airticketOrderDAO.update(tempOrder);
		return airticketOrder.getId();
	}

	private AirticketOrder fillAirticketOrderByAirticketOrderForm(
	    AirticketOrder form, AirticketOrder airticketOrder) throws AppException
	{
		airticketOrder.setAdultCount(form.getAdultCount());// 成人数
		airticketOrder.setChildCount(form.getChildCount());// 儿童数
		airticketOrder.setBabyCount(form.getBabyCount());// 婴儿数
		airticketOrder.setTotalTicketPrice(form.getTotalTicketPrice());// 总票面价
		airticketOrder.setAirportPrice(form.getAirportPrice());// 机建税
		airticketOrder.setFuelPrice(form.getFuelPrice());// 燃油税
		airticketOrder.setTotalAirportPrice(form.getTotalAirportPrice());// 总机建税
		airticketOrder.setTotalFuelPrice(form.getTotalFuelPrice());// 总燃油税

		airticketOrder.setDocumentPrice(form.getDocumentPrice());
		airticketOrder.setInsurancePrice(form.getInsurancePrice());
		airticketOrder.setHandlingCharge(form.getHandlingCharge());

		airticketOrder.setDrawer(form.getDrawer()); // 个出票人
		if (form.getTranType() == AirticketOrder.TRANTYPE__1)
		{// 销售
			airticketOrder.setTranType(AirticketOrder.TRANTYPE__1);
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
			airticketOrder.setTeamaddPrice(form.getTeamaddPrice());// 团队加价
			airticketOrder.setAgentaddPrice(form.getAgentaddPrice());// 客户加价
			airticketOrder
			    .setOverAirportfulePrice(form.getSaleOverAirportfulePrice());// 多收税
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE__2)
		{// 买入
			airticketOrder.setTranType(AirticketOrder.TRANTYPE__2);// 交易类型
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
			airticketOrder.setAirOrderNo(form.getAirOrderNosText());
			airticketOrder.setTotalAmount(form.getTotalAmount());// 交易金额
			airticketOrder.setAgent(null);
		}
		if (form.getTranType() == AirticketOrder.TRANTYPE_3
		    && form.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
		{// 退票销售
			airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
			airticketOrder.setTeamaddPrice(form.getTeamaddPrice());// 团队加价
			airticketOrder.setAgentaddPrice(form.getAgentaddPrice());// 客户加价
			airticketOrder
			    .setOverAirportfulePrice(form.getSaleOverAirportfulePrice());// 多收税
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE_3
		    && form.getBusinessType() == AirticketOrder.TRANTYPE__2)
		{// 退票买入
			airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);// 交易类型
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
			airticketOrder.setAirOrderNo(form.getAirOrderNosText());
			airticketOrder.setTotalAmount(form.getTotalAmount());// 交易金额
			airticketOrder.setAgent(null);
		}
		return airticketOrder;
	}

	/**
	 * 团队订单 编辑利润统计
	 */
	public void editTeamProfit(AirticketOrder form, HttpServletRequest request)
	    throws AppException
	{
		Long id = form.getId();
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(id);
		Long orderGroupId = airticketOrder.getOrderGroup().getId();
		Long subMarkNo = airticketOrder.getSubGroupMarkNo();

		List<AirticketOrder> airticketOrderList = airticketOrderDAO
		    .listBySubGroupAndGroupId(orderGroupId, subMarkNo);
		for (int i = 0; i < airticketOrderList.size(); i++)
		{
			AirticketOrder tempOrder = airticketOrderList.get(i);

			if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__1)
			{
				updateSaleOrderAsProfit(tempOrder, form);
			}
			else if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__2)
			{
				updateBuyOrderAsProfit(tempOrder, form);
			}
			else if (tempOrder.getTranType() == AirticketOrder.TRANTYPE_3)
			{
				if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
				{
					updateSaleOrderAsProfit(tempOrder, form);
				}
				else if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
				{
					updateBuyOrderAsProfit(tempOrder, form);
				}
			}
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			saveAirticketTicketLog(tempOrder, uri.getUser(), request,
			    TicketLog.TYPE_101,"");
		}
	}

	private void updateSaleOrderAsProfit(AirticketOrder order, AirticketOrder form)
	    throws AppException
	{
		order.setOverTicketPrice(form.getSaleOverTicketPrice());// 多收票价
		// order.setOverAirportfulePrice(form.getSaleOverAirportfulePrice());// 多收税
		order.setCommissonCount(form.getSaleCommissonCount());// 返点
		order.setRakeOff(form.getSaleRakeOff());// 后返
		if (order.getMemo() != null)
		{
			form.setMemo(order.getMemo() + "--" + form.getMemo());//
		}
		order.setMemo(form.getSaleMemo());// 备注

		order.setIncomeretreatCharge(form.getSaleIncomeretreatCharge());// 收退票手续费

		if (order.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 退票
			if (order.getStatus() == AirticketOrder.STATUS_107)
			{
				order.setStatus(AirticketOrder.STATUS_116);
			}
			// order.setLocked(AirticketOrder.LOCK1);// 退票利润统计即锁定
		}
		else
		{// 销售
			if (order.getStatus() == AirticketOrder.STATUS_101)
			{
				order.setStatus(AirticketOrder.STATUS_102);
			}
		}

		order.setTotalAmount(form.getSaleTotalAmount());

		update(order);
		updateOrderGroup(order);
	}

	private void updateBuyOrderAsProfit(AirticketOrder order, AirticketOrder form)
	    throws AppException
	{
		order.setHandlingCharge(form.getBuyHandlingCharge());// 手续费
		order.setCommissonCount(form.getBuyCommissonCount());// 返点
		order.setRakeoffCount(form.getBuyRakeoffCount());// 后返
		order.setIncomeretreatCharge(form.getBuyIncomeretreatCharge());// 收退票手续费

		if (order.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 退票
			if (order.getStatus() == AirticketOrder.STATUS_107)
			{
				order.setStatus(AirticketOrder.STATUS_116);
			}
			// order.setLocked(AirticketOrder.LOCK1);// 退票利润统计即锁定
		}
		else
		{// 销售
			if (order.getStatus() == AirticketOrder.STATUS_101)
			{
				order.setStatus(AirticketOrder.STATUS_102);
			}
		}

		update(order);
		updateOrderGroup(order);
	}

	// 团队--根据现有销售订单,创建退票订单
	public long createTeamRefundBySale(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		long groupId = form.getGroupId();
		List orderList = listByGroupIdAndTranType(groupId,
		    AirticketOrder.TRANTYPE__1 + "," + AirticketOrder.TRANTYPE__2);

		AirticketOrder oldSaleOrder = new AirticketOrder();
		AirticketOrder oldBuyOrder = new AirticketOrder();

		for (int i = 0; i < orderList.size(); i++)
		{
			AirticketOrder tempOrder = (AirticketOrder) orderList.get(i);
			long tranType = tempOrder.getTranType();
			if (tranType == AirticketOrder.TRANTYPE__1)
			{
				oldSaleOrder = tempOrder;
			}
			else if (tranType == AirticketOrder.TRANTYPE__2)
			{
				oldBuyOrder = tempOrder;
			}
		}

		AirticketOrder saleOrder = new AirticketOrder();
		AirticketOrder buyOrder = new AirticketOrder();

		long id = fillTeamRefundOrderList(form, oldSaleOrder, saleOrder,
		    oldBuyOrder, buyOrder, request);
		return id;
	}

	// 根据销售订单填充退票订单
	private long fillTeamRefundOrderList(AirticketOrder form,
	    AirticketOrder oldSaleOrder, AirticketOrder saleOrder,
	    AirticketOrder oldBuyOrder, AirticketOrder buyOrder,
	    HttpServletRequest request) throws AppException
	{
		OrderGroup orderGroup = oldSaleOrder.getOrderGroup();

		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		System.out.println("oldSaleOrder id:" + oldSaleOrder.getId());
		System.out.println("oldBuyOrder id:" + oldBuyOrder.getId());

		saleOrder.setAgent(oldSaleOrder.getAgent());
		saleOrder.setDrawer(oldSaleOrder.getDrawer());

		Long originalPassCount = form.getOriginalPassCount();

		if (originalPassCount == null || originalPassCount == 0)
		{
			originalPassCount = new Long(1);
		}

		Long adultCount = form.getAdultCount();
		Long childCount = form.getChildCount();
		Long totalPerson = adultCount + childCount;
		saleOrder.setAdultCount(adultCount);
		saleOrder.setChildCount(childCount);
		buyOrder.setAdultCount(adultCount);
		buyOrder.setChildCount(childCount);

		BigDecimal orderAmount = oldBuyOrder.getTotalAmount().divide(
		    new BigDecimal(originalPassCount), 3);
		orderAmount = orderAmount.multiply(new BigDecimal(totalPerson));
		orderAmount = UnitConverter.round_half_upBigDecimal(orderAmount, -1);
		buyOrder.setTotalAmount(orderAmount);

		BigDecimal teamAddPrice = oldSaleOrder.getTeamaddPrice().divide(
		    new BigDecimal(originalPassCount), 3);
		teamAddPrice = teamAddPrice.multiply(new BigDecimal(totalPerson));
		teamAddPrice = UnitConverter.round_half_upBigDecimal(teamAddPrice, -1);
		saleOrder.setTeamaddPrice(teamAddPrice);

		BigDecimal agentAddPrice = oldSaleOrder.getAgentaddPrice().divide(
		    new BigDecimal(originalPassCount), 3);
		agentAddPrice = agentAddPrice.multiply(new BigDecimal(totalPerson));
		agentAddPrice = UnitConverter.round_half_upBigDecimal(agentAddPrice, -1);
		saleOrder.setAgentaddPrice(agentAddPrice);

		BigDecimal overAirportFuel = oldSaleOrder.getOverAirportfulePrice().divide(
		    new BigDecimal(originalPassCount), 3);
		overAirportFuel = overAirportFuel.multiply(new BigDecimal(totalPerson));
		overAirportFuel = UnitConverter
		    .round_half_upBigDecimal(overAirportFuel, -1);
		saleOrder.setOverAirportfulePrice(overAirportFuel);

		BigDecimal overTicketPrice = oldSaleOrder.getOverTicketPrice().divide(
		    new BigDecimal(originalPassCount), 3);
		overTicketPrice = overTicketPrice.multiply(new BigDecimal(totalPerson));
		overTicketPrice = UnitConverter
		    .round_half_upBigDecimal(overTicketPrice, -1);
		saleOrder.setOverTicketPrice(overTicketPrice);

		saleOrder.setCommissonCount(oldSaleOrder.getCommissonCount());
		saleOrder.setRakeOff(oldSaleOrder.getRakeOff());
		saleOrder.setMemo(oldSaleOrder.getMemo());
		saleOrder.setIncomeretreatCharge(oldSaleOrder.getIncomeretreatCharge());

		saleOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		saleOrder.setTranType(AirticketOrder.TRANTYPE_3);
		saleOrder.setStatus(AirticketOrder.STATUS_107);
		saleOrder.setTicketType(AirticketOrder.TICKETTYPE_2);
		saleOrder.setTotalAmount(BigDecimal.ZERO);
		saleOrder.setFlights(null);
		saleOrder.setPassengers(null);
		saleOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
		saleOrder.setEntryOperator(uri.getUser().getUserNo());
		saleOrder.setOrderGroup(orderGroup);
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(orderGroup
		    .getId());
		saleOrder.setSubGroupMarkNo(newSubGroupNo);
		saleOrder.setOperate121(uri.getUser().getUserNo());
		saleOrder.setOperate121Time(new Timestamp(System.currentTimeMillis()));

		buyOrder.setPlatform(oldBuyOrder.getPlatform());
		buyOrder.setCompany(oldBuyOrder.getCompany());
		Account inRefundAccount = statementDAO.getStatementAccountByOrderSubType(
		    oldBuyOrder.getId(), Statement.SUBTYPE_20, Statement.ORDERTYPE_1);
		if (inRefundAccount != null)
		{
			buyOrder.setAccount(inRefundAccount);// 退票订单收退款账号
		}
		buyOrder.setCommissonCount(oldBuyOrder.getCommissonCount());
		buyOrder.setHandlingCharge(oldBuyOrder.getHandlingCharge());
		buyOrder.setRakeoffCount(oldBuyOrder.getRakeoffCount());
		buyOrder.setIncomeretreatCharge(oldBuyOrder.getIncomeretreatCharge());
		buyOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
		buyOrder.setTranType(AirticketOrder.TRANTYPE_3);
		buyOrder.setTicketType(AirticketOrder.TICKETTYPE_2);
		buyOrder.setStatus(AirticketOrder.STATUS_107);
		buyOrder.setFlights(null);
		buyOrder.setPassengers(null);
		buyOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
		buyOrder.setEntryOperator(uri.getUser().getUserNo());
		buyOrder.setOrderGroup(orderGroup);
		buyOrder.setSubGroupMarkNo(newSubGroupNo);
		buyOrder.setOperate122(uri.getUser().getUserNo());
		buyOrder.setOperate122Time(new Timestamp(System.currentTimeMillis()));

		save(saleOrder);
		save(buyOrder);

		System.out.println("saleOrder id:" + saleOrder.getId());
		System.out.println("buyOrder id:" + buyOrder.getId());

		String[] flightIds = form.getFlightIds();
		flightPassengerBiz.saveFlightByIdsForOrder(saleOrder, flightIds);
		flightPassengerBiz.saveFlightByIdsForOrder(buyOrder, flightIds);

		String[] passengerIds = form.getPassengerIds();
		flightPassengerBiz.savePassengerByIdsForOrder(saleOrder, passengerIds);
		flightPassengerBiz.savePassengerByIdsForOrder(buyOrder, passengerIds);
		update(saleOrder);
		update(buyOrder);

		setTotalTicketPrice(oldSaleOrder, saleOrder);
		setTotalTicketPrice(oldBuyOrder, buyOrder);

		updateOrderGroup(saleOrder);

		return saleOrder.getId();
	}

	// 设置票面、燃油、机建
	public void setTotalTicketPrice(AirticketOrder oldOrder,
	    AirticketOrder newOrder) throws AppException
	{
		try
		{
			Long adultCount = com.fdays.tsms.base.Constant.toLong(newOrder
			    .getAdultCount());
			Long childCount = com.fdays.tsms.base.Constant.toLong(newOrder
			    .getChildCount());
			Long babyCount = com.fdays.tsms.base.Constant.toLong(newOrder
			    .getBabyCount());

			BigDecimal totalTicketPrice = BigDecimal.ZERO;
			BigDecimal totalAirportPrice = BigDecimal.ZERO;
			BigDecimal totalFuelPrice = BigDecimal.ZERO;
			// 航班信息
			Set flights = oldOrder.getFlights();
			if (flights != null)
			{

				Iterator itr = flights.iterator();

				if (adultCount > 0)
				{
					while (itr.hasNext())
					{
						Flight flight = (Flight) itr.next();
						totalTicketPrice = totalTicketPrice.add(flight.getTicketPrice());
						totalAirportPrice = totalAirportPrice.add(flight
						    .getAirportPriceAdult());
						totalFuelPrice = totalFuelPrice.add(flight.getFuelPriceAdult());
					}
				}
				else if (childCount > 0)
				{
					while (itr.hasNext())
					{
						Flight flight = (Flight) itr.next();
						totalTicketPrice = totalTicketPrice.add(flight.getTicketPrice());
						totalAirportPrice = totalAirportPrice.add(flight
						    .getAirportPriceChild());
						totalFuelPrice = totalFuelPrice.add(flight.getFuelPriceChild());
					}
				}
			}
			newOrder.setTotalTicketPrice(totalTicketPrice);
			newOrder.setTotalAirportPrice(totalAirportPrice);
			newOrder.setTotalFuelPrice(totalFuelPrice);

			update(newOrder);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 删除订单(改变状态)
	public void deleteAirticketOrder(Long id) throws AppException
	{
		AirticketOrder airticketOrder = getAirticketOrderById(id);
		airticketOrder.setStatus(AirticketOrder.STATUS_88);// 将订单状态变为已废弃
		update(airticketOrder);

		deleteStatementByOrderId(id);
	}

	// 删除订单的关联结算记录(改变状态)
	public void deleteStatementByOrderId(Long id)
	    throws AppException
	{
		List statementList = statementDAO.getStatementListByOrder(id, Statement.ORDERTYPE_1);
		for (int i = 0; i < statementList.size(); i++)
		{
			Statement statement = (Statement) statementList.get(i);
			statement.setStatus(Statement.STATUS_88);// 已废弃
			statementDAO.update(statement);
		}
	}

	// 显示订单详细信息
	public String view(long id, HttpServletRequest request) throws AppException
	{
		String forwardPage = "";

		if (id > 0)
		{
			List<Statement> statementList = new ArrayList<Statement>();
			List<Passenger> passengerList = new ArrayList<Passenger>();
			List<Flight> flightList = new ArrayList<Flight>();
			List<TicketLog> ticketLogList = new ArrayList<TicketLog>();

			AirticketOrder airticketOrder = getAirticketOrderById(id);

			long subGroupNo = airticketOrder.getSubGroupMarkNo();

			List<AirticketOrder> airticketOrderList = listBySubGroupAndGroupId(
			    airticketOrder.getOrderGroup().getId(), subGroupNo);
			String ordersString = "";

			for (int i = 0; i < airticketOrderList.size(); i++)
			{
				AirticketOrder tempOrder = airticketOrderList.get(i);
				ordersString += tempOrder.getId() + ",";
			}
			if (ordersString.length() > 1)
			{
				ordersString = ordersString.substring(0, ordersString.length() - 1);
			}
//			System.out.println("orderString:" + ordersString);

			passengerList = passengerDAO.listByairticketOrderId(id);
			flightList = flightDAO.getFlightListByOrderId(id);
			statementList = statementDAO.getStatementListByOrders(ordersString,
			    Statement.ORDERTYPE_1);

			request.setAttribute("airticketOrder", airticketOrder);
			request.setAttribute("statementList", statementList);
			request.setAttribute("airticketOrderList", airticketOrderList);
			request.setAttribute("flightList", flightList);
			request.setAttribute("passengerList", passengerList);
			ticketLogList = ticketLogDAO.getTicketLogByOrderIds(ordersString);
			request.setAttribute("ticketLogList", ticketLogList);
			forwardPage = airticketOrder.getOrderGroup().getNo();
		}
		else
		{
			forwardPage = "ERROR";
		}
		return forwardPage;
	}
	
	// 计算订单的利润
	public BigDecimal getOrderProfitById(long orderId)
	    throws AppException
	{
		BigDecimal inAmount=BigDecimal.ZERO;
		BigDecimal outAmount=BigDecimal.ZERO;
		BigDecimal profits=BigDecimal.ZERO;
		AirticketOrder order=getAirticketOrderById(orderId);
		if(order!=null){
			List<AirticketOrder> orderList = listBySubGroupAndGroupId(order.getOrderGroup().getId(), order.getSubGroupMarkNo());
		
			for (int i = 0; i < orderList.size(); i++) {
				AirticketOrder tempOrder=orderList.get(i);
				if(tempOrder!=null){
					Long tempOrderId=Constant.toLong(tempOrder.getId());
					Long businessType=Constant.toLong(tempOrder.getBusinessType());
					Long tranType=Constant.toLong(tempOrder.getTranType());
					
					
					if(businessType==AirticketOrder.BUSINESSTYPE__1&&tranType==AirticketOrder.TRANTYPE__1){
						inAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_10, Statement.ORDERTYPE_1);
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__2&&tranType==AirticketOrder.TRANTYPE__2){
						outAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_20, Statement.ORDERTYPE_1);	
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__1&&tranType==AirticketOrder.TRANTYPE_3){
						outAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_21, Statement.ORDERTYPE_1);	
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__2&&tranType==AirticketOrder.TRANTYPE_3){
						inAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_11, Statement.ORDERTYPE_1);
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__1&&tranType==AirticketOrder.TRANTYPE_4){
						outAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_21, Statement.ORDERTYPE_1);
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__2&&tranType==AirticketOrder.TRANTYPE_4){
						inAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_11, Statement.ORDERTYPE_1);
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__1&&tranType==AirticketOrder.TRANTYPE_5){
						outAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_21, Statement.ORDERTYPE_1);
					}
					if(businessType==AirticketOrder.BUSINESSTYPE__2&&tranType==AirticketOrder.TRANTYPE_5){
						inAmount=statementDAO.getStatementAmount(tempOrderId, Statement.SUBTYPE_11, Statement.ORDERTYPE_1);
					}
				}
			}
		}	
		inAmount=Constant.toBigDecimal(inAmount);
		outAmount=Constant.toBigDecimal(outAmount);		
		if(inAmount.compareTo(BigDecimal.ZERO)==0){
			profits=BigDecimal.ZERO;
		}else{
			profits=inAmount.subtract(outAmount);
		}
		
		
		return profits;
	}


	// 计算订单的销售量
	public BigDecimal getSaleTotalAmount(String carrier, int year, int month)
	    throws AppException
	{
		if (carrier.equalsIgnoreCase("ZH"))
		{
			return BigDecimal.valueOf(7000000);
		}
		else
		{
			return BigDecimal.valueOf(5000000);
		}
	}

	// 查看团队
	public void viewTeam(AirticketOrderListForm ulf, HttpServletRequest request)
	    throws AppException
	{
		Long airticketOrderId = ulf.getId();
		if (airticketOrderId != null && airticketOrderId > 0)
		{
			editTeamOrder(airticketOrderId, request);
		}
	}

	public HttpServletRequest loadFlightAgentByAirticketOrder(
	    AirticketOrder order, HttpServletRequest request) throws AppException
	{
		List<Agent> agentList = agentDAO.getAgentList();
		List<Flight> flightList = flightDAO.getFlightListByOrderId(order.getId());
		Flight flight = flightDAO.getFlightByAirticketOrderID(order.getId());

		request.setAttribute("flight", flight);
		request.setAttribute("flightSize", flightList.size());
		request.setAttribute("flightList", flightList);
		request.setAttribute("agentList", agentList);
		return request;
	}

	// ------DWR
	public AirticketOrder getAirticketOrderByGroupIdAndTranType(long groupId,
	    String tranType) throws AppException
	{
		AirticketOrder order = new AirticketOrder();
		List airticketOrderList = listByGroupIdAndTranType(groupId, tranType);
		if (airticketOrderList != null && airticketOrderList.size() > 0)
		{
			order = (AirticketOrder) airticketOrderList.get(0);
		}

		if (order.getAgent() != null)
		{
			String agentName = order.getAgent().getName();
			System.out.println(agentName);
		}

		return order;
	}

	public AirticketOrder getAirticketOrderByGroupIdStatus(long groupId,
	    String tranType, String status) throws AppException
	{
		AirticketOrder order = new AirticketOrder();
		List airticketOrderList = airticketOrderDAO.listByGroupIdAndTranTypeStatus(
		    groupId, tranType, status);
		if (airticketOrderList != null && airticketOrderList.size() > 0)
		{
			order = (AirticketOrder) airticketOrderList.get(0);
		}
		return order;
	}

	private OrderGroup saveOrderGroup(AirticketOrder order) throws AppException
	{
		OrderGroup og = new OrderGroup();
		og.setNo(noUtil.getAirticketGroupNo());
		if (order.getEntryTime() == null)
			og.setFirstDate(new Timestamp(System.currentTimeMillis()));
		else
			og.setFirstDate(order.getEntryTime());
		og.setLastDate(new Timestamp(System.currentTimeMillis()));
		airticketOrderDAO.saveOrderGroup(og);
		return og;
	}

	private OrderGroup updateOrderGroup(AirticketOrder order) throws AppException
	{
		OrderGroup og = airticketOrderDAO.getOrderGroupById(order.getOrderGroup()
		    .getId());
		og.setLastDate(new Timestamp(System.currentTimeMillis()));
		airticketOrderDAO.saveOrderGroup(og);
//		System.out.println("update OrderGroup no:" + order.getGroupMarkNo() + "--id:"
//		    + order.getId());
		return og;
	}

	public String getForwardPageByOrderType(AirticketOrder airticketOrder)
	{
		String forwardPage = "";
		long tranType = airticketOrder.getTranType();

		if (tranType == AirticketOrder.TRANTYPE__1
		    || tranType == AirticketOrder.TRANTYPE__2)
		{
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else if (tranType == AirticketOrder.TRANTYPE_3
		    || tranType == AirticketOrder.TRANTYPE_4)
		{
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE3 + "";
		}
		else if (tranType == AirticketOrder.TRANTYPE_5)
		{
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE2 + "";
		}
		else
		{
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		return forwardPage;
	}

	/**
	 * 保存结算记录
	 * 
	 * @param AirticketOrder
	 *          order
	 * @param SysUser
	 *          sysUser
	 * @param long statementType
	 * @param long orderSubtype
	 * @param long statementStatus
	 * @param Timestamp
	 *          statementTime
	 */
	public void saveStatementByOrder(AirticketOrder order, SysUser sysUser,
	    long statementType, long orderSubtype, long statementStatus,
	    Timestamp statementTime) throws AppException
	{
		AirticketLogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		long orderId=order.getId();
		long orderGroupId=order.getOrderGroup().getId();
		AirticketOrderStore.addOrderString(orderGroupId);
		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setOrderId(orderId);
		statement.setOrderType(Statement.ORDERTYPE_1);
		statement.setOrderSubtype(orderSubtype);
		if (statementType == Statement.type_1)
		{
			statement.setToAccount(order.getAccount());
		}
		else if (statementType == Statement.type_2)
		{
			statement.setFromAccount(order.getAccount());
		}
		statement.setTotalAmount(order.getTotalAmount());

		statement.setSysUser(sysUser);
		statement.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		if (statementTime != null)
		{
			statement.setStatementDate(statementTime);// 结算时间
		}
		else
		{
			myLog.info(statement.getStatementNo() + "--结算时间为空,默认取当前操作时间为结算时间");
			statement.setStatementDate(new Timestamp(System.currentTimeMillis()));// 结算时间
		}

		if (orderSubtype == Statement.SUBTYPE_20)
		{
			statement.setMemo(order.getOutMemo());
		}

		statement.setType(statementType);// 类型
		statement.setStatus(statementStatus);// 状态

		statementDAO.save(statement);
		
//		statementDAO.synStatementAmount(orderId);
//		statementDAO.synOldStatementAmount(orderId);	
		
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderId(statement.getOrderId());
		ticketLog.setOrderNo(statement.getStatementNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(statement.getSysUser());// 操作员
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(TicketLog.TYPE_98);
		ticketLog.setStatus(new Long(1));
		ticketLog.setContent("创建了" + statement.toLogString());
		ticketLogDAO.save(ticketLog);
		
//		saveAirticketTicketLog(order, sysUser, null, TicketLog.TYPE_98, "创建了" + statement.toLogString());

//		myLog.info(sysUser.getUserName() + "-确认-" + statement.toLogString()
//		    + " For order id:" + order.getId());
	}

	/**
	 * 修改保存结算记录
	 */
	public void updateStatementByOrder(AirticketOrder order, SysUser sysUser,
	    long statementType, long statementStatus, Timestamp statementTime)
	    throws AppException
	{
	
	}

	public void saveAirticketTicketLog(AirticketOrder order, SysUser sysUser,
	    HttpServletRequest request, long ticketLogType,String content) throws AppException
	{
//		TicketLogListener ticketLog=new TicketLogListener(ticketLogDAO,order,sysUser,request,ticketLogType);
//		MainTask.put(ticketLog);
		
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderId(order.getId());
		ticketLog.setOrderNo(order.getOrderNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(sysUser);// 操作员
		if (request != null) {
			ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
		}
		ticketLog.setContent(content);
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(ticketLogType);
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);

//		LogUtil myLog = new AirticketLogUtil(true, false,
//				AirticketOrderBizImp.class, "");
//		myLog.info(sysUser.getUserName() + "-" + ticketLog.getTypeInfo()
//				+ "--order no:" + order.getOrderNo());
	}

	public AirticketOrder getAirticketOrderByStatementId(long statementId)
	    throws AppException
	{
		return airticketOrderDAO.getAirticketOrderByStatementId(statementId);
	}

	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
	    throws AppException
	{
		return airticketOrderDAO.getAirticketOrderBysubPnr(subPnr);
	}

	// 根据PNR、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
	    long businessType, long tranType) throws AppException
	{
		return airticketOrderDAO.getAirticketOrderForRetireUmbuchen(subPnr,
		    businessType, tranType);
	}

	public boolean checkPnrisToday(AirticketOrder airticketOrder)
	    throws AppException
	{
		return airticketOrderDAO.checkPnrisToday(airticketOrder);
	}

	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
	    throws AppException
	{
		return airticketOrderDAO.checkPnrisMonth(airticketOrder);
	}

	public List<AirticketOrder> listByGroupId(long groupId) throws AppException
	{
		return airticketOrderDAO.listByGroupId(groupId);
	}

	public List<AirticketOrder> listBySubGroupAndGroupId(long orderGroupId,
	    Long subMarkNo) throws AppException
	{
		return airticketOrderDAO.listBySubGroupAndGroupId(orderGroupId, subMarkNo);
	}

	// ---------DWR有调用
	public List<AirticketOrder> listByGroupIdAndTranType(long groupId,
	    String tranType) throws AppException
	{
		return airticketOrderDAO.listByGroupIdAndTranType(groupId, tranType);
	}

	public AirticketOrder getDrawedAirticketOrderByGroupId(long groupId,
	    long tranType) throws AppException
	{
		return airticketOrderDAO
		    .getDrawedAirticketOrderByGroupId(groupId, tranType);
	}

	public List<AirticketOrder> listByGroupIdAndBusinessTranType(long groupId,
	    String tranType, String businessType) throws AppException
	{
		return airticketOrderDAO.listByGroupIdAndBusinessTranType(groupId,
		    tranType, businessType);
	}

	public List<AirticketOrder> getSaleDrawedOrderListByPNR(String subPnr) throws AppException{
		return airticketOrderDAO.getSaleDrawedOrderListByPNR(subPnr);
	}
	
	public List<AirticketOrder> getDrawedOrderListByPNR(String subPnr) throws AppException
	{
		return airticketOrderDAO.getDrawedOrderListByPNR(subPnr);
	}

	public long getGroupIdByOrderId(long orderId)throws AppException{
		return airticketOrderDAO.getGroupIdByOrderId(orderId);
	}
	
	public List list(AirticketOrderListForm rlf, UserRightInfo uri)
	    throws AppException
	{
		return airticketOrderDAO.list(rlf, uri);
	}

	public List listByCarrier(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		return airticketOrderDAO.listByCarrier(carrier,startDate,endDate);
		 
	}
	
	//根据承运人分段获取AirlinePolicyAfter对象
	public List listByCarrier(String carrier,Timestamp startDate,Timestamp endDate,
			int startRow,int rowCount) throws AppException
	{
		List<AirticketOrder> orderList = airticketOrderDAO.listByCarrier(carrier,startDate,endDate,startRow,rowCount);
		for(int i=0;i<orderList.size();i++){
			Hibernate.initialize(orderList.get(i).getFlights());	//Hibernate显式初始化Flight
		}
		return orderList;
		 
	}
	

	/**
	 * 初始化后返政策的信息，将利润、后返政策、后返利润设为零。
	 * @param carrier
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public boolean iniProfitAfterInformation(String carrier,
			Timestamp startDate, Timestamp endDate) throws AppException {
		return airticketOrderDAO.iniProfitAfterInformation(carrier, startDate, endDate);
	}

	/**
	 * 查询适合条件的记录总数
	 * @param carrier
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws AppException
	 */
	public int getRowCountByCarrier(String carrier, Timestamp startDate,
			Timestamp endDate) throws AppException {
		return airticketOrderDAO.rowCountByCarrier(carrier, startDate, endDate);
	}


	
	public int sumTicketNum(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
 	{ 		
 		return airticketOrderDAO.sumTicketNum(carrier,startDate,endDate);
 	}
	public int sumOrderNum(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		return airticketOrderDAO.sumOrderNum(carrier,startDate,endDate);
	}
	public BigDecimal sumSaleAmount(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	{
		return airticketOrderDAO.sumSaleAmount(carrier,startDate,endDate);
	}
	 public List<BigDecimal> sumProfitAfter(String carrier,Timestamp startDate,Timestamp endDate) throws AppException
	 {
		 return airticketOrderDAO.sumProfitAfter(carrier,startDate,endDate);
	 }
	
	public List listTeam(AirticketOrderListForm rlf, UserRightInfo uri)
	    throws AppException
	{
		return airticketOrderDAO.listTeam(rlf, uri);
	}

	public List list() throws AppException
	{
		return airticketOrderDAO.list();
	}

	public void delete(long id) throws AppException
	{
		airticketOrderDAO.delete(id);
	}

	public long save(AirticketOrder airticketOrder) throws AppException
	{
		return airticketOrderDAO.save(airticketOrder);
	}

	public long update(AirticketOrder airticketOrder) throws AppException
	{
		return airticketOrderDAO.update(airticketOrder);
	}

	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
	    throws AppException
	{
		return airticketOrderDAO.getAirticketOrderById(airtickeOrderId);
	}
	
	public List listIDBySubGroupAndGroupId(long orderGroupId, Long subMarkNo)
	throws AppException {
		return airticketOrderDAO.listIDBySubGroupAndGroupId(orderGroupId, subMarkNo);
	}

	/**
	 * // * 通过 信息PNR获取外部数据 //
	 */
	public String airticketOrderByBlackOutPNR(HttpServletRequest request,
	    AirticketOrder form) throws AppException
	{
		String forwardPage = "";
		// Inform inf = new Inform();
		// UserRightInfo uri = (UserRightInfo) request.getSession()
		// .getAttribute("URI");
		// String pnrInfo = request.getParameter("pnrInfo");
		//
		// if (pnrInfo != null && !"".equals(pnrInfo.trim()))
		// {
		// TempPNR tempPNR = getTempPNRByBlackInfo(pnrInfo);
		// if (tempPNR != null)
		// {
		// // 设置临时会话
		// form.setBigPnr(tempPNR.getB_pnr());
		// uri.setTempPNR(tempPNR);
		//
		// AirticketOrder order = new AirticketOrder();
		// order.setAddType("OutPNR");// 设置添加类型
		// order.setDrawPnr(tempPNR.getPnr());// 出票pnr
		// order.setSubPnr(tempPNR.getPnr());// 预订pnr
		// order.setBigPnr(tempPNR.getB_pnr());// 大pnr
		// order.setTicketPrice(tempPNR.getFare());// 票面价格
		// order.setAirportPrice(tempPNR.getTax());// 机建费
		// order.setFuelPrice(tempPNR.getYq());// 燃油税
		//
		// flightPassengerBiz.saveFlightPassengerInOrderByTempPNR(tempPNR, order);
		//
		// request.setAttribute("airticketOrder", order);
		//
		// forwardPage = form.getForwardPage();
		// System.out.println("===forwardPage====" + forwardPage);
		// return forwardPage;
		// }
		// else
		// {
		// inf.setMessage("PNR解析错误");
		// inf.setBack(true);
		// forwardPage = "inform";
		// request.setAttribute("inf", inf);
		// }
		// }
		// else
		// {
		// inf.setMessage("PNR 不能为空！");
		// inf.setBack(true);
		// forwardPage = "inform";
		// request.setAttribute("inf", inf);
		// }
		return forwardPage;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO)
	{
		this.airticketOrderDAO = airticketOrderDAO;
	}

	public void setFlightDAO(FlightDAO flightDAO)
	{
		this.flightDAO = flightDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO)
	{
		this.passengerDAO = passengerDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO)
	{
		this.statementDAO = statementDAO;
	}

	public void setNoUtil(NoUtil noUtil)
	{
		this.noUtil = noUtil;
	}

	public void setAgentDAO(AgentDAO agentDAO)
	{
		this.agentDAO = agentDAO;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO)
	{
		this.ticketLogDAO = ticketLogDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO)
	{
		this.accountDAO = accountDAO;
	}

	public void setFlightPassengerBiz(FlightPassengerBiz flightPassengerBiz)
	{
		this.flightPassengerBiz = flightPassengerBiz;
	}

	public void setPlatformDAO(PlatformDAO platformDAO)
	{
		this.platformDAO = platformDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO)
	{
		this.companyDAO = companyDAO;
	}

	public void setPlatComAccountDAO(PlatComAccountDAO platComAccountDAO)
	{
		this.platComAccountDAO = platComAccountDAO;
	}
}
