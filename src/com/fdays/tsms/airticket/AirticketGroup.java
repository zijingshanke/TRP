package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AirticketGroup {
	private String groupMarkNo = "";
	private String carrier = "";// 承运人
	private String flightCode = "";// 航班号
	private String flight = "";// 出发-目的地
	private String drawPNR = "";// 出票PNR
	private String passenger = "";// 乘客
	private String ticketNo = "";// 票号
	private String ticketPrice = "";// 票价
	private String airportPrice = "";// 机建
	private String fuelPrice;// 燃油

	private int orderCount = 1;// 订单数量
	private List<AirticketOrder> orderList = new ArrayList<AirticketOrder>();
	private AirticketOrder saleOrder = new AirticketOrder();// 卖出订单（第一条）

	public AirticketGroup() {

	}

	public AirticketGroup(List<AirticketOrder> orderList) {
		for (int i = 0; i < orderList.size(); i++) {
			AirticketOrder order = orderList.get(i);
			if (order.getBusinessType() != null) {
				if (order.getBusinessType() == AirticketOrder.BUSINESSTYPE__1) {
					getCommonInfoBySaleOrder(order);
				}
			}
		}

		this.orderList = orderList;
	}

	public static void main(String[] args) {
		List<AirticketOrder> testOrderList = getTestOrderList();//
		List<AirticketGroup> groupList = getGroupList(testOrderList);

		printGroupList(groupList);
	}

	/**
	 * 将原始订单List封装为订单组List
	 */
	public static List<AirticketGroup> getGroupList(
			List<AirticketOrder> orderList) {
		List<AirticketGroup> groupList = new ArrayList<AirticketGroup>();

		String tempGroupMarkNo = "";
		boolean flag = false;
		boolean flag2=false;
		boolean flag3=false;
		List<AirticketOrder> tempOrderList = null;

		for (int i = 0; i < orderList.size(); i++) {
			AirticketOrder order = orderList.get(i);
			String groupMarkNo = order.getGroupMarkNo();

			if (groupMarkNo != null) {
				if ("".equals(tempGroupMarkNo)) {
					tempGroupMarkNo = groupMarkNo;
					tempOrderList = new ArrayList<AirticketOrder>();
					tempOrderList.add(order);
//					System.out.println(" add group:" + tempGroupMarkNo);
					groupList.add(new AirticketGroup(tempOrderList));
					flag = false;
					flag2 = false;
					flag3=false;
				} else if (tempGroupMarkNo.equals(groupMarkNo)) {
					tempOrderList.add(order);
					flag = true;
					flag2 = true;
					flag3=false;
				} else {
					tempGroupMarkNo = groupMarkNo;
//					System.out.println(" add group:" + tempGroupMarkNo);
					tempOrderList = new ArrayList<AirticketOrder>();
					tempOrderList.add(order);
					groupList.add(new AirticketGroup(tempOrderList));				
					flag = true;
					flag2 = false;
					flag3=false;
				}
			}
		}

		if (flag == true && flag2==true&&flag3==true && tempOrderList != null) {
//			System.out.println(" add group outside:" + tempGroupMarkNo);
			groupList.add(new AirticketGroup(tempOrderList));
		}
		return groupList;
	}
	
	
	

	// 从卖出订单获取相同的信息
	public void getCommonInfoBySaleOrder(AirticketOrder order) {
		this.carrier = order.getCyrsInfo();
		this.flightCode = order.getFlightCode();
		this.flight = order.getFlightsInfo();
		this.drawPNR = order.getDrawPnr();
		this.passenger = order.getPassengersInfo();
		this.ticketNo = order.getTicketsInfo();
		this.ticketPrice = order.getTicketPrice() + "";
		this.airportPrice = order.getAirportPrice() + "";
		this.fuelPrice = order.getFuelPrice() + "";
		this.saleOrder = order;
	}

	public static List<AirticketOrder> getTestOrderList() {
		AirticketOrder order1 = new AirticketOrder();
		order1.setGroupMarkNo("G0001");
		order1.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order1.setFlightCode("CA123");
		order1.setDrawPnr("UIKKK");
		order1.setTotalAmount(new BigDecimal(1000));

		AirticketOrder order2 = new AirticketOrder();
		order2.setGroupMarkNo("G0001");
		order2.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
		order2.setFlightCode("CA123");
		order2.setDrawPnr("UIKKK");
		order2.setTotalAmount(new BigDecimal(2000));

		AirticketOrder order8 = new AirticketOrder();
		order8.setGroupMarkNo("G0003");
		order8.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order8.setFlightCode("CZ2222");
		order8.setDrawPnr("LLLPP");
		order8.setTotalAmount(new BigDecimal(4550));

		AirticketOrder order3 = new AirticketOrder();
		order3.setGroupMarkNo("G0002");
		order3.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order3.setFlightCode("CZ456");
		order3.setDrawPnr("MIHHH");
		order3.setTotalAmount(new BigDecimal(3000));

		AirticketOrder order4 = new AirticketOrder();
		order4.setGroupMarkNo("G0002");
		order4.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order4.setFlightCode("CZ456");
		order4.setDrawPnr("MIHHH");
		order4.setTotalAmount(new BigDecimal(4000));

		List<AirticketOrder> tempOrderList = new ArrayList<AirticketOrder>();
		tempOrderList.add(order1);
		tempOrderList.add(order2);
		tempOrderList.add(order8);
		tempOrderList.add(order3);
		tempOrderList.add(order4);
		return tempOrderList;
	}

	public static void printGroupList(List<AirticketGroup> groupList) {
		System.out.println("groupSize:" + groupList.size());

		for (int i = 0; i < groupList.size(); i++) {
			AirticketGroup airticketGroup = groupList.get(i);
			System.out.println("group" + i + "--orderCount:"
					+ airticketGroup.orderCount);
			System.out.println("========common info============" + i);
			System.out.println(airticketGroup.getCarrier());
			System.out.println(airticketGroup.getDrawPNR());

			for (int j = 0; j < airticketGroup.getOrderList().size(); j++) {
				System.out.println(" order info=================" + j);
				AirticketOrder order = airticketGroup.getOrderList().get(j);
				System.out.println(order.getTotalAmount());
				System.out.println(order.getStatusText());
			}
		}
	}

	public String getGroupMarkNo() {
		return groupMarkNo;
	}

	public void setGroupMarkNo(String groupMarkNo) {
		this.groupMarkNo = groupMarkNo;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getFlightCode() {
		return flightCode;
	}

	public void setFlightCode(String flightCode) {
		this.flightCode = flightCode;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public String getDrawPNR() {
		return drawPNR;
	}

	public void setDrawPNR(String drawPNR) {
		this.drawPNR = drawPNR;
	}

	public String getPassenger() {
		return passenger;
	}

	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}

	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getAirportPrice() {
		return airportPrice;
	}

	public void setAirportPrice(String airportPrice) {
		this.airportPrice = airportPrice;
	}

	public String getFuelPrice() {
		return fuelPrice;
	}

	public void setFuelPrice(String fuelPrice) {
		this.fuelPrice = fuelPrice;
	}

	public int getOrderCount() {
		return orderList.size();
	}

	public List<AirticketOrder> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<AirticketOrder> orderList) {
		this.orderList = orderList;
	}

	public AirticketOrder getSaleOrder() {
		return saleOrder;
	}

	public void setSaleOrder(AirticketOrder saleOrder) {
		this.saleOrder = saleOrder;
	}
}
