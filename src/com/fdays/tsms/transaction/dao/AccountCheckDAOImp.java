package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.AccountCheck;
import com.fdays.tsms.transaction.AccountCheckListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AccountCheckDAOImp extends BaseDAOSupport implements AccountCheckDAO {

	// 分页查询
	public List list(AccountCheckListForm aclf,UserRightInfo uri) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AccountCheck a where 1=1 ");
		if(uri.hasRight("sf20"))
		{			
			if(aclf.getAccountName()!=null&&"".equals(aclf.getAccountName().trim())==false){
				hql.add(" and a.account.name like '%"+aclf.getAccountName().trim().toUpperCase()+"%'");
			}
			if(aclf.getUserNo()!=null&&"".equals(aclf.getUserNo().trim())==false){
				hql.add(" and (a.sysUser.userName like '%"+aclf.getUserNo().trim()+"%'");
				hql.add(" or a.sysUser.userNo like '%"+aclf.getUserNo().trim()+"%')");			
			}			
		}
		else
		{
			hql.add(" and a.sysUser.id="+uri.getUserId());			
		}
		
			
		// 按日期搜索
		String startDate = aclf.getStartDate();
		String endDate = aclf.getEndDate();

		if ("".equals(startDate) == false && "".equals(endDate) == true)
		{
			hql.add(" and  a.checkOnDate > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate) == true && "".equals(endDate) == false)
		{
			hql.add(" and  a.checkOnDate < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate) == false && "".equals(endDate) == false)
		{
			hql.add(" and  a.checkOnDate  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		
		
		hql.add(" and a.status not in("+AccountCheck.STATES_1+") ");//过滤无效
		
		hql.add(" order by a.checkOnDate desc ");
		
//		System.out.println(hql);
		return this.list(hql, aclf);
	}

	// 删除
	public void delete(long id) throws AppException {
		if (id > 0) {
			AccountCheck account = (AccountCheck) this.getHibernateTemplate().get(
					AccountCheck.class, new Long(id));
			this.getHibernateTemplate().delete(account);
		}
	}

	// 添加保存
	public long save(AccountCheck account) throws AppException {
		this.getHibernateTemplate().save(account);
		return account.getId();
	}

	// 修改
	public long update(AccountCheck account) throws AppException {
		if (account.getId() > 0) {
			this.getHibernateTemplate().update(account);
			return account.getId();
		} else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public AccountCheck getAccountCheckById(long accountId) throws AppException {
		Hql hql = new Hql();
		hql.add("from AccountCheck p where p.id=" + accountId);
		Query query = this.getQuery(hql);
		AccountCheck account = null;
		if (query != null && query.list() != null) {
			account = (AccountCheck) query.list().get(0);
		}
		return account;
	}
	

	// 查询返回一个 List集合
	public List<AccountCheck> getAccountCheckList() throws AppException {
		List<AccountCheck> list = new ArrayList<AccountCheck>();
		Hql hql = new Hql();
		hql.add("from AccountCheck");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null) {
			list = query.list();
		}
		return list;
	}
	

	// 查询有效 List集合
	public List<AccountCheck> getValidAccountCheckList() throws AppException {
		List<AccountCheck> list = new ArrayList<AccountCheck>();
		Hql hql = new Hql();
		hql.add(" from AccountCheck where 1=1 and status=0 ");
		
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null) {
			list = query.list();
		}
		return list;
	}
}
