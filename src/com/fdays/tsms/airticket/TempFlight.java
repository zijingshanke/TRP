package com.fdays.tsms.airticket;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TempFlight {

	private Long lines_Count;// 航段数量
	private String line;// 航段描述
	private String airline;// 航班号
	private String cabin;// 舱位
	private String discount;// 折扣
	private String departureCity;// 出发城市机场三字码
	private String destineationCity;// 到达城市机场三字码
	private String departureAirPort;
	private String destinationAirPort;
	private java.sql.Timestamp date;// 出发日期
	private String tempDate;
	private String state;// 保留
	private java.sql.Timestamp starttime;// 出发时间
	private java.sql.Timestamp arrivaltime;// 到达时间
	private String tempArrivaltime;// 到达时间
	private Long ticket_Count;// 机票数量
	private String ticket;// 票号
	private String cyr = "";// 承运人

	public Long getLines_Count() {
		return lines_Count;
	}

	public void setLines_Count(Long lines_Count) {
		this.lines_Count = lines_Count;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getDepartureCity() {
		return departureCity;
	}

	public void setDepartureCity(String departureCity) {
		this.departureCity = departureCity;
	}

	public String getDestineationCity() {
		return destineationCity;
	}

	public void setDestineationCity(String destineationCity) {
		this.destineationCity = destineationCity;
	}

	public String getDepartureAirPort() {
		return departureAirPort;
	}

	public void setDepartureAirPort(String departureAirPort) {
		this.departureAirPort = departureAirPort;
	}

	public String getDestinationAirPort() {
		return destinationAirPort;
	}

	public void setDestinationAirPort(String destinationAirPort) {
		this.destinationAirPort = destinationAirPort;
	}

	public java.sql.Timestamp getDate() {
		return date;
	}

	public void setDate(java.sql.Timestamp date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public java.sql.Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(java.sql.Timestamp starttime) {
		this.starttime = starttime;
	}

	public java.sql.Timestamp getArrivaltime() {
		return arrivaltime;
	}

	public void setArrivaltime(java.sql.Timestamp arrivaltime) {
		this.arrivaltime = arrivaltime;
	}

	public Long getTicket_Count() {
		return ticket_Count;
	}

	public void setTicket_Count(Long ticket_Count) {
		this.ticket_Count = ticket_Count;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getTempDate() {
		return tempDate;
	}

	
	public void setTempDate(String tempDate, String startTime) {
		if (tempDate.length() > 4) {
			String day = ""; // 获取日期
			String month = "";// 获取月
			Pattern p = Pattern.compile("[0-9]");
			Matcher m = p.matcher(tempDate);
		
			while (m.find()) {
				day += m.group();
			}

			Pattern p2 = Pattern.compile("[a-zA-Z]");
			Matcher m2 = p2.matcher(tempDate);
			while (m2.find()) {
				month += m2.group();
			}

//			System.out.println(day + "----" + month);

			if (month.equalsIgnoreCase("Jan")) {
				month = "01";
			} else if (month.equalsIgnoreCase("Feb")) {
				month = "02";
			} else if (month.equalsIgnoreCase("Mar")) {
				month = "03";
			} else if (month.equalsIgnoreCase("Apr")) {
				month = "04";
			} else if (month.equalsIgnoreCase("May")) {
				month = "05";
			} else if (month.equalsIgnoreCase("Jun")) {
				month = "06";
			} else if (month.equalsIgnoreCase("Jul")) {
				month = "07";
			} else if (month.equalsIgnoreCase("Aug")) {
				month = "08";
			} else if (month.equalsIgnoreCase("Sep")) {
				month = "09";
			} else if (month.equalsIgnoreCase("Oct")) {
				month = "10";
			} else if (month.equalsIgnoreCase("Nov")) {
				month = "11";
			} else if (month.equalsIgnoreCase("Dec")) {
				month = "12";
			} else {
				System.out.println("month=="+month + "无法识别");
			}

			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(System.currentTimeMillis());
			java.util.Date d = c.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");// yyyy年MM月dd日
			// HH时mm分ss秒
			String sysYear = sdf.format(d);
			SimpleDateFormat sdf2 = new SimpleDateFormat("MM");// yyyy年MM月dd日
			// HH时mm分ss秒
			String sysMonth = sdf2.format(d);

			// System.out.println(sdf.format(d) + sdf2.format(d));

			if (month != null && sysMonth != null
					&& (Integer.valueOf(month)) < (Integer.valueOf(sysMonth))) {
				sysYear = String.valueOf((Integer.valueOf(sysYear) + 1));
			}
			tempDate = sysYear + "-" + month + "-" + day;
			setBoardingDateByTempString(tempDate, startTime);
		}
	}
	
	
	public  void setBoardingDateByTempString(String tempDate,String startTime){
		java.sql.Timestamp dd=null;
		java.sql.Timestamp startdd=null;
		try {
			// System.out.println(tempDate);

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date cDate = df.parse(tempDate);
			dd = new java.sql.Timestamp(cDate.getTime());
			// System.out.println("出发日期=" + dd);

			startTime = tempDate + " " + startTime;
			// System.out.println("start" + startTime);
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			java.util.Date cDate2 = df2.parse(startTime);
			startdd = new java.sql.Timestamp(cDate2.getTime());
			 System.out.println("起飞时间=" + startdd);
			
			this.setDate(dd);// 设置出发日期
			this.setStarttime(startdd);// 设置起飞时间
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	public String getCyr() {
		if (this.getAirline() != null) {

			//String count = "";
			StringBuffer count = new StringBuffer();
			Pattern p = Pattern.compile("[a-zA-Z]");
			Matcher m = p.matcher(this.getAirline());
			while (m.find()) {
				//count += m.group();
				count.append(m.group());
			}

			cyr = count.toString();
		}
		return cyr;
	}

	public String getTempArrivaltime() {
		return tempArrivaltime;
	}

	public void setTempArrivaltime(String tempArrivaltime) {
		this.tempArrivaltime = tempArrivaltime;
	}

	public static void main(String[] args) {

		TempFlight tempFlight = new TempFlight();
		tempFlight.setTempDate("19Jan", "15:35");
	}

}
