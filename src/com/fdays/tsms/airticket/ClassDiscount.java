package com.fdays.tsms.airticket;

import java.util.HashMap;
import java.util.Map;

/**
 * 舱位折扣
 */
public class ClassDiscount {

	protected String carrier_code="";//承运人代码
	protected String flight_class = "";
	protected String discount = "";

	protected static Map<String, String> classDiscountMap = new HashMap<String, String>();

	static {
		// key:舱位
		// value:折扣
		classDiscountMap.put("AKU", "阿克苏");
		classDiscountMap.put("AOG", "鞍山");
		classDiscountMap.put("AQG", "安庆");

	}

	public static String getNameByCode(String flightClass) {
		boolean contain = classDiscountMap.containsKey(flightClass);

		if (contain) {
			return classDiscountMap.get(flightClass);
		}
		return "";
	}

	
	
	
	
	public String getCarrier_code() {
		return carrier_code;
	}





	public void setCarrier_code(String carrier_code) {
		this.carrier_code = carrier_code;
	}





	public String getFlight_class() {
		return flight_class;
	}

	public void setFlight_class(String flight_class) {
		this.flight_class = flight_class;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}



}
