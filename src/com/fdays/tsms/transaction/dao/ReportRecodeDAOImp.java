package com.fdays.tsms.transaction.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessResourceFailureException;
import com.fdays.tsms.transaction.ReportRecode;
import com.fdays.tsms.transaction.ReportRecodeListForm;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportRecodeDAOImp extends BaseDAOSupport implements
		ReportRecodeDAO {

	public List<ReportRecode> getReportRecodeListForCompare(
			ReportRecode reportRecode) throws AppException {
		Hql hql = new Hql(
				" select new com.fdays.tsms.transaction.ReportRecode ");
		hql.add("(");
		hql.add("o,s");
		hql.add(")");
		hql.add(" from AirticketOrder o,Statement s where 1=1 ");

		Query query = this.getQuery(hql);

		List<ReportRecode> tempList = new ArrayList<ReportRecode>();
		try {
			if (query != null) {
				tempList = query.list();
				if (tempList != null) {
					if (tempList.size() > 0) {
						return tempList;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tempList;
	}

	public List<ReportRecode> getReportRecodeListByResultId(long resultId)
			throws AppException {
		Hql hql = new Hql(" from ReportRecode r where 1=1 ");
		hql.add(" and r.reportRecodeResult.id=" + resultId);

		Query query = this.getQuery(hql);

		List<ReportRecode> tempList = new ArrayList<ReportRecode>();
		try {
			if (query != null) {
				tempList = query.list();
				if (tempList != null) {
					if (tempList.size() > 0) {
						return tempList;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tempList;
	}

	public List<ReportRecode> getReportRecodeListByCompareResultIdType(long resultId,long recodeType)
			throws AppException {
		Hql hql = new Hql(" from ReportRecode r where 1=1 ");
		hql.add(" and r.reportCompareResult.id=" + resultId);
		hql.add(" and r.type=" + recodeType);		

		Query query = this.getQuery(hql);

		List<ReportRecode> tempList = new ArrayList<ReportRecode>();
		try {
			if (query != null) {
				tempList = query.list();
				if (tempList != null) {
					if (tempList.size() > 0) {
						return tempList;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return tempList;
	}

	public long save(ReportRecode reportRecode) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(reportRecode);
		return reportRecode.getId();
	}

	public long merge(ReportRecode reportRecode) throws AppException {
		this.getHibernateTemplate().merge(reportRecode);
		return reportRecode.getId();
	}

	public long update(ReportRecode reportRecode) throws AppException {
		if (reportRecode.getId() > 0)
			return save(reportRecode);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			ReportRecode reportRecode = (ReportRecode) this
					.getHibernateTemplate().get(ReportRecode.class,
							new Long(id));
			this.getHibernateTemplate().delete(reportRecode);

		}
	}

	public ReportRecode getReportRecodeById(long id) {
		ReportRecode reportRecode;
		try {
			if (id > 0) {
				reportRecode = (ReportRecode) this.getHibernateTemplate().get(
						ReportRecode.class, new Long(id));
				return reportRecode;
			} else
				return new ReportRecode();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ReportRecode();
	}

	public ReportRecode queryById(long id) throws AppException {
		Hql hql = new Hql("from ReportRecode where id=" + id);
		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (ReportRecode) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list(ReportRecodeListForm rrlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from ReportRecode where 1=1");
		if (rrlf.getPlatformId() > 0) {
			hql.add(" and platformId=? ");
			hql.addParamter(rrlf.getPlatformId());
		}

		if (rrlf.getType() > 0) {
			hql.add(" and type=? ");
			hql.addParamter(rrlf.getType());
		}

		if (rrlf.getStatus() > 0) {
			hql.add(" and status=? ");
			hql.addParamter(rrlf.getStatus());
		}

		return this.list(hql, rrlf);
	}

	public void deleteAllByResultId(long id) throws AppException {
		String hql = "delete ReportRecode rr where rr.reportRecodeResult.id="
				+ id;
		try {
			Query query = this.getSession().createQuery(hql);
			query.executeUpdate();
		} catch (DataAccessResourceFailureException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public List<Long> getDistinctIndexId(ReportRecodeResult reportRecodeResult) throws AppException {
		Hql hql = new Hql();
		hql.add("select distinct r.platformReportIndex.id from ReportRecode r where 1=1");
		hql.add(" and r.reportRecodeResult.id=? ");
		hql.addParamter(reportRecodeResult.getId());
		Query query = this.getQuery(hql);
		if(query != null){
			return query.list();
		}
		return null;
	}

	public int getRowCountByIndexId(ReportRecodeResult reportRecodeResult,long indexId) throws AppException {
		Hql hql = new Hql();
		hql.add("select count(*) from ReportRecode r where 1=1");
		hql.add(" and r.reportRecodeResult.id=? ");
		hql.addParamter(reportRecodeResult.getId());
		hql.add(" and r.platformReportIndex.id=? ");
		hql.addParamter(indexId);
		Query query = this.getQuery(hql);
		if(query != null){
			Object obj = query.uniqueResult();
			return Integer.parseInt(obj.toString());
		}
		return 0;
	}

	public List<ReportRecode> getReportRecodeByResultIndex(
			ReportRecodeResult reportRecodeResult, long indexId)
			throws AppException {
		Hql hql = new Hql();
		hql.add("from ReportRecode r where 1=1");
		hql.add(" and r.reportRecodeResult.id=?");
		hql.addParamter(reportRecodeResult.getId());
		hql.add(" and r.platformReportIndex.id=?");
		hql.addParamter(indexId);
		Query query = this.getQuery(hql);
		if(query != null){
			return query.list();
		}
		return null;
	}
}
