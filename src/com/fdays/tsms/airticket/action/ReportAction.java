package com.fdays.tsms.airticket.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.biz.ReportBiz;
import com.fdays.tsms.base.util.StringUtil;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.fdays.tsms.transaction.biz.StatementBiz;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class ReportAction extends BaseAction {
	private ReportBiz reportBiz;
	private StatementBiz statementBiz;
	private PaymentToolBiz paymentToolBiz;

	/***************************************************************************
	 * 下载销售报表 sc
	 **************************************************************************/
	public ActionForward downloadSaleReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf != null) {
			String platformIds = StringUtil.getStringByArray(request
					.getParameterValues("platformId"));
			String accountIds = StringUtil.getStringByArray(request
					.getParameterValues("accountId"));

			System.out.println("platformIds=======>" + platformIds);
			System.out.println("accountIds=======>" + accountIds);
			ulf.setPlatformIds(platformIds);
			ulf.setAccountIds(accountIds);

			ulf.setPerPageNum(10000);
			ArrayList<ArrayList<Object>> lists = reportBiz.downloadSaleReport(ulf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")+ ".csv";
			
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("GBK"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("exit");
		}
	}

	/***************************************************************************
	 * 下载退废报表 sc
	 **************************************************************************/
	public ActionForward downloadRetireReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf != null) {
			String platformIds = StringUtil.getStringByArray(request
					.getParameterValues("platformId"));
			String accountIds = StringUtil.getStringByArray(request
					.getParameterValues("accountId"));

			System.out.println("platformIds=======>" + platformIds);
			System.out.println("accountIds=======>" + accountIds);
			ulf.setPlatformIds(platformIds);
			ulf.setAccountIds(accountIds);

			ulf.setPerPageNum(10000);
			ArrayList<ArrayList<Object>> lists = reportBiz.downloadRetireReport(ulf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("GBK"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("exit");
		}
	}
	
	// 导出团队机票销售报表
	public ActionForward downloadTeamSaleReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrderListForm alf = (AirticketOrderListForm) form;
		if (alf != null) {
			ArrayList<ArrayList<Object>> lists = reportBiz.downloadTeamSaleReport(alf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("GB2312"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GB2312");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("teamAirTicketOrder");
		}
	}

	// 导出团队未返代理费报表
	public ActionForward downloadTeamRakeOffReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrderListForm alf = (AirticketOrderListForm) form;
		if (alf != null) {
			alf.setPerPageNum(10000);// 设制分页显示数据条数
			alf.setProxy_price(1);
			ArrayList<ArrayList<Object>> lists = reportBiz.downloadTeamRakeOffReport(alf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("GB2312"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GB2312");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("teamNotReturnProxy");
		}
	}

	/***************************************************************************
	 * 初始化销售报表 sc PlatComAccountStore
	 **************************************************************************/
	public ActionForward loadSaleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		try {
			List toAccountList = paymentToolBiz
					.getPaymentToolListByType(Account.tran_type_2);// //买出账户
			List formAccountList = paymentToolBiz
					.getPaymentToolListByType(Account.tran_type_1);// //买入账户
			request.setAttribute("toPlatformList", PlatComAccountStore
					.getToPlatform());// 买出平台
			request.setAttribute("formPlatformListByBSP", PlatComAccountStore
					.getFormPlatformByBSP());// 买入平台(平台)
			request.setAttribute("formPlatformListByB2B", PlatComAccountStore
					.getFormPlatformByB2B());// 买入平台(B2B网电)

			request.setAttribute("toAccountList", toAccountList);// 买出账户
			request.setAttribute("formAccountList", formAccountList);// 买入账户
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		forwardPage = "saleReport";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 初始化退废报表 sc
	 **************************************************************************/
	public ActionForward loadRetireReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		try {
			List toAccountList = paymentToolBiz
					.getPaymentToolListByType(Account.tran_type_2);// //买出账户
			List formAccountList = paymentToolBiz
					.getPaymentToolListByType(Account.tran_type_1);// //买入账户
			request.setAttribute("toPlatformList", PlatComAccountStore
					.getToPlatform());// 买出平台
			request.setAttribute("formPlatformListByBSP", PlatComAccountStore
					.getFormPlatformByBSP());// 买入平台(平台)
			request.setAttribute("formPlatformListByB2B", PlatComAccountStore
					.getFormPlatformByB2B());// 买入平台(B2B网电)

			request.setAttribute("toAccountList", toAccountList);// 买出账户
			request.setAttribute("formAccountList", formAccountList);// 买入账户
		} catch (Exception ex) {

		}
		forwardPage = "retireReport";
		return (mapping.findForward(forwardPage));
	}
	
	
	// 银行卡付款统计
	public ActionForward statementReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		StatementListForm statementForm = (StatementListForm) form;
		if (statementForm == null) {
			statementForm = new StatementListForm();
		}
		try {
			statementForm.setList(statementBiz.getStatementList());
		} catch (Exception e) {
			statementForm.setList(new ArrayList());
		}
		forwardPage = "statementReport";
		request.setAttribute("accountList", PlatComAccountStore.accountList);
		request.setAttribute("statementForm", statementForm);
		return mapping.findForward(forwardPage);
	}

	/***************************************************************************
	 * 原始销售报表 sc
	 **************************************************************************/
	public ActionForward marketReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf == null)
			ulf = new AirticketOrderListForm();

		try {
			ulf.setList(reportBiz.marketReportsList(ulf));
		} catch (Exception ex) {
			ulf.setList(new ArrayList());
		}
		request.setAttribute("ulf", ulf);
		forwardPage = "marketReport";
		return (mapping.findForward(forwardPage));
	}

	/***************************************************************************
	 * 原始销售报表下载 sc
	 **************************************************************************/
	public ActionForward downloadMarketReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		if (ulf != null) {

			ArrayList<ArrayList<Object>> lists = reportBiz
					.getMarketReportsList(ulf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("UTF-8"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("exit");
		}
	}

	public void setReportBiz(ReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}

	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}

}
