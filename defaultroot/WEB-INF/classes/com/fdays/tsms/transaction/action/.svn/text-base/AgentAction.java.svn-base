package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.AgentBiz;
import com.fdays.tsms.transaction.biz.CompanyBiz;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AgentAction extends BaseAction{

	AgentBiz agentBiz;
	CompanyBiz companyBiz;
	private SysInitBiz sysInitBiz;
	
	public SysInitBiz getSysInitBiz() {
		return sysInitBiz;
	}

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}

	public AgentBiz getAgentBiz() {
		return agentBiz;
	}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}

	public CompanyBiz getCompanyBiz() {
		return companyBiz;
	}

	public void setCompanyBiz(CompanyBiz companyBiz) {
		this.companyBiz = companyBiz;
	}

	//添加
	public ActionForward saveAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Agent agent = (Agent) form;
		Inform inf = new Inform();
		try {
			long companyId=agent.getCompanyId();
			if(companyId>0)
			{
				Agent ag = new Agent();
				Company company = companyBiz.getCompanyByid(companyId);
				ag.setName(agent.getName());
				ag.setContactWay(agent.getContactWay());
				ag.setAddress(agent.getAddress());
				ag.setType(agent.getType());
				ag.setStatus(agent.getStatus());
				ag.setMemo(agent.getMemo());
				ag.setCompany(company);
				long num =agentBiz.save(ag);
           
			 if (num > 0) {
					inf.setMessage("您已经成功添加客户数据！");
					inf.setForwardPage("/transaction/agentList.do");
					inf.setParamId("thisAction");
					inf.setParamValue("list");
				}else{
					inf.setMessage("您添加支付账号失败！");
					inf.setBack(true);
				}	
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,5);
			MainTask.put(listener);
			//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改
	public ActionForward updateAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent)form;
		Inform inf = new Inform();
		try {
			if(agent.getId()>0)
			{
				long companyId = agent.getCompanyId();
				if(companyId>0)
				{					
					Agent ag = agentBiz.getAgentByid(agent.getId());					
					Company company = companyBiz.getCompanyByid(companyId);
					ag.setName(agent.getName());
					ag.setContactWay(agent.getContactWay());
					ag.setAddress(agent.getAddress());
					ag.setType(agent.getType());
					ag.setStatus(agent.getStatus());
					ag.setMemo(agent.getMemo());
					ag.setCompany(company);
					long flag =agentBiz.update(ag);
					
					if (flag > 0) {
						inf.setMessage("您已经成功修改客户数据！");
						inf.setForwardPage("/transaction/agentList.do");
						inf.setParamId("thisAction");
						inf.setParamValue("list");
					}else{
						inf.setMessage("您改客户数据失败！");
						inf.setBack(true);
					}
				}
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,5);
			MainTask.put(listener);
			//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

}