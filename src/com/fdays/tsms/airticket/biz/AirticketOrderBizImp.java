package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
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
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.dao.AccountDAO;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.PlatComAccountDAO;
import com.fdays.tsms.transaction.dao.StatementDAO;
import com.fdays.tsms.user.SysUser;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderBizImp implements AirticketOrderBiz {
	private AirticketOrderDAO airticketOrderDAO;
	private FlightDAO flightDAO;
	private PassengerDAO passengerDAO;
	private StatementDAO statementDAO;
	private NoUtil noUtil;
	private AgentDAO agentDAO;
	private TicketLogDAO ticketLogDAO;
	private PlatComAccountDAO platComAccountDAO;
	private AccountDAO accountDAO;
	private FlightPassengerBiz flightPassengerBiz;
	
	// B2B订单录入
	public String createTradingOrder(HttpServletRequest request,
			AirticketOrder airticketOrderForm) throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		TempPNR tempPNR = uri.getTempPNR();

		if (tempPNR != null) {
			airticketOrderForm.setStatus(AirticketOrder.STATUS_1); // 订单状态
			airticketOrderForm.setTicketType(AirticketOrder.TICKETTYPE_1); // 设置机票类型
			airticketOrderForm.getTicketLog().setType(TicketLog.TYPE_0);// 操作日志

			// 创建正常销售订单
			forwardPage=createPNR(airticketOrderForm, tempPNR, uri);
			
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
			uri.setTempPNR(null);
		} else {
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
			throws AppException {
		TempPNR tempPNR = ParseBlackUtil.getTempPNRByBlack(blackInfo,ParseBlackUtil.Type_Content);// PNR、乘客、行程
		tempPNR = IBEUtil.setTicketPriceByIBEInterface(tempPNR);// 基准票价、燃油、机建
		tempPNR = CabinUtil.setTicketPriceByIBEInterface(tempPNR);// 舱位折扣
		return tempPNR;
	}

	/**
	 * 创建B2B和B2C销售订单调用
	*/public String  createPNR(AirticketOrder airticketOrderFrom, TempPNR tempPNR,
			UserRightInfo uri) throws AppException {
		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setSubPnr(tempPNR.getPnr());// 预订pnr
		if(airticketOrderFrom.getBigPnr()!=null&&!"".equals(airticketOrderFrom.getBigPnr().trim())){
			ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		}
		ao.setTicketPrice(tempPNR.getFare());// 票面价格
		ao.setAirportPrice(tempPNR.getTax());// 机建费
		ao.setFuelPrice(tempPNR.getYq());// 燃油税
		if (airticketOrderFrom.getAgentId() != null
				&& airticketOrderFrom.getAgentId() > 0) {
			Agent agent = agentDAO
					.getAgentByid(airticketOrderFrom.getAgentId());
			ao.setAgent(agent); // 购票客户
		} else {
			ao.setAgent(null);
		}
		ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrderFrom.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号
		ao.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrderFrom.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getStatement_type());// 交易类型
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(airticketOrderFrom.getStatement_type());// 业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setPayOperator(uri.getUser().getUserNo());		
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		Long platformId = airticketOrderFrom.getPlatformId();
		Long companyId = airticketOrderFrom.getCompanyId();
		Long accountId = airticketOrderFrom.getAccountId();
		if (platformId != null && companyId != null && accountId != null) {
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompnyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
		}
		ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额

		airticketOrderDAO.save(ao);
		airticketOrderFrom.setGroupMarkNo(ao.getGroupMarkNo());
		
		flightPassengerBiz.saveFlightPassengerByTempPNR(tempPNR, ao);
		
		//收款
		saveStatementByAirticketOrder(ao, uri.getUser(), Statement.type_1,Statement.STATUS_1);

		// 操作日志
		saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(),uri.getUser(), airticketOrderFrom.getTicketLog()
				.getType());		
		return ao.getGroupMarkNo();		
	}	
	
	// 创建申请支付订单
	public String createApplyTickettOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
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
			ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
			ao.setStatus(AirticketOrder.STATUS_2); // 订单状态
			ao.setTicketType(airticketOrder.getTicketType());// 机票类型
			ao.setTranType(Statement.type_2);// 交易类型
			ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ao.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
			ao.setEntryOperator(uri.getUser().getUserNo());
			ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

			// 设置平台公司帐号
			long platformId = Long.parseLong(request
					.getParameter("platformId9"));
			long companyId = Long.parseLong(request.getParameter("companyId9"));
			long accountId = Long.parseLong(request.getParameter("accountId9"));
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompnyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));

			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
			airticketOrderDAO.save(ao);

			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);
			
			airticketOrder.setStatus(AirticketOrder.STATUS_3);
			airticketOrderDAO.update(airticketOrder);// 修改原订单信息

			// 操作日志
			saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(), uri.getUser(),
					TicketLog.TYPE_3);
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		} else {
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}
	
	
	//重新申请支付
	public String anewApplyTicket(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		
		if (airticketOrderFrom != null && airticketOrderFrom.getGroupMarkNo()!=null&&!"".equals(airticketOrderFrom.getGroupMarkNo().trim())) {
            if(airticketOrderFrom.getId()>0){
            	AirticketOrder ao = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());
            	ao.setStatus(AirticketOrder.STATUS_10);
            	airticketOrderDAO.update(ao);
            }
            
            //原订单
			AirticketOrder airticketOrder = getAirticketOrderByMarkNo(airticketOrderFrom.getGroupMarkNo(),"1");
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
			ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
			ao.setStatus(AirticketOrder.STATUS_2); // 订单状态
			ao.setTicketType(airticketOrder.getTicketType());// 机票类型
			ao.setTranType(Statement.type_2);// 交易类型
			ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			ao.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
			ao.setEntryOperator(uri.getUser().getUserNo());
			ao.setPayOperator(uri.getUser().getUserNo());
			ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

			// 设置平台公司帐号
			long platformId = Long.parseLong(request
					.getParameter("platformId9"));
			long companyId = Long.parseLong(request.getParameter("companyId9"));
			long accountId = Long.parseLong(request.getParameter("accountId9"));			
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompnyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
			airticketOrderDAO.save(ao);
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);	

			// 操作日志
			saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(), uri.getUser(),
					TicketLog.TYPE_3);
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		}else {
			forwardPage="NOORDER";
		}
		return forwardPage;
	}
	
	//锁定
	public String lockupOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";			
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo=airticketOrder.getGroupMarkNo();
			
			airticketOrder.setStatus(AirticketOrder.STATUS_7); // 订单状态
			airticketOrder.setCurrentOperator(uri.getUser().getUserNo());// 当前操作人
			
			airticketOrderDAO.update(airticketOrder);
			
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, TicketLog.TYPE_29);
			
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}


	//解锁(自己的订单)
	public String unlockSelfOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";	
		LogUtil myLog=new AirticketLogUtil(true,false,AirticketOrderBizImp.class,"");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo=airticketOrder.getGroupMarkNo();
			
			if (airticketOrder.getCurrentOperator() != null&& !"".equals(airticketOrder.getCurrentOperator())) {
				if (airticketOrder.getCurrentOperator().equals(uri.getUser().getUserNo())) {
					airticketOrder.setStatus(AirticketOrder.STATUS_8); //订单状态
					airticketOrder.setCurrentOperator(null);//当前操作人	
					airticketOrderDAO.update(airticketOrder);				
					saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, TicketLog.TYPE_30);	
//					forwardPage=ao.getGroupMarkNo();
					forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
				}else{
					myLog.info(groupMarkNo+"--orderId:"+airticketOrder.getId()+"解锁人与锁定人不符");
					return "ERROR";
				}				
			}else{
				myLog.info(groupMarkNo+"--orderId:"+airticketOrder.getId()+"没有当前操作人");
				return "ERROR";
			}	
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}
	
	//解锁（所有订单）
	public String unlockAllOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";			
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo=airticketOrder.getGroupMarkNo();			
			
			airticketOrder.setStatus(AirticketOrder.STATUS_8); //订单状态
			airticketOrder.setCurrentOperator(null);//当前操作人						
			
			airticketOrderDAO.update(airticketOrder);
			
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, TicketLog.TYPE_30);	
			
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}
	

	// 确认支付
	public String confirmPayment(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			AirticketOrder airticketOrder = airticketOrderDAO
					.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getGroupMarkNo();

			// 设置平台公司帐号
			Long platformId = airticketOrderFrom.getPlatformId();
			Long companyId = airticketOrderFrom.getCompanyId();
			Long accountId = airticketOrderFrom.getAccountId();
			if (platformId != null) {
				airticketOrder.setPlatform(PlatComAccountStore
						.getPlatformById(platformId));
			}
			if (companyId != null) {
				airticketOrder.setCompany(PlatComAccountStore
						.getCompnyById(companyId));
			}
			if (accountId != null) {
				airticketOrder.setAccount(PlatComAccountStore
						.getAccountById(accountId));
			}
			airticketOrder.setStatus(AirticketOrder.STATUS_3); // 修改订单状态
			airticketOrder.setSubPnr(airticketOrderFrom.getPnr());
			airticketOrder.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 订单号
			airticketOrder.setRebate(airticketOrderFrom.getRebate());// 政策
			airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());// 设置金额
			airticketOrder.setPayOperator(uri.getUser().getUserNo());
			airticketOrder.setPayTime(new Timestamp(System.currentTimeMillis()));
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrderDAO.update(airticketOrder);
			
			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request,
					TicketLog.TYPE_7);

			//付款
			saveStatementByAirticketOrder(airticketOrder, uri.getUser(), Statement.type_2,Statement.STATUS_1);
			
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		} else {
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 确认出票
	public String confirmTicket(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			AirticketOrder airticketOrder = airticketOrderDAO
					.getAirticketOrderById(airticketOrderFrom.getId());

			airticketOrder.setStatus(AirticketOrder.STATUS_5);
			airticketOrder.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr

			// 获取乘客信息
			String[] ticketNumber = request.getParameterValues("ticketNumber");
			String[] pId = request.getParameterValues("pId");
			String groupMarkNo = request.getParameter("groupMarkNo2");// 订单组号			

     	   List<AirticketOrder> listao =new ArrayList<AirticketOrder>();
     	   listao.add(airticketOrder);     	   
     	   AirticketOrder ao1 = getAirticketOrderByMarkNo(groupMarkNo,"1");
     
			if (groupMarkNo != null && !"".equals(groupMarkNo.trim())) {				
				
//				List<AirticketOrder> listao = airticketOrderDAO.listByGroupMarkNoAndTranType(groupMarkNo, "1,2");
//				for (AirticketOrder ao : listao) {
//					List pa = passengerDAO.listByairticketOrderId(ao.getId());
//					if (pa != null && groupMarkNo != null
//							&& pa.size() == ticketNumber.length) {
//						for (int i = 0; i < pa.size(); i++) {
//							if (ao.getId() > 0) {
//								Passenger passenger = (Passenger) pa.get(i);
//								System.out.println(i + "==="
//										+ ticketNumber[i].trim());
//								passenger.setTicketNumber(ticketNumber[i]
//										.trim());
//								passengerDAO.update(passenger);
//							}
//						}
//					}
//					// 修改订单状态
//					if (ao.getId() != airticketOrder.getId()) {
//						ao.setStatus(AirticketOrder.STATUS_5);
//						ao.setDrawer(uri.getUser().getUserNo());
//						ao
//								.setOptTime(new Timestamp(System
//										.currentTimeMillis()));// 操作时间
//						airticketOrderDAO.update(ao);
//					}
//				}				
				 if (ao1!=null&&ao1.getId()>0) {
						ao1.setStatus(AirticketOrder.STATUS_5);
						ao1.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
						ao1.setDrawTime(new Timestamp(System.currentTimeMillis()));//出票时间
						ao1.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
						airticketOrderDAO.update(ao1);
						listao.add(ao1);
					}	   			
					for (AirticketOrder ao : listao) {
						List pa = passengerDAO.listByairticketOrderId(ao.getId());
						if (pa != null && groupMarkNo != null
								&& pa.size() == ticketNumber.length) {
							for (int i = 0; i < pa.size(); i++) {
								if (ao.getId() > 0) {
									Passenger passenger = (Passenger) pa.get(i);
									System.out.println(i + "==="+ ticketNumber[i].trim());
									passenger.setTicketNumber(ticketNumber[i].trim());
									passengerDAO.update(passenger);
								}
							}
						}
					}	
				
			}
			airticketOrder.setDrawTime(new Timestamp(System.currentTimeMillis()));//出票时间
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrder.setDrawer(uri.getUser().getUserNo());
			airticketOrderDAO.update(airticketOrder);

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request,
					TicketLog.TYPE_5);
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		} else {
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 取消出票
	public String quitTicket(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			AirticketOrder airticketOrder = airticketOrderDAO
					.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getGroupMarkNo();
			
			airticketOrder.setStatus(AirticketOrder.STATUS_4);
			if(airticketOrderFrom.getStatus()!=null){
				airticketOrder.setStatus(airticketOrderFrom.getStatus());
			}
			airticketOrder.setMemo(airticketOrderFrom.getMemo());
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrderDAO.update(airticketOrder);			

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request,
					TicketLog.TYPE_4);
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		} else {
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}

	// 取消出票,确认退款
	public String agreeCancelRefund(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage="";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo = airticketOrder.getGroupMarkNo();
			long businessType=airticketOrder.getBusinessType();
			
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrder.setStatus(AirticketOrder.STATUS_6); // 订单状态
			airticketOrderDAO.update(airticketOrder);
			
			if (businessType==1) {//卖出
				//取消出票，收供应商退款	
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),  Statement.STATUS_1, Statement.STATUS_1);
				saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, TicketLog.TYPE_6);
			}else if(businessType==2){//买入		
				//取消出票，付采购商退款	
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),  Statement.STATUS_2, Statement.STATUS_1);
				saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, TicketLog.TYPE_6);
			}		
