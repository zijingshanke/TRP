package com.fdays.tsms.airticket;

import com.neza.base.ListActionForm;

public class AirticketOrderListForm extends ListActionForm
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id = new Long(0);
	public String userNo;
	public String drawPnr;// 出票PNR
	public String subPnr;// 预定PNR
	public String bigPnr;// 大PNR
	public String pnr;// 大PNR
	public String groupMarkNo;// 关联订单号
	public String orderNo;// 订单号
	public String flightCode="";// 航班号
	public String ticketNumber;// 票号
	public String agentNo;// 购票客户
	public String drawer;// 出票人
	public String sysName;// 操作人
	public String agentName;// 乘客
	public long ticketType = Long.valueOf(0);// 机票类型
	public String downloadDate;
	public long teamStatus;
	public long airticketOrder_status = Long.valueOf(0);// 订单状态
	public long b2C_status;// B2C订单状态
	private long platformId;// 平台id
	private String moreStatus;// 多个状态
	private String filtrateStatus;// 过滤的状态

	private long tranType;// 交易类型
	private String moreTranType;// 多个交易类型
	private String cyr="";// 承运人
	private String startDate = "";// 开始时间
	private String endDate = "";// 结束时间
	private String startPoint = "";// 开始时间
	private String endPoint = "";// 结束时间
	private long fromPlatformId;// 买入
	private long toPlatformId;// 卖出
	private long fromAccountId = new Long(0);// 付款
	private long toAccountId = new Long(0);// 收款
	private Long recentlyDay;// 是否查询最近
	
	private long groupId;
	private long groupCount = new Long(0);
	public long scrap_status;// 过滤废弃的订单
	private Long orderBy ;
	private Long drawType;
	private String orderType = "";	
	private String forwardPage = "";
	private String forwardPageFlag = "";

	// 团队专用
	public long team_status;// 团队状态
	public long teamTicket_type;// 机票类型
	public long teamTran_type;// 团队机票类型

	// 团队销售报表
	private long proxy_price;// 出团代理费(未返)

	private String platformIds;
	private String accountIds;

	public String getCyr()
	{
		return cyr;
	}

	public void setCyr(String cyr)
	{
		this.cyr = cyr;
	}

	public long getTranType()
	{
		return tranType;
	}

	public void setTranType(long tranType)
	{
		this.tranType = tranType;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFiltrateStatus()
	{
		return filtrateStatus;
	}

	public void setFiltrateStatus(String filtrateStatus)
	{
		this.filtrateStatus = filtrateStatus;
	}

	public String getUserNo()
	{
		return userNo;
	}

	public long getPlatformId()
	{
		return platformId;
	}

	public void setPlatformId(long platformId)
	{
		this.platformId = platformId;
	}

	public void setUserNo(String userNo)
	{
		this.userNo = userNo;
	}

	public String getDrawPnr()
	{
		return drawPnr;
	}

	public void setDrawPnr(String drawPnr)
	{
		this.drawPnr = drawPnr;
	}

	public String getSubPnr()
	{
		return subPnr;
	}

	public void setSubPnr(String subPnr)
	{
		this.subPnr = subPnr;
	}

	public String getBigPnr()
	{
		return bigPnr;
	}

	public String getForwardPage()
	{
		return forwardPage;
	}

	public void setForwardPage(String forwardPage)
	{
		this.forwardPage = forwardPage;
	}

	public void setBigPnr(String bigPnr)
	{
		this.bigPnr = bigPnr;
	}

	public String getPnr()
	{
		if(pnr!=null)
			pnr=pnr.toUpperCase();
		return pnr;
	}

	public void setPnr(String pnr)
	{
		this.pnr = pnr;
	}

	public String getGroupMarkNo()
	{
		return groupMarkNo;
	}

	public void setGroupMarkNo(String groupMarkNo)
	{
		this.groupMarkNo = groupMarkNo;
	}

	public String getFlightCode()
	{
		return flightCode;
	}

	public void setFlightCode(String flightCode)
	{
		this.flightCode = flightCode;
	}

	public String getTicketNumber()
	{
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber)
	{
		this.ticketNumber = ticketNumber;
	}

	public String getSysName()
	{
		return sysName;
	}

	public void setSysName(String sysName)
	{
		this.sysName = sysName;
	}

	public String getAgentName()
	{
		return agentName;
	}

	public void setAgentName(String agentName)
	{
		this.agentName = agentName;
	}

	public long getAirticketOrder_status()
	{
		return airticketOrder_status;
	}

	public void setAirticketOrder_status(long airticketOrder_status)
	{
		this.airticketOrder_status = airticketOrder_status;
	}

 

	public String getStartPoint()
  {
  	return startPoint;
  }

	public void setStartPoint(String startPoint)
  {
  	this.startPoint = startPoint;
  }

	public String getEndPoint()
  {
  	return endPoint;
  }

	public void setEndPoint(String endPoint)
  {
  	this.endPoint = endPoint;
  }

	public String getOrderNo()
  {
  	return orderNo;
  }

	public void setOrderNo(String orderNo)
  {
  	this.orderNo = orderNo;
  }

	public long getTicketType()
	{
		return ticketType;
	}

	public void setTicketType(long ticketType)
	{
		this.ticketType = ticketType;
	}

	public String getDownloadDate()
	{
		return downloadDate;
	}

	public long getTeamStatus()
	{
		return teamStatus;
	}

	public void setDownloadDate(String downloadDate)
	{
		this.downloadDate = downloadDate;
	}

	public void setTeamStatus(long teamStatus)
	{
		this.teamStatus = teamStatus;
	}

	public String getMoreStatus()
	{
		return moreStatus;
	}

	public void setMoreStatus(String moreStatus)
	{
		this.moreStatus = moreStatus;
	}

	public long getB2C_status()
	{
		return b2C_status;
	}

	public void setB2C_status(long b2c_status)
	{
		b2C_status = b2c_status;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public long getFromAccountId()
	{
		return fromAccountId;
	}

	public void setFromAccountId(long fromAccountId)
	{
		this.fromAccountId = fromAccountId;
	}

	public long getToAccountId()
	{
		return toAccountId;
	}

	public void setToAccountId(long toAccountId)
	{
		this.toAccountId = toAccountId;
	}

	public String getPlatformIds()
	{
		return platformIds;
	}

	public void setPlatformIds(String platformIds)
	{
		this.platformIds = platformIds;
	}

	public String getAccountIds()
	{
		return accountIds;
	}

	public void setAccountIds(String accountIds)
	{
		this.accountIds = accountIds;
	}

	public long getProxy_price()
	{
		return proxy_price;
	}

	public void setProxy_price(long proxy_price)
	{
		this.proxy_price = proxy_price;
	}

	public long getTeam_status()
	{
		return team_status;
	}

	public void setTeam_status(long team_status)
	{
		this.team_status = team_status;
	}

	public long getTeamTicket_type()
	{
		return teamTicket_type;
	}

	public void setTeamTicket_type(long teamTicket_type)
	{
		this.teamTicket_type = teamTicket_type;
	}

	public long getScrap_status()
	{
		return scrap_status;
	}

	public void setScrap_status(long scrap_status)
	{
		this.scrap_status = scrap_status;
	}

	public long getTeamTran_type()
	{
		return teamTran_type;
	}

	public void setTeamTran_type(long teamTran_type)
	{
		this.teamTran_type = teamTran_type;
	}

	public long getFromPlatformId()
	{
		return fromPlatformId;
	}

	public void setFromPlatformId(long fromPlatformId)
	{
		this.fromPlatformId = fromPlatformId;
	}

	public long getToPlatformId()
	{
		return toPlatformId;
	}

	public void setToPlatformId(long toPlatformId)
	{
		this.toPlatformId = toPlatformId;
	}

 

	public String getMoreTranType()
	{

		if (this.orderType != null)
		{
			if (this.orderType.equals("91"))
			{// 正常订单
				moreTranType = "1,2";
			}
			else if (this.orderType.equals("92"))
			{// 改签订单
				moreTranType = "5";
			}
			else if (this.orderType.equals("93"))
			{// 退废订单
				moreTranType = "3,4";
			}
			else if (this.orderType.equals("97"))
			{// 团队正常订单
				moreTranType = "1,2";
			}
			else if (this.orderType.equals("98"))
			{// 团队退废订单
				moreTranType = "3";
			}
		}

		return moreTranType;
	}

	public String getOrderType()
	{
		return orderType;
	}

	public void setOrderType(String orderType)
	{
		this.orderType = orderType;
	}

	public String getDrawer()
	{
		return drawer;
	}

	public void setDrawer(String drawer)
	{
		this.drawer = drawer;
	}

	public String getAgentNo()
	{
		return agentNo;
	}

	public void setAgentNo(String agentNo)
	{
		this.agentNo = agentNo;
	}

	public long getGroupId()
	{
		return groupId;
	}

	public void setGroupId(long groupId)
	{
		this.groupId = groupId;
	}

	public long getGroupCount()
	{
		return groupCount;
	}

	public void setGroupCount(long groupCount)
	{
		this.groupCount = groupCount;
	}

 
 

	public Long getOrderBy()
  {
  	return orderBy;
  }

	public void setOrderBy(Long orderBy)
  {
  	this.orderBy = orderBy;
  }

	public String getForwardPageFlag()
	{
		return forwardPageFlag;
	}

	public void setForwardPageFlag(String forwardPageFlag)
	{
		this.forwardPageFlag = forwardPageFlag;
	}

	public Long getDrawType()
  {
  	return drawType;
  }

	public void setDrawType(Long drawType)
  {
  	this.drawType = drawType;
  }

	public Long getRecentlyDay()
  {
	 
  	return recentlyDay;
  }

	public void setRecentlyDay(Long recentlyDay)
  {
  	this.recentlyDay = recentlyDay;
  }

 

 


}