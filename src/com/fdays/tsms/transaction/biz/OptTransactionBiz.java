package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.neza.exception.AppException;

public interface OptTransactionBiz {

	public List list(OptTransactionListForm otf) throws AppException;

	public void createOptTransaction(OptTransactionListForm otf)
			throws AppException;

	public ArrayList<ArrayList<Object>> getDownloadOptTransaction(
			OptTransactionListForm otf) throws AppException;
}