//			forwardPage=ao.getGroupMarkNo();
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}

	// 编辑备注
	public String editRemark(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			AirticketOrder airticketOrder = airticketOrderDAO
					.getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setMemo(airticketOrderFrom.getMemo());
			airticketOrder
					.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			airticketOrderDAO.update(airticketOrder);

			String groupMarkNo = airticketOrder.getGroupMarkNo();

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request,
					TicketLog.TYPE_50);
//			forwardPage=groupMarkNo;
			forwardPage=getForwardPageByOrderType(airticketOrder);
		} else {
			forwardPage = "NOORDER";
		}
		return forwardPage;
	}
	
	/**
	 * 
	 * */
	public String updateAirticketOrderStatus(AirticketOrder airticketOrderFrom,HttpServletRequest request)throws AppException{
		String forwardPage="";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());			
			String groupMarkNo=airticketOrder.getGroupMarkNo();		
			
			Long ticketLogType=null;
			if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_44) {// 改签未通过
				ticketLogType=TicketLog.TYPE_27;
			} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41) {// 改签审核通过
				ticketLogType=TicketLog.TYPE_23;
			} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_42) {// 改签审核通过
				ticketLogType=TicketLog.TYPE_2;
			} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_45) {// 改签完成
				ticketLogType=TicketLog.TYPE_26;
			}

			airticketOrder.setStatus(airticketOrderFrom.getStatus()); // 订单状态	
			airticketOrderDAO.update(airticketOrder);
			
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, ticketLogType);		

