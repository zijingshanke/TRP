package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.transaction.AccountCheck;
import com.fdays.tsms.transaction.AccountCheckListForm;
import com.neza.exception.AppException;

public interface AccountCheckBiz {

	public List list(AccountCheckListForm accountListForm) throws AppException;

	public long delete(long id) throws AppException;

	public long save(AccountCheck account) throws AppException;

	public long update(AccountCheck account) throws AppException;

	public AccountCheck getAccountCheckById(long accountId) throws AppException;

	public List<AccountCheck> getAccountCheckList() throws AppException;

	public ArrayList<ArrayList<Object>> getAccountCheckList(
			AccountCheckListForm alf) throws AppException;

}
