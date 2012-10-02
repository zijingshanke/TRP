package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeListForm;
import com.fdays.tsms.transaction.biz.ReportRecodeBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;


public class ReportRecodeListAction extends BaseAction {	
	private ReportRecodeBiz reportRecodeBiz;
	

	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		ReportRecodeListForm rrlf = (ReportRecodeListForm) form;
		long id = 0;
		if (rrlf.getSelectedItems().length > 0) {
			id = rrlf.getSelectedItems()[0];
		} else
			id = rrlf.getId();
		if (id > 0) {
			ReportRecode reportRecode = (ReportRecode) reportRecodeBiz
					.getReportRecodeById(id);
			if (reportRecode == null) {
				System.out.println("ReportRecode==null");
			}
			reportRecode.setThisAction("update");
			request.setAttribute("reportRecode", reportRecode);

		} else {
			request.setAttribute("reportRecode",
					new ReportRecode());
		}

		forwardPage = "editReportRecode";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ReportRecodeListForm rrlf = (ReportRecodeListForm) form;
		if (rrlf == null)
			rrlf = new ReportRecodeListForm();

		try {
			rrlf.setList(reportRecodeBiz.list(rrlf));
		} catch (Exception ex) {
			ex.printStackTrace();
			rrlf.setList(new ArrayList());
		}
		
		request.setAttribute("rrlf", rrlf);
		forwardPage = "listReportRecode";

		return (mapping.findForward(forwardPage));
	}
	

	/**
	 * 删除指定reportRecodeResultId(主表)下的记录
	 * @throws AppException
	 */
	public ActionForward deleteAllByResultId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		ReportRecodeListForm rrlf = (ReportRecodeListForm) form;
		String forwardPage = "";
		long resultId = 0;
		Inform inf = new Inform();
		try {
			resultId = rrlf.getReportRecodeResultId();
			reportRecodeBiz.deleteAllByResultId(resultId);
			return redirectViewResult(resultId);
		} catch (Exception ex) {
			inf.setMessage("删除失败:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}
	
	public ActionRedirect redirectViewResult(long resultId){
		ActionRedirect redirect = new ActionRedirect("/transaction/reportRecodeResultList.do");
		redirect.addParameter("thisAction", "addReportRecodeContinue");
		redirect.addParameter("id",resultId);
		return redirect;
	}

	/**
	 * 删除记录
	 * @throws AppException
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		ReportRecodeListForm rrlf = (ReportRecodeListForm) form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();
		try {
			for (int i = 0; i < rrlf.getSelectedItems().length; i++) {
				id = rrlf.getSelectedItems()[i];
				if (id > 0){
					reportRecodeBiz.deleteById(id);
				}
			}
			inf.setMessage("您已经成功删除!");
			String path = "/transaction/reportRecodeResultList.do?thisAction=addReportRecodeContinue&id="
					+ rrlf.getReportRecodeResultId();
			inf.setForwardPage(path);

		} catch (Exception ex) {
			inf.setMessage("删除失败:" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	public void setReportRecodeBiz(ReportRecodeBiz reportRecodeBiz) {
		this.reportRecodeBiz = reportRecodeBiz;
	}	
}
