package com.fdays.tsms.airticket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.airticket.Flight;
import com.fdays.tsms.airticket.FlightListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class FlightDAOImp extends BaseDAOSupport  implements FlightDAO{

	public List list(FlightListForm rlf) throws AppException {

		Hql hql = new Hql();
		hql.add("from Flight where 1=1");
		return this.list(hql, rlf);
	}
	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add("from Flight where 1=1");
		return this.list(hql);
	}
	
	// 删除
	public void delete(long id)  throws AppException{
		if (id > 0) {
			Flight flight = (Flight) this.getHibernateTemplate().get(
					Flight.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(flight);
		}

	}
	// 添加保存
	public long save(Flight flight) throws AppException{
		this.getHibernateTemplate().save(flight);
		return flight.getId();
	}

	// 修改
	public long update(Flight flight) throws AppException {
		if (flight.getId() > 0)
			return ((Flight)this.getHibernateTemplate().merge(flight)).getId();
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
	
	//返回一个List集合
	public List<Flight> getFlightList() throws AppException
	{
		List<Flight> list = new ArrayList<Flight>();
		Hql hql = new Hql();
		hql.add("from Flight");
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null && query.list().size()>0)
		{
			list = query.list();
		}
		return list;
	}
	
	//根据航班表ID查询
	public Flight getFlightById(long id) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Flight f where f.id="+id);
		Query query = this.getQuery(hql);
		Flight flight = new Flight();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			flight =(Flight)query.list().get(0);
		}
		return flight;
	}
	
	//根据订单机票ID查询 返回对象
	public Flight getFlightByAirticketOrderID(long airticketOrderId) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from Flight f where f.airticketOrder.id="+airticketOrderId);
		Query query = this.getQuery(hql);
		Flight flight = new Flight();
		if(query != null && query.list() != null && query.list().size()>0)
		{
			flight =(Flight)query.list().get(0);
		}
		return flight;
	}
	
	//根据订单机票ID查询 返回List集合
	public List<Flight> getFlightListByOrderId(long airticketOrderId) throws AppException
	{
		List<Flight> list = new ArrayList<Flight>();
		Hql hql = new Hql();
		hql.add("from Flight f where f.airticketOrder.id="+airticketOrderId);
		Query query = this.getQuery(hql);
		if(query != null && query.list() != null && query.list().size()>0)
		{
			list = query.list();
		}
		return list;
	}
	   
}
