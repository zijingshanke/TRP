package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.AgentListForm;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.neza.exception.AppException;

public class AgentBizImp implements AgentBiz{
		
		AgentDAO agentDAO; 
	
	//分页查询
	public List list(AgentListForm agentListForm) throws AppException
	{
		return agentDAO.list(agentListForm);
	}
	// 删除
	public long delete(long id) throws AppException
	{
		try {
			agentDAO.delete(id);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
	}
	// 添加保存
	public long save(Agent agent) throws AppException
	{
		return agentDAO.save(agent);
	}
	// 修改
	public long update(Agent agent) throws AppException
	{
		return agentDAO.update(agent);
	}
	//根据id查询
	public Agent getAgentByid(long agentId) throws AppException
	{
		return agentDAO.getAgentByid(agentId);
	}
	//查询返回一个 List集合
	public List<Agent> getAgentList() throws AppException
	{
		return agentDAO.getAgentList();
	}
	
	//查询返回某一类型的AgentList集合(1:b2c散客;2:团队;3:b2b)
	public List<Agent> getAgentList(Long type) throws AppException
	{
		return agentDAO.getAgentList(type);
	}
	
	
	public AgentDAO getAgentDAO() {
		return agentDAO;
	}
	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}
	
}
