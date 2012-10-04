package com.fdays.tsms.airticket.action;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.StatisticsOrderListForm;
import com.fdays.tsms.airticket.biz.StatisticsOrderBiz;

import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class StatisticsOrderListAction extends BaseAction {
	
	private StatisticsOrderBiz statisticsOrderBiz;
	//获取所有记录
	public ActionForward listStatisticsOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatisticsOrderListForm solf = (StatisticsOrderListForm) form;
		if (solf == null){
			solf = new StatisticsOrderListForm();
		}
		try {
			solf.setList(statisticsOrderBiz.getStatisticsOrder(solf));
		} catch (Exception e) {
			e.printStackTrace();
			solf.setList(new ArrayList<StatisticsOrderListForm>());
		}
		request.setAttribute("solf", solf);
		forwardPage = "listStatisticsOrder";
		return (mapping.findForward(forwardPage));
	}
	
	//-------------------------set get------------------------//
	public void setStatisticsOrderBiz(StatisticsOrderBiz statisticsOrderBiz) {
		this.statisticsOrderBiz = statisticsOrderBiz;
	}
}
