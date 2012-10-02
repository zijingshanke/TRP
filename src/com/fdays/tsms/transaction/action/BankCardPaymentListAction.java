package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.biz.TempPNRBizImp;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.BankCardPaymentListForm;
import com.fdays.tsms.transaction.biz.BankCardPaymentBiz;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class BankCardPaymentListAction extends BaseAction{

	private LogUtil myLog;
	BankCardPaymentBiz bankCardPaymentBiz;
	
	//分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		BankCardPaymentListForm bankCardPaymentListForm = (BankCardPaymentListForm)form;
		myLog=new AirticketLogUtil(true,false,TempPNRBizImp.class,"");
		if(bankCardPaymentListForm==null)
		{
			bankCardPaymentListForm=new BankCardPaymentListForm();
		}
		
		bankCardPaymentBiz.createBankCardPayment(bankCardPaymentListForm,request.getSession().getId());//执行存储过程
		myLog.info("bankCardPayment表执行存储过程");
		try {
			bankCardPaymentListForm.setList(bankCardPaymentBiz.list(bankCardPaymentListForm));
		} catch (Exception e) {
			e.printStackTrace();
		}

		bankCardPaymentListForm.addSumField(1, "account1");
		bankCardPaymentListForm.addSumField(2, "account2");
		bankCardPaymentListForm.addSumField(3, "account3");
		bankCardPaymentListForm.addSumField(4, "account4");
		bankCardPaymentListForm.addSumField(5, "account5");
		bankCardPaymentListForm.addSumField(6, "account6");
		bankCardPaymentListForm.addSumField(7, "account7");
		bankCardPaymentListForm.addSumField(8, "account8");
		bankCardPaymentListForm.addSumField(9, "account9");
		bankCardPaymentListForm.addSumField(10, "account10");
		bankCardPaymentListForm.addSumField(11, "account11");
		bankCardPaymentListForm.addSumField(12, "account12");
		bankCardPaymentListForm.addSumField(13, "account13");
		bankCardPaymentListForm.addSumField(14, "account14");
		bankCardPaymentListForm.addSumField(15, "account15");
		bankCardPaymentListForm.addSumField(16, "account16");
		bankCardPaymentListForm.addSumField(17, "subtotal");
		bankCardPaymentListForm.addSumField(18, "total");
		
		request.setAttribute("bankCardPaymentListForm", bankCardPaymentListForm);
		return mapping.findForward("listBankCardPayment");	
	}

	
	//分页查询(不执行存储过程)
	public ActionForward getBankCardPaymentlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		BankCardPaymentListForm bankCardPaymentListForm = (BankCardPaymentListForm)form;
		if(bankCardPaymentListForm==null)
		{
			bankCardPaymentListForm=new BankCardPaymentListForm();
		}
		try {
			bankCardPaymentListForm.setList(bankCardPaymentBiz.list(bankCardPaymentListForm));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		bankCardPaymentListForm.addSumField(1, "account1");
		bankCardPaymentListForm.addSumField(2, "account2");
		bankCardPaymentListForm.addSumField(3, "account3");
		bankCardPaymentListForm.addSumField(4, "account4");
		bankCardPaymentListForm.addSumField(5, "account5");
		bankCardPaymentListForm.addSumField(6, "account6");
		bankCardPaymentListForm.addSumField(7, "account7");
		bankCardPaymentListForm.addSumField(8, "account8");
		bankCardPaymentListForm.addSumField(9, "account9");
		bankCardPaymentListForm.addSumField(10, "account10");
		bankCardPaymentListForm.addSumField(11, "account11");
		bankCardPaymentListForm.addSumField(12, "account12");
		bankCardPaymentListForm.addSumField(13, "account13");
		bankCardPaymentListForm.addSumField(14, "account14");
		bankCardPaymentListForm.addSumField(15, "account15");
		bankCardPaymentListForm.addSumField(16, "account16");
		bankCardPaymentListForm.addSumField(17, "subtotal");
		bankCardPaymentListForm.addSumField(18, "total");
		
		request.setAttribute("bankCardPaymentListForm", bankCardPaymentListForm);
		return mapping.findForward("listBankCardPayment");	
	}
	
	//导出
	public ActionForward downloadBankCardPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
			BankCardPaymentListForm blf = (BankCardPaymentListForm)form;
			myLog=new AirticketLogUtil(true,false,TempPNRBizImp.class,"");
			if(blf!=null){
				
				ArrayList<ArrayList<Object>> lists = bankCardPaymentBiz.getDownloadBankCardPayment(blf);
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
				myLog.info("导出银行卡付款报表成功!"+outFileName);
				return null;
			}else{
				request.getSession().invalidate();
				return mapping.findForward("listBankCardPayment");
			}
	}
	
	
	public BankCardPaymentBiz getBankCardPaymentBiz() {
		return bankCardPaymentBiz;
	}

	public void setBankCardPaymentBiz(BankCardPaymentBiz bankCardPaymentBiz) {
		this.bankCardPaymentBiz = bankCardPaymentBiz;
	}
	
	
	

}