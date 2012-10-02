package com.fdays.tsms.transaction.biz;

import java.util.List;

import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.dao.PaymentToolDAO;
import com.neza.exception.AppException;

public class PaymentToolBizImp implements PaymentToolBiz {
	private PaymentToolDAO paymentToolDAO;

	// 分页查询
	public List list(PaymentToolListForm paymentToolForm) throws AppException {
		return paymentToolDAO.list(paymentToolForm);
	}

	public List getPaymentToolListByType(long type) throws AppException {
		return paymentToolDAO.getPaymentToolListByType(type);
	}

	public long delete(long id) throws AppException {
		try {
			paymentToolDAO.delete(id);
			return 1;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public long save(PaymentTool paymentTool) throws AppException {
		return paymentToolDAO.save(paymentTool);
	}

	public long update(PaymentTool paymentTool) throws AppException {
		return paymentToolDAO.update(paymentTool);
	}

	public PaymentTool getPaymentToolByid(long paymentToolId)
			throws AppException {
		return paymentToolDAO.getPaymentToolByid(paymentToolId);
	}

	public List<PaymentTool> getPaymentToolList() throws AppException {
		return paymentToolDAO.getPaymentToolList();
	}

	public void setPaymentToolDAO(PaymentToolDAO paymentToolDAO) {
		this.paymentToolDAO = paymentToolDAO;
	}
}
