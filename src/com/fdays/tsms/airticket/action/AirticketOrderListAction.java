package com.fdays.tsms.airticket.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.airticket.AirticketGroup;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.neza.base.BaseAction;
import com.neza.base.Constant;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderListAction extends BaseAction
{
	private AirticketOrderBiz airticketOrderBiz;

	/***************************************************************************
	 * 散票订单管理 sc
	 **************************************************************************/
	public ActionForward listAirTicketOrderManage(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String path = request.getContextPath();
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try{
			ulf.setUri(uri);
			ulf.setTicketType(AirticketOrder.TICKETTYPE_1);
			ulf.setScrap_status(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			String orderType = request.getParameter("orderType");
			if (orderType != null){
				if (orderType.equals("91"))
				{// 正常订单
					ulf.setMoreTranType("1,2");
				}
				else if (orderType.equals("92"))
				{// 改签订单
					ulf.setMoreTranType("5");
				}
				else if (orderType.equals("93"))
				{// 退费订单
					ulf.setMoreTranType("3,4");
				}
			}

			List<AirticketOrder> orderList = airticketOrderBiz.list(ulf);

			for (AirticketOrder ao : orderList)
			{
				ao.setUri(uri);
				ao.setPath(path);
			}
			List groupList = AirticketGroup.getGroupList(orderList);

			ulf.setList(groupList);
		}
		catch (Exception ex)
		{
			ulf.setList(new ArrayList());
		}
		request.setAttribute("formPlatFormList", PlatComAccountStore
		    .getFormPlatform());// 买入
		request.setAttribute("toPlatFormList", PlatComAccountStore.getToPlatform());// 卖出
		request.setAttribute("formAccountList", PlatComAccountStore
		    .getFormAccount());// 付款账号
		request.setAttribute("toAccountList", PlatComAccountStore.getToAccount());// 收款账号
		request.setAttribute("ulf", ulf);
		forwardPage = "listAirTicketOrderManage";
		return (mapping.findForward(forwardPage));
	}
	
	
	// 团队订单List
	public ActionForward listTeamAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String status = request.getParameter("status");
		try
		{
			if (ulf == null)
				ulf = new AirticketOrderListForm();
			ulf.setUri(uri);
			ulf.setTicketType(AirticketOrder.TICKETTYPE_2);// 团队
			if (status != null && !"".equals(status)){
				ulf.setTeamStatus(Long.parseLong(status));
			}
			String path = request.getContextPath();
			ulf.setScrap_status(AirticketOrder.STATUS_88);// 已废弃
			List<AirticketOrder> orderList= airticketOrderBiz.listTeam(ulf);
			for(AirticketOrder ao:orderList){
				ao.setUri(uri);
				ao.setPath(path);
			}
			List groupList = AirticketGroup.getGroupList(orderList);
			ulf.setList(groupList);
			airticketOrderBiz.editTeamAirticketOrderAccount(ulf, request, response);
			// request.setAttribute("agentList",
			// PlatComAccountStore.getTempAgentListBytype(Agent.type_2));
		}
		catch (Exception e)
		{
			// TODO: handle exception
			ulf.setList(new ArrayList());
			e.printStackTrace();
		}
		String systemTime = DateUtil.getDateString(new Timestamp(System
		    .currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
		request.setAttribute("newTime", systemTime);
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamAirticketOrder";
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 等待回收票款订单 sc
	 **************************************************************************/
	public ActionForward listWaitRecoveryTicketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try
		{

			ulf.setTicketType(AirticketOrder.TICKETTYPE_3);
			ulf.setTranType(AirticketOrder.TRANTYPE__1);// 卖出
			ulf.setB2C_status(AirticketOrder.STATUS_80);// 过滤交易结束的订单
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤已废弃的票
			ulf.setList(airticketOrderBiz.b2cAirticketOrderList(ulf));
		}
		catch (Exception ex)
		{
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitRecoveryTicketOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 查询 关联订单 sc
	 **************************************************************************/
	public ActionForward tradingOrderProcessing(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;		
		try{
			airticketOrderBiz.forwardProcessingTradingOrder(ulf,request);
		}catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "tradingOrderProcessing";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 跳转 编辑订单页 sc
	 **************************************************************************/
	public ActionForward forwardEditTradingOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;		
		try{
			airticketOrderBiz.forwardEditTradingOrder(ulf, request);
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		forwardPage = "editTradingOrder";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * *---团队专用
	 */

	// 申请支付
	public ActionForward updateForpayTeamAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try
		{
			if (airticketOrderId != null && (!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamForpayAirticketOrder(airticketOrderId, uri,
				    request, response);
				return new ActionRedirect(
				    "/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder");
			}
			else
			{
				inf.setMessage("申请支付失败！");
				inf.setBack(true);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 确认出票
	public ActionForward updateTeamAirticketOrderOver(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		long groupId =Constant.toLong(request.getParameter("groupMarkNo"));
		try
		{
			if (airticketOrderId != null && (!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamAirticketOrderOver(groupId,
				    airticketOrderId, uri, request, response);
				return new ActionRedirect(
				    "/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder");
			}
			else
			{
				inf.setMessage("申请支付失败！");
				inf.setBack(true);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// --------退票----------

	// 申请退票
	public ActionForward updateTeamRefundAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try
		{
			if (airticketOrderId != null && (!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamRefundAirticketOrder(airticketOrderId, uri,
				    request, response);
				return new ActionRedirect(
				    "/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder");
			}
			else
			{
				inf.setMessage("申请退票失败！");
				inf.setBack(true);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 查看订单详细信息
	public ActionForward viewAirticketOrderPage(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.viewAirticketOrderPage(ulf,request);
			if ("ERROR".equals(forwardPage)){
				inf.setMessage("程序异常,请联系技术支持");
				inf.setBack(true);
				request.setAttribute("inf", inf);
				forwardPage = "inform";
			}else{
				forwardPage = "viewAirticketOrder";
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}		
		return mapping.findForward(forwardPage);
	}

	// 编辑团队机票订单
	public ActionForward updaTempAirticketOrderPage(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException{
		AirticketOrder airticketOrderForm = new AirticketOrder();
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String forwardPage = "";
		try{
			String airticketOrderId = request.getParameter("airticketOrderId");
			if (airticketOrderId != null && (!airticketOrderId.equals(""))){
				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderById(Long.parseLong(airticketOrderId));
				request.setAttribute("airticketOrder", airticketOrder);
				airticketOrderBiz.updateTeamAirticketOrderPage(uri, airticketOrderForm,
				    airticketOrderId, request, response);
			}
			// 调用AirticketOrderBizImp里的修改利润显示方法
			airticketOrderBiz.updaTempAirticketOrderPrice(airticketOrderForm, Long
			    .parseLong(airticketOrderId), request, response);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		forwardPage = "editTeamTradingOrder";
		return mapping.findForward(forwardPage);
	}

	// 删除订单(改变状态)
	public ActionForward deleteAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		String airticketOrderId = request.getParameter("airticketOrderId");
		String num = request.getParameter("num");
		Inform inf = new Inform();
		try{
			if (airticketOrderId != null && (!airticketOrderId.equals(""))){
				airticketOrderBiz.deleteAirticketOrder(airticketOrderId);
				if (Long.parseLong(num) == 1) { return new ActionRedirect(
				    "/airticket/listAirTicketOrder.do?thisAction=listAirTicketOrderManage"); }
				if (Long.parseLong(num) == 2) { return new ActionRedirect(
				    "/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder"); }
			}
			else{
				inf.setMessage("您删除机票订单数据失败！");
				inf.setBack(true);
			}
		}
		catch (Exception e){
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz){
		this.airticketOrderBiz = airticketOrderBiz;
	}
}
