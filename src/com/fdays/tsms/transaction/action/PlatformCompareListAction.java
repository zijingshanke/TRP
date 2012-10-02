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
	
	

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPlatformCompareBiz(PlatformCompareBiz platformCompareBiz) {
		this.platformCompareBiz = platformCompareBiz;
	}
}
