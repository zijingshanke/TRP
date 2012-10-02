package com.fdays.tsms.transaction.dao;

import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.PlatformReportIndex;
import com.fdays.tsms.transaction.PlatformReportIndexListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class PlatformReportIndexDAOImp extends BaseDAOSupport implements
		PlatformReportIndexDAO {

	public PlatformReportIndex getReportIndexByPlatformIdType(Long platformId,
			Long compareType) throws AppException {
		Hql hql = new Hql("from PlatformReportIndex where 1=1 ");

		if (platformId != null && platformId > 0) {
			hql.add(" and platformId="+ platformId);
		}

		if (compareType != null && compareType > 0) {
			hql.add(" and type="+ compareType);
		}
		
		hql.add(" and status="+ PlatformReportIndex.STATES_1);

		Query query = this.getQuery(hql);
		try {
			if (query != null) {
				List list = query.list();
				if (list != null && list.size() > 0) {
					return (PlatformReportIndex) query.list().get(0);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public long save(PlatformReportIndex user) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(user);
		return user.getId();
	}

	public long merge(PlatformReportIndex user) throws AppException {
		this.getHibernateTemplate().merge(user);
		return user.getId();
	}

	public long update(PlatformReportIndex user) throws AppException {
		if (user.getId() > 0)
			return save(user);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			PlatformReportIndex user = (PlatformReportIndex) this
					.getHibernateTemplate().get(PlatformReportIndex.class,
							new Long(id));
			this.getHibernateTemplate().delete(user);

		}
	}

	public PlatformReportIndex getPlatformReportIndexById(long id) {
		PlatformReportIndex user;
		try {
			if (id > 0) {
				user = (PlatformReportIndex) this.getHibernateTemplate().get(
						PlatformReportIndex.class, new Long(id));
				return user;
			} else
				return new PlatformReportIndex();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new PlatformReportIndex();
	}

	public PlatformReportIndex queryById(long id) throws AppException {
		Hql hql = new Hql("from PlatformReportIndex where id=" + id);

		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (PlatformReportIndex) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list() throws AppException {
		String hql = "from PlatformReportIndex where 1=1";
		return this.list(hql);
	}

	public List getValidPlatformReportIndexList() throws AppException {
		String hql = "from PlatformReportIndex where 1=1 and status=1 ";
		return this.list(hql);
	}

	public List list(PlatformReportIndexListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add("from PlatformReportIndex where 1=1");
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
