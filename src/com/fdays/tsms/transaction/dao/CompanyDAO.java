package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.CompanyListForm;
import com.neza.exception.AppException;

public interface CompanyDAO {

	public List list(CompanyListForm companyForm) throws AppException;

	public void delete(long id) throws AppException;

	public long save(Company company) throws AppException;

	public long update(Company company) throws AppException;

	public Company getCompanyById(long companyId) throws AppException;

	public List<Company> getCompanyList() throws AppException;

	public List<Company> getValidCompanyList() throws AppException;
}
