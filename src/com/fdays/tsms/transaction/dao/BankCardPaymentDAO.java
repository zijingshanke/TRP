package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.neza.exception.AppException;

public interface BankCardPaymentDAO {

	
	//分页查询
	public List list(BankCardPaymentListForm bankCardPaymentListForm) throws AppException;
	//根据id查询
	public BankCardPayment getBankCardPaymentById(long bankCardPaymentId) throws AppException;
	
	public void createBankCardPayment(BankCardPaymentListForm bcplistForm,String sessionId)
	throws AppException;
}
