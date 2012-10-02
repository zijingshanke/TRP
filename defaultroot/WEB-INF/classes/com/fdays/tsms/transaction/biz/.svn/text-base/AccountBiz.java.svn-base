package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountListForm;
import com.neza.exception.AppException;

public interface AccountBiz {
	
	//分页查询
	public List list(AccountListForm accountListForm) throws AppException;
	// 删除
	public long delete(long id) throws AppException;
	// 添加保存
	public long save(Account account) throws AppException;
	// 修改
	public long update(Account account) throws AppException;
	//根据id查询
	public Account getAccountByid(long accountId) throws AppException;
	//查询返回一个 List集合
	public List<Account> getAccountList() throws AppException;
	//根据外键支付工具id查询,(dwr)
	public List<Account> getAccountListByPaymentToolId(long paymentToolId);
	//账号余额导出
	public ArrayList<ArrayList<Object>> getAccountBalanceList(AccountListForm alf)throws AppException;

}
