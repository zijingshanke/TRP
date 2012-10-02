package com.fdays.tsms.airticket.action;

import java.math.BigDecimal;
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
import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.SaleResult;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseAction;
import com.neza.base.Constant;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AirticketOrderAction extends BaseAction
{
	public TempPNRBiz tempPNRBiz;
	public AirticketOrderBiz airticketOrderBiz;
	public PassengerBiz passengerBiz;
	public TicketLogBiz ticketLogBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	/**
	 * 解析黑屏信息获取订单数据
	 **/
	public ActionForward airticketOrderByBlackPNR(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder orderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		TempPNR tempPNR = new TempPNR();
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

					// request.setAttribute("tempPNR", tempPNR);
					request.getSession().setAttribute("tempPNR", tempPNR);

					forwardPage = orderForm.getForwardPage();
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
	    HttpServletResponse response) throws Exception
	{
		AirticketOrder orderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			if (orderForm.getPnr() != null && !"".equals(orderForm.getPnr().trim()))
			{

				AirticketOrder checkAO = new AirticketOrder();
				checkAO.setTranType(AirticketOrder.TRANTYPE__1);
				checkAO.setSubPnr(orderForm.getPnr());
				// boolean checkPnr = airticketOrderBiz.checkPnrisMonth(checkAO);
				// //验证pnr是否重复添加
				// if (checkPnr == false)
				// {
				// request.setAttribute("msg", "已存在相同PNR!");
				// }

				// 根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderForRetireUmbuchen(orderForm.getPnr(), orderForm
				        .getBusinessType(), orderForm.getTranType());

				if (airticketOrder != null && airticketOrder.getId() != 0L)
				{
					request.setAttribute("airticketOrder", airticketOrder);
					forwardPage = orderForm.getForwardPage();
				}
				else
				{
					inf.setMessage("系统内不存在此PNR的信息");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		}
		catch (Exception e)
		{
			inf.setMessage("PNR提取内部信息异常");
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
	    HttpServletResponse response) throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			String aoId = request.getParameter("aoId");
			if (aoId != null && !"".equals(aoId.trim()))
			{

				// 根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz
				    .getAirticketOrderById(Long.valueOf(aoId));

				if (airticketOrder != null && airticketOrder.getId() != 0L)
				{
					request.setAttribute("airticketOrder", airticketOrder);
					forwardPage = airticketOrderFrom.getForwardPage();
				}
				else
				{
					inf.setMessage("系统内不存在此PNR的订单信息");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		}
		catch (Exception e)
		{
			inf.setMessage("获取内部PNR信息异常");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 清空 tempPNR
	 **************************************************************************/
	public ActionForward clearTempPNR(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		request.getSession().setAttribute("tempPNR", null);
		return (mapping.findForward("addOrder"));
	}

	/***************************************************************************
	 * B2B正常订单录入
	 **************************************************************************/
	public ActionForward createOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.createOrder(request, airticketOrderForm);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_1 + "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("订单录入异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 申请支付
	 */
	public ActionForward applyPayOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder orderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.applyPayOrder(orderForm, request);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_1 + "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("申请支付异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 重新 申请支付
	 **************************************************************************/
	public ActionForward anewApplyPayOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.anewApplyPayOrder(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_1 + "");
		}
		catch (Exception e)
		{
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
	    throws AppException
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.lockupOrder(airticketOrderFrom, request);
			// return
			// redirectManagePage(mapping,request,true,forwardPage,AirticketOrder.STATUS_2+","+AirticketOrder.STATUS_7+","+AirticketOrder.STATUS_8);
			// 指定翻页
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_2 + "," + AirticketOrder.STATUS_7 + ","
			        + AirticketOrder.STATUS_8);

		}
		catch (Exception e)
		{
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
	    throws AppException
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.unlockSelfOrder(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_2 + "," + AirticketOrder.STATUS_7 + ","
			        + AirticketOrder.STATUS_8);
		}
		catch (Exception e)
		{
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
	    throws AppException
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.unlockAllOrder(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_2 + "," + AirticketOrder.STATUS_7 + ","
			        + AirticketOrder.STATUS_8);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("解锁他人订单异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 确认支付
	 **************************************************************************/
	public ActionForward confirmPayment(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";

		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.confirmPayment(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_2 + "," + AirticketOrder.STATUS_7 + ","
			        + AirticketOrder.STATUS_8);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("确认支付异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 出票
	 **************************************************************************/
	public ActionForward ticket(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz
			    .confirmTicket(airticketOrderFrom, request);
			return redirectManagePage(mapping, request, true, forwardPage,
			    AirticketOrder.STATUS_3 + "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("确认出票异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 卖出取消出票
	 **************************************************************************/
	public ActionForward quitSaleTicket(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";

		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.quitSaleTicket(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage, "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("卖出订单取消出票异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 买入取消出票
	 **************************************************************************/
	public ActionForward quitBuyTicket(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";

		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz
			    .quitBuyTicket(airticketOrderFrom, request);
			return redirectManagePage(mapping, request, true, forwardPage, "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("买入订单取消出票异常");
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
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.agreeCancelRefund(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage, "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("取消出票确认退款异常");
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
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.updateAirticketOrderStatus(
			    airticketOrderFrom, request);
			return redirectManagePage(mapping, request, false, forwardPage, "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("更新订单异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 修改 订单后返
	 **************************************************************************/
	public ActionForward updateOrderProfitAfter(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		AirticketOrder airticketOrder = (AirticketOrder) form;
		Inform inf = new Inform();
		try
		{
			if (airticketOrder.getId() > 0)
			{
				airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrder
				    .getId());
				AirlinePolicyAfter airlinePolicyAfter = airlinePolicyAfterBiz
				    .getAppropriatePolicy(airticketOrder.getCyr());
				if (airlinePolicyAfter != null)
				{
					BigDecimal saleTotalAmount = airticketOrderBiz.getSaleTotalAmount(
					    airticketOrder.getCyr(), airticketOrder.getEntryTime().getYear(),
					    airticketOrder.getEntryTime().getMonth());
					SaleResult sr = airlinePolicyAfterBiz.getSaleResultByOrder(
					    airlinePolicyAfter, airticketOrder, saleTotalAmount);
					airticketOrder.setProfitAfter(sr.getAfterAmounts());
					airticketOrderBiz.update(airticketOrder);
				}
				else
				{
					inf.setMessage("没有找到合适的政策!");
				}
			}
			else
			{
				inf.setMessage("订单ID为空!");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		inf.setClose(true);
		inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=view&id="
		    + airticketOrder.getId());
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return mapping.findForward(forwardPage);
	}

	/***************************************************************************
	 * 编辑备注 sc
	 **************************************************************************/
	public ActionForward editRemark(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.editRemark(airticketOrderFrom, request);
			return redirectManagePage(mapping, request, false, forwardPage, "");
		}
		catch (Exception e)
		{
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
	public ActionForward addRetireOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.addRetireOrder(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true,
			    AirticketOrder.ORDER_GROUP_TYPE3 + "", "19,29,20,30,24,25,34,35");
		}
		catch (Exception e)
		{
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
	public ActionForward auditRetire(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
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
				forwardPage = airticketOrderBiz.auditRetire(airticketOrderFrom, uri);
				return redirectManagePage(mapping, request, true,
				    AirticketOrder.ORDER_GROUP_TYPE3 + "", "19,29,20,30,24,25,34,35");
			}
			else
			{
				inf.setMessage("表单不能为空");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}
		catch (Exception e)
		{
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
	public ActionForward auditRetire2(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
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
				airticketOrderBiz.auditRetire2(airticketOrderFrom, request);
				return redirectManagePage(mapping, request, true,
				    AirticketOrder.ORDER_GROUP_TYPE3 + "", "19,29,20,30,24,25,34,35");
			}
			else
			{
				inf.setMessage("表单不能为空");
				inf.setBack(true);
				forwardPage = "inform";
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("第二次通过申请");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 确认退票/废票 收、退款 sc
	 **************************************************************************/
	public ActionForward collectionRetire(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.collectionRetire(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage, "");
		}
		catch (Exception e)
		{
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
	public ActionForward addUmbuchenOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
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
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_39); // 订单状态
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
				if (airticketOrder.getAccount() != null)
				{
					airticketOrderFrom
					    .setPlatformId(airticketOrder.getPlatform().getId());
					airticketOrderFrom.setCompanyId(airticketOrder.getCompany().getId());
					airticketOrderFrom.setAccountId(airticketOrder.getAccount().getId());
				}
				airticketOrderBiz.createUmbuchenOrder(airticketOrderFrom,
				    airticketOrder, uri);

				return redirectManagePage(mapping, request, true, "92", "39,46");
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
	 * 审核改签 订单 sc
	 **************************************************************************/
	public ActionForward auditUmbuchenOrder(ActionMapping mapping,
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

				AirticketOrder ao = airticketOrderBiz.getDrawedAirticketOrderByGroupId(
				    airticketOrder.getOrderGroup().getId(), AirticketOrder.TRANTYPE__2);

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
				airticketOrderBiz.createUmbuchenOrder(airticketOrderFrom, ao, uri);

				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				return redirectManagePage(mapping, request, true, "92", "39,46");
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

	/***************************************************************************
	 * 收付 改签 订单 sc
	 **************************************************************************/
	public ActionForward receiptUmbuchenOrder(ActionMapping mapping,
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

				if (airticketOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1)
				{
					List listao = airticketOrderBiz.listBySubGroupAndGroupId(
					    airticketOrder.getOrderGroup().getId(), airticketOrder
					        .getSubGroupMarkNo());
					if (listao != null && listao.size() > 0)
					{
						AirticketOrder tempOrder = (AirticketOrder) listao.get(0);
						if (tempOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__2
						    && tempOrder.getTranType() == AirticketOrder.TRANTYPE_5)
						{
							tempOrder.setStatus(AirticketOrder.STATUS_42);
							airticketOrderBiz.update(tempOrder);
						}
					}

				}
				if (airticketOrderFrom.getTotalAmount() != null)
				{
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

				return redirectManagePage(mapping, request, true, "92", "39,46");
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

	/**
	 * 确认收款，改签完成（原updateOrderStatus）
	 * */
	public ActionForward finishUmbuchenOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.finishUmbuchenOrder(airticketOrderFrom,
			    request);
			return redirectManagePage(mapping, request, true, forwardPage, "43");
		}
		catch (Exception e)
		{
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
	public ActionForward addOrderByHand(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.addOrderByHand(request);
			return redirectManagePage(mapping, request, true, forwardPage, "");
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
	 * 修改订单 sc
	 **************************************************************************/
	public ActionForward updateOrder(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try
		{
			forwardPage = airticketOrderBiz.editOrder(airticketOrderFrom, request);
			return redirectProcessingPage(mapping, request, forwardPage);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			inf.setMessage("编辑异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	// ===================================团队=====================================
	// 更新团队正常订单信息
	public ActionForward updateTeamAirticketOrder(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder orderform = (AirticketOrder) form;
		long id = orderform.getId();
		try
		{
			if (id > 0)
			{
				airticketOrderBiz.updateTeamAirticketOrder(orderform, request);//	
				airticketOrderBiz.editTeamOrder(id, request);
			}
			else
			{
				id = airticketOrderBiz.updateTeamAirticketOrder(orderform, request);//	
				airticketOrderBiz.editTeamOrder(id, request);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return new ActionRedirect(
		    "/airticket/listAirTicketOrder.do?thisAction=editTeamOrder&id=" + id);
	}

	// 团队--编辑利润统计
	public ActionForward editTeamProfit(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
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
		AirticketOrder orderForm = (AirticketOrder) form;

		String forwardPage = "";
		Inform inf = new Inform();
		long id = orderForm.getId();
		try
		{
			if (id > 0)
			{
				forwardPage = airticketOrderBiz.confirmTeamPayment(orderForm, request);
				if ("ACCOUNTERROR".equals(com.fdays.tsms.base.Constant
				    .toUpperCase(forwardPage)))
				{
					inf.setMessage("账号设置异常");
					inf.setBack(true);
				}
				else
				{
					return new ActionRedirect(AirticketOrder.TeamManagePath);
				}
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
				airticketOrderBiz.confirmTeamRefundPayment(airticketOrderForm, request);
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

	// 团队退票,确认收退款
	/*public ActionForward confirmTeamRefundCollection(ActionMapping mapping,
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
				airticketOrderBiz.confirmTeamRefundCollection(airticketOrderForm,
				    request);
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

	}*/

	// 团队退票,再次确认收退款
	public ActionForward reconfirmTeamRefundCollection(ActionMapping mapping,
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
				airticketOrderBiz.reconfirmTeamRefund(airticketOrderForm, request);
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

	// ===================================================end 团队
	// end==========================================

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
						request.getSession().setAttribute("tempPNR", tempPNR);
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
			TempPNR tempPNR = new TempPNR();
			String pnr = airticketOrderFrom.getPnr();
			if (pnr != null && !"".equals(pnr.trim()))
			{
				String importType = airticketOrderFrom.getImportType();
				if (importType != null && "".equals(importType) == false)
				{
					if (importType.trim().equals("oldSystem"))
					{
						tempPNR = tempPNRBiz.getTempPNRByOldSystem(pnr);
					}
					else
					{
						tempPNR = tempPNRBiz.getTempPNRByPnr(pnr);
					}
				}
				else
				{
					tempPNR = tempPNRBiz.getTempPNRByPnr(pnr);
				}

				if (tempPNR != null)
				{
					if (tempPNR.getRt_parse_ret_value() != null
					    && tempPNR.getRt_parse_ret_value() != 0L)
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
							flight.setFlightCode(tempFlight.getFlightNo());// 航班号
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
						request.getSession().setAttribute("tempPNR", tempPNR);
						request.setAttribute("airticketOrder", ao);
						forwardPage = airticketOrderFrom.getForwardPage();
					}
					else
					{
						inf.setMessage("旧系统不存在此PNR");
						inf.setBack(true);
						forwardPage = "inform";
						request.setAttribute("inf", inf);
					}
				}
				else
				{
					inf.setMessage("PNR不能为空");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		}
		catch (Exception e)
		{
			inf.setMessage("提取订单数据异常");
			inf.setBack(true);
			forwardPage = "inform";
			request.setAttribute("inf", inf);
			e.printStackTrace();
		}

		return (mapping.findForward(forwardPage));
	}

	/**
	 * 重定向到XX订单管理页面
	 * 
	 * @param forwardPage
	 *          :错误码/orderType
	 * @param isEnforce
	 *          :是否强制跳转
	 **/
	public ActionForward redirectManagePage(ActionMapping mapping,
	    HttpServletRequest request, boolean isEnforce, String forwardPage,
	    String moreStatus)
	{
		Inform inf = new Inform();
		if (forwardPage != null)
		{
			if ("NOORDER".equals(forwardPage))
			{
				inf.setMessage("订单不存在！");
			}
			else if ("OVERLOOKED".equals(forwardPage))
			{
				inf.setMessage("订单已经锁定，请勿重复操作");
			}
			else if ("NOPNR".equals(forwardPage))
			{
				inf.setMessage("当前PNR不存在");
			}
			else if ("ERROR".equals(forwardPage))
			{
				inf.setMessage("程序异常,请联系技术支持");
			}
			else if ("ACCOUNTERROR".equals(forwardPage))
			{
				inf.setMessage("账号设置异常,请联系管理员");
			}
			else if ("STATEMENTACCOUNTERROR".equals(forwardPage))
			{
				inf.setMessage("STATEMENT账号设置异常,请联系管理员");
			}
			else if (Constant.toLong(forwardPage.trim()) > 0)
			{
				ActionRedirect redirect = new ActionRedirect(
				    AirticketOrder.GeneralManagePath);

				if (isEnforce)
				{
					redirect.addParameter("orderType", forwardPage);
					redirect.addParameter("moreStatus", moreStatus);
				}
				else
				{
					if (request.getSession().getAttribute("orderType") != null)
					{
						redirect.addParameter("orderType", request.getSession()
						    .getAttribute("orderType"));
					}
					else
					{
						redirect.addParameter("orderType", forwardPage);
					}
					if (request.getSession().getAttribute("moreStatus") != null)
					{
						redirect.addParameter("moreStatus", request.getSession()
						    .getAttribute("moreStatus"));
					}
				}
				return redirect;
			}
			else
			{
				inf.setMessage("页面跳转异常");
			}
		}
		else
		{
			inf.setMessage("页面跳转异常");
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
				inf.setMessage("操作异常");
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
			inf.setMessage("跳转到关联订单页面异常");
		}
		inf.setBack(true);
		forwardPage = "inform";
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 修改订单 sc
	 **************************************************************************/
	public ActionForward updateOrderMemo(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "inform";
		Inform inf = new Inform();
		inf.setClose(true);
		try
		{

			AirticketOrder ao = airticketOrderBiz
			    .getAirticketOrderById(airticketOrderFrom.getId());
			ao.setMemo(airticketOrderFrom.getMemo());
			airticketOrderBiz.update(ao);
			if (ao.getTicketType() == AirticketOrder.TICKETTYPE_2)
				inf
				    .setForwardPage("/airticket/listAirTicketOrder.do?thisAction=viewTeam&id="
				        + airticketOrderFrom.getId());
			else
				inf
				    .setForwardPage("/airticket/listAirTicketOrder.do?thisAction=view&id="
				        + airticketOrderFrom.getId());

			request.setAttribute("inf", inf);
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
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

	public void setAirlinePolicyAfterBiz(
	    AirlinePolicyAfterBiz airlinePolicyAfterBiz)
	{
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}
}
