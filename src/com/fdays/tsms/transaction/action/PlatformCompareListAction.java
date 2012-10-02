package com.fdays.tsms.transaction.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.PlatformCompareBiz;
import com.neza.base.BaseAction;
import com.neza.exception.AppException;

public class PlatformCompareListAction extends BaseAction {
	private PlatformBiz platformBiz;
	private PlatformCompareBiz platformCompareBiz;
	
	//导入交易平台报表
	public ActionForward platformCompareManage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformCompare platformCompare = new PlatformCompare();
		platformCompare.setThisAction("insertPlatformReport");
		platformCompare.setStatus(PlatformCompare.STATES_1);
		request.setAttribute("platformCompare", platformCompare);		
		
		List<Platform> platformList = platformBiz.getValidPlatformList();
//		request.setAttribute("platformList", platformList);
		request.getSession().setAttribute("platformList", platformList);
		
		String forwardPage = "platformCompareManage";
		return (mapping.findForward(forwardPage));
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPlatformCompareBiz(PlatformCompareBiz platformCompareBiz) {
		this.platformCompareBiz = platformCompareBiz;
	}
}
