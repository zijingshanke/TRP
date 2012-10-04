package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.biz.ReportRecodeBiz;
import com.fdays.tsms.transaction.biz.ReportRecodeResultBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;

public class ReportRecodeResultAction extends BaseAction
{
	private ReportRecodeResultBiz reportRecodeResultBiz;
	private ReportRecodeBiz reportRecodeBiz;

	public ActionForward update(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String forwardPage = "";
		ReportRecodeResult reportRecodeResult = (ReportRecodeResult) form;
		Inform inf = new Inform();
		try
		{
			long resultId = Constant.toLong(reportRecodeResult.getId());
			if (resultId > 0)
			{
				ReportRecodeResult tempResult = reportRecodeResultBiz
				    .getReportRecodeResultById(resultId);
				tempResult.setName(reportRecodeResult.getName());
				tempResult.setBeginDate(reportRecodeResult.getBeginDate());
				tempResult.setEndDate(reportRecodeResult.getEndDate());
				tempResult.setRecodeSet(reportRecodeResult.getRecodeSet());
				tempResult.setMemo(reportRecodeResult.getMemo());
				tempResult.setUserNo(uri.getUser().getUserNo());
				tempResult.setReportType(reportRecodeResult.getReportType());
				tempResult.setLastDate(new Timestamp(System.currentTimeMillis()));
				tempResult.setStatus(reportRecodeResult.getStatus());
				reportRecodeResultBiz.update(tempResult);

				inf.setMessage("您已经成功更新了结果!");
				inf.setForwardPage("/transaction/reportRecodeResultList.do");
				inf.setParamId("thisAction");
				inf.setParamValue("list");
			}
			else
			{
				inf.setMessage("reportRecodeResultId 不存在");
				inf.setBack(true);
			}

		}
		catch (Exception ex)
		{
			inf.setMessage("更新出错！错误信息是：" + ex.getMessage());
			inf.setBack(true);
		}

		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 新增ReprotRecodeResult
	 * 
	 * @throws AppException
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String forwardPage = "";
		ReportRecodeResult reportRecodeResult = (ReportRecodeResult) form;
		Inform inf = new Inform();

		Timestamp reportDate = reportRecodeResult.getReportDate();
		String reportDateStr = DateUtil.getDateString(reportDate, "yyyy-MM-dd");
		String name = reportDateStr + reportRecodeResult.getReportTypeInfo() + "报表";
		ReportRecodeResult temp = reportRecodeResultBiz
		    .getReportRecodeResultByDateType(reportDate, reportRecodeResult
		        .getReportType());
		if (temp != null)
		{
			inf.setMessage("此日期和类型的报表已经存在，请继续导入!");
			inf
			    .setForwardPage("/transaction/reportRecodeResultList.do?thisAction=addReportRecodeContinue&id="
			        + temp.getId());

			request.setAttribute("inf", inf);
		}
		else
		{
			try
			{
				ReportRecodeResult tempResult = new ReportRecodeResult();
				tempResult.setName(name);
				tempResult.setBeginDate(DateUtil.getTimestamp(reportDateStr
				    + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				tempResult.setEndDate(DateUtil.getTimestamp(
				    reportDateStr + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
				tempResult.setMemo(reportRecodeResult.getMemo());
				tempResult.setUserNo(uri.getUser().getUserNo());
				tempResult.setReportType(reportRecodeResult.getReportType());
				tempResult.setLastDate(new Timestamp(System.currentTimeMillis()));
				tempResult.setTranType(reportRecodeResult.getTranType());
				tempResult.setStatus(reportRecodeResult.getStatus());
				reportRecodeResultBiz.save(tempResult);

				inf.setMessage("增加成功!");
				inf
				    .setForwardPage("/transaction/reportRecodeResultList.do?thisAction=list");
			}
			catch (Exception ex)
			{
				inf.setMessage("增加出错！错误信息是：" + ex.getMessage());
				ex.printStackTrace();
				inf.setBack(true);
			}
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	/**
	 * 继续导入ReportRecode(注：ReportRecode都是从excel文件中导入的)
	 * 
	 * @throws AppException
	 */
	public ActionForward insertContinue(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		String forwardPage = "";
		ReportRecodeResult reportRecodeResult = (ReportRecodeResult) form;
		Inform inf = new Inform();
		try
		{
			long resultId = reportRecodeResult.getId();
			if (resultId > 0)
			{
				ReportRecodeResult newRecodeResult = reportRecodeResultBiz
				    .getReportRecodeResultById(resultId);
				String idTranType = reportRecodeResult.getIdTranType();
				if(idTranType != null &&idTranType.contains(":")){
					newRecodeResult.setPlatformId(Long.parseLong(idTranType.substring(0,idTranType.indexOf(":"))));
					newRecodeResult.setTranType(Long.parseLong(idTranType.substring(idTranType.indexOf(":")+1)));
				}
				newRecodeResult.setReportIndexId(reportRecodeResult.getReportIndexId());
				newRecodeResult.setFileName(reportRecodeResult.getFileName());// 129802011021946.xls
				String result = reportRecodeBiz.saveRecodeByResult(newRecodeResult,request);
				inf.setMessage(result);
				inf.setForwardPage("/transaction/reportRecodeResultList.do?thisAction=addReportRecodeContinue&id="+ resultId);
			}
			else{
				inf.setMessage("resultId不能为空");
				inf.setBack(true);
			}
		}
		catch (Exception ex){
			inf.setMessage("增加出错！错误信息是：" + ex.getMessage());
			ex.printStackTrace();
			inf.setBack(true);
		}
		request.setAttribute("inf", inf);
		forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}

	public ActionRedirect redirectViewResult(long resultId)
	{
		ActionRedirect redirect = new ActionRedirect(
		    "/transaction/reportRecodeResultList.do");
		redirect.addParameter("thisAction", "addReportRecodeContinue");
		redirect.addParameter("id", resultId);
		return redirect;
	}

	public void setReportRecodeResultBiz(
	    ReportRecodeResultBiz reportRecodeResultBiz)
	{
		this.reportRecodeResultBiz = reportRecodeResultBiz;
	}

	public void setReportRecodeBiz(ReportRecodeBiz reportRecodeBiz)
	{
		this.reportRecodeBiz = reportRecodeBiz;
	}

}