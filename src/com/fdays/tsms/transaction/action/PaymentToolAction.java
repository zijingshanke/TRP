package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PaymentToolAction extends BaseAction{

	PaymentToolBiz paymentToolBiz;
	private SysInitBiz sysInitBiz;
	
	public SysInitBiz getSysInitBiz() {
		return sysInitBiz;
	}

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}

	//添加
	public ActionForward savePaymentTool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PaymentTool paymentTool = (PaymentTool) form;
		Inform inf = new Inform();
		try {
			PaymentTool pmentTool = new PaymentTool();
			pmentTool.setName(paymentTool.getName());
			pmentTool.setType(paymentTool.getType());
			pmentTool.setStatus(paymentTool.getStatus());
			long num =paymentToolBiz.save(pmentTool);
           
			 if (num > 0) {
				 return new ActionRedirect("/transaction/paymentToolList.do?thisAction=list");
				}else{
					inf.setMessage("您添加支付工具失败！");
					inf.setBack(true);
				}			 
			 	//--更新静态库
				PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
						sysInitBiz,1);
				MainTask.put(listener);
				//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}		
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	//修改
	public ActionForward updatePaymentTool(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PaymentTool paymentTool = (PaymentTool)form;
		Inform inf = new Inform();
		try {
			if(paymentTool.getId()>0)
			{
				PaymentTool pTool = paymentToolBiz.getPaymentToolByid(paymentTool.getId());
				pTool.setName(paymentTool.getName());
				pTool.setType(paymentTool.getType());
				pTool.setStatus(paymentTool.getStatus());
				long flag = paymentToolBiz.update(pTool);
				
				if (flag > 0) {
					return new ActionRedirect("/transaction/paymentToolList.do?thisAction=list");
				}else{
					inf.setMessage("您改支付工具失败！");
					inf.setBack(true);
				}
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,1);
			MainTask.put(listener);
			//
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}


	public PaymentToolBiz getPaymentToolBiz() {
		return paymentToolBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}
}