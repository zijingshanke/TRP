package com.fdays.tsms.airticket;

import java.util.ArrayList;
import java.util.List;

public class Operate {
   
	private String td;
	private String br;
	private List<MyLabel> myLabels=new ArrayList<MyLabel>();
	
	
	public String getOperateText()
	{
		StringBuffer temp=new StringBuffer();
		
		for(MyLabel ml :myLabels)
		{
			temp.append(ml.toString());
			
		}
		return temp.toString();
	}
	
	public String getTd() {
		return td;
	}
	public void setTd(String td) {
		this.td = td;
	}
	public String getBr() {
		return br;
	}
	public void setBr(String br) {
		this.br = br;
	}
	public List<MyLabel> getMyLabels() {
		return myLabels;
	}
	public void setMyLabels(List<MyLabel> myLabels) {
		this.myLabels = myLabels;
	}
	
}
