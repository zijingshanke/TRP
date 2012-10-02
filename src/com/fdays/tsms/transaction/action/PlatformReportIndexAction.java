package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.biz.PlatformReportIndexBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformReportIndexAction extends BaseAction {
	private PlatformReportIndexBiz platformReportIndexBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		PlatformReportIndex reportIndex = (PlatformReportIndex) form;
		Inform inf = new Inform();
		try {
			PlatformReportIndex tempReportIndex = (PlatformReportIndex) platformReportIndexBiz
					.getPlatformReportIndexById(reportIndex.getId());
			tempReportIndex.setPlatformId(reportIndex.getPlatformId());
			tempReportIndex.setAccountId(reportIndex.getAccountId());
			tempReportIndex.setPaymenttoolId(reportIndex.getPaymenttoolId());
			
			tempReportIndex.setAirOrderNo(reportIndex.getAirOrderNo());
			tempReportIndex.setPayOrderNo(reportIndex.getPayOrderNo());
			tempReportIndex.setInAccount(reportIndex.getInAccount());
			tempReportIndex.setOutAccount(reportIndex.getOutAccount());
			tempReportIndex.setInRetireAccount(reportIndex.getInRetireAccount());
			tempReportIndex.setOutRetireAccount(reportIndex.getOutRetireAccount());
			tempReportIndex.setInAmount(reportIndex.getInAmount());
			tempReportIndex.setOutAmount(reportIndex.getOutAmount());
			tempReportIndex.setInRetireAmount(reportIndex.getInRetireAmount());
			tempReportIndex.setOutRetireAmount(reportIndex.getOutRetireAmount());
			tempReportIndex.setPassengerCount(reportIndex.getPassengerCount());
				
			tempReportIndex.setFlightCode(reportIndex.getFlightCode());
			tempReportIndex.setFlightClass(reportIndex.getFlightClass());
			tempReportIndex.setTicketNumber(reportIndex.getTicketNumber());
			tempReportIndex.setStartPoint(reportIndex.getStartPoint());
			tempReportIndex.setEndPoint(reportIndex.getEndPoint());
			tempReportIndex.setSubPnr(reportIndex.getSubPnr());
			tempReportIndex.setDiscount(reportIndex.getDiscount());
			tempReportIndex.setMemo(reportIndex.getMemo());
			tempReportIndex.setType(reportIndex.getType());
			tempReportIndex.setCompareType(reportIndex.getCompareType());			
			tempReportIndex.setStatus(reportIndex.getStatus());
			tempReportIndex.setLastDate(new Timestamp(System
					.currentTimeMillis()));
			tempReportIndex.setUserNo(uri.getUser().getUserNo());
			platformReportIndexBiz.update(tempReportIndex);

			inf.setMessage("您已经成功更新了报表索引！");
			inf.setForwardPage("/transaction/platformReportIndexList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

		} catch (Exception ex) {
			inf.setMessage("更新报表索引出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		PlatformReportIndex reportIndex = (PlatformReportIndex) form;
		Inform inf = new Inform();
		try {
			PlatformReportIndex tempReportIndex = new PlatformReportIndex();
			tempReportIndex.setPlatformId(reportIndex.getPlatformId());
			tempReportIndex.setAccountId(reportIndex.getAccountId());
			tempReportIndex.setPaymenttoolId(reportIndex.getPaymenttoolId());
			
			tempReportIndex.setAirOrderNo(reportIndex.getAirOrderNo());
			tempReportIndex.setPayOrderNo(reportIndex.getPayOrderNo());
			tempReportIndex.setInAccount(reportIndex.getInAccount());
			tempReportIndex.setOutAccount(reportIndex.getOutAccount());
			tempReportIndex.setInRetireAccount(reportIndex.getInRetireAccount());
			tempReportIndex.setOutRetireAccount(reportIndex.getOutRetireAccount());
			tempReportIndex.setInAmount(reportIndex.getInAmount());
			tempReportIndex.setOutAmount(reportIndex.getOutAmount());
			tempReportIndex.setInRetireAmount(reportIndex.getInRetireAmount());
			tempReportIndex.setOutRetireAmount(reportIndex.getOutRetireAmount());		
			
			tempReportIndex.setFlightCode(reportIndex.getFlightCode());
			tempReportIndex.setFlightClass(reportIndex.getFlightClass());
			tempReportIndex.setTicketNumber(reportIndex.getTicketNumber());
			tempReportIndex.setStartPoint(reportIndex.getStartPoint());
			tempReportIndex.setEndPoint(reportIndex.getEndPoint());
			tempReportIndex.setSubPnr(reportIndex.getSubPnr());
			tempReportIndex.setDiscount(reportIndex.getDiscount());
			tempReportIndex.setMemo(reportIndex.getMemo());
			tempReportIndex.setType(reportIndex.getType());
			tempReportIndex.setCompareType(reportIndex.getCompareType());			
			tempReportIndex.setStatus(PlatformReportIndex.STATES_1);
			tempReportIndex.setLastDate(new Timestamp(System
					.currentTimeMillis()));
			tempReportIndex.setUserNo(uri.getUser().getUserNo());
			platformReportIndexBiz.save(tempReportIndex);
			
			inf.setMessage("您已经成功增加了报表索引！");
			inf.setForwardPage("/transaction/platformReportIndexList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加报表索引出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setPlatformReportIndexBiz(
			PlatformReportIndexBiz platformReportIndexBiz) {
		this.platformReportIndexBiz = platformReportIndexBiz;
	}
}