package com.fdays.tsms.transaction.dao;

import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportCompareResultListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportCompareResultDAOImp extends BaseDAOSupport implements
	ReportCompareResultDAO {

	public long save(ReportCompareResult user) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(user);
		return user.getId();
	}

	public long merge(ReportCompareResult user) throws AppException {
		this.getHibernateTemplate().merge(user);
		return user.getId();
	}

	public long update(ReportCompareResult user) throws AppException {
		if (user.getId() > 0)
			return save(user);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			ReportCompareResult user = (ReportCompareResult) this
					.getHibernateTemplate().get(ReportCompareResult.class,
							new Long(id));
			this.getHibernateTemplate().delete(user);
		}
	}

	public ReportCompareResult getReportCompareResultById(long id) {
		ReportCompareResult user;
		try {
			if (id > 0) {
				user = (ReportCompareResult) this.getHibernateTemplate().get(
						ReportCompareResult.class, new Long(id));
				return user;
			} else
				return new ReportCompareResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ReportCompareResult();
	}

	public ReportCompareResult queryById(long id) throws AppException {
		Hql hql = new Hql(" from ReportCompareResult where id=" + id);

		Query query = this.getQuery(hql);
		try {
			if (query != null) {
				List list=query.list();
				if(list!=null){
					if(list.size()>0){
						return (ReportCompareResult) list.get(0);
					}
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list() throws AppException {
		String hql = "from ReportCompareResult where 1=1";
		return this.list(hql);
	}

	public List getValidReportCompareResultList() throws AppException {
		String hql = "from ReportCompareResult where 1=1 and status=1 ";
		return this.list(hql);
	}

	public List list(ReportCompareResultListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add("from ReportCompareResult where 1=1");
//		if (ulf.getPlatformId() > 0) {
//			hql.add(" and platformId=? ");
//			hql.addParamter(ulf.getPlatformId());
//		}

//		if (ulf.getCompareType() > 0) {
//			hql.add(" and compareType=? ");
//			hql.addParamter(ulf.getCompareType());
//		}

//		if (ulf.getType() > 0) {
//			hql.add(" and type=? ");
//			hql.addParamter(ulf.getType());
//		}

//		if (ulf.getStatus() > 0) {
//			hql.add(" and status=? ");
//			hql.addParamter(ulf.getStatus());
//		}
		return this.list(hql, ulf);
	}
}