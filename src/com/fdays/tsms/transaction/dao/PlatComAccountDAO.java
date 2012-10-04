package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountListForm;
import com.neza.exception.AppException;

public interface PlatComAccountDAO {

	
	//分页查询
	public List list(PlatComAccountListForm platComAccountForm) throws AppException;
	// 删除
	public void delete(long id) throws AppException;
	// 添加保存
	public long save(PlatComAccount platComAccount) throws AppException;
	// 修改
	public long update(PlatComAccount platComAccount) throws AppException;
	//根据id查询
	public PlatComAccount getPlatComAccountById(long platComAccountId) throws AppException;
	//查询 返回一个list集合
	public List<PlatComAccount> getPlatComAccountList() throws AppException;
	//查询 返回一个list集合
	public List<PlatComAccount> getValidPlatComAccountList() throws AppException;
	
	//根据外键 交易平台表ID(dwr)
	//public List<PlatComAccount> getPlatComAccountByPlatformId(long platformId);
	
	//根据平台ID查询团队信息(ID=99999)
	public List<PlatComAccount> getPlatComAccountByPlatformId(long platformId) throws AppException;
}
