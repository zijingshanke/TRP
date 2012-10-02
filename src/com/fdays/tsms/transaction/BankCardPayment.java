package com.fdays.tsms.transaction;


import java.math.BigDecimal;

import com.fdays.tsms.transaction._entity._BankCardPayment;

public class BankCardPayment extends _BankCardPayment{
  	private static final long serialVersionUID = 1L;
  	
  	
    public java.math.BigDecimal getSubtotal() {//计算小计
    	
    	if(account1 != null && account2 != null  && account3 != null )
    	{
    		this.subtotal = account1.add(account2).add(account3);
    	}else if(account1 == null)
    	{
    		account1=BigDecimal.valueOf(0);
    		this.subtotal = account1.add(account2).add(account3);
    	}else if(account2 == null)
    	{
    		account2=BigDecimal.valueOf(0);
    		this.subtotal = account1.add(account2).add(account3);
    	}else if(account3 == null)
    	{
    		account3 = BigDecimal.valueOf(0);
    		this.subtotal = account1.add(account2).add(account3);
    	}
        return this.subtotal;
    }
    
    public java.math.BigDecimal getTotal() {
        return this.total;
    }
  	
}
