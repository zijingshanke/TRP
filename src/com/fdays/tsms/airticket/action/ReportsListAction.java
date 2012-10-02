package com.fdays.tsms.airticket.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.biz.ReportsBiz;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.StatementListForm;

import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class ReportsListAction extends BaseAction{
	public ReportsBiz reportsBiz;

	/***************************************************************************
	 * 全部订单  sc
	 **************************************************************************/
    public ActionForward marketReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setList(reportsBiz.marketReportsList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "marketReports";
		return (mapping.findForward(forwardPage));
	}
   
	/***************************************************************************
	 * 全部订单下载  sc
	 **************************************************************************/
	public ActionForward downloadMarketReports(ActionMapping mapping, ActionForm form,
		    HttpServletRequest request, HttpServletResponse response)
		    throws AppException
		{
			AirticketOrderListForm ulf = (AirticketOrderListForm) form;
			if(ulf!=null){
				
				ArrayList<ArrayList<Object>> lists = reportsBiz.getMarketReportsList(ulf);
				String outFileName = DateUtil.getDateString("yyyyMMddhhmmss") + ".csv";
				String outText = FileUtil.createCSVFile(lists);
				try
				{
					outText = new String(outText.getBytes("UTF-8"));
				}
				catch (Exception ex)
				{
		
				}
				DownLoadFile df = new DownLoadFile();
				df.performTask(response, outText, outFileName, "GBK");
				return null;
			}else{
				request.getSession().invalidate();
				return mapping.findForward("exit");
			}
		}
    
	//银行卡付款统计
	 public ActionForward statementReports(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws AppException {
			 String forwardPage = "";
			 StatementListForm  statementForm =(StatementListForm)form;
			 if(statementForm ==null)
			 {
				 statementForm = new StatementListForm();
			 }
			 try {
				statementForm.setList(reportsBiz.getStatementList(statementForm));
			} catch (Exception e) {
				// TODO: handle exception
				statementForm.setList(new ArrayList());
			}
			forwardPage="statementReports";
			request.setAttribute("accountList", PlatComAccountStore.accountList);
			request.setAttribute("statementForm", statementForm);
			return mapping.findForward(forwardPage);
	 }
	
	
	public ReportsBiz getReportsBiz() {
		return reportsBiz;
	}

	public void setReportsBiz(ReportsBiz reportsBiz) {
		this.reportsBiz = reportsBiz;
	}

}
