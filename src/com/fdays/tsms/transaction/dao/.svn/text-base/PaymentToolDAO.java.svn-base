package com.fdays.tsms.transaction.dao;

import java.util.List;

import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.neza.exception.AppException;

public interface PaymentToolDAO {

	
	//分页查询
	public List list(PaymentToolListForm paymentToolForm) throws AppException;
	// 删除
	public void delete(long id) throws AppException;
	// 添加保存
	public long save(PaymentTool paymentTool) throws AppException;
	// 修改
	public long update(PaymentTool paymentTool) throws AppException;
	//根据id查询
	public PaymentTool getPaymentToolByid(long paymentToolId) throws AppException;
	//查询 返回一个list集合
	public List<PaymentTool> getPaymentToolList() throws AppException;
}
