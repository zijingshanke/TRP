package com.fdays.tsms.policy.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.SaleResult;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.SaleStatisticsBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

/**
 * SaleStatisticsAction
 * @author chenqx 2010-12-24
 */
public class SaleStatisticsAction extends BaseAction
{
	private SaleStatisticsBiz saleStatisticsBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;
	private AirticketOrderBiz airticketOrderBiz;
	private  static int currentRow = 0;
	private  static int totalRowCount = 1;

	// 新增操作
	public ActionForward insert(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		SaleStatistics saleStatistics = (SaleStatistics) form;
		Inform inf = new Inform();
		if (!isCheckDate(saleStatistics.getBeginDate(), saleStatistics.getEndDate()))
		{
			inf.setMessage("增加订单后返统计信息出错！错误信息是：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/saleStatisticsList.do?thisAction=list");
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		try
		{
			SaleStatistics tempSaleStatistics = new SaleStatistics();
			tempSaleStatistics.setCarrier(saleStatistics.getCarrier());
			tempSaleStatistics.setAirlinePolicyAfter(airlinePolicyAfterBiz
			    .getAirlinePolicyAfterById(saleStatistics.getAirlinePolicyAfterId()));
			tempSaleStatistics.setBeginDate(saleStatistics.getBeginDate());
			tempSaleStatistics.setEndDate(saleStatistics.getEndDate());

			tempSaleStatistics.setStatus(saleStatistics.getStatus());
			saleStatisticsBiz.save(tempSaleStatistics);
			inf.setMessage("成功增加订单后返统计信息！");
			inf.setForwardPage("/policy/saleStatisticsList.do?thisAction=list");

		}
		catch (Exception ex)
		{
			inf.setMessage("增加订单后返统计信息出错！错误信息：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		SaleStatistics saleStatistics = (SaleStatistics) form;
		Inform inf = new Inform();
		if (!isCheckDate(saleStatistics.getBeginDate(), saleStatistics.getEndDate()))
		{
			inf.setMessage("修改订单后返统计信息出错！错误信息是：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/saleStatisticsList.do?thisAction=list");
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		try
		{
			SaleStatistics tempSaleStatistics = new SaleStatistics();
			tempSaleStatistics.setCarrier(saleStatistics.getCarrier());
			tempSaleStatistics.setAirlinePolicyAfter(airlinePolicyAfterBiz
			    .getAirlinePolicyAfterById(saleStatistics.getAirlinePolicyAfterId()));
			tempSaleStatistics.setBeginDate(saleStatistics.getBeginDate());
			tempSaleStatistics.setEndDate(saleStatistics.getEndDate());
			tempSaleStatistics.setStatus(saleStatistics.getStatus());
			tempSaleStatistics.setId(saleStatistics.getId());
			saleStatisticsBiz.save(tempSaleStatistics);
			inf.setMessage("成功修改订单后返统计信息！");
			inf.setForwardPage("/policy/saleStatisticsList.do?thisAction=list");
		}
		catch (Exception ex)
		{
			inf.setMessage("修改订单后返统计信息出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 修改高舱奖励
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward updateHighClassAward(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response)
		    throws AppException{
		SaleStatistics saleStatistics = (SaleStatistics) form;
		SaleStatistics tempSaleStatistics = saleStatisticsBiz.getSaleStatisticsById(saleStatistics.getId());
		tempSaleStatistics.setHighClassAward(saleStatistics.getHighClassAward());
		saleStatisticsBiz.save(tempSaleStatistics);
		
		Inform inf = new Inform();
		inf.setMessage("修改成功！");
		inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
		    + saleStatistics.getId());
		request.setAttribute("inf", inf);
		return (mapping.findForward("inform"));
	}
	
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		SaleStatistics saleStatistics = (SaleStatistics) form;
		long id = saleStatistics.getId();
		if (id > 0)
		{
			SaleStatistics ss = saleStatisticsBiz.getSaleStatisticsById(id);
			ss.setThisAction("view");
			request.setAttribute("saleStatistics", ss);
			request.setAttribute("policyAfterSize", ss.getAirlinePolicyAfter().getPolicyAfters().size());
			request.setAttribute("indicatorStatisticsSize",ss.getAirlinePolicyAfter().getIndicatorStatisticss().size());
		}
		forwardPage = "viewSaleStatistics";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 统计票数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward statisticsTicketNum(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		SaleStatistics ssf = (SaleStatistics) form;

		if (ssf.getId() > 0)
		{
			SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz
			    .getSaleStatisticsById(ssf.getId());
			int ticketNum = airticketOrderBiz.sumTicketNum(saleStatistics
			    .getCarrier(), saleStatistics.getBeginDate(), saleStatistics
			    .getEndDate());
			saleStatistics.setTicketNum(new Long(ticketNum));
			saleStatisticsBiz.update(saleStatistics);
			Inform inf = new Inform();
			inf.setMessage("计算成功！");
			inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
			    + ssf.getId());
			inf.setClose(true);
			request.setAttribute("inf", inf);
		}
		return (mapping.findForward("inform"));
	}

	/**
	 * 统计订单数
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward statisticsOrderNum(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		SaleStatistics ssf = (SaleStatistics) form;

		if (ssf.getId() > 0)
		{

			SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz
			    .getSaleStatisticsById(ssf.getId());
			int ticketNum = airticketOrderBiz.sumOrderNum(
			    saleStatistics.getCarrier(), saleStatistics.getBeginDate(),
			    saleStatistics.getEndDate());
			saleStatistics.setTicketNum(new Long(ticketNum));
			saleStatisticsBiz.update(saleStatistics);
			Inform inf = new Inform();
			inf.setMessage("计算成功！");
			inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
			    + ssf.getId());
			inf.setClose(true);
			request.setAttribute("inf", inf);
		}
		return (mapping.findForward("inform"));
	}

	/**
	 * 计算销售量
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward statisticsSaleAmount(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		SaleStatistics ssf = (SaleStatistics) form;

		if (ssf.getId() > 0)
		{
			SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz
			    .getSaleStatisticsById(ssf.getId());
			BigDecimal saleAmount = airticketOrderBiz.sumSaleAmount(saleStatistics
			    .getCarrier(), saleStatistics.getBeginDate(), saleStatistics
			    .getEndDate());
			saleStatistics.setSaleAmount(saleAmount);
			saleStatisticsBiz.update(saleStatistics);
			Inform inf = new Inform();
			inf.setMessage("计算成功！");
			inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
			    + ssf.getId());
			inf.setClose(true);
			request.setAttribute("inf", inf);
		}
		return (mapping.findForward("inform"));
	}

	/**
	 * 计算后返佣金
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward statisticsProfitAfter(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		SaleStatistics ssf = (SaleStatistics) form;

		if (ssf.getId() > 0)
		{
			SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz
			    .getSaleStatisticsById(ssf.getId());
			List<BigDecimal> proList = airticketOrderBiz.sumProfitAfter(saleStatistics
				    .getCarrier(), saleStatistics.getBeginDate(), saleStatistics
				    .getEndDate());
			saleStatistics.setProfitAfter(proList.get(0));
			saleStatistics.setProfit(proList.get(1));
			
			Long highClassQuota = saleStatistics.getAirlinePolicyAfter().getHighClassQuota();
			Long highTicketNum = saleStatistics.getHighClassTicketNum();
			BigDecimal highClassAward = saleStatistics.getHighClassAward();
			BigDecimal afterAmount = BigDecimal.ZERO;
			if(highTicketNum - highClassQuota >= 0){
				afterAmount = BigDecimal.valueOf((highTicketNum - highClassQuota)*highClassAward.floatValue()
						+ proList.get(0).floatValue());
			}else{
				afterAmount = proList.get(0);
			}
			saleStatistics.setAfterAmount(afterAmount);
			saleStatisticsBiz.update(saleStatistics);
			Inform inf = new Inform();
			inf.setMessage("计算成功！");
			inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
			    + ssf.getId());
			inf.setClose(true);
			request.setAttribute("inf", inf);
		}
		return (mapping.findForward("inform"));
	}

	/**
	 * 计算每个订单的后返佣金(执行后返)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward statisticsAllProfitAfter(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		int startRow = 0;
		int rowCount = 50;
		int i = 0;
		long start = System.currentTimeMillis();
		boolean flag = true;
		SaleStatistics ssf = (SaleStatistics) form;
		Inform inf = new Inform();
		try
		{
			SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz
			    .getSaleStatisticsById(ssf.getId());
			AirlinePolicyAfter apa = saleStatistics.getAirlinePolicyAfter();
			Hibernate.initialize(apa.getPolicyAfters());					//Hibernate显式初始化policyAfter
			List<AirticketOrder> aoList = new ArrayList<AirticketOrder>();
			SaleResult sr = new SaleResult();
			BigDecimal saleAmount = BigDecimal.ZERO;
			if (ssf.getQuotaByStatistics() == 1){
				saleAmount = saleStatistics.getSaleAmount();
			}
			airticketOrderBiz.iniProfitAfterInformation(saleStatistics.getCarrier(),
					saleStatistics.getBeginDate(),saleStatistics.getEndDate());//先将数据改为零(清零)
			totalRowCount = airticketOrderBiz.getRowCountByCarrier(saleStatistics.getCarrier(), 
					saleStatistics.getBeginDate(),saleStatistics.getEndDate());
			while(flag){
				long start2 = System.currentTimeMillis();
				if(startRow == 0 || aoList.size()>0){
					System.out.println("从"+startRow+"开始");
					aoList = airticketOrderBiz.listByCarrier(saleStatistics.getCarrier(), saleStatistics.getBeginDate(),
							saleStatistics.getEndDate(),startRow,rowCount);
					for (AirticketOrder order : aoList)
					{
						sr = airlinePolicyAfterBiz.getSaleResultByOrder(apa, order, saleAmount);
						order.setProfitAfter(sr.getAfterAmounts());
						order.setRateAfter(sr.getRateAfter());
						BigDecimal bd = airticketOrderBiz.getOrderProfitById(order.getId());
						order.setProfit(bd);
						i++;
						airticketOrderBiz.update(order);
						currentRow = i;
					}
					long end2 = System.currentTimeMillis();
					System.out.println("计算此"+aoList.size()+"所用时间:" + (end2 - start2) + "毫秒");
					startRow = startRow+rowCount;
					
				}else{
					inf.setMessage("计算成功！");
					flag = false;
				}
			}
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			inf.setMessage(ex.getMessage());
			ex.printStackTrace();
		} 
		long end = System.currentTimeMillis();
		System.out.println("执行后返所用时间:" + (end - start) + "毫秒");
		inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
		    + ssf.getId());
		request.setAttribute("inf", inf);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return (mapping.findForward("inform"));
	}
	
	/**
	 * 计算指标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward computeIndicator(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		
		int startRow = 0;
		int rowCount = 500;
		boolean flag = true;
		SaleStatistics ssf = (SaleStatistics) form;
		SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz.getSaleStatisticsById(ssf.getId());
		AirlinePolicyAfter apa = saleStatistics.getAirlinePolicyAfter();
		Hibernate.initialize(apa.getIndicatorStatisticss());
		List<AirticketOrder> aoList = new ArrayList<AirticketOrder>();
		SaleResult sr = new SaleResult();
		SaleResult statisticsSaleResult = new SaleResult();
		totalRowCount = airticketOrderBiz.getRowCountByCarrier(saleStatistics.getCarrier(), 
				saleStatistics.getBeginDate(),saleStatistics.getEndDate());
		int i = 0;
		while(flag){
			if(startRow == 0 || aoList.size()>0){
				aoList = airticketOrderBiz.listByCarrier(saleStatistics.getCarrier(),
						saleStatistics.getBeginDate(),saleStatistics.getEndDate(),startRow,rowCount);
				for(AirticketOrder ao : aoList){
					sr = airlinePolicyAfterBiz.getSaleResultByOrder(apa, ao);
					statisticsSaleResult.addAmounts(sr.getAmounts());
					statisticsSaleResult.addAwardAmounts(sr.getAwardAmounts());
					statisticsSaleResult.addHighClassTicketNums(sr.getHighClassTicketNum());
					i++;
					currentRow = i;
					
				}
				startRow = startRow+rowCount;
			}else{
				flag = false;
			}
			
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		saleStatistics.setSaleAmount(statisticsSaleResult.getAmounts());					//计量额
		saleStatistics.setHighClassTicketNum(statisticsSaleResult.getHighClassTicketNum().longValue());	//高舱票数
		saleStatisticsBiz.update(saleStatistics);
		Inform inf = new Inform();
		inf.setMessage("计算成功！");
		inf.setForwardPage("/policy/saleStatistics.do?thisAction=view&id="
			    + ssf.getId());
		request.setAttribute("inf", inf);
		return (mapping.findForward("inform"));
	}
	
	/**
	 * ajax用于获取进度条数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward showProgressBar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("showProgressBar:"+currentRow+"--------"+totalRowCount);
		JSONObject jObject = new JSONObject();
		jObject.put("total",totalRowCount);
		jObject.put("current",currentRow);
		out.println(jObject);
		out.close();
		return null;
	}
	
	public ActionForward comple(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
//		System.out.println("complete--------------"); 
		currentRow = 0;
		totalRowCount = 1;
		return null;
	}

	/**
	 * 开始日期是否早于结束日期
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean isCheckDate(Timestamp begin, Timestamp end)
	{
		if (begin == null || end == null) { return true; }
		if (begin.compareTo(end) <= 0) { return true; }
		return false;
	}

	// ----------------------------set get-------------------------//

	public void setAirlinePolicyAfterBiz(
	    AirlinePolicyAfterBiz airlinePolicyAfterBiz)
	{
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

	public void setSaleStatisticsBiz(SaleStatisticsBiz saleStatisticsBiz)
	{
		this.saleStatisticsBiz = saleStatisticsBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz)
	{
		this.airticketOrderBiz = airticketOrderBiz;
	}

}