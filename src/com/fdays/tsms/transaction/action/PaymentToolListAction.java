package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PaymentToolListAction extends BaseAction{

	PaymentToolBiz paymentToolBiz;
	
	//分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PaymentToolListForm paymentToolListForm = (PaymentToolListForm)form;
		if(paymentToolListForm==null)
		{
			paymentToolListForm=new PaymentToolListForm();
		}
		try {
			paymentToolListForm.setList(paymentToolBiz.list(paymentToolListForm));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.setAttribute("paymentToolListForm", paymentToolListForm);
		return mapping.findForward("listpaymentTool");	
	}
	
	//显示详细信息
	public ActionForward viewPaymentToolPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage="";
		try {
			String paymentToolId = request.getParameter("paymentToolId");
			if(paymentToolId != null && (!paymentToolId.equals("")))
			{
				PaymentTool paymentTool = paymentToolBiz.getPaymentToolByid(Long.parseLong(paymentToolId));
				request.setAttribute("paymentTool", paymentTool);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		forwardPage ="viewPaymentTool";
		return mapping.findForward(forwardPage);
	}
	
	//跳转添加页面
	public ActionForward savePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PaymentTool paymentTool = new PaymentTool();
		paymentTool.setThisAction("savePaymentTool");
		request.setAttribute("paymentTool", paymentTool);
		String forwardPage = "editpaymentTool";
		return mapping.findForward(forwardPage);
	}
	
	//跳转修改页面
	public ActionForward updatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PaymentToolListForm paymentToolListForm =(PaymentToolListForm)form;		
		long paymentToolId=paymentToolListForm.getSelectedItems()[0];
		if(paymentToolId>0)
		{
			PaymentTool paymentTool= paymentToolBiz.getPaymentToolByid(paymentToolId);
			paymentTool.setThisAction("updatePaymentTool");
			request.setAttribute("paymentTool", paymentTool);
		}else
		{
			request.setAttribute("paymentTool", new PaymentTool());
		}
		return mapping.findForward("editpaymentTool");
	}
	//删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PaymentToolListForm paymentToolListForm = (PaymentToolListForm)form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();		
		int message = 0;
		try {
			for (int i = 0; i < paymentToolListForm.getSelectedItems().length; i++) {
				id = paymentToolListForm.getSelectedItems()[i];				
				PaymentTool paymentTool=null;
				int b=0;
				if (id > 0)
					paymentTool = paymentToolBiz.getPaymentToolByid(id);//查询子表中是否有数据
					if(paymentTool!=null)
					{
						b=paymentTool.getAccounts().size();
					}
					if(b<=0)//没有数据
					{
						message += paymentToolBiz.delete(id);//根据id删除
						if (message > 0) {
						} else {
							inf.setMessage("删除失败!");
						}
					}else
					{
						inf.setMessage("不能删除,删除失败!");
					}			
				}

			return new ActionRedirect("/transaction/paymentToolList.do?thisAction=list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}
	


	public PaymentToolBiz getPaymentToolBiz() {
		return paymentToolBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}
}