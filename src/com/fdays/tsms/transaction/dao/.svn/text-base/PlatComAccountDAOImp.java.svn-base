package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountListForm;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PlatComAccountDAOImp extends BaseDAOSupport implements PlatComAccountDAO{

	
	//分页查询
	public List list(PlatComAccountListForm platComAccountForm) throws AppException
	{
		Hql hql = new Hql();		
		hql.add("from PlatComAccount p where 1=1");
		if(platComAccountForm.getCompanyName() != null && (!(platComAccountForm.getCompanyName().equals(""))))
		{
			hql.add(" and p.company.id="+platComAccountForm.getCompanyName());
		}
		if(platComAccountForm.getPlatformName() != null && (!(platComAccountForm.getPlatformName().equals(""))))
		{
			hql.add(" and p.platform.id="+platComAccountForm.getPlatformName());
		}
		if(platComAccountForm.getAccountName() != null && (!(platComAccountForm.getAccountName().equals(""))))
		{
			hql.add(" and p.account.id="+platComAccountForm.getAccountName());
		}
		hql.add(" order by p.platform.id");
		return this.list(hql, platComAccountForm);
	}

	// 删除
	public void delete(long id) throws AppException{
		if (id > 0) {
			PlatComAccount platComAccount = (PlatComAccount) this.getHibernateTemplate().get(
					PlatComAccount.class, new Long(id));
			this.getHibernateTemplate().delete(platComAccount);
		}
	}
	
	// 添加保存
	public long save(PlatComAccount platComAccount) throws AppException{
		this.getHibernateTemplate().save(platComAccount);
		return platComAccount.getId();
	}

	// 修改
	public long update(PlatComAccount platComAccount) throws AppException {
		if (platComAccount.getId() > 0)
		{
			this.getHibernateTemplate().update(platComAccount);
			return platComAccount.getId();
		}
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	
	//根据id查询
	public PlatComAccount getPlatComAccountById(long platComAccountId) throws AppException {
		Hql hql = new Hql();
		hql.add("from PlatComAccount p where p.id="+platComAccountId);
		Query query = this.getQuery(hql);
		PlatComAccount platComAccount =null;
		if(query!=null && query.list()!=null)
		{
			platComAccount=(PlatComAccount)query.list().get(0);
		}
		return platComAccount;
	}
	
	//查询 返回一个list集合
	public List<PlatComAccount> getPlatComAccountList() throws AppException
	{
		List<PlatComAccount> list = new ArrayList<PlatComAccount>();
		Hql hql = new Hql();
		hql.add("from PlatComAccount");
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null)
		{
			list =query.list();
		}
		return list;
	}
	
//	//根据外键 交易平台表ID(dwr)
//	public List<PlatComAccount> getPlatComAccountByPlatformId(long platformId)
//	{
//		List<PlatComAccount> list = new ArrayList<PlatComAccount>();
//		Hql hql = new Hql();
//		hql.add("from PlatComAccount p where p.platform.id="+platformId);
//		Query query = this.getQuery(hql);
//		if(query != null && query.list() != null)
//		{
//			list = query.list();
//		}
//		return list;
//	}
		
}
