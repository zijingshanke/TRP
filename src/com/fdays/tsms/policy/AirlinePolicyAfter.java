package com.fdays.tsms.policy;

import java.math.BigDecimal;

import com.fdays.tsms.policy._entity._AirlinePolicyAfter;

public class AirlinePolicyAfter extends _AirlinePolicyAfter{
  	private static final long serialVersionUID = 1L;


    public java.math.BigDecimal getQuota() {
    	if(this.quota == null){
    		this.quota = BigDecimal.ZERO;
    	}
        return this.quota;
    }
    
    public void setQuota(java.math.BigDecimal quota) {
    	if(quota == null){
    		quota = BigDecimal.ZERO;
    	}
        this.quota = quota;
    }
    

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
