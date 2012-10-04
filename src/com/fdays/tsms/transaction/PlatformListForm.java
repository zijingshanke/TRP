package com.fdays.tsms.transaction;

import com.neza.base.ListActionForm;

public class PlatformListForm extends ListActionForm{

	
	private String name;//交易名称
	private String type;//交易类型
    private String drawType;//出票类型
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDrawType() {
		return drawType;
	}
	public void setDrawType(String drawType) {
		this.drawType = drawType;
	}
	
	
	
}
