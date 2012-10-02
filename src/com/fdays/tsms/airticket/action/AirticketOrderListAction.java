package com.fdays.tsms.airticket.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.system.TicketLog;
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
			ulf.setFiltrateTicketType(String.valueOf(AirticketOrder.TICKETTYPE_2));//过滤掉团队订单
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listAirTicketOrder";
		return (mapping.findForward(forwardPage));
	}
    
    //查看详细信息 lrc
    public ActionForward viewAirticketOrderPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    		String forwardPage ="";
    		try {
				String aircketOrderId = request.getParameter("aircketOrderId");
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(Long.parseLong(aircketOrderId));
				request.setAttribute("airticketOrder", airticketOrder);
				String groupMarkNo = airticketOrder.getGroupMarkNo();
				if(groupMarkNo !=null && (!groupMarkNo.equals("")))
				{
					TicketLog ticketLog =ticketLogBiz.getTicketLogByOrderNo(groupMarkNo);//根据订单号查询操作日志
					request.setAttribute("ticketLog", ticketLog);
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
			ulf.setMoreStatus("19,29,20,30");
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
			ulf.setMoreStatus("39");
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
	 * 确定改签订单  sc
	 **************************************************************************/
    public ActionForward  listConfirmUmbuchenOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			//ulf.setAirticketOrder_status(AirticketOrder.STATUS_41); 
			ulf.setMoreStatus("40,41,42,43");
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
	 * 等待回收票款订单  sc
	 **************************************************************************/
    public ActionForward  listWaitRecoveryTicketOrders(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			
			ulf.setTicketType(AirticketOrder.TICKETTYPE_3); 
			ulf.setList(airticketOrderBiz.list(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "listWaitRecoveryTicketOrders";
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
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			String groupMarkNo=request.getParameter("groupMarkNo");
			if(groupMarkNo!=null&&!"".equals(groupMarkNo.trim())){
			ulf.setGroupMarkNo(groupMarkNo);
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
			if(groupMarkNo!=null&&!"".equals(groupMarkNo.trim())){
			ulf.setGroupMarkNo(groupMarkNo);
			ulf.setList(airticketOrderBiz.list(ulf) );
			if(ulf.getList().size()>0){
				AirticketOrder ao=(AirticketOrder)ulf.getList().get(0);
				request.setAttribute("airticketOrder", ao);
			}
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
    public ActionForward  getTempAirticketOrderByticketType(ActionMapping mapping, ActionForm form,
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
		forwardPage = "listTempAirticketOrder";
		return (mapping.findForward(forwardPage));

    }
    
    //编辑团队机票订单
    public ActionForward  updaTempAirticketOrderPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {    		
    		String forwardPage ="";
    		try {
    			String airticketOrderId = request.getParameter("airticketOrderId");
    	    	if(airticketOrderId != null && (!airticketOrderId.equals("")))
    	    	{
    	    		AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(Long.parseLong(airticketOrderId));
    	    		request.setAttribute("airticketOrder", airticketOrder);
    	    		airticketOrderBiz.updateAirticketOrderTempPage(airticketOrderId, request, response);
    	    	}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			forwardPage="editTeamTradingOrder";
    	return mapping.findForward(forwardPage);
    	
    }
    
  //删除团队订单票(改变状态)
	public ActionForward updateAirticketOrderByStatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		String airticketOrderId=request.getParameter("airticketOrderId");
		Inform inf = new Inform();
		try {
			if(airticketOrderId != null && (!airticketOrderId.equals("")))
			{
					airticketOrderBiz.deleteAirticketOrderTempByStateus(airticketOrderId);
				
				inf.setMessage("您已经成功删除机票订单数据！");
				inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=getTempAirticketOrderByticketType");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
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

    //编辑团队结算表(利润统计)
	
    
    
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
