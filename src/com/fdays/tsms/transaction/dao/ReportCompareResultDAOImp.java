package com.fdays.tsms.transaction.dao;

import java.sql.Timestamp;
import java.util.List;
import org.hibernate.Query;
import com.fdays.tsms.base.Constant;
import com.fdays.tsms.transaction.ReportCompareResult;
import com.fdays.tsms.transaction.ReportCompareResultListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class ReportCompareResultDAOImp extends BaseDAOSupport implements
		ReportCompareResultDAO {

	public List list(ReportCompareResultListForm ulf) throws AppException {
		Hql hql = new Hql();
		hql.add("from ReportCompareResult where 1=1");

		if (ulf.getName() != null && (!(ulf.getName().equals("")))) {
			hql.add(" and name like '%" + ulf.getName().trim() + "%'");
		}
		if (ulf.getUserNo() != null && (!(ulf.getUserNo().equals("")))) {
			hql.add(" and userNo like '%" + ulf.getUserNo().trim() + "%'");
		}

		if (ulf.getPaymenttoolId() > 0) {
			hql.add(" and paymenttoolId=? ");
			hql.addParamter(ulf.getPaymenttoolId());
		}
		if (ulf.getAccountId() > 0) {
			hql.add(" and accountId=? ");
			hql.addParamter(ulf.getAccountId());
		}

		if (ulf.getPlatformId() > 0) {
			hql.add(" and platformId=? ");
			hql.addParamter(ulf.getPlatformId());
		}

		// 对比类型 平台、网电、银行
		if (ulf.getCompareType() > 0) {
			hql.add(" and compareType=? ");
			hql.addParamter(ulf.getCompareType());
		}

		// 交易类型
		if (ulf.getTranType() > 0) {
			hql.add(" and tranType=? ");
			hql.addParamter(ulf.getTranType());
		}

		if (ulf.getStatus() > 0) {
			hql.add(" and status=? ");
			hql.addParamter(ulf.getStatus());
		}

		// 按日期搜索
		String startDate = Constant.toString(ulf.getBeginDateStr());
		String endDate = Constant.toString(ulf.getEndDateStr());
		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  beginDate  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		if ("".equals(startDate) == false && "".equals(endDate) == false) {
			hql
					.add(" and  endDate  between to_date(?,'yyyy-mm-dd hh24:mi:ss') and to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
			hql.addParamter(startDate);
			hql.addParamter(endDate);
		}

		hql.add(" and status not in(" + ReportCompareResult.STATES_0 + ") ");

		// System.out.println("hql>>>"+hql.getSql());

		return this.list(hql, ulf);
	}

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
				List list = query.list();
				if (list != null) {
					if (list.size() > 0) {
						return (ReportCompareResult) list.get(0);
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public ReportCompareResult getLastSameReportCompareResult(Timestamp date,
			long compareType) throws AppException {
		Hql hql = new Hql(" from ReportCompareResult r where 1=1");
		String dateStr = date.toString().substring(0,
				date.toString().lastIndexOf("."));
		hql.add(" and  beginDate<=to_date(?,'yyyy-mm-dd hh24:mi:ss') and endDate>=to_date(?,'yyyy-mm-dd hh24:mi:ss') ");
		hql.addParamter(dateStr);
		hql.addParamter(dateStr);
		hql.add(" and r.compareType=?");
		hql.addParamter(compareType);
		Query query = this.getQuery(hql);
		if (query != null) {
			List list=query.list();
			if(list!=null){
				int listSize=list.size();
				if(listSize>0){
					return (ReportCompareResult)list.get(listSize-1);
				}
			}
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
}