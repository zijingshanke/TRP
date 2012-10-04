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
			PlatformbankCardPaymentListForm pbplistForm,String sessionId) throws AppException {

		platformbankCardPaymentDAO.createPlaBankCardPayment(pbplistForm,sessionId);
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
		list_title1.add("支付宝taishen");
		list_title1.add("");
		list_title1.add("支付宝9679");
		list_title1.add("");
		list_title1.add("支付宝SPC");
		list_title1.add("");
		list_title1.add("支付宝6511");
		list_title1.add("");
		list_title1.add("支付宝信用支付");
		list_title1.add("");
		list_title1.add("支付宝5262");
		list_title1.add("");
		list_title1.add("支付宝5261");
		list_title1.add("");
		list_title1.add("支付宝5250");
		list_title1.add("");
		list_title1.add("快钱5261");
		list_title1.add("");
		list_title1.add("快钱5262");
		list_title1.add("");
		list_title1.add("B2C代出");
		list_title1.add("");
		list_title1.add("欠款");
		list_title1.add("");
		list_title1.add("现金");
		list_title1.add("");
		list_title1.add("MU白金卡");
		list_title1.add("");
		list_title1.add("汇付现金支付");
		list_title1.add("");
		list_title1.add("汇付Gold");
		list_title1.add("");
		list_title1.add("汇付信用支付");
		list_title1.add("");
		list_title1.add("汇付zuhts");
		list_title1.add("");
		list_title1.add("汇付920");
		list_title1.add("");
		list_title1.add("汇付10086");
		list_title1.add("");
		list_title1.add("汇付现金池");
		list_title1.add("");
		list_title1.add("etszx132");
		list_title1.add("");
		list_title1.add("et11586755826");
		list_title1.add("");
		list_title1.add("etzuh193");
		list_title1.add("");
		list_title1.add("ettaishengspc");
		list_title1.add("");
		list_title1.add("ettaishenspc");
		list_title1.add("");
		list_title1.add("工行北岭支行");
		list_title1.add("");
		list_title1.add("工妙");
		list_title1.add("");
		list_title1.add("工苏");
		list_title1.add("");
		list_title1.add("工何");
		list_title1.add("");
		list_title1.add("工总");
		list_title1.add("");
		list_title1.add("工关");
		list_title1.add("");
		list_title1.add("工张");
		list_title1.add("");
		list_title1.add("工庄");
		list_title1.add("");
		list_title1.add("工俊");
		list_title1.add("");
		list_title1.add("工毛");
		list_title1.add("");
		list_title1.add("工行拱北支行");
		list_title1.add("");
		list_title1.add("虚拟");
		list_title1.add("");
		list_title1.add("虚拟币");
		list_title1.add("");
		list_title1.add("记帐式服务");
		list_title1.add("");
		list_title1.add("钱门tsb2c");
		list_title1.add("");
		list_title1.add("钱门CHEN");
		list_title1.add("");
		list_title1.add("钱门9679");
		list_title1.add("");
		list_title1.add("钱门6511");
		list_title1.add("");
		list_title1.add("钱门记账式服务");
		list_title1.add("");
		list_title1.add("钱门SPC");
		list_title1.add("");
		list_title1.add("财付通6511");
		list_title1.add("");
		list_title1.add("财付通SPC");
		list_title1.add("");
		list_title1.add("财付通9679");
		list_title1.add("");
		list_title1.add("财付通CHEN");
		list_title1.add("");
		list_title1.add("易宝信用5838");
		list_title1.add("");
		list_title1.add("合计");
		
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
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
		list_title.add("收入");
		list_title.add("支出");
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
			list_context_item.add(pcp.getFromAccount5());
			list_context_item.add(pcp.getToAccount5());
			list_context_item.add(pcp.getFromAccount6());
			list_context_item.add(pcp.getToAccount6());
			list_context_item.add(pcp.getFromAccount7());
			list_context_item.add(pcp.getToAccount7());
			list_context_item.add(pcp.getFromAccount8());
			list_context_item.add(pcp.getToAccount8());
			list_context_item.add(pcp.getFromAccount9());
			list_context_item.add(pcp.getToAccount9());
			list_context_item.add(pcp.getFromAccount10());
			list_context_item.add(pcp.getToAccount10());
			
			list_context_item.add(pcp.getFromAccount11());
			list_context_item.add(pcp.getToAccount11());
			list_context_item.add(pcp.getFromAccount12());
			list_context_item.add(pcp.getToAccount12());
			list_context_item.add(pcp.getFromAccount13());
			list_context_item.add(pcp.getToAccount13());
			list_context_item.add(pcp.getFromAccount14());
			list_context_item.add(pcp.getToAccount14());
			list_context_item.add(pcp.getFromAccount15());
			list_context_item.add(pcp.getToAccount15());
			list_context_item.add(pcp.getFromAccount16());
			list_context_item.add(pcp.getToAccount16());
			list_context_item.add(pcp.getFromAccount17());
			list_context_item.add(pcp.getToAccount17());
			list_context_item.add(pcp.getFromAccount18());
			list_context_item.add(pcp.getToAccount18());
			list_context_item.add(pcp.getFromAccount19());
			list_context_item.add(pcp.getToAccount19());
			list_context_item.add(pcp.getFromAccount20());
			list_context_item.add(pcp.getToAccount20());
			
			list_context_item.add(pcp.getFromAccount21());
			list_context_item.add(pcp.getToAccount21());
			list_context_item.add(pcp.getFromAccount22());
			list_context_item.add(pcp.getToAccount22());
			list_context_item.add(pcp.getFromAccount23());
			list_context_item.add(pcp.getToAccount23());
			list_context_item.add(pcp.getFromAccount24());
			list_context_item.add(pcp.getToAccount24());
			list_context_item.add(pcp.getFromAccount25());
			list_context_item.add(pcp.getToAccount25());
			list_context_item.add(pcp.getFromAccount26());
			list_context_item.add(pcp.getToAccount26());
			list_context_item.add(pcp.getFromAccount27());
			list_context_item.add(pcp.getToAccount27());
			list_context_item.add(pcp.getFromAccount28());
			list_context_item.add(pcp.getToAccount28());
			list_context_item.add(pcp.getFromAccount29());
			list_context_item.add(pcp.getToAccount29());
			list_context_item.add(pcp.getFromAccount30());
			list_context_item.add(pcp.getToAccount30());
			
			list_context_item.add(pcp.getFromAccount31());
			list_context_item.add(pcp.getToAccount31());
			list_context_item.add(pcp.getFromAccount32());
			list_context_item.add(pcp.getToAccount32());
			list_context_item.add(pcp.getFromAccount33());
			list_context_item.add(pcp.getToAccount33());
			list_context_item.add(pcp.getFromAccount34());
			list_context_item.add(pcp.getToAccount34());
			list_context_item.add(pcp.getFromAccount35());
			list_context_item.add(pcp.getToAccount35());
			list_context_item.add(pcp.getFromAccount36());
			list_context_item.add(pcp.getToAccount36());
			list_context_item.add(pcp.getFromAccount37());
			list_context_item.add(pcp.getToAccount37());
			list_context_item.add(pcp.getFromAccount38());
			list_context_item.add(pcp.getToAccount38());
			list_context_item.add(pcp.getFromAccount39());
			list_context_item.add(pcp.getToAccount39());
			list_context_item.add(pcp.getFromAccount40());
			list_context_item.add(pcp.getToAccount40());
			
			list_context_item.add(pcp.getFromAccount41());
			list_context_item.add(pcp.getToAccount41());
			list_context_item.add(pcp.getFromAccount42());
			list_context_item.add(pcp.getToAccount42());
			list_context_item.add(pcp.getFromAccount43());
			list_context_item.add(pcp.getToAccount43());
			list_context_item.add(pcp.getFromAccount44());
			list_context_item.add(pcp.getToAccount44());
			list_context_item.add(pcp.getFromAccount45());
			list_context_item.add(pcp.getToAccount45());
			list_context_item.add(pcp.getFromAccount46());
			list_context_item.add(pcp.getToAccount46());
			list_context_item.add(pcp.getFromAccount47());
			list_context_item.add(pcp.getToAccount47());
			list_context_item.add(pcp.getFromAccount48());
			list_context_item.add(pcp.getToAccount48());
			list_context_item.add(pcp.getFromAccount49());
			list_context_item.add(pcp.getToAccount49());
			list_context_item.add(pcp.getFromAccount50());
			list_context_item.add(pcp.getToAccount50());
			list_context_item.add(pcp.getFromAccount51());
			list_context_item.add(pcp.getToAccount51());
			
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
