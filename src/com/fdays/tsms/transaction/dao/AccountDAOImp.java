package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AccountDAOImp extends BaseDAOSupport implements AccountDAO
{

	// 分页查询
	public List list(AccountListForm accountListForm) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Account a where 1=1");
		if (accountListForm.getPaymentToolId() > 0)
		{
			hql.add(" and a.paymentTool.id=" + accountListForm.getPaymentToolId());
		}

		if (accountListForm.getTranType() > 0)
		{
			hql.add(" and a.tranType = " + accountListForm.getTranType() + "");
		}
		if (accountListForm.getName() != null
		    && (!(accountListForm.getName().equals(""))))
		{
			hql.add(" and a.name like '%" + accountListForm.getName().trim() + "%'");
		}

		if (accountListForm.getAccountNo() != null
		    && (!(accountListForm.getAccountNo().equals(""))))
		{
			hql.add(" and a.accountNo like '%"
			    + accountListForm.getAccountNo().trim() + "%'");
		}
		hql.add("and a.status not in(" + Account.STATES_1 + ")");// 过滤无效
		hql.add(" order by a.paymentTool.id");
		return this.list(hql, accountListForm);
	}

	public void delete(long id) throws AppException
	{
		if (id > 0)
		{
			Account account = (Account) this.getHibernateTemplate().get(
			    Account.class, new Long(id));
			this.getHibernateTemplate().delete(account);
		}
	}

	public long save(Account account) throws AppException
	{
		this.getHibernateTemplate().save(account);
		return account.getId();
	}

	public long update(Account account) throws AppException
	{
		if (account.getId() > 0)
		{
			this.getHibernateTemplate().update(account);
			return account.getId();
		}
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public Account getAccountById(long accountId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Account p where p.id=" + accountId);
		Query query = this.getQuery(hql);
		Account account = null;
		if (query != null)
		{
			List list = query.list();
			if (list != null && list.size() > 0)

				account = (Account) list.get(0);
		}
		return account;
	}

	// 查询返回一个 List集合
	public List<Account> getAccountList() throws AppException
	{
		List<Account> list = new ArrayList<Account>();
		Hql hql = new Hql();
		hql.add("from Account");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null)
		{
			list = query.list();
		}
		return list;
	}

	// 查询有效 List集合
	public List<Account> getValidAccountList() throws AppException
	{
		List<Account> list = new ArrayList<Account>();
		Hql hql = new Hql();
		hql.add("from Account where 1=1 and status=0");
		hql.add(" order by name ");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null)
		{
			list = query.list();
		}
		return list;
	}

	// 查询有效 List集合 By tranType
	public List<Account> getValidAccountListByTranType(String tranType)
	    throws AppException
	{
		List<Account> list = new ArrayList<Account>();
		Hql hql = new Hql();
		hql.add(" from Account a where 1=1 and a.status=0 ");
		hql.add(" and a.tranType in(" + tranType + ") ");
		hql.add(" order by a.name ");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null)
		{
			list = query.list();
		}
		return list;
	}

	// 根据外键支付工具id查询,(dwr)
	public List<Account> getAccountListByPaymentToolId(long paymentToolId)
	{
		List<Account> list = new ArrayList<Account>();
		Hql hql = new Hql();
		hql.add("from Account a where a.account.id=" + paymentToolId);
		hql.add(" order by a.name ");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null)
		{
			list = query.list();
		}
		return list;
	}
}
