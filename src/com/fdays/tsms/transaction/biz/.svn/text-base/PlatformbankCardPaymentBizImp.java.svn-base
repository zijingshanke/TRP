package com.fdays.tsms.transaction.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.PlatformbankCardPayment;
import com.fdays.tsms.transaction.PlatformbankCardPaymentListForm;
import com.fdays.tsms.transaction.dao.PlatformbankCardPaymentDAO;
import com.neza.exception.AppException;

public class PlatformbankCardPaymentBizImp implements
		PlatformbankCardPaymentBiz {
	PlatformbankCardPaymentDAO platformbankCardPaymentDAO;


	public void createPlaBankCardPayment(
			PlatformbankCardPaymentListForm pbplistForm) throws AppException {

		platformbankCardPaymentDAO.createPlaBankCardPayment(pbplistForm);
	};

	public List list(PlatformbankCardPaymentListForm pbplistForm)
			throws AppException {

		return platformbankCardPaymentDAO.list(pbplistForm);
	}
	
	
	//下载
	public ArrayList<ArrayList<Object>> getDownloadPlatformbankCardPayment(PlatformbankCardPaymentListForm pbplistForm) throws AppException
    {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
		ArrayList<Object> list_title = new ArrayList<Object>();
		List list  = platformbankCardPaymentDAO.list(pbplistForm);
		ArrayList<Object> list_title1 = new ArrayList<Object>();
		
		list_title1.add("");
		list_title1.add("散客-B2C");
		list_title1.add("");
		list_title1.add("珠海品尚物流平台");
		list_title1.add("");
		list_title1.add("珠海品尚物流平台");
		list_title1.add("");
		list_title1.add("散客-B2C");
		list_title1.add("");
		list_context.add(list_title1);
		
		list_title.add("平台");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		

		
		list_title.add("总收入");
		list_title.add("总支出");
		list_context.add(list_title);
		for(int i=0;i<list.size();i++)
		{
			PlatformbankCardPayment pcp = (PlatformbankCardPayment)list.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			list_context_item.add(pcp.getPlatform());
			list_context_item.add(pcp.getFromAccount1());
			list_context_item.add(pcp.getToAccount1());
			
			list_context_item.add(pcp.getFromAccount2());
			list_context_item.add(pcp.getToAccount2());
			
			list_context_item.add(pcp.getFromAccount3());
			list_context_item.add(pcp.getToAccount3());
			
			list_context_item.add(pcp.getFromAccount4());
			list_context_item.add(pcp.getToAccount4());
			
			list_context_item.add(pcp.getAllToAccount());
			list_context_item.add(pcp.getAllFromAccount());
			list_context.add(list_context_item);
		}
		return list_context;
    }
	
	public PlatformbankCardPaymentDAO getPlatformbankCardPaymentDAO() {
		return platformbankCardPaymentDAO;
	}

	public void setPlatformbankCardPaymentDAO(
			PlatformbankCardPaymentDAO platformbankCardPaymentDAO) {
		this.platformbankCardPaymentDAO = platformbankCardPaymentDAO;
	}


}
