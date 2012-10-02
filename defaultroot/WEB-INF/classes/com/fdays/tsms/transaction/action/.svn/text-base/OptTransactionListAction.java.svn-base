package com.fdays.tsms.transaction.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.fdays.tsms.transaction.biz.OptTransactionBiz;
import com.fdays.tsms.transaction.dao.BankCardPaymentDAOImp;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;


public class OptTransactionListAction extends BaseAction{

	private LogUtil myLog;
	OptTransactionBiz optTransactionBiz;
	
	
	
	public OptTransactionBiz getOptTransactionBiz() {
		return optTransactionBiz;
	}

	public void setOptTransactionBiz(OptTransactionBiz optTransactionBiz) {
		this.optTransactionBiz = optTransactionBiz;
	}

	//分页查询(不执行存储过程)
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		OptTransactionListForm otf = (OptTransactionListForm)form;
		optTransactionBiz.createOptTransaction(otf);//执行存储过程
		myLog = new AirticketLogUtil(true, false, BankCardPaymentDAOImp.class,"");
		myLog.info("optTransaction表执行存储过程");
		if(otf==null)
		{
			otf=new OptTransactionListForm();
		}
		try {
			otf.setList(optTransactionBiz.list(otf));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		otf.addSumField(1, "inamount");
		otf.addSumField(2, "outamount");
		otf.addSumField(3, "profit");
		otf.addSumField(4, "refundamountreceived");
		otf.addSumField(5, "refundamountpaid");
		otf.addSumField(6, "cancelticketcollection");
		otf.addSumField(7, "cancelticketrefund");
		
		request.setAttribute("otf", otf);
		return mapping.findForward("listOpTtransaction");	
	}
	
	
	//分页查询(不执行存储过程)
	public ActionForward getOptTransactionlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		OptTransactionListForm otf = (OptTransactionListForm)form;
		if(otf==null)
		{
			otf=new OptTransactionListForm();
		}
		try {
			otf.setList(optTransactionBiz.list(otf));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		otf.addSumField(1, "inamount");
		otf.addSumField(2, "outamount");
		otf.addSumField(3, "profit");
		otf.addSumField(4, "refundamountreceived");
		otf.addSumField(5, "refundamountpaid");
		otf.addSumField(6, "cancelticketcollection");
		otf.addSumField(7, "cancelticketrefund");
		
		request.setAttribute("otf", otf);
		return mapping.findForward("listOpTtransaction");	
	}
	
	//导出
	public ActionForward downloadBankCardPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
			OptTransactionListForm otf = (OptTransactionListForm)form;
			if(otf!=null){
				ArrayList<ArrayList<Object>> lists = optTransactionBiz.getDownloadOptTransaction(otf);
				String outFileName = DateUtil.getDateString("yyyyMMddhhmmss") + ".csv";
				String outText = FileUtil.createCSVFile(lists);
				try
				{
					outText = new String(outText.getBytes("UTF-8"));
				}
				catch (Exception ex){
					ex.printStackTrace();
				}
				DownLoadFile df = new DownLoadFile();
				df.performTask(response, outText, outFileName, "GBK");
				myLog.info("导出操作员收付款统计成功!"+outFileName);
				return null;
			}else{
				request.getSession().invalidate();
				return mapping.findForward("listBankCardPayment");
			}
	}
	
	
	
	

}