package com.fdays.tsms.transaction.dao;

import java.util.List;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.neza.exception.AppException;

public interface OptTransactionDAO {
	public List list(OptTransactionListForm otf) throws AppException;

	public void createOptTransaction(OptTransactionListForm otf)
			throws AppException;
}
