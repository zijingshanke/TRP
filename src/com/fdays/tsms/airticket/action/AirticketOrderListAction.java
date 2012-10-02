package com.fdays.tsms.airticket.action;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.TicketLogListForm;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirticketOrderListAction extends BaseAction{

	public AirticketOrderBiz airticketOrderBiz;
	TicketLogBiz ticketLogBiz;
	/***************************************************************************
	 * 全部订单  sc
	 **************************************************************************/
    public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listAirTicketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    /**
     **---团队专用
     */ 
    
    //-----------销售------------
    //（新团队销售订单查询--新团队销售订单）1
    public ActionForward listTeamNewAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_101);//新订单,待统计利润
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);//买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamNewAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
    
  //（新订单,待统计利润--新订单,待统申请）
    public ActionForward listTeamForpayAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_111);//新订单,待统申请
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);//买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamForpayAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
   
  //（新团队订单查询--申请成功，等待支付）
    public ActionForward listTeamAppAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();
			
		try {
			ulf.setTeam_status(AirticketOrder.STATUS_102);//申请成功，等待支付
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);//买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
			airticketOrderBiz.editTeamAirticketOrderAgentName(ulf,request, response);
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
		DateFormat df = DateFormat.getDateTimeInstance();
		request.setAttribute("thisTime", df.format(date));//暂时用系统时间
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamAppAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
    
  //（新团队订单查询--支付成功，等待出票）
    public ActionForward listTeamWaitOutAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_103);//支付成功，等待出票
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);//买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamWaitOutAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
    
  //（新团队订单查询--出票成功，交易结束）
    public ActionForward listTeamOverAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_105);//出票成功，交易结束
			ulf.setTeamTran_type(AirticketOrder.TRANTYPE__2);//买入
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamOverAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    
  //修改状态（新订单,待统计申请--->>申请成功，等待支付）
    public ActionForward UpdateTeamAirticketOrderT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    	String forwardPage = "";
    	UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
		"URI");
    	Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try {
			if(airticketOrderId !=null &&(!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamAirticketOrderT( airticketOrderId,uri, request, response);
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamAppAirticketOrder");
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
    
  //修改状态（支付成功，等待出票--->>出票成功，交易结束）
    public ActionForward updateTeamAirticketOrderOver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    	String forwardPage = "";
    	UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
		"URI");
    	Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try {
			if(airticketOrderId !=null &&(!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamAirticketOrderOver( airticketOrderId,uri, request, response);
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamOverAirticketOrder");
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
    
    
    
    //--------退票----------
    //（新团队退票订单查询--新团队退票订单）
    public ActionForward listTeamNewRefundAirticketOrde(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_107);//新团队退票订单
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamNewRefundAirticketOrde";
		return (mapping.findForward(forwardPage));
	}
    
  //修改状态（新团队退票订单--->>退票审核通过，等待退款）
    public ActionForward updateTeamRefundAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    	String forwardPage = "";
    	UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
		"URI");
    	Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try {
			if(airticketOrderId !=null &&(!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamRefundAirticketOrder( airticketOrderId,uri, request, response);
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamWaitRefundAirticketOrder");
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
    
  //（团队退票订单查询--退票订单，等待审核）
    public ActionForward listTeamWaitRefundAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_108);//退票审核通过，等待退款
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamWaitRefundAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    //修改状态（退票审核通过，等待退款--->>已经退款，交易结束）
    public ActionForward updateTeamRefundAirticketOrderOverT(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    	String forwardPage = "";
    	UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
		"URI");
    	Inform inf = new Inform();
		String airticketOrderId = request.getParameter("airticketOrderId");
		try {
			if(airticketOrderId !=null &&(!(airticketOrderId.equals(""))))
			{
				airticketOrderBiz.editTeamRefundAirticketOrderOver( airticketOrderId,uri, request, response);
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamOverRefundAirticketOrder");
			} else {
				inf.setMessage("确认退票失败！");
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
    
  //（团队退票订单查询--退票订单，等待审核）
    public ActionForward listTeamOverRefundAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setTeam_status(AirticketOrder.STATUS_109);//退票审核通过，等待退款
			ulf.setTeamTicket_type(AirticketOrder.TICKETTYPE_2);//团队订单
			ulf.setScrapTeam_status(AirticketOrder.STATUS_88);//过滤已废弃的订单
			ulf.setList(airticketOrderBiz.teamAirticketOrderList(ulf));
		
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamOverRefundAirticketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    
    
    //查看详细信息 lrc
    public ActionForward viewAirticketOrderPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    		String forwardPage ="";
    		try {
				String aircketOrderId = request.getParameter("aircketOrderId");
				String groupMarkNo = request.getParameter("groupMarkNo");
				String tranType= request.getParameter("tranType");
				if(aircketOrderId !=null && (!aircketOrderId.equals("")))
				{
				//	AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(Long.parseLong(aircketOrderId));
					AirticketOrder airticketOrder = airticketOrderBiz.viewAirticketOrderPage(aircketOrderId,groupMarkNo,tranType, request, response);
					airticketOrder.setThisAction("viewAirticketOrderPage");
					request.setAttribute("airticketOrder", airticketOrder);
					if(groupMarkNo !=null && (!groupMarkNo.equals("")))
					{
						TicketLogListForm ticketLogForm  = new TicketLogListForm();
						ticketLogForm.setOrderNo(groupMarkNo);//订单号
						//ticketLogForm.setPerPageNum(3);//设置3条数据
						List<TicketLog> ticketLogList = ticketLogBiz.getTicketLogs(ticketLogForm);
						request.setAttribute("ticketLogList", ticketLogList);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			forwardPage="viewAirticketOrder";
    	return mapping.findForward(forwardPage);
    	
    }
    
    /***************************************************************************
	 * 新订单  sc
	 **************************************************************************/
    public ActionForward listNewAirTicketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_1);// 
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listNewAirTicketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    /***************************************************************************
	 * 等待支付订单  sc
	 **************************************************************************/
    public ActionForward listWaitPayOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_2);//申请成功，等待支付 
			ulf.setMoreStatus("2,7,8");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitPayOrder";
		return (mapping.findForward(forwardPage));
	}
    
    /***************************************************************************
	 * 等待出票订单  sc
	 **************************************************************************/
    public ActionForward listWaitDrawOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_3);//申请成功，等待出票 
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitDrawOrder";
		return (mapping.findForward(forwardPage));
	}
	
    /***************************************************************************
	 * 出票完成订单  sc
	 **************************************************************************/
    public ActionForward listDrawSuccessOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_5);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listDrawSuccessOrder";
		return (mapping.findForward(forwardPage));
	}
    /***************************************************************************
	 * 取消待退款订单[确认退款]  sc
	 **************************************************************************/
    public ActionForward listConfirmRefundment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_4);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listConfirmRefundment";
		return (mapping.findForward(forwardPage));
	}
    /***************************************************************************
	 * 取消已退款订单  sc
	 **************************************************************************/
    public ActionForward listRefundmentEnd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setAirticketOrder_status(AirticketOrder.STATUS_6);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listRefundmentEnd";
		return (mapping.findForward(forwardPage));
	}
    /***************************************************************************
	 * 等待审核退废订单  sc
	 **************************************************************************/
    public ActionForward listWaitAgreeRetireOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_30); 
			ulf.setMoreStatus("19,29,20,30,24,25,34,35");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitAgreeRetireOrder";
		return (mapping.findForward(forwardPage));
	}
    
    
    /***************************************************************************
	 * 确定退废订单  sc
	 **************************************************************************/
    public ActionForward listConfirmRetireOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_30); 
			ulf.setMoreStatus("21,31");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listConfirmRetireOrder";
		return (mapping.findForward(forwardPage));
	}
    /***************************************************************************
	 * 完成退款订单  sc
	 **************************************************************************/
    public ActionForward listSuccessRetireOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_30); 
			ulf.setMoreStatus("22,32");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listSuccessRetireOrder";
		return (mapping.findForward(forwardPage));
	}
    
    
    /***************************************************************************
	 * 审核不通过 退废订单  sc
	 **************************************************************************/
    public ActionForward listnoPassRetireOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_30); 
			ulf.setMoreStatus("23,33");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listnoPassRetireOrder";
		return (mapping.findForward(forwardPage));
	}
    
    /***************************************************************************
	 * 等待审核改签订单  sc
	 **************************************************************************/
    public ActionForward  listWaitAgreeUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_41); 
			ulf.setMoreStatus("39,46");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
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
    public ActionForward  listLoadUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_41); 
			ulf.setMoreStatus("40,41,42");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listLoadUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}
    
    /***************************************************************************
	 * 确定改签订单（已付待确认订单）  sc
	 **************************************************************************/
    public ActionForward  listConfirmUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_41);40,41,42, 
			ulf.setMoreStatus("43");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listConfirmUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}
    
    /***************************************************************************
	 * 完成改签订单  sc
	 **************************************************************************/
    public ActionForward  listSuccessUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_41); 
			ulf.setMoreStatus("45");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listSuccessUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}
    /***************************************************************************
	 * 改签不通过订单  sc
	 **************************************************************************/
    public ActionForward  listnoPassUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_41); 
			ulf.setMoreStatus("44");
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤废弃订单
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listnoPassUmbuchenOrder";
		return (mapping.findForward(forwardPage));
	}
    /***************************************************************************
	 * 等待回收票款订单  sc
	 **************************************************************************/
    public ActionForward  listWaitRecoveryTicketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			
			ulf.setTicketType(AirticketOrder.TICKETTYPE_3); 
			ulf.setTranType(AirticketOrder.TRANTYPE__1);//卖出
			ulf.setB2C_status(AirticketOrder.STATUS_80);//过滤交易结束的订单
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//过滤已废弃的票
			ulf.setList(airticketOrderBiz.b2cAirticketOrderList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitRecoveryTicketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    
    
    /***************************************************************************
	 *  查询  关联订单 sc
	 **************************************************************************/
    public ActionForward  tradingOrderProcessing(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		ulf.setPerPageNum(100);
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			String groupMarkNo=request.getParameter("groupMarkNo");
			if(groupMarkNo!=null&&!"".equals(groupMarkNo.trim())){
			ulf.setGroupMarkNo(groupMarkNo);
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//已废弃
			ulf.setList(airticketOrderBiz.list(ulf) );
			}else{
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
	 *  跳转 编辑订单页  sc
	 **************************************************************************/
    public ActionForward  forwardEditTradingOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			String groupMarkNo=request.getParameter("groupMarkNo");
			String id=request.getParameter("id");
			if(groupMarkNo!=null&&!"".equals(groupMarkNo.trim())){
			ulf.setGroupMarkNo(groupMarkNo);
			ulf.setList(airticketOrderBiz.list(ulf) );
			/*if(ulf.getList().size()>0){
				AirticketOrder ao=(AirticketOrder)ulf.getList().get(0);
				request.setAttribute("airticketOrder", ao);
			}*/
			AirticketOrder ao=airticketOrderBiz.getAirticketOrderById(Long.valueOf(id));
			request.setAttribute("airticketOrder", ao);
			}else{
				ulf.setList(new ArrayList());
			}
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("airticketOrderList", ulf);
		forwardPage = "editTradingOrder";
		return (mapping.findForward(forwardPage));
	}
    //根据机票类型查询团队机票 lrc
    public ActionForward  listTeamAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		try {
			if (ulf == null)
				ulf = new AirticketOrderListForm();
			ulf.setTicketType(AirticketOrder.TICKETTYPE_2);//团队
			ulf.setTeamStatus(AirticketOrder.STATUS_88);//已废弃
			ulf.setList(airticketOrderBiz.list(ulf) );
		} catch (Exception e) {
			// TODO: handle exception
			ulf.setList(new ArrayList());
			e.printStackTrace();
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listTeamAirticketOrder";
		return (mapping.findForward(forwardPage));

    }
    
    //编辑团队机票订单
    public ActionForward  updaTempAirticketOrderPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {    		
    		AirticketOrder airticketOrderForm=new AirticketOrder();
    		String forwardPage ="";
    		try {
    			String airticketOrderId = request.getParameter("airticketOrderId");
    	    	if(airticketOrderId != null && (!airticketOrderId.equals("")))
    	    	{
    	    		AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(Long.parseLong(airticketOrderId));
    	    		request.setAttribute("airticketOrder", airticketOrder);
    	    		airticketOrderBiz.updateAirticketOrderTempPage(airticketOrderId, request, response);
    	    	}
    	    	//调用AirticketOrderBizImp里的修改利润显示方法
    	    	airticketOrderBiz.updaTempAirticketOrderPrice(airticketOrderForm, Long.parseLong(airticketOrderId), request, response);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			forwardPage="editTeamTradingOrder";
    	return mapping.findForward(forwardPage);
    	
    }

  //删除订单(改变状态)
	public ActionForward deleteAirticketOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		String airticketOrderId=request.getParameter("airticketOrderId");
		String num  =request.getParameter("num"); 
		Inform inf = new Inform();
		try {
			if(airticketOrderId != null && (!airticketOrderId.equals("")))
			{
					airticketOrderBiz.deleteAirticketOrder(airticketOrderId);
				

				if(Long.parseLong(num) ==1)
				{
					return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=list");
				}
				if(Long.parseLong(num) ==2)
				{
					return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamAirticketOrder");
				}
			}else{
				inf.setMessage("您删除机票订单数据失败！");
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

    
	public AirticketOrderBiz getAirticketOrderBiz() {
		return airticketOrderBiz;
	}
	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}


	public TicketLogBiz getTicketLogBiz() {
		return ticketLogBiz;
	}


	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}
    
}
