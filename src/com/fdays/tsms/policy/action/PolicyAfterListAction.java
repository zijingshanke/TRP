package com.fdays.tsms.policy.action;


import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterListForm;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.PolicyAfterBiz;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PolicyAfterListAction extends BaseAction {
	private PolicyAfterBiz policyAfterBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;

	//修改页面
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PolicyAfterListForm palf = (PolicyAfterListForm) form;
		long id = 0;
		if (palf.getSelectedItems().length > 0) {
			id = palf.getSelectedItems()[0];
		} else
			id = palf.getId();
		if (id > 0) {
			PolicyAfter policyAfter = (PolicyAfter) policyAfterBiz.getPolicyAfterById(id);
			if (policyAfter == null) {
				System.out.println("PolicyAfter==null");
			}
			policyAfter.setThisAction("update");
			request.setAttribute("policyAfter", policyAfter);
		} else
			request.setAttribute("policyAfter", new PolicyAfter());
		request.setAttribute("airlinePolicyAfterId", palf.getAirlinePolicyAfterId());
		forwardPage = "editPolicyAfter";
		return (mapping.findForward(forwardPage));
	}

	//增加页面
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PolicyAfterListForm paf = (PolicyAfterListForm) form;
		PolicyAfter policyAfter = new PolicyAfter();
		policyAfter.setThisAction("insert");
		policyAfter.setAirlinePolicyAfter(airlinePolicyAfterBiz.getAirlinePolicyAfterById(paf.getAirlinePolicyAfterId()));
		request.setAttribute("policyAfter", policyAfter);
		String forwardPage = "editPolicyAfter";
		return (mapping.findForward(forwardPage));
	}
	
	//获取所有记录
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PolicyAfterListForm palf = (PolicyAfterListForm) form;
		if (palf == null)
			palf = new PolicyAfterListForm();
		try {
			palf.setList(policyAfterBiz.getPolicyAfter(palf));
//			if(palf.getBeginDate() == null){
//				palf.setBeginDate(new Timestamp(-8*60*60*1000));
//			}
		} catch (Exception ex) {
			palf.setList(new ArrayList<PolicyAfterListForm>());
		}
		request.setAttribute("palf", palf);
		forwardPage = "listPolicyAfter";
		return (mapping.findForward(forwardPage));
	}

	//获取PolicyAfter集合
	public ActionForward listPolicyAfter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PolicyAfterListForm palf = (PolicyAfterListForm) form;
		palf.setList(policyAfterBiz.getPolicyAfter(palf));
		palf.setAirlinePolicyAfter(airlinePolicyAfterBiz.getAirlinePolicyAfterById(palf.getAirlinePolicyAfterId()));
		if(palf.getBeginDate() == null){
			palf.setBeginDate(new Timestamp(-8*60*60*1000));
		}
		request.setAttribute("palf", palf);
		forwardPage = "listPolicyAfter";
		return (mapping.findForward(forwardPage));
	}
	
	//删除记录
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PolicyAfterListForm palf = (PolicyAfterListForm) form;
		String forwardPage = "";
		int id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < palf.getSelectedItems().length; i++) {
				id = palf.getSelectedItems()[i];
				if (id > 0)
					message += policyAfterBiz.deletePolicyAfter(id);
			}

			if (message > 0) {
				inf.setMessage("您已经成功删除后返政策!");
			} else {
				inf.setMessage("删除失败!");
			}
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="+palf.getAirlinePolicyAfterId());
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}


	//-----------------------------------ste get-----------------------//

	public void setPolicyAfterBiz(PolicyAfterBiz policyAfterBiz) {
		this.policyAfterBiz = policyAfterBiz;
	}

	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}


}
