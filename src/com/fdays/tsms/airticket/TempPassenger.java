package com.fdays.tsms.airticket;

public class TempPassenger {
     
	private String passengers_Count;//乘机人数量
	private String passengers;//乘机人信息
	private String name;//姓名
	private String ni;//证件号码
	
	public String getPassengers_Count() {
		return passengers_Count;
	}
	public void setPassengers_Count(String passengers_Count) {
		this.passengers_Count = passengers_Count;
	}
	public String getPassengers() {
		return passengers;
	}
	public void setPassengers(String passengers) {
		this.passengers = passengers;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNi() {
		return ni;
	}
	public void setNi(String ni) {
		this.ni = ni;
	}
}
