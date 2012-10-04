package com.fdays.tsms.transaction.dao;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.transaction.ReportRecodeResult;
import com.fdays.tsms.transaction.ReportRecodeResultListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportRecodeResultDAOImp extends BaseDAOSupport implements
		ReportRecodeResultDAO {

	public List list(ReportRecodeResultListForm rrrlf) throws AppException {
		Hql hql = new Hql();
		hql.add("from ReportRecodeResult where 1=1");
		//操作人：
		if (rrrlf.getUserNo() != null && (!(rrrlf.getUserNo().equals("")))) {
			hql.add(" and userNo like '%" + rrrlf.getUserNo().trim() + "%'");
		}
		// 报表类型:
		if (rrrlf.getReportType() > 0) {
			hql.add(" and reportType=? ");
			hql.addParamter(rrrlf.getReportType());
		}
		//时间：
		if(rrrlf.getReportDate() != null){
			String dateStr = rrrlf.getReportDate().toString().substring(0,rrrlf.getReportDate().toString().lastIndexOf("."));
			hql.add(" and  beginDate<=to_date(?,'yyyy-mm-dd hh24:mi:ss') and endDate>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(dateStr);
			hql.addParamter(dateStr);
		}
		hql.add(" order by beginDate desc");
		return this.list(hql, rrrlf);
	}

	public long save(ReportRecodeResult reportRecodeResult) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(reportRecodeResult);
		return reportRecodeResult.getId();
	}

	public long merge(ReportRecodeResult reportRecodeResult) throws AppException {
		this.getHibernateTemplate().merge(reportRecodeResult);
		return reportRecodeResult.getId();
	}

	public long update(ReportRecodeResult reportRecodeResult) throws AppException {
		if (reportRecodeResult.getId() > 0)
			return save(reportRecodeResult);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			ReportRecodeResult reportRecodeResult = (ReportRecodeResult) this
					.getHibernateTemplate().get(ReportRecodeResult.class,
							new Long(id));
			this.getHibernateTemplate().delete(reportRecodeResult);
		}
	}

	public ReportRecodeResult getReportRecodeResultById(long id) {
		ReportRecodeResult reportRecodeResult;
		try {
			if (id > 0) {
				reportRecodeResult = (ReportRecodeResult) this.getHibernateTemplate().get(
						ReportRecodeResult.class, new Long(id));
				return reportRecodeResult;
			} else
				return new ReportRecodeResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ReportRecodeResult();
	}

	public ReportRecodeResult queryById(long id) throws AppException {
		Hql hql = new Hql(" from ReportRecodeResult where id=" + id);

		Query query = this.getQuery(hql);
		try {
			if (query != null) {
				List list = query.list();
				if (list != null) {
					if (list.size() > 0) {
						return (ReportRecodeResult) list.get(0);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list() throws AppException {
		String hql = "from ReportRecodeResult where 1=1";
		return this.list(hql);
	}

	public ReportRecodeResult getReportRecodeResultByName(String name)
			throws AppException {
		Hql hql = new Hql(" from ReportRecodeResult r where r.name=?");
		hql.addParamter(name);
		Query query = this.getQuery(hql);
		if(query != null){
			List list = query.list(); 
			if(list != null){
				if(list.size()>0){
					return (ReportRecodeResult) list.get(0);
				}
			}
		}
		return null;
	}
	
	public List<ReportRecodeResult> getReportRecodeResultListByType(long type)throws AppException{
		Hql hql = new Hql("from ReportRecodeResult r where 1=1");
		hql.add(" and r.reportType=?");
		hql.addParamter(type);
		hql.add(" order by beginDate desc");
		Query query = this.getQuery(hql);
		if(query != null){
			return query.list();
		}
		return null;
	}

	public ReportRecodeResult getReportRecodeResultByDateType(Timestamp date,
			long type) throws AppException {
		Hql hql = new Hql("from ReportRecodeResult r where 1=1");
		String dateStr = date.toString().substring(0,date.toString().lastIndexOf("."));
		hql.add(" and  beginDate<=to_date(?,'yyyy-mm-dd hh24:mi:ss') and endDate>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
		hql.addParamter(dateStr);
		hql.addParamter(dateStr);
		hql.add(" and r.reportType=?");
		hql.addParamter(type);
		Query query = this.getQuery(hql);
		if(query != null){
			if(query.list().size()>0){
				return (ReportRecodeResult) query.list().get(0);
			}
		}
		return null;
	}
	
}