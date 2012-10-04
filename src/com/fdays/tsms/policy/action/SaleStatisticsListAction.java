package com.fdays.tsms.policy.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.SaleStatisticsListForm;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.SaleStatisticsBiz;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

/**
 * 后返政策ListAction
 * 
 * @author Administrator 2010-12-24
 */
public class SaleStatisticsListAction extends BaseAction
{
	private SaleStatisticsBiz saleStatisticsBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	// 增加或修改页面
	public ActionForward edit(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		SaleStatisticsListForm sslf = (SaleStatisticsListForm) form;
		long id = 0;
		if (sslf.getSelectedItems().length > 0)
		{
			id = sslf.getSelectedItems()[0];
		}
		else
			id = sslf.getId();
		if (id > 0)
		{
			SaleStatistics saleStatistics = (SaleStatistics) saleStatisticsBiz
			    .getSaleStatisticsById(id);
			if (saleStatistics == null)
			{
				System.out.println("SaleStatistics==null");
			}
			saleStatistics.setThisAction("update");
			List<AirlinePolicyAfter> apaList = airlinePolicyAfterBiz
			    .listAirlinePolicyAfter();
			request.setAttribute("apaList", apaList);
			request.setAttribute("saleStatistics", saleStatistics);
		}
		else
			request.setAttribute("saleStatistics", new SaleStatistics());
		forwardPage = "editSaleStatistics";
		return (mapping.findForward(forwardPage));
	}

	// 增加页面
	public ActionForward add(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		SaleStatistics saleStatistics = new SaleStatistics();
		saleStatistics.setThisAction("insert");
		List<AirlinePolicyAfter> apaList = airlinePolicyAfterBiz
		    .listAirlinePolicyAfter();
		request.setAttribute("saleStatistics", saleStatistics);
		request.setAttribute("apaList", apaList);
		String forwardPage = "editSaleStatistics";
		return (mapping.findForward(forwardPage));
	}

	// 删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		SaleStatisticsListForm sslf = (SaleStatisticsListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try
		{
			for (int i = 0; i < sslf.getSelectedItems().length; i++)
			{
				id = sslf.getSelectedItems()[i];
				if (id > 0)
				{
					message += saleStatisticsBiz.deleteSaleStatistics(id);
				}
			}

			if (message > 0)
			{
				inf.setMessage("您已经成功删除订单后返记录!");
			}
			else
			{
				inf.setMessage("删除失败!");
			}
			inf.setForwardPage("/policy/saleStatisticsList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		}
		catch (Exception ex)
		{
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 获取所有记录并支持分页
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		SaleStatisticsListForm sslf = (SaleStatisticsListForm) form;
		if (sslf == null)
			sslf = new SaleStatisticsListForm();
		try
		{
			sslf.setList(saleStatisticsBiz.getSaleStatistics(sslf));
		}
		catch (Exception ex)
		{
			sslf.setList(new ArrayList<SaleStatisticsListForm>());
		}
		List<AirlinePolicyAfter> apaList = airlinePolicyAfterBiz
		    .listAirlinePolicyAfter();
		request.setAttribute("apaList", apaList);
		request.setAttribute("sslf", sslf);
		forwardPage = "listSaleStatistics";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 获取所有记录
	 */
	public ActionForward listAirlinePolicyAfter(ActionMapping mapping,
	    ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String forwardPage = "";
		List<SaleStatistics> saleStatisticsList = saleStatisticsBiz
		    .listSaleStatistics();
		request.setAttribute("saleStatisticsList", saleStatisticsList);
		forwardPage = "listSaleStatistics";
		return (mapping.findForward(forwardPage));
	}

	

	// ------------------------------------set get-----------------------//

	public void setAirlinePolicyAfterBiz(
	    AirlinePolicyAfterBiz airlinePolicyAfterBiz)
	{
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

	public void setSaleStatisticsBiz(SaleStatisticsBiz saleStatisticsBiz)
	{
		this.saleStatisticsBiz = saleStatisticsBiz;
	}
}
