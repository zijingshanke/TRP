package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.biz.AgentBiz;
import com.fdays.tsms.transaction.biz.CompanyBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.utility.PingYin;

public class AgentAction extends BaseAction{
	private AgentBiz agentBiz;
	private CompanyBiz companyBiz;
	private SysInitBiz sysInitBiz;

	//添加
	public ActionForward saveAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Agent agent = (Agent) form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		try {
			long companyId=agent.getCompanyId();
			if(companyId>0)
			{
				Agent ag = new Agent();
				Company company = companyBiz.getCompanyById(companyId);
				ag.setName(agent.getName());
				ag.setContactWay(agent.getContactWay());
				ag.setMobilePhone(agent.getMobilePhone());
				ag.setAddress(agent.getAddress());
				ag.setType(agent.getType());
				ag.setStatus(agent.getStatus());
				ag.setMemo(agent.getMemo());
				ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				ag.setUserName(uri.getUser().getUserName());
				ag.setCompany(company);
				long num =agentBiz.save(ag);
				//--更新静态库
				PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
						sysInitBiz,5);
				MainTask.put(listener);
				//
			 if (num > 0) {
					return new ActionRedirect("/transaction/agentList.do?thisAction=list");
				}else{
					inf.setMessage("您添加客户数据失败！");
					inf.setBack(true);
				}	
			}			
		} catch (Exception e) {
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
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		try {
			if(agent.getId()>0)
			{
				long companyId = agent.getCompanyId();
				if(companyId>0)
				{					
					Agent ag = agentBiz.getAgentByid(agent.getId());					
					Company company = companyBiz.getCompanyById(companyId);
					ag.setName(agent.getName());
					ag.setContactWay(agent.getContactWay());
					ag.setMobilePhone(agent.getMobilePhone());
					ag.setAddress(agent.getAddress());
					ag.setType(agent.getType());
					ag.setStatus(agent.getStatus());
					ag.setMemo(agent.getMemo());
					ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
					ag.setUserName(uri.getUser().getUserName());
					ag.setCompany(company);
					long flag =agentBiz.update(ag);
					//--更新静态库
					PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
							sysInitBiz,5);
					MainTask.put(listener);
					//
					if (flag > 0) {
						return new ActionRedirect("/transaction/agentList.do?thisAction=list");
					}else{
						inf.setMessage("您改客户数据失败！");
						inf.setBack(true);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	////////////////////////////////////////////////
	//团队
	//////////////////////////////////////////////
	
	//添加(团队)
	public ActionForward saveTeamAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Agent agent = (Agent) form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		try {
			long companyId=agent.getCompanyId();
			if(companyId>0)
			{
				Agent ag = new Agent();
				Company company = companyBiz.getCompanyById(companyId);
				ag.setName(agent.getName());
				ag.setContactWay(agent.getContactWay());
				ag.setMobilePhone(agent.getMobilePhone());
				ag.setAddress(agent.getAddress());
				ag.setType(Agent.type_2);//团队
				ag.setStatus(agent.getStatus());
				ag.setMemo(agent.getMemo());
				ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				ag.setUserName(uri.getUser().getUserName());
				ag.setCompany(company);

				long num =agentBiz.save(ag);
				//--更新静态库
				PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
						sysInitBiz,5);
				MainTask.put(listener);
				//
			 if (num > 0) {
				 return new ActionRedirect("/transaction/agentList.do?thisAction=getTeamAgentlist");
				}else{
					inf.setMessage("您添加团队客户数据失败！");
					inf.setBack(true);
				}	
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改(团队)
	public ActionForward updateTeamPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent)form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		try {
			if(agent.getId()>0)
			{
				long companyId = agent.getCompanyId();
				if(companyId>0)
				{					
					Agent ag = agentBiz.getAgentByid(agent.getId());					
					Company company = companyBiz.getCompanyById(companyId);
					ag.setName(agent.getName());
					ag.setContactWay(agent.getContactWay());
					ag.setMobilePhone(agent.getMobilePhone());
					ag.setAddress(agent.getAddress());
					ag.setType(agent.getType());
					ag.setStatus(agent.getStatus());
					ag.setMemo(agent.getMemo());
					ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
					ag.setUserName(uri.getUser().getUserName());
					ag.setCompany(company);
					long flag =agentBiz.update(ag);
					//--更新静态库
					PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
							sysInitBiz,5);
					MainTask.put(listener);
					//
					if (flag > 0) {
						return new ActionRedirect("/transaction/agentList.do?thisAction=getTeamAgentlist");
					}else{
						inf.setMessage("您改客户数据失败！");
						inf.setBack(true);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	
	////////////////////////////////////////////////
	//B2C客户
	//////////////////////////////////////////////
	
	//添加(B2C)
	public ActionForward saveB2CAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Agent agent = (Agent) form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		try {
			long companyId=agent.getCompanyId();
			if(companyId>0)
			{
				Agent ag = new Agent();
				Company company = companyBiz.getCompanyById(companyId);
				ag.setName(agent.getName());
				ag.setContactWay(agent.getContactWay());
				ag.setMobilePhone(agent.getMobilePhone());
				ag.setAddress(agent.getAddress());
				ag.setType(Agent.type_1);//B2C
				ag.setStatus(agent.getStatus());
				ag.setMemo(agent.getMemo());
				ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
				ag.setUserName(uri.getUser().getUserName());
				ag.setCompany(company);
				long num =agentBiz.save(ag);
				//--更新静态库
				PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
						sysInitBiz,5);
				MainTask.put(listener);
				//
			 if (num > 0) {
				 return new ActionRedirect("/transaction/agentList.do?thisAction=getB2CAgentlist");
				}else{
					inf.setMessage("您添加团队客户数据失败！");
					inf.setBack(true);
				}	
			}			
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改(B2C)
	public ActionForward updateB2CAgent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = (Agent)form;
		Inform inf = new Inform();
		UserRightInfo uri = (UserRightInfo) request.getSession()
    .getAttribute("URI");
		try {
			if(agent.getId()>0)
			{
				long companyId = agent.getCompanyId();
				if(companyId>0)
				{					
					Agent ag = agentBiz.getAgentByid(agent.getId());					
					Company company = companyBiz.getCompanyById(companyId);
					ag.setName(agent.getName());
					ag.setContactWay(agent.getContactWay());
					ag.setMobilePhone(agent.getMobilePhone());
					ag.setAddress(agent.getAddress());
					ag.setType(agent.getType());
					ag.setStatus(agent.getStatus());
					ag.setMemo(agent.getMemo());
					ag.setUpdateDate(new Timestamp(System.currentTimeMillis()));
					ag.setUserName(uri.getUser().getUserName());
					ag.setCompany(company);
					long flag =agentBiz.update(ag);
					//--更新静态库
					PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
							sysInitBiz,5);
					MainTask.put(listener);
					//
					if (flag > 0) {
						return new ActionRedirect("/transaction/agentList.do?thisAction=getB2CAgentlist");
					}else{
						inf.setMessage("修改客户数据失败！");
						inf.setBack(true);
					}
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}


	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}


	public void setCompanyBiz(CompanyBiz companyBiz) {
		this.companyBiz = companyBiz;
	}

}