package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.List;
import com.fdays.tsms.base.util.LogUtil;

public class TempPNR {
	private Long rt_parse_ret_value;// 编码提取结果，如果为0，则表示该次请求失败
	private String pnr; // 预订记录编码
	private String b_pnr;
	private String draw_pnr;
	private String is_team;
	private String hasNI;
	private String hasINF;
	private String isCHD;
	private String remark;
	private List<TempFlight> tempFlightList;// 航班信息
	private List<TempPassenger> tempPassengerList;// 乘客信息
	private List tempTicketsList;// 票号信息
	private Long tickets_count;// 票号数量
	private Long passengers_count; // 乘客信息count 无数据为0
	private Long lines_count; // 航班count 无数据为0
	private Long price_Count;// 价格数量
	private java.math.BigDecimal price;// 价格信息
	private java.math.BigDecimal fare;// 票面
	private java.math.BigDecimal tax;// 机建费
	private java.math.BigDecimal yq;// 燃油税
	private LogUtil myLog;

	// -----------------票面价计算使用
	private String cabin;// 舱位
	private String discount;// 折扣

	public String getCarrier() {
		if (this.tempFlightList != null) {
			if (this.tempFlightList.size()>0) {
				return this.tempFlightList.get(0).getCyr();
			}
		}
		return null;
	}

	public String getBegin() {
		if (this.tempFlightList != null) {
			if (this.tempFlightList.size()>0&&this.tempFlightList.get(0) != null) {
				return this.tempFlightList.get(0).getDepartureCity();
			}
		}
		return null;
	}

	public String getEnd() {
		if (this.tempFlightList != null) {
			if (this.tempFlightList.size()>0&&this.tempFlightList.get(0) != null) {
				return this.tempFlightList.get(0).getDestineationCity();
			}
		}
		return null;
	}

	public String getDiscount() {
		if (this.discount == null || "".equals(this.discount)) {
			if (this.tempFlightList != null) {
				if (this.tempFlightList.size()>0&&this.tempFlightList.get(0) != null) {
					return this.tempFlightList.get(0).getDiscount();
				}
			}
		}

		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getCabin() {
		if (this.cabin == null || "".equals(this.cabin)) {
			if (this.tempFlightList != null) {
				if (this.tempFlightList.size()>0&&this.tempFlightList.get(0) != null) {
					return this.tempFlightList.get(0).getCabin();
				}
			}
		}
		return null;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getDraw_pnr() {
		return draw_pnr;
	}

	public void setDraw_pnr(String draw_pnr) {
		this.draw_pnr = draw_pnr;
	}

	public void setFare(java.math.BigDecimal fare) {
		this.fare = fare;
	}

	public void setTax(java.math.BigDecimal tax) {
		this.tax = tax;
	}

	public void setYq(java.math.BigDecimal yq) {
		this.yq = yq;
	}

	public Long getRt_parse_ret_value() {
		return rt_parse_ret_value;
	}

	public void setRt_parse_ret_value(Long rt_parse_ret_value) {
		this.rt_parse_ret_value = rt_parse_ret_value;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}

	public List<TempFlight> getTempFlightList() {
		return tempFlightList;
	}

	public void setTempFlightList(List<TempFlight> tempFlightList) {
		this.tempFlightList = tempFlightList;
	}

	public List<TempPassenger> getTempPassengerList() {
		return tempPassengerList;
	}

	public void setTempPassengerList(List<TempPassenger> tempPassengerList) {
		this.tempPassengerList = tempPassengerList;
	}

	public Long getPrice_Count() {
		return price_Count;
	}

	public void setPrice_Count(Long price_Count) {
		this.price_Count = price_Count;
	}

	public java.math.BigDecimal getPrice() {
		return price;
	}

	public void setPrice(java.math.BigDecimal price) {
		this.price = price;
	}

	public java.math.BigDecimal getFare() {
		if(fare==null){
			return BigDecimal.ZERO;
		}
		return fare;
	}

	public java.math.BigDecimal getTax() {
		if(tax==null){
			return BigDecimal.ZERO;
		}
		return tax;
	}

	public java.math.BigDecimal getYq() {
		if(yq==null){
			return BigDecimal.ZERO;
		}
		return yq;
	}

	public String getB_pnr() {
		return b_pnr;
	}

	public void setB_pnr(String b_pnr) {
		this.b_pnr = b_pnr;
	}

	public String getIs_team() {
		return is_team;
	}

	public void setIs_team(String is_team) {
		this.is_team = is_team;
	}

	public String getHasNI() {
		return hasNI;
	}

	public void setHasNI(String hasNI) {
		this.hasNI = hasNI;
	}

	public String getHasINF() {
		return hasINF;
	}

	public void setHasINF(String hasINF) {
		this.hasINF = hasINF;
	}

	public String getIsCHD() {
		return isCHD;
	}

	public void setIsCHD(String isCHD) {
		this.isCHD = isCHD;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getPassengers_count() {
		if (this.tempPassengerList != null) {
			return new Long(this.tempPassengerList.size());
		}
		return passengers_count;
	}

	public Long getLines_count() {
		return lines_count;
	}

	public void setLines_count(Long lines_count) {
		this.lines_count = lines_count;
	}

	public List getTempTicketsList() {
		return tempTicketsList;
	}

	public void setTempTicketsList(List tempTicketsList) {
		this.tempTicketsList = tempTicketsList;
	}

	public Long getTickets_count() {
		return tickets_count;
	}

	public void setTickets_count(Long tickets_count) {
		this.tickets_count = tickets_count;
	}
}
