package com.fdays.tsms.airticket.biz;


import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TempFlight;
import com.fdays.tsms.airticket.TempPNR;
import com.fdays.tsms.airticket.TempPassenger;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.fdays.tsms.airticket.dao.PassengerDAO;
import com.fdays.tsms.airticket.util.AirticketLogUtil;
import com.fdays.tsms.base.util.HttpInvoker;
import com.fdays.tsms.base.util.LogUtil;
import com.fdays.tsms.transaction.Agent;
import com.fdays.tsms.transaction.Statement;
import com.neza.exception.AppException;

public class TempPNRBizImp implements TempPNRBiz{
	private LogUtil myLog;

	
	
	//根据pnr获取定单信息
	public TempPNR  getTempPNRByPnr(String pnr) throws AppException {
		myLog=new AirticketLogUtil(true,false,TempPNRBizImp.class,"");
		String pnrXMLInfo="";
		  TempPNR tempPNR=new TempPNR();//PRN
		try {
			String url="http://pid.fdays.net:352/ib_tranx_req.asp?uid=Websd&sessionid=0&verify=0&termid=sztest&cmd=rt_parse&pnr="+pnr+"&get_price=1&hk=0&get_ticket=1";
			myLog.info("url----"+url);
			pnrXMLInfo=HttpInvoker.readContentFromGet(url);
			myLog.info(pnrXMLInfo);
			 if(pnrXMLInfo.contains("<?xml version=\"1.0\" encoding=\"GB2312\"?>")){
			    //ByteArrayInputStream   sbInput=new ByteArrayInputStream(pnrXMLInfo.toString().getBytes("GB2312"));  
				//SAXReader reader = new SAXReader();
				//Document document = reader.read(sbInput);
				Document document=DocumentHelper.parseText(pnrXMLInfo);
				List<Element> elementList = document.selectNodes("/rt_parse"); 
				for(Element e: elementList){
					   String ret_value = e.attributeValue("ret_value");//父节点的名称
					   myLog.info(ret_value+"-------------------------"+e.elementText("pnr")
							   +e.elementText("b_pnr")+e.elementText("is_team")+e.elementText("HasINF")+e.elementText("IsCHD")+
							   e.elementText("remark"));
                       
					 if(ret_value!=null&&!ret_value.equals("0")){  

				       tempPNR.setRt_parse_ret_value(Long.valueOf(ret_value));
				       tempPNR.setPnr(e.elementText("pnr"));
				       tempPNR.setB_pnr(e.elementText("b_pnr"));
				       tempPNR.setIs_team(e.elementText("is_team"));
				       tempPNR.setIsCHD(e.elementText("IsCHD"));
				       tempPNR.setRemark(e.elementText("remark"));
						 
					 //循环子节点获取乘客数据
						List<Element> elementListPassengers  = e.selectNodes("/rt_parse/passengers"); 
						for(Element ePassengers : elementListPassengers){
							String passengers_count=ePassengers.attributeValue("count");
							System.out.println("---"+passengers_count);
							if(passengers_count!=null&&!passengers_count.equals("0")){
								
						    tempPNR.setPassengers_count(Long.valueOf(passengers_count));
						    List<TempPassenger> tempPassengerList=new ArrayList<TempPassenger>();
							 List<Element> elementListPassenger = ePassengers.elements("passenger");   
							   for(Element ePassenger : elementListPassenger){   
								   TempPassenger tempPassenger=new TempPassenger();
								   myLog.info(ePassenger.elementText("Name"));
								   myLog.info(ePassenger.elementText("NI"));
					               tempPassenger.setName(ePassenger.elementText("Name"));
					               tempPassenger.setNi(ePassenger.elementText("NI"));
					               tempPassengerList.add(tempPassenger);
							   }
							   tempPNR.setTempPassengerList(tempPassengerList);
							} 
						}   
					   
						
					//循环子节点获取航班数据					
					List<Element> elementListFlight = e.selectNodes("/rt_parse/lines"); 
					for(Element eFlight : elementListFlight){
						String lines_count=eFlight.attributeValue("count");
						myLog.info(lines_count);
						if(lines_count!=null&&!lines_count.equals("0")){
						   tempPNR.setLines_count(Long.valueOf(lines_count)); 	
						   List<Element> elineList = eFlight.elements("Line");   
						   List<TempFlight> tempFlightList=new ArrayList<TempFlight>();
						   for(Element eLine:elineList){   
							   TempFlight TempFlight=new TempFlight();
				               System.out.println(eLine.elementText("AirLine"));
				               TempFlight.setAirline(eLine.elementText("AirLine"));
				               TempFlight.setCabin(eLine.elementText("Cabin"));
				               TempFlight.setDiscount(eLine.elementText("Discount"));
				               TempFlight.setDepartureCity(eLine.elementText("DepartureCity"));
				               TempFlight.setDestineationCity(eLine.elementText("DestinationCity"));
				               TempFlight.setDepartureAirPort(eLine.elementText("DepartureAirPort"));
				               TempFlight.setDestinationAirPort(eLine.elementText("DestinationAirPort"));
				               if(eLine.elementText("Date")!=null&&eLine.elementText("StartTime")!=null){
				               TempFlight.setTempDate(eLine.elementText("Date"),eLine.elementText("StartTime"));
				               }
				               TempFlight.setTempArrivaltime(eLine.elementText("ArriveTime"));
				               TempFlight.setState(eLine.elementText("State"));
				               tempFlightList.add(TempFlight);
						   }
						   tempPNR.setTempFlightList(tempFlightList);
						}
					}
					//票号
					List<Element> elementListTickets = e.selectNodes("/rt_parse/tickets"); 
					for(Element eTickets : elementListTickets){
						String tickets_count=eTickets.attributeValue("count");
						if(tickets_count!=null&&!tickets_count.equals("0")){
							tempPNR.setTickets_count(Long.valueOf(tickets_count));
							List<Element> eticketList = eTickets.elements("ticket");  
							  List  tempTicketsList=new ArrayList();
							  //tempTicketsList.add(eTickets.elementText("ticket"));
							   for(Element eTicket:eticketList){ 
								   tempTicketsList.add(eTicket.getText());
								   System.out.println("ticket====="+eTicket.getText());
							   }
							   tempPNR.setTempTicketsList(tempTicketsList);	  
						}
					}
					//获取航班价格
					List<Element> elementListPrices = document.selectNodes("/rt_parse/prices"); 
					for(Element ePrices: elementListPrices){
						String price_Count = ePrices.attributeValue("count");//父节点的名称
						if(price_Count!=null&&!price_Count.equals("0")){
							tempPNR.setPrice_Count(Long.valueOf(price_Count));
						    
						List<Element> epriceList = ePrices.elements("price");  
						java.math.BigDecimal fare1=new BigDecimal(0);
						java.math.BigDecimal tax1=new BigDecimal(0);
						java.math.BigDecimal yq1=new BigDecimal(0);
						for(Element ePrice:epriceList){ 
							String fare=ePrice.elementText("Fare");
							String tax=ePrice.elementText("Tax");
							String yq=ePrice.elementText("YQ");
						    if(fare!=null){
						    	
						    	fare1=(new BigDecimal(fare));
						    }
						    if(tax!=null){
						    	tax1=(new BigDecimal(tax));
						    }
						    if(yq!=null){
						    	yq1=(new BigDecimal(yq));
						    }
						    myLog.info("fare---"+ePrice.elementText("Fare")+ePrice.elementText("Tax")+ePrice.elementText("YQ")); 	

							}
						if(tempPNR.getPassengers_count()>0){
							
							tempPNR.setFare(fare1.multiply(new BigDecimal(tempPNR.getPassengers_count())));
							tempPNR.setTax(tax1.multiply(new BigDecimal(tempPNR.getPassengers_count())));
							tempPNR.setYq(yq1.multiply(new BigDecimal(tempPNR.getPassengers_count()))); 
							
						}
						myLog.info("fare1==="+tempPNR.getFare()+"tax1==="+tempPNR.getTax()+"yq1===="+tempPNR.getYq());
					   }
					 }
			        }else{
			        	tempPNR.setRt_parse_ret_value(Long.valueOf(0));
			        	myLog.info("PNR无效！！！");
			        }
			    }
			 }else{
				 tempPNR.setRt_parse_ret_value(Long.valueOf(0));
				 myLog.info("PNR无效！！！");
			 }
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	   return tempPNR;
	}
	
	/*public  Long savePNR(String pnr) throws AppException{
		TempPNRBizImp tf=new TempPNRBizImp();
		TempPNR tempPNR =tf.getTempPNRByPnr(pnr);
		Long check=0L;
		if (tempPNR != null && tempPNR.getRt_parse_ret_value()!=0L) {
			AirticketOrder airticketOrder=airticketOrderDAO.airticketOrderByPNR(pnr);
			  if(airticketOrder!=null&&airticketOrder.getId()>0){
				  check=2L;//已存在
			  }else{
				  //机票订单
				  AirticketOrder ao=new AirticketOrder();
				  //ao.setDrawPnr(tempPNR.getPnr());//出票pnr
				  ao.setSubPnr(tempPNR.getPnr());//预订pnr
				  ao.setBigPnr(tempPNR.getB_pnr());//大pnr
				  ao.setAgent(new Agent());
				  ao.setStatement(new Statement());
				  ao.setTicketPrice(tempPNR.getFare());//票面价格
				  airticketOrderDAO.save(ao);
				  
				  //乘机人
				  List<TempPassenger> tempPassengerList=tempPNR.getTempPassengerList();
				  for(TempPassenger tempPassenger:tempPassengerList){
					  Passenger passenger=new Passenger();
					  passenger.setAirticketOrder(ao);
					  passenger.setName(tempPassenger.getName());
					  passenger.setCardno(tempPassenger.getNi());
					  passengerDAO.save(passenger);
				  }
				 
				  //航班
				  List<TempFlight> tempFlightList=tempPNR.getTempFlightList();
				  for(TempFlight tempFlight: tempFlightList){
					  Flight flight=new Flight();
					  flight.setAirticketOrder(ao);
					  flight.setFlightCode(tempFlight.getAirline());//航班号
					  flight.setStartPoint(tempFlight.getDepartureCity()); //出发地
					  flight.setEndPoint(tempFlight.getDestineationCity());//目的地
					  flight.setBoardingTime(tempFlight.getStarttime());//起飞时间
					  flight.setStatus(1L);
					  flightDAO.save(flight);
					  check=ao.getId();//添加成功
				  }
			  }
			
			}else{
				check=0L;//PNR 错误
				System.out.println("PNR 错误");
		}
		return check;
	}*/
	
	
	
	
	public static void main(String[] args) {
		TempPNRBizImp tf=new TempPNRBizImp();
		try {
			LogUtil myLog=new AirticketLogUtil(true,false,AirticketOrderBizImp.class,"");
			myLog.info("444");
			myLog.info("666");
			//TBP3G   EEV53 QWNH0 N2R5D
			tf.getTempPNRByPnr("SNRPJ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
 	}
}
