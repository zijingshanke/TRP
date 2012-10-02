package com.fdays.tsms.policy.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.PolicyAfterUtil;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.IndicatorStatisticsBiz;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class IndicatorStatisticsAction extends BaseAction {
	
	private IndicatorStatisticsBiz indicatorStatisticsBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	
	//新增操作
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		
		String forwardPage = "";
		IndicatorStatistics indicatorStatistics = (IndicatorStatistics) form;
		Inform inf = new Inform();
		if(!check(indicatorStatistics)){
			inf.setMessage("增加指标计算信息出错！错误信息：字段格式出错");
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="
					+indicatorStatistics.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		if(!isCheckDate(indicatorStatistics.getBeginDate(),indicatorStatistics.getEndDate())){
			inf.setMessage("增加指标计算信息出错！错误信息：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="
					+indicatorStatistics.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		try {
			IndicatorStatistics tempIndicatorStatistics = new IndicatorStatistics();
			AirlinePolicyAfter apa = new AirlinePolicyAfter();
			apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(indicatorStatistics.getAirlinePolicyAfterId());
			tempIndicatorStatistics.setCarrier(apa.getCarrier());
			tempIndicatorStatistics.setFlightCode(indicatorStatistics.getFlightCode().trim());
			tempIndicatorStatistics.setFlightCodeExcept(indicatorStatistics.getFlightCodeExcept().trim());
			tempIndicatorStatistics.setFlightPoint(indicatorStatistics.getFlightPoint().trim());
			tempIndicatorStatistics.setFlightPointExcept(indicatorStatistics.getFlightPointExcept().trim());
			tempIndicatorStatistics.setFlightClass(indicatorStatistics.getFlightClass().trim());
			tempIndicatorStatistics.setFlightClassExcept(indicatorStatistics.getFlightClassExcept().trim());
			tempIndicatorStatistics.setTravelType(indicatorStatistics.getTravelType());
			tempIndicatorStatistics.setTicketType(indicatorStatistics.getTicketType());
			tempIndicatorStatistics.setBeginDate(indicatorStatistics.getBeginDate());
			tempIndicatorStatistics.setEndDate(indicatorStatistics.getEndDate());
			tempIndicatorStatistics.setIsAmount(indicatorStatistics.getIsAmount());
			tempIndicatorStatistics.setIsAward(indicatorStatistics.getIsAward());
			tempIndicatorStatistics.setIsHighClass(indicatorStatistics.getIsHighClass());
			tempIndicatorStatistics.setStatus(indicatorStatistics.getStatus());
			tempIndicatorStatistics.setRemark(indicatorStatistics.getRemark());
			
			tempIndicatorStatistics.setAirlinePolicyAfter(apa);
			indicatorStatisticsBiz.saveOrUpdate(tempIndicatorStatistics);
			inf.setMessage("成功增加指标统计信息！");
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="
					+indicatorStatistics.getAirlinePolicyAfterId());
		} catch (Exception ex) {
			
			inf.setMessage("增加指标统计信息出错！错误信息：" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改操作
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		IndicatorStatistics indicatorStatistics = (IndicatorStatistics) form;
		Inform inf = new Inform();
		if(!check(indicatorStatistics)){
			inf.setMessage("修改指标计算信息出错！错误信息：字段格式出错");
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="
					+indicatorStatistics.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		if(!isCheckDate(indicatorStatistics.getBeginDate(),indicatorStatistics.getEndDate())){
			inf.setMessage("修改指标计算信息出错！错误信息：结束日期不能早于起始日期");
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="
					+indicatorStatistics.getAirlinePolicyAfterId());
			request.setAttribute("inf", inf);
			forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
		
		try {
			IndicatorStatistics tempIndicatorStatistics = new IndicatorStatistics();
			AirlinePolicyAfter apa = new AirlinePolicyAfter();
			apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(indicatorStatistics.getAirlinePolicyAfterId());
			tempIndicatorStatistics.setCarrier(apa.getCarrier());
			tempIndicatorStatistics.setFlightCode(indicatorStatistics.getFlightCode().trim());
			tempIndicatorStatistics.setFlightCodeExcept(indicatorStatistics.getFlightCodeExcept().trim());
			tempIndicatorStatistics.setFlightPoint(indicatorStatistics.getFlightPoint().trim());
			tempIndicatorStatistics.setFlightPointExcept(indicatorStatistics.getFlightPointExcept().trim());
			tempIndicatorStatistics.setFlightClass(indicatorStatistics.getFlightClass().trim());
			tempIndicatorStatistics.setFlightClassExcept(indicatorStatistics.getFlightClassExcept().trim());
			tempIndicatorStatistics.setTravelType(indicatorStatistics.getTravelType());
			tempIndicatorStatistics.setTicketType(indicatorStatistics.getTicketType());
			tempIndicatorStatistics.setBeginDate(indicatorStatistics.getBeginDate());
			tempIndicatorStatistics.setEndDate(indicatorStatistics.getEndDate());
			tempIndicatorStatistics.setIsAmount(indicatorStatistics.getIsAmount());
			tempIndicatorStatistics.setIsAward(indicatorStatistics.getIsAward());
			tempIndicatorStatistics.setIsHighClass(indicatorStatistics.getIsHighClass());
			tempIndicatorStatistics.setStatus(indicatorStatistics.getStatus());
			tempIndicatorStatistics.setRemark(indicatorStatistics.getRemark());
			tempIndicatorStatistics.setAirlinePolicyAfter(apa);
			tempIndicatorStatistics.setId(indicatorStatistics.getId());
			inf.setMessage("成功修改指标统计信息！");
			indicatorStatisticsBiz.update(tempIndicatorStatistics);
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="
					+indicatorStatistics.getAirlinePolicyAfterId());
			
		} catch (Exception ex) {
			inf.setMessage("修改指标统计信息出错！错误信息：" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		IndicatorStatistics indicatorStatistics = (IndicatorStatistics) form;
		long id = indicatorStatistics.getId();
		if (id > 0) {
			IndicatorStatistics is = (IndicatorStatistics) indicatorStatisticsBiz.getIndicatorStatisticsById(id);
			is.setThisAction("view");
			request.setAttribute("indicatorStatistics", is);
		}
		forwardPage = "viewIndicatorStatistics";
		return (mapping.findForward(forwardPage));
	}
	/**
	 * 检查各字段格式是否符合要求
	 * @param policyAfter
	 * @return
	 */
	private boolean check(IndicatorStatistics indicatorStatistics){
		String flightCode = indicatorStatistics.getFlightCode().trim();
		String flightClass = indicatorStatistics.getFlightClass().trim();
		String flightCodeExcept = indicatorStatistics.getFlightCodeExcept().trim();
		String flightClassExcept = indicatorStatistics.getFlightClassExcept().trim();
		String flightPoint = indicatorStatistics.getFlightPoint().trim();
		String flightPointExcept = indicatorStatistics.getFlightPointExcept().trim();
		if(flightCode != null && !"".equals(flightCode)){
			if(!PolicyAfterUtil.isFlightCode(flightCode)){
				return false;
			}
		}
		if(flightCodeExcept != null && !"".equals(flightCodeExcept)){
			if(!PolicyAfterUtil.isFlightCode(flightCodeExcept)){
				return false;
			}
		}
		if(flightClass != null && !"".equals(flightClass)){
			if(!PolicyAfterUtil.isFlightClass(flightClass)){
				return false;
			}
		}
		if(flightClassExcept != null && !"".equals(flightClassExcept)){
			if(!PolicyAfterUtil.isFlightClass(flightClassExcept)){
				return false;
			}
		}
		if(flightPoint != null && !"".equals(flightPoint)){
//			System.out.println(flightPoint);
			if(!PolicyAfterUtil.isFlightPoint(flightPoint)){
				return false;
			}
		}
		if(flightPointExcept != null && !"".equals(flightPointExcept)){
//			System.out.println(flightPointExcept);
			if(!PolicyAfterUtil.isFlightPoint(flightPoint)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 开始日期是否早于结束日期
	 * @param begin
	 * @param end
	 * @return
	 */
	private boolean isCheckDate(Timestamp begin,Timestamp end){
		if(begin == null || end == null){
			return true;
		}
		if(begin.compareTo(end) <= 0){
			return true;
		}
		return false;
	}
	
	//----------------------------set get-------------------------//

	

	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

	public void setIndicatorStatisticsBiz(
			IndicatorStatisticsBiz indicatorStatisticsBiz) {
		this.indicatorStatisticsBiz = indicatorStatisticsBiz;
	}

	

}