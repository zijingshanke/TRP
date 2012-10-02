package com.fdays.tsms.policy;


import com.fdays.tsms.policy._entity._AirlinePolicyAfter;

/**
 * AirlinePolicyAfter的ActionForm
 * @author Administrator
 * date 2010-12-7
 */
public class AirlinePolicyAfter extends _AirlinePolicyAfter{
  	private static final long serialVersionUID = 1L;
  	
  	public String getStatusInfo() {
    	if(this.status == null || this.status==0){
    		return "无";
    	}
    	if(this.status==1){
    		return "启用";
    	}
		 return "停用";
    }
  	
}
