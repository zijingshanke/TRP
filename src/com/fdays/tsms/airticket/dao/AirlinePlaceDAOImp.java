package com.fdays.tsms.airticket.dao;


import java.util.List;
import org.hibernate.Query;

import com.fdays.tsms.airticket.AirlinePlace;
import com.fdays.tsms.airticket.AirlinePlaceListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AirlinePlaceDAOImp extends BaseDAOSupport implements AirlinePlaceDAO {

	public AirlinePlace getAirlinePlaceByCarrier(String carrier, String code)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from AirlinePlace f where f.company like '%" + carrier+"%' and f.code like '%"+code+"%'");
		Query query = this.getQuery(hql);
		AirlinePlace flight = new AirlinePlace();
		if (query != null && query.list() != null && query.list().size() > 0) {
			flight = (AirlinePlace) query.list().get(0);
		}
		return flight;
	}

	public List list(AirlinePlaceListForm alf) throws AppException {
		Hql hql = new Hql();
		hql.add(" from AirlinePlace a where 1=1 ");
		
		
		if (alf.getCompany() != null && "".equals(alf.getCompany())==false) {
			hql.add(" and LOWER(a.company) like LOWER(?) ");
			hql.addParamter("%" + alf.getCompany().trim()+ "%");
		}
		
		if (alf.getCode() != null && "".equals(alf.getCode())==false) {
			hql.add(" and LOWER(a.code) like LOWER(?) ");
			hql.addParamter("%" + alf.getCode().trim() + "%");
		}
		
		if (alf.getStatus() != null && "".equals(alf.getStatus())==false && alf.getStatus()>0) {
			hql.add(" and status=? ");
			hql.addParamter(alf.getStatus());
		}
		
//		System.out.println(hql.getSql());
		return this.list(hql, alf);
	}

	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add("from AirlinePlace where 1=1");
		return this.list(hql);
	}
	
	public List getValidList() throws AppException {
		Hql hql = new Hql();
		hql.add("from AirlinePlace where 1=1 and status=1 ");
		return this.list(hql);
	}

	public void delete(long id) throws AppException {
		if (id > 0) {
			AirlinePlace flight = (AirlinePlace) this.getHibernateTemplate().get(
					AirlinePlace.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(flight);
		}
	}

	public long save(AirlinePlace flight) throws AppException {
		this.getHibernateTemplate().save(flight);
		return flight.getId();
	}

	public long update(AirlinePlace flight) throws AppException {
		if (flight.getId() > 0)
			return ((AirlinePlace) this.getHibernateTemplate().merge(flight))
					.getId();
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public AirlinePlace getAirlinePlaceById(long id) throws AppException {
		Hql hql = new Hql();
		hql.add("from AirlinePlace f where f.id=" + id);
		Query query = this.getQuery(hql);
		AirlinePlace flight = new AirlinePlace();
		if (query != null && query.list() != null && query.list().size() > 0) {
			flight = (AirlinePlace) query.list().get(0);
		}
		return flight;
	}
}
