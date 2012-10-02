package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountListForm;
import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.fdays.tsms.transaction.dao.BankCardPaymentDAO;
import com.neza.exception.AppException;

public class BankCardPaymentBizImp implements BankCardPaymentBiz{
	
	BankCardPaymentDAO bankCardPaymentDAO;
	
	//根据IDs查询
	public BankCardPayment getBankCardPaymentById(long bankCardPaymentId)
			throws AppException {
		// TODO Auto-generated method stub
		return bankCardPaymentDAO.getBankCardPaymentById(bankCardPaymentId);
	}

	//分页
	public List list(BankCardPaymentListForm bankCardPaymentListForm)
			throws AppException {
		// TODO Auto-generated method stub
		return bankCardPaymentDAO.list(bankCardPaymentListForm);
	}
	
	public void createBankCardPayment(BankCardPaymentListForm bcplistForm)	throws AppException
	{
		 bankCardPaymentDAO.createBankCardPayment(bcplistForm);
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
		list_title.add("团队收款(系统)");
		list_title.add("团队付款(系统)");
		list_title.add("支付宝5261");
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
			list_context_item.add(bankCardPayment.getSubtotal());//小计
			list_context_item.add(bankCardPayment.getTotal());//总计
			list_context.add(list_context_item);
		}
		return list_context;
    }
	
}
