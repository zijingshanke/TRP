package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.AccountCheck;
import com.fdays.tsms.transaction.AccountCheckListForm;
import com.fdays.tsms.transaction.dao.AccountCheckDAO;
import com.neza.exception.AppException;

public class AccountCheckBizImp implements AccountCheckBiz {
	private AccountCheckDAO accountCheckDAO;

	public List list(AccountCheckListForm accountListForm,UserRightInfo uri) throws AppException {
		return accountCheckDAO.list(accountListForm,uri);
	}

	public long delete(long id) throws AppException {
		try {
			accountCheckDAO.delete(id);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public long save(AccountCheck account) throws AppException {
		return accountCheckDAO.save(account);
	}

	public long update(AccountCheck account) throws AppException {
		return accountCheckDAO.update(account);
	}

	public AccountCheck getAccountCheckById(long accountId) throws AppException {
		return accountCheckDAO.getAccountCheckById(accountId);
	}

	public List<AccountCheck> getAccountCheckList() throws AppException {
		return accountCheckDAO.getAccountCheckList();
	}

	// 导出
	public ArrayList<ArrayList<Object>> getAccountCheckList(
			AccountCheckListForm alf,UserRightInfo uri) throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		List list = accountCheckDAO.list(alf,uri);
		list_title.add("账号");
		list_title.add("余额");
		list_context.add(list_title);

		for (int i = 0; i < list.size(); i++) {
			AccountCheck account = (AccountCheck) list.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(account.getAccount().getName());// 账号
			// list_context_item.add(account.getTotalAmount());// 金额
			list_context.add(list_context_item);
		}
		return list_context;
	}

	public void setAccountCheckDAO(AccountCheckDAO accountCheckDAO) {
		this.accountCheckDAO = accountCheckDAO;
	}
}
