package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.AccountListForm;
import com.fdays.tsms.transaction.BankCardPayment;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.fdays.tsms.transaction.OptTransaction;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.fdays.tsms.transaction.dao.BankCardPaymentDAO;
import com.fdays.tsms.transaction.dao.OptTransactionDAO;
import com.neza.exception.AppException;

public class OptTransactionBizImp implements OptTransactionBiz{
	
	OptTransactionDAO optTransactionDAO;
	
	
	//账号余额导出
	public ArrayList<ArrayList<Object>> getDownloadOptTransaction(OptTransactionListForm otf) throws AppException
    {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
		ArrayList<Object> list_title = new ArrayList<Object>();
		List list  = optTransactionDAO.list(otf);
		list_title.add("操作人");
		list_title.add("卖出订单总数 ");
		list_title.add("正常订单");
		list_title.add("改签订单");
		list_title.add("退票订单");
		list_title.add("废票订单");
		list_title.add("取消订单");
		list_title.add("卖出机票数量 ");
		list_title.add("收款金额");
		list_title.add("付款金额");
		list_title.add("利润");
		list_title.add("收退款金额");
		list_title.add("付退款金额");
		list_title.add("取消出票收款");
		list_title.add("取消出票退款");
		list_context.add(list_title);
		for(int i=0;i<list.size();i++)
		{
			OptTransaction optTransaction = (OptTransaction)list.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(optTransaction.getUserName());//操作人
			list_context_item.add(optTransaction.getOptDate());//日期
			list_context_item.add(optTransaction.getSellorderstotal());
			list_context_item.add(optTransaction.getNormalorder());
			list_context_item.add(optTransaction.getAlteredorder());
			list_context_item.add(optTransaction.getRefundorder());
			list_context_item.add(optTransaction.getInvalidorder());
			list_context_item.add(optTransaction.getCancelorder());
			list_context_item.add(optTransaction.getSoldticketCount());
			list_context_item.add(optTransaction.getInamount());
			list_context_item.add(optTransaction.getOutamount());
			list_context_item.add(optTransaction.getProfit());//利润
			list_context_item.add(optTransaction.getRefundamountreceived());
			list_context_item.add(optTransaction.getRefundamountpaid());
			list_context_item.add(optTransaction.getCancelticketcollection());
			list_context_item.add(optTransaction.getCancelticketrefund());
			list_context.add(list_context_item);
		}
		return list_context;
    }

	//分页
	public List list(OptTransactionListForm otf) throws AppException {
		// TODO Auto-generated method stub
		return optTransactionDAO.list(otf);
	}
	
	//调用的存储过程
	public void createOptTransaction(OptTransactionListForm otf)
			throws AppException {
		// TODO Auto-generated method stub
		optTransactionDAO.createOptTransaction(otf);
	}

	public OptTransactionDAO getOptTransactionDAO() {
		return optTransactionDAO;
	}

	public void setOptTransactionDAO(OptTransactionDAO optTransactionDAO) {
		this.optTransactionDAO = optTransactionDAO;
	}

	
	
}
