package com.fdays.tsms.airticket.dao;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.fdays.tsms.airticket.Passenger;
import com.fdays.tsms.airticket.PassengerListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PassengerDAOImp extends BaseDAOSupport implements PassengerDAO {

	public List list(PassengerListForm rlf) throws AppException {

		Hql hql = new Hql();
		hql.add("from Passenger where 1=1");
		return this.list(hql, rlf);
	}
	public List list() throws AppException {
		Hql hql = new Hql();
		hql.add("from Passenger where 1=1");
		return this.list(hql);
	}
	
	public void delete(long id)  throws AppException{
		if (id > 0) {
			Passenger passenger = (Passenger) this.getHibernateTemplate().get(
					Passenger.class, Long.valueOf(id));
			this.getHibernateTemplate().delete(passenger);
		}

	}

	public long save(Passenger passenger) throws AppException{
		this.getHibernateTemplate().save(passenger);
		return passenger.getId();
	}

	public long update(Passenger passenger) throws AppException {
		if (passenger.getId() > 0)
			return ((Passenger)this.getHibernateTemplate().merge(passenger)).getId();
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public List<Passenger> listByairticketOrderId(long airticketOrderId) throws AppException{		
		List<Passenger> list = new ArrayList();
		Hql hql = new Hql("from Passenger p where 1=1 and p.airticketOrder.id="+airticketOrderId);
		
		Query query = this.getQuery(hql);
		if(query!=null)
		{
			list = query.list();
		}
		return list;
	}

	public Passenger getPassengerById(Long id) throws AppException {
		Passenger passenger=null;
		passenger=(Passenger)this.getHibernateTemplate().get(Passenger.class, new Long(id));
		return passenger;
	}
}
