package com.fdays.tsms.transaction.dao;

import java.util.List;
import com.fdays.tsms.transaction.AccountCheck;
import com.fdays.tsms.transaction.AccountCheckListForm;
import com.neza.exception.AppException;

public interface AccountCheckDAO {

	public List list(AccountCheckListForm accountCheckListForm) throws AppException;

	public void delete(long id) throws AppException;

	public long save(AccountCheck accountCheck) throws AppException;

	public long update(AccountCheck accountCheck) throws AppException;

	public AccountCheck getAccountCheckById(long accountCheckId) throws AppException;

	public List<AccountCheck> getAccountCheckList() throws AppException;

	public List<AccountCheck> getValidAccountCheckList() throws AppException;
}
