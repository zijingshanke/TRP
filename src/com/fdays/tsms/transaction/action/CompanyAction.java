package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.Company;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.biz.CompanyBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class CompanyAction extends BaseAction {
	private CompanyBiz companyBiz;
	private SysInitBiz sysInitBiz;

	// 添加
	public ActionForward saveCompany(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Company company = (Company) form;
		Inform inf = new Inform();
		try {
			Company cpany = new Company();
			cpany.setName(company.getName());
			cpany.setType(company.getType());
			cpany.setStatus(company.getStatus());
			long num = companyBiz.save(cpany);

			if (num > 0) {
				inf.setMessage("您已经成功添加公司数据！");
				inf.setForwardPage("/transaction/companyList.do");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			} else {
				inf.setMessage("您添加公司数据失败！");
				inf.setBack(true);
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,3);
			MainTask.put(listener);
			//---------
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 修改
	public ActionForward updateCompany(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Company company = (Company) form;
		Inform inf = new Inform();
		try {
			if (company.getId() > 0) {
				Company cpany = companyBiz.getCompanyByid(company.getId());
				cpany.setName(company.getName());
				cpany.setType(company.getType());
				cpany.setStatus(company.getStatus());
				long flag = companyBiz.update(cpany);

				if (flag > 0) {
					inf.setMessage("您已经成功修改公司数据！");
					inf.setForwardPage("/transaction/companyList.do");
					inf.setParamId("thisAction");
					inf.setParamValue("list");
				} else {
					inf.setMessage("您改支付公司数据！");
					inf.setBack(true);
				}
			}
			//--更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz,3);
			MainTask.put(listener);
			//---------
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setCompanyBiz(CompanyBiz companyBiz) {
		this.companyBiz = companyBiz;
	}

	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}
}