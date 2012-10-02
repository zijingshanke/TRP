package com.fdays.tsms.airticket.dao;

import java.util.List;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.fdays.tsms.transaction.Statement;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportsDAOImp extends BaseDAOSupport implements ReportsDAO{
	//销售报表
	public List saleReportsByGroupMarkNoList(AirticketOrderListForm rlf) throws AppException {
		Hql hql = new Hql();
		hql.add("select new com.fdays.tsms.airticket.AirticketOrder(a.groupMarkNo)");
		hql.add(" from  AirticketOrder a where 1=1 and a.status not in(88) ");
        
        //多个订单状态
        if(rlf.getMoreStatus()!=null&&!"".equals(rlf.getMoreStatus().trim())){        	
        	hql.add(" and a.status  in ("+rlf.getMoreStatus()+") ");
        }
        
        //操作人
        if(rlf.getSysName()!=null&&!"".equals(rlf.getSysName().trim())){
        	hql.add(" and a.entryOperator like ? ");
        	hql.addParamter( "%" + rlf.getSysName().trim()+ "%");		
        }
        
        //按日期搜索
    	String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();
		
		if ("".equals(startDate)==false && "".equals(endDate)==true) {
			startDate=startDate+" 00:00:00";
			hql.add(" and  a.entryTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate)==true && "".equals(endDate)==false) {
			endDate=endDate+" 23:59:59";
			hql.add(" and  a.entryTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate)==false && "".equals(endDate)==false) {
			startDate=startDate+" 00:00:00";
			endDate=endDate+" 23:59:59";
			hql.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		
		
	 //根据平台查询订单  pl.id=s.toPCAccount.id or pl.id=s.fromPCAccount.id
/*	 hql.add("and exists(");
	 hql.add("from  Statement s  where 1=1");
	 
	 if(rlf.getPlatformIds()!=null || rlf.getAccountIds()!=null){
		 hql.add("and s.fromPCAccount.id in( select pl.id from PlatComAccount pl where   1=1");  
	 if(rlf.getPlatformIds()!=null&&!"".equals(rlf.getPlatformIds().trim())){
		 hql.add("  and  exists(from Platform p where p.id=pl.platform.id and p.id in ("+rlf.getPlatformIds()+"))");
	 }
	 if(rlf.getAccountIds()!=null&&!"".equals(rlf.getAccountIds().trim())){
		 hql.add("  and  exists(from Account ac where ac.id=pl.account.id and ac.id in ("+rlf.getAccountIds()+"))");
	 }
	 hql.add(")");
	 
	 hql.add("or s.toPCAccount.id in(select  pl.id from PlatComAccount pl where   1=1");  
	 if(rlf.getPlatformIds()!=null&&!"".equals(rlf.getPlatformIds().trim())){
		 hql.add("and  exists(from Platform p where p.id=pl.platform.id and p.id in ("+rlf.getPlatformIds()+"))");
	 }
	 if(rlf.getAccountIds()!=null&&!"".equals(rlf.getAccountIds().trim())){
		 hql.add("and  exists(from Account ac where ac.id=pl.account.id  and ac.id in ("+rlf.getAccountIds()+"))");
	 }
	 hql.add(")");
	 }*/
		if(rlf.getPlatformIds()!=null&&!"".equals(rlf.getPlatformIds().trim())){
		 hql.add("  and  a.platform.id  in ("+rlf.getPlatformIds()+")");  
		}
		if(rlf.getAccountIds()!=null&&!"".equals(rlf.getAccountIds().trim())){
		 hql.add("  and  a.account.id   in ("+rlf.getAccountIds()+")");
		}

	 hql.add(" and a.ticketType in(1,3) group by a.groupMarkNo order by  a.groupMarkNo desc");
	 
		List  list=  this.list(hql);
		System.out.println("sql---"+hql.toString());
		System.out.println("---list size  ---->"+list.size());
		return list;
	}
	
	//团队销售报表--lrc
	public List getTeamAirTicketOrderList(AirticketOrderListForm rlf) throws AppException{
		
		Hql hql = new Hql();
	//	hql.add("select new com.fdays.tsms.airticket.AirticketOrder(a.groupMarkNo)");
		hql.add("from AirticketOrder a where 1=1");
		//hql.add("and a.statement.id in (select s.id from Statement s where s.fromPCAccount.id="+rlf.getFromAccountId()+" or s.toPCAccount.id="+rlf.getToAccountId()+")");
		String startTime ="00:00:00";
		String endTime ="23:59:59";
		
		if(rlf.getStartDate() !=null && (!rlf.getStartDate().equals("")) && rlf.getEndDate() !=null && (!rlf.getEndDate().equals(""))) //开始-结束
		{
			hql.add("and to_char(a.entryTime,'yyyy-MM-dd HH:mm:ss') between '"+rlf.getStartDate()+" "+startTime+"' and '"+rlf.getEndDate()+" "+endTime+"'");
		}
		if(rlf.getStartDate() !=null && (!rlf.getStartDate().equals("")) && rlf.getEndDate() =="")//开始
		{
			hql.add(" and to_char(a.entryTime,'yyyy-MM-dd HH:mm:ss')='"+rlf.getStartDate()+startTime+" "+"'");
		}
		if(rlf.getEndDate() !=null && (!rlf.getEndDate().equals("")) && rlf.getStartDate() == "")//结束
		{
			hql.add(" and to_char(a.entryTime,'yyyy-MM-dd HH:mm:ss')='"+rlf.getEndDate()+" "+endTime+"'");
		}
		if(rlf.getProxy_price()>0)
		{
			hql.add(" and a.proxyPrice > 0");
		}
		if(rlf.getTranType() == AirticketOrder.TRANTYPE__2)
		{
			hql.add("and a.tranType="+AirticketOrder.TRANTYPE__2);
		}
		hql.add("and a.status in ("+AirticketOrder.STATUS_105+")");//过滤退票
		hql.add("and a.ticketType ="+AirticketOrder.TICKETTYPE_2);//团队
		//hql.add("group by a.groupMarkNo order by  a.groupMarkNo desc");
		System.out.println(hql);
		return this.list(hql);
	}
	
	//原始销售报表
	public List marketReportsList(AirticketOrderListForm rlf) throws AppException {

		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		//出票PNR
		if(rlf.getDrawPnr()!=null&&!"".equals(rlf.getDrawPnr().trim())){
			hql.add("and a.drawPnr like ?");
			hql.addParamter( "%" + rlf.getDrawPnr().trim()+ "%");	
		}
		//预定PNR
        if(rlf.getSubPnr()!=null&&!"".equals(rlf.getSubPnr().trim())){
        	hql.add("and a.subPnr like ?");
        	hql.addParamter( "%" + rlf.getSubPnr().trim()+ "%");
		}
        //大PNR
        if(rlf.getBigPnr()!=null&&!"".equals(rlf.getBigPnr().trim())){
        	hql.add("and a.bigPnr like ?");
        	hql.addParamter( "%" + rlf.getBigPnr().trim()+ "%");
		}
        //关联订单号
        if(rlf.getGroupMarkNo()!=null&&!"".equals(rlf.getGroupMarkNo().trim())){
        	hql.add("and a.groupMarkNo like ?");
        	hql.addParamter( "%" + rlf.getGroupMarkNo().trim()+ "%");
		}
        //订单号
        if(rlf.getAirOrderNo()!=null&&!"".equals(rlf.getAirOrderNo().trim())){
        	hql.add("and a.airOrderNo like ?");
        	hql.addParamter( "%" + rlf.getAirOrderNo().trim()+ "%");
		}
        //flightCode;//航班号
        if(rlf.getFlightCode()!=null&&!"".equals(rlf.getFlightCode().trim())){
        	hql.add(" and exists(from Flight f where f.flightCode like '%"+rlf.getFlightCode().trim()+"%' and f.airticketOrder.id=a.id)");
		}
        //ticketNumber;//票号
        if(rlf.getTicketNumber()!=null&&!"".equals(rlf.getTicketNumber().trim())){
        	hql.add(" and exists(from Passenger p where  p.ticketNumber like '%"+rlf.getTicketNumber().trim()+"%' and p.airticketOrder.id=a.id)");
		}
        //agentName;//乘客
        if(rlf.getAgentName()!=null&&!"".equals(rlf.getAgentName().trim())){
        	hql.add(" and exists(from Passenger p where  p.name like '%"+rlf.getAgentName().trim()+"%' and p.airticketOrder.id=a.id)");
		}
        //操作人
        if(rlf.getSysName()!=null&&!"".equals(rlf.getSysName().trim())){
        	hql.add("and a.statement.sysUser.userName like ?");
        	hql.addParamter( "%" + rlf.getSysName().trim()+ "%");		
        }
        //订单状态
        if(rlf.getAirticketOrder_status()>0){
        	hql.add("and a.status="+rlf.getAirticketOrder_status());
		}
        //机票类型
        if(rlf.getTicketType()>0){
        	hql.add("and a.ticketType="+rlf.getTicketType());
		}
        
        //平台
        if(rlf.getPlatformId()>0){        	
        	hql.add("and a.platform.id="+rlf.getPlatformId());
        }
        hql.add("order by a.groupMarkNo ,a.tranType");
		return this.list(hql, rlf);
	}
	
}
