package com.fdays.tsms.policy;

import java.math.BigDecimal;

import com.fdays.tsms.policy._entity._OrderPolicyAfter;

public class SaleStatistics extends _OrderPolicyAfter
{
	private static final long serialVersionUID = 1L;

	private Long airlinePolicyAfterId;

	public Long getAirlinePolicyAfterId()
	{
		return airlinePolicyAfterId;
	}

	public void setAirlinePolicyAfterId(Long airlinePolicyAfterId)
	{
		this.airlinePolicyAfterId = airlinePolicyAfterId;
	}

	public BigDecimal getSaleAmountPercent()
	{
		BigDecimal percent = BigDecimal.valueOf(0);

		if (this.getAirlinePolicyAfter() != null
		    && getAirlinePolicyAfter().getQuota().longValue() > 0)
		{
			percent = saleAmount.divide(this.getAirlinePolicyAfter().getQuota(),2, BigDecimal.ROUND_CEILING)
			    .multiply(BigDecimal.valueOf(100));
		}
		return percent;
	}

	public String getStatusInfo()
	{
		if (this.status == null || this.status == 0) { return "无"; }
		if (this.status == 1) { return "启用"; }
		return "停用";
	}
}
