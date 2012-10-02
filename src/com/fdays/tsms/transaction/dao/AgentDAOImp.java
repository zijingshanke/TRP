package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.AgentListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AgentDAOImp extends BaseDAOSupport implements AgentDAO{
	
	//分页查询
	public List list(AgentListForm agentListForm) throws AppException
	{
		Hql hql = new Hql();		
		hql.add("from Agent a where 1=1");
		if(agentListForm.getCompanyId()>0)
		{
			hql.add(" and a.company.id="+agentListForm.getCompanyId());
		}
		if(agentListForm.getName() != null && (!(agentListForm.getName().equals(""))))
		{
			hql.add(" and a.name like '%"+agentListForm.getName().trim()+"%'");
		}
		if(agentListForm.getType()>0)
		{
			hql.add("and a.type="+agentListForm.getType());
		}
		hql.add("and a.status not in("+Agent.STATES_1+")");//过滤无效
		hql.add(" order by a.updateDate");
		return this.list(hql, agentListForm);
	}

	// 删除
	public void delete(long id) throws AppException{
		if (id > 0) {
			Agent agent = (Agent) this.getHibernateTemplate().get(
					Agent.class, new Long(id));
			this.getHibernateTemplate().delete(agent);
		}
	}
	// 添加保存
	public long save(Agent agent) throws AppException{
		this.getHibernateTemplate().save(agent);
		return agent.getId();
	}

	// 修改
	public long update(Agent agent) throws AppException {
		if (agent.getId() > 0)
		{
			this.getHibernateTemplate().update(agent);
			return agent.getId();
		}
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	
	//根据id查询
	public Agent getAgentByid(long agentId) throws AppException {
		Hql hql = new Hql();
		hql.add("from Agent a where a.id="+agentId);
		Query query = this.getQuery(hql);
		Agent agent=null;
		if(query!=null && query.list()!=null)
		{
			agent=(Agent)query.list().get(0);
		}
		return agent;
	}
	
	//查询返回一个 List集合
	public List<Agent> getAgentList() throws AppException
	{
		List<Agent> list = new ArrayList<Agent>();
		Hql hql = new Hql();
		hql.add("from Agent");
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null)
		{
			list =query.list();
		}
		return list;
	}
	
	//查询返回某一类型的AgentList集合(1:b2c散客;2:团队;3:b2b)
	public List<Agent> getAgentList(Long type) throws AppException
	{
		List<Agent> list = new ArrayList<Agent>();
		Hql hql = new Hql();
		hql.add("from Agent a where a.type="+type);
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null)
		{
			list =query.list();
		}
		return list;
	}
	
	
	//查询返回一个 List集合
	public List<Agent> getValidAgentList() throws AppException
	{
		List<Agent> list = new ArrayList<Agent>();
		Hql hql = new Hql();
		hql.add("from Agent a where 1=1 and a.status=0 ");
		hql.add(" order by a.name ");
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null)
		{
			list =query.list();
		}
		return list;
	}
	
}
