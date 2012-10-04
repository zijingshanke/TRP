package com.fdays.tsms.policy.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.SaleStatistics;
import com.fdays.tsms.policy.SaleStatisticsListForm;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

/**
 * SaleStatisticsDAOImp实现类
 * @author chenqx 
 * 2010-12-24
 */
public class SaleStatisticsDAOImp extends BaseDAOSupport implements
    SaleStatisticsDAO
{

	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
	    PlatformTransactionManager transactionManager)
	{
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	// 根据ID删除SaleStatistics对象
	public void deleteById(long id) throws AppException
	{
		if (id > 0)
		{
			SaleStatistics saleStatistics = (SaleStatistics) this
			    .getHibernateTemplate().get(SaleStatistics.class, new Long(id));
			this.getHibernateTemplate().delete(saleStatistics);
		}
	}

	// 根据ID获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsById(long id)
	{
		SaleStatistics saleStatistics;
		try
		{
			if (id > 0)
			{

				saleStatistics = (SaleStatistics) this.getHibernateTemplate()
				    .get(SaleStatistics.class, new Long(id));
				return saleStatistics;
			}
			else
				return new SaleStatistics();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new SaleStatistics();
	}
	

	// 获取所有对象
	public List<SaleStatistics> list() throws AppException
	{
		String hql = "from SaleStatistics where 1=1";
		return this.list(hql);
	}


	public long merge(SaleStatistics saleStatistics) throws AppException
	{
		this.getHibernateTemplate().merge(saleStatistics);
		return saleStatistics.getId();
	}

	// HQL根据ID获取SaleStatistics对象
	public SaleStatistics queryById(long id) throws AppException
	{
		Hql hql = new Hql("from SaleStatistics where id=" + id);
		Query query = this.getQuery(hql);
		try
		{
			if (query != null && query.list() != null && query.list().size() > 0) { 
				return (SaleStatistics) query.list().get(0); }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// 保存或更改对象
	public long save(SaleStatistics saleStatistics) throws AppException
	{
		this.getHibernateTemplate().saveOrUpdate(saleStatistics);
		return saleStatistics.getId();
	}

	// 更改对象
	public long update(SaleStatistics saleStatistics) throws AppException
	{
		if (saleStatistics.getId() > 0)
			return save(saleStatistics);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	// 动态参数获取对象（支持并分页）
	public List list(SaleStatisticsListForm sslf) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from SaleStatistics o where 1=1");
		if ((sslf.getCarrier() != null) // 承运人
		    && (!"".equals(sslf.getCarrier().toString().trim())))
		{
			hql.add(" and o.carrier like ? ");
			hql.addParamter("%" + sslf.getCarrier() + "%");
		}
		if (sslf.getBeginDate() != null){ // 日期
			Timestamp tsBegin = sslf.getBeginDate();
			String start = "";
			start = tsBegin.toString();
			if (!"1970".equals(start.substring(0, 4)))
			{ // 不是1970，即有选择年
				if (1 == tsBegin.getSeconds())
				{ // 秒为1，即有选择月

					hql.add(" and year(o.beginDate)=? and month(o.beginDate)=? ");
					hql.addParamter(start.substring(0, 4));
					hql.addParamter(start.substring(5, 7));

				}
				else
				{ // 秒不为1，无选择月
					hql.add(" and year(o.beginDate)=? ");
					hql.addParamter(start.substring(0, 4));
				}
			}
			else
			{ // 年为1970，即无选择年
				if (1 == tsBegin.getSeconds())
				{ // 秒为1，即有选择月
					hql.add(" and month(o.beginDate)=?");
					hql.addParamter(tsBegin.getMonth() + 1); // +1的原因：Timestamp的月份是从0到11。
				}
			}
		}
		return this.list(hql, sslf);
	}

	//根据承运人获取SaleStatistics对象
	public SaleStatistics getSaleStatisticsByCarrier(String carrier)
	{
		Hql hql = new Hql("from SaleStatistics where carrier='" + carrier
		    + "' and status=1 order by id desc");
		Query query = this.getQuery(hql);
		if (query != null)
		{
			List list = query.list();
			if (list != null && list.size() > 0) { 
				return (SaleStatistics) list.get(0); }
		}
		return null;
	}

}
