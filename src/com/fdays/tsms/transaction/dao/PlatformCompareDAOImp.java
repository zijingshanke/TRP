package com.fdays.tsms.transaction.dao;

import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.PlatformCompare;
import com.fdays.tsms.transaction.PlatformCompareListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PlatformCompareDAOImp extends BaseDAOSupport implements
		PlatformCompareDAO {

	public long save(PlatformCompare compare) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(compare);
		return compare.getId();
	}

	public long merge(PlatformCompare compare) throws AppException {
		this.getHibernateTemplate().merge(compare);
		return compare.getId();
	}

	public long update(PlatformCompare compare) throws AppException {
		if (compare.getId() > 0)
			return save(compare);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			PlatformCompare compare = (PlatformCompare) this
					.getHibernateTemplate().get(PlatformCompare.class,
							new Long(id));
			this.getHibernateTemplate().delete(compare);

		}
	}

	public PlatformCompare getPlatformCompareById(long id) {
		PlatformCompare compare;
		try {
			if (id > 0) {

				compare = (PlatformCompare) this.getHibernateTemplate().get(
						PlatformCompare.class, new Long(id));
				return compare;
			} else
				return new PlatformCompare();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new PlatformCompare();
	}

	public PlatformCompare queryById(long id) throws AppException {
		Hql hql = new Hql("from PlatformCompare where id=" + id);

		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (PlatformCompare) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list() throws AppException {
		String hql = "from PlatformCompare where 1=1";
		return this.list(hql);
	}

	public List getValidPlatformCompareList() throws AppException {
		String hql = "from PlatformCompare where 1=1 and status=1 ";
		return this.list(hql);
	}

	public List list(PlatformCompareListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add("from PlatformCompare where 1=1");
		if (ulf.getPlatformId() > 0) {
			hql.add(" and platformId=? ");
			hql.addParamter(ulf.getPlatformId());
		}

		if (ulf.getType() > 0) {
			hql.add(" and type=? ");
			hql.addParamter(ulf.getType());
		}

		if (ulf.getStatus() > 0) {
			hql.add(" and status=? ");
			hql.addParamter(ulf.getStatus());
		}
		
	
		
		return this.list(hql, ulf);
	}

}
