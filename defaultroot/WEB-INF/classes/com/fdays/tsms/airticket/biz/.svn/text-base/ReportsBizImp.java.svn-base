package com.fdays.tsms.airticket.biz;

import java.util.ArrayList;
import java.util.List;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.dao.ReportsDAO;
import com.fdays.tsms.transaction.StatementListForm;

import com.neza.exception.AppException;

public class ReportsBizImp implements ReportsBiz {
	public ReportsDAO reportsDAO;
	

	public List marketReportsList(AirticketOrderListForm rlf) throws AppException {
		return reportsDAO.marketReportsList(rlf);
	}
	
	//银行卡付款统计
	public List getStatementList(StatementListForm statementForm) throws AppException
	{
		return reportsDAO.getStatementList(statementForm);
	}
	
	//下载用
	public ArrayList<ArrayList<Object>> getMarketReportsList(AirticketOrderListForm alf)throws AppException
    {
		String downloadDate="";
		if(alf.getDownloadDate() == null || alf.getDownloadDate().equals(""))
			alf.setDownloadDate(com.neza.tool.DateUtil.getDateString("yyyy-MM-dd HH:mm:ss"));
		
	downloadDate=alf.getDownloadDate();
	List data=reportsDAO.marketReportsList(alf);
	
	ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
	ArrayList<Object> list_title = new ArrayList<Object>();
	list_title.add("#原始销售报表");
	list_context.add(list_title);
	list_title = new ArrayList<Object>();
	list_title.add("#指定查询时间：");
	list_context.add(list_title);
	list_title = new ArrayList<Object>();
	list_title.add(downloadDate);
	list_context.add(list_title);
	list_title =  new ArrayList<Object>();
	list_title.add("订单时间");
	list_title.add("平台");
	list_title.add("返点");
	list_title.add("预定PNR");
	list_title.add("出票PNR");
	list_title.add("大PNR");
	list_title.add("乘客");
	list_title.add("人数");
	list_title.add("航段");
	list_title.add("航段三字码");
	list_title.add("承运人");
	list_title.add("航班号");
	list_title.add("舱位");
	list_title.add("折扣");
	list_title.add("单票面价");
	list_title.add("单机建税");
	list_title.add("单燃油税");
	list_title.add("总票面价");
	list_title.add("总机建税");
	list_title.add("总燃油税");
	list_title.add("起飞日期");
	list_title.add("票号");
	list_title.add("订单编号");
	list_title.add("实收/付票款");
	list_title.add("支付方式/操作人");
	list_context.add(list_title);
		for (int i = 0; i < data.size(); i++)
	{
		AirticketOrder ao = (AirticketOrder) data.get(i);
		ArrayList<Object> list_context_item = new ArrayList<Object>();
		
		list_context_item.add("");//订单时间
		if(ao.getStatement().getPlatComAccount()!=null){
			list_context_item.add(ao.getStatement().getPlatComAccount().getPlatform().getName());//平台
		}
		list_context_item.add(ao.getStatement().getCommission());//返点
		list_context_item.add(ao.getSubPnr());//预定PNR
		list_context_item.add(ao.getDrawPnr());//出票PNR
		list_context_item.add(ao.getBigPnr());//大PNR
		for(Object obj:  ao.getPassengers()){
			Passenger passenger=(Passenger)obj;	
		list_context_item.add(passenger.getName());//乘客
		}
		list_context_item.add(ao.getAllPeople());//人数
		StringBuffer hd=new StringBuffer();;
		StringBuffer hdszm=new StringBuffer();;
		StringBuffer cyr=new StringBuffer();; 
		StringBuffer flightCode=new StringBuffer();;
		StringBuffer flightClass=new StringBuffer();;
		StringBuffer discount=new StringBuffer();;
		for(Object fobj:  ao.getFlights()){
			Flight flight=(Flight)fobj;
			hd.append(flight.getStartPointText());
			hd.append(flight.getEndPointText());
			hdszm.append(flight.getStartPoint()+"--"+flight.getEndPoint());
			cyr.append(flight.getCyr());
			flightCode.append(flight.getFlightCode());
			flightClass.append(flight.getFlightClass());
			discount.append(flight.getDiscount());
		}
		list_context_item.add(hd);//航段
		list_context_item.add(hdszm);//航段三字码
		list_context_item.add(cyr);//承运人
		list_context_item.add(flightCode);//航班号
		list_context_item.add(flightClass);//舱位
		list_context_item.add(discount);//折扣
		list_context_item.add(ao.getTicketPrice());//单票面价 
		list_context_item.add(ao.getAirportPrice());//单机建税 
		list_context_item.add(ao.getFuelPrice());//单燃油税 
		list_context_item.add(ao.getAllTotlePrice());//总票面价
		list_context_item.add(ao.getAllAirportPrice());//总机建税 
		list_context_item.add(ao.getAllFuelPrice());//总燃油税
		StringBuffer boardingTime=new StringBuffer();
		for(Object fobj2:  ao.getFlights()){
			Flight flight=(Flight)fobj2;
			boardingTime.append(flight.getBoardingTime());
		list_context_item.add(boardingTime);//起飞日期
		}
		StringBuffer ticketNumber=new StringBuffer();;
		for(Object obj2:  ao.getPassengers()){
			Passenger passenger=(Passenger)obj2;
			ticketNumber.append(passenger.getTicketNumber());
		}
		list_context_item.add(ticketNumber);//票号
		list_context_item.add(ao.getAirOrderNo());//订单编号
		list_context_item.add(ao.getStatement().getTotalAmount());//实收/付票款
		list_context_item.add(ao.getStatement().getSysUser().getUserName());//支付方式/操作人
		list_context.add(list_context_item);
	}
		                      
	return list_context;
}

	
	
	public ReportsDAO getReportsDAO() {
		return reportsDAO;
	}

	public void setReportsDAO(ReportsDAO reportsDAO) {
		this.reportsDAO = reportsDAO;
	}
}
