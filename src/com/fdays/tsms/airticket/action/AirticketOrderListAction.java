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
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderListAction extends BaseAction {
	private AirticketOrderBiz airticketOrderBiz;
	private TicketLogBiz ticketLogBiz;
	
	/***************************************************************************
	 * 全部B2B/B2C订单 sc
	 **************************************************************************/
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			List orderList=airticketOrderBiz.list(ulf);		
			List groupList=AirticketGroup.getGroupList(orderList);

			ulf.setList(groupList);
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("formPlatFormList", PlatComAccountStore
				.getFormPlatform());// 买入
		request.setAttribute("toPlatFormList", PlatComAccountStore
				.getToPlatform());// 卖出
		request.setAttribute("formAccountList", PlatComAccountStore
				.getFormAccount());// 付款账号
		request.setAttribute("toAccountList", PlatComAccountStore
				.getToAccount());// 收款账号
		request.setAttribute("ulf", ulf);
		forwardPage = "listAirTicketOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 新订单 sc
	 **************************************************************************/
	public ActionForward listNewAirTicketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_1);// 
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listNewAirTicketOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 等待支付订单 sc
	 **************************************************************************/
	public ActionForward listWaitPayOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_2);//申请成功，等待支付
			ulf.setMoreStatus("2,7,8");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitPayOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 等待出票订单 sc
	 **************************************************************************/
	public ActionForward listWaitDrawOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_3);// 申请成功，等待出票
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitDrawOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 出票完成订单 sc
	 **************************************************************************/
	public ActionForward listDrawSuccessOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_5);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listDrawSuccessOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 取消待退款订单[确认退款] sc
	 **************************************************************************/
	public ActionForward listConfirmRefundment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_4);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listConfirmRefundment";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 取消已退款订单 sc
	 **************************************************************************/
	public ActionForward listRefundmentEnd(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_6);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listRefundmentEnd";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 等待审核退废订单 sc
	 **************************************************************************/
	public ActionForward listWaitAgreeRetireOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_30);
			ulf.setMoreStatus("19,29,20,30,24,25,34,35");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitAgreeRetireOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 确定退废订单 sc
	 **************************************************************************/
	public ActionForward listConfirmRetireOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_30);
			ulf.setMoreStatus("21,31");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listConfirmRetireOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 完成退款订单 sc
	 **************************************************************************/
	public ActionForward listSuccessRetireOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_30);
			ulf.setMoreStatus("22,32");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listSuccessRetireOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 审核不通过 退废订单 sc
	 **************************************************************************/
	public ActionForward listnoPassRetireOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_30);
			ulf.setMoreStatus("23,33");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listnoPassRetireOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 等待审核改签订单 sc
	 **************************************************************************/
	public ActionForward listWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_41);
			ulf.setMoreStatus("39,46");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitAgreeUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 已审待支付订单 sc
	 **************************************************************************/
	public ActionForward listLoadUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_41);
			ulf.setMoreStatus("40,41,42");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listLoadUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 确定改签订单（已付待确认订单） sc
	 **************************************************************************/
	public ActionForward listConfirmUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_41);40,41,42,
			ulf.setMoreStatus("43");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listConfirmUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 完成改签订单 sc
	 **************************************************************************/
	public ActionForward listSuccessUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_41);
			ulf.setMoreStatus("45");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listSuccessUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 改签不通过订单 sc
	 **************************************************************************/
	public ActionForward listnoPassUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			// ulf.setAirticketOrder_status(AirticketOrder.STATUS_41);
			ulf.setMoreStatus("44");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String
					.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listnoPassUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 等待回收票款订单 sc
	 **************************************************************************/
	public ActionForward listWaitRecoveryTicketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {

			ulf.setTicketType(AirticketOrder.TICKETTYPE_3);
			ulf.setTranType(AirticketOrder.TRANTYPE__1);// 卖出
			ulf.setB2C_status(AirticketOrder.STATUS_80);// 过滤交易结束的订单
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤已废弃的票
			ulf.setList(airticketOrderBiz.b2cAirticketOrderList(ulf));
		} catch (Exception ex) {
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
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		String path = request.getContextPath();
		ulf.setPerPageNum(100);
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			String groupMarkNo = request.getParameter("groupMarkNo");
			if (groupMarkNo != null && !"".equals(groupMarkNo.trim())) {
				ulf.setGroupMarkNo(groupMarkNo);
				ulf.setTeamStatus(AirticketOrder.STATUS_88);// 已废弃
				List<AirticketOrder> list =airticketOrderBiz.list(ulf);
				for(AirticketOrder ao:list){
					ao.setUri(uri);
					ao.setPath(path);
				}
				ulf.setList(list);
			} else {
				ulf.setList(new ArrayList());
			}
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "tradingOrderProcessing";
		return (mapping.findForward(forwardPage));
	}
	
	
	/***************************************************************************
	 * 订单管理 sc
	 **************************************************************************/
	public ActionForward listAirTicketOrderManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		String path = request.getContextPath();
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setUri(uri);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));// 过滤掉团队订单
			String orderType=request.getParameter("orderType");
			if(orderType!=null){
				if(orderType.equals("91")){//正常订单
					ulf.setMoreTranType("1,2");
				}else if(orderType.equals("92")){//改签订单
					ulf.setMoreTranType("5");
				}else if(orderType.equals("93")){//退费订单
					ulf.setMoreTranType("3,4");
				}
			}
			
			List<AirticketOrder> orderList=airticketOrderBiz.list(ulf);			
			
			for(AirticketOrder ao:orderList){
				ao.setUri(uri);
				ao.setPath(path);
			}
			List groupList=AirticketGroup.getGroupList(orderList);
			
			
			ulf.setList(groupList);
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("formPlatFormList", PlatComAccountStore
				.getFormPlatform());// 买入
		request.setAttribute("toPlatFormList", PlatComAccountStore
				.getToPlatform());// 卖出
		request.setAttribute("formAccountList", PlatComAccountStore
				.getFormAccount());// 付款账号
		request.setAttribute("toAccountList", PlatComAccountStore
				.getToAccount());// 收款账号
		request.setAttribute("ulf", ulf);
		forwardPage = "listAirTicketOrderManage";
		return (mapping.findForward(forwardPage));
		
		
	}

	/***************************************************************************
	 * 跳转 编辑订单页 sc
	 **************************************************************************/
	public ActionForward forwardEditTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			String groupMarkNo = request.getParameter("groupMarkNo");
			String id = request.getParameter("id");
			if (groupMarkNo != null && !"".equals(groupMarkNo.trim())) {
				ulf.setGroupMarkNo(groupMarkNo);
				ulf.setList(airticketOrderBiz.list(ulf));

				AirticketOrder ao = airticketOrderBiz
						.getAirticketOrderById(Long.valueOf(id));
				request.setAttribute("airticketOrder", ao);
			} else {
				ulf.setList(new ArrayList());
			}
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("airticketOrderList", ulf);
		forwardPage = "editTradingOrder";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * *---团队专用
	 */

	// -----------销售------------
	// 待处理新订单----利润统计
	public ActionForward listTeamNewAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_101);// 新订单,待统计利润
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);// 买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));

		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamNewAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 待申请支付订单-------申请支付
	public ActionForward listTeamForpayAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_111);// 新订单,待统申请
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);// 买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));

		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamForpayAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 团队待确认支付订单 ---------确认支付
	public ActionForward listTeamAppAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_102);// 申请成功，等待支付
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);// 买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
			airticketOrderBiz.editTeamAirticketOrderAccount(ulf, request,
					response);
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		String systemTime = DateUtil.getDateString(new Timestamp(System
				.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
		request.setAttribute("thisTime", systemTime);
		request.setAttribute("sys_userName", uri.getUser().getUserName());// 操作人
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamAppAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 等待出票订单 --------确认出票
	public ActionForward listTeamWaitOutAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_103);// 支付成功，等待出票
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);// 买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamWaitOutAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 完成出票订单
	public ActionForward listTeamOverAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_105);// 出票成功，交易结束
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);// 买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamOverAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 申请支付
	public ActionForward updateForpayTeamAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try {
			if (airticketOrderId != null && (!(airticketOrderId.equals("")))) {
				airticketOrderBiz.editTeamForpayAirticketOrder(
						airticketOrderId, uri, request, response);
				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=listTeamAppAirticketOrder");
			} else {
				inf.setMessage("申请支付失败！");
				inf.setBack(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 确认出票
	public ActionForward updateTeamAirticketOrderOver(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		String groupMarkNo = request.getParameter("groupMarkNo");
		try {
			if (airticketOrderId != null && (!(airticketOrderId.equals("")))) {
				airticketOrderBiz.editTeamAirticketOrderOver(groupMarkNo,
						airticketOrderId, uri, request, response);
				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=listTeamOverAirticketOrder");
			} else {
				inf.setMessage("申请支付失败！");
				inf.setBack(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// --------退票----------
	// 等审核退废订单
	public ActionForward listTeamNewRefundAirticketOrde(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_107);// 新团队退票订单
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));

		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamNewRefundAirticketOrde";
		return (mapping.findForward(forwardPage));
	}

	// 申请退票
	public ActionForward updateTeamRefundAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try {
			if (airticketOrderId != null && (!(airticketOrderId.equals("")))) {
				airticketOrderBiz.editTeamRefundAirticketOrder(
						airticketOrderId, uri, request, response);
				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=listTeamWaitRefundAirticketOrder");
			} else {
				inf.setMessage("申请退票失败！");
				inf.setBack(true);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 已审待退款订单
	public ActionForward listTeamWaitRefundAirticketOrder(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_108);// 退票审核通过，等待退款
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
			airticketOrderBiz.editTeamAirticketOrderAccount(ulf, request,
					response);

		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		String systemTime = DateUtil.getDateString(new Timestamp(System
				.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss");
		request.setAttribute("newTime", systemTime);
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamWaitRefundAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 完成退款订单
	public ActionForward listTeamOverRefundAirticketOrder(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_109);// 退票审核通过，等待退款
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);// 团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);// 过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));

		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamOverRefundAirticketOrder";
		return (mapping.findForward(forwardPage));
	}

	// 查看订单详细信息
	public ActionForward viewAirticketOrderPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		Inform inf=new Inform();
		try {
			forwardPage=airticketOrderBiz.viewAirticketOrderPage(request);
			if ("ERROR".equals(forwardPage)) {
				inf.setMessage("程序异常,请联系技术支持");
				inf.setBack(true);
				request.setAttribute("inf", inf);
				forwardPage = "inform";
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "viewAirticketOrder";
		return mapping.findForward(forwardPage);

	}

	// 根据机票类型查询团队机票 lrc
	public ActionForward listTeamAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		try {
			if (ulf == null)
				ulf = new AirticketOrderListForm();
			ulf.setTicketType(AirticketOrder.TICKETTYPE_2);// 团队
			ulf.setTeamStatus(AirticketOrder.STATUS_88);// 已废弃
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception e) {
			// TODO: handle exception
			ulf.setList(new ArrayList());
			e.printStackTrace();
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamAirticketOrder";
		return (mapping.findForward(forwardPage));

	}

	// 编辑团队机票订单
	public ActionForward updaTempAirticketOrderPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrderForm = new AirticketOrder();
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		try {
			String airticketOrderId = request.getParameter("airticketOrderId");
			if (airticketOrderId != null && (!airticketOrderId.equals(""))) {
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(Long.parseLong(airticketOrderId));
				request.setAttribute("airticketOrder", airticketOrder);
				airticketOrderBiz
						.updateTeamAirticketOrderPage(uri, airticketOrderForm,
								airticketOrderId, request, response);
			}
			// 调用AirticketOrderBizImp里的修改利润显示方法
			airticketOrderBiz.updaTempAirticketOrderPrice(airticketOrderForm,
					Long.parseLong(airticketOrderId), request, response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		forwardPage = "editTeamTradingOrder";
		return mapping.findForward(forwardPage);

	}

	// 删除订单(改变状态)
	public ActionForward deleteAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String airticketOrderId = request.getParameter("airticketOrderId");
		String num = request.getParameter("num");
		Inform inf = new Inform();
		try {
			if (airticketOrderId != null && (!airticketOrderId.equals(""))) {
				airticketOrderBiz.deleteAirticketOrder(airticketOrderId);
				if (Long.parseLong(num) == 1) {
					return new ActionRedirect(
							"/airticket/listAirTicketOrder.do?thisAction=list");
				}
				if (Long.parseLong(num) == 2) {
					return new ActionRedirect(
							"/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder");
				}
			} else {
				inf.setMessage("您删除机票订单数据失败！");
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

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}

	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}
}
