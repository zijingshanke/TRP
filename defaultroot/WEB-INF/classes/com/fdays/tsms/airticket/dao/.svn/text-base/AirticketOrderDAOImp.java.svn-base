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
        
        if(rlf.getTeamStatus()==AirticketOrder.STATUS_88)
    	{
    		hql.add("and a.status not in ("+rlf.getTeamStatus()+")");
    	}
        //机票类型
        if(rlf.getTicketType()>0){
        	hql.add("and a.ticketType="+rlf.getTicketType());
		}
        
        hql.add("order by a.groupMarkNo ,a.tranType");
		return this.list(hql, rlf);
	}
	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add("from AirticketOrder where 1=1");
		return this.list(hql);
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
        	hql.add("and a.subPnr like ?");
        	hql.addParamter( "%" + subPnr.trim()+ "%");		
        }
        hql.add("and a.tranType ="+AirticketOrder.TRANTYPE_2);
		Query query = this.getQuery(hql);
		AirticketOrder airticketOrder=new AirticketOrder();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			airticketOrder =(AirticketOrder)query.list().get(0);
		}
		return airticketOrder;
	}
}
