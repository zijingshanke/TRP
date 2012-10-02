package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;
import com.fdays.tsms.transaction.OptTransaction;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.fdays.tsms.transaction.dao.OptTransactionDAO;
import com.neza.exception.AppException;

public class OptTransactionBizImp implements OptTransactionBiz {

	OptTransactionDAO optTransactionDAO;

	// 账号余额导出
	public ArrayList<ArrayList<Object>> getDownloadOptTransaction(
			OptTransactionListForm otf) throws AppException {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> list_title = new ArrayList<Object>();
		List list = optTransactionDAO.list(otf);
		list_title.add("");
		list_title.add("操作人");
		list_title.add("日期");
		list_title.add("卖出订单总数 ");
		list_title.add("正常订单");
		list_title.add("改签订单");
		list_title.add("退票订单");
		list_title.add("废票订单");
		list_title.add("取消订单");
		list_title.add("卖出机票数量 ");
		list_title.add("收款金额");
		list_title.add("付款金额");
		list_title.add("收退款金额");
		list_title.add("付退款金额");
		list_title.add("取消出票收款");
		list_title.add("取消出票退款");
		list_title.add("利润");
		
		double sellorderstotal=0;//卖出订单总数
		double normalorder=0;
		double alteredorder=0;
		double refundorder=0;
		double invalidorder=0;
		double cancelorder=0;
		double soldticketCount=0;//卖出机票数量
		
		double inamount =0;//收款金额
		double outamount =0;//付款金额
		double refundamountreceived=0;//收退款金额
		double refundamountpaid =0;//付退款金额 
		double cancelticketcollection =0;//取消出票收款 
		double cancelticketrefund =0;//取消出票退款
		double profit =0;//利润 
		
		ArrayList<Object> list_context_item = new ArrayList<Object>();
		list_context.add(list_title);
		for (int i = 0; i < list.size(); i++) {
			OptTransaction optTransaction = (OptTransaction) list.get(i);
			 list_context_item = new ArrayList<Object>();
			 list_context_item.add("");
			list_context_item.add(optTransaction.getUserName());// 操作人
			list_context_item.add(optTransaction.getOptDate());// 日期
			list_context_item.add(optTransaction.getSellorderstotal());
			list_context_item.add(optTransaction.getNormalorder());
			list_context_item.add(optTransaction.getAlteredorder());
			list_context_item.add(optTransaction.getRefundorder());
			list_context_item.add(optTransaction.getInvalidorder());
			list_context_item.add(optTransaction.getCancelorder());
			list_context_item.add(optTransaction.getSoldticketCount());
			
			list_context_item.add(optTransaction.getInamount());
			list_context_item.add(optTransaction.getOutamount());
			list_context_item.add(optTransaction.getRefundamountreceived());
			list_context_item.add(optTransaction.getRefundamountpaid());
			list_context_item.add(optTransaction.getCancelticketcollection());
			list_context_item.add(optTransaction.getCancelticketrefund());
			list_context_item.add(optTransaction.getProfit());// 利润
			list_context.add(list_context_item);
			
			//合计累加
			 sellorderstotal+=optTransaction.getSellorderstotal().doubleValue();//卖出订单总数
			 normalorder+=optTransaction.getNormalorder().doubleValue();
			 alteredorder+=optTransaction.getAlteredorder().doubleValue();
			 refundorder+=optTransaction.getRefundorder().doubleValue();
			 invalidorder+=optTransaction.getInvalidorder().doubleValue();
			 cancelorder+=optTransaction.getCancelorder().doubleValue();
			 soldticketCount+=optTransaction.getSoldticketCount().doubleValue();//卖出机票数量
			
			inamount+=optTransaction.getInamount().doubleValue();//收款金额
			outamount+=optTransaction.getOutamount().doubleValue();
			refundamountreceived+=optTransaction.getRefundamountreceived().doubleValue();
			refundamountpaid+=optTransaction.getRefundamountpaid().doubleValue();
			cancelticketcollection+=optTransaction.getCancelticketcollection().doubleValue();
			cancelticketrefund+=optTransaction.getCancelticketrefund().doubleValue();
			profit+=optTransaction.getProfit().doubleValue();//利润
		}
		//合计
		list_context_item = new ArrayList<Object>();
		list_context_item.add("合 计");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add(sellorderstotal);//卖出订单总数
		list_context_item.add(normalorder);
		list_context_item.add(alteredorder);
		list_context_item.add(refundorder);
		list_context_item.add(invalidorder);
		list_context_item.add(cancelorder);
		list_context_item.add(soldticketCount);//卖出机票数量
		list_context_item.add(inamount);//收款金额
		list_context_item.add(outamount);
		list_context_item.add(refundamountreceived);
		list_context_item.add(refundamountpaid);
		list_context_item.add(cancelticketcollection);
		list_context_item.add(cancelticketrefund);
		list_context_item.add(profit);//利润
		list_context.add(list_context_item);
		return list_context;
	}

	// 分页
	public List list(OptTransactionListForm otf) throws AppException {
		return optTransactionDAO.list(otf);
	}

	// 调用的存储过程
	public void createOptTransaction(OptTransactionListForm otf)
			throws AppException {
		optTransactionDAO.createOptTransaction(otf);
	}

	public OptTransactionDAO getOptTransactionDAO() {
		return optTransactionDAO;
	}

	public void setOptTransactionDAO(OptTransactionDAO optTransactionDAO) {
		this.optTransactionDAO = optTransactionDAO;
	}

}
