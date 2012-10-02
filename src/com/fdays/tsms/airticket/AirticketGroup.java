package com.fdays.tsms.airticket;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.transaction.Agent;

public class AirticketGroup {
	private long saleOrderFlag=new Long(0);
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
	// ----------团队
	private String airorderNo = "";// 订单号
	private Agent buyAgent = new Agent();// 购票客户
	private String totalPassenger = "";// 团队总人数
	private String totalTicketPrice = "";// 总票面价
	private String totalAirportPrice = "";// 总机建税
	private String totalFuelPrice = "";// 总燃油税
	private String discount = "";
	// ----------the end

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
		if (this.saleOrderFlag==0) {
			getCommonInfoBySaleOrder(orderList.get(0));
		}
		
		this.orderList = orderList;
	}

	public static void main(String[] args) {
		List<AirticketOrder> testOrderList = getTestOrderList();//
		List<AirticketGroup> groupList = getGroupList(testOrderList);

		printGroupList(groupList);
	}


	
	public static List<AirticketGroup> getGroupList(
			List<AirticketOrder> orderList) {
		System.out.println("AirticketOrder List size:"+orderList.size());
		String temp="";
		List<AirticketGroup> groupList = new ArrayList<AirticketGroup>();		 
		for(int i=0;i<orderList.size();i++)
		{			
			AirticketOrder ao=orderList.get(i);
			if(i==0)
			{
				 temp=ao.getGroupNo();
			   groupList.add(new AirticketGroup(getSameGroup(orderList,ao.getGroupNo())));
			   continue;
			}
 
			if(!ao.getGroupNo().equals(temp))
			{
				 groupList.add(new AirticketGroup(getSameGroup(orderList,ao.getGroupNo())));
				 temp=ao.getGroupNo();				
			}			
		}
		System.out.println("exchange AiriticketGroup List Success.."+groupList.size());
		return groupList;
	}
	
	private static List<AirticketOrder>  getSameGroup(List<AirticketOrder> orderList,String groupMark) {
		List<AirticketOrder> tempOrderList= new ArrayList<AirticketOrder>();
		
		for(int i=0;i<orderList.size();i++)
		{
			AirticketOrder ao=orderList.get(i);
		
			if(ao.getGroupNo().equals(groupMark))
			{			 
				tempOrderList.add(ao);				
			}	
		}
		return tempOrderList;
	}
	
	
	
	// 从卖出订单获取相同的信息
	public void getCommonInfoBySaleOrder(AirticketOrder order) {
		//flag
		this.saleOrderFlag=order.getId();
		//-------------散票
		this.carrier = order.getCyrsInfo();
		this.flightCode = order.getFlightsCodeInfo();
		this.flight = order.getFlightsInfo();
		this.drawPNR = order.getDrawPnr();
		this.passenger = order.getPassengersInfo();
		this.ticketNo = order.getTicketsInfo();
		this.ticketPrice = order.getTicketPrice() + "";
		this.airportPrice = order.getAirportPrice() + "";
		this.fuelPrice = order.getFuelPrice() + "";
		this.saleOrder = order;
		// --------------------------Team
		this.airorderNo = order.getAirOrderNo();
		this.buyAgent = order.getAgent();
		this.totalPassenger = order.getTotlePerson() + "";
		this.totalTicketPrice = order.getTotalTicketPrice() + "";
		this.totalAirportPrice = order.getTotalAirportPrice() + "";
		this.totalFuelPrice = order.getTotalFuelPrice() + "";
		this.discount = order.getFlightsDiscountInfo();

	}

	public static List<AirticketOrder> getTestOrderList() {
		AirticketOrder order1 = new AirticketOrder();
//		order1.setGroupMarkNo("G0001");
		order1.setSubGroupMarkNo(new Long(0));
		order1.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order1.setFlightCode("CA123");
		order1.setDrawPnr("UIKKK");
		order1.setTotalAmount(new BigDecimal(1000));

		AirticketOrder order2 = new AirticketOrder();
//		order2.setGroupMarkNo("G0001");
		order1.setSubGroupMarkNo(new Long(0));
		order2.setBusinessType(AirticketOrder.BUSINESSTYPE__2);
		order2.setFlightCode("CA123");
		order2.setDrawPnr("UIKKK");
		order2.setTotalAmount(new BigDecimal(2000));

		AirticketOrder order8 = new AirticketOrder();
//		order8.setGroupMarkNo("G0003");
		order1.setSubGroupMarkNo(new Long(1));
		order8.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order8.setFlightCode("CZ2222");
		order8.setDrawPnr("LLLPP");
		order8.setTotalAmount(new BigDecimal(4550));

		AirticketOrder order3 = new AirticketOrder();
//		order3.setGroupMarkNo("G0002");
		order1.setSubGroupMarkNo(new Long(1));
		order3.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order3.setFlightCode("CZ456");
		order3.setDrawPnr("MIHHH");
		order3.setTotalAmount(new BigDecimal(3000));

		AirticketOrder order4 = new AirticketOrder();
//		order4.setGroupMarkNo("G0002");
		order1.setSubGroupMarkNo(new Long(1));
		order4.setBusinessType(AirticketOrder.BUSINESSTYPE__1);
		order4.setFlightCode("CZ456");
		order4.setDrawPnr("MIHHH");
		order4.setTotalAmount(new BigDecimal(4000));
		
		AirticketOrder order5 = new AirticketOrder();
//		order4.setGroupMarkNo("G0002");
		order1.setSubGroupMarkNo(new Long(2));
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
		tempOrderList.add(order5);
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

	public String getAirorderNo() {
		return airorderNo;
	}

	public void setAirorderNo(String airorderNo) {
		this.airorderNo = airorderNo;
	}

	public Agent getBuyAgent() {
		return buyAgent;
	}

	public void setBuyAgent(Agent buyAgent) {
		this.buyAgent = buyAgent;
	}

	public String getTotalPassenger() {
		return totalPassenger;
	}

	public void setTotalPassenger(String totalPassenger) {
		this.totalPassenger = totalPassenger;
	}

	public String getTotalTicketPrice() {
		return totalTicketPrice;
	}

	public void setTotalTicketPrice(String totalTicketPrice) {
		this.totalTicketPrice = totalTicketPrice;
	}

	public String getTotalAirportPrice() {
		return totalAirportPrice;
	}

	public void setTotalAirportPrice(String totalAirportPrice) {
		this.totalAirportPrice = totalAirportPrice;
	}

	public String getTotalFuelPrice() {
		return totalFuelPrice;
	}

	public void setTotalFuelPrice(String totalFuelPrice) {
		this.totalFuelPrice = totalFuelPrice;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public long getSaleOrderFlag() {
		return saleOrderFlag;
	}

	public void setSaleOrderFlag(long saleOrderFlag) {
		this.saleOrderFlag = saleOrderFlag;
	}
	
	
//	/**
//	 * 将原始订单List封装为订单组List
//	 */
//	public static List<AirticketGroup> _getGroupList(
//			List<AirticketOrder> orderList) {
//		List<AirticketGroup> groupList = new ArrayList<AirticketGroup>();
//		Map<String, List> map = new HashMap<String, List>();
//
//		for (Iterator it = orderList.iterator(); it.hasNext();) {
//			AirticketOrder order = (AirticketOrder) it.next();
////如果已经存在这个数组，就放在这里
//			String tempKey=order.getGroupMarkNo()+order.getSubGroupMarkNo();
////			System.out.println("tempKEY:"+tempKey);
//			if (map.containsKey(tempKey)) {
//				List<AirticketOrder> tempOrderList = map.get(tempKey);
//				tempOrderList.add(order);
//			} else {// 重新声明一个数组list
//				List<AirticketOrder> tempOrderList2 = new ArrayList<AirticketOrder>();
//				tempOrderList2.add(order);
//				String newTempKey=order.getGroupMarkNo()+order.getSubGroupMarkNo();
////				System.out.println("newTempKey:"+newTempKey);
//				map.put(newTempKey, tempOrderList2);
//			}
//		}
//
//		for (Iterator iter = map.entrySet().iterator(); iter.hasNext();) {
//			Map.Entry entry = (Map.Entry) iter.next();
//
//			String key = (String) entry.getKey();
//
//			List<AirticketOrder> tempOrderList3 = (List<AirticketOrder>) entry
//					.getValue();
//			
////			System.out.println(key+"--orderList size:"+tempOrderList3.size());
//			
//			groupList.add(new AirticketGroup(tempOrderList3));
//		}
//
//		return groupList;
//	}

	

}
