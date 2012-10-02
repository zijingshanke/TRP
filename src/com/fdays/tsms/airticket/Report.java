package com.fdays.tsms.airticket;

import java.util.ArrayList;
import java.util.List;

public class Report extends org.apache.struts.action.ActionForm implements
		Cloneable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long operatorDepart = new Long(0);
	private String operator = "";
	private String tranType = "";
	private String businessType = "";
	private String statusGroup = "";
	private String ticketTypeGroup = "";
	private String tranTypeGroup = "";
	private String businessTypeGroup = "";
	private String startDate = "";// 开始时间
	private String endDate = "";// 结束时间
	private String downloadDate = "";

	private String[] salePlatformIds;
	private String[] buyPlatformIds;
	private String[] receiveAccountIds;
	private String[] payAccountIds;

	private boolean isRakeOff = false;

	private Long reportType;
	public static Long ReportType1 = new Long(1);//散票销售报表<财务>
	public static Long ReportType2 = new Long(2);//散票销售报表<政策>	
	public static Long ReportType11 = new Long(11);//散票退废报表<财务>	
	public static Long ReportType51 = new Long(51);//团队统计报表<财务>
	public static Long ReportType52 = new Long(52);//团队未返报表<政策>
	
	private List<OptTransaction> optList=new ArrayList<OptTransaction>();//收付款统计
	private String[] optHead=new String[100];	
	

	public Report() {

	}
	
	

	private String thisAction = "";
	
	public Long getOperatorDepart() {
		return operatorDepart;
	}

	public void setOperatorDepart(Long operatorDepart) {
		this.operatorDepart = operatorDepart;
	}

	public List<OptTransaction> getOptList() {
		return optList;
	}

	public void setOptList(List<OptTransaction> optList) {
		this.optList = optList;
	}

	public Long getReportType() {
		return reportType;
	}

	public void setReportType(Long reportType) {
		this.reportType = reportType;
	}

	public boolean isRakeOff() {
		return isRakeOff;
	}

	public void setRakeOff(boolean isRakeOff) {
		this.isRakeOff = isRakeOff;
	}

	public String getThisAction() {
		return thisAction;
	}

	public String getTicketTypeGroup() {
		return ticketTypeGroup;
	}

	public void setTicketTypeGroup(String ticketTypeGroup) {
		this.ticketTypeGroup = ticketTypeGroup;
	}

	public String getTranTypeGroup() {
		return tranTypeGroup;
	}

	public void setTranTypeGroup(String tranTypeGroup) {
		this.tranTypeGroup = tranTypeGroup;
	}

	public String getBusinessTypeGroup() {
		return businessTypeGroup;
	}

	public void setBusinessTypeGroup(String businessTypeGroup) {
		this.businessTypeGroup = businessTypeGroup;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setThisAction(String thisAction) {
		this.thisAction = thisAction;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getStatusGroup() {
		return statusGroup;
	}

	public void setStatusGroup(String statusGroup) {
		this.statusGroup = statusGroup;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String[] getSalePlatformIds() {
		return salePlatformIds;
	}

	public void setSalePlatformIds(String[] salePlatformIds) {
		this.salePlatformIds = salePlatformIds;
	}

	public String[] getBuyPlatformIds() {
		return buyPlatformIds;
	}

	public void setBuyPlatformIds(String[] buyPlatformIds) {
		this.buyPlatformIds = buyPlatformIds;
	}

	public String[] getReceiveAccountIds() {
		return receiveAccountIds;
	}

	public void setReceiveAccountIds(String[] receiveAccountIds) {
		this.receiveAccountIds = receiveAccountIds;
	}

	public String[] getPayAccountIds() {
		return payAccountIds;
	}

	public void setPayAccountIds(String[] payAccountIds) {
		this.payAccountIds = payAccountIds;
	}

	public String getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(String downloadDate) {
		this.downloadDate = downloadDate;
	}

	public String[] getOptHead() {
		return optHead;
	}

	public void setOptHead(String[] optHead) {
		this.optHead = optHead;
	}
	
	

}
