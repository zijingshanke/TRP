package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.neza.exception.AppException;

public interface BankCardPaymentBiz {
	
	//分页查询
	public List list(BankCardPaymentListForm bankCardPaymentListForm) throws AppException;
	//根据id查询
	public BankCardPayment getBankCardPaymentById(long bankCardPaymentId) throws AppException;
	
	//调用存储过程方法
	public void createBankCardPayment(BankCardPaymentListForm bcplistForm,String sessionId)
	throws AppException;
	//账号余额导出
	public ArrayList<ArrayList<Object>> getDownloadBankCardPayment(BankCardPaymentListForm blf) throws AppException;

}
