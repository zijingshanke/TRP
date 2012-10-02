package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.dao.PaymentToolDAO;
import com.neza.exception.AppException;

public class PaymentToolBizImp implements PaymentToolBiz{

	
	PaymentToolDAO paymentToolDAO;
	
	//分页查询
	public List list(PaymentToolListForm paymentToolForm) throws AppException
	{
		return paymentToolDAO.list(paymentToolForm);
	}
	
	// 删除
	public long delete(long id) throws AppException
	{
		try {			
			paymentToolDAO.delete(id);
			return 1;			
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}
	
	// 添加保存
	public long save(PaymentTool paymentTool) throws AppException
	{
		return paymentToolDAO.save(paymentTool);
	}
	
	// 修改
	public long update(PaymentTool paymentTool) throws AppException
	{
		return paymentToolDAO.update(paymentTool);
	}

	//根据id查询
	public PaymentTool getPaymentToolByid(long paymentToolId) throws AppException{
		return paymentToolDAO.getPaymentToolByid(paymentToolId);
	}
	
	//查询 返回一个list集合
	public List<PaymentTool> getPaymentToolList() throws AppException
	{
		return paymentToolDAO.getPaymentToolList();
	}
	
	public PaymentToolDAO getPaymentToolDAO() {
		return paymentToolDAO;
	}

	public void setPaymentToolDAO(PaymentToolDAO paymentToolDAO) {
		this.paymentToolDAO = paymentToolDAO;
	}
}
