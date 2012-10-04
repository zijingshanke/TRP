package com.fdays.tsms.policy.action;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.IndicatorStatisticsListForm;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.IndicatorStatisticsBiz;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class IndicatorStatisticsListAction extends BaseAction {
	private IndicatorStatisticsBiz indicatorStatisticsBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	//修改页面
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		IndicatorStatisticsListForm islf = (IndicatorStatisticsListForm) form;
		long id = 0;
		if (islf.getSelectedItems().length > 0) {
			id = islf.getSelectedItems()[0];
		} else
			id = islf.getId();
		if (id > 0) {
			IndicatorStatistics indicatorStatistics = (IndicatorStatistics) indicatorStatisticsBiz.getIndicatorStatisticsById(id);
			if (indicatorStatistics == null) {
				System.out.println("IndicatorStatistics==null");
			}
			indicatorStatistics.setThisAction("update");
			request.setAttribute("indicatorStatistics", indicatorStatistics);
		} else
			request.setAttribute("indicatorStatistics", new IndicatorStatistics());
		request.setAttribute("airlinePolicyAfterId", islf.getAirlinePolicyAfterId());
		forwardPage = "editIndicatorStatistics";
		return (mapping.findForward(forwardPage));
	}

	//增加页面
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		IndicatorStatisticsListForm islf = (IndicatorStatisticsListForm) form;
		IndicatorStatistics indicatorStatistics = new IndicatorStatistics();
		indicatorStatistics.setThisAction("insert");
		indicatorStatistics.setAirlinePolicyAfter(airlinePolicyAfterBiz.getAirlinePolicyAfterById(islf.getAirlinePolicyAfterId()));
		request.setAttribute("indicatorStatistics", indicatorStatistics);
		String forwardPage = "editIndicatorStatistics";
		return (mapping.findForward(forwardPage));
	}
	
	//获取所有记录
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		IndicatorStatisticsListForm islf = (IndicatorStatisticsListForm) form;
		if (islf == null)
			islf = new IndicatorStatisticsListForm();
		try {
			islf.setList(indicatorStatisticsBiz.getIndicatorStatistics(islf));
		} catch (Exception ex) {
			islf.setList(new ArrayList<IndicatorStatisticsListForm>());
		}
		request.setAttribute("islf", islf);
		forwardPage = "listIndicatorStatistics";
		return (mapping.findForward(forwardPage));
	}

	//获取IndicatorStatistics集合
	public ActionForward listIndicatorStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		IndicatorStatisticsListForm islf = (IndicatorStatisticsListForm) form;
		islf.setList(indicatorStatisticsBiz.getIndicatorStatistics(islf));
		islf.setAirlinePolicyAfter(airlinePolicyAfterBiz.getAirlinePolicyAfterById(islf.getAirlinePolicyAfterId()));
		if(islf.getBeginDate() == null){
			islf.setBeginDate(new Timestamp(-8*60*60*1000));
		}
		request.setAttribute("islf", islf);
		forwardPage = "listIndicatorStatistics";
		return (mapping.findForward(forwardPage));
	}
	
	//删除记录
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		IndicatorStatisticsListForm islf = (IndicatorStatisticsListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < islf.getSelectedItems().length; i++) {
				id = islf.getSelectedItems()[i];
				if (id > 0)
					message += indicatorStatisticsBiz.deleteIndicatorStatistics(id);
			}

			if (message > 0) {
				inf.setMessage("您已经成功删除指标统计方案!");
			} else {
				inf.setMessage("删除失败!");
			}
			inf.setForwardPage("/policy/indicatorStatisticsList.do?thisAction=listIndicatorStatistics&airlinePolicyAfterId="+islf.getAirlinePolicyAfterId());
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}


	//-----------------------------------ste get-----------------------//

	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

	public void setIndicatorStatisticsBiz(
			IndicatorStatisticsBiz indicatorStatisticsBiz) {
		this.indicatorStatisticsBiz = indicatorStatisticsBiz;
	}


}
