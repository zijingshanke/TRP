package com.fdays.tsms.transaction;


import com.fdays.tsms.transaction._entity._PlatComAccount;

public class PlatComAccount extends _PlatComAccount{
  	private static final long serialVersionUID = 1L;
  	
  	private long accountId;//支付账号ID
  	private long companyId;//公司ID
  	private long platformId;//交易平台ID
  	private long paymentToolId;//支付工具ID
  	
  	
	public long getPaymentToolId() {
		return paymentToolId;
	}
	public void setPaymentToolId(long paymentToolId) {
		this.paymentToolId = paymentToolId;
	}
	public long getAccountId() {
		return accountId;
	}
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	//状态
  	public static final long STATES_0=0;//有效
	public static final long STATES_1=1;//无效
	
	//状态
	public String getStatusInfo() {
		if(this.getStatus()!=null)
		{
			if(this.getStatus() == STATES_0)
			{
				return "有效";
			}else if(this.getStatus().intValue() == STATES_1)
			{
				return "无效";
			}else
			{
				return null;
			}
		}else
		{
			return null;
		}
	}
	
	public static final long TYPE_1=1;//1:卖出收款
	public static final long TYPE_2=2;//2:买入付款
	public static final long TYPE_3=3;//3:收付
	
	public String getTypeInfo()
	{
		if(this.getType() != null)
		{
			if(this.getType() == TYPE_1)
			{
				return "卖出收款";
			}else if(this.getType() == TYPE_2)
			{
				return "买入付款";
			}else if(this.getType() == TYPE_3)
			{
				return "收付";
			}else
			{
				return null;
			}
		}else
		{
			return null;
		}
	}
	
}
