package com.fdays.tsms.transaction.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.Account;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.ReportCompare;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.ReportCompareBiz;
import com.fdays.tsms.transaction.biz.PlatformReportIndexBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ReportCompareAction extends BaseAction {
	private AccountBiz accountBiz;	
	private PlatformBiz platformBiz;
	private ReportCompareBiz reportCompareBiz;
	private PlatformReportIndexBiz platformReportIndexBiz;
	
	/**
	 * 保存报表对比结果
	*/
	public ActionForward addReportCompareResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {	
		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		//---------查询是否存在相同条件的结果集
		String result=reportCompareBiz.saveCompareResult(reportCompare, request);		
		
		ActionRedirect redirect = new ActionRedirect("../transaction/reportCompareResultList.do");
		redirect.addParameter("thisAction","list");
		return redirect;
//		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 导入平台报表
	 */
	public ActionForward insertPlatformReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {
			Long platformId = Constant.toLong(reportCompare.getPlatformId());
			Long reportType = Constant.toLong(reportCompare.getType());
			
			PlatformReportIndex reportIndex = platformReportIndexBiz.getReportIndexByPlatformIdType(platformId, reportType);
			
			if(reportIndex==null){
				inf.setMessage("请确认该平台报表已设置字段索引");
				inf.setBack(true);
			}else{
				request = reportCompareBiz.insertPlatformReport(reportCompare,reportIndex,
						request);
				
				request=queryPlatformOrderCompareList(reportCompare, request);

				List<Platform> platformList = platformBiz.getValidPlatformList();
				request.setAttribute("platformList", platformList);
				
				request.getSession().setAttribute("tempCompare", reportCompare);
				
				return (mapping.findForward("platformCompareManage"));
			}
		} catch (Exception ex) {
			inf.setMessage("增加平台对比记录出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 对比平台报表
	 */
	public ActionForward comparePlatformReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {
			long a = System.currentTimeMillis();	
			
			String flag=reportCompareBiz.comparePlatformReport(request);

			List<Platform> platformList = platformBiz.getValidPlatformList();
			request.setAttribute("platformList", platformList);
			
			long b = System.currentTimeMillis();
			System.out.println(" over comparePlatformReport  time:" + ((b - a) / 1000) + "s");

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("platformCompareManage"));
		} catch (Exception ex) {
			inf.setMessage("平台报表对比出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 更新系统报表（平台）
	 */
	public ActionForward updatePlatformOrderCompareList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ReportCompare reportCompare = (ReportCompare) form;
		
		String forwardPage = "";		
		Inform inf = new Inform();
		try {
			request=queryPlatformOrderCompareList(reportCompare,request);

			List<Platform> platformList = platformBiz.getValidPlatformList();
			request.setAttribute("platformList", platformList);

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("platformCompareManage"));			
		} catch (Exception ex) {
			inf.setMessage("更新系统报表出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	private HttpServletRequest queryPlatformOrderCompareList(ReportCompare reportCompare,HttpServletRequest request)throws AppException{
		List<ReportCompare> orderCompareList = reportCompareBiz.getPlatformOrderCompareList(reportCompare);
		request.getSession().setAttribute("orderCompareList", orderCompareList);
		request.getSession().setAttribute("orderCompareListSize", orderCompareList.size());		 
		return request;
	}

	
	/**
	 * 导入BSP<网电>报表
	 */
	public ActionForward insertBSPReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {			
			PlatformReportIndex reportIndex = platformReportIndexBiz.getReportIndexByCompareType(PlatformReportIndex.COMPARETYPE_2);
			
			if(reportIndex==null){
				inf.setMessage("请确认是否设置BSP网电字段索引");
				inf.setBack(true);
			}else{
				request = reportCompareBiz.insertBSPReport(reportCompare,reportIndex,
						request);
				
				request=queryBSPOrderCompareList(reportCompare, request);
		
				request.getSession().setAttribute("tempCompare", reportCompare);
				
				return (mapping.findForward("bspCompareManage"));
			}
		} catch (Exception ex) {
			inf.setMessage("增加BSP对比记录出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 对比BSP(网电)报表
	 */
	public ActionForward compareBSPReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {
			long a = System.currentTimeMillis();	
			
			String flag=reportCompareBiz.compareBSPReport(request);
			
			long b = System.currentTimeMillis();
			System.out.println(" over compareBSPReport  time:" + ((b - a) / 1000) + "s");

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("bspCompareManage"));
		} catch (Exception ex) {
			inf.setMessage("平台报表对比出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 更新系统报表（BSP）
	 */
	public ActionForward updateBSPOrderCompareList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ReportCompare reportCompare = (ReportCompare) form;
		
		String forwardPage = "";		
		Inform inf = new Inform();
		try {
			request=queryBSPOrderCompareList(reportCompare,request);

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("bspCompareManage"));			
		} catch (Exception ex) {
			inf.setMessage("更新系统报表(BSP)出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	private HttpServletRequest queryBSPOrderCompareList(ReportCompare reportCompare,HttpServletRequest request)throws AppException{
		List<ReportCompare> orderCompareList = reportCompareBiz.getBSPOrderCompareList(reportCompare);
		request.getSession().setAttribute("orderCompareList", orderCompareList);
		request.getSession().setAttribute("orderCompareListSize", orderCompareList.size());		 
		return request;
	}
	
	
	/**
	 * 导入<网电>报表
	 */
	public ActionForward insertNetworkReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {			
			PlatformReportIndex reportIndex = platformReportIndexBiz.getReportIndexByCompareType(PlatformReportIndex.COMPARETYPE_4);
			
			if(reportIndex==null){
				inf.setMessage("请确认是否设置网电字段索引");
				inf.setBack(true);
			}else{
				request = reportCompareBiz.insertNetworkReport(reportCompare,reportIndex,
						request);
				
				request=queryNetworkOrderCompareList(reportCompare, request);
		
				request.getSession().setAttribute("tempCompare", reportCompare);
				
				return (mapping.findForward("networkCompareManage"));
			}
		} catch (Exception ex) {
			inf.setMessage("增加网电对比记录出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 对比(网电)报表
	 */
	public ActionForward compareNetworkReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {
			long a = System.currentTimeMillis();	
			
			String flag=reportCompareBiz.compareNetworkReport(request);
			
			long b = System.currentTimeMillis();
			System.out.println(" over compareNetworkReport  time:" + ((b - a) / 1000) + "s");

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("networkCompareManage"));
		} catch (Exception ex) {
			inf.setMessage("网电报表对比出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 更新系统报表（网电）
	 */
	public ActionForward updateNetworkOrderCompareList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ReportCompare reportCompare = (ReportCompare) form;
		
		String forwardPage = "";		
		Inform inf = new Inform();
		try {
			request=queryNetworkOrderCompareList(reportCompare,request);

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("networkCompareManage"));			
		} catch (Exception ex) {
			inf.setMessage("更新系统报表(网电)出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	private HttpServletRequest queryNetworkOrderCompareList(ReportCompare reportCompare,HttpServletRequest request)throws AppException{
		List<ReportCompare> orderCompareList = reportCompareBiz.getNetworkOrderCompareList(reportCompare);
		request.getSession().setAttribute("orderCompareList", orderCompareList);
		request.getSession().setAttribute("orderCompareListSize", orderCompareList.size());		 
		return request;
	}
	
	/**
	 * 导入Bank<银行/支付平台>报表
	 */
	public ActionForward insertBankReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {			
			PlatformReportIndex reportIndex = platformReportIndexBiz.getReportIndexByCompareType(PlatformReportIndex.COMPARETYPE_2);
			
			if(reportIndex==null){
				inf.setMessage("请确认是否设置BSP网电字段索引");
				inf.setBack(true);
			}else{
				request = reportCompareBiz.insertBankReport(reportCompare,reportIndex,
						request);
				
				request=queryBankOrderCompareList(reportCompare, request);
		
				request.getSession().setAttribute("tempCompare", reportCompare);
				
				return (mapping.findForward("bspCompareManage"));
			}
		} catch (Exception ex) {
			inf.setMessage("增加BSP对比记录出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 对比Bank(银行/支付平台)报表
	 */
	public ActionForward compareBankReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportCompare reportCompare = (ReportCompare) form;
		Inform inf = new Inform();
		try {
			long a = System.currentTimeMillis();	
			
			String flag=reportCompareBiz.compareBankReport(request);

			List<Account> accountList = accountBiz.getValidAccountList();
			request.setAttribute("accountList", accountList);
			
			long b = System.currentTimeMillis();
			System.out.println(" over compareBankReport  time:" + ((b - a) / 1000) + "s");

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("bankCompareManage"));
		} catch (Exception ex) {
			inf.setMessage("银行/支付平台报表对比出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 更新系统报表（Bank）
	 */
	public ActionForward updateBankOrderCompareList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		ReportCompare reportCompare = (ReportCompare) form;
		
		String forwardPage = "";		
		Inform inf = new Inform();
		try {
			request=queryBankOrderCompareList(reportCompare,request);

			List<Account> accountList = accountBiz.getValidAccountList();
			request.setAttribute("accountList", accountList);

			request.getSession().setAttribute("tempCompare", reportCompare);
			
			return (mapping.findForward("bankCompareManage"));			
		} catch (Exception ex) {
			inf.setMessage("更新系统报表(Bank)出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	private HttpServletRequest queryBankOrderCompareList(ReportCompare reportCompare,HttpServletRequest request)throws AppException{
		List<ReportCompare> orderCompareList = reportCompareBiz.getBankOrderCompareList(reportCompare);
		request.getSession().setAttribute("orderCompareList", orderCompareList);
		request.getSession().setAttribute("orderCompareListSize", orderCompareList.size());		 
		return request;
	}


	public ActionForward clearPlatformCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			clearCompareSession(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return (mapping.findForward("redirectPlatformCompareManage"));		
	}
	
	public ActionForward clearBSPCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			clearCompareSession(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return (mapping.findForward("bspCompareManage"));		
	}
	
	public ActionForward clearBankCompare(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			clearCompareSession(mapping, form, request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return (mapping.findForward("bankCompareManage"));		
	}
	
	public void clearCompareSession(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		try {
			List<Platform> platformList = platformBiz.getValidPlatformList();
			request.setAttribute("platformList", platformList);

			ReportCompare reportCompare=new ReportCompare();
			request.getSession().setAttribute("tempCompare", reportCompare);
			
			request.getSession().setAttribute("problemCompareList1", new ArrayList<ReportCompare>());
			request.getSession().setAttribute("problemCompareList1Size",new Long(0));		 
			
			request.getSession().setAttribute("problemCompareList2", new ArrayList<ReportCompare>());
			request.getSession().setAttribute("problemCompareList2Size",new Long(0));		
			
			request.getSession().setAttribute("reportCompareList",  new ArrayList<ReportCompare>());
			request.getSession().setAttribute("reportCompareListSize",new Long(0));		 
			
			request.getSession().setAttribute("orderCompareList", new ArrayList<ReportCompare>());
			request.getSession().setAttribute("orderCompareListSize",new Long(0));			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public void setReportCompareBiz(ReportCompareBiz reportCompareBiz) {
		this.reportCompareBiz = reportCompareBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPlatformReportIndexBiz(
			PlatformReportIndexBiz platformReportIndexBiz) {
		this.platformReportIndexBiz = platformReportIndexBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz) {
		this.accountBiz = accountBiz;
	}


	
}