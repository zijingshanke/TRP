package com.fdays.tsms.policy.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.IndicatorStatistics;
import com.fdays.tsms.policy.IndicatorStatisticsListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class IndicatorStatisticsDAOImp extends BaseDAOSupport implements IndicatorStatisticsDAO {
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	public long saveOrUpdate(IndicatorStatistics indicatorStatistics) throws AppException {
		this.getHibernateTemplate().saveOrUpdate(indicatorStatistics);
		return indicatorStatistics.getId();
	}
	

	public long merge(IndicatorStatistics indicatorStatistics) throws AppException {
		this.getHibernateTemplate().merge(indicatorStatistics);
		return indicatorStatistics.getId();
	}

	public void update(IndicatorStatistics indicatorStatistics) throws AppException {
		if (indicatorStatistics.getId() > 0)
			this.getHibernateTemplate().update(indicatorStatistics);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
		
	}

	public void deleteById(long id) throws AppException {
		if (id > 0) {
			IndicatorStatistics indicatorStatistics = (IndicatorStatistics) this.getHibernateTemplate().get(
					IndicatorStatistics.class, new Long(id));
			this.getHibernateTemplate().delete(indicatorStatistics);

		}
	}

	public IndicatorStatistics getIndicatorStatisticsById(long id) {
		IndicatorStatistics indicatorStatistics;
		try {
			if (id > 0) {

				indicatorStatistics = (IndicatorStatistics) this.getHibernateTemplate().get(IndicatorStatistics.class,
						new Long(id));
				return indicatorStatistics;
			} else
				return new IndicatorStatistics();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new IndicatorStatistics();
	}


	public IndicatorStatistics queryById(long id) throws AppException {
		Hql hql = new Hql("from IndicatorStatistics where id=" + id);
		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (IndicatorStatistics) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public List list() throws AppException {
		String hql = "from IndicatorStatistics where 1=1";
		return this.list(hql);
	}

	//动态参数
	public List list(IndicatorStatisticsListForm islf) throws AppException {
		Hql hql = new Hql();
		hql.add("from IndicatorStatistics i where 1=1");
		if(islf.getAirlinePolicyAfterId() != 0){
			hql.add(" and airline_policy_after_id=? ");
			hql.addParamter(islf.getAirlinePolicyAfterId());
		}
		
		if (islf.getBeginDate() != null){ // 日期
			Timestamp tsBegin = islf.getBeginDate();
			String start = "";
			start = tsBegin.toString();
			if (!"1970".equals(start.substring(0, 4))){		 	// 不是1970，即有选择年
				if (1 == tsBegin.getSeconds()){ 				// 秒为1，即有选择月
					hql.add(" and year(i.beginDate)=? and month(i.beginDate)=? ");
					hql.addParamter(start.substring(0, 4));
					hql.addParamter(start.substring(5, 7));

				}
				else{ // 秒不为1，无选择月
					hql.add(" and year(i.beginDate)=? ");
					hql.addParamter(start.substring(0, 4));
				}
			}else{ 												// 年为1970，即无选择年
				if (1 == tsBegin.getSeconds()){ 				// 秒为1，即有选择月
					hql.add(" and month(i.beginDate)=?");
					hql.addParamter(tsBegin.getMonth() + 1); // +1的原因：Timestamp的月份是从0到11。
				}
			}
		}
		return this.list(hql, islf);
	}

	//根据AirlinePolicyAfter获取IndicatorStatistics
	public Set<IndicatorStatistics> getByAirlinePolicyAfter(AirlinePolicyAfter airlinePolicyAfter) {
		
		return airlinePolicyAfter.getIndicatorStatisticss();
	}
	
	//HQL获取IndicatorStatistics集合
	public List<IndicatorStatistics> getIndicatorStatisticsList(IndicatorStatisticsListForm islf){
		Hql hql = new Hql();
		hql.add("from IndicatorStatistics where id="+islf.getId());
		return this.list(hql);
	}

}
