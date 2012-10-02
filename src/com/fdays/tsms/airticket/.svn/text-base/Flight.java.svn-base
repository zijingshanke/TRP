package com.fdays.tsms.airticket;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fdays.tsms.airticket._entity._Flight;

public class Flight extends _Flight {
	private static final long serialVersionUID = 1L;

	private String cyr = "";
	private String cyrText;
	private String startPointText;
	private String endPointText;
    
	public String getCyr() {
		if (this.getFlightCode() != null) {

			String count = "";
			Pattern p = Pattern.compile("[a-zA-Z]");
			Matcher m = p.matcher(this.getFlightCode());
			while (m.find()) {
				count += m.group();
			}

			cyr = count;
		}
		return cyr;
	}

	public String getStartPointText() {

		if (this.getStartPoint() != null
				&& !"".equals(this.getStartPoint().trim())) {
			startPointText = Airport.getNameByCode(this.getStartPoint());
		}
		return startPointText;
	}

	public void setStartPointText(String startPointText) {
		this.startPointText = startPointText;
	}

	public String getEndPointText() {
		
		if (this.getEndPoint() != null&& !"".equals(this.getEndPoint().trim())) {
			endPointText = Airport.getNameByCode(this.getEndPoint());
		}
		return endPointText;
	}

	public void setEndPointText(String endPointText) {
		this.endPointText = endPointText;
	}

	public String getCyrText() {
		if(this.cyr!=null){
			cyrText=Carrier.getNameByCode(this.cyr);
		}
		return cyrText;
	}

	public void setCyrText(String cyrText) {
		this.cyrText = cyrText;
	}

	public void setCyr(String cyr) {
		this.cyr = cyr;
	}

	

}
