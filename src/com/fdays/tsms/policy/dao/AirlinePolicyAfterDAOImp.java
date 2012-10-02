package com.fdays.tsms.policy.dao;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.fdays.tsms.policy.AirlinePolicyAfter;
import com.fdays.tsms.policy.AirlinePolicyAfterListForm;
import com.fdays.tsms.policy.PolicyAfter;
import com.neza.base.BaseDAOSupport;
import com.neza.base.Hql;
import com.neza.exception.AppException;

/**
 * AirlinePolicyAfterDAO实现类
 * 
 * @author chenqx 2010-12-10
 */
public class AirlinePolicyAfterDAOImp extends BaseDAOSupport implements
    AirlinePolicyAfterDAO
{

	private TransactionTemplate transactionTemplate;

	public void setTransactionManager(
	    PlatformTransactionManager transactionManager)
	{
		this.transactionTemplate = new TransactionTemplate(transactionManager);
	}

	// 根据ID删除AirlinePolicyAfter对象
	public void deleteById(long id) throws AppException
	{
		if (id > 0)
		{
			AirlinePolicyAfter airlinePolicyAfter = (AirlinePolicyAfter) this
			    .getHibernateTemplate().get(AirlinePolicyAfter.class, new Long(id));
			this.getHibernateTemplate().delete(airlinePolicyAfter);
		}
	}

	// 根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAirlinePolicyAfterById(long id)
	{
		AirlinePolicyAfter airlinePolicyAfter;
		try
		{
			if (id > 0)
			{

				airlinePolicyAfter = (AirlinePolicyAfter) this.getHibernateTemplate()
				    .get(AirlinePolicyAfter.class, new Long(id));
				return airlinePolicyAfter;
			}
			else
				return new AirlinePolicyAfter();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return new AirlinePolicyAfter();
	}

	// 获取所有对象
	public List<AirlinePolicyAfter> list() throws AppException
	{
		String hql = "from AirlinePolicyAfter where 1=1";
		return this.list(hql);
	}

	// 获取指定承运人某个日期内有效的AirlinePolicyAfter集合
	public List<AirlinePolicyAfter> getAirLinePolicyAfterInTime(String carrier,
	    Timestamp ts)
	{
		Hql hql = new Hql();
		hql.add("from AirlinePolicyAfter a where 1=1");
		hql
		    .add(" and a.carrier = ?  and year(a.beginDate)=? and month(a.beginDate)=? "); // 承运人，起飞日期
		hql.addParamter(carrier);
		hql.addParamter(ts.toString().substring(0, 4));
		hql.addParamter(ts.toString().substring(5, 7));
		Query query = this.getQuery(hql);
		return query.list();
	}

	public long merge(AirlinePolicyAfter airlinePolicyAfter) throws AppException
	{
		this.getHibernateTemplate().merge(airlinePolicyAfter);
		return airlinePolicyAfter.getId();
	}

	// HQL根据ID获取AirlinePolicyAfter对象
	public AirlinePolicyAfter queryById(long id) throws AppException
	{
		Hql hql = new Hql("from AirlinePolicyAfter where id=" + id);
		Query query = this.getQuery(hql);
		try
		{
			if (query != null && query.list() != null && query.list().size() > 0) { return (AirlinePolicyAfter) query
			    .list().get(0); }
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	// 保存或更改对象
	public long save(AirlinePolicyAfter airlinePolicyAfter) throws AppException
	{
		this.getHibernateTemplate().saveOrUpdate(airlinePolicyAfter);
		return airlinePolicyAfter.getId();
	}

	// 更改对象
	public long update(AirlinePolicyAfter airlinePolicyAfter) throws AppException
	{
		if (airlinePolicyAfter.getId() > 0)
			return save(airlinePolicyAfter);
		else
			throw new IllegalArgumentException("id isn't a valid argument.");
	}

	// 动态参数获取对象（支持并分页）

	public List list(AirlinePolicyAfterListForm apalf) throws AppException
	{
		Hql hql = new Hql();
		hql.add("from AirlinePolicyAfter a where 1=1");
		if ((apalf.getCarrier() != null) // 承运人
		    && (!"".equals(apalf.getCarrier().toString().trim())))
		{
			hql.add(" and a.carrier like ? ");
			hql.addParamter("%" + apalf.getCarrier() + "%");
		}
		if (apalf.getBeginDate() != null)
		{ // 日期
			Timestamp tsBegin = apalf.getBeginDate();
			String start = "";
			start = tsBegin.toString();
			if (!"1970".equals(start.substring(0, 4)))
			{ // 不是1970，即有选择年
				if (1 == tsBegin.getSeconds())
				{ // 秒为1，即有选择月

					hql.add(" and year(a.beginDate)=? and month(a.beginDate)=? ");
					hql.addParamter(start.substring(0, 4));
					hql.addParamter(start.substring(5, 7));

				}
				else
				{ // 秒不为1，无选择月
					hql.add(" and year(a.beginDate)=? ");
					hql.addParamter(start.substring(0, 4));
				}
			}
			else
			{ // 年为1970，即无选择年
				if (1 == tsBegin.getSeconds())
				{ // 秒为1，即有选择月
					hql.add(" and month(a.beginDate)=?");
					hql.addParamter(tsBegin.getMonth() + 1); // +1的原因：Timestamp的月份是从0到11。
				}
			}
		}
		return this.list(hql, apalf);
	}

	// 根据承运人获取AirlinePolicyAfter对象
	public AirlinePolicyAfter getAppropriatePolicy(String carrier)
	{
		Hql hql = new Hql("from AirlinePolicyAfter where carrier='" + carrier
		    + "' and status=1 order by id desc");
		Query query = this.getQuery(hql);
		if (query != null)
		{
			List list = query.list();
			if (list != null && list.size() > 0)
			{
				AirlinePolicyAfter apa = (AirlinePolicyAfter) list.get(0);
				System.out.println(""+apa.getId());
				return apa;
			}
		}
		return null;
	}
}
