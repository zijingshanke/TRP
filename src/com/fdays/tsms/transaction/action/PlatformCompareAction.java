package com.fdays.tsms.transaction.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.biz.PlatformCompareBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformCompareAction extends BaseAction {
	private PlatformCompareBiz platformCompareBiz;

	/**
	 * 导入平台报表
	 */
	public ActionForward insertReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {

		String forwardPage = "";
		PlatformCompare platformCompare = (PlatformCompare) form;
		Inform inf = new Inform();
		try {
			List<PlatformCompare> compareList = platformCompareBiz
					.insertReport(platformCompare, request);

			insert(compareList);

			request.setAttribute("compareList", compareList);

			return (mapping.findForward("importPlatformReport"));
		} catch (Exception ex) {
			inf.setMessage("增加平台对比记录出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	private void insert(List<PlatformCompare> compareList) throws AppException {
		for (int i = 0; i < compareList.size(); i++) {
			PlatformCompare tempCompare = compareList.get(i);
			platformCompareBiz.save(tempCompare);

		}
	}

	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		UserRightInfo uri = (UserRightInfo) request.getSession().getAttribute(
				"URI");
		String forwardPage = "";
		PlatformCompare platformCompare = (PlatformCompare) form;
		Inform inf = new Inform();
		try {
			PlatformCompare tempCompare = (PlatformCompare) platformCompareBiz
					.getPlatformCompareById(platformCompare.getId());
			tempCompare.setPlatformId(platformCompare.getPlatformId());
			tempCompare.setFlightCode(platformCompare.getFlightCode());
			tempCompare.setFlightClass(platformCompare.getFlightClass());
			tempCompare.setTicketNumber(platformCompare.getTicketNumber());
			tempCompare.setStartPoint(platformCompare.getStartPoint());
			tempCompare.setEndPoint(platformCompare.getEndPoint());
			tempCompare.setSubPnr(platformCompare.getSubPnr());
			tempCompare.setDiscount(platformCompare.getDiscount());
			tempCompare.setMemo(platformCompare.getMemo());
			tempCompare.setType(platformCompare.getType());
			tempCompare.setStatus(platformCompare.getStatus());
			tempCompare.setBeginDate(platformCompare.getBeginDate());
			tempCompare.setEndDate(platformCompare.getEndDate());
			tempCompare.setUserNo(uri.getUser().getUserNo());
			platformCompareBiz.update(tempCompare);

			inf.setMessage("您已经成功更新了平台对比记录！");
			inf.setForwardPage("/transaction/platformCompareList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");

		} catch (Exception ex) {
			inf.setMessage("更新平台对比记录出错！错误信息是：" + ex.getMessage());
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
		PlatformCompare platformCompare = (PlatformCompare) form;
		Inform inf = new Inform();
		try {
			PlatformCompare tempCompare = new PlatformCompare();
			tempCompare.setPlatformId(platformCompare.getPlatformId());
			tempCompare.setFlightCode(platformCompare.getFlightCode());
			tempCompare.setFlightClass(platformCompare.getFlightClass());
			tempCompare.setTicketNumber(platformCompare.getTicketNumber());
			tempCompare.setStartPoint(platformCompare.getStartPoint());
			tempCompare.setEndPoint(platformCompare.getEndPoint());
			tempCompare.setSubPnr(platformCompare.getSubPnr());
			tempCompare.setDiscount(platformCompare.getDiscount());
			tempCompare.setMemo(platformCompare.getMemo());
			tempCompare.setType(platformCompare.getType());
			tempCompare.setStatus(PlatformCompare.STATES_1);
			tempCompare.setBeginDate(platformCompare.getBeginDate());
			tempCompare.setEndDate(platformCompare.getEndDate());
			tempCompare.setUserNo(uri.getUser().getUserNo());
			platformCompareBiz.save(tempCompare);

			inf.setMessage("您已经成功增加了平台对比记录！");
			inf.setForwardPage("/transaction/platformCompareList.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("增加平台对比记录出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public void setPlatformCompareBiz(PlatformCompareBiz platformCompareBiz) {
		this.platformCompareBiz = platformCompareBiz;
	}
}