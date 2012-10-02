package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.AgentListForm;
import com.neza.exception.AppException;

public interface AgentBiz {
	
	//分页查询
	public List list(AgentListForm agentListForm) throws AppException;
	// 删除
	public long delete(long id) throws AppException;
	// 添加保存
	public long save(Agent agent) throws AppException;
	// 修改
	public long update(Agent agent) throws AppException;
	//根据id查询
	public Agent getAgentByid(long agentId) throws AppException;
	//查询返回一个 List集合
	public List<Agent> getAgentList() throws AppException;

}