//			forwardPage=groupMarkNo;
			forwardPage=getForwardPageByOrderType(airticketOrder);
		} else {
			forwardPage="NOERROR";
		}
		return forwardPage;
	}

	// 通过外部pnr信息创建退废票
	public String  createOutRetireTradingOrder(AirticketOrder airticketOrderFrom,
			TempPNR tempPNR, AirticketOrder airticketOrder, UserRightInfo uri)
			throws AppException {
		String forwardPage="";
		
		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setDrawPnr(airticketOrderFrom.getDrawPnr());// 出票pnr
		ao.setSubPnr(airticketOrderFrom.getSubPnr());// 预订pnr
		ao.setBigPnr(airticketOrderFrom.getBigPnr());// 大pnr
		ao.setTicketPrice(airticketOrderFrom.getTicketPrice());// 票面价格
		ao.setAirportPrice(airticketOrderFrom.getAirportPrice());// 机建费
		ao.setFuelPrice(airticketOrderFrom.getFuelPrice());// 燃油税
		ao.setAgent(airticketOrderFrom.getAgent()); // 购票客户
		ao.setHandlingCharge(airticketOrderFrom.getHandlingCharge());// 手续费
		ao.setDocumentPrice(airticketOrderFrom.getDocumentPrice());// 行程单费用
		ao.setInsurancePrice(airticketOrderFrom.getInsurancePrice());// 保险费
		ao.setRebate(airticketOrderFrom.getRebate());// 政策
		ao.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 机票订单号
		if (airticketOrder != null && airticketOrder.getGroupMarkNo() != null
				&& !"".equals(airticketOrder.getGroupMarkNo().trim())) {
			ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());
		} else {
			ao.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		}
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrderFrom.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getTranType());// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(airticketOrderFrom.getBusinessType());// 业务类型
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退废票原因
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		Long platformId = airticketOrderFrom.getPlatformId();
		Long companyId = airticketOrderFrom.getCompanyId();
		Long accountId = airticketOrderFrom.getAccountId();
		if (platformId != null && companyId != null && accountId != null) {
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompnyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
		}
		ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
		airticketOrderDAO.save(ao);

		// 创建退费
		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_24
				|| airticketOrderFrom.getStatus() == AirticketOrder.STATUS_34) {
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrderFrom,
					ao);
			// 审核退废 并且创建（ 收款订单） 外部
		} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_21
				|| airticketOrderFrom.getStatus() == AirticketOrder.STATUS_31) {
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrderFrom,
					ao);
		}
		// 操作日志
		saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(), uri.getUser(),
				airticketOrderFrom.getTicketLog().getType());
		
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 创建退废票
	public String createRetireTradingOrder(AirticketOrder airticketOrderFrom,
			AirticketOrder airticketOrder, UserRightInfo uri)
			throws AppException {
		String forwardPage="";
		BigDecimal totalAmount = airticketOrder.getTotalAmount();
		String[] passengerId = airticketOrderFrom.getPassengerIds();
		if (passengerId != null && passengerId.length > 0) {
			BigDecimal passengersCount = new BigDecimal(airticketOrder
					.getPassengersCount());
			BigDecimal passengersNum = new BigDecimal(passengerId.length);
			if (totalAmount != null && passengersCount != null
					&& passengersNum != null) {
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
		if (airticketOrder.getBigPnr() != null) {
			ao.setBigPnr(airticketOrder.getBigPnr());// 大pnr
		}
		if (airticketOrderFrom.getBigPnr() != null) {
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
		ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrder.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getTranType());// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退票原因
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(airticketOrderFrom.getBusinessType());// 业务类型
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退废票原因
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		ao.setPlatform(airticketOrder.getPlatform());
		ao.setCompany(airticketOrder.getCompany());
		ao.setAccount(airticketOrder.getAccount());
		
		if (airticketOrderFrom.getTotalAmount()!=null) {//创建买入、通过申请
			ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额
		}else{//创建退废
			ao.setTotalAmount(airticketOrder.getTotalAmount());// 总金额
		}
		
		
		ao.setOldOrderNo(airticketOrder.getAirOrderNo());//原始订单号
		airticketOrderDAO.save(ao);

		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_19
				|| airticketOrderFrom.getStatus() == AirticketOrder.STATUS_29) {// 创建退废
			flightPassengerBiz.saveFlightPassengerByOrderForm(
					airticketOrderFrom, ao);
		} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_21
				|| airticketOrderFrom.getStatus() == AirticketOrder.STATUS_31) {
			// 审核退废
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);
		}

		// 操作日志
		saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(), uri.getUser(),
				airticketOrderFrom.getTicketLog().getType());
		
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 审核退废票
	public String auditRetireTrading(AirticketOrder airticketOrderFrom,
			UserRightInfo uri) throws AppException {
		String forwardPage="";
		
		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(airticketOrderFrom.getId());

		AirticketOrder ao = airticketOrderDAO
				.getDrawedAirticketOrderByGroupMarkNo(airticketOrderFrom
						.getGroupMarkNo(), AirticketOrder.TRANTYPE__2);

		airticketOrderFrom.setDrawPnr(ao.getDrawPnr());
		if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_21); // 订单状态
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_10);// 操作日志
			airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_3);
			airticketOrder.setStatus(AirticketOrder.STATUS_20);
		} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_31); // 订单状态
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_15);// 操作日志
			airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_4);
			airticketOrder.setStatus(AirticketOrder.STATUS_30);
		}
		if(airticketOrderFrom.getTransRule()!=null){
			airticketOrder.setTransRule(airticketOrderFrom.getTransRule());//客规百分比
		}
		airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

		// ------------------------
		createRetireTradingOrder(airticketOrderFrom, ao, uri);

		airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		airticketOrderDAO.update(airticketOrder);
		
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 审核退废票 外部
	public String auditOutRetireTrading(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage="";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(airticketOrderFrom.getId());

		if (airticketOrder != null) {
			airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1);
			airticketOrderFrom.setDrawPnr(airticketOrder.getDrawPnr());// 出票pnr
			airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
			airticketOrderFrom.setBigPnr(airticketOrder.getBigPnr());// 大pnr
			airticketOrderFrom.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
			airticketOrderFrom
					.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
			airticketOrderFrom.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
		}
		airticketOrderFrom.setHandlingCharge(airticketOrderFrom
				.getHandlingCharge());// 手续费
		airticketOrderFrom.setTotalAmount(airticketOrderFrom.getTotalAmount());// 设置交易金额

		if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_21); // 订单状态
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_10);// 操作日志
			// 类型
			airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_3);
			airticketOrder.setStatus(AirticketOrder.STATUS_25);
		} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_31); // 订单状态
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_15);// 操作日志
			// 类型
			airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_4);
			airticketOrder.setStatus(AirticketOrder.STATUS_35);
		}
		airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
		airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

		String platformId = request.getParameter("platformId12");
		String companyId = request.getParameter("companyId12");
		String accountId = request.getParameter("accountId12");
		if (platformId != null) {
			airticketOrderFrom.setPlatformId(Long.parseLong(platformId));
		}
		if (companyId != null) {
			airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
		}
		if (accountId != null) {
			airticketOrderFrom.setAccountId(Long.parseLong(accountId));
		}

		createOutRetireTradingOrder(airticketOrderFrom, new TempPNR(),
				airticketOrder, uri);

		airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		airticketOrderDAO.update(airticketOrder);
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(airticketOrder);
		return forwardPage;

	}

	// 通过外部pnr信息创建改签票
	public String createOutWaitAgreeUmbuchenOrder(
			AirticketOrder airticketOrderFrom, TempPNR tempPNR,
			AirticketOrder airticketOrder, UserRightInfo uri)
			throws AppException {
		String forwardPage="";
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
				&& !"".equals(airticketOrder.getGroupMarkNo().trim())) {
			ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());
		} else {
			ao.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		}
		ao.setStatus(airticketOrderFrom.getStatus()); // 订单状态
		ao.setTicketType(airticketOrderFrom.getTicketType());// 机票类型
		ao.setTranType(airticketOrderFrom.getTranType());// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(airticketOrderFrom.getBusinessType());// 业务类型
		ao.setReturnReason(airticketOrderFrom.getReturnReason());// 退废票原因
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间

		// 设置平台公司帐号
		Long platformId = airticketOrderFrom.getPlatformId();
		Long companyId = airticketOrderFrom.getCompanyId();
		Long accountId = airticketOrderFrom.getAccountId();
		if (platformId != null && companyId != null && accountId != null) {
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompnyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
		}
		ao.setTotalAmount(airticketOrderFrom.getTotalAmount());// 总金额

		airticketOrderDAO.save(ao);
		airticketOrderFrom.setGroupMarkNo(ao.getGroupMarkNo());
		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_46) {
			flightPassengerBiz.saveFlightPassengerByOrderForm(
					airticketOrderFrom, ao);
			// 审核改签 并且创建（ 收款订单） 外部
		} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41) {
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);
		}
		// 操作日志
		saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(), uri.getUser(),
				TicketLog.TYPE_21);
		
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	// 创建改签票
	public String createWaitAgreeUmbuchenOrder(AirticketOrder airticketOrderFrom,
			AirticketOrder airticketOrder, UserRightInfo uri)
			throws AppException {
		String forwardPage="";
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
		ao.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
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
		if (platformId != null && companyId != null && accountId != null) {
			ao.setPlatform(PlatComAccountStore.getPlatformById(platformId));
			ao.setCompany(PlatComAccountStore.getCompnyById(companyId));
			ao.setAccount(PlatComAccountStore.getAccountById(accountId));
		}
		
		ao.setTotalAmount(airticketOrder.getTotalAmount());// 总金额
		airticketOrderDAO.save(ao);

		if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_39) {
			flightPassengerBiz.saveFlightPassengerByOrderForm(
					airticketOrderFrom, ao);
		} else if (airticketOrderFrom.getStatus() == AirticketOrder.STATUS_41) {
			flightPassengerBiz.saveFlightPassengerByOrder(airticketOrder, ao);
		}
		// 操作日志
		saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(),uri.getUser(), TicketLog.TYPE_21);
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(airticketOrder);
		return forwardPage;
	}

	
	public String createB2CTradingOrder(HttpServletRequest request,
			AirticketOrder airticketOrderForm) throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		TempPNR tempPNR = uri.getTempPNR();

		if (tempPNR != null) {
			airticketOrderForm.setStatus(AirticketOrder.STATUS_1); // 订单状态
			airticketOrderForm.setTicketType(AirticketOrder.TICKETTYPE_3); // 设置机票类型
			airticketOrderForm.getTicketLog().setType(TicketLog.TYPE_32);// 操作日志

			// 创建订单
			forwardPage=createPNR(airticketOrderForm, tempPNR, uri);

			uri.setTempPNR(null);

		} else {
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
			AirticketOrder airticketOrderFrom) throws AppException {
		String forwardPage = "";
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String pnrInfo = request.getParameter("pnrInfo");

		if (pnrInfo != null && !"".equals(pnrInfo.trim())) {
			TempPNR tempPNR = getTempPNRByBlackInfo(pnrInfo);
			if (tempPNR != null) {
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

				request.setAttribute("airticketOrder", order);

				return airticketOrderFrom.getForwardPage();
			} else {
				inf.setMessage("PNR解析错误");
				inf.setBack(true);
				forwardPage = "inform";
				request.setAttribute("inf", inf);
			}
		} else {
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
	public String collectionRetireTrading(AirticketOrder airticketOrderFrom,HttpServletRequest request) throws AppException {
		String forwardPage="";
		if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderFrom.getId());
			String groupMarkNo=airticketOrder.getGroupMarkNo();
			long businessType=airticketOrder.getBusinessType();

			Long ticketLogType = null;
			Long orderStatus=null;
			
			if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
				ticketLogType = TicketLog.TYPE_12;
				orderStatus=AirticketOrder.STATUS_22;
			} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
				ticketLogType = TicketLog.TYPE_17;
				orderStatus=AirticketOrder.STATUS_32;
			}
	
			if (airticketOrderFrom.getOptTime() != null) {
				airticketOrder.setOptTime(airticketOrderFrom.getOptTime());// 操作时间
			} else {
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
			}
			
			airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());
			airticketOrder.setStatus(orderStatus);
			airticketOrderDAO.update(airticketOrder);		
			
			if (businessType==1) {
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_2, Statement.STATUS_1,airticketOrder.getOptTime());
			}else if(businessType==2){
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_1, Statement.STATUS_1,airticketOrder.getOptTime());
			}else{
				forwardPage="ERROR";
			}			
			saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(),
					request, ticketLogType);
//			forwardPage=groupMarkNo;
			forwardPage=getForwardPageByOrderType(airticketOrder);
			return forwardPage;
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}
	
	//确认收/付款，改签完成
	public String finishUmbuchenOrder(AirticketOrder airticketOrderForm,HttpServletRequest request)throws AppException{
		String forwardPage="";
		if (airticketOrderForm != null && airticketOrderForm.getId() > 0) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderForm.getId());
			String groupMarkNo = airticketOrder.getGroupMarkNo();
			long businessType=airticketOrder.getBusinessType();
			
			airticketOrder.setStatus(AirticketOrder.STATUS_45);
			airticketOrderDAO.update(airticketOrder);
			
			if (businessType==1) {//卖出 收款
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_2, Statement.STATUS_1,airticketOrder.getOptTime());
			}else if(businessType==2){//买入 付款
				saveStatementByAirticketOrder(airticketOrder, uri.getUser(),Statement.type_1, Statement.STATUS_1,airticketOrder.getOptTime());
			}else{
				return "ERROR";
			}
			saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri.getUser(), request, TicketLog.TYPE_26);
			
