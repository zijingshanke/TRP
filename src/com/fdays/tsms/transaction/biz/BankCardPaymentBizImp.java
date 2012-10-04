package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.fdays.tsms.transaction.dao.BankCardPaymentDAO;
import com.neza.exception.AppException;

public class BankCardPaymentBizImp implements BankCardPaymentBiz{
	
	BankCardPaymentDAO bankCardPaymentDAO;
	
	//根据IDs查询
	public BankCardPayment getBankCardPaymentById(long bankCardPaymentId)
			throws AppException {
		return bankCardPaymentDAO.getBankCardPaymentById(bankCardPaymentId);
	}

	//分页
	public List list(BankCardPaymentListForm bankCardPaymentListForm)
			throws AppException {
		return bankCardPaymentDAO.list(bankCardPaymentListForm);
	}
	
	public void createBankCardPayment(BankCardPaymentListForm bcplistForm,String sessionId)	throws AppException
	{
		 bankCardPaymentDAO.createBankCardPayment(bcplistForm,sessionId);
	}

	public BankCardPaymentDAO getBankCardPaymentDAO() {
		return bankCardPaymentDAO;
	}

	public void setBankCardPaymentDAO(BankCardPaymentDAO bankCardPaymentDAO) {
		this.bankCardPaymentDAO = bankCardPaymentDAO;
	}

	
	//账号余额导出
	public ArrayList<ArrayList<Object>> getDownloadBankCardPayment(BankCardPaymentListForm blf) throws AppException
    {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
		ArrayList<Object> list_title = new ArrayList<Object>();
		List list  = bankCardPaymentDAO.list(blf);
		list_title.add("操作人");
		list_title.add("日期");
		list_title.add("工妙");
		list_title.add("工苏");
		list_title.add("工何");
		list_title.add("工总");
		list_title.add("工关");
		list_title.add("工张");
		list_title.add("工庄");
		list_title.add("工俊");
		list_title.add("工毛");
		list_title.add("工行北岭支行");
		list_title.add("现金");
		list_title.add("支付宝信用支付");
		list_title.add("易宝信用5838");
		list_title.add("工行拱北支行");
		list_title.add("B2C代出");
		list_title.add("欠款");
		list_title.add("小计");
		list_title.add("总计");
		list_context.add(list_title);
		for(int i=0;i<list.size();i++)
		{
			BankCardPayment bankCardPayment = (BankCardPayment)list.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(bankCardPayment.getUserName());//操作人
			list_context_item.add(bankCardPayment.getBanlanceDate());//日期
			list_context_item.add(bankCardPayment.getAccount1());
			list_context_item.add(bankCardPayment.getAccount2());
			list_context_item.add(bankCardPayment.getAccount3());
			list_context_item.add(bankCardPayment.getAccount4());
			list_context_item.add(bankCardPayment.getAccount5());
			list_context_item.add(bankCardPayment.getAccount6());
			list_context_item.add(bankCardPayment.getAccount7());
			list_context_item.add(bankCardPayment.getAccount8());
			list_context_item.add(bankCardPayment.getAccount9());
			list_context_item.add(bankCardPayment.getAccount10());
			list_context_item.add(bankCardPayment.getAccount11());
			list_context_item.add(bankCardPayment.getAccount12());
			list_context_item.add(bankCardPayment.getAccount13());
			list_context_item.add(bankCardPayment.getAccount14());
			list_context_item.add(bankCardPayment.getAccount15());
			list_context_item.add(bankCardPayment.getSubtotal());//小计
			list_context_item.add(bankCardPayment.getTotal());//总计
			list_context.add(list_context_item);
		}
		return list_context;
    }
	
}
