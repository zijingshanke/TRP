package com.fdays.tsms.airticket;

import java.util.Date;
import com.fdays.tsms.airticket._entity._Flight;
import com.neza.tool.DateUtil;

public class Flight extends _Flight {
	private static final long serialVersionUID = 1L;

	private String cyr = "";
	private String cyrText;
	private String startPointText;
	private String endPointText;
	private String hcText="";//航程

	public String getHcText() {
		StringBuffer hcTemp=new StringBuffer();
		if(this.startPoint!=null&&!"".equals(this.startPoint.trim())){
			hcTemp.append(this.startPoint);	
		}
		if(this.endPoint!=null&&!"".equals(this.endPoint.trim())){
			hcTemp.append("-");
			hcTemp.append(this.endPoint);
		}
		return hcText=hcTemp.toString();
	}

	public void setHcText(String hcText) {
		this.hcText = hcText;
	}

	public String getCyr() {
		if (this.flightCode != null && "".equals(this.flightCode) == false) {
			if (this.flightCode.length() > 3) {
				return this.flightCode.substring(0, 2);
			} else {
				return "NO";
			}
		} else {
			return "cyr";
		}
	}

	public String getStartPointText() {

		if (this.getStartPoint() != null
				&& !"".equals(this.getStartPoint().trim())) {
			startPointText = Airport.getNameByCode(this.getStartPoint());
		}
		return startPointText;
	}

	public String getBoardingDate() {
		String mydate = "";
		if (boardingTime != null && "".equals(boardingTime) == false) {
			Date tempDate = new Date(boardingTime.getTime());
			mydate = DateUtil.getDateString(tempDate, "yyyy-MM-dd");
		}
		return mydate;
	}

	public void setStartPointText(String startPointText) {
		this.startPointText = startPointText;
	}

	public String getEndPointText() {

		if (this.getEndPoint() != null && !"".equals(this.getEndPoint().trim())) {
			endPointText = Airport.getNameByCode(this.getEndPoint());
		}
		return endPointText;
	}

	public void setEndPointText(String endPointText) {
		this.endPointText = endPointText;
	}

	public String getCyrText() {
		if (this.cyr != null) {
			cyrText = Carrier.getNameByCode(this.cyr);
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
