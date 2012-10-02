package com.fdays.tsms.airticket.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.StatisticsOrder;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.airticket.biz.StatisticsOrderBiz;
import com.fdays.tsms.airticket.biz.StatisticsOrderBizImp;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.biz.SaleStatisticsBiz;

import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class StatisticsOrderAction extends BaseAction {
	private final int ROWCOUNT = 1000;				//更新表格时批量插入每批操作数量
	private StatisticsOrderBiz statisticsOrderBiz;
	private SaleStatisticsBiz saleStatisticsBiz;
	private AirticketOrderBiz airticketOrderBiz;
	private static int totalRowInsert = 1;			//记录更新表格时所需插入数据的总记录数,用于显示进度条
	private static int totalRowDownload = 1;		//记录下载数据时的总数据数
	private static int currentRowDownload = 0;		//记录下载数据时的当前行数

	/**
	 * 更新表格数据操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		int current = 0;
		boolean flag = true;
		statisticsOrderBiz.deleteAll();					//将临时表原有信息删除
		StatisticsOrder so = (StatisticsOrder) form;
		long saleStatisticsId = so.getSaleStatisticsId();
		if (saleStatisticsId > 0){
			SaleStatistics ss = saleStatisticsBiz.getSaleStatisticsById(saleStatisticsId);
			totalRowInsert = airticketOrderBiz.getRowCountByCarrier(ss.getCarrier(), ss.getBeginDate(),ss.getEndDate());
			List<StatisticsOrder> soList;
			while(flag){
				soList = statisticsOrderBiz.createStatistics(ss,current,ROWCOUNT);
				statisticsOrderBiz.batchSaveOrUpdate(soList);									//批量插入数据
				if(soList.size()>0){
					current = current+ROWCOUNT;
				}else{
					flag = false;
				}
			}
		}
		Inform inf = new Inform();
		inf.setMessage("成功更新表格数据！");
		inf.setForwardPage("/airticket/listStatisticsOrder.do?thisAction=listStatisticsOrder" +
				"&saleStatisticsId=" + saleStatisticsId);
		request.setAttribute("inf", inf);
		resetProgressBarData();					//重置进度条数据
		return (mapping.findForward("inform"));
	}
	
	/**
	 * 查看详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		String forwardPage = "";
		StatisticsOrder statisticsOrder = (StatisticsOrder) form;
		long id = statisticsOrder.getId();
		if (id > 0) {
			StatisticsOrder so = (StatisticsOrder) statisticsOrderBiz.getStatisticsOrderById(id);
			so.setThisAction("view");
			request.setAttribute("statisticsOrder", so);
		}
		forwardPage = "viewStatisticsOrder";
		return (mapping.findForward(forwardPage));
	}
	
	/**
	 * 下载数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
//		long start = System.currentTimeMillis();
		String outFileName = DateUtil.getDateString("yyyyMMddHHmmss") + ".csv";//文件名
		ArrayList<ArrayList<Object>> statisticsOrderList = new ArrayList<ArrayList<Object>>();
		StatisticsOrder statisticsOrder = (StatisticsOrder) form;
		long saleStatisticsId = statisticsOrder.getSaleStatisticsId();
		ArrayList<Object> content = new ArrayList<Object>();
		content.add("流水号");
		content.add("航班号");
		content.add("起止城市");
		content.add("起飞时间");
		content.add("乘客姓名");
		content.add("票号");
		content.add("票面价");
		content.add("利润");
		content.add("后返政策");
		content.add("后返利润");
		content.add("tranType");
		content.add("groupId");
		statisticsOrderList.add(content);
		if (saleStatisticsId > 0){
			SaleStatistics ss = saleStatisticsBiz.getSaleStatisticsById(saleStatisticsId);
			List<StatisticsOrder> soList = statisticsOrderBiz.listBySaleStatistics(ss);
			totalRowDownload = soList.size();
			for(StatisticsOrder so : soList){
				content = new ArrayList<Object>();
				content.add(so.getOrderNo());
				content.add(so.getFlightCode());
				content.add(so.getStartEnd());
				content.add(so.getBoardingTime());
				content.add(so.getPassengerName());
				content.add(so.getTicketNumber());
				content.add(so.getTotalAmount());
				content.add(so.getProfit());
				content.add(so.getRate());
				content.add(so.getProfitAfter());
				content.add(so.getTranType());
				content.add(so.getGroupId());
				statisticsOrderList.add(content);
				currentRowDownload = statisticsOrderList.size()-1;
			}
			String outText = FileUtil.createCSVFile(statisticsOrderList);		//CSV格式的输出内容
			try
			{
				outText = new String(outText.getBytes("UTF-8"));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK"); //UTF-8
			long end = System.currentTimeMillis();
//			System.out.println("所用时间:"+(end-start));
			return null;
		}else{
			request.getSession().invalidate();
			return mapping.findForward("exit");
		}
	}
	
	/**
	 * ajax用于下载数据时获取进度条参数数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward showDownloadProgressBar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		StatisticsOrder so = (StatisticsOrder) form;
//		System.out.println("showDownloadProgressBar:"+totalRowDownload+"----"+currentRowDownload+"---"+so.getThisAction());
		createResponse(response,totalRowDownload,currentRowDownload,so.getThisAction());
		return null;
	}
	
	/**
	 * ajax用于更新表格时获取进度条参数数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward showInsertProgressBar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		StatisticsOrder so = (StatisticsOrder) form;
//		System.out.println("showInsertProgressBar:"+totalRowInsert+"---"+StatisticsOrderBizImp.currentCount+"----"+so.getThisAction());
		createResponse(response,totalRowInsert,StatisticsOrderBizImp.currentCount,so.getThisAction());
		return null;
	}
	/**
	 * 操作完成后处理
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws AppException
	 */
	public ActionForward comple(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		resetProgressBarData();
		return null;
	}
	
	/**
	 * 创建ajax的Response
	 * @param response
	 * @param total
	 * @param current
	 * @param action
	 */
	private void createResponse(HttpServletResponse response, int total,int current,String action){
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jObject = new JSONObject();
		jObject.put("total",total);
		jObject.put("current",current);
		jObject.put("action",action);
		out.println(jObject);
		out.close();
	} 
	
	/**
	 * 重置进度条参数变量
	 */
	private void resetProgressBarData(){
		totalRowInsert = 1;
		totalRowDownload = 1;
		currentRowDownload = 0;
		StatisticsOrderBizImp.currentCount = 0;
	}
	
	
	//----------------------------set get-------------------------//

	public void setSaleStatisticsBiz(SaleStatisticsBiz saleStatisticsBiz) {
		this.saleStatisticsBiz = saleStatisticsBiz;
	}

	public void setStatisticsOrderBiz(StatisticsOrderBiz statisticsOrderBiz) {
		this.statisticsOrderBiz = statisticsOrderBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}
	

}