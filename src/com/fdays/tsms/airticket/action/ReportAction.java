package com.fdays.tsms.airticket.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.OptTransaction;
import com.fdays.tsms.airticket.Report;
import com.fdays.tsms.airticket.biz.ReportBiz;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.biz.AccountBiz;
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
	private AccountBiz accountBiz;
	private PaymentToolBiz paymentToolBiz;
	
	/**
	 * 初始化操作员收付款统计
	 */
	public ActionForward initOptTransactionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		Report report = (Report) form;
		if (report == null) {
			report = new Report();
		}
		try {
			report.setThisAction("listOptTransaction");
		} catch (Exception ex) {
			report.setOptList(new ArrayList());
		}
		request.setAttribute("report", report);
		forwardPage = "listoptTransaction";

		return (mapping.findForward(forwardPage));
	}

	/**
	 * 查询操作员收付款统计
	 */
	public ActionForward listOptTransaction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		Report report = (Report) form;
		if (report == null) {
			report = new Report();
		}

		try {
			List<OptTransaction> optList=reportBiz.listOptTransaction(report);
			System.out.println("======listOptTransaction success...size:"+optList.size());
			report.setOptList(optList);
		
		} catch (Exception ex) {
			report.setOptList(new ArrayList());
		}
		request.setAttribute("report", report);
		forwardPage = "listoptTransaction";

		return (mapping.findForward(forwardPage));
	}

	/**
	 * 下载操作员收付款报表
	 */
	public ActionForward downloadOptTransactionReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Report report = (Report) form;
		if (report != null) {
			ArrayList<ArrayList<Object>> lists = null;
			lists = reportBiz.downloadOptTransactionReport(report);

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

	/**
	 * 下载销售报表
	 */
	public ActionForward downloadSaleReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Report report = (Report) form;
		if (report != null) {
			ArrayList<ArrayList<Object>> lists = null;
			Long reportType = report.getReportType();
			if (reportType != null) {
				if (reportType.compareTo(Report.ReportType1) == 0) {
					lists = reportBiz.downloadSaleReport(report);// 财务版
				} else if (reportType.compareTo(Report.ReportType2) == 0) {
					lists = reportBiz.downloadPolicySaleReport(report);// 政策版
				}
			}
			
				
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

	/**
	 * 下载退废报表
	 */
	public ActionForward downloadRetireReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Report report = (Report) form;
		if (report != null) {
			ArrayList<ArrayList<Object>> lists = reportBiz
					.downloadRetireReport(report);
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

	/**
	 * 下载团队统计报表
	 */
	public ActionForward downloadTeamSaleReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Report report = (Report) form;
		if (report != null) {
			ArrayList<ArrayList<Object>> lists = reportBiz
					.downloadTeamSaleReport(report);
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
			return mapping.findForward("teamAirTicketOrder");
		}
	}

	/**
	 * 下载团队未返代理费报表
	 */
	public ActionForward downloadTeamRakeOffReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		Report report = (Report) form;
		if (report != null) {
			ArrayList<ArrayList<Object>> lists = reportBiz
					.downloadTeamRakeOffReport(report);
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
			return mapping.findForward("teamNotReturnProxy");
		}
	}

	/**
	 * 初始化销售报表查询页面
	 */
	public ActionForward loadSaleReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		try {
			Report report = (Report) form;

			request = loadPlatAcountList(request);

			report.setThisAction("downloadSaleReport");
			request.setAttribute("report", report);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		forwardPage = "saleReport";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 初始化退废报表查询页面
	 */
	public ActionForward loadRetireReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		try {
			Report report = (Report) form;

			request = loadPlatAcountList(request);

			report.setThisAction("downloadRetireReport");
			request.setAttribute("report", report);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		forwardPage = "saleReport";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 加载平台账号信息
	 */
	public HttpServletRequest loadPlatAcountList(HttpServletRequest request)
			throws AppException {
		// List<Account> receiveAccountList =
		// accountBiz.getValidAccountListByTranType(Account.tran_type_2+","+Account.tran_type_3);//
		// 收款账户
		// List<Account> payAccountList
		// =accountBiz.getValidAccountListByTranType(Account.tran_type_1+","+Account.tran_type_3);//
		// 付款账户

		List receiveAccountList = paymentToolBiz
				.getPaymentToolListByType(Account.tran_type_2 + ","
						+ Account.tran_type_3);// 收款账户
		List payAccountList = paymentToolBiz
				.getPaymentToolListByType(Account.tran_type_1 + ","
						+ Account.tran_type_3);// 付款账户

		request.setAttribute("salePlatformList", PlatComAccountStore
				.getToPlatform());// 买出平台
		request.setAttribute("buyPlatformListB2B", PlatComAccountStore
				.getB2BBuyPlatform());// 买入平台(平台)
		request.setAttribute("buyPlatformListBSP", PlatComAccountStore
				.getBSPBuyPlatform());// 买入平台(网电/BSP)

		request.setAttribute("receiveAccountList", receiveAccountList);// 收款账户
		request.setAttribute("payAccountList", payAccountList);// 付款账户
		return request;
	}

	/**
	 * 初始化团队销售查询页面
	 */
	public ActionForward loadTeamSaleReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		try {
			Report report = (Report) form;

			report.setThisAction("downloadTeamSaleReport");
			request.setAttribute("report", report);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		forwardPage = "saleReport";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 初始化团队未返报表查询页面
	 */
	public ActionForward loadTeamRakeOffReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		try {
			Report report = (Report) form;

			report.setThisAction("downloadTeamRakeOffReport");

			request.setAttribute("report", report);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		forwardPage = "saleReport";
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
		// AirticketOrderListForm ulf = (AirticketOrderListForm) form;
		// if (ulf == null)
		// ulf = new AirticketOrderListForm();
		//
		// try {
		// ulf.setList(reportBiz.marketReportsList(ulf));
		// } catch (Exception ex) {
		// ulf.setList(new ArrayList());
		// }
		// request.setAttribute("ulf", ulf);
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

			// ArrayList<ArrayList<Object>> lists = reportBiz
			// .getMarketReportsList(ulf);
			// String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
			// + ".csv";
			// String outText = FileUtil.createCSVFile(lists);
			// try {
			// outText = new String(outText.getBytes("UTF-8"));
			// } catch (Exception ex) {
			// ex.printStackTrace();
			// }
			// DownLoadFile df = new DownLoadFile();
			// df.performTask(response, outText, outFileName, "GBK");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("exit");
		}
	}
//	/**
//	 * 查询操作员收付款统计
//	 */
//	public ActionForward listOptTransaction(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws AppException {
//		String forwardPage = "";
//		Report report = (Report) form;
//		if (report == null) {
//			report = new Report();
//		}
//
//		try {
//			report.setOptList(reportBiz.listOptTransaction(report));
//			report.setOptHead(getOptHeadByDepart(report.getOperatorDepart()));
//			
//			String[] optHead=report.getOptHead();
//			for (int i = 0; i < optHead.length; i++) {
//				if(optHead[i]!=null){
//					System.out.println("显示=====>>>>>"+optHead[i]);
//				}
//				
//			}
//			
//		} catch (Exception ex) {
//			report.setOptList(new ArrayList());
//		}
//		request.setAttribute("report", report);
//		forwardPage = "listoptTransaction";
//
//		return (mapping.findForward(forwardPage));
//	}
//	
//	/**
//	 * 初始化操作员收付款统计
//	 */
//	public ActionForward initOptTransactionReport(ActionMapping mapping,
//			ActionForm form, HttpServletRequest request,
//			HttpServletResponse response) throws AppException {
//		String forwardPage = "";
//		Report report = (Report) form;
//		if (report == null) {
//			report = new Report();
//		}
//
//		try {
//			report.setOptList(null);
//			report.setOptHead(getInitOptHead());
//			report.setThisAction("listOptTransaction");
//		} catch (Exception ex) {
//			report.setOptList(new ArrayList());
//		}
//		request.setAttribute("report", report);
//		forwardPage = "listoptTransaction";
//
//		return (mapping.findForward(forwardPage));
//	}
//
//	public static String[] getInitOptHead() {
//		String[] tempOptHead = { "opterateNo", "opterateName", "saleOrderNum",
//				"normalOrderNum", "", "umbuchenOrderNum", "retireOrderNum",
//				"invalidOrderNum", "cancelOrderNum", "saleTicketNum",
//				"inAmount", "outAmount", "profits", "inRetireAmount",
//				"outRetireAmount", "inCancelAmount", "outCancelAmount" };
//		return tempOptHead;
//	}

//	public static String[] getOptHeadByDepart(Long userDepart) {
//	  //String[] optHead = new String[20];
//		String[] optHead = getInitOptHead();
//		
//		if (userDepart != null && userDepart > 0) {
//			if (userDepart.intValue() == 1) {// 出票组
//				String[] tempOptHead = { "opterateNo", "opterateName",
//						"saleOrderNum", null, null, null, null, null,
//						"saleTicketNum", null, "outAmount", null, null, null,
//						null, null };
//				return tempOptHead;
//			} else if (userDepart.intValue() == 2) {// 倒票组
//				String[] tempOptHead = { "opterateNo", "opterateName",
//						"saleOrderNum", null, null, null, null, null,
//						"saleTicketNum", null, "outAmount", null, null, null,
//						null, null };
//				return tempOptHead;
//			} else if (userDepart.intValue() == 3) {// 退票组;
//				String[] tempOptHead = { "opterateNo", "opterateName", null,
//						"umbuchenOrderNum", "retireOrderNum",
//						"invalidOrderNum", null, null, "saleTicketNum",
//						"inAmount", "outAmount", null, "inRetireAmount",
//						"outRetireAmount", "inCancelAmount", "outCancelAmount" };
//				return tempOptHead;
//			} else if (userDepart.intValue() == 11) {
//				// return "B2C组";
//			} else if (userDepart.intValue() == 12) {
//				// return "团队部";
//			} else if (userDepart.intValue() == 21) {// 支付组
//				String[] tempOptHead = { "opterateNo", "opterateName",
//						"saleOrderNum", null, "umbuchenOrderNum",
//						"retireOrderNum", "invalidOrderNum", null,
//						"saleTicketNum", null, "outAmount", null, null, null,
//						null, null };
//				return tempOptHead;
//			} else if (userDepart.intValue() == 22) {// 财务部
//				String[] tempOptHead = { "opterateNo", "opterateName",
//						"saleOrderNum", "normalOrderNum", "umbuchenOrderNum",
//						"retireOrderNum", "invalidOrderNum", "cancelOrderNum",
//						"saleTicketNum", "inAmount", "outAmount", "profits",
//						"inRetireAmount", "outRetireAmount", "inCancelAmount",
//						"outCancelAmount" };
//				return tempOptHead;
//			} else if (userDepart.intValue() == 41) {
//				// return "政策组";
//			}
//		}
//
//		return optHead;
//	}

	public void setReportBiz(ReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}

	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}

}
