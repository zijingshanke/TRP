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

public class PlatformbankCardPaymentListAction extends BaseAction {
	private PlatformbankCardPaymentBiz platformbankCardPaymentBiz;

	// 分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformbankCardPaymentListForm pbplistForm = (PlatformbankCardPaymentListForm) form;
		if (pbplistForm == null) {
			pbplistForm = new PlatformbankCardPaymentListForm();
		}
		platformbankCardPaymentBiz.createPlaBankCardPayment(pbplistForm,
				request.getSession().getId());// 执行存储过程
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
		pbplistForm.addSumField(9, "fromAccount5");
		pbplistForm.addSumField(10, "toAccount5");
		pbplistForm.addSumField(11, "fromAccount6");
		pbplistForm.addSumField(12, "toAccount6");
		pbplistForm.addSumField(13, "fromAccount7");
		pbplistForm.addSumField(14, "toAccount7");
		pbplistForm.addSumField(15, "fromAccount8");
		pbplistForm.addSumField(16, "toAccount8");
		pbplistForm.addSumField(17, "fromAccount9");
		pbplistForm.addSumField(18, "toAccount9");
		pbplistForm.addSumField(19, "fromAccount10");
		pbplistForm.addSumField(20, "toAccount10");
		pbplistForm.addSumField(21, "fromAccount11");
		pbplistForm.addSumField(22, "toAccount11");
		pbplistForm.addSumField(23, "fromAccount12");
		pbplistForm.addSumField(24, "toAccount12");
		pbplistForm.addSumField(25, "fromAccount13");
		pbplistForm.addSumField(26, "toAccount13");
		pbplistForm.addSumField(27, "fromAccount14");
		pbplistForm.addSumField(28, "toAccount14");
		pbplistForm.addSumField(29, "fromAccount15");
		pbplistForm.addSumField(30, "toAccount15");
		pbplistForm.addSumField(31, "fromAccount16");
		pbplistForm.addSumField(32, "toAccount16");
		pbplistForm.addSumField(33, "fromAccount17");
		pbplistForm.addSumField(34, "toAccount17");
		pbplistForm.addSumField(35, "fromAccount18");
		pbplistForm.addSumField(36, "toAccount18");
		pbplistForm.addSumField(37, "fromAccount19");
		pbplistForm.addSumField(38, "toAccount19");
		pbplistForm.addSumField(39, "fromAccount20");
		pbplistForm.addSumField(40, "toAccount20");
		pbplistForm.addSumField(41, "fromAccount21");
		pbplistForm.addSumField(42, "toAccount21");
		pbplistForm.addSumField(43, "fromAccount22");
		pbplistForm.addSumField(44, "toAccount22");
		pbplistForm.addSumField(45, "fromAccount23");
		pbplistForm.addSumField(46, "toAccount23");
		pbplistForm.addSumField(47, "fromAccount24");
		pbplistForm.addSumField(48, "toAccount24");
		pbplistForm.addSumField(49, "fromAccount25");
		pbplistForm.addSumField(50, "toAccount25");
		pbplistForm.addSumField(51, "fromAccount26");
		pbplistForm.addSumField(52, "toAccount26");
		pbplistForm.addSumField(53, "fromAccount27");
		pbplistForm.addSumField(54, "toAccount27");
		pbplistForm.addSumField(55, "fromAccount28");
		pbplistForm.addSumField(56, "toAccount28");
		pbplistForm.addSumField(57, "fromAccount29");
		pbplistForm.addSumField(58, "toAccount29");
		pbplistForm.addSumField(69, "fromAccount30");
		pbplistForm.addSumField(60, "toAccount30");
		pbplistForm.addSumField(61, "fromAccount31");
		pbplistForm.addSumField(62, "toAccount31");
		pbplistForm.addSumField(63, "fromAccount32");
		pbplistForm.addSumField(64, "toAccount32");
		pbplistForm.addSumField(65, "fromAccount33");
		pbplistForm.addSumField(66, "toAccount33");
		pbplistForm.addSumField(67, "fromAccount34");
		pbplistForm.addSumField(68, "toAccount34");
		pbplistForm.addSumField(69, "fromAccount35");
		pbplistForm.addSumField(70, "toAccount35");
		pbplistForm.addSumField(71, "fromAccount36");
		pbplistForm.addSumField(72, "toAccount36");
		pbplistForm.addSumField(73, "fromAccount37");
		pbplistForm.addSumField(74, "toAccount37");
		pbplistForm.addSumField(75, "fromAccount38");
		pbplistForm.addSumField(76, "toAccount38");
		pbplistForm.addSumField(77, "fromAccount39");
		pbplistForm.addSumField(78, "toAccount39");
		pbplistForm.addSumField(79, "fromAccount40");
		pbplistForm.addSumField(80, "toAccount40");
		pbplistForm.addSumField(81, "fromAccount41");
		pbplistForm.addSumField(82, "toAccount41");
		pbplistForm.addSumField(83, "fromAccount42");
		pbplistForm.addSumField(84, "toAccount42");
		pbplistForm.addSumField(85, "fromAccount43");
		pbplistForm.addSumField(86, "toAccount43");
		pbplistForm.addSumField(87, "fromAccount44");
		pbplistForm.addSumField(88, "toAccount44");
		pbplistForm.addSumField(89, "fromAccount45");
		pbplistForm.addSumField(90, "toAccount45");
		pbplistForm.addSumField(91, "fromAccount46");
		pbplistForm.addSumField(92, "toAccount46");
		pbplistForm.addSumField(93, "fromAccount47");
		pbplistForm.addSumField(94, "toAccount47");
		pbplistForm.addSumField(95, "fromAccount48");
		pbplistForm.addSumField(96, "toAccount48");
		pbplistForm.addSumField(97, "fromAccount49");
		pbplistForm.addSumField(98, "toAccount49");
		pbplistForm.addSumField(99, "fromAccount50");
		pbplistForm.addSumField(100, "toAccount50");
		pbplistForm.addSumField(101, "fromAccount51");
		pbplistForm.addSumField(102, "toAccount51");
		pbplistForm.addSumField(103, "allToAccount"); // 总收入
		pbplistForm.addSumField(104, "allFromAccount");

