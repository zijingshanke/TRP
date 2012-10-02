package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportDAOImp extends BaseDAOSupport implements ReportDAO{
	//销售报表
	public List getGroupIdForSaleReport(AirticketOrderListForm rlf) throws AppException {
		Hql hql = new Hql();
		hql.add("select a.orderGroup.id ");
		hql.add(" from  AirticketOrder a where 1=1 and a.status not in(88) ");     
           	
        hql.add(" and a.status  in ("+AirticketOrder.GROUP_1+") ");       
        
        //操作人
        if(rlf.getSysName()!=null&&!"".equals(rlf.getSysName().trim())){
        	hql.add(" and a.entryOperator like ? ");
        	hql.addParamter( "%" + rlf.getSysName().trim()+ "%");		
        }
        
        //按日期搜索
    	String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();		
		if ("".equals(startDate)==false && "".equals(endDate)==true) {
			hql.add(" and  a.entryTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate)==true && "".equals(endDate)==false) {
			hql.add(" and  a.entryTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate)==false && "".equals(endDate)==false) {
			hql.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		if(rlf.getPlatformIds()!=null&&!"".equals(rlf.getPlatformIds().trim())){
		 hql.add("  and  a.platform.id  in ("+rlf.getPlatformIds()+")");  
		}
		if(rlf.getAccountIds()!=null&&!"".equals(rlf.getAccountIds().trim())){
		 hql.add("  and  a.account.id   in ("+rlf.getAccountIds()+")");
		}

		hql.add(" and a.ticketType in(1,3) group by  a.orderGroup.id order by a.orderGroup.id desc");
	 
		System.out.println("---report----sql---"+hql.toString());
		
		List list=new ArrayList();
		//List  list=  this.list(hql);
		
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0){
			list = query.list();
		}		
		
		System.out.println("---report group list size  ---->"+list.size());
		return list;
	}
	
	//退废报表（取消出票/退票/废票/所有对应的卖出记录）
	public List getGroupIdForRetireReport(AirticketOrderListForm rlf) throws AppException {
		Hql hql = new Hql();
		hql.add("select a.orderGroup.id ");
		hql.add(" from  AirticketOrder a where 1=1 and a.status not in(88) ");
		
		hql.add(" and a.status  in ("+AirticketOrder.GROUP_1+","+AirticketOrder.GROUP_2+","+AirticketOrder.GROUP_5+") ");		
              
        //操作人
        if(rlf.getSysName()!=null&&!"".equals(rlf.getSysName().trim())){
        	hql.add(" and a.entryOperator like ? ");
        	hql.addParamter( "%" + rlf.getSysName().trim()+ "%");		
        }
        
        //按日期搜索
    	String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();		
		if ("".equals(startDate)==false && "".equals(endDate)==true) {
			hql.add(" and  a.entryTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate)==true && "".equals(endDate)==false) {
			hql.add(" and  a.entryTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate)==false && "".equals(endDate)==false) {
			hql.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		if(rlf.getPlatformIds()!=null&&!"".equals(rlf.getPlatformIds().trim())){
		 hql.add("  and  a.platform.id  in ("+rlf.getPlatformIds()+")");  
		}
		if(rlf.getAccountIds()!=null&&!"".equals(rlf.getAccountIds().trim())){
		 hql.add("  and  a.account.id   in ("+rlf.getAccountIds()+")");
		}
		hql.add(" and a.ticketType in(1,3) group by  a.orderGroup.id order by a.orderGroup.id desc");
	 
		System.out.println("---report----sql---"+hql.toString());
		
		List list=new ArrayList();
//		List  list=  this.list(hql);
		
		Query query = this.getQuery(hql);
		if (query != null && query.list() != null && query.list().size() > 0){
			list = query.list();
		}		
		
		System.out.println("---report group list size  ---->"+list.size());
		return list;
	}
	
	//团队销售报表/未返报表
	public List getGroupIdForTeamSaleReport(AirticketOrderListForm rlf) throws AppException{		
		Hql hql = new Hql();
		hql.add(" select a.orderGroup.id ");
		hql.add(" from AirticketOrder a where 1=1 ");
		
		 //按日期搜索
    	String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();		
		if ("".equals(startDate)==false && "".equals(endDate)==true) {
			hql.add(" and  a.entryTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate)==true && "".equals(endDate)==false) {
			hql.add(" and  a.entryTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate)==false && "".equals(endDate)==false) {
			hql.add(" and  a.entryTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
		
		if(rlf.getProxy_price()>0){
			hql.add(" and a.proxyPrice > 0");
		}
		
		hql.add(" and a.status in ("+AirticketOrder.GROUP_9+")");
		hql.add(" and a.ticketType ="+AirticketOrder.TICKETTYPE_2);
		
		hql.add(" group by  a.orderGroup.id order by a.orderGroup.id desc");
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
