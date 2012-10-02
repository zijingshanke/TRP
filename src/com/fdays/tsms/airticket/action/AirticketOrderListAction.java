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
import com.fdays.tsms.airticket.AlidzForm;
import com.fdays.tsms.airticket.TeamProfit;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.airticket.biz.AlidzBiz;

import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.neza.base.BaseAction;
import com.neza.base.Constant;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderListAction extends BaseAction

{
	private AlidzBiz alidzBiz;// 本票通
	private String result = ""; // 本票通返回信息
	private AirticketOrderBiz airticketOrderBiz;

	/***************************************************************************
	 * 散票订单管理
	 **************************************************************************/
	public ActionForward listAirTicketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String path = request.getContextPath();

		if (ulf == null)
		{
			ulf = new AirticketOrderListForm();
		}

		ulf.setTicketType(AirticketOrder.TICKETTYPE_1);
		ulf.setScrap_status(AirticketOrder.STATUS_88);// 过滤废弃订单
		ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单

		saveCustomerSession(request, uri, ulf);

		try
		{
			// long a = System.currentTimeMillis();
			List<AirticketOrder> orderList = airticketOrderBiz.list(ulf, uri);
			// long b = System.currentTimeMillis();
			// System.out.println(" over get sql data  time:" + ((b - a) / 1000) +
			// "s");
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
			ex.printStackTrace();
			ulf.setList(new ArrayList());
		}

		request = loadPlatComAccountStoreForRequest(request);

		request.setAttribute("ulf", ulf);

		forwardPage = "listAirTicketOrder";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 团队订单管理
	 */
	public ActionForward listTeamAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		try
		{
			if (ulf == null)
				ulf = new AirticketOrderListForm();

			ulf.setTicketType(AirticketOrder.TICKETTYPE_2);// 团队
			String path = request.getContextPath();
			ulf.setScrap_status(AirticketOrder.STATUS_88);// 已废弃
			saveCustomerSession(request, uri, ulf);
			List<AirticketOrder> orderList = airticketOrderBiz.listTeam(ulf, uri);

			for (AirticketOrder ao : orderList)
			{
				ao.setUri(uri);
				ao.setPath(path);
			}
			List groupList = AirticketGroup.getGroupList(orderList);
			ulf.setList(groupList);
			request = loadTeamAgentList(request);
		}
		catch (Exception e)
		{
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
	 * 关联订单
	 **************************************************************************/
	public ActionForward processing(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		try
		{
			airticketOrderBiz.processing(ulf, request);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		forwardPage = "processing";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 跳转 编辑订单页
	 **************************************************************************/
	public ActionForward editOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		try
		{
			airticketOrderBiz.editOrder(ulf, request);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		forwardPage = "editOrder";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward redirectManagePage(HttpServletRequest request)
	{

		ActionRedirect redirect = new ActionRedirect(
		    AirticketOrder.GeneralManagePath);

		if (request.getSession().getAttribute("orderType") != null)
		{
			redirect.addParameter("orderType", request.getSession().getAttribute(
			    "orderType"));
		}
		if (request.getSession().getAttribute("moreStatus") != null)
		{
			redirect.addParameter("moreStatus", request.getSession().getAttribute(
			    "moreStatus"));
		}
		if (request.getSession().getAttribute("recentlyDay") != null)
		{
			redirect.addParameter("recentlyDay", request.getSession().getAttribute(
			    "recentlyDay"));
		}

		return redirect;

	}

	public ActionForward redirectTeamManagePage(HttpServletRequest request)
	{

		ActionRedirect redirect = new ActionRedirect(AirticketOrder.TeamManagePath);

		if (request.getSession().getAttribute("orderType") != null)
		{
			redirect.addParameter("orderType", request.getSession().getAttribute(
			    "orderType"));
		}
		if (request.getSession().getAttribute("moreStatus") != null)
		{
			redirect.addParameter("moreStatus", request.getSession().getAttribute(
			    "moreStatus"));

		}
		if (request.getSession().getAttribute("recentlyDay") != null)
		{
			redirect.addParameter("recentlyDay", request.getSession().getAttribute(
			    "recentlyDay"));
		}

		return redirect;

	}

	/**
	 * 团队申请支付
	 */
	public ActionForward applyTeamPayment(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm alf = (AirticketOrderListForm) form;
		Inform inf = new Inform();
		Long airticketOrderId = alf.getId();
		try
		{
			if (airticketOrderId != null && (!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.applyTeamPayment(airticketOrderId, request);

				return new ActionRedirect(AirticketOrder.TeamManagePath);
			}
			else
			{
				inf.setMessage("订单ID不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			inf.setMessage("申请支付异常");
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// /**
	// * 确认出票
	// */
	// public ActionForward ticketTeam(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws AppException {
	// String forwardPage = "";
	// AirticketOrderListForm alf = (AirticketOrderListForm) form;
	// Inform inf = new Inform();
	// Long airticketOrderId = alf.getId();
	// Long groupId = alf.getGroupId();
	// try {
	// if (airticketOrderId != null && (!(airticketOrderId.equals("")))) {
	// airticketOrderBiz
	// .ticketTeam(airticketOrderId, groupId, request);
	//				
	// return redirectTeamManagePage(request);
	// } else {
	// inf.setMessage("订单ID不能为空！");
	// inf.setBack(true);
	// }
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// inf.setMessage("确认出票异常");
	// inf.setBack(true);
	// }
	// request.setAttribute("inf", inf);
	// forwardPage = "inform";
	// return (mapping.findForward(forwardPage));
	// }

	/**
	 * 团队同意退票
	 */
	public ActionForward applyTeamRefund(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm alf = (AirticketOrderListForm) form;
		Inform inf = new Inform();
		Long airticketOrderId = alf.getId();
		try
		{
			if (airticketOrderId != null && (!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.applyTeamRefund(airticketOrderId, request);
				return new ActionRedirect(AirticketOrder.TeamManagePath);
			}
			else
			{
				inf.setMessage("订单ID不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			inf.setMessage("申请支付异常");
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 编辑团队退票订单
	 */
	public ActionForward editTeamRefundOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		String forwardPage = "";
		try
		{
			if (ulf.getId() > 0)
			{
				// airticketOrderBiz.editTeamRefundOrder(ulf.getId(), request);
				airticketOrderBiz.editTeamOrder(ulf.getId(), request);
			}
			else
			{
				// 新录入单
				AirticketOrder airticketOrder = new AirticketOrder();
				airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
				airticketOrder.setTranType(AirticketOrder.TRANTYPE_3);

				AirticketOrder buyerOrder = new AirticketOrder();
				request.setAttribute("buyerOrder", buyerOrder);
				request.setAttribute("airticketOrder", airticketOrder);
				request.setAttribute("teamProfit", new TeamProfit(airticketOrder,
				    buyerOrder));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		// forwardPage = "editTeamRefundOrder";
		forwardPage = "editTeamOrder";

		return mapping.findForward(forwardPage);
	}

	/**
	 * 团队订单录入,团队订单编辑
	 */
	public ActionForward editTeamOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		String forwardPage = "";
		try
		{
			if (ulf.getId() > 0)
			{
				airticketOrderBiz.editTeamOrder(ulf.getId(), request);
			}
			else
			{
				// 新录入单
				AirticketOrder airticketOrder = new AirticketOrder();
				airticketOrder.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
				airticketOrder.setTranType(AirticketOrder.TRANTYPE__1);

				AirticketOrder buyerOrder = new AirticketOrder();
				request.setAttribute("buyerOrder", buyerOrder);
				request.setAttribute("airticketOrder", airticketOrder);
				request.setAttribute("teamProfit", new TeamProfit(airticketOrder,
				    buyerOrder));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		forwardPage = "editTeamOrder";
		return mapping.findForward(forwardPage);
	}

	/**
	 * 加载平台、帐号信息到Request
	 */
	public HttpServletRequest loadPlatComAccountStoreForRequest(
	    HttpServletRequest request)
	{
		request.setAttribute("formPlatFormList", PlatComAccountStore
		    .getFormPlatform());// 买入
		request.setAttribute("toPlatFormList", PlatComAccountStore.getToPlatform());// 卖出
		request.setAttribute("formAccountList", PlatComAccountStore
		    .getFormAccount());// 付款账号
		request.setAttribute("toAccountList", PlatComAccountStore.getToAccount());// 收款账号
		return request;
	}

	public HttpServletRequest loadTeamAgentList(HttpServletRequest request)
	    throws AppException
	{
		List<Agent> agentList = PlatComAccountStore
		    .getTempAgentListBytype(Agent.type_2);
		List<PlatComAccount> platComAccountList = PlatComAccountStore
		    .getPlatComAccountListByPlatformId2AndAccountType(new Long(99999),
		        Account.tran_type_1);// 查询团队
		request.setAttribute("platComAccountList", platComAccountList);
		request.setAttribute("agentList", agentList);
		return request;
	}

	/**
	 * 查看订单详细信息
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.view(ulf, request);
			if ("ERROR".equals(forwardPage))
			{
				inf.setMessage("程序异常,请联系技术支持");
				inf.setBack(true);
				request.setAttribute("inf", inf);
				forwardPage = "inform";
			}
			else
			{
				forwardPage = "view";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return mapping.findForward(forwardPage);
	}

	/**
	 * 查看团队
	 */
	public ActionForward viewTeam(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		Inform inf = new Inform();
		try
		{
			airticketOrderBiz.viewTeam(ulf, request);
			forwardPage = "viewTeam";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("程序异常");
			inf.setBack(true);
			request.setAttribute("inf", inf);
			forwardPage = "inform";
		}
		return mapping.findForward(forwardPage);
	}

	// 删除订单(改变状态)
	public ActionForward deleteAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		String forwardPageFlag = ulf.getForwardPageFlag();

		Inform inf = new Inform();
		try
		{
			if (ulf.getId() != null && ulf.getId().longValue() > 0)
			{
				AirticketOrder ao = airticketOrderBiz
				    .getAirticketOrderById(ulf.getId());
				if (ao.getTicketType().longValue() == AirticketOrder.TICKETTYPE_2)
				{
					List list = airticketOrderBiz.listBySubGroupAndGroupId(ao
					    .getOrderGroup().getId(), ao.getSubGroupMarkNo());
					for (int i = 0; i < list.size(); i++)
					{
						AirticketOrder tempAo = (AirticketOrder) list.get(i);
						airticketOrderBiz.delete(tempAo.getId());
					}
				}
				else
				{
					airticketOrderBiz.deleteAirticketOrder(ao.getId());
				}

				if (forwardPageFlag != null && "".equals(forwardPageFlag) == false)
				{
					if ("General".equals(forwardPageFlag))
					{
						return new ActionRedirect(AirticketOrder.GeneralManagePath);
					}
					else if ("Team".equals(forwardPageFlag)) { return new ActionRedirect(
					    AirticketOrder.TeamManagePath); }
				}
			}
			else
			{
				inf.setMessage("删除机票订单数据失败！");
				inf.setBack(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void saveCustomerSession(HttpServletRequest request,
	    UserRightInfo uri, AirticketOrderListForm alf)
	{
		if (alf.getSysName() == null)
		{
			if (request.getSession().getAttribute("sysName") == null)
			{
				alf.setSysName(uri.getUser().getUserNo());
				request.getSession().setAttribute("sysName", alf.getSysName());
			}
			else
			{
				alf.setSysName((String) request.getSession().getAttribute("sysName"));
			}

		}
		else
			request.getSession().setAttribute("sysName", alf.getSysName());

		if (alf.getPnr() == null)
		{
			if (request.getSession().getAttribute("pnr") == null)
			{
				alf.setPnr("");
				request.getSession().setAttribute("pnr", alf.getPnr());
			}
			else
			{
				alf.setPnr((String) request.getSession().getAttribute("pnr"));
			}
		}
		else
			request.getSession().setAttribute("pnr", alf.getPnr());

		if (alf.getOrderBy() == null)
		{
			if (request.getSession().getAttribute("orderBy") == null)
			{
				alf.setOrderBy(new Long(0));
				request.getSession().setAttribute("orderBy", alf.getOrderBy());
			}
			else
			{
				alf.setOrderBy((Long) request.getSession().getAttribute("orderBy"));
			}
		}
		else
			request.getSession().setAttribute("orderBy", alf.getOrderBy());

		if (alf.getDrawType() == null)
		{
			if (request.getSession().getAttribute("drawType") == null)
			{
				alf.setDrawType(new Long(99));
				request.getSession().setAttribute("drawType", alf.getDrawType());
			}
			else
			{
				alf.setDrawType((Long) request.getSession().getAttribute("drawType"));
			}
		}
		else
			request.getSession().setAttribute("drawType", alf.getDrawType());

		if (alf.getOrderType() == null)
		{
			if (request.getSession().getAttribute("orderType") == null)
			{
				alf.setOrderType("");
				request.getSession().setAttribute("orderType", alf.getOrderType());
			}
			else
			{
				alf.setOrderType((String) request.getSession()
				    .getAttribute("orderType"));
			}
		}
		else
			request.getSession().setAttribute("orderType", alf.getOrderType());

		if (alf.getMoreStatus() == null)
		{
			if (request.getSession().getAttribute("moreStatus") == null)
			{
				alf.setMoreStatus("");
				request.getSession().setAttribute("moreStatus", alf.getMoreStatus());
			}
			else
			{
				alf.setMoreStatus((String) request.getSession().getAttribute(
				    "moreStatus"));
			}
		}
		else
			request.getSession().setAttribute("moreStatus", alf.getMoreStatus());

		if (request.getSession().getAttribute("recentlyDay") == null)
		{
			if (alf.getRecentlyDay() == null)
			{
				alf.setRecentlyDay(new Long(1));
			}
			request.getSession().setAttribute("recentlyDay", alf.getRecentlyDay());
		}
		else
		{
			if (alf.getRecentlyDay() == null)
			{
				int x = Constant.toInt((String) request.getSession().getAttribute(
				    "recentlyDay").toString());
				alf.setRecentlyDay(new Long(x));
			}
			request.getSession().setAttribute("recentlyDay", alf.getRecentlyDay());
		}

	

	}

	/**
	 * 检测本票通是否在运行
	 */
	public ActionForward isRunning(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		result = alidzBiz.isRunning();
		// System.out.println(result);
		request.setAttribute("result", result);
		return (mapping.findForward("backInf"));
	}

	/**
	 * 查询本电政策和价格
	 */
	public ActionForward queryPriceByPnr(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AlidzForm af = (AlidzForm) form;
		result = alidzBiz.queryPriceByPnr(af.getPnr(), af.getBigPnr(), af.getAir(),
		    af.getB2bUser(), af.getB2bPwd());
		// System.out.println(result);
		request.setAttribute("result", result);
		return (mapping.findForward("backInf"));

	}

	/**
	 * 查询本电订单状态
	 */
	public ActionForward queryOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AlidzForm af = (AlidzForm) form;
		result = alidzBiz.queryOrder(af.getPnr(), af.getBigPnr(), af.getAir(), af
		    .getB2bUser(), af.getB2bPwd());
		// System.out.println(result);
		request.setAttribute("result", result);
		return (mapping.findForward("backInf"));
	}

	/**
	 * 导入本电系统
	 * 
	 * @return
	 */
	public ActionForward order(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AlidzForm af = (AlidzForm) form;
		result = alidzBiz.order(af.getPnr(), af.getBigPnr(), af.getAir(), af
		    .getB2bUser(), af.getB2bPwd(), af.getAutoPayFlag());
		// System.out.println(result);
		request.setAttribute("result", result);
		return (mapping.findForward("backInf"));
	}

	// ----------------------------------set
	// get-----------------------------------------//
	public AlidzBiz getAlidzBiz()
	{
		return alidzBiz;
	}

	public void setAlidzBiz(AlidzBiz alidzBiz)
	{
		this.alidzBiz = alidzBiz;
	}

	public String getResult()
	{
		return result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz)
	{
		this.airticketOrderBiz = airticketOrderBiz;
	}
}
