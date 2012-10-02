package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.AgentListForm;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.biz.AgentBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class AgentListAction extends BaseAction{

		AgentBiz agentBiz;		


	//分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;		
		request.setAttribute("companyList", PlatComAccountStore.companyList);
		if(agentListForm==null)
		{
			agentListForm=new AgentListForm();
		}
		try {
			agentListForm.setList(agentBiz.list(agentListForm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("agentListForm", agentListForm);
		return mapping.findForward("listAgent");	
	}
	
	//显示详细信息
	public ActionForward viewAgentPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
			String forwardPage ="";
			try {
				String agentId = request.getParameter("agentId");
				Agent agent = agentBiz.getAgentByid(Long.parseLong(agentId));
				request.setAttribute("agent", agent);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			forwardPage="viewAgent";
		return mapping.findForward(forwardPage);
	}
	
	//跳转添加页面
	public ActionForward savePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = new Agent();		
		request.setAttribute("companyList", PlatComAccountStore.companyList);
		agent.setThisAction("saveAgent");
		request.setAttribute("agent", agent);
		String forwardPage = "editAgent";
		return mapping.findForward(forwardPage);
	}
	
	
	
	//跳转修改页面
	public ActionForward updatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;	
		request.setAttribute("companyList", PlatComAccountStore.companyList);
		long agentId=agentListForm.getSelectedItems()[0];
		if(agentId>0)
		{
			Agent agent= agentBiz.getAgentByid(agentId);
			agent.setThisAction("updateAgent");		
			agent.setCompanyId(agent.getCompany().getId());
			request.setAttribute("agent", agent);
		}else
		{
			request.setAttribute("agent", new Agent());
		}
		return mapping.findForward("editAgent");
	}
	
	
	/////////////////////////////////////////////////
	//团队客户
	////////////////////////////////////////////////
	
	//分页查询(团队)
	public ActionForward getTeamAgentlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;		
		request.setAttribute("companyList", PlatComAccountStore.getTeamCompnayList());
		if(agentListForm==null)
		{
			agentListForm=new AgentListForm();
		}
		try {
			agentListForm.setType(Agent.type_2);//团队
			agentListForm.setList(agentBiz.list(agentListForm));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.setAttribute("agentListForm", agentListForm);
		return mapping.findForward("listTeamAgent");	
	}
	
	//跳转添加页面(团队)
	public ActionForward saveTeamPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = new Agent();		
		request.setAttribute("companyList", PlatComAccountStore.getTeamCompnayList());
		agent.setThisAction("saveTeamAgent");
		request.setAttribute("agent", agent);
		String forwardPage = "ediTeamAgent";
		return mapping.findForward(forwardPage);
	}
	
	//跳转修改页面
	public ActionForward updateTeamPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;	
		request.setAttribute("companyList", PlatComAccountStore.getTeamCompnayList());
		long agentId=agentListForm.getSelectedItems()[0];
		if(agentId>0)
		{
			Agent agent= agentBiz.getAgentByid(agentId);
			agent.setThisAction("updateTeamPage");		
			agent.setCompanyId(agent.getCompany().getId());
			request.setAttribute("agent", agent);
		}else
		{
			request.setAttribute("agent", new Agent());
		}
		return mapping.findForward("ediTeamAgent");
	}
	
	/////////////////////////////////////////////////
	//B2C客户
	////////////////////////////////////////////////
	
	//分页查询(团队)
	public ActionForward getB2CAgentlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;		
		request.setAttribute("companyList", PlatComAccountStore.getTeamCompnayList());
		if(agentListForm==null)
		{
			agentListForm=new AgentListForm();
		}
		try {
			agentListForm.setType(Agent.type_1);//B2C散客
			agentListForm.setList(agentBiz.list(agentListForm));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.setAttribute("agentListForm", agentListForm);
		return mapping.findForward("listB2CAgent");	
	}
	
	//跳转添加页面(团队)
	public ActionForward saveB2CPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Agent agent = new Agent();		
		request.setAttribute("companyList", PlatComAccountStore.getTeamCompnayList());
		agent.setThisAction("saveB2CAgent");
		request.setAttribute("agent", agent);
		String forwardPage = "ediB2CAgent";
		return mapping.findForward(forwardPage);
	}
	
	//跳转修改页面
	public ActionForward updateB2CPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;	
		request.setAttribute("companyList", PlatComAccountStore.getTeamCompnayList());
		long agentId=agentListForm.getSelectedItems()[0];
		if(agentId>0)
		{
			Agent agent= agentBiz.getAgentByid(agentId);
			agent.setThisAction("updateB2CAgent");		
			agent.setCompanyId(agent.getCompany().getId());
			request.setAttribute("agent", agent);
		}else
		{
			request.setAttribute("agent", new Agent());
		}
		return mapping.findForward("ediB2CAgent");
	}
	
	
	
	//删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		AgentListForm agentListForm = (AgentListForm)form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();		
		int message = 0;
		try {
			for (int i = 0; i < agentListForm.getSelectedItems().length; i++) {
				id = agentListForm.getSelectedItems()[i];				
				Agent agent=null;				
				if (id > 0)
					agent = agentBiz.getAgentByid(id);//查询子表中是否有数据
				
					message += agentBiz.delete(id);//根据id删除
					if (message > 0) {
					} else {
						inf.setMessage("删除失败!");
					}						
				}
			return new ActionRedirect("/transaction/agentList.do?thisAction=list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	public AgentBiz getAgentBiz() {
		return agentBiz;
	}

	public void setAgentBiz(AgentBiz agentBiz) {
		this.agentBiz = agentBiz;
	}
	

}