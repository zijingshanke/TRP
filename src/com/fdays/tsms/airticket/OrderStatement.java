package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fdays.tsms.transaction.Account;

public class OrderStatement {     	
	private AirticketOrder order;

	public OrderStatement() {

	}
	
	public OrderStatement(AirticketOrder tempOrder,Account inAccount,Account outAccount,
			Account inRefundAccount,Account outRefundAccount,BigDecimal inAmount,
			BigDecimal outAmount, BigDecimal inRefundAmount,
			BigDecimal outRefundAmount, java.util.Date inTime, java.util.Date outTime,
			java.util.Date inRefundTime, java.util.Date outRefundTime) {
		this.order=tempOrder;	
		
		this.order.setInAccount(inAccount);
		this.order.setOutAccount(outAccount);
		this.order.setInRefundAccount(inRefundAccount);
		this.order.setOutRefundAccount(outRefundAccount);	
		
		this.order.setInAmount(inAmount);
		this.order.setOutAmount(outAmount);
		this.order.setInRefundAmount(inRefundAmount);
		this.order.setOutRefundAmount(outRefundAmount);
		
		Timestamp tempinTime=null;
		Timestamp tempoutTime=null;
		Timestamp tempinRefundTime=null;
		Timestamp tempoutRefundTime=null;
		
		if(inTime!=null){
			tempinTime =  new java.sql.Timestamp(inTime.getTime());
		}
		if(outTime!=null){
			tempoutTime =  new java.sql.Timestamp(outTime.getTime());
		}
		if(inRefundTime!=null){
			tempinRefundTime =  new java.sql.Timestamp(inRefundTime.getTime());
		}
		if(outRefundTime!=null){
			tempoutRefundTime =  new java.sql.Timestamp(outRefundTime.getTime());
		}
		this.order.setInTime(tempinTime);
		this.order.setOutTime(tempoutTime);
		this.order.setInRefundTime(tempinRefundTime);
		this.order.setOutRefundTime(tempoutRefundTime);
	}

	public void setOrder(AirticketOrder order) {
		this.order = order;
	}
	public AirticketOrder getOrder() {
		return order;
	}
}
