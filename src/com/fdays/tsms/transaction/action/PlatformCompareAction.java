package com.fdays.tsms.transaction.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.biz.PlatformBiz;
import com.fdays.tsms.transaction.biz.PlatformCompareBiz;
import com.fdays.tsms.transaction.biz.PlatformReportIndexBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class PlatformCompareAction extends BaseAction {
	private PlatformBiz platformBiz;
	private PlatformCompareBiz platformCompareBiz;
	private PlatformReportIndexBiz platformReportIndexBiz;
	
	/**
	 * 导入平台报表
	 */
	public ActionForward insertPlatformReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {

		String forwardPage = "";
		PlatformCompare platformCompare = (PlatformCompare) form;
		Inform inf = new Inform();
		try {
			Long platformId = Constant.toLong(platformCompare.getPlatformId());
			Long compareType = Constant.toLong(platformCompare.getType());
			
			PlatformReportIndex reportIndex = platformReportIndexBiz.getReportIndexByPlatformIdType(platformId, compareType);
			
			if(reportIndex==null){
				inf.setMessage("请确认该平台报表已设置字段索引");
				inf.setBack(true);
			}else{
				request = platformCompareBiz.insertPlatformReport(platformCompare,reportIndex,
						request);
				
				request=queryOrderCompareList(platformCompare, request);

				List<Platform> platformList = platformBiz.getValidPlatformList();
//				request.setAttribute("platformList", platformList);
				request.getSession().setAttribute("platformList", platformList);

//				return (mapping.findForward("importPlatformReport"));
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
		PlatformCompare platformCompare = (PlatformCompare) form;
		Inform inf = new Inform();
		try {
			long a = System.currentTimeMillis();	
			
			platformCompareBiz.comparePlatformReport(request);

			List<Platform> platformList = platformBiz.getValidPlatformList();
//			request.setAttribute("platformList", platformList);
			request.getSession().setAttribute("platformList", platformList);
			
			long b = System.currentTimeMillis();
			System.out.println(" over get sql data  time:" + ((b - a) / 1000) + "s");

//			return (mapping.findForward("importPlatformReport"));
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
	public ActionForward updateOrderCompareList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		PlatformCompare platformCompare = (PlatformCompare) form;
		
		String forwardPage = "";		
		Inform inf = new Inform();
		try {
			request=queryOrderCompareList(platformCompare,request);

			List<Platform> platformList = platformBiz.getValidPlatformList();
//			request.setAttribute("platformList", platformList);
			request.getSession().setAttribute("platformList", platformList);

//			return (mapping.findForward("importPlatformReport"));
			return (mapping.findForward("platformCompareManage"));			
		} catch (Exception ex) {
			inf.setMessage("更新系统报表出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}
	
	private HttpServletRequest queryOrderCompareList(PlatformCompare platformCompare,HttpServletRequest request)throws AppException{
		List<PlatformCompare> orderCompareList = platformCompareBiz.getOrderCompareList(platformCompare);
		request.getSession().setAttribute("orderCompareList", orderCompareList);
		return request;
	}

	public void setPlatformCompareBiz(PlatformCompareBiz platformCompareBiz) {
		this.platformCompareBiz = platformCompareBiz;
	}

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPlatformReportIndexBiz(
			PlatformReportIndexBiz platformReportIndexBiz) {
		this.platformReportIndexBiz = platformReportIndexBiz;
	}

	
}