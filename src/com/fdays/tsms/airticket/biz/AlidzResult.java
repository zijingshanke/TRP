package com.fdays.tsms.airticket.biz;


/**
 * 返回Alidz业务结果信息
 * @author chenqx
 * date	2010-12-3
 */
public class AlidzResult {
	
	private int status ;			//操作状态(0操作失败,1操作成功)
	private String message ;		//返回信息
	//---------------------查询订单状态-------------------------//
	private int payStatus;			//支付状态(0未支付,1已支付,2未知(用于查询操作失败))
	private int orderstatus;		//出票状态(0未出票,1出票成功,7取消入库,9其他状态)
	//---------------------自动支付---------------------------//
	private int isPaySuccess;		//支付是否成功(0支付失败,1支付成功)
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public int getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(int orderstatus) {
		this.orderstatus = orderstatus;
	}
	public int getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	public int getIsPaySuccess() {
		return isPaySuccess;
	}
	public void setIsPaySuccess(int isPaySuccess) {
		this.isPaySuccess = isPaySuccess;
	}

}
