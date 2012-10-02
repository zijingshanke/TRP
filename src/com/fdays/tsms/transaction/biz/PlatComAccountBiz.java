package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.PlatComAccount;
import com.fdays.tsms.transaction.PlatComAccountListForm;
import com.neza.exception.AppException;

public interface PlatComAccountBiz {
	
	//分页查询
	public List list(PlatComAccountListForm platComAccountForm) throws AppException;
	// 删除
	public long delete(long id) throws AppException;
	// 添加保存
	public long save(PlatComAccount platComAccount) throws AppException;
	// 修改
	public long update(PlatComAccount platComAccount) throws AppException;
	//根据id查询
	public PlatComAccount getPlatComAccountById(long platComAccountId) throws AppException;
	//查询 返回一个list集合
	public List<PlatComAccount> getPlatComAccountList() throws AppException;

}
