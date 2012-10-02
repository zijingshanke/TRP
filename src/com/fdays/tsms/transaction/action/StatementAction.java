package com.fdays.tsms.transaction.action;

import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.biz.StatementBiz;
import com.neza.base.BaseAction;

public class StatementAction extends BaseAction {
	private StatementBiz statementBiz;// 结算表
	private AirticketOrderBiz airticketOrderBiz;// 机票订单表
	private TicketLogBiz ticketLogBiz; // 操作日志


	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}

	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}
	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}

}