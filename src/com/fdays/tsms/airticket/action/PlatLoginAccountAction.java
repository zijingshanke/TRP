package com.fdays.tsms.airticket.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.PlatLoginAccount;
import com.fdays.tsms.airticket.biz.PlatLoginAccountBiz;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatLoginAccountAction extends BaseAction {

	PlatLoginAccountBiz platLoginAccountBiz;
	PlatformBiz platformBiz;
	
	public PlatformBiz getPlatformBiz() {
		return platformBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public PlatLoginAccountBiz getPlatLoginAccountBiz() {
		return platLoginAccountBiz;
	}

	public void setPlatLoginAccountBiz(PlatLoginAccountBiz platLoginAccountBiz) {
		this.platLoginAccountBiz = platLoginAccountBiz;
	}
	
	
	//添加
	 public ActionForward savePlatLoginAccount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		 	PlatLoginAccount platLoginAccount =(PlatLoginAccount)form;
		 	String forwardPage = "";
			Inform inf = new Inform();
		 	long platformId= platLoginAccount.getPlatformId();
		 	try {
		 		if(platformId>0)
			 	{
		 			PlatLoginAccount plat = new PlatLoginAccount();
			 		Platform platform = platformBiz.getPlatformById(platformId);
			 		plat.setPlatform(platform);
			 		plat.setLoginName(platLoginAccount.getLoginName());
			 		plat.setLoginPwd(platLoginAccount.getLoginPwd());
			 		plat.setType(platLoginAccount.getType());
			 		plat.setStatus(platLoginAccount.getStatus());
			 		long num=platLoginAccountBiz.save(plat);
			 		 if (num > 0) {
							inf.setMessage("您已经成功添加平台登录帐号！");
							inf.setForwardPage("/airticket/listPlatLoginAccount.do");
							inf.setParamId("thisAction");
							inf.setParamValue("list");
						}else{
							inf.setMessage("您添加平台登录帐号失败！");
							inf.setBack(true);
						}	
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					inf.setBack(true);
				}		
				request.setAttribute("inf", inf);
				forwardPage = "inform";
				return (mapping.findForward(forwardPage));
	 }
	 
	//修改
		public ActionForward updatePlatLoginAccount(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws AppException {
			PlatLoginAccount platLoginAccount =(PlatLoginAccount)form;
			Inform inf = new Inform();
			try {
				if(platLoginAccount.getId()>0)
				{
					long platformId= platLoginAccount.getPlatformId();
					if(platformId>0)
					{
						Platform platform = platformBiz.getPlatformById(platformId);
						PlatLoginAccount plat= platLoginAccountBiz.getPlatLoginAccountById(platLoginAccount.getId());
						plat.setPlatform(platform);
				 		plat.setLoginName(platLoginAccount.getLoginName());
				 		plat.setLoginPwd(platLoginAccount.getLoginPwd());
				 		plat.setType(platLoginAccount.getType());
				 		plat.setStatus(platLoginAccount.getStatus());
						long flag =platLoginAccountBiz.update(plat);
						
						if (flag > 0) {
							inf.setMessage("您已经成功修改平台登录帐号！");
							inf.setForwardPage("/airticket/listPlatLoginAccount.do");
							inf.setParamId("thisAction");
							inf.setParamValue("list");
						}else{
							inf.setMessage("您改平台登录帐号失败！");
							inf.setBack(true);
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				inf.setBack(true);
			}		
			request.setAttribute("inf", inf);
			String	forwardPage = "inform";
			return (mapping.findForward(forwardPage));
		}
}
