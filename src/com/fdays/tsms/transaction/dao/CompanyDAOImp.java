package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.CompanyListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class CompanyDAOImp extends BaseDAOSupport implements CompanyDAO {

	// 分页查询
	public List list(CompanyListForm companyListForm) throws AppException {
		Hql hql = new Hql();
		hql.add("from Company c where 1=1");
		if (companyListForm.getName() != null
				&& (!(companyListForm.getName().equals("")))) {
			hql.add(" and c.name like '%" + companyListForm.getName().trim()
					+ "%'");
		}
		if (companyListForm.getType() > 0) {
			hql.add(" and c.type=" + companyListForm.getType());
		}
		hql.add("and c.status not in("+Company.STATES_1+")");//过滤无效
		hql.add(" order by updateDate,c.name");
		return this.list(hql, companyListForm);
	}

	// 删除
	public void delete(long id) throws AppException {
		if (id > 0) {
			Company company = (Company) this.getHibernateTemplate().get(
					Company.class, new Long(id));
			this.getHibernateTemplate().delete(company);
		}
	}

	// 添加保存
	public long save(Company company) throws AppException {
		this.getHibernateTemplate().save(company);
		return company.getId();
	}

	// 修改
	public long update(Company company) throws AppException {
		if (company.getId() > 0) {
			this.getHibernateTemplate().update(company);
			return company.getId();
		} else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	// //根据id查询
	// public Company getCompanyByid(long companyId) throws AppException {
	// Hql hql = new Hql();
	// hql.add("from Company c where c.id ="+companyId);
	// Query query = this.getQuery(hql);
	// Company company=null;
	// if(query != null && query.list() != null && query.list().size()>0)
	// {
	// company=(Company)query.list().get(0);
	// }
	// return company;
	// }
	// 根据id查询
	public Company getCompanyByid(long companyId) throws AppException {
		Company company = null;
		try {
			company = (Company) this.getHibernateTemplate().get(Company.class,
					new Long(companyId));
			return company;
		} catch (Exception e) {
			// TODO: handl exception
			e.printStackTrace();
			company = new Company();
		}
		return company;
	}

	// 查询 返回一个list集合
	public List<Company> getCompanyList() throws AppException {
		List<Company> list = new ArrayList<Company>();
		Hql hql = new Hql();
		hql.add("from Company order by name");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null) {
			list = query.list();
		}
		return list;
	}
	
	// 查询 返回一个list集合
	public List<Company> getValidCompanyList() throws AppException {
		List<Company> list = new ArrayList<Company>();
		Hql hql = new Hql();
		hql.add("from Company c where 1=1 and c.status=0 order by name");
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null) {
			list = query.list();
		}
		return list;
	}

}
