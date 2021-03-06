package com.fdays.tsms.airticket;

import java.util.HashMap;
import java.util.Map;

/**
 * 承运人
 */
public class Carrier {
	protected String code = "";
	protected String name = "";

	protected static Map<String, String> carrierMap = new HashMap<String, String>();

	static {
		// key:承运人代码
		// value:承运人简称
		carrierMap.put("3U","四川航空");
		carrierMap.put("8C","东星航空");
		carrierMap.put("8L","翔鹏航空");
		carrierMap.put("9C","春秋航空");
		carrierMap.put("BK","奥凯航空");
		carrierMap.put("CA","国际航空");
		carrierMap.put("CN","大新华航空");
		carrierMap.put("CZ","南方航空");
		carrierMap.put("EU","鹰联航空");
		carrierMap.put("FM","上海航空");		
		carrierMap.put("G5","华夏航空");
		carrierMap.put("GS","大新华快运航空");
		carrierMap.put("HO","吉祥航空");
		carrierMap.put("HU","海南航空");
		carrierMap.put("JD","金鹿航空");
		carrierMap.put("JR","幸福航空");
		carrierMap.put("KN","联合航空");
		carrierMap.put("MU","东方航空");	
		carrierMap.put("KY","昆明航空");
		carrierMap.put("MF","厦门航空");
		carrierMap.put("NS","东北航空");
		carrierMap.put("OQ","重庆航空");
		carrierMap.put("PN","西部航空");
		carrierMap.put("SC","山东航空");
		carrierMap.put("VD","鲲鹏航空");
		carrierMap.put("ZH","深圳航空");
		
	}

	public static String getNameByCode(String code) {
		boolean contain=carrierMap.containsKey(code);
		
		if (contain) {
			return carrierMap.get(code);
		}
		return "";
	}
	
	public static void main(String[] args) {
		System.out.println(Carrier.getNameByCode("CA"));
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
