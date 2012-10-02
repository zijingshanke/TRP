package com.fdays.tsms.transaction.action;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.biz.AirticketOrderBiz;
import com.fdays.tsms.right.UserRightInfo;
import com.fdays.tsms.system.TicketLog;
import com.fdays.tsms.system.biz.TicketLogBiz;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.biz.StatementBiz;
import com.neza.base.BaseAction;
import com.neza.base.Inform;
import com.neza.exception.AppException;

public class StatementAction extends BaseAction{

	StatementBiz statementBiz;//结算表
	AirticketOrderBiz airticketOrderBiz;//机票订单表
	TicketLogBiz ticketLogBiz; //操作日志
	
	
	public TicketLogBiz getTicketLogBiz() {
		return ticketLogBiz;
	}


	public void setTicketLogBiz(TicketLogBiz ticketLogBiz) {
		this.ticketLogBiz = ticketLogBiz;
	}


	//修改未结款,实收款
	public ActionForward updatePlatform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws AppException {
		Statement statement = (Statement)form;
		BigDecimal actualAmount1=statement.getActualAmount();
		Inform inf = new Inform();
		try {
			if(statement.getStatementId()>0 && statement.getAirticketOrderId()>0)
			{
				statement.setId(statement.getStatementId());
				Statement sment = statementBiz.getStatementById(statement.getId());//根据ID查询结算表
				AirticketOrder airticketOrder =airticketOrderBiz.getAirticketOrderById(statement.getAirticketOrderId());//根据ID查询机票订单表
				sment.setStatementNo(sment.getStatementNo());
				//sment.setFromAccountId(sment.getPlatComAccount().getId());
				sment.setFromAccountId(sment.getFromAccountId());
				sment.setToAccountId(sment.getToAccountId());//这地方需要修改
				sment.setOrderType(sment.getOrderType());
				sment.setTotalAmount(sment.getTotalAmount());
				if(actualAmount1 != null )
				{
					BigDecimal actualAmount2=sment.getActualAmount();//实收款(原数据)
					BigDecimal unsettledAccount2=sment.getUnsettledAccount();//未结金额(原数据)					
					BigDecimal actualAmount=actualAmount1.add(actualAmount2);//实收款=实收款(原)+收回金额
					BigDecimal totalAmount = sment.getTotalAmount();//总金额
					if(actualAmount.compareTo(totalAmount)==1 || actualAmount.compareTo(totalAmount)==0 ) //实收款>=总金额
					{
						BigDecimal unset =BigDecimal.valueOf(0);						
						sment.setUnsettledAccount(unset);//未结款金额为0
						airticketOrder.setStatus(AirticketOrder.STATUS_80);//交易结束(机票订单表)
						if(actualAmount.compareTo(totalAmount) == 1) //实收款>总金额
						{
							sment.setActualAmount(totalAmount);
						}else
						{
							sment.setActualAmount(actualAmount);
						}
						sment.setStatus(Statement.STATUS_1);//状态为已结算
						
					}else if(actualAmount.compareTo(totalAmount)==-1 )//实收款<=总金额
					{
						BigDecimal unsettledAccount =unsettledAccount2.subtract(actualAmount1);//未结金额=未结金额(原)-收回金额  (收回金额<=未结金额)
						sment.setActualAmount(actualAmount);
						sment.setUnsettledAccount(unsettledAccount);					
						sment.setStatus(Statement.STATUS_2);//状态为部分结算
					}else
					{
						sment.setActualAmount(sment.getActualAmount());
						sment.setUnsettledAccount(sment.getUnsettledAccount());						
						sment.setStatus(Statement.STATUS_0);//状态为未结算
					}
				}				
				sment.setCommission(sment.getCommission());
				sment.setRakeOff(sment.getRakeOff());	
				sment.setType(sment.getType());		
				airticketOrderBiz.update(airticketOrder);//(机票订单表)
				long flag=statementBiz.update(sment);//(结算表)
		
				if (flag > 0) {
					////////////////////////////////////////////////////////////////////////////////////
					//操作日志添加
					//////////////////////////////////////////////////////////////////////////////////
					try {
						TicketLog ticketLog = new TicketLog();
						ticketLog.setOrderNo(statement.getGroupMarkNo());
						ticketLog.setOrderType(TicketLog.ORDERTYPE_1);
						UserRightInfo uri= (UserRightInfo)request.getSession().getAttribute("URI");
						
						ticketLog.setSysUser(uri.getUser());//操作员
						ticketLog.setIp(request.getRemoteAddr());//IP					 
						ticketLog.setOptTime(new Timestamp(System.currentTimeMillis()));
						ticketLog.setType(TicketLog.TYPE_41);
						ticketLogBiz.saveTicketLog(ticketLog);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					
					inf.setMessage("您已经成功修改收回金额！");
					inf.setForwardPage("/airticket/listAirTicketOrder.do?thisAction=listWaitRecoveryTicketOrders");
					inf.setParamId("thisAction");
					inf.setParamValue("list");
				}else{
					inf.setMessage("您改结算数据失败！");
					inf.setBack(true);
				}
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			inf.setBack(true);
		}
		
		request.setAttribute("inf", inf);
		String	forwardPage = "inform";
		return (mapping.findForward(forwardPage));
	}


	public StatementBiz getStatementBiz() {
		return statementBiz;
	}


	public void setStatementBiz(StatementBiz statementBiz) {
		this.statementBiz = statementBiz;
	}


	public AirticketOrderBiz getAirticketOrderBiz() {
		return airticketOrderBiz;
	}


	public void setAirticketOrderBiz(AirticketOrderBiz airticketOrderBiz) {
		this.airticketOrderBiz = airticketOrderBiz;
	}

}