package com.fdays.tsms.transaction;


import com.fdays.tsms.transaction._entity._Company;

public class Company extends _Company{
  	private static final long serialVersionUID = 1L;
  	

  //类型
  	public static final long type_1=1;//集团下属
  	public static final long type_2=2;//客户公司

  	public String getTypeInfo()
  	{
  		if(this.getType()!=null)
  		{
  			if(this.getType().intValue() == type_1)
  			{
  				return "集团下属";
  			}else if(this.getType().intValue() == type_2)
  			{
  				return "客户公司";
  			}else{
  				return null;
  			}
  		}else
  		{
  			return null;
  		}
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
}
