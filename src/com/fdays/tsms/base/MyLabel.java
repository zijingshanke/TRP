package com.fdays.tsms.base;

public class MyLabel {

	private String url;
	private String src;
	private String href="#";
	private String events="";
	private String eventsValue;
	private String labText;
	private String startText="";
	private String endText="";
	
	public MyLabel(){}
	
	public String toString(){
		
		return this.startText+"<a  "+this.events+"  href=\""+this.href+"\"> "+labText+" </a>"+this.endText;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getEvents() {
		return events;
	}
	public void setEvents(String events) {
		this.events = events;
	}
	public String getLabText() {
		return labText;
	}

	public void setLabText(String labText) {
		this.labText = labText;
	}

	public String getEventsValue() {
		return eventsValue;
	}

	public void setEventsValue(String eventsValue) {
		this.eventsValue = eventsValue;
	}

	public String getStartText() {
		return startText;
	}

	public void setStartText(String startText) {
		this.startText = startText;
	}

	public String getEndText() {
		return endText;
	}

	public void setEndText(String endText) {
		this.endText = endText;
	}
	
}
