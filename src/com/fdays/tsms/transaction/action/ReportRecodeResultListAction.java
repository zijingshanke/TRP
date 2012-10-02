package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.fdays.tsms.transaction.PaymentTool;
import com.fdays.tsms.transaction.PlatComAccountStore;
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.ReportRecodeResultListForm;
import com.fdays.tsms.transaction.biz.PlatformReportIndexBiz;
import com.fdays.tsms.transaction.biz.ReportRecodeBiz;
import com.fdays.tsms.transaction.biz.ReportRecodeResultBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class ReportRecodeResultListAction extends BaseAction {
	private ReportRecodeResultBiz reportRecodeResultBiz;
	private ReportRecodeBiz reportRecodeBiz;
	private PlatformReportIndexBiz platformReportIndexBiz;

	public ActionForward addReportRecodeResult(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportRecodeResult reportRecodeResult = new ReportRecodeResult();
		reportRecodeResult.setThisAction("insert");
		reportRecodeResult.setReportDate(new Timestamp(System
				.currentTimeMillis()));
		request.setAttribute("reportRecodeResult", reportRecodeResult);

		request = loadReportIndexList(request);

		forwardPage = "addReportRecodeResult";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward addReportRecodeContinue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		int id = rrrlf.getId();
		if (id > 0) {
			ReportRecodeResult reportRecodeResult = (ReportRecodeResult) reportRecodeResultBiz
					.getReportRecodeResultById(id);
			reportRecodeResult.setThisAction("insertContinue");
			List<Long> indexIdList = reportRecodeBiz.getDistinctIndexId(reportRecodeResult);
			Map<Long,Map<String,Integer>> idNameCount = new HashMap<Long,Map<String,Integer>>();
			if(indexIdList != null){
				for(int i=0;i<indexIdList.size();i++){
					Long indexId = indexIdList.get(i);
					if(indexId != null){
						String name = platformReportIndexBiz.getPlatformReportIndexById(indexId).getName();
						Map<String,Integer> nameCount = new HashMap<String,Integer>();
						int rowCount = reportRecodeBiz.getRowCountByIndexId(reportRecodeResult, indexId);
						nameCount.put(name,rowCount);
						idNameCount.put(indexId, nameCount);
					}
				}
				reportRecodeResult.setIdNameCount(idNameCount);
			}
			if(rrrlf.getIndexId() != 0){
				List<ReportRecode> reportRecodeList = reportRecodeBiz
						.getReportRecodeByResultIndex(reportRecodeResult, rrrlf.getIndexId());
				request.setAttribute("reportRecodeList", reportRecodeList);
			}else{
				List<ReportRecode> reportRecodeList = reportRecodeBiz
				.getReportRecodeListByResultId(id);
				request.setAttribute("reportRecodeList", reportRecodeList);
			}
			request.setAttribute("reportRecodeResult", reportRecodeResult);
			request = loadReportIndexList(request);
		}

		forwardPage = "addReportRecodeContinue";
		return (mapping.findForward(forwardPage));
	}

	public HttpServletRequest loadReportIndexList(HttpServletRequest request)
			throws AppException {
		List<Platform> platformList = PlatComAccountStore.getSalePlatform();
		request.setAttribute("platformList", platformList);

		List<PaymentTool> paymentToolList = PlatComAccountStore.paymentToolList;
		request.setAttribute("paymentToolList", paymentToolList);

		List<PlatformReportIndex> reportIndexList = platformReportIndexBiz
				.getValidPlatformReportIndexList();
		request.setAttribute("reportIndexList", reportIndexList);
		return request;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		long id = 0;
		if (rrrlf.getSelectedItems().length > 0) {
			id = rrrlf.getSelectedItems()[0];
		} else
			id = rrrlf.getId();
		if (id > 0) {
			ReportRecodeResult reportRecodeResult = (ReportRecodeResult) reportRecodeResultBiz
					.getReportRecodeResultById(id);
			if (reportRecodeResult == null) {
				System.out.println("ReportRecodeResult==null");
			}
			reportRecodeResult.setThisAction("update");
			request.setAttribute("reportRecodeResult", reportRecodeResult);

		} else {
			request
					.setAttribute("reportRecodeResult",
							new ReportRecodeResult());
		}

		forwardPage = "editReportRecodeResult";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		int id = 0;
		if (rrrlf.getId() > 0) {
			id = rrrlf.getId();
			ReportRecodeResult reportRecodeResult = reportRecodeResultBiz
					.getReportRecodeResultById(id);
			reportRecodeResult.setThisAction("view");
			
			
			List<Long> indexIdList = reportRecodeBiz.getDistinctIndexId(reportRecodeResult);
			Map<Long,Map<String,Integer>> idNameCount = new HashMap<Long,Map<String,Integer>>();
			if(indexIdList != null){
				for(int i=0;i<indexIdList.size();i++){
					Long indexId = indexIdList.get(i);
					if(indexId != null){
						String name = platformReportIndexBiz.getPlatformReportIndexById(indexId).getName();
						Map<String,Integer> nameCount = new HashMap<String,Integer>();
						int rowCount = reportRecodeBiz.getRowCountByIndexId(reportRecodeResult, indexId);
						nameCount.put(name,rowCount);
						idNameCount.put(indexId, nameCount);
					}
				}
				reportRecodeResult.setIdNameCount(idNameCount);
			}
			if(rrrlf.getIndexId() != 0){
				List<ReportRecode> reportRecodeList = reportRecodeBiz
						.getReportRecodeByResultIndex(reportRecodeResult, rrrlf.getIndexId());
				request.setAttribute("reportRecodeList", reportRecodeList);
			}else{
				List<ReportRecode> reportRecodeList = reportRecodeBiz
				.getReportRecodeListByResultId(id);
				request.setAttribute("reportRecodeList", reportRecodeList);
			}
			
			
			request.setAttribute("reportRecodeResult", reportRecodeResult);

		}
		forwardPage = "viewReportRecodeResult";
		return (mapping.findForward(forwardPage));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		if (rrrlf == null)
			rrrlf = new ReportRecodeResultListForm();

		try {
			rrrlf.setList(reportRecodeResultBiz.list(rrrlf));
		} catch (Exception ex) {
			ex.printStackTrace();
			rrrlf.setList(new ArrayList());
		}

		request.setAttribute("rrrlf", rrrlf);
		forwardPage = "listReportRecodeResult";

		return (mapping.findForward(forwardPage));
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		String forwardPage = "";
		long id = 0;
		Inform inf = new Inform();

		try {
			for (int i = 0; i < rrrlf.getSelectedItems().length; i++) {
				id = rrrlf.getSelectedItems()[i];
				if (id > 0) {
					reportRecodeBiz.deleteAllByResultId(id);	//删除子表记录
					reportRecodeResultBiz.deleteById(id);		//删除主表记录
				}
			}
			inf.setMessage("操作成功!");
			inf.setForwardPage("/transaction/reportRecodeResultList.do");
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

	public void setReportRecodeResultBiz(
			ReportRecodeResultBiz reportRecodeResultBiz) {
		this.reportRecodeResultBiz = reportRecodeResultBiz;
	}

	public void setReportRecodeBiz(ReportRecodeBiz reportRecodeBiz) {
		this.reportRecodeBiz = reportRecodeBiz;
	}

	public void setPlatformReportIndexBiz(
			PlatformReportIndexBiz platformReportIndexBiz) {
		this.platformReportIndexBiz = platformReportIndexBiz;
	}

}
