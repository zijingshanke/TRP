package com.fdays.tsms.airticket;

import com.fdays.tsms.airticket._entity._OrderGroup;

public class OrderGroup extends _OrderGroup
{
	private static final long serialVersionUID = 1L;

	public String getNo()
	{
		if (this.no == null || this.no.equals("")) { return "G000000000000"; }
		return no;
	}
}
