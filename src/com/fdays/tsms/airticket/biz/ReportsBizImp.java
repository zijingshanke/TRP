package com.fdays.tsms.airticket.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.TeamAirticketOrderReport;
import com.fdays.tsms.airticket.dao.AirticketOrderDAO;
import com.fdays.tsms.airticket.dao.FlightDAO;
import com.fdays.tsms.airticket.TempSaleReport;
import com.fdays.tsms.airticket.dao.ReportsDAO;
import com.fdays.tsms.transaction.Statement;
import com.fdays.tsms.transaction.StatementListForm;
import com.fdays.tsms.transaction.dao.AgentDAO;
import com.fdays.tsms.transaction.dao.StatementDAO;

import com.neza.exception.AppException;

public class ReportsBizImp implements ReportsBiz {
	public AirticketOrderDAO airticketOrderDAO ;
	public ReportsDAO reportsDAO;
	public FlightDAO flightDAO;
	public StatementDAO statementDAO;
	public AgentDAO agentDAO;

	public List marketReportsList(AirticketOrderListForm rlf) throws AppException {
		return reportsDAO.marketReportsList(rlf);
	}
	
	//团队销售报表
	public List getTeamAirTicketOrderList(AirticketOrderListForm rlf) throws AppException
	{
		return reportsDAO.getTeamAirTicketOrderList(rlf);
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
		
		list_context_item.add(ao.getEntryOrderDate());//订单时间
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
		StringBuffer hd=new StringBuffer();
		StringBuffer hdszm=new StringBuffer();
		StringBuffer cyr=new StringBuffer();
		StringBuffer flightCode=new StringBuffer();
		StringBuffer flightClass=new StringBuffer();
		StringBuffer discount=new StringBuffer();
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
			boardingTime.append(flight.getBoardingDate());
		list_context_item.add(boardingTime);//起飞日期
		}
		StringBuffer ticketNumber=new StringBuffer();
		for(Object obj2:  ao.getPassengers()){
			Passenger passenger=(Passenger)obj2;
			ticketNumber.append(passenger.getTicketNumber());
		}
		list_context_item.add(ticketNumber);//票号
		list_context_item.add(ao.getAirOrderNo());//订单编号
		list_context_item.add(ao.getStatement().getTotalAmount());//实收/付票款
		list_context_item.add(ao.getPayOperatorName());//支付方式/操作人
		list_context.add(list_context_item);
	}
		                      
	return list_context;
}
  
	/***************************************************************************
	 * //销售订单  sc
	 **************************************************************************/
	public List<TempSaleReport> saleReportsList(AirticketOrderListForm rlf) throws AppException{
		
		
		List<TempSaleReport> tempSaleReportsList=new ArrayList<TempSaleReport>();
		List  groupMarkNoList =reportsDAO.saleReportsByGroupMarkNoList(rlf);
		for (int i = 0; i < groupMarkNoList.size(); i++) {
         	
			AirticketOrder groupMarkNo=(AirticketOrder)groupMarkNoList.get(i);
			System.out.println("------->"+groupMarkNo.getGroupMarkNo());
			
			List aoList=reportsDAO.getAirticketOrderListByGroupMarkNo(groupMarkNo.getGroupMarkNo(), "1,2");
			System.out.println("aoList----->"+aoList.size());
			
			if(aoList!=null&&aoList.size()>0){
		  	AirticketOrder toAo=new AirticketOrder();//卖出
			AirticketOrder formAo=new AirticketOrder();//买入
		    for(int j=0;j<aoList.size();j++){
		  
		    	if(aoList.size()==1){
		    		AirticketOrder ao=(AirticketOrder)aoList.get(j);
		    		if(ao!=null&&ao.getTranType()==1){//买入 from
		    			formAo=ao;
		    		}
		    		else if(ao!=null&&ao.getTranType()==2){
		    			toAo=ao;
		    		}
		    	}else if(aoList.size()>=2){
		    		AirticketOrder ao2=(AirticketOrder)aoList.get(j);
		    		if(ao2!=null&&ao2.getTranType()==1){
		    			formAo=ao2;
		    		}
		    		else if(ao2!=null&&ao2.getTranType()==2){
		    			toAo=ao2;
		    			
		    		}
		    	}
		    		
		    }
		    System.out.println("toAo===>"+toAo.getGroupMarkNo());
			System.out.println("formAo===>"+formAo.getGroupMarkNo());
			
			TempSaleReport tsr=new TempSaleReport();
			tsr.setOrderTime(formAo.getOptTime());//订单时间
			if(toAo.getStatement().getToPCAccount()!=null){
					tsr.setToPlatform(toAo.getStatement().getToPCAccount().getPlatform().getShowName());//卖出商
			}
			if(formAo.getStatement().getFromPCAccount()!=null){
				tsr.setFromPlatform(formAo.getStatement().getFromPCAccount().getPlatform().getShowName());//买入商
			}
			if(toAo.getRebate()!=null){
			tsr.setToCompany_fanDian(toAo.getRebate());//卖出商 返点
			}
			if(formAo.getRebate()!=null){
			tsr.setFromCompany_fanDian(formAo.getRebate());//买入商 返点
			}
			tsr.setKueiDian(tsr.getKueiDian());//亏点
			tsr.setSubPnr(formAo.getSubPnr());//预定pnr
			tsr.setDrawPnr(formAo.getDrawPnr());//出票pnr
			tsr.setBigPnr(formAo.getBigPnr());//大pnr
			
			StringBuffer passengerName=new StringBuffer();  
			StringBuffer ticketNumber=new StringBuffer();
		    Set pasSet=	formAo.getPassengers();
		    int pNum=0;
		    for(Object obj:pasSet){
		    	
		    	Passenger pass=(Passenger)obj;
		    	passengerName.append(pNum<pasSet.size()-1?pass.getName()+"/":pass.getName());
		    	ticketNumber.append(pNum<pasSet.size()-1?pass.getTicketNumber()+"/":pass.getTicketNumber());
		    	pNum++;
		    }
		    tsr.setPassengerName(passengerName.toString());//乘客姓名
		    tsr.setTicketNumber(ticketNumber.toString());//票号
		   
		    Set fliSet=	formAo.getFlights();
		    for(Object obj:fliSet){
		    	Flight flight=(Flight)obj;
		    	tsr.setStartPoint(flight.getStartPoint());//出发地
		    	tsr.setEndPoint(flight.getEndPoint());  //目的地
		    	tsr.setCyr(flight.getCyr());//承运人
		    	tsr.setFlightCode(flight.getFlightCode());//航班号
		    	tsr.setFlightClass(flight.getFlightClass());//仓位
		    	tsr.setDiscount(flight.getDiscount());//折扣discount
		    	tsr.setBoardingTime(flight.getBoardingTime());//起飞时间
		    	
		    }
		   
		    if(pasSet!=null&&pasSet.size()>0){
		    tsr.setPassengerNumber(pasSet.size());//乘客人数
		    formAo.setAdultCount(Long.valueOf(pasSet.size()));
		    }else{
		    	tsr.setPassengerNumber(0);	
		    }
		    
		    tsr.setTicketPrice(formAo.getTicketPrice());//单张票面价
		    tsr.setAirportPrice(formAo.getAirportPrice());//单张机建税
		    tsr.setFuelPrice(formAo.getFuelPrice());//单张燃油税
		    
		    tsr.setAllTicketPrice(formAo.getAllTotlePrice());//票面总价
		    tsr.setAllAirportPrice(formAo.getAllAirportPrice());//总机建税
		    tsr.setAllFuelPrice(formAo.getAllFuelPrice());//总燃油税
			
		    
			tsr.setToAirOrderNo(toAo.getAirOrderNo());//卖出商订单号
			if(toAo.getStatement()!=null&&toAo.getStatement().getTotalAmount()!=null){
			tsr.setRealIncome(toAo.getStatement().getTotalAmount());//实际收入
			tsr.setReportIncome(toAo.getStatement().getTotalAmount());//报表收入
			}else{
				tsr.setRealIncome(new BigDecimal(0));
				tsr.setReportIncome(new BigDecimal(0));//报表收入
			}
		
			if(toAo.getStatement().getToPCAccount()!=null){
				//System.out.println("/收款帐号"+toAo.getStatement().getToPCAccount().getAccount().getName());
			tsr.setToPCAccount(toAo.getStatement().getToPCAccount().getAccount().getName());//收款帐号
			}
			tsr.setFromAirOrderNo(formAo.getAirOrderNo());//买入商订单号
			if(formAo.getStatement()!=null&&formAo.getStatement().getTotalAmount()!=null){
			tsr.setRealPayout(formAo.getStatement().getTotalAmount());//实际支出
			tsr.setReportPayout(formAo.getStatement().getTotalAmount());//报表支出
			}else{
				tsr.setRealPayout(new BigDecimal(0));
				tsr.setReportPayout(new BigDecimal(0));//报表支出
			}
			if(formAo.getStatement().getFromPCAccount()!=null){
				//System.out.println("付款帐号"+formAo.getStatement().getFromPCAccount().getAccount().getName());
			tsr.setFromPCAccount(formAo.getStatement().getFromPCAccount().getAccount().getName());//付款帐号
			}
			if(toAo.getStatement()!=null&&toAo.getStatement().getTotalAmount()!=null&&formAo.getStatement()!=null&&formAo.getStatement().getTotalAmount()!=null){
				BigDecimal  profit=toAo.getStatement().getTotalAmount().subtract(formAo.getStatement().getTotalAmount());
				tsr.setProfit(profit);//利润
			}else{
				tsr.setProfit(new BigDecimal(0));//利润
			}
		
			if(toAo.getStatement().getSysUser()!=null){
			tsr.setSysUser(toAo.getStatement().getSysUser().getUserName());//操作人
			}
			tsr.setToState(toAo.getStatusText());//供应状态
			tsr.setFromState(formAo.getStatusText());//采购状态
			tsr.setToRemark(toAo.getMemo());//供应备注
			tsr.setFromRemark(formAo.getMemo());//采购备注
			tsr.setRetireType(formAo.getRetireTypeInfo());//
			tempSaleReportsList.add(tsr);
		}
			
		}	
		return tempSaleReportsList;
			
	}
	//下载销售报表
	public ArrayList<ArrayList<Object>> downLoadsaleReports(AirticketOrderListForm alf)throws AppException
    {
		String downloadDate="";
		if(alf.getDownloadDate() == null || alf.getDownloadDate().equals(""))
			alf.setDownloadDate(com.neza.tool.DateUtil.getDateString("yyyy-MM-dd HH:mm:ss"));
		
	downloadDate=alf.getDownloadDate();
	List data=saleReportsList(alf);
	

	ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
	ArrayList<Object> list_title = new ArrayList<Object>();
	list_title.add("#销售报表");
	list_context.add(list_title);
	list_title = new ArrayList<Object>();
	list_title.add("#查询时间：");
	list_context.add(list_title);
	list_title = new ArrayList<Object>();
	list_title.add(downloadDate);
	list_context.add(list_title);
	list_title =  new ArrayList<Object>();

	list_title.add("订单操作时间");
	list_title.add("卖出商");
	list_title.add("返点");
	list_title.add("买入商");
	list_title.add("返点");
	list_title.add("亏点");
	list_title.add("预定PNR");
	list_title.add("出票PNR");
	list_title.add("大PNR");
	list_title.add("乘客姓名");
	list_title.add("乘机人数");
	list_title.add("起始地");
	list_title.add("目地的");
	list_title.add("承运人");
	list_title.add("航班号");
	list_title.add("舱位");
	list_title.add("折扣");
	list_title.add("单张票面价");
	list_title.add("票面总价");
	list_title.add("单张机建税");
	list_title.add("总机建税");
	list_title.add("单张燃油税");
	list_title.add("总燃油税");
	list_title.add("起飞时间");
	list_title.add("票号");
	list_title.add("卖出商订单号");
	list_title.add("实际收入");
	list_title.add("报表收入");
	list_title.add("收款帐号");
	list_title.add("买入商订单号");
	list_title.add("实际支出");
	list_title.add("报表支出");
	list_title.add("付款帐号");
	list_title.add("利润");
	list_title.add("操作人");
	list_title.add("供应状态");
	list_title.add("采购状态");
	list_title.add("供应备注");
	list_title.add("采购备注");

	list_context.add(list_title);
		for (int i = 0; i < data.size(); i++)
	{
		TempSaleReport tsr = (TempSaleReport) data.get(i);
		ArrayList<Object> list_context_item = new ArrayList<Object>();
		
		
		
		
		list_context_item.add(tsr.getOrderTime());//订单时间
		list_context_item.add(tsr.getToCompany());//卖出商
		list_context_item.add(tsr.getToCompany_fanDian()+"%");//卖出商 返点
		list_context_item.add(tsr.getFromCompany());//买入商
		list_context_item.add(tsr.getFromCompany_fanDian()+"%");//买入商 返点
		list_context_item.add(tsr.getKueiDian()+"%");//亏点
		list_context_item.add(tsr.getSubPnr());//预定PNR
		list_context_item.add(tsr.getDrawPnr());//出票PNR
		list_context_item.add(tsr.getBigPnr());//大PNR
		list_context_item.add(tsr.getPassengerName());//乘客姓名
		list_context_item.add(tsr.getPassengerNumber());//乘机人数
		list_context_item.add(tsr.getStartPoint());//出发地
		list_context_item.add(tsr.getEndPoint());//目的地
		list_context_item.add(tsr.getCyr());//承运人
		list_context_item.add(tsr.getFlightCode());//航班号
		list_context_item.add(tsr.getFlightClass());//仓位
		list_context_item.add(tsr.getDiscount());//折扣
		list_context_item.add(tsr.getTicketPrice());//单张票面价
		list_context_item.add(tsr.getAllTicketPrice());//票面总价
		list_context_item.add(tsr.getAirportPrice());////单张机建税
		list_context_item.add(tsr.getAllAirportPrice());//总机建税
		list_context_item.add(tsr.getFuelPrice());//单张燃油税
		list_context_item.add(tsr.getAllFuelPrice());//总燃油税
		list_context_item.add(tsr.getBoardingTime());//起飞时间
		list_context_item.add(tsr.getTicketNumber());//票号
		list_context_item.add(tsr.getToAirOrderNo());//卖出商订单号
		list_context_item.add(tsr.getRealIncome());//实际收入
		list_context_item.add(tsr.getReportIncome());//报表收入
		list_context_item.add(tsr.getToPCAccount());//收款帐号
		list_context_item.add(tsr.getFromAirOrderNo());//买入商订单号
		list_context_item.add(tsr.getRealPayout());//实际支出
		list_context_item.add(tsr.getReportPayout());//报表支出
		list_context_item.add(tsr.getFromPCAccount());//付款帐号
		list_context_item.add(tsr.getProfit());//利润
		list_context_item.add(tsr.getSysUser());//操作人
		list_context_item.add(tsr.getToState());//供应状态
		list_context_item.add(tsr.getFromState());//采购状态
		list_context_item.add(tsr.getToRemark());//供应备注
		list_context_item.add(tsr.getFromRemark());//采购备注
        System.out.println("tsr.getToRemark()====="+tsr.getToRemark());
        System.out.println("tsr.getFromRemark()====="+tsr.getFromRemark());
		list_context.add(list_context_item);
	}
		                      
	return list_context;
}

	
	
	/***************************************************************************
	 * //退废报表  sc
	 **************************************************************************/
	public List<TempSaleReport> retireReportsList(AirticketOrderListForm rlf) throws AppException{		
		List<TempSaleReport> tempSaleReportsList=new ArrayList<TempSaleReport>();
		List  groupMarkNoList =reportsDAO.saleReportsByGroupMarkNoList(rlf);
		for (int i = 0; i < groupMarkNoList.size(); i++) {
         	
			AirticketOrder groupMarkNo=(AirticketOrder)groupMarkNoList.get(i);
			System.out.println("------->"+groupMarkNo.getGroupMarkNo());
			
			List aoList=reportsDAO.getAirticketOrderListByGroupMarkNo(groupMarkNo.getGroupMarkNo(), "1,2");
			System.out.println("aoList----->"+aoList.size());
			
			if(aoList!=null&&aoList.size()>0){
		  	AirticketOrder toAo=new AirticketOrder();//卖出
			AirticketOrder formAo=new AirticketOrder();//买入
		    for(int j=0;j<aoList.size();j++){
		  
		    	if(aoList.size()==1){
		    		AirticketOrder ao=(AirticketOrder)aoList.get(j);
		    		if(ao!=null&&ao.getTranType()==1){//买入 from
		    			formAo=ao;
		    		}
		    		else if(ao!=null&&ao.getTranType()==2){
		    			toAo=ao;
		    		}
		    	}else if(aoList.size()>=2){
		    		AirticketOrder ao2=(AirticketOrder)aoList.get(j);
		    		if(ao2!=null&&ao2.getTranType()==1){
		    			formAo=ao2;
		    		}
		    		else if(ao2!=null&&ao2.getTranType()==2){
		    			toAo=ao2;
		    			
		    		}
		    	}		    		
		    }
			
			TempSaleReport tsr=new TempSaleReport();
			tsr.setOrderTime(formAo.getOptTime());//订单时间
			tsr.setFormTime(formAo.getOptTime());
			tsr.setToTime(toAo.getOptTime());
			tsr.setSysUser(toAo.getEntryOperator());
			if(toAo.getStatement().getToPCAccount()!=null){
			tsr.setToCompany(toAo.getStatement().getToPCAccount().getPlatform().getName());//卖出商
			}
			if(formAo.getStatement().getFromPCAccount()!=null){
			tsr.setFromCompany(formAo.getStatement().getFromPCAccount().getPlatform().getName());//买入商
			}
			if(toAo.getRebate()!=null){
			tsr.setToCompany_fanDian(toAo.getRebate());//卖出商 返点
			}
			if(formAo.getRebate()!=null){
			tsr.setFromCompany_fanDian(formAo.getRebate());//买入商 返点
			}
			tsr.setKueiDian(tsr.getKueiDian());//亏点
			tsr.setSubPnr(formAo.getSubPnr());//预定pnr
			tsr.setDrawPnr(formAo.getDrawPnr());//出票pnr
			tsr.setBigPnr(formAo.getBigPnr());//大pnr
			tsr.setHandlingCharge(formAo.getHandlingCharge());//手续费
			StringBuffer passengerName=new StringBuffer();  
			StringBuffer ticketNumber=new StringBuffer();
		    Set pasSet=	formAo.getPassengers();
		    int pNum=0;
		    for(Object obj:pasSet){		    	
		    	Passenger pass=(Passenger)obj;
		    	passengerName.append(pNum<pasSet.size()-1?pass.getName()+"/":pass.getName());
		    	ticketNumber.append(pNum<pasSet.size()-1?pass.getTicketNumber()+"/":pass.getTicketNumber());
		    	pNum++;
		    }
		    tsr.setPassengerName(passengerName.toString());//乘客姓名
		    tsr.setTicketNumber(ticketNumber.toString());//票号
		   
		    Set fliSet=	formAo.getFlights();
		    for(Object obj:fliSet){
		    	Flight flight=(Flight)obj;
		    	tsr.setStartPoint(flight.getStartPoint());//出发地
		    	tsr.setEndPoint(flight.getEndPoint());  //目的地
		    	tsr.setCyr(flight.getCyr());//承运人
		    	tsr.setFlightCode(flight.getFlightCode());//航班号
		    	tsr.setFlightClass(flight.getFlightClass());//仓位
		    	tsr.setDiscount(flight.getDiscount());//折扣discount
		    	tsr.setBoardingTime(flight.getBoardingTime());//起飞时间		    	
		    }
		   
		    if(pasSet!=null&&pasSet.size()>0){
		    tsr.setPassengerNumber(pasSet.size());//乘客人数
		    formAo.setAdultCount(Long.valueOf(pasSet.size()));
		    }else{
		    	tsr.setPassengerNumber(0);	
		    }
		    tsr.setToOldOrderNo(toAo.getOldOrderNo());
		    tsr.setFromOldOrderNo(formAo.getOldOrderNo());
		    tsr.setTicketPrice(formAo.getTicketPrice());//单张票面价
		    tsr.setAirportPrice(formAo.getAirportPrice());//单张机建税
		    tsr.setFuelPrice(formAo.getFuelPrice());//单张燃油税
		    
		    tsr.setAllTicketPrice(formAo.getAllTotlePrice());//票面总价
		    tsr.setAllAirportPrice(formAo.getAllAirportPrice());//总机建税
		    tsr.setAllFuelPrice(formAo.getAllFuelPrice());//总燃油税
			
		    
			tsr.setToAirOrderNo(toAo.getAirOrderNo());//卖出商订单号
			if(toAo.getStatement()!=null&&toAo.getStatement().getTotalAmount()!=null){
			tsr.setRealIncome(toAo.getStatement().getTotalAmount());//实际收入
			tsr.setReportIncome(toAo.getStatement().getTotalAmount());//报表收入
			}else{
				tsr.setRealIncome(new BigDecimal(0));
				tsr.setReportIncome(new BigDecimal(0));//报表收入
			}
		
			if(toAo.getStatement().getToPCAccount()!=null){
			tsr.setToPCAccount(toAo.getStatement().getToPCAccount().getAccount().getName());//收款帐号
			}
			tsr.setFromAirOrderNo(formAo.getAirOrderNo());//买入商订单号
			if(formAo.getStatement()!=null&&formAo.getStatement().getTotalAmount()!=null){
			tsr.setRealPayout(formAo.getStatement().getTotalAmount());//实际支出
			tsr.setReportPayout(formAo.getStatement().getTotalAmount());//报表支出
			}else{
				tsr.setRealPayout(new BigDecimal(0));
				tsr.setReportPayout(new BigDecimal(0));//报表支出
			}
			if(formAo.getStatement().getFromPCAccount()!=null){
			tsr.setFromPCAccount(formAo.getStatement().getFromPCAccount().getAccount().getName());//付款帐号
			}
			if(toAo.getStatement()!=null&&toAo.getStatement().getTotalAmount()!=null&&formAo.getStatement()!=null&&formAo.getStatement().getTotalAmount()!=null){
				BigDecimal  profit=toAo.getStatement().getTotalAmount().subtract(formAo.getStatement().getTotalAmount());
				tsr.setProfit(profit);//利润
			}else{
				tsr.setProfit(new BigDecimal(0));//利润
			}
		
			if(toAo.getStatement().getSysUser()!=null){
				tsr.setPayOperator(toAo.getStatement().getSysUser().getUserName());//操作人
			}
			tsr.setToState(toAo.getStatusText());//供应状态
			tsr.setFromState(formAo.getStatusText());//采购状态
			tsr.setToRemark(toAo.getMemo());//供应备注
			tsr.setFromRemark(formAo.getMemo());//采购备注
			tsr.setRetireType(formAo.getRetireTypeInfo());
			tempSaleReportsList.add(tsr);
		}
			
		}	
		return tempSaleReportsList;
			
	}
	
	/***************************************************************************
	 * 下载退废报表  sc
	 **************************************************************************/
	public ArrayList<ArrayList<Object>> downLoadRetireReports(AirticketOrderListForm alf)throws AppException
    {
		String downloadDate="";
		if(alf.getDownloadDate() == null || alf.getDownloadDate().equals(""))
			alf.setDownloadDate(com.neza.tool.DateUtil.getDateString("yyyy-MM-dd HH:mm:ss"));
		
	downloadDate=alf.getDownloadDate();
	List data=saleReportsList(alf);
	

	ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
	ArrayList<Object> list_title = new ArrayList<Object>();
	list_title.add("#退废报表");
	list_context.add(list_title);
	list_title = new ArrayList<Object>();
	list_title.add("#查询时间：");
	list_context.add(list_title);
	list_title = new ArrayList<Object>();
	list_title.add(downloadDate);
	list_context.add(list_title);
	list_title =  new ArrayList<Object>();

	list_title.add("提交时间");
	list_title.add("操作人");
	list_title.add("PNR");
	list_title.add("出票PNR");
	list_title.add("大PNR");
	list_title.add("客户姓名");
	list_title.add("退票人数");
	list_title.add("卖出商");
	list_title.add("订单号");
	list_title.add("原始订单号");
	list_title.add("买入商");
	list_title.add("订单号");
	list_title.add("原始订单号");
	list_title.add("票号");
	list_title.add("起始地");
	list_title.add("目的地");
	list_title.add("航班号");
	list_title.add("起飞时间");
	list_title.add("退废类别");
	list_title.add("手续费");
	list_title.add("收款时间");
	list_title.add("实收金额");
	list_title.add("应收金额");
	list_title.add("出票付款金额");
	list_title.add("出票人数");
	list_title.add("单张票面价");
	list_title.add("票面总价");
	list_title.add("单张机建税");
	list_title.add("总机建税");
	list_title.add("单张燃油税");
	list_title.add("总燃油税");
	list_title.add("承运人");
	list_title.add("仓位");
	list_title.add("收款帐号");
	list_title.add("退款时间");
	list_title.add("退款金额");
	list_title.add("退款帐号");
	list_title.add("退款操作人");
	list_title.add("卖出状态");
	list_title.add("买入状态");
	list_title.add("卖出备注");
	list_title.add("买入备注");

	list_context.add(list_title);
		for (int i = 0; i < data.size(); i++)
	{
		TempSaleReport tsr = (TempSaleReport) data.get(i);
		ArrayList<Object> list_context_item = new ArrayList<Object>();
		list_context_item.add(tsr.getEntryOrderDate());//订单时间
		list_context_item.add(tsr.getSysUser());//操作人
		list_context_item.add(tsr.getSubPnr());//预定PNR
		list_context_item.add(tsr.getDrawPnr());//出票PNR
		list_context_item.add(tsr.getBigPnr());//大PNR
		list_context_item.add(tsr.getPassengerName());//乘客姓名
		list_context_item.add(tsr.getPassengerNumber());//乘机人数
		list_context_item.add(tsr.getToCompany());//卖出商
		list_context_item.add(tsr.getToAirOrderNo());//卖出商订单号
		list_context_item.add(tsr.getToOldOrderNo());//原始订单号
		list_context_item.add(tsr.getFromCompany());//买入商
		list_context_item.add(tsr.getFromAirOrderNo());//买入商订单号
		list_context_item.add(tsr.getFromOldOrderNo());//原始订单号
		list_context_item.add(tsr.getTicketNumber());//票号
		list_context_item.add(tsr.getStartPoint());//出发地
		list_context_item.add(tsr.getEndPoint());//目的地
		list_context_item.add(tsr.getFlightCode());//航班号
		list_context_item.add(tsr.getBoardingTime());//起飞时间
		list_context_item.add(tsr.getRetireType());//退废类别
		list_context_item.add(tsr.getHandlingCharge());//手续费
		list_context_item.add(tsr.getToTime());//收款时间
		list_context_item.add(tsr.getRealIncome());//实际收入
		list_context_item.add(tsr.getRealIncome());//应收金额
		list_context_item.add(tsr.getRealPayout());//出票付款金额
		list_context_item.add(tsr.getPassengerNumber());//出票人数
		list_context_item.add(tsr.getTicketPrice());//单张票面价
		list_context_item.add(tsr.getAllTicketPrice());//票面总价
		list_context_item.add(tsr.getAirportPrice());////单张机建税
		list_context_item.add(tsr.getAllAirportPrice());//总机建税
		list_context_item.add(tsr.getFuelPrice());//单张燃油税
		list_context_item.add(tsr.getAllFuelPrice());//总燃油税
		list_context_item.add(tsr.getCyr());//承运人
		list_context_item.add(tsr.getFlightClass());//仓位
		list_context_item.add(tsr.getToPCAccount());//收款帐号
		list_context_item.add(tsr.getFormTime());//退款时间
		list_context_item.add(tsr.getRealPayout());//退款金额
		list_context_item.add(tsr.getFromPCAccount());//付款帐号
		list_context_item.add(tsr.getPayOperator());//退款操作人
		list_context_item.add(tsr.getToState());//供应状态
		list_context_item.add(tsr.getFromState());//采购状态
		list_context_item.add(tsr.getToRemark());//供应备注
		list_context_item.add(tsr.getFromRemark());//采购备注
		
		list_context.add(list_context_item);
	}
		                      
	return list_context;
}
	//导出团队机票销售报表
	public ArrayList<ArrayList<Object>> downloadTeamAirTicketOrder(AirticketOrderListForm rlf,List<TeamAirticketOrderReport> rePortlist)throws AppException
    {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
		ArrayList<Object> list_title = new ArrayList<Object>();
		rlf.setPerPageNum(10000);//设制分页显示数据
		rlf.setProxy_price(-1);
		List list  = reportsDAO.getTeamAirTicketOrderList(rlf);
		list_title.add("");
		list_title.add("出票日期");
		list_title.add("客票类型");
		list_title.add("承运人");
		list_title.add("航段");
		list_title.add("张数");
		list_title.add("订单号");
		list_title.add("航班日期");
		list_title.add("航班号");
		list_title.add("舱位");
		list_title.add("折扣");
		list_title.add("票面价");
		list_title.add("购票客户");
		list_title.add("收退票手续费");
		list_title.add("收票款");
		list_title.add("机场税");
		list_title.add("应付票款");
		list_title.add("实付退票手续费");
		list_title.add("实付款");
		list_title.add("确认支付金额");
		list_title.add("支付账号");
		list_title.add("支付备注");
		list_title.add("支付时间");
		list_title.add("支付人");
		list_title.add("月底返代理费");
		list_title.add("团毛利润");
		list_title.add("退票利润");
		list_title.add("多收票款");
		list_title.add("多收税");
		list_title.add("现返");
		list_title.add("未返");
		list_title.add("未返备注");
		list_title.add("净利合计");
		list_title.add("购票客户");
		list_title.add("总金额");
		list_title.add("操作人");
		list_context.add(list_title);
		ArrayList<Object> list_context_item = new ArrayList<Object>();
		
		//合计
		long personNum = 0;//张数
		double ticketNum = 0;//票面价
		double incomeretreat_charge = 0;//收退票手续费
		double incomeTicketPrice = 0;//收票款
		double airportTaxInfo = 0;//机场税//应付票款//实付退票手续费
		double copeTicketprice = 0;//应付票款
		double actual_incomeretreat_charge = 0;//实付退票手续费
		double paidPrice = 0;//实付款
		double confirm_payment_Price = 0;//确认支付金额
		double agentFeeCarrierInfo = 0;// 月底返代理费
		double profitsInfo = 0;// 团毛利润
		double refundProfit = 0;//退票利润 
		double amountMore = 0;// 多收票款
		double taxMore = 0;// 多收税
		double commission = 0;//现返
		double unsettledAccount = 0;//未返
		double pureProfits = 0;//净利合计
		double totalProce = 0;//总金额
		//long discount=0;//折扣
		
		for(int i=0;i<list.size();i++)
		{
			TeamAirticketOrderReport t = rePortlist.get(i);
			list_context_item = new ArrayList<Object>();
			
			list_context_item.add("");
			if(t.getEntry_time() !=null)
			{
				list_context_item.add(t.getEntry_time().toString().substring(5,10));//出票日期
			}else
			{
				list_context_item.add("");
			}
			list_context_item.add(t.getAgentTypeInfo());////客票类型
			list_context_item.add(t.getCarrier());//承运人
			list_context_item.add(t.getStartPoint()+"/"+t.getEndPoing());
			list_context_item.add(t.getTotalPersonInfo());//张数
			list_context_item.add(t.getAirticketNo());//订单号
			list_context_item.add(t.getFlightTime());//航班日期
			list_context_item.add(t.getFlightCode());//航班号
			list_context_item.add(t.getFlightClass());//舱位
			list_context_item.add(t.getDiscount());//折扣
			list_context_item.add(t.getTicketPrice());//票面价
			list_context_item.add(t.getAgentName());//购票客户
			list_context_item.add(t.getIncomeretreat_charge());//收退票手续费
			list_context_item.add(t.getIncomeTicketPrice());//收票款
			list_context_item.add(t.getAirportTaxInfo());//机场税
			list_context_item.add(t.getCopeTicketprice());//应付票款
			list_context_item.add(t.getActual_incomeretreat_charge());//实付退票手续费
			list_context_item.add(t.getPaidPrice());//实付款
			list_context_item.add(t.getConfirm_payment_Price());//确认支付金额
			list_context_item.add(t.getAccountNo());//支付账号
			list_context_item.add(t.getPaymentMemo());//支付备注
			if(t.getPay_Time() !=null)
			{
				list_context_item.add(t.getPay_Time().toString().substring(0, 10));//收付款时间
			}else
			{
				list_context_item.add("");
			}
			list_context_item.add(t.getPaymentName());//支付人
			list_context_item.add(t.getAgentFeeCarrierInfo());// 月底返代理费
			list_context_item.add(t.getProfitsInfo());// 团毛利润
			list_context_item.add(t.getRefundProfit());//退票利润 
			list_context_item.add(t.getAmountMore());// 多收票款
			list_context_item.add(t.getTaxMore());// 多收税
			list_context_item.add(t.getCommission());//现返
			list_context_item.add(t.getUnsettledAccount());//未返
			list_context_item.add(t.getUnsettledMome());//未返备注
			list_context_item.add(t.getPureProfits());//净利合计
			list_context_item.add(t.getAgentName());//购票客户
			list_context_item.add(t.getTotalProce());//总金额
			list_context_item.add(t.getSysName());//操作人
			list_context.add(list_context_item);
			
			//累加
			 personNum+=t.getTotalPersonInfo();//张数
			 ticketNum+=t.getTicketPrice().doubleValue();//票面价
			 incomeretreat_charge+=t.getIncomeretreat_charge().doubleValue();////收退票手续费
			 incomeTicketPrice+=t.getIncomeTicketPrice().doubleValue();//收票款
			 airportTaxInfo+=t.getAirportTaxInfo().doubleValue();//机场税
			 copeTicketprice+=t.getCopeTicketprice().doubleValue();//应付票款
			 actual_incomeretreat_charge+=t.getActual_incomeretreat_charge().doubleValue();//实付退票手续费
			 paidPrice+=t.getPaidPrice().doubleValue();//实付款
		 	 confirm_payment_Price+=t.getConfirm_payment_Price().doubleValue();//确认支付金额
			 agentFeeCarrierInfo+=t.getAgentFeeCarrierInfo().doubleValue();// 月底返代理费
			 profitsInfo += t.getProfitsInfo().doubleValue();// 团毛利润
			 refundProfit += t.getRefundProfit().doubleValue();//退票利润 
			 amountMore += t.getAmountMore().doubleValue();// 多收票款
			 taxMore += t.getTaxMore().doubleValue();// 多收税
			 commission += t.getCommission().doubleValue();//现返
			 unsettledAccount += t.getUnsettledAccount().doubleValue();//未返
			 pureProfits += t.getPureProfits().doubleValue();//净利合计
			 totalProce += t.getTotalProce().doubleValue();//总金额
			// discount += Long.parseLong(t.getDiscount());//折扣
		}
		//合计
		list_context_item = new ArrayList<Object>();
		list_context_item.add("合 计");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add(personNum);//张数
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");//折扣
		list_context_item.add(ticketNum);//票面价
		list_context_item.add("");
		list_context_item.add(incomeretreat_charge);////收退票手续费
		list_context_item.add(incomeTicketPrice);//收票款
		list_context_item.add(airportTaxInfo);//机场税
		list_context_item.add(copeTicketprice);//应付票款
		list_context_item.add(actual_incomeretreat_charge);//实付退票手续费
		list_context_item.add(paidPrice);//实付款
		list_context_item.add(confirm_payment_Price);//确认支付金额
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add("");
		list_context_item.add(agentFeeCarrierInfo);//月底返代理费
		list_context_item.add(profitsInfo);// 团毛利润
		list_context_item.add(refundProfit);//退票利润
		list_context_item.add(amountMore);// 多收票款
		list_context_item.add(taxMore);// 多收税
		list_context_item.add(commission);//现返
		list_context_item.add(unsettledAccount);//未返
		list_context_item.add("");
		list_context_item.add(pureProfits);//净利合计
		list_context_item.add("");
		list_context_item.add(totalProce);//总金额
		list_context_item.add("");
		list_context.add(list_context_item);
		
		return list_context;
	}

	
	//赋值给TeamAirticketOrderReport类
	public List<TeamAirticketOrderReport> getTeamAirticketOrderReport(AirticketOrderListForm rlf) throws AppException
	{
		List<TeamAirticketOrderReport> resultList=new ArrayList<TeamAirticketOrderReport>();
		List<AirticketOrder> airticketOrderList= reportsDAO.getTeamAirTicketOrderList(rlf);
		for(int i=0;i<airticketOrderList.size();i++)
		{
			AirticketOrder teamAirticketOrder = airticketOrderList.get(i);
			List<AirticketOrder> teamAirticketOrderList =airticketOrderDAO.getAirticketOrderListByGroupMarkNo(teamAirticketOrder.getGroupMarkNo());
			
				AirticketOrder teamAirticket1 = new AirticketOrder();//买入
				AirticketOrder teamAirticket2 = new AirticketOrder();//卖出
				
					for(int j=0;j<teamAirticketOrderList.size();j++)
					{
						if(teamAirticketOrderList.size() ==1)
						{
							AirticketOrder ato1 =teamAirticketOrderList.get(j);
							if(ato1 != null  && ato1.getTranType() == AirticketOrder.TRANTYPE__2)//买入
							{
								teamAirticket1=ato1;
							}
							else if(ato1 != null  && ato1.getTranType() == AirticketOrder.TRANTYPE__1)//卖出
							{
								teamAirticket2=ato1;
							}
						}else if(teamAirticketOrderList.size() >=2)
						{
							AirticketOrder ato2 =teamAirticketOrderList.get(j);
							if(ato2 != null && ato2.getTranType() == AirticketOrder.TRANTYPE__2)//买入
							{
								teamAirticket1=ato2;
							}
							else if(ato2 != null && ato2.getTranType() == AirticketOrder.TRANTYPE__1)//卖出
							{
								teamAirticket2=ato2;
							}
						}
					}
					TeamAirticketOrderReport t = new TeamAirticketOrderReport();
					//t.setOPtime(teamAirticket1.getOptTime());//出票日期
					t.setEntry_time(teamAirticket1.getEntryTime().toString());//录单时间
					if(teamAirticket1.getPayTime() !=null)
					{
						t.setPay_Time(teamAirticket1.getPayTime().toString());//收付款时间
					}else
					{
						t.setPay_Time(null);//收付款时间
					}
					if(teamAirticket1 !=null)
					{
						if(teamAirticket1.getAgent() !=null)
						{
							t.setAgentType(teamAirticket1.getAgent().getType());//客票类型
							t.setAgentName(teamAirticket1.getAgent().getName());//购票客户
						}
					}
					
					 Set fliSet=teamAirticket1.getFlights();
					  StringBuffer startPoint=new StringBuffer();
					  StringBuffer endPoint = new StringBuffer();
					  StringBuffer boarding_time = new StringBuffer();
					  StringBuffer flightCode=new StringBuffer();
					  StringBuffer flightClass = new StringBuffer();
					  StringBuffer discount = new StringBuffer();
					  StringBuffer cyr =new StringBuffer();
					  int num=0;
					 for(Object obj:fliSet)
					 {
						 Flight flight = (Flight) obj;
						 cyr.append(num<fliSet.size()-1?flight.getCyr()+"/":flight.getCyr());
						 startPoint.append(num<fliSet.size()-1?flight.getStartPoint()+"-":flight.getStartPoint());
						 endPoint.append(num<fliSet.size()-1?flight.getEndPoint()+"-":flight.getEndPoint());
						 boarding_time.append(num<fliSet.size()-1?flight.getBoardingTime().toString().subSequence(0, 10)+"/":flight.getBoardingTime().toString().subSequence(0, 10));
						 flightCode.append(num<fliSet.size()-1?flight.getFlightCode()+"/":flight.getFlightCode());
						 flightClass.append(num<fliSet.size()-1?flight.getFlightClass()+"/":flight.getFlightClass());
						 discount.append(num<fliSet.size()-1?flight.getDiscount()+"/":flight.getDiscount());
						 num++;
						
					 }
					 t.setAdult_count(teamAirticket1.getAdultCount());//成人数
					 t.setChild_count(teamAirticket1.getChildCount());//儿童数
					 t.setBaby_count(teamAirticket1.getBabyCount());//婴儿数
					 t.setStartPoint(startPoint.toString());//出发地
					 t.setEndPoing(endPoint.toString());//目的地
					 t.setFlightTime(boarding_time.toString());//航班日期
					 t.setFlightCode(flightCode.toString());//航班号
					 t.setFlightClass(flightClass.toString());//舱位
					 t.setDiscount(discount.toString());//折扣
					 t.setCarrier(cyr.toString());//承运人
					
					 
					 if(teamAirticket2 !=null)
					 {
						 //结算表数据
						 if(teamAirticket2.getStatement() !=null)
							{
								t.setCommission(teamAirticket2.getStatement().getCommission());//现返
								t.setUnsettledAccount(teamAirticket2.getStatement().getUnsettledAccount());//未返
								t.setUnsettledMome(teamAirticket2.getMemo());//未返备注
								t.setTotalProce(teamAirticket2.getStatement().getTotalAmount());//总金额
								if(teamAirticket2.getStatement().getSysUser() !=null)
								{
									t.setSysName(teamAirticket2.getStatement().getSysUser().getUserName());//操作人
								}
							}
						 t.setAirticketNo(teamAirticket2.getAirOrderNo());//订单号
						 t.setIncomeretreat_charge(teamAirticket2.getIncomeretreatCharge());//收退票手续费
						 if(teamAirticket2.getStatement() !=null)
						 {
							 t.setIncomeTicketPrice(teamAirticket2.getStatement().getActualAmount());//收票款
						 }
						 if(teamAirticket1.getStatement() !=null)
						 {
							 t.setConfirm_payment_Price(teamAirticket1.getStatement().getActualAmount());//确认付款金额
						 }
						
						 t.setAmountMore(teamAirticket2.getOverTicketPrice());//多收票款
						 t.setTaxMore(teamAirticket2.getOverAirportfulePrice());//多收税
						 t.setTicketPrice(teamAirticket2.getTotalTicketPrice());//票面价
						 t.setProxy_price(teamAirticket2.getProxyPrice());//应付出团代理费（未返）
						 //机场税计算
						 t.setTotal_airport_price(teamAirticket2.getTotalAirportPrice());//机建
						 t.setTotal_fuel_price(teamAirticket2.getTotalFuelPrice());//燃油
						 
						 if(teamAirticket1.getStatement() !=null)
						 {
							 if(teamAirticket1.getStatement().getFromPCAccount() !=null)
							 {
								 if(teamAirticket1.getStatement().getFromPCAccount().getAccount() !=null)
								 {
									 t.setAccountNo(teamAirticket1.getStatement().getFromPCAccount().getAccount().getName());//支付账号(显示付款账号)
									 t.setPaymentMemo(teamAirticket1.getMemo());//支付备注
								 }
							 }
						 }
							if(teamAirticket2.getStatement().getSysUser() !=null)
							{
								t.setPaymentName(teamAirticket2.getStatement().getSysUser().getUserName());//支付人
							}
							 
						 //付款---航空公司
						 if(teamAirticket1 !=null)
						 {
							//计算
							 t.setCommisson_count(teamAirticket1.getCommissonCount());//返点
							 t.setHandling_charge(teamAirticket1.getHandlingCharge());//手续费
							 t.setRakeoff_count(teamAirticket1.getRakeoffCount());//后返点
						
							 t.setActual_incomeretreat_charge(teamAirticket1.getIncomeretreatCharge());//实付退票手续费
							 BigDecimal ticketPrice=teamAirticket2.getTotalTicketPrice();
							 BigDecimal copeProxy_Price= (ticketPrice.add(teamAirticket2.getOverTicketPrice()).multiply(teamAirticket2.getCommissonCount()));//应付出团代理费（现返）
							 if(teamAirticket1.getCommissonCount() !=null)
							 {
								 BigDecimal comminssonCount =ticketPrice.multiply(teamAirticket1.getCommissonCount()).subtract(teamAirticket1.getHandlingCharge());
							 
							 BigDecimal copeTicketPrice =ticketPrice.subtract(comminssonCount).add(teamAirticket1.getIncomeretreatCharge());
							 BigDecimal refundProfit =teamAirticket1.getIncomeretreatCharge().subtract(teamAirticket2.getIncomeretreatCharge());
							 t.setAgentFeeCarrier(ticketPrice.multiply(teamAirticket1.getRakeoffCount()));// 月底返代理费
							 t.setProfits(comminssonCount);// 团毛利润
							 t.setCopeTicketprice(copeTicketPrice);//应付票款
							 t.setPaidPrice(copeTicketPrice.add(t.getAirportTaxInfo()));//实付款
							 t.setRefundProfit(refundProfit);//退票利润
							 t.setPureProfits(comminssonCount.add(refundProfit).add(teamAirticket2.getOverTicketPrice()).
									 add(teamAirticket2.getOverAirportfulePrice()).subtract(copeProxy_Price).
									 subtract(teamAirticket2.getProxyPrice()));//净利合计= 团毛利润 + 退票利润 + 多收票款 + 多收税款 –应付出团代理费(现返)-应付出团代理费(未返)
							 }
						 }
					 }
					resultList.add(t);
		}
		return resultList;
	}
	
	//导出团队未返代理费报表
	public ArrayList<ArrayList<Object>> downloadTeamNotReturnProxy(AirticketOrderListForm rlf,List<TeamAirticketOrderReport> rePortlist)throws AppException
    {
		ArrayList<ArrayList<Object>> list_context = new ArrayList<ArrayList<Object>>(); 
		ArrayList<Object> list_title = new ArrayList<Object>();
		rlf.setPerPageNum(10000);//设制分页显示数据条数
		
		rlf.setProxy_price(1);
		List list  = reportsDAO.getTeamAirTicketOrderList(rlf);
		list_title.add("出票日期");
		list_title.add("客票类型");
		list_title.add("承运人");
		list_title.add("航段");
		list_title.add("张数");
		list_title.add("订单号");
		list_title.add("航班日期");
		list_title.add("航班号");
		list_title.add("舱位");
		list_title.add("折扣");
		list_title.add("票面价");
		list_title.add("团队加价");
		list_title.add("客户加价");
		list_title.add("购票客户");
		list_title.add("未返");
		list_title.add("未返备注");
		list_title.add("收客人票款");
		list_title.add("操作人");
		list_context.add(list_title);
		for(int i=0;i<list.size();i++)
		{
			AirticketOrder airticketOrder = (AirticketOrder)list.get(i);
			Flight flight= flightDAO.getFlightByAirticketOrderID(airticketOrder.getId());
			
			Statement statement =statementDAO.getStatementById(airticketOrder.getStatement().getId());
			TeamAirticketOrderReport t = rePortlist.get(i);
			ArrayList<Object> list_context_item = new ArrayList<Object>();
			if(t.getOPtime() !=null)
			{
				list_context_item.add(t.getEntry_time().toString().substring(0, 10));//出票日期
			}else
			{
				list_context_item.add("");//出票日期
			}
			list_context_item.add(t.getAgentTypeInfo());////客票类型
			list_context_item.add(t.getCarrier());//承运人
			list_context_item.add(t.getStartPoint()+"/"+t.getEndPoing());
			list_context_item.add(t.getTotalPersonInfo());//张数
			list_context_item.add(t.getAirticketNo());//订单号
			list_context_item.add(t.getFlightTime());//航班日期
			list_context_item.add(t.getFlightCode());//航班号
			list_context_item.add(t.getFlightClass());//舱位
			list_context_item.add(t.getDiscount());//折扣
			list_context_item.add(t.getTicketPrice());//票面价
			list_context_item.add(t.getTeamAddPrice());//团队加价
			list_context_item.add(t.getAgentAddPrice());//客户加价
			list_context_item.add(t.getAgentName());//购票客户
			list_context_item.add(t.getUnsettledAccount());//未返
			list_context_item.add(t.getUnsettledMome());//未返备注
			list_context_item.add(t.getGuestTickPrice());//收客人票款
			list_context_item.add(t.getSysName());//操作人
			
			list_context.add(list_context_item);
			
		}
		return list_context;
	}
	
	//团队未返代理费报表（将数据赋给TeamAirticketOrderReport类）
	public List<TeamAirticketOrderReport> getTeamNotReturnProxy(AirticketOrderListForm rlf) throws AppException
	{
		List<TeamAirticketOrderReport> resultList=new ArrayList<TeamAirticketOrderReport>();
		List<AirticketOrder> airticketOrderList= reportsDAO.getTeamAirTicketOrderList(rlf);
		for(int i=0;i<airticketOrderList.size();i++)
		{
			AirticketOrder teamAirticketOrder = airticketOrderList.get(i);
			List<AirticketOrder> teamAirticketOrderList =airticketOrderDAO.getAirticketOrderListByGroupMarkNo(teamAirticketOrder.getGroupMarkNo());
			
				AirticketOrder teamAirticket1 = new AirticketOrder();//买入
				AirticketOrder teamAirticket2 = new AirticketOrder();//卖出
				
					for(int j=0;j<teamAirticketOrderList.size();j++)
					{
						if(teamAirticketOrderList.size() ==1)
						{
							AirticketOrder ato1 =teamAirticketOrderList.get(j);
							if(ato1 != null  && ato1.getTranType() == AirticketOrder.TRANTYPE__2)//买入
							{
								teamAirticket1=ato1;
							}
							else if(ato1 != null  && ato1.getTranType() == AirticketOrder.TRANTYPE__1)//卖出
							{
								teamAirticket2=ato1;
							}
						}else if(teamAirticketOrderList.size() >=2)
						{
							AirticketOrder ato2 =teamAirticketOrderList.get(j);
							if(ato2 != null && ato2.getTranType() == AirticketOrder.TRANTYPE__2)//买入
							{
								teamAirticket1=ato2;
							}
							else if(ato2 != null && ato2.getTranType() == AirticketOrder.TRANTYPE__1)//卖出
							{
								teamAirticket2=ato2;
							}
						}
					}
					TeamAirticketOrderReport t = new TeamAirticketOrderReport();
					//t.setOPtime(teamAirticket1.getOptTime());//出票日期
					if(teamAirticket1.getEntryTime() !=null)
					{
						t.setEntry_time(teamAirticket1.getEntryTime().toString());//录单时间
					}else
					{
						t.setEntry_time(null);//录单时间
					}
					if(teamAirticket1 !=null)
					{
						if(teamAirticket1.getAgent() !=null)
						{
							t.setAgentType(teamAirticket1.getAgent().getType());//客票类型
							t.setAgentName(teamAirticket1.getAgent().getName());//购票客户
						}
					}
					
					 Set fliSet=teamAirticket1.getFlights();
					  StringBuffer startPoint=new StringBuffer();
					  StringBuffer endPoint = new StringBuffer();
					  StringBuffer boarding_time = new StringBuffer();
					  StringBuffer flightCode=new StringBuffer();
					  StringBuffer flightClass = new StringBuffer();
					  StringBuffer discount = new StringBuffer();
					  StringBuffer cyr =new StringBuffer();
					  int num=0;
						 for(Object obj:fliSet)
						 {
							 Flight flight = (Flight) obj;
							 startPoint.append(num<fliSet.size()-1?flight.getStartPoint()+"-":flight.getStartPoint());
							 endPoint.append(num<fliSet.size()-1?flight.getEndPoint()+"-":flight.getEndPoint());
							 boarding_time.append(num<fliSet.size()-1?flight.getBoardingTime().toString().subSequence(0, 10)+"/":flight.getBoardingTime().toString().subSequence(0, 10));
							 flightCode.append(num<fliSet.size()-1?flight.getFlightCode()+"/":flight.getFlightCode());
							 flightClass.append(num<fliSet.size()-1?flight.getFlightClass()+"/":flight.getFlightClass());
							 discount.append(num<fliSet.size()-1?flight.getDiscount()+"/":flight.getDiscount());
							 cyr.append(num<fliSet.size()-1?flight.getCyr()+"/":flight.getCyr());
							 num++;
							
						 }
						 if(teamAirticket2.getAdultCount() !=null)
						 {
							 t.setAdult_count(teamAirticket2.getAdultCount());//成人数
						 }
						 if(teamAirticket2.getChildCount() !=null)
						 {
							 t.setChild_count(teamAirticket2.getChildCount());//儿童数
						 }
						 if(teamAirticket2.getBabyCount() !=null)
						 {
							 t.setBaby_count(teamAirticket2.getBabyCount());//婴儿数
						 }
					 t.setStartPoint(startPoint.toString());//出发地
					 t.setEndPoing(endPoint.toString());//目的地
					 t.setAirticketNo(teamAirticket2.getAirOrderNo());//订单号
					 t.setFlightTime(boarding_time.toString());//航班日期
					 t.setFlightCode(flightCode.toString());//航班号
					 t.setFlightClass(flightClass.toString());//舱位
					 t.setDiscount(discount.toString());//折扣
					 t.setTicketPrice(teamAirticket2.getTotalTicketPrice());//票面价
					 t.setTeamAddPrice(teamAirticket2.getTeamaddPrice());//团队加价
					 t.setAgentAddPrice(teamAirticket2.getAgentaddPrice());//客户加价
					 t.setCarrier(cyr.toString());//承运人
					 if(teamAirticket2.getStatement() !=null)
					 {
						 t.setUnsettledAccount(teamAirticket2.getProxyPrice());//未返(应付出团代理费--未返)
						 t.setUnsettledMome(teamAirticket2.getMemo());//未返备注
						 t.setGuestTickPrice(teamAirticket2.getStatement().getTotalAmount());//收客人票款(暂时放的是结算表中的总金额)
						 
						 if(teamAirticket2.getStatement().getSysUser() !=null)
							{
								t.setSysName(teamAirticket2.getStatement().getSysUser().getUserName());//操作人
							}
					 }
					 resultList.add(t);
		}
			return resultList;
	}
	

	

	public List saleReportsByGroupMarkNoList(AirticketOrderListForm rlf) throws AppException{
		
		return reportsDAO.saleReportsByGroupMarkNoList(rlf);
	}
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo,String tranType) throws AppException{
		return reportsDAO.getAirticketOrderListByGroupMarkNo(groupMarkNo, tranType);
	}

	public List getPayment_toolList(long type) throws AppException{
		return reportsDAO.getPayment_toolList(type);
	}

	public void setReportsDAO(ReportsDAO reportsDAO) {
		this.reportsDAO = reportsDAO;
	}

	public void setAgentDAO(AgentDAO agentDAO) {
		this.agentDAO = agentDAO;
	}

	public void setAirticketOrderDAO(AirticketOrderDAO airticketOrderDAO) {
		this.airticketOrderDAO = airticketOrderDAO;
	}
	
	public void setFlightDAO(FlightDAO flightDAO) {
		this.flightDAO = flightDAO;
	}

	public void setStatementDAO(StatementDAO statementDAO) {
		this.statementDAO = statementDAO;
	}
}
