package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
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
import com.fdays.tsms.airticket.util.CabinUtil;
import com.fdays.tsms.airticket.util.IBEUtil;
import com.fdays.tsms.airticket.util.ParseBlackUtil;
import com.fdays.tsms.base.NoUtil;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.base.util.UnitConverter;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.dao.TicketLogDAO;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.dao.AccountDAO;
import com.fdays.tsms.transaction.dao.AgentDAO;
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
	private AccountDAO accountDAO;
	private FlightPassengerBiz flightPassengerBiz;

	// B2B订单录入
	public String createOrder(HttpServletRequest request,
	    AirticketOrder airticketOrderForm) throws AppException
	{
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		TempPNR tempPNR = uri.getTempPNR();

		if (tempPNR != null)
		{
			airticketOrderForm.setStatus(AirticketOrder.STATUS_1); // 订单状态
			airticketOrderForm.setTicketType(AirticketOrder.TICKETTYPE_1); // 设置机票类型
			airticketOrderForm.getTicketLog().setType(TicketLog.TYPE_1);// 操作日志

			// 创建正常销售订单
			forwardPage = createPNR(airticketOrderForm, tempPNR, uri);

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
			uri.setTempPNR(null);
		}
		else
		{
			inf.setMessage("tempPNR不能为空！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		return forwardPage;
	}

	/**
	 * 通过黑屏信息解析机票订单数据
	 */
	public static TempPNR getTempPNRByBlackInfo(String blackInfo)
	    throws AppException
	{
		TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(blackInfo,
		    ParseBlackUtil.Type_Content);// PNR、乘客、行程
		tempPNR = IBEUtil.setTicketPriceByIBEInterface(tempPNR);// 基准票价、燃油、机建
		tempPNR = CabinUtil.setTicketPriceByIBEInterface(tempPNR);// 舱位折扣
		return tempPNR;
	}

	/**
	 * 创建B2B和B2C销售订单调用
	 */
	public String createPNR(AirticketOrder airticketOrderFrom, TempPNR tempPNR,
	    UserRightInfo uri) throws AppException
	{
		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setSubPnr(tempPNR.getPnr());// 预订pnr
		if (airticketOrderFrom.getBigPnr() != null
		    && !"".equals(airticketOrderFrom.getBigPnr().trim()))
		{
			ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		}
		ao.setTicketPrice(tempPNR.getFare());// 票面价格
		ao.setAirportPrice(tempPNR.getTax());// 机建费
		ao.setFuelPrice(tempPNR.getYq());// 燃油税
		if (airticketOrderFrom.getAgentId() != null
		    && airticketOrderFrom.getAgentId() > 0)
		{
			Agent agent = agentDAO.getAgentByid(airticketOrderFrom.getAgentId());
			ao.setAgent(agent); // 购票客户
		}
		else
		{
			ao.setAgent(null);
		}
		ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrderFrom.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号

		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrderFrom.getTicketType());// 机票类型
		ao.setTranType(AirticketOrder.TRANTYPE__1);// 交易类型
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
		if (ao.getTicketType() == AirticketOrder.TICKETTYPE_1)
		{
			ao.setOperate1(uri.getUser().getUserNo());
			ao.setOperate1Time(new Timestamp(System.currentTimeMillis()));
			ao.setOperate2(uri.getUser().getUserNo());
			ao.setOperate2Time(new Timestamp(System.currentTimeMillis()));
		}
		else if (ao.getTicketType() == AirticketOrder.TICKETTYPE_3)
		{
			// ao.setOperate301(uri.getUser().getUserNo());
			// ao.setOperate301Time(new Timestamp(System.currentTimeMillis()));
		}

		// 设置平台公司帐号
		Long platformId = airticketOrderFrom.getPlatformId();
		Long companyId = airticketOrderFrom.getCompanyId();
		Long accountId = airticketOrderFrom.getAccountId();
		if (platformId != null && companyId != null && accountId != null)
		{
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
		}
		ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额

		// 创建一个新组
		OrderGroup og = saveOrderGroup(ao);

		ao.setOrderGroup(og);
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(og.getId());
		ao.setSubGroupMarkNo(newSubGroupNo);

		airticketOrderDAO.save(ao);

		flightPassengerBiz.saveFlightPassengerByTempPNR(tempPNR, ao);

		// 收款
		saveStatementByAirticketOrder(ao, uri.getUser(), Statement.type_1,
		    Statement.STATUS_1);

		// 销售订单录入日志
		saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
		    airticketOrderFrom.getTicketLog().getType());

		// 销售-收款-日志
		saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
		    TicketLog.TYPE_2);
		String forwardPage = ao.getOrderGroup().getId() + "";
		return forwardPage;
	}

	// 创建申请支付订单
	public String createApplyTickettOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());

			// 机票订单
			AirticketOrder ao = new AirticketOrder();
			ao.setSubPnr(airticketOrderFrom.getPnr());// 预订pnr
			ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
			ao.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
			ao.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
			ao.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
			ao.setAgent(airticketOrder.getAgent()); // 购票客户
			ao.setDocumentPrice(airticketOrder.getDocumentPrice());// 行程单费用
			ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
			ao.setRebate(airticketOrderFrom.getRebate());// 政策
			ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号

			// 修改一个新组的时间
			updateOrderGroup(airticketOrder);
			ao.setOrderGroup(airticketOrder.getOrderGroup());

			ao.setSubGroupMarkNo(airticketOrder.getSubGroupMarkNo());
			ao.setStatus(AirticketOrder.STATUS_2); // 订单状态
			ao.setTicketType(airticketOrder.getTicketType());// 机票类型
			ao.setTranType(Statement.type_2);// 交易类型
			ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ao.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
			ao.setEntryOperator(uri.getUser().getUserNo());
			ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
			ao.setOperate13(uri.getUser().getUserNo());
			ao.setOperate13Time(new Timestamp(System.currentTimeMillis()));

			// 设置平台公司帐号
			long platformId = airticketOrderFrom.getPlatformId();// Long.parseLong(request.getParameter("platformId9"));
			long companyId = airticketOrderFrom.getCompanyId();// Long.parseLong(request.getParameter("companyId9"));
			long accountId = airticketOrderFrom.getAccountId();// Long.parseLong(request.getParameter("accountId9"));

			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));

			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
			airticketOrderDAO.save(ao);

			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);

			airticketOrder.setStatus(AirticketOrder.STATUS_3);
			airticketOrderDAO.update(airticketOrder);// 修改原订单信息

			// 申请支付--日志
			saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
			    TicketLog.TYPE_13);
			// forwardPage=ao.getGroupMarkNo();
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 重新申请支付
	public String anewApplyTicket(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		if (airticketOrderFrom != null
		    && airticketOrderFrom.getGroupMarkNo() != null)
		{
			if (airticketOrderFrom.getId() > 0)
			{
				AirticketOrder ao = airticketOrderDAO
				    .getAirticketOrderById(airticketOrderFrom.getId());

				if (ao.getStatus() == AirticketOrder.STATUS_4)
				{
					ao.setStatus(AirticketOrder.STATUS_14);
					airticketOrderDAO.update(ao);
				}
				else if (ao.getStatus() == AirticketOrder.STATUS_6)
				{
					ao.setStatus(AirticketOrder.STATUS_16);
					airticketOrderDAO.update(ao);
				}
			}

			// 原销售订单订单
			AirticketOrder airticketOrder = getAirticketOrderByGroupIdAndTranType(
			    airticketOrderFrom.getGroupId(), "1");
			airticketOrder.setStatus(AirticketOrder.STATUS_3);
			airticketOrderDAO.update(airticketOrder);// 修改原订单信息

			// 新建买入订单
			AirticketOrder ao = new AirticketOrder();
			ao.setSubPnr(airticketOrderFrom.getPnr());// 预订pnr
			ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
			ao.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
			ao.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
			ao.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
			ao.setAgent(airticketOrder.getAgent()); // 购票客户
			ao.setDocumentPrice(airticketOrder.getDocumentPrice());// 行程单费用
			ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
			ao.setRebate(airticketOrderFrom.getRebate());// 政策
			ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号

			ao.setOrderGroup(airticketOrder.getOrderGroup());
			updateOrderGroup(ao);// 订单组编号

			ao.setSubGroupMarkNo(airticketOrder.getSubGroupMarkNo());
			ao.setStatus(AirticketOrder.STATUS_2); // 订单状态
			ao.setTicketType(airticketOrder.getTicketType());// 机票类型
			ao.setTranType(Statement.type_2);// 交易类型
			ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ao.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
			ao.setEntryOperator(uri.getUser().getUserNo());
			ao.setPayOperator(uri.getUser().getUserNo());
			ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

			ao.setOperate13(uri.getUser().getUserNo());
			ao.setOperate13Time(new Timestamp(System.currentTimeMillis()));

			// 设置平台公司帐号
			long platformId = airticketOrderFrom.getPlatformId();// Long.parseLong(request.getParameter("platformId9"));
			long companyId = airticketOrderFrom.getCompanyId();// Long.parseLong(request.getParameter("companyId9"));
			long accountId = airticketOrderFrom.getAccountId();// Long.parseLong(request.getParameter("accountId9"));

			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));

			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
			airticketOrderDAO.save(ao);
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);

			// 操作日志
			saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
			    TicketLog.TYPE_13);
			// forwardPage=ao.getGroupMarkNo();
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 锁定
	public String lockupOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getGroupMarkNo();

			airticketOrder.setStatus(AirticketOrder.STATUS_7); // 订单状态
			airticketOrder.setCurrentOperator(uri.getUser().getUserNo());// 当前操作人

			airticketOrder.setOperate14(uri.getUser().getUserNo());
			airticketOrder
			    .setOperate14Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,
			    uri.getUser(), request, TicketLog.TYPE_14);

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 解锁(自己的订单)
	public String unlockSelfOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		LogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			if (airticketOrder.getCurrentOperator() != null
			    && !"".equals(airticketOrder.getCurrentOperator()))
			{
				if (airticketOrder.getCurrentOperator().equals(
				    uri.getUser().getUserNo()))
				{
					airticketOrder.setStatus(AirticketOrder.STATUS_8); // 订单状态
					airticketOrder.setCurrentOperator(null);// 当前操作人
					airticketOrder.setOperate16(uri.getUser().getUserNo());
					airticketOrder.setOperate16Time(new Timestamp(System
					    .currentTimeMillis()));
					airticketOrderDAO.update(airticketOrder);

					updateOrderGroup(airticketOrder);

					saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo, uri
					    .getUser(), request, TicketLog.TYPE_16);

					forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
				}
				else
				{
					myLog.info(groupMarkNo + "--orderId:" + airticketOrder.getId()
					    + "解锁人与锁定人不符");
					return "ERROR";
				}
			}
			else
			{
				myLog.info(groupMarkNo + "--orderId:" + airticketOrder.getId()
				    + "没有当前操作人");
				return "ERROR";
			}
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 解锁（所有订单）
	public String unlockAllOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			airticketOrder.setStatus(AirticketOrder.STATUS_8); // 订单状态
			airticketOrder.setCurrentOperator(null);// 当前操作人
			airticketOrder.setOperate17(uri.getUser().getUserNo());
			airticketOrder
			    .setOperate17Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,
			    uri.getUser(), request, TicketLog.TYPE_17);

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 确认支付
	public String confirmPayment(AirticketOrder form,HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");

		if (form != null && form.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			// 设置平台公司帐号
			Long platformId = form.getPlatformId();
			Long companyId = form.getCompanyId();
			Long accountId = form.getAccountId();
			airticketOrder.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			airticketOrder.setCompany(PlatComAccountStore.getCompanyById(companyId));
			airticketOrder.setAccount(PlatComAccountStore.getAccountById(accountId));

			airticketOrder.setStatus(AirticketOrder.STATUS_3); // 修改订单状态
			airticketOrder.setSubPnr(form.getPnr());
			airticketOrder.setAirOrderNo(form.getAirOrderNo());// 订单号
			airticketOrder.setRebate(form.getRebate());// 政策
			airticketOrder.setTotalAmount(form.getTotalAmount());// 设置金额
			airticketOrder.setPayOperator(uri.getUser().getUserNo());
			airticketOrder.setPayTime(new Timestamp(System.currentTimeMillis()));
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

			airticketOrder.setOperate15(uri.getUser().getUserNo());
			airticketOrder.setOperate15Time(new Timestamp(System.currentTimeMillis()));
			airticketOrder.setCurrentOperator(null);// 当前操作人
			airticketOrderDAO.update(airticketOrder);
			
			// 付款
			saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_2, Statement.STATUS_1);

			updateOrderGroup(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,uri.getUser(), request, TicketLog.TYPE_15);			

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 确认出票
	public String confirmTicket(AirticketOrder form,HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		if (form != null && form.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());
			Long groupId=airticketOrder.getOrderGroup().getId();
			
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
					airticketOrder.setOperate5Time(new Timestamp(System.currentTimeMillis()));
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
			airticketOrder.setDrawer(uri.getUser().getUserNo());
			airticketOrder.setOperate5(uri.getUser().getUserNo());
			airticketOrder.setOperate5Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			// -------------
			setNeverRequestAsDrawTicket(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getOrderGroup().getNo(), uri.getUser(), request, TicketLog.TYPE_5);

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 取消出票
	public String quitTicket(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			airticketOrder.setStatus(AirticketOrder.STATUS_4);
			if (airticketOrderFrom.getStatus() != null)
			{
				airticketOrder.setStatus(airticketOrderFrom.getStatus());
			}
			airticketOrder.setMemo(airticketOrderFrom.getMemo());
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrder.setOperate4(uri.getUser().getUserNo());
			airticketOrder.setOperate4Time(new Timestamp(System.currentTimeMillis()));
			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			setNeverRequestAsQuiutTicket(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,
			    uri.getUser(), request, TicketLog.TYPE_4);
			forwardPage = AirticketOrder.ORDER_GROUP_TYPE1 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 取消出票,确认退款
	public String agreeCancelRefund(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			long businessType = airticketOrder.getBusinessType();

			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrder.setStatus(AirticketOrder.STATUS_6); // 订单状态

			if (businessType == 1)
			{// 卖出
				// 取消出票，收供应商退款
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.STATUS_1, Statement.STATUS_1);
				saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo, uri
				    .getUser(), request, TicketLog.TYPE_20);
				airticketOrder.setOperate20(uri.getUser().getUserNo());
				airticketOrder.setOperate20Time(new Timestamp(System
				    .currentTimeMillis()));

			}
			else if (businessType == 2)
			{// 买入
				// 取消出票，付采购商退款
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.STATUS_2, Statement.STATUS_1);
				saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo, uri
				    .getUser(), request, TicketLog.TYPE_21);
				airticketOrder.setOperate21(uri.getUser().getUserNo());
				airticketOrder.setOperate21Time(new Timestamp(System
				    .currentTimeMillis()));

			}

			airticketOrder.setOperate4(uri.getUser().getUserNo());
			airticketOrder.setOperate4Time(new Timestamp(System.currentTimeMillis()));

			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			setNeverRequestAsQuiutTicket(airticketOrder);

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
	 *          当前操作的订单 卖出取消出票，买入如果有已经退款（status=6）的单改为status=16 买入取消出票，买入单仍可再次申请
	 */
	private void setNeverRequestAsQuiutTicket(AirticketOrder airticketOrder)
	    throws AppException
	{
		LogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");

		Long currentTranType = airticketOrder.getTranType();
		Long groupId = airticketOrder.getOrderGroup().getId();
		if (currentTranType != null && currentTranType > 0 && groupId != null
		    && groupId > 0)
		{
			if (currentTranType == AirticketOrder.TRANTYPE__1)
			{
				List tempList = airticketOrderDAO.listByGroupIdAndTranTypeStatus(
				    groupId, AirticketOrder.TRANTYPE__2 + "", AirticketOrder.STATUS_6
				        + "");
				if (tempList != null)
				{
					for (int i = 0; i < tempList.size(); i++)
					{
						AirticketOrder tempOrder = (AirticketOrder) tempList.get(i);
						Long currentStatus = airticketOrder.getTranType();
						if (currentStatus != null && currentStatus > 0)
						{
							if (tempOrder.getStatus() == AirticketOrder.STATUS_4||tempOrder.getStatus() == AirticketOrder.STATUS_7
									||tempOrder.getStatus() == AirticketOrder.STATUS_8)
							{
								tempOrder.setStatus(AirticketOrder.STATUS_14);
								myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
								    + tempOrder.getId() + "--卖出取消出票,设置买入单不得再次申请");
							}							
							if (tempOrder.getStatus() == AirticketOrder.STATUS_6)
							{
								tempOrder.setStatus(AirticketOrder.STATUS_16);
								myLog.info(tempOrder.getOrderGroup().getNo() + "--order id:"
								    + tempOrder.getId() + "--卖出取消出票,设置买入单不得再次申请");
							}
						}
						airticketOrderDAO.update(tempOrder);
					}
				}
			}
		}
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
								tempOrder.setStatus(AirticketOrder.STATUS_16);
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
	public String editRemark(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";

		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setMemo(airticketOrderFrom.getMemo());
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

			// airticketOrder.setOperate201(uri.getUser().getUserNo());
			// airticketOrder.setOperate201Time(new
			// Timestamp(System.currentTimeMillis()));

			airticketOrderDAO.update(airticketOrder);

			updateOrderGroup(airticketOrder);

			String groupMarkNo = airticketOrder.getGroupMarkNo();

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,
			    uri.getUser(), request, TicketLog.TYPE_201);

			forwardPage = getForwardPageByOrderType(airticketOrder);
		}
		else
		{
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	/**
	 * 
	 */
	public String updateAirticketOrderStatus(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");

			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getOrderGroup().getNo();

			Long ticketLogType = null;
			if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_44)
			{// 改签未通过
				ticketLogType = TicketLog.TYPE_80;
			}
			else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41)
			{// 改签审核通过
				ticketLogType = TicketLog.TYPE_73;
			}
			else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_42)
			{// 改签审核通过
				ticketLogType = TicketLog.TYPE_73;
			}
			else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_45)
			{// 改签完成
				ticketLogType = TicketLog.TYPE_76;
			}

			airticketOrder.setStatus(airticketOrderFrom.getStatus()); // 订单状态
			airticketOrderDAO.update(airticketOrder);
			updateOrderGroup(airticketOrder);

			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,
			    uri.getUser(), request, ticketLogType);

			forwardPage = getForwardPageByOrderType(airticketOrder);
		}
		else
		{
			forwardPage = "NOERROR";
		}
		return forwardPage;
	}

	// 通过外部pnr信息创建退废票
	public String createOutRetireOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Inform inf = new Inform();

		TempPNR tempPNR = uri.getTempPNR();
		if (tempPNR != null)
		{
			Long tempStatus = null;
			Long tempTicketLogType = null;
			if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3)
			{// 3：退票
				tempStatus = AirticketOrder.STATUS_24; // 订单状态
				tempTicketLogType = TicketLog.TYPE_35;// 操作日志
			}
			else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4)
			{// 4：废票
				tempStatus = AirticketOrder.STATUS_34; // 订单状态
				tempTicketLogType = TicketLog.TYPE_51;// 操作日志 类型
			}

			// 机票订单
			AirticketOrder ao = new AirticketOrder();
			ao.setDrawPnr(tempPNR.getPnr());// 出票pnr
			ao.setSubPnr(tempPNR.getPnr());// 预订pnr
			ao.setBigPnr(tempPNR.getB_pnr());// 大pnr
			ao.setTicketPrice(tempPNR.getFare());// 票面价格
			ao.setAirportPrice(tempPNR.getTax());// 机建费
			ao.setFuelPrice(tempPNR.getYq());// 燃油税
			ao.setAgent(airticketOrderFrom.getAgent()); // 购票客户
			ao.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
			ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());// 行程单费用
			ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
			ao.setRebate(airticketOrderFrom.getRebate());// 政策
			ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号
		
			ao.setStatus(tempStatus); // 订单状态
			ao.setTicketType(AirticketOrder.TICKETTYPE_1);// 机票类型
			ao.setTranType(airticketOrderFrom.getTranType());// 交易类型
			ao.setMemo(airticketOrderFrom.getMemo());
			ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ao.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
			ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退废票原因
			ao.setEntryOperator(uri.getUser().getUserNo());
			ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
			// 设置平台公司帐号
			Long platformId = airticketOrderFrom.getPlatformId();// Long.parseLong(request.getParameter("platformId"));
			Long companyId = airticketOrderFrom.getCompanyId();// Long.parseLong(request.getParameter("companyId"));
			Long accountId = airticketOrderFrom.getAccountId();// Long.parseLong(request.getParameter("accountId"));
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
			// 创建一个新组
			OrderGroup og = saveOrderGroup(ao);
			ao.setOrderGroup(og);
			long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(og.getId());
			ao.setSubGroupMarkNo(newSubGroupNo);
			airticketOrderDAO.save(ao);
			flightPassengerBiz.saveFlightPassengerByOrderForm(airticketOrderFrom, ao);

			// 操作日志
			saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
			    tempTicketLogType);
			uri.setTempPNR(null);
			forwardPage = getForwardPageByOrderType(ao);
		}
		else
		{
			forwardPage = "NOPNR";
		}
		return forwardPage;
	}

	// 创建退废票(内部)
	public String addRetireOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		try
		{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
			{
				AirticketOrder airticketOrder = airticketOrderDAO
				    .getAirticketOrderById(airticketOrderFrom.getId());

				airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());

				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3)
				{// 3：退票
					airticketOrderFrom.setStatus(AirticketOrder.STATUS_19); // 订单状态
					airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_35);// 操作日志
				}
				else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4)
				{// 4：废票
					airticketOrderFrom.setStatus(AirticketOrder.STATUS_29); // 订单状态
					airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_51);// 操作日志
					// 类型
				}
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型

				long newSubGroupNo = airticketOrderDAO
				    .getNewSubGroupMarkNo(airticketOrder.getOrderGroup().getId());
				System.out.println("创建退废订单：" + newSubGroupNo);
				airticketOrderFrom.setSubGroupMarkNo(newSubGroupNo);// 新的退废组

				forwardPage = addRetireOrder(airticketOrderFrom, airticketOrder, uri);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			forwardPage = "ERROR";
		}
		return forwardPage;

	}

	// 创建退废票（调用方法）
	private String addRetireOrder(AirticketOrder airticketOrderFrom,
	    AirticketOrder airticketOrder, UserRightInfo uri) throws AppException
	{
		String forwardPage = "";
		BigDecimal totalAmount = airticketOrder.getTotalAmount();
		String[] passengerId = airticketOrderFrom.getPassengerIds();
		if (passengerId != null && passengerId.length > 0)
		{
			BigDecimal passengersCount = new BigDecimal(airticketOrder
			    .getTotalPerson());
			BigDecimal passengersNum = new BigDecimal(passengerId.length);
			if (totalAmount != null && passengersCount != null
			    && passengersNum != null)
			{
				System.out.println("===passengersNum" + passengersNum);
				totalAmount = totalAmount.divide(passengersCount, 2,
				    BigDecimal.ROUND_HALF_UP);
				totalAmount = totalAmount.multiply(passengersNum);
			}
		}

		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
		ao.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
		if (airticketOrder.getBigPnr() != null)
		{
			ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
		}
		if (airticketOrderFrom.getBigPnr() != null)
		{
			ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		}
		ao.setOldOrderNo(airticketOrder.getOldOrderNo());
		ao.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
		ao.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
		ao.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
		ao.setAgent(airticketOrder.getAgent()); // 购票客户
		ao.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
		ao.setDocumentPrice(airticketOrder.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrder.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrder.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号

		updateOrderGroup(airticketOrder);// 订单组编号
		ao.setOrderGroup(airticketOrder.getOrderGroup());
		ao.setSubGroupMarkNo(airticketOrderFrom.getSubGroupMarkNo());
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrder.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getTranType());// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退票原因	
		if (airticketOrderFrom.getTransRule() != null)
		{
			ao.setTransRule(airticketOrderFrom.getTransRule());// 客规(外部)
		}
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(airticketOrderFrom.getBusinessType());// 业务类型
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退废票原因
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		ao.setPlatform(airticketOrder.getPlatform());
		ao.setCompany(airticketOrder.getCompany());
		ao.setAccount(airticketOrder.getAccount());

		if (airticketOrderFrom.getTotalAmount() != null)
		{// 创建买入（第一次通过申请）
			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
		}
		else
		{// 创建小组第一条卖出退废单
			ao.setTotalAmount(airticketOrder.getTotalAmount());// 总金额
		}

		if (ao.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
		{
			ao.setOperate35(uri.getUser().getUserNo());
			ao.setOperate35Time(new Timestamp(System.currentTimeMillis()));
		}
		else if (ao.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
		{
			ao.setOperate40(uri.getUser().getUserNo());
			ao.setOperate40Time(new Timestamp(System.currentTimeMillis()));
		}

		ao.setOldOrderNo(airticketOrder.getAirOrderNo());// 原始订单号
		airticketOrderDAO.save(ao);

		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_19
		    || airticketOrderFrom.getStatus() == AirticketOrder.STATUS_29)
		{// 创建退废
			flightPassengerBiz.saveFlightPassengerByOrderForm(airticketOrderFrom, ao);
		}
		else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_21
		    || airticketOrderFrom.getStatus() == AirticketOrder.STATUS_31)
		{
			// 审核退废
			flightPassengerBiz.saveFlightPassengerBySetForOrder(ao,
			    airticketOrderFrom.getPassengers(), airticketOrderFrom.getFlights());
		}

		// 操作日志
		saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
		    airticketOrderFrom.getTicketLog().getType());

		forwardPage = getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 审核退废票(卖出单，创建买入退废单)
	public String auditRetire(AirticketOrder form, UserRightInfo uri)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());

		AirticketOrder ao = airticketOrderDAO.getDrawedAirticketOrderByGroupId(
				form.getGroupId(), AirticketOrder.TRANTYPE__2);

		form.setDrawPnr(ao.getDrawPnr());
		if (form.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 3：退票
			form.setStatus(AirticketOrder.STATUS_21); // 订单状态
			form.getTicketLog().setType(TicketLog.TYPE_40);// 操作日志
			form.setTranType(AirticketOrder.TRANTYPE_3);
			airticketOrder.setStatus(AirticketOrder.STATUS_20);
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE_4)
		{// 4：废票
			form.setStatus(AirticketOrder.STATUS_31); // 订单状态
			form.getTicketLog().setType(TicketLog.TYPE_52);// 操作日志
			form.setTranType(AirticketOrder.TRANTYPE_4);
			airticketOrder.setStatus(AirticketOrder.STATUS_30);
		}
		if (form.getTransRule() != null)
		{
			airticketOrder.setTransRule(form.getTransRule());// 客规百分比
		}

		airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		airticketOrderDAO.update(airticketOrder);
		updateOrderGroup(airticketOrder);

		form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

		// ------------------------
		form.setSubGroupMarkNo(airticketOrder.getSubGroupMarkNo());
		form.setPassengers(airticketOrder.getPassengers());
		form.setFlights(airticketOrder.getFlights());
		addRetireOrder(form, ao, uri);
		// ------------------------

		forwardPage = getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 审核退废票（卖出单，第二次通过申请，更新卖出）
	public String auditRetire2(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		AirticketOrder airticketOrder = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderFrom.getId());
		String groupMarkNo = airticketOrder.getOrderGroup().getNo();
		airticketOrder.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 票号
		airticketOrder.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
		airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());

		Long currTicketType = null;
		if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 3：退票
			airticketOrder.setStatus(AirticketOrder.STATUS_21);
			currTicketType = TicketLog.TYPE_41;// 操作日志 类型
			airticketOrder.setOperate41(uri.getUser().getUserNo());
			airticketOrder.setOperate41Time(new Timestamp(System.currentTimeMillis()));
		}
		else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_4)
		{// 4：废票
			airticketOrder.setStatus(AirticketOrder.STATUS_31);
			currTicketType = TicketLog.TYPE_53;// 操作日志类型
			airticketOrder.setOperate53(uri.getUser().getUserNo());
			airticketOrder
			    .setOperate53Time(new Timestamp(System.currentTimeMillis()));
		}
		airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

		airticketOrderDAO.update(airticketOrder);
		updateOrderGroup(airticketOrder);

		saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo, uri.getUser(),
		    request, currTicketType);
		return forwardPage;
	}

	// 审核退废1 外部
	public String auditOutRetire(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		AirticketOrder airticketOrder = getAirticketOrderById(form.getId());

		if (airticketOrder != null)
		{
			form.setTicketType(AirticketOrder.TICKETTYPE_1);
			form.setDrawPnr(airticketOrder.getDrawPnr());// 出票pnr
			form.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
			form.setBigPnr(airticketOrder.getBigPnr());// 大pnr
			form.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
			form.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
			form.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
		}
		form.setHandlingCharge(form.getHandlingCharge());// 手续费
		form.setTotalAmount(form.getTotalAmount());// 设置交易金额

		if (form.getTranType() == AirticketOrder.TRANTYPE_3)
		{// 3：退票
			form.setStatus(AirticketOrder.STATUS_21); // 订单状态
			form.getTicketLog().setType(TicketLog.TYPE_40);// 操作日志
			form.setTranType(AirticketOrder.TRANTYPE_3);
			airticketOrder.setStatus(AirticketOrder.STATUS_25);
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE_4)
		{// 4：废票
			form.setStatus(AirticketOrder.STATUS_31); // 订单状态
			form.getTicketLog().setType(TicketLog.TYPE_52);// 操作日志
			form.setTranType(AirticketOrder.TRANTYPE_4);
			airticketOrder.setStatus(AirticketOrder.STATUS_35);
		}		
		
		airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		airticketOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));//录入时间
		airticketOrder.setEntryOperator(uri.getUser().getUserNo());//录入人
		
		update(airticketOrder);
		updateOrderGroup(airticketOrder);

		form.getTicketLog().setSysUser(uri.getUser());// 日志操作员
		form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
		String platformId = request.getParameter("platformId12");
		String companyId = request.getParameter("companyId12");
		String accountId = request.getParameter("accountId12");
		form.setPlatform(PlatComAccountStore.getPlatformById(Long.parseLong(platformId)));
		form.setCompany(PlatComAccountStore.getCompanyById(Long.parseLong(companyId)));
		form.setAccount(PlatComAccountStore.getAccountById(Long.parseLong(accountId)));

		form.setSubGroupMarkNo(airticketOrder.getSubGroupMarkNo());
		form.setPassengers(airticketOrder.getPassengers());
		form.setFlights(airticketOrder.getFlights());

		addRetireOrder(form, airticketOrder, uri);

		forwardPage = getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 审核退废2 外部
	public String auditOutRetire2(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");

		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			AirticketOrder airticketOrder = getAirticketOrderById(airticketOrderFrom
			    .getId());
			airticketOrder.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 票号
			airticketOrder.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
			airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());// 设置交易金额totalAmount

			Long currTicketType = null;
			if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3)
			{// 3：退票
				airticketOrder.setStatus(AirticketOrder.STATUS_21);
				currTicketType = TicketLog.TYPE_41;// 操作日志 类型
				airticketOrder.setOperate41(uri.getUser().getUserNo());
				airticketOrder.setOperate41Time(new Timestamp(System
				    .currentTimeMillis()));
			}
			else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_4)
			{// 4：废票
				airticketOrder.setStatus(AirticketOrder.STATUS_31);
				currTicketType = TicketLog.TYPE_53;// 操作日志 类型
				airticketOrder.setOperate53(uri.getUser().getUserNo());
				airticketOrder.setOperate53Time(new Timestamp(System
				    .currentTimeMillis()));
			}

			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			update(airticketOrder);
			updateOrderGroup(airticketOrder);

			saveAirticketTicketLog(airticketOrder.getId(), airticketOrder
			    .getOrderGroup().getNo(), uri.getUser(), request, currTicketType);
			return airticketOrder.getOrderGroup().getId() + "";
		}
		else
		{
			return "NOORDER";
		}
	}

	// 通过外部pnr信息创建改签票
	public String createOutUmbuchenOrder(AirticketOrder airticketOrderFrom,
	    TempPNR tempPNR, AirticketOrder airticketOrder, UserRightInfo uri)
	    throws AppException
	{
		String forwardPage = "";
		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
		ao.setSubPnr(tempPNR.getPnr());// 预订pnr
		ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		ao.setUmbuchenPnr(airticketOrderFrom.getUmbuchenPnr());// 改签pnr
		ao.setTicketPrice(airticketOrderFrom.getTicketPrice());// 票面价格
		ao.setAirportPrice(airticketOrderFrom.getAirportPrice());// 机建费
		ao.setFuelPrice(airticketOrderFrom.getFuelPrice());// 燃油税
		ao.setAgent(airticketOrderFrom.getAgent()); // 购票客户
		ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrderFrom.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号
		if (airticketOrder != null && airticketOrder.getGroupMarkNo() != null
		    && !"".equals(airticketOrder.getGroupMarkNo().trim()))
		{
			updateOrderGroup(ao);
			ao.setOrderGroup(airticketOrder.getOrderGroup());
		}
		else
		{
			OrderGroup og = this.saveOrderGroup(ao);
			ao.setOrderGroup(og);
		}
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrderFrom.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getTranType());// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());			
		ao.setBusinessType(airticketOrderFrom.getBusinessType());// 业务类型
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退废票原因
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setEntryOperator(uri.getUser().getUserNo());//录入人	
		
		// 设置平台公司帐号
		Long platformId = airticketOrderFrom.getPlatformId();
		Long companyId = airticketOrderFrom.getCompanyId();
		Long accountId = airticketOrderFrom.getAccountId();
		if (platformId != null && companyId != null && accountId != null)
		{
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
		}
		ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额

		save(ao);

		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_46)
		{
			flightPassengerBiz.saveFlightPassengerByOrderForm(airticketOrderFrom, ao);
			// 审核改签 并且创建（ 收款订单） 外部
		}
		else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41)
		{
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);
		}
		// 操作日志
		saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
		    TicketLog.TYPE_71);

		forwardPage = getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 创建改签票
	public String createUmbuchenOrder(AirticketOrder airticketOrderFrom,
	    AirticketOrder airticketOrder, UserRightInfo uri) throws AppException
	{
		String forwardPage = "";
		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
		ao.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
		ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		ao.setUmbuchenPnr(airticketOrderFrom.getUmbuchenPnr());// 改签pnr
		ao.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
		ao.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
		ao.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
		ao.setAgent(airticketOrder.getAgent()); // 购票客户
		ao.setDocumentPrice(airticketOrder.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrder.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrder.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号

		updateOrderGroup(airticketOrder);// 订单组编号
		ao.setOrderGroup(airticketOrder.getOrderGroup());
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(airticketOrder
		    .getOrderGroup().getId());
		ao.setSubGroupMarkNo(newSubGroupNo);
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrder.getTicketType());// 机票类型
		ao.setTranType(AirticketOrder.TRANTYPE_5);// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(airticketOrderFrom.getBusinessType());// 业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		Long platformId = airticketOrderFrom.getPlatformId();
		Long companyId = airticketOrderFrom.getCompanyId();
		Long accountId = airticketOrderFrom.getAccountId();
		ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
		ao.setCompany(PlatComAccountStore.getCompanyById(companyId));
		ao.setAccount(PlatComAccountStore.getAccountById(accountId));

		ao.setTotalAmount(airticketOrder.getTotalAmount());// 总金额
		airticketOrderDAO.save(ao);

		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_39)
		{
			flightPassengerBiz.saveFlightPassengerByOrderForm(airticketOrderFrom, ao);
		}
		else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41)
		{
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);
		}
		// 操作日志
		saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
		    TicketLog.TYPE_71);

		forwardPage = getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	public String createB2COrder(HttpServletRequest request,
	    AirticketOrder airticketOrderForm) throws AppException
	{
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		TempPNR tempPNR = uri.getTempPNR();

		if (tempPNR != null)
		{
			airticketOrderForm.setStatus(AirticketOrder.STATUS_1); // 订单状态
			airticketOrderForm.setTicketType(AirticketOrder.TICKETTYPE_3); // 设置机票类型
			airticketOrderForm.getTicketLog().setType(TicketLog.TYPE_301);// 操作日志

			// 创建订单
			forwardPage = createPNR(airticketOrderForm, tempPNR, uri);
			uri.setTempPNR(null);
		}
		else
		{
			inf.setMessage("tempPNR不能为空！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		return forwardPage;
	}

	/**
	 * 通过 信息PNR获取外部数据
	 */
	public String airticketOrderByBlackOutPNR(HttpServletRequest request,
	    AirticketOrder airticketOrderFrom) throws AppException
	{
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String pnrInfo = request.getParameter("pnrInfo");

		if (pnrInfo != null && !"".equals(pnrInfo.trim()))
		{
			TempPNR tempPNR = getTempPNRByBlackInfo(pnrInfo);
			if (tempPNR != null)
			{
				// 设置临时会话
				airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
				uri.setTempPNR(tempPNR);

				AirticketOrder order = new AirticketOrder();
				order.setAddType("OutPNR");// 设置添加类型
				order.setDrawPnr(tempPNR.getPnr());// 出票pnr
				order.setSubPnr(tempPNR.getPnr());// 预订pnr
				order.setBigPnr(tempPNR.getB_pnr());// 大pnr
				order.setTicketPrice(tempPNR.getFare());// 票面价格
				order.setAirportPrice(tempPNR.getTax());// 机建费
				order.setFuelPrice(tempPNR.getYq());// 燃油税
				
				flightPassengerBiz.saveFlightPassengerInOrderByTempPNR(tempPNR, order);
				
				
				request.setAttribute("airticketOrder", order);
				
				forwardPage=airticketOrderFrom.getForwardPage();
				System.out.println("===forwardPage===="+forwardPage);
				return forwardPage;
			}
			else
			{
				inf.setMessage("PNR解析错误");
				inf.setBack(true);
				forwardPage = "inform";
				request.setAttribute("inf", inf);
			}
		}
		else
		{
			inf.setMessage("PNR 不能为空！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
		}
		return forwardPage;
	}

	/***************************************************************************
	 * 确认退票/废票/ 收、退款
	 **************************************************************************/
	public String collectionRetire(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(airticketOrderFrom.getId());

			long businessType = airticketOrder.getBusinessType();
			Long ticketLogType = null;
			Long orderStatus = null;

			airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());

			if (businessType == 2
			    && airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3)
			{// 退票收退款
				orderStatus = AirticketOrder.STATUS_22;
				ticketLogType = TicketLog.TYPE_42;
				airticketOrder.setOperate42(uri.getUser().getUserNo());
				airticketOrder.setOperate42Time(new Timestamp(System
				    .currentTimeMillis()));

				airticketOrder.setStatus(orderStatus);
				airticketOrderDAO.update(airticketOrder);
				updateOrderGroup(airticketOrder);
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.type_1, Statement.STATUS_1, airticketOrder.getOptTime());
			}
			else if (businessType == 1
			    && airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3)
			{// 退票付退款
				orderStatus = AirticketOrder.STATUS_22;
				ticketLogType = TicketLog.TYPE_43;
				airticketOrder.setOperate43(uri.getUser().getUserNo());
				airticketOrder.setOperate43Time(new Timestamp(System
				    .currentTimeMillis()));

				airticketOrder.setStatus(orderStatus);
				airticketOrderDAO.update(airticketOrder);
				updateOrderGroup(airticketOrder);
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.type_2, Statement.STATUS_1, airticketOrder.getOptTime());
			}
			else if (businessType == 2
			    && airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4)
			{// 废票收退款
				orderStatus = AirticketOrder.STATUS_32;
				ticketLogType = TicketLog.TYPE_54;
				airticketOrder.setOperate54(uri.getUser().getUserNo());
				airticketOrder.setOperate54Time(new Timestamp(System
				    .currentTimeMillis()));
				airticketOrder.setStatus(orderStatus);
				airticketOrderDAO.update(airticketOrder);
				updateOrderGroup(airticketOrder);
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.type_1, Statement.STATUS_1, airticketOrder.getOptTime());
			}
			else if (businessType == 1
			    && airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4)
			{// 废票付退款
				orderStatus = AirticketOrder.STATUS_32;
				ticketLogType = TicketLog.TYPE_55;
				airticketOrder.setOperate55(uri.getUser().getUserNo());
				airticketOrder.setOperate55Time(new Timestamp(System
				    .currentTimeMillis()));
				airticketOrder.setStatus(orderStatus);
				airticketOrderDAO.update(airticketOrder);

				updateOrderGroup(airticketOrder);

				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.type_2, Statement.STATUS_1, airticketOrder.getOptTime());
			}
			else
			{
				return "ERROR";
			}

			saveAirticketTicketLog(airticketOrder.getId(), airticketOrder
			    .getGroupMarkNo(), uri.getUser(), request, ticketLogType);

			forwardPage = AirticketOrder.ORDER_GROUP_TYPE3 + "";
		}
		else
		{
			forwardPage = "NOORDER";
		}
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
			{// 卖出 收款
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.type_2, Statement.STATUS_1, airticketOrder.getOptTime());
			}
			else if (businessType == 2)
			{// 买入 付款
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),
				    Statement.type_1, Statement.STATUS_1, airticketOrder.getOptTime());
			}
			else
			{
				return "ERROR";
			}
			saveAirticketTicketLog(airticketOrder.getId(), groupMarkNo,
			    uri.getUser(), request, TicketLog.TYPE_76);

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
		String platformId = request.getParameter("platformId");
		String companyId = request.getParameter("companyId");
		String accountId = request.getParameter("accountId");

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
		ao.setPlatform(PlatComAccountStore
		    .getPlatformById(Long.valueOf(platformId)));
		ao.setCompany(PlatComAccountStore.getCompanyById(Long.valueOf(companyId)));
		ao.setAccount(PlatComAccountStore.getAccountById(Long.valueOf(accountId)));
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
		saveStatementByAirticketOrder(ao, uri.getUser(), Statement.type_1,
		    Statement.STATUS_1, entryTime);

		// 操作日志
		saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(), uri.getUser(),
		    request, ticketLogType);

		forwardPage = getForwardPageByOrderType(ao);
		return forwardPage;

	}

	// 跳转到编辑页面
	public void editOrder(AirticketOrderListForm ulf,
	    HttpServletRequest request) throws AppException
	{
		if (ulf == null)
		{
			ulf = new AirticketOrderListForm();
		}
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		ulf.setUri(uri);

		Long airtickeOrderId = ulf.getId();
		if (airtickeOrderId != null && airtickeOrderId > 0)
		{
			AirticketOrder order = getAirticketOrderById(airtickeOrderId);
			List groupOrderList = airticketOrderDAO.listBySubGroupByAndGroupId(order
			    .getOrderGroup().getId(), order.getSubGroupMarkNo());
			ulf.setList(groupOrderList);

			// System.out.println("ulf.list totalRowCount:"+ulf.getListSize());

			request.setAttribute("airticketOrder", order);
		}
		else
		{
			ulf.setList(new ArrayList());
		}

		request.setAttribute("airticketOrderList", ulf);
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
		ulf.setUri(uri);
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
	public String editOrder(AirticketOrder airticketOrderFrom,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";

		if (airticketOrderFrom != null)
		{
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
			    "URI");
			String[] airticketOrderIds = request
			    .getParameterValues("airticketOrderIds");
			String[] totalAmount = request.getParameterValues("totalAmount");

			String[] platformId = request.getParameterValues("platformId");
			String[] companyId = request.getParameterValues("companyId");
			String[] accountId = request.getParameterValues("accountId");

			String[] entryOrderDate = request.getParameterValues("entryOrderDate");

			for (int i = 0; i < airticketOrderIds.length; i++)
			{
				if (Long.valueOf(airticketOrderIds[i]) > 0)
				{
					System.out.println("airticket order id:" + airticketOrderIds[i]);
					AirticketOrder ao = airticketOrderDAO.getAirticketOrderById(Long
					    .valueOf(airticketOrderIds[i]));
					String[] drawPnr = request.getParameterValues("drawPnr");
					String[] subPnr = request.getParameterValues("subPnr");
					String[] bigPnr = request.getParameterValues("bigPnr");
					String[] airportPrice = request.getParameterValues("airportPrice");
					String[] fuelPrice = request.getParameterValues("fuelPrice");
					String[] ticketPrice = request.getParameterValues("ticketPrice");
					String[] handlingCharge = request
					    .getParameterValues("handlingCharge");
					String[] rebate = request.getParameterValues("rebate");
					String[] airOrderNo = request.getParameterValues("airOrderNo");
					// String[] ticketType = request.getParameterValues("ticketType");

					// 机票订单
					ao.setDrawPnr(drawPnr[i]);// 出票pnr
					ao.setSubPnr(subPnr[i]);// 预订pnr
					ao.setBigPnr(bigPnr[i]);// 大pnr
					ao.setTicketPrice(new BigDecimal(ticketPrice[i]));// 票面价格
					ao.setAirportPrice(new BigDecimal(airportPrice[i]));// 机建费
					ao.setFuelPrice(new BigDecimal(fuelPrice[i]));// 燃油税
					// ao.setHandlingCharge(new BigDecimal(handlingCharge[i]));// 手续费
					// ao.setDocumentPrice(new BigDecimal(0));// 行程单费用
					// ao.setInsurancePrice(new BigDecimal(0));// 保险费
					ao.setRebate(new BigDecimal(rebate[i]));// 政策
					ao.setAirOrderNo(airOrderNo[i]);// 机票订单号
					ao.setMemo(airticketOrderFrom.getMemo());
					if (entryOrderDate[i] == null || "".equals(entryOrderDate))
					{
						ao.setEntryTime(new Timestamp(System.currentTimeMillis()));
					}
					else
					{
						ao.setEntryTime(DateUtil.getTimestamp(entryOrderDate[i],
						    "yyyy-MM-dd HH:mm:ss"));
					}

					ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间

					// 设置平台帐号
					ao.setPlatform(PlatComAccountStore.getPlatformById(Long
					    .valueOf(platformId[i])));
					ao.setCompany(PlatComAccountStore.getCompanyById(Long
					    .valueOf(companyId[i])));
					ao.setAccount(PlatComAccountStore.getAccountById(Long
					    .valueOf(accountId[i])));

					ao.setTotalAmount(new BigDecimal(totalAmount[i]));

					updateOrderGroup(ao);

					airticketOrderDAO.update(ao);

					flightPassengerBiz.updateFlightPassengerByRequest(request, ao);

					// 操作日志
					saveAirticketTicketLog(ao.getId(), ao.getGroupMarkNo(),
					    uri.getUser(), request, TicketLog.TYPE_202);

					forwardPage = ao.getId() + "";
				}
				else
				{
					forwardPage = "NOORDER";
				}
			}
		}
		else
		{
			forwardPage = "NOORDER";
		}

		return forwardPage;
	}

	// 申请支付
	public void applyTeamPayment(Long airticketOrderId,HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);		
	
		airticketOrder.setStatus(AirticketOrder.STATUS_102);// 状态：申请成功，等待支付

		airticketOrderDAO.update(airticketOrder);

		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_102);
	
	}
	
	// 团队确认支付
	public void confirmTeamPayment(AirticketOrder form,HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		long airticketOrderId = form.getId();
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);

		airticketOrder.setStatus(AirticketOrder.STATUS_103);// 支付成功，等待出票
		airticketOrder.setPayOperator(uri.getUser().getUserNo());
		
		if (form.getOptTime()!=null&&"".equals(form.getOptTime())==false) {
			airticketOrder.setPayTime(form.getOptTime());// 付款时间
		}else{
			airticketOrder.setPayTime(new Timestamp(System.currentTimeMillis()));// 付款时间
		}
		
		airticketOrder.setMemo(form.getMemo());// 备注
		airticketOrder.setAccount(accountDAO.getAccountByid(form.getAccountId()));
		airticketOrder.setTotalAmount(form.getTotalAmount());		
		
		airticketOrderDAO.update(airticketOrder);

		
		saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_2, Statement.STATUS_1);

		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_102);
	}	

	// 确认出票
	public void ticketTeam(Long id,Long groupId, HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession() .getAttribute("URI");		
		
		List<AirticketOrder> airticketList = airticketOrderDAO.listByGroupId(groupId);
		AirticketOrder airticketOrder = new AirticketOrder();
		for (int i = 0; i < airticketList.size(); i++)
		{
			airticketOrder = airticketList.get(i);
			if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE__2)
			{// 买入(采购)
				airticketOrder.setStatus(AirticketOrder.STATUS_105);// 状态：出票成功，交易结束
				airticketOrderDAO.update(airticketOrder);
			}
			if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE__1)
			{ // 卖出(销售)
				airticketOrder.setStatus(AirticketOrder.STATUS_105);// 状态：出票成功，交易结束
				airticketOrderDAO.update(airticketOrder);
			}
		}
		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_105);
	}

	// 申请退票
	public void applyTeamRefund(Long airticketOrderId,HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession() .getAttribute("URI");
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);

		airticketOrder.setStatus(AirticketOrder.STATUS_108);//退票审核通过，等待退款

		airticketOrderDAO.update(airticketOrder);

		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_108);
	}


	// 团队退票，确认付退款
	public void confirmTeamRefundPayment(AirticketOrder form,HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());

		airticketOrder.setIncomeretreatCharge(form.getIncomeretreatCharge());// 收退票手续费
		airticketOrder.setStatus(AirticketOrder.STATUS_109);// 状态：已经退款，交易结束
		
		airticketOrder.setPayOperator(uri.getUser().getUserNo());
		
		if (form.getOptTime()!=null&&"".equals(form.getOptTime())==false) {
			airticketOrder.setOptTime(form.getOptTime());// 收付款时间
		}else{
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));
		}
		airticketOrder.setMemo(form.getMemo());// 备注
		
		airticketOrder.setAccount(accountDAO.getAccountByid(form.getAccountId()));
		airticketOrder.setTotalAmount(airticketOrder.getIncomeretreatCharge());
		airticketOrderDAO.update(airticketOrder);	
		saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_2, Statement.STATUS_1);

		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_109);
	}

	// 团队退票，卖出 确认收款
	public void confirmTeamRefundCollection(AirticketOrder form,HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());

		airticketOrder.setIncomeretreatCharge(form.getIncomeretreatCharge());// 收退票手续费
		airticketOrder.setStatus(AirticketOrder.STATUS_109);// 状态：已经退款，交易结束
		
		airticketOrder.setPayOperator(uri.getUser().getUserNo());
		if (form.getOptTime()!=null&&"".equals(form.getOptTime())==false) {
			airticketOrder.setPayTime(form.getOptTime());// 收付款时间
		}else{
			airticketOrder.setPayTime(new Timestamp(System.currentTimeMillis()));
		}		
		airticketOrder.setMemo(form.getMemo());// 备注
		
		airticketOrder.setAccount(accountDAO.getAccountByid(form.getAccountId()));
		airticketOrder.setTotalAmount(airticketOrder.getIncomeretreatCharge());
		airticketOrderDAO.update(airticketOrder);		
		
		saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_1, Statement.STATUS_1);

		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_110);
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

			List<AirticketOrder> airticketOrderList = airticketOrderDAO.listByGroupId(airticketOrder.getOrderGroup().getId());

			AirticketOrder buyerOrder = new AirticketOrder();
			if (airticketOrderList.size() > 1)
			{
				for (int i = 0; i < airticketOrderList.size(); i++)
				{
					AirticketOrder tempOrder = airticketOrderList.get(i);

					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__1)
					{// 卖出
						airticketOrder = tempOrder;
					}
					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE__2)
					{// 买入
						buyerOrder = tempOrder;
					}					
					if (tempOrder.getTranType() == AirticketOrder.TRANTYPE_3)
					{// 退票
						if (tempOrder.getBusinessType()==AirticketOrder.BUSINESSTYPE__1) {
							airticketOrder = tempOrder;
						}else if (tempOrder.getBusinessType()==AirticketOrder.BUSINESSTYPE__2) {
							buyerOrder = tempOrder;
						}	
					}
				}
			}

			request.setAttribute("buyerOrder", buyerOrder);
			request.setAttribute("airticketOrder", airticketOrder);
			request.setAttribute("airticketOrderSize", airticketOrderList.size());
			request.setAttribute("teamProfit",new TeamProfit(airticketOrder,buyerOrder));
		}
	}


	// 保存团队订单信息
	public long updateTeamAirticketOrder(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		AirticketOrder airticketOrder = new AirticketOrder();
		AirticketOrder tempOrder = new AirticketOrder();
		if (form.getId() > 0)
		{
			airticketOrder = airticketOrderDAO.getAirticketOrderById(form.getId());

			if (airticketOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
			{
				// 更新卖出单
				if (form.getTranType()==AirticketOrder.TRANTYPE__1||form.getTranType()==AirticketOrder.TRANTYPE__2) {
					form.setTranType(AirticketOrder.TRANTYPE__1);
				}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
					form.setTranType(AirticketOrder.TRANTYPE_3);
				}				
				form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
				airticketOrder = fillAirticketOrderByAirticketOrderForm(form, airticketOrder);
				Agent agent = agentDAO.getAgentByid(form.getAgentId());
				airticketOrder.setAgent(agent);
				updateOrderGroup(airticketOrder);
				airticketOrderDAO.update(airticketOrder);

				// 找到买入单
				List<AirticketOrder> airticketOrderList =new ArrayList<AirticketOrder>();
				
				if(form.getTranType()==AirticketOrder.TRANTYPE__1){
					airticketOrderList=airticketOrderDAO.listByGroupIdAndBusinessTranType(airticketOrder.getOrderGroup().getId(),
							AirticketOrder.TRANTYPE__2+"",AirticketOrder.BUSINESSTYPE__2 + "");
				}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
					airticketOrderList=airticketOrderDAO.listByGroupIdAndBusinessTranType(airticketOrder.getOrderGroup().getId(),
							AirticketOrder.TRANTYPE_3+"",AirticketOrder.BUSINESSTYPE__2 + "");
				}
				

				if (airticketOrderList.size() > 0)
				{
					tempOrder = airticketOrderList.get(0);

					if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2)
					{
						if (form.getTranType()==AirticketOrder.TRANTYPE__1||form.getTranType()==AirticketOrder.TRANTYPE__2) {
							form.setTranType(AirticketOrder.TRANTYPE__2);
						}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
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
				if (form.getTranType()==AirticketOrder.TRANTYPE__1||form.getTranType()==AirticketOrder.TRANTYPE__2) {
					form.setTranType(AirticketOrder.TRANTYPE__2);
				}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
					form.setTranType(AirticketOrder.TRANTYPE_3);
				}					
				form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
				airticketOrder = fillAirticketOrderByAirticketOrderForm(form, airticketOrder);

				//更新操作时间
				airticketOrder.setAgent(null);
				updateOrderGroup(airticketOrder);
				airticketOrderDAO.update(airticketOrder);

				// 找到卖出单
				List<AirticketOrder> airticketOrderList = airticketOrderDAO.listByGroupIdAndBusinessTranType(airticketOrder.getOrderGroup().getId(),
						form.getTranType()+"",AirticketOrder.BUSINESSTYPE__1 + "");
				
				if(form.getTranType()==AirticketOrder.TRANTYPE__2){
					airticketOrderList=airticketOrderDAO.listByGroupIdAndBusinessTranType(airticketOrder.getOrderGroup().getId(),
							AirticketOrder.TRANTYPE__1+"",AirticketOrder.BUSINESSTYPE__1 + "");
				}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
					airticketOrderList=airticketOrderDAO.listByGroupIdAndBusinessTranType(airticketOrder.getOrderGroup().getId(),
							AirticketOrder.TRANTYPE_3+"",AirticketOrder.BUSINESSTYPE__1 + "");
				}
				
				if (airticketOrderList.size() > 0)
				{
					tempOrder = airticketOrderList.get(0);

					if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
					{
						if (form.getTranType()==AirticketOrder.TRANTYPE__1||form.getTranType()==AirticketOrder.TRANTYPE__2) {
							form.setTranType(AirticketOrder.TRANTYPE__1);
						}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
							form.setTranType(AirticketOrder.TRANTYPE_3);
						}						
						form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
						tempOrder = fillAirticketOrderByAirticketOrderForm(form, tempOrder);
						tempOrder.setAgent(agentDAO.getAgentByid(form.getAgentId()));
						updateOrderGroup(tempOrder);
						airticketOrderDAO.update(tempOrder);
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
			
			airticketOrder = new AirticketOrder();			
			if (form.getTranType()==AirticketOrder.TRANTYPE__1||form.getTranType()==AirticketOrder.TRANTYPE__2) {
				form.setTranType(AirticketOrder.TRANTYPE__1);
			}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
				form.setTranType(AirticketOrder.TRANTYPE_3);
			}
			
			form.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
			airticketOrder = fillAirticketOrderByAirticketOrderForm(form, airticketOrder);
			airticketOrder.setTicketType(AirticketOrder.TICKETTYPE_2);
			OrderGroup og = this.saveOrderGroup(airticketOrder);
			airticketOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
			airticketOrder.setEntryOperator(uri.getUser().getUserNo());
			
			if (form.getTranType()==AirticketOrder.TRANTYPE__1) {
				airticketOrder.setStatus(AirticketOrder.STATUS_101);
			}
			if (form.getTranType()==AirticketOrder.TRANTYPE_3) {
				airticketOrder.setStatus(AirticketOrder.STATUS_107);
			}
			
			airticketOrder.setTotalAmount(new BigDecimal("0"));
			airticketOrder.setAgent(agentDAO.getAgentByid(form.getAgentId()));
			airticketOrder.setOrderGroup(og);
			System.out.println("order--"+airticketOrder.getTranType()+"--status:"+airticketOrder.getStatus());
			// 创建卖出单
			updateOrderGroup(airticketOrder);
			airticketOrderDAO.update(airticketOrder);

			// 创建买入单
			tempOrder = new AirticketOrder();
			
			if (form.getTranType()==AirticketOrder.TRANTYPE__1||form.getTranType()==AirticketOrder.TRANTYPE__2) {
				form.setTranType(AirticketOrder.TRANTYPE__2);
			}else if(form.getTranType()==AirticketOrder.TRANTYPE_3){
				form.setTranType(AirticketOrder.TRANTYPE_3);
			}			
			form.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
			tempOrder = fillAirticketOrderByAirticketOrderForm(form,tempOrder);
			tempOrder.setTicketType(AirticketOrder.TICKETTYPE_2);
			
			if (form.getTranType()==AirticketOrder.TRANTYPE__2) {
				tempOrder.setStatus(AirticketOrder.STATUS_101);
			}
			if (form.getTranType()==AirticketOrder.TRANTYPE_3) {
				tempOrder.setStatus(AirticketOrder.STATUS_107);
			}			
			tempOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
			tempOrder.setEntryOperator(uri.getUser().getUserNo());
			tempOrder.setAgent(null);
			
			tempOrder.setOrderGroup(og);
			
		//	System.out.println("order--"+tempOrder.getTranType()+"--status:"+tempOrder.getStatus());
			updateOrderGroup(tempOrder);
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
			flight.setBoardingTime(DateUtil.getTimestamp(form.getBoardingTimes()[i].toString(), "yyyy-MM-dd"));
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

		airticketOrderDAO.update(airticketOrder);
		airticketOrderDAO.update(tempOrder);
		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(), airticketOrder.getGroupMarkNo(), uri.getUser(), request, TicketLog.TYPE_93);

		return airticketOrder.getId();

	}

	private AirticketOrder fillAirticketOrderByAirticketOrderForm(
	    AirticketOrder form, AirticketOrder airticketOrder)
	    throws AppException
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
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE__2)
		{// 买入
			airticketOrder.setTranType(AirticketOrder.TRANTYPE__2);// 交易类型
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
			airticketOrder.setAirOrderNo(form.getAirOrderNo());
			airticketOrder.setTotalAmount(form.getTotalAmount());// 交易金额
			airticketOrder.setAgent(null);
		}
		if (form.getTranType() == AirticketOrder.TRANTYPE_3 &&  form.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
		{// 退票销售
			airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
			airticketOrder.setTeamaddPrice(form.getTeamaddPrice());// 团队加价
			airticketOrder.setAgentaddPrice(form.getAgentaddPrice());// 客户加价
		}
		else if (form.getTranType() == AirticketOrder.TRANTYPE_3 && form.getBusinessType() == AirticketOrder.TRANTYPE__2)
		{// 退票买入
			airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);// 交易类型
			airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
			airticketOrder.setAirOrderNo(form.getAirOrderNo());
			airticketOrder.setTotalAmount(form.getTotalAmount());// 交易金额
			airticketOrder.setAgent(null);
		}		
		return airticketOrder;
	}
	
	
	/**
	 * 团队订单 编辑利润统计
	 */
	public void editTeamProfit(AirticketOrder form,
	    HttpServletRequest request) throws AppException
	{
		Long id = form.getId();
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(id);
		Long groupId=airticketOrder.getOrderGroup().getId();
		List<AirticketOrder> airticketOrderList = airticketOrderDAO.listByGroupId(groupId);
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
				if(tempOrder.getBusinessType()==AirticketOrder.BUSINESSTYPE__1){
					updateSaleOrderAsProfit(tempOrder, form);
				}else if(tempOrder.getBusinessType()==AirticketOrder.BUSINESSTYPE__2){
					updateBuyOrderAsProfit(tempOrder, form);
				}		
			}
		}
	}
	
	private void updateSaleOrderAsProfit(AirticketOrder order,AirticketOrder form)throws AppException{
		order.setOverTicketPrice(form.getSaleOverTicketPrice());//多收票价
		order.setOverAirportfulePrice(form.getSaleOverAirportfulePrice());//多收税
		order.setCommissonCount(form.getSaleCommissonCount());//返点
		order.setRakeOff(form.getSaleRakeOff());//后返
		order.setMemo(form.getSaleMemo());//备注
		order.setIncomeretreatCharge(form.getSaleIncomeretreatCharge());//收退票手续费
		if (order.getStatus()==AirticketOrder.STATUS_101) {
			order.setStatus(AirticketOrder.STATUS_111);
		}
		order.setTotalAmount(form.getSaleTotalAmount());
		update(order);
		updateOrderGroup(order);
	}
	
	private void updateBuyOrderAsProfit(AirticketOrder order,AirticketOrder form)throws AppException{
		order.setHandlingCharge(form.getBuyHandlingCharge());//手续费
		order.setCommissonCount(form.getBuyCommissonCount());//返点
		order.setRakeoffCount(form.getBuyRakeoffCount());//后返
		order.setIncomeretreatCharge(form.getBuyIncomeretreatCharge());//收退票手续费
		if (order.getStatus()==AirticketOrder.STATUS_101) {
			order.setStatus(AirticketOrder.STATUS_111);
		}
		
		update(order);
		updateOrderGroup(order);
	}
	
	//团队--根据现有销售订单,创建退票订单
	public void createTeamRefundBySale(	AirticketOrder orderForm,HttpServletRequest request) throws AppException {
		long groupId=orderForm.getGroupId();
		List orderList=listByGroupIdAndTranType(groupId, AirticketOrder.TRANTYPE__1+","+AirticketOrder.TRANTYPE__2);
		
		AirticketOrder oldSaleOrder=new AirticketOrder();
		AirticketOrder oldBuyOrder=new AirticketOrder();	
		
		for (int i = 0; i < orderList.size(); i++) {
			AirticketOrder tempOrder=(AirticketOrder)orderList.get(i);
			long tranType=tempOrder.getTranType();
			if(tranType==AirticketOrder.TRANTYPE__1){
				oldSaleOrder=tempOrder;
			}else if(tranType==AirticketOrder.TRANTYPE__2){
				oldBuyOrder=tempOrder;
			}
		}		
		
		AirticketOrder saleOrder=new AirticketOrder();
		AirticketOrder buyOrder=new AirticketOrder();	
		
		fillTeamRefundOrderList(orderForm,oldSaleOrder, saleOrder, oldBuyOrder, buyOrder,request);				
		
		
	}
	
	//根据销售订单填充退票订单
	private void fillTeamRefundOrderList(AirticketOrder orderForm,AirticketOrder oldSaleOrder,AirticketOrder saleOrder,AirticketOrder oldBuyOrder,AirticketOrder buyOrder,HttpServletRequest request)throws AppException{
		OrderGroup orderGroup=oldSaleOrder.getOrderGroup();
		
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		
		System.out.println("oldSaleOrder id:"+oldSaleOrder.getId());
		System.out.println("oldBuyOrder id:"+oldBuyOrder.getId());		
		
		saleOrder.setAgent(oldSaleOrder.getAgent());
		saleOrder.setDrawer(oldSaleOrder.getDrawer());
		saleOrder.setAdultCount(oldSaleOrder.getAdultCount());
		saleOrder.setChildCount(oldSaleOrder.getChildCount());
		saleOrder.setTeamaddPrice(oldSaleOrder.getTeamaddPrice());
		saleOrder.setAgentaddPrice(oldSaleOrder.getAgentaddPrice());
		saleOrder.setCommissonCount(oldSaleOrder.getCommissonCount());
		saleOrder.setRakeOff(oldSaleOrder.getRakeOff());
		saleOrder.setMemo(oldSaleOrder.getMemo());
		saleOrder.setOverAirportfulePrice(oldSaleOrder.getOverAirportfulePrice());
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
		long newSubGroupNo = airticketOrderDAO.getNewSubGroupMarkNo(orderGroup.getId());
		saleOrder.setSubGroupMarkNo(newSubGroupNo);

		
		buyOrder.setCommissonCount(oldBuyOrder.getCommissonCount());
		buyOrder.setHandlingCharge(oldBuyOrder.getHandlingCharge());
		buyOrder.setRakeoffCount(oldBuyOrder.getRakeoffCount());
		buyOrder.setIncomeretreatCharge(oldBuyOrder.getIncomeretreatCharge());
		buyOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
		buyOrder.setTranType(AirticketOrder.TRANTYPE_3);
		buyOrder.setTicketType(AirticketOrder.TICKETTYPE_2);
		buyOrder.setStatus(AirticketOrder.STATUS_107);
		buyOrder.setTotalAmount(BigDecimal.ZERO);
		buyOrder.setFlights(null);		
		buyOrder.setPassengers(null);
		buyOrder.setEntryTime(new Timestamp(System.currentTimeMillis()));
		buyOrder.setEntryOperator(uri.getUser().getUserNo());
		buyOrder.setOrderGroup(orderGroup);
		buyOrder.setSubGroupMarkNo(newSubGroupNo);
		
		save(saleOrder);
		save(buyOrder);		
		
		System.out.println("saleOrder id:"+saleOrder.getId());
		System.out.println("buyOrder id:"+buyOrder.getId());
		
		String[] flightIds=orderForm.getFlightIds();
		flightPassengerBiz.saveFlightByIdsForOrder(saleOrder, flightIds);
		flightPassengerBiz.saveFlightByIdsForOrder(buyOrder, flightIds);
		
		update(saleOrder);
		update(buyOrder);
		
		updateOrderGroup(saleOrder);
		
		request.setAttribute("buyerOrder", buyOrder);
		request.setAttribute("airticketOrder", saleOrder);
		request.setAttribute("teamProfit",new TeamProfit(saleOrder,buyOrder));
	}
	
	// 删除订单(改变状态)
	public void deleteAirticketOrder(Long airticketOrderId) throws AppException
	{
		AirticketOrder airticketOrder = airticketOrderDAO
		    .getAirticketOrderById(airticketOrderId);
		airticketOrder.setStatus(AirticketOrder.STATUS_88);// 将订单状态变为已废弃
		airticketOrderDAO.update(airticketOrder);

		deleteStatementByAirticketOrder(airticketOrder);
	}

	// 删除订单的关联结算记录(改变状态)
	public void deleteStatementByAirticketOrder(AirticketOrder airticketOrder)
	    throws AppException
	{
		List statementList = statementDAO.getStatementListByOrder(airticketOrder
		    .getId(), Statement.ORDERTYPE_1);
		for (int i = 0; i < statementList.size(); i++)
		{
			Statement statement = (Statement) statementList.get(i);
			statement.setStatus(Statement.STATUS_88);// 已废弃
			statementDAO.update(statement);
		}
	}

	// 显示订单详细信息
	public String view(AirticketOrderListForm ulf,
	    HttpServletRequest request) throws AppException
	{
		String forwardPage = "";
		Long aircketOrderId = ulf.getId();
		if (aircketOrderId != null && aircketOrderId > 0)
		{
			List<Statement> statementList = new ArrayList<Statement>();
			List<Passenger> passengerList = new ArrayList<Passenger>();
			List<Flight> flightList = new ArrayList<Flight>();
			List<TicketLog> ticketLogList = new ArrayList<TicketLog>();

			AirticketOrder airticketOrder = airticketOrderDAO
			    .getAirticketOrderById(aircketOrderId);

			long subGroupNo = airticketOrder.getSubGroupMarkNo();

			List<AirticketOrder> airticketOrderList = airticketOrderDAO
			    .listBySubGroupByAndGroupId(airticketOrder.getOrderGroup().getId(),
			        subGroupNo);
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
			System.out.println("orderString:" + ordersString);

			passengerList = passengerDAO.listByairticketOrderId(aircketOrderId);
			flightList = flightDAO.getFlightListByOrderId(aircketOrderId);
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
	
	// 查看团队
	public void viewTeam(AirticketOrderListForm ulf,
	    HttpServletRequest request) throws AppException
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
		
		if (order.getAgent()!=null) {
			String agentName=order.getAgent().getName();
			//System.out.println(ao.getAgent().getName());
		}
		
		
		return order;
	}


	public AirticketOrder getAirticketOrderByGroupIdStatus(long groupId,
	    String tranType, String status) throws AppException
	{
		AirticketOrder ao = new AirticketOrder();
		List airticketOrderList = airticketOrderDAO.listByGroupIdAndTranTypeStatus(
		    groupId, tranType, status);
		if (airticketOrderList != null && airticketOrderList.size() > 0)
		{
			ao = (AirticketOrder) airticketOrderList.get(0);
		}
		return ao;
	}

	private OrderGroup saveOrderGroup(AirticketOrder ao) throws AppException
	{
		OrderGroup og = new OrderGroup();
		og.setNo(noUtil.getAirticketGroupNo());
		if (ao.getEntryTime() == null)
			og.setFirstDate(new Timestamp(System.currentTimeMillis()));
		else
			og.setFirstDate(ao.getEntryTime());
		og.setLastDate(new Timestamp(System.currentTimeMillis()));
		airticketOrderDAO.saveOrderGroup(og);
		return og;
	}

	private OrderGroup updateOrderGroup(AirticketOrder ao) throws AppException
	{
		OrderGroup og = airticketOrderDAO.getOrderGroupById(ao.getOrderGroup()
		    .getId());
		og.setLastDate(new Timestamp(System.currentTimeMillis()));
		airticketOrderDAO.saveOrderGroup(og);
		System.out.println("update OrderGroup no:" + ao.getGroupMarkNo() + "--id:"
		    + ao.getId());
		return og;
	}

	/**
	 * 保存结算记录
	 */
	public void saveStatementByAirticketOrder(AirticketOrder order,
	    SysUser sysUser, long statementType, long statementStatus)
	    throws AppException
	{
		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setOrderId(order.getId());
		statement.setOrderType(Statement.ORDERTYPE_1);

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

		statement.setType(statementType);// 类型
		statement.setStatus(statementStatus);// 状态

		statementDAO.save(statement);

		AirticketLogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		myLog.info(sysUser.getUserName() + "-确认-" + statement.getTypeInfo()
		    + statement.getTotalAmount() + " For order id:" + order.getId());
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
	 */
	public void saveStatementByAirticketOrder(AirticketOrder order,
	    SysUser sysUser, long statementType, long statementStatus,
	    Timestamp timeString) throws AppException
	{
		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setOrderId(order.getId());
		statement.setOrderType(Statement.ORDERTYPE_1);

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
		statement.setOptTime(timeString);// 操作时间

		statement.setType(statementType);// 类型
		statement.setStatus(statementStatus);// 状态

		statementDAO.save(statement);

		AirticketLogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		myLog.info(sysUser.getUserName() + "-确认-" + statement.getTypeInfo()
		    + statement.getTotalAmount() + " For order id:" + order.getId());
	}

	public void saveAirticketTicketLog(long orderId, String groupMarkNo,
	    SysUser sysUser, HttpServletRequest request, long ticketLogType)
	    throws AppException
	{
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderId(orderId);
		ticketLog.setOrderNo(groupMarkNo);
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(sysUser);// 操作员
		ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(ticketLogType);
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
		AirticketLogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		myLog.info(sysUser.getUserName() + "-" + ticketLog.getTypeInfo()
		    + groupMarkNo + "--order id:" + orderId);
	}

	public void saveAirticketTicketLog(long orderId, String groupMarkNo,
	    SysUser sysUser, long ticketLogType) throws AppException
	{
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderId(orderId);
		ticketLog.setOrderNo(groupMarkNo);
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(sysUser);// 操作员
		// ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(ticketLogType);
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
		LogUtil myLog = new AirticketLogUtil(true, false,
		    AirticketOrderBizImp.class, "");
		myLog.info(sysUser.getUserName() + "-" + ticketLog.getTypeInfo()
		    + groupMarkNo + "--order id:" + orderId);
	}


	public AirticketOrder getAirticketOrderByStatementId(long statementId)
	    throws AppException
	{
		return airticketOrderDAO.getAirticketOrderByStatementId(statementId);
	}

	// 根据 预定pnr查询
	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
	    throws AppException
	{
		return airticketOrderDAO.getAirticketOrderBysubPnr(subPnr);
	}

	// 根据 预定pnr、类型查询导入退废、改签的订单
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

	public AirticketOrder getDrawedAirticketOrderByGroupId(long groupId,
	    long tranType) throws AppException
	{
		return airticketOrderDAO
		    .getDrawedAirticketOrderByGroupId(groupId, tranType);
	}

	public List<AirticketOrder> listByGroupId(long groupId) throws AppException
	{
		return airticketOrderDAO.listByGroupId(groupId);
	}

	//---------DWR有调用
	public List<AirticketOrder> listByGroupIdAndTranType(long groupId,
	    String tranType) throws AppException
	{
		return airticketOrderDAO.listByGroupIdAndTranType(groupId, tranType);
	}

	public List<AirticketOrder> listByGroupIdAndBusinessTranType(long groupId,
	    String tranType, String businessType) throws AppException
	{
		return airticketOrderDAO.listByGroupIdAndBusinessTranType(groupId,
		    tranType, businessType);
	}

	public List<AirticketOrder> getAirticketOrderListByPNR(String subPnr,
	    String tranType) throws AppException
	{
		return airticketOrderDAO.getAirticketOrderListByPNR(subPnr, tranType);
	}

	public List list(AirticketOrderListForm rlf) throws AppException
	{
		return airticketOrderDAO.list(rlf);
	}

	public List listTeam(AirticketOrderListForm rlf) throws AppException
	{
		return airticketOrderDAO.listTeam(rlf);
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

	
}
