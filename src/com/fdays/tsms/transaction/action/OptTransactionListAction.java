package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.OptTransactionListForm;
import com.fdays.tsms.transaction.biz.OptTransactionBiz;
import com.fdays.tsms.transaction.dao.BankCardPaymentDAOImp;
import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class OptTransactionListAction extends BaseAction {
	private LogUtil myLog;
	private OptTransactionBiz optTransactionBiz;

	// 分页查询(执行存储过程)
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		myLog = new AirticketLogUtil(true, false, BankCardPaymentDAOImp.class,
				"");

		OptTransactionListForm otf = (OptTransactionListForm) form;
		otf.setSessionId(request.getSession().getId());
		optTransactionBiz.createOptTransaction(otf);// 执行存储过程

		myLog.info("optTransaction表执行存储过程");

		if (otf == null) {
			otf = new OptTransactionListForm();
		}

		try {
			otf.setPerPageNum(1000);			
			otf.setList(optTransactionBiz.list(otf));
		} catch (Exception e) {
			e.printStackTrace();
		}

		otf.addSumField(1, "inamount");
		otf.addSumField(2, "outamount");
		otf.addSumField(3, "profit");
		otf.addSumField(4, "refundamountreceived");
		otf.addSumField(5, "refundamountpaid");
		otf.addSumField(6, "cancelticketcollection");
		otf.addSumField(7, "cancelticketrefund");
		//---数量
		otf.addSumField(8, "sellorderstotal");
		otf.addSumField(9, "normalorder");
		otf.addSumField(10, "alteredorder");
		otf.addSumField(11, "refundorder");
		otf.addSumField(12, "invalidorder");
		otf.addSumField(13, "cancelorder");
		otf.addSumField(14, "soldticketCount");
		
		otf.setThisAction("getOptTransactionlist");
		request.setAttribute("otf", otf);
		return mapping.findForward("listOpTtransaction");
	}

	// 分页查询(不执行存储过程)
	public ActionForward getOptTransactionlist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		OptTransactionListForm otf = (OptTransactionListForm) form;
		if (otf == null) {
			otf = new OptTransactionListForm();
		}
		try {
			otf.setPerPageNum(1000);
			otf.setSessionId(request.getSession().getId());
			otf.setList(optTransactionBiz.list(otf));

		} catch (Exception e) {
			e.printStackTrace();
		}

		otf.addSumField(1, "inamount");
		otf.addSumField(2, "outamount");
		otf.addSumField(3, "profit");
		otf.addSumField(4, "refundamountreceived");
		otf.addSumField(5, "refundamountpaid");
		otf.addSumField(6, "cancelticketcollection");
		otf.addSumField(7, "cancelticketrefund");
		//---数量（实体数据类型Long）
		otf.addSumField(8, "sellorderstotal");
		otf.addSumField(9, "normalorder");
		otf.addSumField(10, "alteredorder");
		otf.addSumField(11, "refundorder");
		otf.addSumField(12, "invalidorder");
		otf.addSumField(13, "cancelorder");
		otf.addSumField(14, "soldticket_count");
		
//		卖出订单总数	sellorderstotal	integer	FALSE	FALSE	FALSE
//		正常订单	normalorder	INTEGER	FALSE	FALSE	FALSE
//		改签订单	alteredorder	INTEGER	FALSE	FALSE	FALSE
//		退票订单	refundorder	INTEGER	FALSE	FALSE	FALSE
//		废票订单	invalidorder	INTEGER	FALSE	FALSE	FALSE
//		取消订单	cancelorder	INTEGER	FALSE	FALSE	FALSE
//		卖出机票数量	soldticket_count	INTEGER	FALSE	FALSE	FALSE

		request.setAttribute("otf", otf);
		return mapping.findForward("listOpTtransaction");
	}

	// 导出
	public ActionForward downloadBankCardPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		myLog = new AirticketLogUtil(true, false, BankCardPaymentDAOImp.class,
				"");
		OptTransactionListForm otf = (OptTransactionListForm) form;
		if (otf != null) {
			otf.setPerPageNum(1000);
			otf.setSessionId(request.getSession().getId());
			ArrayList<ArrayList<Object>> lists = optTransactionBiz
					.getDownloadOptTransaction(otf);
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
			myLog.info("导出操作员收付款统计成功!" + outFileName);
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("listBankCardPayment");
		}
	}

	public void setOptTransactionBiz(OptTransactionBiz optTransactionBiz) {
		this.optTransactionBiz = optTransactionBiz;
	}
}
