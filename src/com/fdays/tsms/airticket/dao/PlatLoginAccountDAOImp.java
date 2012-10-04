package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.FlightListForm;
import com.fdays.tsms.airticket.PlatLoginAccount;
import com.fdays.tsms.airticket.PlatLoginAccountListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PlatLoginAccountDAOImp extends BaseDAOSupport  implements PlatLoginAccountDAO{

	public List list(PlatLoginAccountListForm plf) throws AppException {

		Hql hql = new Hql();
		hql.add("from PlatLoginAccount p where 1=1");
		if(plf.getPlatformName() !=null && (!(plf.getPlatformName().equals(""))))
		{
			hql.add("and p.platform.id="+plf.getPlatformName());
		}
		if(plf.getLoginName() != null && (!(plf.getLoginName().equals(""))))
		{
			hql.add("and p.loginName like '%"+plf.getLoginName().trim()+"%'");
		}
		return this.list(hql, plf);
	}
	
	// 删除
	public void delete(long id)  throws AppException{
		if (id > 0) {
			PlatLoginAccount platLoginAccount = (PlatLoginAccount) this.getHibernateTemplate().get(
					PlatLoginAccount.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(platLoginAccount);
		}

	}
	// 添加保存
	public long save(PlatLoginAccount platLoginAccount) throws AppException{
		this.getHibernateTemplate().save(platLoginAccount);
		return platLoginAccount.getId();
	}

	// 修改
	public long update(PlatLoginAccount platLoginAccount) throws AppException {
		if (platLoginAccount.getId() > 0)
			return ((PlatLoginAccount)this.getHibernateTemplate().merge(platLoginAccount)).getId();
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	
	//平台登录帐号ID 查询
	public PlatLoginAccount getPlatLoginAccountById(long platLoginAccountId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from PlatLoginAccount p where p.id="+platLoginAccountId);
		Query query = this.getQuery(hql);
		PlatLoginAccount platLoginAccount = new PlatLoginAccount();
		if(query !=null && query.list() != null && query.list().size()>0)
		{
			platLoginAccount=(PlatLoginAccount) query.list().get(0);
		}
		return platLoginAccount;
		
	}
	   
}
