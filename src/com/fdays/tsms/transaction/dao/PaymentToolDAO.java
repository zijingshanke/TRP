package com.fdays.tsms.transaction.dao;

import java.util.List;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.neza.exception.AppException;

public interface PaymentToolDAO {

	public List list(PaymentToolListForm paymentToolForm) throws AppException;

	public void delete(long id) throws AppException;

	public long save(PaymentTool paymentTool) throws AppException;

	public long update(PaymentTool paymentTool) throws AppException;

	public PaymentTool getPaymentToolByid(long paymentToolId)
			throws AppException;

	public List<PaymentTool> getPaymentToolList() throws AppException;

	public List<PaymentTool> getValidPaymentToolList() throws AppException;

	public List getPaymentToolListByType(String type) throws AppException;
}
