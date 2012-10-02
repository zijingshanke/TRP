package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AccountDAOImp extends BaseDAOSupport implements AccountDAO{

	
	//分页查询
	public List list(AccountListForm accountListForm) throws AppException
	{
		Hql hql = new Hql();		
		hql.add("from Account a where 1=1");
		if(accountListForm.getPaymentToolId()>0)
		{
			hql.add(" and a.paymentTool.id="+ accountListForm.getPaymentToolId());
		}
		if(accountListForm.getName()!=null && (!(accountListForm.getName().equals(""))))
		{
			hql.add(" and a.name like '%"+accountListForm.getName().trim()+"%'");
		}
		if(accountListForm.getAccountNo() !=null && (!(accountListForm.getAccountNo().equals(""))))
		{
			hql.add(" and a.accountNo like '%"+accountListForm.getAccountNo().trim()+"%'");
		}		
		hql.add(" order by a.paymentTool.id");
		return this.list(hql, accountListForm);
	}

	// 删除
	public void delete(long id) throws AppException{
		if (id > 0) {
			Account account = (Account) this.getHibernateTemplate().get(
					Account.class, new Long(id));
			this.getHibernateTemplate().delete(account);
		}
	}
	// 添加保存
	public long save(Account account) throws AppException{
		this.getHibernateTemplate().save(account);
		return account.getId();
	}

	// 修改
	public long update(Account account) throws AppException {
		if (account.getId() > 0)
		{
			this.getHibernateTemplate().update(account);
			return account.getId();
		}
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	
	//根据id查询
	public Account getAccountByid(long accountId) throws AppException {
		Hql hql = new Hql();
		hql.add("from Account p where p.id="+accountId);
		Query query = this.getQuery(hql);
		Account account=null;
		if(query!=null && query.list()!=null)
		{
			account=(Account)query.list().get(0);
		}
		return account;
	}
	
	//查询返回一个 List集合
	public List<Account> getAccountList() throws AppException
	{
		List<Account> list = new ArrayList<Account>();
		Hql hql = new Hql();
		hql.add("from Account");
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null)
		{
			list =query.list();
		}
		return list;
	}
	
	//根据外键支付工具id查询,(dwr)
	public List<Account> getAccountListByPaymentToolId(long paymentToolId)
	{
		List<Account> list = new ArrayList<Account>();
		Hql hql = new Hql();
		hql.add("from Account a where a.account.id="+paymentToolId);
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null)
		{
			list = query.list();
		}
		return list;
	}
}
