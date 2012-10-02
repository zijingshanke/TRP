package com.fdays.tsms.airticket.action;

import java.sql.Date;
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
import com.fdays.tsms.airticket.util.CabinUtil;
import com.fdays.tsms.airticket.util.IBEUtil;
import com.fdays.tsms.airticket.util.ParseBlackUtil;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class AirticketOrderAction extends BaseAction {
	public TempPNRBiz tempPNRBiz;
	public AirticketOrderBiz airticketOrderBiz;
	public PassengerBiz passengerBiz;
	public TicketLogBiz ticketLogBiz;

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

	/***************************************************************************
	 * 通过黑屏PNR接口获取数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByBlackPNR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			String pnrInfo = request.getParameter("pnrInfo");
			if (pnrInfo != null && !"".equals(pnrInfo.trim())) {
				TempPNR tempPNR = getTempPNRByBlackInfo(pnrInfo);

				if (tempPNR != null) {
					AirticketOrder checkAO = new AirticketOrder();
					checkAO.setTranType(AirticketOrder.TRANTYPE__1);
					checkAO.setSubPnr(tempPNR.getPnr());
					boolean checkPnr=airticketOrderBiz.checkPnrisMonth(checkAO); //验证pnr是否重复添加
					if(checkPnr==false){
						request.setAttribute("msg", "已存在相同PNR!");
					}
					if (true) {
						request.setAttribute("tempPNR", tempPNR);
						airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
						// 设置临时会话
						UserRightInfo uri = (UserRightInfo) request
								.getSession().getAttribute("URI");
						uri.setTempPNR(tempPNR);
						forwardPage = airticketOrderFrom.getForwardPage();
					} else {
						inf.setMessage("PNR已存在，请勿重复添加！");
						inf.setBack(true);
						forwardPage = "inform";
						request.setAttribute("inf", inf);
					}
				} else {
					inf.setMessage("PNR 信息错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
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
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom.getPnr() != null
					&& !"".equals(airticketOrderFrom.getPnr().trim())) {
				airticketOrderFrom.setSubPnr(airticketOrderFrom.getPnr());
				System.out.println("TranType====>"
						+ airticketOrderFrom.getTranType());
				// if(airticketOrderBiz.checkPnrisMonth(airticketOrderFrom)){

				// 根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderForRetireUmbuchen(airticketOrderFrom
								.getPnr(),
								airticketOrderFrom.getBusinessType(),
								airticketOrderFrom.getTranType());

				if (airticketOrder != null && airticketOrder.getId() != 0L) {
					request.setAttribute("airticketOrder", airticketOrder);

					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("PNR 不存在！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}
			}
		} catch (Exception e) {
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
	public ActionForward getAirticketOrderForRetireUmbuchenBySelect(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
		   String aoId=	request.getParameter("aoId");
			if (aoId!= null	&& !"".equals(aoId.trim())) {				
			
				//根据 预定pnr、类型查询导入退废、改签的订单
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(Long.valueOf(aoId));
				
				if (airticketOrder != null && airticketOrder.getId() != 0L) {
					request.setAttribute("airticketOrder", airticketOrder);
					forwardPage = airticketOrderFrom.getForwardPage();
				} else {
					inf.setMessage("id 不存在！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
				}		
			}
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
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
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage = airticketOrderBiz.airticketOrderByBlackOutPNR(
					request, airticketOrderFrom);
		} catch (Exception e) {
			inf.setMessage("请求超时！！！");
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
			throws Exception {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		uri.setTempPNR(null);
		return (mapping.findForward("addTradingOrder"));
	}

	/***************************************************************************
	 * 倒票-订单录入 sc
	 **************************************************************************/
	public ActionForward createTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage = airticketOrderBiz.createTradingOrder(request,
					airticketOrderForm);
			return redirectManagePage(mapping,request, forwardPage); 
		}catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误异常");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * B2C-订单录入 sc
	 **************************************************************************/
	public ActionForward createB2CTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage = airticketOrderBiz.createB2CTradingOrder(request,
					airticketOrderForm);
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("添加错误！");
			inf.setBack(true);
			forwardPage = "inform";
			System.out.println("PNR 错误");
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 申请支付 sc
	 **************************************************************************/
	public ActionForward applyTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage = airticketOrderBiz.createApplyTickettOrder(
					airticketOrderFrom, request);
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.anewApplyTicket(airticketOrderFrom, request);			
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			HttpServletRequest request, HttpServletResponse response)throws AppException{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.lockupOrder(airticketOrderFrom, request);
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			HttpServletRequest request, HttpServletResponse response)throws AppException{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.unlockSelfOrder(airticketOrderFrom, request);
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			HttpServletRequest request, HttpServletResponse response)throws AppException{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.unlockAllOrder(airticketOrderFrom, request);
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));		
	}
	
	

	/***************************************************************************
	 * 确认支付 sc
	 **************************************************************************/
	public ActionForward confirmTicket(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";

		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.confirmPayment(airticketOrderFrom, request);
			return redirectManagePage(mapping,request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage = airticketOrderBiz.confirmTicket(airticketOrderFrom,
					request);
			return redirectManagePage(mapping, request,forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage = airticketOrderBiz.quitTicket(airticketOrderFrom,request);
			return redirectManagePage(mapping,request,forwardPage);			
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.agreeCancelRefund(airticketOrderFrom, request);
			return redirectManagePage(mapping, request, forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
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
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";	
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.updateAirticketOrderStatus(airticketOrderFrom, request);
			return redirectManagePage(mapping, request,forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
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
			throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {			
			forwardPage = airticketOrderBiz.editRemark(airticketOrderFrom,
					request);
			return redirectManagePage(mapping,request,forwardPage);	
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 创建内部退废 订单 sc
	 **************************************************************************/
	public ActionForward addRetireTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				String groupMarkNo = airticketOrder.getGroupMarkNo();

				airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());
				// if(airticketOrderBiz.checkPnrisMonth(airticketOrderFrom)){//验证是否一个月内重复退费pnr
				if (true) {
					if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
						airticketOrderFrom.setStatus(AirticketOrder.STATUS_19); // 订单状态
						airticketOrderFrom.getTicketLog().setType(
								TicketLog.TYPE_9);// 操作日志
						// 类型
					} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
						airticketOrderFrom.setStatus(AirticketOrder.STATUS_29); // 订单状态
						airticketOrderFrom.getTicketLog().setType(
								TicketLog.TYPE_14);// 操作日志 类型
					}
					airticketOrderFrom
							.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型
					
				

					forwardPage=airticketOrderBiz.createRetireTradingOrder(
							airticketOrderFrom, airticketOrder, uri);
					return redirectManagePage(mapping, request,forwardPage);					
				} else {
					inf.setMessage("PNR已存在，请勿重复添加！");
					inf
							.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing&groupMarkNo="
									+ groupMarkNo);
					forwardPage = "inform";
				}
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 审核退废 并且创建（收款订单） 订单 sc
	 **************************************************************************/
	public ActionForward auditRetireTrading(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				forwardPage=airticketOrderBiz.auditRetireTrading(airticketOrderFrom, uri);

				// 重定向(关联订单)
				return redirectManagePage(mapping,request,forwardPage);
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}
	
	/***************************************************************************
	 * 审核退废 订单 sc
	 **************************************************************************/
	public ActionForward auditRetireTrading2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				String groupMarkNo = airticketOrder.getGroupMarkNo();
				airticketOrder
						.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 票号
				airticketOrder.setHandlingCharge(airticketOrderFrom
						.getHandlingCharge());// 手续费
				airticketOrder.setTotalAmount(airticketOrderFrom
						.getTotalAmount());

				Long currTicketType = null;
				if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
					airticketOrder.setStatus(AirticketOrder.STATUS_21);
					currTicketType = TicketLog.TYPE_11;// 操作日志 类型
				} else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
					airticketOrder.setStatus(AirticketOrder.STATUS_31);
					currTicketType = TicketLog.TYPE_16;// 操作日志 类型
				}
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				airticketOrderBiz.saveAirticketTicketLog(airticketOrder.getId(),groupMarkNo, uri
						.getUser(), request, currTicketType);

				// 重定向(关联订单)
				ActionRedirect redirect = new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo", groupMarkNo);
				return redirect;
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 确认退票/废票 收、退款 sc
	 **************************************************************************/
	public ActionForward collectionRetireTrading(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";		
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.collectionRetireTrading(airticketOrderFrom,request);
			return redirectManagePage(mapping,request,forwardPage);			
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	
	
	/***************************************************************************
	 * 创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_39); // 订单状态
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__1);//业务类型
				if(airticketOrder.getAccount()!=null){
					airticketOrderFrom.setPlatformId(airticketOrder.getPlatform().getId());
					airticketOrderFrom.setCompanyId(airticketOrder.getCompany().getId());
					airticketOrderFrom.setAccountId(airticketOrder.getAccount().getId());
				}
				airticketOrderBiz.createWaitAgreeUmbuchenOrder(
						airticketOrderFrom, airticketOrder, uri);

				return redirectManagePage(mapping, request, "92");
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 审核改签 订单 sc
	 **************************************************************************/
	public ActionForward auditWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				String groupMarkNo = airticketOrder.getGroupMarkNo();

				AirticketOrder ao = airticketOrderBiz
						.getDrawedAirticketOrderByGroupMarkNo(groupMarkNo,
								AirticketOrder.TRANTYPE__2);

				airticketOrderFrom.setDrawPnr(ao.getDrawPnr());
				airticketOrderFrom.setBigPnr(ao.getBigPnr());
				airticketOrderFrom.setStatus(AirticketOrder.STATUS_41); // 订单状态
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_23);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型

				String platformId = request.getParameter("platformId5");
				String companyId = request.getParameter("companyId5");
				String accountId = request.getParameter("accountId5");
				if (platformId != null) {
					airticketOrderFrom
							.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null) {
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null) {
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}
				airticketOrderBiz.createWaitAgreeUmbuchenOrder(
						airticketOrderFrom, ao, uri);

				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				return redirectManagePage(mapping, request, "92");
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}
	
	
	/***************************************************************************
	 * 收付 改签 订单 sc
	 **************************************************************************/
	public ActionForward receiptWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());

				if (airticketOrder.getBusinessType() == AirticketOrder.BUSINESSTYPE__1) {
					List listao = airticketOrderBiz
							.listByGroupMarkNoAndBusinessTranType(
									airticketOrder.getGroupMarkNo(),
									AirticketOrder.TRANTYPE_5 + "",
									AirticketOrder.BUSINESSTYPE__2 + "");
					if (listao != null && listao.size() > 0) {
						AirticketOrder ao = (AirticketOrder) listao.get(0);
						ao.setStatus(AirticketOrder.STATUS_42);
						airticketOrderBiz.update(ao);
					}

				}
				if (airticketOrderFrom.getTotalAmount() != null) {
					airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());
				}
				airticketOrder.setStatus(AirticketOrder.STATUS_43);
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				// 操作日志
				TicketLog ticketLog = new TicketLog();
				ticketLog.setOrderNo(airticketOrder.getGroupMarkNo());
				ticketLog.setIp(request.getRemoteAddr());// IP
				ticketLog.setOrderType(Statement.ORDERTYPE_1);// 订单类型
				ticketLog.setSysUser(airticketOrderFrom.getTicketLog()
						.getSysUser());// 操作员
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));// 操作时间
				ticketLog.setType(TicketLog.TYPE_24);// 操作日志 类型
				ticketLog.setStatus(1L);
				ticketLog.setSysUser(uri.getUser());// 日志操作员
				ticketLogBiz.saveTicketLog(ticketLog);

				return redirectManagePage(mapping, request, "92");
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/**
	 * 确认收款，改签完成（原updateOrderStatus）
	 * */
	public ActionForward finishUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)throws AppException{
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";		
		Inform inf = new Inform();
		try {		
			forwardPage=airticketOrderBiz.finishUmbuchenOrder(airticketOrderFrom,request);
			return redirectManagePage(mapping,request,forwardPage);		
		} catch (Exception e) {
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
	public ActionForward handworkAddTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";		
		Inform inf = new Inform();
		try {		
			forwardPage=airticketOrderBiz.handworkAddTradingOrder(airticketOrderFrom,request);

			return redirectManagePage(mapping,request,forwardPage);		
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 跳转 编辑订单页 sc
	 **************************************************************************/
	public ActionForward forwardEditTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {
				AirticketOrder airticketOrder = airticketOrderBiz.getAirticketOrderById(airticketOrderFrom.getId());
				request.setAttribute("airticketOrder", airticketOrder);
				request.setAttribute("currentTime",DateUtil.getDateString(new Date(System.currentTimeMillis()),"yyyy-MM-dd HH:mm:ss"));
				forwardPage = "editTradingOrder";
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}

	/***************************************************************************
	 * 修改订单 sc
	 **************************************************************************/
	public ActionForward editTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.editTradingOrder(airticketOrderFrom,request);			

			return redirectProcessingPage(mapping, request, forwardPage);	
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作错误！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}	
	
	/***************************************************************************
	 * 通过外部（prn信息或黑屏信息）创建退费 订单 sc
	 **************************************************************************/
	public ActionForward addOutRetireTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {

			TempPNR tempPNR = uri.getTempPNR();
			if (tempPNR != null) {
				airticketOrderFrom.setDrawPnr(tempPNR.getPnr());// 出票pnr
				airticketOrderFrom.setSubPnr(tempPNR.getPnr());// 预订pnr
				airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());// 大pnr
				airticketOrderFrom.setTicketPrice(tempPNR.getFare());// 票面价格
				airticketOrderFrom.setAirportPrice(tempPNR.getTax());// 机建费
				airticketOrderFrom.setFuelPrice(tempPNR.getYq());// 燃油税

				airticketOrderFrom.setTicketType(AirticketOrder.TICKETTYPE_1);
				if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票

					airticketOrderFrom.setStatus(AirticketOrder.STATUS_24); // 订单状态
					airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_9);// 操作日志
					// 类型
				} else if (airticketOrderFrom.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
					airticketOrderFrom.setStatus(AirticketOrder.STATUS_34); // 订单状态
					airticketOrderFrom.getTicketLog()
							.setType(TicketLog.TYPE_14);// 操作日志 类型
				}
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom
						.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型

				String platformId = request.getParameter("platformId");
				String companyId = request.getParameter("companyId");
				String accountId = request.getParameter("accountId");

				if (platformId != null) {
					airticketOrderFrom
							.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null) {
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null) {
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}

				forwardPage=airticketOrderBiz.createOutRetireTradingOrder(
						airticketOrderFrom, tempPNR, new AirticketOrder(), uri);
				uri.setTempPNR(null);

				return redirectManagePage(mapping,request,forwardPage);
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));

	}
	/***************************************************************************
	 * 审核退废 并且创建（ 收款订单） 订单 sc 外部
	 **************************************************************************/
	public ActionForward auditOutRetireTrading(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {
			forwardPage=airticketOrderBiz.auditOutRetireTrading(airticketOrderFrom,request);

			return redirectManagePage(mapping,request,forwardPage);
		} catch (Exception e) {
			e.printStackTrace();
			inf.setMessage("操作失败！");
			inf.setBack(true);
			forwardPage = "inform";
		}
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 审核退废 订单 sc 外部
	 **************************************************************************/
	public ActionForward auditOutRetireTrading2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());
				airticketOrder
						.setAirOrderNo(airticketOrderFrom.getAirOrderNo());// 票号
				airticketOrder.setHandlingCharge(airticketOrderFrom
						.getHandlingCharge());// 手续费
				airticketOrder.setTotalAmount(airticketOrderFrom.getTotalAmount());// 设置交易金额totalAmount
				Long currTicketType = null;
				if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_3) {// 3：退票
					airticketOrder.setStatus(AirticketOrder.STATUS_21);
					currTicketType = TicketLog.TYPE_11;// 操作日志 类型
				} else if (airticketOrder.getTranType() == AirticketOrder.TRANTYPE_4) {// 4：废票
					airticketOrder.setStatus(AirticketOrder.STATUS_31);
					currTicketType = TicketLog.TYPE_16;// 操作日志 类型
				}
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				airticketOrderBiz.saveAirticketTicketLog(airticketOrder.getId(),airticketOrder
						.getGroupMarkNo(), uri.getUser(), request,
						currTicketType);

				return redirectManagePage(mapping, request, "93");
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	
	/***************************************************************************
	 * 通过外部pnr创建改签 订单 sc
	 **************************************************************************/
	public ActionForward addOutWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			TempPNR tempPNR = uri.getTempPNR();
			if (tempPNR != null) {
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
				airticketOrderFrom
						.setBusinessType(AirticketOrder.BUSINESSTYPE__1);// 业务类型

				String platformId = request.getParameter("platformId");
				String companyId = request.getParameter("companyId");
				String accountId = request.getParameter("accountId");
				if (platformId != null) {
					airticketOrderFrom
							.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null) {
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null) {
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}

				airticketOrderBiz.createOutWaitAgreeUmbuchenOrder(
						airticketOrderFrom, tempPNR, new AirticketOrder(), uri);

				return redirectManagePage(mapping, request, "92");
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}


	/***************************************************************************
	 * 审核改签 订单(外部) sc
	 **************************************************************************/
	public ActionForward auditOutWaitAgreeUmbuchenOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		String forwardPage = "";
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom != null && airticketOrderFrom.getId() > 0) {

				AirticketOrder airticketOrder = airticketOrderBiz
						.getAirticketOrderById(airticketOrderFrom.getId());

				if (airticketOrder != null) {
					airticketOrderFrom.setDrawPnr(airticketOrder.getDrawPnr());// 出票pnr
					airticketOrderFrom.setSubPnr(airticketOrder.getSubPnr());// 预订pnr
					airticketOrderFrom.setBigPnr(airticketOrder.getBigPnr());// 大pnr
					airticketOrderFrom.setTicketPrice(airticketOrder
							.getTicketPrice());// 票面价格
					airticketOrderFrom.setAirportPrice(airticketOrder
							.getAirportPrice());// 机建费
					airticketOrderFrom.setFuelPrice(airticketOrder
							.getFuelPrice());// 燃油税
				}

				airticketOrderFrom.setStatus(AirticketOrder.STATUS_41); // 订单状态
				airticketOrderFrom.getTicketLog().setType(TicketLog.TYPE_23);// 操作日志
				// 类型
				airticketOrderFrom.setTranType(AirticketOrder.TRANTYPE_5);
				airticketOrderFrom.getTicketLog().setSysUser(uri.getUser());// 日志操作员
				airticketOrderFrom
						.setBusinessType(AirticketOrder.BUSINESSTYPE__2);// 业务类型
				String platformId = request.getParameter("platformId14");
				String companyId = request.getParameter("companyId14");
				String accountId = request.getParameter("accountId14");

				// 设置平台号
				if (platformId != null) {
					airticketOrderFrom
							.setPlatformId(Long.parseLong(platformId));
				}
				if (companyId != null) {
					airticketOrderFrom.setCompanyId(Long.parseLong(companyId));
				}
				if (accountId != null) {
					airticketOrderFrom.setAccountId(Long.parseLong(accountId));
				}
				airticketOrderBiz.createOutWaitAgreeUmbuchenOrder(
						airticketOrderFrom, new TempPNR(), airticketOrder, uri);

				airticketOrder.setStatus(AirticketOrder.STATUS_40);
				airticketOrder.setOptTime(new Timestamp(System
						.currentTimeMillis()));// 操作时间
				airticketOrderBiz.update(airticketOrder);

				return redirectManagePage(mapping, request, "92");
			} else {
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
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}

	
	// 团队订单录入(销售订单)
	public ActionForward saveAirticketOrderTemp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom.getAgentNo() > 0)// 客户ID
			{
				airticketOrderBiz.saveAirticketOrderTemp(airticketOrderFrom,
						uri, request, response);
				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
								+ airticketOrderFrom.getAirticketOrderId());
			} else {
				inf.setMessage("购票客户不能为空！");
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

	// 团队订单录入（退票订单）
	public ActionForward saveRufundAirticketOrderTemp(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderFrom.getAgentNo() > 0)// 客户ID
			{
				airticketOrderBiz.saveAirticketOrderTemp(airticketOrderFrom,
						uri, request, response);

				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
								+ airticketOrderFrom.getAirticketOrderId());
			} else {
				inf.setMessage("购票客户不能为空！");
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

	// 跳转原来机票添加页面
	public ActionForward saveAirticketOrderPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		try {
			AirticketOrder airticketOrder = new AirticketOrder();
			request.setAttribute("airticketOrder", airticketOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "addTeamTradingOrder";
		return mapping.findForward(forwardPage);
	}

	// 修改机票订单信息
	public ActionForward updateAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");

		Inform inf = new Inform();
		try {
			if (airticketOrderForm.getId() > 0) {
				long agentNo = airticketOrderForm.getAgentNo();
				airticketOrderBiz.updateTeamAirticketOrder(airticketOrderForm,
						uri, agentNo, request, response);//执行修改方法
				airticketOrderBiz.updateTeamAirticketOrderPage(uri, airticketOrderForm, 
						Long.toString(airticketOrderForm.getId()), request, response);//显示更新数据
				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
								+ airticketOrderForm.getId());

			} else {
				inf.setMessage("您改机票订单数据失败！");
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

	// 添加或修改团队利润统计(编辑里的添加统计)
	public ActionForward insertTeamTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderForm = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderForm.getAirticketOrderId() > 0) {
				airticketOrderBiz.updateAirticketOrderAgentAvia(
						airticketOrderForm, airticketOrderForm
								.getAirticketOrderId(), uri, request, response);

				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=updaTempAirticketOrderPage&airticketOrderId="
								+ airticketOrderForm.getAirticketOrderId());
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

	// 新团队订单录入利润统计(统计利润)
	public ActionForward saveTeamTradingOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String forwardPage = "";
		AirticketOrder airticketOrderForm = (AirticketOrder) form;

		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		Inform inf = new Inform();
		try {
			if (airticketOrderForm.getAirticketOrderId() > 0) {
				airticketOrderBiz.updateAirticketOrderAgentAvia(
						airticketOrderForm, airticketOrderForm
								.getAirticketOrderId(), uri, request, response);

				return new ActionRedirect(
						"/airticket/listAirTicketOrder.do?thisAction=listTeamForpayAirticketOrder");
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

	// 团队专用

	// 确认支付
	public ActionForward teamAirticketOrderupdateAccount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		Inform inf = new Inform();
		long airticketOrderId = airticketOrderForm.getAirticketOrderId();
		try {
			if (airticketOrderId > 0) {
				airticketOrderBiz.editTeamAirticketOrderOK(airticketOrderForm,
						airticketOrderId, uri, request, response);
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamWaitOutAirticketOrder");
			} else {
				inf.setMessage("确认支付失败！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}

	// 团队退票，买入 确认付款
	public ActionForward teamRefundAirticketOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		Inform inf = new Inform();
		long airticketOrderId = airticketOrderForm.getAirticketOrderFoId();
		try {
			if (airticketOrderId > 0) {
				airticketOrderBiz.editTeamReFundAirticketOrder(airticketOrderForm,
						airticketOrderId, uri, request, response);
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamOverRefundAirticketOrder");
			} else {
				inf.setMessage("确认退款失败！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}
	
	// 团队退票，卖出 确认收款
	public ActionForward teamRefundAirticketOrderTo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrder airticketOrderForm = (AirticketOrder) form;
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		Inform inf = new Inform();
		long airticketOrderId = airticketOrderForm.getAirticketOrderToId();
		try {
			if (airticketOrderId > 0) {
				airticketOrderBiz.editTeamReFundAirticketOrderTo(airticketOrderForm,
						airticketOrderId, uri, request, response);
				
				return new ActionRedirect("/airticket/listAirTicketOrder.do?thisAction=listTeamOverRefundAirticketOrder");
			} else {
				inf.setMessage("确认退款失败！");
				inf.setBack(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));

	}
	
	/***************************************************************************
	 * 通过PNR接口获取数据 sc
	 **************************************************************************/
	public ActionForward airticketOrderByPNR(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom.getPnr() != null
					&& !"".equals(airticketOrderFrom.getPnr().trim())) {

				AirticketOrder checkAO = new AirticketOrder();
				checkAO.setTranType(AirticketOrder.TRANTYPE__1);
				checkAO.setSubPnr(airticketOrderFrom.getPnr());
				boolean checkPnr = airticketOrderBiz.checkPnrisToday(checkAO);
				// 验证pnr是否重复添加
				if (true) {
					System.out.println("airticketOrderFrom.getPnr() ---"
							+ airticketOrderFrom.getPnr());
					TempPNR tempPNR = tempPNRBiz
							.getTempPNRByPnr(airticketOrderFrom.getPnr());
					if (tempPNR != null
							&& tempPNR.getRt_parse_ret_value() != 0L) {
						request.setAttribute("tempPNR", tempPNR);
						airticketOrderFrom.setBigPnr(tempPNR.getB_pnr());
						// 设置临时会话
						UserRightInfo uri = (UserRightInfo) request
								.getSession().getAttribute("URI");
						uri.setTempPNR(tempPNR);
						forwardPage = airticketOrderFrom.getForwardPage();
					} else {
						inf.setMessage("PNR 错误！");
						inf.setBack(true);
						forwardPage = "inform";
						request.setAttribute("inf", inf);
						System.out.println("PNR 错误");
					}
				} else {
					inf.setMessage("PNR已存在，请勿重复添加！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);

				}
			}
		} catch (Exception e) {
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
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AirticketOrder airticketOrderFrom = (AirticketOrder) form;
		String forwardPage = "";
		Inform inf = new Inform();
		try {

			if (airticketOrderFrom.getPnr() != null
					&& !"".equals(airticketOrderFrom.getPnr().trim())) {
				TempPNR tempPNR = tempPNRBiz.getTempPNRByPnr(airticketOrderFrom
						.getPnr());
				if (tempPNR != null && tempPNR.getRt_parse_ret_value() != 0L) {

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
					for (int i = 0; i < tempPassengerList.size(); i++) {
						TempPassenger tempPassenger = tempPassengerList.get(i);
						Passenger passenger = new Passenger();
						passenger.setAirticketOrder(ao);
						passenger.setName(tempPassenger.getName()); // 乘机人姓名
						passenger.setCardno(tempPassenger.getNi());// 证件号
						passenger.setType(1L); // 类型
						passenger.setStatus(1L);// 状态
						if (tempTicketsList != null
								&& tempTicketsList.size() == tempPassengerList
										.size()) {
							System.out.println("tempTicketsList===="
									+ tempPNR.getTempTicketsList().get(i));
							passenger.setTicketNumber(tempTicketsList.get(i)
									.toString()); // 票号
						}
						tmpPassengerSet.add(passenger);
					}
					ao.setPassengers(tmpPassengerSet);
					// 航班
					List<TempFlight> tempFlightList = tempPNR
							.getTempFlightList();
					Set tmpFlightSet = new HashSet();
					for (TempFlight tempFlight : tempFlightList) {
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
				} else {
					inf.setMessage("PNR 错误！");
					inf.setBack(true);
					forwardPage = "inform";
					request.setAttribute("inf", inf);
					System.out.println("PNR 错误");
				}
			}
		} catch (Exception e) {
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
	 **/
	public ActionForward redirectManagePage(ActionMapping mapping,HttpServletRequest request,String forwardPage) {
		Inform inf = new Inform();
		if (forwardPage!=null) {
			if ("NOORDER".equals(forwardPage)) {
				inf.setMessage("订单不存在！");					
			}else if (Long.parseLong(forwardPage.trim())>0) {
				ActionRedirect redirect = new ActionRedirect("listAirTicketOrder.do?thisAction=listAirTicketOrderManage");
				redirect.addParameter("orderType",forwardPage);
				return redirect;
			}else if("ERROR".equals(forwardPage)){
				inf.setMessage("程序异常,请联系技术支持");
			}else{
				inf.setMessage("操作错误");
			}
		}else{
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
	public ActionForward redirectProcessingPage(ActionMapping mapping,HttpServletRequest request,String forwardPage) {
		Inform inf = new Inform();
		if (forwardPage!=null) {
			if ("NOORDER".equals(forwardPage)) {
				inf.setMessage("订单不存在！");					
			}else if (forwardPage.trim().indexOf("G")==0) {
				ActionRedirect redirect = new ActionRedirect(
				"/airticket/listAirTicketOrder.do?thisAction=tradingOrderProcessing");
				redirect.addParameter("groupMarkNo",forwardPage);
				return redirect;
			}else if("ERROR".equals(forwardPage)){
				inf.setMessage("程序异常,请联系技术支持");
			}else{
				inf.setMessage("操作错误");
			}
		}else{
			inf.setMessage("操作错误");		
		}	
		inf.setBack(true);
		forwardPage = "inform";	
		request.setAttribute("inf", inf);
		return (mapping.findForward(forwardPage));
	}	

	public void setTempPNRBiz(TempPNRBiz tempPNRBiz) {
		this.tempPNRBiz = tempPNRBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}

	public void setPassengerBiz(PassengerBiz passengerBiz) {
		this.passengerBiz = passengerBiz;
	}

	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}
}
