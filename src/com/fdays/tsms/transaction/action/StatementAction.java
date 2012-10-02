package com.fdays.tsms.transaction.action;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.biz.AccountBiz;
import com.fdays.tsms.transaction.biz.StatementBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class StatementAction extends BaseAction
{
	private StatementBiz statementBiz;
	private AccountBiz accountBiz;
	private TicketLogBiz ticketLogBiz;

	public void setStatementBiz(StatementBiz statementBiz)
	{
		this.statementBiz = statementBiz;
	}

	public void setTicketLogBiz(TicketLogBiz ticketLogBiz)
	{
		this.ticketLogBiz = ticketLogBiz;
	}

	public void setAccountBiz(AccountBiz accountBiz)
	{
		this.accountBiz = accountBiz;
	}

	public ActionForward confirmStatement(ActionMapping mapping, ActionForm form,
	    HttpServletRequest request, HttpServletResponse response)
	    throws AppException
	{
		String temp="";
		UserRightInfo uri = (UserRightInfo) request.getSession()
		    .getAttribute("URI");
		Statement statement = (Statement) form;
		TicketLog ticketLog = new TicketLog();
		Inform inf = new Inform();
		if (statement.getId() > 0 && uri.hasRight("sb50"))
		{
			Statement tempStatement = statementBiz
			    .getStatementById(statement.getId());

			tempStatement.setStatementDate(statement.getStatementDate());
			tempStatement.setSysUser(uri.getUser());
			tempStatement.setMemo(statement.getMemo());// 要不要修改订单表?
			if (statement.getStatus() == Statement.STATUS_8)
			{
				temp="已经确认了";
			}
			else
			{
				temp="修改了";
			}
				
				
			if (tempStatement.getType() == Statement.type_2) // 支出
			{
				tempStatement.setFromAccount(accountBiz.getAccountById(statement
				    .getFromAccountId()));
				ticketLog.setContent(temp + tempStatement.toLogString());
			}
			else if (tempStatement.getType() == Statement.type_1)
			{
				tempStatement.setToAccount(accountBiz.getAccountById(statement
				    .getToAccountId()));
				ticketLog.setContent(temp + tempStatement.toLogString());
			}
			tempStatement.setTotalAmount(statement.getTotalAmount());
			if (tempStatement.getStatus() != Statement.STATUS_8)
			{
				tempStatement.setStatus(statement.getStatus());
				statementBiz.update(tempStatement);

				ticketLog.setOrderId(tempStatement.getOrderId());
				ticketLog.setOrderNo(tempStatement.getStatementNo());
				ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
				ticketLog.setSysUser(uri.getUser());// 操作员
				ticketLog.setIp(HttpInvoker.getRemoteIP(request));// IP
				ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
				ticketLog.setType(TicketLog.TYPE_99);
				ticketLog.setStatus(new Long(1));
				ticketLogBiz.saveTicketLog(ticketLog);
				
				inf.setMessage("修改成功！");
			}
			else
			{
				inf.setMessage("您不能修改已经确认的单！");
			}
			inf.setClose(true);
			inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=view&id="
			    + tempStatement.getOrderId());
		}
		else
		{
			inf.setMessage("系统有误，请联系管理员");
		}
		request.setAttribute("inf", inf);
		return mapping.findForward("inform");
	}

}