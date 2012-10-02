package com.fdays.tsms.airticket.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.airticket.StatisticsOrder;
import com.fdays.tsms.airticket.StatisticsOrderListForm;
import com.fdays.tsms.policy.SaleStatistics;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

public class StatisticsOrderDAOImp extends BaseDAOSupport implements StatisticsOrderDAO {
	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
			PlatformTransactionManager transactionManager) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}
	/**
	 * 分页获取所有StatisticsOrder
	 */
	@SuppressWarnings("unchecked")
	public List<StatisticsOrder> listStatisticsOrder(StatisticsOrderListForm solf) {
		Hql hql = new Hql();
		hql.add("from StatisticsOrder s where 1=1");
		if(solf.getSaleStatisticsId() > 0){
			hql.add(" and s.saleStatistics.id=?");
			hql.addParamter(solf.getSaleStatisticsId());
		}
		if(solf.getOrderNo() != null && !"".equals(solf.getOrderNo())){
			hql.add(" and s.orderNo like '%"+solf.getOrderNo()+"%'");
		}
		if(solf.getSort() != null && !"".equals(solf.getSort())){
			hql.add("order by s."+solf.getSort());
		}
		return this.list(hql,solf);
	}

	/**
	 * 根据ID删除StatisticsOrder对象
	 */
	public boolean deleteById(long id) throws AppException {
		if (id > 0) {
			try {
				StatisticsOrder statisticsOrder = (StatisticsOrder) this.getHibernateTemplate().get(
						StatisticsOrder.class, new Long(id));
				this.getHibernateTemplate().delete(statisticsOrder);
				return true;
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
		return false;
	}


	/**
	 * 删除所有记录
	 */
	public boolean deleteAll() {
		String hql = "delete StatisticsOrder";
		Query query;
		int row;
		try {
			query = this.getSession().createQuery(hql);
			row = query.executeUpdate();
			return true;
		} catch (DataAccessResourceFailureException e) {
			System.out.println(e.getMessage());
		} catch (HibernateException e) {
			System.out.println(e.getMessage());
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	/**
	 * 根据SaleStatistics获取StatisticsOrder
	 */
	public List<StatisticsOrder> getStatisticsOrderList(
			SaleStatistics saleStatistics) {
		return null;
	}

	/**
	 *  获取所有记录
	 */
	public List<StatisticsOrder> list() throws AppException {
		String hql = "from StatisticsOrder where 1=1";
		return this.list(hql);
	}

	
	public long merge(StatisticsOrder statisticsOrder) throws AppException {
		this.getHibernateTemplate().merge(statisticsOrder);
		return statisticsOrder.getId();
	}

	/**
	 * 根据ID获取StatisticsOrder对象
	 */
	public StatisticsOrder queryById(long id) throws AppException {
		Hql hql = new Hql("from StatisticsOrder where id=" + id);
		Query query = this.getQuery(hql);
		try {
			if (query != null && query.list() != null
					&& query.list().size() > 0) {
				return (StatisticsOrder) query.list().get(0);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 增加或更新StatisticsOrder对象
	 */
	public long saveOrUpdate(StatisticsOrder statisticsOrder)
			throws AppException {
		this.getHibernateTemplate().saveOrUpdate(statisticsOrder);
		return statisticsOrder.getId();
	}
	

	/**
	 * 批量插入、修改
	 * @param soList
	 * @return
	 */
	public boolean batchSaveOrUpdate(List<StatisticsOrder> soList) throws AppException{
		
		try {
			getHibernateTemplate().saveOrUpdateAll(soList);
			return true;
		} catch (DataAccessException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	/**
	 * 更改StatisticsOrder对象
	 */
	public void update(StatisticsOrder statisticsOrder) throws AppException {
		if (statisticsOrder.getId() > 0)
			this.getHibernateTemplate().update(statisticsOrder);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}
}