//			forwardPage=groupMarkNo;
			forwardPage=getForwardPageByOrderType(airticketOrder);
			return forwardPage;
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}

	// 手动添加 订单
	public String handworkAddTradingOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage="";
		if (airticketOrderFrom != null) {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");		
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
		String returnReason=request.getParameter("returnReason");
		String transRule=request.getParameter("transRule");

		// 机票订单
		AirticketOrder ao = new AirticketOrder();
		ao.setDrawPnr(drawPnr);// 出票pnr
		ao.setSubPnr(subPnr);// 预订pnr
		ao.setBigPnr(bigPnr);// 大pnr
		ao.setTicketPrice(new java.math.BigDecimal(ticketPrice));// 票面价格
		ao.setAirportPrice(new java.math.BigDecimal(airportPrice));// 机建费
		ao.setFuelPrice(new java.math.BigDecimal(fuelPrice));// 燃油税
		ao.setAgent(null); // 购票客户
		ao.setHandlingCharge(new java.math.BigDecimal(handlingCharge));// 手续费
		ao.setDocumentPrice(new java.math.BigDecimal(0));// 行程单费用
		ao.setInsurancePrice(new java.math.BigDecimal(0));// 保险费
		ao.setRebate(new java.math.BigDecimal(rebate));// 政策
		ao.setAirOrderNo(airOrderNo);// 机票订单号
		String groupMarkNo = noUtil.getAirticketGroupNo();
		ao.setGroupMarkNo(groupMarkNo);// 订单组编号
		
		long orderStatus=new Long(0);	
		long tranTypeValue=Long.parseLong(tranType);
		if (tranTypeValue==AirticketOrder.TRANTYPE__1) {
			orderStatus=AirticketOrder.STATUS_1;
		}else if (tranTypeValue==AirticketOrder.TRANTYPE_3) {
			orderStatus=AirticketOrder.STATUS_19;
		}else if (tranTypeValue==AirticketOrder.TRANTYPE_4) {
			orderStatus=AirticketOrder.STATUS_29;
		}else if (tranTypeValue==AirticketOrder.TRANTYPE_5) {
			orderStatus=AirticketOrder.STATUS_39;
		}		
		ao.setStatus(Long.valueOf(orderStatus)); // 订单状态
		
		ao.setTicketType(Long.valueOf(ticketType));// 机票类型
		ao.setTranType(Long.valueOf(tranType));// 交易类型
		ao.setMemo(airticketOrderFrom.getMemo());
		ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		ao.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
		ao.setEntryOperator(uri.getUser().getUserNo());
		ao.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录入订单时间
		ao.setReturnReason(returnReason);
		if (transRule!=null) {
			ao.setTransRule(UnitConverter.getBigDecimalByString(transRule));
		}		
		
		// 设置平台帐号
		ao.setPlatform(PlatComAccountStore.getPlatformById(Long.valueOf(platformId)));
		ao.setCompany(PlatComAccountStore.getCompnyById(Long.valueOf(companyId)));
		ao.setAccount(PlatComAccountStore.getAccountById(Long.valueOf(accountId)));
		if (totalAmount==null||"".equals(totalAmount)) {
			ao.setTotalAmount(BigDecimal.ZERO);
		}
		ao.setTotalAmount(new BigDecimal(totalAmount));
		airticketOrderDAO.save(ao);
		
		flightPassengerBiz.saveFlightPassengerByRequest(request, ao);

		// 操作日志
		saveAirticketTicketLog(ao.getId(),ao.getGroupMarkNo(), uri.getUser(), request,TicketLog.TYPE_0);
		
//		forwardPage=groupMarkNo;
		forwardPage=getForwardPageByOrderType(ao);
		return forwardPage;
		
		}else{
			return "NOORDER";
		}
	}
	

	// 编辑 订单
	public String editTradingOrder(AirticketOrder airticketOrderFrom,
			HttpServletRequest request) throws AppException {
		String forwardPage = "";
		
		if (airticketOrderFrom != null) {
			UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
			String[] airticketOrderIds = request.getParameterValues("airticketOrderIds");
			String[] totalAmount = request.getParameterValues("totalAmount");
			
			String[] platformId = request.getParameterValues("platformId");
			String[] companyId = request.getParameterValues("companyId");
			String[] accountId = request.getParameterValues("accountId");
			
			String[] entryOrderDate=request.getParameterValues("entryOrderDate");
	
			
			for (int i = 0; i < airticketOrderIds.length; i++) {
				if (Long.valueOf(airticketOrderIds[i]) > 0) {
					System.out.println("airticket order id:" + airticketOrderIds[i]);
					AirticketOrder ao = airticketOrderDAO.getAirticketOrderById(Long.valueOf(airticketOrderIds[i]));
					String[] drawPnr = request.getParameterValues("drawPnr");
					String[] subPnr = request.getParameterValues("subPnr");
					String[] bigPnr = request.getParameterValues("bigPnr");
					String[] airportPrice = request.getParameterValues("airportPrice");
					String[] fuelPrice = request.getParameterValues("fuelPrice");
					String[] ticketPrice = request.getParameterValues("ticketPrice");
					String[] handlingCharge = request.getParameterValues("handlingCharge");
					String[] rebate = request.getParameterValues("rebate");
					String[] airOrderNo = request.getParameterValues("airOrderNo");
//					String[] ticketType = request.getParameterValues("ticketType");
	
					// 机票订单
					ao.setDrawPnr(drawPnr[i]);// 出票pnr
					ao.setSubPnr(subPnr[i]);// 预订pnr
					ao.setBigPnr(bigPnr[i]);// 大pnr					
					ao.setTicketPrice(new BigDecimal(ticketPrice[i]));// 票面价格
					ao.setAirportPrice(new BigDecimal(airportPrice[i]));// 机建费
					ao.setFuelPrice(new BigDecimal(fuelPrice[i]));// 燃油税
					ao.setHandlingCharge(new BigDecimal(handlingCharge[i]));// 手续费
//					ao.setDocumentPrice(new BigDecimal(0));// 行程单费用
//					ao.setInsurancePrice(new BigDecimal(0));// 保险费
					ao.setRebate(new BigDecimal(rebate[i]));// 政策
					ao.setAirOrderNo(airOrderNo[i]);// 机票订单号
					ao.setMemo(airticketOrderFrom.getMemo());
					if (entryOrderDate[i]==null||"".equals(entryOrderDate)) {
						ao.setEntryTime(new Timestamp(System.currentTimeMillis()));
					}else{
						ao.setEntryTime(DateUtil.getTimestamp(entryOrderDate[i],"yyyy-MM-dd HH:mm:ss"));
					}
					
					ao.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间					
					
					//设置平台帐号
					ao.setPlatform(PlatComAccountStore.getPlatformById(Long.valueOf(platformId[i])));
					ao.setCompany(PlatComAccountStore.getCompnyById(Long.valueOf(companyId[i])));
					ao.setAccount(PlatComAccountStore.getAccountById(Long.valueOf(accountId[i])));					
					
					ao.setTotalAmount(new BigDecimal(totalAmount[i]));
					airticketOrderDAO.update(ao);
	
					flightPassengerBiz.updateFlightPassengerByRequest(request, ao);
					
					// 操作日志
					saveAirticketTicketLog(ao.getId(),airticketOrderFrom.getGroupMarkNo(), uri.getUser(), request,TicketLog.TYPE_51);
	
					forwardPage=ao.getGroupMarkNo();
				} else {
					forwardPage="NOORDER";
				}
			}
		}else{
			forwardPage="NOORDER";
		}
		return forwardPage;
	}

	/**
	 * 团队专用
	 */
	// ------------销售---------------
	// 修改状态（新订单,待统计利润--->>新订单,待统申请）
	public void editTeamAirticketOrder(String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_111);// 状态：新订单,待统申请
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);

		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(),
				request, TicketLog.TYPE_111);
	}
	
	// 申请支付
	public void editTeamForpayAirticketOrder(String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_102);// 状态：申请成功，等待支付
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);

		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(),
				request, TicketLog.TYPE_102);
	}

	// 确认出票
	public void editTeamAirticketOrderOver(String groupMarkNo,String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		List<AirticketOrder> airticketList =airticketOrderDAO.listByGroupMarkNo(groupMarkNo);
		AirticketOrder airticketOrder = new AirticketOrder();
		for(int i=0;i<airticketList.size();i++){
			airticketOrder = airticketList.get(i);
			Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
			if(airticketOrder.getTranType() == AirticketOrder.TRANTYPE__2){//买入(采购)
				airticketOrder.setStatus(AirticketOrder.STATUS_105);// 状态：出票成功，交易结束
				airticketOrder.setAgent(agent);
				airticketOrderDAO.update(airticketOrder);
			}
			if(airticketOrder.getTranType() == AirticketOrder.TRANTYPE__1){ //卖出(销售)
				airticketOrder.setStatus(AirticketOrder.STATUS_105);// 状态：出票成功，交易结束
				airticketOrder.setAgent(agent);
				airticketOrderDAO.update(airticketOrder);
			}
		}
		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(),
				request, TicketLog.TYPE_105);
	}

	// ------------退票---------------
	// 申请退票
	public void editTeamRefundAirticketOrder(String airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(Long.parseLong(airticketOrderId));
		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setStatus(AirticketOrder.STATUS_108);// 状态：退票审核通过，等待退款
		airticketOrder.setAgent(agent);
		airticketOrderDAO.update(airticketOrder);

		// 操作日志
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(),
				request, TicketLog.TYPE_108);
	}

	// 申请支付 显示购票客户、账号信息
	public void editTeamAirticketOrderAccount(AirticketOrderListForm alf,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		List<Agent> agentList = agentDAO.getAgentList();
		List<PlatComAccount> platComAccountList = platComAccountDAO
				.getPlatComAccountByPlatformId(99999);//查询团队
		request.setAttribute("platComAccountList", platComAccountList);
		request.setAttribute("agentList", agentList);
	}

	// 团队确认支付
	public void editTeamAirticketOrderOK(AirticketOrder airticketOrderForm,
			long airticketOrderId, UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);

		Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());
		airticketOrder.setAgent(agent);
		airticketOrder.setStatus(AirticketOrder.STATUS_103);// 支付成功，等待出票
		airticketOrder.setPayOperator(uri.getUser().getUserNo());
		airticketOrder.setPayTime(airticketOrderForm.getTxtConfirmTime());// 收付款时间
		airticketOrder.setMemo(airticketOrderForm.getTxtOrderRemark());// 备注
		airticketOrderDAO.update(airticketOrder);
		
		airticketOrder.setAccount(accountDAO.getAccountByid(airticketOrderForm.getSelAccount()));
		airticketOrder.setTotalAmount(airticketOrderForm.getTxtOrderAmount());
		saveStatementByAirticketOrder(airticketOrder, uri.getUser(), Statement.type_2, Statement.STATUS_1);
		
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(), request,TicketLog.TYPE_102);		
	}

	// 团队退票，买入 确认付款
	public void editTeamReFundAirticketOrder(AirticketOrder airticketOrderForm,long airticketOrderId, UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response)throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);
	
		airticketOrder.setIncomeretreatCharge(airticketOrderForm.getTxtRefundIncomeretreatChargeFo());//收退票手续费
		airticketOrder.setStatus(AirticketOrder.STATUS_109);// 状态：已经退款，交易结束
		airticketOrder.setPayOperator(uri.getUser().getUserNo());
		airticketOrder.setPayTime(airticketOrderForm.getTxtConfirmTimeFo());// 收付款时间
		airticketOrder.setMemo(airticketOrderForm.getTxtOrderRemarkFo());// 备注
		airticketOrderDAO.update(airticketOrder);
		
		airticketOrder.setAccount(accountDAO.getAccountByid(airticketOrderForm.getSelAccount()));
		airticketOrder.setTotalAmount(airticketOrder.getIncomeretreatCharge());
		saveStatementByAirticketOrder(airticketOrder, uri.getUser(), Statement.type_2, Statement.STATUS_1);
		
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(), request,TicketLog.TYPE_109);
	}
	
	// 团队退票，卖出 确认收款
	public void editTeamReFundAirticketOrderTo(AirticketOrder airticketOrderForm,long airticketOrderId, UserRightInfo uri,
			HttpServletRequest request, HttpServletResponse response)throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);

		airticketOrder.setIncomeretreatCharge(airticketOrderForm.getTxtRefundIncomeretreatChargeTo());//收退票手续费
		airticketOrder.setStatus(AirticketOrder.STATUS_109);// 状态：已经退款，交易结束
		airticketOrder.setPayOperator(uri.getUser().getUserNo());
		airticketOrder.setPayTime(airticketOrderForm.getTxtConfirmTimeTo());// 收付款时间
		airticketOrder.setMemo(airticketOrderForm.getTxtOrderRemarkTo());// 备注
		airticketOrderDAO.update(airticketOrder);
		
		airticketOrder.setAccount(accountDAO.getAccountByid(airticketOrderForm.getSelAccount()));
		airticketOrder.setTotalAmount(airticketOrder.getIncomeretreatCharge());
		saveStatementByAirticketOrder(airticketOrder, uri.getUser(), Statement.type_1, Statement.STATUS_1);
		
		saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri.getUser(), request,TicketLog.TYPE_110);
	}
	

	// 团队订单录入
	public void saveAirticketOrderTemp(AirticketOrder airticketOrderFrom,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		String[] airOrderNo = request.getParameterValues("airOrderNo");// 页面获取的数组
		String[] total = request.getParameterValues("totalAmount");
		String[] flightCode = request.getParameterValues("flightCode");
		String[] startPoint = request.getParameterValues("startPoint");
		String[] endPoint = request.getParameterValues("endPoint");
		String[] boardingTime = request.getParameterValues("boardingTime");
		String[] flightClass = request.getParameterValues("flightClass");
		String[] discount = request.getParameterValues("discount");// 折扣
		String[] ticketPrice = request.getParameterValues("ticketPrice");// 票面价
		String[] adultAirportPrice = request
				.getParameterValues("adultAirportPrice");// 成人机建
		String[] adultFuelPrice = request.getParameterValues("adultFuelPrice");
		String[] childAirportPrice = request
				.getParameterValues("childAirportPrice");
		String[] childfuelPrice = request.getParameterValues("childfuelPrice");
		String[] babyAirportPrice = request
				.getParameterValues("babyAirportPrice");
		String[] babyfuelPrice = request.getParameterValues("babyfuelPrice");

		// 结算表
		Agent agent = agentDAO.getAgentByid(airticketOrderFrom.getAgentNo());

		// 机票订单表
		AirticketOrder air = new AirticketOrder();
		air.setAgent(agent);
		air.setDrawPnr(airticketOrderFrom.getDrawPnr());
		air.setAdultCount(airticketOrderFrom.getAdultCount());
		air.setTotalTicketPrice(airticketOrderFrom.getTotlePrice());// 总票面价
		air.setChildCount(airticketOrderFrom.getChildCount());
		air.setBabyCount(airticketOrderFrom.getBabyCount());
		air.setAirportPrice(BigDecimal.valueOf(0));// 机建税(团队订单录入给0)
		air.setFuelPrice(BigDecimal.valueOf(0));// 燃油税
		air.setTotalAirportPrice(airticketOrderFrom.getAirportPrice());// 总机建税
		air.setTotalFuelPrice(airticketOrderFrom.getFuelPrice());// 总燃油税
		air.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 订单号
		air.setGroupMarkNo(noUtil.getAirticketGroupNo());// 订单组编号
		air.setEntryTime(new Timestamp(System.currentTimeMillis()));// 录单时间
		air.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
		air.setTeamaddPrice(airticketOrderFrom.getTeamAddPrice());// 团队加价
		air.setAgentaddPrice(airticketOrderFrom.getAgentAddPrice());// 客户加价

		air.setTotalAmount(airticketOrderFrom.getTotalAmount());// 订单金额（总金额）
		air.setCommission(BigDecimal.valueOf(0));// 现返佣金
		air.setRakeOff(BigDecimal.valueOf(0));// 后返佣金
		air.setUnsettledAccount(BigDecimal.valueOf(0));// 未结欠款

		air.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型：买入
		air.setTranType(airticketOrderFrom.getTranType());// 交易类型
		air.setTicketType(AirticketOrder.TICKETTYPE_2);// 类型:团队
		air.setEntryOperator(uri.getUser().getUserNo());
		if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE__2)// 买入
		{
			air.setStatus(AirticketOrder.STATUS_101);// 状态:新订单
		}
		if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3)// 退票
		{
			air.setStatus(AirticketOrder.STATUS_107);// 状态:退票订单，等待审核
		}
		air.setDrawer(airticketOrderFrom.getDrawer());// 客票类型(出票人)
		airticketOrderDAO.save(air);
		airticketOrderFrom.setAirticketOrderId(air.getId());

		for (int i = 0; i < flightCode.length; i++) {
			// 航班表
			Flight flight = new Flight();
			flight.setAirticketOrder(air);
			flight.setFlightCode(flightCode[i].toString());// 航班号
			flight.setStartPoint(startPoint[i].toString());// 出发地
			flight.setEndPoint(endPoint[i].toString());// 终点地
			flight.setBoardingTime(DateUtil.getTimestamp(boardingTime[i]
					.toString(), "yyyy-MM-dd"));// 出发时间
			flight.setFlightClass(flightClass[i].toString());// 舱位
			flight.setDiscount(discount[i].toString());// 折扣

			long fliTicketPrice = Long.parseLong(ticketPrice[i]);
			long airportPriceAdult = Long.parseLong(adultAirportPrice[i]);
			long fuelPriceAdult = Long.parseLong(adultFuelPrice[i]);
			long airportPriceChild = Long.parseLong(childAirportPrice[i]);
			long fuelPriceChild = Long.parseLong(childfuelPrice[i]);
			long airportPriceBaby = Long.parseLong(babyAirportPrice[i]);
			long fuelPriceBaby = Long.parseLong(babyfuelPrice[i]);
			flight.setTicketPrice(BigDecimal.valueOf(fliTicketPrice));// 票面价
			flight.setAirportPriceAdult(BigDecimal.valueOf(airportPriceAdult));
			flight.setFuelPriceAdult(BigDecimal.valueOf(fuelPriceAdult));
			flight.setAirportPriceChild(BigDecimal.valueOf(airportPriceChild));
			flight.setFuelPriceChild(BigDecimal.valueOf(fuelPriceChild));
			flight.setAirportPriceBaby(BigDecimal.valueOf(airportPriceBaby));
			flight.setFuelPriceBaby(BigDecimal.valueOf(fuelPriceBaby));
			flight.setStatus(1L);
			flightDAO.save(flight);
		}

		// 操作日志
		if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
			saveStatementByAirticketOrder(air, uri.getUser(), Statement.type_1, Statement.STATUS_2);
			saveAirticketTicketLog(air.getId(),air.getGroupMarkNo(), uri.getUser(),
					request, TicketLog.TYPE_101);
		}
		if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 退票
			saveAirticketTicketLog(air.getId(),air.getGroupMarkNo(), uri.getUser(),
					request, TicketLog.TYPE_107);
		}
				
	}

	// 显示要修改的团队订单信息
	public void updateTeamAirticketOrderPage(UserRightInfo uri,
			AirticketOrder airticketOrderForm, String airticketOrderId,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {

		AirticketOrder air = airticketOrderDAO.getAirticketOrderById(Long
				.parseLong(airticketOrderId));
		List<AirticketOrder> airticketOrderList = airticketOrderDAO
				.listByGroupMarkNo(air.getGroupMarkNo());

		List<Agent> agentList = agentDAO.getAgentList();
		List<Flight> flightList = flightDAO.getFlightListByOrderId(Long
				.parseLong(airticketOrderId));
		Flight flight = flightDAO.getFlightByAirticketOrderID(Long
				.parseLong(airticketOrderId));
		request.setAttribute("flight", flight);
		request.setAttribute("flightSize", flightList.size());
		request.setAttribute("flightList", flightList);
		request.setAttribute("agentList", agentList);
		AirticketOrder toAo = new AirticketOrder();
		AirticketOrder fromAo = new AirticketOrder();
		if (airticketOrderList.size() > 1) {
			for (int i = 0; i < airticketOrderList.size(); i++) {
				AirticketOrder airticket = airticketOrderList.get(i);
				if (airticket.getTranType() == AirticketOrder.TRANTYPE__2)// 买入
				{
					fromAo = airticketOrderList.get(i);
					fromAo.setTxtAgentT(fromAo.getCommissonCount());// 返点（航空公司）
					fromAo.setTxtCharge(fromAo.getHandlingCharge());// 手续费
					fromAo.setTxtAgent(fromAo.getRakeoffCount());// 后返点
					fromAo.setTxtTargetTGQFee(fromAo.getIncomeretreatCharge());// 付退票手续费
				}
				if (airticket.getTranType() == AirticketOrder.TRANTYPE__1)// 卖出
				{
					toAo = airticketOrderList.get(i);
					toAo.setTxtAmountMore(toAo.getOverTicketPrice());// 多收票价
					toAo.setTxtAgents(toAo.getCommissonCount());// 返点（客户）
					toAo.setTxtTaxMore(toAo.getOverAirportfulePrice());// 多收税
					toAo.setTxtSourceTGQFee(toAo.getIncomeretreatCharge());// 收退票手续费
					toAo.setTxtAgentFeeTeams(toAo.getCommission());//现返
					//toAo.setTxtUnAgentFeeTeams(toAo.getUnsettledAccount());//出团代理费未返(未结款)
				}
				if (airticket.getTranType() == AirticketOrder.TRANTYPE_3)// 退票
				{
					fromAo = airticketOrderList.get(i);
					fromAo.setTxtAgentT(fromAo.getCommissonCount());// 返点（航空公司）
					fromAo.setTxtCharge(fromAo.getHandlingCharge());// 手续费
					fromAo.setTxtAgent(fromAo.getRakeoffCount());// 后返点
					fromAo.setTxtTargetTGQFee(fromAo.getIncomeretreatCharge());// 付退票手续费
				}
			}
			request.setAttribute("toAo", toAo);
			request.setAttribute("fromAo", fromAo);

			air.setTxtTargetTGQFee(fromAo.getTxtTargetTGQFee());// 付退票手续费
			air.setTxtSourceTGQFee(toAo.getTxtSourceTGQFee());// 收退票手续费
			air.setTxtAmountMore(toAo.getTxtAmountMore());// 多收票价
			air.setTxtTaxMore(toAo.getTxtTaxMore());// 多收税
			//air.setProxyPrice(toAo.getProxyPrice());
			air.setTxtAgentFeeTeams(toAo.getTxtAgentFeeTeams());//现返
			air.setTxtUnAgentFeeTeams(toAo.getUnsettledAccount());//出团代理费未返(未结款)
			request.setAttribute("air", air);
			request.setAttribute("airticketOrderSize", airticketOrderList.size());

		} else {
			request.setAttribute("toAo", toAo);
			request.setAttribute("fromAo", fromAo);
			request.setAttribute("air", air);
			request.setAttribute("airticketOrderSize", airticketOrderList.size());

		}
		// 操作日志
		if (air.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
			saveAirticketTicketLog(air.getId(),air.getGroupMarkNo(), uri.getUser(),
					request, TicketLog.TYPE_101);
		}
		if (air.getTranType() == AirticketOrder.TRANTYPE_3) {// 退票
			saveAirticketTicketLog(air.getId(),air.getGroupMarkNo(), uri.getUser(),
					request, TicketLog.TYPE_107);
		}
	}

	// 修改的团队利润统计信息（客户，航空）
	public void updateAirticketOrderAgentAvia(
			AirticketOrder airticketOrderForm, long airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(airticketOrderId);
		List<AirticketOrder> airticketOrderList = airticketOrderDAO
				.listByGroupMarkNo(airticketOrder.getGroupMarkNo());
		if (airticketOrderList.size() == 1)// 利润统计添加
		{
			insertTeamTradingOrder(airticketOrderForm, uri, request, response);
		}
		if (airticketOrderList.size() > 1)// 利润统计修改
		{
			updateTeamAirticketOrderAgentAvia(airticketOrderForm,
					airticketOrderId, uri, request, response);
		}
	}

	// 修改团队订单信息
	public void updateTeamAirticketOrder(AirticketOrder airticketOrderForm,
			UserRightInfo uri, long agentNo, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String[] flightCode = request.getParameterValues("flightCode");// 获取页面航班号数组
		String[] flightId = request.getParameterValues("flightId");// 航班ID(页面隐藏域里的ID)
		String[] startPoint = request.getParameterValues("startPoint");
		String[] endPoint = request.getParameterValues("endPoint");
		String[] boardingTime = request.getParameterValues("boardingTime");
		String[] flightClass = request.getParameterValues("flightClass");
		String[] discount = request.getParameterValues("discount");

		String[] ticketPrice = request.getParameterValues("ticketPrice");// 票面价
		String[] adultAirportPrice = request
				.getParameterValues("adultAirportPrice");
		String[] adultFuelPrice = request.getParameterValues("adultFuelPrice");
		String[] childAirportPrice = request
				.getParameterValues("childAirportPrice");
		String[] childfuelPrice = request.getParameterValues("childfuelPrice");
		String[] babyAirportPrice = request
				.getParameterValues("babyAirportPrice");
		String[] babyfuelPrice = request.getParameterValues("babyfuelPrice");

		if (agentNo > 0) {

			AirticketOrder airticketOrder = airticketOrderDAO
					.getAirticketOrderById(airticketOrderForm.getId());

			Agent agent = agentDAO.getAgentByid(agentNo);
			airticketOrder.setAgent(agent);

			airticketOrder.setAirOrderNo(airticketOrderForm.getAirOrderNo());// 订单号
			airticketOrder.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
			airticketOrder.setDrawPnr(airticketOrderForm.getDrawPnr());
			airticketOrder.setSubPnr(airticketOrderForm.getSubPnr());
			airticketOrder.setBigPnr(airticketOrderForm.getBigPnr());
			airticketOrder.setTotalTicketPrice(airticketOrderForm
					.getTotlePrice());// 总票面价
			airticketOrder.setTicketPrice(airticketOrder.getTicketPrice());// 票面价
			airticketOrder.setRebate(airticketOrderForm.getRebate());

			airticketOrder
					.setAdultCount(airticketOrderForm.getTeamAdultCount());// 成人数
			airticketOrder
					.setChildCount(airticketOrderForm.getTeamChildCount());// 儿童数
			airticketOrder.setBabyCount(airticketOrderForm.getTeamBabyCount());// 婴儿数
			airticketOrder.setAirportPrice(airticketOrder.getAirportPrice());// 机建税
			airticketOrder.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
			airticketOrder.setTotalAirportPrice(airticketOrderForm
					.getAirportPrice());// 总机建税
			airticketOrder.setTotalFuelPrice(airticketOrderForm.getFuelPrice());// 总燃油税
			airticketOrder.setEntryTime(airticketOrder.getEntryTime());// 录单时间
			airticketOrder.setOptTime(airticketOrder.getOptTime());// 操作时间
			airticketOrder
					.setTeamaddPrice(airticketOrderForm.getTeamAddPrice());// 团队加价
			airticketOrder.setAgentaddPrice(airticketOrderForm
					.getAgentAddPrice());// 客户加价
			airticketOrder.setTotalAmount(airticketOrderForm.getTotalAmount());// 订单金额（总金额）

			airticketOrder.setDocumentPrice(airticketOrderForm
					.getDocumentPrice());
			airticketOrder.setInsurancePrice(airticketOrderForm
					.getInsurancePrice());
			airticketOrder.setHandlingCharge(airticketOrderForm
					.getHandlingCharge());
			airticketOrder.setTicketType(airticketOrder.getTicketType());// 机票类型
			airticketOrder.setTranType(airticketOrderForm.getTranType());// 交易类型
			airticketOrder.setDrawer(airticketOrderForm.getDrawer()); // 个出票人

			if (airticketOrderForm.getTranType() == AirticketOrder.TRANTYPE_3)// 退票

				airticketOrder.setAdultCount(airticketOrderForm
						.getTeamAdultCount());// 成人数
			airticketOrder
					.setChildCount(airticketOrderForm.getTeamChildCount());// 儿童数
			airticketOrder.setBabyCount(airticketOrderForm.getTeamBabyCount());// 婴儿数
			airticketOrder.setAirportPrice(airticketOrder.getAirportPrice());// 机建税
			airticketOrder.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
			airticketOrder.setTotalAirportPrice(airticketOrderForm
					.getAirportPrice());// 总机建税
			airticketOrder.setTotalFuelPrice(airticketOrderForm.getFuelPrice());// 总燃油税
			airticketOrder.setEntryTime(airticketOrder.getEntryTime());// 录单时间
			airticketOrder.setOptTime(airticketOrder.getOptTime());// 操作时间
			airticketOrder
					.setTeamaddPrice(airticketOrderForm.getTeamAddPrice());// 团队加价
			airticketOrder.setAgentaddPrice(airticketOrderForm
					.getAgentAddPrice());// 客户加价

			airticketOrder.setDocumentPrice(airticketOrderForm
					.getDocumentPrice());
			airticketOrder.setInsurancePrice(airticketOrderForm
					.getInsurancePrice());
			airticketOrder.setHandlingCharge(airticketOrderForm
					.getHandlingCharge());
			airticketOrder.setTicketType(airticketOrder.getTicketType());// 机票类型
			airticketOrder.setTranType(airticketOrderForm.getTranType());// 交易类型
			airticketOrder.setDrawer(airticketOrderForm.getDrawer()); // 个出票人

			if (airticketOrderForm.getTranType() == AirticketOrder.TRANTYPE_3)// 退票

			{
				airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);
			}
			if (airticketOrderForm.getTranType() == AirticketOrder.TRANTYPE__1)// 销售
			{
				airticketOrder.setTranType(AirticketOrder.TRANTYPE__1);
			}
			airticketOrderDAO.update(airticketOrder);

			// 航班信息
			for (int i = 0; i < flightCode.length; i++) {
				Flight flight = flightDAO.getFlightById(Long
						.parseLong(flightId[i]));
				flight.setFlightCode(flightCode[i].toString());// 航班号
				flight.setStartPoint(startPoint[i].toString());// 出发地
				flight.setEndPoint(endPoint[i].toString());
				flight.setBoardingTime(DateUtil.getTimestamp(boardingTime[i]
						.toString(), "yyyy-MM-dd"));
				flight.setFlightClass(flightClass[i].toString());
				flight.setDiscount(discount[i].toString());

				long fliTicketPrice = Long.parseLong(ticketPrice[i]);
				long airportPriceAdult = Long.parseLong(adultAirportPrice[i]);
				long fuelPriceAdult = Long.parseLong(adultFuelPrice[i]);
				long airportPriceChild = Long.parseLong(childAirportPrice[i]);
				long fuelPriceChild = Long.parseLong(childfuelPrice[i]);
				long airportPriceBaby = Long.parseLong(babyAirportPrice[i]);
				long fuelPriceBaby = Long.parseLong(babyfuelPrice[i]);

				flight.setTicketPrice(BigDecimal.valueOf(fliTicketPrice));// 票面价
				flight.setAirportPriceAdult(BigDecimal
						.valueOf(airportPriceAdult));
				flight.setFuelPriceAdult(BigDecimal.valueOf(fuelPriceAdult));
				flight.setAirportPriceChild(BigDecimal
						.valueOf(airportPriceChild));
				flight.setFuelPriceChild(BigDecimal.valueOf(fuelPriceChild));
				flight
						.setAirportPriceBaby(BigDecimal
								.valueOf(airportPriceBaby));
				flight.setFuelPriceBaby(BigDecimal.valueOf(fuelPriceBaby));
				flight.setStatus(flight.getStatus());// 状态
				flight.setAirticketOrder(airticketOrder);
				flightDAO.update(flight);
			}

			// 操作日志
			saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri
					.getUser(), request, TicketLog.TYPE_33);
		}
	}

	// 删除订单(改变状态)
	public void deleteAirticketOrder(String airticketOrderId)
			throws AppException {

		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(Long.parseLong(airticketOrderId));
		airticketOrder.setStatus(AirticketOrder.STATUS_88);// 将订单状态变为已废弃		
		airticketOrderDAO.update(airticketOrder);
		
		deleteStatementByAirticketOrder(airticketOrder);	
	}
	
	// 删除订单的关联结算记录(改变状态)
	public void deleteStatementByAirticketOrder(AirticketOrder airticketOrder)
			throws AppException {		
		List statementList = statementDAO.getStatementListByOrder(airticketOrder.getId(), Statement.ORDERTYPE_1);
		for (int i = 0; i < statementList.size(); i++) {
			Statement statement=(Statement) statementList.get(i);
			statement.setStatus(Statement.STATUS_88);// 已废弃
			statementDAO.update(statement);
		}		
	}

	// 添加团队利润
	public void insertTeamTradingOrder(AirticketOrder airticketOrderForm,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		long airticketOrderId = airticketOrderForm.getAirticketOrderId();// 机票订单ID
		if (airticketOrderId > 0) {
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(airticketOrderId);

			if (airticketOrder.getAgent() != null) {
				Agent agent = agentDAO.getAgentByid(airticketOrder.getAgent().getId());

				// 支出（买入/退票）
				if (airticketOrder.getTranType()==AirticketOrder.TRANTYPE__2 || 
						airticketOrder.getTranType()==AirticketOrder.TRANTYPE_3) {

					airticketOrder.setTotalAirportPrice(airticketOrder.getTotalAirportPrice());// 总建税
					airticketOrder.setTotalFuelPrice(airticketOrder.getTotalFuelPrice());// 总燃油税
					airticketOrder.setOverTicketPrice(BigDecimal.valueOf(0)); // 多收票价
					airticketOrder.setOverAirportfulePrice(BigDecimal.valueOf(0));// 多收税
					airticketOrder.setIncomeretreatCharge(airticketOrderForm.getTxtTargetTGQFee());// 付退票手续费
					airticketOrder.setCommissonCount(airticketOrderForm.getTxtAgentT());// 返点
					airticketOrder.setRakeoffCount(airticketOrderForm.getTxtAgent());// 月底返点
					airticketOrder.setHandlingCharge(airticketOrderForm.getTxtCharge());// 手续费
					airticketOrder.setProxyPrice(BigDecimal.valueOf(0));// 应付出团代理费（未返）
					airticketOrder.setEntryTime(airticketOrder.getEntryTime());// 录单时间
					airticketOrder.setOptTime(airticketOrder.getOptTime());// 操作时间
					airticketOrder.setDocumentPrice(airticketOrder.getDocumentPrice());
					airticketOrder.setInsurancePrice(airticketOrder.getInsurancePrice());
					airticketOrder.setTicketType(airticketOrder.getTicketType());// 机票类型
					airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型：买入
					airticketOrder.setDrawer(airticketOrder.getDrawer()); // 个出票人
					if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
						airticketOrder.setStatus(AirticketOrder.STATUS_111);// 新订单,等待申请
					}
					if (airticketOrder.getTranType() == 3)// 退票
					{
						airticketOrder.setStatus(AirticketOrder.STATUS_107);// 退票订单，等待审核
					}
					airticketOrder.setAgent(agent);
					airticketOrderDAO.update(airticketOrder);// 修改客户（多收票价）
				}

				// 收入（卖出）
					// 创建机票订单表(所对应的结算表状态是收入)--客户
					AirticketOrder teamairticketOrder = new AirticketOrder();
					teamairticketOrder.setAirOrderNo(airticketOrder.getAirOrderNo());// 订单号
					teamairticketOrder.setGroupMarkNo(airticketOrder.getGroupMarkNo());// 订单组编号
					teamairticketOrder.setDrawPnr(airticketOrder.getDrawPnr());
					teamairticketOrder.setSubPnr(airticketOrder.getSubPnr());
					teamairticketOrder.setBigPnr(airticketOrder.getBigPnr());
					teamairticketOrder.setTotalTicketPrice(airticketOrder.getTotalTicketPrice());// 总票面价
					teamairticketOrder.setRebate(airticketOrder.getRebate());

					teamairticketOrder.setAdultCount(airticketOrder.getAdultCount());
					teamairticketOrder.setChildCount(airticketOrder.getChildCount());
					teamairticketOrder.setBabyCount(airticketOrder.getBabyCount());
					teamairticketOrder.setMemo(airticketOrderForm.getTxtRemark());// 备注
					teamairticketOrder.setTotalAirportPrice(airticketOrder.getTotalAirportPrice());// 总建税
					teamairticketOrder.setTotalFuelPrice(airticketOrder.getTotalFuelPrice());// 总燃油税
					teamairticketOrder.setOverTicketPrice(airticketOrderForm.getTxtAmountMore()); // 多收票价
					teamairticketOrder.setOverAirportfulePrice(airticketOrderForm.getTxtTaxMore());// 多收税
					teamairticketOrder.setIncomeretreatCharge(airticketOrderForm.getTxtSourceTGQFee());// 收退票手续费
					teamairticketOrder.setCommissonCount(airticketOrderForm.getTxtAgents());// 返点
					teamairticketOrder.setRakeoffCount(airticketOrderForm.getTxtAgent());// 月底返点
					teamairticketOrder.setProxyPrice(airticketOrderForm.getTxtUnAgentFeeTeams());// 应付出团代理费（未返）
					teamairticketOrder.setTeamaddPrice(airticketOrder.getTeamaddPrice());// 团队加价
					teamairticketOrder.setAgentaddPrice(airticketOrder.getAgentaddPrice());// 客户加价
					
					teamairticketOrder.setTotalAmount(airticketOrderForm.getTxtTotalAmount());//总金额
					teamairticketOrder.setCommission(airticketOrderForm.getTxtAgentFeeTeams());//现返佣金
					teamairticketOrder.setRakeOff(airticketOrder.getRakeOff());//后返佣金
					teamairticketOrder.setUnsettledAccount(airticketOrderForm.getTxtUnAgentFeeTeams());//未结欠款
					
					teamairticketOrder.setEntryTime(airticketOrder.getEntryTime());// 录单时间
					teamairticketOrder.setOptTime(airticketOrder.getOptTime());// 操作时间
					teamairticketOrder.setDocumentPrice(airticketOrder.getDocumentPrice());
					teamairticketOrder.setInsurancePrice(airticketOrder.getInsurancePrice());
					teamairticketOrder.setTicketType(airticketOrder.getTicketType());// 机票类型
					teamairticketOrder.setTranType(AirticketOrder.TRANTYPE__1);// 交易类型:卖出(销售)
					teamairticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型：卖出
					teamairticketOrder.setDrawer(airticketOrder.getDrawer()); // 个出票人

					if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE__2) {// 买入
						teamairticketOrder.setStatus(AirticketOrder.STATUS_111);// 新订单,等待申请
					}
					if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3)// 退票
					{
						teamairticketOrder.setStatus(AirticketOrder.STATUS_107);// 退票订单，等待审核
					}
					teamairticketOrder.setAgent(agent);
					teamairticketOrder.setEntryOperator(uri.getUser().getUserNo());
					airticketOrderDAO.save(teamairticketOrder);// 复制一张订单数据(付出)
					long airOrderId = teamairticketOrder.getId();// 机票订单ID

					// 航班表
					if (airOrderId > 0) {
						AirticketOrder air = airticketOrderDAO.getAirticketOrderById(airOrderId);
						List<Flight> flightList = flightDAO.getFlightListByOrderId(airticketOrderId);
						for (int i = 0; i < flightList.size(); i++) {
							Flight flight = flightList.get(i);
							Flight fl = new Flight();
							fl.setAirticketOrder(air);
							fl.setFlightCode(flight.getFlightCode());
							fl.setStartPoint(flight.getStartPoint());
							fl.setEndPoint(flight.getEndPoint());
							fl.setBoardingTime(flight.getBoardingTime());
							fl.setFlightClass(flight.getFlightClass());
							fl.setDiscount(flight.getDiscount());
							fl.setTicketPrice(flight.getTicketPrice());// 票面价
							fl.setAirportPriceAdult(flight.getAirportPriceAdult());// 机建税（成人）
							fl.setFuelPriceAdult(flight.getFuelPriceAdult());// 燃油税（成人）
							fl.setAirportPriceChild(flight.getAirportPriceChild());// 机建税（儿童）
							fl.setFuelPriceChild(flight.getFuelPriceChild());// 燃油税（儿童）
							fl.setAirportPriceBaby(flight.getAirportPriceBaby());// 机建税（婴儿）
							fl.setFuelPriceBaby(flight.getFuelPriceBaby());// 燃油税（婴儿）
							fl.setStatus(flight.getStatus());
							flightDAO.save(fl);
						}
					}
				}
				// 操作日志
				saveAirticketTicketLog(airticketOrder.getId(),airticketOrder.getGroupMarkNo(), uri
						.getUser(), request, TicketLog.TYPE_34);
			}
	}

	// 编辑团队机票订单利润(显示)
	public void updaTempAirticketOrderPrice(AirticketOrder airticketOrderForm,
			long airticketOrderId, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		if (airticketOrderId > 0) {
			AirticketOrder airticketOrder = airticketOrderDAO
					.getAirticketOrderById(airticketOrderId);
			List<AirticketOrder> airticketOrderList = airticketOrderDAO
					.listByGroupMarkNo(airticketOrder.getGroupMarkNo());
			for (int i = 0; i < airticketOrderList.size(); i++) {
				AirticketOrder airticketOrderTeam = airticketOrderList.get(i);
				if (airticketOrderTeam.getTranType() == AirticketOrder.TRANTYPE__1)// 卖出
				{
					request.setAttribute("airticketOrderTeamAgent",
							airticketOrderTeam);
				} else if (airticketOrderTeam.getTranType() == AirticketOrder.TRANTYPE__2)// 买入
				{
					request.setAttribute("airticketOrderTeamAvia",
							airticketOrderTeam);
				} else if (airticketOrderTeam.getTranType() == AirticketOrder.TRANTYPE_3)// 退票
				{
					request.setAttribute("airticketOrderRefundTeamAgent",
							airticketOrderTeam);
				} else {
					request.setAttribute("airticketOrderTeam",
							airticketOrderTeam);
				}
			}
		}

	}

	// 修改图队利润统计(编辑利润统计)
	public void updateTeamAirticketOrderAgentAvia(
			AirticketOrder airticketOrderForm, long airticketOrderId,
			UserRightInfo uri, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		AirticketOrder airticketOrder = airticketOrderDAO
				.getAirticketOrderById(airticketOrderId);
		List<AirticketOrder> airticketOrderList = airticketOrderDAO
				.listByGroupMarkNo(airticketOrder.getGroupMarkNo());
		for (int i = 0; i < airticketOrderList.size(); i++) {
			AirticketOrder airticketOrderAgentAvia = airticketOrderList.get(i);
			Agent agent = agentDAO.getAgentByid(airticketOrderAgentAvia
					.getAgent().getId());
			if (airticketOrderAgentAvia.getTranType() == AirticketOrder.TRANTYPE__1)// 卖出
			{
				// 机票订单表
				airticketOrderAgentAvia.setProxyPrice(airticketOrderForm.getTxtUnAgentFeeTeams());// 应付出团代理费（未返）
				airticketOrderAgentAvia.setOverAirportfulePrice(airticketOrderForm.getTxtTaxMore());// 多收票价
				airticketOrderAgentAvia.setOverTicketPrice(airticketOrderForm.getTxtAmountMore());// 多收税
				airticketOrderAgentAvia.setCommissonCount(airticketOrderForm.getTxtAgents());// 返点
				airticketOrderAgentAvia.setIncomeretreatCharge(airticketOrderForm.getTxtSourceTGQFee());// 收退票手续费
				airticketOrderAgentAvia.setMemo(airticketOrderForm.getTxtRemark());// 备注

				airticketOrderAgentAvia.setTotalAmount(airticketOrderForm.getTxtTotalAmount());// 实收款
				airticketOrderAgentAvia.setCommission(airticketOrderForm.getTxtAgentFeeTeams());// 现返佣金
				airticketOrderAgentAvia.setRakeOff(BigDecimal.valueOf(0));// 后返佣金
				airticketOrderAgentAvia.setUnsettledAccount(airticketOrderForm.getTxtUnAgentFeeTeams());// （未结欠款）未返

				airticketOrderAgentAvia.setAgent(agent);
				airticketOrderDAO.update(airticketOrderAgentAvia);
			} else if (airticketOrderAgentAvia.getTranType() == AirticketOrder.TRANTYPE__2)// 买入-----航空公司
			{
				// 机票订单表
				airticketOrderAgentAvia.setCommissonCount(airticketOrderForm.getTxtAgentT());// 返点
				airticketOrderAgentAvia.setRakeoffCount(airticketOrderForm.getTxtAgent());// 月底返点
				airticketOrderAgentAvia.setHandlingCharge(airticketOrderForm.getTxtCharge());// 手续费
				airticketOrderAgentAvia.setIncomeretreatCharge(airticketOrderForm.getTxtTargetTGQFee());// 付退票手续费
				airticketOrderAgentAvia.setTotalAmount(airticketOrderForm.getTxtTAmount());// 实付款
				airticketOrderAgentAvia.setRakeOff(airticketOrderForm.getTxtAgentFeeCarrier());// 后返佣金---月底返代理费

				airticketOrderAgentAvia.setAgent(agent);
				airticketOrderDAO.update(airticketOrderAgentAvia);
			} else if (airticketOrderAgentAvia.getTranType() == AirticketOrder.TRANTYPE_3)// 退票-----航空公司
			{
				// 机票订单表
				airticketOrderAgentAvia.setCommissonCount(airticketOrderForm.getTxtAgentT());// 返点
				airticketOrderAgentAvia.setRakeoffCount(airticketOrderForm.getTxtAgent());// 月底返点
				airticketOrderAgentAvia.setHandlingCharge(airticketOrderForm.getTxtCharge());// 手续费
				airticketOrderAgentAvia.setIncomeretreatCharge(airticketOrderForm.getTxtTargetTGQFee());// 收退票手续费
				airticketOrderAgentAvia.setTotalAmount(airticketOrderForm.getTxtTAmount());// 实付款
				airticketOrderAgentAvia.setRakeOff(airticketOrderForm.getTxtAgentFeeCarrier());// 后返佣金---月底返代理费

				airticketOrderAgentAvia.setAgent(agent);
				airticketOrderDAO.update(airticketOrderAgentAvia);
			}
		}
	}

	// 显示订单详细信息
	public String viewAirticketOrderPage(HttpServletRequest request) throws AppException {
		String forwardPage="";
		String aircketOrderIdString = request.getParameter("aircketOrderId");
		if (aircketOrderIdString != null && "".equals(aircketOrderIdString)==false) {	
			List<AirticketOrder> airticketOrderList = new ArrayList<AirticketOrder>();
			List<Statement> statementList=new ArrayList<Statement>();
			List<Passenger> passengerList = new ArrayList<Passenger>();
			List<Flight> flightList = new ArrayList<Flight>();	
			List<TicketLog> ticketLogList=new ArrayList<TicketLog>();
			
			AirticketOrder airticketOrder = airticketOrderDAO.getAirticketOrderById(Long.parseLong(aircketOrderIdString.trim()));
			String groupMarkNo=airticketOrder.getGroupMarkNo();
			long tranType=airticketOrder.getTranType();			

			List<AirticketOrder> airticketList = airticketOrderDAO.listByGroupMarkNo(groupMarkNo);
			String ordersString="";
			
			for (int i = 0; i < airticketList.size(); i++) {
				AirticketOrder tempOrder=airticketList.get(i);
				if (tempOrder.getStatus()!=null) {
					if (tempOrder.getStatus()!=AirticketOrder.STATUS_88) {
						if (tranType == AirticketOrder.TRANTYPE__2 || tranType == AirticketOrder.TRANTYPE__1) {// 买入/卖出
							airticketOrderList.add(tempOrder);
							ordersString+=tempOrder.getId()+",";
						}
						if (tranType == AirticketOrder.TRANTYPE_3) {// 退票
							airticketOrderList.add(tempOrder);
							ordersString+=tempOrder.getId()+",";
						}
						if (tranType == AirticketOrder.TRANTYPE_5) {// 改签
							airticketOrderList.add(tempOrder);
							ordersString+=tempOrder.getId()+",";
						}
					}				
				}				
			}	
			
			if (ordersString.length()>1) {
				ordersString=ordersString.substring(0,ordersString.length()-1);
				passengerList = passengerDAO.listByairticketOrderId(airticketOrder.getId());
				flightList = flightDAO.getFlightListByOrderId(airticketOrder.getId());	
				statementList =statementDAO.getStatementListByOrders(ordersString,Statement.ORDERTYPE_1);		
			}
			request.setAttribute("airticketOrder", airticketOrder);
			request.setAttribute("statementList", statementList);
			request.setAttribute("airticketOrderList", airticketOrderList);
			request.setAttribute("flightList", flightList);
			request.setAttribute("passengerList", passengerList);	
			ticketLogList=ticketLogDAO.getTicketLogByOrderNo(groupMarkNo);	
			request.setAttribute("ticketLogList", ticketLogList);
			forwardPage=groupMarkNo;
			}else{
				forwardPage="ERROR";
			}
		return forwardPage;
	}

	// ------dwr
	public AirticketOrder getAirticketOrderByMarkNo(String markNo,
			String tranType) throws AppException {

		AirticketOrder ao = new AirticketOrder();
		List airticketOrderList = airticketOrderDAO
				.listByGroupMarkNoAndTranType(markNo, tranType);
		if (airticketOrderList != null && airticketOrderList.size() > 0) {
			ao = (AirticketOrder) airticketOrderList.get(0);
		}
		return ao;
	}
	
	public AirticketOrder getAirticketOrderByMarkNoStatus(String markNo,
			String tranType,String status) throws AppException {

		AirticketOrder ao = new AirticketOrder();
		List airticketOrderList = airticketOrderDAO.listByGroupMarkNoAndTranTypeStatus(markNo, tranType,status);
		if (airticketOrderList != null && airticketOrderList.size() > 0) {
			ao = (AirticketOrder) airticketOrderList.get(0);
		}
		return ao;
	}
	
	

	/**
	 * 保存结算记录
	**/
	public void saveStatementByAirticketOrder(AirticketOrder order,
			SysUser sysUser, long statementType, long statementStatus)throws AppException {
		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setOrderId(order.getId());
		statement.setOrderType(Statement.ORDERTYPE_1);

		if (statementType == Statement.type_1) {
			statement.setToAccount(order.getAccount());
		} else if (statementType == Statement.type_2) {
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
		myLog.info(sysUser.getUserName() + "-确认-" + statement.getTypeInfo()+statement.getTotalAmount()+" For order id:"+order.getId());
	}
	
	public String getForwardPageByOrderType(AirticketOrder airticketOrder){
		String forwardPage="";
		long tranType=airticketOrder.getTranType();

		if (tranType==AirticketOrder.TRANTYPE__1||tranType==AirticketOrder.TRANTYPE__2) {
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		}else if (tranType==AirticketOrder.TRANTYPE_3||tranType==AirticketOrder.TRANTYPE_4) {
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE2+"";
		}else if(tranType==AirticketOrder.TRANTYPE_5) {
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE3+"";
		}else{
			forwardPage=AirticketOrder.ORDER_GROUP_TYPE1+"";
		}
		return forwardPage;
	}
	
	/**
	 * 保存结算记录
	**/
	public void saveStatementByAirticketOrder(AirticketOrder order,
			SysUser sysUser, long statementType, long statementStatus,Timestamp timeString)throws AppException {
		// 结算
		Statement statement = new Statement();
		statement.setStatementNo(noUtil.getStatementNo());// 结算单号
		statement.setOrderId(order.getId());
		statement.setOrderType(Statement.ORDERTYPE_1);

		if (statementType == Statement.type_1) {
			statement.setToAccount(order.getAccount());
		} else if (statementType == Statement.type_2) {
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
		myLog.info(sysUser.getUserName() + "-确认-" + statement.getTypeInfo()+statement.getTotalAmount()+" For order id:"+order.getId());
	}

	public void saveAirticketTicketLog(long orderid,String groupNo, SysUser sysUser,
			HttpServletRequest request, long ticketLogType) throws AppException {
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderNo(groupNo);
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(sysUser);// 操作员
		ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(ticketLogType);
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
		AirticketLogUtil myLog = new AirticketLogUtil(true, false,
				AirticketOrderBizImp.class, "");
		myLog.info(sysUser.getUserName() + "-" + ticketLog.getTypeInfo()+groupNo);
	}

	public void saveAirticketTicketLog(long orderId,String groupNo, SysUser sysUser,
			long ticketLogType) throws AppException {
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderId(orderId);		
		ticketLog.setOrderNo(groupNo);
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(sysUser);// 操作员
		// ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(ticketLogType);
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);
		LogUtil myLog = new AirticketLogUtil(true, false,
				AirticketOrderBizImp.class, "");
		myLog.info(sysUser.getUserName() + "-" + ticketLog.getTypeInfo()+groupNo);
	}

	// B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf)
			throws AppException {
		return airticketOrderDAO.b2cAirticketOrderList(rlf);
	}

	// 团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf)
			throws AppException {
		return airticketOrderDAO.teamAirticketOrderList(rlf);
	}

	// 根据结算ID查询
	public AirticketOrder getAirticketOrderByStatementId(long statementId)
			throws AppException {
		return airticketOrderDAO.getAirticketOrderByStatementId(statementId);
	}

	// 根据 预定pnr查询
	public AirticketOrder getAirticketOrderBysubPnr(String subPnr)
			throws AppException {
		return airticketOrderDAO.getAirticketOrderBysubPnr(subPnr);
	}

	// 根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String subPnr,
			long businessType, long tranType) throws AppException {
		return airticketOrderDAO.getAirticketOrderForRetireUmbuchen(subPnr,
				businessType, tranType);
	}

	public boolean checkPnrisToday(AirticketOrder airticketOrder)
			throws AppException {
		return airticketOrderDAO.checkPnrisToday(airticketOrder);
	}

	public boolean checkPnrisMonth(AirticketOrder airticketOrder)
			throws AppException {
		return airticketOrderDAO.checkPnrisMonth(airticketOrder);
	}

	public AirticketOrder getDrawedAirticketOrderByGroupMarkNo(
			String groupMarkNo, long tranType) throws AppException {
		return airticketOrderDAO.getDrawedAirticketOrderByGroupMarkNo(
				groupMarkNo, tranType);
	}

	public List<AirticketOrder> listByGroupMarkNo(String groupMarkNo)
			throws AppException {
		return airticketOrderDAO.listByGroupMarkNo(groupMarkNo);
	}

	public List<AirticketOrder> listByGroupMarkNoAndTranType(
			String groupMarkNo, String tranType) throws AppException {
		return airticketOrderDAO.listByGroupMarkNoAndTranType(groupMarkNo,
				tranType);
	}

	public List<AirticketOrder> listByGroupMarkNoAndBusinessTranType(
			String groupMarkNo, String tranType, String businessType)
			throws AppException {
		return airticketOrderDAO.listByGroupMarkNoAndBusinessTranType(
				groupMarkNo, tranType, businessType);
	}

	public List<AirticketOrder> getAirticketOrderListByPNR(String subPnr,
			String tranType) throws AppException {
		return airticketOrderDAO.getAirticketOrderListByPNR(subPnr, tranType);
	}

	public List list(AirticketOrderListForm rlf) throws AppException {
		return airticketOrderDAO.list(rlf);
	}
	
	public List list() throws AppException {
		return airticketOrderDAO.list();
	}

	public void delete(long id) throws AppException {
		airticketOrderDAO.delete(id);
	}

	public long save(AirticketOrder airticketOrder) throws AppException {
		return airticketOrderDAO.save(airticketOrder);
	}

	public long update(AirticketOrder airticketOrder) throws AppException {
		return airticketOrderDAO.update(airticketOrder);
	}

	public AirticketOrder getAirticketOrderById(long airtickeOrderId)
			throws AppException {
		return airticketOrderDAO.getAirticketOrderById(airtickeOrderId);
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}

	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public void setPassengerDAO(PassengerDAO passengerDAO) {
		this.passengerDAO = passengerDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}

	public void setNoUtil(NoUtil noUtil) {
		this.noUtil = noUtil;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

	public void setPlatComAccountDAO(PlatComAccountDAO platComAccountDAO) {
		this.platComAccountDAO = platComAccountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setFlightPassengerBiz(FlightPassengerBiz flightPassengerBiz) {
		this.flightPassengerBiz = flightPassengerBiz;
	}

}
