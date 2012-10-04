package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import com.fdays.tsms.airticket._entity._Airline;

public class Airline extends _Airline {
	private static final long serialVersionUID = 1L;
	protected static java.math.BigDecimal standAirportPrice = new BigDecimal(50);// 机场建设费
	protected static java.math.BigDecimal standFuelPrice = BigDecimal.ZERO;// 燃油费

	public String getShowName() {
		return this.begin + "--" + this.end;
	}

	public static TempPNR setTempPNRAirportFuel(TempPNR tempPNR, Long distance) {
		tempPNR.setTax(standAirportPrice);
		if (distance != null && distance > 0) {
			if (distance > 800) {
				tempPNR.setYq(new BigDecimal(90));
			} else {
				tempPNR.setYq(new BigDecimal(50));
			}
		}
		return tempPNR;
	}

	public static long STATUS_1 = new Long(1);// 启用
	public static long STATUS_2 = new Long(2);// 停用

	public String getStatusText() {
		if (this.status.intValue() == STATUS_1)
			return "启用";
		else {
			return "停用";
		}
	}
}
