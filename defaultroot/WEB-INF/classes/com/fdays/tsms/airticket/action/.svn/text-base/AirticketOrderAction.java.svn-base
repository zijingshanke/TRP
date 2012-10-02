package com.fdays.tsms.airticket.action;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.airticket.biz.PassengerBiz;
import com.fdays.tsms.airticket.biz.TempPNRBiz;
import com.fdays.tsms.airticket.util.ParseBlackUtil;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseAction;
import com.neza.base.Inform;

public class AirticketOrderAction extends BaseAction {

	public TempPNRBiz tempPNRBiz;
	public AirticketOrderBiz airticketOrderBiz;
	//public AgentBiz agentBiz;
	//public StatementBiz statementBiz;
	//public FlightBiz flightBiz;
	public PassengerBiz passengerBiz;
	public TicketLogBiz ticketLogBiz;
  
	/***************************************************************************
	 * 通过PNR接口获取数据   sc
	 **************************************************************************/
	public ActionForward airticketOrderByPNR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			
	
		if(airticketOrderFrom.getPnr()!=null&&!"".equals(airticketOrderFrom.getPnr().trim())){
			System.out.println("airticketOrderFrom.getPnr() ---"+airticketOrderFrom.getPnr());
	    TempPNR  tempPNR=tempPNRBiz.getTempPNRByPnr(airticketOrderFrom.getPnr());
			if (tempPNR != null && tempPNR.getRt_parse_ret_value()!=0L) {
               request.setAttribute("tempPNR", tempPNR);
               airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
               //设置临时会话
               UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
               uri.setTempPNR(tempPNR);
               forwardPage = airticketOrderFrom.getForwardPage();
			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
				request.setAttribute("inf",inf);
				System.out.println("PNR 错误");
			}
		}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf",inf);
			e.printStackTrace();
		}
		
		return (mapping.findForward(forwardPage));
	}
	
	
	/***************************************************************************
	 * 通过黑屏PNR接口获取数据   sc
	 **************************************************************************/
	public ActionForward airticketOrderByBlackPNR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			
		String pnrInfo=request.getParameter("pnrInfo");
		if(pnrInfo!=null&&!"".equals(pnrInfo.trim())){
			
			
	    TempPNR  tempPNR=ParseBlackUtil.getTempPNRByBlack(pnrInfo);
			if (tempPNR != null) {
               request.setAttribute("tempPNR", tempPNR);
               airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
               //设置临时会话
               UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
               uri.setTempPNR(tempPNR);
               forwardPage = airticketOrderFrom.getForwardPage();
			} else {
				inf.setMessage("PNR 信息错误！");
				inf.setBack(true);
				forwardPage = "inform";
				request.setAttribute("inf",inf);
				
			}
		}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf",inf);
			e.printStackTrace();
		}
		
		return (mapping.findForward(forwardPage));
	}
	
	
	/***************************************************************************
	 * 通过PNR获取内部数据   sc
	 **************************************************************************/
	public ActionForward airticketOrderBysuPNR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			
	
		if(airticketOrderFrom.getPnr()!=null&&!"".equals(airticketOrderFrom.getPnr().trim())){
			AirticketOrder airticketOrder=	airticketOrderBiz.getAirticketOrderBysubPnr(airticketOrderFrom.getPnr());
		
	  
			if (airticketOrder != null && airticketOrder.getId()!=0L) {
               request.setAttribute("airticketOrder", airticketOrder);
              
               forwardPage = airticketOrderFrom.getForwardPage();
			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
				request.setAttribute("inf",inf);
				System.out.println("PNR 错误");
			}
		}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf",inf);
			e.printStackTrace();
		}
		
		return (mapping.findForward(forwardPage));
	}
	
	/***************************************************************************
	 * 通过PNR获取外部数据   sc
	 **************************************************************************/
	public ActionForward airticketOrderByOutPNR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
	
		if(airticketOrderFrom.getPnr()!=null&&!"".equals(airticketOrderFrom.getPnr().trim())){
	    TempPNR  tempPNR=tempPNRBiz.getTempPNRByPnr(airticketOrderFrom.getPnr());
			if (tempPNR != null && tempPNR.getRt_parse_ret_value()!=0L) {
				
               request.setAttribute("tempPNR", tempPNR);
               
               airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
               //设置临时会话
               UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
               uri.setTempPNR(tempPNR);
             //  forwardPage = airticketOrderFrom.getForwardPage();
			} else {
				inf.setMessage("PNR 错误！");
				inf.setBack(true);
				forwardPage = "inform";
				request.setAttribute("inf",inf);
				System.out.println("PNR 错误");
			}
		}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf",inf);
			e.printStackTrace();
		}
		
		return (mapping.findForward(forwardPage));
	}
	/***************************************************************************
	 * 清空  tempPNR  sc
	 **************************************************************************/
	public ActionForward clearTempPNR(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		uri.setTempPNR(null);
		return (mapping.findForward("addTradingOrder"));
	}
	/***************************************************************************
	 * 倒票-订单录入   sc
	 **************************************************************************/
	public ActionForward createTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		TempPNR tempPNR= uri.getTempPNR();
		Inform inf = new Inform();
		try {
		if(tempPNR!=null&&tempPNR.getRt_parse_ret_value()!=0L){
			airticketOrderFrom.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());//总金额
			airticketOrderFrom.getStatement().setActualAmount(airticketOrderFrom.getTotalAmount());//实收款
			airticketOrderFrom.getStatement().setUnsettledAccount(new BigDecimal(0));//未结款
			airticketOrderFrom.getStatement().setCommission(new BigDecimal(0));//现返佣金
			airticketOrderFrom.getStatement().setRakeOff(new BigDecimal(0));//后返佣金
			
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_1);  //订单状态
			airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1); //设置机票类型
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_0);//操作日志 类型
			//创建订单 
			airticketOrderBiz.createPNR(airticketOrderFrom, tempPNR, uri);
			  uri.setTempPNR(null);
			    inf.setMessage("创建成功！");
			    //inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=list");
			    inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="+airticketOrderFrom.getGroupMarkNo());
				forwardPage = "inform";
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
	}
	
	/***************************************************************************
	 * B2C-订单录入   sc
	 **************************************************************************/
	public ActionForward createB2CTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		TempPNR tempPNR= uri.getTempPNR();
		Inform inf = new Inform();
		try {
		if(tempPNR!=null&&tempPNR.getRt_parse_ret_value()!=0L){
			
			airticketOrderFrom.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());//总金额
			airticketOrderFrom.getStatement().setActualAmount(new BigDecimal(0) );//实收款
			airticketOrderFrom.getStatement().setUnsettledAccount(airticketOrderFrom.getTotalAmount());//未结款
			airticketOrderFrom.getStatement().setCommission(new BigDecimal(0));//现返佣金
			airticketOrderFrom.getStatement().setRakeOff(new BigDecimal(0));//后返佣金
			
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_3);  //订单状态
			airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_3); //设置机票类型
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_32);//操作日志 类型
			//创建订单 
			airticketOrderBiz.createPNR(airticketOrderFrom, tempPNR, uri);
			  uri.setTempPNR(null);
			    inf.setMessage("创建成功！");
			   // inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=list");
			    inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="+airticketOrderFrom.getGroupMarkNo());
				forwardPage = "inform";
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
	}
	
	/***************************************************************************
	 * 申请支付 sc
	 **************************************************************************/
	public ActionForward applyTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
	
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setStatus(AirticketOrder.STATUS_2);
			airticketOrder.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());//设置交易金额
			String platformId= request.getParameter("platformId9");
			String companyId= request.getParameter("companyId9");
			String accountId= request.getParameter("accountId9");
			
			if(platformId!=null&&!"".equals(platformId.trim())){
			airticketOrderFrom.setPlatformId(Long.valueOf(platformId));
			}
			if(companyId!=null&&!"".equals(companyId.trim())){
			airticketOrderFrom.setCompanyId(Long.valueOf(companyId));
			}
			if(accountId!=null&&!"".equals(accountId.trim())){
			airticketOrderFrom.setAccountId(Long.valueOf(accountId));
			}
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_2);  //订单状态
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_3);//操作日志 类型
			airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());//日志操作员
			airticketOrderFrom.setStatement_type(Statement.type_1); //买入
			airticketOrderBiz.createApplyTickettOrder(airticketOrderFrom, airticketOrder);
			
			//创建订单 
			  uri.setTempPNR(null);
			    inf.setMessage("操作成功！");
			    //inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=list");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 确认支付 sc
	 **************************************************************************/
	public ActionForward confirmTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
	
			//设置平台号 
			PlatComAccountStore platComAccountStore=new PlatComAccountStore();
			PlatComAccount platComAccount=null;
			if(airticketOrderFrom.getAccountId()!=null&&airticketOrderFrom.getCompanyId()!=null&&airticketOrderFrom.getPlatformId()!=null){
				System.out.println("airticketOrderFrom.getAccountId()=="+airticketOrderFrom.getAccountId());
				platComAccount=platComAccountStore.getPlatComAccountByAllId(airticketOrderFrom.getPlatformId(), airticketOrderFrom.getCompanyId(), airticketOrderFrom.getAccountId());
			}

			
			List<AirticketOrder> listao=airticketOrderBiz.listBygroupMarkNo(airticketOrder.getGroupMarkNo(),"1,2");
			for(AirticketOrder ao :listao){
				
				
			 if (ao.getStatement().getType() == 2	&& platComAccount != null) {// 卖出
					
					ao.getStatement().setToPCAccount(platComAccount);// 收款帐号
				}
					ao.setStatus(AirticketOrder.STATUS_3); //修改订单状态
					ao.setDrawPnr(airticketOrderFrom.getDrawPnr());
					ao.setSubPnr(airticketOrderFrom.getPnr());
					ao.getStatement().setTotalAmount(airticketOrderFrom.getTotalAmount());//设置交易金额
					ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
					airticketOrderBiz.update(ao);
				}
			
			
			 //操作日志
			  TicketLog ticketLog=new TicketLog();
			  ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setStatus(1L);
			  ticketLog.setType(TicketLog.TYPE_7);//操作日志 类型
			  ticketLog.setSysUser(uri.getUser());//日志操作员
			  ticketLogBiz.saveTicketLog(ticketLog);
			
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
				forwardPage = "inform";
	
		  }
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
			
		}
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	
	/***************************************************************************
	 * 出票   sc
	 **************************************************************************/
	public ActionForward ticket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
			
		
	
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setStatus(AirticketOrder.STATUS_5);
			airticketOrder.setDrawPnr(airticketOrderFrom.getDrawPnr());//出票pnr
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_5);//操作日志 类型
			airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());
			//获取乘客信息
			String[] ticketNumber=request.getParameterValues("ticketNumber");
			String[] pId=request.getParameterValues("pId");
			String groupMarkNo=request.getParameter("groupMarkNo");//订单组号
			
			List<AirticketOrder> listao=airticketOrderBiz.listBygroupMarkNo(groupMarkNo,"1,2");
			for(AirticketOrder ao :listao){
				List pa=passengerBiz.listByairticketOrderId(ao.getId());
				for(int i=0;i<pa.size();i++){
					if(ao.getId()>0){
						Passenger passenger	=(Passenger)pa.get(i);
						System.out.println(i+"==="+ticketNumber[i].trim());
						passenger.setTicketNumber(ticketNumber[i].trim());
						passengerBiz.update(passenger);
					}
				
				}
				//修改订单状态
				if(ao.getId()!=airticketOrderFrom.getId()){
					ao.setStatus(AirticketOrder.STATUS_5);
					ao.setDrawPnr(airticketOrderFrom.getDrawPnr());
					ao.setDrawer(uri.getUser().getUserName());//出票人
					ao.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
					airticketOrderBiz.update(ao);
				}
			}
			
			    inf.setMessage("操作成功！");
			    //inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=list");
			    inf.setBack(true);
				forwardPage = "inform";
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	
	
	
	/***************************************************************************
	 * 取消出票 sc
	 **************************************************************************/
	public ActionForward quitTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setMemo(airticketOrderFrom.getMemo());
			airticketOrder.setStatus(AirticketOrder.STATUS_4);
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
			airticketOrderBiz.update(airticketOrder);
			
			 //操作日志
			  TicketLog ticketLog=new TicketLog();
			  ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setStatus(1L);
			  ticketLog.setType(TicketLog.TYPE_4);//操作日志 类型
			  ticketLog.setSysUser(uri.getUser());//日志操作员
			  ticketLogBiz.saveTicketLog(ticketLog);
						
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}

	/***************************************************************************
	 * 创建退费 订单 sc
	 **************************************************************************/
	public ActionForward addRetireTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			
			if(airticketOrderFrom.getTranType()==AirticketOrder.TRANTYPE_3){//3：退票
				
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_19);  //订单状态
				airticketOrderFrom.getStatement().setType(Statement.type_1);//结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_9);//操作日志 类型
				
			}else if(airticketOrderFrom.getTranType()==AirticketOrder.TRANTYPE_4){//4：废票
				
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_29);  //订单状态
				airticketOrderFrom.getStatement().setType(Statement.type_1);//结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_14);//操作日志 类型
			}
			airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());//日志操作员
			airticketOrderBiz.createRetireTradingOrder(airticketOrderFrom, airticketOrder);
						
			    inf.setMessage("创建成功！");
			    inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="+airticketOrder.getGroupMarkNo());
			   // inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 审核退废 并且创建（ 收款订单） 订单 sc
	 **************************************************************************/
	public ActionForward auditRetireTrading(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			
			if(airticketOrderFrom.getTranType()==AirticketOrder.TRANTYPE_3){//3：退票
				
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_21);  //订单状态
				airticketOrderFrom.getStatement().setType(Statement.type_2);//结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_10);//操作日志 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_3);
				airticketOrder.setStatus(AirticketOrder.STATUS_20);
			}else if(airticketOrderFrom.getTranType()==AirticketOrder.TRANTYPE_4){//4：废票
				
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_31);  //订单状态
				airticketOrderFrom.getStatement().setType(Statement.type_2);//结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_15);//操作日志 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_4);
				airticketOrder.setStatus(AirticketOrder.STATUS_30);
			}
			airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());//日志操作员
			airticketOrderBiz.createRetireTradingOrder(airticketOrderFrom, airticketOrder);
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
			airticketOrderBiz.update(airticketOrder);
			
						
			    inf.setMessage("操作成功！");
			    //inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=list");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	
	/***************************************************************************
	 * 审核退废  订单 sc
	 **************************************************************************/
	public ActionForward auditRetireTrading2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setAirOrderNo(airticketOrderFrom.getAirOrderNo());//票号
			airticketOrder.setHandlingCharge(airticketOrderFrom.getHandlingCharge());//手续费
			airticketOrder.getStatement().setTotalAmount(airticketOrderFrom.getStatement().getTotalAmount());
			 TicketLog ticketLog=new TicketLog();
			if(airticketOrder.getTranType()==AirticketOrder.TRANTYPE_3){//3：退票
				airticketOrder.setStatus(AirticketOrder.STATUS_21);
				  ticketLog.setType(TicketLog.TYPE_11);//操作日志 类型
			}else if(airticketOrder.getTranType()==AirticketOrder.TRANTYPE_4){//4：废票
				airticketOrder.setStatus(AirticketOrder.STATUS_31);
				  ticketLog.setType(TicketLog.TYPE_16);//操作日志 类型
			}
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
			airticketOrderBiz.update(airticketOrder);
			
			//操作日志
			  ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setStatus(1L);
			  ticketLog.setSysUser(uri.getUser());//日志操作员
			  ticketLogBiz.saveTicketLog(ticketLog);
						
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
			    forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	
	/***************************************************************************
	 * 确认退费 收款 sc
	 **************************************************************************/
	public ActionForward collectionRetireTrading(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		  TicketLog ticketLog=new TicketLog();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			
			if(airticketOrderFrom.getTranType()==AirticketOrder.TRANTYPE_3){//3：退票
				
				 ticketLog.setType(TicketLog.TYPE_12);//操作日志 类型
				airticketOrder.setStatus(AirticketOrder.STATUS_22);  //订单状态
				
			}else if(airticketOrderFrom.getTranType()==AirticketOrder.TRANTYPE_4){//4：废票
				
				 ticketLog.setType(TicketLog.TYPE_17);//操作日志 类型
				airticketOrder.setStatus(AirticketOrder.STATUS_32);  //订单状态
		
			}
			airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());//日志操作员
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
			airticketOrderBiz.update(airticketOrder);
				
			 //操作日志
			
			  ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setStatus(1L);
			  ticketLog.setSysUser(uri.getUser());//日志操作员
			  ticketLogBiz.saveTicketLog(ticketLog);
		  
			    inf.setMessage("操作成功！");
			    //inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=list");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addWaitAgreeUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			
		  //  String flightId[]= 	request.getParameterValues("flightId");
		  //  String flightCode[]=request.getParameterValues("flightCode");
		    for(int i=0;i<airticketOrderFrom.getFlightIds().length;i++){
		    	System.out.println("flightId=="+airticketOrderFrom.getFlightIds()[i]+"===flightCode="+airticketOrderFrom.getFlightIds()[i]);
		    }
			
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_39);  //订单状态
				airticketOrderFrom.getStatement().setType(Statement.type_1);//结算类型
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_21);//操作日志 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);//
			
			    airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());//日志操作员
			    airticketOrderBiz.createWaitAgreeUmbuchenOrder(airticketOrderFrom, airticketOrder);
						
			    inf.setMessage("创建成功！");
			   // inf.setBack(true);
			    inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="+airticketOrder.getGroupMarkNo());
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	
	/***************************************************************************
	 * 审核改签 订单 sc
	 **************************************************************************/
	public ActionForward auditWaitAgreeUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			
			
			airticketOrderFrom.setStatus(AirticketOrder.STATUS_41);  //订单状态
			airticketOrderFrom.getStatement().setType(Statement.type_2);//结算类型
			airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_23);//操作日志 类型
			airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
		
		    airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());//日志操作员
		    airticketOrderBiz.createWaitAgreeUmbuchenOrder(airticketOrderFrom, airticketOrder);
		    airticketOrder.setStatus(AirticketOrder.STATUS_40);
		    airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		    airticketOrderBiz.update(airticketOrder);			
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 收付 改签 订单 sc 
	 **************************************************************************/
	public ActionForward receiptWaitAgreeUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			
		   String  platformId=request.getParameter("platformId6");
		   String  companyId=request.getParameter("companyId6");
		   String  accountId=request.getParameter("accountId6");
		   
			//设置平台号 
			PlatComAccountStore platComAccountStore=new PlatComAccountStore();
			PlatComAccount platComAccount=null;
			if(platformId!=null&&companyId!=null&&accountId!=null){
				System.out.println("airticketOrderFrom.getAccountId()=="+airticketOrderFrom.getAccountId());
				platComAccount=platComAccountStore.getPlatComAccountByAllId(Long.valueOf(platformId), Long.valueOf(companyId), Long.valueOf(accountId));
			}

		    if(airticketOrder.getStatement().getType()==Statement.type_1){
		    	airticketOrder.getStatement().setFromPCAccount(platComAccount);
		    }else if(airticketOrder.getStatement().getType()==Statement.type_2){
		    	airticketOrder.getStatement().setToPCAccount(platComAccount);
		    }
			
		    if(airticketOrder.getStatement().getType()==Statement.type_1){
		    	airticketOrderFrom.getStatement().setType(Statement.type_2);
		    	airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
		    	airticketOrderFrom.setGroupMarkNo(airticketOrder.getGroupMarkNo());
		    	List listao=airticketOrderBiz.getListByOrder(airticketOrderFrom);
		    	if(listao!=null&&listao.size()>0){
		    		AirticketOrder ao=	(AirticketOrder)listao.get(0);
		    		ao.setStatus(AirticketOrder.STATUS_42);
		    		 airticketOrderBiz.update(ao);	
		    	}
		    	
		    }
		    airticketOrder.setStatus(AirticketOrder.STATUS_43);
		    airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		    airticketOrderBiz.update(airticketOrder);
		    
		  
		      //操作日志
		      TicketLog ticketLog=new TicketLog();
			  ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setType(TicketLog.TYPE_24);//操作日志 类型
			  ticketLog.setStatus(1L);
			  ticketLog.setSysUser(uri.getUser());//日志操作员
			  ticketLogBiz.saveTicketLog(ticketLog);
		    
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 修改  订单状态  sc
	 **************************************************************************/
	public ActionForward updateOrderStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		System.out.println("id==="+airticketOrderFrom.getId()+"pnr==="+airticketOrderFrom.getPnr()+"===="+airticketOrderFrom.getCompanyId());
		
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
			AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
			airticketOrder.setStatus(airticketOrderFrom.getStatus());  //订单状态
			airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis())) ;//操作时间
		    airticketOrderBiz.update(airticketOrder);
		   
		    //操作日志
			  TicketLog ticketLog=new TicketLog();
			  ticketLog.setOrderNo(airticketOrder.getStatement().getStatementNo());//订单号
			  ticketLog.setOrderType(Statement.ORDERTYPE_1);//订单类型
			  ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());//操作员
			  ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));//操作时间
			  ticketLog.setStatus(1L);
			  ticketLog.setSysUser(uri.getUser());//日志操作员
			  if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_6){
				  
			   ticketLog.setType(TicketLog.TYPE_6);//操作日志 类型
			  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_7){//锁定
				  
				  ticketLog.setType(TicketLog.TYPE_29);
			  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_8){//解锁
				  
				  ticketLog.setType(TicketLog.TYPE_30);
			  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_44){//改签未通过
				  
				  ticketLog.setType(TicketLog.TYPE_27);
			  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_41){//改签审核通过
				  
				  ticketLog.setType(TicketLog.TYPE_23);
			  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_42){//改签审核通过
				  
				  ticketLog.setType(TicketLog.TYPE_24);
			  }else if(airticketOrderFrom.getStatus()==AirticketOrder.STATUS_45){//改签完成
				  
				  ticketLog.setType(TicketLog.TYPE_26);
			  }
			 
			  ticketLogBiz.saveTicketLog(ticketLog);
			  
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 手动 添加订单  sc
	 **************************************************************************/
	public ActionForward handworkAddTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			
			if(airticketOrderFrom!=null){
			
				airticketOrderBiz.handworkAddTradingOrder(airticketOrderFrom, request, uri);
			  
			    inf.setMessage("操作成功！");
			    inf.setBack(true);
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 跳转 编辑订单页  sc
	 **************************************************************************/
	public ActionForward forwardEditTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage="";
		Inform inf = new Inform();
		try {
			
			if(airticketOrderFrom!=null&&airticketOrderFrom.getId()>0){
			
				AirticketOrder airticketOrder=airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				request.setAttribute("airticketOrder", airticketOrder);
				forwardPage = "editTradingOrder";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	/***************************************************************************
	 * 修改订单  sc
	 **************************************************************************/
	public ActionForward editTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage="";
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			
			if(airticketOrderFrom!=null){
				airticketOrderBiz.editTradingOrder(airticketOrderFrom, request, uri);
			    inf.setMessage("操作成功！");
			    //inf.setBack(true);
			    inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="+airticketOrderFrom.getGroupMarkNo());
				forwardPage = "inform";
				
		}else{
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
		request.setAttribute("inf",inf);
		return (mapping.findForward(forwardPage));
		
		
	}
	//团队订单录入
	public ActionForward saveAirticketOrderTemp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom.getAgentNo()>0)//客户ID
			{
				airticketOrderBiz.saveAirticketOrderTemp(airticketOrderFrom, uri, request, response);
				
				inf.setMessage("您已经成功添加机票订单数据！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=getTempAirticketOrderByticketType");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("您添加机票订单数据失败！");
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
	
	//跳转原来机票添加页面
	public ActionForward saveAirticketOrderPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			String forwardPage ="";
			try {				
				AirticketOrder airticketOrder = new AirticketOrder();
				request.setAttribute("airticketOrder", airticketOrder);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			forwardPage ="addTeamTradingOrder";
			return mapping.findForward(forwardPage);		
	}
	
	//修改机票订单信息
	public ActionForward updateAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AirticketOrder airticketOrderForm=(AirticketOrder)form; 
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		
		Inform inf = new Inform();
		try {
			if(airticketOrderForm.getId()>0)
			{
				long agentNo =airticketOrderForm.getAgentNo();
				airticketOrderBiz.updateAirticketOrderTemp(airticketOrderForm, uri, agentNo, request, response);	
				
				inf.setMessage("您已经成功修改机票订单数据！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="+airticketOrderForm.getId());
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			}else{
				inf.setMessage("您改机票订单数据失败！");
				inf.setBack(true);
					}		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	
	//添加团队利润
	public ActionForward insertTeamTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try {
			if(airticketOrderFrom !=null)//客户ID
			{
				airticketOrderBiz.insertTeamTradingOrder(airticketOrderFrom, uri, request, response);
				
				inf.setMessage("您已经成功添加团队利润数据！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="+airticketOrderFrom.getAirticketOrderId());
				inf.setParamId("thisAction");
				inf.setParamValue("list");
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
	
	//修改团队利润(修改结算表)
	public ActionForward updateTeamStatement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AirticketOrder airticketOrderForm=(AirticketOrder)form; 
		UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
		
		Inform inf = new Inform();
		try {
			if(airticketOrderForm.getStatementId()>0)
			{
				airticketOrderBiz.updateTeamStatement(airticketOrderForm, uri, request, response);
				
				inf.setMessage("您已经成功修改团队利润数据！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="+airticketOrderForm.getAirticketOrderId());
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			}else{
				inf.setMessage("您改机票团队利润失败数据！");
				inf.setBack(true);
					}		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
		
	}


	public TempPNRBiz getTempPNRBiz() {
		return tempPNRBiz;
	}	

	public void setTempPNRBiz(TempPNRBiz tempPNRBiz) {
		this.tempPNRBiz = tempPNRBiz;
	}

	public AirticketOrderBiz getAirticketOrderBiz() {
		return airticketOrderBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}
	public PassengerBiz getPassengerBiz() {
		return passengerBiz;
	}
	public void setPassengerBiz(PassengerBiz passengerBiz) {
		this.passengerBiz = passengerBiz;
	}
	public TicketLogBiz getTicketLogBiz() {
		return ticketLogBiz;
	}
	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}


}
