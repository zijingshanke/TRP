package com.fdays.tsms.airticket.dao;


import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.airticket.Airline;
import com.fdays.tsms.airticket.AirlineListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class AirlineDAOImp extends BaseDAOSupport implements AirlineDAO {

	public Airline getAirlineByCity(String start, String end)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from Airline f where 1=1 ");
		
		hql.add(" and( f.start like '%" + start+"%' and f.end like '%"+end+"%' or f.start like '%" + end+"%' and f.end like '%"+start+"%')");
		
		Query query = this.getQuery(hql);
		Airline flight = new Airline();
		if (query != null && query.list() != null && query.list().size() > 0) {
			flight = (Airline) query.list().get(0);
		}
		return flight;
	}

	public List list(AirlineListForm alf) throws AppException {
		Hql hql = new Hql();
		hql.add(" from Airline a where 1=1 ");
		
		if ("".equals(alf.getBegin().trim())==false && "".equals(alf.getEnd().trim())) {
			hql.add(" and( LOWER(a.begin) like LOWER(?) or LOWER(a.end) like LOWER(?)) ");
			hql.addParamter("%" + alf.getBegin().trim() + "%");
			hql.addParamter("%" + alf.getBegin().trim() + "%");
		}
		
		if ("".equals(alf.getBegin().trim()) && "".equals(alf.getEnd().trim())==false) {
			hql.add(" and (LOWER(a.begin) like LOWER(?)  or LOWER(a.end) like LOWER(?)) ");
			hql.addParamter("%" + alf.getEnd().trim() + "%");
			hql.addParamter("%" + alf.getEnd().trim() + "%");
		}
		
		if ("".equals(alf.getBegin().trim())==false && "".equals(alf.getEnd().trim())==false) {
			hql.add(" and LOWER(a.begin) like LOWER(?) and LOWER(a.end) like LOWER(?) ");
			hql.addParamter("%" + alf.getBegin().trim() + "%");
			hql.addParamter("%" + alf.getEnd().trim() + "%");
		}
		
		if (alf.getStatus() != null && "".equals(alf.getStatus())==false && alf.getStatus()>0) {
			hql.add(" and a.status=? ");
			hql.addParamter(alf.getStatus());
		}		
		
		hql.add(" order by a.begin,a.end ");
		
//		System.out.println(hql.getSql());
		
		return this.list(hql, alf);
	}

	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add(" from Airline where 1=1 ");
		return this.list(hql);
	}
	
	public List getValidList() throws AppException {
		Hql hql = new Hql();
		hql.add(" from Airline a where 1=1 and status=1 order by a.begin,a.end ");
		return this.list(hql);
	}

	public void delete(long id) throws AppException {
		if (id > 0) {
			Airline flight = (Airline) this.getHibernateTemplate().get(
					Airline.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(flight);
		}
	}

	public long save(Airline flight) throws AppException {
		this.getHibernateTemplate().save(flight);
		return flight.getId();
	}

	public long update(Airline flight) throws AppException {
		if (flight.getId() > 0)
			return ((Airline) this.getHibernateTemplate().merge(flight))
					.getId();
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public Airline getAirlineById(long id) throws AppException {
		Hql hql = new Hql();
		hql.add("from Airline f where f.id=" + id);
		Query query = this.getQuery(hql);
		Airline flight = new Airline();
		if (query != null && query.list() != null && query.list().size() > 0) {
			flight = (Airline) query.list().get(0);
		}
		return flight;
	}

}
