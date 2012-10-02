package com.fdays.tsms.airticket.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.StatisticsOrder;
import com.fdays.tsms.airticket.biz.StatisticsOrderBiz;
import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.biz.SaleStatisticsBiz;

import com.neza.base.BaseAction;
import com.neza.base.DownLoadFile;
import com.neza.base.Inform;
import com.neza.exception.AppException;
import com.neza.tool.DateUtil;
import com.neza.utility.FileUtil;

public class StatisticsOrderAction extends BaseAction {
	
	private StatisticsOrderBiz statisticsOrderBiz;
	private SaleStatisticsBiz saleStatisticsBiz;
	

	//更新表格数据操作
	public ActionForward insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		long start = System.currentTimeMillis();
		statisticsOrderBiz.deleteAll();					//将临时表原有信息删除
		StatisticsOrder so = (StatisticsOrder) form;
		long saleStatisticsId = so.getSaleStatisticsId();
		
		if (saleStatisticsId > 0){
			SaleStatistics ss = saleStatisticsBiz.getSaleStatisticsById(saleStatisticsId);
			System.out.println(ss);
			List<StatisticsOrder> soList = statisticsOrderBiz.createStatistics(ss);
			statisticsOrderBiz.batchSaveOrUpdate(soList);									//批量插入数据
		}
		long end = System.currentTimeMillis();
		System.out.println("所用时间:"+(end-start));
		Inform inf = new Inform();
		inf.setMessage("成功更新表格数据！");
		inf.setForwardPage("/airticket/listStatisticsOrder.do?thisAction=listStatisticsOrder" +
				"&saleStatisticsId=" + saleStatisticsId);
		inf.setClose(true);
		request.setAttribute("inf", inf);
		
		return (mapping.findForward("inform"));
	}
	
	//查看详情
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
		long start = System.currentTimeMillis();
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
		content.add("订单金额");
		content.add("利润");
		content.add("后返政策");
		content.add("后返利润");
		content.add("tranType");
		content.add("groupId");
		statisticsOrderList.add(content);
		if (saleStatisticsId > 0){
			SaleStatistics ss = saleStatisticsBiz.getSaleStatisticsById(saleStatisticsId);
			List<StatisticsOrder> soList = statisticsOrderBiz.createStatistics(ss);
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
				content.add(0);
				content.add(so.getProfitAfter());
				content.add(so.getTranType());
				content.add(so.getGroupId());
				statisticsOrderList.add(content);
			}
			String outText = FileUtil.createCSVFile(statisticsOrderList);		//CSV格式的输出内容
			try
			{
				outText = new String(outText.getBytes("GBK"));
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			DownLoadFile df = new DownLoadFile();
			df.performTask(response, outText, outFileName, "GBK");
			long end = System.currentTimeMillis();
			System.out.println("所用时间:"+(end-start));
			return null;
		}else{
			request.getSession().invalidate();
			return mapping.findForward("exit");
		}
		
	}

	//----------------------------set get-------------------------//

	public void setSaleStatisticsBiz(SaleStatisticsBiz saleStatisticsBiz) {
		this.saleStatisticsBiz = saleStatisticsBiz;
	}

	public void setStatisticsOrderBiz(StatisticsOrderBiz statisticsOrderBiz) {
		this.statisticsOrderBiz = statisticsOrderBiz;
	}
	

}