package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.airticket.AirticketOrder;
import com.fdays.tsms.airticket.AirticketOrderListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AirticketOrderDAOImp extends BaseDAOSupport implements AirticketOrderDAO {

	public List list(AirticketOrderListForm rlf) throws AppException {

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
        //cyr//承运人
        if(rlf.getCyr()!=null&&!"".equals(rlf.getCyr().trim())){
        	hql.add(" and exists(from Flight f where f.flightCode like '%"+rlf.getCyr().trim()+"%' and f.airticketOrder.id=a.id)");
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
        //（过滤）订单类别
        if(rlf.getFiltrateTicketType()!=null&&!"".equals(rlf.getFiltrateTicketType().trim())){
        	hql.add("and a.ticketType  not in ("+rlf.getFiltrateTicketType()+")");
		}
        //单个订单状态
        if(rlf.getAirticketOrder_status()>0){
        	hql.add("and a.status="+rlf.getAirticketOrder_status());
		}
        //多个订单状态
        if(rlf.getMoreStatus()!=null&&!"".equals(rlf.getMoreStatus().trim())){
        	
        	hql.add("and a.status  in ("+rlf.getMoreStatus()+")");
        }
        
        if(rlf.getTeamStatus()==AirticketOrder.STATUS_88)//已废弃
    	{
    		hql.add("and a.status not in ("+rlf.getTeamStatus()+")");
    	}
        //机票类型
        if(rlf.getTicketType()>0){
        	hql.add("and a.ticketType="+rlf.getTicketType());
		}
        //按日期搜索
    	String startDate = rlf.getStartDate();
		String endDate = rlf.getEndDate();
		
		if ("".equals(startDate)==false && "".equals(endDate)==true) {
			startDate=startDate+" 00:00:00";
			hql.add(" and  a.optTime > to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(startDate);
		}
		if ("".equals(startDate)==true && "".equals(endDate)==false) {
			endDate=endDate+" 23:59:59";
			hql.add(" and  a.optTime < to_date(?,'yyyy-mm-dd hh24:mi:ss')");
			hql.addParamter(endDate);
		}
		if ("".equals(startDate)==false && "".equals(endDate)==false) {
			startDate=startDate+" 00:00:00";
			endDate=endDate+" 23:59:59";
			hql.add(" and  a.optTime  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}
        //交易类型
        if(rlf.getTranType()>0)
        {
        	hql.add("and a.tranType="+rlf.getTranType());
        }
        hql.add("order by  a.groupMarkNo desc,a.tranType");//a.optTime desc,
		return this.list(hql, rlf);
	}
	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add("from AirticketOrder where 1=1");
		return this.list(hql);
	}
	
	
	//团队专用---lrc
	public List teamAirticketOrderList(AirticketOrderListForm rlf) throws AppException{
		
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		if(rlf.getTeam_status()>0)//状态
		{
			hql.add("and a.status="+rlf.getTeam_status());
		}
		if(rlf.getTeamTicket_type()>0)//机票类型
		{
			hql.add("and a.ticketType="+rlf.getTeamTicket_type());
		}
		if(rlf.getScrapTeam_status()>0)//过滤已废弃的票    
		{
			hql.add("and a.status not in ("+rlf.getScrapTeam_status()+")");
		}
		if(rlf.getTeamTran_type() >0)//机票类型：买入
		{
			hql.add("and a.tranType="+rlf.getTeamTran_type());
		}
		hql.add("order by a.optTime desc");
		return this.list(hql, rlf);
	}
	
	//B2C分页查询-----lrc
	public List b2cAirticketOrderList(AirticketOrderListForm rlf) throws AppException {
		
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
		//过滤交易结束的订单(lrc)
        if(rlf.getB2C_status() == AirticketOrder.STATUS_80)
        {
        	hql.add("and a.status not in ("+rlf.getB2C_status()+")");
        }
        if(rlf.getTranType()>0)//卖出
        {
        	hql.add("and a.tranType="+rlf.getTranType());
        }
        if(rlf.getTicketType()>0)//b2c
        {
        	hql.add("and a.ticketType="+rlf.getTicketType());
        }
        //过滤已废弃的票        
        if(rlf.getTeamStatus()==AirticketOrder.STATUS_88)
    	{
    		hql.add("and a.status not in ("+rlf.getTeamStatus()+")");
    	}
      //  hql.add("and a.statement.unsettledAccount > 0");//查询未结款>0
        hql.add("order by a.optTime desc");
		return this.list(hql, rlf);
	}
	
	// 删除
	public void delete(long id)  throws AppException{
		if (id > 0) {
			AirticketOrder airticketOrder = (AirticketOrder) this.getHibernateTemplate().get(
					AirticketOrder.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(airticketOrder);
		}

	}
	// 添加保存
	public long save(AirticketOrder airticketOrder) throws AppException{		
		this.getHibernateTemplate().save(airticketOrder);
		return airticketOrder.getId();
	}

	// 修改
	public long update(AirticketOrder airticketOrder) throws AppException {
		if (airticketOrder.getId() > 0)
			return ((AirticketOrder)this.getHibernateTemplate().merge(airticketOrder)).getId();
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	// 根据 drawPnr 查询 	   
	public AirticketOrder airticketOrderByPNR(String  pnr) throws AppException{
		
		Hql hql = new Hql("from AirticketOrder where drawPnr=?");
		hql.addParamter(pnr);

		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder = new AirticketOrder();
		if (query != null && query.list() != null && query.list().size() > 0) {
			airticketOrder = (AirticketOrder) query.list().get(0);
		}
		return airticketOrder;

	}
	
	//根据id查询
	public AirticketOrder getAirticketOrderById(long airtickeOrderId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where a.id="+airtickeOrderId);
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder=new AirticketOrder();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			airticketOrder =(AirticketOrder)query.list().get(0);
		}
		return airticketOrder;
	}
	
	//根据结算ID查询
	public AirticketOrder getAirticketOrderBystatementId(long statementId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where a.statement.id="+statementId);
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder=new AirticketOrder();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			airticketOrder =(AirticketOrder)query.list().get(0);
		}
		return airticketOrder;
	}
	
	//根据订单组编号返加List集合
	public List<AirticketOrder> getAirticketOrderListByGroupMarkNo(String groupMarkNo) throws AppException
	{
		List<AirticketOrder> list = new ArrayList<AirticketOrder>();
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where a.groupMarkNo='"+groupMarkNo+"'");
		Query query =this.getQuery(hql);
		if(query != null && query.list() != null && query.list().size()>0)
		{
			list =query.list();
		}
		return list;
	}
	
	//返回一个List集合
	public List<AirticketOrder> getAirticketOrderList() throws AppException
	{
		List<AirticketOrder> list = new ArrayList<AirticketOrder>();
		Hql hql = new Hql();
		hql.add("from AirticketOrder");
		Query query =this.getQuery(hql);
		if(query != null && query.list() != null && query.list().size()>0)
		{
			list =query.list();
		}
		return list;
		
	}
	//根据订单组号 查询
	public List<AirticketOrder> listBygroupMarkNo(String groupMarkNo,String tranType) throws AppException{
		
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		hql.add("and a.groupMarkNo like ?");
    	hql.addParamter( "%" + groupMarkNo.trim()+ "%");
    	if(tranType!=null&&!"".equals(tranType.trim())){
    		hql.add("and tranType in("+tranType+")");
    	}
		Query query = this.getQuery(hql);
		if(query!=null)
		{
			list = query.list();
		}
		return list;
	}
	
	//根据订单组号 查询
	public List<AirticketOrder> getListByOrder(AirticketOrder airticketOrder) throws AppException{
		
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		hql.add("and a.groupMarkNo like ?");
    	hql.addParamter( "%" + airticketOrder.getGroupMarkNo().trim()+ "%");
    	if(airticketOrder.getStatement().getType()!=null&&airticketOrder.getStatement().getType()>0){
    		hql.add("and a.statement.type="+airticketOrder.getStatement().getType());
    	}
    	if(airticketOrder.getTranType()!=null&&airticketOrder.getTranType()>0){
    		hql.add("and tranType="+airticketOrder.getTranType());
    	}
		Query query = this.getQuery(hql);
		if(query!=null)
		{
			list = query.list();
		}
		return list;
	}
	
	//根据 预定pnr查询
	public AirticketOrder getAirticketOrderBysubPnr(String  subPnr) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
        if(subPnr!=null&&!"".equals(subPnr.trim())){
        	hql.add("and a.subPnr =?");
        	hql.addParamter(subPnr.trim());		
        }
        hql.add("and a.tranType ="+AirticketOrder.TRANTYPE__2);
        hql.add("and a.status ="+AirticketOrder.STATUS_5);
        hql.add("and a.optTime is not null");
        hql.add("order by a.optTime desc");
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder=new AirticketOrder();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			airticketOrder =(AirticketOrder)query.list().get(0);
		}
		return airticketOrder;
	}
	
	//根据 预定pnr、类型查询导入退废、改签的订单
	public AirticketOrder getAirticketOrderForRetireUmbuchen(String  subPnr,long businessType,long tranType) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
        if(subPnr!=null&&!"".equals(subPnr.trim())){
        	hql.add("and a.subPnr =?");
        	hql.addParamter(subPnr.trim());		
        }

        hql.add("and a.businessType ="+businessType);
        hql.add("and a.tranType ="+tranType);
        
        hql.add("and a.status ="+AirticketOrder.STATUS_5);
        hql.add("and a.optTime is not null");
        hql.add("order by a.optTime desc");
        
        System.out.println("getAirticketOrderForRetireUmbuchen hql>>>>");
        System.out.println(hql);
        
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder=new AirticketOrder();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			airticketOrder =(AirticketOrder)query.list().get(0);
		}
		return airticketOrder;
	}
	
	//根据 预定编组号查询
	public AirticketOrder getAirticketOrderByGroupMarkNor(String  groupMarkNo,long tranType) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
        if(groupMarkNo!=null&&!"".equals(groupMarkNo.trim())){
        	hql.add("and a.groupMarkNo =?");
        	hql.addParamter(groupMarkNo.trim());		
        }
        hql.add("and a.tranType ="+tranType);
        hql.add("and a.status ="+AirticketOrder.STATUS_5);
      /*hql.add("and a.optTime is not null");
        hql.add("order by a.optTime desc");*/
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder=new AirticketOrder();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			airticketOrder =(AirticketOrder)query.list().get(0);
			System.out.println("-----"+airticketOrder.getStatement().getPlatComAccount().getPlatform().getName());
			System.out.println("-----"+airticketOrder.getStatement().getPlatComAccount().getCompany().getName());
			System.out.println("-----"+airticketOrder.getStatement().getPlatComAccount().getAccount().getName());
		}
		return airticketOrder;
	}
	
	//验证pnr是一天内否重复添加
	public boolean checkPnrisToday(AirticketOrder airticketOrder)throws AppException{
		boolean bole=true;
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		if(airticketOrder.getSubPnr()!=null&&airticketOrder.getTranType()!=null){
			hql.add("and a.subPnr =?");
		 	hql.addParamter(airticketOrder.getSubPnr().trim());	
			hql.add("and a.tranType=?");
			hql.addParamter(airticketOrder.getTranType());
			hql.add("and a.optTime like sysdate");
			Query query = this.getQuery(hql);
			if(query != null && query.list() != null && query.list().size()>0){
				bole=false;
			}
			
		}
		return bole;
	}
	
	//验证pnr是一月内否重复 退费 改签
	public boolean checkPnrisMonth(AirticketOrder airticketOrder)throws AppException{
		boolean bole=true;
		Hql hql = new Hql("from AirticketOrder a where 1=1 ");
		if(airticketOrder.getSubPnr()!=null&&airticketOrder.getTranType()!=null){
			hql.add("and a.subPnr =?");
		 	hql.addParamter(airticketOrder.getSubPnr().trim());	
			hql.add("and a.tranType=?");
			hql.addParamter(airticketOrder.getTranType());
			hql.add("and  to_char(a.optTime,'yyyy-mm-dd')  between to_char(add_months(sysdate,-1),'yyyy-mm-dd') and   to_char(sysdate,'yyyy-mm-dd')");
			Query query = this.getQuery(hql);
			if(query != null && query.list() != null && query.list().size()>0){
				bole=false;
			}
			
		}
		return bole;
	}
	//根据PNR 和 tranType 获取订单集合
	public List<AirticketOrder> getAirticketOrderListByPNR(String  subPnr,String tranType) throws AppException{
		List<AirticketOrder> list = new ArrayList();
		Hql hql = new Hql();
		hql.add("from AirticketOrder a where 1=1");
        if(subPnr!=null&&!"".equals(subPnr.trim())){
        	hql.add("and a.subPnr =?");
        	hql.addParamter(subPnr.trim());		
        }
        hql.add("and a.tranType ="+AirticketOrder.TRANTYPE__1);
        hql.add("and a.status ="+AirticketOrder.STATUS_5);
        hql.add("and a.optTime is not null");
        hql.add("order by a.optTime desc");
		Query query = this.getQuery(hql);
		if(query!=null)
		{
			list = query.list();
		}
		return list;

	}
}