		// pbplistForm.addSumField(10, "allFromAccount");

		request.setAttribute("pbplistForm", pbplistForm);
		return mapping.findForward("listPlatformbankCardPayment");
	}

	// 分页查询(不执行存储过程)
	public ActionForward getPlatformbankCardPaymentlist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		PlatformbankCardPaymentListForm pbplistForm = (PlatformbankCardPaymentListForm) form;
		if (pbplistForm == null) {
			pbplistForm = new PlatformbankCardPaymentListForm();
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
		pbplistForm.addSumField(9, "fromAccount5");
		pbplistForm.addSumField(10, "toAccount5");
		pbplistForm.addSumField(11, "fromAccount6");
		pbplistForm.addSumField(12, "toAccount6");
		pbplistForm.addSumField(13, "fromAccount7");
		pbplistForm.addSumField(14, "toAccount7");
		pbplistForm.addSumField(15, "fromAccount8");
		pbplistForm.addSumField(16, "toAccount8");
		pbplistForm.addSumField(17, "fromAccount9");
		pbplistForm.addSumField(18, "toAccount9");
		pbplistForm.addSumField(19, "fromAccount10");
		pbplistForm.addSumField(20, "toAccount10");
		pbplistForm.addSumField(21, "fromAccount11");
		pbplistForm.addSumField(22, "toAccount11");
		pbplistForm.addSumField(23, "fromAccount12");
		pbplistForm.addSumField(24, "toAccount12");
		pbplistForm.addSumField(25, "fromAccount13");
		pbplistForm.addSumField(26, "toAccount13");
		pbplistForm.addSumField(27, "fromAccount14");
		pbplistForm.addSumField(28, "toAccount14");
		pbplistForm.addSumField(29, "fromAccount15");
		pbplistForm.addSumField(30, "toAccount15");
		pbplistForm.addSumField(31, "fromAccount16");
		pbplistForm.addSumField(32, "toAccount16");
		pbplistForm.addSumField(33, "fromAccount17");
		pbplistForm.addSumField(34, "toAccount17");
		pbplistForm.addSumField(35, "fromAccount18");
		pbplistForm.addSumField(36, "toAccount18");
		pbplistForm.addSumField(37, "fromAccount19");
		pbplistForm.addSumField(38, "toAccount19");
		pbplistForm.addSumField(39, "fromAccount20");
		pbplistForm.addSumField(40, "toAccount20");
		pbplistForm.addSumField(41, "fromAccount21");
		pbplistForm.addSumField(42, "toAccount21");
		pbplistForm.addSumField(43, "fromAccount22");
		pbplistForm.addSumField(44, "toAccount22");
		pbplistForm.addSumField(45, "fromAccount23");
		pbplistForm.addSumField(46, "toAccount23");
		pbplistForm.addSumField(47, "fromAccount24");
		pbplistForm.addSumField(48, "toAccount24");
		pbplistForm.addSumField(49, "fromAccount25");
		pbplistForm.addSumField(50, "toAccount25");
		pbplistForm.addSumField(51, "fromAccount26");
		pbplistForm.addSumField(52, "toAccount26");
		pbplistForm.addSumField(53, "fromAccount27");
		pbplistForm.addSumField(54, "toAccount27");
		pbplistForm.addSumField(55, "fromAccount28");
		pbplistForm.addSumField(56, "toAccount28");
		pbplistForm.addSumField(57, "fromAccount29");
		pbplistForm.addSumField(58, "toAccount29");
		pbplistForm.addSumField(69, "fromAccount30");
		pbplistForm.addSumField(60, "toAccount30");
		pbplistForm.addSumField(61, "fromAccount31");
		pbplistForm.addSumField(62, "toAccount31");
		pbplistForm.addSumField(63, "fromAccount32");
		pbplistForm.addSumField(64, "toAccount32");
		pbplistForm.addSumField(65, "fromAccount33");
		pbplistForm.addSumField(66, "toAccount33");
		pbplistForm.addSumField(67, "fromAccount34");
		pbplistForm.addSumField(68, "toAccount34");
		pbplistForm.addSumField(69, "fromAccount35");
		pbplistForm.addSumField(70, "toAccount35");
		pbplistForm.addSumField(71, "fromAccount36");
		pbplistForm.addSumField(72, "toAccount36");
		pbplistForm.addSumField(73, "fromAccount37");
		pbplistForm.addSumField(74, "toAccount37");
		pbplistForm.addSumField(75, "fromAccount38");
		pbplistForm.addSumField(76, "toAccount38");
		pbplistForm.addSumField(77, "fromAccount39");
		pbplistForm.addSumField(78, "toAccount39");
		pbplistForm.addSumField(79, "fromAccount40");
		pbplistForm.addSumField(80, "toAccount40");
		pbplistForm.addSumField(81, "fromAccount41");
		pbplistForm.addSumField(82, "toAccount41");
		pbplistForm.addSumField(83, "fromAccount42");
		pbplistForm.addSumField(84, "toAccount42");
		pbplistForm.addSumField(85, "fromAccount43");
		pbplistForm.addSumField(86, "toAccount43");
		pbplistForm.addSumField(87, "fromAccount44");
		pbplistForm.addSumField(88, "toAccount44");
		pbplistForm.addSumField(89, "fromAccount45");
		pbplistForm.addSumField(90, "toAccount45");
		pbplistForm.addSumField(91, "fromAccount46");
		pbplistForm.addSumField(92, "toAccount46");
		pbplistForm.addSumField(93, "fromAccount47");
		pbplistForm.addSumField(94, "toAccount47");
		pbplistForm.addSumField(95, "fromAccount48");
		pbplistForm.addSumField(96, "toAccount48");
		pbplistForm.addSumField(97, "fromAccount49");
		pbplistForm.addSumField(98, "toAccount49");
		pbplistForm.addSumField(99, "fromAccount50");
		pbplistForm.addSumField(100, "toAccount50");
		pbplistForm.addSumField(101, "fromAccount51");
		pbplistForm.addSumField(102, "toAccount51");
		pbplistForm.addSumField(103, "allToAccount"); // 总收入
		pbplistForm.addSumField(104, "allFromAccount");

		request.setAttribute("pbplistForm", pbplistForm);
		return mapping.findForward("listPlatformbankCardPayment");
	}

	// 导出
	public ActionForward downloadPlatformbankCardPayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		PlatformbankCardPaymentListForm blf = (PlatformbankCardPaymentListForm) form;
		if (blf != null) {

			ArrayList<ArrayList<Object>> lists = platformbankCardPaymentBiz
					.getDownloadPlatformbankCardPayment(blf);
			String outFileName = DateUtil.getDateString("yyyyMMddhhmmss")
					+ ".csv";
			String outText = FileUtil.createCSVFile(lists);
			try {
				outText = new String(outText.getBytes("UTF-8"));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "UTF-8");
			return null;
		} else {
			request.getSession().invalidate();
			return mapping.findForward("listPlatformbankCardPayment");
		}
	}

	public void setPlatformbankCardPaymentBiz(
			PlatformbankCardPaymentBiz platformbankCardPaymentBiz) {
		this.platformbankCardPaymentBiz = platformbankCardPaymentBiz;
	}
}
