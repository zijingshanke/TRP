package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.neza.exception.AppException;

public interface OptTransactionBiz {
	
	// 分页查询
	public List list(OptTransactionListForm otf) throws AppException;
	
	
	//调用存储过程方法
	public void createOptTransaction(OptTransactionListForm otf)
	throws AppException;
	//账号余额导出
	public ArrayList<ArrayList<Object>> getDownloadOptTransaction(OptTransactionListForm otf) throws AppException;
}
