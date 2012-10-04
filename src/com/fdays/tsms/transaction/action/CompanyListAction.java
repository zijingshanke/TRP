package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.CompanyListForm;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PaymentToolListForm;
import com.fdays.tsms.transaction.biz.CompanyBiz;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class CompanyListAction extends BaseAction{

	CompanyBiz companyBiz;
	
	//分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CompanyListForm companyListForm = (CompanyListForm)form;
		if(companyListForm==null)
		{
			companyListForm=new CompanyListForm();
		}
		try {
			companyListForm.setType(Company.type_1);//集团公司
			companyListForm.setList(companyBiz.list(companyListForm));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.setAttribute("companyListForm", companyListForm);
		return mapping.findForward("listCompany");	
	}
	
	//分页查询(客户)
	public ActionForward getClient(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CompanyListForm companyListForm = (CompanyListForm)form;
		if(companyListForm==null)
		{
			companyListForm=new CompanyListForm();
		}
		try {
			companyListForm.setType(Company.type_2);//客户公司
			companyListForm.setList(companyBiz.list(companyListForm));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		request.setAttribute("companyListForm", companyListForm);
		return mapping.findForward("listClientCompany");	
	}
	
	
	//显示详细信息
	public ActionForward viewCompanyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
			String forwardPage = "";
			try {
				String companyId = request.getParameter("companyId");
				if(companyId != null && (!companyId.equals("")))
				{
					Company company= companyBiz.getCompanyById(Long.parseLong(companyId));
					request.setAttribute("company", company);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			forwardPage="viewCompany";
		return mapping.findForward(forwardPage);
	}
	
	//跳转添加页面(集团)
	public ActionForward savePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Company company = new Company();
		company.setThisAction("saveCompany");
		request.setAttribute("company", company);
		String forwardPage = "editCompany";
		return mapping.findForward(forwardPage);
	}
	
	//跳转添加页面(客户)
	public ActionForward saveClientPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Company company = new Company();
		company.setThisAction("saveClientCompany");
		request.setAttribute("company", company);
		String forwardPage = "editClientCompany";
		return mapping.findForward(forwardPage);
	}
	
	//跳转修改页面(集团)
	public ActionForward updatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CompanyListForm companyListForm = (CompanyListForm)form;	
		long companyId=companyListForm.getSelectedItems()[0];
		if(companyId>0)
		{
			Company company = companyBiz.getCompanyById(companyId);
			company.setThisAction("updateCompany");
			request.setAttribute("company", company);
		}else
		{
			request.setAttribute("company", new Company());
		}
		return mapping.findForward("editCompany");
	}
	
	//跳转修改页面(客户)
	public ActionForward updateClientPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CompanyListForm companyListForm = (CompanyListForm)form;	
		long companyId=companyListForm.getSelectedItems()[0];
		if(companyId>0)
		{
			Company company = companyBiz.getCompanyById(companyId);
			company.setThisAction("updateClientCompany");
			request.setAttribute("company", company);
		}else
		{
			request.setAttribute("company", new Company());
		}
		return mapping.findForward("editClientCompany");
	}
	//删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		CompanyListForm companyListForm = (CompanyListForm)form;	
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();		
		int message = 0;
		try {
			for (int i = 0; i < companyListForm.getSelectedItems().length; i++) {
				id = companyListForm.getSelectedItems()[i];				
				Company company =null;
				int b=0;
				int c=0;
				if (id > 0)
					company = companyBiz.getCompanyById(id);//查询子表中是否有数据
					if(company!=null)
					{
						b=company.getAgents().size();
						c=company.getPlatComAccounts().size();
					}
					if(b<=0 || c<=0)//没有数据
					{
						message += companyBiz.delete(id);//根据id删除
						if (message > 0) {
						} else {
							inf.setMessage("删除失败!");
						}
					}else
					{
						inf.setMessage("不能删除,删除失败!");
					}			
				}

			return 	new ActionRedirect("/transaction/companyList.do?thisAction=list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	public CompanyBiz getCompanyBiz() {
		return companyBiz;
	}

	public void setCompanyBiz(CompanyBiz companyBiz) {
		this.companyBiz = companyBiz;
	}
	

}