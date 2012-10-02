package com.fdays.tsms.policy.action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

/**
 * 后返政策ListAction
 * @author Administrator
 *
 */
public class AirlinePolicyAfterListAction extends BaseAction {
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;
	private AirticketOrderBiz airticketOrderBiz;


	//增加或修改页面
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePolicyAfterListForm apalf = (AirlinePolicyAfterListForm) form;
		long id = 0;
		if (apalf.getSelectedItems().length > 0) {
			id = apalf.getSelectedItems()[0];
		} else
			id = apalf.getId();
		if (id > 0) {
			AirlinePolicyAfter airlinePolicyAfter = (AirlinePolicyAfter) airlinePolicyAfterBiz.getAirlinePolicyAfterById(id);
			if (airlinePolicyAfter == null) {
				System.out.println("AirlinePolicyAfter==null");
			}
			airlinePolicyAfter.setThisAction("update");
			request.setAttribute("airlinePolicyAfter", airlinePolicyAfter);
		} else
			request.setAttribute("airlinePolicyAfter", new PolicyAfter());
		forwardPage = "editAirlinePolicyAfter";
		return (mapping.findForward(forwardPage));
	}

	//增加页面
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AirlinePolicyAfter airlinePolicyAfter = new AirlinePolicyAfter();
		airlinePolicyAfter.setThisAction("insert");
		request.setAttribute("airlinePolicyAfter", airlinePolicyAfter);
		String forwardPage = "editAirlinePolicyAfter";
		return (mapping.findForward(forwardPage));
	}

	//删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AirlinePolicyAfterListForm apalf = (AirlinePolicyAfterListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for(int i=0;i<apalf.getSelectedItems().length;i++){
				id = apalf.getSelectedItems()[i];
				AirlinePolicyAfter apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(id);
				if(apa.getPolicyAfters().size()>0){
					inf.setMessage("删除失败!所选择政策下有子政策，不能被删除，请先处理子政策");
					inf.setForwardPage("/policy/airlinePolicyAfterList.do");
					inf.setParamId("thisAction");
					inf.setParamValue("list");
					request.setAttribute("inf", inf);
					forwardPage = "inform";
					return (mapping.findForward(forwardPage));
				}
			}
			for (int i = 0; i < apalf.getSelectedItems().length; i++) {
				id = apalf.getSelectedItems()[i];
				if (id > 0){
					message += airlinePolicyAfterBiz.deleteAirlinePolicyAfter(id);
				}
			}

			if (message > 0) {
				inf.setMessage("您已经成功删除后返政策记录!");
			} else {
				inf.setMessage("删除失败!");
			}
			inf.setForwardPage("/policy/airlinePolicyAfterList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	
	//获取所有记录并支持分页
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirlinePolicyAfterListForm apalf = (AirlinePolicyAfterListForm) form;
		if (apalf == null)
			apalf = new AirlinePolicyAfterListForm();
		try {
			apalf.setList(airlinePolicyAfterBiz.getAirlinePolicyAfter(apalf));
		} catch (Exception ex) {
			apalf.setList(new ArrayList<AirlinePolicyAfterListForm>());
		}
		request.setAttribute("apalf", apalf);
		forwardPage = "listAirlinePolicyAfter";
		return (mapping.findForward(forwardPage));
	}
	
	//获取所有记录
	public ActionForward listAirlinePolicyAfter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		List<AirlinePolicyAfter> airlinePolicyAfterList = airlinePolicyAfterBiz.listAirlinePolicyAfter();
		request.setAttribute("airlinePolicyAfterList", airlinePolicyAfterList);
		forwardPage = "listAirlinePolicyAfter";
		return (mapping.findForward(forwardPage));
	}
	

	//------------------------------------set get-----------------------//

	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}
}
