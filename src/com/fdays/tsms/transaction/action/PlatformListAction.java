package com.fdays.tsms.transaction.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformListForm;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformListAction extends BaseAction {
	private PlatformBiz platformBiz;

	// 分页查询
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformListForm platformListForm = (PlatformListForm) form;
		if (platformListForm == null) {
			platformListForm = new PlatformListForm();
		}
		try {
			platformListForm.setList(platformBiz.list(platformListForm));
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("platformListForm", platformListForm);
		return mapping.findForward("listPlatform");
	}

	// 显示详细信息
	public ActionForward viewPlatformPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		try {
			String platformId = request.getParameter("platformId");
			Platform platform = platformBiz.getPlatformById(Long
					.parseLong(platformId));
			request.setAttribute("platform", platform);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forwardPage = "viewPlatform";
		return mapping.findForward(forwardPage);

	}

	// 跳转添加页面
	public ActionForward savePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Platform platform = new Platform();
		platform.setThisAction("savePlatform");
		request.setAttribute("platform", platform);
		String forwardPage = "editPlatform";
		return mapping.findForward(forwardPage);
	}

	// 跳转修改页面
	public ActionForward updatePage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformListForm platformListForm = (PlatformListForm) form;
		long platformId = platformListForm.getSelectedItems()[0];
		if (platformId > 0) {
			Platform platform = platformBiz.getPlatformById(platformId);
			platform.setThisAction("updatePlatform");
			request.setAttribute("platform", platform);
		} else {
			request.setAttribute("platform", new Platform());
		}
		return mapping.findForward("editPlatform");
	}

	// 删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatformListForm platformListForm = (PlatformListForm) form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();
		int message = 0;
		try {
			for (int i = 0; i < platformListForm.getSelectedItems().length; i++) {
				id = platformListForm.getSelectedItems()[i];
				Platform platform = null;
				int b = 0;
				if (id > 0)
					platform = platformBiz.getPlatformById(id);// 查询子表中是否有数据
				if (platform != null) {
					b = platform.getPlatComAccounts().size();
				}
				if (b <= 0)// 没有数据
				{
					message += platformBiz.delete(id);// 根据id删除
					if (message > 0) {
					} else {
						inf.setMessage("删除失败!");
					}
				} else {
					inf.setMessage("不能删除,删除失败!");
				}
			}

			return new ActionRedirect("/transaction/platformList.do?thisAction=list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}


	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}
}