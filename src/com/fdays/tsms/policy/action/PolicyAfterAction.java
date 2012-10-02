package com.fdays.tsms.policy.action;

import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.biz.AirlinePolicyAfterBiz;
import com.fdays.tsms.policy.biz.PolicyAfterBiz;
import com.fdays.tsms.right.UserRightInfo;

import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PolicyAfterAction extends BaseAction {
	private PolicyAfterBiz policyAfterBiz;
	private AirlinePolicyAfterBiz airlinePolicyAfterBiz;
	
	
	//新增操作
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		
		Calendar c = Calendar.getInstance();
		Timestamp ts = new Timestamp(c.getTimeInMillis());
		
		String forwardPage = "";
		PolicyAfter policyAfter = (PolicyAfter) form;
		Inform inf = new Inform();
		try {
			PolicyAfter tempPolicyAfter = new PolicyAfter();
			UserRightInfo uri = new UserRightInfo();
			uri = (UserRightInfo) request.getSession().getAttribute("URI");//session属性里的User的userName作为操作人
			tempPolicyAfter.setFlightCode(policyAfter.getFlightCode());
			tempPolicyAfter.setFlightCodeExcept(policyAfter.getFlightCodeExcept());
			tempPolicyAfter.setStartEnd(policyAfter.getStartEnd());
			tempPolicyAfter.setFlightClass(policyAfter.getFlightClass());
			tempPolicyAfter.setFlightClassExcept(policyAfter.getFlightClassExcept());
			tempPolicyAfter.setDiscount(policyAfter.getDiscount());
			tempPolicyAfter.setRate(policyAfter.getRate());
			tempPolicyAfter.setTravelType(policyAfter.getTravelType());
			tempPolicyAfter.setTicketType(policyAfter.getTicketType());
			tempPolicyAfter.setQuota(policyAfter.getQuota());
			tempPolicyAfter.setMemo(policyAfter.getMemo());
			tempPolicyAfter.setUserName(uri.getUser().getUserName());
			tempPolicyAfter.setStatus(policyAfter.getStatus());
			tempPolicyAfter.setUpdateDate(ts);
			AirlinePolicyAfter apa = new AirlinePolicyAfter();
			apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(policyAfter.getAirlinePolicyAfterId());
			tempPolicyAfter.setAirlinePolicyAfter(apa);
			policyAfterBiz.saveOrUpdate(tempPolicyAfter);
			inf.setMessage("成功增加后返政策信息！");
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="+policyAfter.getAirlinePolicyAfterId());
		} catch (Exception ex) {
			inf.setMessage("增加后返政策信息出错！错误信息是：" + ex.getMessage());
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
		
		Calendar c = Calendar.getInstance();
		Timestamp ts = new Timestamp(c.getTimeInMillis());
		String forwardPage = "";
		PolicyAfter policyAfter = (PolicyAfter) form;
		Inform inf = new Inform();
		try {
			PolicyAfter tempPolicyAfter = new PolicyAfter();
			UserRightInfo uri = new UserRightInfo();
			uri = (UserRightInfo) request.getSession().getAttribute("URI");//session属性里的User的userName作为操作人
			System.out.println("目前操作人:"+uri.getUser().getUserName());
			tempPolicyAfter.setFlightCode(policyAfter.getFlightCode());
			tempPolicyAfter.setFlightCodeExcept(policyAfter.getFlightCodeExcept());
			tempPolicyAfter.setStartEnd(policyAfter.getStartEnd());
			tempPolicyAfter.setFlightClass(policyAfter.getFlightClass());
			tempPolicyAfter.setFlightClassExcept(policyAfter.getFlightClassExcept());
			tempPolicyAfter.setDiscount(policyAfter.getDiscount());
			tempPolicyAfter.setRate(policyAfter.getRate());
			tempPolicyAfter.setTravelType(policyAfter.getTravelType());
			tempPolicyAfter.setTicketType(policyAfter.getTicketType());
			tempPolicyAfter.setQuota(policyAfter.getQuota());
			tempPolicyAfter.setUserName(uri.getUser().getUserName());
			tempPolicyAfter.setMemo(policyAfter.getMemo());
			tempPolicyAfter.setStatus(policyAfter.getStatus());
			tempPolicyAfter.setUpdateDate(ts);
			AirlinePolicyAfter apa = new AirlinePolicyAfter();
			apa = airlinePolicyAfterBiz.getAirlinePolicyAfterById(policyAfter.getAirlinePolicyAfterId());
			tempPolicyAfter.setAirlinePolicyAfter(apa);
			tempPolicyAfter.setId(policyAfter.getId());
			inf.setMessage("成功修改后返政策信息！");
			policyAfterBiz.update(tempPolicyAfter);
			inf.setForwardPage("/policy/policyAfterList.do?thisAction=listPolicyAfter&airlinePolicyAfterId="
					+policyAfter.getAirlinePolicyAfterId());
			
		} catch (Exception ex) {
			inf.setMessage("修改后返政策信息出错！错误信息是：" + ex.getMessage());
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
		PolicyAfter policyAfter = (PolicyAfter) form;
		long id = policyAfter.getId();
		if (id > 0) {
			PolicyAfter pa = (PolicyAfter) policyAfterBiz.getPolicyAfterById(id);
			pa.setThisAction("view");
			request.setAttribute("policyAfter", pa);
		}
		forwardPage = "viewPolicyAfter";
		return (mapping.findForward(forwardPage));
	}
	
	//----------------------------set get-------------------------//

	public void setPolicyAfterBiz(PolicyAfterBiz policyAfterBiz) {
		this.policyAfterBiz = policyAfterBiz;
	}


	public void setAirlinePolicyAfterBiz(AirlinePolicyAfterBiz airlinePolicyAfterBiz) {
		this.airlinePolicyAfterBiz = airlinePolicyAfterBiz;
	}



}