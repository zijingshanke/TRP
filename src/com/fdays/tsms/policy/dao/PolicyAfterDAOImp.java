package com.fdays.tsms.policy.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.fdays.tsms.policy.PolicyAfterListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PolicyAfterDAOImp extends BaseDAOSupport implements PolicyAfterDAO {
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public long saveOrUpdate(PolicyAfter policyAfter) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(policyAfter);
		return policyAfter.getId();
	}
	

	public long merge(PolicyAfter policyAfter) throws AppException {
		this.getHibernateTemplate().merge(policyAfter);
		return policyAfter.getId();
	}

	public void update(PolicyAfter policyAfter) throws AppException {
		if (policyAfter.getId() > 0)
			this.getHibernateTemplate().update(policyAfter);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
		
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			PolicyAfter policyAfter = (PolicyAfter) this.getHibernateTemplate().get(
					PolicyAfter.class, new Long(id));
			this.getHibernateTemplate().delete(policyAfter);

		}
	}

	public PolicyAfter getPolicyAfterById(long id) {
		PolicyAfter policyAfter;
		try {
			if (id > 0) {

				policyAfter = (PolicyAfter) this.getHibernateTemplate().get(PolicyAfter.class,
						new Long(id));
				return policyAfter;
			} else
				return new PolicyAfter();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new PolicyAfter();
	}


	public PolicyAfter queryById(long id) throws AppException {
		Hql hql = new Hql("from PolicyAfter where id=" + id);
		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (PolicyAfter) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list() throws AppException {
		String hql = "from PolicyAfter where 1=1";
		return this.list(hql);
	}

	//动态参数
	public List list(PolicyAfterListForm palf) throws AppException {
		Hql hql = new Hql();
		hql.add("from PolicyAfter p where 1=1");
		if(palf.getAirlinePolicyAfterId() != 0){
			hql.add(" and airline_id=? ");
			hql.addParamter(palf.getAirlinePolicyAfterId());
		}
		if(null != palf.getUserName() && !"".equals(palf.getUserName())){
			hql.add(" and p.userName like ? ");
			hql.addParamter("%" + palf.getUserName() + "%");
		}
		return this.list(hql, palf);
	}

	//根据AirlinePolicyAfter获取PolicyAfter
	public Set<PolicyAfter> getByAirlinePolicyAfger(AirlinePolicyAfter airlinePolicyAfter) {
		
		return airlinePolicyAfter.getPolicyAfters();
	}
	
	//HQL获取PolicyAfter集合
	public List<PolicyAfter> getPolicyAfgerList(PolicyAfterListForm palf){
		Hql hql = new Hql();
		hql.add("from PolicyAfter where airline_id="+palf.getId());
		return this.list(hql);
	}


}
