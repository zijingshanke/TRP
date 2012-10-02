package com.fdays.tsms.airticket.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.fdays.tsms.airticket.PlatLoginAccount;
import com.fdays.tsms.airticket.PlatLoginAccountListForm;
import com.fdays.tsms.airticket.biz.PlatLoginAccountBiz;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatLoginAccountListAction extends BaseAction {

	
	PlatLoginAccountBiz platLoginAccountBiz;

	//分页
    public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		PlatLoginAccountListForm plf = (PlatLoginAccountListForm) form;
		if (plf == null)
			plf = new PlatLoginAccountListForm();

		try {
			plf.setList(platLoginAccountBiz.list(plf));
		} catch (Exception ex) {
			plf.setList(new ArrayList());
		}
		request.setAttribute("plf", plf);
		request.setAttribute("platFormList", PlatComAccountStore.platFormList);
		forwardPage = "listPlatLoginAccount";

		return (mapping.findForward(forwardPage));
	}
    
    //转添加的页面
    public ActionForward savePlatLoginAccountPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    		PlatLoginAccount platLoginAccount = new PlatLoginAccount();
    		platLoginAccount.setThisAction("savePlatLoginAccount");
    		request.setAttribute("platFormList", PlatComAccountStore.platFormList);
    		request.setAttribute("platLoginAccount", platLoginAccount);
			return mapping.findForward("editPlatLoginAccount");
    }
    
    //跳转 修改页面
    public ActionForward updatePlatLoginAccountPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
    		PlatLoginAccountListForm plf = (PlatLoginAccountListForm) form;
    		request.setAttribute("platFormList", PlatComAccountStore.platFormList);//交易平台
    		long platLoginAccountId = plf.getSelectedItems()[0];
    		if(platLoginAccountId>0)
    		{
    			PlatLoginAccount platLoginAccount = platLoginAccountBiz.getPlatLoginAccountById(platLoginAccountId);
    			platLoginAccount.setThisAction("updatePlatLoginAccount");
    			platLoginAccount.setPlatformId(platLoginAccount.getPlatform().getId());
    			request.setAttribute("platLoginAccount", platLoginAccount);
    		}else
    		{
    			request.setAttribute("platLoginAccount", new PlatLoginAccount());
    		}
    		return mapping.findForward("editPlatLoginAccount");
    }
    
	//删除
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		PlatLoginAccountListForm plf = (PlatLoginAccountListForm) form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();		
		int message = 0;
		try {
			for (int i = 0; i < plf.getSelectedItems().length; i++) {
				id = plf.getSelectedItems()[i];				
				PlatLoginAccount platLoginAccount=null;
				int b=0;
				if (id > 0)
					platLoginAccount = platLoginAccountBiz.getPlatLoginAccountById(id);//查询子表中是否有数据
					message += platLoginAccountBiz.delete(id);//根据id删除
					if (message > 0) {
						inf.setMessage("您已经成功删除该平台登录帐号!");
					} else {
						inf.setMessage("删除失败!");
					}
				}	
			inf.setForwardPage("/airticket/listPlatLoginAccount.do");
			inf.setParamId("thisAction");
			inf.setParamValue("list");
		} catch (Exception ex) {
			inf.setMessage("删除失败" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";

		return (mapping.findForward(forwardPage));
	}

	
    
	public PlatLoginAccountBiz getPlatLoginAccountBiz() {
		return platLoginAccountBiz;
	}

	public void setPlatLoginAccountBiz(PlatLoginAccountBiz platLoginAccountBiz) {
		this.platLoginAccountBiz = platLoginAccountBiz;
	}
    
    

	
}
