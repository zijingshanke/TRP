package com.fdays.tsms.airticket.util;

import java.sql.Timestamp;
import javax.servlet.http.HttpServletRequest;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.biz.AirticketOrderBizImp;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.dao.TicketLogDAO;
import com.fdays.tsms.user.SysUser;
import com.neza.exception.AppException;

/**
 * 监听
 * 
 * @author YanRui
 */
public class TicketLogListener implements Runnable {
	private AirticketOrder order;
	private SysUser sysUser;
	private HttpServletRequest request;
	private long ticketLogType;
	private String content;

	private TicketLogDAO ticketLogDAO;
	private LogUtil myLog;

	public TicketLogListener() {
	}
	
	public TicketLogListener(TicketLogDAO ticketLogDAO, AirticketOrder order,
			SysUser sysUser, HttpServletRequest request, long ticketLogType) {
		super();
		this.ticketLogDAO = ticketLogDAO;
		this.order = order;
		this.sysUser = sysUser;
		this.request = request;
		this.ticketLogType = ticketLogType;
	}

	public TicketLogListener(TicketLogDAO ticketLogDAO, AirticketOrder order,
			SysUser sysUser, HttpServletRequest request, long ticketLogType,String content) {
		super();
		this.ticketLogDAO = ticketLogDAO;
		this.order = order;
		this.sysUser = sysUser;
		this.request = request;
		this.ticketLogType = ticketLogType;
		this.content=content;
	}

	public void run() {
		try {
			myLog = new AirticketLogUtil(true, false,
					AirticketOrderBizImp.class, "");

			saveAirticketTicketLog(order, sysUser, request, ticketLogType,content);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("写入票务操作日志异常。。。" + e.getMessage());
		}
	}

	public void saveAirticketTicketLog(AirticketOrder order, SysUser sysUser,
			HttpServletRequest request, long ticketLogType,String Content) throws AppException {
		TicketLog ticketLog = new TicketLog();
		ticketLog.setOrderId(order.getId());
		ticketLog.setOrderNo(order.getGroupMarkNo());
		ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
		ticketLog.setSysUser(sysUser);// 操作员
		if (request != null) {
			ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
		}
		ticketLog.setContent(content);
		ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
		ticketLog.setType(ticketLogType);
		ticketLog.setStatus(1L);
		ticketLogDAO.save(ticketLog);

		myLog.info(sysUser.getUserName() + "-" + ticketLog.getTypeInfo()
				+ "--order no:" + order.getOrderNo());
	}

	public void setOrder(AirticketOrder order) {
		this.order = order;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setTicketLogType(long ticketLogType) {
		this.ticketLogType = ticketLogType;
	}

	public void setTicketLogDAO(TicketLogDAO ticketLogDAO) {
		this.ticketLogDAO = ticketLogDAO;
	}

	public void setMyLog(LogUtil myLog) {
		this.myLog = myLog;
	}

}