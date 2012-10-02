package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.CompanyListForm;
import com.neza.exception.AppException;

public interface CompanyDAO {

	
	//分页查询
	public List list(CompanyListForm companyForm) throws AppException;
	// 删除
	public void delete(long id) throws AppException;
	// 添加保存
	public long save(Company company) throws AppException;
	// 修改
	public long update(Company company) throws AppException;
	//根据id查询
	public Company getCompanyByid(long companyId) throws AppException;
	//查询 返回一个list集合
	public List<Company> getCompanyList() throws AppException;
	
	// 查询 返回一个list集合
	public List<Company> getValidCompanyList() throws AppException;
}
