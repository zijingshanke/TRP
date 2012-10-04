package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.CompanyListForm;
import com.fdays.tsms.transaction.dao.CompanyDAO;
import com.neza.exception.AppException;

public class CompanyBizImp implements CompanyBiz {

	CompanyDAO companyDAO;

	public CompanyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	// 分页查询
	public List list(CompanyListForm companyForm) throws AppException {
		return companyDAO.list(companyForm);
	}

	// 删除
	public long delete(long id) throws AppException {
		try {
			companyDAO.delete(id);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	// 添加保存
	public long save(Company Company) throws AppException {
		return companyDAO.save(Company);
	}

	public long update(Company Company) throws AppException {
		return companyDAO.update(Company);
	}

	public Company getCompanyById(long CompanyId) throws AppException {
		return companyDAO.getCompanyById(CompanyId);
	}

	public List<Company> getCompanyList() throws AppException {
		return companyDAO.getCompanyList();
	}

}
