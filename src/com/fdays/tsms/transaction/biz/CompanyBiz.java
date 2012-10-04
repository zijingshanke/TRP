package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.CompanyListForm;
import com.neza.exception.AppException;

public interface CompanyBiz {

	public List list(CompanyListForm companyForm) throws AppException;

	public long delete(long id) throws AppException;

	public long save(Company company) throws AppException;

	public long update(Company company) throws AppException;

	public Company getCompanyById(long companyId) throws AppException;

	public List<Company> getCompanyList() throws AppException;

}
