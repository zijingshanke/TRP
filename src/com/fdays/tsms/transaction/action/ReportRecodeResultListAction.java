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
import com.fdays.tsms.transaction.Platform;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.ReportRecodeResultListForm;
import com.fdays.tsms.transaction.biz.PaymentToolBiz;
import com.fdays.tsms.transaction.biz.PlatformBiz;
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
	private PlatformBiz platformBiz;
	private PaymentToolBiz paymentToolBiz;

	/**
	 * 增加页面
	 * @throws AppException
	 */
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

	/**
	 * 转到继续添加页面
	 * @throws AppException
	 */
	public ActionForward addReportRecodeContinue(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws AppException {
		String forwardPage = "";
		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		ReportRecode totalReportRecode = new ReportRecode();
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
				
				totalReportRecode
						.setTotalInAmount(reportRecodeBiz.getMoneyByResultInex(reportRecodeResult, rrrlf.getIndexId()));
				
			}else{
				List<ReportRecode> reportRecodeList = reportRecodeBiz
						.getReportRecodeListByResultId(id);
				request.setAttribute("reportRecodeList", reportRecodeList);
				totalReportRecode
						.setTotalInAmount(reportRecodeBiz.getMoneyByResult(reportRecodeResult));
			}
			request.setAttribute("totalReportRecode", totalReportRecode);
			request.setAttribute("reportRecodeResult", reportRecodeResult);
			request = loadReportIndexList(request);
		}

		forwardPage = "addReportRecodeContinue";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 获取平台、支付工具集合
	 * @param request
	 * @return
	 * @throws AppException
	 */
	public HttpServletRequest loadReportIndexList(HttpServletRequest request)
			throws AppException {
		List<PlatformReportIndex> reportIndexList = platformReportIndexBiz.getValidPlatformReportIndexList();
		List<Platform> platformList = new ArrayList<Platform>();
		List<PaymentTool> paymentToolList = new ArrayList<PaymentTool>();
		
		for(int i=0;i<reportIndexList.size();i++){
			PlatformReportIndex pri = reportIndexList.get(i);
			Platform pf  = platformBiz.getPlatformById(pri.getPlatformId());
			if(pf != null){
				pf = new Platform();
				pf.setId(pri.getPlatformId());
				pf.setIdTranType(pf.getId()+":"+pri.getTranType());
				pf.setNameTranType(pri.getName()+"-"+pri.getTranTypeInfo());
				platformList.add(pf);
			}
			
			PaymentTool pt = paymentToolBiz.getPaymentToolByid(pri.getPaymenttoolId());
			if(pt != null){
				pt.setPlatformIReportIndexId(pri.getId());
				paymentToolList.add(pt);
			}else{
				reportIndexList.remove(pri);
				i--;
			}
			
		}
		request.setAttribute("platformList", platformList);
		request.setAttribute("paymentToolList", paymentToolList);
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

	/**
	 * 查看
	 * @throws AppException
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";

		ReportRecodeResultListForm rrrlf = (ReportRecodeResultListForm) form;
		ReportRecode totalReportRecode = new ReportRecode();
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
						.getReportRecodeByResultIndex(reportRecodeResult,rrrlf.getIndexId());
				totalReportRecode
					.setTotalInAmount(reportRecodeBiz.getMoneyByResultInex(reportRecodeResult, rrrlf.getIndexId()));
				
				request.setAttribute("reportRecodeList", reportRecodeList);
			}else{
				List<ReportRecode> reportRecodeList = reportRecodeBiz
						.getReportRecodeListByResultId(id);
				totalReportRecode
					.setTotalInAmount(reportRecodeBiz.getMoneyByResult(reportRecodeResult));
				request.setAttribute("reportRecodeList", reportRecodeList);
			}
			request.setAttribute("totalReportRecode", totalReportRecode);
			request.setAttribute("reportRecodeResult", reportRecodeResult);

		}
		forwardPage = "viewReportRecodeResult";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 分页列表
	 * @throws AppException
	 */
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

	public void setPlatformBiz(PlatformBiz platformBiz) {
		this.platformBiz = platformBiz;
	}

	public void setPaymentToolBiz(PaymentToolBiz paymentToolBiz) {
		this.paymentToolBiz = paymentToolBiz;
	}
	

}
