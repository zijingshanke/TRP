package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.base.MainTask;
import com.fdays.tsms.system.biz.SysInitBiz;
import com.fdays.tsms.transaction.PlatComAccountStoreListener;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformAction extends BaseAction {
	private PlatformBiz platformBiz;
	private SysInitBiz sysInitBiz;

	// 添加
	public ActionForward savePlatform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		Platform platform = (Platform) form;
		Inform inf = new Inform();
		try {
			Platform pform = new Platform();
			pform.setName(platform.getName());
			pform.setType(platform.getType());
			pform.setDrawType(platform.getDrawType());
			pform.setStatus(platform.getStatus());
			long num = platformBiz.save(pform);

			if (num > 0) {
				return new ActionRedirect("/transaction/platformList.do?thisAction=list");
			} else {
				inf.setMessage("您添加交易平台数据失败！");
				inf.setBack(true);
			}
			// --更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz, 2);
			MainTask.put(listener);
			//
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	// 修改
	public ActionForward updatePlatform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Platform platform = (Platform) form;
		Inform inf = new Inform();
		try {
			if (platform.getId() > 0) {
				Platform pform = platformBiz.getPlatformByid(platform.getId());
				pform.setName(platform.getName());
				pform.setType(platform.getType());
				pform.setDrawType(platform.getDrawType());
				pform.setStatus(platform.getStatus());
				long flag = platformBiz.update(pform);

				if (flag > 0) {
					return new ActionRedirect("/transaction/platformList.do?thisAction=list");
				} else {
					inf.setMessage("您改交易平台数据失败！");
					inf.setBack(true);
				}
			}
			// --更新静态库
			PlatComAccountStoreListener listener = new PlatComAccountStoreListener(
					sysInitBiz, 2);
			MainTask.put(listener);
			//
		} catch (Exception e) {
			e.printStackTrace();
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		String forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}


	public void setSysInitBiz(SysInitBiz sysInitBiz) {
		this.sysInitBiz = sysInitBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}
}