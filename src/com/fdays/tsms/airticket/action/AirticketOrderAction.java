package com.fdays.tsms.airticket.action;

import java.sql.Timestamp;
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
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirticketOrderAction extends BaseAction
{
	public TempPNRBiz tempPNRBiz;
	public AirticketOrderBiz airticketOrderBiz;
	public PassengerBiz passengerBiz;
	public TicketLogBiz ticketLogBiz;

	/***************************************************************************
	 * 解析黑屏信息获取订单数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByBlackPNR(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		TempPNR tempPNR=new TempPNR();
		
		try
		{
			String pnrInfo = request.getParameter("pnrInfo");
			if (pnrInfo != null && !"".equals(pnrInfo.trim()))
			{
				tempPNR = tempPNRBiz.getTempPNRByBlackInfo(pnrInfo);
		

				if (tempPNR != null)
				{
					AirticketOrder checkAO = new AirticketOrder();
					checkAO.setTranType(AirticketOrder.TRANTYPE__1);
					checkAO.setSubPnr(tempPNR.getPnr());
					boolean checkPnr = airticketOrderBiz.checkPnrisMonth(checkAO); // 验证pnr是否重复添加
					if (checkPnr == false)
					{
						request.setAttribute("msg", "已存在相同PNR!");
					}

					request.setAttribute("tempPNR", tempPNR);
					airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
					// 设置临时会话
					UserRightInfo uri = (UserRightInfo) request.getSession()
					    .getAttribute("URI");
					uri.setTempPNR(tempPNR);
					forwardPage = airticketOrderFrom.getForwardPage();
					System.out.println("==========forwardPage===="+forwardPage);
				}
				else
				{
					inf.setMessage("导入异常");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		}
		catch (Exception e)
		{
			inf.setMessage("导入异常");
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
	public ActionForward getAirticketOrderForRetireUmbuchen(
	    ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			if (airticketOrderFrom.getPnr() != null && !"".equals(airticketOrderFrom.getPnr().trim())){
				airticketOrderFrom.setSubPnr(airticketOrderFrom.getPnr());
				System.out.println("TranType====>" + airticketOrderFrom.getTranType());
				// if(airticketOrderBiz.checkPnrisMonth(airticketOrderFrom)){

				// 根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderForRetireUmbuchen(airticketOrderFrom.getPnr(),
				        airticketOrderFrom.getBusinessType(), airticketOrderFrom.getTranType());

				if (airticketOrder != null && airticketOrder.getId() != 0L){
					request.setAttribute("airticketOrder", airticketOrder);
					forwardPage = airticketOrderFrom.getForwardPage();
				}else{
					inf.setMessage("PNR 不存在！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		}catch (Exception e){
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
	public ActionForward getAirticketOrderForRetireUmbuchenBySelect(
	    ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    HttpServletResponse response) throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			String aoId = request.getParameter("aoId");
			if (aoId != null && !"".equals(aoId.trim())){

				// 根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderById(Long.valueOf(aoId));

				if (airticketOrder != null && airticketOrder.getId() != 0L)	{
					request.setAttribute("airticketOrder", airticketOrder);
					forwardPage = airticketOrderFrom.getForwardPage();
				}else{
					inf.setMessage("订单不存在");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		}
		catch (Exception e)	{
			inf.setMessage("导入异常");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 通过 信息PNR获取外部数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByBlackOutPNR(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.airticketOrderByBlackOutPNR(request,airticketOrderFrom);
		}
		catch (Exception e){
			inf.setMessage("获取外部PNR信息异常");
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
	    throws Exception{
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		uri.setTempPNR(null);
		return (mapping.findForward("addOrder"));
	}

	/***************************************************************************
	 * 倒票-订单录入 sc
	 **************************************************************************/
	public ActionForward createOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.createOrder(request,airticketOrderForm);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_1+"");
		}
		catch (Exception e){
			e.printStackTrace();
			inf.setMessage("订单录入异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * B2C-订单录入 sc
	 **************************************************************************/
	public ActionForward createB2COrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.createB2COrder(request,airticketOrderForm);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_1+"");
		}
		catch (Exception e){
			e.printStackTrace();
			inf.setMessage("订单录入异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 申请支付 sc
	 **************************************************************************/
	public ActionForward applyTicket(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.createApplyTickettOrder(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_1+"");
		}
		catch (Exception e)	{
			e.printStackTrace();
			inf.setMessage("申请支付异常");
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
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.anewApplyTicket(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_1+"");
		}
		catch (Exception e){
			e.printStackTrace();
			inf.setMessage("重新申请支付异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 锁定
	 * */
	public ActionForward lockupOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try		{
			forwardPage = airticketOrderBiz.lockupOrder(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_2+","+AirticketOrder.STATUS_7+","+AirticketOrder.STATUS_8);
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("锁定异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 解锁(自己的订单)
	 * */
	public ActionForward unlockSelfOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try		{
			forwardPage = airticketOrderBiz.unlockSelfOrder(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_2+","+AirticketOrder.STATUS_7+","+AirticketOrder.STATUS_8);
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("解锁自己的订单异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 解锁(所有人的订单)
	 * */
	public ActionForward unlockAllOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try		{
			forwardPage = airticketOrderBiz.unlockAllOrder(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_2+","+AirticketOrder.STATUS_7+","+AirticketOrder.STATUS_8);
		}catch (Exception e){
			e.printStackTrace();
			inf.setMessage("解锁他人订单异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 确认支付 sc
	 **************************************************************************/
	public ActionForward confirmPayment(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";

		Inform inf = new Inform();
		try		{
			forwardPage = airticketOrderBiz.confirmPayment(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_2+","+AirticketOrder.STATUS_7+","+AirticketOrder.STATUS_8);
		}catch (Exception e){
			e.printStackTrace();
			inf.setMessage("确认支付异常");
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
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.confirmTicket(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true,forwardPage,AirticketOrder.STATUS_3+",");
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("确认出票异常");
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
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";

		Inform inf = new Inform();
		try		{
			forwardPage = airticketOrderBiz.quitTicket(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,false,"","");
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("取消出票(未支付)异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		
		
	
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 取消出票,确认退款
	 **************************************************************************/
	public ActionForward agreeCancelRefund(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try		{
			forwardPage = airticketOrderBiz.agreeCancelRefund(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,false,"","");
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("取消出票(已支付)异常");
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
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.updateAirticketOrderStatus(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,false,"","");
		}catch (Exception e)	{
			e.printStackTrace();
			inf.setMessage("更新订单异常");
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
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.editRemark(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,false,"","");
		}catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("编辑备注异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 创建内部退废 订单 sc
	 **************************************************************************/
	public ActionForward addRetireOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.addRetireOrder(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,true,AirticketOrder.ORDER_GROUP_TYPE3 + "","19,29,20,30,24,25,34,35");
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("创建退废订单（内部）异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 审核退废票（卖出单，第一次通过申请，创建买入退废单）
	 **************************************************************************/
	public ActionForward auditRetire(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try	{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0){
				forwardPage = airticketOrderBiz.auditRetire(airticketOrderFrom,uri);
				return redirectManagePage(mapping, request,true,AirticketOrder.ORDER_GROUP_TYPE3 + "","19,29,20,30,24,25,34,35");
			}else{
				inf.setMessage("表单不能为空");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("第一次通过申请异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 审核退废票（卖出单，第二次通过申请，更新卖出退废订单）
	 **************************************************************************/
	public ActionForward auditRetire2(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try	{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0){
				airticketOrderBiz.auditRetire2(airticketOrderFrom, request);
				return redirectManagePage(mapping, request,true,AirticketOrder.ORDER_GROUP_TYPE3 + "","19,29,20,30,24,25,34,35");
			}
			else{
				inf.setMessage("表单不能为空");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}
		catch (Exception e)		{
			e.printStackTrace();
			inf.setMessage("第二次通过申请");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}
	
	/***************************************************************************
	 * 审核退废 并且创建（ 收款订单） 订单 sc 外部
	 **************************************************************************/
	public ActionForward auditOutRetire(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.auditOutRetire(airticketOrderFrom,request);
			return redirectManagePage(mapping, request,true, forwardPage,"19,29,20,30,24,25,34,35");
		}
		catch (Exception e)	{
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 审核退废2 sc 外部
	 **************************************************************************/
	public ActionForward auditOutRetire2(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try{
			airticketOrderBiz.auditRetire2(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true, "93","19,29,20,30,24,25,34,35");			
		}catch (Exception e)	{
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}


	/***************************************************************************
	 * 确认退票/废票 收、退款 sc
	 **************************************************************************/
	public ActionForward collectionRetire(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try	{
			forwardPage = airticketOrderBiz.collectionRetire(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true,forwardPage,"21,31");
		}catch (Exception e){
			e.printStackTrace();
			inf.setMessage("确认收、退款异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try	{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0){
				AirticketOrder airticketOrder = airticketOrderBiz .getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_39); // 订单状态
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
				if (airticketOrder.getAccount() != null){
					airticketOrderFrom.setPlatformId(airticketOrder.getPlatform().getId());
					airticketOrderFrom.setCompanyId(airticketOrder.getCompany().getId());
					airticketOrderFrom.setAccountId(airticketOrder.getAccount().getId());
				}
				airticketOrderBiz.createUmbuchenOrder(airticketOrderFrom,airticketOrder, uri);

				return redirectManagePage(mapping, request,true,"92","39,46");
			}else{
				inf.setMessage("创建失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}catch (Exception e){
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
	public ActionForward auditUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0){
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());

				AirticketOrder ao = airticketOrderBiz.getDrawedAirticketOrderByGroupId(airticketOrder.getOrderGroup().getId(), AirticketOrder.TRANTYPE__2);

				airticketOrderFrom.setDrawPnr(ao.getDrawPnr());
				airticketOrderFrom.setBigPnr(ao.getBigPnr());
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_41); // 订单状态
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_73);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

				String platformId = request.getParameter("platformId5");
				String companyId = request.getParameter("companyId5");
				String accountId = request.getParameter("accountId5");
				if (platformId != null){
					airticketOrderFrom.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null){
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null){
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}
				airticketOrderBiz.createUmbuchenOrder(airticketOrderFrom, ao,uri);

				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				return redirectManagePage(mapping, request,true,"92","39,46");
			}else{
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}catch (Exception e){
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
	public ActionForward receiptUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute("URI");
		Inform inf = new Inform();
		try{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0){
				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderById(airticketOrderFrom.getId());

				if (airticketOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1){
					List listao = airticketOrderBiz.listByGroupIdAndBusinessTranType(
					    airticketOrder.getOrderGroup().getId(), AirticketOrder.TRANTYPE_5
					        + "", AirticketOrder.BUSINESSTYPE__2 + "");
					if (listao != null && listao.size() > 0){
						AirticketOrder ao = (AirticketOrder) listao.get(0);
						ao.setStatus(AirticketOrder.STATUS_42);
						airticketOrderBiz.update(ao);
					}

				}
				if (airticketOrderFrom.getTotalAmount() != null){
					airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());
				}
				airticketOrder.setStatus(AirticketOrder.STATUS_43);
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getOrderGroup().getNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog().getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setType(TicketLog.TYPE_74);// 操作日志 类型
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				return redirectManagePage(mapping, request,true,"92","39,46");
			}else{
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}catch (Exception e){
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 确认收款，改签完成（原updateOrderStatus）
	 * */
	public ActionForward finishUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.finishUmbuchenOrder(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true,forwardPage,"43");
		}
		catch (Exception e){
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
	public ActionForward addOrderByHand(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.addOrderByHand(request);
			return redirectManagePage(mapping, request,true,forwardPage,"");
		}
		catch (Exception e){
			e.printStackTrace();
			inf.setMessage("添加异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 修改订单 sc
	 **************************************************************************/
	public ActionForward updateOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try{
			forwardPage = airticketOrderBiz.editOrder(airticketOrderFrom,request);
			return redirectProcessingPage(mapping, request, forwardPage);
		}catch (Exception e){
			e.printStackTrace();
			inf.setMessage("编辑异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 通过外部（prn信息或黑屏信息）创建退废 订单 sc
	 **************************************************************************/
	public ActionForward addOutRetireOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.createOutRetireOrder(
			    airticketOrderFrom, request);
			return redirectManagePage(mapping, request,true, forwardPage,"39,46");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("添加异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	
	/***************************************************************************
	 * 通过外部pnr创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addOutUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Inform inf = new Inform();
		try
		{
			TempPNR tempPNR = uri.getTempPNR();
			if (tempPNR != null)
			{
				airticketOrderFrom.setDrawPnr(tempPNR.getPnr());// 出票pnr
				airticketOrderFrom.setSubPnr(tempPNR.getPnr());// 预订pnr
				airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());// 大pnr
				airticketOrderFrom.setTicketPrice(tempPNR.getFare());// 票面价格
				airticketOrderFrom.setAirportPrice(tempPNR.getTax());// 机建费
				airticketOrderFrom.setFuelPrice(tempPNR.getYq());// 燃油税

				airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1);
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_46); // 订单状态
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);//
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型

				String platformId = request.getParameter("platformId");
				String companyId = request.getParameter("companyId");
				String accountId = request.getParameter("accountId");
				if (platformId != null)
				{
					airticketOrderFrom.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null)
				{
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null)
				{
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}

				airticketOrderBiz.createOutUmbuchenOrder(airticketOrderFrom,
				    tempPNR, new AirticketOrder(), uri);

				return redirectManagePage(mapping, request,true, "92","39,46");
			}
			else
			{
				inf.setMessage("创建失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 审核改签 订单(外部) sc
	 **************************************************************************/
	public ActionForward auditOutUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Inform inf = new Inform();
		try
		{
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0)
			{

				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderById(airticketOrderFrom.getId());

				if (airticketOrder != null)
				{
					airticketOrderFrom.setDrawPnr(airticketOrder.getDrawPnr());// 出票pnr
					airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
					airticketOrderFrom.setBigPnr(airticketOrder.getBigPnr());// 大pnr
					airticketOrderFrom.setTicketPrice(airticketOrder.getTicketPrice());// 票面价格
					airticketOrderFrom.setAirportPrice(airticketOrder.getAirportPrice());// 机建费
					airticketOrderFrom.setFuelPrice(airticketOrder.getFuelPrice());// 燃油税
				}

				airticketOrderFrom.setStatus(AirticketOrder.STATUS_41); // 订单状态
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_73);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
				String platformId = request.getParameter("platformId14");
				String companyId = request.getParameter("companyId14");
				String accountId = request.getParameter("accountId14");

				// 设置平台号
				if (platformId != null)
				{
					airticketOrderFrom.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null)
				{
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null)
				{
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}
				airticketOrderBiz.createOutUmbuchenOrder(airticketOrderFrom,
				    new TempPNR(), airticketOrder, uri);

				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				return redirectManagePage(mapping, request,true,"92","39,46");
			}
			else
			{
				inf.setMessage("操作失败！");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}
	
	//==========================================================团队==========================================
	// 更新团队正常订单信息
	public ActionForward updateTeamAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder orderform = (AirticketOrder) form;		
		long id=orderform.getId();		 
		try
		{
			if (id > 0)
			{			
				airticketOrderBiz.updateTeamAirticketOrder(orderform,request);//	
				airticketOrderBiz.editTeamOrder(id, request);
			}
			else
			{
				id=airticketOrderBiz.updateTeamAirticketOrder(orderform,request);//	
 				airticketOrderBiz.editTeamOrder(id, request);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();			 
		}
		return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=editTeamOrder&id="+id);
	}
	


	//团队--编辑利润统计
	public ActionForward editTeamProfit(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		String forwardPage = "";
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		Inform inf = new Inform();
		try
		{
			if (airticketOrderForm.getId() > 0)
			{
				airticketOrderBiz.editTeamProfit(airticketOrderForm, request);
				return new ActionRedirect(AirticketOrder.TeamManagePath);
			}
			else
			{
				inf.setMessage("订单ID不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("编辑团队利润异常");
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 团队确认支付
	public ActionForward confirmTeamPayment(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
	
		String forwardPage = "";
		Inform inf = new Inform();
		long airticketOrderId = airticketOrderForm.getId();
		try
		{
			if (airticketOrderId > 0)
			{
				airticketOrderBiz.confirmTeamPayment(airticketOrderForm,request);
				return new ActionRedirect(AirticketOrder.TeamManagePath);
			}
			else
			{
				inf.setMessage("订单ID不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("确认支付异常");
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}

	// 团队退票，确认付退款
	public ActionForward confirmTeamRefundPayment(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		long airticketOrderId = airticketOrderForm.getId();
		try
		{
			if (airticketOrderId > 0)
			{
				airticketOrderBiz.confirmTeamRefundPayment(airticketOrderForm,request);
				return new ActionRedirect(AirticketOrder.TeamManagePath);
			}
			else
			{
				inf.setMessage("订单ID不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("确认付退款异常");
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}

	// 团队退票，确认收退款
	public ActionForward confirmTeamRefundCollection(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		
		String forwardPage = "";
		Inform inf = new Inform();
		long airticketOrderId = airticketOrderForm.getId();
		try
		{
			if (airticketOrderId > 0)
			{
				airticketOrderBiz.confirmTeamRefundCollection(airticketOrderForm,request);
				return new ActionRedirect(AirticketOrder.TeamManagePath);
			}
			else
			{
				inf.setMessage("订单ID不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("确认退款异常");
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}
//	===================================================end 团队 end==========================================

	/***************************************************************************
	 * 通过PNR接口获取数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByPNR(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			if (airticketOrderFrom.getPnr() != null
			    && !"".equals(airticketOrderFrom.getPnr().trim()))
			{
				AirticketOrder checkAO = new AirticketOrder();
				checkAO.setTranType(AirticketOrder.TRANTYPE__1);
				checkAO.setSubPnr(airticketOrderFrom.getPnr());
				boolean checkPnr = airticketOrderBiz.checkPnrisToday(checkAO);
				// 验证pnr是否重复添加
				if (true)
				{
					System.out.println("airticketOrderFrom.getPnr() ---"
					    + airticketOrderFrom.getPnr());
					TempPNR tempPNR = tempPNRBiz.getTempPNRByPnr(airticketOrderFrom
					    .getPnr());
					if (tempPNR != null && tempPNR.getRt_parse_ret_value() != 0L)
					{
						request.setAttribute("tempPNR", tempPNR);
						airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
						// 设置临时会话
						UserRightInfo uri = (UserRightInfo) request.getSession()
						    .getAttribute("URI");
						uri.setTempPNR(tempPNR);
						forwardPage = airticketOrderFrom.getForwardPage();
					}
					else
					{
						inf.setMessage("PNR 错误！");
						inf.setBack(true);
						forwardPage = "inform";
						request.setAttribute("inf", inf);
						System.out.println("PNR 错误");
					}
				}
				else
				{
					inf.setMessage("PNR已存在，请勿重复添加！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);

				}
			}
		}
		catch (Exception e)
		{
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
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			TempPNR tempPNR=new TempPNR();
			String pnr=airticketOrderFrom.getPnr();
			if (pnr!= null&& !"".equals(pnr.trim()))
			{
				String importType=airticketOrderFrom.getImportType();
				if(importType!=null&&"".equals(importType)==false){
					if(importType.trim().equals("oldSystem")){
						tempPNR = tempPNRBiz.getTempPNRByOldSystem(pnr);
					}else{
						tempPNR = tempPNRBiz.getTempPNRByPnr(pnr);
					}
				}else{
					tempPNR = tempPNRBiz.getTempPNRByPnr(pnr);
				}			
			
				
				if (tempPNR != null && tempPNR.getRt_parse_ret_value() != 0L)
				{

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
					for (int i = 0; i < tempPassengerList.size(); i++)
					{
						TempPassenger tempPassenger = tempPassengerList.get(i);
						Passenger passenger = new Passenger();
						passenger.setAirticketOrder(ao);
						passenger.setName(tempPassenger.getName()); // 乘机人姓名
						passenger.setCardno(tempPassenger.getNi());// 证件号
						passenger.setType(1L); // 类型
						passenger.setStatus(1L);// 状态
						if (tempTicketsList != null
						    && tempTicketsList.size() == tempPassengerList.size())
						{
							System.out.println("tempTicketsList===="
							    + tempPNR.getTempTicketsList().get(i));
							passenger.setTicketNumber(tempTicketsList.get(i).toString()); // 票号
						}
						tmpPassengerSet.add(passenger);
					}
					ao.setPassengers(tmpPassengerSet);
					// 航班
					List<TempFlight> tempFlightList = tempPNR.getTempFlightList();
					Set tmpFlightSet = new HashSet();
					for (TempFlight tempFlight : tempFlightList)
					{
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
				}
				else
				{
					inf.setMessage("PNR 错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
					System.out.println("PNR 错误");
				}
			}
		}
		catch (Exception e)
		{
			inf.setMessage("请求超时！！！");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}

	/**
	 * 重定向到XX订单管理页面
	 * @param forwardPage:错误码/orderType
	 * @param isEnforce:是否强制跳转
	 **/
	public ActionForward redirectManagePage(ActionMapping mapping,
	    HttpServletRequest request,boolean isEnforce,String forwardPage,String moreStatus)
	{
		Inform inf = new Inform();
		if (forwardPage != null)
		{
			if ("NOORDER".equals(forwardPage))
			{
				inf.setMessage("订单不存在！");
			}
			else if (Long.parseLong(forwardPage.trim()) > 0)
			{
				ActionRedirect redirect = new ActionRedirect(AirticketOrder.GeneralManagePath);
				
				if(isEnforce){
					redirect.addParameter("orderType", forwardPage);
					redirect.addParameter("moreStatus", moreStatus);					
				}else{
					if(request.getSession().getAttribute("orderType")!=null)
					{
						redirect.addParameter("orderType", request.getSession().getAttribute("orderType"));
					}
					else
					{
						redirect.addParameter("orderType", forwardPage);
					}
					if(request.getSession().getAttribute("moreStatus")!=null)
					{
						redirect.addParameter("moreStatus", request.getSession().getAttribute("moreStatus"));
					}	
				}
				return redirect;
			}
			else if ("NOPNR".equals(forwardPage))
			{
				inf.setMessage("当前PNR不存在");
			}
			else if ("ERROR".equals(forwardPage))
			{
				inf.setMessage("程序异常,请联系技术支持");
			}
			else
			{
				inf.setMessage("操作错误");
			}
		}
		else
		{
			inf.setMessage("操作错误");
		}
		inf.setBack(true);
		forwardPage = "inform";
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 重定向到关联订单处理页面
	 **/
	public ActionForward redirectProcessingPage(ActionMapping mapping,
	    HttpServletRequest request, String forwardPage)
	{
		Inform inf = new Inform();
		if (forwardPage != null)
		{
			if ("NOORDER".equals(forwardPage))
			{
				inf.setMessage("订单不存在！");
			}
			else if ("ERROR".equals(forwardPage))
			{
				inf.setMessage("程序异常,请联系技术支持");
			}
			else if (Long.parseLong(forwardPage.trim()) > 0)
			{
				ActionRedirect redirect = new ActionRedirect(
				    "/airticket/listAirTicketOrder.do?thisAction=processing");
				redirect.addParameter("id", forwardPage);
				return redirect;
			}
		}
		else
		{
			inf.setMessage("操作错误");
		}
		inf.setBack(true);
		forwardPage = "inform";
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	public void setTempPNRBiz(TempPNRBiz tempPNRBiz)
	{
		this.tempPNRBiz = tempPNRBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz)
	{
		this.airticketOrderBiz = airticketOrderBiz;
	}

	public void setPassengerBiz(PassengerBiz passengerBiz)
	{
		this.passengerBiz = passengerBiz;
	}

	public void setTicketLogBiz(TicketLogBiz ticketLogBiz)
	{
		this.ticketLogBiz = ticketLogBiz;
	}
}
