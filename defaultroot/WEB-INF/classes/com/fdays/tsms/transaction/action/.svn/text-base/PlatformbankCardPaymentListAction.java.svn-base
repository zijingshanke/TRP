package com.fdays.tsms.transaction.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.transaction.PlatformbankCardPaymentListForm;
import com.fdays.tsms.transaction.biz.PlatformbankCardPaymentBiz;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class PlatformbankCardPaymentListAction extends BaseAction{

	PlatformbankCardPaymentBiz platformbankCardPaymentBiz;
	
	

	//分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformbankCardPaymentListForm pbplistForm  = (PlatformbankCardPaymentListForm)form;
		if(pbplistForm==null)
		{
			pbplistForm=new PlatformbankCardPaymentListForm();
		}
		platformbankCardPaymentBiz.createPlaBankCardPayment(pbplistForm);//执行存储过程
		try {
			pbplistForm.setList(platformbankCardPaymentBiz.list(pbplistForm));
		} catch (Exception e) {
			e.printStackTrace();
		}

		pbplistForm.addSumField(1, "fromAccount1");
		pbplistForm.addSumField(2, "toAccount1");
		pbplistForm.addSumField(3, "fromAccount2");
		pbplistForm.addSumField(4, "toAccount2");
		pbplistForm.addSumField(5, "fromAccount3");
		pbplistForm.addSumField(6, "toAccount3");
		pbplistForm.addSumField(7, "fromAccount4");
		pbplistForm.addSumField(8, "toAccount4");
		pbplistForm.addSumField(9, "allToAccount");
		pbplistForm.addSumField(10, "allFromAccount");
		
		request.setAttribute("pbplistForm", pbplistForm);
		return mapping.findForward("listPlatformbankCardPayment");	
	}

	
	//分页查询(不执行存储过程)
	public ActionForward getPlatformbankCardPaymentlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformbankCardPaymentListForm pbplistForm = (PlatformbankCardPaymentListForm)form;
		if(pbplistForm==null)
		{
			pbplistForm=new PlatformbankCardPaymentListForm();
		}
		try {
			pbplistForm.setList(platformbankCardPaymentBiz.list(pbplistForm));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		pbplistForm.addSumField(1, "fromAccount1");
		pbplistForm.addSumField(2, "toAccount1");
		pbplistForm.addSumField(3, "fromAccount2");
		pbplistForm.addSumField(4, "toAccount2");
		pbplistForm.addSumField(5, "fromAccount3");
		pbplistForm.addSumField(6, "toAccount3");
		pbplistForm.addSumField(7, "fromAccount4");
		pbplistForm.addSumField(8, "toAccount4");
		//pbplistForm.addSumField(9, "allToAccount");
		//pbplistForm.addSumField(10, "allFromAccount");
		request.setAttribute("pbplistForm", pbplistForm);
		return mapping.findForward("listPlatformbankCardPayment");	
	}
	
	//导出
	public ActionForward downloadPlatformbankCardPayment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformbankCardPaymentListForm blf = (PlatformbankCardPaymentListForm)form;
			if(blf!=null){
				
				ArrayList<ArrayList<Object>> lists = platformbankCardPaymentBiz.getDownloadPlatformbankCardPayment(blf);
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
				df.performTask(response, outText, outFileName, "UTF-8");
				return null;
			}else{
				request.getSession().invalidate();
				return mapping.findForward("listPlatformbankCardPayment");
			}
	}
	
	public PlatformbankCardPaymentBiz getPlatformbankCardPaymentBiz() {
		return platformbankCardPaymentBiz;
	}


	public void setPlatformbankCardPaymentBiz(
			PlatformbankCardPaymentBiz platformbankCardPaymentBiz) {
		this.platformbankCardPaymentBiz = platformbankCardPaymentBiz;
	}

	
	
}
